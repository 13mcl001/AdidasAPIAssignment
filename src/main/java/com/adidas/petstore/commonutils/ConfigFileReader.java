package com.adidas.petstore.commonutils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

	private static Properties properties;
	private final String propertyFilePath = "src/test/resources/Config/adidas.properties";

	public ConfigFileReader() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("InApiConfig.properties not found at " + propertyFilePath);
		}
	}
	
	
	public static String getAPIHostName() {
		String apiHostName = properties.getProperty("apiHostName");
		if (apiHostName != null)
			return apiHostName;
		else
			throw new RuntimeException("APIHostName not specified in the Configuration.properties file.");
	}

}