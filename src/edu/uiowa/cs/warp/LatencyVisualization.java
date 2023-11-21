package edu.uiowa.cs.warp;

/**
 * LatencyVisualization creates the visualizations for the liability analysis of the WARP program.
 * <p>
 * 
 * CS2820 Fall 2023 Project: Implement this class to create the file visualization that is
 * requested in Warp.
 * 
 * @author sgoddard
 * @author srgoddard
 * @author qmattison
 * @version 1.6
 *
 */
public class LatencyVisualization extends VisualizationObject {

  // TODO Auto-generated class stub for unimplemented visualization

  private static final String SOURCE_SUFFIX = ".la";
  private static final String OBJECT_NAME = "Latency Analysis";
  private WarpInterface warp;
  private LatencyAnalysis la;

  LatencyVisualization(WarpInterface warp) {
    super(new FileManager(), warp, SOURCE_SUFFIX);
    this.warp = warp;
    this.la = warp.toLatencyAnalysis();
  }
  
  /**
  * @Override
  * Gathers the title, column header, and the data for the visualization
  * @return GuiVisualization(createTitle(), createColumnHeader(), createVisualizationData())
  */
  public GuiVisualization displayVisualization() {
	  return new GuiVisualization(createTitle(), createColumnHeader(), createVisualizationData());
  }
  
  /**
  * @Override
  * Creates header which includes Title, Scheduler Name, and number of faults
  * @return header
  */
  protected Description createHeader() {
	  Description header = new Description();

	    header.add(createTitle());
	    header.add(String.format("Scheduler Name: %s\n", warp.getSchedulerName()));

	    /* The following parameters are output based on a special schedule or the fault model */
	    if (warp.getNumFaults() > 0) { // only specify when deterministic fault model is assumed
	      header.add(String.format("numFaults: %d\n", warp.getNumFaults()));
	    }
	    return header;
  }
  
  /**
  * @Override
  * Returns null because there is no footer for the Latency Report
  * @return NULL
  */
  protected Description createFooter() {
	  return null;
  }

  /**
  * @Override
  * @return columnNames
  * Creates the column header for the LatencyTable.
  * Which includes Flow/Time Slot and the time from 0 to hyperPeriod
  */
  //Use the hyperperiod to get the columnheader numbers
  //Should be able to just visualize the columnheaders not figure it out
  //thats what we do in LatencyAnalysis class.
  protected String[] createColumnHeader() {

	  var latencyTable = la.getLatencyTable();
	  var numColumn = latencyTable.getNumColumns();
	  String[] columnNames = new String[numColumn + 1];
	  columnNames[0] = "Flow/Time Slot"; // add the Flow/Time Slot column header first
	  int i = 1;
	  while(numColumn >= i)
	  {
		  columnNames[i]= Integer.toString(i);
		  i++; 
	  } 
	  return columnNames;
  }
  
  /**
   * 
   * Takes the data from Latency Table and pushes it along to GUI for the visual table
   * @return visualizationData
   * @Override
   */
  protected String[][] createVisualizationData() {
	if (visualizationData == null) {
		var latencyTable = la.getLatencyTable();
		int numRows = latencyTable.getNumRows();
		int numColumns = latencyTable.getNumColumns();
		visualizationData = new String[numRows][numColumns + 1];

		for (int row = 0; row < numRows; row++) {
			visualizationData[row][0] = String.format("%s", row);
			for (int column = 0; column < numColumns; column++) {
				visualizationData[row][column + 1] = latencyTable.get(row, column);
			}
		}
	}
    return visualizationData;
  }
  
  /**
   * 
   * Creates the title for the .la function
   * This method should be set to private but is public for testing
   * @return
   */
  protected String createTitle() {
	  return String.format("Latency Analysis for graph %s\n", warp.getName());
  }
}
