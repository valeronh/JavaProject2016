package gui;

import java.util.ArrayList;
import java.util.List;
import algorithms.mazeGenarators.Position;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import algorithms.mazeGenarators.Maze3d;



/**
 * @author Yarden Yerushalmi
 *
 */
public class MazeDisplay extends Canvas {
	
	@SuppressWarnings("unused")
	private String maze_name;
	private int CurrentLevel;
	private int[][] Xsection = { {0}, {0} };
	private Character moshe;
	private boolean drawHINT;
	private Position hintPos;
	private boolean winner;
	private Position goalPos;
	private List<Point> downHint;//FEAUTRE
	private List<Point> upHint;//FEAUTRE
	private Maze3d maze;
	private MazeWindow mazeWindow;
	
	//PICS
	final private Image imgGoal = new Image(null,"resources/images/israel.png") ;
	final private Image imgWinner = new Image(null,"resources/images/finnish.jpg");
	final private Image imgWall = new Image(null, "resources/images/sea.jpg");
	
	

	public MazeDisplay(Composite parent, int style,MazeWindow mazeWindow,Maze3d maze) {
		super(parent, style);
		this.maze = maze;
		this.mazeWindow = mazeWindow;
		
		moshe = new Character();
		if(moshe.getPos() != null)
			CurrentLevel = moshe.getPos().getLevel();
		moshe.setPos(new Position(-1, -1, -1));
		
		this.drawHINT = false;
		this.winner = false;
		upHint = new ArrayList<Point>();
		downHint = new ArrayList<Point>();
		goalPos = new Position(0, 0, 0);

		addPaintListener(new PaintListener() { //Drawing
			@Override
			public void paintControl(PaintEvent e) {
				int wid, hig;
				int canvasWidth = getSize().x;
				int cellWidth = canvasWidth / Xsection.length;
				int canvasHeight = getSize().y;
				int cellHeight = canvasHeight / Xsection[0].length;
				
				e.gc.setForeground(new Color(null, 1, 255, 0));
				e.gc.setBackground(new Color(null, 0, 0, 0));
				CurrentLevel = moshe.getPos().getLevel();
				
				for (int i = 0; i < Xsection.length; i++) {
					for (int j = 0; j < Xsection[i].length; j++) {
						hig = i * cellHeight;
						wid = j * cellWidth;
							
							if(Xsection[i][j] == 0){
							}else
								e.gc.drawImage(imgWall, 0, 0,imgWall.getBounds().width,imgWall.getBounds().height, wid, hig,cellWidth, cellHeight);
						}
					}
				
				
				if(maze != null && mazeWindow.text != null){
					mazeWindow.text.append("Charecter position: " +moshe.getPos().toString()+"\n");
					mazeWindow.text.append("Current floor: "+Integer.toString(moshe.getPos().getLevel())+"\n");
					mazeWindow.text.append("floor: "+Integer.toString(moshe.getPos().getLevel())+"\n");
					mazeWindow.text.append("Goal Position floor: "+ Integer.toString(maze.getendPosition().getLevel())+"\n");
					mazeWindow.text.append("Start Position floor: "+ Integer.toString(maze.getendPosition().getLevel())+"\n");
			}
				
				
				
				if (drawHINT) {
					drawHINT = false;
					e.gc.drawImage(imgGoal, 0, 0, imgGoal.getBounds().width, imgGoal.getBounds().height, (cellWidth * hintPos.getLevel()) + (cellWidth / 4), (cellHeight * hintPos.getRow()) + (cellHeight / 4), cellWidth/2, cellHeight/2);
				}
				
				if(moshe.getPos().getLevel() >= 0 &&
						moshe.getPos().getLevel() == goalPos.getLevel() && 
						moshe.getPos().getRow() == goalPos.getRow() && 
						moshe.getPos().getColumn() == goalPos.getColumn() ){
					winner = true;
				}
					
				
				if (!winner) {
					moshe.draw(cellWidth,cellHeight, e.gc);
					if(goalPos.getLevel() == CurrentLevel)
						e.gc.drawImage(imgGoal, 0, 0, imgGoal.getBounds().width, imgGoal.getBounds().height, cellWidth * goalPos.getColumn(), cellHeight * goalPos.getRow(), cellWidth, cellHeight);
				} else
					e.gc.drawImage(imgWinner, 0, 0, imgWinner.getBounds().width, imgWinner.getBounds().height, cellWidth * goalPos.getColumn(), cellHeight * goalPos.getRow(), cellWidth, cellHeight);
				forceFocus();
				winner = false;

			}
		});
	}
	
	public void setWinner(boolean w) {
		this.winner = w;
	}

	public void setCurrentLevel(int CurrentLevel) {
		this.CurrentLevel = CurrentLevel;
	}
	
	public void setmaze_name(String str) {
		this.maze_name = str;
	}
	
	public void setgoalPos(Position goalPos) {
		this.goalPos = goalPos;
	}
	
	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}
	
	/**
	 * set the character position then draw the maze
	 * @param pos, the position 
	 */
	public void setCharacterPosition(Position pos) {
		this.moshe.setPos(pos);
		redrawMe();
	}
	
	/**
	 * paint the maze in crossSection [][]
	 * @param Xsection, crossSection
	 * @param upHint, List<Point>
	 * @param downHint, List<Point>
	 */
	public void setCrossSection(int[][] Xsection, List<Point> upHint, List<Point> downHint) {
		this.Xsection = Xsection;
		this.upHint = upHint;
		this.downHint = downHint;
		redrawMe();
	}
		
	public Position getMovePosition(Position pos, String direction){
		Position nextPos = new Position(pos);
		
		switch(direction){
			case "Up":
				if(maze.getVal(pos.getLevel()+1, pos.getRow(), pos.getColumn()) == 0){
					nextPos.setLevel(pos.getLevel()+1);					
				}
				break;
			case "Down":
				if(maze.getVal(pos.getLevel()-1, pos.getRow(), pos.getColumn()) == 0){
					nextPos.setLevel(pos.getLevel()-1);
				}
				break;
			case "Forward":
				if(maze.getVal(pos.getLevel(), pos.getRow()-1, pos.getColumn()) == 0){
					nextPos.setRow(pos.getRow()-1);
				}
				break;
			case "Backward":
				if(maze.getVal(pos.getLevel(), pos.getRow()+1, pos.getColumn()) == 0){
					nextPos.setRow(pos.getRow()+1);
				}
				break;
			case "Right":
				if(maze.getVal(pos.getLevel(), pos.getRow(), pos.getColumn()+1) == 0){
					nextPos.setColumn(pos.getColumn()+1);
				}
				break;
			case "Left":
				if(maze.getVal(pos.getLevel(), pos.getRow(), pos.getColumn()-1) == 0){
					nextPos.setColumn(pos.getColumn()-1);
				}
				break;
			default:
				nextPos = null;
				break;
		}
		
		return nextPos;
	}
	
	
	/**
	 * moveChracter method moves the character according to direc
	 * @param direc is the direction for moving as a string
	 * @return true if the character can move to direc else false
	 */
	public boolean moveChracter(String direc) {
		if (possibleMoveFromPosition(moshe.getPos(), direc)) {
			Position nextPosi = getMovePosition(moshe.getPos(), direc);
			int[][] Xsection = maze.getCrossSectionByZ(nextPosi.getLevel());
			setCrossSection(Xsection, null, null);
			setCharacterPosition(nextPosi);
			return true;			
		}else
			return false;
	}	
	
	
	/**
	 * possibleMoveFromPosition method moves the character from position by the direc
	 * @param position is the current position
	 * @param direc is the direction to move as a string
	 * @return true if the movement is possible else false
	 */
	public boolean possibleMoveFromPosition(Position position,String direc){
		if ((maze!= null) && (getMovePosition(position, direc) != null)) 
			return true;
		else
			return false;		
		
	}

	/**
	 * move the character
	 * @param pos, the position
	 */
	public void moveTheCharacter(Position pos) {
		this.moshe.setPos(pos);
		redrawMe();
	}
	
	
	/**
	 *drawHint method draws a hint
	 * @param PositionhintPos
	 */
	public void drawHint(Position hintPos) {
		this.drawHINT = true;
		this.hintPos = hintPos;
		redrawMe();
	}
	
	/**
	 *redrawMe method draws again the maze
	 */
	public void redrawMe() {
		getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				setEnabled(true);
				redraw();
			}
		});
	}

	/**
	 * showMessageBox method shows message box with messaqge
	 * @param str
	 */
	public void showMessageBox(String str) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageBox msg = new MessageBox(new Shell(),
						SWT.ICON_INFORMATION);
				msg.setMessage(str);
				msg.open();
			}
		});
	}
}