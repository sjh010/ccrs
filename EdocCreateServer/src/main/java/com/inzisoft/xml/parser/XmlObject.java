package com.inzisoft.xml.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import com.inzisoft.xml.parser.XmlAttribute;

/**
 * XML을 파싱한 결과를 메모리 상의 tree로 만들어 낼 때 XML의 element에 해당하는 node 클래스
 * 
 * @author	(주)인지소프트 조래훈
 * @version	1.0
 * @date	2018.05.11
 */
public class XmlObject
{
	private String               name = "";
	private Vector<XmlAttribute> attrs = new Vector<XmlAttribute>();
	private String               text = "";
	private Vector<XmlObject>    children = new Vector<XmlObject>();
	private XmlObject            parent = null;
	private int                  indentation = 0;

	public XmlObject()
	{
		// Nothing to do.
	}

	public XmlObject(String name)
	{
		this.name = name;
	}

	public XmlObject(String name, int indentation)
	{
		this.name = name;
		this.indentation = indentation;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Vector<XmlAttribute> getAttrs() {
		return attrs;
	}
	public void setAttrs(Vector<XmlAttribute> attrs) {
		this.attrs = attrs;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public Vector<XmlObject> getChildren() {
		return children;
	}
	public void setChildren(Vector<XmlObject> children) {
		this.children = children;
	}

	public XmlObject getParent() {
		return parent;
	}
	public void setParent(XmlObject parent) {
		this.parent = parent;
	}

	public int getIndentation() {
		return indentation;
	}
	public void setIndentation(int indentation) {
		this.indentation = indentation;
	}

	public void copyTo(XmlObject dest) throws NullPointerException {
		if(dest == null) throw new NullPointerException("XmlObject dest가 null입니다.");

		dest.setName(new String(this.getName()));

		Vector<XmlAttribute> destAttrs = new Vector<XmlAttribute>();
		for(XmlAttribute srcAttr : this.getAttrs()) {
			XmlAttribute destAttr = new XmlAttribute();
			destAttr.setName(new String(srcAttr.getName()));
			destAttr.setValue(new String(srcAttr.getValue()));
			destAttrs.add(destAttr);
		}
		dest.setAttrs(destAttrs);

		dest.setText(new String(this.getText()));

		dest.setIndentation(this.getIndentation());
	}

	/**
	 * 현재 노드와 그 하위 노드에서 nodName, nodeAttrName, nodeAttrValue 값을 가지는 노드를 찾아서 리턴한다.<br>
	 * <br>
	 * 참고 : 노드 = XML element
	 * @param nodeName		노드의 이름
	 * @param nodeAttrName	노드가 가지는 attribute 이름
	 * @param nodeAttrValue	노드가 가지는 nodeAttrName로 지정되는 attribute 의 값
	 * @return	성공 시 XmlObject 타입의 노드. 실패 시 null.
	 */
	public XmlObject find(String nodeName, String nodeAttrName, String nodeAttrValue) {
		if(nodeName.equals(this.getName()) && isAttrExist(nodeAttrName, nodeAttrValue))
			return this;

		for(XmlObject child : children) {
			XmlObject tmpNode = child.find(nodeName, nodeAttrName, nodeAttrValue);
			if(tmpNode != null)
				return tmpNode;
		}

		return null;
	}

	/**
	 * 현재 노드와 그 하위 노드에서 노드의 이름이 nodName 값을 가지는 노드를 찾아서 리턴한다.<br>
	 * <br>
	 * 참고 : 노드 = XML element
	 * @param nodeName		노드의 이름
	 * @return	성공 시 XmlObject 타입의 노드. 실패 시 null.
	 */
	public XmlObject find(String nodeName) {
		if(nodeName.equalsIgnoreCase(this.getName()))
			return this;

		for(XmlObject child : children) {
			XmlObject tmpNode = child.find(nodeName);
			if(tmpNode != null)
				return tmpNode;
		}

		return null;
	}

	private void innerFindAll(String nodeName, List<XmlObject> objects)
	{
		if(nodeName.equalsIgnoreCase(this.getName()))
			objects.add(this);

		for(XmlObject child : children)
			child.innerFindAll(nodeName, objects);
	}

	/**
	 * nodeName으로 지정된 이름을 가지는 모든 xml element들을 찾아서 List로 리턴한다.
	 * @param	nodeName	검색한 xml element의 이름
	 * @return	`nodeName으로 지정된 이름을 가지는 모든 xml element들의 List
	 */
	public List<XmlObject> findAll(String nodeName)
	{
		List<XmlObject> objects = new ArrayList<XmlObject>();
		innerFindAll(nodeName, objects);
		return objects;
	}

	/**
	 * 현재 노드의 attribute 들에서 지정된 이름과 값을 가지는 attribute가 존재하는지 확인한다.
	 * @param attrName
	 * @param attrValue
	 * @return
	 */
	private boolean isAttrExist(String attrName, String attrValue) {
		for(XmlAttribute attr : attrs) {
			if(attrName.equals(attr.getName()) && attrValue.equals(attr.getValue()))
				return true;
		}
		return false;
	}

	/**
	 * 현재 노드의 attribute들 중에서 attrName으로 지정되는 이름을 가지는 attribute의 값을 리턴한다.
	 * @param attrName	attribute의 이름
	 * @return	attribute의 값
	 */
	public String getAttrValue(String attrName) {
		for(XmlAttribute attr : attrs) {
			if(attrName.equals(attr.getName())) {
				return attr.getValue();
			}
		}
		return null;
	}

	public String toString(boolean bIncludeChild, boolean bIndentation)
	{
		StringBuilder sb = new StringBuilder();

		// ROOT인 경우
		if((this.name == null || "".equals(this.name)) && parent == null)
		{
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

			if(bIncludeChild)
			{
				List<XmlObject> childern = getChildren();
				for(int childIndex = 0; childIndex < childern.size(); ++childIndex)
				{
					if(bIndentation) { sb.append("\n"); }
					XmlObject child = childern.get(childIndex);
					sb.append(child.toString(bIncludeChild, bIndentation));
				}
			}
		}
		else
		{
			if(bIndentation) { for(int ind = 0; ind < getIndentation(); ++ind) { sb.append("  "); } }
	
			sb.append("<").append(getName());
			Vector<XmlAttribute> formFieldAttrs = getAttrs();
			for(int j = 0; j < formFieldAttrs.size(); ++j)
			{
				XmlAttribute formFieldAttr = formFieldAttrs.get(j);
				sb.append(" ").append(formFieldAttr.getName()).append("=\"").append(formFieldAttr.getValue()).append("\"");
			}
			sb.append(">");

			boolean bNewLine = false;

			if(getText() != null && !"".equals(getText())) {
				if(bIndentation && children != null && children.size() > 0)
				{
					sb.append("\n");
					for(int ind = 0; ind < getIndentation() + 1; ++ind) { sb.append("  "); }
					bNewLine = true;
				}
				sb.append(getText());
			}
	
			if(bIncludeChild)
			{
				List<XmlObject> childern = getChildren();
				for(int childIndex = 0; childIndex < childern.size(); ++childIndex)
				{
					if(bIndentation) { sb.append("\n"); bNewLine = true; }
					XmlObject child = childern.get(childIndex);
					sb.append(child.toString(bIncludeChild, bIndentation));
				}
			}
	
			if(bNewLine) { sb.append("\n"); for(int ind = 0; ind < getIndentation(); ++ind) { sb.append("  "); } }
			sb.append("</").append(getName()).append(">");
		}

		return sb.toString();
	}

	public XmlObject addAttr(String name, String value)
	{
		XmlAttribute newAttr = new XmlAttribute(name, value);
		attrs.add(newAttr);
		return this;
	}

	public XmlObject addChild(XmlObject child)
	{
		children.add(child);
		return this;
	}
}
