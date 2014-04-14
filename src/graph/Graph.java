package graph;

import java.util.ArrayList;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

@SuppressWarnings("serial")
public class Graph extends DirectedWeightedPseudograph<String, Edge>{
	
	@SuppressWarnings("unchecked")
	public Graph(Class<? extends DefaultEdge> edgeClass) {
		super((Class<? extends Edge>) edgeClass);
	}

	public boolean addVertex() {
		Node n=new Node();
		return addVertex(n.getId());
	}
	
	public boolean addVertex(int type) {
		Node n=new Node(type);
		return addVertex(n.getId());
	}
	
	public boolean addVertex(Node node) {
		return addVertex(node.getId());
	}
	
	public boolean addEdge(String from, String to, int weight) {
		Edge e=new Edge(from, to, weight);
		return addEdge(e.getSource(), e.getTarget(), e);
	}
	
	public boolean addEdge(Node n1, Node n2) {
		Edge e=new Edge(n1.getId(), n2.getId());
		return addEdge(e.getSource(), e.getTarget(), e);
	}
	
	public boolean addEdge(Node n1, Node n2, int weight) {
		Edge e=new Edge(n1.getId(), n2.getId(), weight);
		return addEdge(e.getSource(), e.getTarget(), e);
	}
	
	public boolean addEdge(Edge edge) {
		return addEdge(edge.getSource(), edge.getTarget(), edge);
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
