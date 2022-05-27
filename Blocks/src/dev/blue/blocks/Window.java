package dev.blue.blocks;

import java.awt.Dimension;

import javax.swing.JFrame;

import dev.blue.blocks.utils.WindowLayoutController;

public class Window extends JFrame {
	
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	public boolean isSmall = false;
	
	public Window(String title) {
		this.setTitle(title);
		this.setUndecorated(false);
		this.setPreferredSize(new Dimension(900, 600));
		this.setResizable(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WindowLayoutController controller = new WindowLayoutController(this);
		controller.setLayout(WindowLayoutController.LOGIN_PAGE_LAYOUT);
		this.pack();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
