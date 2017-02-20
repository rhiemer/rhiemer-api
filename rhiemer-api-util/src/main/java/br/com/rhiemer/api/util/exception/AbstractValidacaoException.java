package br.com.rhiemer.api.util.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import br.com.rhiemer.api.util.dto.ErroConstraintViolationDto;

public abstract class AbstractValidacaoException extends RuntimeException {

	private static final long serialVersionUID = 584654789807565625L;
	private List<String> lista;
	private List<ErroConstraintViolationDto> listaDto;

	public static String formatViolation(ConstraintViolation<?> cv) {
		return String.format("Enitidade:%s Bean:%s Atributo:%s Valor:%s %s", cv.getRootBeanClass().getName(),
				cv.getRootBean(), cv.getPropertyPath().toString(), cv.getInvalidValue(), cv.getMessage());
	}

	public static String formatMsg(String message, Set<? extends ConstraintViolation<?>> cve) {
		StringBuilder msg = new StringBuilder();

		for (ConstraintViolation<?> cv : cve) {

			if (msg.length() > 0)
				msg.append("\n");
			msg.append(formatViolation(cv));
		}

		return message.concat("\n").concat(msg.toString());

	}

	public static List<String> listaMsg(Set<? extends ConstraintViolation<?>> cve) {
		List<String> lista = new ArrayList<>();

		for (ConstraintViolation<?> cv : cve) {
			lista.add(formatViolation(cv));
		}

		return lista;

	}

	public static List<ErroConstraintViolationDto> listaDto(Set<? extends ConstraintViolation<?>> cve) {
		List<ErroConstraintViolationDto> lista = new ArrayList<>();

		for (ConstraintViolation<?> cv : cve) {
			lista.add(new ErroConstraintViolationDto(cv));
		}

		return lista;

	}

	public AbstractValidacaoException(Set<? extends ConstraintViolation<?>> cve) {

		super(formatMsg("Atributo inválidos", cve), new ConstraintViolationException(cve));
		this.lista = listaMsg(cve);
		this.listaDto = listaDto(cve);
	};

	public AbstractValidacaoException(String message, Set<? extends ConstraintViolation<?>> cve) {

		super(formatMsg(message, cve), new ConstraintViolationException(cve));
		this.lista = listaMsg(cve);
		this.listaDto = listaDto(cve);

	}

	public List<String> getLista() {
		return lista;
	}

	public List<ErroConstraintViolationDto> getListaDto() {
		return listaDto;
	}

}
