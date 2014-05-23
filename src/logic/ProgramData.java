package logic;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
		ArrayList<Node> copyHash = new ArrayList<Node>(t.getGarbagesToPass());
		Queue<Edge> result = new LinkedList<Edge>();
		ArrayList<Edge> resultArray = new ArrayList<Edge>();
		while (edges.size() > 0)
			resultArray.add(edges.poll());
		for (int i = 0; i < resultArray.size(); i++) {
			Edge e = resultArray.get(i);
			if (t.getActualGarbage() >= t.getCapacity()) {
				for (int k = 0; k < e.getSource().getPathToDump().size(); k++) {
					Edge e1 = e.getSource().getPathToDump().get(k);
					e1.setAddedGarbage(false);
					e1.setResetFuel(false);
					resultArray.add(i + k, e1);
					t.addFuelConsumption(e1.getWeight());
				}
				t.setActualGarbage(0);
				ArrayList<Node> garbages = t.getGarbagesToPass();
				for (int j = 0; j <= i; j++) {
					if (j == 0) {
						if (resultArray.get(j).getSource().getType() == Node.GARBAGE_CONTAINER) {
							garbages.remove(resultArray.get(j).getSource());
						}
					}
					if (resultArray.get(j).getTarget().getType() == Node.GARBAGE_CONTAINER) {
						garbages.remove(resultArray.get(j).getTarget());
					}
				}
				t.setGarbagesToPass(garbages);
				i += e.getSource().getPathToDump().size();
				List<Edge> resultList = resultArray.subList(0, i);
				resultArray = new ArrayList<Edge>(resultList);
				Queue<Edge> tempResult = AStarAlgorithm.searchAStar(g,
						resultArray.get(resultArray.size() - 1).getTarget(), t);
				while (tempResult.size() > 0)
					resultArray.add(tempResult.poll());

			} else {
				if (e.getTarget().getType() == Node.GARBAGE_CONTAINER && t.getGarbagesToPass().contains(e.getTarget())) {
					t.setActualGarbage(t.getActualGarbage() + ProgramData.getInstance().getMultiple());
				}
			}
		}
		while (resultArray.size() > 0) {
			result.add(resultArray.get(0));
			resultArray.remove(0);
		}
		t.setGarbagesToPass(copyHash);
		return result;

	}

	public Queue<Edge> gasAnalyze(Queue<Edge> edges) {
		double fuel = this.t.getFuel();
		ArrayList<Node> copyHash = new ArrayList<Node>(t.getGarbagesToPass());
		Queue<Edge> result = new LinkedList<Edge>();
		ArrayList<Edge> resultArray = new ArrayList<Edge>();
		while (edges.size() > 0)
			resultArray.add(edges.poll());
		for (int i = 0; i < resultArray.size(); i++) {
			Edge e = resultArray.get(i);
				if (fuel -e.getWeight() <= e.getSource()
						.getDistanceToPetrolStation()) {
					for (int k = 0; k < e.getSource().getPathToPetrolStation()
							.size(); k++) {
						Edge e1 = e.getSource().getPathToPetrolStation().get(k);
						e1.setAddedGarbage(false);
						e1.setResetFuel(false);
						resultArray.add(i + k, e1);
						t.addFuelConsumption(e1.getWeight());
					}

					fuel = t.getFuel();
					ArrayList<Node> garbages = t.getGarbagesToPass();

					for (int j = 0; j <= i; j++) {
						if (j == 0) {
							if (resultArray.get(j).getSource().getType() == Node.GARBAGE_CONTAINER) {
								garbages.remove(resultArray.get(j).getSource());
							}
						}
						if (resultArray.get(j).getTarget().getType() == Node.GARBAGE_CONTAINER) {
							garbages.remove(resultArray.get(j).getTarget());
						}
					}
					System.out.println("Garbages=" + garbages);
					t.setGarbagesToPass(garbages);
					i += e.getSource().getPathToPetrolStation().size();
					List<Edge> resultList = resultArray.subList(0, i);
					resultArray = new ArrayList<Edge>(resultList);
					Queue<Edge> tempResult = AStarAlgorithm.searchAStar(g,
							resultArray.get(resultArray.size() - 1).getTarget(), t);
					tempResult=garbageAnalyze(tempResult);
					System.out.println("Result array fuel=" + tempResult);
					while (tempResult.size() > 0)
						resultArray.add(tempResult.poll());
					
			} else {
				t.addFuelConsumption(e.getWeight());
				fuel -= e.getWeight();
			}
		}
		while (resultArray.size() > 0) {
			result.add(resultArray.get(0));
			resultArray.remove(0);
		}
		t.setGarbagesToPass(copyHash);
		System.out.println("Teste");
		System.out.println(result);
		return result;
	}


}