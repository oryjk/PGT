package com.pgt.internal.util;

import java.util.regex.Pattern;

/**
 * Created by Yove on 10/22/2015.
 */
public class FieldsRegexValidator {

	private String mRegexPattern;

	public boolean match(String pText) {
		return Pattern.compile(getRegexPattern()).matcher(pText).matches();
	}

	public String getRegexPattern() {
		return mRegexPattern;
	}

	public void setRegexPattern(final String pRegexPattern) {
		mRegexPattern = pRegexPattern;
	}
}
