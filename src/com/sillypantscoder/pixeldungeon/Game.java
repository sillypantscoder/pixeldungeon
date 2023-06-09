package com.sillypantscoder.pixeldungeon;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.sillypantscoder.pixeldungeon.entities.Entity;
import com.sillypantscoder.pixeldungeon.entities.Player;
import com.sillypantscoder.pixeldungeon.level.Board;
import com.sillypantscoder.pixeldungeon.level.gen.*;
import com.sillypantscoder.pixeldungeon.particles.Particle;

public class Game {
	public Game() {
		this.board = SubdivisionLevelGeneration.generateLevel();
		this.entityList = new ArrayList<Entity>();
		this.itemList = new ArrayList<DroppedItem>();
		this.particles = new ArrayList<Particle>();
		this.prevOffset = new int[] { 0, 0 };
	}
	public void keyPressed(KeyEvent e) {
		entityList.get(getTurn()).registerKey(String.valueOf(e.getKeyChar()));
	}
	public void mouseClicked(int cx, int cy) {
		int x = (cx - prevOffset[0]) / 16;
		int y = (cy - prevOffset[1]) / 16;
		Player mainPlayer = getMainPlayer();
		mainPlayer.target = new int[] { x, y };
		mainPlayer.hasTarget = true;
	}
	protected int[] prevOffset;
	public Board board;
	public ArrayList<Entity> entityList;
	public ArrayList<DroppedItem> itemList;
	public ArrayList<Particle> particles;
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
		prevOffset = new int[] { offset[0], offset[1] };
		// Draw board
		board.draw(g, offset, this);
		// Draw entities
		for (int i = 0; i < this.entityList.size(); i++) {
			Entity e = this.entityList.get(i);
			if (e.health < 0) {
				e.die(this);
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
		// Draw particles
		for (int i = 0; i < this.particles.size(); i++) {
			boolean keepGoing = this.particles.get(i).draw(g, offset);
			if (!keepGoing) {
				this.particles.remove(i);
				i -= 1;
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