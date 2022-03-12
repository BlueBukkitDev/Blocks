package dev.blue.blocks.utils.ui.gfx;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;

import dev.blue.blocks.utils.ui.UIObject;

public class Graphic extends UIObject {
	
	private Pattern pattern;
	
	public Graphic(String id, Pattern pattern) {
		this.id = id;
		this.pattern = pattern;
	}

	@Override
	public void render(Graphics g) {
		pattern.draw(g);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScroll(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onClick(int paramInt, Point paramPoint) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onType(KeyEvent paramKeyEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyPressed(KeyEvent paramKeyEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseMove(Point paramPoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMouseDown(int paramInt, Point paramPoint) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onMouseUp(int paramInt, Point paramPoint) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	
}
