package com.sillypantscoder.pixeldungeon.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.sillypantscoder.pixeldungeon.TextureLoader;

public class Cell {
	public CellType type;
	public int x;
	public int y;
	protected BufferedImage image;
	public CellType previousType;
	public Cell(CellType type, int x, int y) {
		this.type = type;
		this.previousType = type;
		this.x = x;
		this.y = y;
		try {
			this.image = TextureLoader.loadAsset("tiles0.png");
		} catch (IOException e) {
			System.out.println("Cell failed to load texture!");
		}
	}
	public BufferedImage draw() {
		int[] srcPos = new int[] { 0, 0 };
		switch (this.type) {
			case Chasm:
				srcPos = new int[] { 0, 0 };
				break;
			case Ground:
				srcPos = new int[] { 1, 0 };
				break;
			case Wall:
				srcPos = new int[] { 4, 0 };
				break;
		}
		BufferedImage newImg = image.getSubimage(srcPos[0] * 16, srcPos[1] * 16, 16, 16);
		return newImg;
	}
}
