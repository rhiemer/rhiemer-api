package br.com.rhiemer.api.jpa.helper;

import static br.com.rhiemer.api.jpa.constantes.ConstantesCriteriaJPA.FETCH_DEFAULT;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.Attribute;

import br.com.rhiemer.api.util.constantes.ConstantesAPI;
import br.com.rhiemer.api.util.helper.Helper;

public final class HelperRootCriteria {

	private static final String ATRIBUTO_OPTIONAL_JOIN = "optional";

	private HelperRootCriteria() {

	}

	public static Join createJoinComplex(From rootJoin, String atributo) {
		return createJoinComplex(rootJoin, atributo, FETCH_DEFAULT, null);
	}

	public static Path getAttribute(From rootJoin, String atributo) {
		return getAttribute(rootJoin, atributo, FETCH_DEFAULT, null);
	}

	public static Join createJoinComplex(From rootJoin, Attribute... attributes) {
		return createJoinComplex(rootJoin, FETCH_DEFAULT, null, attributes);
	}

	public static Path getAttribute(From rootJoin, Attribute... attributes) {
		return getAttribute(rootJoin, FETCH_DEFAULT, null, attributes);
	}

	public static Join createJoinComplex(From rootJoin, Boolean fecth, JoinType joinType, Attribute... attributes) {

		String atributesStr = HelperAtributeJPA.attributeToString(attributes);
		return createJoinComplex(rootJoin, atributesStr, fecth, joinType);

	}

	public static Path getAttribute(From rootJoin, Boolean fecth, JoinType joinType, Attribute... attributes) {

		String atributesStr = HelperAtributeJPA.attributeToString(attributes);
		return getAttribute(rootJoin, atributesStr, fecth, joinType);
	}

	public static Path getAttribute(From rootJoin, String atributo, Boolean fecth, JoinType joinType) {

		if (!atributo.contains(ConstantesAPI.DOT_FIELD)) {
			return HelperAtributeJPA.getAttribute(rootJoin, atributo);
		} else {
			Join join = createJoinComplex(rootJoin, atributo, fecth, joinType);
			String[] atributoSplit = atributo.split("[.]");
			String lastAtributo = atributoSplit[atributoSplit.length - 1];
			return HelperAtributeJPA.getAttribute(join, lastAtributo);
		}
	}

	public static Join createJoinComplex(From rootJoin, String atributo, Boolean fecth, JoinType joinType) {

		String[] atributoSplit = atributo.split("[.]");
		From joinLoop = rootJoin;
		String atributoComp = "";
		boolean isField = HelperAtributeJPA.isField(rootJoin.getJavaType(), atributo);

		for (int i = 0; (i < atributoSplit.length - 1 && joinLoop != null); i++) {
			if (Helper.isNotBlank(atributoComp))
				atributoComp = atributoComp.concat(".").concat(atributo);
			else
				atributoComp = atributo;
			joinLoop = createJoin(joinLoop, atributoComp, fecth, joinType);
			if (isField && i >= atributoSplit.length - 2)
				break;
		}

		return (Join) joinLoop;

	}

	public static <T extends From & Join, K extends Join> boolean replaceJoinType(From rootJoin, K join,
			JoinType newJoinType) {
		if (join.getJoinType() != JoinType.INNER && newJoinType == JoinType.INNER) {
			removeJoin(rootJoin, join);
			return true;
		} else
			return false;

	}

	public static Join createJoin(From rootJoin, String atributo, Boolean fecth, JoinType joinType) {

		JoinType aJoinType = joinType;
		if (aJoinType == null) {
			aJoinType = joinTypeDefault(rootJoin.getJavaType(), atributo);
		}

		From joinFetch = getJoinByAlias(rootJoin.getFetches(), atributo);
		if (fecth || joinFetch != null) {
			if (joinFetch != null) {
				if (!replaceJoinType(rootJoin, (Join) joinFetch, joinType))
					return (Join) joinFetch;
				else
					joinFetch = null;
			}
			if (joinFetch == null) {
				replaceJoinFecth(rootJoin, atributo);
				return (Join) rootJoin.fetch(atributo, aJoinType);
			}
		} else {
			From joinCreate = getJoinByAlias(rootJoin.getJoins(), atributo);
			if (joinCreate != null) {
				if (!replaceJoinType(rootJoin, (Join) joinCreate, joinType))
					return (Join) joinCreate;
				else
					joinFetch = null;

			}

			if (joinCreate == null) {
				return (Join) rootJoin.join(atributo, aJoinType);
			}

		}

		return null;

	}

	public static From getJoinComplex(From join, String atributo) {

		String[] atributoSplit = atributo.split("[.]");
		From joinLoop = join;
		String atributoComp = "";
		boolean isField = HelperAtributeJPA.isField(join.getJavaType(), atributo);

		for (int i = 0; (i < atributoSplit.length - 1 && joinLoop != null); i++) {
			if (Helper.isNotBlank(atributoComp))
				atributoComp = atributoComp.concat(".").concat(atributo);
			else
				atributoComp = atributo;
			joinLoop = getJoin(joinLoop, atributo);
			if (isField && i >= atributoSplit.length - 2)
				break;
		}

		return joinLoop;

	}

	public static From getJoin(From join, String atributo) {

		if (equalsFrom(join, atributo))
			return join;
		else {
			From joinLoop = getJoinByAlias(join.getFetches(), atributo);
			if (joinLoop != null)
				return joinLoop;
			else {
				joinLoop = getJoinByAlias(join.getJoins(), atributo);
				return joinLoop;
			}

		}

	}

	public static boolean removeJoin(From root, From join) {

		Boolean result = root.getJoins().remove(join);
		result = root.getFetches().remove(join) || result;
		return result;

	}

	public static boolean replaceJoinFecth(From rootJoin, String atributo) {
		Join join1 = (Join) getJoinByAlias(rootJoin.getJoins(), atributo);
		if (join1 != null) {
			rootJoin.getJoins().remove(join1);
			return true;
		}
		return false;

	}

	public static JoinType joinTypeDefault(Class<?> classe, String atributo) {
		Annotation annotationJoinReference = HelperAtributeJPA.annotationJoinReference(classe, atributo);
		if (annotationJoinReference == null)
			return null;

		Boolean optional = Helper.valueAnnotation(annotationJoinReference, ATRIBUTO_OPTIONAL_JOIN, Boolean.class);
		if (optional != null) {
			if (optional.equals(Boolean.TRUE)) {
				return JoinType.LEFT;
			} else {
				return JoinType.INNER;
			}
		} else
			return null;

	}

	public static From getJoinByAlias(Set<? extends From> froms, String atributo) {
		return froms.stream().filter(x -> equalsFrom(x, atributo)).findFirst().get();
	}

	public static boolean equalsFrom(From from, String atributo) {
		if (from.getAlias().equalsIgnoreCase(atributo))
			return true;
		else
			return false;
	}

}
