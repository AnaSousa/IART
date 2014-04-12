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
	
	public Node() {
		id=currentId;
		currentId++;
	}
	
	public Node(int type) {
		id=currentId;
		currentId++;
		this.type=type;
	}
	
	public int getId() {
		return id;
	}
	
	public String getStringId() {
		return new Integer(id).toString();
	}
	
	public int getType() {
		return type;
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
