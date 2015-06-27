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
	
	Text worldHeight;
	Text worldWidth;
	Text worldMaxDepth;
	
	Text fishLifeTime;
	Text fishMaxReproductives;
	Text fishPregnantPeriod;
	Text fishSpeed;
	Text fishMaxDepth;
	
	Text sharkLifeTime;
	Text sharkMaxReproductives;
	Text sharkPregnantPeriod;
	Text sharkMaxHungerTime;
	Text sharkSpeed;
	Text sharkMaxDepth;
	
	Text initialFishesCount;
	Text initialSharkesCount;
	
	int worldHeight1;
	int worldWidth1;
	int worldMaxDepth1;
	
	int fishLifeTime1;
	int fishMaxReproductives1;
	int fishPregnantPeriod1;
	int fishSpeed1;
	int fishMaxDepth1;
	
	int sharkLifeTime1;
	int sharkMaxReproductives1;
	int sharkPregnantPeriod1;
	int sharkMaxHungerTime1;
	int sharkSpeed1;
	int sharkMaxDepth1;
	
	int initialFishesCount1;
	int initialSharkesCount1;
	
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

		worldHeight = new Text(parent, SWT.BORDER | SWT.WRAP);
		worldHeight.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		worldHeight.setTextLimit(5);
		worldHeight.setText(worldHeight1 + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("World Width: ");
		
		worldWidth = new Text(parent, SWT.BORDER | SWT.WRAP);
		worldWidth.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		worldWidth.setTextLimit(5);
		worldWidth.setText(worldWidth1 + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("World Max Depth: ");

		worldMaxDepth = new Text(parent, SWT.BORDER | SWT.WRAP);
		worldMaxDepth.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		worldMaxDepth.setTextLimit(5);
		worldMaxDepth.setText(worldMaxDepth1 + "");
		
		new Label(parent, SWT.LEFT);
		new Label(parent, SWT.LEFT);
		
		// Fish
		label = new Label(parent, SWT.LEFT);
		label.setText("");
		label = new Label(parent, SWT.LEFT);
		label.setText("Fishes Parameters");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("fishLifeTime: ");
		
		fishLifeTime = new Text(parent, SWT.BORDER | SWT.WRAP);
		fishLifeTime.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fishLifeTime.setTextLimit(5);
		fishLifeTime.setText(fishLifeTime1 + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("fishMaxReproductives: ");
		
		fishMaxReproductives = new Text(parent, SWT.BORDER | SWT.WRAP);
		fishMaxReproductives.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fishMaxReproductives.setTextLimit(5);
		fishMaxReproductives.setText(fishMaxReproductives1 + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("fishPregnantPeriod: ");
		
		fishPregnantPeriod = new Text(parent, SWT.BORDER | SWT.WRAP);
		fishPregnantPeriod.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fishPregnantPeriod.setTextLimit(5);
		fishPregnantPeriod.setText(fishPregnantPeriod1 + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("fishSpeed: ");
		
		fishSpeed = new Text(parent, SWT.BORDER | SWT.WRAP);
		fishSpeed.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fishSpeed.setTextLimit(5);
		fishSpeed.setText(fishSpeed1 + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("fishMaxDepth: ");
		
		fishMaxDepth = new Text(parent, SWT.BORDER | SWT.WRAP);
		fishMaxDepth.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fishMaxDepth.setTextLimit(5);
		fishMaxDepth.setText(fishMaxDepth1 + "");
		
		// Shark
		label = new Label(parent, SWT.LEFT);
		label.setText("");
		label = new Label(parent, SWT.LEFT);
		label.setText("Sharkes Parameters");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("sharkLifeTime: ");
		
		sharkLifeTime = new Text(parent, SWT.BORDER | SWT.WRAP);
		sharkLifeTime.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sharkLifeTime.setTextLimit(5);
		sharkLifeTime.setText(sharkLifeTime1 + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("sharkMaxReproductives: ");
		
		sharkMaxReproductives = new Text(parent, SWT.BORDER | SWT.WRAP);
		sharkMaxReproductives.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sharkMaxReproductives.setTextLimit(5);
		sharkMaxReproductives.setText(sharkMaxReproductives1 + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("sharkPregnantPeriod: ");
		
		sharkPregnantPeriod = new Text(parent, SWT.BORDER | SWT.WRAP);
		sharkPregnantPeriod.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sharkPregnantPeriod.setTextLimit(5);
		sharkPregnantPeriod.setText(sharkPregnantPeriod1 + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("sharkMaxHungerTime: ");
		
		sharkMaxHungerTime = new Text(parent, SWT.BORDER | SWT.WRAP);
		sharkMaxHungerTime.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sharkMaxHungerTime.setTextLimit(5);
		sharkMaxHungerTime.setText(sharkMaxHungerTime1 + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("sharkSpeed: ");
		
		sharkSpeed = new Text(parent, SWT.BORDER | SWT.WRAP);
		sharkSpeed.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sharkSpeed.setTextLimit(5);
		sharkSpeed.setText(sharkSpeed1 + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("sharkMaxDepth: ");
		
		sharkMaxDepth = new Text(parent, SWT.BORDER | SWT.WRAP);
		sharkMaxDepth.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sharkMaxDepth.setTextLimit(5);
		sharkMaxDepth.setText(sharkMaxDepth1 + "");
		
		// countes
		label = new Label(parent, SWT.LEFT);
		label.setText("");
		label = new Label(parent, SWT.LEFT);
		label.setText("Initial Count Parameters");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("initialFishesCount: ");
		
		initialFishesCount = new Text(parent, SWT.BORDER | SWT.WRAP);
		initialFishesCount.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		initialFishesCount.setTextLimit(5);
		initialFishesCount.setText(initialFishesCount1 + "");
		
		label = new Label(parent, SWT.LEFT);
		label.setText("initialSharkesCount: ");
		
		initialSharkesCount = new Text(parent, SWT.BORDER | SWT.WRAP);
		initialSharkesCount.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		initialSharkesCount.setTextLimit(5);
		initialSharkesCount.setText(initialSharkesCount1 + "");
		
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
	
	public int getX() {
		return Integer.valueOf(worldHeight.getText());
	}
	
	public int getY() {
		return Integer.valueOf(worldWidth.getText());
	}
	
	
	public void dispose() {}
	
	private void loadParameters() {
		worldHeight1 = 180;
		worldWidth1 = 180;
		worldMaxDepth1 = 70;

		fishLifeTime1 = 70;
		fishMaxReproductives1 = 3;
		fishPregnantPeriod1 = 28;
		fishSpeed1 =  1;
		fishMaxDepth1 = 8;

		sharkLifeTime1 = 110;
		sharkMaxReproductives1 = 3; 
		sharkPregnantPeriod1 = 55;
		sharkMaxHungerTime1 = 18;
		sharkSpeed1 = 3;
		sharkMaxDepth1 = 5;
		
		initialFishesCount1 = 12;
		initialSharkesCount1 = 9;
		
		Properties props = new Properties();
		
		FileInputStream fis = null;
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
			worldHeight1 = Integer.valueOf(props.getProperty("worldHeight"));
			worldWidth1 = Integer.valueOf(props.getProperty("worldWidth"));
			worldMaxDepth1 = Integer.valueOf(props.getProperty("worldMaxDepth"));

			fishLifeTime1 = Integer.valueOf(props.getProperty("fishLifeTime"));
			fishMaxReproductives1 = Integer.valueOf(props.getProperty("fishMaxReproductives"));
			fishPregnantPeriod1 = Integer.valueOf(props.getProperty("fishPregnantPeriod"));
			fishSpeed1 = Integer.valueOf(props.getProperty("fishSpeed"));
			fishMaxDepth1 = Integer.valueOf(props.getProperty("fishMaxDepth"));

			sharkLifeTime1 = Integer.valueOf(props.getProperty("sharkLifeTime"));
			sharkMaxReproductives1 = Integer.valueOf(props.getProperty("sharkMaxReproductives"));
			sharkPregnantPeriod1 = Integer.valueOf(props.getProperty("sharkPregnantPeriod"));
			sharkMaxHungerTime1 = Integer.valueOf(props.getProperty("sharkMaxHungerTime"));
			sharkSpeed1 = Integer.valueOf(props.getProperty("sharkSpeed"));
			sharkMaxDepth1 = Integer.valueOf(props.getProperty("sharkMaxDepth"));

			initialFishesCount1 = Integer.valueOf(props.getProperty("initialFishesCount"));
			initialSharkesCount1 = Integer.valueOf(props.getProperty("initialSharkesCount"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void saveParameters() {
		worldHeight1 = Integer.valueOf(worldHeight.getText());
		worldWidth1 = Integer.valueOf(worldWidth.getText());
		worldMaxDepth1 = Integer.valueOf(worldMaxDepth.getText());

		fishLifeTime1 = Integer.valueOf(fishLifeTime.getText());
		fishMaxReproductives1 = Integer.valueOf(fishMaxReproductives.getText());
		fishPregnantPeriod1 = Integer.valueOf(fishPregnantPeriod.getText());
		fishSpeed1 = Integer.valueOf(fishSpeed.getText());
		fishMaxDepth1 = Integer.valueOf(fishMaxDepth.getText());

		sharkLifeTime1 = Integer.valueOf(sharkLifeTime.getText());
		sharkMaxReproductives1 = Integer.valueOf(sharkMaxReproductives.getText());
		sharkPregnantPeriod1 = Integer.valueOf(sharkPregnantPeriod.getText());
		sharkMaxHungerTime1 = Integer.valueOf(sharkMaxHungerTime.getText());
		sharkSpeed1 = Integer.valueOf(sharkSpeed.getText());
		sharkMaxDepth1 = Integer.valueOf(sharkMaxDepth.getText());
		
		initialFishesCount1 = Integer.valueOf(initialFishesCount.getText());;
		initialSharkesCount1 = Integer.valueOf(initialSharkesCount.getText());;
		
		Properties props = new Properties();
		
		props.setProperty("worldHeight", worldHeight1 + "");
		props.setProperty("worldWidth", worldWidth1 + "");
		props.setProperty("worldMaxDepth", worldMaxDepth1 + "");
		
		props.setProperty("fishLifeTime", fishLifeTime1 + "");
		props.setProperty("fishMaxReproductives", fishMaxReproductives1 + "");
		props.setProperty("fishPregnantPeriod", fishPregnantPeriod1 + "");
		props.setProperty("fishSpeed", fishSpeed1 + "");
		props.setProperty("fishMaxDepth",fishMaxDepth1  + "");
		
		props.setProperty("sharkLifeTime", sharkLifeTime1 + "");
		props.setProperty("sharkMaxReproductives", sharkMaxReproductives1 + "");
		props.setProperty("sharkPregnantPeriod", sharkPregnantPeriod1 + "");
		props.setProperty("sharkMaxHungerTime", sharkMaxHungerTime1 + "");
		props.setProperty("sharkSpeed", sharkSpeed1 + "");
		props.setProperty("sharkMaxDepth", sharkMaxDepth1 + "");
		
		props.setProperty("initialFishesCount", initialFishesCount1 + "");
		props.setProperty("initialSharkesCount", initialSharkesCount1 + "");
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("world.config");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			props.store(fos, "World Config");
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
