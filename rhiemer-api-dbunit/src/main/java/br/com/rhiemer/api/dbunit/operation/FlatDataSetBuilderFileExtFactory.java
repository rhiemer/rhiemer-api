package br.com.rhiemer.api.dbunit.operation;

import static br.com.rhiemer.api.util.constantes.ConstantesFileExtension.XML;

import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import br.com.rhiemer.api.dbunit.helper.HelperDbUnit;

public class FlatDataSetBuilderFileExtFactory implements IFlatDataSetBuilder {

	private Class<?> dataSetBuilderClass;
	public String dataSet;

	public FlatDataSetBuilderFileExtFactory() {
		super();
	}

	public FlatDataSetBuilderFileExtFactory(String dataSet) {
		super();
		setDataSet(dataSet);
	}

	protected void parseDataSetBuilder() {
		String fileExt = FilenameUtils.getExtension(this.dataSet);
		switch (fileExt) {
		case XML:
			dataSetBuilderClass = FlatXmlDataSetBuilder.class;
			break;
		default:
			break;
		}

	}

	@Override
	public IDataSet build(InputStream inputStream) throws DataSetException {
		return HelperDbUnit.builderDataSet(getDataSetBuilderClass(), dataSet);
	}

	public IFlatDataSetBuilder setDataSet(String dataSet) {
		this.dataSet = dataSet;
		parseDataSetBuilder();
		return this;
	}

	public Class<?> getDataSetBuilderClass() {
		return dataSetBuilderClass;
	}

	
}
