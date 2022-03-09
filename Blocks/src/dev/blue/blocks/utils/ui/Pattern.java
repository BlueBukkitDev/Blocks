package dev.blue.blocks.utils.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Pattern {
	List<Shape> shapes;
	public Pattern() {
		shapes = new ArrayList<Shape>();
	}
	
	public void addShape(Shape shape) {
		shapes.add(shape);
	}
	
	public void draw(Graphics g) {
		for(Shape each:shapes) {
			g.setColor(each.getColor()); 
			g.drawPolygon(each.getShape());
		}
	}
}
