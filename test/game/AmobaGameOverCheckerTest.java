package game;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
/*Tesztelt metódusok:
 * - public void checkIfGameIsOver(Coordinate lastStep)
 *
 */
public class AmobaGameOverCheckerTest {
	AmobaStage stage;
	
	
	
	/*Starting table:
	 * (P = player's marker and C = cumputer's marker) (border markers not visualized here)
	 *   0 1 2 3 4 5
	 * 0 C C C C . .
	 * 1 . . . . . .
	 * 2 . . . . . .
	 * 3 . . P P P P
	 * 4 . . . . . P
	 * 
	 * And the computer steps next.
	 */
	@Before
	public void setUp() {
		stage = new AmobaStage(5, 6, "Circle", null);
		stage.setNotifyingMainFrameEnabled(false);
		stage.makeStep(new Coordinate(3,2));
		stage.makeStep(new Coordinate(0,0));
		stage.makeStep(new Coordinate(3,3));
		stage.makeStep(new Coordinate(0,1));
		stage.makeStep(new Coordinate(3,4));
		stage.makeStep(new Coordinate(0,2));
		stage.makeStep(new Coordinate(3,5));
		stage.makeStep(new Coordinate(0,3));
		stage.makeStep(new Coordinate(4, 5));
		
	}

	@Test
	public void testCheckIfGameIsOverComputerWins() {
		stage.makeStep(new Coordinate(0, 4));
		Assert.assertEquals(true , stage.getGameIsOver());
		Assert.assertEquals(Marker.CROSS , stage.getWinningPlayer());
	}
	
	@Test
	public void testCheckIfGameIsOverPlayerWins() {
		//Computersteps
		stage.makeStep(new Coordinate(1, 0));
		
		//player makes winning step
		stage.makeStep(new Coordinate(3, 1));
		Assert.assertEquals(true , stage.getGameIsOver());
		Assert.assertEquals(Marker.CIRCLE , stage.getWinningPlayer());
		
	}
	


}
