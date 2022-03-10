package dev.blue.blocks.utils.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import dev.blue.blocks.App;

public class Button extends UIObject {
	private App app;

	private int tooltipTimer = 0;

	private boolean showTooltip = false;

	private boolean useTooltip = false;

	protected boolean showingClicked = false;

	protected boolean isSelected;

	private boolean showID;

	private int fontSize;

	private Color color = Color.BLACK;
	
	private Pattern pattern;

	public Button(App app, String id, boolean showID, boolean useTooltip, int fontSize, int x, int y, int width,
			int height, Pattern pattern) {
		this.app = app;
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bounds = new Rectangle(x, y, width, height);
		this.showID = showID;
		this.fontSize = fontSize;
		this.useTooltip = useTooltip;
		this.pattern = pattern;
	}

	public void render(Graphics g) {
		pattern.draw(g);
		if (this.showID) {
			g.setFont(new Font("Helvetica", 1, this.fontSize));
			g.setColor(this.color);
			g.drawString(this.id, this.x + this.width / 2 - g.getFontMetrics().stringWidth(this.id) / 2,
					(int) ((this.y + this.height / 2) + g.getFontMetrics().getHeight() / 3.5D));
		}
		if (this.showTooltip && this.useTooltip) {
			g.setFont(new Font("Helvetica", 0, 12));
			g.setColor(Color.GRAY);
			g.drawString(this.id, this.x, (int) (this.y + g.getFontMetrics().getHeight() * 1.5D));
		}
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void update() {
		if (this.hovering) {
			this.tooltipTimer++;
		} else if (this.showingClicked) {
			this.showingClicked = false;
		}
		if (this.tooltipTimer >= 50)
			this.showTooltip = true;
		runOnUpdate();
	}

	public void runOnUpdate() {
	}

	public void onMouseMove(Point p) {
		if (this.bounds.contains(p)) {
			if (!this.hovering) {
				this.hovering = true;
				runOnHover();
			}
		} else if (this.hovering) {
			this.hovering = false;
			this.showTooltip = false;
			this.tooltipTimer = 0;
			runOnStopHover();
		}
	}

	public boolean onClick(int button, Point p) {
		if (this.bounds.contains(p)) {
			runClick();
			return true;
		}
		return false;
	}

	public void runOnMissedClick() {
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
		if (!this.bounds.contains(p)) {
			runOnMissedClick();
		}
		if (this.showingClicked) {
			this.showingClicked = false;
			if (this.bounds.contains(p)) {
				runMouseUp();
				onClick(button, p);
				return true;
			}
			return false;
		}
		return false;
	}

	public void onType(KeyEvent e) {
	}

	public void onKeyPressed(KeyEvent e) {
	}
	
	public void onScroll(int amount) {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
}
