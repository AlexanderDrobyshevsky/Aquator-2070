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

	private Composite dr;

	private Label fishLabel;

	private Label sharkLabel;

	private Label cntLabel;

	boolean isHandling = false;

	private int width = 0;
	private int height = 0;
	private int depth = 0;

	public AquatorLife(PlanetInitialConfigurationWindow config) {
		width = config.worldWidth1;
		height = config.worldHeight1;
		depth = config.worldMaxDepth1;

		AquatorPlanetConfiguration.setWidth(width);
		AquatorPlanetConfiguration.setHeight(height);
		AquatorPlanetConfiguration.setDepth(depth);
		

		aquatorExecutive = new AquatorExecutive();

		aquatorExecutive.initFishes(config.initialFishesCount1, null,
				config.fishLifeTime1, config.fishMaxReproductives1,
				config.fishPregnantPeriod1, config.fishSpeed1, config.fishMaxDepth1);
		aquatorExecutive.initSharks(config.initialSharkesCount1, null,
				config.sharkLifeTime1, config.sharkMaxHungerTime1,
				config.sharkPregnantPeriod1, config.sharkMaxReproductives1,
				config.sharkSpeed1, config.sharkMaxDepth1);
	}

	private int[][] currentLand;

	public void aquatorLifeDraw(Composite parent) {

		final int width = AquatorPlanetConfiguration.getWidth();
		final int height = AquatorPlanetConfiguration.getHeight();

		currentLand = new int[height][width];

		GridLayout parentLayout = new GridLayout();
		parent.setLayout(parentLayout);
		parentLayout.numColumns = 2;
		parent.setSize(width + 100, height + 100);

		surface = new Composite(parent, SWT.NONE);

		surface.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				paintPlanet(e.gc, surface.getShell());
			}
		});

		surface.setEnabled(false);

		Color colorBlack = parent.getDisplay().getSystemColor(SWT.COLOR_BLACK);
		surface.setBackground(colorBlack);

		parentLayout = new GridLayout();
		surface.setLayout(parentLayout);
		parentLayout.numColumns = 1;
		parentLayout.marginHeight = 0;
		parentLayout.marginWidth = 0;
		surface.setSize(width, height);

		GridData gd1 = new GridData(width, height);
		surface.setLayoutData(gd1);

		// /////// surface
		new Composite(parent, SWT.NONE);

		Composite child = new Composite(parent, SWT.BORDER);
		dr = child;
		parentLayout = new GridLayout();
		child.setLayout(parentLayout);
		parentLayout.numColumns = 5;

		Button nextButton = new Button(child, SWT.PUSH);
		GridData gd = new GridData(GridData.END);
		gd.verticalAlignment = 100;
		gd.minimumHeight = 100;
		nextButton.setLayoutData(gd);
		nextButton.setText("Next >>");
		nextButton.setEnabled(true);
		nextButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				nextIteration();
			}
		});

		Button cancelButton = new Button(child, SWT.PUSH);
		gd = new GridData(GridData.END);
		gd.verticalAlignment = 100;
		gd.minimumHeight = 100;
		cancelButton.setLayoutData(gd);
		cancelButton.setText("Cancel");
		cancelButton.setEnabled(true);
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				isCancel = true;
			}
		});

		cntLabel = new Label(child, SWT.NO_BACKGROUND);
		cntLabel.setText("ITER: ----------------------------------");

		fishLabel = new Label(child, SWT.NO_BACKGROUND);
		fishLabel.setText("F:");

		sharkLabel = new Label(child, SWT.NO_BACKGROUND);
		sharkLabel.setText("S:");
	}

	boolean theEnd = false;
	
	private void paintPlanet(GC gc, Shell shell) {
		if (isHandling == true || theEnd) {
			return;
		}
		
		if (shell == null) {
			isHandling = true;
			AquatorExecutive.IterationLateData iterationData = 
					aquatorExecutive.nextIteration(gc, shell, currentLand);
			aquatorExecutive.setLastRedrewIteration(aquatorExecutive.getCurrentIteration());
			
			if (iterationData.getFishesCount() + iterationData.getSharksCount() == 0) {
				theEnd = true;
				System.out.println("The End!");
			}
			
			System.out.println("ITER:--" + aquatorExecutive.getCurrentIteration() + "--; Fishes:Sharks = " + 
					iterationData.getFishesCount() + ":" + iterationData.getSharksCount() + "--");
			
			isHandling = false;
			
			return;
		}
		
		isHandling = true;
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
			
			AquatorExecutive.IterationLateData iterationData = 
				aquatorExecutive.nextIteration(gc, shell, currentLand);
			
			PlanetModelController.getInstance().update();
			
			fishLabel.setText("F: --" + iterationData.getFishesCount() + "--;");
			sharkLabel.setText("S: --" + iterationData.getSharksCount() + "--;");
			cntLabel.setText("ITER:--" + aquatorExecutive.getCurrentIteration()
					+ "--;");
			
			EmpiricGraphicData.getInstance().add(iterationData.getFishesCount(), iterationData.getSharksCount());

			dr.layout(true, true);

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
