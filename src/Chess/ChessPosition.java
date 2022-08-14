package Chess;

import boargame.Position;

public class ChessPosition {
	private char column;
	private int row;
	
	public ChessPosition(char column, int row) {
		
		if(column<'a' || column >'h' || row<1 || row >8) {
			throw new ChessException("Posicao econlhida deve estar entre a1 e h8");
		}
		this.column = column;
		this.row = row;	
		}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	protected Position toPosition(){
		return new Position (8-row,column-'a');
	}
	
	protected static ChessPosition fromPosition(Position posi) {
		return new ChessPosition((char)('a'+posi.getColumn()),8-posi.getRow());
	}
	@Override
	public String toString() {
		return ""+column + row;//p "" força o complilador a entender que column e row devem ser concatenados
	}
}
