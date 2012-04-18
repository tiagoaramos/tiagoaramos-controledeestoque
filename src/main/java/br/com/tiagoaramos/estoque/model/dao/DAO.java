/**
 * 
 */
package br.com.tiagoaramos.estoque.model.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.tiagoaramos.estoque.excecao.PersistenciaException;
import br.com.tiagoaramos.estoque.view.utils.HibernateUtils;

/**
 * @author tiago
 * 
 */
public abstract class DAO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5090700863724964363L;

	public DAO(){
	}
	


	@SuppressWarnings("unchecked")
	private Class classePersistente;

	public DAO(T obj) {
		super();
		this.classePersistente = obj.getClass();
	}

	public void persiste(T obj) throws PersistenciaException {
		EntityManager em = getEm();
		em.getTransaction().begin();
		try {
			em.persist(obj);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new PersistenciaException(e.getMessage(),
					"Erro ao Persistir o objeto");
		} finally {
			em.close();
		}
	}

	public void remove(T obj) throws PersistenciaException {

		EntityManager em = getEm();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			obj = em.merge(obj);
			em.remove(obj);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			e.printStackTrace();
			throw new PersistenciaException(e.getMessage(),
					"Erro ao Remover o objeto");
		} finally {
			em.close();
		}
	}

	public T merge(T obj) throws PersistenciaException {
		EntityManager em = getEm();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			T t = em.merge(obj);
			tx.commit();
			em.close();
			return t;
		} catch (Exception e) {
			tx.rollback();
			throw new PersistenciaException(e.getMessage(),
					"Erro ao atualizar o objeto");
		}
	}

	public void refresh(T obj) throws PersistenciaException {

		EntityManager em = getEm();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			em.refresh(obj);
			tx.commit();
			em.close();
			// return t;
		} catch (Exception e) {
			tx.rollback();
			throw new PersistenciaException(e.getMessage(),
					"Erro ao atualizar o objeto");
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public T buscarPorCodigo(Object obj) {
		EntityManager em = getEm();
		T t = (T) em.find(this.classePersistente, obj);
		return t;
	}

    public List<T> buscarTodos() {
        return executaQueryList(null, "SELECT c FROM " + classePersistente.getSimpleName() + " c ");
	}
	
	@SuppressWarnings("unchecked")
	public void removerPorCodigo(Object obj) throws PersistenciaException {
		EntityManager em = getEm();
		T t = (T) em.find(this.classePersistente, obj);
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.remove(t);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			e.printStackTrace();
			throw new PersistenciaException(e.getMessage(),
					"Erro ao Remover o objeto");
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	protected T executaNamedQuery(String namedQuery,
			Map<String, Object> parametros) {

		EntityManager em = getEm();

		Query query = em.createNamedQuery(namedQuery);

		if (parametros != null) {

			for (Map.Entry param : parametros.entrySet()) {
				query.setParameter((String) param.getKey(), param.getValue());

			}

		}
		T retorno = (T) query.getSingleResult();
		return retorno;
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> executaNamedQuery(Map<String, Object> parametros,
			String namedQuery) {

		EntityManager em = getEm();

		Query query = em.createNamedQuery(namedQuery);

		if (parametros != null) {

			for (Map.Entry param : parametros.entrySet()) {
				query.setParameter((String) param.getKey(), param.getValue());

			}

		}

		return (List<T>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	protected List<T> executaQueryList(Map<String, Object> parametros,
			String strQuery) {

		EntityManager em = getEm();

		Query query = em.createQuery(strQuery);

		if (parametros != null) {

			for (Map.Entry param : parametros.entrySet()) {
				query.setParameter((String) param.getKey(), param.getValue());

			}

		}

		List<T> list = query.getResultList();

		em.close();

		return list;

	}

	@SuppressWarnings("unchecked")
	protected T executaQuerySingle(Map<String, Object> parametros,
			String strQuery) {

		EntityManager em = getEm();

		Query query = em.createQuery(strQuery);

		if (parametros != null) {

			for (Map.Entry param : parametros.entrySet()) {
				query.setParameter((String) param.getKey(), param.getValue());

			}

		}

		T result = (T) query.getSingleResult();

		em.close();

		return result;

	}

	public void fechaConexao() {

		HibernateUtils.fechaConexao();

	}

	protected EntityManager getEm() {
		return HibernateUtils.getEm();
	}

	protected EntityManager getEmOpen() {
		return HibernateUtils.getEmOpen();
	}
	
}
