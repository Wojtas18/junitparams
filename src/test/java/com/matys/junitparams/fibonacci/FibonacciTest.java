package com.matys.junitparams.fibonacci;

import org.junit.runner.RunWith;
import org.junit.Test;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.assertj.core.api.Assertions.*;

@RunWith(JUnitParamsRunner.class)
public class FibonacciTest {
	
	
	//Parameters from a value of annotations
	@Test
	@Parameters({"1, 1",
				"2, 1",})
	public void countFibonacci(int index, int expected) {
		assertThat(new Fibonacci().count(index)).isEqualTo(expected);
	}
	
	
	//Parameters from method
	@Test
	@Parameters(method = "parametersForCountFibonacci2")
	public void countFibonnaci2(int index, int expected) {
		assertThat(new Fibonacci().count(index)).isEqualTo(expected);
	}
	
	
	private Object[] parametersForCountFibonacci2() {
		return new Object[] {
			new Object[] {1, 1},
			new Object[] {2, 1},
			new Object[] {3, 2},
			new Object[] {5, 5},
			new Object[] {8, 21},
			new Object[] {10, 55}
		};
	}
	
	@Test
	@Parameters(source = FibonacciProvider.class)
	public void countFibonacci3(int index, int expected) {
		assertThat(new Fibonacci().count(index)).isEqualTo(expected);
	}
	
}
