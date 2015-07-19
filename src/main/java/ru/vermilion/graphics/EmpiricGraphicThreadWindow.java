package ru.vermilion.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;

public class EmpiricGraphicThreadWindow extends GraphicThreadWindow {

	private static final int REFRESH_INTERVAL = 1000;
	
	private static final int BUTTON_PANEL_SIZE = 80;

	// checkLock.getSelection() = true - check box selected; Screen does not move!
	private Button checkLock;

	// >1 & 2^n
	private int horizontalScale = 1;
	
	private Slider screenSlider;
	
	private double divisor;

	protected void configureWindow() {
		windowShell.setText("Empiric Data Graphic");
		windowShell.setBounds(500, 40, width, height);
		windowShell.setMinimumSize(WINDOW_MIN_SIZE);
	}

	protected void createContent(Composite composite) {
		graphicComposite = new Canvas(composite, SWT.DOUBLE_BUFFERED);

		graphicComposite.setEnabled(false);

		Color color = composite.getDisplay().getSystemColor(SWT.COLOR_BLACK);
		graphicComposite.setBackground(color);

		GridLayout compositeLayout = new GridLayout();
		compositeLayout.numColumns = 1;
		compositeLayout.marginHeight = 0;
		compositeLayout.marginWidth = 0;
		graphicComposite.setLayout(compositeLayout);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalIndent = 0;
		gd.verticalIndent = 0;
		gd.heightHint = height - BUTTON_PANEL_SIZE;
		graphicComposite.setLayoutData(gd);

		Composite buttonsComposite = new Composite(composite, SWT.NONE);
		gd = new GridData(GridData.FILL_BOTH);
		buttonsComposite.setLayoutData(gd);

		buttonsComposite.setLayout(new GridLayout(5, false));
		
		final Label scaleLabel = new Label(buttonsComposite, SWT.NONE);
		scaleLabel.setText("Scale: x" + horizontalScale);

		Button plusButton = new Button(buttonsComposite, SWT.PUSH);
		plusButton.setText(" + ");
		plusButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (horizontalScale > 1) {
					horizontalScale /= 2;
					scaleLabel.setText("Scale: x" + horizontalScale);
					graphicComposite.redraw();
				}
			}
		});

		Button minusButton = new Button(buttonsComposite, SWT.PUSH);
		minusButton.setText(" - ");
		minusButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (horizontalScale < 1000000) {
					horizontalScale *= 2;
					scaleLabel.setText("Scale: x" + horizontalScale);
					graphicComposite.redraw();
				}
			}
		});
		

		screenSlider = new Slider(buttonsComposite, SWT.HORIZONTAL);
		gd = new GridData(GridData.FILL_BOTH);
		screenSlider.setLayoutData(gd);
		screenSlider.setEnabled(false);
		screenSlider.addListener (SWT.Selection, new Listener () {
			public void handleEvent(Event event) {
				switch (event.detail) {
					case SWT.DRAG: {
						boolean isLock = checkLock.getSelection();
						
						int thumb = screenSlider.getThumb();
						int selection = screenSlider.getSelection();
						int max = screenSlider.getMaximum();
						
						if (!isLock) {
							checkLock.setSelection(true);
						}  
						
						if (isLock && selection >= max - thumb) {
							checkLock.setSelection(false);
						} 
						
						break;
					}
				}
			}
		});
		
		checkLock = new Button(buttonsComposite, SWT.CHECK);
		checkLock.setText("Lock");
		checkLock.setSelection(false);
		
		graphicComposite.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				paintGraphic(e.gc, graphicComposite.getShell());
			}
		});

		graphicComposite.getDisplay().timerExec(REFRESH_INTERVAL, new Runnable() {
			public void run() {
				if (!graphicComposite.isDisposed()) {
					Point size = windowShell.getSize();

					graphicComposite.redraw(0, 0, size.x - 10, size.y - BUTTON_PANEL_SIZE, false);

					graphicComposite.getDisplay().timerExec(REFRESH_INTERVAL, this);
				}
			}
		});
	}

	private void paintGraphic(GC gc, Shell shell) {
		Point size = windowShell.getSize();
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalIndent = 0;
		gd.verticalIndent = 0;
		gd.heightHint = size.y - BUTTON_PANEL_SIZE;
		graphicComposite.setLayoutData(gd);
		windowShell.layout(true, true);
		
		if (egd.getIterationsCount() == 0) {
			return;
		}
		
		constructGraphic(gc, size.x - 10, size.y - BUTTON_PANEL_SIZE, 50);
	}

	
	private void constructGraphic(GC gc, int width, int height, int defaultCellSize) {
		int realHeight = height - a - c;
		
		// code_7 BUG b depends divisor , divisor depends d => screen drugging when digits switches (breaks)!!!!!
		int endSegment = egd.getIterationsCount();
		if (checkLock.getSelection()) {
			endSegment = Math.min(getVisibleAreaStartItra(width - b - d, true) + (width - b - d - 1)* horizontalScale , endSegment);
		}
		divisor = getDivisor(realHeight, getVisibleAreaStartItra(width - b - d, checkLock.getSelection()), endSegment);
		
		b = getSizeInPixels((int)divisor * realHeight) + 12; 
		
		int realWidth = width - b - d;
		
		int visibleArea = getVisibleAreaStartItra(realWidth, false);
		int sliderSelection = screenSlider.getSelection();
		screenSlider.setMaximum(egd.getIterationsCount());
		screenSlider.setMinimum(0);
		screenSlider.setThumb(egd.getIterationsCount() - visibleArea);
		screenSlider.setEnabled(visibleArea > 0 || (checkLock.getSelection() && visibleArea > 0));
		screenSlider.setIncrement(horizontalScale);
		screenSlider.setPageIncrement(horizontalScale * cellSize);
		
		if (!checkLock.getSelection()) {
			screenSlider.setSelection(visibleArea);
		} else {
			screenSlider.setSelection((sliderSelection / horizontalScale) * horizontalScale);
		}
		
		paintGraphicNet(gc, height, realWidth, realHeight, colorDarkGreen);

		paintGraphicData(gc, height, realWidth, realHeight);
	}

	private void paintGraphicData(GC gc, int height, int realWidth, int realHeight) {
		int visibleAreaStartItr = getVisibleAreaStartItra(realWidth, checkLock.getSelection());
		
		int endSegment = egd.getIterationsCount();
		if (checkLock.getSelection()) {
			endSegment = Math.min(visibleAreaStartItr + (realWidth - 1)* horizontalScale , endSegment);
		}
		
		Integer x1 = 0;	Integer x2 = 0;
		Integer y1f = null;	Integer y1s = null;	Integer y2f; Integer y2s;
		for (int i = visibleAreaStartItr; i + horizontalScale < endSegment; i = i + horizontalScale) {
			y2f = (int) Math.round(egd.getFishesCount(i) / divisor);
			y2s = (int) Math.round(egd.getSharksCount(i) / divisor);
			if (y1f == null) y1f = y2f;
			if (y1s == null) y1s = y2s;
			paintGraphicLine(gc, colorFish, realHeight, x1, y1f, x2, y2f);
			paintGraphicLine(gc, colorShark, realHeight, x1, y1s, x2, y2s);

			x1 = x2; y1f = y2f;	y1s = y2s; x2++;
		}
	}

	private void paintGraphicNet(GC gc, int height, int realWidth, int realHeight, Color colorDarkGreen) {
		paintSpaceLine(gc, colorDarkGreen, height, b - 5, c, realWidth + b - 1, c);
		paintSpaceLine(gc, colorDarkGreen, height, b - 5, c - 1, realWidth + b - 1, c - 1);

		paintSpaceLine(gc, colorDarkGreen, height, b, c - 5, b, c + realHeight - 1);
		paintSpaceLine(gc, colorDarkGreen, height, b - 1, c - 5, b - 1, c + realHeight - 1);

		//double divisor = getDivisor(realHeight, getVisibleArea(realWidth));
		paintNumber(gc, colorDigits, height, 0, b - 8 - getSizeInPixels(0), c + 2);
		for (int y = cellSize; y + 3 <= realHeight; y = y + cellSize) {
			paintSpaceLine(gc, colorDarkGreen, height, b - 3, y + c, b - 3 + realWidth - 1, y + c);
			int number = (int)(y * divisor);
			paintNumber(gc, colorDigits, height, number, Math.max(b - 6 - getSizeInPixels(number), 0), y + c + 2);
		}

		int maxXNumberSize = getSizeInPixels(getVisibleAreaStartItra(realWidth, checkLock.getSelection()) 
				+ realWidth * horizontalScale) + 4*2;
		int halfCellSize = (int)(maxXNumberSize / (double)cellSize / 2) + 1;
		
		int visibleAreaStartItr = getVisibleAreaStartItra(realWidth, checkLock.getSelection());
		int offcet = (cellSize - (visibleAreaStartItr / horizontalScale) % cellSize) % cellSize;
		
		paintNumber(gc, colorDigits, height, visibleAreaStartItr, b - getSizeInPixels(visibleAreaStartItr) / 2, c - 16);
		
		int step = 0;
		for (int x = offcet; x + 3 <= realWidth; x = x + cellSize) {
			int itrx = visibleAreaStartItr + x * horizontalScale;
			int distance = halfCellSize * cellSize * horizontalScale;
			if (step >= halfCellSize && itrx % distance == 0) {
				if ((itrx / distance) % 2 == 1) {
				// short line
					paintSpaceLine(gc, colorDarkGreen, height, b + x, c - 3, b + x, c - 4 + realHeight);
					paintNumber(gc, colorDigits, height, itrx, b + x - getSizeInPixels(itrx) / 2, c - 7);
				} else {
				// long	line
					paintSpaceLine(gc, colorDarkGreen, height, b + x, c - 12, b + x, c - 4 + realHeight);
					paintNumber(gc, colorDigits, height, itrx, b + x - getSizeInPixels(itrx) / 2, c - 16);
				} 
			} else {
				paintSpaceLine(gc, colorDarkGreen, height, b + x, c - 3, b + x, c - 4 + realHeight);
			}

			step++;
		}
		
	}
	
	private double getDivisor(int realHeight, int visibleAreaStartItr, int visibleAreaEndOperation) {
		int maxInhabitants = 0;
		for (int i = visibleAreaStartItr; i < visibleAreaEndOperation; i++) {
			maxInhabitants = Math.max(maxInhabitants, Math.max(egd.getFishesCount(i), egd.getSharksCount(i)));
		}

		int realVSpace = realHeight - e;
		double divisor = (double)maxInhabitants / realVSpace;
		return divisor;
	}
	
	private int getVisibleAreaStartItra(int realWidth, boolean isLock) {
		int visibleAreaStartItr;
		
		if (isLock) {
			visibleAreaStartItr = screenSlider.getSelection();
		} else {
			int realHSpace = (realWidth - f) * horizontalScale;
			visibleAreaStartItr = Math.max(0, 
					(egd.getIterationsCount() / horizontalScale) * horizontalScale - realHSpace);
		}
		
		return visibleAreaStartItr;
	}
	

}
