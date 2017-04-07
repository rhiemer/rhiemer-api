package br.com.rhiemer.api.util.service;

import java.util.List;

import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucao;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

public interface PersistenceServiceBean<T, K> extends PersistenceService {

	<T extends PojoKeyAbstract> T adicionarOuAtualizar(T t, IExecucao... parametrosExecucao);

	<T> T adicionar(T t, IExecucao... parametrosExecucao);

	<T> T atualizar(T t, IExecucao... parametrosExecucao);

	<T> void remover(T t);

	<K, T> T procurarPeloId(K k,IExecucao... parametrosExecucao);
	
	<T> T procurarPeloIdLazy(Object... chaves);

	<T> T procurarPeloIdLazy(Object[] chaves, IExecucao... parametrosExecucao);

	<T, K> T buscarObjetoSalvoLazy(T t, Class<T> classe, IExecucao... parametrosExecucao);

	<T extends PojoKeyAbstract> T procurarPorUniqueKeyParams(Object... k);

	<T extends PojoKeyAbstract> T procurarPorUniqueKeyByNome(String nome, Object... k);

	<T extends PojoKeyAbstract> T procurarPorUniqueKey(Object[] k, IExecucao... parametrosExecucao);

	<T extends PojoKeyAbstract> T procurarPorUniqueKeyByNome(String nome, Object[] k, IExecucao... parametrosExecucao);

	<T> List<T> listarTodos(IExecucao... parametrosExecucao);

	<K, T> K chaveDoObjeto(T t);

	<T> T copiarDoObjeto(T t, IExecucao... parametrosExecucao);

	<T> T buscarObjetoSalvo(T t, IExecucao... parametrosExecucao);

	<T> void atualizarComCopia(T t);

	<T, K> T removerPeloId(K id,IExecucao... parametrosExecucao);

	<T> int contarTodos();

	<T> List<T> listaTodosPaginada(int firstResult, int maxResults, IExecucao... parametrosExecucao);

	void flush();

	<T> void deletar(T t);

	<T extends PojoKeyAbstract, K> T deletarPeloId(K id,IExecucao... parametrosExecucao);

}