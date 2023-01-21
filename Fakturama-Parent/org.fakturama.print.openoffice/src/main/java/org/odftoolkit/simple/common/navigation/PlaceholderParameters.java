package org.odftoolkit.simple.common.navigation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


/**
 * GS/ [TPR]
 * Class that encapsulates the placeholder's parameter(s)
 * @author gschrick
 *
 */
public class PlaceholderParameters {
    /**
     * char that marks the beginning of a parameter in a placeholder
     */
    public static final char PARAMETER_SEPARATOR = '$';
    /**
     * char that marks the end the parameter name
     */
    public static final char PARAMETER_NAME_TEMINATOR = ':';
	/**
	 * char that marks the beginning of an entity (encoded special character) within
	 * a placeholder parameter body
	 */
	public static final char PLACEHOLDER_ENTITY_CHAR = '%';


	public class Parameter {
		public final String key;
		public final String body;

		Parameter(String paramString) {
			if (paramString.length() >= 1) {
				String[] splittedParam = paramString.split("\\" + PARAMETER_NAME_TEMINATOR, 2);
				key = StringUtils.isNotBlank(splittedParam[0]) ? splittedParam[0].trim().toUpperCase() : null;
				body = splittedParam.length > 0 ? StringUtils.removeStart(StringUtils.removeEnd(splittedParam[1].trim(), "\""), "\"") : null;
			} else {
				key = null;
				body = null;
			}
		}
	}

	private List<Parameter> params = null;
	
	
	private PlaceholderParameters(String placeholder) {
		super();
		readParameters(placeholder);
	}
	
	/**
	 * constructs a new PlaceholderParameter instance<br>
	 * by parsing the input string and extracting the parameters.<br>
	 * Parameters (if any) are preprocessed:<br>
	 * - params with an empty key are ignored<br>
	 * - key is trimmed and uppercased<br>
	 * - body is trimmed first, then enclosing quotes (if any) are removed<br>
	 * - encoded entities in body remain encoded
	 * @param placeholder
	 * @return
	 * 		new instance
	 */
	public static PlaceholderParameters of(String placeholder) {
		return new PlaceholderParameters(placeholder);
	}

	private void readParameters(String placeholder) {
		if (placeholder.indexOf(PARAMETER_SEPARATOR) >= 0) {
			params = new ArrayList<Parameter>();
			String[] parts = StringUtils.removeStart(
						StringUtils.removeEnd(placeholder, PlaceholderNavigation.PLACEHOLDER_SUFFIX),
						PlaceholderNavigation.PLACEHOLDER_PREFIX).split("\\"+PARAMETER_SEPARATOR);
			// note: parts[0] is placeholderName
			for (int i = 1; i < parts.length; i++) {
				Parameter param = new Parameter(parts[i]);
				if (param.key != null)
					params.add(param);
			}
			if (params.size() == 0)
				params = null;
		}
	}

	/**
	 * get body of first(!) parameter with the given key
	 * @param key
	 * 		key of the parameter (param keys are always uppercase!)
	 * @param defaultValue
	 * 		value to return in case the param does not exists
	 * @return
	 * 		parameter body or defaultValue if key is not found
	 */
	public String getParameterBody(String key, String defaultValue) {
		if (key != null && params != null) {
			for (Parameter p: params) {
				if (key.equals(p.key)) return p.body;
			}
		}
		return defaultValue;
	}
	
	public boolean isEmpty() {
		return (params == null || params.size() == 0);
	}
	
	public List<Parameter> getParameters() {
		if (params != null)
			return params;
		else
			return new ArrayList<Parameter>();
	}
}
