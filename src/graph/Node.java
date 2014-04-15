package graph;

public class Node implements Comparable<Node> {

	public static final int SIMPLE_NODE = 0;
	public static final int CROSSROAD = 1;
	public static final int GARBAGE_CONTAINER = 2;
	public static final int DUMP = 3;
	public static final int PETROL_STATION = 4;
	public static final int MAX_INT = Integer.MAX_VALUE;
	
	private static int currentId=0;
	private int id;
	
	/*
	 * 0 - nao tem nada; 1 - cruzamento;
	 * 2 - contentor; 3 - lixeira; 4 - bomba gasolina
	*/
	private int type=SIMPLE_NODE;
	
	private int distanceToDump=MAX_INT;
	private int distanceToPetrolStation=MAX_INT;
	
	private int posX;
	private int posY;
	
	public Node() {
		id=currentId;
		currentId++;
	}
	
	public Node(int x, int y) {
		id=currentId;
		currentId++;
		setPosition(x, y);
	}
	
	public Node(int type) {
		
		if(type!=SIMPLE_NODE && type!=CROSSROAD && type!=GARBAGE_CONTAINER && type!=DUMP && type!=PETROL_STATION) {
			System.out.println("Invalid type. Node not added");
			return;
		}
			
		id=currentId;
		currentId++;
		this.type=type;
	}
	
	public Node(int type, int x, int y) {
		
		if(type<SIMPLE_NODE || type>PETROL_STATION) {
			System.out.println("Invalid type. Node not added");
			return;
		}
		
		id=currentId;
		currentId++;
		this.type=type;
		setPosition(x, y);
	}
	
	public int getIntegerId() {
		return id;
	}
	
	public int getId() {
		return id;
	}
	
	public int getType() {
		return type;
	}
	
	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}
	
	public void setPosition(int x, int y) {
		posX=x;
		posY=y;
	}

	public int getDistanceToDump() {
		return distanceToDump;
	}
	
	public int getDistanceToPetrolStation() {
		return distanceToPetrolStation;
	}
	
	public void setDistanceToDump(int newDistance) {
		distanceToDump = newDistance < distanceToDump ? newDistance : distanceToDump;
	}
	
	public void setDistanceToStation(int newDistance) {
		distanceToPetrolStation = newDistance < distanceToPetrolStation ? newDistance : distanceToPetrolStation;
	}

	@Override
	public boolean equals(Object arg0) {
		Node x = (Node) arg0;
		if(x.getId() == id)
			return true;
		
		return false;
	}

	@Override
	public int compareTo(Node arg0) {
		return Integer.compare(id, arg0.getId());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  id + "";
	}
}