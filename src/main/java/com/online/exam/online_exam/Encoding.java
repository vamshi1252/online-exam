package com.online.exam.online_exam;

public class Encoding {
	
	
	
public static void fun(char[] input,int i, char[] output, int j, int len){
	
	if(i==len) {
		//printing array
		for(int k=0; k<j; k++){
		System.out.print(output[k]);
		}
		System.out.println("\n");
		return;
	}
	
	output[j] = getValue(input[i], ' ');
	if(output[j] == '1')
		return;
	fun(input, i+1, output, j+1,len);
	
	if(i+1 < len) {
	output[j]= getValue(input[i], input[i+1]);
	if(output[j] == '1')
		return;
	fun(input, i+2, output, j+1, len);
	}
	
}

public static char getValue(char ch1, char ch2) {
	 
	if(ch2 == ' ') {
	int a1 = ch1 - 48;
	char c = (char)(64 + a1);
	return c;
	}
	
	if((ch1 - 48) <= 2) {

		if((ch1 - 48) > 1 && (ch2 - 48) <= 6){
			int x = ch1 - 48;
			x = x*10 + (ch2-48);
			return (char)(x + 64);
		} 
		if((ch1 - 48) == 1){
			int x = ch1 - 48;
			x = x*10 + (ch2-48);
			return (char)(x + 64);
		} 
	}
	return '1';
}

public static void main(String args[]) {
	char ch = getValue('1', '7');
	System.out.println(ch);
	char[] input= { '1', '2', '1', '2','3'};
	char[] output= new char[10];
	fun(input, 0, output, 0 , 4);
}
	
}



