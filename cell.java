import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.Border;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.util.*;

class Cell extends JLabel implements MouseListener{
		int GRID_SIZE= 25;

		private final int MARK=2;
		private final int UNREVEAL=1;
		private final int REVEAL=3;
		private final String[] image_nums= {"mine1","mine2","mine3",
			"mine4","mine5","mine6","mine7","mine8",
	       		"mine9"
		};
		private final String image_mine = "minemine";
		private final String image_wrong = "minewrong";
		private final String image_revealed = "revealed";

		private final ImageIcon icon_unflag = new ImageIcon("Images/nothing.PNG");
		private final ImageIcon icon_flag = new ImageIcon("Images/mineflag.PNG");
		private final ImageIcon icon_revealed= getImageIcon("revealed");

		private ImageIcon getImageIcon(String url){
		     return new ImageIcon("Images/"+url+".PNG");
		}

		boolean mine;
		int status;
 		ArrayList<Cell> neighbours;
		CellManager manager;

		public Cell(CellManager manager){
		
			mine=false;
			this.manager = manager;
			status=UNREVEAL;
			neighbours = new ArrayList<Cell>();
			setForeground(Color.white);
			Border border = BorderFactory.createLineBorder(Color.black,1);
			setBorder(border);
			setSize(new Dimension(GRID_SIZE,GRID_SIZE));
		}

		public void setMine(){mine=true;}
		public void removeMine(){mine=false;}
		public boolean hasMine(){return mine;}
		public int returnStatus(){return status;}
		public void addNeighbour(Cell c){
			neighbours.add(c);
		}
		public void endGame(boolean win){
			if(!win){
		     	  if(mine && status!=MARK){
			    setIcon(getImageIcon(image_mine));

			  }else if( !mine && status==MARK){
			    setIcon(getImageIcon(image_wrong));
			  }
		    	}

			if(status!=REVEAL){
			    removeMouseListener(this);
			}
			status=REVEAL;
		}

		public void startGame(){
				setIcon(icon_unflag);
		  		status=UNREVEAL;
			        addMouseListener(this);	
		}

		public void resumeGame(){
		      
		      if(status==REVEAL){
				int mine_count= countMineSurrounding();
				if(mine_count!=0){
			    	  setIcon(getImageIcon(image_nums[mine_count-1]));
				}else{
			    	  setIcon(getImageIcon(image_revealed));
				}

		      }else {
			 addMouseListener(this);      
		         if(status==MARK) setIcon(icon_flag);
			 else setIcon(icon_unflag);
		      }
		}

		public void pauseGame(){
		      removeMouseListener(this);
		      setIcon(icon_revealed);
		}

		private void reveal(){

			int mine_count=0;
			if(!mine && status==UNREVEAL){
			    status=REVEAL;
			    removeMouseListener(this);
			    mine_count= countMineSurrounding();
			    if(mine_count!=0){
			       setIcon(getImageIcon(image_nums[mine_count-1]));
			    }
			    else{
			   	  setIcon(getImageIcon(image_revealed));
			   	  for(int i=0; i<neighbours.size();i++){
			            if(neighbours.get(i).returnStatus()==UNREVEAL){
						neighbours.get(i).reveal();
					}
			   	  }
		 	   }
			}
			manager.increaseCellCount();
		}

		private int countMineSurrounding(){
		     int total_mine=0;
		     for(int i=0; i<neighbours.size(); i++){
		          if(neighbours.get(i).hasMine()){
			  	total_mine++;
			  }
		     }
		     return total_mine;
		}

		public String output_cell(){
			String hasMine;
			if(mine){hasMine="1";}
			else{ hasMine="0";}

			int numMine= countMineSurrounding();

			return Integer.toString(status)+numMine+hasMine+" "; 
			
		}

		public void set_cell(int cell_info){
		  int hasMine= cell_info%2;
		  status= cell_info/100;
		  int numMine = (cell_info-status*100)/10;

		  if(hasMine==1){setMine();}
		  else {removeMine();}

		  if(status==MARK){ flag_cell();}
		  else if(status==UNREVEAL){unflag_cell();}
		  else{
		     removeMouseListener(this);
		     if(numMine!=0){
		       setIcon(getImageIcon(image_nums[numMine-1]));
		     }else{
		       setIcon(getImageIcon(image_revealed));
		     }
		  }
		}

		private void flag_cell(){
			setIcon(icon_flag);
			status=MARK;
			manager.reduceMineCount();
		
		}
		private void unflag_cell(){
		
			setIcon(icon_unflag);
			status=UNREVEAL;
			manager.increaseMineCount();		 
		} 

		public void mouseClicked(MouseEvent evt){}
		public void mouseExited(MouseEvent evt){}
		public void mouseEntered(MouseEvent evt){}
		public void mousePressed(MouseEvent evt){
		
			System.out.println("a mouse click is received");

			if(SwingUtilities.isRightMouseButton(evt)){
				System.out.println("a right click, status="+status);
		              if(status==MARK){
				      unflag_cell();
			      }else if(status==UNREVEAL){
				      flag_cell();
			      }
			}else{
				System.out.println("a left click, status="+status);
			     if(status==UNREVEAL){
				if(mine){manager.endGame(false);}
				else{ reveal();}
			      }
			}
		}

		public void mouseReleased(MouseEvent evt){}
	}//end of Cel
