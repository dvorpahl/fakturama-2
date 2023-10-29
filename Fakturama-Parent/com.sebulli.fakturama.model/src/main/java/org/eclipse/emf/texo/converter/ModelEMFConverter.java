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
 * $Id: ModelEMFConverter.java,v 1.27 2011/09/14 15:35:53 mtaal Exp $
 */

package org.eclipse.emf.texo.converter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.ecore.xml.type.internal.XMLCalendar;
import org.eclipse.emf.texo.model.ModelFeatureMapEntry;
import org.eclipse.emf.texo.model.ModelObject;
import org.eclipse.emf.texo.model.ModelResolver;
import org.eclipse.emf.texo.utils.Check;
import org.eclipse.emf.texo.utils.ModelUtils;

/**
 * Converts a set of model objects to a set of DynamicEObjectImpl instances.
 * 
 * Internally a map from {@link ModelObject} to {@link EObject} is maintained so that an object is at most converted
 * once.
 * 
 * Note: keeps state, to prevent side effects always create new converters for new conversion actions.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 * @see ModelObject
 * @see DynamicEObjectImpl
 */
public class ModelEMFConverter extends BaseModelConverter<Object> {

  private Object lastObject = null;
  private ModelObject<?> lastModelObject = null;

  private Map<Object, InternalEObject> objectMapping = new HashMap<Object, InternalEObject>();

  // list of objects for which the content needs to be converted
  private List<Object> toConvert = new ArrayList<Object>();

  // list of objects which have been converted
  private List<Object> converted = new ArrayList<Object>();

  // keeps track of all many-to-many associations, for these the order of
  // elements has to be repaired
  private List<ManyToMany> toRepairManyToMany = new ArrayList<ManyToMany>();

  /**
   * Converts a set of model managed objects and all the objects they reference to a collection of EObjects.
   * 
   * @param objects
   *          the model objects to convert, also the references/children are converted
   * @return the created EObjects
   */
  public List<EObject> convert(final List<Object> objects) {
    // when converting to EMF we can ignore volatile or not-changeable
    // efeatures
    setSkipVolatileEFeatures(true);
    doBaseActions(objects);

    // the process creates the new target objects and then converts the content
    // this multi-step process prevents stack overflow with large object graphs
    final List<EObject> result = new ArrayList<EObject>();
    Check.isNotNullArgument(objects, "objects"); //$NON-NLS-1$
    for (final Object object : objects) {
      result.add(createTarget(object));
    }

    while (!toConvert.isEmpty()) {
      final ArrayList<Object> beingConverted = new ArrayList<Object>(toConvert);
      toConvert.clear();
      for (Object object : beingConverted) {
        convertContent(object);
        converted.add(object);
      }
    }

    // for (ManyToMany mtm : toRepairManyToMany) {
    // final ModelObject<?> modelObject = mtm.getOwner();
    // final EObject eObject = objectMapping.get(modelObject.getTarget());
    // for (EReference eReference : eObject.eClass().getEAllReferences()) {
    // if (eReference.isMany()) {
    // final Collection<?> coll1 = (Collection<?>) modelObject.eGet(eReference);
    // final Collection<?> coll2 = (Collection<?>) eObject.eGet(eReference);
    // if (coll1.size() != coll2.size()) {
    // throw new IllegalStateException("Unequal sizes of collection for EReference " //$NON-NLS-1$
    // + eReference);
    // }
    // }
    // }
    // }

    // now repair the ManyToMany
    for (ManyToMany mtm : toRepairManyToMany) {
      mtm.repair();
    }
    toRepairManyToMany.clear();

    return result;
  }

  /**
   * Converts a single model managed object.
   * 
   * @param modelObject
   *          the object to convert
   * @return the created EObject
   */
  protected EObject createTarget(final Object target) {
    InternalEObject eObject = objectMapping.get(target);
    if (eObject == null) {
      // not found, create it and add a new entry to the mapping
      eObject = (InternalEObject) getObjectResolver().resolveToEObject(target);
      objectMapping.put(target, eObject);
    }
    if (!converted.contains(target) && !toConvert.contains(target)) {
      toConvert.add(target);
    }
    return eObject;
  }

  protected void convertContent(Object target) {
    // if a proxy then do no feature conversions as this may load
    // the object
    InternalEObject eObject = objectMapping.get(target);
    final ModelObject<?> modelObject = ModelResolver.getInstance().getModelObject(target);
    if (getProxyObjects().contains(target)) {
      final URI proxyURI = getProxyId(modelObject);
      if (proxyURI != null) {
        eObject.eSetProxyURI(proxyURI);
      }
      return;
    }

    for (final EStructuralFeature eStructuralFeature : eObject.eClass().getEAllStructuralFeatures()) {
      if (!eStructuralFeature.isChangeable() || eStructuralFeature.isVolatile()) {
        continue;
      }

      if (FeatureMapUtil.isFeatureMap(eStructuralFeature)) {
        convertFeatureMap(modelObject, eObject, eStructuralFeature);
      } else if (eStructuralFeature.isMany()) {
        if (eStructuralFeature instanceof EAttribute) {
          final EAttribute eAttribute = (EAttribute) eStructuralFeature;
          convertManyEAttribute(modelObject, eObject, eAttribute);
        } else {
          final EReference eReference = (EReference) eStructuralFeature;
          convertManyEReference(modelObject, eObject, eReference);
        }
      } else {
        if (eStructuralFeature instanceof EAttribute) {
          final EAttribute eAttribute = (EAttribute) eStructuralFeature;
          convertSingleEAttribute(modelObject, eObject, eAttribute);
        } else {
          final EReference eReference = (EReference) eStructuralFeature;
          convertSingleEReference(modelObject, eObject, eReference);
        }
      }
    }
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
  protected void convertFeatureMap(final ModelObject<?> modelObject, final EObject eObject,
      final EStructuralFeature eFeature) {
    final Collection<?> mValues = (Collection<?>) modelObject.eGet(eFeature);

    @SuppressWarnings("unchecked")
    final Collection<Object> values = (Collection<Object>) eObject.eGet(eFeature);
    for (final Object mValue : mValues) {
      final ModelFeatureMapEntry<?> mEntry = ModelResolver.getInstance().getModelFeatureMapEntry(eFeature, mValue);
      EStructuralFeature entryFeature = mEntry.getEStructuralFeature();
      Object entryValue = mEntry.getValue();

      // flatten the tree of feature map entries, feature maps maybe nested
      // EMF uses a flattened api, while Texo builds this actual tree of
      // featuremaps, the findFeature and findValue find the deepest featureMap entry
      if (FeatureMapUtil.isFeatureMap(entryFeature)) {
        final ModelFeatureMapEntry<?> modelFeatureMapEntry = ModelResolver.getInstance()
            .getModelFeatureMapEntry(entryFeature, entryValue);
        entryFeature = ModelUtils.findFeature(modelFeatureMapEntry);
        entryValue = ModelUtils.findValue(modelFeatureMapEntry);
      }

      final Object convertedValue;
      if (entryFeature instanceof EAttribute) {
        convertedValue = convertEAttributeValue(entryValue, ((EAttribute) entryFeature).getEAttributeType());
      } else {
        convertedValue = createTarget(entryValue);
      }
      final FeatureMap.Entry eEntry = FeatureMapUtil.createEntry(entryFeature, convertedValue);
      values.add(eEntry);
    }
  }

  /**
   * Converts the value of an EReference with isMany==false, the value is converted to an EObject and set in the correct
   * feature in the eObject.
   * 
   * @param modelObject
   *          the modelObject from which the value is retrieved.
   * @param eObject
   *          the eObject in which the value is set (after it has been converted)
   * @param eReference
   *          the eReference which is converted
   */
  protected void convertSingleEReference(final ModelObject<?> modelObject, final EObject eObject,
      final EReference eReference) {
    // containment/container features are always set from the
    // containment side
    if (eReference.isContainer()) {
      return;
    }

    if (eObject.eIsSet(eReference)) {
      return;
    }

    final Object value = modelObject.eGet(eReference);
    if (value == null) {
      eObject.eSet(eReference, null);
    } else {
      final InternalEObject eValue = (InternalEObject) createTarget(value);
      eObject.eSet(eReference, eValue);
    }
  }

  /**
   * Converts the value of an EReference with isMany==true, the values of the collection are converted to EObjects and
   * added to the list in the correct feature in the eObject.
   * 
   * @param modelObject
   *          the modelObject from which the value is retrieved.
   * @param eObject
   *          the eObject in which the value is set (after it has been converted)
   * @param eReference
   *          the eReference which is converted
   */
  protected void convertManyEReference(final ModelObject<?> modelObject, final EObject eObject,
      final EReference eReference) {
    // container feature is always set from the other side, the containment
    // side
    if (eReference.isContainer()) {
      return;
    }

    final Object manyValue = modelObject.eGet(eReference);
    final Collection<EObject> newValues = new ArrayList<EObject>();

    boolean isMap = Map.class.isAssignableFrom(manyValue.getClass());
    EStructuralFeature valueFeature = null;
    EStructuralFeature keyFeature = null;
    if (isMap) {
      final EClass mapEClass = eReference.getEReferenceType();
      valueFeature = mapEClass.getEStructuralFeature("value"); //$NON-NLS-1$
      keyFeature = mapEClass.getEStructuralFeature("key"); //$NON-NLS-1$

      Check.isTrue(ModelUtils.isEMap(eReference), "Expected emap EReference, but th// case for EReference " //$NON-NLS-1$
          + eReference);

      final Map<?, ?> map = (Map<?, ?>) manyValue;

      for (final Object key : map.keySet()) {
        final Object value = map.get(key);
        final EObject mapEntryEObject = EcoreUtil.create(mapEClass);

        // key and value maybe primitive types but can also be
        // references to model objects.
        if (valueFeature instanceof EReference) {
          mapEntryEObject.eSet(valueFeature, createTarget(value));
        } else {
          mapEntryEObject.eSet(valueFeature, value);
        }
        if (keyFeature instanceof EReference) {
          mapEntryEObject.eSet(keyFeature, createTarget(key));
        } else {
          mapEntryEObject.eSet(keyFeature, key);
        }
        newValues.add(mapEntryEObject);
      }
    } else {

      // a many to many
      if (eReference.getEOpposite() != null && eReference.getEOpposite().isMany()) {
        final ManyToMany mtm = new ManyToMany();
        mtm.setOwner(modelObject);
        mtm.setEReference(eReference);
        toRepairManyToMany.add(mtm);
      }

      @SuppressWarnings("unchecked")
      final Collection<Object> values = (Collection<Object>) manyValue;

      for (final Object value : values) {
        if (value == null) {
          newValues.add(null);
        } else {
          final InternalEObject eValue = (InternalEObject) createTarget(value);
          if (!newValues.contains(eValue)) {
            newValues.add(eValue);
          }
        }
      }
    }

    @SuppressWarnings("unchecked")
    final Collection<EObject> eValues = (Collection<EObject>) eObject.eGet(eReference);

    boolean updateList = false;
    if (newValues.size() == eValues.size()) {
      final Iterator<?> it = eValues.iterator();
      for (Object newValue : newValues) {
        final Object oldValue = it.next();

        if (isMap) {
          final EObject oldMapEntry = (EObject) oldValue;
          final EObject newMapEntry = (EObject) newValue;
          final Object oldMapValue = oldMapEntry.eGet(valueFeature);
          final Object oldMapKey = oldMapEntry.eGet(keyFeature);
          final Object newMapValue = newMapEntry.eGet(valueFeature);
          final Object newMapKey = newMapEntry.eGet(keyFeature);
          if (valueFeature instanceof EReference) {
            updateList = oldMapValue == newMapValue;
          } else {
            updateList = oldMapValue != null ? !oldMapValue.equals(newMapValue)
                : newMapValue != null ? !newMapValue.equals(oldMapValue) : false;
          }
          if (keyFeature instanceof EReference) {
            updateList = updateList || oldMapKey == newMapKey;
          } else {
            updateList = updateList || (oldMapKey != null ? !oldMapKey.equals(newMapKey)
                : newMapKey != null ? !newMapKey.equals(oldMapKey) : false);
          }
        } else {
          updateList = oldValue != newValue;
        }

        if (updateList) {
          break;
        }
      }
    } else {
      updateList = true;
    }

    if (updateList) {
      // clear the evalues so that an empty tag is created in the xml
      eValues.clear();
      eValues.addAll(newValues);
    }
  }

  /**
   * Converts the value of an EAttribute with isMany==false, the value is converted (
   * {@link #convertEAttributeValue(Object, EDataType)}) and set in the correct feature in the eObject.
   * 
   * @param modelObject
   *          the modelObject from which the value is retrieved.
   * @param eObject
   *          the eObject in which the value is set (after it has been converted)
   * @param eAttribute
   *          the EAttribute which is converted
   * @see #convertEAttributeValue(Object, EDataType)
   */
  protected void convertSingleEAttribute(final ModelObject<?> modelObject, final EObject eObject,
      final EAttribute eAttribute) {
    final Object value = modelObject.eGet(eAttribute);

    // don't set the eObject if the value is null and the attribute is unsettable.
    // unsettable is modelled with null in Texo.
    if (value == null && ModelUtils.isUnsettable(eAttribute)) {
      eObject.eUnset(eAttribute);
      return;
    }
    final Object newValue = convertEAttributeValue(value, eAttribute.getEAttributeType());
    ((InternalEObject) eObject).eSet(eAttribute, newValue);
  }

  /**
   * Converts the value of an EAttribute with isMany==true, the values of the collection are converted and added to the
   * list in the correct feature in the eObject.
   * 
   * @param modelObject
   *          the modelObject from which the value is retrieved.
   * @param eObject
   *          the eObject in which the value is set (after it has been converted)
   * @param eAttribute
   *          the EAttribute which is converted
   * @see #convertEAttributeValue(Object, EDataType)
   */
  protected void convertManyEAttribute(final ModelObject<?> modelObject, final EObject eObject,
      final EAttribute eAttribute) {
    final Collection<?> values = (Collection<?>) modelObject.eGet(eAttribute);
    final EDataType eDataType = eAttribute.getEAttributeType();
    @SuppressWarnings("unchecked")
    final List<Object> eValues = (List<Object>) eObject.eGet(eAttribute);

    final List<Object> newValues = new ArrayList<Object>();
    for (final Object value : values) {
      newValues.add(convertEAttributeValue(value, eDataType));
    }
    boolean updateList = false;
    if (values.size() == eValues.size()) {
      final Iterator<?> it = eValues.iterator();
      for (Object newValue : newValues) {
        final Object oldValue = it.next();
        if (newValue != null && oldValue == null) {
          updateList = true;
        } else if (oldValue == null && newValue != null) {
          updateList = true;
        } else if (oldValue != null && newValue != null) {
          updateList = !oldValue.equals(newValue);
        }
        if (updateList) {
          break;
        }
      }
    } else {
      updateList = true;
    }

    if (updateList) {
      // clear the evalues so that an empty tag is created in the xml
      eValues.clear();
      eValues.addAll(newValues);
    }
  }

  /**
   * Converts a primitive type value. For EEnum: this implementation only converts an Enum to an EEnum value.
   * 
   * @param value
   *          the value to convert
   * @param eDataType
   *          its EDataType
   * @return the converted value
   */
  protected Object convertEAttributeValue(final Object value, final EDataType eDataType) {
    if (value instanceof Enum<?>) {
      final EDataType enumDataType = getDataTypeOrBaseType(eDataType);
      Check.isInstanceOf(enumDataType, EEnum.class);
      final EEnum eeNum = (EEnum) enumDataType;
      for (final EEnumLiteral enumLiteral : eeNum.getELiterals()) {
        // the code generation template uppercases enum
        if (enumLiteral.getName().toUpperCase(Locale.ENGLISH).equals(((Enum<?>) value).name())) {
          return enumLiteral.getInstance();
        }
      }
    }

    if (value instanceof Date && eDataType == XMLTypePackage.eINSTANCE.getDate()) {
      final Date date = (Date) value;
      final XMLCalendar xmlCalendar = new XMLCalendar(date, XMLCalendar.DATE);
      final Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      xmlCalendar.clear();
      xmlCalendar.setYear(calendar.get(Calendar.YEAR));
      xmlCalendar.setMonth(1 + calendar.get(Calendar.MONTH));
      xmlCalendar.setDay(calendar.get(Calendar.DATE));
      // note xmlcalendar expects minutes, calendar gives millis
      xmlCalendar.setTimezone(calendar.get(Calendar.ZONE_OFFSET) / 60000);
      return xmlCalendar;
    }

    if (value instanceof Date && eDataType == XMLTypePackage.eINSTANCE.getDateTime()) {
      final Date date = (Date) value;
      return new XMLCalendar(date, XMLCalendar.DATETIME);
    }

    return value;
  }

  /**
   * Class is used to keep track of all the many to many for which the order has to be repaired.
   * 
   * @author mtaal
   */
  private class ManyToMany {
    private ModelObject<?> owner;
    private EReference eReference;

    public void repair() {
      final EObject eObjectOwner = objectMapping.get(owner.getTarget());
      final ModelObject<?> modelObjectOwner = ModelResolver.getInstance().getModelObject(owner);
      final Object listObject = modelObjectOwner.eGet(eReference);
      if (!(listObject instanceof List<?>)) {
        // no order maintained anyway
        return;
      }
      final EList<?> eList = (EList<?>) eObjectOwner.eGet(eReference);
      final List<?> list = (List<?>) listObject;
      int correctIndex = 0;
      for (Object objectElement : list) {
        final EObject eObjectElement = objectMapping.get(objectElement);
        final int newIndex = eList.indexOf(eObjectElement);
        if (newIndex != correctIndex) {
          eList.move(correctIndex, newIndex);
        }
        correctIndex++;
      }
    }

    public void setOwner(ModelObject<?> owner) {
      this.owner = owner;
    }

    public void setEReference(EReference eReference) {
      this.eReference = eReference;
    }

  }

  public Map<Object, InternalEObject> getObjectMapping() {
    return objectMapping;
  }

  public void setObjectMapping(Map<Object, InternalEObject> objectMapping) {
    this.objectMapping = objectMapping;
  }

  public List<Object> getConverted() {
    return converted;
  }

  public void setConverted(List<Object> converted) {
    this.converted = converted;
  }

  private ModelObject<?> getModelObject(Object target) {
    if (target == lastObject) {
      return lastModelObject;
    }
    lastObject = target;
    lastModelObject = ModelResolver.getInstance().getModelObject(target);
    return lastModelObject;
  }

  @Override
  protected EClass eClass(Object target) {
    return getModelObject(target).eClass();
  }

  @Override
  protected Object eGet(Object target, EStructuralFeature eFeature) {
    return getModelObject(target).eGet(eFeature);
  }

  @Override
  protected boolean isModelEnabled(Object target) {
    return ModelResolver.getInstance().isModelEnabled(target);
  }

  @Override
  protected boolean doSkipNotChangeAbleFeatures() {
    return true;
  }

  // always skip volatile when converting to EMF
  @Override
  public boolean isSkipVolatileEFeatures() {
    return true;
  }

}