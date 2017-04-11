package br.com.rhiemer.api.jpa.criteria.builder;

import javax.persistence.criteria.JoinType;

public class ParametroCriteriaJPADto {
	
	private Boolean fecth;
	private JoinType joinType;
	private Boolean not = false;
	private Boolean caseSensitve = true;
	private Boolean includeNull = false;
	private Boolean isExpression = false;
	
	public Boolean getFecth() {
		return fecth;
	}
	public ParametroCriteriaJPADto setFecth(Boolean fecth) {
		this.fecth = fecth;
		return this;
	}
	public JoinType getJoinType() {
		return joinType;
	}
	public ParametroCriteriaJPADto setJoinType(JoinType joinType) {
		this.joinType = joinType;
		return this;
	}
	public Boolean getNot() {
		return not;
	}
	public ParametroCriteriaJPADto setNot(Boolean not) {
		this.not = not;
		return this;
	}
	public Boolean getCaseSensitve() {
		return caseSensitve;
	}
	public ParametroCriteriaJPADto setCaseSensitve(Boolean caseSensitve) {
		this.caseSensitve = caseSensitve;
		return this;
	}
	
	public Boolean getIncludeNull() {
		return includeNull;
	}
	public ParametroCriteriaJPADto setIncludeNull(Boolean includeNull) {
		this.includeNull = includeNull;
		return this;
	}
	public Boolean getIsExpression() {
		return isExpression;
	}
	public ParametroCriteriaJPADto setIsExpression(Boolean isExpression) {
		this.isExpression = isExpression;
		return this;
	}

}
