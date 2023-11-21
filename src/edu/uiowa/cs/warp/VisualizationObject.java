/**
 * 
 */
package edu.uiowa.cs.warp;

/**
 * @author sgoddard
 * @author srgoddard (for java doc comments)
 * @version 1.5
 *
 *Visualizes the information of the node into a readable name with the needed information.
 *Includes faults and schedule name.
 *Contains:
 *nameExtension()
 *displayVisualization()
 *fileVisualization()
 *
 */
abstract class VisualizationObject {

  private FileManager fm;
  private String suffix;
  private String nameExtension;
  private static final String NOT_IMPLEMENTED = "This visualization has not been implemented.";
  protected String[][] visualizationData;

  /**
  * 
  * Class Object that links together the filemanager with the workload and suffix.
  * @param nameExtension is the string variable to be added onto the name of the node
  * @param suffix is the
  *
  */
  
  VisualizationObject(FileManager fm, WorkLoad workLoad, String suffix) {
    this.fm = fm;
    this.nameExtension = String.format("-%sM-%sE2E",
        String.valueOf(workLoad.getMinPacketReceptionRate()), String.valueOf(workLoad.getE2e()));
    this.suffix = suffix;
    visualizationData = null;
  }

  /**
  * 
  * Class object that links together the filemanager to the specific node warp attributes and suffix.
  */

  VisualizationObject(FileManager fm, SystemAttributes warp, String suffix) {
    this.fm = fm;
    if (warp.getNumFaults() > 0) {
      this.nameExtension = nameExtension(warp.getSchedulerName(), warp.getNumFaults());
    } else {
      this.nameExtension =
          nameExtension(warp.getSchedulerName(), warp.getMinPacketReceptionRate(), warp.getE2e());
    }
    this.suffix = suffix;
    visualizationData = null;
  }

  /**
  * 
  * Class object to manage any faults that are attached to the nodes.
  */
  VisualizationObject(FileManager fm, SystemAttributes warp, String nameExtension, String suffix) {
    this.fm = fm;
    if (warp.getNumFaults() > 0) {
      this.nameExtension =
          nameExtension(warp.getSchedulerName(), warp.getNumFaults()) + nameExtension;
    } else {
      this.nameExtension =
          nameExtension(warp.getSchedulerName(), warp.getMinPacketReceptionRate(), warp.getE2e())
              + nameExtension;
    }

    this.suffix = suffix;
    visualizationData = null;
  }

  /**
  *
  * Class object of managing the file for each node
  */
  VisualizationObject(FileManager fm, String nameExtension, String suffix) {
    this.fm = fm;
    this.nameExtension = nameExtension;
    this.suffix = suffix;
    visualizationData = null;
  }

  /**
  * 
  * This method is used to attach the data of the node to the schedule name of the nodes.
  */
  private String nameExtension(String schName, Double m, double e2e) {
    String extension =
        String.format("%s-%sM-%sE2E", schName, String.valueOf(m), String.valueOf(e2e));
    return extension;
  }
  
  /**
  * 
  * This method is used to attach the faults of each node to the name of the same node.
  * @param extension formats the input data into a readable format
  */
  private String nameExtension(String schName, Integer numFaults) {
    String extension = String.format("%s-%sFaults", schName, String.valueOf(numFaults));
    return extension;
  }

  /**
   * 
   * @return the fm
   */
  public FileManager getFileManager() {
    return fm;
  }

  /**
  * 
  * This method is the initial step to visualize the data in a readible format.
  * @param content is created to be used as a description to hold the new ColumnHeader() and data
  * @param data is used to hold NULL through the createVisualizationData() method which returns NULL
  * @return content
  */
  public Description visualization() {
    Description content = new Description();
    var data = createVisualizationData();

    if (data != null) {
      String nodeString = String.join("\t", createColumnHeader()) + "\n";
      content.add(nodeString);

      for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
        var row = data[rowIndex];
        String rowString = String.join("\t", row) + "\n";
        content.add(rowString);
      }
    } else {
      content.add(NOT_IMPLEMENTED);
    }
    return content;
  }

  /**
  * 
  * Method creates the file name by adding the extension and suffix.
  * @return fm.createFile() which calls itself to create the file as a fileNameTemplate
  */
  public String createFile(String fileNameTemplate) {
    return fm.createFile(fileNameTemplate, nameExtension, suffix);
  }

  /**
  *
  * Method is the final step to combine the header/data and footer together and returns fileContent.
  * @param fileContent is created to contain all the data for nodes into a readible format
  */
  public Description fileVisualization() {
    Description fileContent = createHeader();
    fileContent.addAll(visualization());
    fileContent.addAll(createFooter());
    return fileContent;
  }

  /**
  * 
  * @return null
  */
  public GuiVisualization displayVisualization() {
    return null; // not implemented
  }
  
  /**
  * 
  * @return new Description header
  */
  protected Description createHeader() {
    Description header = new Description();
    return header;
  }
  
  /**
  * 
  * @return new Description footer
  */
  protected Description createFooter() {
    Description footer = new Description();
    return footer;
  }

  /**
  * 
  * @return NOT_IMPLEMENTED
  */
  protected String[] createColumnHeader() {
    return new String[] {NOT_IMPLEMENTED};
  }
  
  /**
  * 
  * @return visualizationData which is not implemented and returns null
  */
  protected String[][] createVisualizationData() {
    return visualizationData; // not implemented--returns null
  }
}
