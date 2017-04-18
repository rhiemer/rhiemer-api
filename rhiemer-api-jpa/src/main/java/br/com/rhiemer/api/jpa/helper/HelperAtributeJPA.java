package br.com.rhiemer.api.jpa.helper;

import static br.com.rhiemer.api.jpa.constantes.ConstantesAtributosJPA.ANNOTATIONS_ATRIBUTO;
import static br.com.rhiemer.api.jpa.constantes.ConstantesAtributosJPA.ANNOTATIONS_CREATE;
import static br.com.rhiemer.api.jpa.constantes.ConstantesAtributosJPA.ANNOTATIONS_JOIN;
import static br.com.rhiemer.api.jpa.constantes.ConstantesAtributosJPA.ANNOTATIONS_LAZY;
import static br.com.rhiemer.api.jpa.constantes.ConstantesAtributosJPA.ANNOTATIONS_LIST;
import static br.com.rhiemer.api.jpa.constantes.ConstantesAtributosJPA.ANNOTATIONS_REFERENCE;
import static br.com.rhiemer.api.util.constantes.ConstantesAPI.DOT_FIELD;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.helper.HelperPojoKey;

public final class HelperAtributeJPA {

	private static final String ATRIBUTO_MAPPED_BY = "mappedBy";

	private HelperAtributeJPA() {

	}

	private static <T extends Annotation> T annotationStr(Class<? extends Annotation>[] arrayClass, Class<?> classe,
			String fieldStr) {

		Object annotation = Arrays.stream((Class[]) arrayClass)
				.map(x -> Helper.valueAnnotationOfFieldSplit(classe, fieldStr, x)).filter(y -> y != null).findFirst()
				.orElse(null);
		return (T) annotation;
	}

	private static <T extends Annotation> T annotationAccessibleObject(Class<? extends Annotation>[] arrayClass,
			AccessibleObject field) {
		Object annotation = Arrays.stream((Class[]) arrayClass)
				.map(x -> field.getAnnotation((Class<? extends Annotation>) x)).filter(y -> y != null).findFirst()
				.orElse(null);
		return (T) annotation;
	}

	public static <T extends Annotation> T annotationReference(AccessibleObject field) {
		return annotationAccessibleObject(ANNOTATIONS_REFERENCE, field);

	}

	public static <T extends Annotation> T annotationJoin(AccessibleObject field) {
		return annotationAccessibleObject(ANNOTATIONS_JOIN, field);

	}

	public static <T extends Annotation> T annotationLazy(AccessibleObject field) {
		return annotationAccessibleObject(ANNOTATIONS_LAZY, field);

	}

	public static <T extends Annotation> T annotationJoin(Class<?> classe, String fieldStr) {
		return annotationStr(ANNOTATIONS_JOIN, classe, fieldStr);
	}

	public static <T extends Annotation> T annotationLazy(Class<?> classe, String fieldStr) {
		return annotationStr(ANNOTATIONS_LAZY, classe, fieldStr);
	}

	public static <T extends Annotation> T annotationMappedList(AccessibleObject field) {
		return annotationAccessibleObject(ANNOTATIONS_LIST, field);
	}

	public static <T extends Annotation> T annotationMappedList(Class<?> classe, String fieldStr) {
		return annotationStr(ANNOTATIONS_LIST, classe, fieldStr);
	}

	public static <T extends Annotation> T annotationAtributo(AccessibleObject field) {
		return annotationAccessibleObject(ANNOTATIONS_ATRIBUTO, field);
	}

	public static <T extends Annotation> T annotationCreate(AccessibleObject field) {
		Object annotation = Arrays.stream(ANNOTATIONS_CREATE).filter(x -> field.getAnnotation(x) != null).findFirst()
				.orElse(null);
		return (T) annotation;

	}

	public static <T extends Annotation> T annotationCreate(Class<?> classe, String fieldStr) {
		return annotationStr(ANNOTATIONS_CREATE, classe, fieldStr);

	}

	public static <T extends Annotation> T annotationReference(Class<?> classe, String fieldStr) {
		Annotation ar = annotationStr(ANNOTATIONS_REFERENCE, classe, fieldStr);
		if (ar == null)
			return null;

		String mappedBy = Helper.valueAnnotation(ar, ATRIBUTO_MAPPED_BY, String.class);
		if (Helper.isBlank(mappedBy))
			return null;

		Class<?> classResult = Helper.classResult(classe, fieldStr);
		Annotation aJoin = annotationJoin(classResult, mappedBy);
		return (T) aJoin;

	}

	public static <T extends Annotation> T annotationJoinReference(Class<?> classe, String fieldStr) {
		Annotation aReference = annotationReference(classe, fieldStr);
		if (aReference != null)
			return (T) aReference;

		return annotationJoin(classe, fieldStr);

	}

	public static boolean fieldisJoin(AccessibleObject field) {
		return annotationJoin(field) != null;

	}

	public static boolean fieldisJoin(Class<?> classe, String fieldStr) {
		return annotationJoin(classe, fieldStr) != null;

	}

	public static boolean fieldisMappedList(AccessibleObject field) {
		return annotationMappedList(field) != null;

	}

	public static boolean fieldisMappedList(Class<?> classe, String fieldStr) {
		return annotationMappedList(classe, fieldStr) != null;

	}

	public static boolean fieldisCreate(Class<?> classe, String fieldStr) {
		return annotationCreate(classe, fieldStr) != null;

	}

	public static boolean fieldisCreate(AccessibleObject field) {
		return annotationCreate(field) != null;

	}

	public static boolean fieldisLazy(AccessibleObject field) {
		return annotationLazy(field) != null;

	}

	public static boolean fieldisLazy(Class<?> classe, String fieldStr) {
		return annotationLazy(classe, fieldStr) != null;

	}

	public static boolean isField(Class<?> classe, String atributo) {

		Annotation annotation = annotationJoin(classe, atributo);
		return annotation == null;

	}

	public static boolean isFieldListUnit(Class<?> classe, String fieldStr) {
		AccessibleObject field = Helper.getFieldOrMethod(classe, fieldStr);
		return fieldisMappedList(field);
	}

	public static boolean isFieldList(Class<?> classe, String atributo) {

		String[] strSplit = atributo.split(DOT_FIELD);
		return IntStream.range(0, strSplit.length)
				.filter(idx -> isFieldListUnit(classe, Helper.concatArrayIndex(strSplit, idx))).findFirst()
				.orElse(-1) > -1;

	}

	public static List<String> fieldsAtributo(Class<?> classe) {
		String[] allStrFromFields = Helper.allStrFromFields(classe, ANNOTATIONS_ATRIBUTO, true);
		return Arrays.asList(allStrFromFields);
	}

	public static List<String> fieldsList(Class<?> classe) {
		String[] allStrFromFields = Helper.allStrFromFields(classe, ANNOTATIONS_LIST, true);
		return Arrays.asList(allStrFromFields);
	}

	public static String attributeToString(Attribute... attributes) {
		List<Attribute> listAttributes = Helper.convertArgs(attributes);
		String[] names = listAttributes.stream().map(Attribute::getName).collect(Collectors.toList())
				.toArray(new String[] {});
		return Helper.concatArray(".", names);
	}

	public static Expression getExpression(Path path, Object attribute) {

		Path _path = getPath(path, attribute);
		if (_path != null)
			return _path;

		if (attribute instanceof PluralAttribute) {
			return path.get((PluralAttribute) attribute);
		} else if (attribute instanceof MapAttribute) {
			return path.get((MapAttribute) attribute);
		} else
			return null;

	}

	public static Path getPath(Path path, Object attribute) {
		if (attribute instanceof String) {
			return path.get((String) attribute);
		} else if (attribute instanceof SingularAttribute) {
			return path.get((SingularAttribute) attribute);
		} else
			return null;

	}

	public static Expression getAttribute(Path from, Attribute... attributes) {
		List<Attribute> listAttributes = Helper.convertArgs(attributes);
		Path path = from;
		for (Attribute t : listAttributes) {
			Expression exp = getExpression(path, t);
			if (exp == null)
				break;
			else if (exp instanceof Path)
				path = (Path) exp;
			else
				return exp;

		}

		return path;

	}

	public static Path getAttribute(Path from, String atributo) {
		String[] atributoSplit = atributo.split("[.]");
		Path path = from;
		for (String atributo1 : atributoSplit) {
			Path path1 = getPath(path, atributo1);
			if (path1 == null)
				break;
			else
				path = path1;
		}

		return path;

	}

	public static Object createEntity(Class<?> classe, String atributo, Object... chaves) {
		Class<?> type = null;
		if (atributo != null)
			type = Helper.getTypePropertyComplex(classe, atributo);
		else
			type = classe;

		return HelperPojoKey.verifyNewInstancePrimaryKey(type, chaves);

	}

	public static Object createEntity(Class<?> classe, Object... chaves) {

		return createEntity(classe, null, chaves);

	}

}
