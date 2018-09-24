package com.excilys.cdb.app.menus;


public enum UpdateComputerMenu {
	EXIT(0, "Exit", "Goodbye", ""),
	NAME(1, "Edit name", "Enter a new name : ", "name"),
	INTRODUCED_DATE(2, "Edit introduced date" , "Enter a new introduced date (YYYY-MM-DD) : ", "introduced"),
	DISCONTINUED_DATE(3, "Edit discontinued date" , "Enter a discontinued date (YYYY-MM-DD) : ", "discontinued");
	
	private int optionId;
	private String optionTitle;
	private String order;
	private String column;

	static { //static instance initializer
		System.out.println("---------------------------");
		System.out.println("| Choose an action     	|");
		System.out.println("---------------------------");
	}
	
	UpdateComputerMenu(int optionId, String optionTitle, String order, String column) {
		this.optionId = optionId;
		this.optionTitle = optionTitle;
		this.order = order;
		this.column = column;
	}
	
	public String getOptionTitle() {
		return this.optionId + " - " + this.optionTitle;
	}
	
	public static void getOptions() {
		for (UpdateComputerMenu option : UpdateComputerMenu.values()) {
			System.out.println(option.getOptionTitle());
		}
		
		System.out.println("\nWhat field do you wish to update ?");
	}
	
	public void getOrder() {
		System.out.println(this.order);
	}
	public String getColumnToUpdate() {
		return this.column;
	}
	
}
