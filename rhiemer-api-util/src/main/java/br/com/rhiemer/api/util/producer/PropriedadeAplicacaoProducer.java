package br.com.rhiemer.api.util.producer;

import static br.com.rhiemer.api.util.helper.ConstantesAPI.BOOLEAN_NAO;
import static br.com.rhiemer.api.util.helper.ConstantesAPI.BOOLEAN_SIM;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import br.com.rhiemer.api.util.annotations.app.PropriedadeAplicacao;
import br.com.rhiemer.api.util.propriedades.ConfigurationFilesAplicacao;

public class PropriedadeAplicacaoProducer {

	@Inject
	private ConfigurationFilesAplicacao configurationFile;

	@Dependent
	@Produces
	@PropriedadeAplicacao
	public String getStringConfigValue(InjectionPoint ip) {

		String fqn = ip.getMember().getDeclaringClass().getName() + "." + ip.getMember().getName();
		String key = ip.getAnnotated().getAnnotation(PropriedadeAplicacao.class).value();
		boolean valueRequired = ip.getAnnotated().getAnnotation(PropriedadeAplicacao.class).required();

		String value = configurationFile.valueProperty(key);

		if (value == null && valueRequired) {
			throw new IllegalStateException("Sem valor definido para a propriedade: " + key
					+ " e a mesma foi marcada como required." + " Classe:" + fqn);
		}

		return value;
	}
	
	@Dependent
	@Produces
	@PropriedadeAplicacao
	public boolean getBooleanConfigValue(InjectionPoint ip) {

		String valorPropriedade = getStringConfigValue(ip);
		if (valorPropriedade != null && !BOOLEAN_SIM.equals(valorPropriedade)
				&& !BOOLEAN_NAO.equals(valorPropriedade)) {
			String fqn = ip.getMember().getDeclaringClass().getName() + "." + ip.getMember().getName();
			String key = ip.getAnnotated().getAnnotation(PropriedadeAplicacao.class).value();

			throw new IllegalStateException("Valor definido para a propriedade: " + key
					+ " tem que ser 'S' ou 'N' e o valor que foi informado é " + valorPropriedade + "." + " Classe:"
					+ fqn);
		}

		return BOOLEAN_SIM.equals(valorPropriedade);
	}

}
