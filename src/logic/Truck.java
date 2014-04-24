package logic;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.util.Queue;

public class Truck {
	double weightCarried;
	double fuel;
	double capacity;
	
	/**
	 * @param weightCarried
	 * @param fuel
	 */
	public Truck(double fuel, double capacity) {
		this.weightCarried = 0;
		this.fuel = fuel;
		this.capacity=capacity;
	}
	/**
	 * @return the weightCarried
	 */
	public double getWeightCarried() {
		return weightCarried;
	}
	/**
	 * @param weightCarried the weightCarried to set
	 */
	public void setWeightCarried(double weightCarried) {
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
		return AStarAlgorithm.searchAStar(g, origin, destination);
	}

}
