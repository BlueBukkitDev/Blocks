package dev.blue.blocks.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		panel.add(loginButton(), gbc);
		gbc.gridx = 1;
		panel.add(registerButton(), gbc);
		return panel;
	}
	
	private JButton loginButton() {
		JButton login = new JButton("Login");
		login.setPreferredSize(new Dimension(110, 20));
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//attemptLogin
				if(true) {
					//setState to Client state
				}else {
					//Show error message of some kind
				}
			}
		});
		return login;
	}
	
	private JButton registerButton() {
		JButton register = new JButton("Register");
		register.setPreferredSize(new Dimension(110, 20));
		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//attemptRegistration
				if(true) {
					
				}else {
					//show error message of some kind
				}
			}
		});
		return register;
	}
}
