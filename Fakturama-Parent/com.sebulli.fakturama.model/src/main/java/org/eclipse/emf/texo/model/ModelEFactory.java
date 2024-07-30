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
 * $Id: ModelEFactory.java,v 1.8 2011/08/25 12:32:37 mtaal Exp $
 */

package org.eclipse.emf.texo.model;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Internal;
import org.eclipse.emf.texo.component.TexoComponent;
import org.eclipse.emf.texo.utils.ModelUtils;

/**
 * Wraps a {@link ModelFactory} to forward the calls to convertToString and createFromString to it.
 * 
 * Other EFactory calls are forwarded to the super class.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 */
public class ModelEFactory implements EFactory, InternalEObject, TexoComponent {

  private EFactoryImpl delegate;
  private boolean isDynamicEFactory = false;
  private ModelFactory modelFactory;

  /**
   * Checks if the objectvalue is an {@link Enumerator} or an {@link EEnumLiteral}, if so calls getLiteral() on these.
   * Otherwise if it is a Dynamic EFactory then the call is delegated to
   * {@link ModelFactory#convertToString(EDataType, Object)}. If a generated EFactory then that one is used.
   * 
   * @param dataType
   *          the EDataType of the objectValue
   * @param objectValue
   *          the object to convert to a String
   * 
   * @see org.eclipse.emf.ecore.impl.EFactoryImpl#convertToString(org.eclipse.emf.ecore.EDataType, java.lang.Object)
   * @see #getXmlHandler()
   */
  public String convertToString(final EDataType dataType, final Object objectValue) {
    if (isDynamicEFactory) {
      if (objectValue instanceof Enumerator) {
        final Enumerator enumerator = (Enumerator) objectValue;
        return enumerator.getLiteral();
      }
      if (objectValue instanceof EEnumLiteral) {
        final EEnumLiteral eEnumLiteral = (EEnumLiteral) objectValue;
        return eEnumLiteral.getLiteral();
      }

      return modelFactory.convertToString(dataType, objectValue);
    }
    return delegate.convertToString(dataType, objectValue);
  }

  /**
   * Call is delegated to {@link ModelFactory#createFromString(EDataType, String)}.
   * 
   * In case of a dynamic EFactory, overrides handling of enums to return the EMF Enumerator instance instead of the
   * generated java type.
   * 
   * @see org.eclipse.emf.ecore.impl.EFactoryImpl#createFromString(org.eclipse.emf.ecore.EDataType, java.lang.String)
   * @see #getXmlHandler()
   */
  public Object createFromString(final EDataType dataType, final String stringValue) {
    // if the efactory is not the default efactory then use it to
    // convert
    if (!isDynamicEFactory) {
      return delegate.createFromString(dataType, stringValue);
    }
    if (dataType instanceof EEnum) {
      if (stringValue == null || stringValue.trim().length() == 0) {
        return null;
      }
      return ((EEnum) dataType).getEEnumLiteralByLiteral(stringValue);
    }

    final EDataType baseType = ModelUtils.getEnumBaseDataTypeIfObject(dataType);
    if (baseType != null && baseType instanceof EEnum) {
      if (stringValue == null || stringValue.trim().length() == 0) {
        return null;
      }
      final EEnum eeNum = (EEnum) baseType;
      EEnumLiteral eeNumLiteral = eeNum.getEEnumLiteralByLiteral(stringValue);
      if (eeNumLiteral == null) {
        eeNumLiteral = eeNum.getEEnumLiteral(stringValue);
      }
      if (eeNumLiteral == null) {
        throw new IllegalArgumentException("The value '" + stringValue //$NON-NLS-1$
            + "' is not a valid enumerator of '" //$NON-NLS-1$
            + dataType.getName() + "'"); //$NON-NLS-1$
      }
      return eeNumLiteral;
    }

    return modelFactory.createFromString(dataType, stringValue);
  }

  /**
   * @return the modelFactory
   */
  public ModelFactory getModelFactory() {
    return modelFactory;
  }

  /**
   * @param modelFactory
   *          the modelFactory to set
   */
  public void setModelFactory(final ModelFactory modelFactory) {
    this.modelFactory = modelFactory;
  }

  /**
   * @return the original EFactory which is being wrapped by this ModelEFactory.
   */
  public EFactory getDelegateEFactory() {
    return delegate;
  }

  /**
   * @param eClass
   * @return
   * @see org.eclipse.emf.ecore.EFactory#create(org.eclipse.emf.ecore.EClass)
   */
  public EObject create(final EClass eClass) {
    // overridden to handle cases where the instanceClass is set, with Texo always
    // the DynamicEObjectImpl should be used and not the instanceClass
    if (isDynamicEFactory) {
      // note copied from EFactoryImpl.basicCreate
      return eClass.getInstanceClassName() == "java.util.Map$Entry" ? new DynamicEObjectImpl.BasicEMapEntry<String, String>( //$NON-NLS-1$
          eClass)
          : new DynamicEObjectImpl(eClass);
    }
    return delegate.create(eClass);
  }

  public EList<Adapter> eAdapters() {
    return delegate.eAdapters();
  }

  public boolean eDeliver() {
    return delegate.eDeliver();
  }

  public EList<EAnnotation> getEAnnotations() {
    return delegate.getEAnnotations();
  }

  public void eSetDeliver(boolean deliver) {
    delegate.eSetDeliver(deliver);
  }

  public EPackage getEPackage() {
    return delegate.getEPackage();
  }

  public void eNotify(Notification notification) {
    delegate.eNotify(notification);
  }

  public EAnnotation getEAnnotation(String source) {
    return delegate.getEAnnotation(source);
  }

  public void setEPackage(EPackage value) {
    delegate = (EFactoryImpl) value.getEFactoryInstance();

    // if the efactory is not the default efactory then it will be used
    // to convert. Note that this comparison can go wrong if the
    // class of the delegate is loaded through a different classloader
    // than the EFactoryImpl class. But this is very unlikely (other
    // problems would then occur.
    isDynamicEFactory = delegate.getClass() == EFactoryImpl.class;
  }

  public EClass eClass() {
    return delegate.eClass();
  }

  public Resource eResource() {
    return delegate.eResource();
  }

  public EObject eContainer() {
    return delegate.eContainer();
  }

  public EStructuralFeature eContainingFeature() {
    return delegate.eContainingFeature();
  }

  public EReference eContainmentFeature() {
    return delegate.eContainmentFeature();
  }

  public EList<EObject> eContents() {
    return delegate.eContents();
  }

  public TreeIterator<EObject> eAllContents() {
    return delegate.eAllContents();
  }

  public boolean eIsProxy() {
    return delegate.eIsProxy();
  }

  public EList<EObject> eCrossReferences() {
    return delegate.eCrossReferences();
  }

  public Object eGet(EStructuralFeature feature) {
    return delegate.eGet(feature);
  }

  public Object eGet(EStructuralFeature feature, boolean resolve) {
    return delegate.eGet(feature, resolve);
  }

  public void eSet(EStructuralFeature feature, Object newValue) {
    delegate.eSet(feature, newValue);
  }

  public boolean eIsSet(EStructuralFeature feature) {
    return delegate.eIsSet(feature);
  }

  public void eUnset(EStructuralFeature feature) {
    delegate.eUnset(feature);
  }

  public Object eInvoke(EOperation operation, EList<?> arguments) {
    try {
      // ugly method to make the code backward compatible with 3.5!
      // won't be called anyway in 3.5
      final Method method = delegate.getClass().getMethod("invoke", EOperation.class, EList.class); //$NON-NLS-1$
      return method.invoke(delegate, operation, arguments);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return delegate.equals(obj);
  }

  public NotificationChain basicSetEPackage(EPackage newEPackage, NotificationChain msgs) {
    return delegate.basicSetEPackage(newEPackage, msgs);
  }

  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    return delegate.eGet(featureID, resolve, coreType);
  }

  public InternalEObject eInternalContainer() {
    return delegate.eInternalContainer();
  }

  public void eSet(int featureID, Object newValue) {
    delegate.eSet(featureID, newValue);
  }

  public void eUnset(int featureID) {
    delegate.eUnset(featureID);
  }

  public boolean eIsSet(int featureID) {
    return delegate.eIsSet(featureID);
  }

  public Object eInvoke(int operationID, EList<?> arguments) {
    try {
      // ugly method to make the code backward compatible with 3.5!
      // won't be called anyway in 3.5
      final Method method = delegate.getClass().getMethod("eInvoke", int.class, EList.class); //$NON-NLS-1$
      return method.invoke(delegate, operationID, arguments);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  public String eURIFragmentSegment(EStructuralFeature eStructuralFeature, EObject eObject) {
    return delegate.eURIFragmentSegment(eStructuralFeature, eObject);
  }

  public boolean eNotificationRequired() {
    return delegate.eNotificationRequired();
  }

  public EObject eObjectForURIFragmentSegment(String uriFragmentSegment) {
    return delegate.eObjectForURIFragmentSegment(uriFragmentSegment);
  }

  public URI eProxyURI() {
    return delegate.eProxyURI();
  }

  public void eSetProxyURI(URI uri) {
    delegate.eSetProxyURI(uri);
  }

  public int eContainerFeatureID() {
    return delegate.eContainerFeatureID();
  }

  public void eSetClass(EClass eClass) {
    delegate.eSetClass(eClass);
  }

  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    return delegate.eInverseAdd(otherEnd, featureID, msgs);
  }

  public Internal eDirectResource() {
    return delegate.eDirectResource();
  }

  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    return delegate.eInverseRemove(otherEnd, featureID, msgs);
  }

  public Object dynamicGet(int dynamicFeatureID) {
    return delegate.dynamicGet(dynamicFeatureID);
  }

  public void dynamicSet(int dynamicFeatureID, Object newValue) {
    delegate.dynamicSet(dynamicFeatureID, newValue);
  }

  public void dynamicUnset(int dynamicFeatureID) {
    delegate.dynamicUnset(dynamicFeatureID);
  }

  public boolean eContains(EObject eObject) {
    return delegate.eContains(eObject);
  }

  public Internal eInternalResource() {
    return delegate.eInternalResource();
  }

  public NotificationChain eSetResource(Internal resource, NotificationChain notifications) {
    return delegate.eSetResource(resource, notifications);
  }

  public Object eGet(EStructuralFeature eFeature, boolean resolve, boolean coreType) {
    return delegate.eGet(eFeature, resolve, coreType);
  }

  public Object eDynamicGet(EStructuralFeature eFeature, boolean resolve) {
    return delegate.eDynamicGet(eFeature, resolve);
  }

  public Object eDynamicGet(int featureID, boolean resolve, boolean coreType) {
    return delegate.eDynamicGet(featureID, resolve, coreType);
  }

  public Object eOpenGet(EStructuralFeature eFeature, boolean resolve) {
    return delegate.eOpenGet(eFeature, resolve);
  }

  public void eDynamicSet(EStructuralFeature eFeature, Object newValue) {
    delegate.eDynamicSet(eFeature, newValue);
  }

  public void eDynamicSet(int featureID, Object newValue) {
    delegate.eDynamicSet(featureID, newValue);
  }

  public void eOpenSet(EStructuralFeature eFeature, Object newValue) {
    delegate.eOpenSet(eFeature, newValue);
  }

  public void eDynamicUnset(EStructuralFeature eFeature) {
    delegate.eDynamicUnset(eFeature);
  }

  public void eDynamicUnset(int featureID) {
    delegate.eDynamicUnset(featureID);
  }

  public void eOpenUnset(EStructuralFeature eFeature) {
    delegate.eOpenUnset(eFeature);
  }

  public boolean eDynamicIsSet(EStructuralFeature eFeature) {
    return delegate.eDynamicIsSet(eFeature);
  }

  public boolean eDynamicIsSet(int featureID) {
    return delegate.eDynamicIsSet(featureID);
  }

  public boolean eOpenIsSet(EStructuralFeature eFeature) {
    return delegate.eOpenIsSet(eFeature);
  }

  public NotificationChain eBasicSetContainer(InternalEObject newContainer, int newContainerFeatureID,
      NotificationChain msgs) {
    return delegate.eBasicSetContainer(newContainer, newContainerFeatureID, msgs);
  }

  public NotificationChain eBasicRemoveFromContainer(NotificationChain msgs) {
    return delegate.eBasicRemoveFromContainer(msgs);
  }

  public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
    return delegate.eBasicRemoveFromContainerFeature(msgs);
  }

  public NotificationChain eDynamicBasicRemoveFromContainer(NotificationChain msgs) {
    return delegate.eDynamicBasicRemoveFromContainer(msgs);
  }

  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class<?> baseClass,
      NotificationChain msgs) {
    return delegate.eInverseAdd(otherEnd, featureID, baseClass, msgs);
  }

  public NotificationChain eDynamicInverseAdd(InternalEObject otherEnd, int featureID, Class<?> inverseClass,
      NotificationChain msgs) {
    return delegate.eDynamicInverseAdd(otherEnd, featureID, inverseClass, msgs);
  }

  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class<?> baseClass,
      NotificationChain msgs) {
    return delegate.eInverseRemove(otherEnd, featureID, baseClass, msgs);
  }

  public NotificationChain eDynamicInverseRemove(InternalEObject otherEnd, int featureID, Class<?> inverseClass,
      NotificationChain msgs) {
    return delegate.eDynamicInverseRemove(otherEnd, featureID, inverseClass, msgs);
  }

  public EObject eResolveProxy(InternalEObject proxy) {
    return delegate.eResolveProxy(proxy);
  }

  public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
    return delegate.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
  }

  public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
    return delegate.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
  }

  public int eDerivedStructuralFeatureID(EStructuralFeature eStructuralFeature) {
    return delegate.eDerivedStructuralFeatureID(eStructuralFeature);
  }

  public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
    try {
      // ugly method to make the code backward compatible with 3.5!
      // won't be called anyway in 3.5
      final Method method = delegate.getClass().getMethod("eDerivedOperationID", int.class, Class.class); //$NON-NLS-1$
      return ((Integer) method.invoke(delegate, baseOperationID, baseClass)).intValue();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  public int eDerivedOperationID(EOperation eOperation) {
    try {
      // ugly method to make the code backward compatible with 3.5!
      // won't be called anyway in 3.5
      final Method method = delegate.getClass().getMethod("eDerivedOperationID", EOperation.class); //$NON-NLS-1$
      return ((Integer) method.invoke(delegate, eOperation)).intValue();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  public Setting eSetting(EStructuralFeature eFeature) {
    return delegate.eSetting(eFeature);
  }

  public EStore eStore() {
    return delegate.eStore();
  }

  public void eSetStore(EStore store) {
    delegate.eSetStore(store);
  }

  public Object eVirtualGet(int eDerivedStructuralFeatureID) {
    return delegate.eVirtualGet(eDerivedStructuralFeatureID);
  }

  public Object eVirtualGet(int eDerivedStructuralFeatureID, Object defaultValue) {
    return delegate.eVirtualGet(eDerivedStructuralFeatureID, defaultValue);
  }

  public boolean eVirtualIsSet(int eDerivedStructuralFeatureID) {
    return delegate.eVirtualIsSet(eDerivedStructuralFeatureID);
  }

  public Object eVirtualSet(int eDerivedStructuralFeatureID, Object value) {
    return delegate.eVirtualSet(eDerivedStructuralFeatureID, value);
  }

  public Object eVirtualUnset(int eDerivedStructuralFeatureID) {
    return delegate.eVirtualUnset(eDerivedStructuralFeatureID);
  }

  public Object eDynamicInvoke(int operationID, EList<?> arguments) {
    try {
      // ugly method to make the code backward compatible with 3.5!
      // won't be called anyway in 3.5
      final Method method = delegate.getClass().getMethod("eDynamicInvoke", int.class, EList.class); //$NON-NLS-1$
      return method.invoke(delegate, operationID, arguments);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public String toString() {
    return delegate.toString();
  }

  /**
   * Uses the {@link Proxy} concept to encapsulate the {@link ModelEFactory} within the correct class.
   */
  public static class EFactoryInvocationHandler implements InvocationHandler {

    private final ModelEFactory modelEFactory;

    public EFactoryInvocationHandler(ModelEFactory modelEFactory) {
      this.modelEFactory = modelEFactory;
    }

    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
      return method.invoke(modelEFactory, args);
    }
  }
}