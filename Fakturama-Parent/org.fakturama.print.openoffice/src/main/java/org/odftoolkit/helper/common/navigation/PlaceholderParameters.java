package org.odftoolkit.helper.common.navigation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * GS/ [TPR] Class that encapsulates the placeholder's parameter(s)
 * 
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
	public static final char PARAMETER_NAME_TERMINATOR = ':';

	/**
	 * char that marks the beginning of an entity (encoded special character) within
	 * a placeholder parameter body
	 */
	public static final char PLACEHOLDER_ENTITY_CHAR = '%';

	private Map<String, String> paramMap = new HashMap<>();

	/**
	 * Constructs a new {@link PlaceholderParameters} instance<br>
	 * by parsing the input string and extracting the parameters.<br>
	 * Parameters (if any) are preprocessed:<br>
	 * <ul>
	 * <li>params with an empty key are ignored<br>
	 * <li>key is trimmed and uppercased<br>
	 * <li>body is trimmed first, then enclosing quotes (if any) are removed<br>
	 * <li>encoded entities in body remain encoded
	 * </ul>
	 * 
	 * @param placeholder
	 * @return new instance
	 */
	public static PlaceholderParameters of(String placeholder) {
		return new PlaceholderParameters(placeholder);
	}

	private PlaceholderParameters(String placeholder) {
		readParameters(placeholder);
	}

	private void readParameters(String placeholder) {
		if (placeholder.indexOf(PARAMETER_SEPARATOR) >= 0) {
			String[] parts = StringUtils
					.removeStart(StringUtils.removeEnd(placeholder, PlaceholderNavigation.PLACEHOLDER_SUFFIX),
							PlaceholderNavigation.PLACEHOLDER_PREFIX)
					.split("\\" + PARAMETER_SEPARATOR);
			// note: parts[0] is placeholderName
			for (int i = 1; i < parts.length; i++) {
				extractParams(parts[i]);
			}
		}
	}

	private void extractParams(String paramString) {
		if (paramString.length() >= 1) {
			String[] splittedParam = paramString.split("\\" + PlaceholderParameters.PARAMETER_NAME_TERMINATOR, 2);
			String key = StringUtils.isNotBlank(splittedParam[0]) ? splittedParam[0].trim().toUpperCase() : null;
			String body = splittedParam.length > 0
					? StringUtils.removeStart(StringUtils.removeEnd(splittedParam[1].trim(), "\""), "\"")
					: null;
			paramMap.put(key, body);
		}
	}

	/**
	 * get body of first(!) parameter with the given key
	 * 
	 * @param key          key of the parameter (param keys are always uppercase!)
	 * @param defaultValue value to return in case the param does not exists
	 * @return parameter body or defaultValue if key is not found
	 */
	public String getParameterBody(String key, String defaultValue) {
		if (key != null && !isEmpty()) {
			return paramMap.get(key);
		}
		return defaultValue;
	}

	public boolean isEmpty() {
		return paramMap.isEmpty();
	}

	public Set<Entry<String, String>> getEntries() {
		return paramMap.entrySet();
	}
}
