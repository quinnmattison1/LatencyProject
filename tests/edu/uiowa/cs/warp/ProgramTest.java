package edu.uiowa.cs.warp;

import static org.junit.Assert.*;

import java.util.HashMap;
import org.junit.Test;
import edu.uiowa.cs.warp.SystemAttributes.ScheduleChoices;
/**
 * @author srgoddard
 */


public class ProgramTest{

	

	//Testing Lower Bound for getNodeMapIndex
	@Test(timeout = 5000)
	public void testLBgetNodeMapIndex() {
		Integer result = -1;
		result = helperGetNodeMapIndex(1);
		assertSame(0, result);
	}
	
	//Testing Random Middle for getNodeMapIndex
	@Test(timeout = 5000)
	public void testRMgetNodeMapIndex() {
		Integer result = -1;
		result = helperGetNodeMapIndex(2);
		assertSame(10, result);
	}
	
	//Testing Upper Bound for getNodeMapIndex
	@Test(timeout = 5000)
	public void testUBgetNodeMapIndex() {
		Integer result = -1;
		result = helperGetNodeMapIndex(3);
		assertSame(22, result);
	}
	
	public Integer helperGetNodeMapIndex(Integer input) {
		var nodeMap = new HashMap<String, Integer>();
		WorkLoad newWorkload = new WorkLoad(0.9, 0.99,"StressTest.txt");
		Program testProgram = new Program(newWorkload, 4, ScheduleChoices.PRIORITY);
		Integer index = -1;
		nodeMap = testProgram.getNodeMapIndex();
		if(input == 1) {
			index = nodeMap.get("A");
		}
		
		if(input == 2) {
			index = nodeMap.get("K");
		}
		
		if(input == 3) {
			index = nodeMap.get("W");
		}
		
		return index;
		
	}

}