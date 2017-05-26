package br.com.rhiemer.api.jpa.criteria.subquery;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.criteria.interfaces.ICriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.juncao.IBuilderFiltrosJPA;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.helper.HelperFindEntityClass;

public interface IBuilderSubQuery<T> extends ICriteriaJPA {

	Map<String, SubQueryJPA> getMapSubQueryAlias();

	default Map<String, SubQueryRetornoDTO> getMapSubQueryJpaRoot() {
		return null;
	}

	default T addSubQuerys(List<SubQueryJPA> filtros) {
		filtros.stream().forEach(t -> subQuery(t));
		return (T) this;
	}

	default T addSubQuerys(SubQueryJPA... filtros) {
		Helper.convertArgs(filtros).stream().forEach(t -> subQuery(t));
		return (T) this;
	}

	default SubQueryJPA<T> subQuery(SubQueryJPA subQuery) {
		subQuery.setMapSubQueryJpaRoot(getMapSubQueryJpaRoot());
		getMapSubQueryAlias().put(subQuery.getAlias(), subQuery);
		return subQuery;
	}

	default SubQueryJPA<T> subQuery(Class<?> classe) {
		SubQueryJPA<T> objeto = new SubQueryJPA((IBuilderFiltrosJPA) this, classe)
				.setAlias(HelperFindEntityClass.aliasClass(classe));
		return subQuery(objeto);
	}

	default SubQueryJPA<T> subQuery(String classeName) {
		SubQueryJPA<T> objeto = new SubQueryJPA((IBuilderFiltrosJPA) this, classeName).setAlias(classeName);
		return subQuery(objeto);
	}

	default SubQueryJPA<T> subQuery(Class<?> classe, String aliasName) {
		SubQueryJPA<T> objeto = new SubQueryJPA((IBuilderFiltrosJPA) this, classe).setAlias(aliasName);
		return subQuery(objeto);
	}

	default SubQueryJPA<T> subQuery(String classeName, String aliasName) {
		SubQueryJPA<T> objeto = new SubQueryJPA((IBuilderFiltrosJPA) this, classeName).setAlias(aliasName);
		return subQuery(objeto);
	}

	default void builderSubQuerys(CriteriaBuilder builder, Root root, AbstractQuery query) {
		if (getMapSubQueryJpaRoot() == null)
			return;
		if (getMapSubQueryAlias() != null && getMapSubQueryAlias().size() > 0)
			getMapSubQueryAlias()
					.forEach((t, x) -> getMapSubQueryJpaRoot().put(t, x.builderSubQueryMetodos(builder, root, query)));
	}

}
