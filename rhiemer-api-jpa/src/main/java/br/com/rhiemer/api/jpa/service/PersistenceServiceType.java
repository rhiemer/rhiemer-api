package br.com.rhiemer.api.jpa.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import br.com.rhiemer.api.jpa.entity.Entity;
import br.com.rhiemer.api.util.helper.Helper;

public class PersistenceServiceType<T extends Entity,K extends Serializable> extends PersistenceServiceCrud<T, K> {

	public PersistenceServiceType() {
		if (Helper.isClasseParametrizada(this.getClass())) {
			ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
			Type[] typeArgs = type.getActualTypeArguments();
			Class<T> entityClass = (Class<T>) typeArgs[0];
			setClasseObjeto(entityClass);
			if (typeArgs.length > 1) {
				Class<K> chaveClass = (Class<K>) typeArgs[1];
				setClasseChave(chaveClass);
			}
		}
	}

}
