package com.springblog.springblogapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SpringBlogApiApplicationTests {

	class Calculator {
		int add(int a, int b) {
			return a + b;
		}
	}

	Calculator underTest = new Calculator();

	@Test
	void itShouldAddTwoNumbers() {
		// given
		int numberOne = 20;
		int numberTwo = 22;

		// when
		int result = underTest.add(numberOne, numberTwo);

		// then
		assertThat(result).isEqualTo(42);
	}

}
