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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.texo.component.ComponentProvider;
import org.eclipse.emf.texo.component.TexoComponent;
import org.eclipse.emf.texo.component.TexoStaticSingleton;
import org.eclipse.emf.texo.model.ModelConstants;
import org.eclipse.emf.texo.model.ModelObject;
import org.eclipse.emf.texo.model.ModelResolver;
import org.eclipse.emf.texo.utils.ModelUtils;

/**
 * Provides the title of an object, can be used to fill comboboxes and such. The title of an object can be defined in 2
 * ways:
 * <ul>
 * <li>By annotating one or more {@link EStructuralFeature} of an {@link EClass} with an {@link EAnnotation} with source
 * {@link ModelConstants#TITLE_ANNOTATION_SOURCE}.</li>
 * <li>By annotating an {@link EClass} with an {@link EAnnotation} with source
 * {@link ModelConstants#TITLE_ANNOTATION_SOURCE}. This annotation is considered to be an expression.</li>
 * </ul>
 * 
 * When creating a title of an object the system will handle the above cases in different ways:
 * <ul>
 * <li>EStructuralFeature annotation: the values of the title EStructuralFeature annotations are concatenated with a
 * {@link ModelConstants#TITLE_SEPARATOR}. The separaotor can be customized by extending this class and overriding
 * {@link #getTitleSeparator()}.</li>
 * <li>EClass annotation: the value of the EClass annotation is considered to be an expression which contains references
 * to EStructuralFeatures, for example: ${lastName}, ${firstName}</li>
 * </li>
 * </ul>
 * 
 * Note that an {@link EReference} can be part of a title, the title generation process will step one level deep to
 * generate a title. This to prevent infinite cycles. This means that a title which consists of an EReference and the
 * referenced type has again a title with an EReference that for that second title the {@link Object#toString()} method
 * is called to generate a title when generating a title for the first EReference.
 * 
 * Future functionality:
 * <ul>
 * <li>make it possible to create/define a short title</li>
 * <li>support the dot notation in the title expression</li>
 * </ul>
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 */
public class TitleProvider implements TexoComponent, TexoStaticSingleton {

  private static TitleProvider instance = ComponentProvider.getInstance().newInstance(TitleProvider.class);

  private static final EDataType STRING_EDATATYPE = XMLTypePackage.eINSTANCE.getString();

  public static TitleProvider getInstance() {
    return instance;
  }

  public static void setInstance(TitleProvider instance) {
    TitleProvider.instance = instance;
  }

  private final Map<EClass, EClassTitleProvider> titleProviders = new ConcurrentHashMap<EClass, EClassTitleProvider>();

  /**
   * Determine the title of an object.
   * 
   * @param object
   *          must be an object which can be converted to a {@link ModelObject} using the
   *          {@link ModelResolver#getModelObject(Object)}.
   * @return the title of an object.
   */
  public String getTitle(Object object) {
    if (object instanceof EObject) {
      final EObject eObject = (EObject) object;
      return getCreateTitleProvider(eObject.eClass()).getTitle(eObject);
    }
    final ModelObject<?> modelObject = ModelResolver.getInstance().getModelObject(object);
    return getCreateTitleProvider(modelObject.eClass()).getTitle(modelObject);
  }

  /**
   * Will determine the title of an object, the nextStep parameter defines if title EStructuralFeatures which are
   * EReferences should be traversed. For the first step in the title generation process this is done, for subsequent
   * steps this is not done to prevent infinite cycles.
   * 
   * @param object
   *          must be an object which can be converted to a {@link ModelObject} using the
   *          {@link ModelResolver#getModelObject(Object)}.
   * @param nextStep
   *          if true then EReferences are traversed to generate the title, if false then EReferences are not traversed.
   * @return the title of an object
   */
  protected String getTitle(Object object, boolean nextStep) {
    final ModelObject<?> modelObject = ModelResolver.getInstance().getModelObject(object);
    return getCreateTitleProvider(modelObject.eClass()).getTitle(modelObject, nextStep);
  }

  protected String getTitleSeparator() {
    return ModelConstants.TITLE_SEPARATOR;
  }

  protected EClassTitleProvider getCreateTitleProvider(EClass eClass) {
    EClassTitleProvider titleProvider = titleProviders.get(eClass);
    if (titleProvider != null) {
      return titleProvider;
    }
    // determine what type of titleprovider should be used
    String expression;
    if ((expression = ModelUtils.getEAnnotation(eClass, ModelConstants.TITLE_ANNOTATION_KEY)) != null) {
      final ExpressionTitleProvider expressionTitleProvider = ComponentProvider.getInstance().newInstance(
          ExpressionTitleProvider.class);
      expressionTitleProvider.setEClass(eClass);
      expressionTitleProvider.setExpression(expression);
      titleProvider = expressionTitleProvider;
    } else {
      // find efeatures with the title annotations
      final List<EStructuralFeature> titleEFeatures = new ArrayList<EStructuralFeature>();
      for (EStructuralFeature eFeature : eClass.getEAllStructuralFeatures()) {
        if (ModelUtils.hasEAnnotation(eFeature, ModelConstants.TITLE_ANNOTATION_KEY)) {
          titleEFeatures.add(eFeature);
        }
      }
      if (titleEFeatures.isEmpty()) {
        // take the first String eAttribute
        for (EAttribute eAttribute : eClass.getEAllAttributes()) {
          // check if a string, note TITLE_SEPARATOR is used but any string
          // could be used for this check
          if (eAttribute.getEAttributeType() == STRING_EDATATYPE
              || eAttribute.getEAttributeType().isInstance(ModelConstants.TITLE_SEPARATOR)) {
            titleEFeatures.add(eAttribute);
            break;
          }
        }
      }
      // take the first eattribute
      if (titleEFeatures.isEmpty() && eClass.getEAllAttributes().size() > 0) {
        titleEFeatures.add(eClass.getEAllAttributes().get(0));
      }
      // take the first estructuralfeature
      if (titleEFeatures.isEmpty() && eClass.getEAllStructuralFeatures().size() > 0) {
        titleEFeatures.add(eClass.getEAllStructuralFeatures().get(0));
      }

      final EFeatureTitleProvider eFeatureTitleProvider = new EFeatureTitleProvider();
      eFeatureTitleProvider.setEFeatures(titleEFeatures);
      titleProvider = eFeatureTitleProvider;
    }
    titleProviders.put(eClass, titleProvider);
    return titleProvider;
  }

  public abstract static class EClassTitleProvider {

    protected abstract String getTitle(ModelObject<?> modelObject, boolean nextStep);

    protected abstract String getTitle(EObject modelObject, boolean nextStep);

    public String getTitle(EObject modelObject) {
      return getTitle(modelObject, true);
    }

    public String getTitle(ModelObject<?> modelObject) {
      return getTitle(modelObject, true);
    }
  }

  public static class EFeatureTitleProvider extends EClassTitleProvider {
    private List<EStructuralFeature> eFeatures = new ArrayList<EStructuralFeature>();

    @Override
    protected String getTitle(EObject modelObject, boolean nextStep) {
      if (eFeatures.isEmpty()) {
        // only return the type name
        return modelObject.eClass().getName();
      }
      final StringBuilder sb = new StringBuilder();
      for (EStructuralFeature eFeature : eFeatures) {
        if (FeatureMapUtil.isFeatureMap(eFeature)) {
          continue;
        }

        if (sb.length() > 0) {
          sb.append(ModelConstants.TITLE_SEPARATOR);
        }

        Object value = modelObject.eGet(eFeature);
        if (value == null) {
          continue;
        }
        if (eFeature instanceof EAttribute) {
          if (value instanceof Collection<?>) {
            if (((Collection<?>) value).isEmpty()) {
              continue;
            }
            value = ((Collection<?>) value).iterator().next();
            if (value == null) {
              continue;
            }
          }
          final EDataType eDataType = ((EAttribute) eFeature).getEAttributeType();
          sb.append(eDataType.getEPackage().getEFactoryInstance().convertToString(eDataType, value));
        } else if (nextStep) {
          if (eFeature.isMany()) {
            for (Object o : (Collection<?>) value) {
              if (o instanceof EObject) {
                sb.append(TitleProvider.getInstance().getTitle(o, false));
              }
            }
          } else {
            if (value instanceof EObject) {
              sb.append(TitleProvider.getInstance().getTitle(value, false));
            }
          }
        }
      }
      return sb.toString();
    }

    @Override
    protected String getTitle(ModelObject<?> modelObject, boolean nextStep) {
      if (eFeatures.isEmpty()) {
        // just return the type name
        return modelObject.eClass().getName();
      }
      final StringBuilder sb = new StringBuilder();
      for (EStructuralFeature eFeature : eFeatures) {
        if (sb.length() > 0) {
          sb.append(ModelConstants.TITLE_SEPARATOR);
        }
        final Object value = modelObject.eGet(eFeature);
        if (value == null) {
          continue;
        }
        if (eFeature instanceof EAttribute) {
          final EDataType eDataType = ((EAttribute) eFeature).getEAttributeType();
          if (eFeature.isMany()) {
            for (Object o : (Collection<?>) value) {
              sb.append(eDataType.getEPackage().getEFactoryInstance().convertToString(eDataType, o));
            }
          } else {
            sb.append(eDataType.getEPackage().getEFactoryInstance().convertToString(eDataType, value));
          }
        } else if (nextStep) {
          if (eFeature.isMany()) {
            for (Object o : (Collection<?>) value) {
              if (ModelResolver.getInstance().isModelEnabled(o)) {
                sb.append(TitleProvider.getInstance().getTitle(o, false));
              }
            }
          } else {
            if (ModelResolver.getInstance().isModelEnabled(value)) {
              sb.append(TitleProvider.getInstance().getTitle(value, false));
            }
          }
        }
      }
      return sb.toString();
    }

    public List<EStructuralFeature> getEFeatures() {
      return eFeatures;
    }

    public void setEFeatures(List<EStructuralFeature> eFeatures) {
      this.eFeatures = eFeatures;
    }
  }

  public static class ExpressionTitleProvider extends EClassTitleProvider implements TexoComponent {
    private static final String EXPRESSION_START = "${"; //$NON-NLS-1$
    private static final int EXPRESSION_START_LENGTH = EXPRESSION_START.length();
    private static final String EXPRESSION_END = "}"; //$NON-NLS-1$

    private String expression = null;
    private List<Object> expressionList = new ArrayList<Object>();
    private EClass eClass;

    @Override
    protected String getTitle(EObject modelObject, boolean nextStep) {
      final StringBuilder sb = new StringBuilder();
      for (Object element : expressionList) {
        if (element instanceof String) {
          sb.append((String) element);
        } else {
          final Object value = modelObject.eGet((EStructuralFeature) element);
          if (element instanceof EReference) {
            if (nextStep) {
              sb.append(TitleProvider.getInstance().getTitle(value, false));
            }
          } else if (element instanceof EAttribute) {
            final EDataType eDataType = ((EAttribute) element).getEAttributeType();
            sb.append(eDataType.getEPackage().getEFactoryInstance().convertToString(eDataType, value));
          } else {
            throw new IllegalStateException("Type " + element.getClass().getName() //$NON-NLS-1$
                + " not supported, expression " + expression); //$NON-NLS-1$
          }
        }
      }
      return sb.toString();
    }

    @Override
    protected String getTitle(ModelObject<?> modelObject, boolean nextStep) {
      final StringBuilder sb = new StringBuilder();
      for (Object element : expressionList) {
        if (element instanceof String) {
          sb.append((String) element);
        } else {
          final Object value = modelObject.eGet((EStructuralFeature) element);
          if (element instanceof EReference) {
            if (nextStep) {
              sb.append(TitleProvider.getInstance().getTitle(value, false));
            }
          } else if (element instanceof EAttribute) {
            final EDataType eDataType = ((EAttribute) element).getEAttributeType();
            sb.append(eDataType.getEPackage().getEFactoryInstance().convertToString(eDataType, value));
          } else {
            throw new IllegalStateException("Type " + element.getClass().getName() //$NON-NLS-1$
                + " not supported, expression " + expression); //$NON-NLS-1$
          }
        }
      }
      return sb.toString();
    }

    public void setExpression(String expression) {
      this.expression = expression;
      int previousIndex = 0;
      do {
        int index = expression.indexOf(EXPRESSION_START, previousIndex);
        if (index != -1) {
          int nextIndex = expression.indexOf(EXPRESSION_END, index);
          if (nextIndex == -1) {
            break;
          }
          if (index > previousIndex) {
            expressionList.add(expression.substring(previousIndex, index));
          }
          final String featureName = expression.substring(index + EXPRESSION_START_LENGTH, nextIndex);
          final EStructuralFeature eFeature = eClass.getEStructuralFeature(featureName);
          if (eFeature == null) {
            expressionList.add("FEATURE NOT FOUND: " + featureName); //$NON-NLS-1$
          } else {
            expressionList.add(eFeature);
          }
          previousIndex = nextIndex + 1;
        } else {
          if (previousIndex < expression.length()) {
            expressionList.add(expression.substring(previousIndex));
          }
          break;
        }
      } while (true);

    }

    public EClass getEClass() {
      return eClass;
    }

    public void setEClass(EClass eClass) {
      this.eClass = eClass;
    }
  }
}
