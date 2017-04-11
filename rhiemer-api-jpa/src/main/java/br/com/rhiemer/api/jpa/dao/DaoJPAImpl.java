package br.com.rhiemer.api.jpa.dao;

import static br.com.rhiemer.api.jpa.constantes.ConstantesDesligarEvenetosJPA.PRE_UPDATE;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.apache.commons.beanutils.BeanUtils;

import br.com.rhiemer.api.jpa.annotations.TratarLazy;
import br.com.rhiemer.api.jpa.builder.BuildJPA;
import br.com.rhiemer.api.jpa.builder.BuilderCriteriaJPA;
import br.com.rhiemer.api.jpa.builder.BuilderQuery;
import br.com.rhiemer.api.jpa.entity.Entity;
import br.com.rhiemer.api.jpa.helper.HelperHQL;
import br.com.rhiemer.api.jpa.helper.JPAUtils;
import br.com.rhiemer.api.util.annotations.evento.DesligaEvento;
import br.com.rhiemer.api.util.annotations.evento.DesligaEventoInterceptorDiscovery;
import br.com.rhiemer.api.util.annotations.interceptor.SemTrace;
import br.com.rhiemer.api.util.annotations.interceptor.Trace;
import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucao;
import br.com.rhiemer.api.util.dto.Pager;
import br.com.rhiemer.api.util.exception.APPSystemException;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.helper.PojoHelper;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

/**
 * Serviço genérico de persistência.
 * 
 * @author rhiemer
 *
 */

@Trace
@TratarLazy
public class DaoJPAImpl implements DaoJPA {

	private EntityManager em;

	@Override
	@SemTrace
	public EntityManager getEntityManager() {
		return this.em;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#setEntityManager(javax.persistence.
	 * EntityManager)
	 */
	@Override
	@SemTrace
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#adicionar(T)
	 */
	@Override
	public <T> T adicionar(T t, IExecucao... parametrosExecucao) {
		em.persist(t);
		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#atualizar(T)
	 */
	@Override
	public <T> T atualizar(T t, IExecucao... parametrosExecucao) {
		return em.merge(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#adicionarOuatualizar(T)
	 */
	@Override
	public <T extends PojoKeyAbstract> T adicionarOuatualizar(T t, IExecucao... parametrosExecucao) {
		T value = excutarQueryUniqueResult(
				BuilderCriteriaJPA.builderCreate().resultClass(t.getClass()).build().uniqueKeyValida(t));
		if (value == null) {
			em.persist(t);
			return t;
		} else {
			t.setPrimaryKey(value);
			return em.merge(t);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#remover(T)
	 */
	@Override
	@DesligaEventoInterceptorDiscovery
	@DesligaEvento(chaveEvento = PRE_UPDATE)
	public <T> void remover(T t) {
		em.remove(em.merge(t));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#procurarPeloId(java.lang.Class, K)
	 */
	@Override
	@DesligaEvento(chaveEvento = PRE_UPDATE)
	public <K, T> T procurarPeloId(Class<T> t, K k, IExecucao... parametrosExecucao) {
		return em.find(t, k);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#procurarPeloIdLazy(java.lang.Class,
	 * K)
	 */
	@Override
	@DesligaEventoInterceptorDiscovery
	@DesligaEvento(chaveEvento = PRE_UPDATE)
	public <T> T procurarPeloIdLazy(Class<T> classe, Object... chaves) {

		T result = null;
		String namedQuery = classe.getSimpleName() + ".procurarPeloIdLazy";
		boolean verificaNamedQuery = JPAUtils.verifcaNamedQuery(em.getEntityManagerFactory(), namedQuery);
		if (verificaNamedQuery) {
			result = excutarQueryUniqueResult(
					BuilderQuery.builder().resultClass(classe).sql(namedQuery).build().addParameterPrimaryKey(chaves));
		} else {
			result = excutarQueryUniqueResult(
					BuilderCriteriaJPA.builderCreate().resultClass(classe).build().primaryKey(chaves));
		}

		return result;

	}

	@Override
	public <K, T> T procurarPeloIdLazy(Class<T> classe, K k, IExecucao... parametrosExecucao) {
		return procurarPeloIdLazy(classe, k);
	}

	@Override
	@DesligaEventoInterceptorDiscovery
	@DesligaEvento(chaveEvento = PRE_UPDATE)
	public <T extends PojoKeyAbstract> T procurarPorUniqueKeyParams(Class<T> t, Object... k) {
		return excutarQueryUniqueResult(BuilderCriteriaJPA.builderCreate().resultClass(t).build().uniqueKeyByParams(k));

	}

	@Override
	@DesligaEventoInterceptorDiscovery
	@DesligaEvento(chaveEvento = PRE_UPDATE)
	public <T extends PojoKeyAbstract> T procurarPorUniqueKeyByNome(Class<T> t, String nome, Object... k) {
		return excutarQueryUniqueResult(
				BuilderCriteriaJPA.builderCreate().resultClass(t).build().uniqueKeyByNome(nome, k));
	}

	@Override
	@DesligaEventoInterceptorDiscovery
	@DesligaEvento(chaveEvento = PRE_UPDATE)
	public <T extends PojoKeyAbstract> T procurarPorUniqueKey(Class<T> t, Object[] k, IExecucao... parametrosExecucao) {
		return excutarQueryUniqueResult(BuilderCriteriaJPA.builderCreate().resultClass(t).build().uniqueKeyByParams(k));
	}

	@Override
	@DesligaEventoInterceptorDiscovery
	@DesligaEvento(chaveEvento = PRE_UPDATE)
	public <T extends PojoKeyAbstract> T procurarPorUniqueKeyByNome(Class<T> t, String nome, Object[] k,
			IExecucao... parametrosExecucao) {
		return excutarQueryUniqueResult(
				BuilderCriteriaJPA.builderCreate().resultClass(t).build().uniqueKeyByNome(nome, k));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#listarTodos(java.lang.Class)
	 */
	@Override
	@DesligaEventoInterceptorDiscovery
	@DesligaEvento(chaveEvento = PRE_UPDATE)
	public <T> List<T> listarTodos(Class<T> t, IExecucao... parametrosExecucao) {
		return excutarQueryList(
				BuilderCriteriaJPA.builderCreate().resultClass(t).parametrosExecucao(parametrosExecucao).build());
	}

	private <T> void copiar(T t1, T t2) {

		Object obj = null;
		if (t1 instanceof Entity)
			obj = ((Entity) t1).clone();
		else
			obj = t1;
		try {
			BeanUtils.copyProperties(obj, t2);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new APPSystemException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#chaveDoObjeto(T)
	 */
	@Override
	public <K, T> K chaveDoObjeto(T t) {
		return (K) em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#copiarDoObjeto(T, java.lang.Class)
	 */
	@Override
	public <T> T copiarDoObjeto(T t, Class<T> classe, IExecucao... parametrosExecucao) {
		T objetoSalvo = buscarObjetoSalvo(t, classe, parametrosExecucao);
		copiar(objetoSalvo, t);
		return objetoSalvo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#buscarObjetoSalvo(T, java.lang.Class)
	 */
	@Override
	public <T, K> T buscarObjetoSalvo(T t, Class<T> classe, IExecucao... parametrosExecucao) {
		K chave = chaveDoObjeto(t);
		if (chave == null)
			throw new IllegalArgumentException("Chave do objeto " + t + " está nula .");
		T objetoSalvo = procurarPeloId(classe, chave);
		return objetoSalvo;
	}

	@Override
	public <T, K> T buscarObjetoSalvoLazy(T t, Class<T> classe, IExecucao... parametrosExecucao) {
		K chave = chaveDoObjeto(t);
		if (chave == null)
			throw new IllegalArgumentException("Chave do objeto " + t + " está nula .");
		T objetoSalvo = procurarPeloIdLazy(classe, chave, parametrosExecucao);
		return objetoSalvo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#atualizarComCopia(T, java.lang.Class)
	 */
	@Override
	public <T> void atualizarComCopia(T t, Class<T> classe) {
		T copia = copiarDoObjeto(t, classe);
		atualizar(copia);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#removerPeloId(K, java.lang.Class)
	 */
	@Override
	public <T, K> T removerPeloId(K id, Class<T> classe, IExecucao... parametrosExecucao) {
		T instancia = procurarPeloId(classe, id);
		remover(instancia);
		return instancia;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#contarTodos(java.lang.Class)
	 */
	@Override
	@DesligaEventoInterceptorDiscovery
	@DesligaEvento(chaveEvento = PRE_UPDATE)
	public <T> int contarTodos(Class<T> classe) {
		long result = (Long) em.createQuery("select count(n) from " + classe.getName() + " n").getSingleResult();

		return (int) result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#listaTodosPaginada(java.lang.Class,
	 * int, int)
	 */
	@Override
	@DesligaEventoInterceptorDiscovery
	@DesligaEvento(chaveEvento = PRE_UPDATE)
	public <T> List<T> listaTodosPaginada(Class<T> classe, int firstResult, int maxResults,
			IExecucao... parametrosExecucao) {
		List<T> lista = excutarQueryList(BuilderCriteriaJPA.builderCreate().resultClass(classe)
				.parametrosExecucao(parametrosExecucao).pager(new Pager(firstResult, maxResults)).build());
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#flush()
	 */
	@Override
	public void flush() {
		em.flush();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.rhiemer.api.jpa.dao.Dao#excutarQueryList(br.com.rhiemer.api.jpa.
	 * builder.BuildJPA)
	 */
	@Override
	@DesligaEventoInterceptorDiscovery
	@DesligaEvento(chaveEvento = PRE_UPDATE)
	public <T> List<T> excutarQueryList(BuildJPA query, IExecucao... parametrosExecucao) {
		return query.buildQuery(em).getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.rhiemer.api.jpa.dao.Dao#excutarQueryUniqueResult(br.com.rhiemer.
	 * api.jpa.builder.BuildJPA)
	 */
	@Override
	@DesligaEventoInterceptorDiscovery
	@DesligaEvento(chaveEvento = PRE_UPDATE)
	public <T> T excutarQueryUniqueResult(BuildJPA query, IExecucao... parametrosExecucao) {
		try {
			return (T) query.buildQuery(em).getSingleResult();

		} catch (NonUniqueResultException | NoResultException e) {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.rhiemer.api.jpa.dao.Dao#excutarUpdateQuery(br.com.rhiemer.api.jpa.
	 * builder.BuildJPA)
	 */
	@Override
	public int excutarUpdateQuery(BuildJPA query) {
		return query.buildQuery(em).executeUpdate();
	}

	@Override
	public <T> void deletar(T t) {
		String strDelete = HelperHQL.sqlDelete((PojoKeyAbstract) t);
		if (!Helper.isBlank(strDelete)) {
			BuildJPA query = BuilderQuery.builder().sql(strDelete).build().addParameterPrimaryKey(t);
			excutarUpdateQuery(query);
		}
	}

	@Override
	public <T extends PojoKeyAbstract, K> T deletarPeloId(K id, Class<T> classe, IExecucao... parametrosExecucao) {
		if (id == null)
			return null;
		T instancia = PojoHelper.newInstacePrimaryKey(classe, id);
		deletar(instancia);
		return instancia;
	}

}
