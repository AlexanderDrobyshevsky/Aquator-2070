package ru.vermilion.inhabitants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Shell;

import ru.vermilion.world.AquatorSurface;
import ru.vermilion.basic.AquatorPlanetConfiguration;
import ru.vermilion.basic.AquatorPlanetHelper;
import ru.vermilion.basic.PlanetException;
import ru.vermilion.basic.PlanetPosition;

public class Shark extends AbstractInhabitant {
	
	private int maxHungerTime;
	
	// counter
	private int hungerTime;
	
	private int pregnantPeriod;
	
	private int maxReproductives;
	
	// counter
	private int pregnantTime;
	

	public Shark(AquatorSurface aquator, PlanetPosition position, 
			int lifeTime, int maxHungerTime, int pregnantPeriod, int maxReproductives, 
			int speed, int maxDepth) {
		super(aquator, position, lifeTime, speed, maxDepth);
		
		this.maxHungerTime = maxHungerTime;
		this.pregnantPeriod = pregnantPeriod;
		this.maxReproductives = maxReproductives;
	}

	public int getHungerTime() {
		return hungerTime;
	}

	public void setHungerTime(int hungerTime) {
		this.hungerTime = hungerTime;
	}

	private void doIterationBehavior1(GC gc, Shell shell, int[][] currentLand) {
		List<PlanetPosition> searchPositions = new ArrayList<PlanetPosition>(9);
		
		for (PlanetPosition pos : nearestPositions) {
			PlanetPosition nextPosition = position.addPosition(pos);
			PlanetPosition correctPosition = AquatorPlanetHelper.adjustPlanetPosition(nextPosition);
			
			if (isFishPersists(correctPosition, aquator)) {
				searchPositions.add(correctPosition);
			}
		}
		
		if (searchPositions.isEmpty()) {
			doSimpleMovement(gc, shell, currentLand);
			return;
		}
		
		Random randomGenerator = AquatorPlanetConfiguration.getRandomGenerator();
		int movement = randomGenerator.nextInt(searchPositions.size());
		
		PlanetPosition newPosition = searchPositions.get(movement);
		
		doEatFish(newPosition);
		
		setPosition(newPosition, gc, shell, currentLand); 
	}
	
	public void doIterationBehavior(GC gc, Shell shell, int[][] currentLand) {
		for (int i = 0; i < speed; i++) {
			doIterationBehavior1(gc, shell, currentLand);
		}
	}
	
	private boolean isFishPersists(PlanetPosition position, AquatorSurface aquator) {
		ConcurrentHashMap<Long, IInhabitant> cellInhabitants = aquator.getInhabitants(position.getY(), position.getX());
		
		for (Long id : cellInhabitants.keySet()) {
			IInhabitant entity = cellInhabitants.get(id);
			if (entity instanceof Fish) {
				return true;
			}
		}
		
		return false;
	}
	
	private void doEatFish(PlanetPosition position) {
		ConcurrentHashMap<Long, IInhabitant> cellInhabitants = aquator.getInhabitants(position.getY(), position.getX());
		
		Iterator<Long> iterator = cellInhabitants.keySet().iterator();
		while (iterator.hasNext()) {
			Long id = iterator.next();
			IInhabitant entity = cellInhabitants.get(id);
			
			if (entity instanceof Fish) {
				iterator.remove();
				hungerTime = 0;
				return;
			}
		}
		
		// TODO attach logs
		// TODO attach service tests
		throw new PlanetException("ERROR: No fish in this cell!!!");
	}
	
	public void nextIteration(GC gc, Shell shell, int[][] currentLand) {
		super.nextIteration();
		
		pregnantTime++;
		hungerTime++;
		int randomNumber = randomGenerator.nextInt(pregnantPeriod);
		// Algo was changed
		if (randomNumber == 1) {
			pregnantTime = 0;
		}
		
		if (pregnantTime == 0) {
			doReproduction();
		}
		
		randomNumber = randomGenerator.nextInt(getLifeTime());
		//if (randomNumber <= getAge() || hungerTime >= maxHungerTime) {
		if (randomNumber == 1 || hungerTime >= maxHungerTime) {
			//System.out.println("Shark #" + getId() + " died at age " + getAge());
			doRemove();
		}
		
		AquatorPlanetHelper.drawWorldPosition(gc, shell, currentLand, aquator, position);
	}

	public void doReproduction() {
		if (aquator.getSharkCount(position) >= maxDepth) {
			return;
		}
		
		int childrenCount = randomGenerator.nextInt(maxReproductives + 1);
		for (int i = 1; i <= childrenCount; i++) {
			Shark newShark = new Shark(aquator, position, getLifeTime(), maxHungerTime, pregnantPeriod, 
					maxReproductives, speed, maxDepth);
			aquator.addInhabitant(newShark);
		}
	}
}
