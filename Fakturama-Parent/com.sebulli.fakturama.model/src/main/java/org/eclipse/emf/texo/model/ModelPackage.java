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
 * $Id: ModelPackage.java,v 1.22 2011/08/29 05:16:08 mtaal Exp $
 */

package org.eclipse.emf.texo.model;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.texo.utils.ModelUtils;

/**
 * The ModelPackage contains direct and efficient statics and provides access to the runtime model (the {@link EPackage}
 * ) through generated methods. The ModelPackage is responsible for reading the model from an ecore file located in the
 * same package. Each of the {@link EClassifier} instances in the model and each {@link EStructuralFeature} will have a
 * generated constant and generated methods to have direct access to the model elements at runtime.
 * 
 * The ModelPackage also provides a single access point to the {@Link ModelFactory} through the
 * {@link #getModelFactory()} method and through the generated MODELFACTORY constant.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 */
public abstract class ModelPackage {

  // Store a local ref to the EPackage for performance reasons
  private EPackage ePackage = null;

  /** @return the {@link EPackage} of this ModelPackage */
  public EPackage getEPackage() {
    if (ePackage == null) {
      ePackage = ModelUtils.getEPackage(getNsURI());
      if (ePackage != null) {
        ModelUtils.setEFactoryProxy(this, ePackage);
      }
    }
    return ePackage;
  }

  protected void setEPackage(EPackage ePackage) {
    this.ePackage = ePackage;
  }

  /**
   * Returns the name of the ecore file holding the model represented by this class. Normally this file is present in
   * the same java package as this class. In this case the return is just the ecore file name. The file name is used to
   * read the ecore file from the classpath (see {@link ModelUtils#readEPackagesFromFile(ModelPackage)}).
   * 
   * @return the name of the ecore file
   */
  public abstract String getEcoreFileName();

  /**
   * @return the nsuri of the epackage
   * @see #getEPackage()
   * @see #getEPackage()
   */
  public abstract String getNsURI();

  /**
   * Return the package {@link ModelFactory} specific for the instance of this ModelPackage. There is one global
   * instance of this specific instance of the ModelFactory per ModelPackage.
   * 
   * @return the ModelFactory handling the creation of types for this ModelPackage
   */
  public abstract ModelFactory getModelFactory();

  /**
   * @return the nsprefix of the epackage
   * @see #getEPackage()
   */
  public String getNsPrefix() {
    return getEPackage().getNsPrefix();
  }

  /**
   * Returns the Class implementing a specific EClassifier.
   * 
   * @param eClassifier
   *          the EClassifier
   * @return the implementation class
   */
  public abstract Class<?> getEClassifierClass(org.eclipse.emf.ecore.EClassifier eClassifier);

  @Override
  public String toString() {
    return "ModelPackage " + ePackage; //$NON-NLS-1$
  }
}