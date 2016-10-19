package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;

public class CLI extends Observable {
	private BufferedReader in;
	private PrintWriter out;	
	
	public CLI(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;		
	}
	
	private void printMenu() {
		out.println();
		out.println("Enter The commnad you want and its parameters (separated with spaces)");
		out.println("1) Dir: dir <path>");//2
		out.println("2) Generate3DMaze: generate_3d_maze <name> <levels> <rows> <columns> <Simple/Growing>");//6
		out.println("3) Display: display <name>");//2
		out.println("4) DisplayCrossSectionBy: display_cross_section <cross (X/Y/Z)> <index> <name>");//4
		out.println("5) SaveMaze: save_maze <name> <file name>");//3
		out.println("6) LoadMaze: load_maze <file name> <name> ");//3
		out.println("7) Solve: solve <name> <BFS/DFS>");//3
		out.println("8) DisplaySolution: display_solution <name>");//2
		out.println("9) Exit: Bye!");//1
		out.println("Enter Command: ");
		out.flush();
	}
	
	public void start() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
				
					printMenu();
					
					try {
						String commandLine = in.readLine();
						setChanged();
						notifyObservers(commandLine);
						
						if (commandLine.equals("exit"))
							break;
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}			
		});
		thread.start();		
	}
	
}
