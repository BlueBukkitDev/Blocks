package dev.blue.blocks.utils.ui.gfx;

import java.awt.Color;
import java.awt.Polygon;

import dev.blue.blocks.utils.SP;
import dev.blue.blocks.utils.ui.UIObject;

public class Shape {
	private UIObject parent;
	private Polygon poly;
	private Color color;
	
	public Shape(UIObject parent, Color color, SP... points) {
		this.parent = parent;
		SP topLeft = new SP(parent.getX(), parent.getY());
		int[] xArray = new int[points.length];
		int[] yArray = new int[points.length];
		for(int i = 0; i < points.length; i++) {
			xArray[i] = topLeft.getX()+points[i].getX();
			yArray[i] = topLeft.getY()+points[i].getY();
		}
		poly = new Polygon(xArray, yArray, points.length);
		this.color = color;
	}
	
	public UIObject getParent() {
		return parent;
	}
	
	public Polygon getShape() {
		return poly;
	}
	
	public Color getColor() {
		return color;
	}
}
