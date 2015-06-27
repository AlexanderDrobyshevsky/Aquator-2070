package ru.vermilion.basic;

public class Sequences {

	private static long inhabitantSequence = 0;
	

	public static long getNewInhabitantId() {
		return ++inhabitantSequence;
	}

}
