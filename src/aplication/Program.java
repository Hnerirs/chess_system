package aplication;

import Chess.ChessMatch;

public class Program {

	public static void main(String[] args) {
		
		System.out.println("Iniciando repositório");
		ChessMatch c = new ChessMatch();
		UI.printBoard(c.getPieces());

	}

}
