package logic;

import graph.Node;

import java.util.ArrayList;

public class Truck {
	int weightCarried;
	int distancePassed;
	double fuel;
	double capacity;
	ArrayList<Node> garbagesPassed;
	/**
	 * @param weightCarried
	 * @param fuel
	 */
	public Truck(double fuel, double capacity) {
		this.weightCarried = 0;
		this.distancePassed=0;
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
	/**
	 * @return the distancePassed
	 */
	public int getDistancePassed() {
		return distancePassed;
	}
	/**
	 * @param distancePassed the distancePassed to set
	 */
	public void setDistancePassed(int distancePassed) {
		this.distancePassed = distancePassed;
	}

}
