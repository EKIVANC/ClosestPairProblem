# Closest Pair Problem - Solution with KD Tree:
The task is to identify the closest pair of points in a given N dimensional space.
The input is a text file which each line specifies a point with tab separated double numbers as N dimension of the point.

# Solution Approach
There are two common approaches of this problem, one of them is the Brute Force method means fetching all points by one by while calculating the distance of each point from one to another.
The brute force solution is costly but it has some advantages like being simple and easy to implement.

Another solution of the problem, which also implemented in this GitHub project, is creating a KD Tree with given N dimensional points, then using the constructed tree in calculating the distance between points. Thanks to this approach, we don't need to fetch all points in the space, we simply split the plane according to median values of the Nth axis of the points. Of course there could be closer points in splitted plane, and we need to care the splitted neighbour planes as well.

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

<b>Note: The source code was developed by using Eclipse ide but the instruction was given for a common test environment. If you are using Eclipse or similar IDE you can download the source file and open the existing java project with your IDE.</b>

1-  <b>Checking JDK: </b> The provided solution is developed with Java version 8, hence JDK should be installed to the test machine. To check if you have proper JDK, type the following command on terminal.

 <b>javac -version</b>

 You should see your installed version of Java on the terminal.
2-  <b>Compiling the Java files: </b> Download the source code, open terminal and navigate to "ClosestPairProblem/src" in the project directory, Type the following command to compile Java

Command:  <b>javac assignment/Runner.java </b>

3-  <b>Runing the application: </b> To run this application, open terminal and navigate to "ClosestPairProblem/src" in the project directory, then type the following command:
Command: <b> java assignment.Runner </b> 
you should see the following message:

	********************************************************************************
	Welcome to Closest Distance Exercise!
	********************************************************************************

4-  <b>Enter Input File with absolute path:</b>The Program will ask an input file to operate like the following image:
![SampleInputfile](https://user-images.githubusercontent.com/976950/33721951-931c0102-db79-11e7-9c74-204c3377507f.png "Enter File Name with exact path")


5- <b> Get Result:</b> After entering the file name with its absolute path, the program draw the calculated KDTree on the console, then writes the line numbers of closest points as below:
The final output file directory will be written on the console as well.
![SampleInputfile](https://user-images.githubusercontent.com/976950/33721953-934b6f50-db79-11e7-8ff4-dd6c23e25c08.png "Sample Output")

# Unit Tests
The application has a unit test file called Tester.java. Unit tests are developed with JUnit library. You can download the JUnit library on the project on github (it is in the same directory as /bin directory )

Before running the Tester, we need to compile it with the following command on the terminal:

Command: <b> javac -cp somePathToJarFile/junit-4.10.jar assignment/KdTree.java assignment/Runner.java assignment/Tester.java</b>

To Run the Tester, we need to specify the JUnit.jar file as classPath parameter:

Command: <b> java -cp .:/somePathToJarFile/junit-4.10.jar org.junit.runner.JUnitCore assignment.Tester</b> 

Sample output of the Tester File:
![SampleInputfile](https://user-images.githubusercontent.com/976950/33738320-f9d5d0c8-dba9-11e7-89c3-5c0c5ce4ff7e.png "Enter File Name with exact path")

There are two unitTest, one of them tests the developed nearestNeighbourSearch method and the other just tests the KDTree creation process.

# Sample Input and Output Files
Sample input and output files are in the "/sample_files" directory of the project.

