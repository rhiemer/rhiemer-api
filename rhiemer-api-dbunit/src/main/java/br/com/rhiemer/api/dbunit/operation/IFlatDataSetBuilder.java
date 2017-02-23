package br.com.rhiemer.api.dbunit.operation;

import java.io.InputStream;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;

public interface IFlatDataSetBuilder {

	IDataSet build(InputStream inputStream) throws DataSetException;
	IFlatDataSetBuilder setDataSet(String dataSet);

}