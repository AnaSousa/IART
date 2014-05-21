package logic;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.jgrapht.graph.DefaultEdge;

public class ProgramData {
	private Graph g;
	private Truck t;
	private int multiple;
	static ProgramData data;

	/**
	 * @param g
	 * @param t
	 */
	private ProgramData() {
		Class<? extends DefaultEdge> edgeClass = null;
		this.g = new Graph(edgeClass);
		this.t = new Truck(500, 200);
		this.multiple = 1;
	}

	/**
	 * @return the g
	 */
	public Graph getGraph() {
		return g;
	}

	/**
	 * @param g
	 *            the g to set
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
	 * @param t
	 *            the t to set
	 */
	public void setTruck(Truck t) {
		this.t = t;
	}

	/**
	 * @return the data
	 */
	public static ProgramData getInstance() {
		if (data == null)
			data = new ProgramData();
		return data;
	}

	/**
	 * @return the multiple
	 */
	public int getMultiple() {
		return multiple;
	}

	/**
	 * @param multiple
	 *            the multiple to set
	 */
	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public Queue<Edge> searchPath(Node origin, Node destination) {

		for (Map.Entry<Integer, Node> entry : g.getNodes().entrySet()) {
			Node tmp = entry.getValue();
			if (tmp.getType() == Node.GARBAGE_CONTAINER)
				t.garbagesToPass.add(tmp);
		}
		
		Queue<Edge> e = AStarAlgorithm.searchAStar(g, origin, t);
		e = garbageAnalyze(e);
		e = gasAnalyze(e);
		System.out.println("Returned = " + e);
		return e;
	}

	public Queue<Edge> garbageAnalyze(Queue<Edge> edges) {
		double garbage = 0;
		Queue<Edge> result = new LinkedList<Edge>();
		for (Edge e : edges) {

			if (garbage == t.getCapacity()) {
				for (Edge e1 : e.getSource().getPathToDump()) {
					e1.setAddedGarbage(false);
					e1.setResetFuel(false);
					result.add(e1);
					t.addFuelConsumption(e1.getWeight());
				}
				garbage = 0;
					for (int i = e.getSource().getPathToDump().size() - 1; i >= 0; i--) {
						Edge e1 = e.getSource().getPathToDump().get(i);
						Edge added = new Edge(e1.getTarget(), e1.getSource());
						e1.setAddedGarbage(false);
						e1.setResetFuel(false);
						result.add(added);
					}
				result.add(e);
			} else {
				result.add(e);
				if (e.getTarget().getType() == Node.GARBAGE_CONTAINER)
				garbage += 100;
			}
		}
		return result;

	}

	public Queue<Edge> gasAnalyze(Queue<Edge> edges) {
		double fuel = this.t.getFuel();
		Queue<Edge> result = new LinkedList<Edge>();
		for (Edge e : edges) {

			if (fuel - e.getWeight() <= e.getSource()
					.getDistanceToPetrolStation()) {
				for (int i = 0; i < e.getSource().getPathToPetrolStation()
						.size(); i++) {

					Edge e1 = e.getSource().getPathToPetrolStation().get(i);
					e1.setAddedGarbage(false);
					e1.setResetFuel(true);
					result.add(e1);
					t.addFuelConsumption(e1.getWeight());
				}
				fuel = t.getFuel();
				for (int i = e.getSource().getPathToPetrolStation().size() - 1; i >= 0; i--) {
					Edge e1 = e.getSource().getPathToPetrolStation().get(i);
					Edge added = new Edge(e1.getTarget(), e1.getSource());
					added.setAddedGarbage(false);
					added.setResetFuel(false);
					result.add(added);
					t.addFuelConsumption(e1.getWeight());
				}
				result.add(e);
			} else {
				result.add(e);
				t.addFuelConsumption(e.getWeight());
				fuel -= e.getWeight();
			}
		}
		return result;
	}

}
