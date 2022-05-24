package dev.blue.blocks.utils;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import dev.blue.blocks.panels.LoginPanel;

public class WindowLayoutController {
	
	private JFrame parent;
	
	public static final byte LOGIN_PAGE_LAYOUT = 0;
	
	public WindowLayoutController(JFrame frame) {
		this.parent = frame;
	}
	
	public void setLayout(byte layout) {
		if(layout == LOGIN_PAGE_LAYOUT) {
			//do things
			System.out.println("Have one yourself");
			parent.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			parent.add(new LoginPanel());
		}
	}
}
