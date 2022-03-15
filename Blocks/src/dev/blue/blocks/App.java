package dev.blue.blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import dev.blue.blocks.utils.ConfigOptions;
import dev.blue.blocks.utils.Fonts;
import dev.blue.blocks.utils.KeyManager;
import dev.blue.blocks.utils.MouseManager;
import dev.blue.blocks.utils.SP;
import dev.blue.blocks.utils.ui.Button;
import dev.blue.blocks.utils.ui.NumberInputField;
import dev.blue.blocks.utils.ui.TextArea;
import dev.blue.blocks.utils.ui.TextInputField;
import dev.blue.blocks.utils.ui.UIObjectRegistry;
import dev.blue.blocks.utils.ui.gfx.Graphic;
import dev.blue.blocks.utils.ui.gfx.Pattern;
import dev.blue.blocks.utils.ui.gfx.Shape;

public class App implements Runnable {
	private Thread thread;
	private boolean running = false;
	private BufferStrategy bs;
	private double FPS= 60;
	
	private int cbW;
	private int cbB;
	private int bottom;
	private int top;
	private int left;
	private int right;
	private int msgFieldHeight;
	private int spacer;
	private int msgMenuWidth;
	private int profileHeight;
	
	private Server server;
	private Client client;
	private Window window;
	private KeyManager keyManager;
	private MouseManager mouseManager;
	private Fonts fonts;
	private UIObjectRegistry uiRegistry;
	private ConfigOptions config;
	
	private Pattern msgFieldPattern;
	private Pattern msgBoardPattern;
	private Pattern usernamePattern;
	private Pattern exitButtonPattern;
	private Pattern exitButtonHoverPattern;
	private Pattern resizeButtonPattern;
	private Pattern resizeButtonHoverPattern;
	
	private Pattern fbSpacerGraphicPattern;
	private Pattern fbMenuSpacerGraphicPattern;
	private Pattern headerGraphicPattern;
	private Pattern headerSpacerGraphicPattern;
	private Pattern profileGraphicPattern;
	private Pattern profileSpacerGraphicPattern;
	private Pattern msgMenuGraphicPattern;
	private Pattern portOutPattern;
	
	private TextArea messageBoard;
	private TextInputField messageField;
	private TextInputField username;
	//private TextInputField ipTarget;
	private NumberInputField portOut;
	//private NumberInputField portTarget;
	private Button exitButton;
	private Button resizeButton;
	
	private Graphic fbSpacer;
	private Graphic fbMenuSpacer;
	private Graphic header;
	private Graphic headerSpacer;
	private Graphic profile;
	private Graphic profileSpacer;
	private Graphic msgMenu;
	
	public App() {
		thread = new Thread(this);
		server = new Server();
		client = new Client();
		fonts = new Fonts();
		
		keyManager = new KeyManager(this);
		mouseManager = new MouseManager(this);
		window = new Window(this, "App");
		uiRegistry = new UIObjectRegistry();
		config = new ConfigOptions();
		
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
		msgMenuWidth = (right/7);
		profileHeight = (bottom/7);
	}
	
	public void setupPage() {
		messageBoard = new TextArea(this, "msgBoard", msgMenuWidth+spacer, cbW+spacer, right-msgMenuWidth-spacer, bottom-msgFieldHeight-cbW-spacer, 5, msgBoardPattern);
		messageField = new TextInputField(this, "message", msgMenuWidth+spacer, bottom-msgFieldHeight, right-msgMenuWidth-spacer, msgFieldHeight, "Send a message...", "", true, false, messageBoard, msgFieldPattern) {
			@Override
			public boolean onPrint() {
				if(username.getText().length() <= 4) {
					messageBoard.logInfo("You must set a username at least 4 characters long");
					return false;
				}else {
					client.sendMessage(username.getText()+" "+this.getText());
					prefix = username.getText()+" ";
					return true;
				}
			}
		};
		username = new TextInputField(this, "username", spacer, profileHeight-msgFieldHeight-spacer+cbW, (msgMenuWidth-(spacer*2))/3*2, msgFieldHeight, "username", config.getUsername(), true, false, null, usernamePattern) {
			@Override
			public boolean onPrint() {
				if(getText().length() >= 4) {
					config.setUsername(getText());
					return false;
				}
				return false;
			}
		};
		portOut = new NumberInputField(this, "portOut", username.getX()+username.getWidth()+spacer, username.getY(), username.getWidth()/2-spacer, username.getHeight(), "port", ""+config.getPort(), true, false, null, portOutPattern) {
			
		};
		setupCornerButtons();
		setupGraphics();
		/*ipTarget;
		portTarget;*/
		
		setupCornerButtonPatterns();
		setupCommsFieldsPatterns();
		setupGraphicPatterns();
		
		messageBoard.setPattern(msgBoardPattern);
		messageField.setPattern(msgFieldPattern);
		username.setPattern(usernamePattern);
		portOut.setPattern(portOutPattern);
		
		uiRegistry.registerObject(fbSpacer);
		uiRegistry.registerObject(fbMenuSpacer);
		uiRegistry.registerObject(header);
		uiRegistry.registerObject(headerSpacer);
		uiRegistry.registerObject(profile);
		uiRegistry.registerObject(profileSpacer);
		uiRegistry.registerObject(msgMenu);
		
		uiRegistry.registerObject(messageField);
		uiRegistry.registerObject(messageBoard);
		uiRegistry.registerObject(username);
		uiRegistry.registerObject(portOut);
		
		uiRegistry.registerObject(exitButton);
		uiRegistry.registerObject(resizeButton);
	}
	
	private void setupGraphics() {
		fbSpacer = new Graphic("fbSpacer", fbSpacerGraphicPattern);
		fbMenuSpacer = new Graphic("fbMenuSpacer", fbMenuSpacerGraphicPattern);
		header = new Graphic("header", headerGraphicPattern);
		headerSpacer = new Graphic("headerSpacer", headerSpacerGraphicPattern);
		profile = new Graphic("profile", profileGraphicPattern);
		profileSpacer = new Graphic("profileSpacer", profileSpacerGraphicPattern);
		msgMenu = new Graphic("msgMenu", msgMenuGraphicPattern);
	}
	
	private void setupGraphicPatterns() {
		fbSpacerGraphicPattern = new Pattern();
		fbMenuSpacerGraphicPattern = new Pattern();
		headerGraphicPattern = new Pattern();
		headerSpacerGraphicPattern = new Pattern();
		profileGraphicPattern = new Pattern();
		profileSpacerGraphicPattern = new Pattern();
		msgMenuGraphicPattern = new Pattern();
		
		fbSpacerGraphicPattern.addShape(new Shape(fbSpacer, Color.YELLOW, new SP(left+msgMenuWidth, bottom-msgFieldHeight-spacer), 
			new SP(right, bottom-msgFieldHeight-spacer), new SP(right, bottom-msgFieldHeight), new SP(left+msgMenuWidth, bottom-msgFieldHeight)));
		fbMenuSpacerGraphicPattern.addShape(new Shape(fbMenuSpacer, Color.YELLOW, new SP(msgMenuWidth, top+cbW+spacer), new SP(msgMenuWidth+spacer, top+cbW+spacer),
			new SP(msgMenuWidth+spacer, bottom), new SP(msgMenuWidth, bottom)));
		headerGraphicPattern.addShape(new Shape(header, new Color(70, 65, 60), new SP(left, top), new SP(right-(cbW*2), top), new SP(right-(cbW*2), top+cbW), new SP(left, top+cbW)));
		headerSpacerGraphicPattern.addShape(new Shape(headerSpacer, Color.YELLOW, new SP(left, cbW), new SP(right, cbW), new SP(right, cbW+spacer), new SP(left, cbW+spacer)));
		profileGraphicPattern.addShape(new Shape(profile, Color.GRAY, new SP(left, top+cbW+spacer), new SP(left+msgMenuWidth, top+cbW+spacer), 
			new SP(left+msgMenuWidth, profileHeight+cbW+spacer), new SP(left, profileHeight+cbW+spacer)));
		profileSpacerGraphicPattern.addShape(new Shape(profileSpacer, Color.YELLOW, new SP(left, profileHeight+cbW+spacer), new SP(left+msgMenuWidth, profileHeight+cbW+spacer), 
			new SP(left+msgMenuWidth, profileHeight+cbW+(spacer*2)), new SP(left, profileHeight+cbW+(spacer*2))));
		msgMenuGraphicPattern.addShape(new Shape(msgMenu, Color.GRAY, new SP(left, profileHeight+cbW+(spacer*2)), new SP(left+msgMenuWidth, profileHeight+cbW+(spacer*2)), 
			new SP(left+msgMenuWidth, bottom), new SP(left, bottom)));
		
		
		fbSpacer.setPattern(fbSpacerGraphicPattern);
		fbMenuSpacer.setPattern(fbMenuSpacerGraphicPattern);
		header.setPattern(headerGraphicPattern);
		headerSpacer.setPattern(headerSpacerGraphicPattern);
		profile.setPattern(profileGraphicPattern);
		profileSpacer.setPattern(profileSpacerGraphicPattern);
		msgMenu.setPattern(msgMenuGraphicPattern);
	}
	
	private void setupCornerButtons() {
		exitButton = new Button(this, "exit", false, false, 14, right-cbW, top, cbW, cbW, exitButtonPattern) {
			@Override
			public void runClick() {
				try {
					client.close();
					server.stop();
				}catch(Exception e) {
					
				}
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
	}
	
	private void resetPage() {
		setupMeasurements();
		setupCornerButtonPatterns();
		setupCommsFieldsPatterns();
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
	
	private void setupCommsFieldsPatterns() {
		msgFieldPattern = new Pattern();
		msgBoardPattern = new Pattern();
		usernamePattern = new Pattern();
		portOutPattern = new Pattern();
		
		msgFieldPattern.addShape(new Shape(messageField, new Color(60, 60, 60), 
			new SP(left, top), new SP(messageField.getWidth(), top), new SP(messageField.getWidth(), messageField.getHeight()), new SP(left, messageField.getHeight())));
		msgBoardPattern.addShape(new Shape(messageBoard, new Color(80, 80, 80), 
			new SP(left, top), new SP(messageBoard.getWidth(), top), new SP(messageBoard.getWidth(), messageBoard.getHeight()), new SP(left, messageBoard.getHeight())));
		usernamePattern.addShape(new Shape(username, new Color(200, 200, 200), 
			new SP(left, top), new SP(username.getWidth(), top), new SP(username.getWidth(), username.getHeight()), new SP(left, username.getHeight())));
		portOutPattern.addShape(new Shape(portOut, new Color(200, 200, 200),
			new SP(left, top), new SP(portOut.getWidth(), top), new SP(portOut.getWidth(), portOut.getHeight()), new SP(left, portOut.getHeight())));
	}
	
	private void setupCornerButtonPatterns() {
		exitButtonPattern = new Pattern();
		exitButtonHoverPattern = new Pattern();
		resizeButtonPattern = new Pattern();
		resizeButtonHoverPattern = new Pattern();
		SP[] exitButtonFillPoints = new SP[] {new SP(cbB, cbB), new SP(cbW-cbB, cbB), new SP(cbW-cbB, cbW-cbB), new SP(cbB, cbW-cbB)};
		SP[] exitButtonBorderPoints = new SP[]
			{new SP(left, top), new SP(cbW, top), new SP(cbW, cbW), new SP(left, cbW),//bl
			 new SP(left, cbB), new SP(cbB, cbB), new SP(cbB, cbW-cbB), new SP(cbW-cbB, cbW-cbB),//br
			 new SP(cbW-cbB, cbB), new SP(left, cbB)};
		SP[] resizeButtonFillPoints = new SP[] {new SP(cbB, cbB), new SP(cbW-cbB, cbB), new SP(cbW-cbB, cbW-cbB), new SP(cbB, cbW-cbB)};
		SP[] resizeButtonBorderPoints = new SP[]
				{new SP(left, top), new SP(cbW, top), new SP(cbW, cbW), new SP(left, cbW),//bl
				 new SP(left, cbB), new SP(cbB, cbB), new SP(cbB, cbW-cbB), new SP(cbW-cbB, cbW-cbB),//br
				 new SP(cbW-cbB, cbB), new SP(left, cbB)};
		
		exitButtonPattern.addShape(new Shape(exitButton, new Color(150, 0, 0), exitButtonFillPoints));
		exitButtonPattern.addShape(new Shape(exitButton, new Color(60, 0, 0), exitButtonBorderPoints));
		exitButtonHoverPattern.addShape(new Shape(exitButton, new Color(180, 60, 60), exitButtonFillPoints));
		exitButtonHoverPattern.addShape(new Shape(exitButton, new Color(60, 0, 0), exitButtonBorderPoints));
		resizeButtonPattern.addShape(new Shape(resizeButton, new Color(150, 150, 150), resizeButtonFillPoints));
		resizeButtonPattern.addShape(new Shape(resizeButton, new Color(60, 60, 60), resizeButtonBorderPoints));
		resizeButtonHoverPattern.addShape(new Shape(resizeButton, new Color(180, 180, 180), resizeButtonFillPoints));
		resizeButtonHoverPattern.addShape(new Shape(resizeButton, new Color(60, 60, 60), resizeButtonBorderPoints));
		
		exitButton.setPattern(exitButtonPattern);
		resizeButton.setPattern(resizeButtonPattern);
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
