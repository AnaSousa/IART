package graph;

import org.jgrapht.graph.DefaultEdge;

@SuppressWarnings("serial")
public class Edge extends DefaultEdge {

	private String source;
	private String target;
	private int weight=0;
	
	public Edge(String from, String to) {
		this.source=from;
		this.target=to;
	}

	public Edge(String from, String to, int weight) {
		this.source=from;
		this.target=to;
		this.weight=weight;
	}
	
	public String getSource() {
		return source;
	}
	
	public String getTarget() {
		return target;
	}
	
    public String toString() {
        return "(" + source + " : " + target + ")";
    }
    
    public int getWeight() {
    	return weight;
    }
}
