package game;

public class AmobaGameOverChecker {
	AmobaStage stage;
	
	public AmobaGameOverChecker(AmobaStage stage) {
		this.stage = stage;
	}
	
	public void checkIfGameIsOver(Coordinate lastStep) {
		boolean gameIsOver = false;
		Marker winningPlayer = Marker.EMPTY;
		
		int row = lastStep.getRow();
		int column = lastStep.getColumn();
		int rowCount = stage.getRowCount();
		int columnCount = stage.getColumnCount();
		Marker actual = (Marker) stage.getTable().getValueAt(row, column);
		
		boolean foundFiveHorizontal = findFiveHorizontal(stage.getTable(), row, column, rowCount, columnCount);
		boolean foundFiveVertical = findFiveVertical(stage.getTable(), row, column, rowCount, columnCount);
		boolean foundFiveDiagonalFromBottomRight = findFiveDiagonalFromBottomRight(stage.getTable(), row, column, rowCount, columnCount);
		boolean foundFiveDiagonalFromBottomLeft = findFiveDiagonalFromBottomLeft(stage.getTable(), row, column, rowCount, columnCount);
		if( (foundFiveHorizontal || foundFiveVertical) || (foundFiveDiagonalFromBottomRight || foundFiveDiagonalFromBottomLeft)) {
			gameIsOver = true;
		}
		
		if(gameIsOver) {
			winningPlayer = actual;
			stage.setGameIsOver(true);
			stage.setWinningPlayer(winningPlayer);
		}
		else {
			if((stage.getNumberOfPreviousSteps() == rowCount*columnCount)) {
				winningPlayer = Marker.BORDER;
				gameIsOver =true;
				stage.setGameIsOver(true);
				stage.setWinningPlayer(winningPlayer);
			}
		}
		
		
	}
	
	private boolean findFiveHorizontal(AmobaTable table, int row, int column, int rowCount, int columnCount) {
		boolean foundFiveHorizontal = false;
		int mostLeftRow = row;
		int mostLeftColumn = column-4;
		for(int delta = 0; delta <5; delta++) {
			//gondolatmenet szemléltetése céljából vannak a nem használt változók
			int firstRow = mostLeftRow; int firstColumn = mostLeftColumn + delta;
			int secondRow = mostLeftRow; int secondColumn = mostLeftColumn+1 + delta;
			int thirdRow = mostLeftRow; int thirdColumn = mostLeftColumn+2 + delta;
			int forthRow = mostLeftRow; int forthColumn = mostLeftColumn+3 + delta;
			int fifthRow = mostLeftRow; int fifthColumn = mostLeftColumn+4 + delta;
			
			if( (mostLeftRow<rowCount) && (firstColumn < columnCount) && (firstColumn>=0) &&
				(secondColumn < columnCount) &&  (secondColumn>=0) && (thirdColumn < columnCount) && (thirdColumn >=0) &&
				(forthColumn < columnCount) && (forthColumn>=0) && (fifthColumn < columnCount) && (fifthColumn>=0) ) {
				
					int numberOfCircles =0;
					int numberOfCrosses = 0;
					for(int k = 0; k < 5; k++) {
						Marker m = (Marker) table.getValueAt(mostLeftRow, firstColumn+k);
						if( m.equals(Marker.CIRCLE)) {
							numberOfCircles++;
						}
						if( m.equals(Marker.CROSS)) {
							numberOfCrosses++;
						}
					}
					if((numberOfCircles==5) || (numberOfCrosses ==5)) {
						foundFiveHorizontal =true;
					}
					
			}
			
		}
		
		return foundFiveHorizontal;
	}
	
	
	private boolean findFiveVertical(AmobaTable table, int row, int column, int rowCount, int columnCount) {
		boolean foundFiveVertical = false;
		int mostDownRow = row+4;
		int mostDownColumn = column;
		for(int delta = 0; delta <5; delta++) {
			int firstRow = mostDownRow  - delta; int firstColumn = mostDownColumn;
			int secondRow = mostDownRow - 1 - delta; int secondColumn = mostDownColumn;
			int thirdRow = mostDownRow - 2 - delta; int thirdColumn = mostDownColumn;
			int forthRow = mostDownRow - 3 - delta; int forthColumn = mostDownColumn;
			int fifthRow = mostDownRow - 4 - delta; int fifthColumn = mostDownColumn;
			
			if( (mostDownColumn<columnCount) && (firstRow < rowCount) && (firstRow>=0) &&
				(secondRow < rowCount) &&  (secondRow>=0) && (thirdRow < rowCount) && (thirdRow>=0) &&
				(forthRow < rowCount) && (forthRow>=0) && (fifthRow < rowCount) && (fifthRow>=0) ) {
				
					int numberOfCircles =0;
					int numberOfCrosses = 0;
					for(int k = 0; k < 5; k++) {
						Marker m = (Marker) table.getValueAt(firstRow - k, mostDownColumn);
						if( m.equals(Marker.CIRCLE)) {
							numberOfCircles++;
						}
						if( m.equals(Marker.CROSS)) {
							numberOfCrosses++;
						}
					}
					
					if((numberOfCircles==5) || (numberOfCrosses ==5)) {
						foundFiveVertical =true;
					}
					
			}
			
		}
		
		return foundFiveVertical;
	}
	
	
	private boolean findFiveDiagonalFromBottomRight(AmobaTable table, int row, int column, int rowCount, int columnCount) {
		boolean foundFiveDiagonalFromButtomLeft = false;
		int mostBottomRightRow = row + 4;
		int mostBottomRightColumn = column + 4;
		for(int delta = 0; delta <5; delta++) {
			int firstRow = mostBottomRightRow  - delta; int firstColumn = mostBottomRightColumn  - delta;
			int secondRow = mostBottomRightRow - 1 - delta; int secondColumn = mostBottomRightColumn -1  - delta;
			int thirdRow = mostBottomRightRow - 2 - delta; int thirdColumn = mostBottomRightColumn -2 - delta;
			int forthRow = mostBottomRightRow - 3 - delta; int forthColumn = mostBottomRightColumn -3 - delta;
			int fifthRow = mostBottomRightRow - 4 - delta; int fifthColumn = mostBottomRightColumn -4 - delta;
			
			if( (firstColumn < columnCount) && (firstColumn>=0) && (secondColumn < columnCount) &&  (secondColumn>=0) 
					&& (thirdColumn < columnCount) && (thirdColumn >=0) && (forthColumn < columnCount) && (forthColumn>=0) 
					&& (fifthColumn < columnCount) && (fifthColumn>=0)&& (firstRow < rowCount) && (firstRow>=0) 
					&& (secondRow < rowCount) &&  (secondRow>=0)
					&& (thirdRow < rowCount) && (thirdRow>=0) &&(forthRow < rowCount) && 
					(forthRow>=0) && (fifthRow < rowCount) && (fifthRow>=0) ) {
						
						int numberOfCircles =0;
						int numberOfCrosses = 0;
						for(int k = 0; k < 5; k++) {
							Marker m = (Marker) table.getValueAt(firstRow - k, firstColumn - k);
							if( m.equals(Marker.CIRCLE)) {
								numberOfCircles++;
							}
							if( m.equals(Marker.CROSS)) {
								numberOfCrosses++;
							}
						}
						
						if((numberOfCircles==5) || (numberOfCrosses ==5)) {
							foundFiveDiagonalFromButtomLeft = true;
						}
						
				}
		}
		return foundFiveDiagonalFromButtomLeft;
	}
	
	
	private boolean findFiveDiagonalFromBottomLeft(AmobaTable table, int row, int column, int rowCount, int columnCount) {
		boolean foundFiveDiagonalFromBottomRight = false;
		int mostBottomLeftRow = row + 4;
		int mostBottomLeftColumn = column - 4;
		for(int delta = 0; delta <5; delta++) {
			int firstRow = mostBottomLeftRow  - delta; int firstColumn = mostBottomLeftColumn  + delta;
			int secondRow = mostBottomLeftRow - 1 - delta; int secondColumn = mostBottomLeftColumn + 1  + delta;
			int thirdRow = mostBottomLeftRow - 2 - delta; int thirdColumn = mostBottomLeftColumn + 2 + delta;
			int forthRow = mostBottomLeftRow - 3 - delta; int forthColumn = mostBottomLeftColumn  + 3 + delta;
			int fifthRow = mostBottomLeftRow - 4 - delta; int fifthColumn = mostBottomLeftColumn + 4 + delta;
			
			if( (firstColumn < columnCount) && (firstColumn>=0) && (secondColumn < columnCount) &&  (secondColumn>=0) 
					&& (thirdColumn < columnCount) && (thirdColumn >=0) && (forthColumn < columnCount) && (forthColumn>=0) 
					&& (fifthColumn < columnCount) && (fifthColumn>=0)&& (firstRow < rowCount) && (firstRow>=0) 
					&& (secondRow < rowCount) &&  (secondRow>=0)
					&& (thirdRow < rowCount) && (thirdRow>=0) &&(forthRow < rowCount) && 
					(forthRow>=0) && (fifthRow < rowCount) && (fifthRow>=0) ) {
						
						int numberOfCircles =0;
						int numberOfCrosses = 0;
						for(int k = 0; k < 5; k++) {
							Marker m = (Marker) table.getValueAt(firstRow - k, firstColumn + k);
							if( m.equals(Marker.CIRCLE)) {
								numberOfCircles++;
							}
							if( m.equals(Marker.CROSS)) {
								numberOfCrosses++;
							}
						}

						if((numberOfCircles==5) || (numberOfCrosses ==5)) {
							foundFiveDiagonalFromBottomRight =true;
						}
						
				}
		}
		return foundFiveDiagonalFromBottomRight;
	}
	
	
}






