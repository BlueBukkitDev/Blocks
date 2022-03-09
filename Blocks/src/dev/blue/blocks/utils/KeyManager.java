package dev.blue.blocks.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	public KeyManager() {
		
	}
	
	public void keyPressed(KeyEvent e) {
		/*if (e.getKeyCode() == 32) {
			engine.getBooleans().isUpKeyPressed = true;
		} else if (e.getKeyCode() == 65) {
			engine.getBooleans().isLeftKeyPressed = true;
		} else if (e.getKeyCode() == 68) {
			engine.getBooleans().isRightKeyPressed = true;
		} else if (e.getKeyCode() == 27) {
			System.exit(0);
		}*/
		//alertUIObjectDown(e);
	}

	public void keyReleased(KeyEvent e) {
		/*if (e.getKeyCode() == 32) {
			engine.getBooleans().isUpKeyPressed = false;
		} else if (e.getKeyCode() == 65) {
			engine.getBooleans().isLeftKeyPressed = false;
		} else if (e.getKeyCode() == 68) {
			engine.getBooleans().isRightKeyPressed = false;
		}*/
	}

	public void keyTyped(KeyEvent e) {
		/*if(e.getKeyChar() == KeyEvent.VK_1) {
			engine.getWorld().setTileType(100);
		}else if(e.getKeyChar() == KeyEvent.VK_2) {
			engine.getWorld().setTileType(200);
		}else if(e.getKeyChar() == KeyEvent.VK_3) {
			engine.getWorld().setTileType(300);
		}else if(e.getKeyChar() == KeyEvent.VK_4) {
			engine.getWorld().setTileType(400);
		}else if(e.getKeyChar() == KeyEvent.VK_DELETE) {
			engine.getWorld().toggleDelete();
		}else if(e.getKeyChar() == KeyEvent.VK_SPACE) {
			engine.getWorld().cycleType();
		}*/
	}
	
	//private void alertUIObjectDown(KeyEvent e) {
		/*if(engine.getPageRegistry().getCurrentPage() == null) {
			System.out.println("No page set as current page! Please set a page to be displayed and restart the program. ");
			return;
		}
		for(Module every:engine.getPageRegistry().getCurrentPage().getModules()) {
			if(e.getKeyCode() == KeyEvent.VK_TAB) {
				if(every.getObjectRegistry().getSelectedUIObject() != null) {
					
				}
			}
			for(UIObject each:every.getUIObjects()) {
				each.onKeyPressed(e);
			}
		}*/
	//}
}
