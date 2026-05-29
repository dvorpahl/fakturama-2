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
 * $Id: ModelFactory.java,v 1.11 2011/08/25 12:32:37 mtaal Exp $
 */

package org.eclipse.emf.texo.model;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * The ModelFactory is responsible for runtime and instance handling of a model. This interface defines methods
 * providing and creating instances of the generated pojo's, access to features of model instances.
 * 
 * Generated ModelFactory classes will have inner classes (extending {@link AbstractModelObject} and implementing
 * {@link ModelObject}) for each of the {@link EClass} of the {@link EPackage}. These inner classes provide efficient
 * access to the runtime model for a pojo. See also the {@link ModelResolver#getModelObject(Object)} method.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 * @see AbstractModelObject
 * @see ModelObject
 */
public interface ModelFactory {

  /**
   * Creates an instance for the {@link EClass}.
   * 
   * @return an instance for the EClass.
   */
  Object create(EClass eClass);

  /**
   * Wraps an object in a {@link ModelObject}.
   * 
   * @param eClass
   *          the EClass of the object
   * @param adaptee
   *          the object being wrapped/adapted
   * @return the wrapper {@link ModelObject}
   */
  <T> ModelObject<T> createModelObject(EClass eClass, T adaptee);

  /**
   * Creates a feature map entry instance for a certain EStructuralFeature.
   * 
   * @param eStructuralFeature
   *          the feature map feature
   * @return the pojo feature map entry
   * @generated
   */
  public Object createFeatureMapEntry(EStructuralFeature eFeature);

  /**
   * Wraps a feature map entry pojo in a {@link AbstractModelFeatureMapEntry}. If the feature map entry is null then a
   * new one is created and <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @param eStructuralFeature
   *          the feature map feature of the object
   * @param adaptee
   *          the pojo feature map entry being wrapped/adapted
   * @return the wrapper {@link ModelFeatureMapEntry}
   * @generated
   */
  public ModelFeatureMapEntry<?> createModelFeatureMapEntry(EStructuralFeature eFeature, Object adaptee);

  /**
   * <!-- begin-user-doc --> Converts an instance of an {@link org.eclipse.emf.ecore.EDataType} to a String <!--
   * end-user-doc -->
   * 
   * @param eDataType
   *          the {@link org.eclipse.emf.ecore.EDataType} defining the type
   * @param value
   *          the object to convert, if null then null is returned
   * @generated
   */
  String convertToString(org.eclipse.emf.ecore.EDataType eDataType, Object value);

  /**
   * <!-- begin-user-doc --> Converts an instance of an {@link org.eclipse.emf.ecore.EDataType} to a String <!--
   * end-user-doc -->
   * 
   * @param eDataType
   *          the {@link org.eclipse.emf.ecore.EDataType} defining the type
   * @param value
   *          the object to convert, if null then null is returned
   * @generated
   */
  Object createFromString(org.eclipse.emf.ecore.EDataType eDataType, String value);

}