package br.com.rhiemer.api.rest.resources.list;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;

import br.com.rhiemer.api.util.format.converter.ConverterFacotry;
import br.com.rhiemer.api.util.format.json.FormatFacotry;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.lambda.optinal.HelperOptional;

public class HelperMockHttp {

	private HelperMockHttp() {

	}

	public static MockHttpResponse invokeGet(Dispatcher dispatcher, String url, String mediaType) {
		MockHttpResponse response = new MockHttpResponse();
		MockHttpRequest request = null;
		try {
			request = MockHttpRequest.get(url).accept(mediaType);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		dispatcher.invoke(request, response);
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			throw new RuntimeException(String.format("Erro ao listar Resources.\nStatus:%s\nMesnagem:%s",
					response.getStatus(), response.getContentAsString()));
		}
		return response;

	}

	public static Dispatcher createDispatcher(Class<?>... classes) {
		Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
		Helper.convertArgs(classes).forEach(t -> dispatcher.getRegistry().addSingletonResource(Helper.newInstance(t)));
		return dispatcher;
	}

	public static MockHttpResponse invokeGet(String url, String mediaType, Class<?>... classes) {
		Dispatcher dispatcher = createDispatcher(classes);
		return invokeGet(dispatcher, url, mediaType);
	}
	
	public static <T> T convertResponseToObj(MockHttpResponse response,Class<T> classe)
	{
		T obj=null;
		try {
			obj = Helper.newInstanceByStream(response.getOutputStream(), classe);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return obj;
	}
	
	public static <T> T convertResponseToObjStr(MockHttpResponse response,Class<T> classe,String mediaType)
	{
		T obj = ConverterFacotry.converter(mediaType).parseString(classe,response.getContentAsString());
		return obj;
	}
	
	public static <T> T invokeGetObj(String url,String mediaType,Class<T> classe,Class<?>... classes) {
		MockHttpResponse response = invokeGet(url, mediaType,classes);
		return convertResponseToObj(response,classe);		
	}

	public static <T> T invokeGetObj(Dispatcher dispatcher, String url, String mediaType, Class<T> classe,
			Class<?>... classes) {
		MockHttpResponse response = invokeGet(dispatcher, url, mediaType);
		return convertResponseToObj(response,classe);

	}
	
	public static <T> T invokeGetObjStr(String url,String mediaType,Class<T> classe,Class<?>... classes) {
		MockHttpResponse response = invokeGet(url, mediaType,classes);
		return convertResponseToObjStr(response,classe,mediaType);		
	}

	public static <T> T invokeGetObjStr(Dispatcher dispatcher, String url, String mediaType, Class<T> classe,
			Class<?>... classes) {
		MockHttpResponse response = invokeGet(dispatcher, url, mediaType);
		return convertResponseToObjStr(response,classe,mediaType);

	}

	public static String invokeGetStr(Dispatcher dispatcher, String url, String mediaType, boolean pretty) {
		MockHttpResponse response = invokeGet(dispatcher, url, mediaType);
		String result = response.getContentAsString();
		return !pretty ? result
				: HelperOptional.mapIsBlank(result, t -> FormatFacotry.builder(mediaType).formatar(t)).get();

	}

	public static String invokeGetStr(String url, String mediaType, boolean pretty, Class<?>... classes) {
		MockHttpResponse response = invokeGet(url, mediaType, classes);
		String result = response.getContentAsString();
		return !pretty ? result
				: HelperOptional.mapIsBlank(result, t -> FormatFacotry.builder(mediaType).formatar(t)).get();

	}

	public static String invokeGetStr(Dispatcher dispatcher, String url, String mediaType) {
		return invokeGetStr(dispatcher, url, mediaType, false);
	}

	public static String invokeGetStr(String url, String mediaType, Class<?>... classes) {
		return invokeGetStr(url, mediaType, false, classes);

	}

}
