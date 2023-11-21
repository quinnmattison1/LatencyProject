package edu.uiowa.cs.warp;

import static org.junit.Assert.*;
import java.util.ArrayList;

//import org.junit.Before;
import org.junit.Test;
/**
 * @author srgoddard
 * 
 * Methods to test in WorkLoad.java:
 * [X]setFlowsInPriorityOrder()
 * [X]getNodesInFlow()
 * [X]nextAbsoluteDeadline()
 * [X]getFlowDeadline()
 * [X]nextReleaseTime()
 * [X]setFlowsInRMorder()
 * [X]setFlowDeadline()
 */


public class GoddardWorkloadTest {	
	
	//Testing Lower Bound for setFlowsInPriorityOrder
	@Test(timeout = 5000)
	public void testLBsetFlowsInPriorityOrder() {
		boolean result = false;
		result = helperSetFlowsInPriorityOrder(1);
		assertEquals(true, result);
	}
	
	//Testing Random Middle for setFlowsInPriorityOrder
	@Test(timeout = 5000)
	public void testRMsetFlowsInPriorityOrder() {
		boolean result = false;
		result = helperSetFlowsInPriorityOrder(2);
		assertEquals(true, result);
	}
	
	//Testing Upper Bound for setFlowsInPriorityOrder
	@Test(timeout = 5000)
	public void testUBsetFlowsInPriorityOrder() {
		boolean result = false;
		result = helperSetFlowsInPriorityOrder(3);
		assertEquals(true, result);
	}
	
	//Testing LowerBound for getNodesInFlow
	@Test(timeout = 5000)
	public void testLBgetNodesInFlow() {
		Boolean result = false;
		result = helperGetNodesInFlow(1);
		assertEquals(true, result);
	}
	
	//Testing Random Middle Bound for getNodesInFlow
	@Test(timeout = 5000)
	public void testRMgetNodesInFlow() {
		Boolean result = false;
		result = helperGetNodesInFlow(2);
		assertEquals(true, result);
	}
	
	//Testing Upper Bound for getNodesInFlow
	@Test(timeout = 5000)
	public void testUBgetNodesInFlow() {
		Boolean result = false;
		result = helperGetNodesInFlow(3);
		assertEquals(true, result);
	}

	//Testing Lower Bound for nextAbsoluteDeadline
	@Test(timeout = 5000)
	public void testLBnextAbsoluteDeadline() {
		Integer result = 0;
		result = helperNextAbsoluteDeadline(1);
		assertSame(40, result);	
	}
	
	//Testing Random Middle for nextAbsoluteDeadline
	@Test(timeout = 5000)
	public void testRMnextAbsoluteDeadline() {
		Integer result = 0;
		result = helperNextAbsoluteDeadline(2);
		assertSame(60, result);	
	}
	
	//Testing Upper Bound for nextAbsoluteDeadline
	@Test(timeout = 5000)
	public void testUBnextAbsoluteDeadline() {
		Integer result = 0;
		result = helperNextAbsoluteDeadline(3);
		assertSame(120, result);	
	}
	
	//Testing Lower Bound for getFlowDeadline
	@Test(timeout = 5000)
	public void testLBgetFlowDeadline() {
		Integer result = 0;
		result = helperGetFlowDeadline(1);
		assertSame(20, result);
	}
	
	//Testing Random Middle for getFlowDeadline
	@Test(timeout = 5000)
	public void testRMgetFlowDeadline() {
		Integer result = 0;
		result = helperGetFlowDeadline(2);
		assertSame(100, result);
	}
	
	//Testing Upper Bound for getFlowDeadline
	@Test(timeout = 5000)
	public void testUBgetFlowDeadline() {
		Integer result = 0;
		result = helperGetFlowDeadline(3);
		assertSame(100, result);
	}
	
	//Testing Lower Bound for nextReleaseTime
	@Test(timeout = 5000)
	public void testLBnextReleaseTime() {
		Integer result = 0;
		result = helperNextReleaseTime(1);
		assertSame(0, result);
	}
	
	//Testing for Random Middle for nextReleaseTime
	@Test(timeout = 5000)
	public void testRMnextReleaseTime() {
		Integer result = 0;
		result = helperNextReleaseTime(2);
		assertSame(60, result);
	}
	
	//Testing for Upper Bound for nextReleaseTime
	@Test(timeout = 5000)
	public void testUBnextReleaseTime() {
		Integer result = 0;
		result = helperNextReleaseTime(3);
		assertSame(100, result);
	}
	
	//Testing for Lower Bound for setFlowsInRMorder
	@Test(timeout = 5000)
	public void testLBsetFlowsInRMorder() {
		boolean result = false;
		result = helperSetFlowsInRMorder(1);
		assertSame(true, result);
	}
	
	//Testing for Random Middle for setFlowsInRMorder
	@Test(timeout = 5000)
	public void testRMsetFlowsInRMorder() {
		boolean result = false;
		result = helperSetFlowsInRMorder(2);
		assertSame(true, result);
	}
	
	//Testing for Upper Bound for setFlowsInRMorder
	@Test(timeout = 5000)
	public void testUBsetFlowsInRMorder() {
		boolean result = false;
		result = helperSetFlowsInRMorder(3);
		assertSame(true, result);
	}
	
	//Testing for Lower Bound for setFlowDeadline
	@Test(timeout = 5000)
	public void testLBsetFlowDeadline() {
		Integer result = 0;
		result = helperSetFlowDeadline(1);
		assertSame(10 ,result);
	}
	
	//Testing for Random Middle for setFlowDeadline
	@Test(timeout = 5000)
	public void testRMsetFlowDeadline() {
		Integer result = 0;
		result = helperSetFlowDeadline(2);
		assertSame(30 ,result);
	}
	
	//Testing for Upper Bound for setFlowDeadline
	@Test(timeout = 5000)
	public void testUBsetFlowDeadline() {
		Integer result = 0;
		result = helperSetFlowDeadline(3);
		assertSame(100 ,result);
	}
	
	
	public boolean helperGetNodesInFlow(Integer input) {
		WorkLoad testFlows = new WorkLoad(0.9, 0.99, "Stresstest.txt");
		int flowLength = testFlows.maxFlowLength();
		String[] nodes = new String[flowLength]; 
		Boolean result = false;
		nodes = testFlows.getNodesInFlow("F1");
		if (input == 1) {
			//Checking if first flow has information
			if (nodes[0].equals("B")) {
				result = true;
			}
		}
		if (input == 2) {
			//Checking random middle if has information
			if (nodes[1].equals("C")) {
				result = true;
			}
		}
		if (input == 3) {
			//Checking last flow if it has information
			if (nodes[2].equals("D")) {
				result = true;
			}
		}
		return result;
	}

	public Integer helperGetFlowDeadline(Integer input) {
		WorkLoad testFlows = new WorkLoad(0.9, 0.99, "Stresstest.txt");
		Integer flowDeadline = 1;
		//For testing Lower Bound
		if(input == 1) {
			flowDeadline = testFlows.getFlowDeadline("F1");
		}
		//For testing Random Middle
		if(input == 2) {
			flowDeadline = testFlows.getFlowDeadline("F9");
		}
		//For testing Upper Bound
		if(input == 3) {
			flowDeadline = testFlows.getFlowDeadline("AF10");
		}
		return flowDeadline;
	}

	public boolean helperSetFlowsInPriorityOrder(Integer input) {
		WorkLoad testFlows = new WorkLoad(0.9, 0.99, "Stresstest.txt");
		boolean result = false;
		ArrayList<String> nodes = new ArrayList<>(); 
		String test = new String();
		testFlows.setFlowsInPriorityOrder();
		nodes = testFlows.getFlowNamesInPriorityOrder();

		//For testing first position F1
		if(input == 1) {
			test = nodes.get(0);
			if (test.equals("F1")) {
				result = true;
			}
		}
		//For testing random middle F4
		if(input == 2) {
			test = nodes.get(5);
			if (test.equals("F4")) {
				result = true;
			}
		}
		//For testing last position AF10
		if(input == 3) {
			test = nodes.get(14);
			if (test.equals("AF10")) {
				result = true;
			}
		}
		return result;
	}

	public Integer helperNextAbsoluteDeadline(Integer input) {
		WorkLoad testFlows = new WorkLoad(0.9, 0.99, "Stresstest.txt");
		Integer absoluteDeadline = 1;
		if(input == 1) {
			absoluteDeadline = testFlows.nextAbsoluteDeadline("F1", 5);
		}
		if(input == 2) {
			absoluteDeadline = testFlows.nextAbsoluteDeadline("F1", 25);
		}
		if(input == 3) {
			absoluteDeadline = testFlows.nextAbsoluteDeadline("F1", 100);
		}
		
		return absoluteDeadline;
	}

	public Integer helperNextReleaseTime(Integer input) {
		WorkLoad testFlows = new WorkLoad(0.9, 0.99, "Stresstest.txt");
		Integer nextReleaseTime = 0;
		//Testing for Lower Bound
		if(input == 1) {
			nextReleaseTime = testFlows.nextReleaseTime("F1", 0);
		}
		//Testing for Random Middle
		if(input == 2) {
			nextReleaseTime = testFlows.nextReleaseTime("F1", 50);
		}
		//Testing for Upper Bound
		if(input == 3) {
			nextReleaseTime = testFlows.nextReleaseTime("F1", 100);
		}
		return nextReleaseTime;
	}

	public boolean helperSetFlowsInRMorder(Integer input) {
		WorkLoad testFlows = new WorkLoad(0.9, 0.99, "Stresstest.txt");
		boolean result = false;
		ArrayList<String> nodes = new ArrayList<>(); 
		String test = new String();
		testFlows.setFlowsInRMorder();
		nodes = testFlows.getFlowNamesInPriorityOrder();
		
		if(input == 1) {
			test = nodes.get(0);
			if (test.equals("F1")) {
				result = true;
			}
		}
		
		if(input == 2) {
			test = nodes.get(5);
			if(test.equals("F4")) {
				result = true;
			}
		}
		
		if(input == 3) {
			test = nodes.get(14);
			if(test.equals("AF10")) {
				result = true;
			}
		}
		return result;
	}

	public Integer helperSetFlowDeadline(Integer input) {
		WorkLoad testFlows = new WorkLoad(0.9, 0.99, "Stresstest.txt");
		Integer result = 1;
		//Testing Lower Bound 
		if(input == 1) {
			testFlows.setFlowDeadline("F1", 10);
			result = testFlows.getFlowDeadline("F1");
		}
		//Testing Random Middle
		if(input == 2) {
			testFlows.setFlowDeadline("F1", 30);
			result = testFlows.getFlowDeadline("F1");
		}
		//Testing Upper Bound
		if(input == 3) {
			testFlows.setFlowDeadline("F1", 100);
			result = testFlows.getFlowDeadline("F1");
		}

		return result;
	}
}
