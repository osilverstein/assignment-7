# image-processor-7

Image processor provided by classmates that we added a mosaic filter to. The user specifies the number of tiles and the effect tiles the image geometrically. 

The implementation follows k-means clustering. Pixels are assigned as nodes randomly within a range of how much space an expected node would have they have to the right. 

Then all pixels closest to each node are set to the average color of that given cluster. 

Mosaic filter implements the existing Filter class which provides the adequate methods to integrate our implementation cleanly. We opted not to try to use the
additionally provided AbstractFilter because it used data structures not relevant to the problem at hand.
