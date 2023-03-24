package com.sillypantscoder.pixeldungeon.items;

import java.awt.image.BufferedImage;

public class FoodRation extends Item {
	public BufferedImage draw() {
		return itemImage.getSubimage(4 * 16, 0 * 16, 16, 16);
	}
}
