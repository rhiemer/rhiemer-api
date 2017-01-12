package br.com.rhiemer.api.util.propriedades;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.rhiemer.api.util.annotations.LogApp;
import br.com.rhiemer.api.util.log.LogAplicacao;

/***
 * Classe abstrata criada com os procedimentos de carga das propriedades de
 * acordo com o
 * 
 * @author rodrigo.hiemer
 * 
 */

public abstract class ConfigurationFile {

	@Inject
	@LogApp
	private LogAplicacao logger;

	private Map<String, Long> filesModified = new HashMap<>();

	private Properties prop = new Properties();

	protected abstract String[] getFilesName();

	protected abstract String pastaPropriedade();

	@PostConstruct
	public void init() {
		String filePath = pastaPropriedade();
		if (filePath == null)
			return;
		for (String file : getFilesName()) {
			filesModified.put(file, Long.MIN_VALUE);
		}
	}

	private File filePath(String fileName) {

		String filePath = pastaPropriedade();
		if (filePath == null)
			return null;

		// Adiciona separador de arquivo de não existe no final do caminho
		if (!filePath.endsWith(File.separator)) {
			filePath += File.separator;
		}

		String filePropertie = filePath.concat(fileName);
		File f = new File(filePropertie);
		if (!f.exists()) {
			throw new RuntimeException(
					String.format("O arquivo de configuração não foi encontrado: %s", filePropertie));
		}

		return f;
	}

	private void loadProperties(File f, Properties properties) {

		FileReader fr;
		try {
			fr = new FileReader(f);
			properties.load(fr);
			logger.info(prop.toString());
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Arquivo de configuração não encontrado:" + f.getName(), e);
		} catch (IOException e) {
			throw new RuntimeException("Falha ao carregar propriedades do arquivo de configuração da Receita", e);
		}

	}

	private void loadFiles() {

		for (String fileName : getFilesName()) {
			File file = filePath(fileName);
			if (file == null)
				continue;
			if (filesModified.get(fileName) < file.lastModified()) {
				filesModified.put(fileName, file.lastModified());
				loadProperties(file, prop);
			}
		}

	}

	public String valueProperty(String key) {
		loadFiles();
		String value = prop.getProperty(key);
		return value;
	}

	public Map<String, String> getProperties() {
		loadFiles();
		Map<String, String> map = new HashMap<String, String>();
		for (String key : prop.stringPropertyNames()) {
			map.put(key, prop.getProperty(key));
		}
		return Collections.unmodifiableMap(map);
	}

}
