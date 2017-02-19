package br.com.rhiemer.api.util.helper;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import br.com.rhiemer.api.util.exception.APPSystemException;

public class HelperException {

	private HelperException() {

	}

	public static String strThrowApp(Throwable e) {
		String message = null;
		if (e instanceof SQLException) {
			SQLException se = (SQLException) e;
			message = HelperMessage.formatMessage("{}/nErro Code:{}/nSQL:{}", se.getMessage(), se.getErrorCode(),
					se.getSQLState());
		} else {
			message = e.getMessage();
		}

		return message;

	}

	public static String tratarStrThrowApp(Throwable e) {
		return strThrowApp(tratarThrowApp(e));

	}

	public static Throwable tratarThrowApp(Throwable e) {
		if (e instanceof InvocationTargetException) {
			return e.getCause();
		} else {
			return e;
		}
	}

	public static RuntimeException throwApp(Throwable e) {
		return new APPSystemException(e);
	}

	public static RuntimeException throwApp(String msg, Object... params) {
		return new APPSystemException(msg, params);
	}

	public static RuntimeException throwAppIllegalArgument(String msg, Object... params) {
		throw new IllegalArgumentException(HelperMessage.formatMessage(msg, params));
	}

}
