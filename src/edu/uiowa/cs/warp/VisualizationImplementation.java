/**
 * 
 */
package edu.uiowa.cs.warp;

import java.io.File;

/**
 * @author sgoddard
 * @author Frank Forliano (for java doc comments)
 * @version 1.5
 */
public class VisualizationImplementation implements Visualization {

  private Description visualization;
  private Description fileContent;
  private GuiVisualization window;
  private String fileName;
  private String inputFileName;
  private String fileNameTemplate;
  private FileManager fm = null;
  private WarpInterface warp = null;
  private WorkLoad workLoad = null;
  private VisualizationObject visualizationObject;

/**
 * @param warp
 * @param outputDirectory
 * @param choice
 */
  public VisualizationImplementation(WarpInterface warp, String outputDirectory,
      SystemChoices choice) {
    this.fm = new FileManager();
    this.warp = warp;
    inputFileName = warp.toWorkload().getInputFileName();
    this.fileNameTemplate = createFileNameTemplate(outputDirectory);
    visualizationObject = null;
    createVisualization(choice);
  }
/*
 * @param WorkLoad
 * @param outputDirectory
 * @param choice
 */
  public VisualizationImplementation(WorkLoad workLoad, String outputDirectory,
      WorkLoadChoices choice) {
    this.fm = new FileManager();
    this.workLoad = workLoad;
    inputFileName = workLoad.getInputFileName();
    this.fileNameTemplate = createFileNameTemplate(outputDirectory);
    visualizationObject = null;
    createVisualization(choice);
  }

  /**
   * Display the visualization in a GUI window.
   */
  @Override
  public void toDisplay() {
    // System.out.println(displayContent.toString());
    window = visualizationObject.displayVisualization();
    if (window != null) {
      window.setVisible();
    }
  }
/**
 * Write the data from the Flow visualization to a file.
 */
  @Override
  public void toFile() {
    fm.writeFile(fileName, fileContent.toString());
  }

  @Override
  public String toString() {
    return visualization.toString();
  }

  /**
   * Create visualizations based on SystemChoices.
   * The visualization is created by enumerating the SystemChoices.
   * @param choice
   */
  private void createVisualization(SystemChoices choice) {
    switch (choice) { // select the requested visualization
      case SOURCE:
        createVisualization(new ProgramVisualization(warp));
        break;

      case RELIABILITIES:
        // TODO Implement Reliability Analysis Visualization
        createVisualization(new ReliabilityVisualization(warp));
        break;

      case SIMULATOR_INPUT:
        // TODO Implement Simulator Input Visualization
        createVisualization(new NotImplentedVisualization("SimInputNotImplemented"));
        break;

      case LATENCY:
        // TODO Implement Latency Analysis Visualization
        createVisualization(new LatencyVisualization(warp));
        break;

      case CHANNEL:
        // TODO Implement Channel Analysis Visualization
        createVisualization(new ChannelVisualization(warp));
        break;

      case LATENCY_REPORT:
        createVisualization(new ReportVisualization(fm, warp,
            new LatencyAnalysis(warp).latencyReport(), "Latency"));
        break;

      case DEADLINE_REPORT:
        createVisualization(
            new ReportVisualization(fm, warp, warp.toProgram().deadlineMisses(), "DeadlineMisses"));
        break;

      default:
        createVisualization(new NotImplentedVisualization("UnexpectedChoice"));
        break;
    }
  }
  
 /**
  * Create visualization based off WorkLoadChoices.
  * 
  * @param The choice of the visualization for the workload.
  */
  private void createVisualization(WorkLoadChoices choice) {
    switch (choice) { // select the requested visualization
      case COMUNICATION_GRAPH:
        // createWarpVisualization();
        createVisualization(new CommunicationGraph(fm, workLoad));
        break;

      case GRAPHVIZ:
        createVisualization(new GraphViz(fm, workLoad.toString()));
        break;

      case INPUT_GRAPH:
        createVisualization(workLoad);
        break;

      default:
        createVisualization(new NotImplentedVisualization("UnexpectedChoice"));
        break;
    }
  }
  
  /**
   * Extends the VsualizationObject class to create a visualization of the data.
   * 
   * @param visualization The visualization details.
   * @param fileContent The data from the flows.
   * @param fileName The file name of where the visualization is wrote to.
   * @param <T>
   * @param obj VisualizationObject that creates the Visualization
   */
  private <T extends VisualizationObject> void createVisualization(T obj) {
    visualization = obj.visualization();
    fileContent = obj.fileVisualization();
    /* display is file content printed to console */
    fileName = obj.createFile(fileNameTemplate); // in output directory
    visualizationObject = obj;
  }

  /**
   * 
   * Creates a file name template based on the outputDirectory and FileName. 
   * @param outputDirectory
   * @return fileNameTemplate
   */
  private String createFileNameTemplate(String outputDirectory) {
    String fileNameTemplate;
    var workingDirectory = fm.getBaseDirectory();
    var newDirectory = fm.createDirectory(workingDirectory, outputDirectory);
    // Now create the fileNameTemplate using full output path and input filename
    if (inputFileName.contains("/")) {
      var index = inputFileName.lastIndexOf("/") + 1;
      fileNameTemplate = newDirectory + File.separator + inputFileName.substring(index);
    } else {
      fileNameTemplate = newDirectory + File.separator + inputFileName;
    }
    return fileNameTemplate;
  }

}
