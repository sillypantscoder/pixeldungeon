package com.sillypantscoder.pixeldungeon;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
//import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * A panel that automatically redraws itself.
 */
public abstract class RepaintingPanel extends JPanel {
	private static final long serialVersionUID = 7148504528835036003L;
	protected static JFrame frame;
	public abstract void painter(Graphics g);
	/**
	* Called by the runtime system whenever the panel needs painting.
	*/
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		painter(g);
		// Paint an oval:
		//g.setColor(Color.WHITE);
		//g.fillOval(0, 0, 30, 10); // cx, cy, dx, dy
		// Can call getWidth() and getHeight() for panel dimensions
		// Paint a rectangle:
		//g.setColor(Color.WHITE);
		//g.fillRect(60, 60, 40, 40); // x, y, w, h
		//g.fillRect((int)(Math.random() * 100), (int)(Math.random() * 100), (int)(Math.random() * 100), (int)(Math.random() * 100));
		// Paint progress bar?
		//g.fillRect(0, getHeight() - 50, (int)((Instant.now().toEpochMilli() - timeInitial) / (1000 / getWidth())), 50);
	}
	public abstract void mouseClicked(MouseEvent e);
	public abstract void mouseMoved(MouseEvent e);
	public abstract void keyPressed(KeyEvent e);
	public static void startAnimation() {
		SwingWorker<Object, Object> sw = new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
				while (true) {
					frame.revalidate();
					frame.getContentPane().repaint();
					// frame.setVisible(true);
					Thread.sleep(16);
					// BufferedImage bImg = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
					// Graphics2D cg = bImg.createGraphics();
					// frame.paintAll(cg);
					// ImageIO.write(bImg, "png", new File("./output_image.png"));
				}
			}
		};

		sw.execute();
	}

	/**
	* A little driver in case you want a stand-alone application.
	*/
	public void run() {
		//SwingUtilities.invokeLater(() -> {
			frame = new JFrame("Java Graphics");
			frame.setSize(1500, 500);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(this, BorderLayout.CENTER);
			frame.setVisible(true);
			startAnimation();
			// Start mouse listener
			myMouseListener ml = new myMouseListener(this);
			this.addMouseListener(ml);
			mouseMotionListener mml = new mouseMotionListener(this);
			this.addMouseMotionListener(mml);
			// Add keyboard listener
			RepaintingPanel thePanel = this;
			frame.addKeyListener(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent e){ thePanel.keyPressed(e); }
				@Override
				public void keyReleased(KeyEvent e) { }
				@Override
				public void keyTyped(KeyEvent e) { }
			});
		//});
	}
}
class myMouseListener implements MouseListener {
	protected RepaintingPanel srcPanel;
	public myMouseListener(RepaintingPanel p) {
		srcPanel = p;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) { }

	@Override
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) {
		srcPanel.mouseClicked(arg0);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) { }

}
class mouseMotionListener implements MouseMotionListener {
	protected RepaintingPanel srcPanel;
	public mouseMotionListener(RepaintingPanel p) {
		srcPanel = p;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) { }

	@Override
	public void mouseMoved(MouseEvent arg0) {
		srcPanel.mouseMoved(arg0);
	}
}