package br.com.rhiemer.api.jpa.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.annotations.UniqueKey;
import br.com.rhiemer.api.jpa.builder.BuilderCriteria;
import br.com.rhiemer.api.jpa.builder.BuilderCriteriaJPA;
import br.com.rhiemer.api.jpa.builder.BuilderCriteriaPredicate;
import br.com.rhiemer.api.jpa.builder.ParametrizarCriteria;
import br.com.rhiemer.api.jpa.execucao.IJPAExecucao;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

public final class HelperUniqueKeyJPA {

	private HelperUniqueKeyJPA() {
	}

	public static <T extends PojoKeyAbstract> T listaEntityByUniqueKeyByNome(EntityManager entityManager,
			Class<T> entityClass, String chave, Object... params) {

		final UniqueKey[] arrayUniqueKey = entityClass.getAnnotationsByType(UniqueKey.class);
		if (arrayUniqueKey == null || arrayUniqueKey.length == 0)
			return null;

		for (UniqueKey uniqueKey : arrayUniqueKey) {
			if (chave.equalsIgnoreCase(uniqueKey.nome())) {
				PojoKeyAbstract entity = null;
				try {
					entity = entityClass.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}

				for (int i = 0; i < params.length && i < uniqueKey.columnNames().length; i++) {
					Helper.setValueMethodOrField(entity, uniqueKey.columnNames()[i], params[i]);
				}
				return listaEntityByUniqueKey(entityManager, entity, false, false, false, chave);
			}

		}

		return null;

	}

	public static <T extends PojoKeyAbstract> T listaEntityByUniqueKeyByParams(EntityManager entityManager,
			Class<T> entityClass, Object... params) {

		final UniqueKey[] arrayUniqueKey = entityClass.getAnnotationsByType(UniqueKey.class);
		if (arrayUniqueKey == null || arrayUniqueKey.length == 0)
			return null;

		loop1: for (UniqueKey uniqueKey : arrayUniqueKey) {

			int i;
			for (i = 0; i < params.length && i < uniqueKey.columnNames().length; i++) {
				Class<?> atributeType = Helper.getPropertyType(entityClass, uniqueKey.columnNames()[i]);
				if (!atributeType.isInstance(params[i]))
					continue loop1;
			}

			if (i == params.length) {

				return listaEntityByUniqueKeyByNome(entityManager, entityClass, uniqueKey.nome(), params);

			}

		}

		return null;

	}

	public static <T> T listaEntityByUniqueKeyValida(EntityManager entityManager, PojoKeyAbstract entity) {
		return listaEntityByUniqueKey(entityManager, entity, true, true, false);

	}

	public static <T> T listaEntityByUniqueKeyAtualiza(EntityManager entityManager, PojoKeyAbstract entity) {
		return listaEntityByUniqueKey(entityManager, entity, false, false, true);

	}

	public static <T extends PojoKeyAbstract> void uniqueKeyAtualizaCopy(EntityManager entityManager,
			PojoKeyAbstract entity) {

		T value = listaEntityByUniqueKeyAtualiza(entityManager, entity);
		if (value != null)
			entity.setPrimaryKey(value);

	}
	
	public static <T> T listaEntityByUniqueKey(EntityManager entityManager, PojoKeyAbstract entity, boolean isNot,
			boolean valida, boolean atualiza, String... nomesUniqueKey) {
		
		return listaEntityByUniqueKey(entityManager,entity,isNot,valida,atualiza,null,nomesUniqueKey);
		
	}

	public static <T> T listaEntityByUniqueKey(EntityManager entityManager, PojoKeyAbstract entity, boolean isNot,
			boolean valida, boolean atualiza,IJPAExecucao[] execucoes,String... nomesUniqueKey) {

		Class<?> entityClass = entity.getClass();
		final UniqueKey[] arrayUniqueKey = entityClass.getAnnotationsByType(UniqueKey.class);

		if (arrayUniqueKey == null || arrayUniqueKey.length == 0)
			return null;

		BuilderCriteriaJPA buildCriteria = BuilderCriteriaJPA.builderCreate().resultClass(entity.getClass()).parametrosExecucao(execucoes)
				.parametrizarCriteria(new ParametrizarCriteria() {

					@Override
					public CriteriaQuery parametrizar(CriteriaBuilder builder, CriteriaQuery query, Root from,
							List<Predicate> predicates) {

						List<Predicate> predicatesChave = new ArrayList<Predicate>();
						for (String idProperty : entity.getPrimaryKeyList()) {
							Object propertyKeyValue = Helper.getValueMethodOrField(entity, idProperty);

							BuilderCriteriaPredicate.build(predicatesChave).equals(builder, from.get(idProperty),
									propertyKeyValue);
						}

						if (predicatesChave.size() > 0) {
							Predicate pr = builder.and(predicatesChave.toArray(new Predicate[] {}));
							if (isNot)
								pr = pr.not();
							else
								pr = builder.or(pr);
							predicates.add(pr);
						}

						List<Predicate> predicatesFilterUniqueKey = null;
						Predicate predicateAtributos = null;
						loop1: for (UniqueKey uk : arrayUniqueKey) {
							if (nomesUniqueKey != null) {
								if (!Arrays.asList(nomesUniqueKey).contains(uk.nome())) {
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
							List<Predicate> predicatesUniqueKey = new ArrayList<Predicate>();
							for (int i = 0; i < columnNames.length; i++) {
								String propertyName = columnNames[i];
								Object propertyValue = Helper.getValueMethodOrField(entity, propertyName);
								BuilderCriteriaPredicate.build(predicatesUniqueKey).equalsCaseInsensitive(builder,
										from.get(propertyName), propertyValue);

							}
							if (predicatesUniqueKey.size() > 0) {
								predicateAtributos = builder.and(predicatesUniqueKey.toArray(new Predicate[] {}));
								if (predicatesFilterUniqueKey == null)
									predicatesFilterUniqueKey = new ArrayList<>();
								predicatesFilterUniqueKey.add(predicateAtributos);
							}

						}
						predicates.add(builder.or(predicatesFilterUniqueKey.toArray(new Predicate[] {})));
						return query;
					}
				}).build();

		List<T> lista = buildCriteria.buildQuery(entityManager).getResultList();
		if (lista.size() > 0)
			return lista.get(0);
		else
			return null;

	}
}
