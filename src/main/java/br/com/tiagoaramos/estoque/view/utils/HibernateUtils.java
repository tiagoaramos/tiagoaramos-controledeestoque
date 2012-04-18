package br.com.tiagoaramos.estoque.view.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtils {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("controle");

	private static EntityManager em = null;
	
	static {
		emf = Persistence.createEntityManagerFactory("controle");
		em = emf.createEntityManager(); 
	}
	
	public static void fechaConexao(){
		if (emf.isOpen()) {
			em.close();
			emf.close();
		}
	}
	
	public static EntityManager getEm() {
		if (em.isOpen()) {
			em.close();
		}
		em = emf.createEntityManager();
		return em;
	}

	public static EntityManager getEmOpen() {
		if (em.isOpen()) {
			return em;
		}
		em = emf.createEntityManager();
		return em;
	}
	
}
