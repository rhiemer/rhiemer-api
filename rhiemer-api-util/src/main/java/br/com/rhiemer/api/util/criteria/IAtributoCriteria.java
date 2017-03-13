package br.com.rhiemer.api.util.criteria;

public interface IAtributoCriteria<T,K> extends ICriteria {
	
	IAtributoCriteria<T,K> get(T atributo);

}
