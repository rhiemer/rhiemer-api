package br.com.rhiemer.api.rest.params;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.WebApplicationException;

import br.com.rhiemer.api.util.exception.APPIllegalArgumentException;
import br.com.rhiemer.api.util.helper.DateTimeUtils;

public class RESTDateParam {

	private SimpleDateFormat df = new SimpleDateFormat(DateTimeUtils.REST_DATE_FORMAT);
	private Date date;

	public RESTDateParam(Date date) {
		this.date = date;
	}

	public RESTDateParam(String dateStr) throws WebApplicationException {
		try {
			date = df.parse(dateStr);
		} catch (final ParseException ex) {

			throw new WebApplicationException(
					new APPIllegalArgumentException("Erro na conversão da data[{}]:{}", ex, dateStr, ex.toString()));
		}
	}

	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		if (date != null) {
			return df.format(this.date);
		} else {
			return "";
		}
	}

}
