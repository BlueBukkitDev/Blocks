package dev.blue.blocks.utils.ui;

import java.awt.Color;
import java.awt.Polygon;

import dev.blue.blocks.utils.SimplePoint;

public class Shape {
	private Polygon poly;
	private Color color;
	
	public Shape(Color color, SimplePoint... points) {
		int[] xArray = new int[points.length];
		int[] yArray = new int[points.length];
		for(int i = 0; i < points.length; i++) {
			xArray[i] = points[i].getX();
			yArray[i] = points[i].getY();
		}
		poly = new Polygon(xArray, yArray, points.length);
		this.color = color;
	}
	
	public Polygon getShape() {
		return poly;
	}
	
	public Color getColor() {
		return color;
	}
}
