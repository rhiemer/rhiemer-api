package br.com.rhiemer.api.jpa.helper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.annotations.UniqueKey;
import br.com.rhiemer.api.jpa.builder.BuilderCriteria;
import br.com.rhiemer.api.jpa.builder.BuilderCriteriaPredicate;
import br.com.rhiemer.api.jpa.builder.ParametrizarCriteria;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

public final class HelperJPA {

	private HelperJPA() {
	}


	public static <T> T listaEntityByUniqueKey(EntityManager entityManager,
			PojoKeyAbstract entity, boolean isNot)  {

		Class<?> entityClass = entity.getClass();
		UniqueKey uniqueKey = entityClass.getAnnotation(UniqueKey.class);
		UniqueKey.List listaUniqueKey = entityClass
				.getAnnotation(UniqueKey.List.class);

		if (uniqueKey == null && listaUniqueKey == null)
			return null;

		BuilderCriteria buildCriteria = BuilderCriteria.builder()
				.parametrizarCriteria(new ParametrizarCriteria() {

					@Override
					public CriteriaQuery parametrizar(CriteriaBuilder builder,
							CriteriaQuery query, Root from,
							List<Predicate> predicates) {

						UniqueKey[] arrayUniqueKey = null;
						if (listaUniqueKey == null)
							arrayUniqueKey = new UniqueKey[] { uniqueKey };
						else
							arrayUniqueKey = listaUniqueKey.value();

						List<Predicate> predicatesChave = new ArrayList<Predicate>();
						for (String idProperty : entity.getPrimaryKeyList()) {
							Object propertyKeyValue = Helper
									.getValueMethodOrField(entity, idProperty);

							BuilderCriteriaPredicate.build(predicatesChave)
									.equals(builder, from.get(idProperty),
											propertyKeyValue);
						}

						if (predicatesChave.size() > 0) {
							Predicate pr = builder.and(predicatesChave
									.toArray(new Predicate[] {}));
							if (isNot)
								pr = pr.not();
							else
								pr = builder.or(pr);
							predicates.add(pr);
						}

						Predicate predicateOr = null;
						for (UniqueKey uk : arrayUniqueKey) {
							String[] columnNames = uk.columnNames();
							List<Predicate> predicatesUniqueKey = new ArrayList<Predicate>();
							for (int i = 0; i < columnNames.length; i++) {
								String propertyName = columnNames[i];
								Object propertyValue = Helper
										.getValueMethodOrField(entity,
												propertyName);
								BuilderCriteriaPredicate.build(
										predicatesUniqueKey)
										.equalsCaseInsensitive(builder,
												from.get(propertyName),
												propertyValue);

							}
							if (predicatesUniqueKey.size() > 0)
								predicateOr = builder.or(predicatesUniqueKey
										.toArray(new Predicate[] {}));
						}
						predicates.add(predicateOr);
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
