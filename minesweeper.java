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
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.util.Scanner;


class MineSweeper extends WindowAdapter {
	int GRID_SIZE=25;
	int ROWS,COLUMS,TOTAL_MINE;
	boolean continue_on_saved_game,save_on_exiting_game;
	CellManager manager;
	JFrame frame;

	public MineSweeper(int rows, int colums){
	  frame = new JFrame("Minesweeper");
	  frame.setLayout(new GridBagLayout());
	  frame.addWindowListener(this);
	  frame.setVisible(true);

	  updateOption();
	  setUpCellManager(rows,colums,TOTAL_MINE);
	  setUpMenuBar();
		
	}

	private void setUpCellManager(int rows, int colums, int mine){
	   frame.setSize(new Dimension(GRID_SIZE*rows+150,GRID_SIZE*colums+150));
	   System.out.println("M:rows="+(GRID_SIZE*rows+150)+","+"colums="+(GRID_SIZE*colums+150));	   
	   manager = new CellManager(rows,colums,TOTAL_MINE);
	   manager.addGameBoardToFrame(frame);
	   manager.startGame();	
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
			openOptionFrame();
		}
	  });

	  new_game.addActionListener(new ActionListener(){
	  	public void actionPerformed(ActionEvent e){
			manager.newGame();
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

	void showSaveGameDialog(){

		    manager.pauseGame();
		    if(save_on_exiting_game){
		    	saveGameThenExit(true);
		    }else{

		     JDialog exit_dialog= new JDialog(frame,"",Dialog.ModalityType.DOCUMENT_MODAL);

		     int a=JOptionPane.showConfirmDialog(exit_dialog,"Do you want to save this game?");  
			if(a==JOptionPane.YES_OPTION){  
			    saveGameThenExit(true);
   			}else if(a==JOptionPane.NO_OPTION){
			    saveGameThenExit(false);			
			}else {manager.resumeFromPause();}
		   }
	}

	private void saveGameThenExit(boolean save){
		
		if(save){ manager.saveGame();}

		//save the options in a file for future use
	   try{
		FileWriter save_options = new FileWriter("Data/options.txt");
		String option = String.valueOf(ROWS);
		save_options.write(option);save_options.write(String.format("%n"));
		option= String.valueOf(COLUMS);
		save_options.write(option);save_options.write(String.format("%n"));
		option= String.valueOf(TOTAL_MINE);
		save_options.write(option);save_options.write(String.format("%n"));
		option= String.valueOf(continue_on_saved_game);
		save_options.write(option);save_options.write(String.format("%n"));
		option= String.valueOf(save_on_exiting_game);
		save_options.write(option);save_options.write(String.format("%n"));
		save_options.close();
	   }catch(Exception e){
	   
	   }

		//terminate minesweeper game
		frame.dispose();System.exit(0);
	}

	private void updateOption(){
	  try{
	      FileReader option= new FileReader("option.txt");
	      BufferedReader reader = new BufferedReader(option);
	      Scanner parser = new Scanner(reader);
	
	      ROWS= parser.nextInt();
	      COLUMS= parser.nextInt();
	      TOTAL_MINE= parser.nextInt();
	      continue_on_saved_game= parser.nextBoolean();
	      save_on_exiting_game= parser.nextBoolean();

	     }catch(IOException e){
	  	ROWS= 10;
		COLUMS= 20;
		TOTAL_MINE= ROWS*COLUMS/5;
		continue_on_saved_game=false;
		save_on_exiting_game= false;
	     }
	}


	private void openOptionFrame(){
	    new optionFrame(this);
	}

	private void openStatisticsFrame(){
	
	}

	public void windowClosing(WindowEvent e){
	    showSaveGameDialog();
	}

	public void windowActivated( WindowEvent e){
		//File f = new File("Data/save.txt");
		
	}

	public void resumeFromPause(){ manager.resumeFromPause();}
	public void change_saving_option(boolean continue_sv, boolean sv_exit){
	  continue_on_saved_game= continue_sv;
	  save_on_exiting_game = sv_exit;
	}

	public void change_grid_option(int row,int col, int mine){
	  ROWS = row;
	  COLUMS = col;
	  TOTAL_MINE = mine;

	  frame.getContentPane().removeAll();
	  setUpCellManager(ROWS,COLUMS,TOTAL_MINE);//this creates a new cellmanager
	  setUpMenuBar();

	}




	public static void main (String[] args ){

	 //X-cordinate==number of colums==width of the mine fields
	 //Y-cordinate==number of rows == height of the mine fields
	
	    MineSweeper game = new MineSweeper(10,20);
	}// end of main
}
