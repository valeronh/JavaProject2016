package model;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import presenter.LoaderProperties;
import presenter.Properties;
import algorithms.demo.SearchableAdapter;
import algorithms.mazeGenarators.GrowingTreeGenerator;
import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Maze3dGenerator;
import algorithms.mazeGenarators.Position;
import algorithms.mazeGenarators.SimpleMaze3dGenerator;
import algorithms.search.BFS;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import algorithms.search.State;

public class MyModel extends Observable implements Model {
	
	private ExecutorService executor;
	private Map<String,Maze3d> maze_hm = new ConcurrentHashMap<String, Maze3d>();
	private Map<String,Solution<Position>> sol_hm = new ConcurrentHashMap<String, Solution<Position>>();
	private Properties prop;
	
	public MyModel() {
		prop = LoaderProperties.getInstance().getProperties();
		executor = Executors.newFixedThreadPool(prop.getNumOfThreads());
		loadMap();
	}

	@Override
	public void generateM3d(String name, int levels, int rows, int cols, String algo) {
		executor.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				Maze3d maze3d = null;
				Maze3dGenerator mg = null;
				String algorithm = prop.getGenerateMazeAlgorithm();
				
				if(algo.equals("Simple") || algo.equals("Growing"))
					algorithm = algo;
				
				switch(algorithm){
				case "Simple":
					mg = new SimpleMaze3dGenerator();
					maze3d = mg.generate(levels, rows, cols);
					maze_hm.put(name, maze3d);
					setChanged();
					notifyObservers("print "+name+" has been generated!");
					break;
				case "Growing":
					mg = new GrowingTreeGenerator();
					maze3d = mg.generate(levels, rows, cols);
					maze_hm.put(name, maze3d);
					setChanged();
					notifyObservers("print "+name+" has been generated!");
					break;
				default:
					setChanged();
					notifyObservers("print "+"The algorithm " + algo + " does not exists");
				}
				
				return maze3d;
			}
			
		});
			
	}

	@Override
	public Maze3d getMaze(String name) {
		return maze_hm.get(name);
	}
	
	public void dir(File dir) {
		String[] arr;
		 if(dir.exists())
		 {
			 arr = dir.list();
			 setChanged();
			 notifyObservers("print "+toString(arr));
		 }
		 else
		 {
			 setChanged();
			 notifyObservers("print "+"The path " + dir.getPath() + " does not exists");
		 }
	}

	@Override
	public void getCrossBY(String name, int index, char place) {
		if(maze_hm.containsKey(name)){
			try{
				int[][] cross = new int[0][0];
				
				switch(place){
				case 'x':
					cross = maze_hm.get(name).getCrossSectionByX(index);
					break;
				case 'y':
					cross = maze_hm.get(name).getCrossSectionByY(index);
					break;
				case 'z':
					cross = maze_hm.get(name).getCrossSectionByZ(index);
					break;
				default:
					setChanged();
					notifyObservers("print "+"The place " + place + " does not exists");
				}
				
				setChanged();
				notifyObservers("print "+toString(cross));
			}catch(IndexOutOfBoundsException e){
				e.printStackTrace();
			}
		}else{
			setChanged();
			notifyObservers("print "+"Maze "+name+" doesn't exists!");
		}
	}

	@Override
	public void display(String name) {
		if(maze_hm.containsKey(name)){
			setChanged();
			notifyObservers("print "+maze_hm.get(name).toString());
		}else{
			setChanged();
			notifyObservers("print "+"Maze "+name+" doesn't exists!");
		}
	}

	@Override
	public void saveM3d(String name, FileOutputStream fos) {
		if(maze_hm.containsKey(name)){
			OutputStream out = new MyCompressorOutputStream(fos);
			
			try {
				out.write(maze_hm.get(name).toByteArray());
				out.flush();
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else{
			setChanged();
			notifyObservers("print "+"Maze "+name+" doesn't exists!");
		}
		
	}

	@Override
	public void loadM3d(FileInputStream fis, String name) {
		MyDecompressorInputStream in = new MyDecompressorInputStream( fis );

		int size = 0;
		try {
			size = in.getSize();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		byte b[] = new byte[size];
		
		try {
			in.read(b);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Maze3d maze3d = new Maze3d(b);
		maze_hm.put(name, maze3d);
		
	}
	
	@Override
	public void solveM3d(String name, String algo) {
		if(maze_hm.containsKey(name)){
			if(sol_hm.containsKey(name)){
				setChanged();
				notifyObservers("print "+"Solution for "+name+" already exists!");
			}else{
				
				executor.submit(new Callable<Solution<Position>>() {

					@Override
					public Solution<Position> call() throws Exception {
						SearchableAdapter sa = new SearchableAdapter(maze_hm.get(name));
						Solution<Position> solution = null;
						String algorithm = prop.getGenerateMazeAlgorithm();
						
						if(algo.equals("BFS") || algo.equals("DFS"))
							algorithm = algo;
						
						if(algorithm.equals("BFS")){
							Searcher<Position> bfs = new BFS<Position>();
							solution = bfs.search(sa);
							sol_hm.put(name, solution);
							setChanged();
							notifyObservers("print "+"Solution for "+name+" is ready!");
						}else if(algorithm.equals("DFS")){
							Searcher<Position> dfs = new BFS<Position>();
							solution = dfs.search(sa);
							sol_hm.put(name, solution);
							setChanged();
							notifyObservers("print "+"Solution for "+name+" is ready!");
						}else{
							setChanged();
							notifyObservers("print "+"Algorithm "+algo+" doesn't exists!");
						}
						
						return solution;
					}
					
				});
			}
		}else{
			setChanged();
			notifyObservers("print "+"Maze "+name+" doesn't exists!");
		}
	}

	@Override
	public void displaySolution(String name) {
		if(sol_hm.containsKey(name)){
			List<State<Position>> positions = sol_hm.get(name).getStates();
			for( State<Position> pos : positions){
				setChanged();
				notifyObservers("print "+pos.getVal().toString());
			}
		}else{
			setChanged();
			notifyObservers("print "+"Solution for "+name+" doesn't exists!");
		}
	}

	@Override
	public void exit() {
		saveMap();
		setChanged();
		notifyObservers("print "+"Bye!");
		executor.shutdownNow();
	}

	public void saveMap(){
		ObjectOutputStream oos = null;
		try {
		    oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("solutions.dat")));
			oos.writeObject(maze_hm);
			oos.writeObject(sol_hm);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadMap(){
		File file = new File("solutions.dat");
		if (!file.exists())
			return;
		
		ObjectInputStream ois = null;
		
		try {
			FileInputStream fis = new FileInputStream("solutions.dat");
			GZIPInputStream gis = new GZIPInputStream(fis);
			ois = new ObjectInputStream(gis);
			maze_hm = new ConcurrentHashMap<String, Maze3d>((ConcurrentHashMap<String, Maze3d>)ois.readObject());
			sol_hm = new ConcurrentHashMap<String, Solution<Position>> ((ConcurrentHashMap<String, Solution<Position>>)ois.readObject());		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally{
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String toString(String[] arr){
		String output = "";
		
		for (String a : arr)
			output += a+"\n";
		
		return output;
	}
	
	public String toString(int[][] arr){
		StringBuilder sb = new StringBuilder();
		for (int[] a : arr) {
			for (int x : a) {
				sb.append(x + " ");
			}
		
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
