package com.signimus.Task;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator {

	public static double calculate(double num1, double num2, char operator) {
		switch (operator) {
		case '+':
			return num1 + num2;
		case '-':
			return num1 - num2;
		case '*':
			return num1 * num2;
		case '/':

			if (num2 == 0) {
				System.out.println("Undifined cannot be divided by 0");
			} else {
				return num1 / num2;
			}

		default:
			return Double.MAX_VALUE;

		}
	}

	public static void main(String[] args) {

		try (Scanner sc = new Scanner(System.in)) {
			double num1, num2;
			char operator;

			try {
				System.out.println("Enter The Num1:");
				num1 = sc.nextDouble();

				System.out
						.println("Enter The Operator: 1.Addtion = + 2.Substraction = -  3.Multiply = * 4.Division = /");
				operator = sc.next().charAt(0);

				System.out.println("Enter The Num2");
				num2 = sc.nextDouble();

				double result = calculate(num1, num2, operator);

				if (result == Double.MAX_VALUE) {
					System.out.println("Error Occurred invalid input try again");
				} else {
					System.out.println("result:" + result);
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid Input Plese give number input only");
			}
		}
	}

}
