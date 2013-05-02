
options(width=180)

ca=commandArgs(trailingOnly=TRUE) #only args after --args
#interpret all elements of ca as names of case studies

caseStudies = ca[!is.na(ca)]
csInternalData = vector("list",length(caseStudies))
csExternalData = vector("list",length(caseStudies))
for (i in 1:length(caseStudies)) {
		cat("reading case study data \"",caseStudies[i],"\"\n", sep="")
		int = read.csv(file=paste("../",caseStudies[i],"/inttimetypechecker.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
		ext = read.csv(file=paste("../",caseStudies[i],"/exttimetypechecker.csv",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
		
		if (nrow(int) != nrow(ext)) {
			stop("inconsistent input tables")
		}
		#if (! all(int['variant'] == ext['variant'])) {
		#	stop("inconsistent variants in input tables")
		#}
		csInternalData[[i]]<-int
		csExternalData[[i]]<-ext
}

#cat("externalTable: ")
#print(externalTable)


ex_color <- rgb(51, 102, 255, maxColorValue=255)
bdd_color <- rgb(204, 0, 51, maxColorValue=255)	
bool_color <- rgb(0, 133, 133, maxColorValue=255)	
intEq_color <- rgb(153,115,0, maxColorValue=255)
intAdd_color <- rgb(0,204,51, maxColorValue=255)
pred_color <- rgb(153, 0, 204, maxColorValue=255)

getProductTime <- function(caseStudyID, intable) {
	ext <- csExternalData[[caseStudyID]]
	prodlines = ext[ext$variant!='family',]
	sum((prodlines$usertime))+sum((prodlines$systemtime))
}
getProductTimeVector <- function(caseStudyID, intable) {
	ext <- csExternalData[[caseStudyID]]
	prodlines = ext[ext$variant!='family',]
	(prodlines$usertime+prodlines$systemtime)
}
getFamilyTime <- function(caseStudyID, intable) {
	ext <- csExternalData[[caseStudyID]]
	famline = ext[ext$variant=='family',]
	if (nrow(famline) < 1) stop(paste("no family measurement for case study ",caseStudies[caseStudyID], "!", sep=""))
	if (nrow(famline) > 1) stop(paste("too many family measurements for case study ", caseStudies[caseStudyID], "!", sep=""))
	sum(famline$usertime[1],famline$systemtime[1])
}

plotData <- matrix(nrow=2, ncol=length(caseStudies))

for (i in 1:length(caseStudies)) {
	plotData[1, i] = getProductTime(i)
	plotData[2, i] = getFamilyTime(i)
}
yLimits=c(1,max(plotData)*2) # logarithmic plot, so we need to make the y axis larger
color=c(ex_color,bdd_color)
pdf(file=paste("plot_ext.pdf",sep=""), width=8,height=5,onefile=TRUE,paper="special")
barplot(plotData, beside=TRUE, space=c(0.1,0.5), col=color, log="y", ylim=yLimits, xaxt="n")

legend("topleft",c('product','family'), inset = .01,fill=color)
positions=(((0:(length(caseStudies)-1)) *2.6)+1.5)
axis(1, pos=1.3, at=positions, labels=caseStudies, cex.axis=1, tick=FALSE, las=3)
axis(1, at=positions-0.5, labels=rep("",length(caseStudies)), cex.axis=1)
axis(1, at=positions+0.6, labels=rep("",length(caseStudies)), cex.axis=1)

warnings()
dev.off()
