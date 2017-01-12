package br.com.rhiemer.api.jpa.helper;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.ManagedType;

public class JPAUtils {

	private static final Pattern FROM_PATTERN = Pattern.compile("(from(\\w|\\s|\\W)+)", Pattern.CASE_INSENSITIVE);
	private static final String COUNT_QUERY = "select count(*) %s";

	public static EntityManager entityManagerByName(String pu) {

		EntityManager entityManager = null;
		Context initCtx = null;
		try {
			initCtx = new InitialContext();
		} catch (NamingException e) {
			return null;
		}
		try {
			entityManager = (EntityManager) initCtx.lookup(String.format("java:comp/env/%s", pu));
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			return null;
		}

		return entityManager;

	}

	public static void fillParameters(Query query, Map<Object, Object> queryParameters) {

		if (queryParameters != null) {

			for (Map.Entry<Object, Object> item : queryParameters.entrySet()) {

				if (item.getKey() instanceof Integer) {
					query.setParameter((Integer) item.getKey(), item.getValue());
				} else if (item.getValue() != null && item.getValue() instanceof Date) {
					query.setParameter(item.getKey().toString(), (Date) item.getValue(), TemporalType.DATE);

				} else {
					query.setParameter(item.getKey().toString(), item.getValue());
				}

			}

		}

	}

	public static String getCountQuery(String query) {

		Matcher fromMatcher = FROM_PATTERN.matcher(query);

		if (fromMatcher.find()) {

			return String.format(COUNT_QUERY, fromMatcher.group(1));

		} else
			return query;
	}

	public static Set<NamedQuery> findAllNamedQueries(EntityManagerFactory emf) {
		Set<NamedQuery> allNamedQueries = new HashSet<NamedQuery>();

		Set<ManagedType<?>> managedTypes = emf.getMetamodel().getManagedTypes();
		for (ManagedType<?> managedType : managedTypes) {
			if (managedType instanceof IdentifiableType) {
				Class<?> identifiableTypeClass=managedType.getJavaType();;
				
				if (identifiableTypeClass == null)
				 continue;	

				NamedQueries namedQueries = identifiableTypeClass.getAnnotation(NamedQueries.class);
				if (namedQueries != null) {
					allNamedQueries.addAll(Arrays.asList(namedQueries.value()));
				}

				NamedQuery namedQuery = identifiableTypeClass.getAnnotation(NamedQuery.class);
				if (namedQuery != null) {
					allNamedQueries.add(namedQuery);
				}
			}
		}
		return allNamedQueries;
	}

	public static NamedQuery getNamedQueries(EntityManagerFactory emf, String nome) {
		Set<NamedQuery> allNamedQueries = findAllNamedQueries(emf);
		for (NamedQuery namedQuery : allNamedQueries) {
			if (namedQuery.name().equalsIgnoreCase(nome)) {
				return namedQuery;
			}
		}
		return null;
	}

	public static boolean verifcaNamedQuery(EntityManagerFactory emf, String nome) {

		return getNamedQueries(emf, nome) != null;
	}

}
