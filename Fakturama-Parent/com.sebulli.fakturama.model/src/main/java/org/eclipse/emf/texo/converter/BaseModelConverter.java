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
 * $Id: ModelEMFConverter.java,v 1.27 2011/09/14 15:35:53 mtaal Exp $
 */

package org.eclipse.emf.texo.converter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.texo.component.ComponentProvider;
import org.eclipse.emf.texo.component.TexoComponent;
import org.eclipse.emf.texo.model.ModelObject;
import org.eclipse.emf.texo.resolver.DefaultObjectResolver;
import org.eclipse.emf.texo.resolver.ObjectResolver;
import org.eclipse.emf.texo.utils.ModelUtils;

/**
 * Base class for model to another format converter.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 * @see ModelObject
 */
public abstract class BaseModelConverter<T extends Object> implements TexoComponent {

  // list of objects that are to be proxied
  private List<T> proxyObjects = new ArrayList<T>();
  private List<T> nonProxiedObjects = new ArrayList<T>();

  private boolean skipVolatileEFeatures = false;

  /**
   * If true then referenced non contained objects are also converted and added to the conversion stack.
   */
  private boolean convertNonContainedReferencedObjects = false;

  /**
   * How many levels of child objects should be traversed before using proxy objects and stop converting
   */
  private int maxChildLevelsToConvert = Integer.MAX_VALUE;

  /**
   * The object resolver responsible for creating uri's and objects.
   */
  private ObjectResolver objectResolver = ComponentProvider.getInstance().newInstance(DefaultObjectResolver.class);

  protected void doBaseActions(Collection<T> objects) {
    if (objectResolver != null
        && (!convertNonContainedReferencedObjects || maxChildLevelsToConvert < Integer.MAX_VALUE)) {
      computeProxyObjects(objects);
    }
    // make sure that the non proxied objects don't get proxied
    proxyObjects.removeAll(nonProxiedObjects);
  }

  protected void computeProxyObjects(final Collection<T> objects) {
    for (T o : objects) {
      traverseEReferencesForProxyDetermination(o, 0);
    }
  }

  protected boolean isProxy(T o) {
    return false;
  }

  @SuppressWarnings("unchecked")
  protected void traverseEReferencesForProxyDetermination(T object, int level) {

    if (isProxy(object)) {
      if (!proxyObjects.contains(object)) {
        proxyObjects.add(object);
      }
      return;
    }

    if (nonProxiedObjects.contains(object) || nonProxiedObjects.contains(object)) {
      // been here, go away
      return;
    }

    // special case, a query can return an array, allows jsonizing this
    // to be send to the client
    if (object.getClass().isArray()) {
      for (int i = 0; i < Array.getLength(object); i++) {
        final Object value = Array.get(object, i);
        if (isModelEnabled(value)) {
          traverseEReferencesForProxyDetermination((T) value, level);
        }
      }
      return;
    }

    nonProxiedObjects.add(object);

    final boolean proxyChildObjects = level == maxChildLevelsToConvert;
    for (EReference eReference : eClass(object).getEAllReferences()) {
      if (isSkipVolatileEFeatures() && eReference.isVolatile()) {
        continue;
      }
      if (doSkipNotChangeAbleFeatures() && !eReference.isChangeable()) {
        continue;
      }
      final T value = (T) eGet(object, eReference);
      if (value == null) {
        continue;
      }
      if (eReference.isContainment()) {
        if (!proxyChildObjects) {
          if (value instanceof Collection<?>) {
            for (T o : (Collection<T>) value) {
              traverseEReferencesForProxyDetermination(o, level + 1);
            }
          } else if (value instanceof Map<?, ?>) {
            final Map<?, ?> map = (Map<?, ?>) value;
            for (Object key : map.keySet()) {
              final Object keyValue = map.get(key);
              if (isModelEnabled(key)) {
                traverseEReferencesForProxyDetermination((T) key, level + 1);
              }
              if (isModelEnabled(keyValue)) {
                traverseEReferencesForProxyDetermination((T) keyValue, level + 1);
              }
            }
          } else {
            traverseEReferencesForProxyDetermination(value, level + 1);
          }
          continue;
        }
      } else if (convertNonContainedReferencedObjects) {
        if (value instanceof Collection<?>) {
          for (T o : (Collection<T>) value) {
            traverseEReferencesForProxyDetermination(o, level);
          }
        } else if (value instanceof Map<?, ?>) {
          final Map<?, ?> map = (Map<?, ?>) value;
          for (Object key : map.keySet()) {
            final Object keyValue = map.get(key);
            if (isModelEnabled(key)) {
              traverseEReferencesForProxyDetermination((T) key, level + 1);
            }
            if (isModelEnabled(keyValue)) {
              traverseEReferencesForProxyDetermination((T) keyValue, level + 1);
            }
          }
        } else {
          traverseEReferencesForProxyDetermination(value, level);
        }
        continue;
      }
      if (value instanceof Collection<?>) {
        proxyObjects.addAll((Collection<T>) value);
      } else {
        proxyObjects.add(value);
      }
    }
  }

  protected abstract EClass eClass(T target);

  protected abstract Object eGet(T target, EStructuralFeature eFeature);

  // final ModelObject<?> modelObject = ModelResolver.getInstance().getModelObject(object);

  protected abstract boolean isModelEnabled(Object target);

  // ModelResolver.getInstance().isModelEnabled(key)

  /**
   * If a non-null value is returned then the content of the modelObject is not converted.
   * 
   * The default implementation returns null.
   * 
   * @param modelObject
   *          the modelObject to get the proxy id for
   * @return the proxy uri, should encode the type of the object as well as its id
   */
  protected org.eclipse.emf.common.util.URI getProxyId(T target) {
    return getObjectResolver().toUri(target);
  }

  /**
   * See the javadoc in the {@link ModelUtils#getEnumBaseDataTypeIfObject(EDataType)} for details.
   * 
   * @param eDataType
   * @return the passed EDataType or its base type if the base type is an EEnum
   */
  protected EDataType getDataTypeOrBaseType(EDataType eDataType) {
    final EDataType baseType = ModelUtils.getEnumBaseDataTypeIfObject(eDataType);
    if (baseType != null) {
      return baseType;
    }
    return eDataType;
  }

  public boolean isConvertNonContainedReferencedObjects() {
    return convertNonContainedReferencedObjects;
  }

  public void setConvertNonContainedReferencedObjects(boolean convertNonContainedReferencedObjects) {
    this.convertNonContainedReferencedObjects = convertNonContainedReferencedObjects;
  }

  public int getMaxChildLevelsToConvert() {
    return maxChildLevelsToConvert;
  }

  public void setMaxChildLevelsToConvert(int maxChildLevelsToConvert) {
    this.maxChildLevelsToConvert = maxChildLevelsToConvert;
  }

  public ObjectResolver getObjectResolver() {
    return objectResolver;
  }

  public void setObjectResolver(ObjectResolver uriResolver) {
    objectResolver = uriResolver;
  }

  public List<T> getProxyObjects() {
    return proxyObjects;
  }

  public void setProxyObjects(List<T> proxyObjects) {
    this.proxyObjects = proxyObjects;
  }

  public List<T> getNonProxiedObjects() {
    return nonProxiedObjects;
  }

  public void setNonProxiedObjects(List<T> nonProxiedObjects) {
    this.nonProxiedObjects = nonProxiedObjects;
  }

  public boolean isSkipVolatileEFeatures() {
    return skipVolatileEFeatures;
  }

  public void setSkipVolatileEFeatures(boolean skipVolatileEFeatures) {
    this.skipVolatileEFeatures = skipVolatileEFeatures;
  }

  protected boolean doSkipNotChangeAbleFeatures() {
    return false;
  }

}