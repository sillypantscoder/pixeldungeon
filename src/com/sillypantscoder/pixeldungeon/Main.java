package com.sillypantscoder.pixeldungeon;

import com.sillypantscoder.pixeldungeon.entities.Player;
import com.sillypantscoder.pixeldungeon.entities.Rat;

public class Main {
	public static void main(String[] args) {
		GameScreen screen = new GameScreen();
		screen.run();
		// add an entity
		int[] pos = screen.game.board.getSpawnLocation();
		screen.game.entityList.add(new Rat(pos[0], pos[1], 0));
		// add an entity
		pos = screen.game.board.getSpawnLocation();
		screen.game.entityList.add(new Player(pos[0], pos[1], 0));
	}
}
