package com.online.exam.online_exam;

public class Remove {

	public static long remove(int[] input, int start, int end) {

	/*	if (start == end) {
			return Integer.MAX_VALUE;
		}
*/
		if (checkValid(input, start, end))
			return 0;
		
		if(end-start == 1) return Integer.MAX_VALUE;

		return 1 + Math.min(remove(input, start + 1, end), remove(input, start, end - 1));

	}

	public static boolean checkValid(int[] input, int start, int end) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;

		for (int i = start; i <= end; i++) {
			if (input[i] < min) {
				min = input[i];
			}
			if (input[i] > max) {
				max = input[i];
			}
		}

		if (2 * min >= max)
			return true;
		return false;

	}

	public static void main(String args[]) {

		int[] input = {4, 5, 100, 9, 10, 11, 12, 15, 200};
		//System.out.println(input.length);
		System.out.println(remove(input, 0,(input.length)-1));
	}

}
