package com.sillypantscoder.pixeldungeon.particles;

import java.awt.image.BufferedImage;
import java.time.Instant;

import com.sillypantscoder.pixeldungeon.TextureLoader;
import com.sillypantscoder.pixeldungeon.entities.Entity;

public class DeathParticle extends Particle {
	public int[][] positions;
	public BufferedImage image;
	public int x;
	public int y;
	public DeathParticle(Entity origin, BufferedImage image, int[][] positions) {
		this.image = image;
		this.positions = positions;
		this.time = Instant.now().toEpochMilli();
		this.x = origin.x;
		this.y = origin.y;
	}
	public long time;
	public boolean draw(BufferedImage g, int[] offset) {
		int dTime = (int)(Instant.now().toEpochMilli() - this.time);
		dTime /= 100;
		int[] thisPosition = this.positions[this.positions.length - 1];
		if (dTime < this.positions.length) thisPosition = this.positions[dTime];
		// Draw the image
		TextureLoader.drawImage(g, image, thisPosition[0], thisPosition[1], (this.x * 16) + offset[0], (this.y * 16) + offset[1]);
		return dTime < this.positions.length + 10;
	}
}
