package logic;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;

public class Truck {
	int weightCarried;
	double fuel;
	double capacity;
	ArrayList<Node> garbagesPassed;
	/**
	 * @param weightCarried
	 * @param fuel
	 */
	public Truck(double fuel, double capacity) {
		this.weightCarried = 0;
		this.fuel = fuel;
		this.capacity=capacity;
		this.garbagesPassed=new ArrayList<Node>();
	}
	/**
	 * @return the weightCarried
	 */
	public int getWeightCarried() {
		return weightCarried;
	}
	/**
	 * @param weightCarried the weightCarried to set
	 */
	public void setWeightCarried(int weightCarried) {
		this.weightCarried = weightCarried;
	}
	/**
	 * @return the fuel
	 */
	public double getFuel() {
		return fuel;
	}
	/**
	 * @param fuel the fuel to set
	 */
	public void setFuel(double fuel) {
		this.fuel = fuel;
	}
	
	/**
	 * @return the capacity
	 */
	public double getCapacity() {
		return capacity;
	}
	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	public Queue<Edge> searchPath(Graph g,Node origin, Node destination)
	{
		
		for (Map.Entry<Integer, Node> entry : g.getNodes().entrySet()) {
			Node tmp = entry.getValue();
			if(tmp.getId()==Node.GARBAGE_CONTAINER)
				this.garbagesPassed.add(tmp);
		}
		return AStarAlgorithm.searchAStar(g, origin, destination,this);
	}
	/**
	 * @return the garbagesPassed
	 */
	public ArrayList<Node> getGarbagesPassed() {
		return garbagesPassed;
	}
	/**
	 * @param garbagesPassed the garbagesPassed to set
	 */
	public void setGarbagesPassed(ArrayList<Node> garbagesPassed) {
		this.garbagesPassed = garbagesPassed;
	}

}
