package com.sillypantscoder.pixeldungeon.items;

import java.awt.image.BufferedImage;

import com.sillypantscoder.pixeldungeon.Game;
import com.sillypantscoder.pixeldungeon.entities.Entity;

public class MysteryMeat extends Item {
	public MysteryMeat() {
		super();
		buttons = new String[] { };
		name = "Mystery Meat";
	}
	public BufferedImage draw() {
		return itemImage.getSubimage(1 * 16, 14 * 16, 16, 16);
	}
	public void execButton(int n, Entity target, Game game) {
		switch (n) {
			case 0:
				target.health = target.maxHealth;
		}
	}
}
