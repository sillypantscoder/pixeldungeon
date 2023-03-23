package com.sillypantscoder.pixeldungeon.level;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;

import com.sillypantscoder.pixeldungeon.Game;
import com.sillypantscoder.pixeldungeon.TextureLoader;
import com.sillypantscoder.pixeldungeon.entities.Player;

public class Cell {
	public CellType type;
	public LightStatus lightStatus;
	public int x;
	public int y;
	protected BufferedImage image;
	public Cell(CellType type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.lightStatus = LightStatus.Unknown;
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
		if (lightStatus == LightStatus.Unknown) {
			RescaleOp op = new RescaleOp(0.0f, 0, null);
			newImg = op.filter(newImg, null);
		}
		if (lightStatus == LightStatus.Memory) {
			RescaleOp op = new RescaleOp(0.5f, 0, null);
			newImg = op.filter(newImg, null);
		}
		return newImg;
	}
	public void updateLight(Game g) {
		if (lightStatus == LightStatus.Current) lightStatus = LightStatus.Memory;
		for (int i = 0; i < g.entityList.size(); i++) {
			if (g.entityList.get(i) instanceof Player) {
				Player p = (Player)(g.entityList.get(i));
				// Check blocks
				int[][] points = LinePoints.get_line(new int[] { p.x, p.y }, new int[] { x, y });
				boolean canSee = true;
				for (int n = 0; n < points.length; n++) {
					if (! g.board.board[points[n][0]][points[n][1]].type.canSeeThrough()) {
						canSee = false;
						if (points[n][0] == this.x) {
							if (points[n][1] == this.y) {
								canSee = true;
							}
						}
						break;
					}
				}
				// Update light status
				if (canSee) this.lightStatus = LightStatus.Current;
			}
		}
	}
}
