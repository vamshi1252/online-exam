package com.inmobi.online.dto;

/*
 * @author vamshi.vijay
 */

public enum Branch {

	CSE(1), ECE(2), EEE(3), CIVIL(4), MECH(5);

	private int id;

	Branch(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public static Branch findByValue(int value) {
		switch(value) {
		case 1:
    		return CSE;
		case 2:
    		return CSE;
		case 3:
    		return CSE;
		case 4:
    		return CSE;
		case 5:
    		return CSE;
    	default :
    		return null;
		}
	}

}
