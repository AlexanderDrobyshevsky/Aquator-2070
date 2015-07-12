package ru.vermilion.world;

import ru.vermilion.inhabitants.Fish;
import ru.vermilion.inhabitants.IInhabitant;
import ru.vermilion.inhabitants.Shark;

import java.util.concurrent.ConcurrentHashMap;

import ru.vermilion.basic.PlanetException;
import ru.vermilion.basic.PlanetPosition;

public class AquatorSurface {
	// y, x
	private ConcurrentHashMap<Long, IInhabitant>[][] aquator;
	
	private int width;
	
	private int  height;
	
	private int depth;
	
	private long cnt;
	
	public AquatorSurface(int height, int width, int depth)  {
		aquator = new ConcurrentHashMap[height][width];
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				aquator[y][x] = new ConcurrentHashMap<Long, IInhabitant>();
			}
		}
		
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.cnt = 0;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public ConcurrentHashMap<Long, IInhabitant> getInhabitants(int y, int x) {
		return aquator[y][x];
	}
	
	private static final ConcurrentHashMap<Long, IInhabitant> allInhabitants = 
		new ConcurrentHashMap<Long, IInhabitant>(20000);
	private static final ConcurrentHashMap<Long, IInhabitant> newInhabitants = 
		new ConcurrentHashMap<Long, IInhabitant>(3000);
	public ConcurrentHashMap<Long, IInhabitant> getAllInhabitants() {
		//allInhabitants.clear();
		
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
//				allInhabitants.putAll(getInhabitants(y, x));
//			}
//		}
		
		allInhabitants.putAll(newInhabitants);
		newInhabitants.clear();

		return allInhabitants;
	}
	
	public ConcurrentHashMap<Long, IInhabitant> getInhabitants(PlanetPosition position) {
		return aquator[position.getY()][position.getX()];
	}	
	
	public void removeInhabitant(IInhabitant entity) {
		PlanetPosition position = entity.getPosition();
		Object removedInh = aquator[position.getY()][position.getX()].remove(entity.getId());
		if (removedInh == null) {
			throw new PlanetException("Error removing entity: No entity to remove!!!");
		}
	}
	
	public int getFishCount(PlanetPosition position) {
		ConcurrentHashMap<Long, IInhabitant> inhs = getInhabitants(position);
		
		int count = 0;
		for (Long id : inhs.keySet()) {
			IInhabitant inh = inhs.get(id);
			
			if (inh instanceof Fish) {
				count++;
			}
		}
		
		return count;
	}
	
	public int getSharkCount(PlanetPosition position) {
		ConcurrentHashMap<Long, IInhabitant> inhs = getInhabitants(position);
		
		int count = 0;
		for (Long id : inhs.keySet()) {
			IInhabitant inh = inhs.get(id);
			
			if (inh instanceof Shark) {
				count++;
			}
		}
		
		return count;
	}
	
	public void addInhabitant(IInhabitant entity) {
		ConcurrentHashMap<Long, IInhabitant> cellInhabitants = getInhabitants(entity.getPosition());
		
		// In this case this Inh does not appear in current iteration through aquatorSurface.getAllInhabitants().keySet();
		cellInhabitants.put(entity.getId(), entity);
		
		// Why We use newInhabitants - I dont remember ((;
		newInhabitants.put(entity.getId(), entity);
	}
	
	public boolean isEmpty(PlanetPosition position) {
		return getInhabitants(position).isEmpty();
	}
	
	public boolean isEmpty(int y, int x) {
		return getInhabitants(y, x).isEmpty();
	}
	
	public long incIter() {
		cnt++;
		return cnt;
	}
	
	public long getIter() {
		return cnt;
	}
}
