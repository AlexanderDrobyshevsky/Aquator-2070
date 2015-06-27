package ru.vermilion.basic;

import java.util.Random;

public class AquatorPlanetConfiguration {

	private static int width = 400;
	
	private static int height = 300;
	
	private static int depth = 100;
	
	private static Random randomGenerator = new Random();

	
	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}
	
	public static int getDepth() {
		return depth;
	}
	
	public static void setWidth(int width) {
		AquatorPlanetConfiguration.width = width;
	}

	public static void setHeight(int height) {
		AquatorPlanetConfiguration.height = height;
	}
	
	public static void setDepth(int depth) {
		AquatorPlanetConfiguration.depth = depth;
	}

	public static Random getRandomGenerator() {
		return randomGenerator;
	}
	
}
