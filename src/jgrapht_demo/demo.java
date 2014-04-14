package jgrapht_demo;

import graph.Edge;
import graph.Graph;
import graph.Node;

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
		
		
		//com adaptaçoes de acordo com a implementação feita
		Class<? extends Edge> edgeClass1 = null;
		Graph b = new Graph((Class<? extends Edge>) edgeClass1);
		
		Node n=new Node();
		Node n1=new Node();
		Node n2=new Node();
		b.addVertex(n.getId());
		b.addVertex(n1.getId());
		b.addVertex(n2.getId());
		b.addEdge(n, n1);
		b.addEdge(n1, n2);
		b.addEdge(n2, n);
		System.out.println(b.toString()); //TODO: porque imprime de forma diferente? :s
		
		DijkstraShortestPath<String, Edge> c = new DijkstraShortestPath<String, Edge>(b, n.getId(), n2.getId());
		System.out.println(c.getPathEdgeList());
	}
}
