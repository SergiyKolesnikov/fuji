######################
## load needed packages and libraries
#install.packages("stringr")
#install.packages("plotrix")
library("stringr")
library("plotrix")

######################
## configure this script
## TODO command line parameter!
# spl <- "GPL"
# run <- "run11"
# path <- "~/Documents/Projekte/feat-fam-p/runs"
# setwd("~/Documents/Projekte/feat-fam-p/runs/run1/EPL")
#currentPath <- str_c(path, "/", run, "/", spl)
spl <- basename(getwd())
currentPath <- "."
exportFilePB <- str_c(currentPath, "/", spl, "_inttimetypechecker.pdf")
exportFileCum <- str_c(currentPath, "/", spl, "_inttimetypechecker_cum.pdf")

######################
## configure this script
## TODO command line parameter!

filePB <- str_c(currentPath, "/inttimetypechecker.csv")
fileFB <- str_c(currentPath, "/inttimetypechecker_featurebased.csv")

smoothMax <- function(x) {
  digits <- floor(log10(x)+1)
  firstX <- floor( x / (10^(digits-2)) ) 
  nextth <- (firstX+1)*10^(digits-2)
  return(nextth)
}

######################
## prepare empty tables

emptyPB <- data.frame("ASTcomp" = 0, "typecheck" = 0)
names(emptyPB) <- str_c("PB: ", names(emptyPB))

emptyFB <- data.frame("ASTComp" = 0, "typecheck" = 0)
names(emptyFB) <- str_c("FB: ", names(emptyFB))

######################
## prepare and read BCC files

#read outStubGen file
pb <- read.table(filePB, sep="\t", header=TRUE)
#delete products' names from frame
pb <- subset(pb, select = -c(variant,errors) )

#read outStubApp file
fb <- read.table(fileFB, sep="\t", header=TRUE)
#delete feature names from frame
fb <- subset(fb, select = -c(variant,errors) )

######################
## sums
sumsPB <- colSums(pb)
names(sumsPB) <- str_c("PB: ", names(sumsPB))
sumsFB <- colSums(fb)
names(sumsFB) <- str_c("FB: ", names(sumsFB))

######################
## generate result tables

sumsPB <- cbind(t(sumsPB), emptyFB)
sumsFB <- cbind(emptyPB, t(sumsFB))

######################
## configure plots

colors <- c("lightblue", "lightskyblue", # pb
            "tomato2", "red2" # fb
)

######################
## plot SOP

pdf(file=exportFilePB,paper="A4r")  

maximumY <- max(sum(sumsPB), sum(sumsFB))
barplot(cbind(as.matrix(t(sumsPB)), as.matrix(t(sumsFB))), 
        names.arg = c("product-\nbased", "feature-\nbased"), 
        col= colors,
        main = str_c(spl, ": ", "all products"),
        ylab = "seconds",
        xlim = c(0, 6),
        ylim = c(0, smoothMax(maximumY))
)
legend("topright",
       legend = names(sumsPB),
       col = colors,
       pch = 15,
       y.intersp = 1
)

# Write the file  
dev.off()  


######################
## plot cum

pdf(file=exportFileCum, paper="A4r")  

cumPB <- rowSums(cumsum(pb))
cumFB <- max(rowSums(cumsum(fb)))

xrange <- c(0,NROW(cumPB)-1)
yrange <- c(0,smoothMax(max(max(cumPB),cumFB)))

par(xaxs="i", yaxs="i")
colorsCum <- c("skyblue3", "tomato2") #

plot(xrange, yrange,
     type = "n",
     xlab="# products",
     ylab="seconds",
     main=str_c(spl, ": ", "run-time by # of products")
)

par(lwd=2.5, lty=1, col="black")

lines(xrange[1]:xrange[2], cumPB, type="l", col=colorsCum[1]) 
abline(h=cumFB, col=colorsCum[2], lty=2)
points(xrange[2],cumFB, type = "b", col = colorsCum[2], lwd=7, pch = 7, xpd = TRUE)

legend("topleft", 
       legend = c("product-based", "feature-based"),
       col = colorsCum,
       pch = 15,
       y.intersp = 1
)

# Write the file  
dev.off()  
