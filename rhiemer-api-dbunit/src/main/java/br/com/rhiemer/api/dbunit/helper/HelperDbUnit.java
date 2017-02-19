package br.com.rhiemer.api.dbunit.helper;

import java.sql.Connection;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;

import br.com.rhiemer.api.util.exception.APPSystemException;

public class HelperDbUnit {

	private HelperDbUnit() {
	}

	public static void habilitaHQL(DatabaseConnection databaseConnection) {
		databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
				new HsqldbDataTypeFactory());
	}

	public static DatabaseConnection createDatabaseConnection(Connection connection, boolean habilitaHQL) {
		DatabaseConnection databaseConnection = null;
		try {
			databaseConnection = new DatabaseConnection(connection);
		} catch (DatabaseUnitException e) {
			throw new APPSystemException(e);
		}
		if (habilitaHQL) {
			HelperDbUnit.habilitaHQL(databaseConnection);
		}
		return databaseConnection;
	}

	public static DatabaseConnection createDatabaseConnection(Connection connection) {
		return createDatabaseConnection(connection, false);
	}

}
