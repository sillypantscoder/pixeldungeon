package com.sillypantscoder.pixeldungeon.level.gen;

import com.sillypantscoder.pixeldungeon.random;
import com.sillypantscoder.pixeldungeon.level.Board;
import com.sillypantscoder.pixeldungeon.level.Cell;
import com.sillypantscoder.pixeldungeon.level.CellType;

public class RandomLevelGeneration {
	public static Board generateLevel(int width, int height) {
		Board board = new Board(width, height);
		for (int x = 0; x < board.board.length; x++) {
			for (int y = 0; y < board.board[x].length; y++) {
				if (x == 0 || y == 0 || x == board.board.length - 1 || y == board.board[x].length - 1) board.board[x][y] = new Cell(CellType.Chasm, x, y);
				else if (x == 1 || y == 1 || x == board.board.length - 2 || y == board.board[x].length - 2) board.board[x][y] = new Cell(CellType.Wall, x, y);
				else board.board[x][y] = new Cell(random.choice(new CellType[] {
					/* 12x ground */ CellType.Ground, CellType.Ground, CellType.Ground, CellType.Ground, CellType.Ground, CellType.Ground, CellType.Ground, CellType.Ground, CellType.Ground, CellType.Ground, CellType.Ground, CellType.Ground,
					/*  3x wall   */ CellType.Wall, CellType.Wall, CellType.Wall,
					/*  1x chasm  */ CellType.Chasm
				}), x, y);
			}
		}
		return board;
	}
}
