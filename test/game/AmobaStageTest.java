package game;

//import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
/*Tesztelt metódusok:
 * - Konstruktor
 * - public int getRowCount();
 * - public int getColumnCount();
 * - public boolean makeStep(Coordinate step);
 * - public void updateBorders(Coordinate lastStep);
 * - public Object clone();
 */
public class AmobaStageTest {

	@Test
	public void testConstructor() {
		//fail("Not yet implemented");
		AmobaStage a = new AmobaStage(8, 9, "Circle", null);
		Assert.assertEquals(8, a.getRowCount(), 0.0);
		Assert.assertEquals(9, a.getColumnCount(), 0.0);
		Assert.assertEquals(Marker.CIRCLE, a.getPlayerMarker());
		Assert.assertEquals(0, a.getNumberOfPreviousSteps(), 0.0);
		Assert.assertEquals(false, a.getGameIsOver());
		Assert.assertEquals(Marker.EMPTY, a.getWinningPlayer());
	}
	
	@Test
	public void testGetRowCount() {
		//fail("Not yet implemented");
		AmobaStage a = new AmobaStage(7, 8, "Circle", null);
		Assert.assertEquals(7, a.getRowCount(), 0.0);
	}
	
	@Test
	public void testGetColumnCount() {
		//fail("Not yet implemented");
		AmobaStage a = new AmobaStage(7, 8, "Circle", null);
		Assert.assertEquals(8, a.getColumnCount(), 0.0);
	}

	
	@Test
	public void testMakeStepValidStep() {
		//fail("Not yet implemented");
		AmobaStage a = new AmobaStage(5, 5, "Circle", null);
		boolean success1 = a.makeStep(new Coordinate(2, 3));
		Assert.assertEquals(true, success1);
		
		Marker placedMarker1 = (Marker) a.getTable().getValueAt(2, 3);
		Assert.assertEquals(Marker.CIRCLE, placedMarker1);
		
		
		boolean success2 = a.makeStep(new Coordinate(4, 4));
		Assert.assertEquals(true, success2);
		
		Marker placedMarker2 = (Marker) a.getTable().getValueAt(4, 4);
		Assert.assertEquals(Marker.CROSS, placedMarker2);
	}

	
	@Test
	public void testMakeStepInvalidStepOutOfBound() {
		//fail("Not yet implemented");
		AmobaStage a = new AmobaStage(5, 5, "Circle", null);
		boolean success = a.makeStep(new Coordinate(8, 3));
		Assert.assertEquals(false, success);
		
		for(int i =0; i< 5; i++) {
			for(int j =0; j <5; j++) {
				Marker m = (Marker) a.getTable().getValueAt(i, j);
				Assert.assertEquals(Marker.EMPTY, m);
			}
		}	
	}
	
	@Test
	public void testMakeStepInvalidStepMarkerAlreadyPlaced() {
		//fail("Not yet implemented");
		AmobaStage a = new AmobaStage(5, 5, "Circle", null);
		a.makeStep(new Coordinate(3, 3));
		boolean success = a.makeStep(new Coordinate(3, 3));
		Assert.assertEquals(false, success);
		
		Marker firstPlacedMarker = (Marker) a.getTable().getValueAt(3, 3);
		Assert.assertEquals(Marker.CIRCLE, firstPlacedMarker);
		
		Assert.assertEquals(1, a.getNumberOfPreviousSteps(), 0.0);
	}

	
	
	/*Expected table:
	 * (P = player's marker and B = border)
	 *   0 1 2 3 4 5
	 * 0 . . . . . .
	 * 1 . B B B . .
	 * 2 . B P B . .
	 * 3 . B B B . .
	 * 4 . . . . . .
	 */
	@Test
	public void testUpdateBorders() {
		AmobaStage a = new AmobaStage(5, 6, "Circle", null);
		a.makeStep(new Coordinate(2, 2));
		
		int upperLeftRowCoord = 1;
		int upperLeftColumnCoord = 1;
		for(int i =0; i < 3; i++) {
			for(int j = 0; j<3; j++) {
				int actualRowCoord = upperLeftRowCoord + i;
				int actualColumnCoord = upperLeftColumnCoord + j;
				if( (actualRowCoord >= 0) && (actualRowCoord < 5) && (actualColumnCoord >= 0) && (actualColumnCoord < 6 ) ) {
					Marker actual = (Marker) a.getTable().getValueAt(actualRowCoord, actualColumnCoord);
					if(actualRowCoord==2 && actualColumnCoord==2) {
						Assert.assertEquals(Marker.CIRCLE, actual);
					}
					else {
						Assert.assertEquals(Marker.BORDER, actual);
					}
				}
			}
		}
	}
	
	@Test
	public void testClone() {

		AmobaStage original = new AmobaStage(5, 10, "Circle", null);
		original.makeStep(new Coordinate(2,2));
		AmobaStage copy = (AmobaStage) original.clone();
		Assert.assertEquals(5, copy.getRowCount(), 0.0);
		Assert.assertEquals(10, copy.getColumnCount(), 0.0);
		Assert.assertEquals(1, copy.getNumberOfPreviousSteps(), 0.0);
		Assert.assertEquals(false, copy.getGameIsOver());
		Assert.assertEquals(Marker.CIRCLE, copy.getPlayerMarker());
		
		original.makeStep(new Coordinate(4, 7));
		Marker copysMarker = (Marker) copy.getTable().getValueAt(4, 7);
		Assert.assertEquals(Marker.EMPTY, copysMarker);
		
		copy.makeStep(new Coordinate(4, 9));
		Marker originalsMarker = (Marker) original.getTable().getValueAt(4, 9);
		Assert.assertEquals(Marker.EMPTY, originalsMarker);
	}



}
