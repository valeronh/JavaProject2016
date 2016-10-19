package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import algorithms.mazeGenarators.Maze3d;

public interface Model {
	/**
	 * Maze getter from hash map
	 * @param name String This is the name of the maze
	 * @return Maze3d This returns a maze3d object
	 */
	Maze3d getMaze(String name);
	/** 
	 * open all the files from the path dir
	 * @param dir the path of files we want to open
	 */
	public void dir(File dir);
	/**
	 * bring us the maze2d on the place we want and index
	 * @param name of the maze3d
	 * @param index the row/column/floor from the maze3d we want to bring
	 * @param place x/y/z what sector
	 */
	public void getCrossBY(String name, int index, char place);
	/**
	 * generate maze3d with the sizes we want
	 * @param name of maze3d we want to generate
	 * @param x size of x sector
	 * @param y size of y sector
	 * @param z size of z sector
	 * @param algo algorithm to build maze
	 */
	public void generateM3d(String name,int x,int y,int z, String algo);
	/**
	 * display the maze we want 
	 * @param name of the maze3d we want to display
	 */
	public void display(String name);
	/**
	 * save the maze3d we want into file
	 * @param name of maze3d we want to save
	 * @param fos the file we want to save in
	 */
	public void saveM3d(String name,FileOutputStream fos);
	/**
	 * load maze3d from file that have it
	 * @param fis the file we want to load from
	 * @param name of maze3d to load
	 */
	public void loadM3d(FileInputStream fis, String name);
	/**
	 * bring us the solve of the maze we want
	 * @param name of maze3d we want to solve
	 * @param algo algorithm to solve maze by
	 */
	public void solveM3d(String name, String algo);
	/**
	 * display the solution for the maze we want
	 * @param name of Maze3d we want to solve
	 */
	public void displaySolution(String name);
	/**
	 * exit from the project
	 */
	public void exit();

}
