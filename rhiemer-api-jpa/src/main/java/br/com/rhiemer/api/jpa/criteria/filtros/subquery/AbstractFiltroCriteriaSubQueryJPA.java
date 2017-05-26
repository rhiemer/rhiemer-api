package br.com.rhiemer.api.jpa.criteria.filtros.subquery;

import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.criteria.subquery.SubQueryRetornoDTO;
import br.com.rhiemer.api.util.exception.APPIllegalArgumentException;

public abstract class AbstractFiltroCriteriaSubQueryJPA implements IFiltroCriteriaSubQueryJPA {

	private Map<String, SubQueryRetornoDTO> mapSubQueryJpaRoot;
	private SubQueryRetornoDTO subQueryRetornoDTO;
	private String subQueryAlias;
	private Boolean not = false;

	public AbstractFiltroCriteriaSubQueryJPA() {
		super();
	}

	public Map<String, SubQueryRetornoDTO> getMapSubQueryJpaRoot() {
		return mapSubQueryJpaRoot;
	}

	public String getSubQueryAlias() {
		return subQueryAlias;
	}

	public AbstractFiltroCriteriaSubQueryJPA setSubQueryAlias(String subQueryAlias) {
		this.subQueryAlias = subQueryAlias;
		return this;
	}

	public AbstractFiltroCriteriaSubQueryJPA setMapSubQueryJpaRoot(Map<String, SubQueryRetornoDTO> mapSubQueryJpaRoot) {
		this.mapSubQueryJpaRoot = mapSubQueryJpaRoot;
		return this;
	}

	public SubQueryRetornoDTO getSubQueryRetornoDTO() {
		return subQueryRetornoDTO;
	}

	public AbstractFiltroCriteriaSubQueryJPA setSubQueryRetornoDTO(SubQueryRetornoDTO subQueryRetornoDTO) {
		this.subQueryRetornoDTO = subQueryRetornoDTO;
		return this;
	}

	public Predicate builderSubQuery(CriteriaBuilder criteriaBuilder, AbstractQuery query, Root root) {
		SubQueryRetornoDTO subQueryRetornoDTO = Optional.ofNullable(this.getSubQueryRetornoDTO())
				.orElse(queryByAlias());
		if (subQueryRetornoDTO == null) {
			throw new APPIllegalArgumentException("Não foi encontrado nenhuma subQuery:Alias {}", getSubQueryAlias());
		}

		Predicate builder = builder(criteriaBuilder, query, root, subQueryRetornoDTO);
		if (getNot() != null && getNot()) {
			return criteriaBuilder.not(builder);
		} else {
			return builder;
		}

	}

	protected SubQueryRetornoDTO queryByAlias() {
		return Optional.ofNullable(getMapSubQueryJpaRoot()).map(t -> t.get(getSubQueryAlias())).orElse(null);
	}

	public Boolean getNot() {
		return not;
	}

	public void setNot(Boolean not) {
		this.not = not;
	}

}
