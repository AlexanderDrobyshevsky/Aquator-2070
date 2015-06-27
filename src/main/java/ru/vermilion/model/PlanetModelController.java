package ru.vermilion.model;

import java.util.ArrayList;
import java.util.List;

// Model and Controller
public class PlanetModelController {

	private static final PlanetModelController instance = new PlanetModelController();
	
	List<IModelListener> modelListeners = new ArrayList<IModelListener>(); 
	
	private PlanetModelController() {
		
	}
	
	public static PlanetModelController getInstance() {
		return instance;
	}
	
	public void addModelListener(IModelListener modelListener) {
		modelListeners.add(modelListener);
	}
	
	public void update() {
		for (IModelListener modelListener : modelListeners) {
			modelListener.modelChanged();
		}
	}
	
	public void removeModelListener(IModelListener modelListener) {
		modelListeners.remove(modelListener);
	}
	
}
