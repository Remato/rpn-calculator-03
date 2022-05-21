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

package postfix.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Henrique Rebelo
 */
public class Scanner {

	private final String source;
	private final List<Token> tokens = new ArrayList<>();

	public Scanner(String source) {
		this.source = source;
	}

	/**
	 * Scan the source program [as a String]
	 * store the tokens as a List<Token> 
	 * 
	 * @param program
	 * @return the list of tokens
	 */
	public List<Token> scan() {
		return this.scan(this.source);
	}
	
	/**
	 * Scan the given program [as a String]
	 * store the tokens as a List<Token> 
	 * 
	 * @param program
	 * @return the list of tokens
	 */
	public List<Token> scan(String program) {
		StringTokenizer tokenizer = new StringTokenizer(program, Token.TOKENIZER_DELIMITER); 
		// processing each tokenized word
		while (tokenizer.hasMoreElements()) {
			String tokenStr = tokenizer.nextToken();
			this.tokens.add(this.getToken(tokenStr));
		}
		this.tokens.add(new Token(TokenType.EOF, "")); // EOF
		
		return this.tokens;
	}

	// -------------------------------------------------------------
	// HELPERS METHODS
	// -------------------------------------------------------------

	private Token getToken(String token) {
		Token ret = null;
		if(Regex.isNum(token)) {
			ret = new Token(TokenType.NUM, token);
		}
		else if(Regex.isId(token)) { //added
			ret = new Token(TokenType.ID, token);
		}
		else if(Regex.isOP(token)) {
			ret = new Token(Regex.getOPTokenType(token), token);
		}
		else {
			throw new LexError("Unexpected character: "+token);
		}
		return ret;
	}
}
