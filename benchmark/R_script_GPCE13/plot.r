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
csInternalData_fam_noOpt = vector("list",length(caseStudies))
for (i in 1:length(caseStudies)) {
  cat(i, ") reading case study data \"",caseStudies[i],"\"\n", sep="")
  int = read.csv(file=paste("../resultBackup_with_optimization_rev1110/",caseStudies[i],"/inttimetypechecker.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  ext = read.csv(file=paste("../resultBackup_with_optimization_rev1110/",caseStudies[i],"/exttimetypechecker.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  int_noOpt = read.csv(file=paste("../resultBackup_without_optimization_rev1102/",caseStudies[i],"/inttimetypechecker.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  ext_noOpt = read.csv(file=paste("../resultBackup_without_optimization_rev1102/",caseStudies[i],"/exttimetypechecker.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  
  csInternalData_features[[i]] <- read.csv(file=paste("../resultBackup_with_optimization_rev1110/",caseStudies[i],"/inttimetypechecker_featurebased.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  csExternalData_features[[i]] <- read.csv(file=paste("../resultBackup_with_optimization_rev1110/",caseStudies[i],"/exttimetypechecker_featurebased.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
  
  csInternalData_features[[i]][is.na(csInternalData_features[[i]])] <- c(0)
  int[is.na(int)] <- c(0)
  
  csInternalData_fam[[i]] = int[int$variant == "family", ]
  csInternalData_fam_noOpt[[i]] = int_noOpt[int$variant=="family", ]
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

bytecodeCompTimes = read.csv(file=paste("bytecode_typecheck.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
print("bytecodecomp:")
print(bytecodeCompTimes[bytecodeCompTimes$name=="GPL",])

getFeatureTimeInt <- function(caseStudyID) {
  prodlines = csInternalData_features[[caseStudyID]]
  c(sum((prodlines$ASTcomp)),sum((prodlines$typecheck)))
}
getFeatureTimeInt_wBytecodeComp <- function(caseStudyID) {
  bytecodeCompTime=bytecodeCompTimes[bytecodeCompTimes$name=="GPL",]$bytecode_typecheck
  prodlines = csInternalData_features[[caseStudyID]]
  c(sum((prodlines$ASTcomp)),sum((prodlines$typecheck)),sum((bytecodeCompTime)))
}
getProdTimeInt <- function(caseStudyID) {
  prodlines = csInternalData[[caseStudyID]]
  c(sum((prodlines$ASTcomp)),sum((prodlines$typecheck)))
}
getFamTimeInt <- function(caseStudyID) {
  prodlines = csInternalData_fam[[caseStudyID]]
  c(sum((prodlines$ASTcomp)),sum((prodlines$typecheck)))
}

getFamTimeInt_noOpt <- function(caseStudyID) {
  prodlines = csInternalData_fam_noOpt[[caseStudyID]]
  c(sum((prodlines$ASTcomp)),sum((prodlines$typecheck)))
}

plotData <- matrix(nrow=2, ncol=length(caseStudies))
for (i in 1:length(caseStudies)) {
  plotData[,i] = getProdTimeInt(i)/1000 # product time
}
#print (plotData)

plotDataFeat <- matrix(nrow=2, ncol=length(caseStudies))
for (i in 1:length(caseStudies)) {
  plotDataFeat[,i] = getFeatureTimeInt(i)/1000 # feature time
}
#print (plotDataFeat)

plotDataFam <- matrix(nrow=2, ncol=length(caseStudies))
for (i in 1:length(caseStudies)) {
  plotDataFam[,i] = getFamTimeInt(i)/1000 # fam time
}
#print (plotDataFam)

plotDataFeat_wBytecodeComp <- matrix(nrow=3, ncol=length(caseStudies))
for (i in 1:length(caseStudies)) {
  plotDataFeat_wBytecodeComp[,i] = getFeatureTimeInt_wBytecodeComp(i)/1000 # fam time
}
print (plotDataFeat_wBytecodeComp)

plotDataFam_noOpt <- matrix(nrow=2, ncol=length(caseStudies))
for (i in 1:length(caseStudies)) {
  plotDataFam_noOpt[,i] = getFamTimeInt_noOpt(i)/1000 # fam time
}
#print (plotDataFam_noOpt)

getMaxY <- function(plotData, plotDataFeat, plotDataFam, plotDataFam_noOpt) {
  maximum = 0
  for (i in 1:ncol(plotData)) {
    maximum = max( sum(plotData[,i]), sum(plotDataFeat[,i]), sum(plotDataFam[,i]), sum(plotDataFam_noOpt[,i]), maximum)
  }
  maximum
}

if (draft) { # use colors
	color <- c("lightblue", "lightskyblue", # prod
				"tomato2", "firebrick3", # feat
				"olivedrab2", "chartreuse3" # fam
	)
	textcolor="firebrick3"
} else { # use black/white
	color <- c(rgb(0, 0, 0), rgb(1, 1, 1), rgb(0.7, 0.7, 0.7)) # setup, typecheck, bytecodeComp
	textcolor=rgb(0, 0, 0)
}
if (!draft) pdf(file=paste("plot_int.","pdf",sep=""), width=9, height=5, onefile=TRUE, paper="special") 

#layout(matrix(c(1,2,3,4,5,6,7,8,9,10,11,12,13,13), 2, 7, byrow = TRUE))
layout(matrix(c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,14), 3, 5, byrow = TRUE))

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
				sum(t(t(plotDataFam[,i]))),
				sum(t(t(plotDataFam_noOpt[,i]))))
  if (log=="y" && ! caseStudies[i] =="GPL") {
	maxY=maxY*10 # y axis must be longer, because it is logarithmic
  }
  yLimits=c(0,maxY)
  if (paintLog) yLimits[1]=0.01
  xLimits=c(1,9)
#  if (caseStudies[i]=="Violet") {
#    plot.new() # insert an empty plot to allow for violet's large y axis labels
#  }
  plot.new()
  if (i==1 || i==6 || i==11) {
    title(main=caseStudies[i],ylab="Time (Seconds)          ",cex.lab=0.9, mgp=c(3,0,0))
  } else {
      title(main=caseStudies[i])
  }
  # c(bottom, left, top, right)
  par(mar=c(2, 4, 3, 1))
  par(new=TRUE)
  barplot(t(t(plotData[,i])), #, t(t(as.matrix(c(10000,10000,10000,10000))))
		#beside=TRUE,
		space=c(1.1),
		col=color[1:2],
		
		ylim=yLimits,
		xlim=xLimits,
		xaxt="n",
		yaxt="n",
		log=log,
		cex.lab=1.3,
  )
  if (caseStudies[i] =="Violet") {
	textpos = sum(t(t(plotData[,i])))
	text(x=c(1.64), y=c(textpos), labels=c("x"), col=textcolor, cex=2)
  }
  par(new=TRUE)
  barplot(t(t(plotDataFeat[,i])),
          #beside=TRUE,
          space=c(2.5),
          col=color[1:2],
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
          col=color[1:2],
          ylim=yLimits,
          xlim=xLimits,
          xaxt="n",
          log=log,
          yaxt="n",
  )
  par(new=TRUE)
  barplot(t(t(plotDataFeat_wBytecodeComp[,i])),
          #beside=TRUE,
          space=c(6.5),
          col=color[1:3],
          ylim=yLimits,
          xlim=xLimits,
          xaxt="n",
          log=log,
          yaxt="n",
  )
  par(new=TRUE)
  barplot(t(t(plotDataFam_noOpt[,i])),
          #beside=TRUE,
          space=c(8),
          col=color[1:2],
          ylim=yLimits,
          xlim=xLimits,
          xaxt="n",
          log=log,
          yaxt="n",
  )
  axis(1, tick=FALSE, at=c(1.1,2.5,4,6.5,8.1)+0.5,labels=c("PB","FT","FM","FT'","FM'"), cex.axis=0.8, mgp=c(3,0,0))
	if (par("ylog")) {
		# 10er potenzen falls die achse logarithmisch ist
		aty <- exp(log(10)*seq(log10(yLimits[1]), log10(par("yaxp")[2]),by=1))
	} else {
		# sonst die Skala vom Plot übernehmen
		aty <- seq(yLimits[1], par("yaxp")[2], (par("yaxp")[2] - par("yaxp")[1])/par("yaxp")[3])
	}
	#big.mark is the thousand-seperator
	axis(2, at=aty, labels=formatC(aty, big.mark=" ",digits = 2, format="fg"), hadj=0.9, cex.axis=1,cex.lab=3, las=2)
}

plot.new()
# c(bottom, left, top, right)
par(mar=c(1,0,1,0)) 
plot.new()
legend("topleft",
       c(
       "PB   Product-Based",
       "FT    Feature Based",
       "FM   Family Based",
       "FT'   Feature Based With Bytecode Check",
       "FM'  Family Based Without Optimization"),
       inset = 0, cex=1)
       
#par(mar=c(0,0,0,0)) 
#plot.new()
legend("bottomleft",
       c('Setup',
       'Check',
         'Bytecode Check'),
       inset = 0, fill=color, cex=1)

warnings()
dev.off()

#######################################################
## Output LaTeX table with measurement results
cat("Results LaTeX table for Product-Based (Setup, Check, Total) | Feature-Based (Setup, Check, Total, PB/FT) | Family-Based (Setup, Check, Total, PB/FM, FT/FM)\n")
violetMarker <- ""
strategyDataRowLatex_prod <- function(splNr, mark=FALSE) {
  if (mark && caseStudies[splNr] == "Violet") {
    violetMarker <- "\\textsuperscript{*}"
  } else {
    violetMarker <- ""
  }
  paste("\\num{", plotData[1,splNr], "}", violetMarker, " & \\num{",  plotData[2,splNr], "}", violetMarker, " & \\num{", plotData[1,splNr] + plotData[2,splNr], "}", violetMarker, sep = "")
}
strategyDataRowLatex_feat <- function(splNr, mark=FALSE) {
  if (mark && caseStudies[splNr] == "Violet") {
    violetMarker <- "\\textsuperscript{*}"
  } else {
    violetMarker <- ""
  }
  paste("\\num{", plotDataFeat[1,splNr], "}", violetMarker, " & \\num{",  plotDataFeat[2,splNr], "}", violetMarker, " & \\num{", plotDataFeat[1,splNr] + plotDataFeat[2,splNr], "}", violetMarker, " & \\num{", 
		formatC((plotData[1,splNr]+plotData[2,splNr])/(plotDataFeat[1,splNr]+plotDataFeat[2,splNr]), big.mark=" ",digits = 2, format="fg"), "}", violetMarker, sep = "")
}
strategyDataRowLatex_fam <- function(splNr, mark=FALSE) {
  if (mark && caseStudies[splNr] == "Violet") {
    violetMarker <- "\\textsuperscript{*}"
  } else {
    violetMarker <- ""
  }
  paste("\\num{", plotDataFam[1,splNr], "}", violetMarker, " & \\num{",  plotDataFam[2,splNr], "}", violetMarker, " & \\num{", plotDataFam[1,splNr] + plotDataFam[2,splNr], "}",  violetMarker,
		" & \\num{", formatC((plotData[1,splNr]+plotData[2,splNr])/(plotDataFam[1,splNr]+plotDataFam[2,splNr])), "}", violetMarker,
		" & \\num{", formatC((plotDataFeat[1,splNr]+plotDataFeat[2,splNr])/(plotDataFam[1,splNr]+plotDataFam[2,splNr])), "}", violetMarker, sep = "")
}
for (i in 1:length(caseStudies)) {
  cat(caseStudies[i], " & ", strategyDataRowLatex_prod(i, TRUE), " & ", strategyDataRowLatex_feat(i), " & ", strategyDataRowLatex_fam(i), "\\\\\n", sep = "")
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
