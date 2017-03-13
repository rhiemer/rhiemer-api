package br.com.rhiemer.api.util.criteria;

public interface IMetodoCriteria<T extends ICreateCriteria> extends ICriteria {
	
	void builder(T createCriteria);

}
