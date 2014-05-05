package logic;

import graph.Node;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Joao Nadais on 17/04/2014.
 */
public class AStarNode {
	private int id;
	private Node node;
	private AStarNode cameFrom;
	private HashSet<Integer> garbagesPassed;
	private int weight;
	private int distance;
	private double g;
	private double h;

	public AStarNode(int id,Node node, AStarNode cameFrom, double g,
			double h) {
		this.id=id;
		this.node = node;
		this.cameFrom = cameFrom;
		garbagesPassed= new HashSet<Integer>();
		this.distance=0;
		this.weight=0;
		this.g = g;
		this.h = h;
	}

	public int getId() {
		return id;
	}

	/**
	 * @return the node
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * @param node
	 *            the node to set
	 */
	public void setNode(Node node) {
		this.node = node;
	}

	/**
	 * @return the cameFrom
	 */
	public AStarNode getCameFrom() {
		
		return cameFrom;
	}

	/**
	 * @param cameFrom
	 *            the cameFrom to set
	 */
	public void setCameFrom(AStarNode cameFrom) {
		this.cameFrom=cameFrom;
	}

	/**
	 * @return the distanceSource
	 */
	public double getG() {
		return g;
	}

	/**
	 * @param g
	 *            the g to set
	 */
	public void setG(double g) {
		this.g = g;
	}

	/**
	 * @return the trucks left in iteration
	 */
	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int compareTo(AStarNode obj)
	{
		return Double.compare(this.getG()+this.getH(),obj.getG()+obj.getH());
	}

	/**
	 * @return the garbagesPassed
	 */
	public HashSet<Integer> getGarbagesPassed() {
		return garbagesPassed;
	}

	/**
	 * @param garbagesPassed the garbagesPassed to set
	 */
	public void setGarbagesPassed(HashSet<Integer> garbagesPassed) {
		this.garbagesPassed = garbagesPassed;
	}
	
	public void addGarbage(int garbage)
	{
		this.garbagesPassed.add(garbage);
	}
	public double calculateManhattanLeft()
	{
		ArrayList<Node> nodesLeft=ProgramData.getInstance().getTruck().getGarbagesPassed();
		for(Node n:nodesLeft)
		{
			if(this.garbagesPassed.contains(n.getId()))
				nodesLeft.remove(n);
		}
		double result=0.0;
		for(Node n:nodesLeft)
		{
			result+=ProgramData.getInstance().getGraph().calcManhattanDistance(this.getNode(), n);
		}
		return result;
	}
}
