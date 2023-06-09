package com.sillypantscoder.pixeldungeon.level;

import java.util.Arrays;

public enum LightStatus {
	Unknown,
	Memory,
	Current;
	public boolean canSeeEntities() {
		return Arrays.stream(new LightStatus[] {
			LightStatus.Current
		}).anyMatch(this::equals);
	}
}
