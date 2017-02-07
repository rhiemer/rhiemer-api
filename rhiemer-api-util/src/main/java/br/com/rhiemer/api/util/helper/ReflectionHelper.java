package br.com.rhiemer.api.util.helper;

import static br.com.rhiemer.api.util.helper.ConstantesAPI.BASE_PACKAGE;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public final class ReflectionHelper {

	private ReflectionHelper() {
	}

	public static Reflections reflections() {
		return reflections(BASE_PACKAGE);
	}

	public static Reflections reflections(String... basePackages) {

		List<URL> basePackagesList = new ArrayList<>();
		for (String basePackage : basePackages) {
			basePackagesList.addAll(ClasspathHelper.forPackage(basePackage));

		}
		return new Reflections(new ConfigurationBuilder().setUrls(basePackagesList).setScanners(new SubTypesScanner(),
				new TypeAnnotationsScanner(), new MethodAnnotationsScanner(), new MethodParameterScanner()));
	}

}
