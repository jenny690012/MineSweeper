import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.util.Scanner;
import java.lang.Math;


class statisticsModel{

   final int MAX_NUM_RECORD=5;
   final int BEGINNER=0;
   final int INTERMEDIATE=1;
   final int ADVANCED=2;
   final String BEGINNER_LEVEL="Data/Beginner.txt";
   final String INTERMEDIATE_LEVEL="Data/Intermediate.txt";
   final String ADVANCED_LEVEL="Data/Advanced.txt";

   int level;
   int games_played;
   int games_won;
   int winning_streak;
   int losing_streak;
   int current_streak;

   int [] times;
   String[] dates;
   int length;
   
   public statisticsModel(int cell_width, int cell_height, int num_mines){
	String level_string;
        if(cell_height==9 && cell_width==9 && num_mines==10){
	        level_string = BEGINNER_LEVEL;
		this.level=BEGINNER;
	}else if (cell_height==16 && cell_width==16 && num_mines==40){
		level_string = INTERMEDIATE_LEVEL;
		this.level=INTERMEDIATE;
	}else {
		level_string = ADVANCED_LEVEL;
		this.level=ADVANCED;
	}
	import_data(level_string);
   }

   public statisticsModel(int level){
   	this.level=level;
	String level_string;

	if(level==BEGINNER) level_string= BEGINNER_LEVEL;
	else if(level==INTERMEDIATE) level_string= INTERMEDIATE_LEVEL;
	else /*if (level==ADVANCED)*/ level_string= ADVANCED_LEVEL;
	import_data(level_string);

   }

   private void import_data(String level_string){
	
	times = new int[MAX_NUM_RECORD];
	dates = new String[MAX_NUM_RECORD];

 	try{
	   if(new File(level_string).isFile()){
	     FileReader data = new FileReader(level_string);
	     BufferedReader reader = new BufferedReader(data);
	     Scanner parser = new Scanner(reader);

	     games_played= parser.nextInt();
	     games_won = parser.nextInt();
	     winning_streak = parser.nextInt();
	     losing_streak = parser.nextInt();
	     current_streak = parser.nextInt();
	     length = parser.nextInt();
		
	     for(int i=0; i<length; i++){
	        times[i] = parser.nextInt();
		dates[i] = parser.next();
	     }
	   }else{
	      games_played=0;
	      games_won=0;
	      winning_streak=0;
	      losing_streak=0;
	      current_streak=0;
	      length=0;
	   }

	}catch(IOException e){
	     System.out.println("error in opening "+level_string);
	}  
   }

   public int get_level(){return level;}
   public int get_games_played(){return games_played;}
   public int get_games_won(){ return games_won;}
   public int get_winning_streak(){return winning_streak;}
   public int get_losing_streak(){return 0-losing_streak;}
   public int get_current_streak(){return current_streak;}
   public  int get_winning_percentage(){
	   double won= (double) games_won;
   	   double played = (double) games_played;

	   if(games_played!=0){
             return  (int) Math.round((won* 100.0f)/ played);
	   }else return 0;
   }
   public int get_best_times_length(){return length;}
   public String[] get_best_times(){
	   String[] result= new String[MAX_NUM_RECORD];
        for(int i=0;i<length;i++){
	   StringBuilder builder = new StringBuilder();
	   String time_str = String.valueOf(times[i]);
	   String padded_time_str = String.format("%-10s", time_str);
	   builder.append(padded_time_str);
	   builder.append(dates[i]);
	   result[i] = builder.toString();
	}
	return result;
   }

   public boolean update_win(int time, String date){

	   games_played++;
	   games_won++;
	   if(current_streak>=0){
		  current_streak++;
	   }else{ current_streak=1;}
	   if(current_streak>winning_streak){
	      winning_streak=current_streak;
	   }

	   int i=0;
	   while(i<length){
	        if(time < times[i]) break;
		else i++;
	   }
  
	   //no new record
	   if(i==length && length==MAX_NUM_RECORD){
		save_data();
	   	return false;
	   }

	   while(i < length){
	         int temp_time = times[i];
		 String temp_date= dates[i];
		 times[i]=time;
		 dates[i]=date;
		 time= temp_time;
		 date= temp_date;
		 i++;
	   }
	   if(length<MAX_NUM_RECORD){
	         times[length]=time;
		 dates[length]=date;
		 length++;
	   }
	   
	   save_data();
	   return true;
   }

   private void save_data(){
	String level_string;
	if(level==BEGINNER) level_string=BEGINNER_LEVEL;
	else if(level==INTERMEDIATE) level_string=INTERMEDIATE_LEVEL;
	else /*if (level==ADVANCED)*/ level_string=ADVANCED_LEVEL;

	try{
	    FileWriter saved_game = new FileWriter(level_string);
	    saved_game.write(Integer.toString(games_played));
	    saved_game.write(String.format("%n"));
	    saved_game.write(Integer.toString(games_won));
	    saved_game.write(String.format("%n"));
	    saved_game.write(Integer.toString(winning_streak));
	    saved_game.write(String.format("%n"));
	    saved_game.write(Integer.toString(losing_streak));
	    saved_game.write(String.format("%n"));
	    saved_game.write(Integer.toString(current_streak));
	    saved_game.write(String.format("%n"));
	    saved_game.write(String.format("%n"));

	    
	      saved_game.write(Integer.toString(length));
	      saved_game.write(String.format("%n"));
	      String[] records = get_best_times();
	      for(int i=0; i<length; i++){

	        saved_game.write(records[i]);
	        saved_game.write(String.format("%n"));
	      }
	    
	    saved_game.close();
	}catch(IOException e){
	    System.out.print("Write to "+level_string+" failed");
	}
   }

   public void reset(){
     games_played=0;
     games_won=0;
     winning_streak=0;
     losing_streak=0;
     current_streak=0;
     length=0;
     save_data();
   }


   public void update_lose(){
   	games_played++;
	if(current_streak<=0){
	   current_streak--;
	}else{
	   current_streak=-1;
	}

	if(current_streak < losing_streak){
	   losing_streak= current_streak;
	}
	save_data();
   }

   /*public static void main (String[] args){
           statisticsModel model = new statisticsModel(0);
	   System.out.println("level="+model.get_level());
	   System.out.println("games played="+model.get_games_played());
	   System.out.println("games won="+model.get_games_won());
	   System.out.println("winning percentage="+model.get_winning_percentage());

	   model.update_lose();
	   model.update_win(103,"14/05/2018");
	   model.update_win(10,"14/05/2018");
	   model.update_win(100,"14/05/2018");
	   model.update_win(10,"14/05/2018");
	   model.update_win(100,"14/05/2018");
	   model.update_win(102,"14/05/2018");
	   model.update_win(102,"14/05/2018");
	   
	   model.save_data();

	   System.out.println("After updates");
	   	   System.out.println("games played="+model.get_games_played());
	   System.out.println("games won="+model.get_games_won());
	   System.out.println("winning percentage="+model.get_winning_percentage());
	   System.out.println("winning streak="+model.get_winning_streak());
	   System.out.println("losing streak="+model.get_losing_streak());
	   System.out.println("current streak="+model.get_current_streak());

   }*/
}
