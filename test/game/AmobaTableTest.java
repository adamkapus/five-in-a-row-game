package game;

//import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

//import junit.framework.Assert;


/*Tesztelt metódusok :
 * - Konstruktor
 * - public int getColumnCount();
 * - public int getRowCount();
 * - public Class<?> getColumnClass(int columnIndex);
 * - public void setValueAt(Object aValue, int row, int column);
 * - public Object getValueAt(int rowIndex, int columnIndex);
 * - public void clear();
 * - private void init();
 * - public Object clone();
 */
public class AmobaTableTest {

	@Test
	public void testConstructor() {
		AmobaTable t = new AmobaTable(5, 7);
		int rowCount = t.getRowCount();
		int columnCount = t.getColumnCount();
		Assert.assertEquals(5, rowCount, 0.0);
		Assert.assertEquals(7, columnCount, 0.0);
		for(int i = 0; i<rowCount; i++) {
			for(int j =0; j <columnCount; j++) {
				Marker m = (Marker) t.getValueAt(i, j);
				Assert.assertEquals(Marker.EMPTY, m);
			}
		}
	}
	
	@Test
	public void testGetRowCount() {
		AmobaTable t = new AmobaTable(10, 15);
		int rowCount = t.getRowCount();
		Assert.assertEquals(10, rowCount, 0.0);
	}
	
	@Test
	public void testGetColumnCount() {
		AmobaTable t = new AmobaTable(17, 20);
		int columnCount = t.getColumnCount();
		Assert.assertEquals(20, columnCount, 0.0);
	}
	
	
	@Test
	public void testGetColumnClass() {
		AmobaTable t = new AmobaTable(7, 7);
		Class<?> c = t.getColumnClass(1);
		Assert.assertEquals(Marker.class, c);
	}
	
	
	@Test
	public void testGetValueAtValidArguments() {
		//fail("Not yet implemented");
		AmobaTable t = new AmobaTable(10, 15);
		Marker m = (Marker) t.getValueAt(2, 3);
		Assert.assertEquals(Marker.EMPTY, m);
	}
	
	@Test (expected=IndexOutOfBoundsException.class)
	public void testGetValueAtOutOfBoundRow() throws IndexOutOfBoundsException{
		AmobaTable t = new AmobaTable(10, 15);
		Marker m = (Marker) t.getValueAt(-3, 10);
		//Assert.assertEquals(10, test, 0.0);
	}
	
	@Test (expected=IndexOutOfBoundsException.class)
	public void testGetValueAtOutOfBoundColumn() throws IndexOutOfBoundsException{
		AmobaTable t = new AmobaTable(10, 15);
		Marker m = (Marker) t.getValueAt(5, 17);
		//Assert.assertEquals(10, test, 0.0);
	}
	
	
	@Test
	public void testSetValueValidArguments() {
		//fail("Not yet implemented");
		AmobaTable t = new AmobaTable(10, 15);
		t.setValueAt(Marker.CROSS, 3, 7);
		Marker m = (Marker) t.getValueAt(3, 7);
		Assert.assertEquals(Marker.CROSS, m);
	}
	
	@Test (expected=IndexOutOfBoundsException.class)
	public void testSetValueOutOfBoundRow() throws IndexOutOfBoundsException {
		AmobaTable t = new AmobaTable(10, 15);
		t.setValueAt(Marker.CIRCLE, 17, 8);
	}
	
	@Test (expected=IndexOutOfBoundsException.class)
	public void testSetValueOutOfBoundColumn() throws IndexOutOfBoundsException{
		AmobaTable t = new AmobaTable(10, 15);
		t.setValueAt(Marker.CROSS, 3, -4);
	}
	
	
	@Test
	public void testClear() {
		AmobaTable t = new AmobaTable(5, 6);
		t.setValueAt(Marker.CIRCLE, 3, 4);
		t.clear();
		Marker m = (Marker) t.getValueAt(3, 4);
		Assert.assertEquals(Marker.EMPTY, m);
	}
	
	@Test
	public void testClone() {
		AmobaTable original = new AmobaTable(5,7);
		AmobaTable copy = (AmobaTable) original.clone();
		
		Assert.assertEquals(5, copy.getRowCount(), 0.0);
		Assert.assertEquals(7, copy.getColumnCount(), 0.0);
		
		
		copy.setValueAt(Marker.CROSS, 4, 4);
		Marker originalsMarker =  (Marker) copy.getValueAt(2, 3);
		Assert.assertEquals(Marker.EMPTY, originalsMarker);
		
		original.setValueAt(Marker.CROSS, 2, 3);
		Marker copysMarker =  (Marker) copy.getValueAt(2, 3);
		Assert.assertEquals(Marker.EMPTY, copysMarker);
		
		
	}

}
