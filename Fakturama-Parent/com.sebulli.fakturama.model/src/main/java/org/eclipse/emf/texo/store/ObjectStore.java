package org.eclipse.emf.texo.store;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.texo.model.ModelConstants;
import org.eclipse.emf.texo.provider.IdProvider;
import org.eclipse.emf.texo.resolver.DefaultObjectResolver;
import org.eclipse.emf.texo.utils.ModelUtils;

/**
 * Defines the interface for an object store. An object store can:
 * <ul>
 * <li>create a unique uri for an object</li>
 * <li>retrieve objects using an uri</li>
 * <li>query for an object using a string query</li>
 * <li>update, delete and insert objects</li>
 * </ul>
 * 
 * An object store corresponds roughly to the concept of an EMF {@link Resource} or JPA EntityManager.
 * 
 * Compared to the EMF Resource, the main difference is that an object store does not support the concept of containment
 * and is mostly a wrapper around specific implementations such as a JPA entity manager.
 * 
 * The object store api is not targeted as a full replacement or wrapper over an underlying technology such as an
 * EntityManager. The main purpose of the api is to support webservice and crud operations.
 * 
 * An object store is uniquely identified by an {@link URI}.
 * 
 * A main capability of an object store is to create a uniquely identifying string for an object, the uri.
 * 
 * Creating an object store should be a light weight operation comparable to creating an EntityManager in JPA.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 */
public abstract class ObjectStore extends DefaultObjectResolver {

  /**
   * Update an existing object in the object store. The signature of the method corresponds to the merge operation of
   * the EntityManager.merge operation.
   * 
   * @param object
   *          the object to update or merge with the object store
   * @return the object stored in the object store.
   */
  public abstract <T extends Object> T update(T object);

  /**
   * Delete an existing object from the object store.
   * 
   * @param object
   */
  public abstract <T extends Object> void remove(T object);

  /**
   * Insert an existing object in the object store.
   * 
   * @param object
   */
  public abstract <T extends Object> void insert(T object);

  /**
   * Refresh an existing object from the object store.
   * 
   * @param object
   */
  public abstract <T extends Object> void refresh(T object);

  /**
   * Query for a set of objects from the object store.
   * 
   * @param qry
   *          a query string which can be handled by the native implementation
   * @param namedParameters
   *          a set of parameters used in the query
   * @param firstResult
   *          the row number of the first row to return, pass -1 to not have a first row.
   * @param maxResults
   *          the number of rows to return
   * @return the objects part of the query
   */
  public abstract List<?> query(String qryStr, Map<String, Object> namedParameters, int firstResult, int maxResults);

  /**
   * Query for a set of objects from the object store using a predefined named query.
   * 
   * @param name
   *          the name of the named query
   * @param namedParameters
   *          a set of parameters used in the query
   * @param firstResult
   *          the row number of the first row to return, pass -1 to not have a first row.
   * @param maxResults
   *          the number of rows to return
   * @return the objects part of the query
   */
  public abstract List<?> namedQuery(String name, Map<String, Object> namedParameters, int firstResult, int maxResults);

  /**
   * Query for a specific type
   * 
   * @param eClass
   *          the type to query
   * @param firstResult
   *          the row number of the first row to return, pass -1 to not have a first row.
   * @param maxResults
   *          the number of rows to return
   * @return the objects part of the query
   */
  public abstract List<?> query(EClass eClass, int firstResult, int maxResults);

  /**
   * Count a set of objects in the object store.
   * 
   * @param qry
   *          a query string which can be handled by the native implementation
   * @param namedParameters
   *          a set of parameters used in the query
   * @return the objects part of the query
   */
  public abstract long count(String qry, Map<String, Object> namedParameters);

  /**
   * Count a set of objects in the object store.
   * 
   * @param name
   *          the named query for counting
   * @param namedParameters
   *          a set of parameters used in the query
   * @return the objects part of the query
   */
  public abstract long countNamedQuery(String name, Map<String, Object> namedParameters);

  /**
   * Method to indicate that a transaction is to be started by the underlying implementation.
   */
  public void begin() {
  }

  /**
   * Method to indicate that a transaction can be committed by the underlying implementation.
   */
  public void commit() {
  }

  /**
   * Method to indicate that a transaction can be rolled back by the underlying implementation.
   */
  public void rollback() {
  }

  /**
   * Method to indicate that the underlying implementation can be closed.
   */
  public void close() {

  }

  /**
   * @return the underlying technical component taking care of actually storing and retrieving information. This can for
   *         example be the EntityManager which is used to access the database. Note that null is returned if there is
   *         no delegate.
   */
  public abstract Object getDelegate();

  /**
   * Flush any changes to the underlying store.
   */
  public void flush() {
  }

  /**
   * Return the instance of the specified class which has the id.
   */
  public abstract <T extends Object> T get(Class<T> clz, Object id);

  /**
   * Checks if an object is new for the datastore or exists. An existing object can be detached, this method will return
   * false for detached non-new objects.
   * 
   * @param o
   *          the object to check
   * @return true if the object is new, false otherwise
   * @see IdProvider#getId(Object)
   */
  public boolean isNew(Object o) {
    return null == IdProvider.getInstance().getId(o);
  }

  /**
   * Can be used to check if an object is referenced from other objects. This is usefull when trying/planning to delete
   * an object.
   * 
   * @param target
   *          the object to check if it is referenced
   * @return true if the target is refered to from other objects, false otherwise
   */
  public <T extends Object> boolean isReferenced(T target, boolean includeContainmentReferences) {
    final List<Object> referees = getReferingObjects(target, 1, includeContainmentReferences);
    return !referees.isEmpty();
  }

  /**
   * Returns all objects which refer to the passed in target object. Calls {@link #getReferingObjects(Object, int)} with
   * -1 as the second parameter.
   * 
   * @param target
   *          the target object for which the referees need to be retrieved
   * @param includeContainmentReferences
   *          also return refering objects which reference the target through an EReference with containment==true
   * @return the referencing objects
   */
  public <T extends Object> List<Object> getReferingObjects(T target, boolean includeContainmentReferences) {
    return getReferingObjects(target, -1, includeContainmentReferences);
  }

  /**
   * Returns all objects which refer to the passed in target object, will stop querying if maxResult has been reached.
   * 
   * @param target
   *          the target object for which the referees need to be retrieved
   * @param maxResult
   *          the maximum number of results to return
   * @param includeContainmentReferences
   *          also return refering objects which reference the target through an EReference with containment==true
   * @return the referencing objects
   */
  public abstract <T extends Object> List<Object> getReferingObjects(T target, int maxResult,
      boolean includeContainmentReferences);

  protected String getJavaPropertyName(EStructuralFeature eFeature) {
    final String propertyName = ModelUtils.getEAnnotation(eFeature, ModelConstants.JAVAMEMBER_ANNOTATION_KEY);
    if (propertyName == null) {
      return eFeature.getName();
    }
    return propertyName;
  }

  /**
   * Return the (JPA) entity name for the {@link EClass}. The entity name can be used to create queries.
   * 
   * @param eClass
   *          the {@link EClass} for which to get the entity name
   * @return the entity name for the {@link EClass}
   */
  public String getEntityName(EClass eClass) {
    return eClass.getName();
  }
}
