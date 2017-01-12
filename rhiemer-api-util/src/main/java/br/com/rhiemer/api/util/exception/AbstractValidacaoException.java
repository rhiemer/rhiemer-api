package br.com.rhiemer.api.util.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public abstract class AbstractValidacaoException extends RuntimeException {

	private static final long serialVersionUID = 584654789807565625L;

	private static String formatMsg(String message,
			Set<? extends ConstraintViolation<?>> cve) {
		StringBuilder msg = new StringBuilder(message);

		for (ConstraintViolation<?> cv : cve) {

			msg.append(String.format(
					"\nEnitidade :%s Bean %s Atributo :%s Valor  :%s\n%s", cv
							.getRootBeanClass().getName(), cv.getRootBean(), cv
							.getPropertyPath().toString(),
					cv.getInvalidValue(), cv.getMessage()));
		}

		return msg.toString();

	}

	public AbstractValidacaoException(Set<? extends ConstraintViolation<?>> cve) {

		super(formatMsg("Atributo inválidos", cve),
				new ConstraintViolationException(cve));
	};

	public AbstractValidacaoException(String message,
			Set<? extends ConstraintViolation<?>> cve) {

		super(formatMsg(message, cve), new ConstraintViolationException(cve));

	}

}
