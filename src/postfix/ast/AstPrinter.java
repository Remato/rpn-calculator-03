package postfix.ast;

import java.util.Stack;

import postfix.ast.Expr.Binop;
import postfix.ast.Expr.Id;
import postfix.ast.Expr.Number;

public class AstPrinter implements Expr.Visitor<String>{

	public String print(Expr expr) {
		return expr.accept(this);
	}

	@Override
	public String visitNumberExpr(Number expr) {
		return expr.value.toString();
	}
	
	@Override
	public String visitIdExpr(Id expr) { //added
		return expr.value.toString();
	}

	@Override
	public String visitBinopExpr(Binop expr) {
		return parenthesizePreOrder(expr.operator.lexeme,
				expr.left, expr.right);
	}

	// -------------------------------------------------------------
	// HELPERS METHODS
	// -------------------------------------------------------------

	private String parenthesizePreOrder(String name, Expr... exprs) {
		StringBuffer buffer = new StringBuffer();

		buffer.append("(").append(name);
		for (Expr expr : exprs) {
			buffer.append(" ");
			buffer.append(expr.accept(this));
		}
		buffer.append(")");

		return buffer.toString();
	}

//	private String parenthesizePostOrder(String name, Expr... exprs) {
//		StringBuffer buffer = new StringBuffer();
//
//		buffer.append("(");
//		for (Expr expr : exprs) {
//			buffer.append(expr.accept(this));
//			buffer.append(" ");
//		}
//		buffer.append(name).append(")");
//
//		return buffer.toString();
//	}

	public boolean isBalancedParantheses(String expr) {
		if (expr.isEmpty()) { 
			return true; 
		}

		Stack<Character> stack = new Stack<Character>();
		for (int i = 0; i < expr.length(); i++) {
			char current = expr.charAt(i);
			if (current == '(') {
				stack.push(current);
			}
			if (current == ')') {
				if (stack.isEmpty()) { 
					return false; 
				}
				char last = stack.peek();
				if (current == ')' && last == '(') {
					stack.pop();
				}
				else { 
					return false; 
				}
			}
		}

		return stack.isEmpty()?true:false;
	}
}
