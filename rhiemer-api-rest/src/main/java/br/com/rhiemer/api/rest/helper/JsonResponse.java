package br.com.rhiemer.api.rest.helper;

import static br.com.rhiemer.api.util.helper.ConstantesAPI.MEDIA_REST_JSON;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/***
 * Métodos utilitários para respostas JSON
 * 

 *
 */
public class JsonResponse {

	/***
	 * Constrói uma resposta com um cabeçalho Content-Type especificando UTF8
	 * como charset
	 * 
	 * @param status
	 *            Status HTTP
	 * @param entity
	 *            Entidade a ser gerado o JSON na resposta
	 * @return Response com formatação solicitada
	 */
	public static Response respondeUTF8(Status status, Object entity) {
		return Response.status(status)
				.header(HttpHeaders.CONTENT_TYPE, MEDIA_REST_JSON)
				.entity(entity).build();
	}

	/***
	 * Constrói uma resposta com um cabeçalho Content-Type especificando UTF8
	 * como charset e do tipo OK
	 * 
	 * @param status
	 *            Status HTTP
	 * @param entity
	 *            Entidade a ser gerado o JSON na resposta
	 * @return Response com formatação solicitada
	 */
	public static Response respondeOK() {
		return Response.status(Response.Status.OK)
				.header(HttpHeaders.CONTENT_TYPE, MEDIA_REST_JSON)
				.build();
	}

	/***
	 * Constrói uma resposta com um cabeçalho Content-Type especificando UTF8
	 * como charset e do tipo OK
	 * 
	 * @param status
	 *            Status HTTP
	 * @param entity
	 *            Entidade a ser gerado o JSON na resposta
	 * @return Response com formatação solicitada
	 */
	public static Response respondeOK(Object entity) {
		return Response.status(Response.Status.OK)
				.header(HttpHeaders.CONTENT_TYPE, MEDIA_REST_JSON)
				.entity(entity).build();
	}

	/***
	 * Constrói uma resposta com um HTTP Status 405: Method Not Allowed
	 * 
	 * @return Response 405
	 */
	public static Response respondeHTTPStatus405() {
		return Response.status(405)
				.header(HttpHeaders.CONTENT_TYPE, MEDIA_REST_JSON)
				.build();
	}

	/***
	 * Constrói uma resposta com um HTTP Status NoContent: NoContent
	 * 
	 * @return Response 204
	 */
	public static Response respondeHTTPStatusNoContent(Object... t) {
		return Response.status(Response.Status.NO_CONTENT)
				.header(HttpHeaders.CONTENT_TYPE, MEDIA_REST_JSON)
				.entity(t).build();
	}

	/***
	 * Constrói uma resposta com um HTTP Status NoContent: NoContent
	 * 
	 * @return Response 404
	 */
	public static Response respondeHTTPStatusNotFound(String complemento) {
		ErroRetornoDTO erroRetornoDTO = new ErroRetornoDTO("DadosInexistentes",
				"A consulta não retornou resultados.", complemento);
		return Response.status(Response.Status.NOT_FOUND)
				.header(HttpHeaders.CONTENT_TYPE, MEDIA_REST_JSON)
				.entity(erroRetornoDTO).build();
	}

	/***
	 * Constrói uma resposta com um HTTP Status Bad Request
	 * 
	 * @return Response 400
	 */
	public static Response respondeHTTPStatusBadRequest(String complemento) {
		ErroRetornoDTO erroRetornoDTO = new ErroRetornoDTO(
				"ParametroInvalido",
				"O tipo do argumento informado pelo usuário não corresponde ao esperado pela interface.",
				complemento);
		return Response.status(Response.Status.BAD_REQUEST)
				.header(HttpHeaders.CONTENT_TYPE, MEDIA_REST_JSON)
				.entity(erroRetornoDTO).build();
	}

	/***
	 * Constrói uma resposta com um HTTP Status Server Error: Server Error
	 * 
	 * @return Response 404
	 */
	public static Response respondeHTTPStatusServerError(String complemento) {
		ErroRetornoDTO erroRetornoDTO = new ErroRetornoDTO("ServerError",
				"Erro de processamento interno.", complemento);
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.header(HttpHeaders.CONTENT_TYPE, MEDIA_REST_JSON)
				.entity(erroRetornoDTO).build();
	}

	/***
	 * Constrói uma resposta com um HTTP Status 401: Unauthorized
	 * 
	 * @return Response 401
	 */
	public static Response respondeHTTPStatus401() {
		return Response.status(Response.Status.UNAUTHORIZED)
				.header(HttpHeaders.CONTENT_TYPE, MEDIA_REST_JSON)
				.build();
	}

	/***
	 * Constrói uma resposta com um HTTP Status 403: Forbidden
	 * 
	 * @return Response 403
	 */
	public static Response respondeHTTPStatus403() {
		return Response.status(Response.Status.FORBIDDEN)
				.header(HttpHeaders.CONTENT_TYPE, MEDIA_REST_JSON)
				.build();
	}

	/***
	 * Constrói uma resposta com um HTTP Status 422: Unprocessable Entity (RFC
	 * 4918)
	 * 
	 * The 422 (Unprocessable Entity) status code means the server understands
	 * the content type of the request entity (hence a 415(Unsupported Media
	 * Type) status code is inappropriate), and the syntax of the request entity
	 * is correct (thus a 400 (Bad Request) status code is inappropriate) but
	 * was unable to process the contained instructions. For example, this error
	 * condition may occur if an XML request body contains well-formed (i.e.,
	 * syntactically correct), but semantically erroneous, XML instructions.
	 * 
	 * @param entity
	 *            Entidade a ser gerado o JSON na resposta
	 * 
	 * @return Response 422
	 */
	public static Response respondeHTTPStatusUnprocessableEntity(Object entity) {
		return Response.status(422)
				.header(HttpHeaders.CONTENT_TYPE, MEDIA_REST_JSON)
				.entity(entity).build();
	}
}
