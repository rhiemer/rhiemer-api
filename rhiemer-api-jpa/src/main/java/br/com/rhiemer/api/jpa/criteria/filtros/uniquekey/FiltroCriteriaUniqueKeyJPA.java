package br.com.rhiemer.api.jpa.criteria.filtros.uniquekey;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.rhiemer.api.jpa.annotations.UniqueKey;
import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.filtros.equals.EqualsCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.juncao.IBuilderMetodosJPA;
import br.com.rhiemer.api.jpa.criteria.juncao.MetodosJuncaoJPAAnd;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

public interface FiltroCriteriaUniqueKeyJPA extends IBuilderMetodosJPA {
	
	
	Class<?> getCreateClass();

	default void primaryKeyNot(PojoKeyAbstract entity) {
		MetodosJuncaoJPAAnd and1 = new MetodosJuncaoJPAAnd(this).andNot();
		for (String idProperty : entity.getPrimaryKeyList()) {
			Object propertyKeyValue = Helper.getValueMethodOrField(entity, idProperty);
			and1.equal(idProperty, propertyKeyValue);
		}
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setObjeto(and1));
	}

	default void primaryKey(PojoKeyAbstract entity) {
		for (String idProperty : entity.getPrimaryKeyList()) {
			Object propertyKeyValue = Helper.getValueMethodOrField(entity, idProperty);
			getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(EqualsCriteriaJPA.class)
					.setAtributo(idProperty).setValues(new Object[] { propertyKeyValue }));
		}
	}
	
	default void uniqueKeyByNome(String chave, Object... params)
	{
		final UniqueKey[] arrayUniqueKey = getCreateClass().getAnnotationsByType(UniqueKey.class);
		if (arrayUniqueKey == null || arrayUniqueKey.length == 0)
			return;

		for (UniqueKey uniqueKey : arrayUniqueKey) {
			if (chave.equalsIgnoreCase(uniqueKey.nome())) {
				PojoKeyAbstract entity = (PojoKeyAbstract)Helper.newInstance(getCreateClass());				
				for (int i = 0; i < params.length && i < uniqueKey.columnNames().length; i++) {
					Helper.setValueMethodOrField(entity, uniqueKey.columnNames()[i], params[i]);
				}
				uniqueKey(entity);
				break;
			}

		}
	}
	
	default <T extends PojoKeyAbstract> void uniqueKeyByNome(Object... params)
	{
		final UniqueKey[] arrayUniqueKey = getCreateClass().getAnnotationsByType(UniqueKey.class);
		if (arrayUniqueKey == null || arrayUniqueKey.length == 0)
			return;

		loop1: for (UniqueKey uniqueKey : arrayUniqueKey) {

			int i;
			for (i = 0; i < params.length && i < uniqueKey.columnNames().length; i++) {
				Class<?> atributeType = Helper.getPropertyType(getCreateClass(), uniqueKey.columnNames()[i]);
				if (!atributeType.isInstance(params[i]))
					continue loop1;
			}

			if (i == params.length) {
				uniqueKeyByNome(getCreateClass(),uniqueKey.nome(), params);
				break;
			}

		}

	}
	
	default void uniqueKeyValida(EntityManager entityManager, PojoKeyAbstract entity) {
		uniqueKey(entity, true, true, false);
	}

	default void uniqueKeyAtualiza(EntityManager entityManager, PojoKeyAbstract entity) {
		uniqueKey(entity, false, false, true);
	}
	
	default void uniqueKey(PojoKeyAbstract entity)
	{
		uniqueKey(entity, false, true, true);
	}
	
	default void uniqueKeyNot(PojoKeyAbstract entity)
	{
		uniqueKey(entity, true, true, true);
	}
	
	

	default void uniqueKey(PojoKeyAbstract entity, boolean isNot, boolean valida, boolean atualiza,
			String... nomesUniqueKey) {

		Class<?> entityClass = entity.getClass();
		final UniqueKey[] arrayUniqueKey = entityClass.getAnnotationsByType(UniqueKey.class);

		if (arrayUniqueKey == null || arrayUniqueKey.length == 0)
			return;
		if (isNot)
			primaryKeyNot(entity);
		else
			primaryKey(entity);

		List<String> _nomesUniqueKey = Helper.convertArgs(nomesUniqueKey);
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

			String[] columnNames = uk.columnNames();
			for (int i = 0; i < columnNames.length; i++) {
				String propertyName = columnNames[i];
				Object propertyValue = Helper.getValueMethodOrField(entity, propertyName);
				getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(EqualsCriteriaJPA.class)
						.setAtributo(propertyName).setValues(new Object[] { propertyValue }));
			}
		}
	}
}
