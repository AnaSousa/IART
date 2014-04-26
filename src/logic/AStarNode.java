package logic;

import graph.Node;

import java.util.ArrayList;

/**
 * Created by Joao Nadais on 17/04/2014.
 */
public class AStarNode {
	private Node node;
	private ArrayList<AStarNode> cameFrom;
	private int weight;
	private int distance;
	private double g;
	private double h;
	private int cameFromIndex;

	public AStarNode(Node node, AStarNode cameFrom, double g,
			double h) {
		this.node = node;
		this.cameFrom = new ArrayList<AStarNode>(0);
		cameFromIndex = -1;
		this.cameFrom.add(cameFrom);
		this.weight=1;
		this.distance=0;
		this.g = g;
		this.h = h;
	}

	public AStarNode(Node node, int weightOrigin, int distanceOrigin,double h) {
		this.node = node;
		this.cameFrom = new ArrayList<AStarNode>(0);
		cameFromIndex = -1;
		this.weight=weightOrigin;
		this.distance=distanceOrigin;
		this.g = (double) distance/weight;
		this.h = h;
	}

	public int getId() {
		return this.node.getId();
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
		cameFromIndex++;
		if(cameFrom.size()==0)
			return null;
		else return cameFrom.get(cameFromIndex);
	}

	/**
	 * @param cameFrom
	 *            the cameFrom to set
	 */
	public void setCameFrom(AStarNode cameFrom) {
		this.cameFrom.add(cameFrom);
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
}
