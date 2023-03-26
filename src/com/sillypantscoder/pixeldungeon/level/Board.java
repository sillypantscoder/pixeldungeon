package com.sillypantscoder.pixeldungeon.level;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.sillypantscoder.pixeldungeon.Game;
import com.sillypantscoder.pixeldungeon.Helpers;
import com.sillypantscoder.pixeldungeon.random;

public class Board {
	public Cell[][] board;
	public Board() {
		board = new Cell[10][10];
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				board[x][y] = new Cell(CellType.Ground, x, y);
			}
		}
	}
	public Board(int width, int height) {
		board = new Cell[width][height];
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				board[x][y] = new Cell(CellType.Ground, x, y);
			}
		}
	}
	public BufferedImage image;
	public void draw(BufferedImage g, int[] offset, Game game) {
		image = new BufferedImage(board.length * 16, board[0].length * 16, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				board[x][y].updateLight(game);
				BufferedImage newImage = board[x][y].draw();
				Helpers.blit(image, newImage, x * 16, y * 16);
				//image.drawImage(newImage, x * 16, y * 16, new DummyImageObserver());
			}
		}
		Helpers.blit(g, image, offset[0], offset[1]);
		//g.drawImage(image, offset[0], offset[1], new DummyImageObserver());
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
