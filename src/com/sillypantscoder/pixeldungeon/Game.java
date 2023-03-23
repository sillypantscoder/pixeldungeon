package com.sillypantscoder.pixeldungeon;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.sillypantscoder.pixeldungeon.entities.Entity;
import com.sillypantscoder.pixeldungeon.entities.Player;
import com.sillypantscoder.pixeldungeon.entities.Rat;
import com.sillypantscoder.pixeldungeon.level.Board;
import com.sillypantscoder.pixeldungeon.level.LevelGeneration;

public class Game extends RepaintingPanel {
	public Game() {
		this.board = LevelGeneration.generateLevel(50, 10);
		this.entityList = new ArrayList<Entity>();
	}
	public static void main(String[] args) {
		Game screen = new Game();
		screen.run();
		// add an entity
		int[] pos = screen.board.getSpawnLocation();
		screen.entityList.add(new Rat(pos[0], pos[1], 0));
		// add an entity
		pos = screen.board.getSpawnLocation();
		screen.entityList.add(new Player(pos[0], pos[1], 0));
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void keyPressed(KeyEvent e) {
		entityList.get(getTurn()).registerKey(String.valueOf(e.getKeyChar()));
	}
	public Board board;
	public ArrayList<Entity> entityList;
	public void painter(Graphics g) {
		// Get offset
		int[] offset = new int[] { 0, 0 };
		for (int i = 0; i < entityList.size(); i++) {
			if (entityList.get(i) instanceof Player) {
				offset[0] = entityList.get(i).x;
				offset[1] = entityList.get(i).y;
			}
		}
		offset[0] = (this.getWidth()  / 2) - ((offset[0] * 16) + 8);
		offset[1] = (this.getHeight() / 2) - ((offset[1] * 16) + 8);
		// Draw board
		board.draw(g, offset, this);
		// Draw entities
		for (int i = 0; i < this.entityList.size(); i++) {
			Entity e = this.entityList.get(i);
			if (board.board[e.x][e.y].lightStatus.canSeeEntities()) {
				e.draw(g, offset);
			}
		}
		// Do turn
		turn();
	}
	public void turn() {
		int turn = getTurn();
		//ArrayList<Entity> oldEntityList = (ArrayList<Entity>)(entityList.clone());
		entityList.get(turn).doTurn(this);
		/*if (completed) {
			int newIndex = 0;
			for (; entityList.get(newIndex) == oldEntityList.get(turn); newIndex++) {
				if (newIndex > entityList.size()) newIndex = 0;
			}
		}*/
	}
	public int getTurn() {
		Entity winner = null;
		float minTime = 10000000;
		for (int i = 0; i < entityList.size(); i++) {
			if (entityList.get(i).time < minTime) {
				winner = entityList.get(i);
				minTime = entityList.get(i).time;
			}
		}
		return entityList.indexOf(winner);
	}
}