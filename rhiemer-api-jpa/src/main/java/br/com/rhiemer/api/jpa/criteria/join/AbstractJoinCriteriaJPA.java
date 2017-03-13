package br.com.rhiemer.api.jpa.criteria.join;

import static br.com.rhiemer.api.jpa.constantes.ConstantesCriteriaJPA.FETCH_DEFAULT;

import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.Attribute;

public abstract class AbstractJoinCriteriaJPA implements IJoinCriteriaJPA {

	private String atributo;
	private From root;
	private Boolean fecth = FETCH_DEFAULT;
	private JoinType joinType;
	private Attribute[] attributes;

	
	@Override
	public Boolean getFecth() {
		return fecth;
	}

	@Override
	public JoinType getJoinType() {
		return joinType;
	}

	@Override
	public String getAtributo() {
		return atributo;
	}

	@Override
	public From getRoot() {
		return root;
	}
	
	@Override
	public Attribute[] getAttributes() {
		return attributes;
	}

	public AbstractJoinCriteriaJPA setAtributo(String atributo) {
		this.atributo = atributo;
		return this;
	}

	public AbstractJoinCriteriaJPA setRoot(From root) {
		this.root = root;
		return this;
	}

	public AbstractJoinCriteriaJPA setFecth(Boolean fecth) {
		this.fecth = fecth;
		return this;
	}

	public AbstractJoinCriteriaJPA setJoinType(JoinType joinType) {
		this.joinType = joinType;
		return this;
	}

	public AbstractJoinCriteriaJPA setAttributes(Attribute[] attributes) {
		this.attributes = attributes;
		return this;
	}

	
}
