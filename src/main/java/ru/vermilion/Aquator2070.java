package ru.vermilion;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Monitor;
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
		Shell worldShell = new Shell(display);
		worldShell.setLayout(new GridLayout());
		worldShell.setText("World Configuration");
		PlanetInitialConfigurationWindow planetInitialConfigurationWindow = new PlanetInitialConfigurationWindow(worldShell);
		worldShell.open();

		while (!worldShell.isDisposed()) {
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

		if (!worldShell.isDisposed()) {
			worldShell.close();
			worldShell.dispose();
		}

		// /////////////

        final int SHELL_TRIM = SWT.TITLE | SWT.MIN; // SWT.CLOSE | SWT.MAX;  | SWT.RESIZE;
		worldShell = new Shell(display, SHELL_TRIM);
		AquatorPlanetHelper.initColors(worldShell);
		worldShell.setText("Life..");

        worldShell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent disposeEvent) {
				System.exit(0);
			}
		});

		AquatorLife aquaLife = new AquatorLife(planetInitialConfigurationWindow);
		aquaLife.aquatorLifeDraw(worldShell);
		worldShell.open();

		System.out.println("shell.getSize() = " + worldShell.getBounds());
		//System.exit(0);
        //shell.setLocation(0,0);

		
		EmpiricGraphicThreadWindow egtw = new EmpiricGraphicThreadWindow();
		egtw.setDaemon(true);
		egtw.start();
		
		EllipseGraphicThreadWindow ellipsegtw = new EllipseGraphicThreadWindow();
		ellipsegtw.setDaemon(true);
		ellipsegtw.start();
		
		RealtimeDatasheet rtds = new RealtimeDatasheet(planetInitialConfigurationWindow, aquaLife);
		rtds.setDaemon(true);
		rtds.start();

////////////////

        Point pt = display.getCursorLocation();
        Rectangle rect = null;
        Monitor[] monitors = display.getMonitors();
        for (int i = 0; i < monitors.length; i++) {
            if (monitors[i].getBounds().contains(pt)) {
                rect = monitors[i].getClientArea();
            }
        }


        // rect = Rectangle {0, 0, 1920, 1156}
//        System.out.println("monitor = " + rect);
//        System.exit(0);
        // width, height





        Point mainWindowSize = worldShell.getSize();
        Point egtwWindowSize = egtw.getWindowSize();
        Point ellipsegtwWindowSize = ellipsegtw.getWindowSize();
        Point rtdsWindowSize = rtds.getWindowSize();


        Point mainWindowPoint = new Point(50, 50);





		while (!worldShell.isDisposed()) {
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

    public void layoutShells() {

    }

	public static void main(String args[]) {
		new Aquator2070().start();

	}

}
