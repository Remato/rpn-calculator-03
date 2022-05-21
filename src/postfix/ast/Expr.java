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

package postfix.ast;

import postfix.lexer.Token;

/**
 * @author Henrique Rebelo
 */
public abstract class Expr {

	// visitors for expressions
	public interface Visitor<T> {
		T visitNumberExpr(Number expr);
		T visitBinopExpr(Binop expr);
		T visitIdExpr(Id expr); //added
	}

	// Nested Expr classes here

	// Number expression
	public static class Number extends Expr {
		public Number(String value){
			this.value = value;
		}

		@Override
		public <T> T accept(Visitor<T> visitor) {
			return visitor.visitNumberExpr(this);
		}

		public final String value;
	}
	
	// Id expression (added)
		public static class Id extends Expr { 
			public Id(String value){
				this.value = value;
			}

			@Override
			public <T> T accept(Visitor<T> visitor) {
				return visitor.visitIdExpr(this);
			}

			public final String value;
		}
	

	// Binop expression
	public static class Binop extends Expr {
		public Binop(Expr right, Expr left, Token operator) {
			this.left = left;
			this.right = right;
			this.operator = operator;
		}

		@Override
		public <T> T accept(Visitor<T> visitor) {
			return visitor.visitBinopExpr(this);
		}

		public final Expr left;
		public final Expr right;
		public final Token operator;
	}

	public abstract <T> T accept(Visitor<T> visitor);
}
