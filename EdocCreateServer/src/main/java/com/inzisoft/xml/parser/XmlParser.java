package com.inzisoft.xml.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * SAX(Simple API for XML)를 이용하여 XML 파일을 파싱하는 메인 클래스
 * 
 * @author	(주)인지소프트 조래훈
 * @version	1.0
 * @date	2018.05.11
 */
public class XmlParser extends DefaultHandler
{
	private Logger logger = LoggerFactory.getLogger(XmlParser.class);

	private XmlHandler xmlHandler = null;

	public void parse(String xmlFilePath) throws Exception {
		parse(xmlFilePath, "UTF-8");
	}

	public void parse(String xmlFilePath, String charset) throws Exception {
		logger.debug("[XmlParser.parse()] Starts...");

		BufferedReader xmlBr = null;
		
		try {
			xmlHandler = new XmlHandler();

			XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			xmlReader.setContentHandler(xmlHandler);
			xmlReader.setErrorHandler(xmlHandler);

			xmlBr = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(xmlFilePath), charset));
			InputSource xmlInputSource = new InputSource(xmlBr);

			logger.debug("[xmlReader.parse()] Starts...");
			xmlReader.parse(xmlInputSource);
			logger.debug("[xmlReader.parse()] Ends...");
			
		} catch(IOException e) {
			throw e;
		} catch (SAXException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if(xmlBr != null) xmlBr.close();

			logger.debug("[XmlParser.parse()] Ends...");
		}

	}

	public XmlObject getRoot() {
		if(xmlHandler == null) return null;
		return xmlHandler.getRoot();
	}

	public void print(String charset, boolean bIndent) {
		logger.info("[XmlParser.print] Starts...");
		xmlHandler.print(xmlHandler.getRoot(), charset, bIndent);
		logger.info("[XmlParser.print] Ends...");
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		xmlHandler.toString(sb, xmlHandler.getRoot());
		return sb.toString();
	}
}
