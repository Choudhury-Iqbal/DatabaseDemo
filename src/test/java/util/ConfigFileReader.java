/**
 * 
 */
package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Vaibhav Srivastava-G This file is use to read the data from the
 *         Properties file
 */
public class ConfigFileReader {
	private Properties properties;
	
	
	/*
	 * private final String propertyFilePath = System.getProperty("user.dir") +
	 * "\\resources\\configuration\\config.properties"; private final String
	 * testDataFilePath = System.getProperty("user.dir") +
	 * "\\resources\\testData\\testData.properties";
	 */
	
	private final String propertyFilePath = "resources/config.properties";

	public ConfigFileReader() {
		BufferedReader propertyFileReader;
		try {
			propertyFileReader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			new Properties();
			try {
				properties.load(propertyFileReader);
				propertyFileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}
	}

	

	

	public String getConfigKey(String key) {
		String value = properties.getProperty(key);
		if (value != null)
			return value;
		else
			throw new RuntimeException("Value not specified in the Configuration.properties file.");
	}

	
}
