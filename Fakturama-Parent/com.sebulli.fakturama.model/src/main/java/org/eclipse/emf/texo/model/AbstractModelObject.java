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
 * $Id: AbstractModelObject.java,v 1.6 2011/08/25 12:32:37 mtaal Exp $
 */

package org.eclipse.emf.texo.model;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * The abstract parent class for all {@link ModelObject} wrappers. ModelObjects are wrappers/adapters of generated pojos
 * to add model-related behavior in an efficient way. The generated ModelObjects extend this abstract class. The class
 * parameter is the class of the pojo wrapped by this ModelObject.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 * @see ModelFactory
 * @see ModelResolver
 */
public abstract class AbstractModelObject<E extends Object> implements ModelObject<E> {

  private E target;

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.emf.texo.model.ModelObject#setTarget(java.lang.Object)
   */
  public void setTarget(final E target) {
    if (!getTargetClass().isInstance(target)) {
      throw new IllegalArgumentException("Expected object of class " + getTargetClass() + " but it was " + target); //$NON-NLS-1$//$NON-NLS-2$
    }
    this.target = target;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.emf.texo.model.ModelObject#getTargetClass()
   */
  public Class<?> getTargetClass() {
    return Object.class;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.emf.texo.model.ModelObject#getTarget()
   */
  public E getTarget() {
    return target;
  }

  /*
   * (non-Javadoc)
   * 
   * @seeorg.eclipse.emf.texo.model.ModelObject#eGet(org.eclipse.emf.ecore. EStructuralFeature)
   */
  public Object eGet(final EStructuralFeature eStructuralFeature) {
    throw new IllegalArgumentException("EStructuralFeature: " + eStructuralFeature + " not found in EClass " //$NON-NLS-1$ //$NON-NLS-2$
        + eClass());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.emf.texo.model.ModelObject#eSet(org.eclipse.emf.ecore. EStructuralFeature, java.lang.Object)
   */
  public void eSet(final EStructuralFeature eStructuralFeature, final Object value) {
    throw new IllegalArgumentException("EStructuralFeature: " + eStructuralFeature + " not found in EClass " //$NON-NLS-1$ //$NON-NLS-2$
        + eClass() + " when setting value " + value); //$NON-NLS-1$
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.emf.texo.model.ModelObject#eAddTo(org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
   */
  public boolean eAddTo(final EStructuralFeature eStructuralFeature, final Object value) {
    throw new IllegalArgumentException(
        "EStructuralFeature: " + eStructuralFeature + " not found in EClass or feature is not a Many feature " //$NON-NLS-1$ //$NON-NLS-2$
        + eClass() + " when adding value " + value); //$NON-NLS-1$
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.emf.texo.model.ModelObject#eRemoveFrom(org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
   */
  public boolean eRemoveFrom(final EStructuralFeature eStructuralFeature, final Object value) {
    throw new IllegalArgumentException(
        "EStructuralFeature: " + eStructuralFeature + " not found in EClass or feature is not a Many feature " //$NON-NLS-1$ //$NON-NLS-2$
        + eClass() + " when removing value " + value); //$NON-NLS-1$
  }

  @Override
  public String toString() {
    return "ModelObject [EClass: " + eClass() + ", target: " + target + "]"; //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
  }

}