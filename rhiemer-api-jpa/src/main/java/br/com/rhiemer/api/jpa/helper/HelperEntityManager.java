package br.com.rhiemer.api.jpa.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.spi.CDI;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import br.com.rhiemer.api.jpa.entity.Entity;
import br.com.rhiemer.api.jpa.mapper.EntityManagerMapClass;
import br.com.rhiemer.api.jpa.work.WorkEntityManager;

public class HelperEntityManager {

	public static Session getSession(EntityManager entityManager) {
		Session session = entityManager.unwrap(Session.class);
		return session;

	}
	
	public static void workConnectionBySession(EntityManager entityManager,Work work) {
		Session session = getSession(entityManager);
		session.doWork(work);
	}
	
	public static void workEntityManage(EntityManager entityManager,WorkEntityManager work) {
		work.run(entityManager);
		Session session = getSession(entityManager);		
		Work workHibernate = new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				work.execute(entityManager,connection);
			}
		};
		session.doWork(workHibernate);
	}

	public static Connection getConnectionBySession(Session session) {
		List<Connection> connections = new ArrayList<>();
		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				connections.add(connection);
			}
		});
		return connections.get(0);
	}

	public static Connection getConnectionByEntityManager(EntityManager entityManager) {
		Session session = getSession(entityManager);
		return getConnectionBySession(session);
	}
	
	public static EntityManager getEntityManagerByClass(Class<?extends Entity> classe) 
	{
		EntityManagerMapClass emMapClasse = CDI.current().select(EntityManagerMapClass.class).get();
		EntityManager entityManager = emMapClasse.getEntityManagerByEntityPadrao(classe);
		return entityManager;
	}
	
	public static EntityManager getEntityManagerByClass(String classe) 
	{
		EntityManagerMapClass emMapClasse = CDI.current().select(EntityManagerMapClass.class).get();
		EntityManager entityManager = emMapClasse.getEntityManagerByEntityPadrao(classe);
		return entityManager;
	}
	
	public static EntityManager getEntityManagerAplicacao() 
	{
		EntityManagerMapClass emMapClasse = CDI.current().select(EntityManagerMapClass.class).get();
		EntityManager entityManager = emMapClasse.getEntityManagerAplicacao();
		return entityManager;
	}
}
