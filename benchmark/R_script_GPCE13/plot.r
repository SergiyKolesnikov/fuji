draft = F
if (draft) dev.off()


options(width=180)

ca=commandArgs(trailingOnly=TRUE) #only args after --args
if (draft) ca=c("EPL", "GPL", "GUIDSL", "Prevayler")

#interpret all elements of ca as names of case studies
caseStudies = ca[!is.na(ca)]
csInternalData = vector("list",length(caseStudies))
csExternalData = vector("list",length(caseStudies))
csExternalData_features = vector("list",length(caseStudies))
csInternalData_features = vector("list",length(caseStudies))
csInternalData_fam = vector("list",length(caseStudies))
for (i in 1:length(caseStudies)) {
  cat("reading case study data \"",caseStudies[i],"\"\n", sep="")
  int = read.csv(file=paste("../",caseStudies[i],"/inttimetypechecker.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  ext = read.csv(file=paste("../",caseStudies[i],"/exttimetypechecker.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  
  csInternalData_features[[i]] <- read.csv(file=paste("../",caseStudies[i],"/inttimetypechecker_featurebased.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  csExternalData_features[[i]] <- read.csv(file=paste("../",caseStudies[i],"/exttimetypechecker_featurebased.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  
  csInternalData_features[[i]][is.na(csInternalData_features[[i]])] <- c(0)
  int[is.na(int)] <- c(0)
  
  csInternalData_fam[[i]] = int[int$variant == "family", ]
  int = int[int$variant != "family", ]
  ext = ext[ext$variant != "family", ]
  
  if (nrow(int) != nrow(ext)) {
    stop("inconsistent input tables")
  }
  #if (! all(int['variant'] == ext['variant'])) {
  #	stop("inconsistent variants in input tables")
  #}
  csInternalData[[i]]<-int
  csExternalData[[i]]<-ext
}

getFeatureTimeInt <- function(caseStudyID) {
  prodlines = csInternalData_features[[caseStudyID]]
  c(sum((prodlines$ASTcomp)),sum((prodlines$typecheck)))
}
getProdTimeInt <- function(caseStudyID) {
  prodlines = csInternalData[[caseStudyID]]
  c(sum((prodlines$ASTcomp)),sum((prodlines$typecheck)))
}
getFamTimeInt <- function(caseStudyID) {
  prodlines = csInternalData_fam[[caseStudyID]]
  c(sum((prodlines$ASTcomp)),sum((prodlines$typecheck)))
}

plotData <- matrix(nrow=2, ncol=length(caseStudies))
for (i in 1:length(caseStudies)) {
  plotData[,i] = getProdTimeInt(i) # product time
}
print (plotData)

plotDataFeat <- matrix(nrow=2, ncol=length(caseStudies))
for (i in 1:length(caseStudies)) {
  plotDataFeat[,i] = getFeatureTimeInt(i) # feature time
}
print (plotDataFeat)

plotDataFam <- matrix(nrow=2, ncol=length(caseStudies))
for (i in 1:length(caseStudies)) {
  plotDataFam[,i] = getFamTimeInt(i) # fam time
}
print (plotDataFam)

getMaxY <- function(plotData, plotDataFeat, plotDataFam) {
  maximum = 0
  for (i in 1:ncol(plotData)) {
    maximum = max( sum(plotData[,i]), sum(plotDataFeat[,i]), sum(plotDataFam[,i]), maximum)
  }
  maximum
}

color <- c("lightblue", "lightskyblue", # prod
            "tomato2", "firebrick3", # feat
            "olivedrab2", "chartreuse3" # fam
)

if (!draft) pdf(file=paste("plot_int.","pdf",sep=""), width=9, height=5, onefile=TRUE, paper="special") 
par(mfrow=c(2,7))
layout(matrix(c(1,2,3,4,5,6,7,8,9,10,11,12,13,13), 2, 7, byrow = TRUE))

for (i in 1:length(caseStudies)) {  
  paintLog=FALSE
  log=""
  for(logcs in c("EPL", "GPL", "GUIDSL", "Notepad", "TankWar", "Violet"))  {
	if (caseStudies[i]==logcs) {
		paintLog=TRUE
		log="y"
	}
  }
  #if (i != 1) par(new=TRUE)
  maxY = max (	sum (t(t(plotData[,i]))),
				sum(t(t(plotDataFeat[,i]))),
				sum(t(t(plotDataFam[,i]))))
  yLimits=c(0,maxY)
  if (paintLog) yLimits[1]=1
  xLimits=c(1,5)
  barplot(t(t(plotData[,i])), #, t(t(as.matrix(c(10000,10000,10000,10000))))
        #beside=TRUE,
        space=c(1),
        col=color[1:2],
        ylim=yLimits,
        xlim=xLimits,
        xaxt="n",
        yaxt="n",
        log=log,
        xlab=caseStudies[i],
        cex.lab=1.3
  )
  par(new=TRUE)
  barplot(t(t(plotDataFeat[,i])), #, t(t(as.matrix(c(10000,10000,10000,10000))))
          #beside=TRUE,
          space=c(2.5),
          col=color[3:4],
          ylim=yLimits,
          xlim=xLimits,
          xaxt="n",
          log=log,
          yaxt="n",
  )
  par(new=TRUE)
  barplot(t(t(plotDataFam[,i])), #, t(t(as.matrix(c(10000,10000,10000,10000))))
          #beside=TRUE,
          space=c(4),
          col=color[5:6],
          ylim=yLimits,
          xlim=xLimits,
          xaxt="n",
          log=log,
          yaxt="n",
  )
  axis(1, tick=FALSE, at=c(1,2,3),labels=c("","","")) #suppress x axis
	if (par("ylog")) {
		# 10er potenzen falls die achse logarithmisch ist
		aty <- exp(log(10)*seq(log10(yLimits[1]),log10(par("yaxp")[2]),by=1))
	} else {
		# sonst die Skala vom Plot übernehmen
		aty <- seq(yLimits[1], par("yaxp")[2], (par("yaxp")[2] - par("yaxp")[1])/par("yaxp")[3])
	}
	#big.mark is the thousand-seperator
	axis(2, at=aty, labels=format(aty, scientific=FALSE, big.mark=" "), hadj=0.9, cex.axis=1,cex.lab=3, las=2)
}

par(mar=c(0,0,0,0)) 
plot.new()
legend("center",
       c('product-based ASTComp','product-based typecheck',
         'feature-based ASTComp','feature-based typecheck',
         'family-based ASTComp','family-based typecheck'),
       inset = 0, fill=color, cex=1.2)

warnings()
if (!draft) dev.off()
