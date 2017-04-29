package br.com.rhiemer.api.jpa.criteria.filtros.like;

import javax.persistence.criteria.Expression;

import br.com.rhiemer.api.jpa.criteria.filtros.FiltroCriteriaJPA;
import br.com.rhiemer.api.jpa.enums.EnumLike;
import br.com.rhiemer.api.jpa.helper.HelperLikeCriteria;

public class LikeCriteriaJPA extends FiltroCriteriaJPA {

	private EnumLike tipoLike = EnumLike.INICO_FIM;

	public LikeCriteriaJPA() {
		this.setCaseSensitve(false);
	}

	@Override
	public Expression buildSingular(Expression path, Object filtro) {
		if (filtro instanceof Expression)
			return getBuilder().like(path,
					HelperLikeCriteria.concatExpressionCriteria(getBuilder(), (Expression) filtro, getTipoLike()));
		else {
			return getBuilder().like(path, getTipoLike().format(filtro.toString()));
		}

	}

	public EnumLike getTipoLike() {
		return tipoLike;
	}

	public LikeCriteriaJPA setTipoLike(EnumLike tipoLike) {
		this.tipoLike = tipoLike;
		return this;
	}

}
