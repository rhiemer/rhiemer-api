package br.com.rhiemer.api.test.integration.helper;

import java.io.File;

import org.jboss.shrinkwrap.api.container.ResourceContainer;

import br.com.rhiemer.api.util.exception.APPIllegalArgumentException;

public class HelperArquillian {

	private HelperArquillian() {

	}

	public static void addResourcesFiles(ResourceContainer<?> deploy, File dir, String pathApp) {
		if (!dir.exists()) {
			throw new APPIllegalArgumentException("Diretorio {} não existe.", dir);
		}
		if (!dir.isDirectory()) {
			throw new APPIllegalArgumentException("Arquivo {} não é um diretorio.", dir);
		}

		for (File f : dir.listFiles()) {
			if (f.isFile()) {
				deploy.addAsResource(f, f.getPath().replace("\\", "/").substring(pathApp.length()));
			} else {
				addResourcesFiles(deploy, f, pathApp);
			}
		}
	}

	public static void addResourcesFiles(ResourceContainer<?> deploy, File dir) {
		addResourcesFiles(deploy, dir, "src/test/resources");
	}

}
