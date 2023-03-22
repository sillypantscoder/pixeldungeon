package com.sillypantscoder.pixeldungeon.level;

import java.util.Arrays;

public enum CellType {
	Chasm,
	Ground,
	Wall;
	public boolean walkable() {
		return Arrays.stream(new CellType[] {
			CellType.Ground
		}).anyMatch(this::equals);
	}
	public boolean canSee() {
		return Arrays.stream(new CellType[] {
			CellType.Ground,
			CellType.Chasm
		}).anyMatch(this::equals);
	}
}
