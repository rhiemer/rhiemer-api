package br.com.rhiemer.api.util.cdi.desligar.evento;

import javax.enterprise.context.Dependent;

import br.com.rhiemer.api.util.annotations.bean.BeanDiscoveryEJB;

@Dependent
@BeanDiscoveryEJB(classe = DesligamentoEventoContext.class, app = "app", modulo = "rhiemer-api-ejb", beanName = "desligamentoEventoContext")
public interface DesligamentoEventoContext {

	Boolean temDesligamentoEvento(ParametrosBuscaDesligamentoEventoDto parametro);

	void addDesligamentoEventoDto(DesligamentoEventoDto dto);

	void removeDesligamentoEventoDto(String chave);

}