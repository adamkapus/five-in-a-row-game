package game;

public class AmobaStepMaker {
	private AmobaStage currentStage;
	private Coordinate step;
	
	public AmobaStepMaker(AmobaStage stage) {
		this.currentStage = stage;
		step = new Coordinate(-1, -1);
	}
	
	public void calculateStep() {
		int bestScore = Integer.MAX_VALUE;
		int rowCount = currentStage.getRowCount();
		int columnCount = currentStage.getColumnCount();
		Coordinate bestStep = new Coordinate(4, 5);
		for(int i =0; i < rowCount ; i++) {
			for(int j = 0; j < columnCount ; j++) {
				Marker actual = (Marker) currentStage.getTable().getValueAt(i, j);
				if(  (actual == Marker.BORDER)) {
					try {
						AmobaStage test = (AmobaStage) currentStage.clone();
						test.setNotifyingMainFrameEnabled(false);
						Coordinate possibleStep = new Coordinate(i, j);
						test.makeStep(possibleStep);

						int actualScore = calculateScore(test);
						if(actualScore < bestScore) {
							bestScore = actualScore;
							bestStep = possibleStep;
						}
					}
					catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		//System.out.println("SCORE: " + bestScore);
		step = bestStep;
	}
	
	public Coordinate getStep() {
		return step;
	}
	
	private int calculateScore(AmobaStage stage) {
		int score = 0;
		Marker playerMarker = stage.getPlayerMarker();
		int rowCount = stage.getRowCount();
		int columnCount = stage.getColumnCount();
		for(int i =0; i < rowCount ; i++) {
			for(int j = 0; j < columnCount ; j++) {
				Marker actual = (Marker) stage.getTable().getValueAt(i, j);
				if( ((actual == Marker.CROSS) || (actual == Marker.CIRCLE))  ) {
					int thisMarkerScoreHorizontal = calculateScoreHorizontal(stage.getTable(), playerMarker, actual, i, j, rowCount, columnCount);
					score+= thisMarkerScoreHorizontal;
					
					
					int thisMarkerScoreVertical = calculateScoreVertical(stage.getTable(), playerMarker, actual, i, j, rowCount, columnCount);
					score+= thisMarkerScoreVertical;
					
					
					int thisMarkerScoreDiagonalFromBottomRight = calculateScoreDiagonalFromBottomRight(stage.getTable(), playerMarker, actual, i, j, rowCount, columnCount);
					score+= thisMarkerScoreDiagonalFromBottomRight;
					
					
					int thisMarkerScoreDiagonalFromBottomLeft = calculateScoreDiagonalFromBottomLeft(stage.getTable(), playerMarker, actual, i, j, rowCount, columnCount);
					score+= thisMarkerScoreDiagonalFromBottomLeft;
				}
			}
		}
		return score;
	}
	
	private int calculateScoreHorizontal(AmobaTable table, Marker playerMarker, Marker actual, int row, int column, int rowCount, int columnCount) {
		int thisMarkerScore =0;
		Marker searchedMarker = actual;
		int mostLeftRow = row;
		int mostLeftColumn = column-4;
		// a gondolatmenet szemléltetése céljából vannak a nem használt változók
		for(int delta = 0; delta <5; delta++) {
			int firstRow = mostLeftRow; int firstColumn = mostLeftColumn + delta;
			int secondRow = mostLeftRow; int secondColumn = mostLeftColumn+1 + delta;
			int thirdRow = mostLeftRow; int thirdColumn = mostLeftColumn+2 + delta;
			int forthRow = mostLeftRow; int forthColumn = mostLeftColumn+3 + delta;
			int fifthRow = mostLeftRow; int fifthColumn = mostLeftColumn+4 + delta;
			//ellenorizzuk, hogy mind az ot mezo kiolvashato-e
			if( (mostLeftRow<rowCount) && (firstColumn < columnCount) && (firstColumn>=0) &&
				(secondColumn < columnCount) &&  (secondColumn>=0) && (thirdColumn < columnCount) && (thirdColumn >=0) &&
				(forthColumn < columnCount) && (forthColumn>=0) && (fifthColumn < columnCount) && (fifthColumn>=0) ) {
					//megnezzuk, mi van ebben az ot mezoben
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
					//ehhez az otoshoz kiszamoljuk a score-t
					int thisFiveScore = calculateScoreForFive(numberOfCircles, numberOfCrosses, searchedMarker, playerMarker);
					thisMarkerScore+= thisFiveScore;
			}
			
		}
		
		return thisMarkerScore;
	}
	
	private int calculateScoreVertical(AmobaTable table, Marker playerMarker, Marker actual, int row, int column, int rowCount, int columnCount) {
		int thisMarkerScore =0;
		Marker searchedMarker = actual; 
		int mostDownRow = row+4;
		int mostDownColumn = column;
		for(int delta = 0; delta <5; delta++) {
			int firstRow = mostDownRow  - delta; int firstColumn = mostDownColumn;
			int secondRow = mostDownRow - 1 - delta; int secondColumn = mostDownColumn;
			int thirdRow = mostDownRow - 2 - delta; int thirdColumn = mostDownColumn;
			int forthRow = mostDownRow - 3 - delta; int forthColumn = mostDownColumn;
			int fifthRow = mostDownRow - 4 - delta; int fifthColumn = mostDownColumn;
			//ellenorizzuk, hogy mind az ot mezo kiolvashato-e
			if( (mostDownColumn<columnCount) && (firstRow < rowCount) && (firstRow>=0) &&
				(secondRow < rowCount) &&  (secondRow>=0) && (thirdRow < rowCount) && (thirdRow>=0) &&
				(forthRow < rowCount) && (forthRow>=0) && (fifthRow < rowCount) && (fifthRow>=0) ) {
					//megnezzuk, mi van ebben az ot mezoben
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
					//ehhez az otoshoz kiszamoljuk a score-t
					int thisFiveScore = calculateScoreForFive(numberOfCircles, numberOfCrosses, searchedMarker, playerMarker);
					thisMarkerScore+= thisFiveScore;
			}
			
		}
		
		return thisMarkerScore;
	}
	
	private int calculateScoreDiagonalFromBottomRight(AmobaTable table, Marker playerMarker, Marker actual, int row, int column, int rowCount, int columnCount) {
		int thisMarkerScore =0;
		Marker searchedMarker = actual;
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
						//megnezzuk, mi van ebben az ot mezoben
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
						//ehhez az otoshoz kiszamoljuk a score-t
						int thisFiveScore = calculateScoreForFive(numberOfCircles, numberOfCrosses, searchedMarker, playerMarker);
						thisMarkerScore+= thisFiveScore;
				}
		}
		return thisMarkerScore;
	}
	
	private int calculateScoreDiagonalFromBottomLeft(AmobaTable table, Marker playerMarker, Marker actual, int row, int column, int rowCount, int columnCount) {
		int thisMarkerScore =0;
		Marker searchedMarker = actual;
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
						//megnezzuk, mi van ebben az ot mezoben
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
						//ehhez az otoshoz kiszamoljuk a score-t
						int thisFiveScore = calculateScoreForFive(numberOfCircles, numberOfCrosses, searchedMarker, playerMarker);
						thisMarkerScore+= thisFiveScore;
				}
		}
		return thisMarkerScore;
	}
	
	private int calculateScoreForFive(int numberOfCircles, int numberOfCrosses, Marker searchedMarker, Marker playermarker) {
		if (numberOfCircles>0 && numberOfCrosses>0 ) {
			return 0;
		}
		else if((searchedMarker == Marker.CIRCLE) && (numberOfCircles>0)) {
				  int scoreWithoutSign =0;
				  switch(numberOfCircles) {
					  case 1: /*scoreWithoutSign+= 1;*/ break;
					  case 2: scoreWithoutSign+= 1;break;
					  case 3: scoreWithoutSign+= 20; break;
					  case 4: scoreWithoutSign+= 1000; break;
					  case 5: scoreWithoutSign+= 100000; break;
					  default: break;
				  }
				  if(searchedMarker != playermarker) {
					  scoreWithoutSign = scoreWithoutSign*(-1);
				  }
			  return scoreWithoutSign;
			}
		else if((searchedMarker == Marker.CROSS) && (numberOfCrosses>0)){
					int scoreWithoutSign =0;
					switch(numberOfCrosses) {
						case 1: /*scoreWithoutSign+= 1;*/ break;
						case 2: scoreWithoutSign+= 1;break;
						case 3: scoreWithoutSign+= 20; break;
						case 4: scoreWithoutSign+= 1000; break;
						case 5: scoreWithoutSign+= 100000; break;
						default: break;
					  }
					if(searchedMarker != playermarker) {
						scoreWithoutSign = scoreWithoutSign*(-1);
					}
			  return scoreWithoutSign;
		}
		else {
			return 0;
		}
			
	}
	
	

}

