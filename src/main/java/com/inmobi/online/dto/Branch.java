package com.inmobi.online.dto;

/*
 * @author vamshi.vijay
 */

public enum Branch {

	CS(1), EC(2), EE(3), CE(4), ME(5);

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
    		return CS;
		case 2:
    		return EC;
		case 3:
    		return EE;
		case 4:
    		return CE;
		case 5:
    		return ME;
    	default :
    		return null;
		}
	}

}
