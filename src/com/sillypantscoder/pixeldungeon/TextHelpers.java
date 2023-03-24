package com.sillypantscoder.pixeldungeon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class TextHelpers {
	public static void drawText(Graphics g, String text, int x, int y, int a) {
		try {
			if (g instanceof Graphics2D) {
				Graphics2D g2 = (Graphics2D)g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(255, 10, 10, a));
				g2.drawString(text, x, y);
			}
		} catch (IllegalArgumentException e) {
			System.out.println(a);
		}
	}
}
