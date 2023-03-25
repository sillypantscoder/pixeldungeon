package com.sillypantscoder.pixeldungeon.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.sillypantscoder.pixeldungeon.Game;
import com.sillypantscoder.pixeldungeon.TextureLoader;
import com.sillypantscoder.pixeldungeon.entities.Entity;

public class Item {
	public BufferedImage itemImage;
	public Item() {
		try {
			itemImage = TextureLoader.loadAsset("items.png");
		} catch (IOException e) {
			System.out.println("Item failed to load texture!");
		}
		buttons = new String[] {};
		name = "Item";
	}
	public BufferedImage draw() {
		return itemImage.getSubimage(7 * 16, 15 * 16, 16, 16);
	}
	public String[] buttons;
	public String name;
	public void execButton(int n, Entity target, Game game) {}
}
