package ru.vermilion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


public class PlanetInitialConfigurationWindow {
	
	private Boolean isOK = null;

	private Text worldHeightText;
	private Text worldWidthText;
	private Text worldMaxDepthText;

	private Text fishLifeTimeText;
	private Text fishMaxReproductivesText;
	private Text fishPregnantPeriodText;
	private Text fishSpeedText;
	private Text fishMaxDepthText;

	private Text sharkLifeTimeText;
	private Text sharkMaxReproductivesText;
	private Text sharkPregnantPeriodText;
	private Text sharkMaxHungerTimeText;
	private Text sharkSpeedText;
	private Text sharkMaxDepthText;

	private Text initialFishesCountText;
	private Text initialSharkesCountText;

	private int worldHeight;
	private int worldWidth;
	private int worldMaxDepth;

	private int fishLifeTime;
	private int fishMaxReproductives;
	private int fishPregnantPeriod;
	private int fishSpeed;
	private int fishMaxDepth;

	private int sharkLifeTime;
	private int sharkMaxReproductives;
	private int sharkPregnantPeriod;
	private int sharkMaxHungerTime;
	private int sharkSpeed;
	private int sharkMaxDepth;

	private int initialFishesCount;
	private int initialSharkesCount;
	
	public PlanetInitialConfigurationWindow(Composite parent) {
		loadParameters();
		
		parent.setSize(350, 650);
		parent.update();
		
		GridLayout parentLayout = new GridLayout();
		parent.setLayout(parentLayout);
		parentLayout.numColumns = 2;
		
		Label label = new Label(parent, SWT.LEFT);
		label.setText("");
		label = new Label(parent, SWT.LEFT);
		label.setText("Planet Aquator Parameters");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("World Height: ");

		worldHeightText = new Text(parent, SWT.BORDER | SWT.WRAP);
		worldHeightText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		worldHeightText.setTextLimit(5);
		worldHeightText.setText(worldHeight + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("World Width: ");
		
		worldWidthText = new Text(parent, SWT.BORDER | SWT.WRAP);
		worldWidthText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		worldWidthText.setTextLimit(5);
		worldWidthText.setText(worldWidth + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("World Max Depth: ");

		worldMaxDepthText = new Text(parent, SWT.BORDER | SWT.WRAP);
		worldMaxDepthText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		worldMaxDepthText.setTextLimit(5);
		worldMaxDepthText.setText(worldMaxDepth + "");
		
		new Label(parent, SWT.LEFT);
		new Label(parent, SWT.LEFT);
		
		// Fish
		label = new Label(parent, SWT.LEFT);
		label.setText("");
		label = new Label(parent, SWT.LEFT);
		label.setText("Fishes Parameters");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("fishLifeTimeText: ");
		
		fishLifeTimeText = new Text(parent, SWT.BORDER | SWT.WRAP);
		fishLifeTimeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fishLifeTimeText.setTextLimit(5);
		fishLifeTimeText.setText(fishLifeTime + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("fishMaxReproductivesText: ");
		
		fishMaxReproductivesText = new Text(parent, SWT.BORDER | SWT.WRAP);
		fishMaxReproductivesText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fishMaxReproductivesText.setTextLimit(5);
		fishMaxReproductivesText.setText(fishMaxReproductives + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("fishPregnantPeriodText: ");
		
		fishPregnantPeriodText = new Text(parent, SWT.BORDER | SWT.WRAP);
		fishPregnantPeriodText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fishPregnantPeriodText.setTextLimit(5);
		fishPregnantPeriodText.setText(fishPregnantPeriod + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("fishSpeedText: ");
		
		fishSpeedText = new Text(parent, SWT.BORDER | SWT.WRAP);
		fishSpeedText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fishSpeedText.setTextLimit(5);
		fishSpeedText.setText(fishSpeed + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("fishMaxDepthText: ");
		
		fishMaxDepthText = new Text(parent, SWT.BORDER | SWT.WRAP);
		fishMaxDepthText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fishMaxDepthText.setTextLimit(5);
		fishMaxDepthText.setText(fishMaxDepth + "");
		
		// Shark
		label = new Label(parent, SWT.LEFT);
		label.setText("");
		label = new Label(parent, SWT.LEFT);
		label.setText("Sharkes Parameters");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("sharkLifeTimeText: ");
		
		sharkLifeTimeText = new Text(parent, SWT.BORDER | SWT.WRAP);
		sharkLifeTimeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sharkLifeTimeText.setTextLimit(5);
		sharkLifeTimeText.setText(sharkLifeTime + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("sharkMaxReproductivesText: ");
		
		sharkMaxReproductivesText = new Text(parent, SWT.BORDER | SWT.WRAP);
		sharkMaxReproductivesText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sharkMaxReproductivesText.setTextLimit(5);
		sharkMaxReproductivesText.setText(sharkMaxReproductives + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("sharkPregnantPeriodText: ");
		
		sharkPregnantPeriodText = new Text(parent, SWT.BORDER | SWT.WRAP);
		sharkPregnantPeriodText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sharkPregnantPeriodText.setTextLimit(5);
		sharkPregnantPeriodText.setText(sharkPregnantPeriod + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("sharkMaxHungerTimeText: ");
		
		sharkMaxHungerTimeText = new Text(parent, SWT.BORDER | SWT.WRAP);
		sharkMaxHungerTimeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sharkMaxHungerTimeText.setTextLimit(5);
		sharkMaxHungerTimeText.setText(sharkMaxHungerTime + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("sharkSpeedText: ");
		
		sharkSpeedText = new Text(parent, SWT.BORDER | SWT.WRAP);
		sharkSpeedText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sharkSpeedText.setTextLimit(5);
		sharkSpeedText.setText(sharkSpeed + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("sharkMaxDepthText: ");
		
		sharkMaxDepthText = new Text(parent, SWT.BORDER | SWT.WRAP);
		sharkMaxDepthText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sharkMaxDepthText.setTextLimit(5);
		sharkMaxDepthText.setText(sharkMaxDepth + "");
		
		// countes
		label = new Label(parent, SWT.LEFT);
		label.setText("");
		label = new Label(parent, SWT.LEFT);
		label.setText("Initial Count Parameters");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("initialFishesCountText: ");
		
		initialFishesCountText = new Text(parent, SWT.BORDER | SWT.WRAP);
		initialFishesCountText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		initialFishesCountText.setTextLimit(5);
		initialFishesCountText.setText(initialFishesCount + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("initialSharkesCountText: ");
		
		initialSharkesCountText = new Text(parent, SWT.BORDER | SWT.WRAP);
		initialSharkesCountText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		initialSharkesCountText.setTextLimit(5);
		initialSharkesCountText.setText(initialSharkesCount + "");
		
		///////////////
		new Label(parent, SWT.LEFT);
		new Label(parent, SWT.LEFT);
		
		
		Button saveButton = new Button(parent, SWT.PUSH);
		GridData gd = new GridData(GridData.END);
		gd.verticalAlignment = 100;
		gd.minimumHeight = 100;
		saveButton.setLayoutData(gd);
		saveButton.setText("Save/OK");
		saveButton.setEnabled(true);
		saveButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				isOK = true;
				saveParameters();
			}
		});
		
		Button cancelButton = new Button(parent, SWT.PUSH);
		gd = new GridData(GridData.END);
		gd.verticalAlignment = 100;
		gd.minimumHeight = 100;
		cancelButton.setLayoutData(gd);
		cancelButton.setText("Cancel");
		cancelButton.setEnabled(true);
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				System.exit(0);
				isOK = false;
			}
		});
		
		parent.layout(true);
	}
	
	public Boolean isOK() {
		return isOK;
	}
	
	public void dispose() {}
	
	private void loadParameters() {
		worldHeight = 180;
		worldWidth = 180;
		worldMaxDepth = 70;

		fishLifeTime = 70;
		fishMaxReproductives = 3;
		fishPregnantPeriod = 28;
		fishSpeed =  1;
		fishMaxDepth = 8;

		sharkLifeTime = 110;
		sharkMaxReproductives = 3;
		sharkPregnantPeriod = 55;
		sharkMaxHungerTime = 18;
		sharkSpeed = 3;
		sharkMaxDepth = 5;
		
		initialFishesCount = 12;
		initialSharkesCount = 9;
		
		Properties props = new Properties();
		
		FileInputStream fis;
		try {
			fis = new FileInputStream("world.config");
			props.load(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			worldHeight = Integer.valueOf(props.getProperty("worldHeight"));
			worldWidth = Integer.valueOf(props.getProperty("worldWidth"));
			worldMaxDepth = Integer.valueOf(props.getProperty("worldMaxDepth"));

			fishLifeTime = Integer.valueOf(props.getProperty("fishLifeTime"));
			fishMaxReproductives = Integer.valueOf(props.getProperty("fishMaxReproductives"));
			fishPregnantPeriod = Integer.valueOf(props.getProperty("fishPregnantPeriod"));
			fishSpeed = Integer.valueOf(props.getProperty("fishSpeed"));
			fishMaxDepth = Integer.valueOf(props.getProperty("fishMaxDepth"));

			sharkLifeTime = Integer.valueOf(props.getProperty("sharkLifeTime"));
			sharkMaxReproductives = Integer.valueOf(props.getProperty("sharkMaxReproductives"));
			sharkPregnantPeriod = Integer.valueOf(props.getProperty("sharkPregnantPeriod"));
			sharkMaxHungerTime = Integer.valueOf(props.getProperty("sharkMaxHungerTime"));
			sharkSpeed = Integer.valueOf(props.getProperty("sharkSpeed"));
			sharkMaxDepth = Integer.valueOf(props.getProperty("sharkMaxDepth"));

			initialFishesCount = Integer.valueOf(props.getProperty("initialFishesCount"));
			initialSharkesCount = Integer.valueOf(props.getProperty("initialSharkesCount"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void saveParameters() {
		worldHeight = Integer.valueOf(worldHeightText.getText());
		worldWidth = Integer.valueOf(worldWidthText.getText());
		worldMaxDepth = Integer.valueOf(worldMaxDepthText.getText());

		fishLifeTime = Integer.valueOf(fishLifeTimeText.getText());
		fishMaxReproductives = Integer.valueOf(fishMaxReproductivesText.getText());
		fishPregnantPeriod = Integer.valueOf(fishPregnantPeriodText.getText());
		fishSpeed = Integer.valueOf(fishSpeedText.getText());
		fishMaxDepth = Integer.valueOf(fishMaxDepthText.getText());

		sharkLifeTime = Integer.valueOf(sharkLifeTimeText.getText());
		sharkMaxReproductives = Integer.valueOf(sharkMaxReproductivesText.getText());
		sharkPregnantPeriod = Integer.valueOf(sharkPregnantPeriodText.getText());
		sharkMaxHungerTime = Integer.valueOf(sharkMaxHungerTimeText.getText());
		sharkSpeed = Integer.valueOf(sharkSpeedText.getText());
		sharkMaxDepth = Integer.valueOf(sharkMaxDepthText.getText());
		
		initialFishesCount = Integer.valueOf(initialFishesCountText.getText());;
		initialSharkesCount = Integer.valueOf(initialSharkesCountText.getText());;
		
		Properties props = new Properties();
		
		props.setProperty("worldHeight", worldHeight + "");
		props.setProperty("worldWidth", worldWidth + "");
		props.setProperty("worldMaxDepth", worldMaxDepth + "");
		
		props.setProperty("fishLifeTime", fishLifeTime + "");
		props.setProperty("fishMaxReproductives", fishMaxReproductives + "");
		props.setProperty("fishPregnantPeriod", fishPregnantPeriod + "");
		props.setProperty("fishSpeed", fishSpeed + "");
		props.setProperty("fishMaxDepth", fishMaxDepth + "");
		
		props.setProperty("sharkLifeTime", sharkLifeTime + "");
		props.setProperty("sharkMaxReproductives", sharkMaxReproductives + "");
		props.setProperty("sharkPregnantPeriod", sharkPregnantPeriod + "");
		props.setProperty("sharkMaxHungerTime", sharkMaxHungerTime + "");
		props.setProperty("sharkSpeed", sharkSpeed + "");
		props.setProperty("sharkMaxDepth", sharkMaxDepth + "");
		
		props.setProperty("initialFishesCount", initialFishesCount + "");
		props.setProperty("initialSharkesCount", initialSharkesCount + "");
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("world.config");

			props.store(fos, "World Config");
			fos.flush();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public int getWorldHeight() {
		return worldHeight;
	}

	public int getWorldWidth() {
		return worldWidth;
	}

	public int getWorldMaxDepth() {
		return worldMaxDepth;
	}

	public int getFishLifeTime() {
		return fishLifeTime;
	}

	public int getFishMaxReproductives() {
		return fishMaxReproductives;
	}

	public int getFishPregnantPeriod() {
		return fishPregnantPeriod;
	}

	public int getFishSpeed() {
		return fishSpeed;
	}

	public int getFishMaxDepth() {
		return fishMaxDepth;
	}

	public int getSharkLifeTime() {
		return sharkLifeTime;
	}

	public int getSharkMaxReproductives() {
		return sharkMaxReproductives;
	}

	public int getSharkPregnantPeriod() {
		return sharkPregnantPeriod;
	}

	public int getSharkMaxHungerTime() {
		return sharkMaxHungerTime;
	}

	public int getSharkSpeed() {
		return sharkSpeed;
	}

	public int getSharkMaxDepth() {
		return sharkMaxDepth;
	}

	public int getInitialFishesCount() {
		return initialFishesCount;
	}

	public int getInitialSharkesCount() {
		return initialSharkesCount;
	}
}
