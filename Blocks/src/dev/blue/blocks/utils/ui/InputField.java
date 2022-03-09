package dev.blue.blocks.utils.ui;


import java.awt.Rectangle;

public abstract class InputField extends UIObject {
	protected String id;

	protected Rectangle bounds;

	protected boolean hovering = false;

	public InputField(String id, int x, int y, int width, int height) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bounds = new Rectangle(x, y, width, height);
	}
}
