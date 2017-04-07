package br.com.rhiemer.api.util.dao;

import java.util.List;

import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucao;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

public interface Dao {

	<T> T adicionar(T t,IExecucao... parametrosExecucao);

	<T> T atualizar(T t,IExecucao... parametrosExecucao);

	<T extends PojoKeyAbstract> T adicionarOuatualizar(T t,IExecucao... parametrosExecucao);

	<T> void remover(T t);
	
	<T> void deletar(T t);

	<K, T> T procurarPeloId(Class<T> t, K k,IExecucao... parametrosExecucao);
	
	<T> T   procurarPeloIdLazy(Class<T> classe,Object... chaves);

	<K, T> T procurarPeloIdLazy(Class<T> classe, K k,IExecucao... parametrosExecucao);
	
	<T, K> T buscarObjetoSalvoLazy(T t, Class<T> classe,IExecucao... parametrosExecucao);
	
	<T extends PojoKeyAbstract> T procurarPorUniqueKeyParams(Class<T> t, Object... k);
	
	<T extends PojoKeyAbstract> T procurarPorUniqueKeyByNome(Class<T> t,String nome,Object... k);
	
    <T extends PojoKeyAbstract> T procurarPorUniqueKey(Class<T> t, Object[] k,IExecucao... parametrosExecucao);
	
	<T extends PojoKeyAbstract> T procurarPorUniqueKeyByNome(Class<T> t,String nome,Object[] k,IExecucao... parametrosExecucao);	

	<T> List<T> listarTodos(Class<T> t,IExecucao... parametrosExecucao);

	<K, T> K chaveDoObjeto(T t);

	<T> T copiarDoObjeto(T t, Class<T> classe, IExecucao... parametrosExecucao);

	<T, K> T buscarObjetoSalvo(T t, Class<T> classe,IExecucao... parametrosExecucao);

	<T> void atualizarComCopia(T t, Class<T> classe);

	<T, K> T removerPeloId(K id, Class<T> classe,IExecucao... parametrosExecucao);
	
	<T extends PojoKeyAbstract, K> T deletarPeloId(K id, Class<T> classe,IExecucao... parametrosExecucao);

	<T> int contarTodos(Class<T> classe);

	<T> List<T> listaTodosPaginada(Class<T> classe, int firstResult, int maxResults,IExecucao... parametrosExecucao);

}