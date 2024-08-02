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
 * $Id: ModelResolver.java,v 1.17 2011/08/28 12:34:59 mtaal Exp $
 */

package org.eclipse.emf.texo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.texo.component.ComponentProvider;
import org.eclipse.emf.texo.component.TexoStaticSingleton;
import org.eclipse.emf.texo.utils.Check;

/**
 * The ModelResolver is the central location for retrieving model information and setting and getting values on a target
 * model-managed object for an {@link EStructuralFeature}. When a {@link ModelPackage} initializes then it registers
 * itself in the model resolver. The ModelPackage also registers the generated classes and their corresponding
 * {@link EClass} in the ModelResolver.
 * 
 * {@link ModelPackage} instances can be retrieved by calling the {@link #getModelPackage(String)} method.
 * 
 * At runtime model information can be obtained for a model-managed pojo by calling the {@link #getModelObject(Object)}
 * method. The {@link ModelObject} wraps/adapts a model-managed pojo and provides access to runtime model information
 * (the {@link EClass}) and generic set/get methods (see {@link ModelObject#eGet(EStructuralFeature)} and
 * {@link ModelObject#eSet(EStructuralFeature, Object)}).
 * 
 * It is also possible to retrieve model information for a Class. For this the class provides access to the
 * {@link ModelDescriptor} maintained for each registered Class. The ModelDescriptor contains the {@link EClass} and
 * {@link ModelPackage}.
 * 
 * The ModelResolver is maintained in a static member (which can be overwritten with your own implementation). It is
 * also maintainable by thread. If you want a specific thread to use a different ModelResolver then call
 * {@;ink #setThreadInstance(ModelResolver)}. Note that you should take care to call this method with a null value (to
 * clear the {@link ThreadLocal}) at the end of the thread to clean up the thread ModelResolver (some environments, for
 * example Tomcat, re-uses Thread objects!).
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 * @see EClassifier
 * @see EPackage
 * @see ModelPackage
 * @see ModelFactory
 * @see ModelObject
 */
public class ModelResolver implements TexoStaticSingleton {

  private static ModelResolver instance = new ModelResolver();
  private static ThreadLocal<ModelResolver> threadInstance = new ThreadLocal<ModelResolver>();

  /**
   * Get the ModelResolver stored as a static instance. If a ModelResolver is set for this thread (see
   * {@link #setThreadInstance(ModelResolver)} then that one is returned.
   * 
   * @return an instance of a ModelResolver
   */
  public static ModelResolver getInstance() {
    if (threadInstance.get() != null) {
      return threadInstance.get();
    }
    return instance;
  }

  public static void setInstance(final ModelResolver instance) {
    ModelResolver.instance = instance;
  }

  /**
   * Set a specific ModelResolver for this thread. See the comments in the Class header above.
   * 
   * @param instance
   */
  public static void setThreadInstance(final ModelResolver instance) {
    threadInstance.set(instance);
  }

  private final Map<Class<?>, ModelDescriptor> classToModelMapping = new ConcurrentHashMap<Class<?>, ModelDescriptor>();

  private final Map<EClassifier, Class<?>> eClassifierToClassMapping = new ConcurrentHashMap<EClassifier, Class<?>>();

  private final Map<String, ModelPackage> nsuriToModelPackages = new ConcurrentHashMap<String, ModelPackage>();

  private EPackage.Registry ePackageRegistry = EPackage.Registry.INSTANCE;

  /**
   * Registers a {@link ModelPackage} in this registry. Does not register the related EPackage.
   * 
   * @param modelPackage
   *          to register, not <code>NULL</code>
   */
  public void registerModelPackage(final ModelPackage modelPackage) {
    Check.isNull(nsuriToModelPackages.get(modelPackage.getNsURI()), "The EcoreModelPackage with NsUri " //$NON-NLS-1$
        + modelPackage.getNsURI() + " has already been registered!");//$NON-NLS-1$
    nsuriToModelPackages.put(modelPackage.getNsURI(), modelPackage);
  }

  /**
   * Registers a {@link ModelPackage} in this registry and registers its EPackage.
   * 
   * @param modelPackage
   *          to register, not <code>NULL</code>
   */
  public void reRegisterModelPackage(final ModelPackage modelPackage) {
    if (nsuriToModelPackages.get(modelPackage.getNsURI()) != null) {
      return;
    }
    nsuriToModelPackages.put(modelPackage.getNsURI(), modelPackage);
    if (ePackageRegistry.getEPackage(modelPackage.getNsURI()) == null && modelPackage.getEPackage() != null) {
      ePackageRegistry.put(modelPackage.getNsURI(), modelPackage.getEPackage());
    }
    for (EClassifier eClassifier : modelPackage.getEPackage().getEClassifiers()) {
      if (eClassifier.getEPackage() != modelPackage.getEPackage()) {
        continue;
      }
      try {
        final Class<?> clz = modelPackage.getEClassifierClass(eClassifier);
        registerClassModelMapping(clz, eClassifier, modelPackage);
      } catch (IllegalArgumentException ignore) {
        // ignore all the ones which are not generated by Texo
      }
    }
  }

  /**
   * Return a {@link ModelPackage} on the basis of the NsUri
   * 
   * @param nsURI
   *          the namespace URI as defined in the {@link EPackage#getNsURI()}
   * @return an ModelPackage instance or null if not found
   * @see ModelPackage#getNsURI()
   */
  public ModelPackage getModelPackage(final String nsURI) {
    return nsuriToModelPackages.get(nsURI);
  }

  /**
   * Create a model enabled objecct for the {@link EClass}. If the related {@link EPackage} does not have a
   * {@link ModelPackage} then a {@link DynamicModelObject} is returned.
   * 
   * @see ModelFactory#create(EClass)
   */
  public Object create(EClass eClass) {
    final ModelPackage modelPackage = getModelPackage(eClass.getEPackage().getNsURI());
    // a dynamic ModelObject
    if (modelPackage == null) {
      final DynamicModelObject result = ComponentProvider.getInstance().newInstance(DynamicModelObject.class);
      result.setEClass(eClass);
      return result;
    }

    return modelPackage.getModelFactory().create(eClass);
  }

  /**
   * Return all registered model packages.
   */
  public Collection<ModelPackage> getModelPackages() {
    return nsuriToModelPackages.values();
  }

  /**
   * Convert a value to a string using the {@link ModelFactory}. If there is not ModelPackage registered for the
   * EDataType then the EFactory is used.
   * 
   * @param eDataType
   *          the {@link EDataType} to convert
   * @param value
   *          the value to convert
   * @return the String representation of the value
   */
  public String convertToString(EDataType eDataType, Object value) {
    final ModelPackage modelPackage = getModelPackage(eDataType.getEPackage().getNsURI());
    if (modelPackage != null) {
      return modelPackage.getModelFactory().convertToString(eDataType, value);
    }
    return eDataType.getEPackage().getEFactoryInstance().convertToString(eDataType, value);
  }

  /**
   * Creates a value from a String representation using the {@link ModelFactory#createFromString(EDataType, String)}
   * method. If there is no registered ModelPackage for the name space uri then the EFactory is used.
   * 
   * @param eDataType
   *          the EDataType of the to-be-converted value
   * @param strValue
   *          the String to convert
   * @return the object representation of the string
   */
  public Object createFromString(EDataType eDataType, String strValue) {
    final ModelPackage modelPackage = getModelPackage(eDataType.getEPackage().getNsURI());
    if (modelPackage != null) {
      return modelPackage.getModelFactory().createFromString(eDataType, strValue);
    }
    return eDataType.getEPackage().getEFactoryInstance().createFromString(eDataType, strValue);
  }

  /**
   * Register a new mapping from a Class to its {@link ModelDescriptor}.
   * 
   * @param clazz
   *          the Class to register
   * @param eClassifier
   *          the EClassifier
   * @param modelPackage
   *          the ModelPackage
   */
  public void registerClassModelMapping(final Class<?> clazz, final EClassifier eClassifier,
      final ModelPackage modelPackage) {
    Check.isFalse(classToModelMapping.containsKey(clazz),
        "The class " + clazz + " already has a model descriptor " + classToModelMapping.get(clazz)); //$NON-NLS-1$ //$NON-NLS-2$
    final ModelDescriptor modelDescriptor = new ModelDescriptor();
    modelDescriptor.setEClassifier(eClassifier);
    modelDescriptor.setModelPackage(modelPackage);
    classToModelMapping.put(clazz, modelDescriptor);
    eClassifierToClassMapping.put(eClassifier, clazz);
  }

  /**
   * Removes the artifacts of the {@link ModelPackage} and its sub packages from the internal data structures.
   */
  public void deregister(ModelPackage modelPackage) {
    deregister(modelPackage.getEPackage());
  }

  /**
   * @see #deregister(ModelPackage)
   */
  public void deregister(EPackage ePackage) {
    nsuriToModelPackages.remove(ePackage.getNsURI());
    // only remove from registry if exact same epackage
    if (ePackageRegistry.getEPackage(ePackage.getNsURI()) == ePackage) {
      ePackageRegistry.remove(ePackage.getNsURI());
    }
    final List<Class<?>> toRemove = new ArrayList<Class<?>>();
    for (Class<?> clz : classToModelMapping.keySet()) {
      final ModelDescriptor modelDescriptor = classToModelMapping.get(clz);
      if (modelDescriptor.getModelPackage().getEPackage() == ePackage) {
        toRemove.add(clz);
      }
    }
    for (Class<?> clz : toRemove) {
      classToModelMapping.remove(clz);
    }
    for (EClassifier eClassifier : ePackage.getEClassifiers()) {
      eClassifierToClassMapping.remove(eClassifier);
    }
    for (EPackage subEPackage : ePackage.getESubpackages()) {
      deregister(subEPackage);
    }
  }

  /**
   * Returns the implementation class for a certain EClassifier, can be a normal java class or an enum class.
   * 
   * @param eClassifier
   *          the EClassifier to get the implementation class for
   * @return the implementation class
   * @throws IllegalArgumentException
   *           if there is no implementation class
   */
  public Class<?> getImplementationClass(EClassifier eClassifier) {
    final Class<?> clz = eClassifierToClassMapping.get(eClassifier);
    if (clz == null) {
      throw new IllegalArgumentException("No implementation class for classifier " + eClassifier); //$NON-NLS-1$
    }
    return clz;
  }

  /**
   * Is the {@link EClassifier} managed by the ModelResolver.
   */
  public boolean isModeledEClassifier(EClassifier eClassifier) {
    return eClassifierToClassMapping.containsKey(eClassifier);
  }

  /**
   * @param target
   *          the object to check if it is managed by a {@link ModelPackage}. Note if null is passed then false is
   *          returned.
   * @return true if this is an object managed by a {@link ModelPackage}, false if not or if null is passed
   */
  public boolean isModelEnabled(final Object target) {
    if (target == null) {
      return false;
    }
    return getModelDescriptor(target.getClass(), false) != null;
  }

  /**
   * @return the {@link EClass} represented by this object
   * @throws IllegalArgumentException
   *           if no EClass can be found
   */
  public ModelObject<?> getModelObject(final Object target) {
    Check.isNotNullArgument(target, "target"); //$NON-NLS-1$

    if (target instanceof ModelObject<?>) {
      return (ModelObject<?>) target;
    }

    if (target instanceof EObject) {
      final ModelEObject modelEObject = ComponentProvider.getInstance().newInstance(ModelEObject.class);
      modelEObject.setTarget((EObject) target);
      return modelEObject;
    }

    final ModelDescriptor modelDescriptor = getModelDescriptor(target.getClass(), true);
    return modelDescriptor.createAdapter(target);
  }

  /**
   * Wrap an object (a pojo feature map entry) in a {@link ModelFeatureMapEntry}.
   * 
   * @param eFeature
   *          the feature used to find/create the ModelFeatureMapEntry.
   * @param adaptee
   *          the pojo entry to wrap
   * @return the wrapper
   */
  public ModelFeatureMapEntry<?> getModelFeatureMapEntry(EStructuralFeature eFeature, Object adaptee) {
    final String nsuri = eFeature.getEContainingClass().getEPackage().getNsURI();
    final ModelPackage modelPackage = getModelPackage(nsuri);
    if (modelPackage == null) {
      final DynamicModelFeatureMapEntry fmEntry = ComponentProvider.getInstance()
          .newInstance(DynamicModelFeatureMapEntry.class);
      fmEntry.setEStructuralFeature(eFeature);
      return fmEntry;
    }
    return modelPackage.getModelFactory().createModelFeatureMapEntry(eFeature, adaptee);
  }

  /**
   * Creates a feature map entry instance for a certain EStructuralFeature.
   * 
   * @param eStructuralFeature
   *          the feature map feature
   * @return the pojo feature map entry
   * @generated
   */
  public Object createFeatureMapEntry(EStructuralFeature eFeature) {
    final String nsuri = eFeature.getEContainingClass().getEPackage().getNsURI();
    final ModelPackage modelPackage = getModelPackage(nsuri);
    if (modelPackage == null) {
      final DynamicModelFeatureMapEntry fmEntry = ComponentProvider.getInstance()
          .newInstance(DynamicModelFeatureMapEntry.class);
      fmEntry.setEStructuralFeature(eFeature);
      return fmEntry;
    }
    Check.isNotNull(modelPackage, "No modelpackage found for EPackage nsuri: " + nsuri); //$NON-NLS-1$
    return modelPackage.getModelFactory().createFeatureMapEntry(eFeature);
  }

  /**
   * Find the ModelDescriptor for a Class. The system tries to find one for the Class itself or for its super Classes or
   * implemented interfaces.
   * 
   * If no ModelDescriptor can be found then an IllegalArgumentException is thrown (depends on the exceptionOnNotFound
   * parameter).
   * 
   * @param clazz
   *          the Class to find the ModelDescriptor for.
   * @param exceptionOnNotFound
   *          if true then an IllegalArgumentException is thrown when no ModelDescriptor can be found, if false then
   *          null is returned (if no ModelDescriptor can be found).
   * @return the found ModelDescriptor.
   * @throws IllegalArgumentException
   *           if no ModelDescriptor can be found and exceptionOnNotFound is true
   */
  public ModelDescriptor getModelDescriptor(final Class<?> clazz, final boolean exceptionOnNotFound) {
    ModelDescriptor modelDescriptor = classToModelMapping.get(clazz);

    if (!exceptionOnNotFound && modelDescriptor == null) {
      return null;
    }
    Check.isNotNull(modelDescriptor, "The class " + clazz + " is not managed by this ModelResolver"); //$NON-NLS-1$ //$NON-NLS-2$

    if (modelDescriptor != null) {
      return modelDescriptor;
    }

    // not there, try superclasses first
    modelDescriptor = findModelDescriptorSuperClasses(clazz.getSuperclass());
    // then try interfaces
    if (modelDescriptor == null) {
      modelDescriptor = findModelDescriptorInterfaces(clazz);
    }
    // found register it so that it is found quicker the next time
    if (modelDescriptor != null) {
      classToModelMapping.put(clazz, modelDescriptor);
      return modelDescriptor;
    }

    if (exceptionOnNotFound) {
      throw new IllegalArgumentException("The class " + clazz //$NON-NLS-1$
          + " is not managed by this ModelResolver"); //$NON-NLS-1$
    }
    return null;
  }

  /**
   * Checks the super class of a class for the existence of a ModelDescriptor.
   * 
   * @param clazz
   *          the Class to find the ModelDescriptor for
   * @return the ModelDescriptor or null if none found
   */
  protected ModelDescriptor findModelDescriptorSuperClasses(final Class<?> clazz) {
    if (clazz == null) {
      // end of the inheritance tree
      return null;
    }
    final ModelDescriptor modelDescriptor = classToModelMapping.get(clazz);
    if (modelDescriptor == null) {
      return findModelDescriptorSuperClasses(clazz.getSuperclass());
    }
    return modelDescriptor;
  }

  /**
   * Checks the interfaces of a class. Depth first, so for each implemented interface, first its inherited interfaces
   * are checked before moving on to the next implemented interface.
   * 
   * @param clazz
   *          the Class to find the ModelDescriptor for
   * @return the ModelDescriptor or null if none found
   */
  protected ModelDescriptor findModelDescriptorInterfaces(final Class<?> clazz) {
    if (clazz == null) {
      return null;
    }
    for (final Class<?> interf : clazz.getInterfaces()) {
      ModelDescriptor modelDescriptor = classToModelMapping.get(clazz);
      // ask the super interfaces
      if (modelDescriptor == null) {
        modelDescriptor = findModelDescriptorInterfaces(interf);
      }
      if (modelDescriptor != null) {
        return modelDescriptor;
      }
    }
    return null;
  }

  public EPackage.Registry getEPackageRegistry() {
    return ePackageRegistry;
  }

  public void setEPackageRegistry(EPackage.Registry ePackageRegistry) {
    this.ePackageRegistry = ePackageRegistry;
  }

  /**
   * Returns the list of {@link EReference} instances which refer to the {@link EClass} of the object. Note that the
   * {@link EReference} can also refer to a super {@link EClass} of the actual {@link EClass} of the object.
   * 
   * @param object
   *          the object for which the ereferences need to be returned.
   * @param includeContainmentReferences
   *          also return refering objects which reference the target through an EReference with containment==true
   * @return the list of EReferences which refer to the object.
   */
  public List<EReference> getReferingEReferences(EClass eClass, boolean includeContainerReferences) {
    final List<EReference> result = new ArrayList<EReference>();
    for (EClassifier eClassifier : eClassifierToClassMapping.keySet()) {
      if (eClassifier instanceof EClass) {
        final EClass referingEClass = (EClass) eClassifier;
        for (EReference eReference : referingEClass.getEAllReferences()) {
          if (eReference.isVolatile() || eReference.isTransient()) {
            continue;
          }
          if (eReference.isContainer() && !includeContainerReferences) {
            continue;
          }
          if (refersTo(eReference, eClass)) {
            result.add(eReference);
          }
        }
      }
    }
    return result;
  }

  private boolean refersTo(EReference eReference, EClass eClass) {
    final EClass referedEClass = eReference.getEReferenceType();
    return referedEClass == eClass || referedEClass.isSuperTypeOf(eClass);
  }

  /**
   * Returns the name of the java member in the class which represents this {@link EStructuralFeature}.
   * 
   * @param eFeature
   *          the {@link EStructuralFeature} for which to get the java property name.
   * @return the java member name
   */
  public String getJavaPropertyName(EStructuralFeature eFeature) {
    return eFeature.getName();
  }

  /**
   * Combines model information for a specific {@link EClassifier}, provides a convenient api to get the adapter
   * {@link ModelObject} for an object.
   * 
   * @author mtaal
   */
  public static class ModelDescriptor {
    private EClassifier eClassifier;
    private ModelPackage modelPackage;
    private ModelFactory modelFactory;

    /**
     * Retrieve/create a {@link ModelObject} instance for the object passed in as a parameter.
     * 
     * @param object
     *          the object for which a model adapter needs to be provided
     * @return the {@link ModelObject} instance.
     */
    public ModelObject<?> createAdapter(final Object object) {
      Check.isTrue(getEClassifier() instanceof EClass, "The model descriptor: " + this //$NON-NLS-1$
          + " does not represent an EClass"); //$NON-NLS-1$
      return modelFactory.createModelObject((EClass) eClassifier, object);
    }

    public ModelPackage getModelPackage() {
      return modelPackage;
    }

    public void setModelPackage(final ModelPackage modelPackage) {
      this.modelPackage = modelPackage;
      modelFactory = modelPackage.getModelFactory();
    }

    public EClassifier getEClassifier() {
      return eClassifier;
    }

    public void setEClassifier(final EClassifier eClassifier) {
      this.eClassifier = eClassifier;
    }

    @Override
    public String toString() {
      return eClassifier + " " + modelPackage; //$NON-NLS-1$
    }
  }

  protected Map<Class<?>, ModelDescriptor> getClassToModelMapping() {
    return classToModelMapping;
  }

  protected Map<EClassifier, Class<?>> getEClassifierToClassMapping() {
    return eClassifierToClassMapping;
  }

  protected Map<String, ModelPackage> getNsuriToModelPackages() {
    return nsuriToModelPackages;
  }

}