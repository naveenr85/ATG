/**
 * 
 */
package se.atg.service.harrykart.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import se.atg.service.harrykart.exception.HarryKartException;

/**
 * Utility class to read the properties from the resource
 *
 */
public class HarryKartProperties {
	
	private Properties prop;
	
	private void init() throws HarryKartException {
		prop = new Properties();
		InputStream input = null;
		try {
			ClassLoader loader = getClass().getClassLoader();
			File propFile = new File(loader.getResource("harrykart.properties").getFile());
			input = new FileInputStream(propFile);
			prop.load(input);
			
		} catch (IOException e) {
			throw new HarryKartException("Error while loading properties", e);
		} finally {
			if(input != null) {
				try {
					input.close();
				} catch (IOException e) {
					throw new HarryKartException("Error while closing the input stream", e);
				}
			}
		}
	}
	
	public String getProperty(String propKey) throws HarryKartException {
		if(prop == null) {
			init();
		}
		return (String) prop.get(propKey);
	}

}
