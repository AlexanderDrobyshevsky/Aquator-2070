package ru.vermilion.representation;

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
	
	public static final int SHELL_TRIM = SWT.TITLE | SWT.MIN; // SWT.CLOSE | SWT.MAX;  | SWT.RESIZE;
	
	public void run() {
		Display display = new Display();
		windowShell = new Shell(display, SHELL_TRIM);
		GridLayout gl = new GridLayout(2, false);
		gl.marginHeight = 0;
		gl.marginWidth = 0;
		windowShell.setLayout(gl);
		newSheet();
		
		configureWindow();
		
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
	
	private Button minimumFishesButton;
	private Button maximumFishesButton;
	private Button minimumSharksButton;
	private Button maximumSharksButton;

	private Button timePassedButton;
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
		createButtonedLabel2x(datasheetComposite11, "Cycle:", "0");
		fishesButton = createButtonedLabel2x(datasheetComposite11, "Fishes:", "0");
		sharksButton = createButtonedLabel2x(datasheetComposite11, "Sharks:", "0");
		
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
		createButtonedLabel4x(datasheetComposite12, "Min fishes:", "0");
		createButtonedLabel4x(datasheetComposite12, "Min sharks:", "0");
		newLine();
		createButtonedLabel4x(datasheetComposite12, "Max fishes:", "0");
		createButtonedLabel4x(datasheetComposite12, "Max sharks:", "0");
		
		
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
		
		createButtonedLabel4x(datasheetComposite21, "World Height:", "0");
		createButtonedLabel4x(datasheetComposite21, "World Width:", "0");
		newLine();
		createButtonedLabel4x(datasheetComposite21, "World Max Depth:", "0");
		createButtonedLabel4x(datasheetComposite21, "", "");
		newLine();
		
		l = new Label(datasheetComposite21, SWT.LEFT);
		l.setText("World Configuration : Fishes Parameters");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		l.setLayoutData(gd);
		newSheet();
		
		createButtonedLabel4x(datasheetComposite21, "Fish Life Time:", "0");
		createButtonedLabel4x(datasheetComposite21, "Fish Max Reproductives:", "0");
		newLine();
		createButtonedLabel4x(datasheetComposite21, "Fish Pregnant Period:", "0");
		createButtonedLabel4x(datasheetComposite21, "Fish Speed:", "0");
		newLine();
		createButtonedLabel4x(datasheetComposite21, "Fish Max Depth:", "0");
		createButtonedLabel4x(datasheetComposite21, "", "");
		newLine();
		
		l = new Label(datasheetComposite21, SWT.LEFT);
		l.setText("World Configuration : Sharks Parameters");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		l.setLayoutData(gd);
		newSheet();
		
		createButtonedLabel4x(datasheetComposite21, "Shark Life Time:", "0");
		createButtonedLabel4x(datasheetComposite21, "Shark Max Reproductives:", "0");
		newLine();
		createButtonedLabel4x(datasheetComposite21, "Shark Pregnant Period:", "0");
		createButtonedLabel4x(datasheetComposite21, "Shark Speed:", "0");
		newLine();
		createButtonedLabel4x(datasheetComposite21, "Shark Max Hunger Time:", "0");
		createButtonedLabel4x(datasheetComposite21, "Shark Max Depth:", "0");
		newLine();
		
		l = new Label(datasheetComposite21, SWT.LEFT);
		l.setText("World Configuration : Initial Count Parameters");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		l.setLayoutData(gd);
		newSheet();
		
		createButtonedLabel4x(datasheetComposite21, "Initial Fishes Count:", "0");
		createButtonedLabel4x(datasheetComposite21, "Initial Sharks Count:", "0");
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
	
	private Button createButtonedLabel(Composite composite, String name, String value, 
			int keyWidth, int valueWidth) {
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
	

}
