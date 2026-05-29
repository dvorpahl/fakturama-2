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
 * $Id: ModelObject.java,v 1.13 2011/08/25 12:32:37 mtaal Exp $
 */

package org.eclipse.emf.texo.model;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * The ModelObject is a wrapper/adapter of a model-managed pojo. A model-managed pojo is a pojo which has been generated
 * on the basis of a model.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 * @see ModelResolver#getModelObject(Object)
 */
public interface ModelObject<E extends Object> {

  /**
   * @return the {@link ModelPackage} of this object
   */
  ModelPackage getModelPackage();

  /**
   * @param target
   *          the target object to be wrapped by this ModelObject
   */
  void setTarget(E target);

  /**
   * @return the expected {@link Class} of the target object
   */
  Class<?> getTargetClass();

  /**
   * @return the wrapped target object
   */
  E getTarget();

  /**
   * @return the {@link EClass} represented by this object
   */
  EClass eClass();

  /**
   * @param eStructuralFeature
   *          the {@link EStructuralFeature} for which the value is returned.
   * @return the value of the {@link EStructuralFeature} in the target object.
   * @see #getTarget()
   */
  Object eGet(EStructuralFeature eStructuralFeature);

  /**
   * Sets the value of an {@link EStructuralFeature} in the target object.
   * 
   * @param eStructuralFeature
   *          the feature to set
   * @param value
   *          the value to set
   * @see #getTarget()
   */
  void eSet(EStructuralFeature eStructuralFeature, Object value);

  /**
   * Adds a value to the {@link EStructuralFeature} of the target object.
   * 
   * @param eStructuralFeature
   *          the feature to add
   * @param value
   *          the value to add
   * @return true if the object gets added to the list, normally an object is only added if it is not already in the
   *         list
   */
  boolean eAddTo(EStructuralFeature eStructuralFeature, Object value);

  /**
   * Removes the value from an {@link EStructuralFeature} of the target object.
   * 
   * @param eStructuralFeature
   *          the feature to add
   * @param value
   *          the value to add
   * @return true if the object got removed, if it was present in the list.
   * 
   */
  boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value);

}