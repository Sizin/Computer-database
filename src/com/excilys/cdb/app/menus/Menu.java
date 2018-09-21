package com.excilys.cdb.app.menus;

public enum Menu {
	DISPLAY_COMPUTERS(1, "List computers"),
	DISPLAY_COMPANIES(2, "List companies"),
	COMPUTER_DETAIL(3, "Show computer details"),
	ADD_COMPUTER(4, "Create a computer"),
	UPDATE_COMPUTER(5, "Update a computer"),
	DELETE_COMPUTER(6, "Delete a computer"),
	EXIT(0, "Exit");
	
	private int optionId;
	private String optionTitle;

	static { //static instance initializer
		System.out.println("---------------------------");
		System.out.println("| Computer DB Application |");
		System.out.println("---------------------------");
	}
	
	Menu(int optionId, String optionTitle) {
		this.optionId = optionId;
		this.optionTitle = optionTitle;
	}
	
	public String getOptionTitle() {
		return this.optionId + " - " + this.optionTitle;
	}
	
	public static void getOptions() {
		for (Menu option : Menu.values()) {
			System.out.println(option.getOptionTitle());
		}
			
		System.out.println("\nChose one action");
	}
	
	
}
