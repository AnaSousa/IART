package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Node implements Comparable<Node> {

	public static final int SIMPLE_NODE = 0;
	public static final int CROSSROAD = 1;
	public static final int GARBAGE_CONTAINER = 2;
	public static final int DUMP = 3;
	public static final int PETROL_STATION = 4;
	public static final int MAX_INT = Integer.MAX_VALUE;
	
	private static int currentId=0;
	private int id;
    private Queue<Node> pathToPetrolStation;
	private ArrayList<Edge> adjacents;
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

    public ArrayList<Edge> getAdjacents() {
        return adjacents;
    }

    public Node(int type) {
		
		if(type!=SIMPLE_NODE && type!=CROSSROAD && type!=GARBAGE_CONTAINER && type!=DUMP && type!=PETROL_STATION) {
			System.out.println("Invalid type. Node not added");
			return;
		}
			
		id=currentId;
		currentId++;
		this.type=type;
        this.pathToPetrolStation = new LinkedList<Node>();
        adjacents=new ArrayList<Edge>(0);	}
	
	public Node(int type, int x, int y) {
		
		if(type<SIMPLE_NODE || type>PETROL_STATION) {
			System.out.println("Invalid type. Node not added");
			return;
		}
		
		id=currentId;
		currentId++;
		this.type=type;
		setPosition(x, y);
        this.pathToPetrolStation = new LinkedList<Node>();
        adjacents=new ArrayList<Edge>(0);
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

	public void setDistanceToStation(int newDistance,List<Edge> newPath) {
        if(newDistance<distanceToPetrolStation)
        {
            this.distanceToPetrolStation=newDistance;
            if(newPath!=null)
            {
            	LinkedList<Node> l = new LinkedList<Node>();
            	for(int i = 0;i<newPath.size();i++)
            	{
            		if(i==0)
            		l.add(newPath.get(i).getSource());
            		l.add(newPath.get(i).getTarget());
            	}
                this.pathToPetrolStation=l;
            }
            else this.pathToPetrolStation=null;
        }
	}

    public Queue<Node> getPathToPetrolStation() {
        return pathToPetrolStation;
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

    public void addAdjacent(Edge e)
    {
    	if(!adjacents.contains(e))
        this.adjacents.add(e);
    }
}
