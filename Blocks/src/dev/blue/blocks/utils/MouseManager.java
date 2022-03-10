package dev.blue.blocks.utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import dev.blue.blocks.App;
import dev.blue.blocks.utils.ui.UIObject;

public class MouseManager implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	//private UIObject currentObject;
	public UIObject clickedObject;
	private App app;
	
	private boolean mouseDown = false;
	private int xOffset;
	private int yOffset;
	
	public MouseManager(App app) {
		this.app = app;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			
		}else if(e.getButton() == MouseEvent.BUTTON3) {
			
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!mouseDown) {
			mouseDown = true;
			xOffset = (int)e.getLocationOnScreen().getX() - (int)app.getWindow().getLocation().getX();//mouseCoord will always be larger than windowCoord
			yOffset = (int)e.getLocationOnScreen().getY() - (int)app.getWindow().getLocation().getY();
			//alertUIObjectDown(e.getButton(), e.getPoint());
			for(UIObject each:app.getUIRegistry().getObjects()) {
				if(each.onMouseDown(e.getButton(), e.getPoint())) {
					clickedObject = each;
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
		for(UIObject each:app.getUIRegistry().getObjects()) {
			each.onMouseUp(e.getButton(), e.getPoint());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int mouseX = (int)e.getLocationOnScreen().getX();
		int mouseY = (int)e.getLocationOnScreen().getY();
		
		app.getWindow().setLocation(mouseX - xOffset, mouseY - yOffset);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
	}
}
