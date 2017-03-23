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
import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucaoAtributos;
import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucaoLista;
import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucaoSemAtributos;

public interface IBuilderExecucaoAtributos extends ICriteriaJPA {

	Class<?> getCreateClass();

	List<IExecucao> getParametrosExecucao();

	default void builderExecucaoAtributos(CriteriaBuilder builder, Root root, CriteriaQuery query) {
		if (getParametrosExecucao() == null)
			return;
		
		if (getParametrosExecucao().stream().filter(t -> t instanceof IExecucaoSemAtributos).findFirst()
				.get() != null)
		{
			return;
		}	
		if (getParametrosExecucao().stream().filter(t -> t instanceof IExecucaoAtributos).findFirst()
				.get() != null) {
			List<String> fieldsAtributo = HelperAtributeJPA.fieldsAtributo(getCreateClass());
			fieldsAtributo.addAll(Arrays.stream(getCreateClass().getAnnotationsByType(ExecucaoAtributo.class))
					.filter(t -> !HelperAtributeJPA.isFieldList(getCreateClass(), t.value()))
					.map(ExecucaoAtributo::value).collect(Collectors.toList()));
			fieldsAtributo.stream().forEach(t -> new ParametrizarCriteriaJPAParametro()
					.setClasse(FetchCriteriaJPA.class).setAtributo(t).build(builder, root, query, null));
		}
		if (getParametrosExecucao().stream().filter(t -> t instanceof IExecucaoLista).findFirst().get() != null) {
			List<String> fieldsAtributo = HelperAtributeJPA.fieldsList(getCreateClass());
			fieldsAtributo.addAll(Arrays.stream(getCreateClass().getAnnotationsByType(ExecucaoAtributo.class))
					.filter(t -> HelperAtributeJPA.isFieldList(getCreateClass(), t.value()))
					.map(ExecucaoAtributo::value).collect(Collectors.toList()));
			fieldsAtributo.stream().forEach(t -> new ParametrizarCriteriaJPAParametro()
					.setClasse(FetchCriteriaJPA.class).setAtributo(t).build(builder, root, query, null));
		}
	}
	
	

}