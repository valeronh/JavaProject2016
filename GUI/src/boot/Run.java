package boot;

import gui.MazeWindow;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.LoaderProperties;
import presenter.Presenter;
import presenter.Properties;
import view.AbstractView;
import view.MyView;

/**
 * @author Yarden Yerushalmi 204326292
 * @author Valery Khirin 
 * @author 317974467
 */
public class Run {
	
	
	public static void main(String[] args) {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);

		Properties proper = LoaderProperties.getInstance().getProperties();
		MyModel model = new MyModel();
		
		AbstractView view = null;
		
		if(proper.getIsgui()){
			view = new MazeWindow(proper);
		}else{
			view = new MyView(in, out);
		}
				
		Presenter presenter = new Presenter(model, view);
		model.addObserver(presenter);
		view.addObserver(presenter);
				
		view.start();
	}
}
