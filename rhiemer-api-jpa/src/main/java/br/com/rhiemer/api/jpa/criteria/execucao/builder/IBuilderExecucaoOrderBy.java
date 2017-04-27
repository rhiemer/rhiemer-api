package br.com.rhiemer.api.jpa.criteria.execucao.builder;

import java.util.Arrays;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.annotations.OrderByAtributo;
import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.interfaces.ICriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.orderby.OrderByCriteriaJPAAsc;
import br.com.rhiemer.api.jpa.criteria.orderby.OrderByCriteriaJPADesc;
import br.com.rhiemer.api.jpa.enums.EnumTipoOrderBY;
import br.com.rhiemer.api.jpa.helper.HelperRootCriteria;

public interface IBuilderExecucaoOrderBy extends ICriteriaJPA {

	Class<?> getResultClass();

	default void builderExecucaoOrderBy(CriteriaBuilder builder, Root root, CriteriaQuery query) {
		Arrays.stream(getResultClass().getAnnotationsByType(OrderByAtributo.class))
				.filter(t -> HelperRootCriteria.isJoinInRoot(getResultClass(), root, t.value()))
				.forEach(t -> new ParametrizarCriteriaJPAParametro()
						.setClasse(t.tipo() == EnumTipoOrderBY.DESC ? OrderByCriteriaJPADesc.class
								: OrderByCriteriaJPAAsc.class)
						.setAtributo(t.value()).build(builder, root, query, null));
	}

}
