package edu.uiowa.cs.warp;

import static org.junit.Assert.*;
import org.junit.Test;
import edu.uiowa.cs.warp.SystemAttributes.ScheduleChoices;

/**
 * Testing the CreateColumnHeader/createVisualizationData/createTitle
 * @author alvanotterloo
 * @author threlkeld
 */

public class LatencyVisualizationTest {
	
	private static ScheduleChoices select = ScheduleChoices.PRIORITY;
	
	public WorkLoad workLoad = new WorkLoad(1,0.9, 0.99, "Example1a.txt");
	public WarpInterface warp = SystemFactory.create(workLoad, 16, select);
	public LatencyVisualization lv = new LatencyVisualization(warp);
	
	public WorkLoad workLoad2 = new WorkLoad(1,0.9, 0.99, "StressTest4.txt");
	public WarpInterface warp2 = SystemFactory.create(workLoad2, 16, select);
	public LatencyVisualization lv2 = new LatencyVisualization(warp2);

	
	@Test(timeout = 5000)
	public void testCreateColumnHeader() {
		var hyper = workLoad.getHyperPeriod();
		String[] expected = lv.createColumnHeader(); 
		assertEquals(expected.length, hyper+1);
		

	}
	
	@Test(timeout = 5000)
	public void testCreateColumnHeaderStressTest() {
		var hyper = workLoad2.getHyperPeriod();
		String[] expected = lv2.createColumnHeader(); 
		assertEquals(expected.length, hyper+1);
	}
	
	@Test(timeout = 5000)
	public void testCreateVisualizationData() {
		// Call the method that needs testing
        String[][] actualData = lv.createVisualizationData(); 
        var flows = workLoad.getFlowNamesInPriorityOrder();
        
        // Assert that the dimensions match the expected dimensions
        int expectedRows = flows.size();
        int expectedColumns = workLoad.getHyperPeriod() + 1;
        int actualRows = actualData.length;
        int actualColumns = actualData[0].length;
        assertEquals(expectedRows, actualRows);
        assertEquals(expectedColumns, actualColumns);
       
	}
	@Test(timeout = 5000)
	public void testCreateVisualizationData2() {
		// Call the method that needs testing
        String[][] actualData = lv2.createVisualizationData(); 
        var flows = workLoad2.getFlowNamesInPriorityOrder();
        
        // Assert that the dimensions match the expected dimensions
        int expectedRows = flows.size();
        int expectedColumns = workLoad2.getHyperPeriod() + 1;
        int actualRows = actualData.length;
        int actualColumns = actualData[0].length;

        assertEquals(expectedRows, actualRows);
        assertEquals(expectedColumns, actualColumns);
	}
	
	@Test(timeout = 5000)
	public void testCreateTitle() {
		String title = lv.createTitle();
		String expected = String.format("Latency Analysis for graph %s\n", warp.getName());
		assertEquals(expected, title);
		
	}
	
	@Test(timeout = 5000)
	public void testCreateTitleStressTest() {
		String title = lv2.createTitle();
		String expected = String.format("Latency Analysis for graph %s\n", warp2.getName());
		assertEquals(expected, title);
		
	}
}


