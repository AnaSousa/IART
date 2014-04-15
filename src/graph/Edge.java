package graph;

import org.jgrapht.graph.DefaultWeightedEdge;

@SuppressWarnings("serial")
public class Edge extends DefaultWeightedEdge {

	private Node source;
	private Node target;
	private int weight=0;
	
	private boolean directed;
	
	public Edge(Node from, Node to) {
		this.source=from;
		this.target=to;
		this.directed = true;
	}

	public Edge(Node from, Node to, int weight) {
		if(weight<0) {
			System.out.println("Invalid weigtht. Edge from " + from + " to " + to + " not added");
			return;
		}
		
		this.source=from;
		this.target=to;
		this.weight=weight;
		this.directed = true;
		
	}
	
	public Edge(Node from, Node to, int weight, boolean directed) {
		if(weight<0) {
			System.out.println("Invalid weigtht. Edge from " + from + " to " + to + " not added");
			return;
		}
		
		this.source=from;
		this.target=to;
		this.weight=weight;
		this.directed = directed;
	}
	
	public Node getSource() {
		return source;
	}
	
	public Node getTarget() {
		return target;
	}
	
	public int getSourceId() {
		return source.getId();
	}
	
	public int getTargetId() {
		return target.getId();
	}
	
    public String toString() {
        return "(" + source + " : " + target + ")";
    }
    
    public double getWeight() {
    	return weight;
    }

	/**
	 * @return the directed
	 */
	public boolean isDirected() {
		return directed;
	}

	/**
	 * @param directed the directed to set
	 */
	public void setDirected(boolean directed) {
		this.directed = directed;
	}
}
