package br.com.rhiemer.api.util.criteria;

public abstract class MetodoCriteria<T extends ICreateCriteria> implements IMetodoCriteria<T> {

	private MetodoCriteria<T> metodoNext;

	abstract protected void builderInternal();

	public MetodoCriteria() {
		super();
	}

	public void builder(ICreateCriteria createCriteria) {
		createCriteria.builder();
		builderInternal();
		if (this.getMetodoNext() != null) {
			builderNext(createCriteria);
		}
	}

	protected void builderNext(ICreateCriteria createCriteria) {

		this.getMetodoNext().builder(createCriteria);
	}

	public MetodoCriteria<T> getMetodoNext() {
		return metodoNext;
	}

	public void setMetodoNext(MetodoCriteria<T> metodoNext) {
		this.metodoNext = metodoNext;
	}

}
