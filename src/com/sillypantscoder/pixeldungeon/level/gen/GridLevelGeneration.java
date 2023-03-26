package com.sillypantscoder.pixeldungeon.level.gen;

import com.sillypantscoder.pixeldungeon.level.Board;
import com.sillypantscoder.pixeldungeon.level.CellType;

public class GridLevelGeneration {
	public static Board generateLevel() {
		int width = 20;
		int height = 20;
		Board board = new Board(width * 5, height * 5);
		for (int x = 0; x < width * 5; x++) {
			for (int y = 0; y < height * 5; y++) {
				if (x % 5 == 0 || y % 5 == 0) {
					// Wall
					if (x % 5 == 3 || y % 5 == 3) board.board[x][y].type = CellType.Ground;
					else board.board[x][y].type = CellType.Wall;
				} else {
					board.board[x][y].type = CellType.Ground;
				}
			}
		}
		return board;
	}
}
