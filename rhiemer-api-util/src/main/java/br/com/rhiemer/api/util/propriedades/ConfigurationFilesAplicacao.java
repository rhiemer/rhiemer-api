package br.com.rhiemer.api.util.propriedades;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import br.com.rhiemer.api.util.annotations.app.PastaPropriedades;
import br.com.rhiemer.api.util.annotations.app.PastaPropriedadesRootServidor;

/***
 * Classe abstrata criada com os procedimentos de carga das propriedades de
 * acordo com o
 * 
 * @author rodrigo.hiemer
 * 
 */


@Dependent
public class ConfigurationFilesAplicacao extends ConfigurationFiles {

	private String pastaPropriedades;
	private transient Instance<Object> instance;

	@Inject
	@PastaPropriedadesRootServidor
	private String pastaRoot;

	@PostConstruct
	@Override
	public void init() {

		super.init();
		try {
			pastaPropriedades = instance.select(new AnnotationLiteral<PastaPropriedades>() {
			}).get().toString();
		} catch (Exception e) {
		}
	}

	@Override
	protected String pastaPropriedade() {

		if (pastaPropriedades == null)
			return null;

		String filePath = pastaRoot;

		// Adiciona separador de arquivo de não existe no final do caminho
		if (!filePath.endsWith(File.separator)) {
			filePath += File.separator;
		}

		filePath.concat(pastaPropriedades);

		return filePath;
	}

	@Override
	public String valueProperty(String key) {

		String value = super.valueProperty(key);
		if (value == null)
			value = System.getProperty(key);
		return value;
	}

}
