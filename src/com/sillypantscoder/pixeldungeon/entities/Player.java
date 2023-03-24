package com.sillypantscoder.pixeldungeon.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.sillypantscoder.pixeldungeon.Game;
import com.sillypantscoder.pixeldungeon.TextureLoader;

public class Player extends Entity {
	public String rKey;
	protected BufferedImage image;
	protected boolean direction;
	public int attackTime;
	public Player(int x, int y, float time) {
		super(x, y, time, 25);
		rKey = null;
		try {
			image = TextureLoader.loadAsset("mage2.png");
		} catch (IOException e) {
			System.out.println("Entity failed to load texture!");
		}
		direction = false;
		attackTime = 0;
	}
	public void draw(Graphics g, int[] offset) {
		int drawX = (new int[] { 0, 15, 14, 13 })[(int)(Math.floor(attackTime / 10))];
		if (attackTime > 0) attackTime -= 1;
		TextureLoader.drawImage(g, image, drawX, 2, (x * 16) + offset[0], (y * 16) + offset[1], !direction);
		drawStatuses(g, offset);
	}
	public void registerKey(String key) {
		rKey = key;
	}
	public void doTurn(Game game) {
		if (rKey == null) return;
		else {
			int oldX = this.x;
			int oldY = this.y;
				 if (rKey.equals("q")) { this.y += -1; this.x += -1; } // ↖
			else if (rKey.equals("w")) { this.y += -1; this.x +=  0; } // ↑
			else if (rKey.equals("e")) { this.y += -1; this.x +=  1; } // ↗
			else if (rKey.equals("a")) { this.y +=  0; this.x += -1; } // ←
			else if (rKey.equals("s")) { this.y +=  0; this.x +=  0; } // ●
			else if (rKey.equals("d")) { this.y +=  0; this.x +=  1; } // →
			else if (rKey.equals("z")) { this.y +=  1; this.x += -1; } // ↙
			else if (rKey.equals("x")) { this.y +=  1; this.x +=  0; } // ↓
			else if (rKey.equals("c")) { this.y +=  1; this.x +=  1; } // ↘
			else {
				System.out.println(rKey);
				return;
			}
			rKey = null;
			// Switch direction
			if (this.x < oldX) this.direction = false;
			else if (oldX < this.x) this.direction = true;
			// Check if we can walk here
			for (int i = 0; i < game.entityList.size(); i++) {
				Entity e = game.entityList.get(i);
				if (e == this) continue;
				if (e.x == this.x && e.y == this.y) {
					// Attack!
					this.x = oldX;
					this.y = oldY;
					attack(e);
					return;
				}
			}
			if (game.board.board[this.x][this.y].type.walkable()) {
				this.time += 1;
				return;
			}
			// uh oh!
			this.x = oldX;
			this.y = oldY;
		}
	}
	public void attack(Entity e) {
		attackTime = 30;
		time += 0.5;
		int damage = (int)(Math.random() * 6) - 3;
		damage += 3;
		e.health -= damage;
		e.addStatus(String.valueOf(damage));
	}
}
