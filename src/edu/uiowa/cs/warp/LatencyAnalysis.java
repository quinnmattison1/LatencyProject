package edu.uiowa.cs.warp;

import edu.uiowa.cs.warp.WarpDSL.InstructionParameters;
import java.util.HashMap;

/**

 * @author sgoddard
 * @author srgoddard (for java doc comments/refactor buildLatencyReport())
 * @version 1.6
 */
public class LatencyAnalysis {

	/**
	 * @param DEADLINE_MISS = DEADLINE MISS
	 * @param FLOW_SEPARATOR = ******************************\n
	 * @param latencyReport used to hold latency message or flow separator
	 * @param program used to hold the toProgram()
	 * @param workload used to hold the toWorkload()
	 * @param programTable used to hold the getSchedule()
	 * @param nodeIndex used to hold the NodeMapIndex()
	 */
   
  public static String DEADLINE_MISS = " => DEADLINE MISS";
  private static String FLOW_SEPARATOR = "******************************\n";
  private Description latencyReport;
  private Program program;
  private WorkLoad workload;
  private ProgramSchedule programTable;
  private HashMap<String, Integer> nodeIndex;
  private Integer time;
  private Integer instance;
  private Integer numTxRequired;
  private Integer numTxProcessed;
  private Integer deadline;
  /**
   * Adding the private var dsl
   */
  private WarpDSL dsl;
  
  /**
   * This object holds the toWorkload()/getSchedule()/NodeMapIndex()/and toProgram()
   * Has since been refactored with the LatencyAnalysis(Program program) constructor.
   */
  LatencyAnalysis(WarpInterface warp) {
	this(warp.toProgram());
    //this.program = warp.toProgram();
    //this.workload = warp.toWorkload();
    //this.programTable = program.getSchedule();
    //this.nodeIndex = program.getNodeMapIndex();
    //buildLatencyReport();
  }

  /**
   * This contains the program side of the nodes. Including toWorkload()/getSchedule()/getNodeMapIndex()
   * We refactored the latencyAnalysis(WarpInterface Warp) with LatencyAnalysis(Program program). This
   * removes code duplication making it easier to follow.
   */
  LatencyAnalysis(Program program) {
    this.program = program;
    this.workload = program.toWorkLoad();
    this.programTable = program.getSchedule();
    this.nodeIndex = program.getNodeMapIndex();
    dsl = new WarpDSL();
    buildLatencyReport();
  }

  /**
   * latencyReport() now returns latencyReport instead of building the report
   * @return latencyReport
   */
  public Description latencyReport() {
	  return latencyReport;
	  
  }
  
  public void buildLatencyReport() {
    /*
     * 
     * Build a latency report. Flows are output in priority order (based on the priority used to
     * build the program. The latency for each instance of the flow is reported as follows
     * "Maximum latency for FlowName:Instance is Latency"
     * 
     * For flow instances that have latency > deadline, then the latency message is appended with
     * the string " => DEADLINE MISS"
     * 
     * A line of 30 '*' characters separates each group of flow instance reports.
     * 
     * When there are not enough transmissions attempted between the release and the next release of
     * an instance, then the latency is not computed (as we assume deadline <= period. Thus, the
     * report is: "UNKNOWN latency for FlowName:Instance; Not enough transmissions attempted"
     * 
     */
	latencyReport = new Description();  
    var flows = workload.getFlowNamesInPriorityOrder();
    for (String flowName : flows) {	
    	helperBuildLatency(flowName);			
    }
  }
  
  /**
   * 
   * @return latencyTable
   */
  public ProgramSchedule getLatencyTable() {
	  var flows = workload.getFlowNamesInPriorityOrder();
	  var numRows = flows.size();
	  var numColumns = workload.getHyperPeriod();
	  return new ProgramSchedule(numRows,numColumns);
	// Should return latencyTable when turn in for Sprint 2
  }
  
  /**
   * Helps build latency report by dividing up the loops
   * @param flowName
   */
  public void helperBuildLatency(String flowName) {
  	time = 0;
  	/* get snk and src from helper function getSnk() and getSrc()*/
    String snk = getSnk(flowName);
    String src = getSrc(flowName);
    /* get (column) indexes into programTable of these nodes */
    var snkIndex = nodeIndex.get(snk);   
    var srcIndex = nodeIndex.get(src);	
    /* get the number of transmission required for the last link in the flow */
    getNumTxRequired(flowName);
    numTxProcessed = 0; // num of Tx seen in the program schedule so far
    instance = 0;
    while (time < workload.getHyperPeriod()) {
    	calculateReleaseTime(src, snk, srcIndex, snkIndex, flowName);
       }
       String flowSeparator = FLOW_SEPARATOR;
       latencyReport.add(flowSeparator);				
  }
  
  /**
   * Gets snk of last link in the flow, which is also the Flow snk node 
   * @param flowName
   * @return nodes[flowSnkIndex]
   */
  public String getSnk (String flowName) {
	  var nodes = workload.getNodesInFlow(flowName); // names of nodes in flow
      var flowSnkIndex = nodes.length - 1;
      return nodes[flowSnkIndex];
  }
  
  /**
   * Gets the src of last link in the flow 
   * @param flowName
   * @return nodes[flowSnkIndex - 1]
   */
  public String getSrc(String flowName) {
	  var nodes = workload.getNodesInFlow(flowName); // names of nodes in flow
      var flowSnkIndex = nodes.length - 1;
      return nodes[flowSnkIndex - 1];
  }
  
  /**
   * 
   * @param flowName
   * @return Integer from array #of Tx Attempts Per Link 
   */
  public void getNumTxRequired(String flowName) {
	  var numTxAttemptsPerLink = workload.getNumTxAttemptsPerLink(flowName);
	  numTxRequired = numTxAttemptsPerLink[numTxAttemptsPerLink.length - 1];
  }
  
  /**
   * get next release time and absolute deadline of the flow
   * @param numTxRequired
   * @param src
   * @param snk
   * @param srcIndex
   * @param snkIndex
   * @param numTxProcessed
   * @param flowName
   */
  public void calculateReleaseTime(String src, String snk, Integer srcIndex, Integer snkIndex, 
		  String flowName) {
	  var releaseTime = workload.nextReleaseTime(flowName, time);
	  deadline = workload.nextAbsoluteDeadline(flowName, releaseTime);
	  var nextReleaseTime = workload.nextReleaseTime(flowName, deadline);
	  time = releaseTime;
	  numTxProcessed = 0; // num of Tx seen in the program schedule so far
	  while (time < nextReleaseTime) {
		  calculateNumTxProcessed(srcIndex, snkIndex, flowName, src, snk, releaseTime, 
				  nextReleaseTime);
		  /* get instruction strings at these to locations */
	  }
	  if (numTxProcessed < numTxRequired) {
		  /*
		   * This flow missed its deadline with required number of Tx!! This message should not be
		   * printed with the schedulers built
		   */
		  recordLatencyMessage(flowName);
	  }
	  instance++;	
  }
  
  public WorkLoad getWorkLoad() {
	  return workload;
  }
  
  /**
   * Calculates numTxProcessed
   * @param srcIndex
   * @param snkIndex
   * @param flowName
   * @param src
   * @param snk
   * @param deadline
   * @param releaseTime
   * @param nextReleaseTime
   */
  public void calculateNumTxProcessed(Integer srcIndex, Integer snkIndex, String flowName, String src, String snk,
		  Integer releaseTime, Integer nextReleaseTime) {
	  String instr1 = programTable.get(time, srcIndex);
	  String instr2 = programTable.get(time, snkIndex);
	  numTxProcessed += numMatchingTx(flowName, src, snk, instr1);
	  numTxProcessed += numMatchingTx(flowName, src, snk, instr2);
	  if (numTxProcessed == numTxRequired) {
		  /*
		   * all required Tx attempts have been made compute and record latency
		   */
		  var latency = time - releaseTime + 1;
		  // record and report latency message via constructLatencyMessage()
		  constructLatencyMessage(flowName, latency);
		  time = nextReleaseTime;
		} else {
			time++;
		}
	  
  }
  
  /**
   * This method is to combine flowName, instance, and latency in a message.
   * As well as include a message if a deadline is missed.
   * @param flowName
   * @param instance
   * @param deadline
   * @param latency
   * @return latencyMsg
   */
  public void constructLatencyMessage(String flowName, Integer latency) {
    String latencyMsg = 
    		String.format("Maximum latency for %s:%d is %d", flowName, instance, latency);
    if (latency > deadline) {
    /* deadline missed, so color the text red */
    	 latencyMsg += DEADLINE_MISS;
    }
  	latencyMsg += "\n";
  	latencyReport.add(latencyMsg);
  }
  
  /**
   * Records unknown latency message for latency Report
   * @param flowName
   * @param instance
   */
  public void recordLatencyMessage(String flowName) {
		String latencyMsg =
				String.format("UNKNOWN latency for %s:%d; Not enough transmissions attempted\n",
						flowName, instance);
		latencyReport.add(latencyMsg);
  }
  
  /**
 	* @return numTX counts the numbers of Tx attempts made successfully
 	* @param dsl is used as a mediator to establish new WarpDSL()
 	* @param instructionParametersArray is used to obtain instruction parameters from the dsl.getInstructionParameters(instr)
 	* This method is used to obtain the number of successful Tx attempts if flowName equals flow
 	*/
  public Integer numMatchingTx(String flow, String src, String snk, String instr) {
	var numTx = 0;

    if (flow == null || src == null || snk == null || instr == null) {
      /* make sure all parameters are valid */
      return numTx;
    }
    /*
     * get a Warp instruction parser object and then get the instruction parameters from the
     * instruction string.
     */
    var instructionParametersArray = dsl.getInstructionParameters(instr);

    for (InstructionParameters entry : instructionParametersArray) {
      String flowName = entry.getFlow();
      if (flowName.equals(flow)) {
        /*
         * This instruction is for the flow we want. (flow name is set for push/pull instructions,
         * which are all we want. If not push/pull, then we skip this instruction.) If flow, src,
         * and snk names in instruction match input parameters, then we have a Tx attempt.
         */
        if (src.equals(entry.getSrc()) && snk.equals(entry.getSnk())) {
          /* flow, src, and snk match, so increment Tx attempts */
          numTx++;
        }
      }
    }
    return numTx;
  }
	   
	  
}
