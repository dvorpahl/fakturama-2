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
 * $Id: EMFModelConverter.java,v 1.23 2011/08/29 05:16:04 mtaal Exp $
 */

package org.eclipse.emf.texo.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.texo.component.ComponentProvider;
import org.eclipse.emf.texo.component.TexoComponent;
import org.eclipse.emf.texo.model.DynamicModelObject;
import org.eclipse.emf.texo.model.ModelFeatureMapEntry;
import org.eclipse.emf.texo.model.ModelObject;
import org.eclipse.emf.texo.model.ModelPackage;
import org.eclipse.emf.texo.model.ModelResolver;
import org.eclipse.emf.texo.utils.ModelUtils;

/**
 * Capable of copying objects and their children and references.
 * 
 * This object can be used for subsequent copy actions, internal data structures can/should be cleaned using the clear
 * method in between copy actions.
 * 
 * As a default referenced objects are not copied, use the {@link #setCopyChildren(boolean)} or
 * {@link #setCopyReferences(boolean)} methods to enable copying of contained and not contained objects.
 * 
 * If referenced objects are also copied then these referenced objects are at most copied once, so if multiple objects
 * refer to another object (and that other object is also copied) then that other object is only copied once.
 * 
 * The complete set of copied objects (including copied referenced objects) can be obtained through the
 * {@link #getAllCopiedObjects()} method.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 * @see ModelObject
 */
public class ObjectCopier implements TexoComponent {

  private Map<Object, Object> sourceTargetMap = new HashMap<Object, Object>();

  private boolean copyChildren = false;
  private boolean copyReferences = false;

  /**
   * Copy all objects in the sourceList,
   * 
   * @param sourceList
   * @return
   */
  public List<Object> copyAll(List<Object> sourceList) {
    final List<Object> result = new ArrayList<Object>();
    for (Object source : sourceList) {
      result.add(copy(source));
    }
    return result;
  }

  public Object copy(Object source) {
    if (source == null) {
      return null;
    }
    if (sourceTargetMap.containsKey(source)) {
      return sourceTargetMap.get(source);
    }

    if (source instanceof DynamicModelObject) {
      final DynamicModelObject target = ComponentProvider.getInstance().newInstance(DynamicModelObject.class);
      target.setData((DynamicModelObject) source);
      sourceTargetMap.put(source, target);
      return target;
    }

    @SuppressWarnings("unchecked")
    final ModelObject<Object> sourceModelObject = (ModelObject<Object>) ModelResolver.getInstance().getModelObject(
        source);
    final ModelPackage modelPackage = ModelResolver.getInstance().getModelPackage(
        sourceModelObject.eClass().getEPackage().getNsURI());
    final Object target = modelPackage.getModelFactory().create(sourceModelObject.eClass());
    @SuppressWarnings("unchecked")
    final ModelObject<Object> targetModelObject = (ModelObject<Object>) ModelResolver.getInstance().getModelObject(
        target);

    sourceTargetMap.put(source, target);

    for (EStructuralFeature eFeature : sourceModelObject.eClass().getEAllStructuralFeatures()) {
      if (eFeature.isVolatile()) {
        continue;
      }
      if (FeatureMapUtil.isFeatureMap(eFeature)) {
        copyFeatureMap(sourceModelObject, targetModelObject, eFeature);
      } else if (ModelUtils.isEMap(eFeature)) {
        copyMap(sourceModelObject, targetModelObject, (EReference) eFeature);
      } else if (eFeature.isMany()) {
        if (eFeature instanceof EAttribute) {
          copyManyEAttribute(sourceModelObject, targetModelObject, (EAttribute) eFeature);
        } else {
          copyManyEReference(sourceModelObject, targetModelObject, (EReference) eFeature);
        }
      } else if (eFeature instanceof EAttribute) {
        copySingleEAttribute(sourceModelObject, targetModelObject, (EAttribute) eFeature);
      } else {
        copySingleEReference(sourceModelObject, targetModelObject, (EReference) eFeature);
      }
    }
    return target;
  }

  protected void copyManyEAttribute(ModelObject<Object> source, ModelObject<Object> target, EAttribute eAttribute) {
    for (Object value : (Collection<?>) source.eGet(eAttribute)) {
      target.eAddTo(eAttribute, value);
    }
  }

  protected void copySingleEAttribute(ModelObject<Object> source, ModelObject<Object> target, EAttribute eAttribute) {
    target.eSet(eAttribute, source.eGet(eAttribute));
  }

  protected void copyManyEReference(ModelObject<Object> source, ModelObject<Object> target, EReference eReference) {
    for (Object value : (Collection<?>) source.eGet(eReference)) {
      if (eReference.isContainment() && copyChildren) {
        target.eAddTo(eReference, copy(value));
      } else if (!eReference.isContainment() && copyReferences) {
        target.eAddTo(eReference, copy(value));
      } else {
        target.eAddTo(eReference, value);
      }
    }
  }

  protected void copySingleEReference(ModelObject<Object> source, ModelObject<Object> target, EReference eReference) {
    final Object value = source.eGet(eReference);
    if (eReference.isContainment() && copyChildren) {
      target.eSet(eReference, copy(value));
    } else if (!eReference.isContainment() && copyReferences) {
      target.eSet(eReference, copy(value));
    } else {
      target.eSet(eReference, value);
    }
  }

  protected void copyMap(ModelObject<Object> source, ModelObject<Object> target, EReference eReference) {
    final Map<?, ?> sourceMap = (Map<?, ?>) source.eGet(eReference);
    @SuppressWarnings("unchecked")
    final Map<Object, Object> targetMap = (Map<Object, Object>) target.eGet(eReference);
    final EClass mapEClass = eReference.getEReferenceType();
    final EStructuralFeature valueFeature = mapEClass.getEStructuralFeature("value"); //$NON-NLS-1$
    final EStructuralFeature keyFeature = mapEClass.getEStructuralFeature("key"); //$NON-NLS-1$
    final boolean copyKey = keyFeature instanceof EReference && copyReference((EReference) keyFeature);
    final boolean copyValue = valueFeature instanceof EReference && copyReference((EReference) valueFeature);

    for (Object key : sourceMap.keySet()) {
      Object value = sourceMap.get(key);
      if (copyKey) {
        key = copy(key);
      }
      if (copyValue) {
        value = copy(value);
      }
      targetMap.put(key, value);
    }
  }

  private boolean copyReference(EReference eReference) {
    return copyChildren && eReference.isContainment() || copyReferences && !eReference.isContainment();
  }

  protected void copyFeatureMap(ModelObject<Object> source, ModelObject<Object> target, EStructuralFeature eFeature) {
    @SuppressWarnings("unchecked")
    final Collection<Object> targetCollection = (Collection<Object>) target.eGet(eFeature);
    for (Object sourceEntry : (Collection<?>) source.eGet(eFeature)) {
      final ModelFeatureMapEntry<?> sourceModelEntry = ModelResolver.getInstance().getModelFeatureMapEntry(eFeature,
          sourceEntry);
      final Object targetEntry = ModelResolver.getInstance().createFeatureMapEntry(eFeature);
      final ModelFeatureMapEntry<?> targetModelEntry = ModelResolver.getInstance().getModelFeatureMapEntry(eFeature,
          targetEntry);
      final EStructuralFeature eFMFeature = sourceModelEntry.getEStructuralFeature();
      Object value = sourceModelEntry.getValue();
      if (eFMFeature instanceof EReference && copyReference((EReference) eFMFeature)) {
        value = copy(value);
      }
      targetModelEntry.setEStructuralFeature(eFMFeature);
      targetModelEntry.setValue(value);
      targetCollection.add(targetEntry);
    }
  }

  public void clear() {
    sourceTargetMap.clear();
  }

  public Collection<Object> getAllCopiedObjects() {
    return sourceTargetMap.values();
  }

  public Map<Object, Object> getSourceTargetMap() {
    return sourceTargetMap;
  }

  public void setSourceTargetMap(Map<Object, Object> sourceTargetMap) {
    this.sourceTargetMap = sourceTargetMap;
  }

  /**
   * If true then also children (referenced using an {@link EReference} with {@link EReference#isContainment()} true)
   * are also copied.
   */
  public boolean isCopyChildren() {
    return copyChildren;
  }

  public void setCopyChildren(boolean copyChildren) {
    this.copyChildren = copyChildren;
  }

  /**
   * If true then also non-containment references (referenced using an {@link EReference} with
   * {@link EReference#isContainment()} false) are also copied.
   */
  public boolean isCopyReferences() {
    return copyReferences;
  }

  public void setCopyReferences(boolean copyReferences) {
    this.copyReferences = copyReferences;
  }

}