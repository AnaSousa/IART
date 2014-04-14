package graph;

import java.util.ArrayList;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

@SuppressWarnings("serial")
public class Graph extends DirectedWeightedPseudograph<Node, Edge>{
	
	@SuppressWarnings("unchecked")
	public Graph(Class<? extends DefaultEdge> edgeClass) {
		super((Class<? extends Edge>) edgeClass);
	}

	public boolean addVertex() {
		Node n=new Node();
		return addVertex(n);
	}
	
	public boolean addVertex(int type) {
		Node n=new Node(type);
		return addVertex(n);
	}
	
	public boolean addVertex(Node node) {
		return addVertex(node);
	}
	
	
	/*public Edge addEdge(String from, String to, int weight) {
		
		if(containsVertex(from) && containsVertex(to)) {
			Edge e=new Edge(from, to, weight);
			if(addEdge(e.getSource(), e.getTarget(), e))
				return e;
			else 
				return null;
		}
		return null;
	}*/
	
	public Edge addEdge(Node n1, Node n2) {
		
		if(containsVertex(n1) && containsVertex(n2)) {
			Edge e=new Edge(n1, n2);
			if(addEdge(n1, n2, e))
				return e;
			else 
				return null;
		}
		return null;
	}
	
	public Edge addEdge(Node n1, Node n2, int weight) {
		
		if(containsVertex(n1) && containsVertex(n2)) {
			Edge e=new Edge(n1, n2, weight);
			if(addEdge(n1, n2, e))
				return e;
			else 
				return null;
		}
		return null;
	}
	
	public Edge addEdge(Edge edge) {
		
		if(containsVertex(edge.getSource()) && containsVertex(edge.getTarget())) {
			if(addEdge(edge.getSource(), edge.getTarget(), edge))
				return edge;
			else 
				return null;
		}
		return null;
	}
	
	public Set<Edge> getSetEdges() {
		return edgeSet();
	}
	
	public ArrayList<Edge> getEdges() {
		return new ArrayList<Edge>(getSetEdges());
	}
	
	public void calculateDistances() { //TODO
		//calcultateDistanceDump();
		//calculateDistanceStation();
	}
}
