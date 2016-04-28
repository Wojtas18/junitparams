package com.matys.junitparams.fibonacci;

public class Fibonacci {

	public int count(int index) {
		int sum = 1;
		int numberOne = 1;
		int numberTwo = 1;
		if (index < 1) {
			return -1;
		}
		if(index < 3) {
			return sum;
		} else {
			for (int i = 2; i < index; i++) {
				sum = numberOne + numberTwo;
				numberOne = numberTwo;
				numberTwo = sum;	
			}
		}
		return sum;
	}
}
