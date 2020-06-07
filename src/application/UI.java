package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {

	// text
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	// background
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.nextLine();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1)); // recorta a string a partir da posicao 1 e converte para int
			return new ChessPosition(column, row);
		}
		catch(RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition. Valid position: a1-h8");
		}
	}
	
	public static void printMatch(ChessMatch match, List<ChessPiece> cp) {
		printBoard(match.getPieces());
		System.out.println();
		printCapturedPieces(cp);
		System.out.println();
		System.out.println("Turn: " + match.getTurn());
		System.out.println("Waiting for " + match.getCurrentPlayer());
		if(match.getCheck())
			System.out.println("\nCHECK!");
	}

	public static void printBoard(ChessPiece[][] pieces) {
		for(int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		for(int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	private static void printPiece(ChessPiece piece, boolean bckgrnd) {
		if(bckgrnd)
			System.out.print(ANSI_BLUE_BACKGROUND);
    	if(piece == null)
            System.out.print("-" + ANSI_RESET);
        else {
        	if (piece.getColor() == Color.WHITE)
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            else
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
        }
        System.out.print(" ");
	}
	
	public static void printCapturedPieces(List<ChessPiece> cp) {
		List<ChessPiece> white = cp.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList()); // pega todas as brancas e coloca na lista
		List<ChessPiece> black = cp.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList()); // pega todas as pretas e coloca na lista
		System.out.println("Captured pieces:");
		System.out.print("White: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray())); // transforma um array em string
		System.out.print(ANSI_RESET);
		System.out.print("Black: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(black.toArray())); // transforma um array em string
		System.out.print(ANSI_RESET);
	}
}
