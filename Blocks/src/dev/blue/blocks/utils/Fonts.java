package dev.blue.blocks.utils;

import java.awt.Font;

public class Fonts {
	public Font plain;
	public Font italic;
	public Font bold;
	
	public Fonts() {
		plain = new Font("Helvetica", Font.PLAIN, 14);
		bold = new Font("Helvetica", Font.BOLD, 14);
		italic = new Font("Helvetica", Font.ITALIC, 14);
	}
}
