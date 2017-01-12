package br.com.rhiemer.api.util.propriedades;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/***
 * Classe abstrata criada com os procedimentos de carga das propriedades de
 * acordo com o
 * 
 * @author rodrigo.hiemer
 * 
 */

public abstract class ConfigurationFiles extends ConfigurationFile {

	@Override
	protected String[] getFilesName() {
		final List<String> files = new ArrayList<>();
		Path path = Paths.get(pastaPropriedade());
		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) throws IOException {
					if (!attrs.isDirectory()) {
						files.add(file.toFile().getName());

					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return files.toArray(new String[] {});
	}

}
