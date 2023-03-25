package com.sillypantscoder.pixeldungeon.particles;

import java.awt.image.BufferedImage;

public abstract class Particle {
	public abstract boolean draw(BufferedImage g, int[] offset);
}
