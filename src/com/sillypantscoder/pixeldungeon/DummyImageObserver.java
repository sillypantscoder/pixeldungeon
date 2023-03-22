package com.sillypantscoder.pixeldungeon;

import java.awt.Image;
import java.awt.image.ImageObserver;

public class DummyImageObserver implements ImageObserver {
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}
}