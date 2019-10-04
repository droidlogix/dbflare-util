package com.droidlogix.dbflare.util;

import com.dbflare.core.models.IEndpoint;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: John Pili
 */

public class ApiUtil
{
	/**
	 * This process the URL parameters into a Map
	 * @param request
	 * @return
	 */
	public static Map<String, Object[]> hashifyRequestParameter(HttpServletRequest request)
	{
		Map<String, Object[]> parameters = new HashMap<>();
		Enumeration<String> parameterNames = request.getParameterNames();
		if (parameterNames != null)
		{
			while (parameterNames.hasMoreElements())
			{
				String parameterName = parameterNames.nextElement();
				parameters.put(parameterName, request.getParameterValues(parameterName));
			}
		}
		return parameters;
	}

	/**
	 * This method is intended for extracting the sort column from the parameters
	 * @param src
	 * @return
	 */
	public static String extractSortColumn(String src)
	{
		if (src.toLowerCase().endsWith("_desc") || src.toLowerCase().endsWith("_asc"))
		{
			if (src.toLowerCase().endsWith("_desc"))
			{
				String filteredString = src;
				filteredString = src.replace("_desc", "").replace("_DESC", "").replace("_Desc", "");
				return filteredString;
			}
			else if (src.toLowerCase().endsWith("_asc"))
			{
				String filteredString = src;
				filteredString = src.replace("_asc", "").replace("_ASC", "").replace("_Asc", "");
				return filteredString;
			}
		}
		return src;
	}

	/**
	 * This is use to generate key fingerprint for memory cache.
	 * @param apiEndPoint
	 * @param parameters
	 * @return
	 */
	public static String getCacheFingerPrint(IEndpoint apiEndPoint, Map<String, Object[]> parameters)
	{
		if (apiEndPoint != null)
		{
			StringBuffer keyBuffer = new StringBuffer(apiEndPoint.getApi());
			if (parameters != null)
			{
				for (Map.Entry<String, Object[]> entry : parameters.entrySet())
				{
					keyBuffer.append(entry.getKey());
					keyBuffer.append(Arrays.toString(entry.getValue()));
				}
			}
			return keyBuffer.toString();
		}
		throw new NullPointerException();
	}
}
