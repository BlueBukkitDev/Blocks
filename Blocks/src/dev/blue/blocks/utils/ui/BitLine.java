package dev.blue.blocks.utils.ui;


import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class BitLine {
	private List<TextBit> bits = new ArrayList<>();

	private int x;

	private int y;

	private int index;

	//private int width;

	private int height;

	//private int maxWidth;

	public BitLine(int maxWidth) {
		//this.maxWidth = maxWidth;
		this.index = this.x;
	}

	public void addBit(TextBit bit) {
		if (bit != null && bit.getS().length() > 0) {
			this.bits.add(bit);
			int i = 0;
			for (TextBit each : this.bits) {
				int h = each.getHeight();
				if (h > i)
					i = h;
			}
			if (i > this.height)
				this.height = i;
		}
	}

	public void render(Graphics g) {
		this.index = this.x;
		for (TextBit each : this.bits) {
			if (each.isContainer()) {
				byte b;
				int i;
				TextBit[] arrayOfTextBit;
				for (i = (arrayOfTextBit = each.getBits()).length, b = 0; b < i;) {
					TextBit every = arrayOfTextBit[b];
					g.setColor(every.getC());
					g.setFont(every.getF());
					g.drawString(every.getS(), this.index, this.y);
					this.index += g.getFontMetrics().stringWidth(every.getS());
					b++;
				}
				continue;
			}
			g.setColor(each.getC());
			g.setFont(each.getF());
			g.drawString(each.getS(), this.index, this.y);
			this.index += g.getFontMetrics().stringWidth(each.getS());
		}
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return this.height;
	}
}
