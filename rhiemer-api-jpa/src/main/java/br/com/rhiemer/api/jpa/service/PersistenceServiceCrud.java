package br.com.rhiemer.api.jpa.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;

import br.com.rhiemer.api.jpa.builder.BuildJPA;
import br.com.rhiemer.api.jpa.dao.DaoJPA;
import br.com.rhiemer.api.jpa.entity.Entity;
import br.com.rhiemer.api.util.annotations.app.ServiceAplicacao;
import br.com.rhiemer.api.util.annotations.interceptor.SemTrace;
import br.com.rhiemer.api.util.annotations.interceptor.Trace;
import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucao;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

@Trace
@ServiceAplicacao
public class PersistenceServiceCrud<T extends Entity, K extends Serializable>
		implements PersistenceServiceBeanJPA<T, K> {

	private DaoJPA dao;
	private Class<T> classeObjeto;
	private Class<K> classeChave;

	@SemTrace
	public DaoJPA getDao() {
		return dao;
	}

	@SemTrace
	public void setDao(DaoJPA dao) {
		this.dao = dao;
	}

	@SemTrace
	public EntityManager getEntityManager() {
		return this.getDao().getEntityManager();
	}

	@SemTrace
	public Class<T> getClasseObjeto() {
		return this.classeObjeto;
	}

	@SemTrace
	public Class<K> getClasseChave() {
		return this.classeChave;
	}

	@SemTrace
	public void setClasseObjeto(Class<T> classeObjeto) {
		this.classeObjeto = classeObjeto;
	}

	@SemTrace
	public void setClasseChave(Class<K> classeChave) {
		this.classeChave = classeChave;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.util.service.PersistenceServiceBean#adicionar(T)
	 */
	@Override
	public <T> T adicionar(T t) {
		return this.getDao().adicionar(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.util.service.PersistenceServiceBean#atualizar(T)
	 */
	@Override
	public <T> T atualizar(T t) {
		return this.getDao().atualizar(t);
	}

	@Override
	public <T extends PojoKeyAbstract> T adicionarOuAtualizar(T t) {
		return this.getDao().adicionarOuatualizar(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.util.service.PersistenceServiceBean#remover(T)
	 */
	@Override
	public <T> void remover(T t) {
		this.getDao().remover(t);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.rhiemer.api.util.service.PersistenceServiceBean#procurarPeloId(K)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <K, T> T procurarPeloId(K k) {
		return this.getDao().procurarPeloId((Class<T>) classeObjeto, k);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <K, T> T procurarPeloIdLazy(K k,IExecucao... parametrosExecucao) {
		return this.getDao().procurarPeloIdLazy((Class<T>) classeObjeto, k,parametrosExecucao);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T extends PojoKeyAbstract> T procurarPorUniqueKey(Object... k) {
		return this.getDao().procurarPorUniqueKey((Class<T>) classeObjeto, k);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T extends PojoKeyAbstract> T procurarPorUniqueKeyByNome(String nome, Object... k) {
		return this.getDao().procurarPorUniqueKeyByNome((Class<T>) classeObjeto, nome, k);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.util.service.PersistenceServiceBean#listarTodos()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T> List<T> listarTodos(IExecucao... parametrosExecucao) {
		return this.getDao().listarTodos((Class<T>) classeObjeto,parametrosExecucao);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.rhiemer.api.util.service.PersistenceServiceBean#chaveDoObjeto(T)
	 */
	@Override
	public <K, T> K chaveDoObjeto(T t) {
		return this.getDao().chaveDoObjeto(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.rhiemer.api.util.service.PersistenceServiceBean#copiarDoObjeto(T)
	 */
	@Override
	public <T> T copiarDoObjeto(T t) {
		return this.getDao().copiarDoObjeto(t, (Class<T>) classeObjeto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.rhiemer.api.util.service.PersistenceServiceBean#buscarObjetoSalvo
	 * (T)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T> T buscarObjetoSalvo(T t) {
		return this.getDao().buscarObjetoSalvo(t, (Class<T>) classeObjeto);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T, K> T buscarObjetoSalvoLazy(T t, Class<T> classe,IExecucao... parametrosExecucao) {
		return this.getDao().buscarObjetoSalvoLazy(t, (Class<T>) classeObjeto,parametrosExecucao);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.rhiemer.api.util.service.PersistenceServiceBean#atualizarComCopia
	 * (T)
	 */
	@Override
	public <T> void atualizarComCopia(T t) {
		this.getDao().atualizarComCopia(t, (Class<T>) classeObjeto);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.rhiemer.api.util.service.PersistenceServiceBean#removerPeloId(K)
	 */
	@Override
	public <T, K> T removerPeloId(K id) {
		return this.getDao().removerPeloId(id, (Class<T>) classeObjeto);
	}

	@Override
	public <T> void deletar(T t) {
		this.getDao().deletar(t);

	}

	@Override
	public <T extends PojoKeyAbstract, K> T deletarPeloId(K id) {
		return this.getDao().deletarPeloId(id, (Class<T>) classeObjeto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.util.service.PersistenceServiceBean#contarTodos()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T> int contarTodos() {
		// TODO Auto-generated method stub
		return this.getDao().contarTodos((Class<T>) classeObjeto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.rhiemer.api.util.service.PersistenceServiceBean#listaTodosPaginada
	 * (int, int)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T> List<T> listaTodosPaginada(int firstResult, int maxResults,IExecucao... parametrosExecucao) {
		return this.getDao().listaTodosPaginada((Class<T>) classeObjeto, firstResult, maxResults,parametrosExecucao);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.util.service.PersistenceServiceBean#flush()
	 */
	@Override
	public void flush() {
		this.getDao().flush();

	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T> List<T> excutarQueryList(BuildJPA query) {
		return (List<T>) this.getDao().excutarQueryList(query);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T> T excutarQueryUniqueResult(BuildJPA query) {
		return (T) this.getDao().excutarQueryUniqueResult(query);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T> List<T> excutarTypeQueryList(BuildJPA query) {
		if (query.getResultClass() == null)
			query.setResultClass(classeObjeto);
		return this.getDao().excutarQueryList(query);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T> T excutarTypeQueryUniqueResult(BuildJPA query) {
		if (query.getResultClass() == null)
			query.setResultClass(classeObjeto);
		return this.getDao().excutarQueryUniqueResult(query);
	}

	public int excutarUpdateQuery(BuildJPA query) {
		return this.getDao().excutarUpdateQuery(query);
	}

}
