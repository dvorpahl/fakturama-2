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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.texo.component.TexoComponent;
import org.eclipse.emf.texo.model.ModelFeatureMapEntry;
import org.eclipse.emf.texo.model.ModelObject;
import org.eclipse.emf.texo.model.ModelResolver;
import org.eclipse.emf.texo.provider.IdProvider;
import org.eclipse.emf.texo.utils.ModelUtils;

/**
 * Compares {@link ModelObject} objects and reports the first encountered difference as a
 * {@link ObjectComparatorException}.
 * 
 * This is just a simple comparison class, for extensive comparison consider converting objects to EMF objects using the
 * {@link ModelEMFConverter} and then use EMF Compare.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 * @see ModelObject
 */
public class ObjectComparator implements TexoComponent {

  private Stack<String> path = new Stack<String>();

  private List<Object> compared = new ArrayList<Object>();

  /**
   * Clears internal datastructure, must be called when doing subsequent compare calls.
   */
  public void clear() {
    path.clear();
    compared.clear();
  }

  /**
   * Compare two objects and throw an {@link ObjectComparatorException} if there are differences.
   */
  public void compare(Object o1, Object o2) {
    if (o1 == o2) {
      return;
    }
    if (compared.contains(o1)) {
      return;
    }
    compared.add(o1);
    if (o1 == null || o2 == null) {
      throw new ObjectComparatorException(o1, o2, "One of the values is null: " + o1 + "/" + o2 + getPath()); //$NON-NLS-1$ //$NON-NLS-2$
    }
    if (ModelResolver.getInstance().isModelEnabled(o1) != ModelResolver.getInstance().isModelEnabled(o2)) {
      throw new ObjectComparatorException(o1, o2, "One of these 2 is not model enabled: " + o1 + "/" + o2 + getPath()); //$NON-NLS-1$ //$NON-NLS-2$
    }
    final ModelObject<?> m1 = ModelResolver.getInstance().getModelObject(o1);
    final ModelObject<?> m2 = ModelResolver.getInstance().getModelObject(o2);

    compareType(m1, m2);
    path.push(m1.eClass().getName());
    for (EStructuralFeature eFeature : m1.eClass().getEAllStructuralFeatures()) {
      if (eFeature.isVolatile()) {
        continue;
      }
      if (FeatureMapUtil.isFeatureMap(eFeature)) {
        compareFeatureMapEFeature(m1, m2, eFeature);
      } else if (ModelUtils.isEMap(eFeature)) {
        compareMapEFeature(m1, m2, eFeature);
      } else if (eFeature.isMany()) {
        compareManyEFeature(m1, m2, eFeature);
      } else if (eFeature instanceof EAttribute) {
        compareSingleEAttribute(m1, m2, (EAttribute) eFeature);
      } else {
        compareSingleEReference(m1, m2, (EReference) eFeature);
      }
    }
    path.pop();
  }

  protected void compareFeatureMapEFeature(ModelObject<?> m1, ModelObject<?> m2, EStructuralFeature eFeature) {
    @SuppressWarnings("unchecked")
    final Collection<Object> targetCollection = (Collection<Object>) m2.eGet(eFeature);
    @SuppressWarnings("unchecked")
    final Collection<Object> sourceCollection = (Collection<Object>) m1.eGet(eFeature);
    path.push(eFeature.getName());
    if (targetCollection.size() != sourceCollection.size()) {
      throw new ObjectComparatorException(m1, m2, "Collections have different sizes " + sourceCollection.size() + "/" //$NON-NLS-1$//$NON-NLS-2$
          + targetCollection.size() + getPath());
    }
    final Iterator<?> targetIterator = targetCollection.iterator();
    int i = 0;
    for (Object sourceEntry : (Collection<?>) m1.eGet(eFeature)) {
      path.push("[" + i + "]"); //$NON-NLS-1$ //$NON-NLS-2$
      final Object targetEntry = targetIterator.next();
      final ModelFeatureMapEntry<?> sourceModelEntry = ModelResolver.getInstance().getModelFeatureMapEntry(eFeature,
          sourceEntry);
      final ModelFeatureMapEntry<?> targetModelEntry = ModelResolver.getInstance().getModelFeatureMapEntry(eFeature,
          targetEntry);
      if (sourceModelEntry.getEStructuralFeature() != targetModelEntry.getEStructuralFeature()) {
        throw new ObjectComparatorException(sourceModelEntry, targetModelEntry,
            "Feature map entries have different efeatures " + sourceModelEntry + "/" //$NON-NLS-1$//$NON-NLS-2$
                + targetModelEntry + getPath());
      }
      final Object v1 = sourceModelEntry.getValue();
      final Object v2 = targetModelEntry.getValue();
      if (sourceModelEntry.getEStructuralFeature() instanceof EReference) {
        compare(v1, v2);
      } else {
        compareValue(v1, v2, (EAttribute) sourceModelEntry.getEStructuralFeature());
      }
      path.pop();
      i++;
    }
    path.pop();
  }

  protected void compareManyEFeature(ModelObject<?> m1, ModelObject<?> m2, EStructuralFeature eFeature) {
    final Object v1 = m1.eGet(eFeature);
    final Object v2 = m2.eGet(eFeature);

    if (v1.getClass() != v2.getClass()) {
      throw new ObjectComparatorException(v1, v2, "Different many efeature value types " + v1 + "/" + v2 + getPath()); //$NON-NLS-1$//$NON-NLS-2$
    }

    boolean compareAsSet = v1 instanceof Set<?>;
    // compare many-to-many also as set
    if (!compareAsSet && eFeature instanceof EReference) {
      final EReference eReference = (EReference) eFeature;
      compareAsSet = eReference.isMany() && eReference.getEOpposite() != null && eReference.getEOpposite().isMany();
    }

    if (ModelUtils.isEMap(eFeature)) {
      compareMapEFeature(m1, m2, eFeature);
    } else if (compareAsSet) {
      compareSetEFeature(m1, m2, eFeature);
    } else if (v2 instanceof List<?>) {
      compareListEFeature(m1, m2, eFeature);
    }
  }

  protected void compareMapEFeature(ModelObject<?> m1, ModelObject<?> m2, EStructuralFeature eFeature) {
    path.push(eFeature.getName());
    final Map<?, ?> c1 = (Map<?, ?>) m1.eGet(eFeature);
    final Map<?, ?> c2 = (Map<?, ?>) m2.eGet(eFeature);
    if (c1.size() != c2.size()) {
      throw new ObjectComparatorException(m1, m2, "Different collection sizes " + c1.size() + "/" + c2.size() //$NON-NLS-1$ //$NON-NLS-2$
          + getPath());
    }
    final EClass mapEClass = ((EReference) eFeature).getEReferenceType();
    final EStructuralFeature keyFeature = mapEClass.getEStructuralFeature("key"); //$NON-NLS-1$

    for (Object key : c1.keySet()) {
      final Object o1 = c1.get(key);
      Object o2 = null;
      if (keyFeature instanceof EReference && IdProvider.getInstance().hasIdEAttribute(key)) {
        final Object id1 = IdProvider.getInstance().getId(key);
        if (id1 != null) {
          for (Object key2 : c2.keySet()) {
            if (IdProvider.getInstance().hasIdEAttribute(key2)) {
              final Object id2 = IdProvider.getInstance().getId(key2);
              if (id2 != null && id1.equals(id2)) {
                o2 = c2.get(key2);
                break;
              }
            }
          }
        }
      }
      if (o2 == null) {
        o2 = c2.get(key);
      }
      path.push("[" + key + "]"); //$NON-NLS-1$//$NON-NLS-2$
      if (eFeature instanceof EReference) {
        compare(o1, o2);
      } else {
        compareValue(o1, o2, (EAttribute) eFeature);
      }
      path.pop();
    }
    path.pop();
  }

  protected void compareSetEFeature(ModelObject<?> m1, ModelObject<?> m2, EStructuralFeature eFeature) {
    path.push(eFeature.getName());
    final Collection<?> c1 = (Collection<?>) m1.eGet(eFeature);
    final Collection<?> c2 = (Collection<?>) m2.eGet(eFeature);
    if (c1.size() != c2.size()) {
      throw new ObjectComparatorException(m1, m2, "Different collection sizes " + c1.size() + "/" + c2.size() //$NON-NLS-1$ //$NON-NLS-2$
          + getPath());
    }
    final Iterator<?> i1 = c1.iterator();
    int cnt = 0;
    while (i1.hasNext()) {
      final Object o1 = i1.next();
      path.push("[" + cnt + "]"); //$NON-NLS-1$ //$NON-NLS-2$
      boolean found = false;
      for (Object o2 : c2) {
        try {
          if (eFeature instanceof EReference) {
            compare(o1, o2);
          } else {
            compareValue(o1, o2, (EAttribute) eFeature);
          }
          found = true;
          break;
        } catch (ObjectComparatorException e) {
          // ignore
        }
      }
      if (!found) {
        throw new ObjectComparatorException(m1, m2, "Value " + o1 + " not present in collection " + getPath()); //$NON-NLS-1$ //$NON-NLS-2$
      }
      path.pop();
      cnt++;
    }
    path.pop();
  }

  protected void compareListEFeature(ModelObject<?> m1, ModelObject<?> m2, EStructuralFeature eFeature) {
    path.push(eFeature.getName());
    final Collection<?> c1 = (Collection<?>) m1.eGet(eFeature);
    final Collection<?> c2 = (Collection<?>) m2.eGet(eFeature);
    if (c1.size() != c2.size()) {
      throw new ObjectComparatorException(m1, m2, "Different collection sizes " + c1.size() + "/" + c2.size() //$NON-NLS-1$ //$NON-NLS-2$
          + getPath());
    }
    final Iterator<?> i1 = c1.iterator();
    final Iterator<?> i2 = c2.iterator();
    int cnt = 0;
    while (i1.hasNext()) {
      final Object o1 = i1.next();
      final Object o2 = i2.next();
      path.push("[" + cnt + "]"); //$NON-NLS-1$ //$NON-NLS-2$
      if (eFeature instanceof EReference) {
        compare(o1, o2);
      } else {
        compareValue(o1, o2, (EAttribute) eFeature);
      }
      path.pop();
      cnt++;
    }
    path.pop();
  }

  protected void compareSingleEAttribute(ModelObject<?> m1, ModelObject<?> m2, EAttribute eAttribute) {
    path.push(eAttribute.getName());
    compareValue(m1.eGet(eAttribute), m2.eGet(eAttribute), eAttribute);
    path.pop();
  }

  protected void compareSingleEReference(ModelObject<?> m1, ModelObject<?> m2, EReference eReference) {
    path.push(eReference.getName());
    try {
      final Object o1 = m1.eGet(eReference);
      final Object o2 = m2.eGet(eReference);
      if (o1 == o2) {
        return;
      }
      if (o1 != null && o2 == null) {
        throw new ObjectComparatorException(o1, o2, "Different Values " + o1 + "/" + o2 + getPath()); //$NON-NLS-1$ //$NON-NLS-2$
      } else if (o1 == null && o2 != null) {
        throw new ObjectComparatorException(o1, o2, "Different Values " + o1 + "/" + o2 + getPath()); //$NON-NLS-1$ //$NON-NLS-2$
      }
      compare(o1, o2);
    } finally {
      path.pop();
    }

  }

  protected void compareValue(Object v1, Object v2, EAttribute eAttribute) {
    if (v1 == v2) {
      return;
    }
    if (v1 != null && v1.getClass().isArray()) {
      checkEqualArrays(v1, v2, eAttribute);
    } else if (v1 != null && !v1.equals(v2)) {
      throw new ObjectComparatorException(v1, v2, "Different values " + v1 + "/" + v2 + getPath()); //$NON-NLS-1$ //$NON-NLS-2$
    }
  }

  private void checkEqualArrays(Object v1, Object v2, EAttribute eAttribute) {
    if (v1 == null || !v1.getClass().isArray() || v2 == null || !v2.getClass().isArray()) {
      throw new ObjectComparatorException(v1, v2, "Different values " + v1 + "/" + v2 + getPath()); //$NON-NLS-1$ //$NON-NLS-2$
    }
    final int l1 = Array.getLength(v1);
    final int l2 = Array.getLength(v1);
    if (l1 != l2) {
      throw new ObjectComparatorException(v1, v2, "Different values " + v1 + "/" + v2 + getPath()); //$NON-NLS-1$ //$NON-NLS-2$
    }
    for (int i = 0; i < l1; i++) {
      final Object e1 = Array.get(v1, i);
      final Object e2 = Array.get(v2, i);
      compareValue(e1, e2, eAttribute);
    }
  }

  protected void compareType(ModelObject<?> m1, ModelObject<?> m2) {
    if (m1.eClass() != m2.eClass()) {
      throw new ObjectComparatorException(m1, m2, "Different EClasses " + m1.eClass().getName() + "/" //$NON-NLS-1$ //$NON-NLS-2$
          + m2.eClass().getName() + getPath());
    }
  }

  protected String getPath() {
    final StringBuilder sb = new StringBuilder(" - path: "); //$NON-NLS-1$
    for (String part : path) {
      sb.append("/" + part); //$NON-NLS-1$
    }
    return sb.toString();
  }

  public class ObjectComparatorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Object o1;
    private Object o2;

    public ObjectComparatorException(Object o1, Object o2, String msg) {
      super(msg);
      this.o1 = o1;
      this.o2 = o2;
    }

    protected Object getO1() {
      return o1;
    }

    protected Object getO2() {
      return o2;
    }
  }
}