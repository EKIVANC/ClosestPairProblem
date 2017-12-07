package assignment;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import assignment.KdTree.DPoint;

import java.io.BufferedWriter;
import java.io.FileWriter;


public class Runner {

	static double closestDistance = Double.MAX_VALUE;
	static DPoint closestA = null;
	static DPoint closestB = null;

	public static void main(String[] args) throws IOException, URISyntaxException {

		// create a scanner so we can read the command-line input
	    Scanner scanner = new Scanner(System.in);

	    //  prompt for the user's name
	    System.out.println("********************************************************************************");
	    System.out.println("Welcome to Closest Distance Exercise!");
	    System.out.println("********************************************************************************");
	    System.out.println("Please Enter The input file name:");
	    // get their input as a String
	    String fileName = scanner.next();
	    
	    // basic file name validation
	    while(fileName == null || fileName.trim().length() ==0 ) {
	    		System.out.println("Please enter a file name contaning exact directory path:");
	    		System.out.println("like /Users/test/workspace/sample_input_44_55.tsv");
	    		fileName = scanner.next();
	    }
	    
	    // read points from file
	    // ClassLoader.getSystemResource(fileName).getPath()
		List<DPoint> initialPointList = readLines(fileName);
		
		// create a copy of existing list to change the order and remove calculated points 
		List<DPoint> points2Sort = new ArrayList<DPoint>(initialPointList);

		// First Create KD-Tree
		KdTree<DPoint> kdTree = new KdTree<DPoint>(points2Sort);
		
		// Print Tree, the toString method has been overridden
		System.out.println("Here is KD Tree constructed to solve the problem:");
		System.out.println(kdTree.toString());

		// Find the closest two points until all points calculated
		while (points2Sort.size() > 0) {
			DPoint[] pair = new DPoint[2];
			Collection<DPoint> result = kdTree.nearestNeighbourSearch(2, points2Sort.get(0));
			result.toArray(pair);

			if (pair[1].getNearestPointEclDistance() < closestDistance) {
				// A closer pair found!
				closestDistance = pair[1].getNearestPointEclDistance();
				closestA = pair[0];
				closestB = pair[1];
			}
			// remove calculated point and go on
			points2Sort.remove(points2Sort.get(0));
			// points2Sort.removeAll(result);
		}

		saveFile(fileName.substring(0, fileName.length() - 4), initialPointList.indexOf(closestA) + 1, closestA,
				initialPointList.indexOf(closestB) + 1, closestB);
	}

	private static void saveFile(String fileName, int lineFirst, DPoint firstCloserPoint, int lineSecond,
			DPoint secondCloserPoint) {

		BufferedWriter bw = null;
		FileWriter fw = null;
		String outputFileName = fileName.replace("input", "output") + ".txt";
		try {

			String content = lineSecond + ":" +secondCloserPoint + System.getProperty("line.separator") + lineFirst
					+ ":" + firstCloserPoint;
			fw = new FileWriter(outputFileName);
			bw = new BufferedWriter(fw);
			bw.write(content);

			System.out.println("Done!");
			System.out.println("The closest points line numbers are: "+lineFirst+" and "+ lineSecond+"");
			System.out.println("You can find the result in the followoing file:");
    			System.out.println(outputFileName);
    		
			

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

	private static List<DPoint> readLines(String filename) throws IOException {
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		List<DPoint> points = new ArrayList<DPoint>();

		String line = null;
		int lineNumber = 1;
		while ((line = bufferedReader.readLine()) != null) {
			String[] numbersInString = line.split("\t");
			double[] coordinatesPerPoint = new double[numbersInString.length];

			for (int i = 0; i < numbersInString.length; i++) {
				coordinatesPerPoint[i] = Double.parseDouble(numbersInString[i]);
			}

			DPoint dpoint = new DPoint(coordinatesPerPoint);
			points.add(dpoint);
			lineNumber++;
		}
		bufferedReader.close();
		return points;
	}

}
