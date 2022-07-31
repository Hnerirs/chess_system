package aplication;

import java.util.Scanner;

import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch c = new ChessMatch();
		while (true) {
			UI.printBoard(c.getPieces());
			System.out.println();
			System.out.println("Source =");
			ChessPosition source = UI.readChessPosition(sc);

			System.out.println();
			System.out.println("Target = ");
			ChessPosition target = UI.readChessPosition(sc);

			ChessPiece capturedPiece = c.performChessMove(source, target);
			
		}

	}

}
