package com.up24.cgieditor.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.up24.cgieditor.model.Cgi;
import com.up24.cgieditor.model.Cgi.Arg;
import com.up24.cgieditor.common.Constants;
import com.up24.cgieditor.common.Logger;

public class CgiDao {
	
	private static final Logger logger = Logger.getLogger();
	
	public List<Cgi> getCgis() {
		List<Cgi> cgiList = new ArrayList<Cgi>();
		try {
			File cgiXml = new File(getCgiXmlPath());
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(cgiXml);
			Element cgisElement = document.getDocumentElement();
			NodeList cgiNodeList = cgisElement.getChildNodes();
			Cgi cgi = null;
			Arg arg = null;
			for (int i = 0, cgiLen = cgiNodeList.getLength(); i < cgiLen; i++) {
				Node cgiNode = cgiNodeList.item(i);
				if (Node.ELEMENT_NODE != cgiNode.getNodeType()) {
					continue;
				}
				NamedNodeMap cgiAttrs = cgiNode.getAttributes();
				cgi = new Cgi();
				cgi.setName(cgiAttrs.getNamedItem("name").getNodeValue());
				cgi.setType(cgiAttrs.getNamedItem("type").getNodeValue());
				cgi.setComment(cgiAttrs.getNamedItem("comment").getNodeValue());
				cgi.setAuthor(cgiAttrs.getNamedItem("author").getNodeValue());
				cgi.setNeedLogin(cgiAttrs.getNamedItem("needLogin").getNodeValue());
				cgi.setArgList(new ArrayList<Arg>());
				NodeList argNodeList = cgiNode.getChildNodes();
				for (int j = 0, argLen = argNodeList.getLength(); j < argLen; j++) {
					Node argNode = argNodeList.item(j);
					if (Node.ELEMENT_NODE != argNode.getNodeType()) {
						continue;
					}
					NamedNodeMap argAttrs = argNode.getAttributes();
					arg = new Arg();
					arg.setName(argAttrs.getNamedItem("name").getNodeValue());
					arg.setFrom(argAttrs.getNamedItem("origin").getNodeValue());
					arg.setIsRequire(argAttrs.getNamedItem("required").getNodeValue());
					arg.setType(argAttrs.getNamedItem("type").getNodeValue());
					arg.setAllowHtml(argAttrs.getNamedItem("allowHtml").getNodeValue());
					arg.setTypeDesc(argAttrs.getNamedItem("typeDesc").getNodeValue());
					arg.setComment(argAttrs.getNamedItem("comment").getNodeValue());
					cgi.getArgList().add(arg);
				}
				cgiList.add(cgi);
			}
		} catch (Exception e) {
			logger.error("getCgis failed", e);
			return null;
		}
		logger.info("getCgis succeed");
		return cgiList;
	}
	
	public boolean setCgisByDomApi(List<Cgi> cgiList) {
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.newDocument();
			document.setXmlStandalone(true);
			Element cgisElement = document.createElement("cgis");
			cgisElement.setAttribute("xmlns", "http://www.up24.com/cgischema");
			cgisElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			cgisElement.setAttribute("xsi:schemaLocation", "http://www.up24.com/cgischema cgi.xsd");
			document.appendChild(cgisElement);
			Element cgiElement = null;
			Element argElement = null;
			for (Cgi cgi : cgiList) {
				cgiElement = document.createElement("cgi");
				cgiElement.setAttribute("name", cgi.getName());
				cgiElement.setAttribute("type", cgi.getType());
				cgiElement.setAttribute("comment", cgi.getComment());
				cgiElement.setAttribute("author", cgi.getAuthor());
				cgiElement.setAttribute("needLogin", cgi.getNeedLogin());
				for (Arg arg : cgi.getArgList()) {
					argElement = document.createElement("param");
					argElement.setAttribute("name", arg.getName());
					argElement.setAttribute("origin", arg.getFrom());
					argElement.setAttribute("required", arg.getIsRequire());
					argElement.setAttribute("type", arg.getType());
					argElement.setAttribute("allowHtml", arg.getAllowHtml());
					argElement.setAttribute("typeDesc", arg.getTypeDesc());
					argElement.setAttribute("comment", arg.getComment());
					cgiElement.appendChild(argElement);
				}
				cgisElement.appendChild(cgiElement);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformerFactory.setAttribute("indent-number", 2);
			Transformer transformer = transformerFactory.newTransformer();
	        DOMSource source = new DOMSource(document);
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        PrintWriter pw = new PrintWriter(new FileOutputStream(getCgiXmlPath()));
	        StreamResult result = new StreamResult(pw);
	        transformer.transform(source, result);
		} catch (Exception e) {
			logger.error("setCgis failed", e);
			return false;
		}
		logger.info("setCgis succeed");
		return true;
	}
	
	public boolean setCgis(List<Cgi> cgiList) {
		try {
			XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
			XMLStreamWriter streamWriter = outputFactory.createXMLStreamWriter(new FileOutputStream(getCgiXmlPath()), "UTF-8");
			streamWriter.writeStartDocument("UTF-8", "1.0");
			streamWriter.writeCharacters("\n");
			streamWriter.writeStartElement("cgis");
			streamWriter.writeAttribute("xmlns", "http://www.up24.com/cgischema");
			streamWriter.writeAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			streamWriter.writeAttribute("xsi:schemaLocation", "http://www.up24.com/cgischema cgi.xsd");
			streamWriter.writeCharacters("\n");
			for (Cgi cgi : cgiList) {
				streamWriter.writeStartElement("cgi");
				streamWriter.writeAttribute("name", cgi.getName());
				streamWriter.writeAttribute("type", cgi.getType());
				streamWriter.writeAttribute("comment", cgi.getComment());
				streamWriter.writeAttribute("author", cgi.getAuthor());
				streamWriter.writeAttribute("needLogin", cgi.getNeedLogin());;
				streamWriter.writeCharacters("\n");
				for (Arg arg : cgi.getArgList()) {
					streamWriter.writeCharacters("\t");
					streamWriter.writeEmptyElement("param");
					streamWriter.writeAttribute("name", arg.getName());
					streamWriter.writeAttribute("origin", arg.getFrom());
					streamWriter.writeAttribute("required", arg.getIsRequire());
					streamWriter.writeAttribute("type", arg.getType());
					streamWriter.writeAttribute("allowHtml", arg.getAllowHtml());
					streamWriter.writeAttribute("typeDesc", arg.getTypeDesc());
					streamWriter.writeAttribute("comment", arg.getComment());
					streamWriter.writeCharacters("\n");
				}
				streamWriter.writeEndElement();
				streamWriter.writeCharacters("\n");
			}
			streamWriter.writeEndElement();
			streamWriter.writeEndDocument();
			streamWriter.flush();
			streamWriter.close();
		} catch (Exception e) {
			logger.error("setCgis failed", e);
			return false;
		}
		logger.info("setCgis succeed");
		return true;
	}
	
	private String getCgiXmlPath() {
		return this.getClass().getClassLoader().getResource(Constants.EMPTY_STRING).getPath() + Constants.CGI_XML_PATH;
	}
}
