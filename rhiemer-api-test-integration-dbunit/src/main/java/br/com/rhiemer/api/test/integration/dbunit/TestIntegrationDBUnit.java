package br.com.rhiemer.api.test.integration.dbunit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.jboss.arquillian.junit.InSequence;
import org.junit.After;
import org.junit.Before;

import br.com.rhiemer.api.dbunit.rest.DBUnitRestConsumer;
import br.com.rhiemer.api.test.integration.client.ClientTest;
import br.com.rhiemer.api.test.integration.dbunit.annotations.DbUnitAdicionarArquivo;
import br.com.rhiemer.api.util.helper.Helper;

import static br.com.rhiemer.api.dbunit.transactional.DBUnitTransactional.DELETE_DATA_SET;
import static br.com.rhiemer.api.dbunit.transactional.DBUnitTransactional.DELETE_INSERT_DATA_SET;

public abstract class TestIntegrationDBUnit extends ClientTest {

	private static final String FOLDER_DATASETS_DB_UNIT = "datasets";
	protected final List<String> arquivosDbUnit = new ArrayList<>();
	protected boolean dbUnitCriado = false;
	protected boolean dbUnitRemovido = false;

	@Before
	@InSequence(0)
	public void beforeTestDbUnit() {
		insertDBUnit();
	}

	@After
	@InSequence(100)
	public void afterTestDbUnit() {
		removeDBUnit();
	}

	protected String folderDataSetsDbUnit() {
		return FOLDER_DATASETS_DB_UNIT;
	}

	protected String pathDomainDbUnit() {
		return Helper.concat(getPath(), "/");
	}

	protected void inserirArquivosDbUnitAnnotation() {

		Optional.ofNullable(this.getClass().getAnnotationsByType(DbUnitAdicionarArquivo.class))
				.ifPresent(t -> Arrays.asList(t).forEach(x -> addArquivoDbUnit(x.value())));

	}

	protected void inserirArquivosDbUnit() {

	}

	protected void removerArquivosDbUnit() {

	}

	protected boolean addArquivoDbUnit(String arquivo) {
		return arquivosDbUnit.add(folderDataSetsDbUnit().concat(arquivo));
	}

	protected void insertDBUnit() {
		inserirArquivosDbUnitAnnotation();
		inserirArquivosDbUnit();
		if (arquivosDbUnit.size() > 0) {
			inserirDataSetDbUnit();
			dbUnitCriado = true;
		}

	}

	protected void servicoDataSetDbUnit(String operacao) {
		DBUnitRestConsumer.operacaoDataset(urlAplicacao.toString().concat(pathDomainDbUnit()), operacao,
				Helper.collectionToString(arquivosDbUnit, ","));
	}

	protected void inserirDataSetDbUnit() {
		servicoDataSetDbUnit(DELETE_INSERT_DATA_SET);
	}

	protected void deleteDataSetDbUnit() {
		servicoDataSetDbUnit(DELETE_DATA_SET);
	}

	protected void removeDBUnit() {
		removerArquivosDbUnit();
		if (arquivosDbUnit.size() > 0) {
			deleteDataSetDbUnit();
			dbUnitRemovido = true;
		}

	}

}
