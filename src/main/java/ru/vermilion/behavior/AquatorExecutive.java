package ru.vermilion.behavior;

import ru.vermilion.inhabitants.Fish;
import ru.vermilion.inhabitants.IInhabitant;
import ru.vermilion.inhabitants.Shark;

import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Shell;

import ru.vermilion.world.AquatorSurface;
import ru.vermilion.basic.AquatorPlanetConfiguration;
import ru.vermilion.basic.PlanetPosition;

// -Xbatch -Xms256m -Xmx1024m -Xint
public class AquatorExecutive {

	private AquatorSurface aquatorSurface;
	 
	private long lastRedrewIteration;

	public AquatorExecutive() {
		aquatorSurface = new AquatorSurface(AquatorPlanetConfiguration.getHeight(),
				AquatorPlanetConfiguration.getWidth(), AquatorPlanetConfiguration.getDepth());
	}
	
	public void setLastRedrewIteration(long cnt) {
		lastRedrewIteration = cnt;
	}
	
	public class IterationLateData {
		int fishesCount;
		int sharksCount;
		
		long cnt;

		public IterationLateData(long cnt, int fishesCount, int sharksCount) {
			this.fishesCount = fishesCount;
			this.sharksCount = sharksCount;
			this.cnt = cnt;
		}

		public int getFishesCount() {
			return fishesCount;
		}

		public int getSharksCount() {
			return sharksCount;
		}

		public long getCnt() {
			return cnt;
		}
	}
	
	// Data constructs for previous period
	public IterationLateData nextIteration(GC gc, Shell shell, int[][] currentLand) {
		long currentIter = aquatorSurface.getIter();
		if (lastRedrewIteration != currentIter) {
			System.err.println("Error ### : (lastRedrewIteration != currentIter)");
			return null;
		}
		
		int fishCount = 0, sharkCount = 0;

		ConcurrentHashMap<Long, IInhabitant> allInhabitants = aquatorSurface.getAllInhabitants();
		for (Long id : allInhabitants.keySet()) {
			IInhabitant entity = allInhabitants.get(id);

			if (entity instanceof Fish) {
				fishCount++;
			}

			if (entity instanceof Shark) {
				sharkCount++;
			}

			if (aquatorSurface.getInhabitants(entity.getPosition()).containsKey(id)) {
				entity.doIterationBehavior(gc, shell, currentLand);
				entity.nextIteration(gc, shell, currentLand);
			} else {
				allInhabitants.remove(id);
			}
		}

		return new IterationLateData(aquatorSurface.incIter(), fishCount, sharkCount);
	}
	
	// TODO
	public void initFishes(int count, PlanetPosition position, int lifeTime,
			int maxReproductives, int pregnantPeriod, int speed, int maxDepth) {
		for (int i = 0; i < count; i++) {
			Fish fish = new Fish(aquatorSurface, new PlanetPosition(100, 100), lifeTime,
					maxReproductives, pregnantPeriod, speed, maxDepth);

			aquatorSurface.addInhabitant(fish);
		}
	}

	public void initSharks(int count, PlanetPosition position, int lifeTime,
			int maxHungerTime, int pregnantPeriod, int maxReproductives, int speed, int maxDepth) {
		
		for (int i = 0; i < count; i++) {
			Shark shark = new Shark(aquatorSurface, new PlanetPosition(110, 110), lifeTime,
					maxHungerTime, pregnantPeriod, maxReproductives, speed, maxDepth);
			
			aquatorSurface.addInhabitant(shark);
		}
	}


	public AquatorSurface getAquator() {
		return aquatorSurface;
	}
	
	public long getCurrentIteration() {
		return aquatorSurface.getIter();
	}
	
}
