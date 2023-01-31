package com.mobileleader.edoc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

/**
 * 서식/비즈로직 배포 폴더의 서식/비즈로직.xml 파일 기준으로 EDOC_VerCheck.xml/BIZ_VerCheck.xml 파일을 
 * 생성하기 위한 Utility class 
 * @since 2019.07.22
 *
 */

public class XmlUtil {
    private static final Logger LOG = LoggerFactory.getLogger(XmlUtil.class);

	private String formPath;	
    private String bizPath;		
    
    public final String EDOC_VERSION_FILE_NAME = "EDOC_VerCheck.xml";
    public final String BIZ_VERSION_FILE_NAME = "BIZ_VerCheck.xml";
    public final String BACKUP_SUFFIX = "_backup";
    private final String DEFAULT_VERSION = "0.1";
    
    public class NodePair{
    	private Node node;
    	private Node textNode;
		/**
		 * @return the node
		 */
		public Node getNode() {
			return node;
		}
		/**
		 * @param node the node to set
		 */
		public void setNode(Node node) {
			this.node = node;
		}
		/**
		 * @return the textNode
		 */
		public Node getTextNode() {
			return textNode;
		}
		/**
		 * @param textNode the textNode to set
		 */
		public void setTextNode(Node textNode) {
			this.textNode = textNode;
		}
    }
    
    /**
     * @param formPath	서식 배포 폴더 경로 ex) /programs/data/eform/form/
     * @param bizPath	비즈로직 배포 폴더 경로 ex) /programs/data/eform/biz/
     */
    public XmlUtil(String formPath, String bizPath) {
    	this.formPath = formPath;
    	this.bizPath = bizPath;
    }
    
    /**
	 * 서식/비즈로직 배포 폴더의 서식/비즈로직.xml 파일 기준으로 EDOC_VerCheck.xml/BIZ_VerCheck.xml 파일을 생성한다.
	 * @throws Exception
	 */
	public void generateVersionXml() {
		try {
			String[] formXmlList = new File(formPath).list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".xml") && !name.contains("EDOC_VerCheck");
				}
			});
			
			generateEdocVerCheckXml(formXmlList);
			
			LOG.info("[generateVerCheckXml] success to generate EDOC ver check xml!");
			
			String[] bizXmlList = new File(bizPath).list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.startsWith("BusinessLogic_") && name.endsWith(".xml") && !name.contains("BIZ_VerCheck");
				}
			});
			
			generateBizVerCheckXml(bizXmlList);
			LOG.info("[generateVerCheckXml] success to generate BIZ ver check xml!");
			
		} catch(JDOMException e){
			LOG.error("[generateEdocVerXml] JDOMException : " + e.getMessage());
		} catch(IOException e) {
			LOG.error("[generateEdocVerXml] IOException : " + e.getMessage());
		} catch (Exception e) {
			LOG.error("[generateEdocVerXml] Exception  : " + e.getMessage());
		}
	}
	
	
	/**
	 * 파일명 리스트로 EDOC_VerCheck.xml생성한다.
	 * @param fileNameList	파일명 리스트
	 * @throws Exception
	 */
	public void generateEdocVerCheckXml(String[] fileNameList) throws Exception {
		File[] list = new File[fileNameList.length];
		
		for(int i = 0; i < fileNameList.length; i++){
			File file = new File(formPath + fileNameList[i]);
			if(file.exists()){
				list[i] = file;
			}
		}
		generateEdocVerCheckXml(list);	
	}
	
	/**
	 * 파일 객체 배열로 EDOC_VerCheck.xml생성한다.
	 * @param fileList	파일객체 리스트
	 * @throws IOException 
	 * @throws JDOMException 
	 * @throws Exception
	 */
	public synchronized void generateEdocVerCheckXml(File[] fileList) throws Exception {
		FileOutputStream fOS    = null;
		
		try {
			SAXBuilder saxBuilder   = new SAXBuilder();			
			Element formsElement = new Element("forms");
			Element totalElement = new Element("total");
			
			formsElement.setAttribute("modified", getLocalDateTime());
			formsElement.setAttribute("version", DEFAULT_VERSION);
			formsElement.addContent(totalElement);
			
			LOG.info("파일 length : " + fileList.length);
			for ( int i = 0; i < fileList.length; i++ ) {
				File formFile = fileList[i].getAbsoluteFile();
				Document doc = saxBuilder.build(formFile);
				Element rootElement = doc.getRootElement();
				
				Map<String, Object> xmlMap = new HashMap<String, Object>();
				xmlMap.put("ECODE", formFile.getName().length() > 4 ? formFile.getName().substring(0, formFile.getName().length() - 4) : formFile.getName());
				xmlMap.put("EVERSION", rootElement.getAttributeValue("version"));
				xmlMap.put("ENAME", rootElement.getAttributeValue("nm"));
				xmlMap.put("EIMGNAME", rootElement.getAttributeValue("imgfileNames"));
				xmlMap.put("EDATE", rootElement.getAttributeValue("date"));
				
				Element eFormDoc = generateEdocElement(xmlMap);
				LOG.info("[generateEdocVerCheckXml] Add form : " + formFile.getName());
				formsElement.addContent(eFormDoc);
			}

			Document eDocDoc = new Document();
			eDocDoc.setRootElement(formsElement);
			
			fOS = SerializeEdocVerCheck(eDocDoc);
			
		}catch(JDOMException | IOException e) {
			LOG.error("[generateEdocVerCheckXml] Fail to generate EdocVerCheckXml : {}", e.getMessage());
			throw e;
		} finally {
			fOS.flush();
			fOS.close();
		}
	}

	private FileOutputStream SerializeEdocVerCheck(Document eDocDoc) throws FileNotFoundException, IOException {
		FileOutputStream fOS;
		XMLOutputter serializer = new XMLOutputter();
		Format f = serializer.getFormat();
		f.setEncoding("UTF-8");
		f.setIndent(" ");
		f.setLineSeparator("\r\n");
		f.setTextMode(Format.TextMode.TRIM);
		
		//EDOC_Version.xml 파일 백업
		if( new File(formPath + EDOC_VERSION_FILE_NAME).exists()) {
			LOG.info("[generateEdocVerCheckXml] Backup EDOC_VerCheck.xml ");
			fileCopy(formPath + EDOC_VERSION_FILE_NAME, formPath + EDOC_VERSION_FILE_NAME + BACKUP_SUFFIX );
			LOG.info("[generateEdocVerCheckXml] Success to backup at " + formPath + EDOC_VERSION_FILE_NAME + BACKUP_SUFFIX);
		}
		
		LOG.info("[generateEdocVerCheckXml] Serialize " + formPath + EDOC_VERSION_FILE_NAME);
		fOS = new FileOutputStream(formPath + EDOC_VERSION_FILE_NAME);
		serializer.setFormat(f);
		serializer.output(eDocDoc, fOS);
		LOG.info("[generateEdocVerCheckXml] Success to generate " + formPath + EDOC_VERSION_FILE_NAME);
		return fOS;
	}

	/**
	 * EDOC element를 생성한다.
	 * @param xmlMap EDOC element의 attribute 정보
	 * @return
	 */
	private Element generateEdocElement(Map<String, Object> xmlMap) {
		Element eFormDoc = new Element("EDOC");
		
		eFormDoc.setAttribute("no", nvl((String)xmlMap.get("ECODE")));
		eFormDoc.setAttribute("nm", nvl((String)xmlMap.get("ENAME")));
		eFormDoc.setAttribute("version", nvl((String)xmlMap.get("EVERSION")));
		eFormDoc.setAttribute("pdffileName", nvl((String)xmlMap.get("ECODE") + ".pdf"));
		eFormDoc.setAttribute("xmlfileName", nvl((String)xmlMap.get("ECODE") + ".xml"));
		eFormDoc.setAttribute("imgfileNames", nvl((String)xmlMap.get("EIMGNAME")));
		eFormDoc.setAttribute("group", "");
		eFormDoc.setAttribute("type", "");
		eFormDoc.setAttribute("date", nvl((String)xmlMap.get("EDATE")));
		return eFormDoc;
	}
	
	/**
	 * BIZ_VerCheck.xml 파일을 생성한다.
	 * @param fileNameList	비즈로직 파일명 리스트
	 * @throws Exception
	 */
	private void generateBizVerCheckXml(String[] fileNameList) throws Exception  {
		File[] list = new File[fileNameList.length];
		
		for(int i = 0; i < fileNameList.length; i++){
			File file = new File(bizPath + fileNameList[i]);
			if(file.exists()){
				list[i] = file;
			}
		}
		generateBizVerCheckXml(list);	
	}
	
	/**
	 *  BIZ_VerCheck.xml 파일을 생성한다.
	 * @param fileList
	 * @param bizPath
	 * @throws IOException 
	 * @throws JDOMException 
	 * @throws Exception
	 */
	private synchronized void generateBizVerCheckXml(File[] fileList) throws Exception {
		FileOutputStream fOS    = null;
		
		try {
			SAXBuilder saxBuilder   = new SAXBuilder();
			XMLOutputter serializer = new XMLOutputter();
			Element formsEmelent = new Element("forms");
			formsEmelent.setAttribute("modified", getLocalDateTime());
			formsEmelent.setAttribute("version", DEFAULT_VERSION);
			
			LOG.info("[generateBizVerCheckXml] Bizlogic count : " + fileList.length);
			for ( int i = 0; i < fileList.length; i++ ) {
				File bizFile = fileList[i].getAbsoluteFile();
				Document doc = saxBuilder.build(bizFile);
				Element rootElement = doc.getRootElement();
				
				Element eFormBiz = new Element("BusinessLogic");
				eFormBiz.setAttribute("id", nvl(rootElement.getAttributeValue("id")));
				eFormBiz.setAttribute("version", nvl(rootElement.getAttributeValue("id")));
				eFormBiz.setAttribute("date", getLocalDateTime());
				eFormBiz.setAttribute("title", rootElement.getAttributeValue("title"));
				
				LOG.info("[generateBizVerCheckXml] Add Bizlogic : " + bizFile.getName());
				formsEmelent.addContent(eFormBiz);
			}
			
			LOG.info("파일 갯수 : " + fileList.length);
			Document eDoc = new Document();
			eDoc.setRootElement(formsEmelent);
			
			//BIZ_VerCheck.xml 파일 백업
			if( new File(bizPath + BIZ_VERSION_FILE_NAME).exists()) {				
				LOG.info("[generateBizVerCheckXml] Backup BIZ_VerCheck.xml ");
				fileCopy( bizPath + BIZ_VERSION_FILE_NAME, bizPath + BIZ_VERSION_FILE_NAME + BACKUP_SUFFIX );
				LOG.info("[generateBizVerCheckXml] Success to backup at " + bizPath + BIZ_VERSION_FILE_NAME + BACKUP_SUFFIX);
			}
			
			fOS = new FileOutputStream(bizPath + BIZ_VERSION_FILE_NAME);
			Format f                = null;
			f = serializer.getFormat();
			f.setEncoding("UTF-8");
			f.setIndent(" ");
			f.setLineSeparator("\r\n");
			f.setTextMode(Format.TextMode.TRIM);
			
			serializer.setFormat(f);
			serializer.output(eDoc, fOS);
			LOG.info("generateBizVerCheckXml Success to generate " + bizPath + BIZ_VERSION_FILE_NAME);
		}catch (JDOMException | IOException e) {
			LOG.error("[generateBizVerCheckXml] Fail to generate BizVerCheckXml : {}", e.getMessage());
			throw e;
		} finally {
			fOS.flush();
			fOS.close();
		}
	}
	
	
	/**
	 * 문자열이 null일때 ""를 리턴한다.
	 *
	 * @param obj
	 *            the obj
	 * @return String
	 */
	private String nvl(Object obj) {
		return nvl(obj, "");
	}

	/**
	 * 문자열이 null일때 ""를 리턴한다.
	 *
	 * @param obj
	 * @param ifNull
	 * @return String
	 */
	private String nvl(Object obj, String ifNull) {
		return (obj != null) ? obj.toString() : ifNull;
	}
	
	/**
	 * <p>
	 * 현재 날짜와 시각을 yyyyMMddHHmmss 형태로 변환 후 return.
	 *
	 * @return yyyyMMddHHmmss
	 * @see java.util.Date
	 * @see java.util.Locale <p>
	 */
	private String getLocalDateTime() {
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
		return formatter.format(today);
	}
	
	/**
	 * 파일 복사 
	 * @param inFileName
	 * @param outFileName
	 * @throws IOException 
	 */
	 public static void fileCopy(String inFileName, String outFileName) throws IOException {

		   FileInputStream fis = null;
		   FileOutputStream fos = null;
		   
		   try {
			   fis = new FileInputStream(inFileName);
			   fos = new FileOutputStream(outFileName);
			   
			   int data = 0;
			   while((data=fis.read())!=-1) {
			    fos.write(data);
			   }
		   } catch(IOException e) {
			   throw e;
		   } finally {
			   if(fos != null) fos.close();
			   if(fis != null) fis.close();
		   }
	 }
}

