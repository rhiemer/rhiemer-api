package br.com.rhiemer.api.test.integration.client;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Before;

import br.com.rhiemer.api.rest.client.factory.RestClientProxy;
import br.com.rhiemer.api.test.integration.annotation.ServiceTestClientRest;
import br.com.rhiemer.api.util.helper.Helper;

public class ClientTest {

	@ArquillianResource
	protected URL urlAplicacao;

	protected String getPath() {
		return null;
	}

	protected Object createService(Field field, String urlAplicacao, String path, Class[] filters) {
		String _url = null;
		if (StringUtils.isNotBlank(path)) {
			_url = urlAplicacao.toString().concat(path);
		} else {
			_url = urlAplicacao.toString();

		}

		Object servico = RestClientProxy.builder().setUrl(_url).setFiltersClass(filters).builder()
				.createService(field.getType());
		return servico;

	}

	@Before
	@InSequence
	public void carregarServicos() {
		List<Field> fieldsService = Helper.allFields(this.getClass(), ServiceTestClientRest.class);

		for (Field field : fieldsService) {
			boolean acess = field.isAccessible();
			try {
				field.setAccessible(true);
				String path = field.getAnnotation(ServiceTestClientRest.class).path();
				if (StringUtils.isBlank(path))
					path = getPath();

				Class[] filters = null;
				ServiceTestClientRest.ServiceClientRestFilters filtersAnnotation = this.getClass()
						.getAnnotation(ServiceTestClientRest.ServiceClientRestFilters.class);
				if (filtersAnnotation != null)
					filters = filtersAnnotation.filters();
				Object servico = createService(field, urlAplicacao.toString(), path, filters);
				try {
					field.set(this, servico);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}

			} finally {
				if (!acess)
					field.setAccessible(false);
			}

		}
	}

}
