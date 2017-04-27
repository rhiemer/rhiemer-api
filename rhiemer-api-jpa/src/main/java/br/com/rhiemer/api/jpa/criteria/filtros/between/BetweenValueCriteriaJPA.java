package br.com.rhiemer.api.jpa.criteria.filtros.between;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

import br.com.rhiemer.api.jpa.criteria.filtros.FiltroCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.FiltroParametro;
import br.com.rhiemer.api.jpa.criteria.filtros.maiormenor.MaiorQueOuIgualCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.maiormenor.MenorQueOuIgualCriteriaJPA;

public class BetweenValueCriteriaJPA extends FiltroCriteriaIntervaloValorJPA {

	public Expression buildFiltroIntervalo(Object filtro1, Object filtro2) {
		FiltroParametro _filtro1 = new FiltroParametro();
		_filtro1.setFiltro(MaiorQueOuIgualCriteriaJPA.class);
		_filtro1.setValue(filtro1);

		FiltroParametro _filtro2 = new FiltroParametro();
		_filtro2.setFiltro(MenorQueOuIgualCriteriaJPA.class);
		_filtro2.setValue(filtro2);

		return buildFiltro(_filtro1, _filtro2);

	}

	
	
}
