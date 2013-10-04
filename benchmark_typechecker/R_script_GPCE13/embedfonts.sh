# I got the command from a comment on this site: http://lemire.me/blog/archives/2005/08/29/getting-pdflatex-to-embed-all-fonts/
# He says it is from a document for camera-ready preparation from conference CPVR (did not find the document)

gs -sDEVICE=pdfwrite -q -dBATCH -dNOPAUSE -dSAFER -dPDFX -dPDFSETTINGS=/prepress -dAutoFilterColorImages=false -dColorImageFilter=/FlateEncode -dAutoFilterGrayImages=false -dGrayImageFilter=/FlateEncode -sOutputFile=plot_int_emb.pdf -f plot_int.pdf

# show the result
pdffonts plot_int_emb.pdf
