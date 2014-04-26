package logic;

import graph.Node;

import java.util.ArrayList;

/**
 * Created by Joao Nadais on 17/04/2014.
 */
public class AStarNode {
	private Node node;
	private ArrayList<AStarNode> cameFrom;
	private int g;
	private int h;
	private int cameFromIndex;

	public AStarNode(Node node, AStarNode cameFrom, int g,
			int h) {
		this.node = node;
		this.cameFrom = new ArrayList<AStarNode>(0);
		cameFromIndex = -1;
		this.cameFrom.add(cameFrom);
		this.g = g;
		this.h = h;
	}

	public AStarNode(Node node, int g, int h) {
		this.node = node;
		this.cameFrom = new ArrayList<AStarNode>(0);
		cameFromIndex = -1;
		this.g = g;
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
	public int getG() {
		return g;
	}

	/**
	 * @param g
	 *            the g to set
	 */
	public void setG(int g) {
		this.g = g;
	}

	/**
	 * @return the trucks left in iteration
	 */
	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
	public int compareTo(AStarNode obj)
	{
		return Integer.compare(this.getG()+this.getH(),obj.getG()+obj.getH());
	}
}
