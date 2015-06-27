package ru.vermilion.basic;

// Immutable!!!
public class PlanetPosition {
	// tor position
	int y, x;

	public PlanetPosition(int y, int x) {
		super();
		this.y = y;
		this.x = x;
	}

	public PlanetPosition addPosition(PlanetPosition deltaPos) {
		return new PlanetPosition(y + deltaPos.y, x + deltaPos.x);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean equals(Object obj) {
		if (obj instanceof PlanetPosition) {
			PlanetPosition p = (PlanetPosition) obj;
			if (p.x == this.x && p.y == this.y) {
				return true;
			}
		}

		return false;
	}
	
	public String toString() {
		return "(y = " + y + "; x = " + x + ")";
	}

}
