package br.com.rhiemer.api.jpa.dao;

import static br.com.rhiemer.api.jpa.constantes.ConstantesDesligarEvenetosJPA.PRE_UPDATE;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.beanutils.BeanUtils;

import br.com.rhiemer.api.jpa.builder.BuildJPA;
import br.com.rhiemer.api.jpa.entity.Entity;
import br.com.rhiemer.api.jpa.helper.HelperJPA;
import br.com.rhiemer.api.jpa.helper.JPAUtils;
import br.com.rhiemer.api.util.annotations.bean.BeanDiscovery;
import br.com.rhiemer.api.util.annotations.evento.DesligaEvento;
import br.com.rhiemer.api.util.annotations.evento.DesligaEventoInterceptorDiscovery;
import br.com.rhiemer.api.util.annotations.interceptor.SemTrace;
import br.com.rhiemer.api.util.annotations.interceptor.Trace;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

/**
 * Serviço genérico de persistência.
 * 
 * @author rhiemer
 *
 */

@Trace
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
	public <T> T adicionar(T t) {
		em.persist(t);
		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#atualizar(T)
	 */
	@Override
	public <T> T atualizar(T t) {
		return em.merge(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#adicionarOuatualizar(T)
	 */
	@Override
	public <T extends PojoKeyAbstract> T adicionarOuatualizar(T t) {
		T value = HelperJPA.listaEntityByUniqueKey(em, t, false);
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
	public <K, T> T procurarPeloId(Class<T> t, K k) {
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
	public <K, T> T procurarPeloIdLazy(Class<T> classe, K k) {

		T result = null;
		String namedQuery = classe.getSimpleName() + ".procurarPeloIdLazy";
		boolean verificaNamedQuery = JPAUtils.verifcaNamedQuery(em.getEntityManagerFactory(), namedQuery);
		if (verificaNamedQuery) {
			TypedQuery<T> query = em.createNamedQuery(namedQuery, classe);
			query.setParameter("id", k);
			List<T> results = query.getResultList();
			if (results.size() > 0)
				result = results.get(0);

		} else {
			result = em.find(classe, k);
		}

		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#listarTodos(java.lang.Class)
	 */
	@Override
	@DesligaEventoInterceptorDiscovery
	@DesligaEvento(chaveEvento = PRE_UPDATE)
	public <T> List<T> listarTodos(Class<T> t) {
		Query q = em.createQuery("select n from " + t.getName() + " n");
		return q.getResultList();
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
			throw new RuntimeException(e instanceof InvocationTargetException ? e.getCause() : e);
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
	public <T> T copiarDoObjeto(T t, Class<T> classe) {
		T objetoSalvo = buscarObjetoSalvo(t, classe);
		copiar(objetoSalvo, t);
		return objetoSalvo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rhiemer.api.jpa.dao.Dao#buscarObjetoSalvo(T, java.lang.Class)
	 */
	@Override
	public <T, K> T buscarObjetoSalvo(T t, Class<T> classe) {
		K chave = chaveDoObjeto(t);
		if (chave == null)
			throw new IllegalArgumentException("Chave do objeto " + t + " está nula .");
		T objetoSalvo = procurarPeloId(classe, chave);
		return objetoSalvo;
	}

	@Override
	public <T, K> T buscarObjetoSalvoLazy(T t, Class<T> classe) {
		K chave = chaveDoObjeto(t);
		if (chave == null)
			throw new IllegalArgumentException("Chave do objeto " + t + " está nula .");
		T objetoSalvo = procurarPeloIdLazy(classe, chave);
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
	public <T, K> T removerPeloId(K id, Class<T> classe) {
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
	public <T> List<T> listaTodosPaginada(Class<T> classe, int firstResult, int maxResults) {
		Query q = em.createQuery("select n from " + classe.getName() + " n");
		List<T> lista = q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
	public <T> List<T> excutarQueryList(BuildJPA query) {
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
	public <T> T excutarQueryUniqueResult(BuildJPA query) {

		return (T) query.buildQuery(em).getSingleResult();
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

}
