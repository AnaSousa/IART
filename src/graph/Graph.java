package graph;

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
		return addVertex(n.getStringId());
	}
	
	public boolean addVertex(int type) {
		Node n=new Node(type);
		return addVertex(n.getStringId());
	}
	
	public boolean addVertex(Node node) {
		return addVertex(node.getStringId());
	}
	
	public boolean addEdge(String from, String to, int weight) {
		Edge e=new Edge(from, to, weight);
		return addEdge(e.getSource(), e.getTarget(), e);
	}
	
	public boolean addEdge(Node n1, Node n2) {
		Edge e=new Edge(n1.getStringId(), n2.getStringId());
		return addEdge(e.getSource(), e.getTarget(), e);
	}
	
	public boolean addEdge(Node n1, Node n2, int weight) {
		Edge e=new Edge(n1.getStringId(), n2.getStringId(), weight);
		return addEdge(e.getSource(), e.getTarget(), e);
	}
	
	public boolean addEdge(Edge edge) {
		return addEdge(edge.getSource(), edge.getTarget(), edge);
	}
	
	public Set<Edge> getEdges() {
		return edgeSet();
	}
	
	public void calculateDistances() {
		//calcultateDistanceDump();
		//calculateDistanceStation();
	}
}
