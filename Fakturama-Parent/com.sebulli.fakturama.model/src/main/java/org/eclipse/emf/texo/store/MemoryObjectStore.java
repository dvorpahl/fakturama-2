package org.eclipse.emf.texo.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.texo.model.ModelObject;
import org.eclipse.emf.texo.model.ModelResolver;
import org.eclipse.emf.texo.provider.IdProvider;
import org.eclipse.emf.texo.utils.ModelUtils;
import org.eclipse.emf.texo.utils.ModelUtils.TypeIdTuple;

/**
 * An object store which keeps all the data in memory, only supports simple querying.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 */
public class MemoryObjectStore extends ObjectStore {

  private static final String FROM = "from"; //$NON-NLS-1$
  private static final int FROM_LENGTH = FROM.length();

  private final Map<EClass, List<Object>> data = new HashMap<EClass, List<Object>>();

  protected Map<EClass, List<Object>> getData() {
    return data;
  }

  public void addData(Collection<Object> objects) {
    for (Object object : objects) {
      @SuppressWarnings("unchecked")
      final ModelObject<Object> modelObject = (ModelObject<Object>) ModelResolver.getInstance().getModelObject(object);
      final EClass eClass = modelObject.eClass();
      List<Object> dataList = data.get(resolveEClass(eClass));
      if (dataList == null) {
        dataList = new ArrayList<Object>();
        data.put(eClass, dataList);
      }
      dataList.add(object);
    }
  }

  @Override
  public Object get(EClass eClass, Object id) {
    final List<Object> dataList = data.get(resolveEClass(eClass));
    if (dataList != null) {
      for (Object o : dataList) {
        final Object objectId = IdProvider.getInstance().getId(o);
        if (objectId != null && objectId.equals(id)) {
          return o;
        }
      }
    }
    return null;
  }

  @Override
  public long countNamedQuery(String name, Map<String, Object> namedParameters) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.emf.texo.server.store.ObjectStore#get(java.lang.Class, java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  @Override
  public <T extends Object> T get(Class<T> clz, Object id) {
    for (List<Object> dataList : data.values()) {
      for (Object o : dataList) {
        final Object objectId = IdProvider.getInstance().getId(o);
        if (clz.isAssignableFrom(o.getClass()) && objectId != null && objectId.equals(id)) {
          return (T) o;
        }
      }
    }
    return null;
  }

  @Override
  public <T extends Object> T update(T object) {
    return object;
  }

  @Override
  public <T extends Object> void remove(T object) {
    if (object == null) {
      return;
    }
    final ModelObject<?> modelObject = ModelResolver.getInstance().getModelObject(object);
    final List<Object> dataList = data.get(resolveEClass(modelObject.eClass()));
    boolean removed = false;
    if (dataList != null) {
      removed = dataList.remove(object);
    }
    if (!removed) {
      return;
    }

    // remove all contained children
    for (EReference eref : modelObject.eClass().getEAllReferences()) {
      if (!eref.isContainment()) {
        continue;
      }
      if (eref.isMany()) {
        final Collection<?> collection = (Collection<?>) modelObject.eGet(eref);
        for (Object o : collection) {
          remove(o);
        }
      } else {
        remove(modelObject.eGet(eref));
      }
    }
  }

  protected EClass resolveEClass(EClass eClass) {
    if (data.containsKey(eClass)) {
      return eClass;
    }
    for (EClass key : data.keySet()) {
      if (key.getName().equals(eClass.getName())
          && key.getEPackage().getNsURI().equals(eClass.getEPackage().getNsURI())) {
        return key;
      }
    }
    return eClass;
  }

  @Override
  public <T extends Object> void refresh(T object) {
    // don't do anything
  }

  @Override
  public <T extends Object> void insert(T object) {
    final ModelObject<?> modelObject = ModelResolver.getInstance().getModelObject(object);
    List<Object> dataList = data.get(resolveEClass(modelObject.eClass()));
    if (dataList == null) {
      dataList = new ArrayList<Object>();
      data.put(modelObject.eClass(), dataList);
    }

    if (dataList.contains(object)) {
      // already inserted go away
      return;
    }

    // set the id
    setIdAttributeOnInsert(modelObject);

    // add it
    dataList.add(object);

    // insert all contained children
    for (EReference eref : modelObject.eClass().getEAllReferences()) {
      if (!eref.isContainment()) {
        continue;
      }
      if (eref.isMany()) {
        final Collection<?> collection = (Collection<?>) modelObject.eGet(eref);
        for (Object o : collection) {
          insert(o);
        }
      } else {
        insert(modelObject.eGet(eref));
      }
    }
  }

  protected void setIdAttributeOnInsert(ModelObject<?> modelObject) {
    final EAttribute idEAttribute = IdProvider.getInstance().getIdEAttribute(modelObject.eClass());
    if (modelObject.eGet(idEAttribute) == null) {
      // always wait a few milliseconds to make sure id is unique, is not good for production code
      // but production code should not use the MemoryObjectStore or its derivatives
      try {
        Thread.sleep(2);
      } catch (Exception ignore) {
      }
      if (idEAttribute.getEAttributeType().getInstanceClass() == String.class) {
        modelObject.eSet(idEAttribute, "" + System.currentTimeMillis()); //$NON-NLS-1$
      } else if (Long.class.isAssignableFrom(idEAttribute.getEAttributeType().getInstanceClass())) {
        modelObject.eSet(idEAttribute, System.currentTimeMillis());
      } else if (long.class.isAssignableFrom(idEAttribute.getEAttributeType().getInstanceClass())) {
        modelObject.eSet(idEAttribute, System.currentTimeMillis());
      } else if (Integer.class.isAssignableFrom(idEAttribute.getEAttributeType().getInstanceClass())) {
        modelObject.eSet(idEAttribute, new Integer((int) System.currentTimeMillis()));
      } else if (Integer.class.isAssignableFrom(idEAttribute.getEAttributeType().getInstanceClass())) {
        modelObject.eSet(idEAttribute, new Integer((int) System.currentTimeMillis()));
      }
    }
  }

  @Override
  public List<?> query(String qryStr, Map<String, Object> namedParameters, int firstResult, int maxResults) {
    final int index = qryStr.toLowerCase().indexOf(FROM);
    if (index != -1) {
      String eClassName = qryStr.substring(index + FROM_LENGTH).trim();
      if (eClassName.indexOf(" ") != -1) { //$NON-NLS-1$
        eClassName = eClassName.substring(0, eClassName.indexOf(" ")); //$NON-NLS-1$
      }
      final EClass eClass = ModelUtils.getEClassFromQualifiedName(eClassName);
      if (eClass == null) {
        return Collections.emptyList();
      }
      return query(eClass, firstResult, maxResults);
    }
    return Collections.emptyList();
  }

  @Override
  public List<?> namedQuery(String name, Map<String, Object> namedParameters, int firstResult, int maxResults) {
    throw new UnsupportedOperationException();
  }

  @Override
  public long count(String qryStr, Map<String, Object> namedParameters) {
    final List<?> list = query(qryStr, namedParameters, 0, -1);
    return list.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.emf.texo.store.ObjectStore#query(org.eclipse.emf.ecore.EClass)
   */
  @Override
  public List<?> query(EClass eClass, int firstResult, int maxResults) {
    List<?> result = data.get(resolveEClass(eClass));
    if (result == null) {
      return Collections.emptyList();
    }

    int from = 0;
    if (firstResult > -1) {
      from = firstResult;
    }
    int to = result.size();
    if (maxResults > -1) {
      to = from + maxResults;
    }
    if (from >= result.size()) {
      from = result.size();
      if (from == 0) {
        return Collections.emptyList();
      }
    }
    if (to > result.size()) {
      to = result.size();
    }

    return result.subList(from, to);
  }

  @Override
  public Object getDelegate() {
    return data;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.emf.texo.server.store.ObjectStore#getReferingObjects(java.lang.Object, int, boolean)
   */
  @Override
  public List<Object> getReferingObjects(Object target, int maxResult, boolean includeContainmentReferences) {
    return null;
  }

  @Override
  public EObject getEObject(URI objectUri) {
    EObject eObject = super.getEObject(objectUri);
    if (eObject == null) {
      final TypeIdTuple tuple = ModelUtils.getTypeAndIdFromUri(isUseWebServiceUriFormat(), objectUri);
      eObject = (EObject) get(tuple.getEClass(), tuple.getId());
      if (eObject == null) {
        eObject = EcoreUtil.create(tuple.getEClass());
        eObject.eSet(IdProvider.getInstance().getIdEAttribute(tuple.getEClass()), tuple.getId());
      }
    }
    return eObject;
  }

}
