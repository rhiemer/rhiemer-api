package br.com.rhiemer.api.jpa.criteria.filtros.uniquekey;

import java.util.List;

import br.com.rhiemer.api.jpa.annotations.UniqueKey;
import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.filtros.equals.EqualsCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.juncao.IBuilderMetodosJPA;
import br.com.rhiemer.api.jpa.criteria.juncao.MetodosJuncaoJPAAnd;
import br.com.rhiemer.api.jpa.criteria.juncao.MetodosJuncaoJPAOr;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

public interface FiltroCriteriaUniqueKeyJPA<T> extends IBuilderMetodosJPA {
	
	
	Class<?> getCreateClass();

	default T primaryKeyNot(PojoKeyAbstract entity) {
		MetodosJuncaoJPAAnd and1 = new MetodosJuncaoJPAAnd(this).andNot();
		for (String idProperty : entity.getPrimaryKeyList()) {
			Object propertyKeyValue = Helper.getValueMethodOrField(entity, idProperty);
			and1.equal(idProperty, propertyKeyValue);
		}
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setObjeto(and1));
		return (T)this;
	}

	default T primaryKey(PojoKeyAbstract entity) {
		for (String idProperty : entity.getPrimaryKeyList()) {
			Object propertyKeyValue = Helper.getValueMethodOrField(entity, idProperty);
			getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(EqualsCriteriaJPA.class)
					.setAtributo(idProperty).setValues(new Object[] { propertyKeyValue }));
		}
		return (T)this;
	}
	
	default T uniqueKeyByNome(String chave, Object... params)
	{
		final UniqueKey[] arrayUniqueKey = getCreateClass().getAnnotationsByType(UniqueKey.class);
		if (arrayUniqueKey == null || arrayUniqueKey.length == 0)
			return (T)this;

		for (UniqueKey uniqueKey : arrayUniqueKey) {
			if (chave.equalsIgnoreCase(uniqueKey.nome())) {
				PojoKeyAbstract entity = (PojoKeyAbstract)Helper.newInstance(getCreateClass());				
				for (int i = 0; i < params.length && i < uniqueKey.columnNames().length; i++) {
					Helper.setValueMethodOrField(entity, uniqueKey.columnNames()[i], params[i]);
				}
				uniqueKey(entity, false, true, true,uniqueKey.nome());
				break;
			}

		}
		return (T)this;
	}
	
	default T uniqueKeyByNome(Object... params)
	{
		final UniqueKey[] arrayUniqueKey = getCreateClass().getAnnotationsByType(UniqueKey.class);
		if (arrayUniqueKey == null || arrayUniqueKey.length == 0)
			return (T)this;

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
		return (T)this;

	}
	
	default T uniqueKeyValida(PojoKeyAbstract entity) {
		uniqueKey(entity, true, true, false);
		return (T)this;
	}

	default T uniqueKeyAtualiza(PojoKeyAbstract entity) {
		uniqueKey(entity, false, false, true);
		return (T)this;
	}
	
	default T uniqueKey(PojoKeyAbstract entity)
	{
		uniqueKey(entity, false, true, true);
		return (T)this;
	}
	
	default T uniqueKeyNot(PojoKeyAbstract entity)
	{
		uniqueKey(entity, true, true, true);
		return (T)this;
	}
	
	

	default T uniqueKey(PojoKeyAbstract entity, boolean isNot, boolean valida, boolean atualiza,
			String... nomesUniqueKey) {

		Class<?> entityClass = entity.getClass();
		final UniqueKey[] arrayUniqueKey = entityClass.getAnnotationsByType(UniqueKey.class);

		if (arrayUniqueKey == null || arrayUniqueKey.length == 0)
			return (T)this;
		if (isNot)
			primaryKeyNot(entity);
		else
			primaryKey(entity);

		List<String> _nomesUniqueKey = Helper.convertArgs(nomesUniqueKey);
		MetodosJuncaoJPAAnd and1 = new MetodosJuncaoJPAAnd(this);		
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
		return (T)this;
		
	}
}
