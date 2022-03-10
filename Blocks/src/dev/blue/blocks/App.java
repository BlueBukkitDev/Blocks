package dev.blue.blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import dev.blue.blocks.utils.Fonts;
import dev.blue.blocks.utils.KeyManager;
import dev.blue.blocks.utils.MouseManager;
import dev.blue.blocks.utils.SimplePoint;
import dev.blue.blocks.utils.ui.Button;
import dev.blue.blocks.utils.ui.TextArea;
import dev.blue.blocks.utils.ui.TextInputField;
import dev.blue.blocks.utils.ui.UIObjectRegistry;
import dev.blue.blocks.utils.ui.gfx.Pattern;
import dev.blue.blocks.utils.ui.gfx.Shape;

public class App implements Runnable {
	private Thread thread;
	private boolean running = false;
	private BufferStrategy bs;
	private double FPS= 60;
	
	private Server server;
	private Client client;
	private Window window;
	private KeyManager keyManager;
	private MouseManager mouseManager;
	private Fonts fonts;
	private UIObjectRegistry uiRegistry;
	
	private int cbW;
	private int cbB;
	private int bottom;
	private int top;
	private int left;
	private int right;
	private int msgFieldHeight;
	private int spacer;
	
	private Pattern msgFieldPattern;
	private Pattern msgBoardPattern;
	private Pattern exitButtonPattern;
	private Pattern exitButtonHoverPattern;
	private Pattern resizeButtonPattern;
	private Pattern resizeButtonHoverPattern;
	
	private TextArea messageBoard;
	private TextInputField messageField;
	private Button exitButton;
	private Button resizeButton;
	
	public App() {
		thread = new Thread(this);
		server = new Server();
		client = new Client();
		fonts = new Fonts();
		
		keyManager = new KeyManager(this);
		mouseManager = new MouseManager(this);
		window = new Window(this, "App");
		uiRegistry = new UIObjectRegistry();
		
		setupMeasurements();
		
		setupPage();
		initializeGraphics();
		
		System.out.println("Client set up");
		System.out.println("Server is running");
	}
	
	private void setupMeasurements() {
		cbW = (int)(window.getScreenSize().getWidth()/80);
		cbB = 2;
		bottom = window.getHeight();
		top = 0;
		left = 0;
		right = window.getWidth();
		msgFieldHeight = 28;
		spacer = 2;
	}
	
	public void setupPage() {
		setupCornerButtons();
		setupCommsFields();
		
		messageBoard = new TextArea(this, "msgBoard", left, top, right, bottom-msgFieldHeight, 5, msgBoardPattern);
		messageField = new TextInputField(this, "message", left, bottom-msgFieldHeight, right, msgFieldHeight, "Send a message...", "", true, false, messageBoard, msgFieldPattern) {
			@Override
			public void onPrint() {
				client.sendMessage(this.getText());
			}
		};
		exitButton = new Button(this, "exit", false, false, 14, right-cbW, top, cbW, cbW, exitButtonPattern) {
			@Override
			public void runClick() {
				client.close();
				server.stop();
				System.exit(0);
			}
			
			@Override
			public void runOnHover() {
				setPattern(exitButtonHoverPattern);
			}
			
			@Override
			public void runOnStopHover() {
				setPattern(exitButtonPattern);
			}
		};
		resizeButton = new Button(this, "Resize", false, false, 14, right-(cbW*2), top, cbW, cbW, resizeButtonPattern) {
			@Override
			public void runClick() {
				if(!window.isSmall) {
					window.resizeDown();
					resetPage();
				}else {
					window.resizeUp();
					resetPage();
				}
			}
			
			@Override
			public void runOnHover() {
				setPattern(resizeButtonHoverPattern);
			}
			
			@Override
			public void runOnStopHover() {
				setPattern(resizeButtonPattern);
			}
		};
		
		uiRegistry.registerObject(messageField);
		uiRegistry.registerObject(messageBoard);
		uiRegistry.registerObject(exitButton);
		uiRegistry.registerObject(resizeButton);
	}
	
	private void resetPage() {
		setupMeasurements();
		setupCornerButtons();
		setupCommsFields();
		exitButton.setX(right-cbW);
		exitButton.setY(top);
		exitButton.setPattern(exitButtonPattern);
		resizeButton.setX(right-(cbW*2));
		resizeButton.setY(top);
		resizeButton.setPattern(resizeButtonPattern);
		messageField.setX(left);
		messageField.setY(bottom-msgFieldHeight);
		messageField.setWidth(right);
		messageField.setPattern(msgFieldPattern);
		messageBoard.setX(left);
		messageBoard.setY(top);
		messageBoard.setWidth(right);
		messageBoard.setPattern(msgBoardPattern);
	}
	
	private void setupCommsFields() {
		msgFieldPattern = new Pattern();
		msgBoardPattern = new Pattern();
		SimplePoint[] msgFieldPoints = new SimplePoint[] 
			{new SimplePoint(left, bottom-msgFieldHeight), new SimplePoint(right, bottom-msgFieldHeight), new SimplePoint(right, bottom), new SimplePoint(left, bottom)};
		SimplePoint[] msgBoardPoints = new SimplePoint[]
			{new SimplePoint(left, top), new SimplePoint(right, top), new SimplePoint(right, bottom-msgFieldHeight+spacer), new SimplePoint(left, bottom-msgFieldHeight+spacer)};
		msgFieldPattern.addShape(new Shape(Color.GRAY, msgFieldPoints));
		msgBoardPattern.addShape(new Shape(Color.GRAY, msgBoardPoints));
	}
	
	private void setupCornerButtons() {
		exitButtonPattern = new Pattern();
		exitButtonHoverPattern = new Pattern();
		resizeButtonPattern = new Pattern();
		resizeButtonHoverPattern = new Pattern();
		SimplePoint[] exitButtonFillPoints = new SimplePoint[]
			{new SimplePoint(right-(cbW-cbB), cbB), new SimplePoint(right-cbB, cbB), new SimplePoint(right-cbB, cbW-cbB), new SimplePoint(right-(cbW-cbB), cbW-cbB)};
		SimplePoint[] exitButtonBorderPoints = new SimplePoint[]
			{new SimplePoint(right-cbW, top), new SimplePoint(right, top), new SimplePoint(right, cbW), new SimplePoint(right-cbW, cbW), 
			 new SimplePoint(right-cbW, cbB), new SimplePoint(right-(cbW-cbB), cbB), new SimplePoint(right-(cbW-cbB), cbW-cbB), new SimplePoint(right-cbB, cbW-cbB),
			 new SimplePoint(right-cbB, cbB), new SimplePoint(right-cbW, cbB)};
		SimplePoint[] resizeButtonFillPoints = new SimplePoint[] 
			{new SimplePoint(right-((cbW*2)-cbB), cbB), new SimplePoint(right-cbW-cbB, cbB), new SimplePoint(right-cbW-cbB, cbW-cbB), new SimplePoint(right-((cbW*2)-cbB), cbW-cbB)};
		SimplePoint[] resizeButtonBorderPoints = new SimplePoint[]
			{new SimplePoint(right-(cbW*2), top), new SimplePoint(right-(cbW*1), top), new SimplePoint(right-(cbW*1), cbW), new SimplePoint(right-(cbW*2), cbW), 
			 new SimplePoint(right-(cbW*2), cbB), new SimplePoint(right-((cbW*2)-cbB), cbB), new SimplePoint(right-((cbW*2)-cbB), cbW-cbB), new SimplePoint(right-cbB-(cbW*1), cbW-cbB),
			 new SimplePoint(right-cbB-(cbW*1), cbB), new SimplePoint(right-(cbW*2), cbB)};
		
		exitButtonPattern.addShape(new Shape(new Color(150, 0, 0), exitButtonFillPoints));
		exitButtonPattern.addShape(new Shape(new Color(60, 0, 0), exitButtonBorderPoints));
		exitButtonHoverPattern.addShape(new Shape(new Color(180, 60, 60), exitButtonFillPoints));
		exitButtonHoverPattern.addShape(new Shape(new Color(60, 0, 0), exitButtonBorderPoints));
		resizeButtonPattern.addShape(new Shape(new Color(150, 150, 150), resizeButtonFillPoints));
		resizeButtonPattern.addShape(new Shape(new Color(60, 60, 60), resizeButtonBorderPoints));
		resizeButtonHoverPattern.addShape(new Shape(new Color(180, 180, 180), resizeButtonFillPoints));
		resizeButtonHoverPattern.addShape(new Shape(new Color(60, 60, 60), resizeButtonBorderPoints));
	}
	
	public void sendMessage(String msg) {
		client.sendMessage(msg);//block it up first with commands
	}
	
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public MouseManager getMouseManager() {
		return mouseManager;
	}
	
	public Fonts getFonts() {
		return fonts;
	}
	
	public void render() {
		if (bs == null) {
			try {
				this.window.canvas.createBufferStrategy(2);
				bs = this.window.canvas.getBufferStrategy();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			render();
			return;
		}
		Graphics g = bs.getDrawGraphics();
		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.window.getWidth(), this.window.getHeight());
		//render everything
		uiRegistry.render(g);
		//end render
		g.dispose();
		bs.show();
	}
	
	public void update() {
		
	}
	
	public synchronized void start() {
		running = true;
		thread.start();
	}
	
	public synchronized void stop() {
		client.close();
		server.stop();
		try {
			thread.join(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public UIObjectRegistry getUIRegistry() {
		return uiRegistry;
	}
	
	public Window getWindow() {
		return window;
	}

	@Override
	public void run() {
		long lastFrame = System.nanoTime();
		double passed = 0.0D;
		while (this.running) {
			long now = System.nanoTime();
			passed = ((now - lastFrame) / 1000000L);
			if (passed >= 1000.0D/FPS) {
				passed = 0.0D;
				update();
				render();
				lastFrame = now;
			}
		}
		stop();
	}
	
	private void initializeGraphics() {
		BufferStrategy bs = this.window.canvas.getBufferStrategy();
		if (bs == null) {
			try {
				this.window.canvas.createBufferStrategy(2);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			initializeGraphics();
			return;
		}
		Graphics g = bs.getDrawGraphics();
		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		//this.graphics = g;
		g.dispose();
		bs.show();
	}
}
