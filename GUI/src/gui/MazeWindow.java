package gui;

import io.MyDecompressorInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import presenter.Properties;
import view.View;
import algorithms.mazeGenarators.Maze3d;

public class MazeWindow extends BasicWindow implements View {
	
	private Properties properties;
	private MazeDisplay mazDis;
	
	public Text text = null;
	final private Image imgBackground = new Image(null, "resources/images/Background_Beach_Sand.jpg");
	
	public MazeWindow(Properties p) {
		this.properties = p;
	}
	
	@Override
	public void setProperties(Properties prop) {
		properties = prop;
	}
	
	@Override
	@SuppressWarnings("unused")
	protected void initWidgets() {
	
		
		GridLayout gridLayout = new GridLayout(2, false);
		shell.setLayout(gridLayout);				
		shell.setText("Maze3d Game");
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		shell.setBackgroundImage(imgBackground);
		shell.setSize(1200, 700);
		
		MazeMenu menu = new MazeMenu(this);
		
		// Open in center of screen
		Rectangle bounds = display.getPrimaryMonitor().getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);

		// handle with the RED X
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				exit();
			}

		});	
		
		//Composite btnGroup = new Composite(shell, SWT.TOP);
		//RowLayout rowLayout = new RowLayout(SWT.TOP);
		//btnGroup.setLayout(rowLayout);
		
		//comments = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		//comments.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Button btnGenerateMaze = new Button(shell, SWT.PUSH | SWT.FILL);
		btnGenerateMaze.setLayoutData(new GridData(SWT.LEFT,SWT.TOP,false,false,1,1));
		btnGenerateMaze.setText("Hit your stick in the water! ");	
		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				showGenerateMazeOptions();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// IGNORE
			}
		});
		Text empty = new Text(shell, SWT.READ_ONLY);
		empty.setText("(Generate maze)");	
		empty.setLayoutData(new GridData(SWT.LEFT,SWT.TOP,false,false,1,1));
		
		Button btnDisplayMaze = new Button(shell, SWT.PUSH | SWT.FILL);
		btnDisplayMaze.setText("Display maze");	
		btnDisplayMaze.setLayoutData(new GridData(SWT.LEFT,SWT.TOP,false,false,1,1));
		btnDisplayMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				showMaze("Maze");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		//Set layout to maze display
		mazDis = new MazeDisplay(shell, SWT.NONE,this,null);
		mazDis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mazDis.setFocus();
	}

	/**
	 * showGenerateMazeOptions method shows new shell to enter the maze measures after clicking on generate
	 */
	protected void showGenerateMazeOptions() {
		
		
		Shell generateWindowShell = new Shell(display, SWT.TITLE | SWT.CLOSE);
		generateWindowShell.setText("Generate Maze Window");
		generateWindowShell.setLayout(new GridLayout(2, false));
		generateWindowShell.setSize(210, 210);
		generateWindowShell.setBackgroundImage(new Image(null, "resources/images/background2.jpg"));
		generateWindowShell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		Rectangle bounds = display.getPrimaryMonitor().getBounds();
		Rectangle rect = generateWindowShell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		generateWindowShell.setLocation(x, y);
		
		Label lblHead = new Label(generateWindowShell, SWT.BOLD);
		FontData fontData = lblHead.getFont().getFontData()[0];
		Font font = new Font(display, new FontData(fontData.getName(), fontData.getHeight()+1, SWT.BOLD));
		lblHead.setFont(font);
		lblHead.setText("Enter size of the maze (x,y,z)");
		lblHead.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
		
		Label lblFloors = new Label(generateWindowShell, SWT.NONE);
		lblFloors.setText("Floors: ");
		Text txtFloors = new Text(generateWindowShell, SWT.BORDER);
		txtFloors.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		txtFloors.setText("3");
		
		Label lblRows = new Label(generateWindowShell, SWT.NONE);
		lblRows.setText("Rows: ");
		Text txtRows = new Text(generateWindowShell, SWT.BORDER);
		txtRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		txtRows.setText("3");
		
		Label lblCols = new Label(generateWindowShell, SWT.NONE);
		lblCols.setText("Columns: ");
		Text txtCols = new Text(generateWindowShell, SWT.BORDER);
		txtCols.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		txtCols.setText("3");
		
		Button btnStartGame = new Button(generateWindowShell, SWT.PUSH);
		btnStartGame.setText("GO!");
		btnStartGame.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		btnStartGame.addSelectionListener(new SelectionListener() {

			/**
			 * take all the floors, cols, rows and make them from Text Box 
			 * 
			 * @param SelectionEvent
			 */
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("generate_3d_maze" + " Maze " + txtFloors.getText() + " " + txtRows.getText() + " " + txtCols.getText() + " a");
				generateWindowShell.dispose();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) { }
			
		});

		generateWindowShell.open();

		mazDis.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				String direction = null;
				switch (e.keyCode) {
				case SWT.ARROW_LEFT:
					direction = "Left";
					break;
				case SWT.ARROW_RIGHT:
					direction = "Right";
					break;
				case SWT.ARROW_DOWN:
					direction = "Backward";
					break;
				case SWT.ARROW_UP:
					direction = "Forward";
					break;
				case SWT.PAGE_UP:
					direction = "Up";
					break;
				case SWT.PAGE_DOWN:
					direction = "Down";
					break;
				default:
					//NONE
					break;
				}
				if (direction != null) {
					mazDis.moveChracter(direction);
					mazDis.redrawMe();
				}
			}
		});
	}

	@Override
	public void notifyMazeIsReady(String str) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				setChanged();
				notifyObservers("display_maze " + str);
			}
		});
	}

	/**
	 * showMessageBox method shows message box with messaqge
	 * @param str is the message to show as string
	 */
	public void showMessageBox(String str) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageBox msg = new MessageBox(shell, SWT.ICON_INFORMATION);
				msg.setMessage(str);
				msg.open();
			}
		});
	}
	

	@Override
	public void start() {
		run();		
	}
	
	/**
	 * showError method shows error in message box
	 * @param message is the message to show as string
	 */
	public void showError(String message) {
		showMessageBox(message);
		
	}
	
	/**
	 * showMaze method shows maze in the window after reading from byte array
	 * @param mazeByteArrString is the maze 
	 */
	public void showMaze(String mazeByteArrString) {
		setChanged();
		notifyObservers("save_maze " + "Maze Maze");
		int size = 0;
		
		MyDecompressorInputStream in = null;
		try {
			in = new MyDecompressorInputStream( new FileInputStream("Maze") );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			size = in.getSize();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		byte byarr[] = new byte[size];
		
		try {
			in.read(byarr);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Maze3d maze3d=new Maze3d(byarr);
		mazDis.setMaze(maze3d);
		mazDis.setCharacterPosition(maze3d.getStartPosition());
		int[][] crossSection = maze3d.getCrossSectionByZ(maze3d.getStartPosition().getLevel());
		mazDis.setCrossSection(crossSection, null, null);
		mazDis.setgoalPos(maze3d.getendPosition());
		mazDis.setmaze_name("Maze");
	}

	public void showDirPath(String dirArray) {
		// FUTURE
		
	}

	public void showDisplayCrossSectionBy(String crossMazeBySection) {
		// FUTURE
		
	}

	public void showSaveMaze(String str) {
		// FUTURE
		
	}

	public void showLoadMaze(String str) {
		// FUTURE
		
	}

	public void showSolve(String message) {
		// FUTURE
		
	}

	public void showDisplaySolution(String sol) {
		// FUTURE
		
	}

	public void printMenu(String menu) {
		// FUTURE
		
	}

	@Override
	public void display(String msg) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayMaze(Maze3d maze) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
	}

}
