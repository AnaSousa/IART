package graph;

/**
 * Created by João Nadais on 17/04/2014.
 */
public class AStarNode {
    private Node node;
    private AStarNode cameFrom;
    private double distanceSource;
    private double distanceTarget;

    public AStarNode(Node node, AStarNode cameFrom, double distanceSource, double distanceTarget) {
        this.node = node;
        this.cameFrom = cameFrom;
        this.distanceSource = distanceSource;
        this.distanceTarget = distanceTarget;
    }

    public AStarNode(Node node, double distanceSource, double distanceTarget) {
        this.node = node;
        this.distanceSource = distanceSource;
        this.distanceTarget = distanceTarget;
    }
    public int getId()
    {
        return this.node.getId();
    }

	/**
	 * @return the node
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * @param node the node to set
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
	 * @param cameFrom the cameFrom to set
	 */
	public void setCameFrom(AStarNode cameFrom) {
		this.cameFrom = cameFrom;
	}

	/**
	 * @return the distanceSource
	 */
	public double getDistanceSource() {
		return distanceSource;
	}

	/**
	 * @param distanceSource the distanceSource to set
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
	 * @param distanceTarget the distanceTarget to set
	 */
	public void setDistanceTarget(double distanceTarget) {
		this.distanceTarget = distanceTarget;
	}
}

