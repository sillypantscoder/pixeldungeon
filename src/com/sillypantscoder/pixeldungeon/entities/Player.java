package com.sillypantscoder.pixeldungeon.entities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.sillypantscoder.pixeldungeon.Game;
import com.sillypantscoder.pixeldungeon.Pathfinding;
import com.sillypantscoder.pixeldungeon.TextureLoader;
import com.sillypantscoder.pixeldungeon.items.Inventory;
import com.sillypantscoder.pixeldungeon.particles.AttackParticle;
import com.sillypantscoder.pixeldungeon.particles.DeathParticle;

public class Player extends Entity {
	public String rKey;
	protected BufferedImage image;
	protected boolean direction;
	public int attackTime;
	public int selectedInvSlot;
	public Inventory inventory;
	public int[] target;
	public boolean hasTarget;
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
		inventory = new Inventory();
		selectedInvSlot = -1;
		target = new int[] { x, y };
		hasTarget = false;
	}
	public void draw(BufferedImage g, int[] offset) {
		int drawX = (new int[] { 0, 15, 14, 13 })[(int)(Math.floor(attackTime / 10))];
		if (attackTime > 0) attackTime -= 1;
		TextureLoader.drawImage(g, image, drawX, 2, (x * 16) + offset[0], (y * 16) + offset[1], !direction);
		drawStatuses(g, offset);
	}
	public void registerKey(String key) {
		rKey = key;
	}
	public void doTurn(Game game) {
		if (hasTarget) {
			// Go to the target
			int[][] walkable = new int[game.board.board.length][game.board.board[0].length];
			for (int x = 0; x < game.board.board.length; x++) {
				for (int y = 0; y < game.board.board[x].length; y++) {
					walkable[x][y] = 0;
					if (game.board.board[x][y].type.walkable()) {
						walkable[x][y] = 1;
					}
				}
			}
			int[][] path = Pathfinding.findPath(walkable, new int[] { this.x, this.y }, new int[] { target[0], target[1] });
			if (path.length <= 1) {
				hasTarget = false;
				return;
			}
			if (this.x == target[0] && this.y == target[1]) {
				hasTarget = false;
				return;
			}
			int oldX = this.x;
			this.x = path[1][0];
			this.y = path[1][1];
			this.time += 1;
			// Switch direction
			if (this.x < oldX) this.direction = false;
			else if (oldX < this.x) this.direction = true;
		}
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
					attack(e, game);
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
	public void attack(Entity e, Game game) {
		attackTime = 30;
		time += 0.5;
		int damage = (int)(Math.random() * 6) - 3;
		damage += 3;
		e.health -= damage;
		e.addStatus(String.valueOf(damage));
		// Particles
		game.particles.add(new AttackParticle(e.x, e.y, this.x, this.y));
	}
	@Override
	public void die(Game game) {
		game.particles.add(new DeathParticle(this, image, new int[][] {
			new int[] { 8,  2 },
			new int[] { 9,  2 },
			new int[] { 10, 2 },
			new int[] { 11, 2 },
			new int[] { 12, 2 }
		}));
	}
}
