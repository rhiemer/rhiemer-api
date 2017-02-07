package br.com.rhiemer.api.util.interceptor;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.com.rhiemer.api.util.annotations.evento.DesligaEventoInterceptorDiscovery;

@Interceptor
@DesligaEventoInterceptorDiscovery
@Priority(Interceptor.Priority.LIBRARY_BEFORE + 9)
public class DesligarEventoDestinoInterceptor {


	@AroundInvoke
	public Object buscaDesligamentoEventoEntry(InvocationContext invocationContext) throws Exception {

	
		return invocationContext.proceed();

	}

}
