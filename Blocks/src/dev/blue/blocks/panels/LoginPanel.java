package dev.blue.blocks.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import dev.blue.blocks.utils.JPromptTextField;

public class LoginPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LoginPanel() {
		super (new GridBagLayout());
		JPromptTextField user = new JPromptTextField(16, "Username");
		JPromptTextField pass = new JPromptTextField(16, "Password");
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridy = 0;
		add(user, gbc);
		gbc.gridy++;
		add(Box.createVerticalStrut(5), gbc);
		gbc.gridy++;
		add(pass, gbc);
		gbc.gridy++;
		add(buttonsPanel(), gbc);
	}
	
	private JPanel buttonsPanel() {
		JPanel panel = new JPanel();
		JButton login = new JButton("Login");
		JButton register = new JButton("Register");
		
		login.setPreferredSize(new Dimension(110, 20));
		register.setPreferredSize(new Dimension(110, 20));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		panel.add(login, gbc);
		gbc.gridx = 1;
		panel.add(register, gbc);
		return panel;
	}
}
