package com.sillypantscoder.pixeldungeon.entities;

import java.awt.Graphics;
import java.util.ArrayList;

import com.sillypantscoder.pixeldungeon.Game;
import com.sillypantscoder.pixeldungeon.TextHelpers;

public abstract class Entity {
	public int x;
	public int y;
	public float time;
	public int maxHealth;
	public int health;
	public ArrayList<String> statuses;
	public ArrayList<Integer> statusPositions;
	public Entity(int x, int y, float time, int health) {
		this.x = x;
		this.y = y;
		this.time = time;
		this.maxHealth = health;
		this.health = health;
		this.statuses = new ArrayList<String>();
		this.statusPositions = new ArrayList<Integer>();
	}
	public abstract void draw(Graphics g, int[] offset);
	public abstract void registerKey(String key);
	public abstract void doTurn(Game game);
	public void addStatus(String msg) {
		for (int i = 0; i < statuses.size(); i++) {
			statusPositions.set(i, statusPositions.get(i) + 32 + 16);
		}
		statuses.add(msg);
		statusPositions.add(0);
	}
	public void drawStatuses(Graphics g, int[] offset) {
		for (int i = 0; i < statuses.size(); i++) {
			if (statusPositions.get(i) >= 255) {
				statusPositions.remove(i);
				statuses.remove(i);
				i -= 1;
			} else {
				TextHelpers.drawText(g, statuses.get(i), (x * 16) + offset[0], ((y * 16) + offset[1]) - (statusPositions.get(i) / 5), 255 - statusPositions.get(i));
				statusPositions.set(i, statusPositions.get(i) + 1);
			}
		}
	}
}
