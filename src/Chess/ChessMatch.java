package Chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Chess.pieces.King;
import Chess.pieces.Rook;
import boargame.Board;
import boargame.Piece;
import boargame.Position;

public class ChessMatch {
	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check;
	private boolean checkMate;
	private List<Piece> piecesOnBoard = new ArrayList<>();
	private List<Piece> caturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8);// o tabuleiro de xadrez possui oito linhas e oito colunas
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return this.currentPlayer;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return this.checkMate;
	}

	private boolean testCheckMate(Color cor) {
		if (!testCheck(cor)) {
			return false;
		}
		List<Piece> pieces = piecesOnBoard.stream().filter(x -> ((ChessPiece) x).getColor() == cor)
				.collect(Collectors.toList());

		for (Piece p : pieces) {
			boolean[][] mat = p.possibleMoves();
			for(int i =0 ; i<board.getRows();i++) {
				for(int j=0 ; j<board.getColumns();j++) {
					if(mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i,j);
						Piece capturedPiece = makeMove(source,target);
						boolean testCheck  = testCheck(cor);
						undoMove(source,target,capturedPiece);
						if(!testCheck) {
							return false;
						}
					}
				}
			}
			
		}
		return true;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validationSourcePosition(source);
		validationTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}

		this.check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if(testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {
			nextTurn();
		}
		
		
		return (ChessPiece) capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decresMoveCount();
		board.placePiece(p, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			caturedPieces.remove(capturedPiece);
			piecesOnBoard.add(capturedPiece);
		}
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position p = sourcePosition.toPosition();
		validationSourcePosition(p);
		return board.piece(p).possibleMoves();
	}

	private void validationSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is a piece here /há uma peça aqui");
		}
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException(
					"There is no possible moves for the piece/não há movimentos possíveis para a peças");
		}

	}

	private void validationTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException(
					"The chosen piece can't move to target position/A peça escolhida não pode ser movida para posição de destino ");
		}
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);

		if (capturedPiece != null) {
			this.piecesOnBoard.remove(capturedPiece);
			this.caturedPieces.add(capturedPiece);
		}

		return capturedPiece;
	}

	private Color opponent(Color cor) {
		return (cor == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece King(Color cor) {
		List<Piece> list = piecesOnBoard.stream().filter(x -> ((ChessPiece) x).getColor() == cor)
				.collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("Ther is no " + cor + " king on the board");
	}

	private boolean testCheck(Color cor) {
		Position kingPosition = King(cor).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(cor))
				.collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
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
