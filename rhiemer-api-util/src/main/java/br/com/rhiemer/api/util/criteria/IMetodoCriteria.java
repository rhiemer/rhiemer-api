package br.com.rhiemer.api.util.criteria;

public interface IMetodoCriteria<T extends ICreateCriteria>  {
	
	void builder(T createCriteria);

}
