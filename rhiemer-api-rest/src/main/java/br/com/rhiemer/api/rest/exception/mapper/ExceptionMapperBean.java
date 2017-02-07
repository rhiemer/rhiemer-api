package br.com.rhiemer.api.rest.exception.mapper;

import static br.com.rhiemer.api.util.helper.ConstantesAPI.BASE_PACKAGE;

import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.reflections.Reflections;

import br.com.rhiemer.api.util.annotations.app.ReflectionBasePackage;

/**
 * Devido a ser mapear no JAX-RS o provider Exception foi desenvolvida uma
 * solução de chamada ao provider correto da exceção, não utilizando sempre o
 * provider genérico
 * 
 * @author rodrigo.hiemer
 *
 */
@ApplicationScoped
public class ExceptionMapperBean {


	@Inject
	private transient Instance<Object> creator;

	private Map<Class<? extends Throwable>, Class<? extends ExceptionMapper<?>>> classesExceptionMapper = new LinkedHashMap<>();
	
	@Inject
	@ReflectionBasePackage
	private Reflections reflections;

	/**
	 * Referncia o provider com a exceção
	 */
	@PostConstruct
	private void init() {

		Set<Class<? extends ExceptionMapper>> clazzs = reflections.getSubTypesOf(ExceptionMapper.class);

		for (Class<?> clazz : clazzs) {
			adicionarExceptionMapper((Class<? extends ExceptionMapper<?>>) clazz);
		}

	}

	private void adicionarExceptionMapper(Class<? extends ExceptionMapper<?>> clazzExceptionMapper) {
		Class<? extends Throwable> clazzException = (Class<? extends Throwable>) ((ParameterizedType) clazzExceptionMapper
				.getGenericInterfaces()[0]).getActualTypeArguments()[0];

		//TO-DO: Tratar caso tenha uma hierarquiezação de uma exceção com a outra
		if (!clazzException.equals(Exception.class))
			classesExceptionMapper.put(clazzException, clazzExceptionMapper);
	}

	private Class<? extends ExceptionMapper<?>> classeExcecaoMapeada(Throwable exception) {

		for (Map.Entry<Class<? extends Throwable>, Class<? extends ExceptionMapper<?>>> entry : classesExceptionMapper
				.entrySet()) {

			if (entry.getKey().isAssignableFrom(exception.getClass())) {
				return entry.getValue();
			}

		}
		return null;
	}

	/**
	 * Gerar um Response com o provider mapeado para exceção 
	 * @param exception
	 * @return Response gerado pelo provider mapeado da exceção
	 */
	public Response toResponse(Exception exception) {

		Throwable exceptionMapeadaCause = exception;
		Throwable exceptionResponse = null;

		Class<? extends ExceptionMapper<?>> mapperClazz = null;

		//procura no map, com a rootcause da exceção se tem algum mapeamento para mesma 
		do {
			mapperClazz = classeExcecaoMapeada(exceptionMapeadaCause);
			exceptionResponse = exceptionMapeadaCause;
			exceptionMapeadaCause = exceptionMapeadaCause.getCause();
		} while (mapperClazz == null && exceptionMapeadaCause != null);
		if (mapperClazz == null)
			return null;
		
		//recupera o bean do provider dessa exceção
		ExceptionMapper exceptionMapper = creator.select(mapperClazz).get();
		if (exceptionMapper == null)
			return null;

		//invoca o método toResponse do provider da exceção
		return exceptionMapper.toResponse(exceptionResponse);
	}

}
