package assignment;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import assignment.KdTree.DPoint;

import java.io.BufferedWriter;
import java.io.FileWriter;


public class Runner {

	private static final String MSG_INPUT_IS_EMPTY = "Input file is empty!, please give a proper input file.";
	private static final String MSG_INPUT_LESS_THAN_TWO = "Number of inputs should me more than two to operate!";
	private static final String MSG_INPUT_DIMENSIONS_NOT_SAME = "Dimesions of input has to be same!";
	
	static double closestDistance = Double.MAX_VALUE;
	
	static List<DPoint> closestPoints = new ArrayList<>();

	public static void main(String[] args) throws IOException, URISyntaxException, InputValidationException {

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
			
			Collection<DPoint> result = kdTree.nearestNeighbourSearch(2, points2Sort.get(0));
			DPoint[] pair = new DPoint[result.size()];
			result.toArray(pair);

			if (pair[result.size()-1].getNearestPointEclDistance() < closestDistance) {
				// A closer pair found!
				closestDistance = pair[result.size()-1].getNearestPointEclDistance();
				closestPoints.clear();
				closestPoints.addAll(result);
			}
			
			if (pair[1].getNearestPointEclDistance() == closestDistance) {
				// An Equal Point has been found
				for(DPoint tmpPoint: result) {
					if( !closestPoints.contains(tmpPoint) ) {
						closestPoints.add(tmpPoint);
					}
				}
				
			}
			
			// remove calculated point and go on
			points2Sort.remove(points2Sort.get(0));
			// points2Sort.removeAll(result);
		}

		saveFile(fileName.substring(0, fileName.length() - 4), initialPointList, closestPoints);
	}

	private static void saveFile(String fileName, List<DPoint> initialList, List<DPoint> closestPoints) {

		BufferedWriter bw = null;
		FileWriter fw = null;
		String outputFileName ="";
		if(fileName.indexOf("input")>0) {
			outputFileName = fileName.replace("input", "output") + ".txt";	
		}
		else {
			outputFileName = fileName+"_Output"+".txt";
		}
		
		List<Integer> lineNumbers = new ArrayList<>();
		try {
			
			StringBuilder content = new StringBuilder();
			for ( DPoint dpoint : closestPoints ) {
				int lineNumber = initialList.indexOf(dpoint)+1;
				lineNumbers.add(lineNumber);
				content.append( lineNumber  + ":" + dpoint + System.getProperty("line.separator"));
			}
			
			
			fw = new FileWriter(outputFileName);
			bw = new BufferedWriter(fw);
			bw.write(content.toString());
			System.out.println("Done!");
			Collections.sort(lineNumbers);
			System.out.print("The closest points line numbers are: ");
			
			StringBuilder sblineNum = new StringBuilder(); 
			for( Integer lineNum: lineNumbers ) {
				sblineNum.append(lineNum+ " , ");
			}
			System.out.println(sblineNum.substring(0,sblineNum.length()-2));
			System.out.println("*****************");
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

	
	private static List<DPoint> readLines(String filename) throws IOException, InputValidationException {
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		List<DPoint> points = new ArrayList<DPoint>();

		int tmpDimension = 0;
		String line = null;
		int lineNumber = 1;
		while ((line = bufferedReader.readLine()) != null) {
			
			if(line == null || line.length() == 0) {
				throw new InputValidationException(MSG_INPUT_IS_EMPTY);
			}
			
			String[] numbersInString = line.split("\t");
			double[] coordinatesPerPoint = new double[numbersInString.length];

			if(tmpDimension == 0 ) {
				tmpDimension = numbersInString.length;
			}
			else {
				if(tmpDimension != numbersInString.length)
				{
					throw new InputValidationException(MSG_INPUT_DIMENSIONS_NOT_SAME);
				}
			}
			
			for (int i = 0; i < numbersInString.length; i++) {
				coordinatesPerPoint[i] = Double.parseDouble(numbersInString[i]);
			}

			DPoint dpoint = new DPoint(coordinatesPerPoint);
			points.add(dpoint);
			lineNumber++;
		}
		
		
		if(points == null || points.size() ==0 ) {
			throw new InputValidationException(MSG_INPUT_IS_EMPTY);
		}
		
		else if(points.size() < 3  ) {
			throw new InputValidationException(MSG_INPUT_LESS_THAN_TWO);
		}
		

		bufferedReader.close();
		return points;
	}

	

}
