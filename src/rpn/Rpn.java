package rpn;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Stack;
import java.io.File;

public class Rpn {

	public static void main(String[] args) throws Exception {
		Stack<String> stack = new Stack<String>();
		File file = new File("Calc1.stk");
		byte[] content = Files.readAllBytes(file.toPath());
		String rpnExpression[] = new String(content).split("\n");
		String result;
		boolean keepGoing = true;
		ArrayList<Token> tokenList = new ArrayList<Token>();

		public void selectOperation() {
			
		}

		for (int i = 0; i < rpnExpression.length; i++) {

			boolean isADigit = rpnExpression[i].matches("[0-9]*"); //regex numbers 0 -> 9

			if (isADigit){
				tokenList.add(new Token(TokenType.NUM, rpnExpression[i]));
			}else if ("+-*/".contains(rpnExpression[i])){
				//add os tokens aq tira as funções publicas
				switch (rpnExpression[i]) {
					case "+":
						tokenList.add(new Token(TokenType.PLUS, rpnExpression[i]));
						break;
					case "-":
						tokenList.add(new Token(TokenType.MINUS, rpnExpression[i]));
						break;
					case "*":
						tokenList.add(new Token(TokenType.STAR, rpnExpression[i]));
						break;
					case "/":
						tokenList.add(new Token(TokenType.SLASH, rpnExpression[i]));
						break;
				}
			} else {
				keepGoing = false;
				throw new Exception("Error: Unexpected char: " + rpnExpression[i]);
			}
		}

		if (keepGoing) {
			System.out.println(tokenList.toString());
			
			for (int i = 0; i < tokenList.size(); i++) {

				if (tokenList.get(i).type == TokenType.NUM) {
					stack.push(tokenList.get(i).lexeme);
				} else {
					BigDecimal rightOp = new BigDecimal(stack.pop());
					BigDecimal leftOp = new BigDecimal(stack.pop());

					switch (tokenList.get(i).type) {
						case PLUS:
							stack.push(leftOp.add(rightOp).toString());
							break;
						case MINUS:
							stack.push(leftOp.subtract(rightOp).toString());
							break;
						case STAR:
							stack.push(leftOp.multiply(rightOp).toString());
							break;
						case SLASH:
							stack.push(leftOp.divide(rightOp).toString());
							break;
						default:
							break;
					}
				}
			}
			result = stack.pop();
			System.out.println("The result of expression in RPN is: " + result);
		}
	}

}