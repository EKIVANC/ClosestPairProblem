# Closest Pair Problem - Solution with KD Tree:
The task is to identify the closest pair of points in a given N dimensional space.
The input is a text file which each line specifies a point with tab separated double type numbers as N dimension of the point.

# Solution Approach
There are some common approaches of this problem, one of them is the Brute Force method means fetching all points by one by while calculating the distance of each point from one to another.
The brute force solution is costly but it has some advantages like being simple and easy to implement.

Another solution of the problem, which also implemented in this GitHub project, is creating a KD Tree with given N dimensional points, then using the constructed tree in calculating the distance between points. Thanks to this approach, we mostly don't need to fetch all points in the space, we simply split the plane according to median values of the Nth axis of the points. Of course there could be closer points in splitted plane, and we need to care the splitted neighbour planes as well. We manage to do this by checking the distance of other splitted panes <b>possible closer points</b> recursively.

# The asymptotical running time of the algorithm
if brute force method were used, it took n<sup>2</sup>-n times calculation to complete, and for D dimension, it will be d*(n<sup>2</sup>-n) which becomes O(n<sup>2</sup>).

For our case with a nearly balanced KD-Tree,

For Construction;
* Size: There will be 2N-1 nodes if 1 data point at each leaf.
* Depth: O ( Log N )
* Median + send point left, right : O(N) at every level of the tree

Total construction time of the Tree is:<b> O( N Log N ) </b> 

For 1-NN Query:
* Traverse down tree to starting point: O ( Log N )
* Maximum backtrack and traverse like worst case:  O ( N )
Complexity Range is between: O ( Log N ) -> O ( N ) according to pruning the nodes, but this values are exponential with d, so in KD-Tree we might face with performance problems in high numbers of Dimensions.

O(N) is also the same complexity as the brute force search so for a single Nearest Search Query if we are unlucky about the structure of the data, we might actually take a penalty over a brute force search. But in some cases we can have significant gains in efficiency. 	  


# Limitations
* Input text file should contain one point per line.
* The dimension separator is "\t".
* For each point in input file should contain exactly same number of coordinates.
* The input file must have at least two point.
* No restriction for dimension.
* No maximum limit for points.

# Usage instructions

<b>Note: The source code was developed by using Eclipse IDE but the instruction was given for a common test environment. If you are using Eclipse or similar IDE you can download the source file and open the existing java project with your IDE.</b>

1-  <b>Checking JDK: </b> The provided solution is developed with Java version 8, hence JDK should be installed to the test machine. To check if you have proper JDK, type the following command on terminal.

 <b>javac -version</b>

 You should see your installed version of Java on the terminal.

2-  <b>Compiling the Java files: </b> Download the source code, open terminal and navigate to "ClosestPairProblem/src" in the project directory, Type the following command to compile Java class.

Command:  

<b>javac assignment/KdTree.java </b>
<b>javac assignment/Runner.java </b>

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
![SampleInputfile](https://user-images.githubusercontent.com/976950/33797948-c0692410-dd21-11e7-95a8-61d69c1f81f3.png "Sample Output")

# Unit Tests
The application has a unit test file called Tester.java. Unit tests are developed with JUnit library. You can download the JUnit library on the project on github (it is in the same directory as /bin directory )

Before running the Tester, we need to compile it with the following command on the terminal:

Command: <b> javac -cp somePathToJarFile/junit-4.10.jar assignment/KdTree.java assignment/Runner.java assignment/Tester.java</b>

To Run the Tester, we need to specify the JUnit.jar file as classPath parameter:

Command: <b> java -cp .:/somePathToJarFile/junit-4.10.jar org.junit.runner.JUnitCore assignment.Tester</b> 

Sample output of the Tester File:
![SampleInputfile](https://user-images.githubusercontent.com/976950/33738320-f9d5d0c8-dba9-11e7-89c3-5c0c5ce4ff7e.png "Enter File Name with exact path")

There are two unitTest, one of them tests the developed nearestNeighbourSearch method and the other just tests the KDTree creation process.


| Test Nearest Point According to a given Array  | Construct KDTree and check items |
| ------------- | ------------- |
| ![UnitTest1](https://user-images.githubusercontent.com/976950/33799735-a3106b8c-dd42-11e7-9573-c0a76a9be133.png "Unit Test 1")   | ![UnitTest2](https://user-images.githubusercontent.com/976950/33799736-a5d8cc56-dd42-11e7-9a40-d8b4280eac13.png   "Unit Test 2") |



# Sample Input and Output Files
Sample input and output files are in the "/sample_files" directory of the project.

# Sample Input File Provided by me
The file with name "sample_input_2_5" was provided by me which has more than <b>two Closest points</b>. Actually it has 3 equal distance closest points. 

<b>***The program can identify if more than two points are equal closest distance.</b>

# Sample Output of three closest points:
As explained, there can be more then two closer point ( imagine N point those have equal distance ). The following images has a snapthot of file have equal distance  between 1.1.1 and 2.2.2 and 2 and 3.3.3

![SampleInputfilewithMoreThan2ClosestPoint](https://user-images.githubusercontent.com/976950/33796098-1e07de4a-dcff-11e7-8390-14b0aeef1e11.png "Sample Inputfile with More Than 2 ClosestPoints")

The output of the console containing tree is:
![SampleOutputfileMoreThan2ClosestPoints](https://user-images.githubusercontent.com/976950/33797950-c61dd504-dd21-11e7-8bbb-81856d6ec898.png "Sample Console Output File with More Than 2 ClosestPoints")

The output file is as below:


![SampleThreeOutputfile](https://user-images.githubusercontent.com/976950/33797951-c9bb9656-dd21-11e7-8c4d-b0b403736e19.png "Sample Output File with More Than 2 ClosestPoints")