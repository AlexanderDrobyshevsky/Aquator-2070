package ru.vermilion.basic;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Shell;

import ru.vermilion.world.AquatorSurface;
import ru.vermilion.inhabitants.IInhabitant;

public class AquatorPlanetHelper {
	private static Color colorRed;
	private static Color colorYellow;
	private static Color colorBlue;
	private static Color colorBlack;
	private static Color colorGreen;

	private static Color[] colorMapping;


	public static void initColors(Shell shell) {
		colorRed = shell.getDisplay().getSystemColor(SWT.COLOR_RED);
		colorYellow = shell.getDisplay().getSystemColor(SWT.COLOR_YELLOW);
		colorBlue = shell.getDisplay().getSystemColor(SWT.COLOR_BLUE);
		colorBlack = shell.getDisplay().getSystemColor(SWT.COLOR_BLACK);
		colorGreen = shell.getDisplay().getSystemColor(SWT.COLOR_GREEN);

		colorMapping = new Color[]{colorBlack, colorRed, colorYellow, colorBlue};
	}
	
	public static final PlanetPosition adjustPlanetPosition(PlanetPosition dirtyPosition) {
		int x = dirtyPosition.getX();
		int y = dirtyPosition.getY();
		int width = AquatorPlanetConfiguration.getWidth();
		int height = AquatorPlanetConfiguration.getHeight();

		if (x < 0) {
			x = width + x % width;
		}

		if (x >= width) {
			x = x % width;
		}

		if (y < 0) {
			y = height + y % height;
		}

		if (y >= height) {
			y = y % height;
		}

		PlanetPosition newPosition = new PlanetPosition(y, x);

		return newPosition;
	}

	public static void drawLand(GC gc, int[][] currentLand) {
		for (int i = 0; i < currentLand.length; i++) {
			for (int j = 0; j < currentLand[i].length; j++) {
				gc.setForeground(colorMapping[currentLand[i][j]]);
				gc.drawPoint(j, i);
			}
		}
	}

	public static void drawWorldPosition(GC gc, int[][] currentLand, AquatorSurface planet, PlanetPosition position) {
		int y = position.getY();
		int x = position.getX();

		// 1 - red = fishes & sharkes; 2 - yellow = fishes; 3 - blue = sharks; 0 - black - empty;
		if (!planet.isEmpty(y, x)) {
			if (planet.getFishCount(new PlanetPosition(y, x)) > 0
					&& planet.getSharkCount(new PlanetPosition(y, x)) > 0) {

				if (currentLand[y][x] != 1) {
                    if (gc != null) {
                        gc.setForeground(colorRed);
                        gc.drawPoint(x, y);
                    }
					currentLand[y][x] = 1;
				}
			} else if (planet.getFishCount(new PlanetPosition(y, x)) > 0
					&& planet.getSharkCount(new PlanetPosition(y, x)) == 0) {

				if (currentLand[y][x] != 2) {
                    if (gc != null) {
                        gc.setForeground(colorYellow);
                        gc.drawPoint(x, y);
                    }
					currentLand[y][x] = 2;
				}

			} else if (planet.getSharkCount(new PlanetPosition(y, x)) > 0) {

				if (currentLand[y][x] != 3) {
                    if (gc != null) {
                        gc.setForeground(colorBlue);
                        gc.drawPoint(x, y);
                    }
					currentLand[y][x] = 3;
				}
			}

		} else {
			if (currentLand[y][x] != 0) {
                if (gc != null) {
                    gc.setForeground(colorBlack);
                    gc.drawPoint(x, y);
                }
				currentLand[y][x] = 0;
			}
		}
	}

	public static void checkEntity(PlanetPosition pos, IInhabitant entity) {
		if (!pos.equals(entity.getPosition())) {
			throw new PlanetException("Position Error!! Entity = " + entity);
		}
	}
	
	public static void delay(long ms) {
		try {
			Thread.currentThread().sleep(ms);
		} catch (Exception ex) {
			
		}
	}

}
