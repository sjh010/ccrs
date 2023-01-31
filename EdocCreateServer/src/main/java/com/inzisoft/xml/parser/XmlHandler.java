package com.inzisoft.xml.parser;

import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX(Simple API for XML)를 이용하여 XML 파일을 파싱할 때 발생하는 이벤트 처리 클래스.<br>
 * SAX(Simple API for XML)를 이용하여 XML 파일을 파싱할 때, 
 * element의 시작, element의 끝, element의 시작과 끝 사이의 문자를 발견했다는 이벤트가 발생하였을 때 호출되는 핸들러 클래스.<br>
 * 이벤트의 발생 시 각 함수를 이용하여 XML을 파싱한 결과를 element node로 구성되는 tree로 만들어낸다.
 * 
 * @author	(주)인지소프트 조래훈
 * @version	1.0
 * @date	2018.05.11
 */
public class XmlHandler extends DefaultHandler
{
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(XmlHandler.class);

	private XmlObject root = new XmlObject();

	private int indentation = 0;

	private XmlObject cursor = root;

	public void startElement(String uri, String name, String qName, Attributes atts)
	{
		indentation++;

//		////////////////////////////////////////////////////////////////////////
//		// Just for debug
//		String leading = ""; for(int ind = 0 ; ind < indentation ; ind++) leading += "  ";
//		String attributes = "";
//		for(int i = 0 ; i < atts.getLength() ; i++)
//		{
//			attributes += atts.getLocalName(i) + "=" + atts.getValue(i) + ", ";
//		}
//		logger.debug("\t" + leading + "[startElement," + indentation + "] name=" + name + ", " + "atts={" + attributes + "}");
//		////////////////////////////////////////////////////////////////////////

		XmlObject obj = new XmlObject();
		obj.setName(name);
		for(int i = 0 ; i < atts.getLength() ; i++)
		{
			XmlAttribute attr = new XmlAttribute();
			attr.setName(atts.getLocalName(i));
			attr.setValue(atts.getValue(i));
			obj.getAttrs().add(attr);
		}
		obj.setIndentation(indentation);

		XmlObject parent = cursor;
		if(parent.getIndentation() < obj.getIndentation())
		{
			// child에 추가
			// Nothing to do.
		}
		else if(parent.getIndentation() == obj.getIndentation())
		{
			// sibling에 추가
			parent = parent.getParent();
		}
		else
		{
			// parent의 sibling으로 추가
			parent = parent.getParent().getParent();
			while(parent.getIndentation() + 1 > obj.getIndentation())
				parent = parent.getParent();
		}

		obj.setParent(parent);
		parent.getChildren().add(obj);
		cursor = obj;
	}

	public void endElement(String uri, String name, String qName)
	{
//		////////////////////////////////////////////////////////////////////////
//		// Just for debug
//		String leading = ""; for(int ind = 0 ; ind < indentation ; ind++) leading += "  ";
//		logger.debug("\t" + leading + "[endElement," + indentation + "] name=" + name);
//		////////////////////////////////////////////////////////////////////////

		indentation--;
	}

	public void characters(char[] ch, int start, int length)
	{
		String tmpStr = "";
		boolean isAllWhiteSpace = true;
		for(int i = start ; i < start + length ; i++)
		{
			if(ch[i] != ' ' && ch[i] != '\t' && ch[i] != '\n')
				isAllWhiteSpace = false;
			tmpStr += ch[i];
		}
		if(isAllWhiteSpace) return;

		cursor.setText(tmpStr);

//		////////////////////////////////////////////////////////////////////////
//		// Just for debug
//		String leading = ""; for(int ind = 0 ; ind < indentation ; ind++) leading += "  ";
//		logger.debug("\t" + leading + "[characters," + indentation + "] characters=\"" + tmpStr + "\"");
//		////////////////////////////////////////////////////////////////////////
	}

	public XmlObject getRoot()
	{
		return root;
	}

	public void setRoot(XmlObject root)
	{
		this.root = root;
	}

	public void print(XmlObject obj, String charset, boolean bIndent)
	{
		if(obj == null) return;

		if(obj == root)
		{
			System.out.println("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>");

			Vector<XmlObject> children = obj.getChildren();
			for(XmlObject child : children)
				this.print(child, charset, bIndent);
		}
		else
		{
			Vector<XmlObject> children = obj.getChildren();

			String newLine = "";
			if(children != null && children.size() > 0) newLine = "\n";

			// Element start
			if(bIndent) for(int ind = 0 ; ind < obj.getIndentation() ; ind++) System.out.print("  ");
			System.out.print("(" + obj.getIndentation() + ")" + "<" + obj.getName());
			for(int i = 0 ; i < obj.getAttrs().size() ; i++)
				System.out.print(" " + obj.getAttrs().elementAt(i).getName() + "=\"" + obj.getAttrs().elementAt(i).getValue() + "\"");
			System.out.print(">" + newLine);
	
			// Element text
			if("".equals(obj.getText()) == false)
			{
				if(bIndent && !"".equals(newLine)) for(int ind = 0 ; ind < obj.getIndentation() + 1 ; ind++) System.out.print("  ");
				if("JavaScript".equals(obj.getName()))
					System.out.print("<![CDATA[" + obj.getText() + "]]>" + newLine);
				else
					System.out.print(obj.getText() + newLine);
			}

			// Element children
			for(XmlObject child : children)
				this.print(child, charset, bIndent);

			// Element end
			if(bIndent && !"".equals(newLine)) for(int ind = 0 ; ind < obj.getIndentation() ; ind++) System.out.print("  ");
			System.out.print("(" + obj.getIndentation() + ")" + "</" + obj.getName() + ">\n");
		}
	}

	// TODO : NewLine, Space(TAB 포함) 문자 일단 막고, 나중에 풀자.
	public void toString(StringBuilder sb, XmlObject obj)
	{
		if(obj == null) return;

		if(obj == root)
		{
			//sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

			Vector<XmlObject> children = obj.getChildren();
			for(XmlObject child : children)
				this.toString(sb, child);
		}
		else
		{
			Vector<XmlObject> children = obj.getChildren();

			String newLine = "";
			//if(children != null && children.size() > 0) newLine = "\n";
			if(children != null && children.size() > 0) newLine = "";

			//for(int ind = 0 ; ind < obj.getIndentation() ; ind++) sb.append("  ");
			sb.append("<" + obj.getName());
			for(int i = 0 ; i < obj.getAttrs().size() ; i++)
				sb.append(" " + obj.getAttrs().elementAt(i).getName() + "=\"" + obj.getAttrs().elementAt(i).getValue() + "\"");
			sb.append(">" + newLine);
	
			if("".equals(obj.getText()) == false)
			{
//				if("".equals(newLine) == false)
//					for(int ind = 0 ; ind < obj.getIndentation() + 1 ; ind++) sb.append("  ");
				if("JavaScript".equals(obj.getName()))
					// TODO : 테스트를 위해서 막았다. 나중에 풀어줄 것
					//sb.append("<![CDATA[" + obj.getText() + "]]>" + newLine);
					sb.append("");
				else
					sb.append(obj.getText() + newLine);
			}

			for(XmlObject child : children)
				this.toString(sb, child);

//			if("".equals(newLine) == false)
//				for(int ind = 0 ; ind < obj.getIndentation() ; ind++) sb.append("  ");
			//sb.append("</" + obj.getName() + ">\n");
			sb.append("</" + obj.getName() + ">");
		}
	}
}
