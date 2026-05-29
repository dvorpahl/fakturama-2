/**
 * <copyright>
 *
 * Copyright (c) 2012 Springsite BV (The Netherlands) and others
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
package org.eclipse.emf.texo.store;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.texo.component.TexoComponent;

/**
 * The base class of the Texo resource implementations.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 */
public abstract class TexoResource extends ResourceImpl implements TexoComponent {

  public static final String TYPE_PARAMETER = "type"; //$NON-NLS-1$
  public static final String TYPES_PARAMETER = "types"; //$NON-NLS-1$

  private EObjectStore eObjectStore;

  private Set<EObject> modifiedObjects = new LinkedHashSet<EObject>();
  private Set<EObject> newObjects = new LinkedHashSet<EObject>();
  private Set<EObject> deletedObjects = new LinkedHashSet<EObject>();

  private boolean loaded = false;

  private boolean isSaving = false;

  private ObjectModificationTrackingAdapter objectModificationTrackingAdapter = createObjectModificationTrackingAdapter();

  public TexoResource() {
    setLoaded(true);
  }

  public TexoResource(URI uri) {
    super(uri);
    uriSet(uri);
    setLoaded(true);
  }

  @Override
  protected boolean isAttachedDetachedHelperRequired() {
    return true;
  }

  // is called for contained objects, these should not be stored
  // in the newObjects list.
  @Override
  protected void attachedHelper(EObject eObject) {
    if (deletedObjects.contains(eObject)) {
      deletedObjects.remove(eObject);
    } else if (!isLoading() && !newObjects.contains(eObject)) {
      newObjects.add(eObject);
    }
    eObject.eAdapters().add(objectModificationTrackingAdapter);
  }

  @Override
  protected void detachedHelper(EObject eObject) {
    if (!isSaving && !isUnloading() && !deletedObjects.contains(eObject)) {
      deletedObjects.add(eObject);
    }
    eObject.eAdapters().remove(objectModificationTrackingAdapter);
    super.detachedHelper(eObject);
  }

  public boolean isUnloading() {
    // unloadingContents is a protected member of the super class
    return unloadingContents != null;
  }

  @Override
  public void setURI(URI uri) {
    super.setURI(uri);
    uriSet(uri);
  }

  protected void uriSet(URI theUri) {
    getEObjectStore().setUri(theUri);
  }

  @Override
  public String getURIFragment(EObject eObject) {
    return getEObjectStore().getQualifiedIdString(eObject);
  }

  @Override
  public EObject getEObject(String uriFragment) {
    final EObject eObject = getEObjectStore().getFromQualifiedIdString(uriFragment);
    if (eObject != null && eObject.eResource() == null) {
      addLoadedObject(eObject);
    }
    return eObject;
  }

  @Override
  public synchronized void save(Map<?, ?> options) throws IOException {
    try {
      isSaving = true;
      final List<EObject> toInsert = new ArrayList<EObject>();
      toInsert.addAll(newObjects);
      final List<EObject> toUpdate = new ArrayList<EObject>();
      toUpdate.addAll(modifiedObjects);

      getEObjectStore().persist(toInsert, toUpdate, new ArrayList<EObject>(deletedObjects));

      newObjects.clear();
      modifiedObjects.clear();
      deletedObjects.clear();
    } finally {
      isSaving = false;
    }
  }

  @Override
  public void load(Map<?, ?> options) throws IOException {
    final List<String> types = getTypes();
    for (String type : types) {
      query("select e from " + type + " e", Collections.<String, Object> emptyMap(), 0, -1); //$NON-NLS-1$//$NON-NLS-2$
    }
    loaded = true;
  }

  @Override
  public boolean isLoaded() {
    if (loaded) {
      return true;
    }
    // no types to load, we are loaded
    if (getTypes().isEmpty()) {
      return true;
    }
    return loaded;
  }

  @Override
  protected void doUnload() {
    super.doUnload();
    newObjects.clear();
    modifiedObjects.clear();
    deletedObjects.clear();
    if (eObjectStore != null) {
      eObjectStore.clearCache();
    }
    loaded = false;
  }

  // some super class methods which should not be called in this implementation
  @Override
  protected EObject getEObject(List<String> uriFragmentPath) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected EObject getEObjectForURIFragmentRootSegment(String uriFragmentRootSegment) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected EObject getEObjectByID(String id) {
    return getEObject(id);
  }

  @Override
  public void setIntrinsicIDToEObjectMap(Map<String, EObject> intrinsicIDToEObjectMap) {
    throw new UnsupportedOperationException();
  }

  /**
   * Will query the {@link EObjectStore} and add the returned objects to this resource. If you don't want to add
   * returned objects to this resource then get the object store ({@link #getEObjectStore()} and call the query method
   * on the object store.
   */
  public List<EObject> query(String qryStr, Map<String, Object> namedParameters, int firstResult, int maxResults) {
    final List<EObject> eObjects = getEObjectStore().query(qryStr, namedParameters, firstResult, maxResults);
    for (EObject eObject : eObjects) {
      if (eObject.eResource() == null) {
        addLoadedObject(eObject);
      }
    }
    return eObjects;
  }

  protected void addLoadedObject(EObject eObject) {
    try {
      // protected member from superclass
      isLoading = true;

      if (eObject.eResource() == null) {
        if (eObject.eContainer() != null) {
          addLoadedObject(eObject.eContainer());
        } else {
          getContents().add(eObject);
        }
      }
    } finally {
      // protected member from superclass
      isLoading = false;
    }
  }

  public void refresh(EObject eObject) {
    getEObjectStore().refresh(eObject);
  }

  /**
   * Calls {@link EObjectStore#getReferingObjects(EObject, int, boolean)} and adds the returned objects to this
   * resource. If you don't want objects to be added to this resource then get the object store (
   * {@link #getEObjectStore()} and call the same method on the {@link EObjectStore}.
   */
  public List<EObject> getReferingObjects(EObject target, int maxResult, boolean includeContainmentReferences) {
    final List<EObject> eObjects = getEObjectStore()
        .getReferingObjects(target, maxResult, includeContainmentReferences);
    for (EObject eObject : eObjects) {
      if (eObject.eResource() == null) {
        addLoadedObject(eObject);
      }
    }
    return eObjects;
  }

  public synchronized EObjectStore getEObjectStore() {
    if (eObjectStore == null) {
      eObjectStore = createEObjectStore();
    }
    return eObjectStore;
  }

  /**
   * Is called when this resource does not have an objectstore. Sub class is expected to create a new one.
   */
  protected EObjectStore createEObjectStore() {
    throw new UnsupportedOperationException("Override this method or set an object store"); //$NON-NLS-1$
  }

  public void setEObjectStore(EObjectStore eObjectStore) {
    this.eObjectStore = eObjectStore;
  }

  private List<String> getTypes() {
    final List<String> values = new ArrayList<String>();
    if (getURI() == null) {
      return values;
    }
    final String queryString = getURI().query();
    if (queryString == null) {
      return values;
    }
    try {
      for (String param : queryString.split("&")) { //$NON-NLS-1$
        String keyValue[] = param.split("="); //$NON-NLS-1$
        String key = URLDecoder.decode(keyValue[0], "UTF-8"); //$NON-NLS-1$
        if (TYPE_PARAMETER.equals(key) || TYPES_PARAMETER.equals(key)) {
          if (keyValue.length > 1) {
            final String value = URLDecoder.decode(keyValue[1], "UTF-8"); //$NON-NLS-1$
            if (value.contains(",")) { //$NON-NLS-1$
              for (String val : value.split(",")) { //$NON-NLS-1$
                values.add(val.trim());
              }
            } else {
              values.add(value);
            }
            return values;
          }
        }
      }
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
    return values;
  }

  @Override
  protected Adapter createModificationTrackingAdapter() {
    return new ResourceModificationTrackingAdapter();
  }

  protected ObjectModificationTrackingAdapter createObjectModificationTrackingAdapter() {
    return new ObjectModificationTrackingAdapter();
  }

  /**
   * Overridden/replace {@link ModificationTrackingAdapter} to prevent modification during save/load.
   */
  private class ResourceModificationTrackingAdapter extends ModificationTrackingAdapter {
    @Override
    public void notifyChanged(Notification notification) {
      // don't track changes during load or save
      if (isLoading() || isSaving || isUnloading()) {
        return;
      }
      super.notifyChanged(notification);
    }
  }

  /**
   * An adapter which keeps track of which objects changed for modification
   */
  protected class ObjectModificationTrackingAdapter extends AdapterImpl {
    @Override
    public void notifyChanged(Notification notification) {
      // don't track changes during load or save
      if (isLoading() || isSaving || isUnloading()) {
        return;
      }

      if (!notification.isTouch() && notification.getNotifier() instanceof EObject) {
        final EObject notifier = (EObject) notification.getNotifier();
        if (notifier.eResource() == TexoResource.this && !modifiedObjects.contains(notifier)
            && !newObjects.contains(notifier)) {
          modifiedObjects.add(notifier);
        }
      }
    }
  }

  @Override
  protected void unloaded(InternalEObject internalEObject) {
    super.unloaded(internalEObject);
    final URI objectUri = getEObjectStore().toUri(internalEObject);
    getEObjectStore().removeFromCache(objectUri);
  }

  /**
   * Set the number of levels of children to retrieve when requesting an object from the server through a request.
   * 
   * The default for the child levels is 2 (one level).
   * 
   * @param childLevels
   *          a number larger than 0.
   */
  public void setChildLevels(int childLevels) {
    getEObjectStore().setChildLevels(childLevels);
  }

  /**
   * Return the current child levels setting. If -1 is returned then the system will use the default system setting
   * (which is 2, meaning one child level).
   */
  public int getChildLevels() {
    return getEObjectStore().getChildLevels();
  }
}
