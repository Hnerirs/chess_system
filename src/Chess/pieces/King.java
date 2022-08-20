package Chess.pieces;

import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.Color;
import boargame.Board;
import boargame.Position;

public class King extends ChessPiece {
	
	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch c) {
		super(board, color);
		this.chessMatch = c;
	}

	public boolean canMove(Position posi) {
		ChessPiece p = (ChessPiece) getBoard().piece(posi);
		return p == null || p.getColor() != getColor();
	}

	private boolean testRookCasting(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p instanceof Rook && p.getMoveCount() == 0 && p.getColor() == getColor();

	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// above
		p.setValues(this.position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// below
		p.setValues(this.position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// left
		p.setValues(this.position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// right
		p.setValues(this.position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// nw noroeste
		p.setValues(this.position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// ne
		p.setValues(this.position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// sw sudeste
		p.setValues(this.position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// se
		p.setValues(this.position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// espceial move castling/ Roque
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			// Roque pequeno
			Position posiT1 = new Position(position.getRow(), position.getColumn() + 3);
			if (testRookCasting(posiT1)) {
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			Position posiT2 = new Position(position.getRow(), position.getColumn() - 4);
			if (testRookCasting(posiT1)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}

		return mat;
	}
}
