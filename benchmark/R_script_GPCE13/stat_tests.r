dataset <- read.csv(file="data.csv",head=TRUE,sep=",")

print(dataset)

shapiro.test(dataset$PDBTotal-dataset$FMBTotal)	
shapiro.test(dataset$FTBTotal-dataset$FMBTotal)	
shapiro.test(dataset$PDBTotal-dataset$FTBTotal)

wilcox.test(dataset$PDBTotal, dataset$FMBTotal, alternative="two.sided", paired=TRUE)
wilcox.test(dataset$PDBTotal, dataset$FTBTotal, alternative="two.sided", paired=TRUE)
wilcox.test(dataset$FTBTotal, dataset$FMBTotal, alternative="two.sided", paired=TRUE)

cat("product-based (time): ", "mean: ",  mean(dataset$PDBTotal), "median: ",  median(dataset$PDBTotal), "sd: ",  sd(dataset$PDBTotal), "min: ",  min(dataset$PDBTotal), "max: ",  max(dataset$PDBTotal))
cat("\n")
cat("feature-based (time): ", "mean: ",  mean(dataset$FTBTotal), "median: ",  median(dataset$FTBTotal), "sd: ",  sd(dataset$FTBTotal), "min: ",  min(dataset$FTBTotal), "max: ",  max(dataset$FTBTotal))
cat("\n")
cat("family-based (time):  ", "mean: ",  mean(dataset$FMBTotal), "median: ",  median(dataset$FMBTotal), "sd: ",  sd(dataset$FMBTotal), "min: ",  min(dataset$FMBTotal), "max: ",  max(dataset$FMBTotal))
cat("\n")
