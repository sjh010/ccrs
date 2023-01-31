package com.inzisoft.xml.parser;

/**
 * XML의 element가 가지는 attribute에 해당하는 클래스
 * 
 * @author	(주)인지소프트 조래훈
 * @version	1.0
 * @date	2018.05.11
 */
public class XmlAttribute
{

	private String name = "";
	private String value = "";

	public XmlAttribute()
	{
		// Nothig to do.
	}

	public XmlAttribute(String name, String value)
	{
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
