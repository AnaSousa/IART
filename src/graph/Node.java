package graph;

public class Node {

	final int SIMPLE_NODE = 0;
	final int CROSSROAD = 1;
	final int GARBAGE_CONTAINER = 2;
	final int DUMP = 3;
	final int PETROL_STATION = 4;
	final int MAX_INT = Integer.MAX_VALUE;
	
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
		
		if(type!=SIMPLE_NODE || type!=CROSSROAD || type!=GARBAGE_CONTAINER || type!=DUMP || type!=PETROL_STATION) {
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
