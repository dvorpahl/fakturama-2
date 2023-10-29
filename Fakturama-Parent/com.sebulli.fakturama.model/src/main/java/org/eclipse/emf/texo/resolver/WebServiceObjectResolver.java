/**
 * <copyright>
 *
 * Copyright (c) 2012 Springsite BV (The Netherlands) and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martin Taal - Initial API and implementation
 *   Dzmitry [zmicer] Harachka - implementation
 * </copyright>
 *
 * $Id: XMLServiceContext.java,v 1.6 2011/08/27 05:40:32 mtaal Exp $
 */
package org.eclipse.emf.texo.resolver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;

/**
 * An {@link ObjectResolver} which reads referenced objects using the url, through a webservice.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 */
public abstract class WebServiceObjectResolver extends DefaultObjectResolver {

  private final Map<String, Object> objectCache = new HashMap<String, Object>();

  public WebServiceObjectResolver() {
    setUseWebServiceUriFormat(true);
  }

  @Override
  public Object fromUri(URI objectUri) {
    final String urlStr = objectUri.toString();
    if (objectCache.containsKey(urlStr)) {
      return objectCache.get(urlStr);
    }

    try {
      final Object result = readObject(urlStr);
      if (result != null) {
        objectCache.put(urlStr, result);
        return result;
      }
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
    return super.fromUri(objectUri);
  }

  protected Object readObject(String urlStr) throws Exception {
    final HttpURLConnection hc = createConnection(urlStr);
    final InputStream is = hc.getInputStream();
    final StringBuilder sb = new StringBuilder();
    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8")); //$NON-NLS-1$
    String line;
    while ((line = reader.readLine()) != null) {
      sb.append(line).append("\n"); //$NON-NLS-1$
    }

    return deserialize(urlStr, sb.toString());
  }

  protected abstract Object deserialize(String urlString, String content);

  protected HttpURLConnection createConnection(String urlStr) throws Exception {
    final URL url = new URL(urlStr);
    final HttpURLConnection hc = (HttpURLConnection) url.openConnection();
    hc.setRequestMethod("GET"); //$NON-NLS-1$
    hc.setAllowUserInteraction(false);
    hc.setDefaultUseCaches(false);
    hc.setDoOutput(true);
    hc.setDoInput(true);
    hc.setInstanceFollowRedirects(true);
    hc.setUseCaches(false);
    return hc;
  }

  public Map<String, Object> getObjectCache() {
    return objectCache;
  }

}
