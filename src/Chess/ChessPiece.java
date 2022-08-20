package Chess;

import boargame.Board;
import boargame.Piece;
import boargame.Position;

public abstract class ChessPiece extends Piece{
	private Color color;
	private int moveCount;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void increaseMoveCount() {
		moveCount++;
	}
	
	public void decresMoveCount() {
		moveCount--;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	protected  boolean isThereOpponentPiece(Position p) {
		ChessPiece pieces = (ChessPiece) getBoard().piece(p);
		return p!= null && pieces.getColor()!= color;
	}
}
