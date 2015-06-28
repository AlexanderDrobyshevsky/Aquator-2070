package ru.vermilion.model;

import java.util.ArrayList;
import java.util.List;


// TODO: Iterations Count is Long , not Int !!! 
// Another, more difficult aproach is needed!
public class EmpiricGraphicData {

	private List<Integer> fishes = new ArrayList<Integer>();
	
	private List<Integer> sharks = new ArrayList<Integer>();
	
	private static EmpiricGraphicData instance = new EmpiricGraphicData();
	
	public static EmpiricGraphicData getInstance() {
		return instance;
	}
	
	public synchronized void add(int fishedCount, int sharksCount) {
		fishes.add(fishedCount);
		sharks.add(sharksCount);
	}
	
	public int getFishesCount(int iteration) {
		if (fishes.size() - 1 < iteration) {
			throw new RuntimeException("Invalid Iteration EmpireGraphicData!");
		}
		
		return fishes.get(iteration);
	}
	
	public int getSharksCount(int iteration) {
		if (fishes.size() - 1 < iteration) {
			throw new RuntimeException("Invalid Iteration EmpireGraphicData!");
		}
		
		return sharks.get(iteration);
	}
	
	public int getIterationsCount() {
		return fishes.size();
	}
	
}