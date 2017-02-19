package br.com.rhiemer.api.dbunit.transactional;

import br.com.rhiemer.api.dbunit.operation.DBUnitOperations;

public class DBUnitTransactional {

	public static final String DELETE_DATA_SET = "deletar";
	public static final String DELETE_INSERT_DATA_SET = "create";

	private void operacaoDataSet(String operacao, DBUnitOperations dbUnitOperations, String dataSet) {
		switch (operacao) {
		case DELETE_INSERT_DATA_SET:
			dbUnitOperations.deleteInsertDataset(dataSet);
			break;

		case DELETE_DATA_SET:
			dbUnitOperations.deleteDataset(dataSet);
			break;

		default:
			break;
		}
	}

	public void operacaoDataset(String operacao, String dataset) {
		DBUnitOperations dbUnitOperations = DBUnitOperations.createBuilder().builder();
		operacaoDataSet(operacao, dbUnitOperations, dataset);
	}

	public void operacaoDataset(String operacao, String dataset, String classe) {
		DBUnitOperations dbUnitOperations = DBUnitOperations.createBuilder().setClassName(classe).builder();
		operacaoDataSet(operacao, dbUnitOperations, dataset);
	}
}
