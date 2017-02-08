package br.com.rhiemer.api.util.producer;

import static br.com.rhiemer.api.util.constantes.ConstantesAPI.BASE_PACKAGE;
import static br.com.rhiemer.api.util.constantes.ConstantesAPI.PASTAS_PROPRIEDADE_APLICACOES;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.rhiemer.api.util.annotations.app.PastaPropriedadesRootServidor;
import br.com.rhiemer.api.util.annotations.app.ReflectionBasePackage;
import br.com.rhiemer.api.util.annotations.cdi.API;
import br.com.rhiemer.api.util.helper.ReflectionHelper;

/**
 * Producer default das APIs rhiemer.
 * 
 * @author rhiemer
 *
 */

@ApplicationScoped
public class Producer {

	/**
	 * The directory for data files - default: $jboss.server.home.dir/data
	 */

	private static final String JBOSS_DATA_DIRECTORY_SYSTEM_PROPERTY = "jboss.server.data.dir";
	private Reflections reflections;
	
	public static Reflections reflectionsAplicacao()
	{
		return ReflectionHelper.reflections();
	}
	
	@PostConstruct
	private void init()
	{
		reflections = reflectionsAplicacao();
	}

	@Produces
	@API
	public Logger getLooger(InjectionPoint injectionPoint) {
		return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}

	@Produces
	@ReflectionBasePackage
	public Reflections getReflections(InjectionPoint injectionPoint) {
		return reflections;
	}

	@Produces
	@PastaPropriedadesRootServidor
	public String getPastaProprieadesServidor() {
		// Carrega o caminho do arquivo de configuração RECEITA do diretório de
		// dados da instância do servidor de aplicações (tipicamente:
		// JBOSS_HOME\standalone\data)
		String filePath = System.getProperty(JBOSS_DATA_DIRECTORY_SYSTEM_PROPERTY);
		if (filePath == null) {
			throw new RuntimeException(String.format(
					"A propriedade de sistema que determina o diretório de dados (%s) não possui nenhum valor",
					JBOSS_DATA_DIRECTORY_SYSTEM_PROPERTY));
		}

		// Adiciona separador de arquivo de não existe no final do caminho
		if (!filePath.endsWith(File.separator)) {
			filePath += File.separator;
		}

		filePath = filePath.concat(PASTAS_PROPRIEDADE_APLICACOES);

		return filePath;
	}

}
