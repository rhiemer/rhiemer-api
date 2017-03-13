package br.com.rhiemer.api.jpa.builder;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;

import br.com.rhiemer.api.jpa.criteria.filtros.FiltroCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.between.FiltroCriteriaIntervaloAtributoJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.between.FiltroCriteriaIntervaloValorJPA;
import br.com.rhiemer.api.jpa.criteria.join.AbstractJoinCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.orderby.OrderByCriteriaJPA;
import br.com.rhiemer.api.util.helper.Helper;

public class ParametrizarCriteriaJPAParametro {

	private Class<? extends AbstractJoinCriteriaJPA> classe;
	private Boolean fecth;
	private JoinType joinType;
	private String atributo;
	private Object[] values;
	private Attribute[] attributes;
	private Boolean not = false;
	private Boolean caseSensitve = false;
	private Boolean includeNull = false;
	private Object filtro1;
	private Object filtro2;
	private String path1;
	private String path2;
	private Attribute[] pathAttribute1;
	private Attribute[] pathAttribute2;

	protected AbstractJoinCriteriaJPA clone(CriteriaBuilder builder, Root root) {
		AbstractJoinCriteriaJPA operacao = Helper.newInstance(classe);
		operacao.setRoot(root);
		operacao.setJoinType(this.getJoinType());
		operacao.setFecth(this.getFecth());
		operacao.setAttributes(this.getAttributes());
		operacao.setAtributo(this.getAtributo());
		Helper.setValueMethodOrField(operacao,"not",this.getNot());
		Helper.setValueMethodOrField(operacao,"caseSensitve",this.getCaseSensitve());
		Helper.setValueMethodOrField(operacao,"includeNull",this.getIncludeNull());
		Helper.setValueMethodOrField(operacao,"builder",builder);
		return operacao;
	}

	protected void execute(AbstractJoinCriteriaJPA operacao, CriteriaBuilder builder, Root root, CriteriaQuery query,
			List<Predicate> predicates) {
		Object result = null;
		if (operacao instanceof FiltroCriteriaIntervaloAtributoJPA)
		{
		   if (pathAttribute1 != null) 	
		     result = ((FiltroCriteriaIntervaloAtributoJPA)operacao).buildFiltroIntervalo(this.getPathAttribute1(),this.getPathAttribute2(),this.getFiltro1());
		   else
			 result = ((FiltroCriteriaIntervaloAtributoJPA)operacao).buildFiltroIntervalo(this.getPath1(),this.getPath2(),this.getFiltro1());   
		}
		else if (operacao instanceof FiltroCriteriaIntervaloValorJPA)
		{
			result = ((FiltroCriteriaIntervaloValorJPA)operacao).buildFiltroIntervalo(this.getFiltro1(),this.getFiltro2());
		}
		else if (operacao instanceof FiltroCriteriaJPA)
		{
			result = ((FiltroCriteriaJPA)operacao).build(this.getValues());
		}
		else if (operacao instanceof OrderByCriteriaJPA)
		{
			Order order = ((OrderByCriteriaJPA)operacao).build();
			query.orderBy(order);
			
		}		
		else if (operacao instanceof AbstractJoinCriteriaJPA)
		{
			((AbstractJoinCriteriaJPA)operacao).builderJoin();
		}
		
		if (result != null)
		{
			predicates.add((Predicate)result);
		}	
	}
	
	public void build(CriteriaBuilder builder, Root root,CriteriaQuery query,List<Predicate> predicates) {
		AbstractJoinCriteriaJPA operacao = clone(builder,root);
		execute(operacao,builder,root,query,predicates);
	}



	public Class<? extends AbstractJoinCriteriaJPA> getClasse() {
		return classe;
	}

	public ParametrizarCriteriaJPAParametro setClasse(Class<? extends AbstractJoinCriteriaJPA> classe) {
		this.classe = classe;
		return this;
	}

	public Boolean getFecth() {
		return fecth;
	}

	public ParametrizarCriteriaJPAParametro setFecth(Boolean fecth) {
		this.fecth = fecth;
		return this;
	}

	public JoinType getJoinType() {
		return joinType;
	}

	public ParametrizarCriteriaJPAParametro setJoinType(JoinType joinType) {
		this.joinType = joinType;
		return this;
	}

	public String getAtributo() {
		return atributo;
	}

	public ParametrizarCriteriaJPAParametro setAtributo(String atributo) {
		this.atributo = atributo;
		return this;
	}

	public Object[] getValues() {
		return values;
	}

	public ParametrizarCriteriaJPAParametro setValues(Object[] values) {
		this.values = values;
		return this;
	}

	public Attribute[] getAttributes() {
		return attributes;
	}

	public ParametrizarCriteriaJPAParametro setAttributes(Attribute[] attributes) {
		this.attributes = attributes;
		return this;
	}

	public Object getFiltro1() {
		return filtro1;
	}

	public ParametrizarCriteriaJPAParametro setFiltro1(Object filtro1) {
		this.filtro1 = filtro1;
		return this;
	}

	public Object getFiltro2() {
		return filtro2;
	}

	public ParametrizarCriteriaJPAParametro setFiltro2(Object filtro2) {
		this.filtro2 = filtro2;
		return this;
	}

	public Boolean getNot() {
		return not;
	}

	public ParametrizarCriteriaJPAParametro setNot(Boolean not) {
		this.not = not;
		return this;
	}

	public Boolean getCaseSensitve() {
		return caseSensitve;
	}

	public ParametrizarCriteriaJPAParametro setCaseSensitve(Boolean caseSensitve) {
		this.caseSensitve = caseSensitve;
		return this;
		
	}

	public Boolean getIncludeNull() {
		return includeNull;
	}

	public ParametrizarCriteriaJPAParametro setIncludeNull(Boolean includeNull) {
		this.includeNull = includeNull;
		return this;
	}

	public String getPath1() {
		return path1;
	}

	public ParametrizarCriteriaJPAParametro setPath1(String path1) {
		this.path1 = path1;
		return this;
	}

	public String getPath2() {
		return path2;
	}

	public ParametrizarCriteriaJPAParametro setPath2(String path2) {
		this.path2 = path2;
		return this;
	}

	public Attribute[] getPathAttribute1() {
		return pathAttribute1;
	}

	public ParametrizarCriteriaJPAParametro setPathAttribute1(Attribute[] pathAttribute1) {
		this.pathAttribute1 = pathAttribute1;
		return this;
	}

	public Attribute[] getPathAttribute2() {
		return pathAttribute2;
	}

	public ParametrizarCriteriaJPAParametro setPathAttribute2(Attribute[] pathAttribute2) {
		this.pathAttribute2 = pathAttribute2;
		return this;
	}

}
