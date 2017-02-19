package br.com.rhiemer.api.dbunit.operation;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

import br.com.rhiemer.api.dbunit.helper.HelperDbUnit;
import br.com.rhiemer.api.jpa.work.WorkEntityManagerHibernate;
import br.com.rhiemer.api.util.exception.APPSystemException;

public class WorkDbUnitOperationEntityManager extends WorkEntityManagerHibernate implements WorkDbUnitOperation {

	private EntityManager entityManager;
	private DatabaseOperation dataBaseOperation;
	private IDataSet iDataSet;
	private boolean habilitaHQL = false;

	public WorkDbUnitOperationEntityManager(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	public WorkDbUnitOperationEntityManager(EntityManager entityManager, boolean habilitaHQL) {
		super();
		this.entityManager = entityManager;
		this.habilitaHQL = habilitaHQL;
	}

	@Override
	public void execute(DatabaseOperation dataBaseOperation, IDataSet iDataSet) {
		this.dataBaseOperation = dataBaseOperation;
		this.iDataSet = iDataSet;
		this.run(this.entityManager);
	}

	@Override
	public void execute(EntityManager entityManager, Connection connection) {
		DatabaseConnection databaseConnection = HelperDbUnit.createDatabaseConnection(connection, habilitaHQL);
		try {
			dataBaseOperation.execute(databaseConnection, iDataSet);
		} catch (DatabaseUnitException | SQLException e) {
			throw new APPSystemException(e);
		}
	}

}
