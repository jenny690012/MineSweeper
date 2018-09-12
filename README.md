# MineSweeper

This is a Java Mine Sweeper Game That I implemented for fun in my spare time.
![minesweeper1](https://user-images.githubusercontent.com/29102031/45423209-7c59e700-b647-11e8-8817-30fbd314edde.png)

You can click on the menu item "Options" of the menu "Game" to change level and the way you want the game to be saved.

![minesweeper3](https://user-images.githubusercontent.com/29102031/45424043-233f8280-b64a-11e8-85f7-c81164b89a7c.PNG)

You can also click on the menu item "Statistics" to view your game record and reset record.
![minesweeper2](https://user-images.githubusercontent.com/29102031/45424210-a95bc900-b64a-11e8-8ba3-5a7bb67acc95.PNG)

##Things to improve:
1. Make Statistics dialog's layout better.
2. Add more details to winning and losing dialog by showing the statistics of the latest played game.
3. Let user to play the game with keyboard.

## Java Version:
   java version "1.8.0_172"
   Java(TM) SE Runtime Environment (build 1.8.0_172-b11) 
   
## Editor:
Vim



## Objective and after thoughts:
   This game was originally my high school final project for grade 11 my computer science course in my last high school semester. My teacher gives us two weeks to complete a game of our choice. It was the first game and first big project I ever wrote. At that time I was so eager to get it done that I set this project as the first priority before everything else and spent a couple of sleepless nights on it. Unfortunately, I was not able to make it functional at the end. Despite of failure, my teacher still gave me a very high mark for my hard work. After my university, I decided to bring this high school failure back to alive. This project probably can be done faster if using MVC design pattern, but I went with my approach of high school anyway just to see will my old idea actually work and how long will it take to make it work.
   The usual MVC approach separates model, view, controller. The model probably has a 2D integer array which is the data structure representing the game board, among with some other variables for bookkeeping. The model uses this 2D array to keep track of all the states of cells grid within the game board and updates it correspondingly when controllers notify model when user did something on the view. In my approach, such a model does not exits. Instead, each cell grid is like an individual responsible for its' own state, it's view and interaction with user. There is a cell manager who is responsible for creating all the cells and displaying everything you see on the main interface, However, cell manager has no knowledge on what is going on within the game. When each cell it is clicked, it update its view and states, then depend on situation it will notify its neighbour cells and cell manager something has happened. This is what I later learned in university, "Observer Pattern". If I gonna judge the high school of me after I implemented the game, I think her idea is cool, but observer pattern is harder to implement, require more memory space and is more difficult to debug. I started mine sweeper project in November 2017 and building it slowly until it reaches the current milestone in September 2018. Sometime I work two weeks straight on the game, sometime I pause it for two months or so. I did not keep track of the time but the total time was not least than 100 hours. The most of the time was spend on learning how to use Java Swing and layout manager to make UI/UX nice, and even until now, I still a little unsatisfied with UI representation. However, This game is working the way I want it to be, and it has more features than the old mine sweeper I planned in high school, So, I think I can say good job to myself and pause this project until I have more time to make it better.


