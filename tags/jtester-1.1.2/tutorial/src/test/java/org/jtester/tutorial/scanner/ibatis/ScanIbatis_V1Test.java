package org.jtester.tutorial.scanner.ibatis;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class ScanIbatis_V1Test {

	public void testScan1() {
		try {
			ScanIbatis_V1.main(new String[] { "d:/alibaba/martini/bundle/war" });
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testScan2() throws ParserConfigurationException, SAXException, IOException {
		String configFileName = System.getProperty("user.dir")
				+ "/src/test/resources/org/jtester/tutorial/scanner/ibatis";
		ScanIbatis_V1.main(new String[] { configFileName });
	}
}
