package ru.vermilion;

import ru.vermilion.graphics.EllipseGraphicThreadWindow;
import ru.vermilion.graphics.EmpiricGraphicThreadWindow;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ru.vermilion.representation.RealtimeDatasheet;

import ru.vermilion.basic.AquatorPlanetHelper;

public class Aquator2070 {

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

		if (planetInitialConfigurationWindow.isOK() == null || !planetInitialConfigurationWindow.isOK()) {
			System.exit(0);
		}

		if (!shell.isDisposed()) {
			shell.close();
			shell.dispose();
		}

		// /////////////

		shell = new Shell(display);
		// Sets the layout for the shell
		shell.setLayout(new GridLayout());
		shell.setText("Life..");

		AquatorLife aquaLife = new AquatorLife(planetInitialConfigurationWindow);
		aquaLife.aquatorLifeDraw(shell);
		shell.open();
		
		EmpiricGraphicThreadWindow egtw = new EmpiricGraphicThreadWindow();
		egtw.setDaemon(true);
		egtw.start();
		
		EllipseGraphicThreadWindow ellipsegtw = new EllipseGraphicThreadWindow();
		ellipsegtw.setDaemon(true);
		ellipsegtw.start();
		
		RealtimeDatasheet rtds = new RealtimeDatasheet(planetInitialConfigurationWindow);
		rtds.setDaemon(true);
		rtds.start();
		
		AquatorPlanetHelper.initColors(shell);

		while (!shell.isDisposed()) {
			if (display.readAndDispatch() == false) {
				aquaLife.nextIteration();
			}
			aquaLife.nextIteration();

			if (aquaLife.isCancel()) {
				break;
			}
		}

		// ////////////////////////

		// //display = new Display();
		// shell = new Shell(display);
		// //Sets the layout for the shell
		// shell.setLayout(new GridLayout());
		// shell.setText("World Configuration 2");
		//		
		// planetInitialConfigurationWindow = new
		// PlanetInitialConfigurationWindow(shell);
		// shell.open();
		// while (!shell.isDisposed()) {
		// if (!display.readAndDispatch())
		// display.sleep();
		//			
		// if (planetInitialConfigurationWindow.isOK() != null) {
		// break;
		// }
		// }

		planetInitialConfigurationWindow.dispose();
	}

	public static void main(String args[]) {
		new Aquator2070().start();

	}

}
