package ru.vermilion.graphics;

import org.eclipse.swt.graphics.Point;
import ru.vermilion.model.EmpiricGraphicData;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class GraphicThreadWindow extends Thread {
	protected Shell windowShell;

	protected int width = 600;
	protected int height = 300;

	protected Color colorDarkGreen;
	protected Color colorShark;
	protected Color colorFish;
	protected Color colorDigits;
	
	protected static final int a = 10; // 20;
	protected static final int c = 27;
	protected int b = 50; // 70; // changeable
	protected static final int d = 10;
	protected static final int e = 10;
	protected static final int f = 20;
	
	// idea: do cellSize X, cellSize Y 
	// 5 is Minimal due to y-digits
	protected static final int cellSize = 10;
	
	protected Canvas graphicComposite;
	
	protected EmpiricGraphicData egd = EmpiricGraphicData.getInstance();

	protected final Point WINDOW_MIN_SIZE = new Point(250, 180);

	private Point windowSize;

	private final Object synchronizer = new Object();

	private AtomicInteger createContentSynchronizer;

	public GraphicThreadWindow(AtomicInteger createContentSynchronizer) {
		this.createContentSynchronizer = createContentSynchronizer;
	}

	// if repainting is more than REFRESH_INTERVAL what will happens : Either the method runs again or only after previous one is finished ?
	public void run() {
		Display display;
		synchronized (synchronizer) {
			display = new Display();
			windowShell = new Shell(display);
			GridLayout gl = new GridLayout(1, false);
			gl.marginHeight = 0;
			gl.marginWidth = 0;
			windowShell.setLayout(gl);

			configureWindow();

			colorDarkGreen = display.getSystemColor(SWT.COLOR_DARK_GREEN);
			colorShark = display.getSystemColor(SWT.COLOR_BLUE);
			colorFish = display.getSystemColor(SWT.COLOR_YELLOW);
			colorDigits = display.getSystemColor(SWT.COLOR_WHITE);

			createContent(windowShell);

			windowShell.open();
			windowSize = windowShell.getSize();
			createContentSynchronizer.incrementAndGet();
		}

		while (!windowShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	protected abstract void configureWindow();
	
	protected abstract void createContent(Composite composite);
	
	protected static final int[][] DIGITS = new int[][] {
		{0,1,1,1,0,0,0,1,0,1,1,1,0,1,1,1,0,1,0,1,0,1,1,1,0,1,1,1,0,1,1,1,0,1,1,1,0,1,1,1},
		{0,1,0,1,0,0,1,1,0,0,0,1,0,0,0,1,0,1,0,1,0,1,0,0,0,1,0,0,0,0,0,1,0,1,0,1,0,1,0,1},
		{0,1,0,1,0,1,0,1,0,1,1,1,0,0,1,1,0,1,1,1,0,1,1,1,0,1,1,1,0,0,0,1,0,1,1,1,0,1,1,1},
		{0,1,0,1,0,0,0,1,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,1,0,1,0,0,0,1,0,1,0,1,0,0,0,1},
		{0,1,1,1,0,0,0,1,0,1,1,1,0,1,1,1,0,0,0,1,0,1,1,1,0,1,1,1,0,0,0,1,0,1,1,1,0,1,1,1} 
	};
	
	protected static final int[][] APOSTROF = new int[][] {
		{0,1},
		{0,1},
		{0,0},
		{0,0},
		{0,0}
	};

	protected void paintNumber(GC gc, Color color, int height, int number, int x, int y) {
		int apostrof = 0;
		int len = Integer.toString(number).length();
		int debugNumber = number;

		int xCaret = x + getSizeInPixels(number);
		for (int i = 0; i < len; i++) {
			xCaret -= 4;
			paintDigit(gc, color, height, number % 10, xCaret, y);

			number = number / 10;
			apostrof = ++apostrof % 3;
			if (apostrof == 0 && number > 0) {
				xCaret -= 2;
				paintApostrof(gc, color, height, xCaret, y);
			}

			assert xCaret >= 0 : "xCaret < 0 = " + xCaret + ";number="+number + 
					";getSizeInPixels(number)=" + (getSizeInPixels(debugNumber)) + ";b=" + b + ";debugNumber=" +debugNumber +
					";len =" + len + ";x=" + x;
		}

		assert number == 0;
	}
	
	protected static int getSizeInPixels(int a) {
		int len = Integer.toString(a).length();
		
		return len * 4 + 2 * ((len - 1) / 3);
	}
	
	protected void paintDigit(GC gc, Color color, int height, int digit, int x, int y) {
		for (int i = 0; i < 4; i++) {
			for (int j=0; j < 5; j++) {
				if (DIGITS[j][digit*4 + i] == 1) {
					paintSpacePoint(gc, color, height, x + i, y - j);
				}
			}
		}
	}
	
	protected void paintApostrof(GC gc, Color color, int height, int x, int y) {
		for (int i = 0; i < 5; i++) {
			for (int j=0; j < 2; j++) {
				if (APOSTROF[i][j] == 1) {
					paintSpacePoint(gc, color, height, x + j, y - i);
				}
			}
		}
	}

	protected void paintSpacePoint(GC gc, Color color, int height, int x, int y) {
		gc.setForeground(color);
		gc.drawPoint(x, height - y);
	}
	
	protected void paintSpaceLine(GC gc, Color color, int height, int x1, int y1, int x2, int y2) {
		gc.setForeground(color);
		gc.drawLine(x1, height - y1, x2, height - y2);
	}

//	private void paintGraphicPoint(GC gc, Color color, int realHeight, int x, int y) {
//		gc.setForeground(color);
//		gc.drawPoint(x + b, realHeight + a - y);
//	}

	protected void paintGraphicLine(GC gc, Color color, int realHeight, int x1, int y1, int x2, int y2) {
		gc.setForeground(color);
		gc.drawLine(x1 + b, realHeight + a - y1, x2 + b, realHeight + a - y2);
	}

	public Point getWindowSize() {
        synchronized (synchronizer) {
            return windowSize;
        }
	}

    public void setWindowLocation(Point newLocation) {
        windowShell.setLocation(newLocation);
    }

    public Shell getWindowShell() {
        return windowShell;
    }
}
