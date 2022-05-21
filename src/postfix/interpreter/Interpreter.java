/* *******************************************************************
 * Copyright (c) 2021 Universidade Federal de Pernambuco (UFPE).
 * 
 * This file is part of the Compilers course at UFPE.
 * 
 * During the 1970s and 1980s, Hewlett-Packard used RPN in all 
 * of their desktop and hand-held calculators, and continued to 
 * use it in some models into the 2020s. In computer science, 
 * reverse Polish notation is used in stack-oriented programming languages 
 * such as Forth, STOIC, PostScript, RPL and Joy.
 *  
 * Contributors: 
 *     Henrique Rebelo      initial design and implementation 
 *     http://www.cin.ufpe.br/~hemr/
 * ******************************************************************/

package postfix.interpreter;

import java.util.HashMap;

import postfix.ast.Expr;

/**
 * @author Henrique Rebelo
 */
public class Interpreter implements Expr.Visitor<Integer> {

	public HashMap<String, String> env = new HashMap<String, String>(); //added
	
	public Interpreter(HashMap<String, String> env){this.env = env;}   //added
	
	public int interp(Expr expression) { 
		int value = evaluate(expression);
	
		
		return value;
	}

	@Override
	public Integer visitNumberExpr(Expr.Number expr) {
		return Integer.parseInt(expr.value);
	}
	
	@Override
	public Integer visitIdExpr(Expr.Id expr) { //added
		return Integer.parseInt(env.get(expr.value));
	}
	
	@Override
	public Integer visitBinopExpr(Expr.Binop expr) {
		int left = evaluate(expr.left);
		int right = evaluate(expr.right); 
		int result = 0;

		switch (expr.operator.type) {
		case PLUS:
			result = left + right;
			break;
		case MINUS:
			result = left - right;
			break;
		case SLASH:
			result = left / right;
			break;
		case STAR:
			result = left * right;
			break;
		default:
			break;
		}

		return result;
	}

	private int evaluate(Expr expr) {
		return expr.accept(this);
	}
}
