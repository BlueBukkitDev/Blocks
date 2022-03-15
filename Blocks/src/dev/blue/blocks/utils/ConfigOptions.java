package dev.blue.blocks.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ConfigOptions {

	private List<String> options = new ArrayList<String>();
	//username
	//port
	//
	//
	//
	//
	//

	public ConfigOptions() throws IndexOutOfBoundsException, ArrayIndexOutOfBoundsException, NumberFormatException {
		initConfig();//Checks if the file exists; if not, creates it. 
		options.add(0, "");
		options.add(1, "");
		int index = 0;
		List<String> lines = new ArrayList<String>();
		try {
			lines = Files.readAllLines(Paths.get("blocks.cfg"));
		} catch (IOException e1) {
			System.out.println("Could not read from blocks.cfg file");
			return;
		}
		for(index = 0; index <= options.size(); index++){
			options.set(index, lines.get(index).split(":")[1]);
		}
		if (options.size() == 0) {
			saveCfg();
		}
	}

	public String getUsername() {
		return options.get(0);
	}

	public int getPort() {
		return Integer.parseInt(options.get(1));
	}
	
	public void setUsername(String username) {
		options.set(0, username);
	}
	
	public void setPort(String port) {
		options.set(1, port);
	}
	
	private void initConfig() {
		if (!new File("blocks.cfg").exists()) {
			FileWriter writer;
			try {
				writer = new FileWriter("blocks.cfg");
				writer.write("username:default" + System.lineSeparator());
				writer.write("port:45225" + System.lineSeparator());
				writer.close();
			} catch (IOException e) {
				System.out.println("Unable to setup config file");
			}
		}
	}

	private void saveCfg() {
		if(options.size() == 2) {
			if(options.get(0).length() < 4) {
				options.add(0, "default");
			}
			if(options.get(1).length() < 5) {
				options.add(1, "45225");
			}
		}
		FileWriter writer;
		try {
			writer = new FileWriter("blocks.cfg");
			for (String str : options) {
				writer.write(str + System.lineSeparator());
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("Unable to setup config file");
		}
	}
}
