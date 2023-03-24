package com.sillypantscoder.pixeldungeon;

import com.sillypantscoder.pixeldungeon.entities.Player;
import com.sillypantscoder.pixeldungeon.entities.Rat;
import com.sillypantscoder.pixeldungeon.items.FoodRation;

public class Main {
	public static void main(String[] args) {
		GameScreen screen = new GameScreen();
		screen.run();
		// add an entity
		int[] pos = screen.game.board.getSpawnLocation();
		screen.game.entityList.add(new Rat(pos[0], pos[1], 0));
		// add an entity
		pos = screen.game.board.getSpawnLocation();
		Player p = new Player(pos[0], pos[1], 0);
		screen.game.entityList.add(p);
		p.inventory.add(new FoodRation());
		// add some items
		DroppedItem i = new DroppedItem(new FoodRation(), screen.game);
		screen.game.itemList.add(i);
	}
}
