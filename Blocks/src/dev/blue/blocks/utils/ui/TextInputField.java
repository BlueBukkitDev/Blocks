package dev.blue.blocks.utils.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;

import dev.blue.blocks.App;

public class TextInputField extends InputField {

	private App app;

	protected boolean writable;

	protected int vertIndent = 4;

	protected int indent = 6;

	protected int timer = 0;

	protected int blinkSpeed = 16;

	protected String text1 = "";

	protected String text2 = "";

	protected String displayText = "";

	protected String preview = "";

	protected char[] allowed = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '0', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '+', '=', '~', '`',
			',', '<', '.', '>', '/', '?', '\\', '|', ' ', '{', '}', '[', ']', ':', ';', '\'', '"', '\'' };

	protected Graphics g;

	protected Font font;

	protected int typeIndex = 0;

	protected boolean protectedDisplay = false;

	protected boolean showingClicked = false;

	protected boolean visible = true;

	private TextArea toWriteTo;
	
	private Pattern pattern;

	public TextInputField(App app, String id, int x, int y, int width, int height, String preview, String value,
			boolean writable, boolean protectedDisplay, TextArea toWriteTo, Pattern pattern) {
		super(id, x, y, width, height);
		this.pattern = pattern;
		this.app = app;
		this.writable = writable;
		this.preview = preview;
		this.text1 = value;
		this.displayText = this.text1;
		this.protectedDisplay = protectedDisplay;
		this.toWriteTo = toWriteTo;
	}

	public void render(Graphics g) {
		if (this.visible) {
			pattern.draw(g);
			if (this.text1.length() + this.text2.length() >= 1) {
				if (this.font == null) {
					g.setFont(new Font("Arial", Font.PLAIN, 16));
				} else {
					g.setFont(this.font);
				}
				g.setColor(Color.BLACK);
				if (this.protectedDisplay) {
					String replacement = "";
					for (int i = 0; i < this.displayText.length(); i++)
						replacement = String.valueOf(replacement) + "*";
					g.drawString(replacement, this.x + this.indent - 1, this.y + g.getFontMetrics().getHeight());
				} else {
					g.drawString(this.displayText, this.x + this.indent - 1, this.y + g.getFontMetrics().getHeight());
				}
			} else if (!isSelected()) {
				if (this.font == null) {
					g.setFont(new Font("Arial", Font.ITALIC, 14));
				} else {
					g.setFont(this.font);
				}
				g.setColor(Color.BLACK);
				g.drawString(this.preview, this.x + this.indent - 1, this.y + g.getFontMetrics().getHeight());
			}
			this.g = g;
			if (this.selected) {
				if (this.timer >= 2 * this.blinkSpeed)
					this.timer = 0;
				if (this.timer <= this.blinkSpeed) {
					g.setColor(Color.BLACK);
					g.fillRect(
							this.x + g.getFontMetrics().stringWidth(this.displayText.substring(0, this.text1.length()))
									+ this.indent - 1,
							this.y + this.vertIndent, 2, this.height - this.vertIndent * 2);
				}
			}
			this.timer++;
		}
	}

	public String getText() {
		return String.valueOf(this.text1) + this.text2;
	}

	public void setText(String text) {
		this.text1 = text;
		this.text2 = "";
		this.displayText = String.valueOf(this.text1) + this.text2;
		this.typeIndex = this.displayText.length();
	}

	public void update() {
		if (this.selected && app.getUIRegistry().selected != this) {
			this.selected = false;
		}
	}

	public boolean onClick(int button, Point p) {
		if (this.visible) {
			if (this.bounds.contains(p)) {
				this.timer = 0;
				if (this.writable) {
					this.selected = true;
					if (app.getUIRegistry().selected == null || app.getUIRegistry().selected != this) {
						app.getUIRegistry().selected = this;
					}
				}
				int i = getIndex(p);
				if (i < this.displayText.length()) {
					this.text1 = this.displayText.substring(0, i);
					this.text2 = this.displayText.substring(i, this.displayText.length());
					this.typeIndex = i;
				} else if (i >= this.displayText.length()) {
					this.text1 = this.displayText;
					this.text2 = "";
					this.typeIndex = this.displayText.length();
				}
				return true;
			}
			this.selected = false;
		}
		return false;
	}

	public boolean onMouseDown(int button, Point p) {
		if (this.bounds.contains(p)) {
			app.getMouseManager().clickedObject = this;
			runMouseDown();
			this.showingClicked = true;
			return true;
		}
		return false;
	}

	public boolean onMouseUp(int button, Point p) {
		if (app.getMouseManager().clickedObject == this) {
			app.getMouseManager().clickedObject = null;
		}
		if (this.bounds.contains(p)) {
			if (this.showingClicked) {
				this.showingClicked = false;
				runMouseUp();
				onClick(button, p);
				return true;
			}
			return false;
		}
		if (app.getUIRegistry().selected != null
				&& app.getUIRegistry().selected == this)
			app.getUIRegistry().selected = null;
		this.selected = false;
		return false;
	}

	public void onMouseMove(Point p) {
		if (this.visible)
			if (this.bounds.contains(p)) {
				this.hovering = true;
				//app.getMouseManager().setCursor(app.getTextures().typeCursor);
			} else {
				this.hovering = false;
				//app.getMouseManager().setCursor(app.getTextures().basicCursor);
			}
	}

	public void onType(KeyEvent e) {
		if (this.visible) {
			if (this.g.getFontMetrics().stringWidth(
					String.valueOf(this.text1) + this.text2 + e.getKeyChar()) <= this.width - this.indent * 2) {
				byte b;
				int i;
				char[] arrayOfChar;
				for (i = (arrayOfChar = this.allowed).length, b = 0; b < i;) {
					char each = arrayOfChar[b];
					if (each == e.getKeyChar()) {
						this.text1 = String.valueOf(this.text1) + e.getKeyChar();
						this.displayText = String.valueOf(this.text1) + this.text2;
						this.typeIndex++;
						break;
					}
					b++;
				}
			}
			if (e.getKeyChar() == '\b' && this.text1.length() > 0) {
				this.text1 = this.text1.substring(0, this.text1.length() - 1);
				this.displayText = String.valueOf(this.text1) + this.text2;
				this.typeIndex--;
			}
			if (this.protectedDisplay) {
				String replacement = "";
				for (int i = 0; i < this.displayText.length(); i++)
					replacement = String.valueOf(replacement) + "*";
				this.displayText = replacement;
			}
			onTypeExtra();
		}
	}

	public void onTypeExtra() {
	}

	public void onKeyPressed(KeyEvent e) {
		if (this.visible)
			if (e.getKeyCode() == 37) {
				if (this.typeIndex >= 1) {
					this.text1 = this.text1.substring(0, this.text1.length() - 1);
					this.text2 = this.displayText.substring(this.typeIndex - 1, this.displayText.length());
					this.typeIndex--;
					this.timer = 0;
				}
			} else if (e.getKeyCode() == 39) {
				if (this.typeIndex < this.displayText.length()) {
					this.text1 = this.displayText.substring(0, this.text1.length() + 1);
					this.text2 = this.displayText.substring(this.typeIndex + 1, this.displayText.length());
					this.typeIndex++;
					this.timer = 0;
				}
			} else if (e.getKeyCode() == 10) {
				print();
			}
	}
	
	@Override
	public void onScroll(int amount) {
		
	}

	public TextInputField setFont(Font font) {
		this.font = font;
		return this;
	}
	
	public void onPrint() {}

	public void print() {
    if (this.toWriteTo != null) {
      String message = getText();
      if (message.contains("§")) {
        TextBit[] bits = getBits(message);
        if (bits != null) {
          this.toWriteTo.addLine(bits);
          onPrint();
          setText("");
        } 
      } else {
        this.toWriteTo.addLine(new TextBit[] { new TextBit(app, Color.BLACK, (app.getFonts()).plain, message, null, null) });
        onPrint();
        setText("");
      } 
    }

	}

	public void setToWriteTo(TextArea toWriteTo) {
		this.toWriteTo = toWriteTo;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible.booleanValue();
	}

	private TextBit[] getBits(String message) {
    String[] parts = message.split("§");
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
        bit = new TextBit(app, Color.BLACK, (app.getFonts()).plain, "§"+ parts[i], null, null);
      } 
      bits[i] = bit;
    } 
    return bits;
  }

	private int getIndex(Point p) {
		if (this.bounds.contains(p))
			for (int i = 0; i < this.displayText.length(); i++) {
				if ((this.x + this.indent + this.g.getFontMetrics().stringWidth(this.displayText.substring(0, i))) >= p
						.getX())
					return i;
				if ((this.x + this.indent + this.g.getFontMetrics().stringWidth(this.displayText)) < p.getX())
					return this.displayText.length();
			}
		return 0;
	}
}
