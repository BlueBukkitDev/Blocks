package dev.blue.blocks;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private App app;
	private Dimension d;
	public Canvas canvas;
	
	public Window(App app, String title, int width, int height) {
		this.app = app;
		this.d = new Dimension(width, height);
		setupWindow(title);
	}
	
	private void setupWindow(String title) {
		this.setTitle(title);
		this.setUndecorated(true);
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		this.setSize(d);
		this.canvas = new Canvas();
		canvas.setPreferredSize(d);
		canvas.setMinimumSize(d);
		canvas.setMaximumSize(d);
		canvas.setSize(d);
		
		this.addMouseListener(app.getMouseManager());
		this.addMouseMotionListener(app.getMouseManager());
		this.addMouseWheelListener(app.getMouseManager());
		canvas.addMouseListener(app.getMouseManager());
		canvas.addMouseMotionListener(app.getMouseManager());
		canvas.addMouseWheelListener(app.getMouseManager());
		
		this.addKeyListener(app.getKeyManager());
		canvas.addKeyListener(app.getKeyManager());
		
		this.add(canvas);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}
	
}
