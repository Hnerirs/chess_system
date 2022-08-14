package Chess;

import java.util.ArrayList;
import java.util.List;

import Chess.pieces.King;
import Chess.pieces.Rook;
import boargame.Board;
import boargame.Piece;
import boargame.Position;

public class ChessMatch {
	private Board board;
	private  int turn;
	private Color currentPlayer;
	private List<Piece> piecesOnBoard = new ArrayList<>();
	private List<Piece> caturedPieces = new ArrayList<>();
	
	
	public ChessMatch() {
		board = new Board(8,8);//o tabuleiro de xadrez possui oito linhas e oito colunas
		turn =1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	public Color getCurrentPlayer() {
		return this.currentPlayer;
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
	
	public ChessPiece performChessMove(ChessPosition sourcePosition,ChessPosition targetPosition ) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validationSourcePosition(source);
		validationTargetPosition(source, target);
		Piece capturedPiece = makeMove(source,target);
		nextTurn();
		return (ChessPiece) capturedPiece;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position p = sourcePosition.toPosition();
		validationSourcePosition(p);
		return board.piece(p).possibleMoves();
	}
	
	private void validationSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {
			throw new ChessException("There is a piece here /h� uma pe�a aqui");
		}
		if(currentPlayer !=((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the piece/n�o h� movimentos poss�veis para a pe�as");
		}
		
	}
	
	private void validationTargetPosition(Position source,Position target) {
		if(!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position/A pe�a escolhida n�o pode ser movida para posi��o de destino ");
		}
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE)? Color.BLACK : Color.WHITE;
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if(capturedPiece!=null) {
			this.piecesOnBoard.remove(capturedPiece);
			this.caturedPieces.add(capturedPiece);
		}
		
		return capturedPiece;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column,row).toPosition());
		this.piecesOnBoard.add(piece);
	}
	
	void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
