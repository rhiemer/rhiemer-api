package br.com.rhiemer.api.util.format.json;

import java.io.ByteArrayInputStream;
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
import org.xml.sax.InputSource;

import br.com.rhiemer.api.util.constantes.ConstantesAPI;

public class FormatXML implements IFormat {

	@Override
	public String formatar(String xml) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder parser = factory.newDocumentBuilder();
			Document doc;
			doc = parser.parse(new InputSource(
					new ByteArrayInputStream(xml.getBytes(ConstantesAPI.ENCONDING_PADRAO.toLowerCase()))));
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer idTransform = transFactory.newTransformer();
			idTransform.setOutputProperty(OutputKeys.METHOD, "xml");
			idTransform.setOutputProperty(OutputKeys.INDENT, "yes");
			idTransform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

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
