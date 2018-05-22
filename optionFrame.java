import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.Border;
import javax.swing.SpringLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.util.*;



class optionFrame implements ActionListener{

	final String BEGINNER= "<html>Beginnner<br>10 mines<br>9x9 grids</html>";
	final String INTERMEDIATE = "<html>Intermediate<br>40 mines<br>16x16 grids</html>";
	final String ADVANCED = "<html>Advanced<br>99 mines<br>16x30 grids</html>";
	final String CUSTOMED = "Customed:";
	final int MIN_HEIGHT=9;
	final int MAX_HEIGHT= 24;
	final int MAX_WIDTH= 30;
	final int MIN_MINE = 10;
	final int X_SIZE=400;
	final int Y_SIZE=400;

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


   public optionFrame(MineSweeper ms){
	
	cell_height= ms.ROWS;
	cell_width= ms.COLUMS;
	num_mines= ms.TOTAL_MINE;
	customed_cell_height= 20;
	customed_cell_width=20;
	customed_num_mines=80;

	JDialog frame = new JDialog(ms.frame,"Options",Dialog.ModalityType.DOCUMENT_MODAL);
	frame.setSize(X_SIZE,Y_SIZE);

	JRadioButton beginnerButn= new JRadioButton(BEGINNER);
	beginnerButn.setActionCommand(BEGINNER);
	beginnerButn.addActionListener(this);


	JRadioButton intermediateButn = new JRadioButton(INTERMEDIATE);
	intermediateButn.setActionCommand(INTERMEDIATE);
	intermediateButn.addActionListener(this);

	JRadioButton advancedButn = new JRadioButton(ADVANCED);
	advancedButn.setActionCommand(ADVANCED);
	advancedButn.addActionListener(this);

	JRadioButton customedButn = new JRadioButton(CUSTOMED);
	customedButn.setActionCommand(CUSTOMED);
	customedButn.addActionListener(this);

	JPanel customfields = new JPanel(new GridLayout(3,2));


			
	JLabel width_label = new JLabel("Width (9-30): ");
	width_textfield = new JTextField(2);
	width_label.setLabelFor(width_textfield);

	JLabel height_label = new JLabel("Height (9-24): ");
	height_textfield = new JTextField(2);
	height_label.setLabelFor(height_textfield);

	JLabel mine_label = new JLabel("Mines (10-668): ");
	mine_textfield = new JTextField(2);
	mine_label.setLabelFor(mine_textfield);

	width_textfield.addFocusListener(new FocusListener() {
      		public void focusGained(FocusEvent e) {}
      		public void focusLost(FocusEvent e) {
			customed_cell_width= parseInt(width_textfield,frame, 
				   MIN_HEIGHT,MAX_WIDTH,
				   customed_cell_width);}});

	height_textfield.addFocusListener(new FocusListener() {
      		public void focusGained(FocusEvent e) {}
      		public void focusLost(FocusEvent e) {
       			customed_cell_height= parseInt(height_textfield,frame, 
				MIN_HEIGHT,MAX_HEIGHT,
				customed_cell_height);}});

	mine_textfield.addFocusListener(new FocusListener() {
      		public void focusGained(FocusEvent e) {}
      		public void focusLost(FocusEvent e) {
       			mineParseInt(frame);}});

	customfields.add(height_label);
	customfields.add(height_textfield);
	customfields.add(width_label);
	customfields.add(width_textfield);
	customfields.add(mine_label);
	customfields.add(mine_textfield);
	setTextFieldsUnfocus();


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
	JCheckBox continueSaved =new JCheckBox("Always continue on saved game",
						ms.continue_on_saved_game);
	continueSaved.addItemListener(new ItemListener(){
		public void itemStateChanged(ItemEvent e) {
        	 // ms.continue_on_saved_game= continueSaved.isSelected();
         }});

	JCheckBox savedExits= new JCheckBox("Always save game on exit", 
			                      ms.save_on_exiting_game);
	savedExits.addItemListener(new ItemListener(){
		public void itemStateChanged(ItemEvent e) {
        	 // ms.save_on_exiting_game= savedExits.isSelected();
         }});

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
	JButton OK = new JButton("Apply");
	frame.add(OK, new GridBagConstraints(1,2,1,1,0.4,0.1,
					GridBagConstraints.LAST_LINE_END,
					GridBagConstraints.HORIZONTAL,
					new Insets(0,0,0,0),X_SIZE/20,20));

	OK.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){

		  int new_cell_height,new_cell_width,new_num_mines;
		  if(customedButn.isSelected()){

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

		  System.out.println("new_cell_height="+new_cell_height);	  
		  System.out.println("new_cell_width="+new_cell_width);
		  System.out.println("new_num_mines="+new_num_mines);

		  if(!(new_cell_height==0 || new_cell_width==0 || new_num_mines==0)){
		  	ms.continue_on_saved_game = continueSaved.isSelected();
		  	ms.save_on_exiting_game = savedExits.isSelected();

			if(!(new_num_mines == num_mines && new_cell_height== cell_height
			   && new_cell_width== cell_width)){
			      ms.change_grid_option(new_cell_height, new_cell_width,
					 new_num_mines);
		        }else{ms.resumeFromPause();}
			frame.dispose();	  
		  }
		}});


			
	JButton Cancel = new JButton("Cancel");
	Cancel.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
		 ms.resumeFromPause();
		 frame.dispose();
		}});
			
	frame.add(Cancel, new GridBagConstraints(2,2,1,1,0.4,0.04,
					GridBagConstraints.LAST_LINE_END,
					GridBagConstraints.HORIZONTAL,
					new Insets(0,0,0,10),X_SIZE/20,20));




	mine_textfield.addFocusListener(new FocusListener() {
      			public void focusGained(FocusEvent e) {}
      			public void focusLost(FocusEvent e) {
			  /* mineParseInt(mine_textfield, height_textfield,
				  	width_textfield, frame);*/}});


	setTextFieldsUnfocus();
	if(cell_height==9 && cell_width==9 && num_mines==10){
	        beginnerButn.setSelected(true);	   
	}else if (cell_height==16 && cell_width==16 && num_mines==40){
		intermediateButn.setSelected(true);
	}else if (cell_height==16 && cell_width==30 && num_mines==99){
		advancedButn.setSelected(true);
	}else{
		customedButn.setSelected(true);
		setTextFieldsFocus();
		height_textfield.setText(Integer.toString(cell_height));
	   	width_textfield.setText(Integer.toString(cell_width));;
	   	mine_textfield.setText(Integer.toString(num_mines));;
	}
	System.out.println("cell_height="+cell_height);
	System.out.println("cell_width="+cell_width);
	System.out.println("num_mines="+num_mines);

	frame.setResizable(false);
	frame.setVisible(true);
	frame.addWindowListener(new java.awt.event.WindowAdapter() {
    
   	 public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		ms.resumeFromPause();
		frame.dispose();
    	 }});
			
   }

   public void showOptionFrame(){

   
   }

   public void actionPerformed(ActionEvent e){
	  setTextFieldsUnfocus();
          String command = e.getActionCommand();
	  if(command.equals(CUSTOMED)){
	     setTextFieldsFocus();
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


   private int parseInt(JTextField t,JDialog f, int min, int max, int ori){

	   int result;
	   try{
	      result = Integer.parseInt(t.getText());
	      if(result > max){
	        JOptionPane.showMessageDialog(f,
			"value must be less than "+max,"Out Of Range",
				   JOptionPane.ERROR_MESSAGE);
		t.setText(Integer.toString(max));
		result=max;
		}else if(result < min){
	        JOptionPane.showMessageDialog(f,
			"value must be greater than "+min,"Out Of Range",
				   JOptionPane.ERROR_MESSAGE);
		t.setText(Integer.toString(min));
		result=min;
	      }
	   }catch(NumberFormatException e){
	       JOptionPane.showMessageDialog(f,
		"Must be numerical characters from 0-1","Unacceptable Character",
				   JOptionPane.ERROR_MESSAGE);
	       t.setText(Integer.toString(ori)); result=ori;
	   }
   
	   return result;
   }


   private int mineParseInt(JDialog f){

	   int result;
	   try{
	      result = Integer.parseInt(mine_textfield.getText());

	      int max_mines = (customed_cell_height * customed_cell_width *4)/5 ;
	      if( result < MIN_MINE || result > max_mines){
	      	JOptionPane.showMessageDialog(f,
			"value of mines must be an number between[10-"
			+max_mines+"] based on current height and cell width.",
				   "Out of Range",JOptionPane.ERROR_MESSAGE);
	      	if(result > max_mines){
	           mine_textfield.setText(Integer.toString(max_mines));
	           result=max_mines;
	      	}else{
	           mine_textfield.setText(Integer.toString(MIN_MINE));
	           result=MIN_MINE;		
		}
	      }
	   }catch(NumberFormatException e){
	       JOptionPane.showMessageDialog(f,
		"Must be numerical characters from 0-1","Unacceptable Character",
				   JOptionPane.ERROR_MESSAGE);
	       mine_textfield.setText(Integer.toString(num_mines));
	       result=customed_num_mines;
	   }
   
	   return result;
   }
}
