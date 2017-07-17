package com.tcs.app.services.tasks;

public class NumberFormatter {

	public static String process(String numberText) {
		
		String[] numTexts = numberText.split(" ");
		char[] numbers = new char[numTexts.length];
		String finalNumbers = null;
		
		for (int i = 0; i<numTexts.length; i++){
			switch (numTexts[i]){
			
				case "one": numbers[i] = '1';break;
				case "two": numbers[i] = '2';break;
				case "three": numbers[i] = '3';break;
				case "four": numbers[i] = '4';break;
				case "five": numbers[i] = '5';break;
				case "six": numbers[i] = '6';break;
				case "seven": numbers[i] = '7';break;
				case "eight": numbers[i] = '8';break;
				case "nine": numbers[i] = '9';break;
				case "zero": numbers[i] = '0';break;
				default : ; 
				
			}
		}
		
		finalNumbers = String.copyValueOf(numbers);
		return finalNumbers;
	}
	
}
