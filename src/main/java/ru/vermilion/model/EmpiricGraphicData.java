package ru.vermilion.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// TODO: Iterations Count is Long , not Int !!! 
// Another, more difficult aproach is needed!
public class EmpiricGraphicData {

	private final List<Integer> fishes = new ArrayList<Integer>();
	
	private final List<Integer> sharks = new ArrayList<Integer>();

	private long minFishes = Integer.MAX_VALUE,
			     maxFishes = 0,
			     minSharks = Integer.MAX_VALUE,
			     maxSharks = 0;

	private long cycle = 0;

	private CycleState cycleState = CycleState.STATE_1;

	private final static int APPROXIMATION = 12;

	private enum CycleState {
		STATE_1(new ICycleState() {
			@Override
			public EmpiricGraphicData.CycleState handle(EmpiricGraphicData data) {
				if (data.fishes.size() < APPROXIMATION + 2) {
					return STATE_1;
				}

				if (isValuesHasLocalTop(getDifferValues(data.fishes, APPROXIMATION), APPROXIMATION)) {
					return CycleState.STATE_2;
				}

				return STATE_1;
			}
		}),

		STATE_2(new ICycleState() {
			@Override
			public CycleState handle(EmpiricGraphicData data) {
				if (isValuesHasLocalTop(getDifferValues(data.sharks, APPROXIMATION), APPROXIMATION)) {
					return CycleState.STATE_3;
				}

				return STATE_2;
			}
		}),

		STATE_3(new ICycleState() {
			@Override
			public CycleState handle(EmpiricGraphicData data) {
				if (isValuesHasLocalDint(getDifferValues(data.fishes, APPROXIMATION), APPROXIMATION)) {
					return CycleState.STATE_4;
				}

				return STATE_3;
			}
		}),

		STATE_4(new ICycleState() {
			@Override
			public CycleState handle(EmpiricGraphicData data) {
				if (isValuesHasLocalDint(getDifferValues(data.sharks, APPROXIMATION), APPROXIMATION)) {
					data.cycle++;
					return CycleState.STATE_1;
				}

				return STATE_4;
			}
		});

		private ICycleState cycleStateHandler;

		CycleState(ICycleState cycleStateHandler) {
			this.cycleStateHandler = cycleStateHandler;
		}

		public CycleState switchState(EmpiricGraphicData data) {
			return cycleStateHandler.handle(data);
		};
	}

	private static List<Integer> getDifferValues(List<Integer> collection, int count) {
		List<Integer> differValues = new ArrayList<>();

		int i = collection.size() - 1;
		int value = collection.get(i);
		differValues.add(value);

		while (i > 0 && differValues.size() < count) {
			i--;
			value = collection.get(i);

			if (value != differValues.get(differValues.size() - 1)) {
				differValues.add(value);
			}
		}

		return differValues;
	}

	private static boolean isValuesHasLocalTop(List<Integer> collection, int necessaryCount) {
		assert necessaryCount >= 4;

		if (collection.size() != necessaryCount) {
			return false;
		}

		int i = 0;
		while (i + 2 < collection.size() && collection.get(i) < collection.get(i + 1)) {
			i++;
		}

		int j = collection.size() - 1;
		while (j - 1 > 0 && collection.get(j - 1) > collection.get(j)) {
			j--;
		}

		return i == j;
	}

	private static boolean isValuesHasLocalDint(List<Integer> collection, int necessaryCount) {
		assert necessaryCount >= 4;

		if (collection.size() != necessaryCount) {
			return false;
		}

		int i = 0;
		while (i + 2 < collection.size() && collection.get(i) > collection.get(i + 1)) {
			i++;
		}

		int j = collection.size() - 1;
		while (j - 1 > 0 && collection.get(j - 1) < collection.get(j)) {
			j--;
		}

		return i == j;
	}

	public interface ICycleState {
		CycleState handle(EmpiricGraphicData data);
	}
	
	private static EmpiricGraphicData instance = new EmpiricGraphicData();
	
	public static EmpiricGraphicData getInstance() {
		return instance;
	}
	
	public synchronized void add(int fishedCount, int sharksCount) {
		minFishes = Math.min(minFishes, fishedCount);
		maxFishes = Math.max(maxFishes, fishedCount);

		minSharks = Math.min(minSharks, sharksCount);
		maxSharks = Math.max(maxSharks, sharksCount);

		fishes.add(fishedCount);
		sharks.add(sharksCount);

		cycleState = cycleState.switchState(this);
	}
	
	public int getFishesCount(int iteration) {
		if (fishes.size() - 1 < iteration) {
			throw new RuntimeException("Invalid Iteration EmpireGraphicData!");
		}
		
		return fishes.get(iteration);
	}
	
	public int getSharksCount(int iteration) {
		if (sharks.size() - 1 < iteration) {
			throw new RuntimeException("Invalid Iteration EmpireGraphicData!");
		}
		
		return sharks.get(iteration);
	}
	
	public int getIterationsCount() {
		return fishes.size();
	}

	public long getMinFishes() {
		return minFishes;
	}

	public long getMaxFishes() {
		return maxFishes;
	}

	public long getMinSharks() {
		return minSharks;
	}

	public long getMaxSharks() {
		return maxSharks;
	}

	public long getCycle() {
		return cycle;
	}
}
