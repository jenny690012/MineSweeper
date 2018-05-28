import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.Border;
import javax.swing.SpringLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.util.Scanner;


class optionFrame implements ActionListener{

	final String BEGINNER= "<html>Beginnner<br>10 mines<br>9x9 grids</html>";
	final String INTERMEDIATE = "<html>Intermediate<br>40 mines<br>16x16 grids</html>";
	final String ADVANCED = "<html>Advanced<br>99 mines<br>16x30 grids</html>";
	final String CUSTOMED = "Custom:";
	final String HEIGHT_LABEL="Height (9-24): ";
	final String WIDTH_LABEL="Width (9-30): ";
	final String MINE_LABEL="Mines (10-668): ";
	final String CONTINUE_SAVE_LABEL="Always continue on saved game";
	final String SAVE_EXIT_LABEL="Always save game on exit";
	final String APPLY="APPLY";
	final String CANCEL= "CANCEL";
	final int MIN_HEIGHT=9;
	final int MAX_HEIGHT= 24;
	final int MAX_WIDTH= 30;
	final int MIN_MINE = 10;
	final int X_SIZE=400;
	final int Y_SIZE=400;

	JRadioButton beginnerButn;
	JRadioButton intermediateButn;
        JRadioButton advancedButn;
	JRadioButton customedButn;
	JCheckBox continueSaved;
	JCheckBox savedExits;	
	int num_mines;
	int cell_height;
	int cell_width;
	boolean continue_save;
	boolean save_exit;

	JTextField mine_textfield;
	JTextField height_textfield;
	JTextField width_textfield;
	int customed_num_mines;
	int customed_cell_height;
	int customed_cell_width;

	JButton OK;
	JButton cancel;
	JDialog frame;
	MineSweeper ms;


   public optionFrame(MineSweeper ms){
	
	this.ms=ms;
	File options = new File("options.txt");
	if(options.exists() && !options.isDirectory()){
	  try{
		FileReader option = new FileReader(options);
		BufferedReader reader = new BufferedReader(option);
		Scanner parser = new Scanner(reader);

		cell_height = parser.nextInt();
		cell_width = parser.nextInt();
		num_mines = parser.nextInt();
		continue_save = parser.nextBoolean();
		save_exit= parser.nextBoolean();
		customed_cell_height = parser.nextInt();
		customed_cell_width = parser.nextInt();
		customed_num_mines = parser.nextInt();
	    }catch(IOException e){}
	 }else{	    	
		cell_height = ms.ROWS;
		cell_width = ms.COLUMS;
		num_mines =  ms.TOTAL_MINE;
		continue_save = ms.continue_on_saved_game;
		save_exit = ms.save_on_exiting_game;
		customed_cell_height=0;
		customed_cell_width=0;
		customed_num_mines=0;
	 }


	frame = new JDialog(null,"Options",Dialog.ModalityType.DOCUMENT_MODAL);
	frame.setSize(X_SIZE,Y_SIZE);

	beginnerButn= new JRadioButton(BEGINNER);
	beginnerButn.setActionCommand(BEGINNER);
	beginnerButn.addActionListener(this);

	intermediateButn = new JRadioButton(INTERMEDIATE);
	intermediateButn.setActionCommand(INTERMEDIATE);
	intermediateButn.addActionListener(this);

	advancedButn = new JRadioButton(ADVANCED);
	advancedButn.setActionCommand(ADVANCED);
	advancedButn.addActionListener(this);

	customedButn = new JRadioButton(CUSTOMED);
	customedButn.setActionCommand(CUSTOMED);
	customedButn.addActionListener(this);

	JPanel customfields = new JPanel(new GridLayout(3,2));
			
	JLabel width_label = new JLabel(WIDTH_LABEL);
	width_textfield = new JTextField(2);
	width_textfield.setActionCommand(WIDTH_LABEL);
	width_textfield.addKeyListener(new DigitChecker(width_textfield));
	width_textfield.addActionListener(this);
	width_label.setLabelFor(width_textfield);


	JLabel height_label = new JLabel(HEIGHT_LABEL);
	height_textfield = new JTextField(2);
	height_textfield.setActionCommand(HEIGHT_LABEL);
	height_textfield.addKeyListener(new DigitChecker(height_textfield));
	height_label.setLabelFor(height_textfield);
	height_textfield.addActionListener(this);


	JLabel mine_label = new JLabel(MINE_LABEL);
	mine_textfield = new JTextField(2);
	mine_textfield.setActionCommand(MINE_LABEL);
	mine_textfield.addKeyListener(new DigitChecker(mine_textfield));
	mine_textfield.addActionListener(this);
	mine_label.setLabelFor(mine_textfield);


	customfields.add(height_label);
	customfields.add(height_textfield);
	customfields.add(width_label);
	customfields.add(width_textfield);
	customfields.add(mine_label);
	customfields.add(mine_textfield);

	ButtonGroup g = new ButtonGroup();
	g.add(beginnerButn);
	g.add(intermediateButn);
	g.add(advancedButn);
	g.add(customedButn);

		
	JPanel difficulty= new JPanel();
	difficulty.setLayout(new GridBagLayout());
			
	Border border = BorderFactory.createLineBorder(Color.gray,1);
	
	GridBagConstraints radioBnConstr = new GridBagConstraints();
	radioBnConstr.gridx=0;
	radioBnConstr.gridy=0;
	radioBnConstr.weightx=0.4;
	radioBnConstr.insets= new Insets(5,5,0,0);
	radioBnConstr.anchor=GridBagConstraints.LINE_START;
	beginnerButn.setBorder(border);
	difficulty.add(beginnerButn,radioBnConstr);
	difficulty.setBorder(border);

	radioBnConstr.gridy=1;
	difficulty.add(intermediateButn,radioBnConstr);
		
	radioBnConstr.gridy=2;
	difficulty.add(advancedButn,radioBnConstr);

	radioBnConstr.gridx=2;
	radioBnConstr.gridy=0;
	radioBnConstr.weightx=0.6;
			
	difficulty.add(customedButn,radioBnConstr);

	radioBnConstr.gridy=1;
	radioBnConstr.insets= new Insets(0,30,0,0);
	difficulty.add(customfields,radioBnConstr);
			
	JPanel setting = new JPanel();
	setting.setBorder(border);
	setting.setLayout(new BoxLayout(setting,BoxLayout.Y_AXIS));
	continueSaved =new JCheckBox(CONTINUE_SAVE_LABEL,continue_save);
	continueSaved.addActionListener(this);
	savedExits= new JCheckBox(SAVE_EXIT_LABEL,save_exit);
	savedExits.addActionListener(this);


	setting.add(continueSaved);
	setting.add(savedExits);
	frame.setLayout(new GridBagLayout());
			
	int weightx= GridBagConstraints.REMAINDER;
	frame.add(difficulty, new GridBagConstraints(0,0,weightx,1,1.0,0.48,
					GridBagConstraints.CENTER,
					GridBagConstraints.BOTH,
					new Insets(10,10,0,10),X_SIZE,(Y_SIZE/2)-50));
			
	frame.add(setting, new GridBagConstraints(0,1,weightx,1,1.0,0.48,
					GridBagConstraints.CENTER,
					GridBagConstraints.BOTH,
					new Insets(5,10,10,10),X_SIZE,(Y_SIZE/2)-50));
	JButton OK = new JButton(APPLY);
	OK.addActionListener(this);
	frame.add(OK, new GridBagConstraints(1,2,1,1,0.4,0.1,
					GridBagConstraints.LAST_LINE_END,
					GridBagConstraints.HORIZONTAL,
					new Insets(0,0,0,0),X_SIZE/20,20));


			
	cancel = new JButton(CANCEL);
	cancel.addActionListener(this);
	frame.add(cancel, new GridBagConstraints(2,2,1,1,0.4,0.04,
					GridBagConstraints.LAST_LINE_END,
					GridBagConstraints.HORIZONTAL,
					new Insets(0,0,0,10),X_SIZE/20,20));



	setTextFieldsUnfocus();
	height_textfield.setText(Integer.toString(customed_cell_height));
	width_textfield.setText(Integer.toString(customed_cell_width));
	mine_textfield.setText(Integer.toString(customed_num_mines));
	if(cell_height==9 && cell_width==9 && num_mines==10){
	        beginnerButn.setSelected(true);
	}else if (cell_height==16 && cell_width==16 && num_mines==40){
		intermediateButn.setSelected(true);
	}else if (cell_height==16 && cell_width==30 && num_mines==99){
		advancedButn.setSelected(true);
	}else{
		customedButn.setSelected(true);
		setTextFieldsFocus();
	}

	frame.addWindowListener(new java.awt.event.WindowAdapter() {
   	 public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		ms.resumeFromPause();
		frame.dispose();
    	 }});
	frame.setResizable(false);
	frame.setVisible(true);			
   }


   public void actionPerformed(ActionEvent e){

	if(customedButn.isSelected()){ setTextFieldsFocus();}
	else setTextFieldsUnfocus();

	  
          String command = e.getActionCommand();
	  System.out.println("command="+command);
	  if(command.equals(HEIGHT_LABEL)){
	       checkTextfields(true,false,true);
	  }else if(command.equals(WIDTH_LABEL)){
	       checkTextfields(true,true,false);
	  }else if(command.equals(MINE_LABEL)){
		checkTextfields(false,true,true);
	  }else if(command.equals(BEGINNER)){
	  	selectButton( beginnerButn);
	  }else if(command.equals(INTERMEDIATE)){
	  	selectButton( intermediateButn);
	  }else if(command.equals(ADVANCED)){
	  	selectButton( advancedButn);
	  }else if(command.equals(CONTINUE_SAVE_LABEL)){
	  	selectButton(continueSaved);
	  }else if(command.equals(SAVE_EXIT_LABEL)){
	  	selectButton(savedExits);
	  }else if(command.equals(APPLY)){
	  	clickOnApplyButn();
	  }else if(command.equals(CANCEL)){
		ms.resumeFromPause();
		frame.dispose();
	  }
   }

   private void selectButton(JToggleButton bn){
	boolean all_true=checkTextfields(true,true,true);

	if(!(all_true)){
	   if(bn.isSelected()) bn.setSelected(false);
	   else bn.setSelected(true);
	}
   }

   private void setTextFieldsFocus(){
	   height_textfield.setFocusable(true);
	   width_textfield.setFocusable(true);
	   mine_textfield.setFocusable(true);

	   height_textfield.setBackground(Color.WHITE);
	   width_textfield.setBackground(Color.WHITE);
	   mine_textfield.setBackground(Color.WHITE);


   
   }

   private void setTextFieldsUnfocus(){
   	   height_textfield.setFocusable(false);
	   width_textfield.setFocusable(false);
	   mine_textfield.setFocusable(false);

	   height_textfield.setBackground(Color.lightGray);
	   width_textfield.setBackground(Color.lightGray);
	   mine_textfield.setBackground(Color.lightGray);	   
   }

   private void clickOnApplyButn(){
   	int new_cell_height,new_cell_width,new_num_mines;
	boolean textfields_correct = checkTextfields(true,true,true);

		  if(customedButn.isSelected()&& textfields_correct){

			  new_cell_height= customed_cell_height;
	      		  new_cell_width = customed_cell_width;			
			  new_num_mines= customed_num_mines;

		  }else if(beginnerButn.isSelected()){
		  	   new_num_mines=10;
	     		   new_cell_height=9;
	     		   new_cell_width=9;	 
		  }else if(intermediateButn.isSelected()){
		  	   new_num_mines=40;
	     		   new_cell_height=16;
	     		   new_cell_width=16;	 
		  }else{
	     		   new_num_mines=99;
	     		   new_cell_height=16;
	     		   new_cell_width=30;		  
		  }

		  if(textfields_correct){
			
		   try{ 
   			FileWriter options = new FileWriter("options.txt");
			options.write(Integer.toString(new_cell_height));
			options.write(String.format("%n"));
			options.write(Integer.toString(new_cell_width));
			options.write(String.format("%n"));
			options.write(Integer.toString(new_num_mines));
			options.write(String.format("%n"));
			options.write(Boolean.toString(continueSaved.isSelected()));
			options.write(String.format("%n"));
			options.write(Boolean.toString(savedExits.isSelected()));
			options.write(String.format("%n"));
			options.write(Integer.toString(customed_cell_height));
			options.write(String.format("%n"));
			options.write(Integer.toString(customed_cell_width));
			options.write(String.format("%n"));
			options.write(Integer.toString(customed_num_mines));
			options.close();
		  }catch(IOException e){
	 			System.out.print("Write to lastSave.txt failed");
		  }
	 
		  	ms.continue_on_saved_game = continueSaved.isSelected();
		  	ms.save_on_exiting_game = savedExits.isSelected();

			if (!(new_num_mines == num_mines && 
			   new_cell_height== cell_height && 
			   new_cell_width== cell_width)){
			      ms.change_grid_option(new_cell_height,
					      new_cell_width,
					      new_num_mines);
		        }else{ms.resumeFromPause();}
			frame.dispose();
	}
   }

   private boolean checkTextfields(boolean mine, boolean height, boolean width){
	boolean mine_check= true;
	boolean height_check= true;
	boolean width_check= true;

	if(height) height_check= heightParseInt();
	if(width) width_check= widthParseInt();
	if(mine) mine_check= mineParseInt();

	System.out.println("mine_check="+mine_check);
	System.out.println("height_check="+height_check);
	System.out.println("width_check="+width_check);
	return mine_check&&height_check&&width_check;

   }


   private boolean heightParseInt(){
   
	   try{
	      customed_cell_height = Integer.parseInt(height_textfield.getText());
	   }catch(NumberFormatException e){
		  customed_cell_height=0;
	   }
  
	      if(customed_cell_height > MAX_HEIGHT){
	        JOptionPane.showMessageDialog(frame,
			"height value must be less than "+MAX_HEIGHT,
			"Out Of Range",
				   JOptionPane.ERROR_MESSAGE);
		height_textfield.setText(Integer.toString(MAX_HEIGHT));
		customed_cell_height=MAX_HEIGHT;
		return false;
		} else if(customed_cell_height < MIN_HEIGHT){
	        JOptionPane.showMessageDialog(frame,
			"height value must be greater than "+MIN_HEIGHT,
			"Out Of Range",
				   JOptionPane.ERROR_MESSAGE);
		height_textfield.setText(Integer.toString(MIN_HEIGHT));
		customed_cell_height=MIN_HEIGHT;
		return false;
	      }
 
	   return true;
   }

      private boolean widthParseInt(){
   
	   try{
	      customed_cell_width = Integer.parseInt(width_textfield.getText());
	   }catch(NumberFormatException e){
		customed_cell_width=0;
	   }
	      if(customed_cell_width > MAX_WIDTH){
	        JOptionPane.showMessageDialog(frame,
			"width value must be less than "+MAX_WIDTH,
			"Out Of Range", JOptionPane.ERROR_MESSAGE);
		width_textfield.setText(Integer.toString(MAX_WIDTH));
		customed_cell_width=MAX_WIDTH;
		return false;
		} else if(customed_cell_width < MIN_HEIGHT){
	        JOptionPane.showMessageDialog(frame,
			"width value must be greater than "+MIN_HEIGHT,
			"Out Of Range", JOptionPane.ERROR_MESSAGE);
		width_textfield.setText(Integer.toString(MIN_HEIGHT));
		customed_cell_width=MIN_HEIGHT;
		return false;
	      }

   
	   return true;
   }


   private boolean mineParseInt(){


	   try{
	      customed_num_mines = Integer.parseInt(mine_textfield.getText());
	   }catch(NumberFormatException e){
		num_mines=0;
	   }

	      int max_mines = (customed_cell_height * customed_cell_width *4)/5 ;
	      if( customed_num_mines < MIN_MINE||customed_num_mines > max_mines){
	      	JOptionPane.showMessageDialog(frame,
			"value of mines must be an number between[10-"
			+max_mines+"] based on current height and cell width.",
				   "Out of Range",JOptionPane.ERROR_MESSAGE);
	      	if(customed_num_mines > max_mines){
	           mine_textfield.setText(Integer.toString(max_mines));
		   customed_num_mines=max_mines;
	      	}else{
	           mine_textfield.setText(Integer.toString(MIN_MINE));
	           customed_num_mines=MIN_MINE;		
		}
		return false;
	      }

	   return true;
   }


	private class DigitChecker implements KeyListener{
		JTextField tx;
		String old;
		public DigitChecker(JTextField tx){
		    this.tx=tx;
		}
   		public void keyTyped(KeyEvent e) {
			old= tx.getText();
		}
    		public void keyReleased(KeyEvent e) {
			int keycode= e.getKeyCode();
			System.out.println("keycode="+keycode);
			if((keycode>=65 && keycode<= 93) || 
				(keycode<=111 && keycode>=106)){
				tx.setText(old);
			       JOptionPane.showMessageDialog(frame,
			"Must be numerical characters from 0-1",
			"Unacceptable Character",
			JOptionPane.ERROR_MESSAGE);
			}
		}
		public void keyPressed(KeyEvent e) {
			int keycode= e.getKeyCode();
			if((keycode>=65 && keycode<= 93) || 
				(keycode<=111 && keycode>=106)){
				tx.setText(old);
			}
		}

	}
}
