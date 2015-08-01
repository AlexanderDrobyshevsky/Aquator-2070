package ru.vermilion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import ru.vermilion.basic.CommonHelper;


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


	private Menu menuBar,  systemMenu, helpMenu;

	private MenuItem systemMenuHeader, helpMenuHeader;

	// System Items
	private MenuItem setDefaultsItem, exitItem;

	// Help Items
	private MenuItem aboutItem;


	
	public PlanetInitialConfigurationWindow(Composite parent) {
		parent.getShell().setLayout(new GridLayout());
		createMenu(parent.getShell());

		loadParameters();
		
        parent.getShell().setText("New World Configuration");
        GridLayout parentLayout = new GridLayout();
        parent.setLayout(parentLayout);
        parentLayout.numColumns = 2;

        createHeader(parent);

        // World's initial
        layoutH2Label(parent, "World's Parameters");

		new Label(parent, SWT.LEFT).setText("World Height: ");   worldHeightText = createSingleText(parent, worldHeight);
		new Label(parent, SWT.LEFT).setText("World Width: ");    worldWidthText = createSingleText(parent, worldWidth);
		new Label(parent, SWT.LEFT).setText("World Max Depth: "); worldMaxDepthText = createSingleText(parent, worldMaxDepth);

        // Fishes
        layoutH2Label(parent, "Fishes' Parameters");

		new Label(parent, SWT.LEFT).setText("Fish's Life Time: "); fishLifeTimeText = createSingleText(parent, fishLifeTime);
		new Label(parent, SWT.LEFT).setText("Fish's Max Reproduction: "); fishMaxReproductivesText = createSingleText(parent, fishMaxReproductives);
		new Label(parent, SWT.LEFT).setText("Fish's Pregnant Period: "); fishPregnantPeriodText = createSingleText(parent, fishPregnantPeriod);
		new Label(parent, SWT.LEFT).setText("Fish's Speed: "); fishSpeedText = createSingleText(parent, fishSpeed);
        new Label(parent, SWT.LEFT).setText("Fish's Max Depth: "); fishMaxDepthText = createSingleText(parent, fishMaxDepth);

		// Shark
        layoutH2Label(parent, "Sharks' Parameters");
		
		new Label(parent, SWT.LEFT).setText("Shark's Life Time: "); sharkLifeTimeText = createSingleText(parent, sharkLifeTime);
		new Label(parent, SWT.LEFT).setText("Shark's Max reproduction: "); sharkMaxReproductivesText = createSingleText(parent, sharkMaxReproductives);
		new Label(parent, SWT.LEFT).setText("Shark's Pregnant Period: "); sharkPregnantPeriodText = createSingleText(parent, sharkPregnantPeriod);
	    new Label(parent, SWT.LEFT).setText("Shark's Max Hunger Time: "); sharkMaxHungerTimeText = createSingleText(parent, sharkMaxHungerTime);
		new Label(parent, SWT.LEFT).setText("Shark's Speed: "); sharkSpeedText = createSingleText(parent, sharkSpeed);
        new Label(parent, SWT.LEFT).setText("Shark's Max Depth: "); sharkMaxDepthText = createSingleText(parent, sharkMaxDepth);

		// Initial population
        layoutH2Label(parent, "Initial Population Parameters");
		
		new Label(parent, SWT.LEFT).setText("Initial Fishes Count: "); initialFishesCountText = createSingleText(parent, initialFishesCount);
		new Label(parent, SWT.LEFT).setText("Initial Sharks Count: "); initialSharkesCountText = createSingleText(parent, initialSharkesCount);

        createActionButtons(parent);
		
		parent.layout(true);
		parent.pack();

        CommonHelper.centerShell(parent.getShell());
	}

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

    private void createHeader(Composite parent) {
        Composite headerComposite = new Composite(parent, SWT.FILL);

        GridLayout headerCompositeLayout = new GridLayout(2, false);
        headerCompositeLayout.marginHeight = 20;
        headerCompositeLayout.marginWidth = 30;
        headerCompositeLayout.horizontalSpacing = 35;
        headerComposite.setLayout(headerCompositeLayout);

        GridData gd = new GridData();
        gd.horizontalAlignment = SWT.CENTER;
        gd.horizontalSpan = 2;

        headerComposite.setLayoutData(gd);

        Label label = new Label(headerComposite, SWT.CENTER);
        label.setText("Planet Aquator's Parameters");
        CommonHelper.increaseControlFontSize(label, +7);
    }

    private void createActionButtons(Composite parent) {
        GridData gd;
        Composite buttonComposite = new Composite(parent, SWT.FILL);

        GridLayout compositeLayout = new GridLayout(2, false);
        compositeLayout.marginHeight = 20;
        compositeLayout.marginWidth = 30;
        compositeLayout.horizontalSpacing = 35;
        buttonComposite.setLayout(compositeLayout);

        gd = new GridData();
        gd.horizontalAlignment = SWT.CENTER;
        gd.horizontalSpan = 2;

        buttonComposite.setLayoutData(gd);

        Button saveButton = new Button(buttonComposite, SWT.PUSH);
        gd = new GridData();
        gd.heightHint = 40;
        gd.widthHint = 150;
        saveButton.setLayoutData(gd);
        saveButton.setText("Start Simulation");
        saveButton.setEnabled(true);
        CommonHelper.increaseControlFontSize(saveButton, +3, true);
        saveButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                isOK = true;
                saveParameters();
            }
        });

        Button cancelButton = new Button(buttonComposite, SWT.PUSH);
        gd = new GridData(GridData.END);
        gd.minimumWidth = 400;
        gd.heightHint = 40;
        gd.widthHint = 130;
        cancelButton.setLayoutData(gd);
        cancelButton.setText("Cancel and Exit");
        cancelButton.setEnabled(true);
        CommonHelper.increaseControlFontSize(cancelButton, +3);
        cancelButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                //System.exit(0);
                isOK = false;
            }
        });

        parent.getShell().setDefaultButton(saveButton);
    }

    private Text createSingleText(Composite parent, long value) {
        Text text = new Text(parent, SWT.BORDER | SWT.SINGLE);
        text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        text.setTextLimit(5);
        addVerifier(text);
        text.setText(value + "");

        return text;
    }

    private void layoutH2Label(Composite parent, String labelText) {
        Composite labelComposite = new Composite(parent, SWT.FILL);

        GridLayout labelCompositeLayout = new GridLayout(2, false);
        labelCompositeLayout.marginHeight = 5;
        labelCompositeLayout.marginWidth = 30;
        labelCompositeLayout.horizontalSpacing = 35;
        labelComposite.setLayout(labelCompositeLayout);

        GridData gd = new GridData();
        gd.horizontalAlignment = SWT.CENTER;
        gd.horizontalSpan = 2;

        labelComposite.setLayoutData(gd);

        Label label = new Label(labelComposite, SWT.CENTER);
        label.setText(labelText);

        CommonHelper.increaseControlFontSize(label, +4);
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

    private void addVerifier(Text text) {
        final Pattern patternCompiled = Pattern.compile("^(0|[1-9][0-9]*)$");

        text.addVerifyListener(new VerifyListener() {
            public void verifyText(VerifyEvent e) {
                String currentText = ((Text)e.widget).getText();
                String newValue = currentText.substring(0, e.start) + e.text + currentText.substring(e.end);
                e.doit = patternCompiled.matcher(newValue).matches();
            }
        });
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
