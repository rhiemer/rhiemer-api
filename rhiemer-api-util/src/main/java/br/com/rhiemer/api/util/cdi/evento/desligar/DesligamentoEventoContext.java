package br.com.rhiemer.api.util.cdi.evento.desligar;

import javax.ejb.Remote;
import javax.enterprise.context.Dependent;

import br.com.rhiemer.api.util.annotations.bean.BeanDiscoveryEJB;
import static br.com.rhiemer.api.util.constantes.ConstantesModulos.MODULO_EJB_SESSION;

@Dependent
@BeanDiscoveryEJB(classe = DesligamentoEventoContext.class, modulo = MODULO_EJB_SESSION, beanName = "desligarEventoContext")
@Remote
public interface DesligamentoEventoContext {

	Boolean temDesligamentoEvento(ParametrosBuscaDesligamentoEventoDto parametro);

	void addDesligamentoEventoDto(DesligamentoEventoDto dto);

	void removeDesligamentoEventoDto(String chave);

}