package org.jtester.module.utils;

import static ext.jtester.org.apache.commons.io.IOUtils.closeQuietly;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jtester.exception.JTesterException;
import org.jtester.utility.ResourceHelper;

public class PropertiesReader {

	private final static Logger log4j = Logger.getLogger(PropertiesReader.class);

	/**
	 * Loads the properties file with the given name, which is available in the
	 * user home folder. If no file with the given name is found, null is
	 * returned.
	 * 
	 * @param propertiesFileName
	 *            The name of the properties file
	 * @return The Properties object, null if the properties file wasn't found.
	 */
	public Properties loadPropertiesFileFromUserHome(String propertiesFileName) {
		InputStream inputStream = null;
		try {
			if ("".equals(propertiesFileName)) {
				throw new IllegalArgumentException("Properties Filename must be given.");
			}
			Properties properties = new Properties();
			String userHomeDir = System.getProperty("user.home");
			File localPropertiesFile = new File(userHomeDir, propertiesFileName);
			if (!localPropertiesFile.exists()) {
				return null;
			}
			inputStream = new FileInputStream(localPropertiesFile);
			properties.load(inputStream);
			log4j.info("Loaded configuration file " + propertiesFileName + " from user home");
			return properties;

		} catch (FileNotFoundException e) {
			return null;
		} catch (Exception e) {
			throw new JTesterException("Unable to load configuration file: " + propertiesFileName + " from user home",
					e);
		} finally {
			closeQuietly(inputStream);
		}
	}

	/**
	 * Loads the properties file with the given name, which is available in the
	 * classpath. If no file with the given name is found, null is returned.
	 * 
	 * @param propertiesFileName
	 *            The name of the properties file
	 * @return The Properties object, null if the properties file wasn't found.
	 */
	public Properties loadPropertiesFileFromClasspath(String propertiesFileName) {
		InputStream inputStream = null;
		try {
			if ("".equals(propertiesFileName)) {
				throw new IllegalArgumentException("Properties Filename must be given.");
			}
			Properties properties = new Properties();
			inputStream = ResourceHelper.getResourceAsStream(propertiesFileName);
			if (inputStream == null) {
				return null;
			}
			properties.load(inputStream);
			return properties;
		} catch (FileNotFoundException e) {
			return null;
		} catch (Exception e) {
			throw new JTesterException("Unable to load configuration file: " + propertiesFileName, e);
		} finally {
			closeQuietly(inputStream);
		}
	}
}
