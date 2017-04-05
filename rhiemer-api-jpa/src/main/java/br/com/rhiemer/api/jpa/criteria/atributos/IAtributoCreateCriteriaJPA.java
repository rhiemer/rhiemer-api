package br.com.rhiemer.api.jpa.criteria.atributos;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.Attribute;

import br.com.rhiemer.api.jpa.criteria.interfaces.ICriteriaJPA;
import br.com.rhiemer.api.jpa.helper.HelperRootCriteria;

import static br.com.rhiemer.api.jpa.constantes.ConstantesCriteriaJPA.FETCH_DEFAULT;

public interface IAtributoCreateCriteriaJPA extends ICriteriaJPA {

	String getAtributo();

	Attribute[] getAttributes();

	From getRoot();

	default Boolean getFecth() {
		return FETCH_DEFAULT;
	}

	default JoinType getJoinType() {
		return null;
	}

	default Path builderAtributoCriteria() {
		Attribute[] _attributes = getAttributes();
		if (_attributes != null && _attributes.length > 0)
			return HelperRootCriteria.getAttribute(getRoot(), getFecth(), getJoinType(), _attributes);
		else
			return HelperRootCriteria.getAttribute(getRoot(), getAtributo(), getFecth(), getJoinType());
	}

}
