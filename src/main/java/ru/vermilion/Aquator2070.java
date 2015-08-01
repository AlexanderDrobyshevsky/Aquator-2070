package ru.vermilion;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;
import ru.vermilion.graphics.EllipseGraphicThreadWindow;
import ru.vermilion.graphics.EmpiricGraphicThreadWindow;

import ru.vermilion.representation.SimulationDashboard;

import ru.vermilion.basic.AquatorPlanetHelper;

import java.util.concurrent.atomic.AtomicInteger;

public class Aquator2070 {

    private static final int SHELL_TRIM = SWT.TITLE | SWT.MIN;


    private void inseption() {

        boolean isRestart = true;

        while (isRestart) {
            isRestart = start();
        }
    }

    // refactor  ;;  true - restart;
	private boolean start() {
		Display display = new Display();
        AtomicInteger synchronizer = new AtomicInteger(0);
        PlanetInitialConfigurationWindow planetInitialConfigurationWindow = requestPlanetConfiguration(display);
        if (planetInitialConfigurationWindow == null) {
            return false;
        }

        Shell worldShell = new Shell(display, SHELL_TRIM);
        AquatorPlanetHelper.initColors(worldShell);
		worldShell.setText("Aquator World Simulation");

        worldShell.addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent disposeEvent) {
                //System.exit(0);
            }
        });

        synchronizer.set(0);
		AquatorLife aquaLife = new AquatorLife(planetInitialConfigurationWindow);
		aquaLife.createContent(worldShell, synchronizer);
		worldShell.open();

		final EmpiricGraphicThreadWindow egtw = new EmpiricGraphicThreadWindow(synchronizer);
		egtw.setDaemon(true);
		egtw.start();
		
		final EllipseGraphicThreadWindow ellipsegtw = new EllipseGraphicThreadWindow(synchronizer);
		ellipsegtw.setDaemon(true);
		ellipsegtw.start();
		
		final SimulationDashboard rtds = new SimulationDashboard(planetInitialConfigurationWindow, aquaLife, synchronizer);
		rtds.setDaemon(true);
        rtds.start();

        while (synchronizer.get() != 4) {   AquatorPlanetHelper.delay(20);    }

        layoutShells(display, worldShell, egtw, ellipsegtw, rtds);

		while (!worldShell.isDisposed()) {
			if (display.readAndDispatch() == false) {
				aquaLife.nextIteration();
			}
			aquaLife.nextIteration();

			if (aquaLife.isCancel()) {
				break;
			}
		}

        disposeWindows(worldShell, egtw, ellipsegtw, rtds);
        display.dispose();

        return aquaLife.isRestart();
	}

    private PlanetInitialConfigurationWindow requestPlanetConfiguration(Display display) {
        Shell configurationWindowShell = new Shell(display, SHELL_TRIM);

        PlanetInitialConfigurationWindow planetInitialConfigurationWindow = new PlanetInitialConfigurationWindow(configurationWindowShell);

        configurationWindowShell.open();

        while (!configurationWindowShell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();

            if (planetInitialConfigurationWindow.isOK() != null) {
                break;
            }
        }

        // todo refactor
        if (planetInitialConfigurationWindow.isOK() == null || !planetInitialConfigurationWindow.isOK()) {
            planetInitialConfigurationWindow.dispose();
            return null;
        }
        planetInitialConfigurationWindow.dispose();

        if (!configurationWindowShell.isDisposed()) {
            configurationWindowShell.close();
            configurationWindowShell.dispose();
        }

        return planetInitialConfigurationWindow;
    }

    private void layoutShells(Display display, Shell worldShell, final EmpiricGraphicThreadWindow egtw, final EllipseGraphicThreadWindow ellipsegtw, final SimulationDashboard rtds) {
        Point pt = display.getCursorLocation();
        Rectangle rect = null;
        Monitor[] monitors = display.getMonitors();
        for (int i = 0; i < monitors.length; i++) {
            if (monitors[i].getBounds().contains(pt)) {
                rect = monitors[i].getClientArea();
            }
        }

        Point mainWindowSize = worldShell.getSize();
        Point egtwWindowSize = egtw.getWindowSize();
        Point ellipsegtwWindowSize = ellipsegtw.getWindowSize();
        Point rtdsWindowSize = rtds.getWindowSize();

        //case 1  // todo use constants
        // width(x), height(y)
        final Point a = new Point(50, 50);
        final int gap = 50;  // todo distinct vertical & horizontal gap
        final int screenWidth = Math.max(rect.width, 600);
        final int screenHeight = Math.max(rect.height, 700);

        final Point b,c,d;
        if (mainWindowSize.x <= 580 && mainWindowSize.y <= 580) {
            int h;
            if (mainWindowSize.y <= egtwWindowSize.y) {
                h = 50 + egtwWindowSize.y + gap;
            } else {
                h = 50 + mainWindowSize.y + gap;
            }

            b = new Point(50, Math.min(h, screenHeight - 100));
            c = new Point(Math.min(50 + 600 + gap, screenWidth - egtwWindowSize.x), 50);
            d = new Point(Math.min(50 + 600 + gap, screenWidth - ellipsegtwWindowSize.x), Math.min(50 + egtwWindowSize.y + gap, screenHeight - 100));
        } else

        // case 2
        if (mainWindowSize.x > 580 && mainWindowSize.y <= 580) {
            c = new Point(50, 50 + mainWindowSize.y + gap);
            d = new Point(Math.min(50 + egtwWindowSize.x + gap,screenWidth - ellipsegtwWindowSize.x), Math.min(50 + mainWindowSize.y + gap,screenHeight - 100));
            b = new Point(Math.min(50 + (egtwWindowSize.x) / 2,screenWidth - rtdsWindowSize.x), Math.min(50 + mainWindowSize.y + gap + egtwWindowSize.y + gap,screenHeight - 100));
        } else

        // case 3
        if (mainWindowSize.y > 580) {
            c = new Point(Math.min(50 + mainWindowSize.x + gap,screenWidth - egtwWindowSize.x), 50);
            d = new Point(Math.min(50 + mainWindowSize.x + gap,screenWidth - ellipsegtwWindowSize.x), Math.min(50 + egtwWindowSize.y + gap,screenHeight - 100));
            b = new Point(Math.min(50 + mainWindowSize.x + gap,screenWidth - rtdsWindowSize.x), Math.min(50 + egtwWindowSize.y + gap + ellipsegtwWindowSize.y + gap,screenHeight - 100));
        } else  {
            b = new Point(50, 50);
            c = new Point(70, 70);
            d = new Point(90, 90);
        }

        applyCalculatedLocations(worldShell, egtw, ellipsegtw, rtds, a, b, c, d);
    }

    private void applyCalculatedLocations(Shell worldShell, final EmpiricGraphicThreadWindow egtw, final EllipseGraphicThreadWindow ellipsegtw, final SimulationDashboard rtds, Point a, final Point b, final Point c, final Point d) {
        worldShell.setLocation(a);
        egtw.getWindowShell().getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                egtw.setWindowLocation(c);
            }
        });

        ellipsegtw.getWindowShell().getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                ellipsegtw.setWindowLocation(d);
            }
        });

        rtds.getWindowShell().getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                rtds.setWindowLocation(b);
            }
        });
    }

    private void disposeWindows(Shell worldShell, final EmpiricGraphicThreadWindow egtw, final EllipseGraphicThreadWindow ellipsegtw, final SimulationDashboard rtds) {
        worldShell.dispose();

        egtw.getWindowShell().getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                egtw.getWindowShell().getDisplay().dispose();
            }
        });

        ellipsegtw.getWindowShell().getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                ellipsegtw.getWindowShell().getDisplay().dispose();
            }
        });

        rtds.getWindowShell().getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                rtds.getWindowShell().getDisplay().dispose();
            }
        });
    }

	public static void main(String args[]) {
		new Aquator2070().inseption();

	}

}
