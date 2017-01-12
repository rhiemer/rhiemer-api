package br.com.rhiemer.api.util.dao;

import java.util.List;

import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

public interface Dao {

	<T> T adicionar(T t);

	<T> T atualizar(T t);

	<T extends PojoKeyAbstract> T adicionarOuatualizar(T t);

	<T> void remover(T t);

	<K, T> T procurarPeloId(Class<T> t, K k);

	<K, T> T procurarPeloIdLazy(Class<T> classe, K k);
	
	<T, K> T buscarObjetoSalvoLazy(T t, Class<T> classe);

	<T> List<T> listarTodos(Class<T> t);

	<K, T> K chaveDoObjeto(T t);

	<T> T copiarDoObjeto(T t, Class<T> classe);

	<T, K> T buscarObjetoSalvo(T t, Class<T> classe);

	<T> void atualizarComCopia(T t, Class<T> classe);

	<T, K> T removerPeloId(K id, Class<T> classe);

	<T> int contarTodos(Class<T> classe);

	<T> List<T> listaTodosPaginada(Class<T> classe, int firstResult, int maxResults);

}