package logic;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.jgrapht.graph.DefaultEdge;

public class ProgramData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Graph g;
	private Truck t;
	private int multiple;
	static ProgramData data;
	private ArrayList<Integer> garbagesOrder;
	private ArrayList<Integer> fuelIndex;
	private ArrayList<Integer> garbagesIndex;
	private int actualGarbageIndex;
	private int actualIndex;

	/**
	 * @param g
	 * @param t
	 */
	private ProgramData() {
		Class<? extends DefaultEdge> edgeClass = null;
		this.g = new Graph(edgeClass);
		this.t = new Truck(500, 200);
		this.multiple = 1;
		this.actualIndex=0;
		garbagesOrder = new ArrayList<Integer>();
		this.fuelIndex = new ArrayList<Integer>();
		this.garbagesIndex = new ArrayList<Integer>();
		this.actualGarbageIndex++;
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

	public static void deleteInstance() {
		Node.resetId();
		data = null;
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
		this.garbagesIndexes(e);
		System.out.println("Fuel index="+this.fuelIndex);
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
				if (e.getTarget().getType() == Node.GARBAGE_CONTAINER
						&& t.getGarbagesToPass().contains(e.getTarget())) {
					this.actualGarbageIndex++;
					this.garbagesOrder.add(e.getTarget().getId());
					t.setActualGarbage(t.getActualGarbage()
							+ ProgramData.getInstance().getMultiple());
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
			if ((fuel - this.calculateDistanceToNextGarbage(resultArray, i)) <= e
					.getSource().getDistanceToPetrolStation()) {
				for (int k = 0; k < e.getSource().getPathToPetrolStation()
						.size(); k++) {
					Edge e1 = e.getSource().getPathToPetrolStation().get(k);
					if (k == 0)
						e1.setAddedGarbage(true);
					else
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
				this.fuelIndex.add(resultArray.size());
				Queue<Edge> tempResult = AStarAlgorithm.searchAStar(g,
						resultArray.get(resultArray.size() - 1).getTarget(), t);
				tempResult = garbageAnalyze(tempResult);
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

	public double calculateDistanceToNextGarbage(ArrayList<Edge> actualGarbage,
			int actualIndex) {

		double result = 0.0;
		int actualGarbageOrder = (actualGarbageIndex > garbagesOrder.size() ? -1
				: this.garbagesOrder.get(actualGarbageIndex));
		for (; actualIndex < actualGarbage.size(); actualIndex++) {
			result += actualGarbage.get(actualIndex).getWeight();
			if (actualGarbageOrder == -1) {
				if (actualGarbage.get(actualIndex).getTarget().getType() == Node.DUMP)
					break;
			} else if (actualGarbage.get(actualIndex).getTarget().getType() == Node.GARBAGE_CONTAINER
					&& actualGarbage.get(actualIndex).getTarget().getId() == this.garbagesOrder
					.get(actualGarbageIndex))
				break;
		}
		return result;
	}

	public boolean isFuelIndex()
	{
		System.out.println("Fuel index=" + this.fuelIndex+";Actual Index="+this.actualIndex);
		return this.fuelIndex.contains(this.actualIndex);
	}
	public boolean isGarbageIndex()
	{
		System.out.println("Garbage index=" + this.garbagesIndex+";Actual Index="+this.actualIndex);
		return this.garbagesIndex.contains(this.actualIndex);
	}

	public void garbagesIndexes(Queue<Edge> path)
	{
		ArrayList<Integer> garbagesPassedIteration = new ArrayList<Integer>();
		int pathInt;
		int garbage=0;
		ArrayList<Edge> pathList = new ArrayList<Edge>();
		while(path.size()>0)
			pathList.add(path.poll());
		for(pathInt=0;pathInt<pathList.size();pathInt++)
		{
			if(garbage<this.t.getCapacity())
			{
				if(pathList.get(pathInt).getSource().getType()==Node.GARBAGE_CONTAINER && !garbagesPassedIteration.contains(pathList.get(pathInt).getSource().getId()))
				{
					garbage+=this.multiple;
					this.garbagesIndex.add(pathInt);
					garbagesPassedIteration.add(pathList.get(pathInt).getSource().getId());
				}
			}
			else
			{
				if(pathList.get(pathInt).getSource().getType()==Node.DUMP)
				{
					garbage=0;
				}
			}
		}
		while(pathList.size()>0)
		{
			path.add(pathList.get(0));
			pathList.remove(0);
		}
	}

	/**
	 * @return the actualIndex
	 */
	public int getActualIndex() {
		return actualIndex;
	}

	/**
	 * @param actualIndex the actualIndex to set
	 */
	public void setActualIndex(int actualIndex) {
		this.actualIndex = actualIndex;
	}

}