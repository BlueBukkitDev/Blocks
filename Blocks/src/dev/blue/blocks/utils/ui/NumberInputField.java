package dev.blue.blocks.utils.ui;

import dev.blue.blocks.App;
import dev.blue.blocks.utils.ui.gfx.Pattern;

public class NumberInputField extends TextInputField {
	public NumberInputField(App app, String id, int x, int y, int width, int height, String preview, String value,
			boolean writable, boolean protectedDisplay, TextArea toWriteTo, Pattern pattern, char... added) {
		super(app, id, x, y, width, height, preview, value, writable, protectedDisplay, toWriteTo, pattern);
		this.allowed = new char[10 + added.length];
		this.allowed[0] = '0';
		this.allowed[1] = '1';
		this.allowed[2] = '2';
		this.allowed[3] = '3';
		this.allowed[4] = '4';
		this.allowed[5] = '5';
		this.allowed[6] = '6';
		this.allowed[7] = '7';
		this.allowed[8] = '8';
		this.allowed[9] = '9';
		for (int i = 10; i < added.length + 10; i++)
			this.allowed[i] = added[i - 10];
	}
}
