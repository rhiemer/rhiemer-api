package br.com.rhiemer.api.util.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.jboss.vfs.VirtualFile;

import br.com.rhiemer.api.util.dto.Arquivo;

/**
 * 
 * Reúne funções utilitárias relacionadas a arquivos
 * 
 * @author rodrigo.hiemer
 * 
 */
public final class FileUtils {

	private static final String VIRTUAL_FILE_SYSTEM = "vfs";

	private FileUtils() {

	}

	public static Arquivo getArquivoResource(String caminho) {
		URL url = Thread.currentThread().getContextClassLoader().getResource(caminho);
		if (VIRTUAL_FILE_SYSTEM.equals(url.getProtocol())) {
			try {
				return vfsToArquivo((VirtualFile) url.getContent());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			return buscarArquivoPorCaminhoFisico(url.getFile());
		}
	}

	public static Arquivo getArquivoCaminhoFisico(String caminho) {
		URL url = null;
		try {
			url = new URL(caminho);
		} catch (MalformedURLException e1) {
			throw new RuntimeException(e1);
		}
		if (VIRTUAL_FILE_SYSTEM.equals(url.getProtocol())) {
			try {
				return vfsToArquivo((VirtualFile) url.getContent());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			return buscarArquivoPorCaminhoFisico(url.getFile());
		}
	}

	public static Arquivo pathToArquivo(Path path) {
		Arquivo arquivo = new Arquivo();
		arquivo.setNome(path.toFile().getName());
		arquivo.setCaminho(path.toFile().toString());
		try {
			arquivo.setConteudo(Files.readAllBytes(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return arquivo;
	}

	public static Arquivo vfsToArquivo(VirtualFile virtualFile) {
		Arquivo arquivo = new Arquivo();
		arquivo.setNome(virtualFile.getName());
		arquivo.setCaminho(virtualFile.toString());
		try {
			arquivo.setConteudo(IOUtils.toByteArray(virtualFile.openStream()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return arquivo;
	}

	public static Arquivo buscarArquivoPorCaminhoFisico(String file) {
		String arquivoCaminho = System.getProperty("os.name").contains("indow") ? file.substring(1) : file;
		Path path = Paths.get(arquivoCaminho);
		return pathToArquivo(path);
	}

}
