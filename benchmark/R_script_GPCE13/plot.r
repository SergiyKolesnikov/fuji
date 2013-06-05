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
  cat(i, ") reading case study data \"",caseStudies[i],"\"\n", sep="")
  int = read.csv(file=paste("../resultBackup_with_optimization_rev1110/",caseStudies[i],"/inttimetypechecker.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  ext = read.csv(file=paste("../resultBackup_with_optimization_rev1110/",caseStudies[i],"/exttimetypechecker.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  
  csInternalData_features[[i]] <- read.csv(file=paste("../resultBackup_with_optimization_rev1110/",caseStudies[i],"/inttimetypechecker_featurebased.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  csExternalData_features[[i]] <- read.csv(file=paste("../resultBackup_with_optimization_rev1110/",caseStudies[i],"/exttimetypechecker_featurebased.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  
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

if (draft) { # use colors
	color <- c("lightblue", "lightskyblue", # prod
				"tomato2", "firebrick3", # feat
				"olivedrab2", "chartreuse3" # fam
	)
	xcolor="firebrick3"
} else { # use black/white
	color <- c(	rgb(0.9, 0.9, 0.9), rgb(1, 1, 1),  # prod
				 rgb(0.5, 0.5, 0.5), rgb(0.7, 0.7, 0.7), # feat
				rgb(0, 0, 0), rgb(0.4, 0.4, 0.4) # fam
	)
	xcolor=rgb(0, 0, 0)
}
if (!draft) pdf(file=paste("plot_int.","pdf",sep=""), width=9, height=5, onefile=TRUE, paper="special") 

layout(matrix(c(1,2,3,4,5,6,7,8,9,10,11,12,13,13), 2, 7, byrow = TRUE))

for (i in 1:length(caseStudies)) {  
  paintLog=TRUE
  log="y"
  #for(logcs in c("EPL", "GPL", "GUIDSL", "Notepad", "TankWar", "Violet"))  {
  #for(logcs in c())  {
	#if (caseStudies[i]==logcs) {
	#	paintLog=TRUE
	#	log="y"
	#}
  #}
  #if (i != 1) par(new=TRUE)
  maxY = max (	sum (t(t(plotData[,i]))),
				sum(t(t(plotDataFeat[,i]))),
				sum(t(t(plotDataFam[,i]))))
  if (log=="y" && ! caseStudies[i] =="GPL") {
	maxY=maxY*10 # y axis must be longer, because it is logarithmic
  }
  yLimits=c(0,maxY)
  if (paintLog) yLimits[1]=1
  xLimits=c(1,5)
  plot.new()
  title(main=caseStudies[i])
  # c(bottom, left, top, right)
  par(mar=c(2, 4.5, 4, 2))
  par(new=TRUE)
  barplot(t(t(plotData[,i])), #, t(t(as.matrix(c(10000,10000,10000,10000))))
		#beside=TRUE,
		space=c(1.1),
		col=color[2:1],
		
		ylim=yLimits,
		xlim=xLimits,
		xaxt="n",
		yaxt="n",
		log=log,
		cex.lab=1.3
  )
  if (caseStudies[i] =="Violet") {
	textpos = sum(t(t(plotData[,i])))
	text(x=c(1.64), y=c(textpos), labels=c("x"), col=xcolor, cex=2)
  }
  par(new=TRUE)
  barplot(t(t(plotDataFeat[,i])),
          #beside=TRUE,
          space=c(2.5),
          col=color[4:3],
          
          ylim=yLimits,
          xlim=xLimits,
          xaxt="n",
          log=log,
          yaxt="n",
  )
  par(new=TRUE)
  barplot(t(t(plotDataFam[,i])),
          #beside=TRUE,
          space=c(4),
          col=color[6:5],
          
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
       c('Product-Based Type Check',
       'Product-Based Setup',
         'Feature-Based Type Check',
         'Feature-Based Setup',
         'Family-Based Type Check',
         'Family-Based Setup'),
       inset = 0, fill=color, cex=1.2)

warnings()
dev.off()

#######################################################
## Output LaTeX table with measurement results
cat("Results LaTeX table for Product-Based | Feature-Based | Family-Based\n")
cat("Name & Setup & Check & Total & Setup & Check & Total & Setup & Check & Total\\\\\n")
cat("\\midrule\n")
strategyDataRow <- function(data, splNr) {
  paste(data[1,splNr], " & ",  data[2,splNr], " & ", data[1,splNr] + data[2,splNr], sep = "")
}
for (i in 1:length(caseStudies)) {
  cat(caseStudies[i], " & ", strategyDataRow(plotData, i), " & ", strategyDataRow(plotDataFeat, i), " & ", strategyDataRow(plotDataFam, i), "\\\\\n", sep = "")
}
cat("\n")
#######################################################
## Output CSV table with measurement results
cat("Results CSV table for Product-Based | Feature-Based | Family-Based\n")
cat("Name,PD−B Setup,PD−B Check,PD−B Total,FT−B Setup,FT−B Check,FT−B Total,FM−B Setup,FM−B Check,FM−B Total\n")
strategyDataRow <- function(data, splNr) {
  paste(data[1,splNr], ",",  data[2,splNr], ",", data[1,splNr] + data[2,splNr], sep = "")
}
for (i in 1:length(caseStudies)) {
  cat(caseStudies[i], ",", strategyDataRow(plotData, i), ",", strategyDataRow(plotDataFeat, i), ",", strategyDataRow(plotDataFam, i), "\n", sep = "")
}
cat("\n")

#######################################################
## plot sum over all case studies

plotDataSum <- matrix(nrow=1, ncol=3)
plotDataSum[1,1] = sum(plotData)
plotDataSum[1,2] = sum(plotDataFeat)
plotDataSum[1,3] = sum(plotDataFam)
print(plotDataSum)

colorSum <- c(color[1], color[3], color[5])
names = c('product-based', 'feature-based', 'family-based')

if (!draft) pdf(file=paste("plot_int_sum",".pdf",sep=""), width=8, height=3, onefile=TRUE, paper="special") 

logX = "" # "x" for logarithmic x-scale, "" otherwise
xLimitsSum=c(0,max(plotDataSum))
if (logX == "x") {
 xLimitsSum[1] = 1
}

par(las=2) # make label text perpendicular to axis
par(mar=c(5,8,0,2)) # increase y-axis margin.
barplot(plotDataSum,
        beside=TRUE,
        space=c(0.1, 0.1),
        col=colorSum[1:3],
        names.arg = names,
        horiz=TRUE,
        ylim=c(0,4)
)

warnings()
if (!draft) dev.off()
