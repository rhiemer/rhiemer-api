package br.com.rhiemer.api.util.constantes;

import javax.interceptor.Interceptor;

public interface SequencialInterceptor {

	static final int DEFAULT = Interceptor.Priority.LIBRARY_BEFORE;
	static final int VALIDA_PARAMETROS = DEFAULT + 1;
	static final int TRACE = DEFAULT + 10;
	static final int DESLIGAR_EVENTO = DEFAULT + 11;
	static final int DESLIGAR_EVENTO_DESTINO = DESLIGAR_EVENTO + 1;

}
