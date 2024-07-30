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
 * $Id: ModelFeatureMapEntry.java,v 1.4 2011/08/25 12:32:37 mtaal Exp $
 */

package org.eclipse.emf.texo.model;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * The interface definition for the adapter/wrapper generated for a feature map.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 * @see FeatureMap
 * @see ModelResolver
 */
public interface ModelFeatureMapEntry<E extends Object> {

  /**
   * @return the underlying generated target/group object
   */
  E getTarget();

  /**
   * @param the
   *          target object which is wrapped
   */
  void setTarget(E target);

  /**
   * @return the EStructuralFeature set for the feature map entry.
   */
  EStructuralFeature getEStructuralFeature();

  /**
   * Sets the EStructuralFeature for the feature map entry.
   * 
   * @param eStructuralFeature
   */
  void setEStructuralFeature(final EStructuralFeature eStructuralFeature);

  /**
   * @return the value in the feature map entry
   */
  Object getValue();

  /**
   * Sets the value in the feature map entry. Note first call {@link #setEStructuralFeature(EStructuralFeature)} before
   * calling this method.
   * 
   * @param value
   */
  void setValue(final Object value);

  /**
   * Returns true if the entry models an xsd:any model element.
   */
  public boolean isAnyType();

}