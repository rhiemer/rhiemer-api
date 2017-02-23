package br.com.rhiemer.api.dbunit.helper;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;

import br.com.rhiemer.api.dbunit.operation.IFlatDataSetBuilder;
import br.com.rhiemer.api.util.exception.APPSystemException;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.helper.HelperClassLoader;

public class HelperDbUnit {

	private static final String BUILDER_METHOD_DATASET = "build";

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

	public static IDataSet builderDataSet(Class<?> dataSetClass, String dataSet) {
		InputStream is = HelperClassLoader.getResourceAsStream(dataSet, HelperDbUnit.class.getClass());

		IDataSet iDataSet = null;
		Method method = null;
		try {
			method = dataSetClass.getMethod(BUILDER_METHOD_DATASET, InputStream.class);
		} catch (NoSuchMethodException | SecurityException e1) {
		}

		if (method != null) {
			Object builder = Helper.newInstance(dataSetClass);
			if (IFlatDataSetBuilder.class.isAssignableFrom(dataSetClass)) {
				((IFlatDataSetBuilder) builder).setDataSet(dataSet);
			}
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

}
