package br.com.rhiemer.api.jpa.criteria.filtros.between;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.Attribute;

import br.com.rhiemer.api.jpa.criteria.filtros.FiltroCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.FiltroParametro;
import br.com.rhiemer.api.jpa.criteria.filtros.maiormenor.MaiorQueOuIgualCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.maiormenor.MenorQueOuIgualCriteria;

public class BetweenAtributoCriteriaJPA extends FiltroCriteriaIntervaloAtributoJPA {

	@Override
	public Expression buildFiltroIntervalo(String path1,String path2, Object filtro) {
		FiltroParametro _filtro1 = new FiltroParametro();
		_filtro1.setFiltro(MenorQueOuIgualCriteria.class);
		_filtro1.setAtributo(path1);
		_filtro1.setValue(filtro);

		FiltroParametro _filtro2 = new FiltroParametro();
		_filtro2.setFiltro(MaiorQueOuIgualCriteriaJPA.class);
		_filtro2.setAtributo(path2);
		_filtro2.setValue(filtro);

		return buildFiltro(_filtro1, _filtro2);
	}

	@Override
	public Expression buildFiltroIntervalo(Attribute[] path1, Attribute[] path2, Object filtro) {
		FiltroParametro _filtro1 = new FiltroParametro();
		_filtro1.setFiltro(MenorQueOuIgualCriteria.class);
		_filtro1.setAttributes(path1);
		_filtro1.setValue(filtro);

		FiltroParametro _filtro2 = new FiltroParametro();
		_filtro2.setFiltro(MaiorQueOuIgualCriteriaJPA.class);
		_filtro1.setAttributes(path2);
		_filtro2.setValue(filtro);

		return buildFiltro(_filtro1, _filtro2);
	}

	

	
	
}
