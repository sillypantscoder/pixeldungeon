package com.sillypantscoder.pixeldungeon;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.sillypantscoder.pixeldungeon.entities.Entity;
import com.sillypantscoder.pixeldungeon.entities.Player;
import com.sillypantscoder.pixeldungeon.items.Item;

public class DroppedItem {
	public Item item;
	public int x;
	public int y;
	public DroppedItem(Item item, Game game) {
		this.item = item;
		int[] spawn = game.board.getSpawnLocation();
		this.x = spawn[0];
		this.y = spawn[1];
	}
	public DroppedItem(Item item, int x, int y) {
		this.item = item;
		this.x = x;
		this.y = y;
	}
	public Player check(ArrayList<Entity> e) {
		Player r = null;
		for (int i = 0; i < e.size(); i++) {
			Entity t = e.get(i);
			if (t instanceof Player) {
				if (t.x == x && t.y == y) {
					r = (Player)(t);
				}
			}
		}
		return r;
	}
	public void draw(BufferedImage g, int[] offset) {
		BufferedImage itemImg = item.draw();
		int drawX = (x * 16) + offset[0];
		int drawY = (y * 16) + offset[1];
		Helpers.blit(g, itemImg, drawX, drawY);
	}
}
