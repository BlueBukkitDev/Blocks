package dev.blue.blocks;

import java.util.HashMap;

public class Block {
	
	private HashMap<String, String> data = new HashMap<String, String>();
	
	public Block(String name) {
		
	}
	
	public void addData(String field, String value) {
		data.put(field, value);
	}
	
	public void certify() {
		
	}
	
}
