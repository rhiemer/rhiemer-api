package br.com.rhiemer.api.rest.resources.list;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.stats.RegistryData;
import org.jboss.resteasy.plugins.stats.RegistryStatsResource;
import org.reflections.Reflections;

import br.com.rhiemer.api.util.format.converter.ConverterFacotry;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.helper.ReflectionHelper;

public class HelperListResorce {

	public static final String RESTEASY_REGISTRY_ENDPOINT = "/resteasy/registry";

	private static Reflections reflections;
	public static Set<Class<?>> clazzsResource;
	public static Map<Class<?>, Class<? extends Application>> maplCazzApplicationResource;

	static {
		reflections = ReflectionHelper.reflections();
		clazzsResource = clazzsResourceRest();
		maplCazzApplicationResource = getMapClazzApplication();
	}

	private HelperListResorce() {
	}

	private static Set<Class<?>> clazzsResourceRest() {
		Set<Class<?>> clazzsResource = new HashSet<>();
		clazzsResource.addAll(reflections.getTypesAnnotatedWith(Path.class));
		clazzsResource.add(RegistryStatsResource.class);
		Set<Class<?>> clazzsResourceClass = clazzsResource.stream().filter(t -> !t.isInterface())
				.collect(Collectors.toSet());
		return clazzsResourceClass;
	}

	private static Map<Class<?>, Class<? extends Application>> getMapClazzApplication() {
		Map<Class<?>, Class<? extends Application>> result = new HashMap<>();
		Set<Class<? extends Application>> clazzsApplicationResouce = new HashSet<>();
		clazzsApplicationResouce.addAll(reflections.getSubTypesOf(Application.class));

		for (Class<? extends Application> clazzApplicationResouce : clazzsApplicationResouce) {
			if (Helper.buscarAnnotation(clazzApplicationResouce, ApplicationPath.class) != null) {
				Application app = Helper.newInstance(clazzApplicationResouce);
				for (Class<?> classeResource : clazzsResource.stream().filter(t -> result.get(t) == null)
						.collect(Collectors.toSet())) {
					if (app.getClasses() != null && app.getClasses().contains(classeResource)) {
						result.put(classeResource, clazzApplicationResouce);
					} else if (app.getClasses() == null || app.getClasses().size() == 0) {
						result.put(classeResource, clazzApplicationResouce);
					}

					if (result.get(classeResource) != null && classeResource.getInterfaces() != null) {
						Arrays.asList(classeResource.getInterfaces()).stream()
								.filter(i -> Helper.buscarAnnotation(i, Path.class) != null)
								.forEach(ii -> result.put(ii, clazzApplicationResouce));
					}

				}
			}
		}
		return result;
	}

	public static MockHttpResponse responseResourcesRestEasy(String mediaType) {
		return HelperMockHttp.invokeGet(RESTEASY_REGISTRY_ENDPOINT, mediaType,
				clazzsResourceRest().toArray(new Class[1]));
	}

	public static MockHttpResponse responseResourcesRestEasy() {
		return responseResourcesRestEasy(MediaType.APPLICATION_XML);
	}

	public static RegistryData responseResourcesRestEasyApplicationObj() {
		return HelperMockHttp.invokeGetObj(RESTEASY_REGISTRY_ENDPOINT, MediaType.APPLICATION_XML, RegistryData.class,
				clazzsResourceRest().toArray(new Class[1]));
	}

	public static RegistryData responseResourcesRestEasyApplicationObjStr() {
		return HelperMockHttp.invokeGetObjStr(RESTEASY_REGISTRY_ENDPOINT, MediaType.APPLICATION_XML, RegistryData.class,
				clazzsResourceRest().toArray(new Class[1]));
	}

	public static void setApplicationRegistryData(RegistryData registryData) {
		maplCazzApplicationResource.forEach((k, v) -> registryData.getEntries().forEach(t -> t.getMethods().stream()
				.filter(x -> k.getName().equals(x.getClazz())).findFirst().ifPresent(z -> t.setUriTemplate(
						Helper.buscarAnnotation(v, ApplicationPath.class).value().concat(t.getUriTemplate())))));
	}

	public static String listResourceRestEasy() {
		RegistryData data = responseResourcesRestEasyApplicationObjStr();
		setApplicationRegistryData(data);
		String result = ConverterFacotry.converter(MediaType.APPLICATION_XML).converter(data, true);
		return result;
	}

}
