import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.Border;
import javax.swing.SpringLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.util.Scanner;


class MineSweeper extends WindowAdapter {
	int GRID_SIZE=25;
	int ROWS,COLUMS,TOTAL_MINE;
	boolean continue_on_saved_game,save_on_exiting_game;
	CellManager manager;
	JFrame frame;

	public MineSweeper(){
	  frame = new JFrame("Minesweeper");
	  frame.setLayout(new GridBagLayout());
	  updateOption();
	  showStartGameDialog();
	  frame.addWindowListener(this);
	  setUpMenuBar();
	  frame.setVisible(true);

	}

	private void setUpCellManager(int rows, int colums, int mine,boolean saved_game){
	   frame.setSize(new Dimension(GRID_SIZE*rows+150,GRID_SIZE*colums+150));   
	   manager = new CellManager(rows,colums,mine);
	   manager.addGameBoardToFrame(frame);
	   if(saved_game) manager.importGame();
	   else manager.startGame();	
	   frame.setVisible(true);
	}

	private void setUpMenuBar(){
	  JMenuBar menuBar = new JMenuBar();
 	  JMenu menu = new JMenu("Game");


	  JMenuItem new_game = new JMenuItem("New Game");
	  JMenuItem exit = new JMenuItem("Exit");
	  JMenuItem statistics = new JMenuItem("Statistics");
 	  JMenuItem options = new JMenuItem("Options");
	  JMenuItem pause = new JMenuItem("Pause Game");

	  options.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			manager.pauseGame();
			new optionFrame(MineSweeper.this);
		}
	  });

	  new_game.addActionListener(new ActionListener(){
	  	public void actionPerformed(ActionEvent e){
			manager.makeNewGame();
		}
	  });
	  exit.addActionListener( new ActionListener(){
	  	public void actionPerformed(ActionEvent e){
	                 showSaveGameDialog();
		}	       
	  });

	  pause.addActionListener( new ActionListener(){
	  	public void actionPerformed(ActionEvent e){
			final String p_txt = "Pause Game";
			final String r_txt = "Resume Game";

		      	String text = pause.getText();
			if( text.equals(p_txt)){
				manager.pauseGame();
				pause.setText(r_txt);
			}else{
			 	manager.resumeFromPause();
				pause.setText(p_txt);
			}
		}	       
	  });

	 statistics.addActionListener( new ActionListener(){
	 	public void actionPerformed(ActionEvent e){
			manager.pauseGame();
			new statisticsFrame(manager.getStatisticsModel(),
				MineSweeper.this);
			manager.resumeFromPause();
		}
	 }); 



	  menu.add(new_game);
	  menu.addSeparator();
	  menu.add(pause);
	  menu.addSeparator();
	  menu.add(statistics);
	  menu.add(options);
	  menu.addSeparator();
	  menu.add(exit);
	  menuBar.add(menu);
	  frame.setJMenuBar(menuBar);	
	}

	private void showSaveGameDialog(){

		    manager.pauseGame();
		    if(save_on_exiting_game){
		    	    if(manager.isGameStarted()) manager.saveGame();
			    frame.dispose();
			    System.exit(0);
		    }else{

		     JDialog exit_dialog= new JDialog(frame,"",
				     Dialog.ModalityType.DOCUMENT_MODAL);

		     int a=JOptionPane.showConfirmDialog(exit_dialog,
				     "Do you want to save this game?");  
			if(a==JOptionPane.YES_OPTION){  
			    manager.saveGame();
			    frame.dispose();
			    System.exit(0);
   			}else if(a==JOptionPane.NO_OPTION){
			    frame.dispose();
			    System.exit(0);			    
			}else {manager.resumeFromPause();
			  frame.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
			}
		   }
	}

	private void showStartGameDialog(){

		    File saved_game = new File("Data/save.txt");     

		    if(continue_on_saved_game){
			setUpCellManager(ROWS,COLUMS,TOTAL_MINE,true);
		    }else if(saved_game.isFile()){

		     JDialog start_dialog= new JDialog(frame,"",
				     Dialog.ModalityType.DOCUMENT_MODAL);

		     
		     int a=JOptionPane.showConfirmDialog(start_dialog,
				     "Do you want to continue with saved game?");  
			if(a==JOptionPane.YES_OPTION){  
				setUpCellManager(ROWS,COLUMS,TOTAL_MINE,true);
   			}else if(a==JOptionPane.NO_OPTION){
				saved_game.delete();
				setUpCellManager(ROWS,COLUMS,TOTAL_MINE,false);			    
			}else {
				      System.exit(0);
			}
	            
		   }else{
			setUpCellManager(ROWS,COLUMS,TOTAL_MINE,false);			   
		   }
	}


	private void updateOption(){
	  try{
	      
	      	FileReader option= new FileReader("Data/options.txt");
	      	BufferedReader reader = new BufferedReader(option);
	      	Scanner parser = new Scanner(reader);
	
	      	ROWS= parser.nextInt();
	      	COLUMS= parser.nextInt();
	      	TOTAL_MINE= parser.nextInt();
	      	continue_on_saved_game= parser.nextBoolean();
	      	save_on_exiting_game= parser.nextBoolean();
	      	option.close();
	      

	     }catch(IOException e){
	  	ROWS= 16;
		COLUMS= 16;
		TOTAL_MINE= 40;
		continue_on_saved_game=false;
		save_on_exiting_game= false;
	     }
	}


	public void windowClosing(WindowEvent e){
	     showSaveGameDialog();
	}

	public void resumeFromPause(){ manager.resumeFromPause();}

	public void change_grid_option(int row,int col, int mine){
	  ROWS = row;
	  COLUMS = col;
	  TOTAL_MINE = mine;

	  frame.getContentPane().removeAll();
	  setUpCellManager(ROWS,COLUMS,TOTAL_MINE,false);
	  setUpMenuBar();
	}


	public static void main (String[] args ){

	 //X-cordinate==number of colums==width of the mine fields
	 //Y-cordinate==number of rows == height of the mine fields
	
	    MineSweeper game = new MineSweeper();
	}// end of main
}
