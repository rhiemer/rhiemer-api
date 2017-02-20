package br.com.rhiemer.api.jpa.work;

import java.sql.Connection;

import javax.persistence.EntityManager;

public interface WorkEntityManager {

	void run(EntityManager entityManager);
	void execute(EntityManager entityManager, Connection connection);
}
