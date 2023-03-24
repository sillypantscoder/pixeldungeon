package com.sillypantscoder.pixeldungeon.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.sillypantscoder.pixeldungeon.Helpers;
import com.sillypantscoder.pixeldungeon.TextureLoader;

public class InventorySlot {
	BufferedImage slotIcon;
	Item item;
	public InventorySlot() {
		try {
			slotIcon = TextureLoader.loadAsset("toolbar.png");
		} catch (IOException e) {
			System.out.println("Slot failed to load texture!");
		}
		item = null;
	}
	public BufferedImage draw() {
		BufferedImage image = slotIcon.getSubimage(83, 8, 22, 24);
		if (item != null) {
			BufferedImage itemImg = item.draw();
			int drawX = ( image.getWidth() / 2) - ( itemImg.getWidth() / 2);
			int drawY = (image.getHeight() / 2) - (itemImg.getHeight() / 2);
			Helpers.blit(image, itemImg, drawX, drawY);
		}
		return image;
	}
}
