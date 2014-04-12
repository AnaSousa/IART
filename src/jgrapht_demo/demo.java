package jgrapht_demo;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;

public class demo {
	
	
	public static void main(String [] args) {
		Class<? extends DefaultEdge> edgeClass = null;
		DirectedPseudograph<String, DefaultEdge> a = new DirectedPseudograph<String, DefaultEdge>((Class<? extends DefaultEdge>) edgeClass);

		a.addVertex("1");
		a.addVertex("2");
		a.addVertex("3");
		a.addEdge("1", "2", new DefaultEdge());
		a.addEdge("2", "3", new DefaultEdge());
		a.addEdge("3", "1", new DefaultEdge());
		System.out.println(a);
		

		DijkstraShortestPath<String, DefaultEdge> o = new DijkstraShortestPath<String, DefaultEdge>(a, "1", "3");
		
		
		System.out.println(o.getPathEdgeList());
	}
}
