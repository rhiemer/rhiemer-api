package br.com.rhiemer.api.test.unit.matcher;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class StringsContains extends TypeSafeMatcher<String> {

	private final Iterable<String> substrings;
	private boolean ignoreCase = false;
	private String stringNaoContem = null;

	public StringsContains(Iterable<String> substrings) {
		this.substrings = substrings;
	}

	public StringsContains(Iterable<String> substrings, boolean ignoreCase) {
		this.substrings = substrings;
		this.ignoreCase = ignoreCase;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("\nTexto não contem a String ").appendText(stringNaoContem);

	}

	@Override
	protected boolean matchesSafely(String s) {
		for (String substring : substrings) {
			int fromIndex = -1;
			if (ignoreCase)
				fromIndex = (substring == null ? (s == null ? 0 : -1)
						: s.toLowerCase().indexOf(substring.toLowerCase()));
			else
				fromIndex = s.indexOf(substring);
			if (fromIndex == -1) {
				stringNaoContem = substring;
				return false;
			}
		}

		return true;
	}

	public static Matcher<String> stringsContains(Iterable<String> substrings) {
		return new StringsContains(substrings);
	}

	public static Matcher<String> stringsContains(String... substrings) {
		return new StringsContains(Arrays.asList(substrings));
	}

	public static Matcher<String> stringsContains(Iterable<String> substrings, boolean ignoreCase) {
		return new StringsContains(substrings, ignoreCase);
	}

	public static Matcher<String> stringsContains(boolean ignoreCase, String... substrings) {
		return new StringsContains(Arrays.asList(substrings), ignoreCase);
	}

}
