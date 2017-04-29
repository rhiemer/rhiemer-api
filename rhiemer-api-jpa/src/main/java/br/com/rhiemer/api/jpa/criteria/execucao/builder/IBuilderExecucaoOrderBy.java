package br.com.rhiemer.api.jpa.criteria.execucao.builder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.path.SingularAttributePath;

import br.com.rhiemer.api.jpa.annotations.OrderByAtributo;
import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.interfaces.ICriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.orderby.OrderByCriteriaJPAAsc;
import br.com.rhiemer.api.jpa.criteria.orderby.OrderByCriteriaJPADesc;
import br.com.rhiemer.api.jpa.enums.EnumTipoOrderBY;
import br.com.rhiemer.api.jpa.helper.HelperRootCriteria;
import br.com.rhiemer.api.util.helper.Helper;

public interface IBuilderExecucaoOrderBy extends ICriteriaJPA {

	Class<?> getResultClass();

	default void builderExecucaoOrderBy(CriteriaBuilder builder, Root root, CriteriaQuery query) {

		Helper.annotationsByType(this.getResultClass(), OrderByAtributo.class).stream()
				.filter(t -> HelperRootCriteria.fieldInCriteira(getResultClass(), root, t.value()))
				.filter(x -> !query.getOrderList().stream().map(t -> ((Order) t).getExpression())
						.filter(t -> t instanceof Path).filter(t -> ((Path) t).getAlias().equalsIgnoreCase(x.value()))
						.findFirst().isPresent())
				.forEach(t -> new ParametrizarCriteriaJPAParametro()
						.setClasse(t.tipo() == EnumTipoOrderBY.DESC ? OrderByCriteriaJPADesc.class
								: OrderByCriteriaJPAAsc.class)
						.setAtributo(t.value()).build(builder, root, query, null));
	}

}
