package br.com.rhiemer.api.jpa.builder;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import br.com.rhiemer.api.jpa.helper.HelperPredicateCriteria;
import br.com.rhiemer.api.util.helper.Helper;

public class BuilderCriteriaPredicate {

	private List<Predicate> predicates;

	public BuilderCriteriaPredicate(List<Predicate> predicates) {
		super();
		this.predicates = predicates;
	}

	public List<Predicate> getLista() {
		return predicates;

	}

	public BuilderCriteriaPredicate equalsCaseInsensitive(
			CriteriaBuilder builder, Expression exp, Object valor) {

		if (!Helper.valueIsEmpty(valor)) {
			predicates.add(HelperPredicateCriteria.equalsCaseInsensitive(builder, exp,
					valor));
		}
		return this;
	}

	public BuilderCriteriaPredicate equals(CriteriaBuilder builder,
			Expression<Object> exp, Object valor) {
		if (!Helper.valueIsEmpty(valor)) {
			predicates.add(builder.equal(exp, valor));
		}
		return this;
	}

	public BuilderCriteriaPredicate iLikeCaseInsensitive(
			CriteriaBuilder builder, Expression<String> exp, String valor) {
		if (!Helper.valueIsEmpty(valor)) {
			predicates.add(HelperPredicateCriteria.iLikeCaseInsensitive(builder, exp,
					valor));
		}
		return this;
	}

	public BuilderCriteriaPredicate iLikeCaseSensitive(CriteriaBuilder builder,
			Expression<String> exp, String valor) {
		if (!Helper.valueIsEmpty(valor)) {
			predicates.add(HelperPredicateCriteria.iLikeCaseSensitive(builder, exp,
					valor));
		}
		return this;
	}

	public static BuilderCriteriaPredicate build(List<Predicate> predicates) {
		return new BuilderCriteriaPredicate(predicates);
	}

}
