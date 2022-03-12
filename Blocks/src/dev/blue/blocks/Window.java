package dev.blue.blocks;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private App app;
	private Dimension d;
	private Dimension screenSize;
	public Canvas canvas;
	public boolean isSmall = false;
	
	public Window(App app, String title) {
		this.app = app;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		d = new Dimension(screen.width, screen.height - taskBarSize);
		screenSize = d;
		setupWindow(title);
	}
	
	private void setupWindow(String title) {
		this.setTitle(title);
		this.setUndecorated(true);
		this.canvas = new Canvas();
		setupWindowDimensions();
		
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
	
	public void resizeUp() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		d = new Dimension(screenSize.width, screenSize.height - taskBarSize);
		setupWindowDimensions();
		setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
		isSmall = false;
	}
	
	public void resizeDown() {
		this.d = new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()*.6), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*.6));
		setupWindowDimensions();
		setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
		isSmall = true;
	}
	
	private void setupWindowDimensions() {
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		this.setSize(d);
		this.setResizable(false);
		canvas.setPreferredSize(d);
		canvas.setMinimumSize(d);
		canvas.setMaximumSize(d);
		canvas.setSize(d);
	}
	
	public Dimension getScreenSize() {
		return screenSize;
	}
}
