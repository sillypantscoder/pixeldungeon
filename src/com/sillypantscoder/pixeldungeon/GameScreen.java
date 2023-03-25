package com.sillypantscoder.pixeldungeon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.sillypantscoder.pixeldungeon.entities.Player;
import com.sillypantscoder.pixeldungeon.items.InventorySlot;

public class GameScreen extends RepaintingPanel {
	public Game game;
	public GameScreen() {
		game = new Game();
		hasClicked = false;
		clickPos = new int[] { 0, 0 };
	}
	public void mouseClicked(MouseEvent e) {
		hasClicked = true;
		clickPos = new int[] { e.getX(), e.getY() };
	}
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
	public boolean hasClicked;
	public int[] clickPos;
	public BufferedImage drawUI(int height) {
		BufferedImage g = new BufferedImage(200, height, BufferedImage.TYPE_INT_ARGB);
		Player mainPlayer = game.getMainPlayer();
		// Get graphics + height
		Graphics2D g2d = g.createGraphics();
		int fontHeight = g2d.getFontMetrics().getHeight();
		int nextHeight = fontHeight;
		// Catch death
		if (mainPlayer == null) {
			Helpers.drawText(g, "You died...", 0, nextHeight * 2, 255);
			g2d.dispose();
			return g;
		}
		// Calculate health
		int health = mainPlayer.health;
		int maxHealth = mainPlayer.maxHealth;
		// Draw text
		Helpers.drawText(g, "Your health: " + health + "/" + maxHealth + "", 0, nextHeight, 255);
		// Draw health bar
		g2d.setColor(Color.RED);
		g2d.fillRect(0, nextHeight, 200, 20);
		g2d.setColor(Color.GREEN);
		g2d.fillRect(0, nextHeight, (int)(200 * ((double)(health) / maxHealth)), 20);
		// Draw inventory
		InventorySlot[] inventory = mainPlayer.inventory.slots;
		nextHeight = nextHeight + fontHeight + 20;
		Helpers.drawText(g, "Your inventory:", 0, nextHeight, 255);
		int currentX = 0;
		for (int i = 0; i < inventory.length; i++) {
			// Draw the item
			BufferedImage slotImg = inventory[i].draw();
			// Reset the position (next line)
			if (currentX + slotImg.getWidth() > 200) {
				currentX = 0;
				nextHeight += slotImg.getHeight();
			}
			// Draw to screen
			Helpers.blit(g, slotImg, currentX, nextHeight);
			// Clicked here?
			if (hasClicked && new Rectangle(currentX, nextHeight, slotImg.getWidth(), slotImg.getHeight()).contains(clickPos[0], clickPos[1])) {
				if (mainPlayer.selectedInvSlot == i) mainPlayer.selectedInvSlot = -1;
				else mainPlayer.selectedInvSlot = i;
				hasClicked = false;
			}
			// Add target if applicable
			if (i == mainPlayer.selectedInvSlot) {
				try {
					Helpers.blit(g, TextureLoader.loadAsset("icons.png").getSubimage(0, 13, 16, 16), currentX, nextHeight);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// Next position
			currentX += slotImg.getWidth();
		}
		currentX = 0;
		nextHeight += 15;
		// Add GUI for item
		if (mainPlayer.selectedInvSlot != -1) {
			nextHeight = nextHeight + fontHeight + 20;
			Helpers.drawText(g, inventory[mainPlayer.selectedInvSlot].item.name, 0, nextHeight, 255);
			for (int i = 0; i < inventory[mainPlayer.selectedInvSlot].item.buttons.length; i++) {
				BufferedImage btnImg = drawButton(inventory[mainPlayer.selectedInvSlot].item.buttons[i]);
				// Reset the position (next line)
				if (currentX + btnImg.getWidth() > 200) {
					currentX = 0;
					nextHeight += btnImg.getHeight();
				}
				// Draw
				Helpers.blit(g, btnImg, currentX, nextHeight);
				// Clicked here?
				if (hasClicked && new Rectangle(currentX, nextHeight, btnImg.getWidth(), btnImg.getHeight()).contains(clickPos[0], clickPos[1])) {
					inventory[mainPlayer.selectedInvSlot].item.execButton(i, mainPlayer, game);
					mainPlayer.selectedInvSlot = -1;
					hasClicked = false;
					break;
				}
				// Next position
				currentX += btnImg.getWidth();
			}
		}
		// Finish
		hasClicked = false;
		g2d.dispose();
		return g;
	}
	public static BufferedImage drawButton(String text) {
		int padding = 3;
		// Measure text
		BufferedImage measurer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = measurer.createGraphics();
		int fontHeight = g2d.getFontMetrics().getHeight();
		g2d.dispose();
		// Create the image
		int textWidth = g2d.getFontMetrics().stringWidth(text);
		BufferedImage g = new BufferedImage(padding + fontHeight + textWidth + padding, padding + fontHeight + padding, BufferedImage.TYPE_INT_ARGB);
		g2d = g.createGraphics();
		g2d.setColor(new Color(178, 42, 42));
		g2d.fillRect(0, 0, g.getWidth(), g.getHeight());
		// Top
		g2d.setColor(new Color(219, 79, 53));
		g2d.fillRect(0, 0, g.getWidth(), padding);
		// Left
		g2d.fillRect(0, 0, padding, g.getHeight());
		// Right
		g2d.setColor(new Color(153, 68, 82));
		g2d.fillRect(g.getWidth() - padding, 0, padding, g.getHeight());
		// Bottom
		g2d.fillRect(0, g.getHeight() - padding, g.getWidth(), padding);
		// Text
		Helpers.drawText(g, text, padding + (fontHeight / 2), (padding / 2) + fontHeight, 255);
		return g;
	}
}
