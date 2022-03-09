package dev.blue.blocks;

public class CommandExecutor {
	
	private Block currentBlock;
	
	App app;
	
	public CommandExecutor (App app) {
		this.app = app;
	}
	
	public boolean execute(String[] args) {
		//create <displayName> <path-to-database>#creates new block instance with a given name, referencing blocks in the given database.
		//set <fieldName> <value> #can be done any number of times, used to record data. Can have a value be the name of a previous field, if prefixed by "!". 
		//certify #attempts to build out the new block and create an ID based on last known block. If there is no prior block, generates a random ID. 
		
		//block (reference command) <id> get <field> #returns value
		
		
		if(args[0].equalsIgnoreCase("exit")||args[0].equalsIgnoreCase("quit")) {
			return false;
		}
		
		if(args[0].equalsIgnoreCase("create")) {
			runCreateArg(args);
		}else if(args[0].equalsIgnoreCase("set")) {
			runSetArg(args);
		}else if(args[0].equalsIgnoreCase("certify")) {
			runCertifyArg(args);
		}else if(args[0].equalsIgnoreCase("say")) {
			runSayArg(args);
		}
		
		return true;
	}
	
	private void runSayArg(String[] args) {
		String message = "";
		for(int i = 1; i < args.length; i++) {
			message += args[i];
		}
		app.sendMessage(message);
	}
	
	private void runCreateArg(String[] args) {
		if(args.length != 2) {
			System.err.println("Incorrect usage! Correct usage is \"create <name>\"");
			return;
		}
		currentBlock = new Block(args[1]);
	}
	
	private void runSetArg(String[] args) {
		if(args.length != 3) {
			System.err.println("Incorrect usage! Correct usage is \"set <field> <value>\"");
			return;
		}
		currentBlock.addData(args[1], args[2]);
	}
	
	private void runCertifyArg(String[] args) {
		if(args.length != 1) {
			System.err.println("Incorrect usage! Correct usage is \"certify\"");
			return;
		}
		currentBlock.certify();
	}
}
