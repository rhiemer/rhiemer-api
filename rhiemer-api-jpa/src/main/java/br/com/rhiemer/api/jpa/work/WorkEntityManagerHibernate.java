package br.com.rhiemer.api.jpa.work;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import org.hibernate.jdbc.Work;

import br.com.rhiemer.api.jpa.helper.HelperEntityManager;

public abstract class WorkEntityManagerHibernate implements WorkEntityManager {

	protected Work workHibernate;

	@Override
	public void run(EntityManager entityManager) {
		final WorkEntityManagerHibernate workEntityManagerHibernate = this;
		this.workHibernate = new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				workEntityManagerHibernate.execute(entityManager, connection);
			}
		};
		HelperEntityManager.workConnectionBySession(entityManager, workHibernate);

	}

	@Override
	public abstract void execute(EntityManager entityManager, Connection connection);
}
