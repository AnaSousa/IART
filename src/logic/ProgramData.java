package logic;

import java.util.Map;
import java.util.Queue;

import org.jgrapht.graph.DefaultEdge;

import graph.Edge;
import graph.Graph;
import graph.Node;

public class ProgramData {
private Graph g;
private Truck t;
static ProgramData data;
/**
 * @param g
 * @param t
 */
private ProgramData() {
	Class<? extends DefaultEdge> edgeClass = null;
	this.g = new Graph(edgeClass);
	this.t = new Truck(500,200);
}
/**
 * @return the g
 */
public Graph getGraph() {
	return g;
}
/**
 * @param g the g to set
 */
public void setGraph(Graph g) {
	this.g = g;
}
/**
 * @return the t
 */
public Truck getTruck() {
	return t;
}
/**
 * @param t the t to set
 */
public void setTruck(Truck t) {
	this.t = t;
}
/**
 * @return the data
 */
public static ProgramData getInstance() {
	if(data==null)
		data=new ProgramData();
	return data;
}

public Queue<Edge> searchPath(Graph g,Node origin, Node destination)
{
	
	for (Map.Entry<Integer, Node> entry : g.getNodes().entrySet()) {
		Node tmp = entry.getValue();
		if(tmp.getId()==Node.GARBAGE_CONTAINER)
			t.garbagesPassed.add(tmp);
	}
	return AStarAlgorithm.searchAStar(g, origin, destination,t);
}

}
