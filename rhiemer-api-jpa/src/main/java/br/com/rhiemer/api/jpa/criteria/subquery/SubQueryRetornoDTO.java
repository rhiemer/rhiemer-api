package br.com.rhiemer.api.jpa.criteria.subquery;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.Root;

public class SubQueryRetornoDTO {

	private AbstractQuery subquery;
	private Root root;

	public SubQueryRetornoDTO() {
		super();
	}

	public SubQueryRetornoDTO(AbstractQuery subquery, Root root) {
		super();
		this.subquery = subquery;
		this.root = root;
	}

	public AbstractQuery getSubquery() {
		return subquery;
	}

	public void setSubquery(AbstractQuery subquery) {
		this.subquery = subquery;
	}

	public Root getRoot() {
		return root;
	}

	public void setRoot(Root root) {
		this.root = root;
	}

}
