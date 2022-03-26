package game;

public class Coordinate {
	private int row;
	private int  column;
	
	public Coordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setRow(int newRow) {
		row = newRow;
	}
	
	public void setColumn(int newColumn) {
		column = newColumn;
	}
}
