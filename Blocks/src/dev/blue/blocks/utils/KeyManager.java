package dev.blue.blocks.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import dev.blue.blocks.App;
import dev.blue.blocks.utils.ui.UIObject;

public class KeyManager implements KeyListener {
	
	private App app;
	
	public KeyManager(App app) {
		this.app = app;
	}
	
	public void keyPressed(KeyEvent e) {
		for(UIObject each: app.getUIRegistry().getObjects()) {
			each.onKeyPressed(e);
		}
		
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		for(UIObject each: app.getUIRegistry().getObjects()) {
			each.onType(e);
		}
	}
}
