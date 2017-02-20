package br.com.rhiemer.api.dbunit.operation;

import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;

public interface ICompositeOperationAPP {

	void execute(IDatabaseConnection connection, IDataSet... dataSet) throws DatabaseUnitException, SQLException;

	void executeByDataSet(IDatabaseConnection connection, IDataSet... dataSet)
			throws DatabaseUnitException, SQLException;

}