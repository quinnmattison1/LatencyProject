/**
 * 
 */
package edu.uiowa.cs.warp;
import java.util.Scanner;
import java.nio.file.Files;

/**
 * Reads the input file, whose name is passed as input parameter to the constructor, and builds a
 * Description object based on the contents. Each line of the file is an entry (string) in the
 * Description object.
 * 
 * Modified by srgoddard
 * @author sgoddard
 * @version 1.4 Fall 2022
 */
public class WorkLoadDescription extends VisualizationObject {

  private static final String EMPTY = "";
  private static final String INPUT_FILE_SUFFIX = ".wld";

  private Description description;
  private String inputGraphString;
  private FileManager fm;
  private String inputFileName;

  WorkLoadDescription(String inputFileName) {
    super(new FileManager(), EMPTY, INPUT_FILE_SUFFIX); // VisualizationObject constructor
    this.fm = this.getFileManager();
    initialize(inputFileName);
  }

  @Override
  public Description visualization() {
    return description;
  }

  @Override
  public Description fileVisualization() {
    return description;
  }

  // @Override
  // public Description displayVisualization() {
  // return description;
  // }

  @Override
  public String toString() {
    return inputGraphString;
  }

  public String getInputFileName() {
    return inputFileName;
  }

  private void initialize(String inputFile) {
    // Get the input graph file name and read its contents
    InputGraphFile gf = new InputGraphFile(fm);
    inputGraphString = gf.readGraphFile(inputFile);
    this.inputFileName = gf.getGraphFileName();
    description = new Description(inputGraphString);
  }
  
  public static void main(String[] args) {
	WorkLoadDescription test = new WorkLoadDescription("stresstest.txt");
	String [] arr = new String [20];//Primary storage of information
	String temp = new String(); //A temporary string to use for information transfer
	int trackArraySize = 0; //Used to track the positions in an Array that has information stored.
	//This is where the information is put into an array.
	Scanner input = new Scanner(test.toString());//Used to process information from test to arr
	while (input.hasNextLine()) {
		temp = input.nextLine();
		temp = temp.replace("}", " ");
		temp = temp.replace("{", " ");
		arr[trackArraySize]= temp;
		trackArraySize++;
	}
	input.close();
	//The following is where the information is sorted reverse alphabetically.
	for(int i=1; i<trackArraySize;i++) {
		for(int j=i+1; j<trackArraySize; j++) {
			if(arr[j].compareTo(arr[i]) > 0) {
				temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}
	}
	//The following is to print out the sorted array
	System.out.println(arr[0]);
	for(int i=1; i<trackArraySize-1; i++) {
		System.out.println("Flow " + i + ": " + arr[i]);
	}
  }  
}

  



