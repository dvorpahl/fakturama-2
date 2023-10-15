package org.odftoolkit.helper.common.navigation;

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

	private List<TemplateParameter> params = null;
	
	
	private PlaceholderParameters(String placeholder) {
		super();
		readParameters(placeholder);
	}
	
	/**
	 * constructs a new {@link PlaceholderParameters} instance<br>
	 * by parsing the input string and extracting the parameters.<br>
	 * Parameters (if any) are preprocessed:<br>
	 * <ul><li> params with an empty key are ignored<br>
	 * <li> key is trimmed and uppercased<br>
	 * <li> body is trimmed first, then enclosing quotes (if any) are removed<br>
	 * <li> encoded entities in body remain encoded</ul>
	 * @param placeholder
	 * @return
	 * 		new instance
	 */
	public static PlaceholderParameters of(String placeholder) {
		return new PlaceholderParameters(placeholder);
	}

	private void readParameters(String placeholder) {
		if (placeholder.indexOf(PARAMETER_SEPARATOR) >= 0) {
			params = new ArrayList<>();
			String[] parts = StringUtils.removeStart(
						StringUtils.removeEnd(placeholder, PlaceholderNavigation.PLACEHOLDER_SUFFIX),
						PlaceholderNavigation.PLACEHOLDER_PREFIX).split("\\"+PARAMETER_SEPARATOR);
			// note: parts[0] is placeholderName
			for (int i = 1; i < parts.length; i++) {
				TemplateParameter param = new TemplateParameter(parts[i]);
				if (param.getKey() != null)
					params.add(param);
			}
			if (params.isEmpty())
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
			for (TemplateParameter p: params) {
				if (key.equals(p.getKey())) return p.getBody();
			}
		}
		return defaultValue;
	}
	
	public boolean isEmpty() {
		return (params == null || params.size() == 0);
	}
	
	public List<TemplateParameter> getParameters() {
		if (params != null)
			return params;
		else
			return new ArrayList<TemplateParameter>();
	}
}
