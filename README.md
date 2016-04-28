# JUnitParams

JUnitParams is very nice tools, to write parametrized test with easy way. I tried explained everything on example with fibonacci test.

##### 1. First of all, we need a maven project. After create it our pom.xml should looks like that
----

``` xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.matys.junitparams</groupId>
	<artifactId>fibonacci</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>fibonacci</name>
	<url>http://maven.apache.org</url>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>

	<dependencies>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>3.8.1</version>
			<scope>test</scope>
		</dependency>
	</dependencies>
</project>
```
in pom.xml we need add some dependencies, but first we have to change version of JUnit to 4.12:

``` xml
<dependency>
    <groupId>pl.pragmatists</groupId>
    <artifactId>JUnitParams</artifactId>
    <version>1.0.5</version>
    <scope>test</scope>
</dependency>
```
and optional (but in this example I will use it)
``` xml
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.4.1</version>
</dependency>
```
dependencies should looks like

``` xml
<dependencies>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.12</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>pl.pragmatists</groupId>
			<artifactId>JUnitParams</artifactId>
			<version>1.0.5</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.assertj</groupId>
			<artifactId>assertj-core</artifactId>
			<version>3.4.1</version>
		</dependency>
	</dependencies>
```
#### 2. Create class with method which we will test and class to testing.
----
``` java
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
```
#### 3. First we have to add annotations and imports to our test class and then we can write first parametrized test
----
``` java
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class FibonacciTest {
}
```
#### 4. To write parametrized test are some ways, first If you have a simple parametrized test you can define test parameters as a value of the ```@Parameters``` annotations
----
``` java
import org.junit.Test;
import junitparams.Parameters;
import static org.assertj.core.api.Assertions.*;

    .
    .
    .
    
    //Parameters from a value of annotations
	@Test
	@Parameters({"1, 1",
				"2, 1",})
	public void countFibonacci(int index, int expected) {
		assertThat(new Fibonacci().count(index)).isEqualTo(expected);
	}
```

#### 5. If you want to have a lot of values you should write a method in test class which is data provider to ours test. You have to change annotations ```@Parameters``` to ```@Parameters(method = "parametersForCountFibonacci2")``` and our method should return array of Object.
----
``` java
//Parameters from method parametersForCountFibonacci2()
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
```
#### 6. Another way to write parameters test, create params provider. It will be useful when reading the test params from file or a database.
----

So in this case we are writing a FibonacciProvider class for our new test. This class must have at least one static method which name start with "provide" and returns arrays of Object.
``` java
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
}
```

``` java
    //Parameters from FibonacciProvider class
	@Test
	@Parameters(source = FibonacciProvider.class)
	public void countFibonacci3(int index, int expected) {
		assertThat(new Fibonacci().count(index)).isEqualTo(expected);
	}
```
Of course we can have a lot of methods in our provider class and will run all test from first and second method.

``` java
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
```
All test have been runed from first and second method.
