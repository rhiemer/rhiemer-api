package br.com.rhiemer.api.util.service;

import java.util.List;

import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucao;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

public interface PersistenceServiceBean<T, K> extends PersistenceService {

	<T extends PojoKeyAbstract> T adicionarOuAtualizar(T t);

	<T> T adicionar(T t);

	<T> T atualizar(T t);

	<T> void remover(T t);

	<K, T> T procurarPeloId(K k);

	<K, T> T procurarPeloIdLazy(K k,IExecucao... parametrosExecucao);

	<T, K> T buscarObjetoSalvoLazy(T t, Class<T> classe,IExecucao... parametrosExecucao);

	<T extends PojoKeyAbstract> T procurarPorUniqueKey(Object... k);

	<T extends PojoKeyAbstract> T procurarPorUniqueKeyByNome(String nome, Object... k);

	<T> List<T> listarTodos(IExecucao... parametrosExecucao);

	<K, T> K chaveDoObjeto(T t);

	<T> T copiarDoObjeto(T t);

	<T> T buscarObjetoSalvo(T t);

	<T> void atualizarComCopia(T t);

	<T, K> T removerPeloId(K id);

	<T> int contarTodos();

	<T> List<T> listaTodosPaginada(int firstResult, int maxResults,IExecucao... parametrosExecucao);

	void flush();
	
	<T> void deletar(T t);
	
	<T extends PojoKeyAbstract, K> T deletarPeloId(K id);

}