package logic;

import graph.Node;

import java.util.ArrayList;

/**
 * Created by João Nadais on 17/04/2014.
 */
public class AStarNode {
	private Node node;
	private ArrayList<AStarNode> cameFrom;
	private double distanceSource;
	private double distanceTarget;
	private int cameFromIndex;

	public AStarNode(Node node, AStarNode cameFrom, double distanceSource,
			double distanceTarget) {
		this.node = node;
		this.cameFrom = new ArrayList<AStarNode>(0);
		cameFromIndex = -1;
		this.cameFrom.add(cameFrom);
		this.distanceSource = distanceSource;
		this.distanceTarget = distanceTarget;
	}

	public AStarNode(Node node, double distanceSource, double distanceTarget) {
		this.node = node;
		this.cameFrom = new ArrayList<AStarNode>(0);
		cameFromIndex = -1;
		this.distanceSource = distanceSource;
		this.distanceTarget = distanceTarget;
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
	public double getDistanceSource() {
		return distanceSource;
	}

	/**
	 * @param distanceSource
	 *            the distanceSource to set
	 */
	public void setDistanceSource(double distanceSource) {
		this.distanceSource = distanceSource;
	}

	/**
	 * @return the distanceTarget
	 */
	public double getDistanceTarget() {
		return distanceTarget;
	}

	/**
	 * @param distanceTarget
	 *            the distanceTarget to set
	 */
	public void setDistanceTarget(double distanceTarget) {
		this.distanceTarget = distanceTarget;
	}
}
