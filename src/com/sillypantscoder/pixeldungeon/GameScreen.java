package com.sillypantscoder.pixeldungeon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.sillypantscoder.pixeldungeon.entities.Player;
import com.sillypantscoder.pixeldungeon.items.InventorySlot;

public class GameScreen extends RepaintingPanel {
	Game game;
	public GameScreen() {
		game = new Game();
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void keyPressed(KeyEvent e) {
		game.keyPressed(e);
	}
	public BufferedImage painter() {
		BufferedImage g = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		// Background
		Graphics2D g2d = g.createGraphics();
		g2d.setColor(Color.BLACK);
		g2d.fillRect(200, 0, getWidth() - 200, getHeight());
		g2d.dispose();
		// Render game screen
		Helpers.blit(g, game.renderGameScreen(getWidth() - 200, getHeight()), 200, 0);
		// Render UI
		Helpers.blit(g, drawUI(getHeight()), 0, 0);
		return g;
	}
	public BufferedImage drawUI(int height) {
		BufferedImage g = new BufferedImage(200, height, BufferedImage.TYPE_INT_ARGB);
		// Calculate health
		int health = game.getMainPlayer().health;
		int maxHealth = game.getMainPlayer().maxHealth;
		// Get graphics + height
		Graphics2D g2d = g.createGraphics();
		int fontHeight = g2d.getFontMetrics().getHeight();
		int nextHeight = fontHeight;
		// Draw text
		Helpers.drawText(g, "Your health: " + health + "/" + maxHealth + "", 0, nextHeight, 255);
		// Draw health bar
		g2d.setColor(Color.RED);
		g2d.fillRect(0, nextHeight, 200, 20);
		g2d.setColor(Color.GREEN);
		g2d.fillRect(0, nextHeight, (int)(200 * ((double)(health) / maxHealth)), 20);
		// Draw inventory
		InventorySlot[] inventory = ((Player)(game.getMainPlayer())).inventory.slots;
		nextHeight = nextHeight + fontHeight + 20;
		Helpers.drawText(g, "Your inventory:", 0, nextHeight, 255);
		int currentX = 0;
		for (int i = 0; i < inventory.length; i++) {
			BufferedImage slotImg = inventory[i].draw();
			if (currentX + slotImg.getWidth() > 200) {
				currentX = 0;
				nextHeight += slotImg.getHeight();
			}
			Helpers.blit(g, slotImg, currentX, nextHeight);
			currentX += slotImg.getWidth();
		}
		// Finish
		g2d.dispose();
		return g;
	}
}
