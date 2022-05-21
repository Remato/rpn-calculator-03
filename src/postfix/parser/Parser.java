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

package postfix.parser;

import java.util.List;
import java.util.Stack;

import postfix.ast.Expr;
import postfix.lexer.Token;
import postfix.lexer.TokenType;

/**
 * @author Henrique Rebelo
 */
public class Parser {

	private final List<Token> tokens;
	// The internal stack used for shift-reduce parsing
	private Stack<Expr> stack = new Stack<>();
	private int current = 0;

	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}

	//Parsing Expressions 
	public Expr parse() {
		try {
			return expression();
		} catch (java.util.EmptyStackException error) {
			throw new ParserError("incomplete binop expression");
		}
	}

	// -------------------------------------------------------------
	// HELPERS METHODS
	// -------------------------------------------------------------
	private Expr expression() {
		while (!isAtEnd()) {
			if(this.match(TokenType.NUM)) {
				this.stack.push(this.number());
			}
			else if(this.match(TokenType.ID)){ //added
				this.stack.push(this.id());
			}
			// matching any of the operation tokens
			else if(this.match(TokenType.PLUS, TokenType.MINUS, 
					TokenType.SLASH, TokenType.STAR)) {
				this.stack.push(this.binop());
			}
			this.advance();
		}
		return this.stack.pop();
	}

	private Expr number() {
		return new Expr.Number(peek().lexeme);
	}
	
	private Expr id() { //added
		return new Expr.Id(peek().lexeme);
	}

	private Expr binop() {
		Expr right = this.stack.pop();
		Expr left = this.stack.pop();
		return new Expr.Binop(right, left, this.peek());
	}

	private boolean match(TokenType... types) {
		for (TokenType type : types) {
			if (check(type)) {
				return true;
			}
		}

		return false;
	}

	private boolean check(TokenType type) {
		if (isAtEnd()) return false;
		return peek().type == type;
	}

	private Token advance() {
		if (!isAtEnd()) current++;
		return previous();
	}

	private boolean isAtEnd() {
		return peek().type == TokenType.EOF;
	}

	private Token peek() {
		return tokens.get(current);
	}

	private Token previous() {
		return tokens.get(current - 1);
	}
}
