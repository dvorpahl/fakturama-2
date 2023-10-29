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

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.texo.component.TexoComponent;

/**
 * Implementation of the {@link ModelObject} interface with as an underlying object an EObject.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 * @see ModelResolver#getModelObject(Object)
 */
public class ModelEObject implements ModelObject<EObject>, TexoComponent {

  private EObject target;

  public ModelPackage getModelPackage() {
    return ModelResolver.getInstance().getModelPackage(target.eClass().getEPackage().getNsURI());
  }

  public void setTarget(EObject target) {
    this.target = target;
  }

  public EObject getTarget() {
    return target;
  }

  public Class<?> getTargetClass() {
    return EObject.class;
  }

  public EClass eClass() {
    return target.eClass();
  }

  public Object eGet(EStructuralFeature eStructuralFeature) {
    return target.eGet(eStructuralFeature);
  }

  public void eSet(EStructuralFeature eStructuralFeature, Object value) {
    target.eSet(eStructuralFeature, value);
  }

  @SuppressWarnings("unchecked")
  public boolean eAddTo(EStructuralFeature eStructuralFeature, Object value) {
    return ((Collection<Object>) eGet(eStructuralFeature)).add(value);
  }

  @SuppressWarnings("unchecked")
  public boolean eRemoveFrom(EStructuralFeature eStructuralFeature, Object value) {
    return ((Collection<Object>) eGet(eStructuralFeature)).remove(value);
  }

}