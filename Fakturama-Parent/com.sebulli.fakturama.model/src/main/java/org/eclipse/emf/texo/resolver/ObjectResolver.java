package org.eclipse.emf.texo.resolver;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.texo.model.ModelObject;
import org.eclipse.emf.texo.model.ModelResolver;
import org.eclipse.emf.texo.provider.IdProvider;

/**
 * Can resolve URI's and id's to objects and back. Provider of URI's for an object, is capable of creating an URI and
 * resolve an URI to an object.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 */
public interface ObjectResolver {

  /**
   * Create an {@link EObject} for the passed in object.
   */
  public EObject resolveToEObject(Object object);

  /**
   * Create or retrieve an object that represents the passed in {@link EObject}.
   */
  public Object resolveFromEObject(EObject eObject);

  /**
   * Retrieve an object using an URI. Converts the URI to an EClass and id object and then calls
   * {@link #get(EClass, Object)}.
   * 
   * @param objectUri
   *          the uri representing the object's {@link EClass} and id.
   * @return an object read from the object store
   * @see IdProvider#convertIdStringToId(EClass, String)
   */
  public Object fromUri(URI objectUri);

  /**
   * Create a unique URI from an object.
   * 
   * @param object
   *          must be an EObject or an object which can be converted to a {@link ModelObject} using the
   *          {@link ModelResolver#getModelObject(Object)}.
   * @return the unique URI for the object
   * @see #fromUri(URI)
   * @see IdProvider#getIdAsString(ModelObject)
   */
  public URI toUri(Object object);

  /**
   * Creates an uri from the type and id.
   * 
   * @param eClass
   *          the eClass defining the type
   * @param idString
   *          the id as a string
   * 
   * @return the created uri which fits to the store
   */
  public URI toURI(EClass eClass, String idString);

  /**
   * If true then this uri resolver will generate and parse uris of the form: http://localhost:8080/texo/wsxml/Library/1
   * So the EClass name and id are appended at the end with the forward slash as separator.
   */
  public boolean isUseWebServiceUriFormat();

  public void setUseWebServiceUriFormat(boolean useWebServiceUriFormat);

  public void setUri(URI uri);

  public EObject getEObject(URI uri);

  public void removeFromCache(URI objectUri);

}
