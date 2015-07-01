package ru.vermilion.inhabitants;

import java.util.Random;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Shell;

import ru.vermilion.world.AquatorSurface;
import ru.vermilion.basic.AquatorPlanetConfiguration;
import ru.vermilion.basic.AquatorPlanetHelper;
import ru.vermilion.basic.PlanetPosition;
import ru.vermilion.basic.Sequences;

public abstract class AbstractInhabitant implements IInhabitant {

	private long id;
	
	// world position
	protected PlanetPosition position;
	
	// starts from 0; iterations of life.
	private int age;
	
	// how many iterations fish lives
	private int lifeTime;
	
	// how many times behavior repeats
	protected int speed;
	
	// how many entities can live/divide in one cell
	protected int maxDepth;
	
	protected AquatorSurface aquator;
	
	protected Random randomGenerator = AquatorPlanetConfiguration.getRandomGenerator();
	
	
	public AbstractInhabitant(AquatorSurface aquator, PlanetPosition position, int lifeTime, 
			int speed, int maxDepth) {
		this.id = Sequences.getNewInhabitantId();
		this.lifeTime = lifeTime;
		this.position = AquatorPlanetHelper.adjustPlanetPosition(position);
		this.aquator = aquator;
		this.speed = speed;
		this.maxDepth = maxDepth;
	}
	
	protected static final PlanetPosition[] nearestPositions = 
		new PlanetPosition[] {
			new PlanetPosition(-1,-1),
			new PlanetPosition(0,-1),
			new PlanetPosition(+1,-1),
			new PlanetPosition(-1,0),
			new PlanetPosition(0,0),
			new PlanetPosition(+1,0),
			new PlanetPosition(-1,+1),
			new PlanetPosition(0,+1),
			new PlanetPosition(+1,+1)
		};
	
	public PlanetPosition getPosition() {
		return position;
	}
	
	public void setPosition(PlanetPosition newPosition, GC gc, Shell shell, int[][] currentLand) {
		newPosition = AquatorPlanetHelper.adjustPlanetPosition(newPosition);
		
		PlanetPosition oldPosition = this.position;

		if (!this.position.equals(newPosition)) {
			aquator.removeInhabitant(this);
			aquator.getInhabitants(newPosition).put(id, this);

			this.position = newPosition;
			
			AquatorPlanetHelper.drawWorldPosition(gc, shell, currentLand, aquator, oldPosition);
			AquatorPlanetHelper.drawWorldPosition(gc, shell, currentLand, aquator, newPosition);
		}
	}
	
	public int getAge() {
		return age;
	}

	public long getId() {
		return id;
	}
	
	public int getLifeTime() {
		return lifeTime;
	}

	public void nextIteration() {
		age++;
	}
	
	protected void doSimpleMovement(GC gc, Shell shell, int[][] currentLand) {
		Random randomGenerator = AquatorPlanetConfiguration.getRandomGenerator();
		int move = randomGenerator.nextInt(9);
		
		PlanetPosition newPosition = position.addPosition(nearestPositions[move]);
		
		setPosition(AquatorPlanetHelper.adjustPlanetPosition(newPosition), gc, shell, currentLand);
	}
	
	public void doRemove() {
		aquator.removeInhabitant(this);
	}
	
}