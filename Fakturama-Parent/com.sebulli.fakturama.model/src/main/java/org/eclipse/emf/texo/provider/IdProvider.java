/**
 * <copyright>
 *
 * Copyright (c) 2011-2012 Springsite BV (The Netherlands) and others
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
package org.eclipse.emf.texo.provider;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.texo.component.ComponentProvider;
import org.eclipse.emf.texo.component.TexoComponent;
import org.eclipse.emf.texo.component.TexoStaticSingleton;
import org.eclipse.emf.texo.model.ModelConstants;
import org.eclipse.emf.texo.model.ModelFactory;
import org.eclipse.emf.texo.model.ModelObject;
import org.eclipse.emf.texo.model.ModelPackage;
import org.eclipse.emf.texo.model.ModelResolver;
import org.eclipse.emf.texo.utils.ModelUtils;

/**
 * Provides the {@link EAttribute} which models the id of an object. The EAttribute is identified by an
 * {@link EAnnotation} with the source set to {@link ModelConstants#ID_ANNOTATION_SOURCE}.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 */
public class IdProvider implements TexoComponent, TexoStaticSingleton {

  private static final String EMPTY_ID_STRING = ""; //$NON-NLS-1$

  private static IdProvider instance = ComponentProvider.getInstance().newInstance(IdProvider.class);

  public static IdProvider getInstance() {
    return instance;
  }

  public static void setInstance(IdProvider instance) {
    IdProvider.instance = instance;
  }

  private final Map<EClass, EAttribute> idEAttributes = new ConcurrentHashMap<EClass, EAttribute>();

  private final Set<EClass> noIdEAttributes = new HashSet<EClass>();

  /**
   * Returns the id value of an object. Works for EObject as well as ModelObjects.
   * 
   * @param object
   *          the object to determine an id for, must be an object which can be converted to a {@link ModelObject} using
   *          the {@link ModelResolver#getModelObject(Object)}.
   * @return the id value of the object
   */
  public Object getId(Object object) {
    if (object instanceof EObject) {
      final EObject eObject = (EObject) object;
      final EAttribute idEAttribute = getCreateIdEAttribute(eObject.eClass());
      return eObject.eGet(idEAttribute);
    }

    final ModelObject<?> modelObject = ModelResolver.getInstance().getModelObject(object);
    return modelObject.eGet(getCreateIdEAttribute(modelObject.eClass()));
  }

  /**
   * Returns the id value of an object as a String. Internally calls the {@link #getIdAsString(ModelObject)}.
   * 
   * @param object
   *          the object to determine an id for, must be an object which can be converted to a {@link ModelObject} using
   *          the {@link ModelResolver#getModelObject(Object)}.
   * @return the id value of the object as a String
   * @see ModelFactory#convertToString(EDataType, Object)
   */
  public String getIdAsString(Object object) {
    if (object instanceof EObject) {
      final EObject eObject = (EObject) object;
      final EAttribute idEAttribute = getCreateIdEAttribute(eObject.eClass());
      return getIdAsString(eObject.eClass(), eObject.eGet(idEAttribute));
    }
    return getIdAsString(ModelResolver.getInstance().getModelObject(object));
  }

  /**
   * @see #getIdAsString(Object)
   */
  public String getIdAsString(ModelObject<?> modelObject) {
    final EAttribute idEAttribute = getCreateIdEAttribute(modelObject.eClass());
    return getIdAsString(modelObject.eClass(), modelObject.eGet(idEAttribute));
  }

  /**
   * @see #getIdAsString(Object)
   * @see ModelFactory#convertToString(EDataType, Object)
   */
  public String getIdAsString(EClass eClass, Object idValue) {
    final EAttribute idEAttribute = getCreateIdEAttribute(eClass);
    final EDataType eDataType = idEAttribute.getEAttributeType();
    if (idValue == null) {
      // NOTE: or should null be returned....
      return EMPTY_ID_STRING;
    }
    final String eDataTypePackageUri = eDataType.getEPackage().getNsURI();
    final ModelPackage modelPackage = ModelResolver.getInstance().getModelPackage(eDataTypePackageUri);
    if (modelPackage != null) {
      final ModelFactory modelFactory = modelPackage.getModelFactory();
      return modelFactory.convertToString(eDataType, idValue);
    }
    return eDataType.getEPackage().getEFactoryInstance().convertToString(eDataType, idValue);
  }

  /**
   * Convert the id String to an object of the correct type.
   * 
   * @param eClass
   * @param idString
   * @return the id of the correct java class.
   * @see ModelFactory#createFromString(EDataType, String)
   */
  public Object convertIdStringToId(EClass eClass, String idString) {
    if (idString == null || idString.trim().length() == 0) {
      return null;
    }
    final EAttribute idEAttribute = getCreateIdEAttribute(eClass);
    final EDataType eDataType = idEAttribute.getEAttributeType();
    final String eDataTypePackageUri = eDataType.getEPackage().getNsURI();
    final ModelPackage modelPackage = ModelResolver.getInstance().getModelPackage(eDataTypePackageUri);
    if (modelPackage != null) {
      final ModelFactory modelFactory = modelPackage.getModelFactory();
      return modelFactory.createFromString(eDataType, idString);
    }
    return eDataType.getEPackage().getEFactoryInstance().createFromString(eDataType, idString);
  }

  /**
   * Find the {@link EAttribute} that represents the id of instances of the {@link EClass}.
   */
  public EAttribute getIdEAttribute(EClass eClass) {
    return getCreateIdEAttribute(eClass);
  }

  /**
   * Return true if the object has a modeled id eattribute, false otherwise.
   */
  public boolean hasIdEAttribute(Object object) {
    final ModelObject<?> modelObject = ModelResolver.getInstance().getModelObject(object);
    return hasIdEAttribute(modelObject.eClass());
  }

  /**
   * Return true if the eClass has a modeled id eattribute, false otherwise.
   */
  public boolean hasIdEAttribute(EClass eClass) {
    EAttribute idEAttribute = idEAttributes.get(eClass);
    if (idEAttribute != null) {
      return true;
    }
    if (noIdEAttributes.contains(eClass)) {
      return false;
    }
    try {
      getCreateIdEAttribute(eClass);
      return true;
    } catch (IllegalStateException e) {
      noIdEAttributes.add(eClass);
      return false;
    }
  }

  protected EAttribute getCreateIdEAttribute(EClass eClass) {
    EAttribute idEAttribute = idEAttributes.get(eClass);
    if (idEAttribute != null) {
      return idEAttribute;
    }
    for (EStructuralFeature eFeature : eClass.getEAllStructuralFeatures()) {
      if (ModelUtils.hasEAnnotation(eFeature, ModelConstants.ID_ANNOTATION_KEY)) {
        if (eFeature instanceof EReference) {
          throw new UnsupportedOperationException("EReference id's not yet supported, eclass " //$NON-NLS-1$
              + eClass.getName());
        }
        idEAttribute = (EAttribute) eFeature;
        break;
      }
    }

    if (idEAttribute == null) {
      throw new IllegalStateException("Not possible to determine id eattribute for eClass " + eClass); //$NON-NLS-1$
    }
    idEAttributes.put(eClass, idEAttribute);
    return idEAttribute;
  }
}
