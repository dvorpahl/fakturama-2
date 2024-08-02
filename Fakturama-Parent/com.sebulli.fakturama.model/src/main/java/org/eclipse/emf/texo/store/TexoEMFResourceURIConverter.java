/**
 * <copyright>
 *
 * Copyright (c) 2015 Springsite BV (The Netherlands) and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martin Taal - Initial API and implementation
 *
 * </copyright>
 *
 * $Id: EntityManagerProvider.java,v 1.7 2011/09/26 19:48:10 mtaal Exp $
 */
package org.eclipse.emf.texo.store;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.texo.component.TexoComponent;

/**
 * Normalizes a http URI to a default file URI. As a default it will create a file uri pointing to a file in the temp
 * directory. It uses
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 */
public class TexoEMFResourceURIConverter extends ExtensibleURIConverterImpl implements TexoComponent {

  private ResourceType resourceType = ResourceType.XMI;

  @Override
  public URI normalize(URI uri) {
    if (uri.isFile()) {
      return uri;
    }
    String[] segments = uri.segments();
    String fileName = segments[0] + "_" + segments[1] + "." + getFileExtension(); //$NON-NLS-1$ //$NON-NLS-2$
    String dir = getTemporaryDirectoryPath();
    return URI.createFileURI(dir + File.separator + fileName);
  }

  protected String getFileExtension() {
    switch (resourceType) {
    case XMI:
      return "xmi"; //$NON-NLS-1$
    case XML:
      return "xml"; //$NON-NLS-1$
    default:
      throw new IllegalStateException("Resource type " + resourceType + " not supported"); //$NON-NLS-1$ //$NON-NLS-2$
    }
  }

  /**
   * @return the directory in which the xmi/xmi files are found. As a default will return the java temporary directory.
   */
  protected String getTemporaryDirectoryPath() {
    try {
      final File f = File.createTempFile("test" + System.currentTimeMillis(), null); //$NON-NLS-1$
      String tempDirectory = f.getParentFile().getAbsolutePath();
      f.delete();
      return tempDirectory;
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public ResourceType getResourceType() {
    return resourceType;
  }

  public void setResourceType(ResourceType resourceType) {
    this.resourceType = resourceType;
  }

  public static enum ResourceType {
    XML, XMI
  }
}
