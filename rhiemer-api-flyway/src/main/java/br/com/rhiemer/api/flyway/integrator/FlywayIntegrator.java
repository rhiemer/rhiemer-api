package br.com.rhiemer.api.flyway.integrator;

import javax.sql.DataSource;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.flyway.core.Flyway;
import com.googlecode.flyway.core.api.MigrationInfo;

public class FlywayIntegrator implements Integrator {

	private static final String JAVAX_PERSISTENCE_JTA_DATA_SOURCE = "javax.persistence.jtaDataSource";
	public static final Logger logger = LoggerFactory
			.getLogger(FlywayIntegrator.class);

	@Override
	public void integrate(Metadata metadata,
			SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
		if (logger.isDebugEnabled()) {
			logger.debug("Iniciando Flyway");
			//TO-DO:Printar informações do banco
		}

		DataSource dataSource = (DataSource) sessionFactory.getProperties()
				.get(JAVAX_PERSISTENCE_JTA_DATA_SOURCE);
		

		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.setInitOnMigrate(true);

		if (logger.isDebugEnabled()) {
			for (MigrationInfo i : flyway.info().all()) {
				logger.debug("Tarefa de migração do flyway {} : {} Script: {}",
						i.getVersion(), i.getDescription(), i.getScript());
			}

		}

		flyway.migrate();

	}

	@Override
	public void disintegrate(SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
		// TODO Auto-generated method stub

	}

}
