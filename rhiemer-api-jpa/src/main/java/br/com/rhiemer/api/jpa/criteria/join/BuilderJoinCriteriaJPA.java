package br.com.rhiemer.api.jpa.criteria.join;

import static br.com.rhiemer.api.jpa.constantes.ConstantesCriteriaJPA.FETCH_DEFAULT;

import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.Attribute;

import br.com.rhiemer.api.util.helper.Helper;

public class BuilderJoinCriteriaJPA {
	
	private Class<? extends AbstractJoinCriteriaJPA> classe;
	private String atributo;
	private From root;
	private Boolean fecth = FETCH_DEFAULT;
	private JoinType joinType;
	private Attribute[] attributes;

	public BuilderJoinCriteriaJPA(Class<? extends AbstractJoinCriteriaJPA> classe, From root) {
		this.classe = classe;
		this.root = root;
	}

	public BuilderJoinCriteriaJPA setAtributo(String atributo) {
		this.atributo = atributo;
		return this;
	}

	public BuilderJoinCriteriaJPA setFecth(Boolean fecth) {
		this.fecth = fecth;
		return this;
	}

	public BuilderJoinCriteriaJPA setJoinType(JoinType joinType) {
		this.joinType = joinType;
		return this;
	}

	public BuilderJoinCriteriaJPA setAttributes(Attribute[] attributes) {
		this.attributes = attributes;
		return this;
	}
	
	protected <T extends AbstractJoinCriteriaJPA> void setAtributos(T result)
	{
		result.setAtributo(this.atributo);
		result.setAttributes(this.attributes);
		result.setJoinType(this.joinType);
		result.setFecth(this.fecth);		
	}

	public <T extends AbstractJoinCriteriaJPA> T builder() {
		T result = (T) Helper.newInstance(this.classe);
		setAtributos(result);
		return result;
	}
	
	public static BuilderJoinCriteriaJPA builder(Class<? extends AbstractJoinCriteriaJPA> classe, From root) {
		return new BuilderJoinCriteriaJPA(classe, root);
	}

}
