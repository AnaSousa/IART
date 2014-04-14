package graph;

import org.jgrapht.graph.DefaultEdge;

@SuppressWarnings("serial")
public class Edge extends DefaultEdge {

	private Node source;
	private Node target;
	private int weight=0;
	
	public Edge(Node from, Node to) {
		this.source=from;
		this.target=to;
	}

	public Edge(Node from, Node to, int weight) {
		
		if(weight<0) {
			System.out.println("Invalid weigth. Edge from " + from + " to " + to + " not added");
			return;
		}
		
		this.source=from;
		this.target=to;
		this.weight=weight;
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
    
    public int getWeight() {
    	return weight;
    }
}
