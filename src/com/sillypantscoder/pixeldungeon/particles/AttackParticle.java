package com.sillypantscoder.pixeldungeon.particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class AttackParticle extends Particle {
	public float[] x;
	public float[] y;
	public float[] vx;
	public float[] vy;
	public float[] size;
	public static int nParticles = 10;
	public AttackParticle(int tileX, int tileY, int originX, int originY) {
		x = new float[nParticles];
		y = new float[nParticles];
		vx = new float[nParticles];
		vy = new float[nParticles];
		size = new float[nParticles];
		for (int i = 0; i < nParticles; i++) {
			x[i] = (tileX * 16) + 8;
			y[i] = (tileY * 16) + 8;
			vx[i] = ((float)(tileX) - originX) * (((float)(Math.random() * 2)) + 1f);
			vy[i] = ((float)(tileY) - originY) * (((float)(Math.random() * 2)) + 1f);
			vx[i] += ((float)(Math.random() * 2)) - 1f;
			vy[i] += ((float)(Math.random() * 2)) - 1f;
			size[i] = (float)(Math.random() * 10);
		}
	}
	public boolean draw(BufferedImage g, int[] offset) {
		Graphics2D g2d = g.createGraphics();
		boolean keepGoing = false;
		for (int i = 0; i < nParticles; i++) {
			if (size[i] > 0) {
				keepGoing = true;
				x[i] += vx[i];
				y[i] += vy[i];
				vx[i] *= 0.9;
				vy[i] *= 0.9;
				size[i] -= (float)(Math.random() / 2);
				g2d.setColor(Color.RED);
				float drawX = (x[i] - (size[i] / 2)) + offset[0];
				float drawY = (y[i] - (size[i] / 2)) + offset[1];
				g2d.fillRect((int)(drawX), (int)(drawY), (int)(size[i]), (int)(size[i]));
			}
		}
		g2d.dispose();
		return keepGoing;
	}
}
