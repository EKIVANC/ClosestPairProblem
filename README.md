# ClosestPairProblem - Solution with KD Tree
The task is to identify the closest pair of points in a given N dimensional space.
The input is a text file where each line specifies a point with tab separated double numbers, as N dimension of the point.

# Solution Approach
There are two common approach of this solution, one of them is the brute force, means fetching all points by one by and calculating the distance of each point from one to another.
The brute force solution is costly but it has some advantages like being simple and easy to implement.

Another solution of this problem is, creating a K D Tree with given N dimensional points, then using the constructed tree in calculating	the distance between points. Thanks to this approach, we don't need to fetch all points in the space, we simply split the plane according to median values of the Nth axis of the points.

# The asymptotical running time of the algorithm
If we were solve the problem in brute force method it took O(n2) calculations but thanks to KD Tree approach, it takes O(log(n)) to success.

# Limitations
* Input text file should contain one point per line.
* The dimension separator is "\t".
* For each point in input file should contain exactly same number of coordinates.
* The input file must have at least two point.
* No restriction for dimension.
* No maximum limit for points.


# Usage instructions
* Check JDK: The provided solution is developed with Java version 8, hence JDK should be installed to the test machine. To check if you have proper JDK, type javac -version.
* Compile the Java file: Open terminal and navigate to "ClosestPairProblem/src/assignment" directory, Type the following command to compile Java
Command: javac *.java
* Run the program: To run this application, type the following command:
java Runner

