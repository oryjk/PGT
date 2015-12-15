package com.pgt.integration.yeepay;

import java.security.InvalidParameterException;
import java.util.Map;

public interface ParamValidateor {

	void validInputParam(YeePayConfig config, Map<String, Object> params) throws InvalidParameterException;

}
