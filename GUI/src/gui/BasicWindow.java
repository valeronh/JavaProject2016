package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import view.AbstractView;

public abstract class BasicWindow extends AbstractView implements Runnable {

	protected Display display;
	protected Shell shell;
	
	/**
	 * initWidgets method initialize all widgets we use in the window
	 */
	protected abstract void initWidgets();
	
	@Override
	public void run() {
		display = Display.getCurrent();
		shell = new Shell(display);

		initWidgets();
		
		shell.open();
		
		while(!shell.isDisposed()){ 
		   if(!display.readAndDispatch()){ 
		      display.sleep(); 
		   }
		}
		display.dispose(); 
	}
	
	/**
	 * exit method exits the program
	 */
	public void exit(){
		shell.dispose(); 
		display.dispose(); 
		System.exit(0);
	}

}
