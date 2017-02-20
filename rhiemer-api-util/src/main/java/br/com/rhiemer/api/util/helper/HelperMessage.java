package br.com.rhiemer.api.util.helper;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public class HelperMessage {

	private HelperMessage() {

	}

	public static String formatMessage(String msg, Object... arguments) {
		FormattingTuple ft = MessageFormatter.arrayFormat(msg, arguments);
		return ft.getMessage();
	}
}
