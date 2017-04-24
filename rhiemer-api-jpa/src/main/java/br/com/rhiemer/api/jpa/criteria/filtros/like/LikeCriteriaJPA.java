package br.com.rhiemer.api.jpa.criteria.filtros.like;

import javax.persistence.criteria.Expression;

import br.com.rhiemer.api.jpa.criteria.filtros.FiltroCriteriaJPA;
import br.com.rhiemer.api.jpa.enums.EnumLike;
import br.com.rhiemer.api.jpa.helper.HelperLikeCriteria;
import br.com.rhiemer.api.jpa.helper.HelperPredicateCriteria;

public class LikeCriteriaJPA extends FiltroCriteriaJPA {

	private EnumLike tipo = EnumLike.INICO_FIM;

	public LikeCriteriaJPA() {
		this.setCaseSensitve(false);
	}

	@Override
	public Expression buildSingular(Expression path, Object filtro) {
		if (filtro instanceof Expression)
			return getBuilder().like(path,
					HelperLikeCriteria.concatExpressionCriteria(getBuilder(), (Expression) filtro, getTipo()));
		else
			return getBuilder().like(path, getTipo().format(filtro.toString()));
	}

	public EnumLike getTipo() {
		return tipo;
	}

	public LikeCriteriaJPA setTipo(EnumLike tipo) {
		this.tipo = tipo;
		return this;
	}

}
