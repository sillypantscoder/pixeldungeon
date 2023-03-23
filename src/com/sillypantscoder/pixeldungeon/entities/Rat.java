package com.sillypantscoder.pixeldungeon.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Instant;

import com.sillypantscoder.pixeldungeon.Game;
import com.sillypantscoder.pixeldungeon.Pathfinding;
import com.sillypantscoder.pixeldungeon.TextureLoader;

public class Rat extends Entity {
	protected BufferedImage image;
	protected BufferedImage icons;
	protected boolean direction;
	public boolean sleeping;
	protected boolean needsExcIcon;
	public Rat(int x, int y, float time) {
		super(x, y, time);
		try {
			image = TextureLoader.loadAsset("rat_a.png");
		} catch (IOException e) {
			System.out.println("Entity failed to load texture!");
		}
		try {
			icons = TextureLoader.loadAsset("icons.png");
		} catch (IOException e) {
			System.out.println("Entity failed to load icons!");
		}
		target = null;
		direction = true;
		sleeping = true;
		needsExcIcon = false;
	}
	public Entity target;
	public void draw(Graphics g, int[] offset) {
		int drawX = 0;
		if (!sleeping) {
			if (target == null) {
				int animationTime = (int)(Instant.now().toEpochMilli() % 4000);
				if (animationTime > 2000) {
					drawX += 1;
				}
			} else {
				int animationTime = (int)(Instant.now().toEpochMilli() % 600);
				animationTime = (int)(((float)(animationTime) / 600) * 5);
				drawX = (new int[] { 6, 7, 8, 9, 10 })[animationTime];
			}
		}
		TextureLoader.drawImage(g, image, drawX, 0, (x * 16) + offset[0], (y * 16) + offset[1], !direction);
		// Sleeping icon
		if (sleeping) {
			TextureLoader.drawImage(g, icons, 13, 45, 9, 8, (x * 16) + offset[0], (y * 16) + offset[1]);
		}
		if (needsExcIcon) {
			TextureLoader.drawImage(g, icons, 22, 45, 8, 8, (x * 16) + offset[0], (y * 16) + offset[1]);
		}
	}
	public void registerKey(String key) {}
	public void doTurn(Game game) {
		needsExcIcon = false;
		if (sleeping) {
			// zzzzz...
			if (game.board.board[x][y].lightStatus.canSeeEntities() && Math.random() < 0.2) {
				// zzzz-AAA!
				sleeping = false;
				time += 1;
				needsExcIcon = true;
			}
			time += 1;
			return;
		}
		// Find a target
		target = null;
		for (int i = 0; i < game.entityList.size(); i++) {
			if (game.entityList.get(i) instanceof Player) {
				target = game.entityList.get(i);
				break;
			}
		}
		if (target == null) {
			time += 1;
			return;
		}
		// Pathfind towards target
		int[][] walkable = new int[game.board.board.length][game.board.board[0].length];
		for (int x = 0; x < game.board.board.length; x++) {
			for (int y = 0; y < game.board.board[x].length; y++) {
				walkable[x][y] = 0;
				if (game.board.board[x][y].type.walkable()) {
					walkable[x][y] = 1;
				}
			}
		}
		int[][] path = Pathfinding.findPath(walkable, new int[] { this.x, this.y }, new int[] { target.x, target.y });
		if (path.length <= 1) {
			time += 1;
			return;
		}
		int oldX = this.x;
		this.x = path[1][0];
		this.y = path[1][1];
		// Switch direction
		if (this.x < oldX) this.direction = false;
		else if (oldX < this.x) this.direction = true;
		// Finish
		time += 1;
	}
}
