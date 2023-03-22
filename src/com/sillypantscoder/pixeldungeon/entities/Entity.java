package com.sillypantscoder.pixeldungeon.entities;

import java.awt.Graphics;

import com.sillypantscoder.pixeldungeon.Game;

public abstract class Entity {
	public int x;
	public int y;
	public float time;
	public Entity(int x, int y, float time) {
		this.x = x;
		this.y = y;
		this.time = time;
	}
	public abstract void draw(Graphics g, int[] offset);
	public abstract void registerKey(String key);
	public abstract void doTurn(Game game);
}
