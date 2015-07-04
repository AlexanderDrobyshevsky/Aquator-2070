package ru.vermilion.model;

import java.util.ArrayList;
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

	private enum CycleState {
		STATE_1(new ICycleState() {
			@Override
			public EmpiricGraphicData.CycleState handle(EmpiricGraphicData data) {
				if (data.fishes.size() < 10) {
					return STATE_1;
				}

				Differ3Values diffs = get3differ(data.fishes);
				if (diffs.a < diffs.b && diffs.b > diffs.c) {
					System.out.println("Switch state State_2");
					return CycleState.STATE_2;
				}

				return STATE_1;
			}
		}),

		STATE_2(new ICycleState() {
			@Override
			public CycleState handle(EmpiricGraphicData data) {
				Differ3Values diffs = get3differ(data.sharks);
				if (diffs.a < diffs.b && diffs.b > diffs.c) {
					System.out.println("Switch state State_3");
					return CycleState.STATE_3;
				}

				return STATE_2;
			}
		}),

		STATE_3(new ICycleState() {
			@Override
			public CycleState handle(EmpiricGraphicData data) {
				Differ3Values diffs = get3differ(data.fishes);
				if (diffs.a > diffs.b && diffs.b < diffs.c) {
					System.out.println("Switch state State_4");
					return CycleState.STATE_4;
				}

				return STATE_3;
			}
		}),

		STATE_4(new ICycleState() {
			@Override
			public CycleState handle(EmpiricGraphicData data) {
				Differ3Values diffs = get3differ(data.sharks);
				if (diffs.a > diffs.b && diffs.b < diffs.c) {
					System.out.println("Switch state State_1");
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

	private static class Differ3Values {
		int a,b,c;

		public Differ3Values(int a, int b, int c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}

	}

	private static Differ3Values get3differ(List<Integer> collection) {
		assert collection.size() >= 3;
		int a, b, c;
		int i = collection.size() - 3;
		a = collection.get(i + 2);
		b = collection.get(i + 1);
		while (a == b && i >= 0) {
			b = collection.get(i);
			i--;
		}

		c = collection.get(i);
		while (b == c && i > 0) {
			i--;
			c = collection.get(i);
		}

		return new Differ3Values(c,b,a);
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
