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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.texo.component.ComponentProvider;
import org.eclipse.emf.texo.component.TexoComponent;
import org.eclipse.emf.texo.model.ModelFeatureMapEntry;
import org.eclipse.emf.texo.model.ModelObject;
import org.eclipse.emf.texo.model.ModelPackage;
import org.eclipse.emf.texo.model.ModelResolver;
import org.eclipse.emf.texo.resolver.DefaultObjectResolver;
import org.eclipse.emf.texo.resolver.ObjectResolver;
import org.eclipse.emf.texo.utils.Check;
import org.eclipse.emf.texo.utils.ModelUtils;

/**
 * Converts a set of {@link EObject} instances to objects managed by {@link ModelPackage} instances.
 * 
 * Uses an internal map during the conversion process to map already converted EObjects to ModelObjects.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 * @see ModelObject
 * @see DynamicEObjectImpl
 */
public class EMFModelConverter implements TexoComponent {

  private Map<EObject, Object> objectMapping = new HashMap<EObject, Object>();

  private ObjectResolver objectResolver = ComponentProvider.getInstance().newInstance(DefaultObjectResolver.class);

  private final List<EObject> toConvert = new ArrayList<EObject>();

  // keeps track of all objects which have been converted
  private final List<EObject> allConvertedEObjects = new ArrayList<EObject>();

  // keeps track of all many-to-many associations, for these the order of
  // elements has to be repaired
  private List<ManyToMany> toRepairManyToMany = new ArrayList<ManyToMany>();

  /**
   * Converts a set of EObjects and all the objects they reference to a a collection of model managed objects.
   * 
   * @param eObjects
   *          the eObjects to convert, also the references/children are converted
   * @return the created model objects
   */
  public List<Object> convert(final List<EObject> eObjects) {
    // the process creates the new target objects and then converts the content
    // this multi-step process prevents stack overflow with large object graphs
    final List<Object> result = new ArrayList<Object>();
    for (final EObject eObject : eObjects) {
      result.add(createTarget(eObject));
    }
    while (!toConvert.isEmpty()) {
      final ArrayList<EObject> beingConverted = new ArrayList<EObject>(toConvert);
      allConvertedEObjects.addAll(beingConverted);
      toConvert.clear();
      for (EObject eObject : beingConverted) {
        convertContent(eObject);
      }
    }

    // now repair the ManyToMany
    for (ManyToMany mtm : toRepairManyToMany) {
      mtm.repair();
    }
    toRepairManyToMany.clear();

    return result;
  }

  /**
   * Converts a single EObject. Internally a map from EObject to Object is kept so that an instance of an EObject is
   * only converted once and the same Object is returned.
   * 
   * If the EObject is a proxy then it is read from the {@link #getUriResolver()}.
   * 
   * @param eObject
   *          the object to convert
   * @return the created model managed object
   */
  protected Object createTarget(final EObject eObject) {
    Object target = objectMapping.get(eObject);
    if (target != null) {
      return target;
    }
    target = objectResolver.resolveFromEObject(eObject);
    if (!eObject.eIsProxy()) {
      toConvert.add(eObject);
    }
    objectMapping.put(eObject, target);
    return target;
  }

  protected void convertContent(EObject eObject) {
    final Object target = objectMapping.get(eObject);

    final ModelObject<?> modelObject = ModelResolver.getInstance().getModelObject(target);
    for (final EStructuralFeature eStructuralFeature : modelObject.eClass().getEAllStructuralFeatures()) {
      if (!eStructuralFeature.isChangeable() || eStructuralFeature.isVolatile()) {
        continue;
      }

      // not set, not convert
      if (!eStructuralFeature.isMany() && !eObject.eIsSet(eStructuralFeature)) {
        continue;
      }

      if (FeatureMapUtil.isFeatureMap(eStructuralFeature)) {
        convertFeatureMap(eObject, modelObject, eStructuralFeature);
      } else if (eStructuralFeature.isMany()) {
        if (eStructuralFeature instanceof EAttribute) {
          final EAttribute eAttribute = (EAttribute) eStructuralFeature;
          convertManyEAttribute(eObject, modelObject, eAttribute);
        } else {
          final EReference eReference = (EReference) eStructuralFeature;
          if (eReference.isContainer()) {
            continue;
          }
          convertManyEReference(eObject, modelObject, eReference);
        }
      } else {
        if (eStructuralFeature instanceof EAttribute) {
          final EAttribute eAttribute = (EAttribute) eStructuralFeature;
          convertSingleEAttribute(eObject, modelObject, eAttribute);
        } else {
          final EReference eReference = (EReference) eStructuralFeature;
          if (eReference.isContainer()) {
            continue;
          }
          convertSingleEReference(eObject, modelObject, eReference);
        }
      }
    }
  }

  /**
   * Converts the value of an EReference with isMany==false, the value is converted to a Object and set in the correct
   * feature in the model managed object.
   * 
   * @param eObject
   *          the eObject from which the value is read
   * @param modelObject
   *          the Object in which the value is to be set
   * @param eReference
   *          the eReference which is converted
   */
  protected void convertSingleEReference(final EObject eObject, final ModelObject<?> modelObject,
      final EReference eReference) {
    // bidirectional one-to-many are always set from the many side to preserve the order
    final EObject value = (EObject) eObject.eGet(eReference);
    if (!eReference.isMany() && value != null && eReference.getEOpposite() != null
        && eReference.getEOpposite().isMany()) {
      return;
    }
    if (value == null) {
      modelObject.eSet(eReference, null);
    } else {
      modelObject.eSet(eReference, createTarget(value));
    }
  }

  /**
   * Converts the value of an EReference with isMany==true, the values of the collection are converted to Objects and
   * added to the list in the correct feature in the {@link ModelObject}.
   * 
   * @param eObject
   *          the eObject from which the value is read
   * @param modelObject
   *          the Object in which the value is set
   * @param eReference
   *          the eReference which is converted
   */
  protected void convertManyEReference(final EObject eObject, final ModelObject<?> modelObject,
      final EReference eReference) {
    @SuppressWarnings("unchecked")
    final Collection<EObject> eValues = (Collection<EObject>) eObject.eGet(eReference);
    if (ModelUtils.isEMap(eReference)) {
      @SuppressWarnings("unchecked")
      final Map<Object, Object> mValues = (Map<Object, Object>) modelObject.eGet(eReference);

      // clear as there can be current values if the target is read from the db
      mValues.clear();

      for (final Object eValue : eValues) {
        @SuppressWarnings("unchecked")
        final Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) eValue;
        // key and value can also be an EObject
        final Object key;
        if (entry.getKey() instanceof EObject) {
          key = createTarget((EObject) entry.getKey());
        } else {
          key = entry.getKey();
        }
        final Object value;
        if (entry.getValue() instanceof EObject) {
          value = createTarget((EObject) entry.getValue());
        } else {
          value = entry.getValue();
        }

        mValues.put(key, value);
      }
    } else {

      // a many to many
      if (eReference.getEOpposite() != null && eReference.getEOpposite().isMany()) {
        final ManyToMany mtm = new ManyToMany();
        mtm.setOwner(eObject);
        mtm.setEReference(eReference);
        toRepairManyToMany.add(mtm);
      }

      final Collection<?> mValues = (Collection<?>) modelObject.eGet(eReference);

      // make a copy
      final List<Object> copiedMValues = new ArrayList<Object>(mValues);

      // clear as there can be current values if the target is read from the db
      // use forloop as the collection can be unmodifiable
      for (Object o : new ArrayList<Object>(mValues)) {
        modelObject.eRemoveFrom(eReference, o);
      }

      // check that the eopposite is indeed cleared, this is not the case if
      // there is no bi-directional code generated
      final EReference eOpposite = eReference.getEOpposite();
      if (eOpposite != null && !eOpposite.isMany()) {
        for (Object mValue : copiedMValues) {
          final ModelObject<?> modelMValue = ModelResolver.getInstance().getModelObject(mValue);
          modelMValue.eSet(eOpposite, null);
        }
      }

      for (final EObject eValue : eValues) {
        final Object target = createTarget(eValue);
        // first add to the many reference
        modelObject.eAddTo(eReference, target);

        // add to the other side, this is needed because the bi-directional
        // api is not always generated
        if (eOpposite != null && !eOpposite.isMany()) {
          final ModelObject<?> modelObjectTarget = ModelResolver.getInstance().getModelObject(target);
          modelObjectTarget.eSet(eReference.getEOpposite(), modelObject.getTarget());
        }
      }
    }
  }

  /**
   * Converts the value of an EAttribute with isMany==false, the value is converted (
   * {@link #convertEAttributeValue(Object, EDataType)}) and set in the correct feature in the {@link ModelObject}.
   * 
   * @param eObject
   *          the eObject from which the value is read
   * @param modelObject
   *          the @[link ModelObject} in which the value is to be set
   * @param eAttribute
   *          the EAttribute which is converted
   * @see #convertEAttributeValue(Object, EDataType)
   */
  protected void convertSingleEAttribute(final EObject eObject, final ModelObject<?> modelObject,
      final EAttribute eAttribute) {
    if (!eObject.eIsSet(eAttribute)) {
      return;
    }
    final Object eValue = eObject.eGet(eAttribute);
    modelObject.eSet(eAttribute, convertEAttributeValue(eValue, eAttribute.getEAttributeType()));
  }

  /**
   * Converts the values of an FeatureMap, the values of the collection are converted to and added to the list in the
   * correct feature in the modelObject.
   * 
   * @param eObject
   *          the eObject from which the value is read
   * @param modelObject
   *          the {@link ModelObject} in which the value is to be set
   * @param eFeature
   *          the eFeature which is converted
   */
  protected void convertFeatureMap(final EObject eObject, final ModelObject<?> modelObject,
      final EStructuralFeature eFeature) {
    final Collection<?> eValues = (Collection<?>) eObject.eGet(eFeature);

    @SuppressWarnings("unchecked")
    final Collection<Object> values = (Collection<Object>) modelObject.eGet(eFeature);

    // clear as the object may have been read from the db
    values.clear();

    for (final Object eValue : eValues) {
      final FeatureMap.Entry eEntry = (FeatureMap.Entry) eValue;

      final Object featureMapEntry = ModelResolver.getInstance().createFeatureMapEntry(eFeature);
      final ModelFeatureMapEntry<?> mEntry = ModelResolver.getInstance().getModelFeatureMapEntry(eFeature,
          featureMapEntry);
      mEntry.setEStructuralFeature(eEntry.getEStructuralFeature());

      final Object convertedValue;
      if (mEntry.getEStructuralFeature() instanceof EAttribute) {
        convertedValue = convertEAttributeValue(eEntry.getValue(),
            ((EAttribute) eEntry.getEStructuralFeature()).getEAttributeType());
      } else {
        convertedValue = createTarget((EObject) eEntry.getValue());
      }

      mEntry.setValue(convertedValue);
      values.add(featureMapEntry);
    }
  }

  /**
   * Converts the value of an EAttribute with isMany==true, the values of the collection are converted to and added to
   * the list in the correct feature in the eObject.
   * 
   * @param eObject
   *          the eObject from which the value is read
   * @param modelObject
   *          the {@link ModelObject} in which the value is to be set
   * @param eAttribute
   *          the EAttribute which is converted
   * @see #convertEAttributeValue(Object, EDataType)
   */
  protected void convertManyEAttribute(final EObject eObject, final ModelObject<?> modelObject,
      final EAttribute eAttribute) {
    final Collection<?> eValues = (Collection<?>) eObject.eGet(eAttribute);
    final EDataType eDataType = eAttribute.getEAttributeType();

    @SuppressWarnings("unchecked")
    final Collection<Object> values = (Collection<Object>) modelObject.eGet(eAttribute);
    // clear as the target may have been read from the db
    values.clear();

    for (final Object eValue : eValues) {
      values.add(convertEAttributeValue(eValue, eDataType));
    }
  }

  /**
   * Converts a primitive type value, this implementation only converts an EEnum to an Enum value.
   * 
   * @param value
   *          the value to convert
   * @param eDataType
   *          its EDataType
   * @return the converted value
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected Object convertEAttributeValue(final Object value, final EDataType eDataType) {
    if (value instanceof Enum<?>) {
      final EDataType enumDataType = getDataTypeOrBaseType(eDataType);
      Check.isInstanceOf(enumDataType, EEnum.class);
      final ModelPackage modelPackage = ModelResolver.getInstance()
          .getModelPackage(enumDataType.getEPackage().getNsURI());
      final Class<? extends Enum> enumClass = (Class<? extends Enum>) modelPackage.getEClassifierClass(enumDataType);
      return Enum.valueOf(enumClass, ((Enum<?>) value).name().toUpperCase(Locale.ENGLISH));
    } else if (value instanceof EEnumLiteral) {
      final EDataType enumDataType = getDataTypeOrBaseType(eDataType);
      Check.isInstanceOf(enumDataType, EEnum.class);
      final EEnumLiteral eeNumLiteral = (EEnumLiteral) value;
      final ModelPackage modelPackage = ModelResolver.getInstance()
          .getModelPackage(enumDataType.getEPackage().getNsURI());
      if (modelPackage == null) {
        // dynamic model
        return eeNumLiteral;
      }
      final Class<? extends Enum> enumClass = (Class<? extends Enum>) modelPackage.getEClassifierClass(enumDataType);
      return Enum.valueOf(enumClass, eeNumLiteral.getName().toUpperCase(Locale.ENGLISH));
    }

    // convert these to a Date always
    if (value instanceof XMLGregorianCalendar) {
      final XMLGregorianCalendar xmlCalendar = (XMLGregorianCalendar) value;
      final Date date = xmlCalendar.toGregorianCalendar().getTime();
      return date;
    }

    return value;
  }

  /**
   * See the javadoc in the {@link ModelUtils#getEnumBaseDataTypeIfObject(EDataType)} for details.
   * 
   * @param eDataType
   * @return the passed EDataType or its base type if the base type is an EEnum
   */
  private EDataType getDataTypeOrBaseType(EDataType eDataType) {
    final EDataType baseType = ModelUtils.getEnumBaseDataTypeIfObject(eDataType);
    if (baseType != null) {
      return baseType;
    }
    return eDataType;
  }

  public ObjectResolver getUriResolver() {
    return objectResolver;
  }

  public void setUriResolver(ObjectResolver uriResolver) {
    objectResolver = uriResolver;
  }

  public List<EObject> getAllConvertedEObjects() {
    return allConvertedEObjects;
  }

  public Collection<Object> getAllConvertedObjects() {
    return objectMapping.values();
  }

  /**
   * Class is used to keep track of all the many to many for which the order has to be repaired.
   * 
   * @author mtaal
   */
  private class ManyToMany {
    private EObject owner;
    private EReference eReference;

    public void repair() {
      final Object target = objectMapping.get(owner);
      final ModelObject<?> modelObject = ModelResolver.getInstance().getModelObject(target);
      final Object listObject = modelObject.eGet(eReference);
      if (!(listObject instanceof List<?>)) {
        // no order maintained anyway
        return;
      }
      final EList<?> eList = (EList<?>) owner.eGet(eReference);
      @SuppressWarnings("unchecked")
      final List<Object> list = (List<Object>) listObject;
      int correctIndex = 0;
      for (Object objectElement : eList) {
        final EObject eObjectElement = (EObject) objectElement;
        final Object element = objectMapping.get(eObjectElement);
        final int newIndex = list.indexOf(element);
        if (newIndex != correctIndex) {
          try {
            list.remove(element);
            list.add(correctIndex, element);
          } catch (UnsupportedOperationException ignore) {
            // unmodifiable collection, can't handle those, go away
            return;
          }
        }
        correctIndex++;
      }
    }

    public void setOwner(EObject owner) {
      this.owner = owner;
    }

    public void setEReference(EReference eReference) {
      this.eReference = eReference;
    }

  }
}