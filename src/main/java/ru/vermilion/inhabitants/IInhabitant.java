package ru.vermilion.inhabitants;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Shell;

import ru.vermilion.basic.PlanetPosition;

public interface IInhabitant  {
	
	public long getId();

	public PlanetPosition getPosition();

	public void setPosition(PlanetPosition position, GC gc, Shell shell, int[][] currentLand);

	public void doIterationBehavior(GC gc, Shell shell, int[][] currentLand);
	
	public void nextIteration(GC gc, Shell shell, int[][] currentLand);
}
