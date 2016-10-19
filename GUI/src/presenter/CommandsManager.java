package presenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import presenter.Command;
import model.Model;
import view.View;

public class CommandsManager {
	private Model m;
	private View view;
		
	public CommandsManager(Model model, View view) {
		this.m = model;
		this.view = view;		
	}
	
	public HashMap<String, Command> getCommandsMap() {
		HashMap<String, Command> commands = new HashMap<String, Command>();
		commands.put("maze_ready", new MazeReadyCommand());
		commands.put("print", new Print());
		commands.put("dir", new Dir());
		commands.put("generate_3d_maze", new Generate3DMaze());
		commands.put("display", new Display());
		commands.put("display_cross_section", new DisplayCrossSectionBy());
		commands.put("save_maze", new SaveMaze());
		commands.put("load_maze", new LoadMaze());
		commands.put("solve", new Solve());
		commands.put("display_solution", new DisplaySolution());
		commands.put("exit", new Exit());
		
		return commands;
	}
	
	class Print implements Command {
		@Override
		public void doCommand(String[] args) {
			for(String a : args)
				view.display(a);
		}
	}
	
	class MazeReadyCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			String msg = "maze " + name + " is ready";
			view.display(msg);
		}
	}
	
	class Dir implements Command {

		@Override
		public void doCommand(String[] st) {
			m.dir(new File(st[0]));
		}

	}
	
	class Display implements Command {

		@Override
		public void doCommand(String[] st) {
			m.display(st[0]);
		}

	}
	
	class DisplayCrossSectionBy implements Command {

		@Override
		public void doCommand(String[] st) {
			m.getCrossBY(st[2], Integer.parseInt(st[1]), st[0].charAt(0));
		}

	}
	
	class DisplaySolution implements Command {

		@Override
		public void doCommand(String[] st) {
			m.displaySolution(st[0]);
		}

	}
	
	class Exit implements Command {

		@Override
		public void doCommand(String[] st) {
			m.exit();
		}

	}
	
	class Generate3DMaze implements Command {

		@Override
		public void doCommand(String[] st) {
			m.generateM3d(st[0], Integer.parseInt(st[1]), Integer.parseInt(st[2]), Integer.parseInt(st[3]), st[4]);
		}

	}
	
	class LoadMaze implements Command {

		@Override
		public void doCommand(String[] st) {
			try {
				m.loadM3d(new FileInputStream(st[0]), st[1]);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	class SaveMaze implements Command {

		@Override
		public void doCommand(String[] st) {
			try {
				m.saveM3d(st[0], new FileOutputStream (st[1]));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	class Solve implements Command {

		@Override
		public void doCommand(String[] st) {
			m.solveM3d(st[0], st[1]);
		}

	}
	
	
}