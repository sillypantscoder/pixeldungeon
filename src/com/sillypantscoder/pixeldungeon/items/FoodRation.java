package com.sillypantscoder.pixeldungeon.items;

import java.awt.image.BufferedImage;

import com.sillypantscoder.pixeldungeon.Game;
import com.sillypantscoder.pixeldungeon.entities.Entity;
import com.sillypantscoder.pixeldungeon.entities.Player;

public class FoodRation extends Item {
	public FoodRation() {
		super();
		buttons = new String[] { "Eat" };
		name = "Ration of Food";
	}
	public BufferedImage draw() {
		return itemImage.getSubimage(4 * 16, 0 * 16, 16, 16);
	}
	public void execButton(int n, Entity target, Game game) {
		switch (n) {
			case 0:
				target.health = target.maxHealth;
				target.time += 2.5f;
				if (target instanceof Player) ((Player)(target)).inventory.remove(this);
		}
	}
}
