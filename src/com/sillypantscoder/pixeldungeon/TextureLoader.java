package com.sillypantscoder.pixeldungeon;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import java.awt.image.RasterFormatException;
import java.awt.image.WritableRaster;
import java.awt.geom.AffineTransform;

public class TextureLoader {
	public static BufferedImage loadAsset(String filename) throws IOException {
		File f = AssetLoader.getResource(filename);
		BufferedImage image = ImageIO.read(f);
		return image;
	}
	public static void drawImage(BufferedImage g, BufferedImage image, int srcX, int srcY, int destX, int destY) {
		try {
			BufferedImage newImg = image.getSubimage(srcX * 16, srcY * 16, 16, 16);
			Helpers.blit(g, newImg, destX, destY);
			//g.drawImage(newImg, destX, destY, new DummyImageObserver());
		} catch (RasterFormatException e) {
			System.out.print("ERROR LOADING TEXTURE: ");
			System.out.print(srcX * 16);
			System.out.print(", ");
			System.out.print(srcY * 16);
			System.out.print(" out of: ");
			System.out.print(image.getWidth());
			System.out.print("x");
			System.out.print(image.getHeight());
			try {
				int id = (int)(Math.random() * 10000000);
				ImageIO.write(image, "png", new File("./errored_image_" + id + ".png"));
				System.out.println(" (saved as: errored_image_" + id + ".png)");
			} catch (IOException r) {
				System.out.println(" (failed saving)");
			}
		}
	}
	public static void drawImage(BufferedImage g, BufferedImage image, int srcX, int srcY, int srcWidth, int srcHeight, int destX, int destY) {
		try {
			BufferedImage newImg = image.getSubimage(srcX, srcY, srcWidth, srcHeight);
			Helpers.blit(g, newImg, destX, destY);
			//g.drawImage(newImg, destX, destY, new DummyImageObserver());
		} catch (RasterFormatException e) {
			System.out.print("ERROR LOADING TEXTURE: ");
			System.out.print(srcX);
			System.out.print(", ");
			System.out.print(srcY);
			System.out.print(" ");
			System.out.print(srcWidth);
			System.out.print("x");
			System.out.print(srcHeight);
			System.out.print(" out of: ");
			System.out.print(image.getWidth());
			System.out.print("x");
			System.out.print(image.getHeight());
			try {
				int id = (int)(Math.random() * 10000000);
				ImageIO.write(image, "png", new File("./errored_image_" + id + ".png"));
				System.out.println(" (saved as: errored_image_" + id + ".png)");
			} catch (IOException r) {
				System.out.println(" (failed saving)");
			}
		}
	}
	public static void drawImage(BufferedImage g, BufferedImage image, int srcX, int srcY, int destX, int destY, boolean flipHorizontal) {
		try {
			BufferedImage newImg = image.getSubimage(srcX * 16, srcY * 16, 16, 16);
			if (flipHorizontal) newImg = flipHorizontal(newImg);
			Helpers.blit(g, newImg, destX, destY);
			//g.drawImage(newImg, destX, destY, new DummyImageObserver());
		} catch (RasterFormatException e) {
			System.out.print("ERROR LOADING TEXTURE: ");
			System.out.print(srcX * 16);
			System.out.print(", ");
			System.out.print(srcY * 16);
			System.out.print(" out of: ");
			System.out.print(image.getWidth());
			System.out.print("x");
			System.out.print(image.getHeight());
			try {
				int id = (int)(Math.random() * 10000000);
				ImageIO.write(image, "png", new File("./errored_image_" + id + ".png"));
				System.out.println(" (saved as: errored_image_" + id + ".png)");
			} catch (IOException r) {
				System.out.println(" (failed saving)");
			}
		}
	}
	public static BufferedImage flipVertical(BufferedImage image) {
		AffineTransform at = new AffineTransform();
		at.concatenate(AffineTransform.getScaleInstance(1, -1));
		at.concatenate(AffineTransform.getTranslateInstance(0, -image.getHeight()));
		return createTransformed(image, at);
	}
	public static BufferedImage flipHorizontal(BufferedImage image) {
		AffineTransform at = new AffineTransform();
		at.concatenate(AffineTransform.getScaleInstance(-1, 1));
		at.concatenate(AffineTransform.getTranslateInstance(-image.getWidth(), 0));
		return createTransformed(image, at);
	}
	private static BufferedImage createTransformed(BufferedImage image, AffineTransform at) {
		BufferedImage newImage = new BufferedImage(
			image.getWidth(), image.getHeight(),
			BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImage.createGraphics();
		g.transform(at);
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return newImage;
	}
	public static BufferedImage copyImage(BufferedImage i) {
		ColorModel cm = i.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = i.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}