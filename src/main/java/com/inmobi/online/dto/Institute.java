package com.inmobi.online.dto;

public enum Institute {
	
	LEAD(1);
	
	private int id;
	

	Institute(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public static Institute findByValue(int value) {
		switch(value) {
		case 1:
    		return LEAD;
    	default :
    		return null;
		}
	}

}
