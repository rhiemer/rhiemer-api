package br.com.rhiemer.api.web.filter.nomeurl;

import javax.enterprise.inject.Produces;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import br.com.rhiemer.api.util.annotations.app.DadosRequestAPI;
import br.com.rhiemer.api.util.dto.DadosRequest;

@WebListener
public class ServletRequestListenerThreadLocal implements ServletRequestListener {

	private static ThreadLocal<ServletRequest> SERVLET_REQUESTS = new ThreadLocal<>();
	
	

	@Override
	public void requestInitialized(ServletRequestEvent sre) {

		SERVLET_REQUESTS.set(sre.getServletRequest());

	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		SERVLET_REQUESTS.remove();
	}

	@Produces
	@DadosRequestAPI
	public DadosRequest obtain() {

		if (SERVLET_REQUESTS.get() != null && SERVLET_REQUESTS.get() instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) SERVLET_REQUESTS.get();

			String strUrl = request.getRequestURL().toString();

			if (request.getServletPath() != null) {
				strUrl = strUrl.substring(0, strUrl.indexOf(request.getServletPath()) + 1);
			}

			DadosRequest dadosRequest = new DadosRequest();
			dadosRequest.setNomeUrl(strUrl);
			dadosRequest.setServletPath(request.getServletPath());
			dadosRequest.setRequestURL(request.getRequestURL().toString());

			return dadosRequest;
		} else
			return null;

	}
	
	
}
