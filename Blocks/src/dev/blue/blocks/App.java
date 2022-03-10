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
import dev.blue.blocks.utils.ui.Pattern;
import dev.blue.blocks.utils.ui.Shape;
import dev.blue.blocks.utils.ui.TextArea;
import dev.blue.blocks.utils.ui.TextInputField;
import dev.blue.blocks.utils.ui.UIObjectRegistry;

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
	
	public App() {
		thread = new Thread(this);
		server = new Server();
		client = new Client();
		fonts = new Fonts();
		
		keyManager = new KeyManager(this);
		mouseManager = new MouseManager(this);
		window = new Window(this, "App");
		uiRegistry = new UIObjectRegistry();
		setupPage();
		initializeGraphics();
		System.out.println("Client set up");
		System.out.println("Server is running");
	}
	
	public void setupPage() {
		Pattern msgFieldPattern = new Pattern();
		Pattern msgBoardPattern = new Pattern();
		Pattern exitButtonPattern = new Pattern();
		Pattern exitButtonHoverPattern = new Pattern();
		SimplePoint[] msgFieldPoints = new SimplePoint[] 
			{new SimplePoint(0, window.getHeight()-28), new SimplePoint(window.getWidth(), window.getHeight()-28), new SimplePoint(window.getWidth(), window.getHeight()), new SimplePoint(0, window.getHeight())};
		SimplePoint[] msgBoardPoints = new SimplePoint[]
			{new SimplePoint(0, 0), new SimplePoint(window.getWidth(), 0), new SimplePoint(window.getWidth(), window.getHeight()-30), new SimplePoint(0, window.getHeight()-30)};
		SimplePoint[] exitButtonFillPoints = new SimplePoint[]
			{new SimplePoint(window.getWidth()-28, 2), new SimplePoint(window.getWidth()-2, 2), new SimplePoint(window.getWidth()-2, 28), new SimplePoint(window.getWidth()-28, 28)};
		SimplePoint[] exitButtonBorderPoints = new SimplePoint[]
			{new SimplePoint(window.getWidth()-30, 0), new SimplePoint(window.getWidth(), 0), new SimplePoint(window.getWidth(), 30), new SimplePoint(window.getWidth()-30, 30), 
			 new SimplePoint(window.getWidth()-30, 2), new SimplePoint(window.getWidth()-28, 2), new SimplePoint(window.getWidth()-28, 28), new SimplePoint(window.getWidth()-2, 28),
			 new SimplePoint(window.getWidth()-2, 2), new SimplePoint(window.getWidth()-30, 2)};
		msgFieldPattern.addShape(new Shape(Color.GRAY, msgFieldPoints));
		msgBoardPattern.addShape(new Shape(Color.GRAY, msgBoardPoints));
		exitButtonPattern.addShape(new Shape(new Color(150, 0, 0), exitButtonFillPoints));
		exitButtonPattern.addShape(new Shape(new Color(95, 0, 0), exitButtonBorderPoints));
		exitButtonHoverPattern.addShape(new Shape(new Color(180, 60, 60), exitButtonFillPoints));
		exitButtonHoverPattern.addShape(new Shape(new Color(95, 0, 0), exitButtonBorderPoints));
		
		TextArea messageBoard = new TextArea(this, "msgBoard", 0, 0, window.getWidth(), window.getHeight()-28, 5, msgBoardPattern);
		TextInputField messageField = new TextInputField(this, "message", 0, window.getHeight()-28, window.getWidth(), 28, "Send a message...", "", true, false, messageBoard, msgFieldPattern) {
			@Override
			public void onPrint() {
				client.sendMessage(this.getText());
			}
		};
		Button exitButton = new Button(this, "X", true, false, 14, window.getWidth()-30, 0, 30, 30, exitButtonPattern) {
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
		uiRegistry.registerObject(messageField);
		uiRegistry.registerObject(messageBoard);
		uiRegistry.registerObject(exitButton);
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
