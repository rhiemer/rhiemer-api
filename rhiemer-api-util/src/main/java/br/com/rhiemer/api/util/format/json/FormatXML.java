package br.com.rhiemer.api.util.format.json;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class FormatXML implements IFormat {

	@Override
	public String formatar(String xml) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = factory.newDocumentBuilder();
			Document doc;
			doc = parser.parse(xml);
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer idTransform = transFactory.newTransformer();
			idTransform.setOutputProperty(OutputKeys.METHOD, "xml");
			idTransform.setOutputProperty(OutputKeys.INDENT, "yes");
			idTransform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			StringWriter sw = new StringWriter();
			
			Source input = new DOMSource(doc);
			Result output = new StreamResult(sw);
			idTransform.transform(input, output);
			String xmlFormatado = sw.toString();
			return xmlFormatado;			
		} catch (Exception e) {
			return xml;
		}
	}


}
