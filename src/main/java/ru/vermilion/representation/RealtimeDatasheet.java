package ru.vermilion.representation;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import ru.vermilion.PlanetInitialConfigurationWindow;
import ru.vermilion.basic.CommonHelper;
import ru.vermilion.model.EmpiricGraphicData;
import ru.vermilion.model.IModelListener;
import ru.vermilion.model.PlanetModelController;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class RealtimeDatasheet extends Thread {
	
	protected Shell windowShell;
	
	protected EmpiricGraphicData egd = EmpiricGraphicData.getInstance();

	private PlanetInitialConfigurationWindow planetInitialConfiguration;
	
	private static final int SHELL_TRIM = SWT.TITLE | SWT.MIN; // SWT.CLOSE | SWT.MAX;  | SWT.RESIZE;

	private long startTime;

	public RealtimeDatasheet(PlanetInitialConfigurationWindow planetInitialConfiguration) {
		this.planetInitialConfiguration = planetInitialConfiguration;
	}

	public void run() {
		Display display = new Display();
		windowShell = new Shell(display, SHELL_TRIM);
		GridLayout gl = new GridLayout(2, false);
		gl.marginHeight = 0;
		gl.marginWidth = 0;
		windowShell.setLayout(gl);
		newSheet();
		
		configureWindow();

		startTime = System.currentTimeMillis();
		
		createContent(windowShell);
		
		windowShell.pack();

		windowShell.open();

		while (!windowShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	protected void configureWindow() {
		windowShell.setText("Realtime Datasheet");
		//windowShell.setBounds(500, 360, width, height);
		//windowShell.setMinimumSize(new Point(250, 180));
	}
	
	private Button itrxButton;
	private Button fishesButton;
	private Button sharksButton;
	private Button runningTimeButton;
	
	private Button minimumFishesButton;
	private Button maximumFishesButton;
	private Button minimumSharksButton;
	private Button maximumSharksButton;

	private Button cycleButton;
	
	
	protected void createContent(Composite composite) {
		Composite datasheetComposite11 = new Composite(composite, SWT.NONE);

		GridLayout compositeLayout = new GridLayout(2, false);
		compositeLayout.marginHeight = 0;
		compositeLayout.marginWidth = 0;
		compositeLayout.horizontalSpacing = 0;
		compositeLayout.verticalSpacing = 0;
		datasheetComposite11.setLayout(compositeLayout);
		
		GridData gd = new GridData();
		gd.verticalAlignment = SWT.TOP;
		datasheetComposite11.setLayoutData(gd);
		
		// realtime environment 
		itrxButton = createButtonedLabel2x(datasheetComposite11, "Iteration:", "0");
		cycleButton = createButtonedLabel2x(datasheetComposite11, "Cycle:", "0");
		fishesButton = createButtonedLabel2x(datasheetComposite11, "Fishes:", "0");
		sharksButton = createButtonedLabel2x(datasheetComposite11, "Sharks:", "0");

		runningTimeButton = createButtonedLabel2x(datasheetComposite11, "Running Time:", "0");
		
		Composite datasheetComposite12 = new Composite(datasheetComposite11, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		datasheetComposite12.setLayoutData(gd);
		
		compositeLayout = new GridLayout(4, false);
		compositeLayout.marginHeight = 0;
		compositeLayout.marginWidth = 0;
		compositeLayout.horizontalSpacing = 0;
		compositeLayout.verticalSpacing = 0;
		datasheetComposite12.setLayout(compositeLayout);
		
		newSheet();
		minimumFishesButton = createButtonedLabel(datasheetComposite12, "Min fishes:", "0", 75, 30);
		maximumFishesButton = createButtonedLabel(datasheetComposite12, "Max fishes:", "0", 75, 60);
		newLine();
		minimumSharksButton = createButtonedLabel(datasheetComposite12, "Min sharks:", "0", 75, 30);
		maximumSharksButton = createButtonedLabel(datasheetComposite12, "Max sharks:", "0", 75, 60);

		newSheet();
		createButton(datasheetComposite12, "Pause..", 105);
		createButton(datasheetComposite12, "Next Step..", 135, false);
		newLine();
		createButton(datasheetComposite12, "New Simulation..", 105);
		Button exitButton = createButton(datasheetComposite12, "Exit", 135);

		exitButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				// todo
				System.exit(0);
			}
		});


		Composite datasheetComposite21 = new Composite(composite, SWT.NONE);
		
		compositeLayout = new GridLayout(4, false);
		compositeLayout.marginHeight = 0;
		compositeLayout.marginWidth = 0;
		compositeLayout.horizontalSpacing = 0;
		compositeLayout.verticalSpacing = 0;
		datasheetComposite21.setLayout(compositeLayout);
		
		gd = new GridData();
		gd.verticalAlignment = SWT.TOP;
		datasheetComposite21.setLayoutData(gd);
		
		Label l = new Label(datasheetComposite21, SWT.LEFT);
		l.setText("World Configuration : Planet Aquator Parameters");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		l.setLayoutData(gd);
		
		createButtonedLabel4x(datasheetComposite21, "World Height:", planetInitialConfiguration.getWorldHeight() + "");
		createButtonedLabel4x(datasheetComposite21, "World Width:", planetInitialConfiguration.getWorldWidth() + "");
		newLine();
		createButtonedLabel4x(datasheetComposite21, "World Max Depth:", planetInitialConfiguration.getWorldMaxDepth() + "");
		createButtonedLabel4x(datasheetComposite21, "", "");
		newLine();
		
		l = new Label(datasheetComposite21, SWT.LEFT);
		l.setText("World Configuration : Fishes Parameters");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		l.setLayoutData(gd);
		newSheet();
		
		createButtonedLabel4x(datasheetComposite21, "Fish Life Time:", planetInitialConfiguration.getFishLifeTime() + "");
		createButtonedLabel4x(datasheetComposite21, "Fish Max Reproductives:", planetInitialConfiguration.getFishMaxReproductives() + "");
		newLine();
		createButtonedLabel4x(datasheetComposite21, "Fish Pregnant Period:", planetInitialConfiguration.getFishPregnantPeriod() + "");
		createButtonedLabel4x(datasheetComposite21, "Fish Speed:", planetInitialConfiguration.getFishSpeed() + "");
		newLine();
		createButtonedLabel4x(datasheetComposite21, "Fish Max Depth:", planetInitialConfiguration.getFishMaxDepth() + "");
		createButtonedLabel4x(datasheetComposite21, "", "");
		newLine();
		
		l = new Label(datasheetComposite21, SWT.LEFT);
		l.setText("World Configuration : Sharks Parameters");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		l.setLayoutData(gd);
		newSheet();
		
		createButtonedLabel4x(datasheetComposite21, "Shark Life Time:", planetInitialConfiguration.getSharkLifeTime() + "");
		createButtonedLabel4x(datasheetComposite21, "Shark Max Reproductives:", planetInitialConfiguration.getSharkMaxReproductives() + "");
		newLine();
		createButtonedLabel4x(datasheetComposite21, "Shark Pregnant Period:", planetInitialConfiguration.getSharkPregnantPeriod() + "");
		createButtonedLabel4x(datasheetComposite21, "Shark Speed:", planetInitialConfiguration.getSharkSpeed() + "");
		newLine();
		createButtonedLabel4x(datasheetComposite21, "Shark Max Hunger Time:", planetInitialConfiguration.getSharkMaxHungerTime() + "");
		createButtonedLabel4x(datasheetComposite21, "Shark Max Depth:", planetInitialConfiguration.getSharkMaxDepth() + "");
		newLine();
		
		l = new Label(datasheetComposite21, SWT.LEFT);
		l.setText("World Configuration : Initial Count Parameters");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		l.setLayoutData(gd);
		newSheet();
		
		createButtonedLabel4x(datasheetComposite21, "Initial Fishes Count:", planetInitialConfiguration.getInitialFishesCount() + "");
		createButtonedLabel4x(datasheetComposite21, "Initial Sharks Count:", planetInitialConfiguration.getInitialSharkesCount() + "");
		newLine();
		
		registerListener();
	}

	private void newSheet() {
		layoutAdjuster = 0;
	}
	
	private void registerListener() {
		final IModelListener modelListener = new IModelListener() {

			public void modelChanged() {
				updateView();
			}
			
			class Java {
			   int v,e,r,m,ili,o,n;
			}
			
		};
		
		PlanetModelController.getInstance().addModelListener(modelListener);
		
		windowShell.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				PlanetModelController.getInstance().removeModelListener(modelListener);
			}
		});
	}
	
	private void updateView() {
		windowShell.getDisplay().asyncExec(new Runnable() {

			public void run() {
				if (windowShell.isDisposed()) {
					return;
				}

				int itrx = egd.getIterationsCount() - 1;
				itrxButton.setText(itrx + "");
				fishesButton.setText(egd.getFishesCount(itrx) + "");
				sharksButton.setText(egd.getSharksCount(itrx) + "");

				minimumFishesButton.setText(egd.getMinFishes() + "");
				maximumFishesButton.setText(egd.getMaxFishes() + "");
				minimumSharksButton.setText(egd.getMinSharks() + "");
				maximumSharksButton.setText(egd.getMaxSharks() + "");

				cycleButton.setText(egd.getCycle() + "");

				runningTimeButton.setText(CommonHelper.getElapsedTime(startTime));
			}
		});
	}

	private static int layoutAdjuster = 0;
	
	private Button createButtonedLabel2x(Composite composite, String name, String value) {
		Button button = createButtonedLabel(composite, name, value, 150, 100);
		newLine();
		
		return button;
	}
	
	private Button createButtonedLabel4x(Composite composite, String name, String value) {
        return createButtonedLabel(composite, name, value, 100, 40);
	}

	private void newLine() {
		layoutAdjuster++; layoutAdjuster++;
	}
	
	private Button createButtonedLabel(Composite composite, String name, String value, int keyWidth, int valueWidth) {
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalIndent = 0;
		gd.verticalIndent = -layoutAdjuster;
		gd.heightHint = 19 + layoutAdjuster;
		gd.minimumWidth = keyWidth;
		
		Button button = new Button(composite, SWT.PUSH);
		button.setEnabled(false);
		button.setAlignment(SWT.LEFT);
		button.getBorderWidth();
		
		button.setLayoutData(gd);
		button.setText(name);
		
		GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
		gd2.horizontalIndent = -1;
		gd2.verticalIndent = -layoutAdjuster;
		gd2.heightHint = 19 + layoutAdjuster;
		gd2.minimumWidth = valueWidth;
		
		Button buttonValue = new Button(composite, SWT.PUSH);
		buttonValue.setLayoutData(gd2);
		buttonValue.setText(value);
		
		buttonValue.setEnabled(false);
		
		return buttonValue;
	}

	private Button createButton(Composite composite, String name, int width) {
		return createButton(composite, name, width, true);
	}

	private Button createButton(Composite composite, String name, int width, boolean isEnabled) {
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalIndent = 0;
		gd.verticalIndent = 5;
		gd.heightHint = 25;
		gd.minimumWidth = width;
		gd.horizontalSpan = 2;

		Button button = new Button(composite, SWT.PUSH);
		button.setEnabled(isEnabled);
		button.getBorderWidth();

		button.setLayoutData(gd);
		button.setText(name);

		return button;
	}
	

}
