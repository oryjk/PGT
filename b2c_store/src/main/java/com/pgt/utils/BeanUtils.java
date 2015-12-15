package com.pgt.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanUtils {

	public static Map<String, Object> toMap(Mapable bean)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		BeanInfo info = Introspector.getBeanInfo(bean.getClass());
		Map<String, Object> result = new HashMap<String, Object>();
		for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
			Method reader = pd.getReadMethod();
			if ("MapValue".equalsIgnoreCase(pd.getName()) || "class".equals(pd.getName())) {
				continue;
			}
			if (reader == null || reader.invoke(bean) == null) {
				continue;
			}
			result.put(pd.getName(), reader.invoke(bean));
		}
		return result;
	}

}
