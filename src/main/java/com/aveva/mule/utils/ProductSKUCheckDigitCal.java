package com.aveva.mule.utils;

public class ProductSKUCheckDigitCal {

	public static String getCheckSum(String inputSku) throws ArithmeticException {
			long skuNumber = Long.valueOf(inputSku);
			int sumOdd = 0, sumEven = 0, counter = 1;
			while (skuNumber != 0) {
				if (counter % 2 == 0)
					sumEven += skuNumber % 10;
				else
					sumOdd += skuNumber % 10;
				skuNumber /= 10;
				counter++;
			}
			int checkSum = (10 - ((sumOdd * 3) + sumEven) % 10) % 10;
			String finalOutput = inputSku + checkSum;
			return finalOutput;
	}
}
