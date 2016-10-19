package presenter;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoaderProperties {
	private static LoaderProperties instance;
	private Properties properties;
	
	public Properties getProperties() {
		return properties;
	}
	
	private LoaderProperties() 
	{
		try {
			XMLDecoder decoder = new XMLDecoder(new FileInputStream("properties.xml"));
			properties = (Properties)decoder.readObject();
			decoder.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static LoaderProperties getInstance() {
		if (instance == null) 
			instance = new LoaderProperties();
		return instance;
	}
	
	
}
