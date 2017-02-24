package br.com.rhiemer.api.util.criteria;

public abstract class AtributoCriteria<T, K> implements IAtributoCriteria<T, K> {

	private CreateCriteria createCriteria;

	public AtributoCriteria(CreateCriteria createCriteria) {
		super();
		setCreateCriteria(createCriteria);
	}

	protected abstract K create(T atributo);

	@Override
	public IAtributoCriteria<T, K> get(T atributo) {
		return this;
	}

	public CreateCriteria getCreateCriteria() {
		return createCriteria;
	}

	protected void setCreateCriteria(CreateCriteria createCriteria) {
		this.createCriteria = createCriteria;
	}

}
