package dev.blue.blocks.utils;

public class AccountManager {
	
	public static boolean isRegistered(String username) {
		ConfigOptions config = new ConfigOptions();
		if(config.getUsername().equalsIgnoreCase(username)) {
			return true;
		}
		return false;
	}
	
	public static boolean isAccountMatch(String username, String password) {
		ConfigOptions config = new ConfigOptions();
		if(config.getUsername().equalsIgnoreCase(username) && config.getPassword().equalsIgnoreCase(password)) {
			return true;
		}
		return false;
	}
}
