package br.com.rhiemer.api.dbunit.operation;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import br.com.rhiemer.api.dbunit.helper.HelperDbUnit;
import br.com.rhiemer.api.jpa.entity.Entity;
import br.com.rhiemer.api.jpa.helper.HelperEntityManager;
import br.com.rhiemer.api.util.exception.APPSystemException;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.helper.HelperClassLoader;

public class DBUnitOperations {

	public static final DatabaseOperation DELETE_INSERT = new CompositeOperationAPP(DatabaseOperation.DELETE,
			DatabaseOperation.INSERT);

	private static final String BUILDER_METHOD_DATASET = "build";
	private DatabaseConnection databaseConnection;
	private Connection connection;
	private boolean isClose;
	private WorkDbUnitOperation workDbUnitOperation;

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

	public void cleanInsertDataset(Class<?> dataSetClass, String... datasets) {
		operationExecute(DatabaseOperation.CLEAN_INSERT, dataSetClass, datasets);
	}

	public void cleanInsertDataset(String... dataset) {
		operationExecute(DatabaseOperation.CLEAN_INSERT, dataset);
	}

	public void cleanInsertDataset(IDataSet iDataSet) {
		operationExecute(DatabaseOperation.CLEAN_INSERT, iDataSet);
	}

	public void deleteInsertDataset(Class<?> dataSetClass, String... datasets) {
		operationExecute(DELETE_INSERT, dataSetClass, datasets);
	}

	public void deleteInsertDataset(String... dataset) {
		operationExecute(DELETE_INSERT, dataset);
	}

	public void deleteInsertDataset(IDataSet iDataSet) {
		operationExecute(DELETE_INSERT, iDataSet);
	}

	public void deleteDataset(Class<?> dataSetClass, String... datasets) {
		operationExecute(DatabaseOperation.DELETE, dataSetClass, datasets);
	}

	public void deleteDataset(String... dataset) {
		operationExecute(DatabaseOperation.DELETE, dataset);
	}

	public void deleteDataset(IDataSet iDataSet) {
		operationExecute(DatabaseOperation.DELETE, iDataSet);
	}

	public void deleteAllDataset(Class<?> dataSetClass, String... datasets) {
		operationExecute(DatabaseOperation.DELETE_ALL, dataSetClass, datasets);
	}

	public void deleteAllDataset(String... dataset) {
		operationExecute(DatabaseOperation.DELETE_ALL, dataset);
	}

	public void deleteAllDataset(IDataSet iDataSet) {
		operationExecute(DatabaseOperation.DELETE_ALL, iDataSet);
	}

	public void operationExecute(DatabaseOperation dataBaseOperation, IDataSet... iDataSets) {

		try {
			WorkDbUnitOperation workDbUnitOperationLocal = getWorkDbUnitOperation();
			if (workDbUnitOperationLocal != null) {
				workDbUnitOperationLocal.execute(dataBaseOperation, iDataSets);
			} else {
				List<IDataSet> listDataSets = Helper.convertArgs(iDataSets);
				for (IDataSet iDataSet : listDataSets) {
					dataBaseOperation.execute(databaseConnection, iDataSet);
				}
			}
		} catch (DatabaseUnitException | SQLException e) {
			throw new APPSystemException(e);
		} finally {
			closeConn();
		}
	}

	public void operationExecute(DatabaseOperation dataBaseOperation, String... dataset) {
		operationExecute(dataBaseOperation, FlatXmlDataSetBuilder.class, dataset);
	}

	public IDataSet builderDataSet(Class<?> dataSetClass, String dataSet) {

		InputStream is = HelperClassLoader.getResourceAsStream(dataSet, this.getClass());
		IDataSet iDataSet = null;
		Method method = null;
		try {
			method = dataSetClass.getMethod(BUILDER_METHOD_DATASET, InputStream.class);
		} catch (NoSuchMethodException | SecurityException e1) {
		}

		if (method != null) {
			Object builder = Helper.newInstance(dataSetClass);
			try {
				iDataSet = (IDataSet) method.invoke(builder, is);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new APPSystemException(e);
			}
		} else {
			iDataSet = (IDataSet) Helper.newInstance(dataSetClass, is);
		}
		return iDataSet;
	}

	public void addListDataSets(Class<?> dataSetClass, Set<IDataSet> listDataSets, String... datasets) {
		List<String> listStringDataSets = Helper.convertArgs(datasets);
		for (String dataset : listStringDataSets) {
			if (dataset.indexOf(",") > 0) {
				String[] split = dataset.trim().split(",");
				for (String dataSet2 : split) {
					addListDataSets(dataSetClass, listDataSets, dataSet2);
				}
				return;
			}
			IDataSet iDataSet = builderDataSet(dataSetClass, dataset);
			listDataSets.add(iDataSet);
		}

	}

	public Set<IDataSet> addListDataSets(Class<?> dataSetClass, String... datasets) {
		Set<IDataSet> listDataSets = new HashSet<>();
		addListDataSets(dataSetClass, listDataSets, datasets);
		return listDataSets;

	}

	public void operationExecute(DatabaseOperation dataBaseOperation, Class<?> dataSetClass, String... datasets) {
		boolean _isClose = this.isClose;
		try {
			Set<IDataSet> dataSets = addListDataSets(dataSetClass, datasets);
			try {
				_isClose = false;
				operationExecute(dataBaseOperation, dataSets.toArray(new IDataSet[] {}));
			} finally {
				isClose = _isClose;
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
				throw new APPSystemException(e);
			}
		}
	}

	public static Builder createBuilder() {
		return new Builder();
	}

	public WorkDbUnitOperation getWorkDbUnitOperation() {
		return workDbUnitOperation;
	}

	public void setWorkDbUnitOperation(WorkDbUnitOperation workDbUnitOperation) {
		this.workDbUnitOperation = workDbUnitOperation;
	}

	public static class Builder {
		private boolean habilitaHQL;
		private boolean isClose;
		private DatabaseConnection databaseConnection;
		private Connection connection;
		private DataSource dataSource;
		private String dataSourceName;
		private EntityManager entityManager;
		private WorkDbUnitOperation workDbUnitOperation;
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

		public Builder setWorkDbUnitOperation(WorkDbUnitOperation workDbUnitOperation) {
			this.workDbUnitOperation = workDbUnitOperation;
			return this;
		}

		public Builder setClose(boolean isClose) {
			this.isClose = isClose;
			return this;
		}

		public Builder setHabilitaHQL(boolean habilitaHQL) {
			this.habilitaHQL = habilitaHQL;
			return this;
		}

		public DBUnitOperations builder() {
			if (this.dataSource == null && this.connection == null && this.databaseConnection == null) {
				if (this.entityManager == null && StringUtils.isBlank(dataSourceName)) {
					if (this.classe != null) {
						this.entityManager = HelperEntityManager.getEntityManagerByClass(this.classe);
					} else if (!StringUtils.isBlank(this.className)) {
						this.entityManager = HelperEntityManager.getEntityManagerByClass(this.className);
					} else {
						this.entityManager = HelperEntityManager.getEntityManagerAplicacao();
					}
				}
				if (this.entityManager != null) {
					this.workDbUnitOperation = new WorkDbUnitOperationEntityManager(this.entityManager,
							this.habilitaHQL);
				} else if (!StringUtils.isBlank(dataSourceName)) {
					try {
						this.isClose = true;
						dataSource = (DataSource) new InitialContext().lookup(dataSourceName);
					} catch (NamingException e) {
						throw new APPSystemException(e);
					}
				}
			}

			if (this.databaseConnection == null) {
				if (this.dataSource != null && this.connection == null) {
					try {
						this.connection = dataSource.getConnection();
					} catch (SQLException e) {
						throw new APPSystemException(e);
					}
				}

				if (this.connection != null) {
					this.databaseConnection = HelperDbUnit.createDatabaseConnection(this.connection, this.habilitaHQL);
				}
			}

			DBUnitOperations dbUnitOperations = new DBUnitOperations();
			dbUnitOperations.setDatabaseConnection(this.databaseConnection);
			dbUnitOperations.setConnection(this.connection);
			dbUnitOperations.setClose(this.isClose);
			dbUnitOperations.setWorkDbUnitOperation(this.workDbUnitOperation);
			return dbUnitOperations;
		}

	}

}
