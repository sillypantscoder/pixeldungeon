package com.sillypantscoder.pixeldungeon.entities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Instant;

import com.sillypantscoder.pixeldungeon.DroppedItem;
import com.sillypantscoder.pixeldungeon.Game;
import com.sillypantscoder.pixeldungeon.Pathfinding;
import com.sillypantscoder.pixeldungeon.TextureLoader;
import com.sillypantscoder.pixeldungeon.items.MysteryMeat;
import com.sillypantscoder.pixeldungeon.particles.AttackParticle;
import com.sillypantscoder.pixeldungeon.particles.DeathParticle;

public class Crab extends Entity {
	public BufferedImage image;
	protected BufferedImage icons;
	protected boolean direction;
	public int attackTime;
	public boolean sleeping;
	public Crab(int x, int y, float time) {
		super(x, y, time, 25);
		try {
			image = TextureLoader.loadAsset("crab.png");
		} catch (IOException e) {
			System.out.println("Entity failed to load texture!");
		}
		try {
			icons = TextureLoader.loadAsset("icons.png");
		} catch (IOException e) {
			System.out.println("Entity failed to load icons!");
		}
		direction = false;
		attackTime = 0;
		sleeping = true;
		hasMoved = false;
		needsExcIcon = false;
	}
	protected boolean hasMoved;
	protected boolean needsExcIcon;
	public Entity target;
	public void draw(BufferedImage g, int[] offset) {
		int drawX = 0;
		if (attackTime <= 0) {
			if (!sleeping) {
				if (!hasMoved) {
					int animationTime = (int)(Instant.now().toEpochMilli() % 4000);
					if (animationTime > 2000) {
						drawX += 1;
					}
				} else {
					int animationTime = (int)(Instant.now().toEpochMilli() % 600);
					animationTime = (int)(((float)(animationTime) / 600) * 6);
					drawX = (new int[] { 0, 1, 2, 3, 4, 5 })[animationTime];
				}
			}
		} else {
			drawX = (new int[] { 6, 7, 8, 9, 2 })[(int)(Math.floor(attackTime / 5))];
			attackTime -= 1;
		}
		TextureLoader.drawImage(g, image, drawX, 0, (x * 16) + offset[0], (y * 16) + offset[1], !direction);
		// Sleeping icon
		if (sleeping) {
			TextureLoader.drawImage(g, icons, 13, 45, 9, 8, (x * 16) + offset[0], (y * 16) + offset[1]);
		}
		if (needsExcIcon) {
			TextureLoader.drawImage(g, icons, 22, 45, 8, 8, (x * 16) + offset[0], (y * 16) + offset[1]);
		}
		// Statuses
		drawStatuses(g, offset);
	}
	public void registerKey(String key) {}
	public void doTurn(Game game) {
		needsExcIcon = false;
		if (sleeping) {
			// zzzzz...
			if (game.board.board[x][y].lightStatus.canSeeEntities() && Math.random() < 0.2) {
				// zzzz-AAA!
				sleeping = false;
				time += 0.5;
				needsExcIcon = true;
				hasMoved = false;
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
			hasMoved = false;
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
			hasMoved = false;
			time += 1;
			return;
		}
		int oldX = this.x;
		int oldY = this.y;
		this.x = path[1][0];
		this.y = path[1][1];
		// Attack?
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
		hasMoved = true;
		// Switch direction
		if (this.x < oldX) this.direction = false;
		else if (oldX < this.x) this.direction = true;
		// Finish
		time += 0.5;
	}
	public void attack(Entity e, Game game) {
		hasMoved = false;
		attackTime = 4 * 5;
		time += 0.5;
		int damage = (int)(Math.random() * 3) + 1;
		e.health -= damage;
		e.addStatus(String.valueOf(damage));
		// Particles
		game.particles.add(new AttackParticle(e.x, e.y, this.x, this.y));
	}
	@Override
	public void die(Game game) {
		game.particles.add(new DeathParticle(this, image, new int[][] {
			new int[] { 11, 0 },
			new int[] { 12, 0 },
			new int[] { 13, 0 },
			new int[] { 14, 0 }
		}));
		// Drops
		if (Math.random() < 0.5) {
			game.itemList.add(new DroppedItem(new MysteryMeat(), this.x, this.y));
		}
	}
}
