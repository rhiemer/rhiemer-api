package br.com.rhiemer.api.jpa.criteria.filtros.like;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import br.com.rhiemer.api.jpa.criteria.filtros.FiltroCriteriaJPA;
import br.com.rhiemer.api.jpa.enums.EnumLike;
import br.com.rhiemer.api.jpa.helper.HelperLikeCriteria;

public class ILikeCriteriaJPA extends FiltroCriteriaJPA {

	public ILikeCriteriaJPA() {
		this.setCaseSensitve(false);
	}

	@Override
	public Expression buildSingular(Expression path, Object filtro) {
		if (filtro instanceof Expression)
			return getBuilder().like(path,
					HelperLikeCriteria.concatExpressionCriteria(getBuilder(), (Expression) filtro, EnumLike.AUTO));
		else {
			String[] strSplit = HelperLikeCriteria.quebraStringLike(filtro.toString());
			List<Predicate> predicates = new ArrayList<>();
			Arrays.stream(strSplit).forEach(x -> predicates.add(getBuilder().like(path, x)));
			return getBuilder().or(predicates.toArray(new Predicate[] {}));
		}
	}

}
