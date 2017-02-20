package br.com.rhiemer.api.util.format.converter;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import br.com.rhiemer.api.util.constantes.ConstantesAPI;

public class ConverterXML implements IConverter {

	@Override
	public <T> String converter(T obj, boolean pretty) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, ConstantesAPI.ENCONDING_PADRAO);
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, pretty);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(obj, sw);
			return sw.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> T parseString(Class<T> classe, String str) {
		T t;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(classe);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			StringReader sr = new StringReader(str);
			t = (T) jaxbUnmarshaller.unmarshal(sr);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
		return t;
	}

}
