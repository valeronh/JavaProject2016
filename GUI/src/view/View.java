package view;

import algorithms.mazeGenarators.Maze3d;
import presenter.Properties;

public interface View {
	/**
	 * display method displays string 
	 * @param msg is the string to display
	 */
	void display(String msg);
	
	
	/**
	 * start method starts the view
	 */
	void start();	
	
	/**
	 * displayMaze method displays the maze
	 * @param maze is a maze to display
	 */
	void displayMaze(Maze3d maze);
	
	/**
	 * setProperties method sets the properties of the program
	 * @param properties is the properties to set(xml)
	 */
	void setProperties(Properties properties);
	
	/**
	 * notifyMazeIsReady method alerts the user when the maze is ready
	 * @param name is the name of the maze to notify
	 */
	void notifyMazeIsReady(String name);
}
