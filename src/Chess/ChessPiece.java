package Chess;

import boargame.Board;
import boargame.Piece;
import boargame.Position;

public abstract class ChessPiece extends Piece{
	private Color color;

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
	
	protected  boolean isThereOpponentPiece(Position p) {
		ChessPiece pieces = (ChessPiece) getBoard().piece(p);
		return p!= null && pieces.getColor()!= color;
	}
}
