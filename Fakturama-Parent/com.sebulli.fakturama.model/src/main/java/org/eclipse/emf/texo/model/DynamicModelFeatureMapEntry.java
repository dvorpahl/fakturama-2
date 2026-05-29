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
 *
 * </copyright>
 *
 * $Id: AbstractModelFeatureMapEntry.java,v 1.5 2011/08/25 12:32:37 mtaal Exp $
 */

package org.eclipse.emf.texo.model;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * {@link ModelFeatureMapEntry} used for dynamic models.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 * @see FeatureMap
 * @see ModelResolver
 */
public class DynamicModelFeatureMapEntry extends AbstractModelFeatureMapEntry<Object> {

  private Object value;
  private EStructuralFeature eStructuralFeature;

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public EStructuralFeature getEStructuralFeature() {
    return eStructuralFeature;
  }

  public void setEStructuralFeature(EStructuralFeature eStructuralFeature) {
    this.eStructuralFeature = eStructuralFeature;
  }

}