package dev.blue.blocks;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private Dimension d;
	public Canvas canvas;
	
	public Window(String title, int width, int height) {
		d = new Dimension(width, height);
		setupWindow(title);
	}
	
	private void setupWindow(String title) {
		this.setTitle(title);
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		this.setSize(d);
		this.canvas = new Canvas();
		canvas.setPreferredSize(d);
		canvas.setMinimumSize(d);
		canvas.setMaximumSize(d);
		canvas.setSize(d);
		this.add(canvas);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}
	
}
