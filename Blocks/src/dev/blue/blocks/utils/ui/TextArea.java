package dev.blue.blocks.utils.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import dev.blue.blocks.App;

public class TextArea extends UIObject {

	private App app;

	private String id;

	private Rectangle bounds;

	private int x;

	private int y;

	private int width;

	private int height;

	private boolean hovering = false;

	private List<BitLine> lines;

	private int lastLineY = 0;

	private Graphics g;

	private int cushion;

	private boolean isActive;

	private Pattern pattern;

	public TextArea(App app, String id, int x, int y, int width, int height, int cushion, Pattern pattern) {
		this.app = app;
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bounds = new Rectangle(x, y, width, height);
		this.cushion = cushion;
		this.lines = new ArrayList<>();
		this.isActive = true;
		this.pattern = pattern;
	}

	public void render(Graphics g) {
		if (this.isActive) {
			this.g = g;
			this.pattern.draw(g);
			try {
				for (BitLine each : this.lines)
					each.render(g);
			} catch (ConcurrentModificationException e) {
				e.printStackTrace();
			}
		}
	}

	public void logInfo(String message) {
		addLine(new TextBit[] { new TextBit(app, Color.BLUE, (app.getFonts()).bold, "INFO: ", null, null),
				new TextBit(app, Color.BLACK, (app.getFonts()).plain, message, null, null) });
	}

	public void logError(String message) {
		addLine(new TextBit[] { new TextBit(app, Color.RED, (app.getFonts()).bold, "ERROR: ", null, null),
				new TextBit(app, Color.RED, (app.getFonts()).plain, message, null, null) });
	}

	public void logIncoming(String message) {
		if (message.contains("§")) {/////////////////////// Guess it was §, idk
			TextBit[] bits = getBits(message);
			if (this != null && bits != null)
				addLine(bits);
		} else if (this != null)

		{
			addLine(new TextBit[] { new TextBit(app, Color.BLACK, (app.getFonts()).plain, message, null, null) });
		}
	}

	private TextBit[] getBits(String message) {
		String[] parts = message.split("§");//////////////////////////// Prolly also is §
		TextBit[] bits = new TextBit[parts.length];
		for (int i = 1; i < parts.length; i++) {
			TextBit bit;
			if (parts[i].startsWith("a")) {
				bit = new TextBit(app, new Color(0, 255, 0), (app.getFonts()).plain, parts[i].substring(1), null, null);
			} else if (parts[i].startsWith("b")) {
				bit = new TextBit(app, Color.CYAN, (app.getFonts()).plain, parts[i].substring(1), null, null);
			} else if (parts[i].startsWith("c")) {
				bit = new TextBit(app, Color.RED, (app.getFonts()).plain, parts[i].substring(1), null, null);
			} else if (parts[i].startsWith("d")) {
				bit = new TextBit(app, Color.PINK, (app.getFonts()).plain, parts[i].substring(1), null, null);
			} else if (parts[i].startsWith("e")) {
				bit = new TextBit(app, Color.YELLOW, (app.getFonts()).plain, parts[i].substring(1), null, null);
			} else if (parts[i].startsWith("f")) {
				bit = new TextBit(app, Color.WHITE, (app.getFonts()).plain, parts[i].substring(1), null, null);
			} else if (parts[i].startsWith("0")) {
				bit = new TextBit(app, Color.BLACK, (app.getFonts()).plain, parts[i].substring(1), null, null);
			} else if (parts[i].startsWith("1")) {
				bit = new TextBit(app, Color.BLUE, (app.getFonts()).plain, parts[i].substring(1), null, null);
			} else if (parts[i].startsWith("2")) {
				bit = new TextBit(app, new Color(0, 80, 0), (app.getFonts()).plain, parts[i].substring(1), null, null);
			} else if (parts[i].startsWith("3")) {
				bit = new TextBit(app, new Color(0, 80, 80), (app.getFonts()).plain, parts[i].substring(1), null, null);
			} else if (parts[i].startsWith("4")) {
				bit = new TextBit(app, new Color(80, 0, 0), (app.getFonts()).plain, parts[i].substring(1), null, null);
			} else if (parts[i].startsWith("5")) {
				bit = new TextBit(app, new Color(80, 0, 80), (app.getFonts()).plain, parts[i].substring(1), null, null);
			} else if (parts[i].startsWith("6")) {
				bit = new TextBit(app, new Color(80, 0, 80), (app.getFonts()).plain, parts[i].substring(1), null, null);
			} else if (parts[i].startsWith("7")) {
				bit = new TextBit(app, Color.GRAY, (app.getFonts()).plain, parts[i].substring(1), null, null);
			} else if (parts[i].startsWith("8")) {
				bit = new TextBit(app, Color.DARK_GRAY, (app.getFonts()).plain, parts[i].substring(1), null, null);
			} else if (parts[i].startsWith("9")) {
				bit = new TextBit(app, new Color(80, 0, 200), (app.getFonts()).plain, parts[i].substring(1), null, null);
			} else if (parts[i].startsWith("l")) {
				if (i > 1) {
					bit = new TextBit(app, bits[i - 1].getC(), (app.getFonts()).bold, parts[i].substring(1), null, null);
				} else {
					bit = new TextBit(app, Color.BLACK, (app.getFonts()).bold, parts[i].substring(1), null, null);
				}
			} else if (parts[i].startsWith("i")) {
				if (i > 1) {
					bit = new TextBit(app, bits[i - 1].getC(), (app.getFonts()).italic, parts[i].substring(1), null, null);
				} else {
					bit = new TextBit(app, Color.BLACK, (app.getFonts()).italic, parts[i].substring(1), null, null);
				}
			} else if (parts[i].startsWith("r")) {
				bit = new TextBit(app, Color.BLACK, (app.getFonts()).plain, parts[i].substring(1), null, null);
			} else if (i > 1) {
				bit = new TextBit(app, bits[i - 1].getC(), bits[i - 1].getF(), parts[i], null, null);
			} else {
				bit = new TextBit(app, Color.BLACK, (app.getFonts()).plain, "§" + parts[i], null, null);
			}
			bits[i] = bit;
		}
		return bits;
	}

	public void addLine(TextBit... bits) {
		BitLine line = new BitLine(this.width - this.cushion * 2);
		byte b;
		int i;
		TextBit[] arrayOfTextBit;
		for (i = (arrayOfTextBit = bits).length, b = 0; b < i;) {
			TextBit each = arrayOfTextBit[b];
			line.addBit(each);
			b++;
		}
		if(lastLineY == 0) {
			lastLineY = y+cushion+line.getHeight();
		}
		line.setY(this.lastLineY);
		line.setX(this.x + this.cushion);
		this.lastLineY += line.getHeight();
		this.lines.add(line);
	}

	public void update() {

	}
	
	@Override
	public void onMouseMove(Point p) {
		if (this.bounds.contains(p)) {
			this.hovering = true;
		} else {
			this.hovering = false;
		}
	}

	public void run() {
	}

	@Override
	public boolean onClick(int button, Point p) {
		if (this.bounds.contains(p)) {
			run();
			return true;
		}
		return false;
	}
	
	@Override
	public void onScroll(int amount) {
		
	}
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Rectangle getBounds() {
		return this.bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
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

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isHovering() {
		return this.hovering;
	}

	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}

	public List<BitLine> getLines() {
		return this.lines;
	}

	public Graphics getGraphics() {
		return this.g;
	}

	public void deactivate() {
		this.isActive = false;
	}

	public void clear() {
		try {
			this.lines.clear();
			this.lastLineY = 0;
		} catch (ConcurrentModificationException e) {
			System.out.println("Caught a CCME!");
		}
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
	public boolean onMouseDown(int paramInt, Point paramPoint) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onMouseUp(int paramInt, Point paramPoint) {
		// TODO Auto-generated method stub
		return false;
	}
}
