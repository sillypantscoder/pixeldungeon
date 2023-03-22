package com.sillypantscoder.pixeldungeon.level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.sillypantscoder.pixeldungeon.DummyImageObserver;
import com.sillypantscoder.pixeldungeon.random;

public class Board {
	public Cell[][] board;
	public Board() {
		board = new Cell[10][10];
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				board[x][y] = new Cell(random.choice(CellType.values()), x, y);
			}
		}
	}
	public Board(int width, int height) {
		board = new Cell[width][height];
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				board[x][y] = new Cell(random.choice(CellType.values()), x, y);
			}
		}
	}
	public BufferedImage image;
	public void draw(Graphics g, int[] offset) {
		image = new BufferedImage(board.length * 16, board[0].length * 16, BufferedImage.TYPE_INT_ARGB);
		Graphics writable = image.getGraphics();
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				BufferedImage newImage = board[x][y].draw();
				writable.drawImage(newImage, x * 16, y * 16, new DummyImageObserver());
			}
		}
		g.drawImage(image, offset[0], offset[1], new DummyImageObserver());
	}
	public int[] getSpawnLocation() {
		ArrayList<int[]> allowableLocs = new ArrayList<int[]>();
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				if (board[x][y].type.walkable()) {
					allowableLocs.add(new int[] { board[x][y].x, board[x][y].y });
				}
			}
		}
		return random.choice(allowableLocs);
	}
}
