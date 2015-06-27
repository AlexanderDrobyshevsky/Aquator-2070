package ru.vermilion.inhabitants;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Shell;

import ru.vermilion.world.AquatorSurface;
import ru.vermilion.basic.AquatorPlanetConfiguration;
import ru.vermilion.basic.AquatorPlanetHelper;
import ru.vermilion.basic.PlanetPosition;

public class Fish extends AbstractInhabitant  {
	
	// max borning children
	private int maxReproductives;
	
	// 
	private int pregnantPeriod;
	
	// counter
	private int pregnantTime;
	
	
	public Fish(AquatorSurface aquator, PlanetPosition position, int lifeTime, int maxReproductives, 
			int pregnantPeriod, int speed, int maxDepth) {
		super(aquator, position, lifeTime, speed, maxDepth);
		
		this.maxReproductives = maxReproductives;
		this.pregnantPeriod = pregnantPeriod;
	}


	
	public void doIterationBehavior(GC gc, Shell shell, int[][] currentLand) {
		for (int i = 0; i < speed; i++) {
			doSimpleMovement(gc, shell, currentLand);
		}
	}
	
	public void nextIteration(GC gc, Shell shell, int[][] currentLand) {
		super.nextIteration();
		
		pregnantTime++;
		int randomNumber = randomGenerator.nextInt(pregnantPeriod);
		// Algo was changed
		if (randomNumber == 1) {
			pregnantTime = 0;
		}
		
		if (pregnantTime == 0) {
			doReproduction();
		}
		
		randomNumber = randomGenerator.nextInt(getLifeTime());
		// Algo was changed
		if (randomNumber == 1) {
			doRemove();
			
			// check
			ConcurrentHashMap<Long, IInhabitant> inhs = aquator.getInhabitants(position);
			IInhabitant inh = inhs.get(getId());
			if (inh != null) {
				System.err.println("Error ## : (inh != null) after remove");
			}
		}
		
		AquatorPlanetHelper.drawWorldPosition(gc, shell, currentLand, aquator, position);
	}
	
	public void doReproduction() {
		if (aquator.getFishCount(position) >= maxDepth) {
			return;
		}
		
		int childrenCount = randomGenerator.nextInt(maxReproductives + 1);
		for (int i = 1; i <= childrenCount; i++) {
			Fish newFish = new Fish(aquator, position, getLifeTime(), maxReproductives, pregnantPeriod, speed, 
					maxDepth);
			aquator.addInhabitant(newFish);
		}
	}
	
}
