package ru.vermilion;

import ru.vermilion.graphics.EllipseGraphicThreadWindow;
import ru.vermilion.graphics.EmpiricGraphicThreadWindow;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ru.vermilion.representation.RealtimeDatasheet;

import ru.vermilion.basic.AquatorPlanetHelper;

public class Aquator2062    {

	private void start() {
		// Creates a new display object for the example to go into
		Display display = new Display();
		// Creates a new shell object
		Shell shell = new Shell(display);
		// Sets the layout for the shell
		shell.setLayout(new GridLayout());
		shell.setText("World Configuration");
		// Creates the control example - see import statement for location.
		PlanetInitialConfigurationWindow planetInitialConfigurationWindow = new PlanetInitialConfigurationWindow(
				shell);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();

			if (planetInitialConfigurationWindow.isOK() != null) {
				break;
			}
		}

		shell.close();
		shell.dispose();

		// /////////////

		// display = new Display();
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
		
		RealtimeDatasheet rtds = new RealtimeDatasheet();
		rtds.setDaemon(true);
		rtds.start();
		
		AquatorPlanetHelper.initColors(shell);

		while (!shell.isDisposed()) {
			if (display.readAndDispatch() == false) {
				//display.sleep();

				aquaLife.nextIteration();
			}
			aquaLife.nextIteration();

			// try {
			// //Thread.currentThread().sleep(500);
			// } catch (Exception ex) {
			//
			// }

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
		new Aquator2062().start();

	}

}
