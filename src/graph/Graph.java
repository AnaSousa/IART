package graph;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

@SuppressWarnings("serial")
public class Graph extends DirectedWeightedPseudograph<String, Edge>{

	//TODO: criar lista para guardar vertices?
	
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
	
	public boolean addEdge(Edge edge) {
		return addEdge(edge.getSource(), edge.getTarget(), edge);
	}
	
	public void calculateDistances() {
		//calcultateDistanceDump();
		//calculateDistanceStation();
	}
}
