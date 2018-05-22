import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.Border;
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


class CellManager{


                int GRID_SIZE=25;

		Cell[][] cells;
		JPanel game_board;
		JLabel timer_show,mine_left_show,cell_revealed_show;


		int ROWS;
		int COLUMS;
		int cells_count;
		int mines_count;
		int TOTAL_MINE;
		Timer mine_timer;
		int time_so_far;

		JDialog end;
		JPanel buttonPanel;
		JLabel massage;
		JButton restart, exit,new_game;	

		public CellManager(int x, int y, int mine){
			ROWS= x;
			COLUMS= y;
			cells_count=0;
			TOTAL_MINE=mine;
			mines_count=TOTAL_MINE;

			game_board = new JPanel();
			game_board.setSize(new Dimension(GRID_SIZE*x,GRID_SIZE*y));
		        System.out.println("C:x="+(GRID_SIZE*x)+","+"y="+(GRID_SIZE*y));
		  	cells= createCells(x,y);
			game_board.setLayout(new GridLayout(x,y));

			for(int i=0; i<ROWS; i++){
		  	     for(int j=0;j<COLUMS; j++){
		    	       game_board.add(cells[i][j]); 
		  	     }
			}

		}

		public void addGameBoardToFrame(JFrame frame){

		GridBagConstraints gameBoardConstraints = new GridBagConstraints();
		gameBoardConstraints.gridx=1;
		gameBoardConstraints.gridy=1;
		gameBoardConstraints.gridwidth= 6;
		gameBoardConstraints.insets= new Insets(5,5,5,5);
		gameBoardConstraints.anchor = GridBagConstraints.CENTER;
		frame.add(game_board,gameBoardConstraints);

		GridBagConstraints timerConstraints = new GridBagConstraints();
		timerConstraints.gridx=1;
		timerConstraints.gridy=2;
		timerConstraints.ipady=5;
		timerConstraints.insets= new Insets(0,10,0,0);
		timerConstraints.anchor = GridBagConstraints.LAST_LINE_START;
		

		JLabel timer = new JLabel("Time: ");
		frame.add(timer,timerConstraints);

		timerConstraints.gridx=2;
		timerConstraints.insets = new Insets(0,2,0,0);
		timer_show = new JLabel("0");
		frame.add(timer_show,timerConstraints);

		timerConstraints.gridx=3;
		timerConstraints.insets= new Insets(0,40,0,0);
		JLabel mine_left = new JLabel("Mine Left: ");
		frame.add(mine_left,timerConstraints);

		timerConstraints.gridx=4;
		timerConstraints.insets= new Insets(0,2,0,0);	
		mine_left_show = new JLabel();
		mine_left_show.setText(String.valueOf(mines_count));
		frame.add(mine_left_show,timerConstraints);

		timerConstraints.gridx=5;
		timerConstraints.insets= new Insets(0,40,0,0);	
		JLabel cell_revealed = new JLabel("Cell Revealed:");
		frame.add(cell_revealed,timerConstraints);

		timerConstraints.gridx=6;
		timerConstraints.insets= new Insets(0,2,0,0);	
		cell_revealed_show = new JLabel();
		cell_revealed_show.setText(String.valueOf(cells_count));
		frame.add(cell_revealed_show,timerConstraints);


		end = new JDialog(frame,"End Game",Dialog.ModalityType.DOCUMENT_MODAL);
		end.setLayout(new BoxLayout(end.getContentPane(),BoxLayout.Y_AXIS));
		end.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				newGame();
			}
		});

		 massage = new JLabel();
		 massage.setAlignmentX(Component.CENTER_ALIGNMENT);
		 buttonPanel = new JPanel();
		     
		 
		 restart = new JButton("restart this game");
		 restart.addActionListener(new ActionListener(){
		 public void actionPerformed(ActionEvent e){
			      
				reStartGame();
				end.dispose();
			 }
		 });
		     
		 new_game= new JButton("start new game");
		   new_game.addActionListener(new ActionListener(){
		         public void actionPerformed(ActionEvent e){ 
				newGame();
				end.dispose();
			 }
		 });

		 exit = new JButton("exit");
		 exit.addActionListener(new ActionListener(){
		         public void actionPerformed(ActionEvent e){ 
				frame.dispatchEvent(new WindowEvent(frame,
				      WindowEvent.WINDOW_CLOSING));
				end.dispose();
			 }
		 });

		    buttonPanel.setLayout(new FlowLayout());
		    buttonPanel.add(new_game);
		    buttonPanel.add(restart);
		    end.add(massage);
		    end.add(buttonPanel);
		    end.setSize(300,100);

		}//end of addGameBoardToFrame

	  	private Cell[][] createCells(int ROWS,int COLUMS){
	
			Cell[][] cells = new Cell[ROWS][COLUMS];
			for(int i=0; i<ROWS; i++){
		  	   for(int j=0;j<COLUMS; j++){
		    	     cells[i][j] = new Cell(this);
		  	   }
			}
		
			for(int i=0;i<ROWS;i++){
			   for(int j=0;j<COLUMS;j++){
		   		if(i==0 ){

					if(j==0){
				
				  	cells[0][0].addNeighbour(cells[0][1]);
				  	cells[0][0].addNeighbour(cells[1][0]);
				  	cells[0][0].addNeighbour(cells[1][1]);	

					}else if (j== COLUMS-1){
				   
					cells[0][COLUMS-1].addNeighbour(cells[0][COLUMS-2]);
				   	cells[0][COLUMS-1].addNeighbour(cells[1][COLUMS-2]);
				   	cells[0][COLUMS-1].addNeighbour(cells[1][COLUMS-1]);

					}else {

			       	   	cells[i][j].addNeighbour(cells[i][j-1]);
			    	   	cells[i][j].addNeighbour(cells[i][j+1]);
			    	   	cells[i][j].addNeighbour(cells[i+1][j]);
			    	   	cells[i][j].addNeighbour(cells[i+1][j-1]);
			    	   	cells[i][j].addNeighbour(cells[i+1][j+1]);	
					}
				}
				else if(i==ROWS-1 ){

					if(j==0){
				  
				  	cells[ROWS-1][0].addNeighbour(cells[ROWS-2][0]);
				  	cells[ROWS-1][0].addNeighbour(cells[ROWS-2][1]);
				  	cells[ROWS-1][0].addNeighbour(cells[ROWS-1][1]);
				
					} else if (j==COLUMS-1){
				
					cells[ROWS-1][COLUMS-1].addNeighbour(cells[ROWS-2][COLUMS-2]);
					cells[ROWS-1][COLUMS-1].addNeighbour(cells[ROWS-2][COLUMS-1]);
				  	cells[ROWS-1][COLUMS-1].addNeighbour(cells[ROWS-1][COLUMS-2]);

					} else {

			    	  	cells[i][j].addNeighbour(cells[i][j-1]);
			   	  	cells[i][j].addNeighbour(cells[i][j+1]);
			    	  	cells[i][j].addNeighbour(cells[i-1][j]);
			    	  	cells[i][j].addNeighbour(cells[i-1][j-1]);
			    	  	cells[i][j].addNeighbour(cells[i-1][j+1]);
					}

				}
				else if(j==0 && (i!=0 || i!=ROWS-1) ){
			    		cells[i][j].addNeighbour(cells[i+1][j]);
			    		cells[i][j].addNeighbour(cells[i-1][j]);
			    		cells[i][j].addNeighbour(cells[i+1][j+1]);
			    		cells[i][j].addNeighbour(cells[i-1][j+1]);
			    		cells[i][j].addNeighbour(cells[i][j+1]);
				}
				else if(j==COLUMS-1 && (i!=0 || i!= ROWS -1)){
			    		cells[i][j].addNeighbour(cells[i-1][j]);
			    		cells[i][j].addNeighbour(cells[i+1][j]);
			    		cells[i][j].addNeighbour(cells[i-1][j-1]);
			    		cells[i][j].addNeighbour(cells[i+1][j-1]);
			    		cells[i][j].addNeighbour(cells[i][j-1]);
				}else{
			    		cells[i][j].addNeighbour(cells[i-1][j]);
			    		cells[i][j].addNeighbour(cells[i+1][j]);
			    		cells[i][j].addNeighbour(cells[i-1][j-1]);
			    		cells[i][j].addNeighbour(cells[i+1][j-1]);
			    		cells[i][j].addNeighbour(cells[i][j-1]);
			    		cells[i][j].addNeighbour(cells[i][j+1]);
			    		cells[i][j].addNeighbour(cells[i-1][j+1]);
			    		cells[i][j].addNeighbour(cells[i+1][j+1]);
				}
		   	    }//inner forloop
		      }//outer forloop
			return cells;
	}

		public void startGame(){
			Random ran = new Random();

			int x,y,counter=0;
		      while(counter!=TOTAL_MINE){

		      do{
			  x=  ran.nextInt(ROWS);
			  y = ran.nextInt(COLUMS);

			}while(cells[x][y].hasMine());
			 counter++;
		         cells[x][y].setMine();

   		      }
		      reStartGame();
		}

		public void reStartGame(){
		     for(int i=0;i<ROWS;i++){
		     	for(int j=0;j<COLUMS;j++){
			    cells[i][j].startGame();
			}
		     }
		    resetLabels(0,TOTAL_MINE,0);
		}

		public void newGame(){
		     	for(int i=0;i<ROWS;i++){
		     	  for(int j=0;j<COLUMS;j++){
			    	cells[i][j].removeMine();
			  }
		     	}
			startGame();		
		}

		public void resumeFromPause(){
		    resetLabels(time_so_far,mines_count,cells_count);
		    if(cells_count!=0) startTimer();
		     for(int i=0;i<ROWS;i++){
		     	for(int j=0;j<COLUMS;j++){
			    cells[i][j].resumeGame();
			}
		     }  
		}

		public void pauseGame(){
		    	for(int i=0;i<ROWS;i++){
		     	  for(int j=0;j<COLUMS;j++){
			    	cells[i][j].pauseGame();
			  }
		     	}
		        if(cells_count!=0) mine_timer.cancel();
		}

		private void resetLabels(int time, int mine, int cells){
		       time_so_far =time;
		       timer_show.setText(String.valueOf(time_so_far));
		       mines_count=mine;
		       mine_left_show.setText(String.valueOf(mines_count));
		       cells_count=cells;
		       cell_revealed_show.setText(String.valueOf(cells_count));
		
		}

		private void startTimer(){
		        mine_timer = new Timer();
			mine_timer.scheduleAtFixedRate( new TimerTask(){
				public void run(){
				    time_so_far++;
				    timer_show.setText(
				   String.valueOf(time_so_far));
				}},0, 1000); 
		}

		public void endGame(boolean win){

		     for(int i=0;i<ROWS;i++){
		     	  for(int j=0;j<COLUMS; j++){
			  	cells[i][j].endGame(win);
			  }
			  if(cells_count!=0) mine_timer.cancel();
		     }


		     buttonPanel.remove(1);
		     if(win){ massage.setText("Congradulation! you Win.");
				buttonPanel.add(exit);
		     }
		     else{ massage.setText("Sorry! you Lose.");
				buttonPanel.add(restart);
		     }

		     end.setVisible(true);
		}
		
		public void saveGame(){
	         try{	
			System.out.println("cellMeneger.savegame is called");
			FileWriter saved_game= new FileWriter("Data/save.txt");
			String x_cord = Integer.toString(ROWS);
			String y_cord = Integer.toString(COLUMS);
			String mine_count = Integer.toString(TOTAL_MINE);
			saved_game.write(x_cord,0,x_cord.length());
			saved_game.write(String.format("%n"));
			saved_game.write(y_cord);
			saved_game.write(String.format("%n"));
			saved_game.write(mine_count);
			saved_game.write(String.format("%n"));

			String cell_info;
			for(int i=0;i<ROWS;i++){
			    for(int j=0;j<COLUMS;j++){
			    	cell_info = cells[i][j].output_cell();
				saved_game.write(cell_info,0,cell_info.length());
			    }
			    saved_game.write(String.format("%n"));
			}
			saved_game.close();

		    }catch(IOException e){
		       System.out.print("Write to lastSave.txt failed");
		    }
		}

		public void importGame(){
					
	         try{
			FileReader saved_game= new FileReader("Data/save.txt");
			BufferedReader reader = new BufferedReader(saved_game);
			Scanner parser = new Scanner(reader);
			
			int x= parser.nextInt();
			int y= parser.nextInt();
			int mine_count = parser.nextInt();
			ROWS=x;COLUMS=y;TOTAL_MINE=mine_count;
			mine_timer.cancel();
			System.out.println("x="+x+" y="+y);
			
			for(int i=0;i<x;i++){
			   for(int j=0;j<y;j++){
			   	int cell_info= parser.nextInt();
				cells[i][j].set_cell(cell_info);
				System.out.println("cell: "+x+","+y+" has "+cell_info);
			   }
			}
			resetLabels(-1,-1,-1);
		    }catch(IOException e){
		       System.out.println("error in opening save.txt");
		    }
		}

		public void increaseCellCount(){
		    if(cells_count==0)	startTimer();
		    cells_count++;	   
		    cell_revealed_show.setText(String.valueOf(cells_count));
		    if(cells_count==ROWS*COLUMS-TOTAL_MINE){
		    	endGame(true);
		    }
		}

		public void increaseMineCount(){
		       if(mines_count< TOTAL_MINE){
		            mines_count++;
			    mine_left_show.setText(String.valueOf(mines_count));
		       }
		}

		public void reduceMineCount(){
		      if(mines_count>0){
		      	    mines_count--;
			    mine_left_show.setText(String.valueOf(mines_count));
		      }
		}
	
	}
