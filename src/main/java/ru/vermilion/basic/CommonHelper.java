package ru.vermilion.basic;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import java.util.concurrent.TimeUnit;

public class CommonHelper {
	
	/**
	 * a <= number <= b 
	 *  
	 * @return bounded number;
	 */
	public static int minmax(int a, int number, int b) {
		return Math.max(a, Math.min(number, b)); 
	}


	public static String getElapsedTime(long startTime) {
		long elapsedTime = System.currentTimeMillis() - startTime;

		final long d = TimeUnit.MILLISECONDS.toDays(elapsedTime);
		final long hr = TimeUnit.MILLISECONDS.toHours(elapsedTime - TimeUnit.DAYS.toMillis(d));
		final long min = TimeUnit.MILLISECONDS.toMinutes(elapsedTime - TimeUnit.DAYS.toMillis(d) - TimeUnit.HOURS.toMillis(hr));
		final long sec = TimeUnit.MILLISECONDS.toSeconds(elapsedTime - TimeUnit.DAYS.toMillis(d) - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
		final long ms = TimeUnit.MILLISECONDS.toMillis(elapsedTime - TimeUnit.DAYS.toMillis(d) - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec));

		return String.format("%d %02d:%02d:%02d", d, hr, min, sec, ms);
	}



	public static void increaseControlFontSize(Control label, int increaseSize, boolean isMakeBold) {
		int fontSize;
		try {
			FontData[] fD = label.getFont().getFontData();

			fontSize = fD[0].getHeight() + increaseSize;

			FontData[] fontData = label.getFont().getFontData();
			fontData[0].setHeight(fontSize);
			if (isMakeBold) {
				fontData[0].setStyle(SWT.BOLD);
			}
			final Font font;
			label.setFont(font = new Font(label.getDisplay(), fontData[0]));

			label.getShell().addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					if (!font.isDisposed()) {
						font.dispose();
					}
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void increaseControlFontSize(Control label, int increaseSize) {
		increaseControlFontSize(label, increaseSize, false);
	}

	public static void centerShell(Shell shell) {
		Monitor primary = shell.getDisplay().getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();

		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;

		shell.setLocation(x, y);
	}

	public static void centerShell(Shell parentShell, Shell centerShell) {
		Rectangle bounds = parentShell.getBounds();
		Rectangle rect = centerShell.getBounds();

		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;

		centerShell.setLocation(x, y);
	}

}
