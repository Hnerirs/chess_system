package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Chess.ChessException;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch c = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		while (!c.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMach(c,captured);
				System.out.println();
				System.out.println("Source =");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = c.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(c.getPieces(),possibleMoves);
	
				System.out.println();
				System.out.println("Target = ");
				ChessPosition target = UI.readChessPosition(sc);
	
				ChessPiece capturedPiece = c.performChessMove(source, target);
				if(capturedPiece !=null) {
					captured.add(capturedPiece);
				}
			}
			catch(ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMach(c, captured);

	}

}
