package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;


public class MazeMenu {
	
  Display display;
  Shell shell;
  
  @SuppressWarnings("unused")
  public MazeMenu(MazeWindow mazeWindow) {
    this.display = mazeWindow.display;
    this.shell = mazeWindow.shell;
    
    
    Menu m = new Menu(shell, SWT.BAR);


    final MenuItem file = new MenuItem(m, SWT.CASCADE);
    file.setText("File");
    final Menu filemenu = new Menu(shell, SWT.DROP_DOWN);
    file.setMenu(filemenu);
    

    final MenuItem openItem = new MenuItem(filemenu, SWT.CASCADE);
    openItem.setText("Open");
    final Menu submenu = new Menu(shell, SWT.DROP_DOWN);
    openItem.setMenu(submenu);
    final MenuItem xml = new MenuItem(submenu, SWT.PUSH);
    xml.setText("Show XML");
 
    
    final MenuItem sep = new MenuItem(filemenu, SWT.SEPARATOR);
    
    
    final MenuItem Bye = new MenuItem(filemenu, SWT.PUSH);
    Bye.setText("Exit");



    final MenuItem edit = new MenuItem(m, SWT.CASCADE);
    edit.setText("Edit");
    final Menu editmenu = new Menu(shell, SWT.DROP_DOWN);
    edit.setMenu(editmenu);
    final MenuItem cutItem = new MenuItem(editmenu, SWT.PUSH);
    cutItem.setText("Cut");
    final MenuItem copyItem = new MenuItem(editmenu, SWT.PUSH);
    copyItem.setText("Copy");
    final MenuItem pasteItem = new MenuItem(editmenu, SWT.PUSH);
    pasteItem.setText("Paste");

    
    
    final MenuItem window = new MenuItem(m, SWT.CASCADE);
    window.setText("Window");
    final Menu windowmenu = new Menu(shell, SWT.DROP_DOWN);
    window.setMenu(windowmenu);
    final MenuItem maxItem = new MenuItem(windowmenu, SWT.PUSH);
    maxItem.setText("Maximize");
    final MenuItem minItem = new MenuItem(windowmenu, SWT.PUSH);
    minItem.setText("Minimize");



    final MenuItem help = new MenuItem(m, SWT.CASCADE);
    help.setText("Help");
    final Menu helpmenu = new Menu(shell, SWT.DROP_DOWN);
    help.setMenu(helpmenu);
    final MenuItem aboutItem = new MenuItem(helpmenu, SWT.PUSH);
    aboutItem.setText("About");

    xml.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e) {
        Shell parent = (Shell) maxItem.getParent().getParent();
        SubShell cs = new SubShell(parent);
      }

      public void widgetDefaultSelected(SelectionEvent e) {

      }
    });


    Bye.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e) {
    	  mazeWindow.exit();
      }

      public void widgetDefaultSelected(SelectionEvent e) {

      }
    });

    cutItem.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e) {
        System.out.println("Cut");
      }

      public void widgetDefaultSelected(SelectionEvent e) {

      }
    });

    copyItem.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e) {
        System.out.println("Copy");
      }

      public void widgetDefaultSelected(SelectionEvent e) {

      }
    });

    pasteItem.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e) {
        System.out.println("Paste");
      }

      public void widgetDefaultSelected(SelectionEvent e) {

      }
    });

    maxItem.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e) {
        Shell parent = (Shell) maxItem.getParent().getParent();
        parent.setMaximized(true);
      }

      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });

    minItem.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e) {
        Shell parent = (Shell) minItem.getParent().getParent();
        parent.setMaximized(false);
      }

      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });

    aboutItem.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e) {
        Shell parent = (Shell) minItem.getParent().getParent();
        parent.setMaximized(false);
      }

      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });

    shell.setMenuBar(m);
    
  }
}

class DialogExample extends Dialog {

	DialogExample(Shell parent) {
    super(parent);
  }

  public String open() {
    Shell parent = getParent();
    Shell shell1 = new Shell(parent, SWT.DIALOG_TRIM
        | SWT.APPLICATION_MODAL);
    shell1.setSize(100, 100);
    shell1.setText("Sources and Support");
    shell1.open();
    Display display = parent.getDisplay();
    while (!shell1.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    return "Done";
  }

  public static void main(String[] argv) {
    new DialogExample(new Shell());
  }
}



class SubShell {

	SubShell(Shell parent) {
    Shell sub = new Shell(parent);
    sub.setSize(200, 200);
    sub.open();
  }
}