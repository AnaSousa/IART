package graph;

public class Node {

	private static int currentId=0;
	private int id;
	
	/*
	 * 0 - nao tem nada; 1 - cruzamento;
	 * 2 - contentor; 3 - lixeira; 5 - bomba gasolina
	*/
	private int type=0;
	
	private int distanceToDump=Integer.MAX_VALUE;
	private int distanceToPetrolStation=Integer.MAX_VALUE;
	
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
		id=currentId;
		currentId++;
		this.type=type;
	}
	
	public Node(int type, int x, int y) {
		id=currentId;
		currentId++;
		this.type=type;
		setPosition(x, y);
	}
	
	public int getIntegerId() {
		return id;
	}
	
	public String getId() {
		return new Integer(id).toString();
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
		distanceToDump=newDistance;
	}
	
	public void setDistanceToStation(int newDistance) {
		distanceToPetrolStation=newDistance;
	}
}
