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
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column,row).toPosition());
	}
	
	void initialSetup() {
		placeNewPiece('b',6, new Rook(board, Color.WHITE));
		placeNewPiece('d',5 ,new King(board,Color.BLACK));
	}
}
