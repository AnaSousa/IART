package graph;

import java.util.Comparator;

public class AStarNodeComparator implements Comparator<AStarNode> {

	@Override
	public int compare(AStarNode arg0, AStarNode arg1) {
			return arg0.getNode().compareTo(arg1.getNode());
		
	}

}
