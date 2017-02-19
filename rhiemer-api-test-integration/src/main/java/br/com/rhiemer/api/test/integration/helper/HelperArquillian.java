package br.com.rhiemer.api.test.integration.helper;

import java.io.File;

import org.jboss.shrinkwrap.api.spec.WebArchive;

public class HelperArquillian {

	private HelperArquillian() {

	}

	public static void addWebFiles(WebArchive war, File dir, String pathApp) {
		if (!dir.exists()) {
			throw new IllegalArgumentException(String.format("Diretorio %s não existe.", dir));
		}
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException(String.format("Arquivo %s não é um diretorio.", dir));
		}

		for (File f : dir.listFiles()) {
			if (f.isFile()) {
				war.addAsWebInfResource(f.getPath().substring(pathApp.length()));
			} else {
				addWebFiles(war, f, pathApp);
			}
		}
	}

	public static void addWebFiles(WebArchive war, File dir) {
		addWebFiles(war, dir, "src/test/resources");
	}

}
