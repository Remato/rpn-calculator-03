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

/**
 * @author Henrique Rebelo
 */
public class Regex {
	// regex for literals recognition
	private static final String NUM_REGEX = "(\\d)+"; // short for [0-9]
	private static final String ID_REGEX = "(a|b|c)"; // short for [0-9] (added)
	// regex for single-character operation recognition.
	private static final String OP_REGEX = "(\\+|-|\\*|/)"; // recognizes as an operation
	private static final String PLUS_REGEX = "(\\+)"; // for plus operation recognition
	private static final String MINUS_REGEX = "(\\-)"; // for minus operation recognition
	private static final String SLASH_REGEX = "(/)"; // for div operation recognition
	private static final String STAR_REGEX = "(\\*)"; // for mult operation recognition
	
	public static boolean isNum(String token) {
		return token.matches(NUM_REGEX);
	}
	
	public static boolean isId(String token) { //added
		return token.matches(ID_REGEX);
	}
	
	public static boolean isOP(String token) {
		return token.matches(OP_REGEX);
	}
	
	public static boolean isPlus(String token) {
		return token.matches(PLUS_REGEX);
	}
	
	public static boolean isMinus(String token) {
		return token.matches(MINUS_REGEX);
	}
	
	public static boolean isSlash(String token) {
		return token.matches(SLASH_REGEX);
	}
	
	public static boolean isStar(String token) {
		return token.matches(STAR_REGEX);
	}
	
	/**
	 * returns the proper token type for an operation token
	 * 
	 * @param token
	 * @return the operation token type
	 */
	public static TokenType getOPTokenType(String token) {
		TokenType tokenType = null;
		if(isPlus(token)) {
			tokenType = TokenType.PLUS;
		}
		else if(isMinus(token)) {
			tokenType =  TokenType.MINUS;
		}
		else if(isSlash(token)) {
			tokenType =  TokenType.SLASH;
		}
		else if(isStar(token)) {
			tokenType = TokenType.STAR;
		}
		
		return tokenType;
	}
}
