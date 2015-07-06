package ru.vermilion;

import java.util.Arrays;

import ru.vermilion.model.EmpiricGraphicData;
import ru.vermilion.model.PlanetModelController;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import ru.vermilion.basic.AquatorPlanetConfiguration;
import ru.vermilion.behavior.AquatorExecutive;

public class AquatorLife {

	private AquatorExecutive aquatorExecutive;

	private boolean isCancel;

	private Composite surface;

//	private Composite dr;
//
//	private Label fishLabel;
//
//	private Label sharkLabel;
//
//	private Label cntLabel;

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

	public void aquatorLifeDraw(Composite parent) {
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
	}

	boolean theEnd = false;
	
	private void paintPlanet(GC gc, Shell shell) {
		if (isHandling == true || theEnd) {
			return;
		}

		isHandling = true;
		if (shell == null) {
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

			isHandling = false;
			
			return;
		}
		
		for (int i = 0; i < currentLand.length; i++) {
			Arrays.fill(currentLand[i], 0);
		}

		while (!theEnd) {
			if (shell.getDisplay().readAndDispatch()
					& shell.getDisplay().readAndDispatch()
					& shell.getDisplay().readAndDispatch()
					& shell.getDisplay().readAndDispatch() == true) {
				isHandling = false;
				return;
			}
			
			AquatorExecutive.IterationLateData iterationData = aquatorExecutive.nextIteration(gc, shell, currentLand);
			EmpiricGraphicData.getInstance().add(iterationData.getFishesCount(), iterationData.getSharksCount());
			PlanetModelController.getInstance().update();
			
			aquatorExecutive.setLastRedrewIteration(aquatorExecutive.getCurrentIteration());
			
			if (iterationData.getFishesCount() + iterationData.getSharksCount() == 0) {
				theEnd = true;
				System.out.println("The End!");
			}

			System.out.println("ITER:--" + aquatorExecutive.getCurrentIteration() + "--;");
		}
	}

	public void nextIteration() {
		long currItr = aquatorExecutive.getCurrentIteration();
		
		surface.redraw();
		
		// Application dropped in tray
		if (currItr == aquatorExecutive.getCurrentIteration()) {
			paintPlanet(null, null);
		}

		System.out.println("--REDRAW");
	}

	public boolean isCancel() {
		return isCancel;
	}

}
