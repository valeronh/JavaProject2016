package gui;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;

import presenter.Properties;
import algorithms.mazeGenarators.Maze3d;

public class MessageWindow extends BasicWindow
{
	String strn;
	
	@Override
	protected void initWidgets() {
		shell.setLayout(new GridLayout(1,true));
	}
			
	public void display(String str) {
		this.strn = str;
		Text text = new Text(shell, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		text.setText(strn);
	}

	@Override
	public void start() {
		// IGNORE
	}

	@Override
	public void update(Observable o, Object arg) {
		// IGNORE
	}

	@Override
	public void displayMaze(Maze3d maze) {
		// IGNORE
	}

	@Override
	public void setProperties(Properties properties) {
		// IGNORE	
	}

	@Override
	public void notifyMazeIsReady(String name) {
		// IGNORE	
	}
}

