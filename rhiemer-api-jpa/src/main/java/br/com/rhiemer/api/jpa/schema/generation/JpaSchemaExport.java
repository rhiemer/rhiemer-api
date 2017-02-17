package br.com.rhiemer.api.jpa.schema.generation;

import java.util.Properties;
import java.io.IOException;

import javax.persistence.Persistence;

import org.hibernate.jpa.AvailableSettings;

public class JpaSchemaExport {

	public static void main(String[] args) throws IOException {
		execute(args[0], args[1]);
		System.exit(0);
	}

	public static void execute(String persistenceUnitName, String destination) {
		System.out.println("Generating DDL create script to : " + destination);

		final Properties persistenceProperties = new Properties();

		persistenceProperties.setProperty(org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO, "");

		// XXX force persistence properties : remove database target
		persistenceProperties.setProperty(org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO, "");
		persistenceProperties.setProperty(AvailableSettings.SCHEMA_GEN_DATABASE_ACTION, "none");

		// XXX force persistence properties : define create script target from
		// metadata to destination
		persistenceProperties.setProperty(AvailableSettings.SCHEMA_GEN_SCRIPTS_ACTION, "create");
		persistenceProperties.setProperty(AvailableSettings.SCHEMA_GEN_CREATE_SOURCE, "metadata");
		persistenceProperties.setProperty(AvailableSettings.SCHEMA_GEN_SCRIPTS_CREATE_TARGET, destination);

		persistenceProperties.setProperty(AvailableSettings.SCHEMA_GEN_CREATE_SOURCE, "metadata");
		persistenceProperties.setProperty(AvailableSettings.SCHEMA_GEN_SCRIPTS_CREATE_TARGET, destination);
		persistenceProperties.setProperty(AvailableSettings.SCHEMA_GEN_CREATE_SOURCE, "metadata");
		persistenceProperties.setProperty(AvailableSettings.SCHEMA_GEN_SCRIPTS_CREATE_TARGET, destination);
		try {
			Persistence.generateSchema(persistenceUnitName, persistenceProperties);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
