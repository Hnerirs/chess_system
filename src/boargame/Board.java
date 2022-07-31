package boargame;

public class Board {
	private int rows;
	private int columns;
	private Piece[][] pieces;
	public Board(int rows, int columns) {
		
		if(columns<1 || rows<1) {
			throw new BoardException("Numero de linhas ou colunas inválido: porfavor digite um numero entre 1 e 8");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public Piece piece(int row, int columns) {
		if(!positionExists(row, columns)) {
			throw new BoardException("Posição não encontrada no tabuleiro");
		}
		return pieces[row][columns];
	}
	public Piece piece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Posição não encontrada no tabuleiro");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	public void placePiece(Piece piece, Position position) {
		
		if(thereIsAPiece(position)) {
			throw new BoardException("Ha uma peça nessa posição");
		}
		
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	private boolean positionExists(int row, int column) {
		return row>=0 && row<this.rows && column >= 0 && column < this.columns;
	}
	
	public boolean positionExists(Position posi) {
		return positionExists(posi.getRow(),posi.getColumn());
		
	}
	
	public boolean thereIsAPiece(Position posi) {
		if(!positionExists(posi)) {
			throw new BoardException("Posição não existe no tabuleiro");
		}
		return piece(posi) != null;
	}
	
	public Piece removePiece(Position posi) {
		if(!positionExists(posi)) {
			throw new BoardException("Position not on the board");
		}
		if(piece(posi) == null) {
			return null;
		}
		Piece aux = piece(posi);
		aux.position = null;
		pieces[posi.getRow()][posi.getColumn()] = null;
		return aux;
		
	}
}
