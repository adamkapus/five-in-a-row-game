package game;

import javax.swing.table.AbstractTableModel;


import java.util.*;;

//import javax.swing.*;

public class AmobaTable extends AbstractTableModel implements Cloneable {
	private ArrayList<ArrayList<Marker>> table;
	private int rowCount;
	private int columnCount;
	
	public AmobaTable(int rowCount, int columnCount) {
		table = new ArrayList<ArrayList<Marker> >();
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.init();
	}


	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return table.get(rowIndex).get(columnIndex);
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}


	@Override
	public int getRowCount() {
		return rowCount;
	}
	
	public Class<?> getColumnClass(int columnIndex){
		return Marker.class;
    }

	
	@Override
	public void setValueAt(Object aValue, int row, int column) {
    	ArrayList<Marker> actualRow = table.get(row);
    	actualRow.set(column, (Marker) aValue);
    	this.fireTableDataChanged();
    }
	
	
	
	private void init() {
		table = new ArrayList<ArrayList<Marker> >();
		for(int i = 0; i < rowCount; i++) {
			ArrayList<Marker> a = new ArrayList<Marker>();
			for(int j = 0; j < columnCount; j++) {
				a.add(Marker.EMPTY);
			}
			table.add(a);
		}
	}
	
	public void clear() {
		for(int i = 0; i < rowCount; i++) {
			for(int j = 0; j < columnCount; j++) {
				setValueAt(Marker.EMPTY, i, j);
			}
		}
	}
	
	public Object clone() {
		AmobaTable t2 = new AmobaTable(rowCount, columnCount);
		t2.table = new ArrayList<ArrayList<Marker> >();
		for(int i = 0; i < rowCount; i++) {
			ArrayList<Marker> a = new ArrayList<Marker>();
			for(int j = 0; j < columnCount; j++) {
				Marker actual = (Marker) getValueAt(i, j);
				a.add(actual);
			}
			t2.table.add(a);
		}
		return t2;
	}
	

}



