package com.pgt.integration.yeepay;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

public class BaseParamValidator implements ParamValidateor {
	
	private Map<String, String> paramNames;

	@Override
	public void validInputParam(YeePayConfig config, Map<String, Object> params) throws InvalidParameterException {
		if (null == params) {
			throw new InvalidParameterException();
		}
		if (null == getParamNames() || getParamNames().isEmpty()) {
			return;
		}
		
		for (Map.Entry<String, String> entry : getParamNames().entrySet()) {
			if (YeePayConstants.VALUE_REQUIRE.equals(entry.getValue()) && null == params.get(entry.getKey())) {
				throw new InvalidParameterException("Param " + entry.getKey() + " is required.");
			}
		}
		

	}

	public Map<String, String> getParamNames() {
		return paramNames;
	}

	public void setParamNames(Map<String, String> paramNames) {
		this.paramNames = paramNames;
	}


}
