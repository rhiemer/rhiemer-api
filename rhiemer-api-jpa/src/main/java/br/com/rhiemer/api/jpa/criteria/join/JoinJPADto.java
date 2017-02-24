package br.com.rhiemer.api.jpa.criteria.join;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.StringUtils;

import br.com.rhiemer.api.jpa.helper.HelperRootCriteria;
import br.com.rhiemer.api.util.helper.Helper;

public class JoinJPADto {

	private From root;
	private JoinType joinType = JoinType.INNER;
	private boolean fetch = false;

	public From getRoot() {
		return root;
	}

	public void setRoot(From root) {
		this.root = root;
	}

	public JoinType getJoinType() {
		return joinType;
	}

	public void setJoinType(JoinType joinType) {
		this.joinType = joinType;
	}

	public boolean getFetch() {
		return fetch;
	}

	public void setFetch(boolean fetch) {
		this.fetch = fetch;
	}

	public From join(String atributoJoin) {
		From join = getRoot();
		String[] atributos = atributoJoin.split("[.]");
		for (int i = 0; i < atributos.length; i++) {
			String atributo = atributos[i];
			if (getFetch()) {
				join = (From) join.fetch(atributo, getJoinType());
			} else {
				join = join.join(atributo, getJoinType());
			}
			String atributoComp = "";
			if (Helper.isNotBlank(atributoComp))
				atributoComp = atributoComp.concat(".").concat(atributo);
			else
				atributoComp = atributo;
			join.alias(atributoComp);
		}

		return join;
	}
	
	public From join(SingularAttribute... atributosJoin) {
		List<SingularAttribute> listAttributes = Helper.convertArgs(atributosJoin);
		final From[] join = new From[1];

		listAttributes.forEach(new Consumer<SingularAttribute>() {
			@Override
			public void accept(SingularAttribute t) {				
				if (getFetch()) {
					join[0] = (From) join[0].fetch(t, getJoinType());
				} else {
					join[0] = join[0].join(t, getJoinType());
				}
				String atributoComp = "";
				if (Helper.isNotBlank(atributoComp))
					atributoComp = atributoComp.concat(".").concat(t.getName());
				else
					atributoComp = t.getName();
				join[0].alias(atributoComp);
			}
		});
		
		return join[0];
	}

	public static Builder createBuilder() {
		return new Builder();
	}

	public static class Builder {
		private From root;
		private JoinType joinType = JoinType.INNER;
		private boolean fetch = false;

		public Builder setRoot(From root) {
			this.root = root;
			return this;
		}

		public Builder setJoinType(JoinType joinType) {
			this.joinType = joinType;
			return this;
		}

		public Builder setFetch(boolean fetch) {
			this.fetch = fetch;
			return this;
		}

		public JoinJPADto build() {
			JoinJPADto joinJPADto = new JoinJPADto();
			joinJPADto.setFetch(this.fetch);
			joinJPADto.setJoinType(this.joinType);
			joinJPADto.setFetch(this.fetch);
			return joinJPADto;
		}

	}

}
