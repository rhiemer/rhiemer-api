package br.com.rhiemer.api.jpa.criteria.execucao.builder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.annotations.ExecucaoAtributo;
import br.com.rhiemer.api.jpa.annotations.OrderByAtributo;
import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.interfaces.ICriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.join.FetchCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.orderby.OrderByCriteriaJPAAsc;
import br.com.rhiemer.api.jpa.criteria.orderby.OrderByCriteriaJPADesc;
import br.com.rhiemer.api.jpa.enums.EnumTipoOrderBY;
import br.com.rhiemer.api.jpa.helper.HelperAtributeJPA;
import br.com.rhiemer.api.jpa.helper.HelperRootCriteria;
import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucao;
import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucaoAtributos;
import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucaoLista;

public interface IBuilderExecucaoOrderBy extends ICriteriaJPA {

	Class<?> getCreateClass();

	default void builderExecucaoOrderBy(CriteriaBuilder builder, Root root, CriteriaQuery query) {
		Arrays.stream(getCreateClass().getAnnotationsByType(OrderByAtributo.class))
				.filter(t -> HelperRootCriteria.isJoinInRoot(getCreateClass(), root, t.value()))
				.forEach(t -> new ParametrizarCriteriaJPAParametro()
						.setClasse(t.tipo() == EnumTipoOrderBY.DESC ? OrderByCriteriaJPADesc.class
								: OrderByCriteriaJPAAsc.class)
						.setAtributo(t.value()).build(builder, root, query, null));
	}

}
