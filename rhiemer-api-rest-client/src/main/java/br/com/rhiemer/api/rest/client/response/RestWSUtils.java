package br.com.rhiemer.api.rest.client.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import br.com.rhiemer.api.rest.client.log.LoggingRequestFilter;
import br.com.rhiemer.api.rest.client.log.LoggingResponseFilter;

/**
 * Classe com funcionalidades utilitárias ao consumo de serviços REST
 * 
 * @author rodrigo.hiemer
 * 
 */
public class RestWSUtils {

	/**
	 * Faz um request com método HTTP GET no formato JSON com log das
	 * informações do request
	 * 
	 * @param url
	 *            URL destino da requisição
	 * 
	 * @return Instância da resposta HTTP
	 */
	public static Response requestGETJSONLog(String url) {
		Response response = null;
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(url);
		target.register(LoggingRequestFilter.class);
		target.register(LoggingResponseFilter.class);
		response = target.request(MediaType.APPLICATION_JSON).get();
		return response;
	}

	/**
	 * Faz um request com método HTTP POST no formato JSON com log das
	 * informações do request
	 * 
	 * @param url
	 *            URL destino da requisição
	 * @param body
	 *            Corpo do POST da requisição
	 * @param formato
	 *            Formato JSON ou XML
	 * 
	 * @return Instância da resposta HTTP
	 */
	public static Response requestPOSTLog(String url, Serializable body, String formato) {
		Response response = null;
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(url);
		target.register(LoggingRequestFilter.class);
		target.register(LoggingResponseFilter.class);
		response = target.request(formato).post(Entity.entity(body, formato));
		return response;
	}

	/**
	 * Faz um request com método HTTP GET no formato JSON
	 * 
	 * @param url
	 *            URL destino da requisição
	 * 
	 * @return Instância da resposta HTTP
	 */
	public static Response requestGETJSON(String url) {
		Response response = null;
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(url);
		verificaLog(target);
		response = target.request(MediaType.APPLICATION_JSON).get();
		return response;
	}

	/**
	 * Faz um request com método HTTP GET no formato XML
	 * 
	 * @param url
	 *            URL destino da requisição
	 * 
	 * @return Instância da resposta HTTP
	 */
	public static Response requestGETXML(String url) {
		Response response = null;
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(url);
		verificaLog(target);
		response = target.request(MediaType.APPLICATION_XML).get();
		return response;
	}

	/**
	 * Faz um request com método HTTP TRACE no formato JSON
	 * 
	 * @param url
	 *            URL destino da requisição
	 * @param formato
	 *            formato JSON ou XML
	 * 
	 * @return Instância da resposta HTTP
	 */
	public static Response requestTRACE(String url, String formato) {
		Response response = null;
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(url);
		verificaLog(target);
		response = target.request(formato).trace();
		return response;
	}

	/**
	 * Faz um request com método HTTP DELETE no formato JSON
	 * 
	 * @param url
	 *            URL destino da requisição
	 * @param formato
	 *            Formato JSON ou XML
	 * 
	 * @return Instância da resposta HTTP
	 */
	public static Response requestDELETE(String url, String formato) {
		Response response = null;
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(url);
		verificaLog(target);
		response = target.request(formato).delete();
		return response;
	}

	/**
	 * Faz um request com método HTTP POST no formato JSON
	 * 
	 * @param url
	 *            URL destino da requisição
	 * @param body
	 *            Corpo do POST da requisição
	 * @param formato
	 *            Formato JSON ou XML
	 * 
	 * @return Instância da resposta HTTP
	 */
	public static Response requestPOST(String url, Serializable body, String formato) {
		Response response = null;
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(url);
		verificaLog(target);
		response = target.request(formato).post(Entity.entity(body, formato));
		return response;
	}

	/**
	 * Faz um request com método HTTP POST no formato JSON verificando status
	 * 
	 * @param url
	 *            URL destino da requisição
	 * @param body
	 *            Corpo do POST da requisição
	 * @param formato
	 *            Formato JSON ou XML
	 * 
	 * @return Instância da resposta HTTP
	 */
	public static Response requestPOSTVerificandoStatus(String url, Serializable body, String formato, int status) {
		Response response = requestPOST(url, body, formato);
		assertNotNull("Verificando se o response retornado é nulo", response);
		String mensagem = "Verificando o codigo de retorno da requisição.";
		if (response.getStatus() != status) {
			String resposta = response.readEntity(String.class);
			mensagem = String.format(mensagem.concat(" Resposta=[%s]"), resposta);
		}
		assertEquals(mensagem, response.getStatus(), status);
		return response;
	}

	/**
	 * Faz um request com método HTTP POST no formato JSON verificando status
	 * 
	 * @param url
	 *            URL destino da requisição
	 * @param body
	 *            Corpo do POST da requisição
	 * @param formato
	 *            Formato JSON ou XML
	 * 
	 * @return Instância da resposta HTTP
	 */
	public static <T> T resultadoPOSTVerificandoStatus(String url, Serializable body, String formato, int status,
			Class<T> classeResponse) {
		Response response = requestPOSTVerificandoStatus(url, body, formato, status);
		T obj = response.readEntity(classeResponse);
		assertNotNull("Resultado da resposta é nulo", obj);
		return obj;
	}

	/**
	 * Faz um request com método HTTP POST no formato JSON
	 * 
	 * @param url
	 *            URL destino da requisição
	 * @param body
	 *            Corpo do GET da requisição
	 * @param formato
	 *            Formato JSON ou XML
	 * 
	 * @return Instância da resposta HTTP
	 */
	public static Response requestGET(String url, String formato) {
		Response response = null;
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(url);
		verificaLog(target);
		response = target.request(formato).get();
		return response;
	}

	/**
	 * Faz um request com método HTTP POST no formato JSON verificando o status
	 * da resposta
	 * 
	 * @param url
	 *            URL destino da requisição
	 * @param body
	 *            Corpo do GET da requisição
	 * @param formato
	 *            Formato JSON ou XML
	 * 
	 * @return Instância da resposta HTTP
	 */
	public static Response requestGET(String url, String formato, int status) {
		Response response = null;
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(url);
		verificaLog(target);
		response = target.request(formato).get();
		assertNotNull("Verificando se o response retornado é nulo", response);

		String mensagem = "Verificando o codigo de retorno da requisição.";
		if (response.getStatus() != status) {
			String resposta = response.readEntity(String.class);
			mensagem = String.format(mensagem.concat(" Resposta=[%s]"), resposta);
		}

		assertEquals(mensagem, response.getStatus(), status);
		return response;
	}

	/**
	 * Faz um request com método HTTP POST no formato JSON verificando status
	 * 
	 * @param url
	 *            URL destino da requisição
	 * @param body
	 *            Corpo do POST da requisição
	 * @param formato
	 *            Formato JSON ou XML
	 * 
	 * @return Instância da resposta HTTP
	 */
	public static <T> T resultadoGETVerificandoStatus(String url, String formato, int status, Class<T> classeResponse) {
		Response response = requestGET(url, formato, status);
		T obj = response.readEntity(classeResponse);
		assertNotNull("Resultado da resposta é nulo", obj);

		return obj;
	}

	private static void verificaLog(ResteasyWebTarget target) {
		target.register(LoggingRequestFilter.class);
		target.register(LoggingResponseFilter.class);
	}
}
