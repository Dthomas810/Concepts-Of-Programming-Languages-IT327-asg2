
import java.util.*;

public class Calculator {

	public Calculator() {
	}

	public static double evaluate(String s) {
		if (isbalanced(s) == true) {
			String x = infixToPostfix(s);
			// System.out.println(x);
			return evaluatePostFix(x);

		} else {
			System.out.print("Not a valid expression ");
			return 0.0;
		}

	}

	public static double evaluatePostFix(String s) {
		String delims = "+-*/()^ ";

		StringTokenizer x = new StringTokenizer(s, delims, true);
		AStack<String> neo = new AStack<String>();

		while (x.hasMoreTokens()) {
			String temp = x.nextToken();// read a token

			if (delims.indexOf(temp) < 0) {
				neo.push(temp);
			} // if the token is a value, push it onto the stack

			else if (temp.equals(" ")) {
				continue;
			} // ignore spaces ?
			else { // the token is an operator
				if (neo.size() < 2) { // the user has not input sufficient values in the expression
					System.out.println("Not a valid expression!");
					return 0.0;
				}
				String num1 = neo.pop(); // else pop the top n values from the stack
				String num2 = neo.pop();
				neo.push(math(num1, temp, num2));// evaluate the operater with values as arguments and push the results
													// back to stack
			}

		}

		if (neo.size() > 1) {
			System.out.println("Not a valid expression!");
			return 0.0;
		} else {
			String ans = neo.pop();
			return Double.parseDouble(ans);

		}
	}

	public static String infixToPostfix(String s) {
		String delims = "+-*/()^ ";

		StringTokenizer x = new StringTokenizer(s, delims, true);
		AStack<String> yard = new AStack<String>(); // for operators
		String output = "";

		while (x.hasMoreTokens()) {
			String temp = x.nextToken();// read a token

			if (delims.indexOf(temp) < 0) {
				output += temp + " ";
			} // if token is a number then add to output

			if (isOperator(temp)) { // if an operator
				while (isOperator(yard.peek())) {
					// System.out.println("top of yard while() reached");
					if (leftass(temp) && getOrder(temp) >= getOrder(yard.peek())) {
						// System.out.println("first reached");
						output += yard.pop() + " ";
					} else if (!(leftass(temp)) && getOrder(temp) > getOrder(yard.peek())) {
						// System.out.println("second reached");
						output += yard.pop() + " ";
					} else
						break;

				}
				yard.push(temp);
				// System.out.println("reached");
			}

			if (delims.indexOf(temp) == 4) {// left parenthesis
				yard.push(temp);
			}

			if (delims.indexOf(temp) == 5) {// right parenthesis
				while (!(yard.peek().equals("("))) { // until the token at the top of the stack is a left parenthesis
					output += yard.pop() + " "; // pop operators off the stack onto the output queue
				}
				yard.pop(); // pop the left parenthesis
			}

		}

		// no more tokens to read
		while (yard.size() > 0) {
			output += yard.pop() + " ";
		}
		return output;
	}

	public static boolean leftass(String s) {
		if (s.equals("^")) {
			return false;
		}
		return true;
	}

	public static void compareToPrecedence(AStack opstack, String qq, AStack numstack) { // qq is temp, operator being
																							// read in
		if (opstack.empty()) {
			opstack.push(qq);
			return;
		}

		String in = (String) opstack.pop();
		int in_precedence = getOrder(in);
		int out_precedence = 0;
		if (qq.equals("(")) {
			out_precedence = 0;
		} else
			out_precedence = getOrder(qq);

		if (in_precedence <= out_precedence && isOperator(qq) && isOperator(in)) {

			if (!(qq.equals("^")) && isOperator(in)) {
				String num1 = (String) numstack.pop();
				String num2 = (String) numstack.pop();
				numstack.push(math(num1, in, num2));
				compareToPrecedence(opstack, qq, numstack);
			}

			else {
				opstack.push(in);
				opstack.push(qq);
				return;
			}

		}

		else if (in_precedence > out_precedence && isOperator(qq) && isOperator(in)) {
			opstack.push(in);
			opstack.push(qq);
			return;
		}

		else if (qq.equals("(") && isOperator(in)) {
			opstack.push(in);
			opstack.push(qq);
		}

		else if (in.equals("(") && isOperator(qq)) {
			opstack.push(in);
			opstack.push(qq);
			return;
		}

		else if (qq.equals(")") && isOperator(in)) {
			String num1 = (String) numstack.pop();
			String num2 = (String) numstack.pop();
			numstack.push(math(num1, in, num2));
			compareToPrecedence(opstack, qq, numstack);
		}

		else if (qq.equals(")") && in.equals("(")) {
			return;
		}

		else
			return;

	}

	public static void solvewhatsleft(AStack ops, AStack nums) {
		while (ops.size() >= 1) {
			if (ops.peek().equals(")") || ops.peek().equals("(")) {
				// System.err.print("Not a valid expression! :");
				System.out.print("Not a valid expression! : ");
				return;
			}
			String op = (String) ops.pop();
			String num1 = (String) nums.pop();
			String num2 = (String) nums.pop();
			nums.push(math(num1, op, num2));
		}
	}

	public static boolean isvalid(AStack ops, AStack nums) {
		while (ops.size() >= 1) {
			if (ops.peek().equals(")") || ops.peek().equals("(")) {
				return false;
			}

			else
				return true;

		}
		return true;
	}

	public static String math(String number1, String operator, String number2) {
		double num1 = Double.parseDouble(number1);
		double num2 = Double.parseDouble(number2);

		if (operator.equals("+")) {
			return "" + (num2 + num1);
		} else if (operator.equals("-")) {
			return "" + (num2 - num1);
		} else if (operator.equals("*")) {
			return "" + (num2 * num1);
		} else if (operator.equals("/")) {
			String thing = "";

			try {
				thing = "" + num2 / num1;
				return thing;
			} catch (ArithmeticException e) {
				System.out.println("Error: Divided by zero");
			}
			return thing;
		}

		else if (operator.equals("^")) {
			return "" + (Math.pow(num2, num1));
		} else {
			System.out.println("shit");
			return null;
		}
	}

	public static int getOrder(String thing) {
		if (thing.equals(")")) {
			return 1;
		} else if (thing.equals("^")) {
			return 2;
		} else if (thing.equals("/") || thing.equals("*")) {
			return 3;
		} else if (thing.equals("+") || thing.equals("-")) {
			return 4;
		} else if (thing.equals("(")) {
			return 5;
		} else
			return -1;
	}

	public static boolean isOperator(String x) {
		if (x == null) {
			return false;
		}
		if (x.equals("+") || x.equals("-") || x.equals("*") || x.equals("/") || x.equals("^")) {
			return true;
		} else
			return false;
	}

	private static boolean isbalanced(String temp) {
		Stack<String> data = new Stack<String>();// holds brackets etc.
		String lookingfor = "{}()[]<>";
		for (int i = 0; i <= temp.length() - 1; i++) {
			String x = temp.substring(i, i + 1);
			for (int k = 0; k <= lookingfor.length() - 1; k++) {
				String yup = lookingfor.substring(k, k + 1);
				if (x.equals(yup)) {
					data.push(x);
				}
			}
		}

		int a = 0; // {
		int b = 0; // }

		int d = 0; // (
		int e = 0; // )

		int f = 0; // [
		int g = 0; // ]

		int h = 0; // <
		int i = 0; // >

		while (!(data.empty())) {
			String qwerty = data.pop();
			if (qwerty.equals("{")) {
				a++;
			} else if (qwerty.equals("}")) {
				b++;
			} else if (qwerty.equals("(")) {
				d++;
			} else if (qwerty.equals(")")) {
				e++;
			} else if (qwerty.equals("[")) {
				f++;
			} else if (qwerty.equals("]")) {
				g++;
			} else if (qwerty.equals("<")) {
				h++;
			} else
				i++;
		}

		if (a == b && d == e && f == g && h == i) {
			return true;
		} else
			return false;
	}

}
