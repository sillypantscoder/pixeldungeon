package com.sillypantscoder.pixeldungeon.entities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.sillypantscoder.pixeldungeon.Game;
import com.sillypantscoder.pixeldungeon.Helpers;

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
		this.statusHeight = 1;
	}
	public abstract void draw(BufferedImage g, int[] offset);
	public abstract void registerKey(String key);
	public abstract void doTurn(Game game);
	protected int statusHeight;
	public void addStatus(String msg) {
		for (int i = 0; i < statuses.size(); i++) {
			statusPositions.set(i, statusPositions.get(i) + statusHeight);
		}
		statuses.add(msg);
		statusPositions.add(0);
	}
	public void drawStatuses(BufferedImage g, int[] offset) {
		statusHeight = g.createGraphics().getFontMetrics().getHeight();
		for (int i = 0; i < statuses.size(); i++) {
			if (statusPositions.get(i) >= 255) {
				statusPositions.remove(i);
				statuses.remove(i);
				i -= 1;
			} else {
				Helpers.drawText(g, statuses.get(i), (x * 16) + offset[0], ((y * 16) + offset[1]) - (statusPositions.get(i) / 5), 255 - statusPositions.get(i));
				statusPositions.set(i, statusPositions.get(i) + 1);
			}
		}
	}
}
