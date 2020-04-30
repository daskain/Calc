import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter expression (delimiter: space): ");
		String expression = sc.nextLine();

		if (expression.length() >= 5 && expression.length() <= 8) {
			String[] buf = expression.split(" ");

			if (buf.length == 3) {

				if (buf[1].matches("[\\+\\-\\/\\*]")) {

					if (!buf[0].matches("(X{0}(?:V?I{0,3}|I[VX]|X))|([1-9]|10)")) {
						System.out.println("Out of range [1,10] OR [I,X]: " + buf[0]);

					} else if (!buf[2].matches("(X{0}(?:V?I{0,3}|I[VX]|X))|([1-9]|10)")) {
						System.out.println("Out of range [1,10] OR [I,X]: " + buf[2]);

					} else if (buf[0].matches("[1-9]|10") && buf[2].matches("X{0}(?:V?I{0,3}|I[VX]|X)")) {
						System.out.println("Do not use arabian and roman: firstNumber arab=" + buf[0]
								+ ", secondNumber roman=" + buf[2]);

					} else if (buf[2].matches("[1-9]|10") && buf[0].matches("X{0}(?:V?I{0,3}|I[VX]|X)")) {
						System.out.println("Do not use arabian and roman: firstNumber arab=" + buf[2]
								+ ", secondNumber roman=" + buf[0]);

					} else if (buf[0].matches("X{0}(?:V?I{0,3}|I[VX]|X)")
							&& buf[2].matches("X{0}(?:V?I{0,3}|I[VX]|X)")) {
						
						System.out.println(arabicToRoman(resultMath(romanToArabic(buf[0]), romanToArabic(buf[2]), buf[1])));

					} else if (buf[0].matches("[1-9]|10") && buf[2].matches("[1-9]|10")) {
						System.out.println(resultMath(buf));

					} else {
						System.out.println("Ivalid arguments: firstNumber=" + buf[0] + ", secondNumber=" + buf[2]
								+ ", expression=\"" + buf[1] + "\"");
					}
				} else {

					System.out.println("Invalid math expression: " + buf[1]);
				}
			} else {
				System.out.println("Input error! Try again!");
			}
		} else {
			System.out.println("Input error! Try again!");
		}

		sc.close();

	}

	private static int resultMath(String... args) {
		int result = 0;
		String sign = args[1];
		int firstNumber = Integer.parseInt(args[0]);
		int secondNumber = Integer.parseInt(args[2]);

		if (sign.equals("+")) {
			result = firstNumber + secondNumber;
		} else if (sign.equals("-")) {
			result = firstNumber - secondNumber;
		} else if (sign.equals("*")) {
			result = firstNumber * secondNumber;
		} else if (sign.equals("/")) {
			result = firstNumber / secondNumber;
		}

		return result;
	}

	private static int resultMath(int firstNumber, int secondNumber, String sign) {
		int result = 0;

		if (sign.equals("+")) {
			result = firstNumber + secondNumber;
		} else if (sign.equals("-")) {
			result = firstNumber - secondNumber;
		} else if (sign.equals("*")) {
			result = firstNumber * secondNumber;
		} else if (sign.equals("/")) {
			result = firstNumber / secondNumber;
		}

		return result;
	}

	public static int romanToArabic(String input) {

		String romanNumeral = input.toUpperCase();
		int result = 0;

		List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

		int i = 0;

		while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
			RomanNumeral symbol = romanNumerals.get(i);
			if (romanNumeral.startsWith(symbol.name())) {
				result += symbol.getValue();
				romanNumeral = romanNumeral.substring(symbol.name().length());
			} else {
				i++;
			}
		}

		if (romanNumeral.length() > 0) {
			throw new IllegalArgumentException(input + " cannot be converted to a Roman Numeral");
		}

		return result;
	}
	
	public static String arabicToRoman(int number) {
	    if ((number <= 0) || (number > 4000)) {
	        throw new IllegalArgumentException(number + " is not in range (0,4000]");
	    }
	 
	    List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();
	 
	    int i = 0;
	    StringBuilder sb = new StringBuilder();
	 
	    while ((number > 0) && (i < romanNumerals.size())) {
	        RomanNumeral currentSymbol = romanNumerals.get(i);
	        if (currentSymbol.getValue() <= number) {
	            sb.append(currentSymbol.name());
	            number -= currentSymbol.getValue();
	        } else {
	            i++;
	        }
	    }
	 
	    return sb.toString();
	}
}

enum RomanNumeral {
	I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100), CD(400), D(500), CM(900), M(1000);

	private int value;

	RomanNumeral(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static List<RomanNumeral> getReverseSortedValues() {
		return Arrays.stream(values()).sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
				.collect(Collectors.toList());
	}
}