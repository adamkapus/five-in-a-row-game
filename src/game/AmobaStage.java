package game;

import java.io.Serializable;

import gui.MainFrame;

public class AmobaStage implements Serializable, Cloneable{
	private AmobaTable table;
	private Marker playerMarker ;
	Marker computerMarker ;
	private int numberOfPreviousSteps;
	private boolean gameIsOver;
	private Marker winningPlayer;
	
	private MainFrame mf;
	private boolean NotifyingFrameEnabled;

	
	
	public AmobaStage(int rowCount, int columnCount, String playerMarker, MainFrame mf) {
		table = new AmobaTable(rowCount, columnCount);
		
		if(playerMarker.equals("Circle")) {
			this.playerMarker = Marker.CIRCLE;
			this.computerMarker = Marker.CROSS;
		}
		else {
			this.playerMarker = Marker.CROSS;
			this.computerMarker = Marker.CIRCLE;
		}
		
		this.mf = mf;
		NotifyingFrameEnabled = false;
		
		numberOfPreviousSteps= 0;
		gameIsOver=false;
		winningPlayer = Marker.EMPTY;
	}
	
	public AmobaTable getTable() {
		return table;
	}
	
	public Marker getMarker(Coordinate c) {
		int row = c.getRow();
		int column = c.getColumn();
		return  (Marker) table.getValueAt(row, column);
	}
	
	public int getRowCount() {
		return table.getRowCount();
	}
	
	public int getColumnCount() {
		return table.getColumnCount();
	}
	
	public Marker getPlayerMarker() {
		return playerMarker;
	}
	
	public int getNumberOfPreviousSteps() {
		return numberOfPreviousSteps;
	}
	
	public Marker getWinningPlayer() {
		return winningPlayer;
	}
	
	
	public void setGameIsOver(boolean newGameIsOver) {
		gameIsOver = newGameIsOver;
	}
	
	public void setWinningPlayer(Marker newWinningPlayer) {
		winningPlayer = newWinningPlayer;
	}
	
	public boolean getGameIsOver() {
		return gameIsOver;
	}
	
	public void setNotifyingMainFrameEnabled(boolean newValue) {
		NotifyingFrameEnabled = newValue;
	}
	
	public void setMainFrame(MainFrame mf) {
		this.mf = mf;
	}
	
	private void NotifyFrameAboutGameOver() {
		
		if(gameIsOver) {
			mf.setGameIsOver(true);
			
			if(winningPlayer == playerMarker) {
				mf.playerWonGame();
			}
			else if(winningPlayer == Marker.BORDER){
					mf.gameIsDraw();
			}
			else {
				mf.computerWonGame();
			}
		}

	}
	
	
	public boolean makeStep(Coordinate step) {
		int row = step.getRow();
		int column = step.getColumn();
		if((row>=0) && (row < table.getRowCount()) && (column >=0 ) && (column < table.getColumnCount())) {
			Marker actual = (Marker) table.getValueAt(row, column);
			if((actual==Marker.EMPTY) || (actual==Marker.BORDER)) {
				if(numberOfPreviousSteps%2 ==0) {
					table.setValueAt(playerMarker, row, column);
				}
				else {
					table.setValueAt(computerMarker, row, column);
				}
				updateBorders(step);
				numberOfPreviousSteps++;
				AmobaGameOverChecker checker = new AmobaGameOverChecker(this);
				checker.checkIfGameIsOver(step);
				
				if(NotifyingFrameEnabled) {
					NotifyFrameAboutGameOver();
				}
				
				
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	public void updateBorders(Coordinate lastStep) {
		int upperLeftRowCoord = lastStep.getRow()-1;
		int upperLeftColumnCoord = lastStep.getColumn()-1;
		for(int i =0; i < 3; i++) {
			for(int j = 0; j<3; j++) {
				int actualRowCoord = upperLeftRowCoord + i;
				int actualColumnCoord = upperLeftColumnCoord + j;
				if( (actualRowCoord >= 0) && (actualRowCoord < table.getRowCount()) && (actualColumnCoord >= 0) && (actualColumnCoord < table.getColumnCount() ) ) {
					Marker actual = (Marker) table.getValueAt(actualRowCoord, actualColumnCoord);
					if(actual == Marker.EMPTY) {
						table.setValueAt(Marker.BORDER, actualRowCoord, actualColumnCoord);
					}
				}
			}
		}
	}
	
	public Object clone()   {
		String pMarker;
		if(playerMarker == Marker.CIRCLE) {
			pMarker = "Circle";
		}
		else {
			pMarker = "Cross";
		}
		AmobaStage a2 = new AmobaStage(getRowCount(), getColumnCount(), pMarker, mf);
		//System.out.println("CLONEE : " +playerMarker.toString());
		a2.numberOfPreviousSteps = numberOfPreviousSteps;
		a2.gameIsOver = gameIsOver;
		a2.winningPlayer = winningPlayer;
		
		a2.table = (AmobaTable) table.clone();
		
		return a2;
	}
	
}
