package br.com.rhiemer.api.jpa.criteria.execucao.builder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.annotations.ExecucaoAtributo;
import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.interfaces.ICriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.join.FetchCriteriaJPA;
import br.com.rhiemer.api.jpa.helper.HelperAtributeJPA;
import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucao;
import br.com.rhiemer.api.util.dao.parametros.execucao.ExecucaoAtributos;
import br.com.rhiemer.api.util.dao.parametros.execucao.ExecucaoLista;
import br.com.rhiemer.api.util.dao.parametros.execucao.ExecucaoSemAtributos;

public interface IBuilderExecucaoAtributos extends ICriteriaJPA {

	Class<?> getResultClass();

	List<IExecucao> getParametrosExecucao();

	default void builderExecucaoAtributos(CriteriaBuilder builder, Root root, CriteriaQuery query) {
		if (getParametrosExecucao() == null)
			return;

		if (getParametrosExecucao().stream().filter(ExecucaoSemAtributos.class::isInstance).findFirst().isPresent()) {
			return;
		}
		if (getParametrosExecucao().stream().filter(ExecucaoAtributos.class::isInstance).findFirst().isPresent()) {
			List<String> fieldsAtributo = HelperAtributeJPA.fieldsAtributo(getResultClass());
			fieldsAtributo.addAll(Arrays.stream(getResultClass().getAnnotationsByType(ExecucaoAtributo.class))
					.filter(t -> !HelperAtributeJPA.isFieldList(getResultClass(), t.value()))
					.map(ExecucaoAtributo::value).collect(Collectors.toList()));
			fieldsAtributo.stream().forEach(t -> new ParametrizarCriteriaJPAParametro()
					.setClasse(FetchCriteriaJPA.class).setAtributo(t).build(builder, root, query, null));
		}
		if (getParametrosExecucao().stream().filter(ExecucaoLista.class::isInstance).findFirst().isPresent()) {
			List<String> fieldsAtributo = HelperAtributeJPA.fieldsList(getResultClass());
			fieldsAtributo.addAll(Arrays.stream(getResultClass().getAnnotationsByType(ExecucaoAtributo.class))
					.filter(t -> HelperAtributeJPA.isFieldList(getResultClass(), t.value()))
					.map(ExecucaoAtributo::value).collect(Collectors.toList()));
			fieldsAtributo.stream().forEach(t -> new ParametrizarCriteriaJPAParametro()
					.setClasse(FetchCriteriaJPA.class).setAtributo(t).build(builder, root, query, null));
		}
	}

}
