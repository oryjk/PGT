package com.pgt.cart.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

/**
 * Created by Yove on 10/21/2015.
 */
public class RepositoryUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryUtils.class);

	public static boolean idIsValid(final int pId) {
		return pId > 0;
	}

	public static boolean idIsValid(final Integer pId) {
		return pId != null && pId.intValue() > 0;
	}

	public static boolean idIsValid(final String pId) {
		return idIsValid(safeParseId(pId));
	}

	public static int safeParseId(String pIdString) {
		int id = -1;
		if (ObjectUtils.isEmpty(pIdString)) {
			return id;
		}
		try {
			id = Integer.valueOf(pIdString);
		} catch (NumberFormatException nfe) {
			StackTraceElement[] elements = nfe.getStackTrace();
			LOGGER.error("{}#{} (line: {})", elements[3].getClassName(), elements[3].getMethodName(), elements[3].getLineNumber());
		}
		return id;
	}

	public static long safeParse2LongId(String pLongString) {
		long longValue = -1L;
		if (StringUtils.isBlank(pLongString)) {
			return longValue;
		}
		try {
			longValue = Long.valueOf(pLongString);
		} catch (NumberFormatException nfe) {
			StackTraceElement[] elements = nfe.getStackTrace();
			LOGGER.error("{}#{} (line: {})", elements[3].getClassName(), elements[3].getMethodName(), elements[3].getLineNumber());
		}
		return longValue;
	}
}
