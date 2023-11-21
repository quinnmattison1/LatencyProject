package edu.uiowa.cs.warp;

import java.util.ArrayList;

/**
 * <p> This class is for representing network flows.
 * 		Network flows are used to model the communication patterns between other flows within the network.
 * 		Each flow has the following properties: priority, index, and values that show if data has been transmitted reliably.
 * 		Flows are compared based off their priority and contain nodes and edges
 * 		
 * 
 * @author sgoddard
 * @author Frank Forliano (creating comments, for JavaDoc)
 */
public class Flow extends SchedulableObject implements Comparable<Flow>{

	private static final Integer UNDEFINED = -1;
	private static final Integer DEFAULT_FAULTS_TOLERATED = 0; 
	private static final Integer DEFAULT_INDEX = 0;
	private static final Integer DEFAULT_PERIOD = 100; 
	private static final Integer DEFAULT_DEADLINE = 100;
	private static final Integer DEFAULT_PHASE = 0;
	

    Integer initialPriority = UNDEFINED;
    Integer index;  // order in which the node was read from the Graph file
    Integer numTxPerLink; //  determined by fault model
    ArrayList<Node> nodes; // Flow src is 1st element and flow snk is last element in array
    /*
     *  nTx needed for each link to reach E2E reliability target. Indexed by src node of the link. 
     *  Last entry is total worst-case E2E Tx cost for schedulability analysis
     */
    ArrayList<Integer> linkTxAndTotalCost; 
    ArrayList<Edge> edges; //used in Partition and scheduling
    Node nodePredecessor;
    Edge edgePredecessor;
    
    /*
     * Constructor that sets name, priority, and index
     * @param name The name of the Flow
     * @param priority The priority of the Flow	
     * @param Integer The flow index number
     */
    
    Flow (String name, Integer priority, Integer index){
    	super(name, priority, DEFAULT_PERIOD, DEFAULT_DEADLINE, DEFAULT_PHASE);
    	this.index = index;
        /*
         *  Default numTxPerLink is 1 transmission per link. Will be updated based
         *  on flow updated based on flow length and reliability parameters
         */
        this.numTxPerLink = DEFAULT_FAULTS_TOLERATED + 1; 
        this.nodes = new ArrayList<>();
        this.edges  = new ArrayList<>();
        this.linkTxAndTotalCost = new ArrayList<>();
        this.edges = new ArrayList<>();	
        this.nodePredecessor = null;
        this.edgePredecessor = null;
    }
    
    /*
     * The default constructor for Flow
     */
    Flow () {
    	super();
    	this.index = DEFAULT_INDEX;
    	/*
    	 *  Default numTxPerLink is 1 transmission per link. Will be updated based
    	 *  on flow updated based on flow length and reliability parameters
    	 */
    	this.numTxPerLink = DEFAULT_FAULTS_TOLERATED + 1; 
    	this.nodes = new ArrayList<>();
    	this.linkTxAndTotalCost = new ArrayList<>();
    	this.edges = new ArrayList<>();
    	this.nodePredecessor = null;
        this.edgePredecessor = null;
    }

	/**
	 * Use this to see the flow priority)
	 * @return the initialPriority 
	 */
	public Integer getInitialPriority() {
		return initialPriority;
	}

	/**
	 * Return the value of the index.
	 * @param index
	 * @return the index (use this to get the index of the list))
	 */
	public Integer getIndex() {
		return index;
	}

	/** Use to see flow transmissions.
	 *  @param numTxPerLink
	 * @return the numTxPerLink 
	 */
	public Integer getNumTxPerLink() {
		return numTxPerLink;
	}

	/**
	 * Returns an ArrayList object of the nodes.
	 * @return nodes 
	 */
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	/**
	 * Get the edges from the flow.
	 * @param edges 
	 * @return the nodes (returns an ArrayList object of the edges)
	 */
	public ArrayList<Edge> getEdges() {
		return edges;
	}

	/**
	 * Add an edge to the flow.
	 * @param edge The edge which is added to the flow.
	 */
	public void addEdge(Edge edge) {
		/* set predecessor and add edge to flow */
		edge.setPredecessor(edgePredecessor);
		edges.add(edge);
		/* update predecessor for next edge added */
		edgePredecessor = edge;
	}
	
	/**
	 * Add a node to the flow.
	 * @param node The node which is added to the flow.
	 */
	public void addNode(Node node) {
		/* set predecessor and add edge to flow */
		node.setPredecessor(nodePredecessor);
		nodes.add(node);
		/* update predecessor for next edge added */
		nodePredecessor = node;
	}
	/**
	 * Returns the link transmission and cost of the value.
	 * @return An ArrayList that contains the link transmission and the cost of the value.
	 */
	public ArrayList<Integer> getLinkTxAndTotalCost() {
		return linkTxAndTotalCost;
	}

	/** The initialPriority to set.
	 * @param initialPriority 
	 */
	public void setInitialPriority(Integer initialPriority) {
		this.initialPriority = initialPriority;
	}

	/**
	 * The index value which needs to be set.
	 * @param index 
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * The numTxPerLink to set.
	 * @param numTxPerLink
	 */
	public void setNumTxPerLink(Integer numTxPerLink) {
		this.numTxPerLink = numTxPerLink;
	}

	/**
	 * The nodes to set.
	 * @param nodes 
	 */
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * The linkTxAndTotalCost to set.
	 * @param linkTxAndTotalCost 
	 */
	public void setLinkTxAndTotalCost(ArrayList<Integer> linkTxAndTotalCost) {
		this.linkTxAndTotalCost = linkTxAndTotalCost;
	}
	/**
	 * Flow that needs to be compared to.
	 * @param flow 
	 * @return Return a negative or positive value depending on the other flow's priority
	 */
	@Override
    public int compareTo(Flow flow) {
    	// ascending order (0 is highest priority)
        return flow.getPriority() > this.getPriority() ? -1 : 1;
    }
    /** Returns a string representation of the flow.
     * @param toString
     * @return Flow Name
     */
    @Override
    public String toString() {
        return getName();
    }
    
}
