package ru.vermilion.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;

import ru.vermilion.basic.CommonHelper;

import java.util.concurrent.atomic.AtomicInteger;

public class EllipseGraphicThreadWindow extends GraphicThreadWindow {

	private static final int REFRESH_INTERVAL = 1000;
	
	private static final int BUTTON_PANEL_SIZE = 55 + 50;
	
	private Slider fromSlider;
	private Slider tillSlider;
	private Spinner fromSpinner;
	private Spinner tillSpinner;
	
	private Slider diapazonSlider;
	private Spinner diapazonSpinner;
	
	private Button autoAdjustCheckBox;
	private Button showAllCheckBox;

	public EllipseGraphicThreadWindow(AtomicInteger createContentSynchronizer) {
		super(createContentSynchronizer);
	}

	protected void configureWindow() {
		windowShell.setText("Ellipse Data Graphic");
		windowShell.setBounds(500, 360, width, height);
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

		buttonsComposite.setLayout(new GridLayout(4, false));
		
		fromSlider = new Slider(buttonsComposite, SWT.HORIZONTAL);
		gd = new GridData(GridData.FILL_BOTH);
		fromSlider.setLayoutData(gd);
		fromSpinner = new Spinner(buttonsComposite, SWT.BORDER);
		
		diapazonSlider = new Slider(buttonsComposite, SWT.HORIZONTAL);
		diapazonSpinner = new Spinner(buttonsComposite, SWT.BORDER);
		
		tillSlider = new Slider(buttonsComposite, SWT.HORIZONTAL);
		gd = new GridData(GridData.FILL_BOTH);
		tillSlider.setLayoutData(gd);
		tillSpinner = new Spinner(buttonsComposite, SWT.BORDER);
		
		autoAdjustCheckBox = new Button(buttonsComposite, SWT.CHECK);
		autoAdjustCheckBox.setText("Auto Adjust");
		showAllCheckBox = new Button(buttonsComposite, SWT.CHECK);
		showAllCheckBox.setText("Show All");
		
		graphicComposite.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				paintGraphic(e.gc, graphicComposite.getShell());
			}
		});
		
		initComponents();
		
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

	/// INIT
	
	private void initComponents() {
		configSlider(fromSlider, fromSpinner, new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				disableCheckboxes();
				
				fromSpinner.setSelection(fromSlider.getSelection());
			}
		});
		
		configSlider(tillSlider, tillSpinner, new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				disableCheckboxes();
				
				int sliderWidth = tillSlider.getSize().x;
				int add = 0;
				if (sliderWidth != 0) {
				   add = (int)(egd.getIterationsCount() * 15L / sliderWidth); // logic is (itrxc / width) * 15
				}
				
				tillSpinner.setSelection(Math.min(tillSlider.getSelection() + add, egd.getIterationsCount()));
			}
		});
		
		showAllCheckBox.setSelection(true);
		autoAdjustCheckBox.setSelection(false);
		
		showAllCheckBox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (showAllCheckBox.getSelection()) {
					autoAdjustCheckBox.setSelection(false);
				}
			}
		});
		
		autoAdjustCheckBox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (autoAdjustCheckBox.getSelection()) {
					showAllCheckBox.setSelection(false);
				}
			}
		});
		
		diapazonSlider.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				showAllCheckBox.setSelection(false);
				autoAdjustCheckBox.setSelection(true);

				int diapazonSpinnerValue = diapazonSpinner.getSelection();
				int diapazonSliderValue = diapazonSlider.getSelection();
				
				int sliderWidth = diapazonSlider.getSize().x;
				int add = 0;
				if (sliderWidth != 0) {
				   add = (int)(egd.getIterationsCount() * 15L / sliderWidth); // logic is (itrxc / width) * 15
				}
				
				switch (e.detail) {
				    case SWT.DRAG : {
				    	diapazonSpinner.setSelection(CommonHelper.minmax(0, 
				    			egd.getIterationsCount() - diapazonSliderValue - add - 2, egd.getIterationsCount()));
						break;
				    }
				    
				    case SWT.ARROW_UP : {
				    	diapazonSpinner.setSelection(Math.min(egd.getIterationsCount(), diapazonSpinnerValue + 1));
				    	break;
				    }
				    
				    case SWT.ARROW_DOWN : {
				    	diapazonSpinner.setSelection(Math.max(0, diapazonSpinnerValue - 1));
				    	break;
				    }
				    
				    case SWT.PAGE_UP : {
				    	diapazonSpinner.setSelection(Math.min(diapazonSpinnerValue + 10, egd.getIterationsCount()));
				    	break;
				    }
				    
				    case SWT.PAGE_DOWN : {
				    	diapazonSpinner.setSelection(Math.max(0, diapazonSpinnerValue - 10));
				    	break;
				    }
					
					default : {};
				}
				
			};
		});
	}

	private void configSlider(final Slider slider, final Spinner spinner, SelectionListener sliderSelectionListener) {
		slider.setIncrement(1);
		slider.setMinimum(0);
		
		slider.setMinimum(0);
		slider.setIncrement(1);
		slider.setPageIncrement(10);
		slider.setSelection(0);
		
		spinner.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				slider.setSelection(spinner.getSelection());
			}
		});
		
		slider.addSelectionListener(sliderSelectionListener);
	}
	
	//// Adjust
	
	private void adjustComponents() {
		adjustSlider(fromSlider, fromSpinner);
		adjustSlider(tillSlider, tillSpinner);
		
		int fromSliderValue = fromSlider.getSelection();
		int tillSliderValue = tillSlider.getSelection();
		
		if (tillSliderValue < fromSliderValue) {
			tillSlider.setSelection(fromSliderValue);
			tillSpinner.setSelection(fromSliderValue);
		}
		
		adjustSlider(diapazonSlider, diapazonSpinner);
		diapazonSlider.setSelection(egd.getIterationsCount() - diapazonSpinner.getSelection());
	}

	private void adjustSlider(final Slider slider, final Spinner spinner) {
		slider.setMaximum(egd.getIterationsCount());
		
		int sliderWidth = slider.getSize().x;
		if (sliderWidth != 0) {
			slider.setThumb(egd.getIterationsCount() * 15 / sliderWidth);
		}
		
		spinner.setMaximum(egd.getIterationsCount());
	}
	
	private void paintGraphic(GC gc, Shell shell) {
		// TODO Here spinners and sliders are recalculated;
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
		
		adjustComponents();
		
		constructGraphic(gc, size.x - 10, size.y - BUTTON_PANEL_SIZE, 50);
	}
	
	int bNext = 6 * 4 + 12; // TODO may be choose the same approach in EmpireGraphic ..
	private void constructGraphic(GC gc, int width, int height, int defaultCellSize) {
		int realHeight = height - a - c;
		
		b = bNext; 
		
		int realWidth = width - b - d;
		
		int maxIteration = egd.getIterationsCount(); 
		int fromItrx;
		int tillItrx;
		
		if (showAllCheckBox.getSelection()) {
			fromItrx = 0;
			tillItrx = maxIteration;
			fromSlider.setSelection(0);
			fromSpinner.setSelection(0);
			tillSlider.setSelection(tillItrx);
			tillSpinner.setSelection(tillItrx);
			
			diapazonSlider.setSelection(0);
			diapazonSpinner.setSelection(maxIteration);
		} else {
			fromItrx = Math.max(0, fromSpinner.getSelection());
			tillItrx = Math.min(tillSpinner.getSelection(), maxIteration);
		}
		
		if (autoAdjustCheckBox.getSelection()) {
			fromItrx = CommonHelper.minmax(0, maxIteration - diapazonSpinner.getSelection(), maxIteration); 
			tillItrx = maxIteration;
			
			fromSlider.setSelection(fromItrx);
			fromSpinner.setSelection(fromItrx);
			tillSlider.setSelection(tillItrx);
			tillSpinner.setSelection(tillItrx);
		}
		
    	if (fromItrx > tillItrx) {
			fromItrx = tillItrx;
		}
		
		TwinDivisor divisors = getHVDivisor(realHeight, realWidth, fromItrx, tillItrx);
		bNext = getSizeInPixels(divisors.maxSharks) + 15;
		
		paintGraphicNet(gc, height, realWidth, realHeight, colorDarkGreen, divisors);
		
		paintGraphicData(gc, realHeight, fromItrx, tillItrx, divisors);
	}
	
	private void paintGraphicNet(GC gc, int height, int realWidth, int realHeight, 
			Color colorDarkGreen, TwinDivisor divisors) {
		paintSpaceLine(gc, colorDarkGreen, height, b - 5, c, realWidth + b - 1, c);
		paintSpaceLine(gc, colorDarkGreen, height, b - 5, c - 1, realWidth + b - 1, c - 1);

		paintSpaceLine(gc, colorDarkGreen, height, b, c - 5, b, c + realHeight - 1);
		paintSpaceLine(gc, colorDarkGreen, height, b - 1, c - 5, b - 1, c + realHeight - 1);

		paintNumber(gc, colorDigits, height, 0, b - 8 - getSizeInPixels(0), c + 2);
		for (int y = cellSize; y + 3 <= realHeight; y = y + cellSize) {
			paintSpaceLine(gc, colorDarkGreen, height, b - 3, y + c, b - 3 + realWidth - 1, y + c);
			int number = (int)(y * divisors.sharkDivisor);
			// code_7 todo!!!
			//int number = (int)(y * divisor);
			paintNumber(gc, colorDigits, height, number, Math.max(b - 6 - getSizeInPixels(number), 0), y + c + 2);
		}

		int maxXNumberSize = getSizeInPixels(divisors.maxFishes) + 4*2;
		int halfCellSize = (int)(maxXNumberSize / (double)cellSize / 2) + 1;
		
		/// numbers and V-Grid
		boolean isLongLine = true; int i = 0;
		for (int x = 0; x <= realWidth; x = x + cellSize) {
			i++;
			int cnt = (int)(x * divisors.fishDivisor);
			
			if (i % halfCellSize == 0) {
				if (isLongLine) {
					// short line
					paintSpaceLine(gc, colorDarkGreen, height, b + x, c - 3, b + x, c - 4 + realHeight);
					paintNumber(gc, colorDigits, height, cnt, b + x - getSizeInPixels(cnt) / 2, c - 7);
				} else {
					// long	line
					paintSpaceLine(gc, colorDarkGreen, height, b + x, c - 12, b + x, c - 4 + realHeight);
					paintNumber(gc, colorDigits, height, cnt, b + x - getSizeInPixels(cnt) / 2, c - 16);
				}
				isLongLine = !isLongLine;
			} else {
				paintSpaceLine(gc, colorDarkGreen, height, b + x, c - 3, b + x, c - 4 + realHeight);
			}
		}
		
	}

	private void paintGraphicData(GC gc, int realHeight, int fromItrx, int tillItrx, TwinDivisor divisors) {
		Integer x1f = null;	int x2f = 0;
		Integer y1s = null;	Integer y2s = 0;
		for (int i = fromItrx; i < tillItrx; i++) {
			x2f = (int) Math.round(egd.getFishesCount(i) / divisors.fishDivisor);
			y2s = (int) Math.round(egd.getSharksCount(i) / divisors.sharkDivisor);
			if (y1s == null) y1s = y2s;
			if (x1f == null) x1f = x2f;
			paintGraphicLine(gc, colorFish, realHeight, x1f, y1s, x2f, y2s);

			x1f = x2f; y1s = y2s;	
		}
	}
	
	private TwinDivisor getHVDivisor(int realHeight,int realWidth, int visibleAreaStartItr, int visibleAreaEndOperation) {
		int maxFishes = 0, maxSharks = 0;
		for (int i = visibleAreaStartItr; i < visibleAreaEndOperation; i++) {
			maxFishes = Math.max(maxFishes, egd.getFishesCount(i));
			maxSharks = Math.max(maxSharks, egd.getSharksCount(i));
		}

		int realVSpace = realHeight - e;
		int realHSpace = realWidth - f;
		
		TwinDivisor divisors = new TwinDivisor();
		divisors.fishDivisor = (double)maxFishes / realHSpace;
		divisors.sharkDivisor = (double)maxSharks / realVSpace;
		divisors.maxFishes = maxFishes;
		divisors.maxSharks = maxSharks;
				
		return divisors;
	}
	
	private static class TwinDivisor {
		double sharkDivisor, fishDivisor;
		int maxFishes, maxSharks;
	}

	private void disableCheckboxes() {
		showAllCheckBox.setSelection(false);
		autoAdjustCheckBox.setSelection(false);
	}
	
}
