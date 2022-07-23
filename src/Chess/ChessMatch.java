package Chess;

import Chess.pieces.King;
import Chess.pieces.Rook;
import boargame.Board;
import boargame.Position;

public class ChessMatch {
	private Board board;
	
	public ChessMatch() {
		board = new Board(8,8);//o tabuleiro de xadrez possui oito linhas e oito colunas
		initialSetup();
	}
	
	public ChessPiece[][] getPieces(){
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for(int  i=0;i<board.getRows();i++) {
			for(int j = 0; j<board.getColumns();j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	void initialSetup() {
		board.placePiece(new Rook(board, Color.WHITE),new Position(2,4));
		board.placePiece(new King(board,Color.BLACK), new Position(6,2));
	}
}
