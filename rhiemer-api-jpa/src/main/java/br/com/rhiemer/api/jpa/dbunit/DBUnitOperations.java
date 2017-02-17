package br.com.rhiemer.api.jpa.dbunit;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;

import br.com.rhiemer.api.jpa.entity.Entity;
import br.com.rhiemer.api.jpa.helper.EntityManagerHelper;

public class DBUnitOperations {

	private DatabaseConnection databaseConnection;
	private Connection connection;
	private boolean isClose;

	public DBUnitOperations() {
		super();
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public boolean isClose() {
		return isClose;
	}

	public void setClose(boolean isClose) {
		this.isClose = isClose;
	}

	public DatabaseConnection getDatabaseConnection() {
		return databaseConnection;
	}

	public void setDatabaseConnection(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	public void cleanInsertDataset(Class<? extends IDataSet> dataSetClass, String... datasets) {
		operationExecute(DatabaseOperation.CLEAN_INSERT, dataSetClass, datasets);
	}

	public void cleanInsertDataset(String... dataset) {
		operationExecute(DatabaseOperation.CLEAN_INSERT, dataset);
	}

	public void cleanInsertDataset(IDataSet iDataSet) {
		operationExecute(DatabaseOperation.CLEAN_INSERT, iDataSet);
	}

	public void deleteAllDataset(Class<? extends IDataSet> dataSetClass, String... datasets) {
		operationExecute(DatabaseOperation.DELETE_ALL, dataSetClass, datasets);
	}

	public void deleteAllDataset(String... dataset) {
		operationExecute(DatabaseOperation.DELETE_ALL, dataset);
	}

	public void deleteAllDataset(IDataSet iDataSet) {
		operationExecute(DatabaseOperation.DELETE_ALL, iDataSet);
	}

	public void operationExecute(DatabaseOperation dataBaseOperation, IDataSet iDataSet) {

		try {
			dataBaseOperation.execute(databaseConnection, iDataSet);
		} catch (DatabaseUnitException | SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeConn();
		}
	}

	public void operationExecute(DatabaseOperation dataBaseOperation, String... dataset) {
		operationExecute(dataBaseOperation, XmlDataSet.class, dataset);
	}

	public void operationExecute(DatabaseOperation dataBaseOperation, Class<? extends IDataSet> dataSetClass,
			String... datasets) {
		boolean _isClose = this.isClose;
		try {
			for (String dataset : datasets) {
				if (dataset.indexOf(",") > 0) {
					String[] split = dataset.trim().split(",");
					for (String dataSet2 : split) {
						operationExecute(dataBaseOperation, dataSetClass, dataSet2.trim());
					}
					return;
				}
				if (!dataset.startsWith("/")) {
					dataset = "/" + dataset;
				}
				InputStream is = this.getClass().getResourceAsStream(dataset);
				IDataSet iDataSet;
				try {

					iDataSet = dataSetClass.getConstructor(IDataSet.class).newInstance(is);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					throw new RuntimeException(e);
				}
				try {
					_isClose = false;
					operationExecute(dataBaseOperation, iDataSet);
				} finally {
					isClose = _isClose;
				}
			}
		} finally {
			closeConn();
		}
	}

	protected void closeConn() {
		if (isClose && connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static Builder createBuilder() {
		return new Builder();
	}

	public static class Builder {
		private boolean isClose;
		private DatabaseConnection databaseConnection;
		private Connection connection;
		private DataSource dataSource;
		private String dataSourceName;
		private EntityManager entityManager;
		private Class<? extends Entity> classe;
		private String className;

		public Builder setDatabaseConnection(DatabaseConnection databaseConnection) {
			this.databaseConnection = databaseConnection;
			return this;
		}

		public Builder setDataSource(DataSource dataSource) {
			this.dataSource = dataSource;
			return this;
		}

		public Builder setDataSourceName(String dataSourceName) {
			this.dataSourceName = dataSourceName;
			return this;
		}

		public Builder setConnection(Connection connection) {
			this.connection = connection;
			return this;
		}

		public Builder setClasse(Class<? extends Entity> classe) {
			this.classe = classe;
			return this;
		}

		public Builder setClassName(String className) {
			this.className = className;
			return this;
		}

		public Builder setEntityManager(EntityManager entityManager) {
			this.entityManager = entityManager;
			return this;
		}

		public Builder setClose(boolean isClose) {
			this.isClose = isClose;
			return this;
		}

		public DBUnitOperations builder() {
			if (this.dataSource == null && this.connection == null && this.databaseConnection == null) {
				if (this.entityManager == null && StringUtils.isBlank(dataSourceName)) {
					if (this.classe != null) {
						this.entityManager = EntityManagerHelper.getEntityManagerByClass(this.classe);
					} else if (!StringUtils.isBlank(this.className)) {
						this.entityManager = EntityManagerHelper.getEntityManagerByClass(this.className);
					} else {
						this.entityManager = EntityManagerHelper.getEntityManagerAplicacao();
					}
				}
				if (this.entityManager != null) {
					this.connection = EntityManagerHelper.getConnectionByEntityManager(this.entityManager);
				} else if (!StringUtils.isBlank(dataSourceName)) {
					try {
						this.isClose = true;
						dataSource = (DataSource) new InitialContext().lookup(dataSourceName);
					} catch (NamingException e) {
						throw new RuntimeException(e);
					}
				}
			}

			if (this.databaseConnection == null) {
				if (this.dataSource != null && this.connection == null) {
					try {
						this.connection = dataSource.getConnection();
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
				}

				if (this.connection != null) {
					try {
						databaseConnection = new DatabaseConnection(this.connection);
					} catch (DatabaseUnitException e) {
						throw new RuntimeException(e);
					}
				}
			}

			DBUnitOperations dbUnitOperations = new DBUnitOperations();
			dbUnitOperations.setDatabaseConnection(this.databaseConnection);
			dbUnitOperations.setConnection(this.connection);
			dbUnitOperations.setClose(this.isClose);
			return dbUnitOperations;
		}

	}

}
