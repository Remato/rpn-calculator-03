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

package postfix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import postfix.ast.AstPrinter;
import postfix.ast.Expr;
import postfix.interpreter.Interpreter;
import postfix.lexer.LexError;
import postfix.lexer.Scanner;
import postfix.lexer.Token;
import postfix.parser.Parser;
import postfix.parser.ParserError;

/**
 * @author Henrique Rebelo
 */
public class Postfix {
	
	//colocar parametro new HashMap<String, String>()
	private static final Interpreter interpreter = new Interpreter(new HashMap<String, String>()); //added
	private static boolean hasError = false;
	private static boolean debugging = false;

	/**
	 * Running Postfix Interpreter
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
//		args = new String [1];
//		args [0] = "../StackerPrograms/program/Calc1.stk";
//		args [1] = "../StackerPrograms/program/Calc2.stk";

		debugging = false; // for interpretation phases
		run(args, debugging);
	}

	/**
	 * run by Interpretation the given program as 
	 * a source file path
	 * 
	 * @param sourceFilePath to be interpreted
	 */
	private static void runFile(String sourceFilePath) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(sourceFilePath));
		String sourceProgram = 
				new String(bytes, Charset.defaultCharset());
		run(sourceProgram);

		// Indicate an error in the exit code.
		if (hasError) System.exit(65); // exiting with non-zero code as should be
	}

	/**
	 * Prompt where you can enter and execute code one 
	 * line at a time.
	 * 
	 * @throws IOException
	 */
	private static void runPrompt() throws IOException {
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(input);

		for (;;) { 
			System.out.print("> ");
			String line = reader.readLine();
			if (line == null) break;
			run(line);
			hasError = false;
		}
	}

	/**
	 * run by Interpretation the given program [as a String]
	 * 
	 * @param source to be interpreted
	 */
	private static void run(String source) {
		try {
			Scanner scanner = new Scanner(source);
			List<Token> tokens = scanner.scan();

			// debugging for tokens
			if(debugging) {
				printTokens(tokens);
			}

			Parser parser = new Parser(tokens);
			Expr expr = parser.parse();

			// debugging for parsing/ast
			if(debugging) {
				printAST(expr);
			}
			//added
			interpreter.env.put("a", "10");
			interpreter.env.put("b", "-5");
			interpreter.env.put("c", "150");
			
			System.out.println(interpreter.interp(expr));
		} catch (LexError e) {
			error("Lex", e.getMessage());
			hasError = true;
		}	
		catch (ParserError e) {
			error("Parser", e.getMessage());
			hasError = true;
		}	
	}

	// -------------------------------------------------------------
	// HELPERS METHODS
	// -------------------------------------------------------------
	private static void run(String[] args, boolean debugging) throws IOException {
		Postfix.debugging = debugging;
		if(args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				runFile(args[i]);
			}
		}
		else {
			runPrompt();
		}
	}

	private static void printAST(Expr expr) {
		System.out.println("ast: "+new AstPrinter().print(expr));
		System.out.println();
	}

	private static void printTokens(List<Token> tokens) {
		for (Token token : tokens) {
			System.out.println(token);
		}
		System.out.println();
	}

	private static void error(String kind, String message) {
		report(kind, message);
	}

	private static void report(String kind, String message) {
		System.err.println(
				"[" + kind + "] Error: " + message);
		hasError = true;
	}
}
