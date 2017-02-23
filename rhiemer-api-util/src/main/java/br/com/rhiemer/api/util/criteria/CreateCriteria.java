package br.com.rhiemer.api.util.criteria;

public abstract class CreateCriteria implements ICreateCriteria {

	private boolean isCreate = false;

	public CreateCriteria() {
		super();
	}

	abstract protected void create();

	public void builder() {
		if (!getIsCreate()) {
			create();
			setIsCreate(true);
		}		
	}

	protected boolean getIsCreate() {
		return this.isCreate;
	}

	protected void setIsCreate(boolean isCreate) {
		this.isCreate = isCreate;
	}


}
