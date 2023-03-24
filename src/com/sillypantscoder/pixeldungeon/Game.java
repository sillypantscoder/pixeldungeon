package com.sillypantscoder.pixeldungeon;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.sillypantscoder.pixeldungeon.entities.Entity;
import com.sillypantscoder.pixeldungeon.entities.Player;
import com.sillypantscoder.pixeldungeon.level.Board;
import com.sillypantscoder.pixeldungeon.level.LevelGeneration;

public class Game {
	public Game() {
		this.board = LevelGeneration.generateLevel(50, 10);
		this.entityList = new ArrayList<Entity>();
		this.itemList = new ArrayList<DroppedItem>();
	}
	public void keyPressed(KeyEvent e) {
		entityList.get(getTurn()).registerKey(String.valueOf(e.getKeyChar()));
	}
	public Board board;
	public ArrayList<Entity> entityList;
	public ArrayList<DroppedItem> itemList;
	public BufferedImage renderGameScreen(int width, int height) {
		BufferedImage g = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		// Get offset
		int[] offset = new int[] { 0, 0 };
		Player mainPlayer = getMainPlayer();
		if (mainPlayer != null) {
			offset[0] = mainPlayer.x;
			offset[1] = mainPlayer.y;
			offset[0] = ( width / 2) - ((offset[0] * 16) + 8);
			offset[1] = (height / 2) - ((offset[1] * 16) + 8);
		}
		// Draw board
		board.draw(g, offset, this);
		// Draw entities
		for (int i = 0; i < this.entityList.size(); i++) {
			Entity e = this.entityList.get(i);
			if (e.health < 0) {
				this.entityList.remove(i);
				i -= 1;
			} else {
				if (board.board[e.x][e.y].lightStatus.canSeeEntities()) {
					e.draw(g, offset);
				}
			}
		}
		// Draw items
		for (int i = 0; i < this.itemList.size(); i++) {
			DroppedItem e = this.itemList.get(i);
			Player target = e.check(this.entityList);
			if (target != null) {
				this.itemList.remove(i);
				i -= 1;
				target.inventory.add(e.item);
				target.time += 1;
			} else {
				if (board.board[e.x][e.y].lightStatus.canSeeEntities()) {
					e.draw(g, offset);
				}
			}
		}
		// Do turn
		turn();
		// Finish drawing
		return g;
	}
	public Player getMainPlayer() {
		for (int i = 0; i < entityList.size(); i++) {
			if (entityList.get(i) instanceof Player) {
				return (Player)(entityList.get(i));
			}
		}
		return null;
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