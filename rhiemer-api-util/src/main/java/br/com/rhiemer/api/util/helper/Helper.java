package br.com.rhiemer.api.util.helper;

import static br.com.rhiemer.api.util.constantes.ConstantesAPI.ANNOTATIOSID;
import static br.com.rhiemer.api.util.constantes.ConstantesAPI.DATES_FORMAT;
import static br.com.rhiemer.api.util.constantes.ConstantesAPI.DOT_FIELD;
import static br.com.rhiemer.api.util.constantes.ConstantesAPI.ENCONDING_PADRAO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.IntStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.rhiemer.api.util.annotations.entity.Chave;
import br.com.rhiemer.api.util.annotations.entity.ToString;
import br.com.rhiemer.api.util.exception.APPSystemException;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

public final class Helper {

	public static final String SPLASH = "/";
	public static final String VIRGULA = ",";

	private Helper() {
	}

	public static String subsStrIndexOf(String str, String value) {
		if (isBlank(str))
			return str;

		if (isBlank(value))
			return str;

		int indexOf = str.lastIndexOf(value);
		if (indexOf == -1)
			return null;
		else
			return value.substring(indexOf);
	}

	public static String concat(String str, String value) {
		if (isBlank(value) && isNotBlank(str))
			return str;
		else if (isNotBlank(str) && isNotBlank(value))
			return str.concat(value);
		else
			return StringUtils.EMPTY;
	}

	public static String concatArrayIndex(String[] strArray, int ind, String prefix) {
		final String[] result = new String[] { null };
		IntStream.range(0, ind).forEach(idx -> result[0] = (result[0] == null ? strArray[idx]
				: result[0].concat(prefix).concat(strArray[idx])));
		return result[0];
	}

	public static String concatArrayIndex(String[] strArray, int ind) {
		return concatArrayIndex(strArray, ind, DOT_FIELD);
	}

	public static String subStringEndWith(String str, String value) {
		if (str.endsWith(value))
			return str.substring(0, str.length() - value.length());
		else
			return str;
	}

	public static String subStringStartWith(String str, String value) {
		if (str.startsWith(value))
			return str.substring(value.length() + 1);
		else
			return str;
	}

	public static String subStringStartEndWith(String str, String value) {
		return subStringEndWith(subStringStartWith(str, value), value);
	}

	public static String concatArray(String value, String... params) {
		return concatArray("", value, params);
	}

	public static String concatArray(String str, String value, String... params) {
		List<String> listValue = convertArgs(params);
		if (listValue.size() == 0)
			return str;

		String result = subStringEndWith(str, value);

		for (int i = 0; i < listValue.size(); i++) {
			String newValue = null;
			if (i == listValue.size() - 1)
				newValue = subStringStartWith(listValue.get(i), value);
			else
				newValue = subStringStartEndWith(listValue.get(i), value);
			result = result.concat(value).concat(newValue);

		}

		return result;

	}

	public static String concaNotRepeatPrefixSufix(String str, String suffix) {
		if (isNotBlank(str) && isNotBlank(suffix) && (str.startsWith(suffix) || str.endsWith(suffix)))
			return str;
		else
			return concat(str, suffix);
	}

	public static String concaNotRepeatSufix(String str, String suffix) {
		if (isNotBlank(str) && isNotBlank(suffix) && str.endsWith(suffix))
			return str;
		else
			return concat(str, suffix);
	}

	public static String concaNotRepeatPrefix(String str, String prefix) {
		if (isNotBlank(str) && isNotBlank(prefix) && str.startsWith(prefix))
			return str;
		else
			return concat(prefix, str);
	}

	public static String concatSplash(String str, String... values) {

		return concatArray(str, SPLASH, values);

	}

	public static String concatSplashStarWith(String str, String... values) {
		String result = concatSplash(str, values);
		return concaNotRepeatPrefix(result, SPLASH);
	}

	public static String concatSplashStarEndWith(String str, String... values) {
		String result = concatSplash(str, values);
		return concaNotRepeatSufix(result, SPLASH);
	}

	public static String concatSplashStarStartEndWith(String str, String... values) {
		String result = concatSplashStarWith(str, values);
		result = concatSplashStarStartEndWith(str, values);
		return result;
	}

	public static <T> boolean isBlank(T value) {
		if (value == null)
			return true;
		else if (value instanceof String)
			return StringUtils.isBlank((String) value);
		else
			return false;
	}

	public static <T> boolean isNotBlank(T value) {
		return !(isBlank(value));
	}

	public static <T> String collectionToString(Collection<T> collection, String separator) {
		if (collection == null || collection.size() == 0)
			return null;

		final StringBuilder sb = new StringBuilder();
		collection.forEach(t -> {
			if (!StringUtils.isBlank(sb)) {
				sb.append(separator);
			}
			sb.append(t.toString());
		});
		return sb.toString();
	}

	public static <T> String collectionToString(Collection<T> collection) {
		return collectionToString(collection, VIRGULA);
	}

	public static <T> String[] toArrayString(T[] arrayObj) {
		if (arrayObj == null)
			return null;

		String[] result = new String[arrayObj.length];
		for (int i = 0; i < arrayObj.length; i++)
			result[i] = arrayObj[i] == null ? null : arrayObj[i] + "";
		return result;
	}

	public static <T> String getStrOfArray(T[] arrayObj, String separator) {
		String[] resultArray = toArrayString(arrayObj);
		String result = "";
		for (String strId : resultArray)
			if (strId != null && !strId.trim().equals(""))
				result = result + (result.equals("") ? "" : separator) + strId;

		return result.equals("") ? null : result;
	}

	public static Object clone(Object obj) {
		try {

			final ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			final ObjectOutputStream objOutputStream = new ObjectOutputStream(byteArray);
			try {
				objOutputStream.writeObject(obj);
			} finally {
				objOutputStream.close();
			}
			ObjectInputStream objInputStream = null;
			final Object result;
			try {
				objInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArray.toByteArray()));
				result = (Object) objInputStream.readObject();
			} finally {
				if (objInputStream != null)
					objInputStream.close();
			}
			return result;
		} catch (final Throwable t) {
			throw new APPSystemException(t);
		}
	}

	public static <T> Object getValueMethodOrField(T objeto, String property, String separator) {

		String[] s = property.split(separator);
		Object _objeto = objeto;
		for (int i = 0; _objeto != null && i < s.length; i++) {
			_objeto = getValueProperty(_objeto, s[i]);
			if (_objeto == null)
				return null;
		}

		return _objeto;

	}

	public static <T> Object getValueMethodOrField(T objeto, String property) {
		return getValueMethodOrField(objeto, property, DOT_FIELD);
	}

	public static Class getPropertyType(Object objeto, String property) {
		return getTypePropertyComplex(objeto, property, DOT_FIELD);
	}

	public static Class getPropertyType(Class classe, String property) {
		return getTypePropertyComplex(classe, property, DOT_FIELD);
	}

	public static boolean isInstance(Class classe, String property, Object value) {
		Class<?> atributeType = getPropertyType(classe, property);
		return atributeType.isInstance(value);
	}

	public static boolean isAssignableFrom(Class classe, String property, Class value) {
		Class<?> atributeType = getPropertyType(classe, property);
		return (atributeType.isAssignableFrom(value) || value.isAssignableFrom(atributeType));
	}

	public static Class getTypePropertyComplex(Class classe, String property, String separetor) {

		String[] s = property.split(separetor);
		Class propertie = classe;

		for (int i = 0; propertie != null && i < s.length; i++) {
			propertie = getPropertyTypeClass(propertie, s[i]);
		}

		return propertie;

	}

	public static Class getTypePropertyComplex(Object objeto, String property, String separetor) {
		return getTypePropertyComplex(objeto.getClass(), property, separetor);
	}

	public static <T> Class<?> getTypeProperty(T objeto, String property) {

		return getPropertyTypeClass(objeto.getClass(), property);

	}

	public static Class getPropertyTypeClass(Class clazz, String property) {

		Field field = geField(clazz, property);
		if (field != null)
			return field.getType();

		Method _methodGet = methodGet(clazz, property);
		if (_methodGet != null)
			return _methodGet.getReturnType();

		Method _method = getMethod(clazz, property);
		if (_method != null)
			return _method.getReturnType();

		return null;

	}

	public static <T> Object getValueProperty(T objeto, String property) {

		Method _methodGet = methodGet(objeto, property);
		if (_methodGet != null)
			return invokeMethod(_methodGet, objeto);

		Method _method = getMethodObject(objeto.getClass(), property);
		if (_method != null)
			return invokeMethod(_method, objeto);

		return getValueField(objeto, property);

	}

	public static Field geField(Class<?> classe, String property) {

		List<Field> fields = allFields(classe);
		for (Field field : fields)
			if (field.getName().equalsIgnoreCase(property)) {

				return field;
			}
		return null;

	}

	public static <T> Field geFieldObj(T objeto, String property) {

		return geField(objeto.getClass(), property);

	}

	public static <T> Object getValueField(T objeto, String property) {

		return Optional.ofNullable(geFieldObj(objeto, property)).map(x -> getValueField(objeto, x)).orElse(null);

	}

	public static Object invokeMethodGet(Object objeto, String methodName) {
		String _methodName = "get" + methodName;
		return invokeMethod(objeto, _methodName);
	}

	public static void invokeMethodSet(Object objeto, String methodName, Object param) {
		Method method = methodSet(objeto.getClass(), methodName);
		if (method != null)
			invokeMethod(method, objeto, param);
	}

	public static Method methodGet(Class<?> classe, String methodName) {
		String _methodName = "get" + methodName;
		Method method = getMethodObject(classe, _methodName);
		return method;
	}

	public static Method methodSet(Class<?> classe, String methodName) {
		String _methodName = "set" + methodName;
		Method method = getMethodByName(classe, _methodName);
		return method;
	}

	public static Method getMethodByName(Class<?> classe, String methodName) {
		return Arrays.asList(classe.getMethods()).stream().filter(x -> x.getName().equalsIgnoreCase(methodName))
				.findFirst().orElse(null);
	}

	public static Method methodGet(Object objeto, String methodName) {
		Method method = methodGet(objeto.getClass(), methodName);
		return method;
	}

	public static Method methodSet(Object objeto, String methodName) {
		Method method = methodSet(objeto.getClass(), methodName);
		return method;
	}

	public static Object invokeMethod(Method method, Object objeto, Object... params) {
		Object[] _params = null;
		if (params != null && params.length > 0) {
			_params = convertObjectReflextionParams(convertArgsArray(Object.class, params), method.getParameterTypes());
		}
		try {
			return method.invoke(objeto, _params);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new APPSystemException(e);
		}
	}

	public static Object invokeMethodComplex(Object objeto, String methodName, Object... params) {

		return invokeMethodComplex(objeto, methodName, DOT_FIELD, params);

	}

	public static Object invokeMethodComplex(Object objeto, String methodName, String separator, Object... params) {

		String[] s = methodName.split(separator);
		Object _objeto = objeto;
		for (int i = 0; _objeto != null && i < s.length; i++) {
			if (i != s.length - 1) {
				_objeto = getValueProperty(_objeto, s[i]);
			} else
				return invokeMethod(_objeto, s[i], params);

		}

		return null;

	}

	public static Object[] convertObjectReflextionParams(Object[] args, Class<?>[] classes) {

		List<Object> objectList = new ArrayList<>();
		IntStream.range(0, args.length).filter(idx -> classes.length > idx)
				.forEach(idx -> objectList.add(convertObjectReflextionVerifiyNull(args[idx], classes[idx])));
		return objectList.toArray((Object[]) Array.newInstance(Object.class, 1));

	}

	public static Object invokeMethod(Object objeto, String methodName, Object... params) {
		Method method = getMethodObject(objeto.getClass(), methodName, params);
		if (method == null)
			return null;
		else
			return invokeMethod(method, objeto, params);
	}

	public static Method getMethodObject(Class<?> classe, String methodName, Object... params) {
		Class<?>[] arrayClasseParams = convertObjectToClassArray(params);
		return getMethod(classe, methodName, arrayClasseParams);
	}

	public static Method getMethod(Class<?> classe, String methodName, Class<?>... classeParams) {
		Class[] arrayClasseParams = convertArgsArray(Class.class, classeParams);
		return Arrays.asList(classe.getMethods()).stream()
				.filter(x -> (x.getName().equalsIgnoreCase(methodName)
						&& ((x.getParameterTypes() == null || x.getParameterTypes().length == 0)
								&& arrayClasseParams.length == 0)
						|| compareClassArray(x.getParameterTypes(), arrayClasseParams)))
				.findFirst().orElse(null);

	}

	public static <T> Object getValueField(T objeto, Field field) {

		boolean _setacessible = false;
		if (field.getModifiers() != Member.PUBLIC) {
			field.setAccessible(true);
			_setacessible = true;
		}

		try {
			return field.get(objeto);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new APPSystemException(e);
		} finally {
			if (_setacessible)
				field.setAccessible(false);
		}

	}

	public static Object constructorObject(Object t, Class<?> tClass) {
		for (Constructor c : tClass.getConstructors()) {
			if (c.getParameterTypes() == null || c.getParameterTypes().length == 0)
				if (t == null)
					try {
						return c.newInstance();
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						throw new APPSystemException(e);
					}
				else
					continue;

			if (t != null && c.getParameterTypes().length == 1
					&& (c.getParameterTypes()[0]).getName().equals(t.getClass().getName()))
				try {
					return c.newInstance(t);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					throw new APPSystemException(e);
				}
		}

		if (t != null)
			for (Constructor c : tClass.getConstructors()) {
				if (c.getParameterTypes().length == 1
						&& (c.getParameterTypes()[0]).getName().equals("a".getClass().getName()))
					try {
						return c.newInstance(t.toString());
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						throw new APPSystemException(e);
					}

			}

		return null;
	}

	public static Class getTypePropertyComplexClass(Class clazz, String property, String separetor) {

		String[] s = property.split(separetor);
		Class _clazz = clazz;
		for (int i = 0; clazz != null && i < s.length; i++) {
			_clazz = getPropertyTypeClass(_clazz, s[i]);
		}

		return _clazz;

	}

	public static Class<?> converteTypeToClass(java.lang.reflect.Type type) {
		Class<?> result = null;
		if (type instanceof ParameterizedType) {
			ParameterizedType aType = (ParameterizedType) type;
			java.lang.reflect.Type[] parameterArgTypes = aType.getActualTypeArguments();
			for (java.lang.reflect.Type parameterArgType : parameterArgTypes) {
				result = (Class) parameterArgType;
				break;
			}

		}

		return result;

	}

	public static <T> Class<?> getGenericTypeProperty(T objeto, String property) {

		java.lang.reflect.Type genericReturnType = null;
		Class<?> result = null;
		for (Method method : objeto.getClass().getMethods())
			if (method.getName().equalsIgnoreCase("get" + property) || (method.getName().equalsIgnoreCase(property))) {
				genericReturnType = method.getGenericReturnType();
				break;
			}

		if (genericReturnType != null) {
			List<Field> fields = allFields(objeto.getClass());
			for (Field field : fields)
				if (field.getName().equalsIgnoreCase(property)) {

					boolean _setacessible = false;
					if (field.getModifiers() != Member.PUBLIC) {
						field.setAccessible(true);
						_setacessible = true;
					}

					try {
						genericReturnType = field.getGenericType();
						break;
					} finally {
						if (_setacessible)
							field.setAccessible(false);
					}
				}
		}

		result = converteTypeToClass(genericReturnType);
		if (result == null)
			result = getTypeProperty(objeto, property);
		return result;

	}

	public static Class<?> getGenericTypePropertyComplex(Object objeto, String property) {
		return getGenericTypePropertyComplex(objeto, property, "[.]");
	}

	public static Class<?> getGenericTypePropertyComplex(Object objeto, String property, String separetor) {

		String[] s = property.split(separetor);
		Object _objeto = objeto;
		Class<?> propertie = null;
		for (int i = 0; _objeto != null && i < s.length; i++) {
			_objeto = getValueProperty(_objeto, s[i]);
			if (_objeto == null)
				break;

			if (i == s.length - 1)
				propertie = getGenericTypeProperty(_objeto, property);
		}

		return propertie;

	}

	public static String printStackTrace(Exception ex) {

		Writer writer = new StringWriter();
		ex.printStackTrace(new PrintWriter(writer));
		return writer.toString();

	}

	public static <T, O> void setValueMethodOrField(T objeto, String property, O value) {
		setValueMethodOrField(objeto, property, value, DOT_FIELD);
	}

	public static <T, O> Object setValueMethodOrField(T objeto, String property, O value, String separator) {

		String[] s = property.split(separator);
		Object _objeto = objeto;
		for (int i = 0; _objeto != null && i < s.length; i++) {
			if (i != s.length - 1) {
				Object _objetoNull = getValueProperty(_objeto, s[i]);
				if (_objetoNull != null)
					_objeto = _objetoNull;
				else
					_objeto = setNewValueProperty(_objeto, s[i]);
			} else
				setValueProperty(_objeto, s[i], value);

		}

		return _objeto;

	}

	public static <T> void setNewValueMethodOrField(T objeto, String property) {
		setNewValueMethodOrField(objeto, property, DOT_FIELD);
	}

	public static <T, O> void setNewValueMethodOrField(T objeto, String property, String separator) {

		String[] s = property.split(separator);
		Object _objeto = objeto;
		for (int i = 0; _objeto != null && i < s.length; i++) {
			if (i != s.length - 1) {
				Object _objetoNull = getValueProperty(_objeto, s[i]);
				if (_objetoNull != null)
					_objeto = _objetoNull;
				else
					setNewValueProperty(_objeto, s[i]);
			} else
				setNewValueProperty(_objeto, s[i]);

		}
	}

	public static Collection createCollectionDefault(Class classeInterface) {
		if (classeInterface == null)
			return null;

		Collection result = null;
		if (List.class.isAssignableFrom(classeInterface))
			result = new ArrayList();
		else if (Set.class.isAssignableFrom(classeInterface))
			result = new HashSet();

		return result;

	}

	public static <T> Object setNewValueProperty(T objeto, String property) {

		Object result = null;

		List<Field> fields = allFields(objeto.getClass());

		for (Field field : fields)
			if (field.getName().equalsIgnoreCase(property)) {
				boolean _setacessible = false;
				if (field.getModifiers() != Member.PUBLIC) {
					field.setAccessible(true);
					_setacessible = true;
				}

				try {
					if (field.getType().isInterface() && Collection.class.isAssignableFrom(field.getType())) {
						result = createCollectionDefault(field.getType());
					} else {
						try {
							result = (Object) field.getType().newInstance();
						} catch (InstantiationException | IllegalAccessException e) {
							throw new APPSystemException(e);
						}
					}
					try {
						field.set(objeto, result);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						throw new APPSystemException(e);
					}
					return result;
				} finally {
					if (_setacessible)
						field.setAccessible(false);
				}
			}

		return result;

	}

	public static Date strToDate(String dateStr) {

		return Arrays.stream(DATES_FORMAT).map(x -> Helper.strToDateSimple(x, dateStr.trim().replace(" ", "")))
				.filter(x -> x != null).findFirst().orElse(null);

	}

	public static Date strToDateSimple(String dateFormat, String dateStr) {

		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}

	}

	public static <O> Object parsePrimitive(O objetoTarget, Class<?> classe) {
		String s = objetoTarget + "";
		if (classe.equals(int.class))
			return Integer.parseInt(s);
		else if (classe.isInstance(new Integer(0).longValue()))
			return Long.parseLong(s);
		else if (classe.isInstance(new Integer(0).doubleValue()))
			return Double.parseDouble(s);
		else if (classe.isInstance(new Integer(0).shortValue()))
			return Short.parseShort(s);
		else if (classe.isInstance(new Integer(0).byteValue()))
			return Byte.parseByte(s);
		else if (classe.isInstance(true))
			return charToBoolean(s);
		else if (classe.isInstance(s.toCharArray()[0]))
			return s.toCharArray()[0];
		else
			return null;
	}

	public static <O> Object parseString(String s, Class<?> classe) {

		if (s == null)
			return null;

		if (String.class.isAssignableFrom(classe))
			return s;

		if (classe.isPrimitive()) {
			return parsePrimitive(s, classe);
		}

		if (Integer.class.isAssignableFrom(classe))
			return Integer.valueOf(s);
		else if (Long.class.isAssignableFrom(classe))
			return Long.valueOf(s);
		else if (Double.class.isAssignableFrom(classe))
			return Double.valueOf(s);
		else if (Short.class.isAssignableFrom(classe))
			return Short.valueOf(s);
		else if (Byte.class.isAssignableFrom(classe))
			return Byte.valueOf(s);
		else if (Boolean.class.isAssignableFrom(classe))
			return Boolean.valueOf(charToBoolean(s));
		else if (Character.class.isAssignableFrom(classe))
			return Character.valueOf(s.toCharArray()[0]);
		else if (Date.class.isAssignableFrom(classe))
			return strToDate(s);
		else if (Calendar.class.isAssignableFrom(classe)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(strToDate(s));
			return strToDate(s);
		} else {
			Object result = Helper.getConstructorObject(classe, s);
			return result;
		}

	}

	public static <O> Object verifyNull(Object obj) {

		if (obj == null)
			return null;

		if ((Double.class.isInstance(obj) || obj.getClass().isInstance(new Integer(0).doubleValue()))) {
			if (((Double) obj).isNaN())
				return null;
		}
		return obj;
	}

	public static <O> Object convertObjectReflextion(O objetoTarget, Class classe) {
		if (objetoTarget == null || classe.isInstance(objetoTarget) || ProxyUtils.isProxiedCdi(objetoTarget.getClass()))
			return objetoTarget;

		Object objNull = verifyNull(objetoTarget);
		if (objNull == null)
			return null;

		if (classe.isPrimitive())
			return parsePrimitive(objetoTarget, classe);

		Object result = Helper.parseString(objetoTarget.toString(), classe);
		if (result != null)
			return result;

		result = Helper.getConstructorObject(classe, objetoTarget);
		if (result != null)
			return result;

		return null;

	}

	public static <O> Object convertObjectReflextionVerifiyNull(O objetoTarget, Class classe) {

		Object result = convertObjectReflextion(objetoTarget, classe);
		if (result != null)
			return result;
		else
			return objetoTarget;

	}

	public static boolean charToBoolean(String value) {
		return ("true".equalsIgnoreCase(value) || "S".equalsIgnoreCase(value) || "T".equalsIgnoreCase(value)
				|| "1".equalsIgnoreCase(value));
	}

	public static <T, O> void setValueProperty(T objeto, String property, O value) {

		Method _methodSet = methodSet(objeto, property);
		if (_methodSet != null) {
			invokeMethod(_methodSet, objeto, value);
			return;
		}

		Method _method = getMethodObject(objeto.getClass(), property, value);
		if (_method != null) {
			invokeMethod(_method, objeto, value);
			return;
		}

		setValueField(objeto, property, value);

	}

	public static void setValueField(Object objeto, String property, Object value) {

		List<Field> fields = allFields(objeto.getClass());
		for (Field field : fields)
			if (field.getName().equalsIgnoreCase(property)) {
				setValueField(objeto, field, value);
				return;
			}

	}

	public static void setValueField(Object objeto, Field field, Object value) {

		boolean _setacessible = false;
		if (field.getModifiers() != Member.PUBLIC) {
			field.setAccessible(true);
			_setacessible = true;
		}

		try {
			field.set(objeto, convertObjectReflextion(value, field.getType()));
			return;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new APPSystemException(e);
		} finally {
			if (_setacessible)
				field.setAccessible(false);
		}

	}

	public static <C, T> String getStrOfMap(Map<C, T> map) {
		if (map == null)
			return null;

		String result = "";
		for (Map.Entry<C, T> e : map.entrySet())
			if (e.getValue() == null)
				result += e.getKey() + " = null";
			else if (e.getValue() instanceof Object[])
				result += e.getKey() + " = " + Arrays.deepToString((T[]) e.getValue()) + " ;";
			else if (e.getValue() instanceof Map)
				result += e.getKey() + " = ( " + getStrOfMap((Map) e.getValue()) + " ) ;";
			else
				result += e.getKey() + " =  " + e.getValue() + " ;";

		result = "(" + result + ")";

		return result;

	}

	public static void setPropertiesObjectByMap(Map<String, Object> map, Object object, String divisor,
			boolean sobrescrever) {

		Object aObject = null;
		if (object instanceof Class)
			try {
				aObject = ((Class) object).newInstance();
			} catch (InstantiationException | IllegalAccessException e1) {
				throw new APPSystemException(e1);
			}
		else
			aObject = object;

		for (Map.Entry<String, Object> e : map.entrySet()) {
			if (sobrescrever) {
				setValueMethodOrField(aObject, e.getKey(), e.getValue(), divisor);

			} else {
				Object value = getValueMethodOrField(aObject, e.getKey(), divisor);
				if (value == null)
					setValueMethodOrField(aObject, e.getKey(), e.getValue(), divisor);
			}
		}

	}

	public static void setPropertiesObjectByMap(Map<String, Object> map, Object object) {
		setPropertiesObjectByMap(map, object, DOT_FIELD, true);
	}

	public static Map<String, Object> mapPropertiesByRoot(String root, Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (Map.Entry<String, Object> e : map.entrySet()) {

			if (!e.getKey().equals(root) && e.getKey().indexOf(root) == 1) {
				String newKey = e.getKey().substring(root.length() + 2);
				result.put(newKey, e.getValue());
			}
		}

		if (result.size() == 0)
			return null;
		else
			return result;

	}

	public static void setObjectByProperties(String root, Map properties, Object object, String divisor,
			boolean sobrescrever) {
		if (root != null) {
			Map<String, Object> map = mapPropertiesByRoot(root, properties);
			if (map != null)
				setPropertiesObjectByMap(map, object, divisor, sobrescrever);
		} else
			setPropertiesObjectByMap(properties, object, divisor, sobrescrever);

	}

	public static void setObjectByProperties(String root, Map properties, Object object) {
		setObjectByProperties(root, properties, object, DOT_FIELD, true);
	}

	public static <C, T> Object objectByPropertiesNivel(String root, Map<C, T> properties, Class classObject,
			String divisor) {
		Map<String, Object> mapResult = null;
		Object aObject = null;
		String aRoot = "root";
		int len = divisor.length();

		for (Map.Entry<C, T> e : properties.entrySet()) {
			String newKey = null;
			String key = e.getKey() + "";

			if (key.indexOf(root) == 0) {
				newKey = key.substring(root.length() + len, key.length());

			}

			if (newKey != null && !(newKey.trim().equals("")))
				if (newKey.indexOf(divisor) >= 0) {

					String[] split = newKey.replace(divisor, "#").split("#");
					if (mapResult == null || mapResult.get(split[0]) == null) {

						String newRoot = root + divisor + split[0];
						Object result2 = objectByPropertiesNivel(newRoot, properties, classObject, divisor);
						if (result2 != null) {
							if (mapResult == null)
								mapResult = new HashMap<String, Object>();
							mapResult.put(split[0], result2);
						}
					}
				} else {
					if (classObject != null && newKey != null && !(newKey.trim().equals(""))) {
						if (aObject == null || aObject instanceof String)
							try {
								aObject = classObject.newInstance();
							} catch (InstantiationException | IllegalAccessException e1) {
								throw new APPSystemException(e1);
							}
						setValueMethodOrField(aObject, newKey, e.getValue());
					} else if (aObject == null)
						aObject = (e.getValue() + "");
				}

		}

		Object result = null;
		if (mapResult != null) {
			if (aObject != null)
				mapResult.put(aRoot, aObject);
			result = mapResult;
		} else
			result = aObject;

		return result;
	}

	public static Object valueAnnotationOfFieldSplit(Object objeto, String strField, Class annotationClass,
			String property) {

		return valueAnnotationOfFieldSplit(objeto, strField, annotationClass, property, DOT_FIELD);

	}

	public static Object valueAnnotationOfFieldSplit(Object objeto, String strField, Class annotationClass,
			String property, String strSplit) {

		String[] s = strField.split(strSplit);
		Object _objeto = objeto;
		for (int i = 0; _objeto != null && i < s.length; i++) {
			if (i != s.length - 1) {
				_objeto = getValueProperty(_objeto, s[i]);
			} else
				return valueAnnotationOfField(_objeto, s[i], annotationClass, property);

		}

		return null;

	}

	public static Class verifyClassHandler(Object objeto) {
		if (objeto == null)
			return null;

		Class classHandle = (Class) getValueMethodOrField(objeto, "handler_persistentClass");
		if (classHandle != null)
			return classHandle;
		else
			return objeto.getClass();

	}

	public static Class<?> classResult(AccessibleObject ao) {
		if (ao == null)
			return null;
		else if (ao instanceof Method)
			return ((Method) ao).getReturnType();
		else if (ao instanceof Field)
			return ((Field) ao).getType();
		else
			return null;
	}

	public static Class<?> classResult(Class<?> classe, String field) {
		AccessibleObject ao = getFieldOrMethod(classe, field);
		if (ao == null)
			return null;
		return classResult(ao);
	}

	public static AccessibleObject getFieldOrMethod(Class<?> classe, String field) {
		return getFieldOrMethod(classe, field, "get");
	}

	public static AccessibleObject getFieldOrMethod(Object objeto, String field) {
		return getFieldOrMethod(objeto, field, "get");
	}

	public static AccessibleObject getFieldOrMethod(Class<?> classe, String field, String prefix) {

		if (Helper.isBlank(field))
			return null;

		if (field.contains(DOT_FIELD)) {
			String[] fieldsArray = field.split(DOT_FIELD);
			AccessibleObject ao = null;
			Class<?> classLoop = classe;
			for (int i = 0; i < fieldsArray.length && classLoop != null; i++) {
				String fieldArray = fieldsArray[i];
				ao = getFieldOrMethod(classLoop, fieldArray, prefix);
				if (i == fieldsArray.length - 1)
					break;
				classLoop = classResult(ao);
				if (ao == null)
					return null;
			}
			return ao;
		}

		List<AccessibleObject> lista = new ArrayList<AccessibleObject>();
		for (Method method : classe.getMethods()) {
			String methodName = null;
			if (Helper.isNotBlank(prefix)) {
				if (method.getName().equalsIgnoreCase(prefix.concat(field)))
					return method;
				else if (method.getName().equalsIgnoreCase(field))
					return method;
			}
		}
		List<Field> fields = allFields(classe);

		for (Field fieldObj : fields) {
			if (fieldObj.getName().equalsIgnoreCase(field)) {
				return fieldObj;
			}
		}

		return null;
	}

	public static AccessibleObject getFieldOrMethod(Object objeto, String field, String prefix) {
		if (objeto == null)
			return null;
		Class clazz = verifyClassHandler(objeto);
		AccessibleObject ao = getFieldOrMethod(clazz, field, prefix);
		return ao;
	}

	public static List<AccessibleObject> allMethodsFieldsObj(Object objeto, String prefix) {
		if (objeto == null)
			return null;
		Class clazz = verifyClassHandler(objeto);
		List<AccessibleObject> lista = allMethodsFields(clazz, prefix);
		return lista;
	}

	public static List<AccessibleObject> allMethodsFields(Class classe, String prefix) {

		List<AccessibleObject> lista = new ArrayList<AccessibleObject>();
		for (Method method : classe.getDeclaredMethods())
			if (prefix == null || method.getName().startsWith(prefix)) {
				lista.add(method);
			}
		List<Field> fields = allFields(classe);

		for (Field field : fields) {
			Boolean include = true;
			if (prefix != null)
				for (AccessibleObject aObj : lista) {

					if ((aObj instanceof Method
							&& ((Method) aObj).getName().equalsIgnoreCase(prefix + field.getName()))) {
						include = false;
						break;
					}
				}

			if (include)
				lista.add(field);

		}

		return lista;
	}

	public static boolean isClasseParametrizada(Class classe) {

		if (classe.getGenericSuperclass() != null && classe.getGenericSuperclass() instanceof ParameterizedType
				&& ((ParameterizedType) classe.getGenericSuperclass()).getActualTypeArguments().length > 0
				&& ((ParameterizedType) classe.getGenericSuperclass()).getActualTypeArguments()[0] instanceof Class)

		{
			return true;
		} else
			return false;

	}

	public static Class getClassPrincipal(Class classe) {

		if (classe.isArray()) {
			Class componente = classe.getComponentType();
			if (componente != null)
				return componente;
		} else if (isClasseParametrizada(classe)) {
			String className = ((ParameterizedType) classe.getGenericSuperclass()).getActualTypeArguments()[0]
					.getTypeName();
			Class clazz = (Class) ((ParameterizedType) classe.getGenericSuperclass()).getActualTypeArguments()[0];
			return clazz;

		}

		return classe;
	}

	public static <T extends Annotation> T valueAnnotationOfFieldSplit(Class<?> classe, String strField,
			Class<?> annotationClass, String prefix) {

		String[] s = strField.split(DOT_FIELD);
		Class<?> clazz = classe;
		for (int i = 0; clazz != null && i < s.length; i++) {
			if (i != s.length - 1) {
				clazz = getTypePropertyComplex(clazz, s[i], DOT_FIELD);
			} else
				return (T) valueAnnotationOfField(clazz, s[i], annotationClass, prefix);

		}

		return null;

	}

	public static <T extends Annotation> T valueAnnotationOfFieldSplit(Class<?> classe, String strField,
			Class<?> annotationClass) {

		return (T) valueAnnotationOfFieldSplit(classe, strField, annotationClass, "get");

	}

	public static <T extends Annotation> T valueAnnotationOfField(Class<?> classe, String strField,
			Class<?> annotationClass, String prefix) {

		List<AccessibleObject> lista = allMethodsFields(classe, null);
		for (AccessibleObject field : lista) {
			String _name = field instanceof Field ? ((Field) field).getName() : ((Method) field).getName();
			if (strField.equalsIgnoreCase(_name)
					|| (isNotBlank(prefix) && prefix.concat(strField).equalsIgnoreCase(_name))) {
				Annotation _annotation = field.getAnnotation((Class<? extends Annotation>) annotationClass);
				if (_annotation != null)
					return (T) _annotation;
			}
		}
		return null;

	}

	public static <T extends Annotation> T valueAnnotationOfField(Class<?> classe, String strField,
			Class<?> annotationClass) {
		return valueAnnotationOfField(classe, strField, annotationClass, "get");
	}

	public static Object valueAnnotationOfField(Object objeto, String strField, Class annotationClass,
			String property) {

		List<AccessibleObject> lista = allMethodsFieldsObj(objeto, null);
		for (AccessibleObject field : lista) {
			String _name = field instanceof Field ? ((Field) field).getName() : ((Method) field).getName();

			if (strField.equals(_name) && field.getAnnotation(annotationClass) != null) {
				Annotation _annotation = field.getAnnotation(annotationClass);
				if (property == null)
					return _annotation;
				else
					try {
						return annotationClass.getMethod(property).invoke(_annotation, (Object[]) null);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
							| NoSuchMethodException | SecurityException e) {
						throw new APPSystemException(e);
					}

			}

		}

		return null;

	}

	public static Object valueAnnotationClass(Class classe, Class<? extends Annotation> annotationClass,
			String strField) {

		for (Annotation annotation : classe.getAnnotations()) {
			if (annotation.annotationType().toString().equalsIgnoreCase(annotationClass.toString().toLowerCase())) {
				for (Method method : annotation.getClass().getMethods())
					if (method.getName().equalsIgnoreCase(strField)) {
						try {
							return method.invoke(annotation, (Object[]) null);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							throw new APPSystemException(e);
						}
					}
			}
		}

		return null;

	}

	public static <T extends Annotation, K> K valueAnnotation(T annotation, String strField, Class<K> classeObjeto) {

		for (Method method : annotation.getClass().getMethods())
			if (method.getName().equalsIgnoreCase(strField)) {
				try {
					return (K) method.invoke(annotation, (Object[]) null);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new APPSystemException(e);
				}
			}

		return null;

	}

	public static Object valueAnnotationClass(Class classe, Class<? extends Annotation> annotationClass) {

		return valueAnnotationClass(classe, annotationClass, "value");

	}

	public static String[] allStrFromFields(Class classe, Class[] annotations, Boolean include) {

		List<AccessibleObject> lista = new ArrayList<AccessibleObject>();
		List<String> result = new ArrayList<String>();
		List<AccessibleObject> aAllMethodsFields = allMethodsFields(classe, null);
		for (AccessibleObject field : aAllMethodsFields) {
			boolean _include = false;

			if (field instanceof Field && Modifier.isStatic(((Field) field).getModifiers()))
				continue;

			if (field instanceof Method && Modifier.isStatic(((Method) field).getModifiers()))
				continue;

			String _name = field instanceof Field ? ((Field) field).getName() : ((Method) field).getName();

			if (annotations != null && annotations.length > 0) {
				for (Class annotation : annotations)
					if (field.getAnnotation(annotation) != null) {
						_include = true;
						break;
					}

			} else {
				_include = true;
			}

			if (include == _include) {
				if (field instanceof Method && (_name.startsWith("get") || _name.startsWith("set"))) {
					String vName = _name.substring(3, 4).toLowerCase() + _name.substring(4);
					try {
						if (classe.getDeclaredField(vName) != null)
							_name = vName;
					} catch (NoSuchFieldException | SecurityException e) {
						throw new APPSystemException(e);
					}
				}

				if (result.indexOf(_name) == -1)
					result.add(_name);
			}

		}

		return (result == null || result.size() == 0) ? null : result.toArray(new String[1]);

	}

	public static String[] allStrFromFields(Class classe, Boolean include) {

		String[] result = allStrFromFields(classe, new Class[] {}, include);
		return result;

	}

	public static String[] allStrFromFields(Class classe) {

		String[] result = allStrFromFields(classe, true);
		return result;

	}

	public static Object copyObjectNewObject(Object objTarget, boolean isNotNull, String[] noMethodos) {
		return copyObjectNewObject(objTarget, isNotNull, noMethodos);

	}

	public static Object copyObjectNewObject(Object objTarget, boolean isNotNull) {
		Object copia;
		try {
			copia = objTarget.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new APPSystemException(e);
		}
		copyObject(objTarget, copia, isNotNull);
		return copia;

	}

	public static void copyObject(Object objTarget, Object objSource, boolean isNotNull) {
		copyObject(objTarget, objSource, isNotNull, null);
	}

	public static void copyObject(Object objTarget, Object objSource, boolean isNotNull, String[] noMethodos) {

		String[] aAllMethodsFields = allStrFromFields(objSource.getClass());
		for (String field : aAllMethodsFields) {
			if (noMethodos != null) {
				int i;
				for (i = 0; i < noMethodos.length; i++) {
					if (noMethodos[i].equalsIgnoreCase(field)) {
						break;
					}
				}
				if (i != noMethodos.length)
					continue;
			}
			Object value = getValueMethodOrField(objTarget, field);
			if (!isNotNull || value != null)
				setValueMethodOrField(objSource, field, value);
		}

	}

	public static Object execMethodStr(Object obj, String nameMethod, Object[] paramsMethod) {

		if (obj == null)
			return null;

		for (Method method : obj.getClass().getMethods()) {
			if (method.getName().equalsIgnoreCase(nameMethod)) {
				boolean invoke = false;
				if (paramsMethod != null) {
					if (method.getParameterTypes() == null || method.getParameterTypes().length != paramsMethod.length)
						continue;

					for (int i = 0; i < paramsMethod.length; i++) {
						Class _type = method.getParameterTypes()[i];
						Object o = paramsMethod[i];
						if (!_type.isInstance(o))
							break;
					}

					invoke = true;
				} else
					invoke = true;

				if (invoke)
					try {
						return method.invoke(obj, (Object[]) paramsMethod);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						throw new APPSystemException(e);
					}
			}
		}

		return null;
	}

	public static <T extends Comparable<? super T>> int compareMethodsObjects(T fromObject, T toObject,
			String[] strMethods) {

		for (int i = 0; i < strMethods.length; i++) {
			strMethods[i] = "get" + strMethods[i].substring(0, 1).toUpperCase() + strMethods[i].substring(1);
		}

		List<String> listNameMethods = Arrays.asList(strMethods);

		for (Method method : fromObject.getClass().getMethods()) {

			if (listNameMethods.indexOf(method.getName()) == -1)
				continue;

			Object resultMethodFrom;
			try {
				resultMethodFrom = method.invoke(fromObject, (Object[]) null);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new APPSystemException(e);
			}
			Object resultMethodTo;
			try {
				resultMethodTo = method.invoke(toObject, (Object[]) null);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new APPSystemException(e);
			}

			int result = 0;
			if (resultMethodFrom != null || resultMethodTo != null)
				if (resultMethodFrom == null && resultMethodTo != null)
					return -1;
				else
					result = ((Comparable) resultMethodFrom).compareTo(resultMethodTo);

			if (result != 0)
				return result;

		}

		return 0;
	}

	public static String inputStreamToString(InputStream inputStream) {

		return inputStreamToString(inputStream, ENCONDING_PADRAO);

	}

	public static String inputStreamToString(InputStream inputStream, String enconding) {
		String result = null;
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(inputStream, writer, Charset.forName(enconding));
		} catch (IOException e) {
			throw new APPSystemException(e);
		}
		result = writer.toString();

		return result;

	}

	public static String fileToString(File file, String enconding) {
		String result = null;
		try {
			InputStream input = new FileInputStream(file);
			try {
				result = inputStreamToString(input, enconding);
			} finally {
				input.close();
			}
		} catch (Throwable e) {
			throw new APPSystemException(e);
		}
		return result;
	}

	static public String strEncondig(String value, String enconding) {
		String result = null;
		try {

			InputStream is = new ByteArrayInputStream(value.getBytes());
			try {
				result = inputStreamToString(is, enconding);
			} finally {
				is.close();
			}
		} catch (Throwable e) {
			throw new APPSystemException(e);
		}
		return result;
	}

	static public String strEncondig(String value) {
		return strEncondig(value, null);
	}

	static public void copyFile(File source, File destination) {

		if (destination.exists())
			destination.delete();

		FileChannel sourceChannel = null;
		FileChannel destinationChannel = null;

		try {
			try {
				sourceChannel = new FileInputStream(source).getChannel();
				destinationChannel = new FileOutputStream(destination).getChannel();
				sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
			} finally {
				if (sourceChannel != null && sourceChannel.isOpen())
					sourceChannel.close();
				if (destinationChannel != null && destinationChannel.isOpen())
					destinationChannel.close();
			}
		} catch (Throwable t) {
			throw new APPSystemException(t);
		}
	}

	public static String pojoToString(Object object) {

		if (object == null)
			return null;

		String result = "";
		try {

			String[] fields = allStrFromFields(object.getClass(), new Class[] { Chave.class }, true);
			if (fields == null || fields.length == 0)
				return null;

			for (String field : fields) {

				Object valor = getValueMethodOrField(object, field);
				String strValor = null;
				if (valor != null)
					strValor = valor.toString();
				else
					strValor = "null";

				String titulo = (String) valueAnnotationOfField(object, field, ToString.class, "value");
				if (titulo == null || "".equals(titulo))
					titulo = field;

				result += (result.equals("") ? "" : VIRGULA) + String.format("%s=%s", titulo, strValor);

			}

			return ("".equals(result) ? null : result);

		} catch (Throwable t) {
			throw new APPSystemException(t);
		}

	}

	public static String pojoToStringClass(Object object) {

		String valueQgString = pojoToStringClass(object);
		if (valueQgString == null)
			return null;

		String result = String.format("%s [%s]}", object.getClass().getSimpleName(), valueQgString);
		return result;

	}

	public static <K> List<K> objectNoEqualsCollection(Collection<? extends K> collectionSource,
			Collection<? extends K> collectionTarget, boolean equals) {

		List result;
		try {
			result = new ArrayList<K>();

			for (Object objSource : collectionSource) {
				if (objSource == null)
					continue;

				int indObjTarget = 0;
				for (Object objTarget : collectionTarget) {

					if (objTarget == null) {
						indObjTarget++;
						continue;
					}

					if (objTarget instanceof PojoKeyAbstract
							&& (((PojoKeyAbstract) objTarget).getPrimaryKey() == null)) {
						indObjTarget++;
						continue;
					}

					boolean aEquals = (objSource == objTarget);
					if (!aEquals)
						aEquals = objSource.equals(objTarget);

					if (aEquals) {
						if (equals) {
							if (aEquals)
								result.add(objSource);
						} else
							break;
					}

					indObjTarget++;
				}

				if (!equals) {
					if (collectionTarget.size() == indObjTarget)
						result.add(objSource);
				}

			}
		} catch (Throwable e) {

			throw new APPSystemException(e);
		}
		return result;

	}

	public static boolean valueIsEmpty(Object value) {
		if (value == null)
			return true;
		else if (value instanceof String && StringUtils.isBlank(value.toString()))
			return true;
		else if (value instanceof Array && value.getClass().isArray() && Array.getLength(value) == 0)
			return true;
		else if (value instanceof Collection && ((Collection) value).size() == 0)
			return true;
		else
			return false;

	}

	public static Set<Class<?>> carregarInterfaces(Class<?> classe) {
		Class<?> classePai = classe;
		Set<Class<?>> result = new HashSet<>();
		while (classePai != null && classePai.equals(Object.class)) {
			if (classePai.getInterfaces().length > 0) {

				result.addAll(new HashSet(Arrays.asList(classePai.getInterfaces())));
			}
			classePai = classe.getSuperclass();
		}
		return result;
	}

	public static Map<String, Class<?>> getPrimaryKeyList(Class<?> classe) {

		final Map<String, Class<?>> primaryKeyTypes = new HashMap<>();

		for (Class<?> annotation : ANNOTATIOSID) {
			List<Field> fields = allFields(classe);
			for (Field f : fields) {

				if (f.getAnnotation((Class<? extends Annotation>) annotation) != null) {
					primaryKeyTypes.put(f.getName(), f.getType());
				}
			}
		}

		return primaryKeyTypes;
	}

	public static Map<String, Object> valuePrimaryKeyList(Class<?> classe, Object obj) {

		final Map<String, Object> primaryKeyValues = new HashMap<>();

		for (Class<?> annotation : ANNOTATIOSID) {
			List<Field> fields = allFields(classe);
			for (Field f : fields) {

				if (f.getAnnotation((Class<? extends Annotation>) annotation) != null) {
					primaryKeyValues.put(f.getName(), getValueField(obj, f));
				}
			}
		}

		return primaryKeyValues;
	}

	public static Map<String, Object> valuePrimaryKeyListParams(Class<?> classe, Object... keys) {

		final Map<String, Object> primaryKeyValues = new HashMap<>();
		final List<Object> listKeys = convertArgs(keys);

		List<Field> fields = allFields(classe);
		for (Class<?> annotation : ANNOTATIOSID) {

			int i = -1;
			for (Field f : fields) {
				if (i >= listKeys.size())
					break;
				if (f.getAnnotation((Class<? extends Annotation>) annotation) != null) {
					i++;
					primaryKeyValues.put(f.getName(), convertObjectReflextionVerifiyNull(listKeys.get(i), f.getType()));
				}

			}
		}

		return primaryKeyValues;
	}

	public static Object validaPrimaryKeyValor(Class<?> classe, String id) {

		Map<String, Class<?>> primaryKeyTypes = getPrimaryKeyList(classe);
		if (id != null && primaryKeyTypes.size() == 1) {
			for (Map.Entry<String, Class<?>> entry : primaryKeyTypes.entrySet()) {

				return convertObjectReflextion(id, entry.getValue());
			}
		}

		return id;
	}

	public static List<Field> allFields(Class<?> classe) {

		Class<?> clazz = classe;
		List<Field> result = new ArrayList<>();
		while (clazz != null && !clazz.equals(Object.class)) {
			for (Field f : clazz.getDeclaredFields()) {
				if (!Modifier.isStatic((f.getModifiers())))
					result.add(f);
			}
			clazz = clazz.getSuperclass();
		}

		return result;
	}

	public static List<Field> allFields(Class<?> classe, Class<?>... annotations) {

		Class<?> clazz = classe;
		List<Field> result = new ArrayList<>();
		while (clazz != null && !clazz.equals(Object.class)) {
			loopField: for (Field f : clazz.getDeclaredFields()) {
				if (Modifier.isStatic((f.getModifiers())))
					continue;
				for (Class annotation : annotations) {
					Annotation _annotation = f.getAnnotation(annotation);
					if (_annotation != null) {
						result.add(f);
						continue loopField;
					}
				}
			}
			clazz = clazz.getSuperclass();
		}

		return result;
	}

	public static <T extends Annotation> T buscarAnnotation(Class<?> classe, Class<T> annotation) {

		Class<?> clazz = classe;
		Annotation result = null;
		while (clazz != null && !clazz.equals(Object.class)) {
			result = clazz.getAnnotation(annotation);
			if (result != null)
				return (T) result;
			clazz = clazz.getSuperclass();
		}

		result = buscarAnnotation(classe.getInterfaces(), annotation);
		if (result != null)
			return (T) result;

		return null;
	}

	public static <T extends Annotation> T buscarAnnotation(Class<?>[] interfaces, Class<T> annotation) {

		Annotation result = null;
		for (int i = 0; interfaces != null && i < interfaces.length; i++) {
			result = interfaces[i].getAnnotation(annotation);
			if (result != null)
				return (T) result;
			else {
				result = buscarAnnotation(interfaces[i].getInterfaces(), annotation);
				if (result != null)
					return (T) result;
			}
		}

		return null;
	}

	public static <T> T getListByKey(List<T> lista, T key) {
		int index = lista.indexOf(key);
		if (index >= 0)
			return lista.get(index);
		else
			return null;
	}

	public static <T> T addListByKey(List<T> lista, T key) {
		T t = getListByKey(lista, key);
		if (t != null)
			return t;
		else {
			lista.add(key);
			return key;
		}
	}

	public static <T> T addListByKey(List<T> lista, T key, int pos) {
		T t = getListByKey(lista, key);
		if (t != null)
			return t;
		else {
			lista.add(pos, key);
			return key;
		}
	}

	public static int compareToArrays(Object[] array1, Object[] array2) {
		boolean equals = Arrays.deepEquals(array1, array2);
		if (equals)
			return 0;

		List a = Arrays.asList(array1);
		List b = Arrays.asList(array1);

		Collections.sort(a, (o1, o2) -> compareToObj(o1, o2));
		Collections.sort(b, (o1, o2) -> compareToObj(o1, o2));

		for (Object obj1 : a) {
			for (Object obj2 : b) {
				int compare = compareToObj(obj1, obj2);
				if (compare != 0)
					return compare;
			}
		}

		return -1;

	}

	public static <T, K> T putMapToKey(Map map, K key, T value) {
		Object result = map.get(key);
		if (result == null) {
			map.put(key, value);
			return value;
		} else
			return (T) result;
	}

	public static Map.Entry<Object, Object> getMapToKeyValue(Map<Object, Object> map, Object chave, Object valor) {
		return map.entrySet().stream().filter(entry -> entry.getKey().equals(chave))
				.filter(entry -> entry.getValue().equals(valor)).findFirst().get();
	}

	public static boolean mapTemChaveValor(Map<Object, Object> map, Object chave, Object valor) {
		return getMapToKeyValue(map, chave, valor) != null;
	}

	public static Map.Entry<Object, Object> getMapToKeyValue(Map<Object, Object> map1, Map<Object, Object> map2) {
		return map1.entrySet().stream().filter(entry -> mapTemChaveValor(map2, entry.getKey(), entry.getValue()))
				.findFirst().get();
	}

	public static boolean mapTemChaveValor(Map<Object, Object> map1, Map<Object, Object> map2) {
		return getMapToKeyValue(map1, map2) != null;
	}

	public static int compareToObj(Object obj1, Object obj2) {
		if (obj1 == obj2)
			return 0;
		if (obj1 == null)
			return -1;
		if (obj1 instanceof Comparable)
			return ((Comparable) obj1).compareTo(obj1);
		if (obj1.getClass().isArray() && obj2.getClass().isArray())
			return compareToArrays((Object[]) obj1, (Object[]) obj2);
		else {
			boolean equals = obj1.equals(obj2);
			if (equals)
				return 0;
		}

		return -1;
	}

	public static String enconder(String str) {

		return enconder(str, ENCONDING_PADRAO);
	}

	public static String enconder(String str, String enconding) {
		String strEnconder = null;
		try {
			strEnconder = URLEncoder.encode(str, enconding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			throw new APPSystemException(e);
		}
		return strEnconder;
	}

	public static <T> List<T> convertArgs(T... args) {
		List<T> result = new ArrayList<>();
		if (args == null)
			return result;
		for (T t : args) {
			if (t == null)
				result.add(null);
			else if (t.getClass().isArray())
				result.addAll((List<T>) Arrays.asList((Object[]) t));
			else if (Collection.class.isInstance(t))
				result.addAll((Collection<T>) t);
			else
				result.add(t);
		}
		return result;
	}

	public static <T> T[] convertArgsArray(Class<?> classe, T... args) {
		List<T> listClasseParams = convertArgs(args);
		T[] array = (T[]) Array.newInstance(classe, 1);
		T[] arrayClasseParams = listClasseParams.toArray(array);
		return arrayClasseParams;
	}

	public static boolean compareClassArray(Class[] arrayClass1, Class[] arrayClass2) {

		if (arrayClass1 == null || arrayClass2 == null || arrayClass1.length != arrayClass2.length)
			return false;

		OptionalInt optinal = IntStream.range(0, arrayClass1.length).filter(idx -> arrayClass1[idx] == null
				|| arrayClass2[idx] == null || !arrayClass1[idx].isAssignableFrom(arrayClass2[idx])).findFirst();
		return !optinal.isPresent();
	}

	public static <T> Constructor<T> getConstructor(Class<?> classe, Class<?>... classeParams) {
		Class[] arrayClasseParams = convertArgsArray(Class.class, classeParams);

		return (Constructor<T>) Arrays.asList(classe.getConstructors()).stream()
				.filter(x -> ((x.getParameterTypes() == null || x.getParameterTypes().length == 0)
						&& arrayClasseParams.length == 0)
						|| compareClassArray(x.getParameterTypes(), arrayClasseParams))
				.findFirst().orElse(null);

	}

	public static List<Class<?>> convertObjectToClass(Object... classeParams) {
		List<Class<?>> arrayClasseParams = new ArrayList<>();
		Optional.ofNullable(classeParams).filter(t -> classeParams.length > 0)
				.ifPresent(t -> Arrays.asList(convertArgsArray(Object.class, classeParams))
						.forEach(x -> arrayClasseParams.add(x.getClass())));
		return arrayClasseParams;
	}

	public static Class<?>[] convertObjectToClassArray(Object... classeParams) {
		List<Class<?>> arrayClasseParams = convertObjectToClass(classeParams);
		return arrayClasseParams.toArray(new Class<?>[] {});
	}

	public static <T> Constructor<T> getConstructorObject(Class<?> classe, Object... params) {
		Class<?>[] arrayClasseParams = convertObjectToClassArray(params);
		return getConstructor(classe, arrayClasseParams);
	}

	public static <T> Constructor<T> getConstructorObject(Object obj, Object... params) {
		Class<?>[] arrayClasseParams = convertObjectToClassArray(params);
		return getConstructor(obj.getClass(), arrayClasseParams);
	}

	public static <T> T newInstance(Class<T> classe, Object... params) {
		List<?> listParams = convertArgs(params);
		if (listParams == null || listParams.size() == 0) {
			try {
				return classe.newInstance();
			} catch (InstantiationException | IllegalAccessException e1) {
				throw new APPSystemException(e1);
			}
		} else {

			Constructor<T> constructor = getConstructorObject(classe, params);
			if (constructor == null)
				return null;
			try {
				return constructor.newInstance(params);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new APPSystemException(e);
			}

		}
	}

	public static <T> T newInstanceClass(Class<T> classe, Class<?>... params) {
		List<?> listParams = convertArgs(params);
		if (listParams == null || listParams.size() == 0) {
			return newInstance(classe);
		} else {

			List<Object> listObject = new ArrayList<>();
			listParams.forEach(x -> listObject.add(newInstance((Class) x)));
			return newInstance(classe, listParams.toArray(new Object[] {}));

		}
	}

	public static <T> T newInstanceByStream(OutputStream stream, Class<T> classe) {
		T data = newInstance(classe);
		try {
			new ObjectOutputStream(stream).writeObject(data);
		} catch (IOException e) {
			throw new APPSystemException(e);
		}
		return data;
	}

	public static boolean isInstanceOrCollection(Object result, Class<?> classe) {
		if (result == null) {
			return false;
		} else if (result instanceof Collection
				&& ((Collection) result).stream().filter(classe::isInstance).findFirst().isPresent()) {
			return true;
		} else if (!(classe.isInstance(result))) {
			return true;
		} else {
			return false;
		}

	}

}
