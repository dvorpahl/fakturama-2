/**
 * <copyright>
 *
 * Copyright (c) 2009, 2010 Springsite BV (The Netherlands) and others
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
 * $Id: AbstractModelFeatureMapEntry.java,v 1.5 2011/08/25 12:32:37 mtaal Exp $
 */

package org.eclipse.emf.texo.model;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * The abstract base class for the generated wrappers of feature maps.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 * @see FeatureMap
 * @see ModelResolver
 */
public abstract class AbstractModelFeatureMapEntry<E extends Object> implements ModelFeatureMapEntry<E> {

  private E target;

  /**
   * @return the underlying generated target/group object
   */
  public E getTarget() {
    return target;
  }

  /**
   * @param the
   *          target object which is wrapped
   */
  public void setTarget(final E target) {
    this.target = target;
  }

  /**
   * Returns true if the entry models an xsd:any model element.
   */
  public boolean isAnyType() {
    return false;
  }
}