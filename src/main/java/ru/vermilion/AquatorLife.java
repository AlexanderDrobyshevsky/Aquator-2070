package ru.vermilion;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import ru.vermilion.basic.AquatorPlanetConfiguration;
import ru.vermilion.basic.AquatorPlanetHelper;
import ru.vermilion.basic.CommonHelper;
import ru.vermilion.behavior.AquatorExecutive;
import ru.vermilion.model.EmpiricGraphicData;
import ru.vermilion.model.PlanetModelController;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class AquatorLife {

	private AquatorExecutive aquatorExecutive;

	private boolean isCancel;

	private boolean isRestart;

	private boolean isPaused;

	private Composite surface;


	private boolean isHandling = false;

	public AquatorLife(PlanetInitialConfigurationWindow config) {
		AquatorPlanetConfiguration.setWidth(config.getWorldWidth());
		AquatorPlanetConfiguration.setHeight(config.getWorldHeight());
		AquatorPlanetConfiguration.setDepth(config.getFishMaxDepth());
		
		aquatorExecutive = new AquatorExecutive();

		aquatorExecutive.initFishes(config.getInitialFishesCount(), null,
				config.getFishLifeTime(), config.getFishMaxReproductives(),
				config.getFishPregnantPeriod(), config.getFishSpeed(), config.getFishMaxDepth());
		aquatorExecutive.initSharks(config.getInitialSharkesCount(), null,
				config.getSharkLifeTime(), config.getSharkMaxHungerTime(),
				config.getSharkPregnantPeriod(), config.getSharkMaxReproductives(),
				config.getSharkSpeed(), config.getSharkMaxDepth());
	}

	private int[][] currentLand;

	public void createContent(Composite parent, AtomicInteger synchronizer) {
		final int width = AquatorPlanetConfiguration.getWidth();
		final int height = AquatorPlanetConfiguration.getHeight();

		currentLand = new int[height][width];

		GridLayout parentLayout = new GridLayout();
		parentLayout.numColumns = 1;
		parentLayout.marginWidth = 0;
		parentLayout.marginHeight = 0;
		parent.setLayout(parentLayout);

		surface = new Composite(parent, SWT.NONE);

		GridData gd1 = new GridData(width, height);
		gd1.horizontalIndent = 0;
		gd1.verticalIndent = 0;
		surface.setLayoutData(gd1);

		surface.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				paintPlanet(e.gc, surface.getShell());
			}
		});

		surface.setEnabled(false);

		Color colorBlack = parent.getDisplay().getSystemColor(SWT.COLOR_BLACK);
		surface.setBackground(colorBlack);
		parent.pack();

		synchronizer.getAndIncrement();
	}

	boolean theEnd = false;


	private void idleRepaint(GC gc) {
        AquatorPlanetHelper.drawLand(gc, currentLand);
	}

	private void paintPlanet(GC gc, Shell shell) {
		if (isHandling == true || theEnd || isCancel) {
			return;
		}

		if (isPaused) {
			idleRepaint(gc);

			return;
		}

		isHandling = true;

		if (shell == null) {
            step(gc, shell);

			isHandling = false;
			
			return;
		}
		
		for (int i = 0; i < currentLand.length; i++) {
			Arrays.fill(currentLand[i], 0);
		}

		while (!theEnd) {
			if (isCancel || shell.getDisplay().readAndDispatch()
					& shell.getDisplay().readAndDispatch()
					& shell.getDisplay().readAndDispatch()
					& shell.getDisplay().readAndDispatch() || isPaused) {

				isHandling = false;
				return;
			}
			
            step(gc, shell);
		}
	}

    public void step(GC gc, Shell shell) {
        AquatorExecutive.IterationLateData iterationData = aquatorExecutive.nextIteration(gc, shell, currentLand);
        aquatorExecutive.setLastRedrewIteration(aquatorExecutive.getCurrentIteration());
        EmpiricGraphicData.getInstance().add(iterationData.getFishesCount(), iterationData.getSharksCount());
        PlanetModelController.getInstance().update();

        if (iterationData.getFishesCount() + iterationData.getSharksCount() == 0) {
            theEnd = true;
            System.out.println("The End!");
        }

        System.out.println("ITER:--" + aquatorExecutive.getCurrentIteration() + "--; Fishes:Sharks = " +
                iterationData.getFishesCount() + ":" + iterationData.getSharksCount() + "--");
    }

	public void nextIteration() {
		if (isPaused || isCancel) return;

		long currItr = aquatorExecutive.getCurrentIteration();
		
		surface.redraw();
		
		// Application dropped in tray
		if (currItr == aquatorExecutive.getCurrentIteration()) {
            if (isPaused) return;

			paintPlanet(null, null);
		}

		System.out.println("--REDRAW");
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setIsPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public void nextStep() {
		nextIteration();
	}

	public boolean isCancel() {
		return isCancel;
	}

	public void setIsCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}

	public boolean isRestart() {
		return isRestart;
	}

	public void setIsRestart(boolean isRestart) {
		this.isRestart = isRestart;
	}

	public Composite getSurface() {
        return surface;
    }
}
