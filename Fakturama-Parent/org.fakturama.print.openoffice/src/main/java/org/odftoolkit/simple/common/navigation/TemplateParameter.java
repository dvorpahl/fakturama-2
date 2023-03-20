package org.odftoolkit.simple.common.navigation;

import org.apache.commons.lang3.StringUtils;

public class TemplateParameter {
	private final String key;
	private final String body;

	public TemplateParameter(String paramString) {
		if (paramString.length() >= 1) {
			String[] splittedParam = paramString.split("\\" + PlaceholderParameters.PARAMETER_NAME_TEMINATOR, 2);
			key = StringUtils.isNotBlank(splittedParam[0]) ? splittedParam[0].trim().toUpperCase() : null;
			body = splittedParam.length > 0 ? StringUtils.removeStart(StringUtils.removeEnd(splittedParam[1].trim(), "\""), "\"") : null;
		} else {
			key = null;
			body = null;
		}
	}

	public String getKey() {
		return key;
	}

	public String getBody() {
		return body;
	}
}