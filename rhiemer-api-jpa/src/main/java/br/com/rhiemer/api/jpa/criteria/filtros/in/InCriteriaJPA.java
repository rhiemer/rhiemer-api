package br.com.rhiemer.api.jpa.criteria.filtros.in;

import java.util.Collection;

import javax.persistence.criteria.Expression;

import br.com.rhiemer.api.jpa.criteria.filtros.FiltroCriteriaArrayJPA;

public class InCriteriaJPA extends FiltroCriteriaArrayJPA {

	@Override
	public Expression buildSingular(Expression path, Object filtro) {
		if (filtro instanceof Expression)
			return path.in((Expression) filtro);
		else if (filtro instanceof Expression[])
			return path.in((Expression[]) filtro);
		else if (filtro instanceof Collection)
			return path.in((Collection) filtro);
		else if (filtro instanceof Object[])
			return path.in((Object[]) filtro);
		else
			return path.in(filtro);
	}

}
