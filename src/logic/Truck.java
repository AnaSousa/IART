package logic;

import graph.Node;

import java.util.ArrayList;

public class Truck {
	int weightCarried;
	int distancePassed;
	double fuel;
	ArrayList<Double> fuelInPath;
	double capacity;
	ArrayList<Node> garbagesToPass;
	/**
	 * @param weightCarried
	 * @param fuel
	 */
	public Truck(double fuel, double capacity) {
		this.weightCarried = 0;
		this.distancePassed=0;
		this.fuel = fuel;
		this.capacity=capacity;
		this.garbagesToPass=new ArrayList<Node>();
		fuelInPath=new ArrayList<Double>();
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
	public ArrayList<Node> getGarbagesToPass() {
		return garbagesToPass;
	}
	/**
	 * @param garbagesToPass the garbagesToPass to set
	 */
	public void setGarbagesToPass(ArrayList<Node> garbagesToPass) {
		this.garbagesToPass = garbagesToPass;
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
	/**
	 * @return the fuelInPath
	 */
	public ArrayList<Double> getFuelInPath() {
		return fuelInPath;
	}
	/**
	 * @param fuelInPath the fuelInPath to set
	 */
	public void setFuelInPath(ArrayList<Double> fuelInPath) {
		this.fuelInPath = fuelInPath;
	}
	
	public void addFuelConsumption(double fuel)
	{
		this.fuelInPath.add(fuel);
	}
	

}
