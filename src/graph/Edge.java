package graph;

import java.io.Serializable;

import org.jgrapht.graph.DefaultWeightedEdge;

@SuppressWarnings("serial")
public class Edge extends DefaultWeightedEdge implements Serializable {

	private Node source;
	private Node target;
	private int weight=0;
	
	private boolean directed;
	private boolean resetFuel; //add fuel
	private boolean addedGarbage; //IF false not sum
	
	public Edge(Node from, Node to) {
		this.source=from;
		this.target=to;
		this.directed = true;
		this.addedGarbage=true;
		this.resetFuel=false;
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

	/**
	 * @param source the source to set
	 */
	public void setSource(Node source) {
		this.source = source;
	}
	

	/**
	 * @return the resetFuel
	 */
	public boolean isResetFuel() {
		return resetFuel;
	}

	/**
	 * @param resetFuel the resetFuel to set
	 */
	public void setResetFuel(boolean resetFuel) {
		this.resetFuel = resetFuel;
	}

	/**
	 * @return the addedGarbage
	 */
	public boolean isAddedGarbage() {
		return addedGarbage;
	}

	/**
	 * @param addedGarbage the addedGarbage to set
	 */
	public void setAddedGarbage(boolean addedGarbage) {
		this.addedGarbage = addedGarbage;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Edge)
		{
			Edge e = (Edge) obj;
			return e.source==source && e.target==target;
		}
		return super.equals(obj);
	}
}
