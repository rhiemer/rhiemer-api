package br.com.rhiemer.api.jpa.criteria.builder;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;

import br.com.rhiemer.api.jpa.criteria.atributos.IAtributoCreateCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.FiltroCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.between.FiltroCriteriaIntervaloAtributoJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.between.FiltroCriteriaIntervaloValorJPA;
import br.com.rhiemer.api.jpa.criteria.interfaces.ICriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.join.IJoinCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.juncao.IBuilderMetodosJuncaoJPA;
import br.com.rhiemer.api.jpa.criteria.juncao.IBuilderMetodosJuncaoJPAWhere;
import br.com.rhiemer.api.jpa.criteria.orderby.OrderByCriteriaJPA;
import br.com.rhiemer.api.util.helper.Helper;

public class ParametrizarCriteriaJPAParametro {

	private Class<? extends ICriteriaJPA> classe;
	private ICriteriaJPA objeto;
	private Boolean fecth;
	private JoinType joinType;
	private String atributo;
	private Object[] values;
	private Attribute[] attributes;
	private Boolean not = false;
	private Boolean caseSensitve = false;
	private Boolean includeNull = false;
	private Boolean isExpression = false;
	private Object filtro1;
	private Object filtro2;
	private String path1;
	private String path2;
	private Attribute[] pathAttribute1;
	private Attribute[] pathAttribute2;

	public ParametrizarCriteriaJPAParametro() {
		super();
	}

	public ParametrizarCriteriaJPAParametro(ParametroCriteriaJPADto parametrizaCriteriaJPADto) {
		super();
		this.setCaseSensitve(parametrizaCriteriaJPADto.getCaseSensitve());
		this.setJoinType(parametrizaCriteriaJPADto.getJoinType());
		this.setNot(parametrizaCriteriaJPADto.getNot());
		this.setIncludeNull(parametrizaCriteriaJPADto.getIncludeNull());
		this.setFecth(parametrizaCriteriaJPADto.getFecth());
		this.setIsExpression(parametrizaCriteriaJPADto.getIsExpression());
	}

	protected ICriteriaJPA clone(CriteriaBuilder builder, Root root) {
		ICriteriaJPA operacao = null;
		if (this.getObjeto() != null)
			operacao = this.getObjeto();
		else {
			operacao = Helper.newInstance(classe);
			Helper.setValueMethodOrField(operacao, "not", this.getNot());
			Helper.setValueMethodOrField(operacao, "caseSensitve", this.getCaseSensitve());
			Helper.setValueMethodOrField(operacao, "includeNull", this.getIncludeNull());
			Helper.setValueMethodOrField(operacao, "isExpression", this.getIsExpression());
			Helper.setValueMethodOrField(operacao, "joinType", this.getJoinType());
			Helper.setValueMethodOrField(operacao, "fecth", this.getFecth());
			Helper.setValueMethodOrField(operacao, "attributes", this.getAttributes());
			Helper.setValueMethodOrField(operacao, "atributo", this.getAtributo());
		}
		Helper.setValueMethodOrField(operacao, "root", root);
		Helper.setValueMethodOrField(operacao, "builder", builder);
		return operacao;
	}

	protected void execute(ICriteriaJPA operacao, CriteriaBuilder builder, Root root, CriteriaQuery query,
			List<Predicate> predicates) {
		Object result = null;
		if (operacao instanceof FiltroCriteriaIntervaloAtributoJPA) {
			if (pathAttribute1 != null)
				result = ((FiltroCriteriaIntervaloAtributoJPA) operacao).buildFiltroIntervalo(this.getPathAttribute1(),
						this.getPathAttribute2(), this.getFiltro1());
			else
				result = ((FiltroCriteriaIntervaloAtributoJPA) operacao).buildFiltroIntervalo(this.getPath1(),
						this.getPath2(), this.getFiltro1());
		} else if (operacao instanceof FiltroCriteriaIntervaloValorJPA) {
			result = ((FiltroCriteriaIntervaloValorJPA) operacao).buildFiltroIntervalo(this.getFiltro1(),
					this.getFiltro2());
		} else if (operacao instanceof FiltroCriteriaJPA) {
			result = ((FiltroCriteriaJPA) operacao).build(this.getValues());
		} else if (operacao instanceof OrderByCriteriaJPA) {
			Order order = ((OrderByCriteriaJPA) operacao).build();
			query.orderBy(order);
		} else if (operacao instanceof IAtributoCreateCriteriaJPA) {
			((IAtributoCreateCriteriaJPA) operacao).builderAtributoCriteria();
		} else if (operacao instanceof IJoinCriteriaJPA) {
			((IJoinCriteriaJPA) operacao).builderJoin();
		} else if (operacao instanceof IBuilderMetodosJuncaoJPAWhere) {
			((IBuilderMetodosJuncaoJPAWhere) operacao).builderQuery(builder, root, query);
		} else if (operacao instanceof IBuilderMetodosJuncaoJPA) {
			result = ((IBuilderMetodosJuncaoJPA) operacao).builderMetodoJuncao(builder, root, query);
		}

		if (result != null) {
			predicates.add((Predicate) result);
		}
	}

	public void build(CriteriaBuilder builder, Root root, CriteriaQuery query, List<Predicate> predicates) {
		ICriteriaJPA operacao = clone(builder, root);
		execute(operacao, builder, root, query, predicates);
	}

	public Class<? extends ICriteriaJPA> getClasse() {
		return classe;
	}

	public ParametrizarCriteriaJPAParametro setClasse(Class<? extends ICriteriaJPA> classe) {
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

	public ICriteriaJPA getObjeto() {
		return objeto;
	}

	public ParametrizarCriteriaJPAParametro setObjeto(ICriteriaJPA objeto) {
		this.objeto = objeto;
		return this;
	}

	public Boolean getIsExpression() {
		return isExpression;
	}

	public ParametrizarCriteriaJPAParametro setIsExpression(Boolean isExpression) {
		this.isExpression = isExpression;
		return this;
	}

}
