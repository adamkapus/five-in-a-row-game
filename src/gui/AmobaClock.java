package gui;

import java.awt.Color;
//import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JTextField;
import javax.swing.Timer;


public class AmobaClock extends JTextField implements Serializable {
	private Timer timer;
	private int maxCountBack;
	private int actualCountBack;
	private ClockActionListener clockListener;
	private MainFrame mf;
	
	public AmobaClock(int countBack, MainFrame mf) {
		this.maxCountBack = countBack;
		this.mf = mf;
		actualCountBack = maxCountBack;
		Font normalFont = new Font(null, Font.PLAIN, 20);
		this.setFont(normalFont);
		updateTime();
		this.setEditable(false);
		
		clockListener = new ClockActionListener();
		timer = new Timer(1000, clockListener);
		timer.setActionCommand("clock");
	}
	
	public void startClock() {
		actualCountBack = maxCountBack;
		updateTime();
		timer.restart();
	}
	
	public void stopClock() {
		timer.stop();
	}
	
	private void updateTime() {
		String newText;
		int minutes = actualCountBack/60;
		int seconds = actualCountBack%60;
		if(minutes <= 9) {
			newText = "0" + minutes;
		}
		else {
			newText = "" + minutes;
		}
		newText = newText + ":";
		if(seconds <= 9) {
			newText = newText + "0" + seconds;
		}
		else{
			newText = newText + seconds;
		}
		
		
		if((minutes ==0) && seconds <=5) {
			setForeground(Color.RED);
		}
		else {
			setForeground(Color.BLACK);
		}
		
		setText(newText);
		
	}
	
	
	public void deactivateClock() {
		setForeground(Color.BLACK);
		setText(" --:-- ");
	}
	
	private class ClockActionListener implements ActionListener, Serializable {
        public void actionPerformed(ActionEvent ae) {
			if (ae.getActionCommand().equals("clock")) {
				if(actualCountBack>0) {
					
					actualCountBack = actualCountBack-1;
					updateTime();
				}
				else {
					mf.setGameIsOver(true);
					mf.playerTimeIsUp();
				}
			}
		}
		
	}
	
}
