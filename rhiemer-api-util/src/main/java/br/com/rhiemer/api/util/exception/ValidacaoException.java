package br.com.rhiemer.api.util.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;

public final class ValidacaoException extends AbstractValidacaoException {

	

	public ValidacaoException(Set<? extends ConstraintViolation<?>> cve) {
		super(cve);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1607340894351409534L;

}
