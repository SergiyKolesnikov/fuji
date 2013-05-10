options(width=180)

ca=commandArgs(trailingOnly=TRUE) #only args after --args
#ca=c("EPL", "GPL", "GUIDSL", "Prevayler")

#interpret all elements of ca as names of case studies
caseStudies = ca[!is.na(ca)]
csInternalData = vector("list",length(caseStudies))
csExternalData = vector("list",length(caseStudies))
csExternalData_features = vector("list",length(caseStudies))
csInternalData_features = vector("list",length(caseStudies))
for (i in 1:length(caseStudies)) {
  cat("reading case study data \"",caseStudies[i],"\"\n", sep="")
  int = read.csv(file=paste("./",caseStudies[i],"/inttimetypechecker.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  ext = read.csv(file=paste("./",caseStudies[i],"/exttimetypechecker.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  
  csInternalData_features[[i]] <- read.csv(file=paste("./",caseStudies[i],"/inttimetypechecker_featurebased.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  csExternalData_features[[i]] <- read.csv(file=paste("./",caseStudies[i],"/exttimetypechecker_featurebased.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  
  csInternalData_features[[i]][is.na(csInternalData_features[[i]])] <- c(0)
  int[is.na(int)] <- c(0)
  
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
  prodlines = prodlines[prodlines$variant != "family", ]
  c(sum((prodlines$ASTcomp)),sum((prodlines$typecheck)))
}
getProdTimeInt <- function(caseStudyID) {
  prodlines = csInternalData[[caseStudyID]]
  prodlines = prodlines[prodlines$variant != "family", ]
  c(sum((prodlines$ASTcomp)),sum((prodlines$typecheck)))
}

plotData <- matrix(nrow=4, ncol=length(caseStudies))
for (i in 1:length(caseStudies)) {
  plotData[,i] = rbind(as.matrix(getProdTimeInt(i)), as.matrix(c(0,0))) # feature time
}
print (plotData)

plotDataFeat <- matrix(nrow=4, ncol=length(caseStudies))
for (i in 1:length(caseStudies)) {
  plotDataFeat[,i] = rbind(as.matrix(c(0,0)), as.matrix(getFeatureTimeInt(i))) # feature time
}
print (plotDataFeat)

ex_color <- rgb(51, 102, 255, maxColorValue=255)
bdd_color <- rgb(204, 0, 51, maxColorValue=255)  
bool_color <- rgb(0, 133, 133, maxColorValue=255)	
intEq_color <- rgb(153,115,0, maxColorValue=255)
intAdd_color <- rgb(0,204,51, maxColorValue=255)
pred_color <- rgb(153, 0, 204, maxColorValue=255)

getMaxY <- function(plotData, plotDataFeat) {
  maximum = 0
  for (i in 1:ncol(plotData)) {
    maximum = max( sum(plotData[,i]), sum(plotDataFeat[,i]), maximum)
  }
  maximum
}

yLimits=c(1,getMaxY(plotData, plotDataFeat)) # logarithmic plot, so we need to make the y axis larger
xLimits=c(0,length(caseStudies)*4)
color <- c("lightblue", "lightskyblue", # prod
            "tomato2", "red2", "firebrick3", # feat
            "olivedrab2", "lawngreen", "chartreuse3" # fam
)


pdf(file=paste("plot_int.","pdf",sep=""), width=8, height=5, onefile=TRUE, paper="special") 

for (i in 1:length(caseStudies)) {  
  par(new=TRUE)
  barplot(cbind( t(t(plotData[,i])), t(t(plotDataFeat[,i])) ), #, t(t(as.matrix(c(10000,10000,10000,10000))))
        #beside=TRUE,
        space=c((i-1)*3, 0.1),
        col=color,
        ylim=yLimits,
        xlim=xLimits,
        xaxt="n"
        #log="y",
  )
}

legend("topright",c('product-based ASTComp','product-based typecheck','feature-based ASTComp','feature-based typecheck'), inset = .01, fill=color)
positions=(((0:(length(caseStudies)-1)) * 3.0 ) ) # begin of case study
axis(1, pos=1.1, at=positions+1.1, labels=caseStudies, cex.axis=1, tick=FALSE, las=3) #labels
axis(1, at=positions+0.5, labels=rep("",length(caseStudies)), cex.axis=1)
axis(1, at=positions+1.6, labels=rep("",length(caseStudies)), cex.axis=1)
#axis(1, at=positions+2.625, labels=rep("",length(caseStudies)), cex.axis=1)

warnings()
dev.off()
