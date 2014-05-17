package logic;

import graph.Node;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Joao Nadais on 17/04/2014.
 */
public class AStarNode {
	private Node node;
	private AStarNode cameFrom;
	private HashSet<Integer> garbagesPassed;
	private int weight;
	private int distance;
	private double fuelSpent;
	private double g;
	private double h;

	/**
	 * @return the garbagesPassed
	 */
	public HashSet<Integer> getGarbagesPassed() {
		return garbagesPassed;
	}
	public AStarNode(Node node, AStarNode cameFrom, double g,
			double h) {
		this.node = node;
		this.cameFrom = cameFrom;
		this.weight=1;
		this.distance=0;
		this.g = g;
		this.h = h;
		this.fuelSpent=0;
		this.garbagesPassed=new HashSet<Integer>();
	}

	public AStarNode(Node node, int weightOrigin, int distanceOrigin,double h) {
		this.node = node;
		this.cameFrom = null;
		this.weight=weightOrigin;
		this.distance=distanceOrigin;
		this.g = (double) distance-weight;
		this.h = h;
		this.fuelSpent=0;
		this.garbagesPassed=new HashSet<Integer>();
	}

	public AStarNode(Node neighbor, int weight, int distance) 
	{
		this.node=neighbor;
		this.cameFrom=null;
		this.weight=weight;
		this.distance=distance;
		this.fuelSpent=0;
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
	
	public void addGarbage(int garbage)
	{
		this.garbagesPassed.add(garbage);
	}

	/**
	 * @param garbagesPassed the garbagesPassed to set
	 */
	public void setGarbagesPassed(HashSet<Integer> garbagesPassed) {
		this.garbagesPassed = garbagesPassed;
	}
	
	/**
	 * @return the fuelSpent
	 */
	public double getFuelSpent() {
		return fuelSpent;
	}
	/**
	 * @param d the fuelSpent to set
	 */
	public void setFuelSpent(double d) {
		this.fuelSpent = d;
	}
	public final void recalculateGarbagesPassed(int total,double manhattanLeft)
	{
		ArrayList<Node> path = new ArrayList<Node>();
		AStarNode test = this;
		while(test!=null)
		{
			path.add(test.getNode());
			test=test.getCameFrom();
		}
		this.garbagesPassed=new HashSet<Integer>();
		for(Node n : path)
		{
			if(n.getType()==Node.GARBAGE_CONTAINER && !garbagesPassed.contains(n))
				garbagesPassed.add(n.getId());
		}
		g=distance/(garbagesPassed.size()==0 ? 1 : garbagesPassed.size()*100);
		this.h=((total-garbagesPassed.size())*1000);
	}
}

