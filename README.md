# ClosestPairProblem - Solution with KD Tree
The task is to identify the closest pair of points in a given N dimensional space.
The input is a text file where each line specifies a point with tab separated double numbers as N dimension of the point.

# Solution Approach
There are two common approach of this solution, one of them is the Brute Force method means fetching all points by one by while calculating the distance of each point from one to another.
The brute force solution is costy but it has some advantages like being simple and easy to implement.

Another solution of this problem is, creating a K D Tree with given N dimensional points, then using the constructed tree in calculating the distance between points. Thanks to this approach, we don't need to fetch all points in the space, we simply split the plane according to median values of the Nth axis of the points. Of course there could be closer points in splitted plane, and we need to calculate the neighboured planes as well.

# The asymptotical running time of the algorithm
If we were solve the problem in brute force method it took O(n2) calculations but thanks to KD Tree approach, it takes O(log(n)) calculations to success.

# Limitations
* Input text file should contain one point per line.
* The dimension separator is "\t".
* For each point in input file should contain exactly same number of coordinates.
* The input file must have at least two point.
* No restriction for dimension.
* No maximum limit for points.


# Usage instructions
* Check JDK: The provided solution is developed with Java version 8, hence JDK should be installed to the test machine. To check if you have proper JDK, type javac -version.
* Compile the Java file: Download the source code, open terminal and navigate to "ClosestPairProblem/src" in the project directory, Type the following command to compile Java
Command: javac assignment/Runner.java
* Runing the application: To run this application, open terminal and navigate to "ClosestPairProblem/src" in the project directory, then type the following command:
java assignment.Runner you should see the following message:

	********************************************************************************
	Welcome to Closest Distance Exercise!
	********************************************************************************

* The Program will ask an input file to operate like the following image:
![SampleInputfile](https://user-images.githubusercontent.com/976950/33721951-931c0102-db79-11e7-9c74-204c3377507f.png "Enter File Name with exact path")


* After entering the file name with its absolute path, the program draw the calculated KDTree on the console, then writes the line numbers of closest points as below:

* The final output file directory will be written on the console as well.
![SampleInputfile](https://user-images.githubusercontent.com/976950/33721953-934b6f50-db79-11e7-8ff4-dd6c23e25c08.png "Sample Output")
