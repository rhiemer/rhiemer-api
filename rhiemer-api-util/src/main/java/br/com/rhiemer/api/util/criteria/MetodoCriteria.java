package br.com.rhiemer.api.util.criteria;

public abstract class MetodoCriteria implements IMetodoCriteria {

	private MetodoCriteria metodoNext;

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

	public MetodoCriteria getMetodoNext() {
		return metodoNext;
	}

	public void setMetodoNext(MetodoCriteria metodoNext) {
		this.metodoNext = metodoNext;
	}

}
