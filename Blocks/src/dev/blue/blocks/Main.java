package dev.blue.blocks;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;

public class Main {
	
	public static void main(String[] args) {
		try {
		    UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch( Exception ex ) {
		    System.err.println( "Failed to initialize L&F" );//https://mvnrepository.com/artifact/com.formdev/flatlaf/2.2
		}
		new Window("Blocks");
	}
}
