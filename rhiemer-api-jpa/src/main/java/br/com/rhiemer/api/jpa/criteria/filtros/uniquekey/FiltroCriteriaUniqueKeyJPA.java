package br.com.rhiemer.api.jpa.criteria.filtros.uniquekey;

import java.util.List;
import java.util.Map;

import br.com.rhiemer.api.jpa.annotations.UniqueKey;
import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.filtros.equals.EqualsCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.juncao.IBuilderMetodosJPA;
import br.com.rhiemer.api.jpa.criteria.juncao.MetodosJuncaoJPAAnd;
import br.com.rhiemer.api.jpa.criteria.juncao.MetodosJuncaoJPAOr;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.helper.HelperPojoKey;

public interface FiltroCriteriaUniqueKeyJPA<T> extends IBuilderMetodosJPA {

	Class<?> getResultClass();

	default T primaryKeyNot(Object... chaves) {
		MetodosJuncaoJPAAnd and1 = new MetodosJuncaoJPAAnd().andNot();
		Map<String, Object> map = HelperPojoKey.mapPrimaryKey(getResultClass(), chaves);
		and1.equal(map);
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setObjeto(and1));
		return (T) this;
	}

	default T primaryKey(Object... chaves) {
		Map<String, Object> map = HelperPojoKey.mapPrimaryKey(getResultClass(), chaves);
		map.forEach((x, y) -> getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(EqualsCriteriaJPA.class)
				.setAtributo(x).setValues(new Object[] { y })));
		return (T) this;
	}

	default T uniqueKeyByNome(String chave, Object... params) {
		final UniqueKey[] arrayUniqueKey = getResultClass().getAnnotationsByType(UniqueKey.class);
		if (arrayUniqueKey == null || arrayUniqueKey.length == 0)
			return (T) this;

		for (UniqueKey uniqueKey : arrayUniqueKey) {
			if (chave.equalsIgnoreCase(uniqueKey.nome())) {
				Object entity = Helper.newInstance(getResultClass());
				for (int i = 0; i < params.length && i < uniqueKey.columnNames().length; i++) {
					Helper.setValueMethodOrField(entity, uniqueKey.columnNames()[i], params[i]);
				}
				uniqueKey(entity, false, true, true, uniqueKey.nome());
				break;
			}

		}
		return (T) this;
	}

	default T uniqueKeyByNome(Object... params) {
		final UniqueKey[] arrayUniqueKey = getResultClass().getAnnotationsByType(UniqueKey.class);
		if (arrayUniqueKey == null || arrayUniqueKey.length == 0)
			return (T) this;

		loop1: for (UniqueKey uniqueKey : arrayUniqueKey) {

			int i;
			for (i = 0; i < params.length && i < uniqueKey.columnNames().length; i++) {
				if (!Helper.isInstance(getResultClass(), uniqueKey.columnNames()[i], params[i]))
					continue loop1;
			}

			if (i == params.length) {
				uniqueKeyByNome(getResultClass(), uniqueKey.nome(), params);
				break;
			}

		}
		return (T) this;

	}

	default T uniqueKeyValida(Object entity) {
		uniqueKey(entity, true, true, false);
		return (T) this;
	}

	default T uniqueKeyAtualiza(Object entity) {
		uniqueKey(entity, false, false, true);
		return (T) this;
	}

	default T uniqueKey(Object entity) {
		uniqueKey(entity, false, true, true);
		return (T) this;
	}

	default T uniqueKeyNot(Object entity) {
		uniqueKey(entity, true, true, true);
		return (T) this;
	}

	default T uniqueKey(Object entity, boolean isNot, boolean valida, boolean atualiza,
			String... nomesUniqueKey) {

		Class<?> entityClass = entity.getClass();
		final UniqueKey[] arrayUniqueKey = entityClass.getAnnotationsByType(UniqueKey.class);

		if (arrayUniqueKey == null || arrayUniqueKey.length == 0)
			return (T) this;
		if (isNot)
			primaryKeyNot(entity);
		else
			primaryKey(entity);

		List<String> _nomesUniqueKey = Helper.convertArgs(nomesUniqueKey);
		MetodosJuncaoJPAAnd and1 = new MetodosJuncaoJPAAnd();
		loop1: for (UniqueKey uk : arrayUniqueKey) {

			if (_nomesUniqueKey.size() > 0) {
				if (!_nomesUniqueKey.contains(uk.nome())) {
					continue loop1;
				}
			}

			if (valida && !uk.validar()) {
				continue loop1;
			}

			if (atualiza && !uk.atualizar()) {
				continue loop1;
			}

			MetodosJuncaoJPAOr or = and1.or();
			String[] columnNames = uk.columnNames();
			for (int i = 0; i < columnNames.length; i++) {
				String propertyName = columnNames[i];
				Object propertyValue = Helper.getValueMethodOrField(entity, propertyName);
				or.and().equal(propertyName, propertyValue);
			}
		}
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setObjeto(and1));
		return (T) this;

	}
}
