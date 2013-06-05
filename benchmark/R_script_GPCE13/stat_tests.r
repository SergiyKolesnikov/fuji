dataset <- read.csv(file="data.csv",head=TRUE,sep=",")

print(dataset)

shapiro.test(dataset$PDBSum-dataset$FMBSum)	
shapiro.test(dataset$FTBSum-dataset$FMBSum)	
shapiro.test(dataset$PDBSum-dataset$FTBSum)

wilcox.test(dataset$PDBSum, dataset$FMBSum, alternative="two.sided", paired=TRUE)
wilcox.test(dataset$PDBSum, dataset$FTBSum, alternative="two.sided", paired=TRUE)
wilcox.test(dataset$FTBSum, dataset$FMBSum, alternative="two.sided", paired=TRUE)

cat("product-based (time): ", "mean: ",  mean(dataset$PDBSum), "median: ",  median(dataset$PDBSum), "sd: ",  sd(dataset$PDBSum), "min: ",  min(dataset$PDBSum), "max: ",  max(dataset$PDBSum))
cat("\n")
cat("feature-based (time): ", "mean: ",  mean(dataset$FTBSum), "median: ",  median(dataset$FTBSum), "sd: ",  sd(dataset$FTBSum), "min: ",  min(dataset$FTBSum), "max: ",  max(dataset$FTBSum))
cat("\n")
cat("family-based (time):  ", "mean: ",  mean(dataset$FMBSum), "median: ",  median(dataset$FMBSum), "sd: ",  sd(dataset$FMBSum), "min: ",  min(dataset$FMBSum), "max: ",  max(dataset$FMBSum))
cat("\n")
