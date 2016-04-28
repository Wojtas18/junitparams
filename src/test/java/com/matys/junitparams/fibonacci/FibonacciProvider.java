package com.matys.junitparams.fibonacci;

public class FibonacciProvider {
	
	public static Object[] provideCount() {
		return new Object[] {
				new Object[] {1, 1},
				new Object[] {2, 1},
				new Object[] {3, 2},
				new Object[] {5, 5},
				new Object[] {8, 21},
				new Object[] {10, 55}
			};
	}
	
	public static Object[] provideWrongParameters() {
		return new Object[] {
				new Object[] {0, -1},
				new Object[] {-10, -1},
			};
	}
}
