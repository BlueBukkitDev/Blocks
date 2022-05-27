package dev.blue.blocks.utils;

public class Profile {
	private String profileName;
	private String username;
	private String password;
	private int port;
	
	public Profile(String profileName, String username, String password, int port) {
		this.profileName = profileName;
		this.username = username; 
		this.password = password; 
		this.port = port;
	}
	
	public String getProfileName() {
		return profileName;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}
	
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
