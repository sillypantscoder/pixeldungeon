package com.sillypantscoder.pixeldungeon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class Helpers {
	public static void drawText(BufferedImage g, String text, int x, int y, int a) {
		try {
			Graphics2D g2d = g.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(new Color(255, 10, 10, a));
			g2d.drawString(text, x, y);
		} catch (IllegalArgumentException e) {
			System.out.println(a);
		}
	}
	public static void blit(BufferedImage dest, BufferedImage source, int x, int y) {
		Graphics2D g2d = dest.createGraphics();
		g2d.drawImage(source, x, y, null);
		g2d.dispose();
	}
}
