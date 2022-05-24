package dev.blue.blocks;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;

import dev.blue.blocks.utils.WindowLayoutController;

public class Window extends JFrame {
	
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private Dimension d;
	private Dimension screenSize;
	public boolean isSmall = false;
	
	public Window(String title) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		d = new Dimension(screen.width, screen.height - taskBarSize);
		screenSize = d;
		this.setTitle(title);
		this.setUndecorated(false);
		setupWindowDimensions();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		WindowLayoutController controller = new WindowLayoutController(this);
		controller.setLayout(WindowLayoutController.LOGIN_PAGE_LAYOUT);
		this.pack();
		this.setVisible(true);
	}
	
	private void setupWindowDimensions() {
		this.setPreferredSize(d);
		this.setResizable(true);
	}
	
	public Dimension getScreenSize() {
		return screenSize;
	}
}
