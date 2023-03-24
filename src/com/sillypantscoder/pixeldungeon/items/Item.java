package com.sillypantscoder.pixeldungeon.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.sillypantscoder.pixeldungeon.TextureLoader;

public class Item {
	public BufferedImage itemImage;
	public Item() {
		try {
			itemImage = TextureLoader.loadAsset("items.png");
		} catch (IOException e) {
			System.out.println("Item failed to load texture!");
		}
	}
	public BufferedImage draw() {
		return itemImage.getSubimage(7 * 16, 15 * 16, 16, 16);
	}
}
