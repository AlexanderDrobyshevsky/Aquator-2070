package ru.vermilion;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;
import ru.vermilion.graphics.EllipseGraphicThreadWindow;
import ru.vermilion.graphics.EmpiricGraphicThreadWindow;

import org.eclipse.swt.layout.GridLayout;

import ru.vermilion.representation.RealtimeDatasheet;

import ru.vermilion.basic.AquatorPlanetHelper;

public class Aquator2070 {

    private Menu menuBar,  systemMenu, helpMenu;

    private MenuItem systemMenuHeader, helpMenuHeader;

    // System Items
    private MenuItem setDefaultsItem, exitItem;

    // Help Items
    private MenuItem aboutItem;


    private void createMenu(Shell shell) {
        menuBar = new Menu(shell, SWT.BAR);

        // System
        systemMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        systemMenuHeader.setText("&System");

        systemMenu = new Menu(shell, SWT.DROP_DOWN);
        systemMenuHeader.setMenu(systemMenu);

        setDefaultsItem = new MenuItem(systemMenu, SWT.PUSH);
        setDefaultsItem.setText("&Reset parameters to defaults");

        exitItem = new MenuItem(systemMenu, SWT.PUSH);
        exitItem.setText("&Exit");

        exitItem.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                System.exit(0);
            }
        });


        // Help
        helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        helpMenuHeader.setText("&Help");

        helpMenu = new Menu(shell, SWT.DROP_DOWN);
        helpMenuHeader.setMenu(helpMenu);

        aboutItem = new MenuItem(helpMenu, SWT.PUSH);
        aboutItem.setText("&About Program");

        shell.setMenuBar(menuBar);
    }

    // refactor
	private void start() {
		Display display = new Display();
		Shell worldShell = new Shell(display);
		worldShell.setLayout(new GridLayout());
		worldShell.setText("World Configuration");
        createMenu(worldShell);
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

		final EmpiricGraphicThreadWindow egtw = new EmpiricGraphicThreadWindow();
		egtw.setDaemon(true);
		egtw.start();
		
		final EllipseGraphicThreadWindow ellipsegtw = new EllipseGraphicThreadWindow();
		ellipsegtw.setDaemon(true);
		ellipsegtw.start();
		
		final RealtimeDatasheet rtds = new RealtimeDatasheet(planetInitialConfigurationWindow, aquaLife);
		rtds.setDaemon(true);
		rtds.start();

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

		planetInitialConfigurationWindow.dispose();
	}

    private void layoutShells(Display display, Shell worldShell, final EmpiricGraphicThreadWindow egtw, final EllipseGraphicThreadWindow ellipsegtw, final RealtimeDatasheet rtds) {
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

    private void applyCalculatedLocations(Shell worldShell, final EmpiricGraphicThreadWindow egtw, final EllipseGraphicThreadWindow ellipsegtw, final RealtimeDatasheet rtds, Point a, final Point b, final Point c, final Point d) {
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

	public static void main(String args[]) {
		new Aquator2070().start();

	}

}
