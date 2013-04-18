
options(width=180)

ca=commandArgs(trailingOnly=TRUE) #only args after --args
#interpret all elements of ca as names of case studies

caseStudies = ca[!is.na(ca)]
csInternalData = vector("list",length(caseStudies))
csExternalData = vector("list",length(caseStudies))
for (i in 1:length(caseStudies)) {
		cat("reading case study data \"",caseStudies[i],"\"\n", sep="")
		int = read.csv(file=paste("../",caseStudies[i],"/inttimetypechecker.txt",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
		ext = read.csv(file=paste("../",caseStudies[i],"/exttimetypechecker.txt",sep=""),head=TRUE, sep="\t", na.strings=c("","NA"))
		
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
getFamilyTime <- function(caseStudyID, intable) {
	ext <- csExternalData[[caseStudyID]]
	famline = ext[ext$variant=='family',]
	if (nrow(famline) != 1) stop("too many family measurements?")
	sum(famline$usertime[1],famline$systemtime[1])
}

plotData <- matrix(nrow=2, ncol=length(caseStudies))

for (i in 1:length(caseStudies)) {
	plotData[1, i] = getProductTime(i)
	plotData[2, i] = getFamilyTime(i)
}
color=c(ex_color,bdd_color)
pdf(file=paste("plot_ext.pdf",sep=""), width=8,height=5,onefile=TRUE,paper="special")
barplot(plotData, beside=TRUE, space=c(0.1,0.5), col=color)

legend("top",c('product','family'), inset = .01,fill=color)
axis(1, pos=-0.1, at=((1:length(caseStudies)) *2), labels=caseStudies, cex.axis=2, tick=FALSE)

warnings()
dev.off()
