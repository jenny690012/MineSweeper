import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.Border;
import javax.swing.BoxLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.lang.Math;

class statisticsFrame{

	final int X_SIZE=500;
	final int Y_SIZE=300;
        final int MAX_BEST_TIME=5;	
	final String GAMES_PLAYED= "Games Played: ";
	final String GAMES_WON = "Games Won: ";
	final String WIN_PERCENTAGE = "Win Percentage: ";
	final String WINNING_STREAK = "Longest Winning Streak: ";
	final String LOSING_STREAK = "Longest Losing Streak: ";
	final String CURRENT_STREAK = "Current Streak: ";
	final Border empty = BorderFactory.createEmptyBorder(10,0,0,0);

	JLabel games_played, games_won,win_percentage,winning_streak,
	       losing_streak,current_streak;
	JLabel[] best_times;
	statisticsModel model;
	MineSweeper ms;
	int best_times_length;
	JList<String> level_list;

	private void setLabels(){

	    games_played.setText(GAMES_PLAYED+
			    model.get_games_played());
	    games_won.setText(GAMES_WON+
			    model.get_games_won());
	    win_percentage.setText(WIN_PERCENTAGE+
			    model.get_winning_percentage());
	    winning_streak.setText(WINNING_STREAK+
			    model.get_winning_streak());
	    losing_streak.setText(LOSING_STREAK+
			    model.get_losing_streak());
	    current_streak.setText(CURRENT_STREAK+
			    model.get_current_streak());

	    best_times_length = model.get_best_times_length();
	    String[] best_times_string = model.get_best_times();
	    for(int i=0; i<best_times_length;i++){
	    	best_times[i].setText(best_times_string[i]);
	    }
	    for(int i=best_times_length; i<MAX_BEST_TIME;i++){
	    	best_times[i].setText("               ");
	    }
	}


	public statisticsFrame(statisticsModel model, MineSweeper ms){
		this.model = model;
		this.ms=ms;
		games_played = new JLabel();
		games_won = new JLabel();
		win_percentage = new JLabel();
		winning_streak = new JLabel();
		losing_streak = new JLabel();
		current_streak = new JLabel();
		best_times = new JLabel[MAX_BEST_TIME];
		for(int i=0; i<MAX_BEST_TIME;i++){
		   best_times[i] = new JLabel();
		}
		best_times_length=0;
		DefaultListModel<String> level_string = new DefaultListModel<>();
		level_string.addElement("Beginner");
		level_string.addElement("Intermediate");
		level_string.addElement("Advanced");
		level_list = new JList<>(level_string);
		level_list.setSelectedIndex(model.get_level());
		setLabels();
		statisticsFrameGUI();
		
	}


	private void statisticsFrameGUI(){
	
	JDialog frame = new JDialog(null,"Statistics",Dialog.ModalityType.DOCUMENT_MODAL);
	frame.setSize(X_SIZE,Y_SIZE);
	frame.setLayout(new GridBagLayout());


	level_list.setFixedCellHeight(30);
	JScrollPane list_pane = new JScrollPane(level_list);
	level_list.setPreferredSize(new Dimension(100,150));
	level_list.addListSelectionListener(new ListSelectionListener(){
		public void valueChanged(ListSelectionEvent e){
		   if(!e.getValueIsAdjusting()){
		      int level = level_list.getSelectedIndex();
		      model = new statisticsModel(level);
		      setLabels();
		   }
		}
	});

	GridBagConstraints panel_constraint = new GridBagConstraints();
	panel_constraint.gridx=1;
	panel_constraint.gridy=1;
	panel_constraint.gridheight=1;
	panel_constraint.gridwidth =1;
	panel_constraint.weightx=0.5;
	panel_constraint.weighty=0.7;
	panel_constraint.anchor= GridBagConstraints.LINE_START;
	panel_constraint.fill= GridBagConstraints.BOTH;
	panel_constraint.insets = new Insets(5,5,5,5);

	frame.add(level_list, new GridBagConstraints(1,1,1,1,0.4,0.1,
		 		GridBagConstraints.LINE_START,
				GridBagConstraints.BOTH,
				new Insets(0,0,0,0),X_SIZE/20,20));

	//----------------------for time section
	JPanel best_time_panel = new JPanel();
	best_time_panel.setLayout(new BoxLayout(best_time_panel, BoxLayout.Y_AXIS));
	best_time_panel.setSize(300,150);
	for(int i=0;i<MAX_BEST_TIME;i++){
	  best_time_panel.add(best_times[i]);
	}
	panel_constraint.gridx=2;
	panel_constraint.gridy=1;
	panel_constraint.gridheight=2;
	TitledBorder topBorder = BorderFactory.createTitledBorder("Best Time");
    	topBorder.setTitlePosition(TitledBorder.TOP);
	best_time_panel.setBorder(topBorder);
	frame.add(best_time_panel,panel_constraint);

	//best_time_panel.add(date);
	/*frame.add(best_time_panel, new GridBagConstraints(1,2,2,3,0.4,0.1,
		 		GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				new Insets(0,0,0,0),X_SIZE/20,20));*/


	//------------for record section
	JPanel record_panel = new JPanel();
	record_panel.setLayout(new BoxLayout(record_panel, BoxLayout.Y_AXIS));
	record_panel.setSize(400,80);
	panel_constraint.gridx=3;
	panel_constraint.gridy=1;
	frame.add(record_panel,panel_constraint);

	record_panel.add(games_played);
	games_played.setBorder( BorderFactory.createEmptyBorder(30,15,0,0));

	record_panel.add(games_won);
	Border spacing = BorderFactory.createEmptyBorder(10,15,0,0);
	games_won.setBorder(spacing);

	record_panel.add(win_percentage);
	win_percentage.setBorder(spacing);

	record_panel.add(winning_streak);
	winning_streak.setBorder(spacing);

	record_panel.add(losing_streak);
	losing_streak.setBorder(spacing);

	record_panel.add(current_streak);
	current_streak.setBorder(spacing);
	/*frame.add(record_panel, new GridBagConstraints(1,3,2,3,0.4,0.1,
		 		GridBagConstraints.LINE_END,
				GridBagConstraints.BOTH,
				new Insets(0,0,0,0),X_SIZE/20,20));*/


	JPanel buttonPanel = new JPanel();
	JButton close = new JButton("Close");
	close.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
			// ms.resumeFromPause();		 
			 frame.dispose();
		     }});
	frame.add(close, new GridBagConstraints(2,3,1,1,0.4,0.3,
		 		GridBagConstraints.LAST_LINE_START,
				GridBagConstraints.HORIZONTAL,
				new Insets(0,0,0,0),X_SIZE/50,5));

			
	JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
			 model.reset();
			 setLabels();
		     }});
	frame.add(reset, new GridBagConstraints(3,3,1,1,0.4,0.3,
				GridBagConstraints.LAST_LINE_END,
				GridBagConstraints.HORIZONTAL,
				new Insets(0,0,0,10),X_SIZE/50,5));

	
	  //frame.setResizable(false);
	  frame.setVisible(true);
	}

}
