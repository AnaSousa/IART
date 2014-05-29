package graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node>, Serializable{

	public static final int SIMPLE_NODE = 0;
	public static final int CROSSROAD = 1;
	public static final int GARBAGE_CONTAINER = 2;
	public static final int DUMP = 3;
	public static final int PETROL_STATION = 4;
	public static final int MAX_INT = Integer.MAX_VALUE;
	
	private static int currentId=0;
	private int id;
    private ArrayList<Edge> pathToPetrolStation;
    private ArrayList<Edge> pathToDump;
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
        this.pathToPetrolStation = new ArrayList<Edge>();
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
		this.distanceToDump=Integer.MAX_VALUE;
		this.distanceToPetrolStation=Integer.MAX_VALUE;
        this.pathToPetrolStation = new ArrayList<Edge>();
        this.pathToDump=new ArrayList<Edge>();
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

	

	public void setDistanceToStation(int newDistance,List<Edge> newPath) {
        if(newDistance<distanceToPetrolStation)
        {
        	System.out.println("Station: " + newPath);
            this.distanceToPetrolStation= newDistance;
            if(newPath!=null)
            {
            	ArrayList<Edge> l = new ArrayList<Edge>();
            	for(int i = 0;i<newPath.size();i++)
            	{            		
            		l.add(newPath.get(i));
            	}
                this.pathToPetrolStation=l;
            }
            else this.pathToPetrolStation=null;
        }
	}
	public void setDistanceToDump(int newDistance,List<Edge> newPath) {
        if(newDistance<distanceToDump)
        {
        	System.out.println("Dump: " + newPath);
        	distanceToDump=newDistance;
            if(newPath!=null)
            {
            	ArrayList<Edge> l = new ArrayList<Edge>();
            	for(int i = 0;i<newPath.size();i++)
            	{            		
            		l.add(newPath.get(i));
            	}
                this.pathToDump=l;
            }
            else this.pathToDump=null;
        }
	}

    public ArrayList<Edge> getPathToPetrolStation() {
        return pathToPetrolStation;
    }


    @Override
	public boolean equals(Object arg0) {
		Node x = (Node) arg0;
		if(x.getId() == id)
			return true;

		return false;
	}

	public int compareTo(Node arg0) {
		return Integer.compare(id, arg0.getId());
	}
	
	public static void resetId() {
		currentId = 0;
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

	/**
	 * @return the pathToDump
	 */
	public ArrayList<Edge> getPathToDump() {
		return pathToDump;
	}

	/**
	 * @param pathToDump the pathToDump to set
	 */
	public void setPathToDump(ArrayList<Edge> pathToDump) {
		this.pathToDump = pathToDump;
	}
}
