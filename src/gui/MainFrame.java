//Player is always starting

package gui;


import game.*;
import java.awt.BorderLayout;
//import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

//import java.util.List;
//import javax.swing.table.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;




public class MainFrame extends JFrame implements Serializable{
	private boolean gameIsGoingForPlayer;
	private boolean gameIsOver;
	
	private AmobaStage stage;
	private JTable jt;
	
	private JPanel settingsJp;
	private AmobaClock clock;
	private JMenuItem startGameMenuItem;
	private JMenuItem loadGameMenuItem;
	private JMenuItem saveGameMenuItem;
	private JComboBox<Object> tableHeightCombo;
	private JComboBox<Object> tableWidthCombo;
	private JComboBox<Object> playerMarkerCombo;
	
	private ImageIcon circleIcon;
	private ImageIcon crossIcon;
	private ImageIcon emptyIcon;
	//private ImageIcon borderIcon;
	
	public void initComponents() {
		
		jt = new JTable();
        jt.setFillsViewportHeight(true);
        jt.setTableHeader(null);
        AmobaTableCellRenderer atcRenderer = new AmobaTableCellRenderer(jt.getDefaultRenderer(Marker.class));
        jt.setDefaultRenderer( Marker.class , atcRenderer);
        
        TableClickListener tcListener = new TableClickListener();
        jt.addMouseListener(tcListener);
        
        JScrollPane pane = new JScrollPane(jt);
        this.add(pane, BorderLayout.CENTER);
        
        initMarkerPictures();
        
        
        settingsJp = new JPanel();
        
        clock = new AmobaClock(10, this);
        settingsJp.add(clock);
        
        settingsJp.add(new JLabel("Table height:"));
        initTableHeightComboBox();
        settingsJp.add(tableHeightCombo);
        settingsJp.add(new JLabel("Table width:"));
        initTableWidthComboBox();
        settingsJp.add(tableWidthCombo);
        settingsJp.add(new JLabel("Your marker:"));
        initplayerMarkerComboBox();
        settingsJp.add(playerMarkerCombo);
        
        this.add(settingsJp, BorderLayout.NORTH);
        
        
        
        JMenu m1 = new JMenu("Actions");
        
        startGameMenuItem = new JMenuItem("Start new game!");
        startGameMenuItem.setActionCommand("start new game");
        StartGameMenuItemActionListener startGameBListener = new StartGameMenuItemActionListener();
        startGameMenuItem.addActionListener(startGameBListener);
        m1.add(startGameMenuItem);
 
        loadGameMenuItem = new JMenuItem("Load previous game!");
        loadGameMenuItem.setActionCommand("load game");
        LoadGameMenuitemActionListener loadGameBListener = new LoadGameMenuitemActionListener();
        loadGameMenuItem.addActionListener(loadGameBListener);
        m1.add(loadGameMenuItem);
        
        saveGameMenuItem = new JMenuItem("Save current game!");
        saveGameMenuItem.setActionCommand("save game");
        SaveGameMenuItemActionListener saveGameBListener = new SaveGameMenuItemActionListener();
        saveGameMenuItem.addActionListener(saveGameBListener);
        m1.add(saveGameMenuItem);
        
        JMenuBar bar = new JMenuBar();
        bar.add(m1);
        this.setJMenuBar(bar);
        
	}
	
	public void initStage(){
		jt.setModel(stage.getTable());
		stage.setMainFrame(this);
	}
	
	public void initTableHeightComboBox() {
		Object height[] = new Object[46];
		for(int i = 0; i <= 45; i++) {
			height[i] = (int) (i + 5);
		}
		tableHeightCombo = new JComboBox<Object>(height);
		tableHeightCombo.setSelectedItem(11);
		
	}
	
	public void initTableWidthComboBox() {
		Object width[] = new Object[26];
		for(int i = 0; i <= 25; i++) {
			width[i] = (int) (i+ 5);
		}
		tableWidthCombo = new JComboBox<Object>(width);
		tableWidthCombo.setSelectedItem(20);
	}
	
	public void initplayerMarkerComboBox() {
		Object options[] = new Object[2];
		options[0] = (String) "Circle";
		options[1] = (String) "Cross";
		playerMarkerCombo = new JComboBox<Object>(options);
		
	}
	
	public void setRowHeight() {
		jt.setRowHeight(60);
	}
	
	public void setColumnWidht() {
		for(int i =0; i < jt.getColumnCount(); i++) {
	        jt.getColumnModel().getColumn(i).setMaxWidth(60);
		}
	}
	
	public void initMarkerPictures(){
		emptyIcon = new ImageIcon("ures.png");
		circleIcon = new ImageIcon("kor.png");
		crossIcon = new ImageIcon("iksz.png");
		//borderIcon = new ImageIcon("hatar.png");
		
	}
	
	public void computerSteps() {
		AmobaStepMaker a= new AmobaStepMaker(stage);
		a.calculateStep();
		Coordinate computerStep = a.getStep();
		stage.makeStep(computerStep);
		
		if(!gameIsOver) {
			gameIsGoingForPlayer = true;
			startClock();
		}
		
	}
	
	public void playerSteps(Coordinate playerStep) {
		boolean successfulStep = stage.makeStep(playerStep);
        if(successfulStep && !gameIsOver) {
    		   gameIsGoingForPlayer = false;
    		   stopClock();
     	   
    		   computerSteps();
        }
       
	}
	
	public void initNewGame(){
		 int tableHeight = (int) tableHeightCombo.getSelectedItem();
	     int tableWidth = (int) tableWidthCombo.getSelectedItem();
	     String playerMarker = (String) playerMarkerCombo.getSelectedItem();
	     stage = new AmobaStage(tableHeight, tableWidth, playerMarker, this);
	     stage.setNotifyingMainFrameEnabled(true);
	     initStage();
	      
	     setRowHeight();	
	     setColumnWidht();
	      
	     gameIsOver = false;
	}
	
	public void startGame() {
		saveGameMenuItem.setEnabled(true);
		gameIsGoingForPlayer = true;
		gameIsOver = false;
		startClock();
	}
	
	public void startClock() {
		clock.startClock();
	}
	
	public void stopClock() {
		clock.stopClock();
	}
	
	public void playerTimeIsUp() {
		gameIsGoingForPlayer = false;
		gameIsOver = true;
		
		stage.setGameIsOver(true);
		Marker winner;
		if(stage.getPlayerMarker() == Marker.CIRCLE) {
			winner = Marker.CROSS;
		}
		else{
			winner = Marker.CIRCLE;
		}
		stage.setWinningPlayer(winner);
		
		computerWonGame();	
	}
	
	public void playerWonGame() {
		gameIsGoingForPlayer = false;
		stopClock();
		JOptionPane.showMessageDialog(this, "Gratulalok, nyertel!");
		gameIsEnded();
	}
	
	public void computerWonGame() {
		gameIsGoingForPlayer = false;
		stopClock();
		JOptionPane.showMessageDialog(this, "Sajnos most a gep nyert.");
		gameIsEnded();
	}
	
	public void gameIsDraw() {
		gameIsGoingForPlayer = false;
		stopClock();
		JOptionPane.showMessageDialog(this, "Dontetlen");
		gameIsEnded();
	}
	
	public void gameIsEnded() {
		clock.deactivateClock();
		saveGameMenuItem.setEnabled(false);
	}
	
	
	public void loadGame(){
		try {
		    ObjectInputStream ois = new ObjectInputStream(new FileInputStream("amobasavefile.dat"));
		    stage = (AmobaStage) ois.readObject();
		    ois.close();
		    
		    initStage();
		    setRowHeight();	
		    setColumnWidht();
		    startGame();
		} catch(Exception ex) {
		    ex.printStackTrace();
		}
	}
	
	
	
	public void saveGame() {
		//System.out.println("Mentve");
		try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("amobasavefile.dat"));
            oos.writeObject(stage);
            oos.close();

        } catch(Exception ex) {
            ex.printStackTrace();
        }
	}
	
	public void setGameIsOver(boolean newValue) {
		gameIsOver = newValue;
	}
	
	
	public MainFrame() {
	
        super("Amoba");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1210, 765));
        initComponents();
        
        gameIsGoingForPlayer = false;
        gameIsOver = false;
	}
	
	
	
	private class StartGameMenuItemActionListener implements ActionListener {
	        public void actionPerformed(ActionEvent ae) {
				if (ae.getActionCommand().equals("start new game")) {
				     initNewGame();
				     startGame();
				}
			}
			
		}
	
	
	private class LoadGameMenuitemActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if (ae.getActionCommand().equals("load game")) {
				 //System.out.println("Betolt Gomb figyelt");
			     loadGame();
			}
		}
		
	}
	
	
	private class SaveGameMenuItemActionListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
			if (ae.getActionCommand().equals("save game")) {
				//System.out.println("Gomb figyelt");
			    saveGame();
			}
		}
		
	}
	
	private class TableClickListener extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent me) {
			if(gameIsGoingForPlayer) {
               JTable t = (JTable) me.getSource();
               int row = t.getSelectedRow(); 
               int column = t.getSelectedColumn();
               Coordinate playerStepCoord = new Coordinate(row, column);
               playerSteps(playerStepCoord);
            	   
			}
		}
		
	}
	
	
	
	private class AmobaTableCellRenderer implements TableCellRenderer {

		 private TableCellRenderer renderer;

		 public AmobaTableCellRenderer(TableCellRenderer defRenderer) {
			 this.renderer = defRenderer;
		 }
		
		 public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus,int row, int column){
			 Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			 Marker actualMarker =  (Marker) stage.getTable().getValueAt(row, column);
			 if(actualMarker == Marker.EMPTY){
				 JLabel j = new JLabel();
				 j.setIcon(emptyIcon);
				 return j;
			 }
			 else if(actualMarker == Marker.BORDER){
				 JLabel j = new JLabel();
				 //j.setIcon(borderIcon);
				 j.setIcon(emptyIcon);
				 return j;		 
			 }
			 else if(actualMarker == Marker.CIRCLE){
				 JLabel j = new JLabel();
				 j.setIcon(circleIcon);
				 return j;
			 }
			 else if(actualMarker == Marker.CROSS){
				 JLabel j = new JLabel();
				 j.setIcon(crossIcon);
				 return j;			 
			 }
			 else {
			 return component;
			 }
		 
		  }
	}

}

