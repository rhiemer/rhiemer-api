package br.com.rhiemer.api.dbunit.operation;

import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

public interface WorkDbUnitOperation {
	
	void execute(DatabaseOperation dataBaseOperation, IDataSet... iDataSet);

}
