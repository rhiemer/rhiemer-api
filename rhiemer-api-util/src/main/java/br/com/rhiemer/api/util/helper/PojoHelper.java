package br.com.rhiemer.api.util.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

public class PojoHelper {

	private PojoHelper() {
	};

	public static <T extends PojoKeyAbstract> T newInstacePrimaryKey(Class<T> classe, Object... keys) {
		T resultado;
		try {
			resultado = classe.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		resultado.setPrimaryKeyArray(keys);
		return resultado;
	}

	public static <T extends PojoKeyAbstract> T newInstacePrimaryKeyLista(Collection<T> lista, Class<T> classe,
			Object... keys) {
		T resultado = newInstacePrimaryKey(classe, keys);
		lista.add(resultado);
		return resultado;
	}

	public static <T extends PojoKeyAbstract> T newInstacePrimaryKeyListaIndex(Collection<T> lista, Class<T> classe,
			int index, Object... keys) {
		T resultado = newInstacePrimaryKey(classe, keys);
		final List<T> _lista = lista instanceof List ? (List<T>) lista : new ArrayList<>(lista);
		_lista.add(index, resultado);
		if (!(lista instanceof List)) {
			lista.clear();
			lista.addAll(_lista);
		}
		return resultado;
	}

	public static <T extends PojoKeyAbstract> T findPrimaryKey(Collection<T> lista, Object... keys) {
		T resultado = lista.stream().filter(t -> t.equalsToKey(keys)).findFirst().get();
		return resultado;
	}

	public static <T extends PojoKeyAbstract> int indexOfPrimaryKey(Collection<T> lista, Object... keys) {
		final List<T> _lista = lista instanceof List ? (List<T>) lista : new ArrayList<>(lista);
		return IntStream.range(0, lista.size()).reduce((i, j) -> _lista.get(i).equalsToKey(keys) ? i : j).getAsInt();
	}

	public static <T extends PojoKeyAbstract> T addCollectionPojoKey(Class<T> classe, Collection<T> lista,
			Object... keys) {
		return Optional.ofNullable(findPrimaryKey(lista, keys)).orElse(newInstacePrimaryKeyLista(lista, classe, keys));

	}

	public static <T extends PojoKeyAbstract> T addListIndexPojoKey(Class<T> classe, Collection<T> lista, int index,
			Object... keys) {
		return Optional.ofNullable(findPrimaryKey(lista, keys))
				.orElse(newInstacePrimaryKeyListaIndex(lista, classe, index, keys));

	}

	public static <T extends PojoKeyAbstract> T findPrimaryKeyArray(T[] array, Object... keys) {
		List<T> lista = Arrays.asList(array);
		return findPrimaryKey(lista, keys);
	}

	public static <T extends PojoKeyAbstract> int indexOfPrimaryKeyArray(T[] array, Object... keys) {
		List<T> lista = Arrays.asList(array);
		return indexOfPrimaryKey(lista, keys);
	}

}
