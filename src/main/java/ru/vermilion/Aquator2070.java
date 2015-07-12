package ru.vermilion;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import ru.vermilion.graphics.EllipseGraphicThreadWindow;
import ru.vermilion.graphics.EmpiricGraphicThreadWindow;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ru.vermilion.representation.RealtimeDatasheet;

import ru.vermilion.basic.AquatorPlanetHelper;

public class Aquator2070 {

    // refactor
	private void start() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());
		shell.setText("World Configuration");
		PlanetInitialConfigurationWindow planetInitialConfigurationWindow = new PlanetInitialConfigurationWindow(shell);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();

			if (planetInitialConfigurationWindow.isOK() != null) {
				break;
			}
		}

        // todo refactor
		if (planetInitialConfigurationWindow.isOK() == null || !planetInitialConfigurationWindow.isOK()) {
			System.exit(0);
		}

		if (!shell.isDisposed()) {
			shell.close();
			shell.dispose();
		}

		// /////////////

        final int SHELL_TRIM = SWT.TITLE | SWT.MIN; // SWT.CLOSE | SWT.MAX;  | SWT.RESIZE;
		shell = new Shell(display, SHELL_TRIM);
		AquatorPlanetHelper.initColors(shell);
		shell.setText("Life..");

        shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent disposeEvent) {
				System.exit(0);
			}
		});

		AquatorLife aquaLife = new AquatorLife(planetInitialConfigurationWindow);
		aquaLife.aquatorLifeDraw(shell);
		shell.open();
		
		EmpiricGraphicThreadWindow egtw = new EmpiricGraphicThreadWindow();
		egtw.setDaemon(true);
		egtw.start();
		
		EllipseGraphicThreadWindow ellipsegtw = new EllipseGraphicThreadWindow();
		ellipsegtw.setDaemon(true);
		ellipsegtw.start();
		
		RealtimeDatasheet rtds = new RealtimeDatasheet(planetInitialConfigurationWindow, aquaLife);
		rtds.setDaemon(true);
		rtds.start();
		


		while (!shell.isDisposed()) {
			if (display.readAndDispatch() == false) {
				aquaLife.nextIteration();
			}
			aquaLife.nextIteration();

			if (aquaLife.isCancel()) {
				break;
			}
		}

		planetInitialConfigurationWindow.dispose();
	}

	public static void main(String args[]) {
		new Aquator2070().start();

	}

}
