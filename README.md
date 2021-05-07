# Distinct-Islands
Challenge at binarysearch.com. Tags: Graphs.

Here the presented solutions to the challenge at binarysearch.com are two.

1. The first one is in file "Solution_differentShapes_whereRotationAndFlippingMatter.java". As the name suggest, here as unique are regarded any figures that have different shapes, or have the same shape but differ in their rotation and/or are flipped on opposite side. And figres with exactly the same shapes and flipping are also regarded as different, if they differ in size.

Examples:

different rotation:                figure one               figure two

                                 1 1 1                      0 0 1
                                 
                                 1 0 0                      1 1 1

flipped on opposite sides:         figure one               figure two

                                  1 1                       1 1
                                  
                                  1 1                       1 1
                                  
                                  0 1                       1 0

different size:                    figure one               figure two

                                   1 1                    1 1 1 1
                                   
                                   1 1                    1 1 1 1
                                   
                                                          1 1 1 1
                                                          
                                                          1 1 1 1

As per the posted challenge on binarysearch.com, all of the above cases are regarded as
different figures, even though they are exactly the same and differ only in their position
on the input matrix or in size. This, however, is not explicitly described in the problem statement, which mentions only " different shapes" and has to be inferred from the test cases, which regard figures with the same shapes but different rotation/flipping/size as different shapes!

In the first solution, for each figure is made a hash calculation. If two figures have the same shape, rotation, flipping and size, their hash calculation will be the same! In this way, we extract the different figures, as per the problem statement (explicit and implicit).

2. The second solution is in file
"Solution_differentShapes_whereRotationAndFlipping_doNotMatter.java". As the name suggest, this is an expanded version of the problem, where the shapes are almost truly unique, and their rotation and flipping do not matter!! 

The word "almost" is not accidental: this expanded version of the problem regards two figures with the same shape but different size as different. Although this also can be coded, by first bringing the two figures to the same scale and then performing 
the comparison (or by some other method, which I am currently unaware of), this goes way beyond the original problem, and is not considered in the second solution.

Here, first through breadth first search, we exctract the outermost points of the shapes
i.e. leftmost, rightmost, top, bottom. On the bases of these extracted points, we recreate the shapes in their own matrices, so that we can perform rotation/flipping while trying to find a match. 

The matrices that contain the shapes, are the smallest possible rectangular matrices that  can contain the shape. Thus, they may consist both of '1's and '0's, and in such cases, we have to find a match for all '1's and '0's in order to declare the shapes as the same - if the shapes are the same, as per the definition for the second version of the solution, the amount/position of '0's, contained in the smallest possible rectangular matrix, will be exactly the same. This does not burden much the algorithm in terms of space and time, but makes a lot more easy to handle the rotations and flippings. And it makes the code easier to read.

Examples:           shape         extracted matrix

                     1 1 1            0 1 1 1
                     
                   1 1 1              1 1 1 0
 
					 shape         extracted matrix
           
                     1 1 1             1 1 1
                     
                     1 1 1             1 1 1
