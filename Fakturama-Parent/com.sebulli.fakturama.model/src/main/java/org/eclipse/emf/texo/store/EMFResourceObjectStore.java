package org.eclipse.emf.texo.store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.texo.component.ComponentProvider;
import org.eclipse.emf.texo.converter.EMFModelConverter;
import org.eclipse.emf.texo.converter.ModelEMFConverter;

/**
 * An object store which loads the dataset from an EMF resource and persists/saves it in the same uris.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 */
public class EMFResourceObjectStore extends MemoryObjectStore {

  private Resource resource;
  private ResourceSet resourceSet;
  private URIConverter uriConverter = null;

  /**
   * Loads the underlying resource in memory
   */
  @Override
  public void begin() {
    resource = createResource();
    try {
      final boolean loadFile = resource.getResourceSet().getURIConverter().exists(resource.getURI(),
          Collections.emptyMap());
      if (loadFile) {
        resource.load(getResourceLoadOptions());
        final List<EObject> eObjects = new ArrayList<EObject>();
        final Iterator<EObject> iterator = resource.getAllContents();
        while (iterator.hasNext()) {
          eObjects.add(iterator.next());
        }

        EMFModelConverter emfModelConverter = ComponentProvider.getInstance().newInstance(EMFModelConverter.class);
        final List<Object> content = emfModelConverter.convert(eObjects);
        // add the data
        addData(content);
      } else {
        resource.getContents().clear();
      }
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  protected Map<?, ?> getResourceLoadOptions() {
    return Collections.emptyMap();
  }

  protected Map<?, ?> getResourceSaveOptions() {
    final Map<String, String> options = new HashMap<String, String>();
    options.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
    return options;
  }

  protected Resource createResource() {
    if (resource != null) {
      return resource;
    }
    resourceSet = createResourceSet();
    return resourceSet.createResource(getUri());
  }

  protected ResourceSet createResourceSet() {
    ResourceSet rs = new ResourceSetImpl();
    rs.setURIConverter(getURIConverter());
    rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml", //$NON-NLS-1$
        new XMLResourceFactoryImpl());
    rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", //$NON-NLS-1$
        new XMIResourceFactoryImpl());
    rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", //$NON-NLS-1$
        new XMIResourceFactoryImpl());
    return rs;
  }

  /**
   * Method to indicate that a transaction can be committed by the underlying implementation.
   */
  @Override
  public void commit() {
    try {
      resource.getContents().clear();
      ModelEMFConverter modelEMFConverter = ComponentProvider.getInstance().newInstance(ModelEMFConverter.class);
      final List<EObject> eObjects = new ArrayList<EObject>();
      final List<Object> toConvert = new ArrayList<Object>();
      for (List<Object> objs : getData().values()) {
        toConvert.addAll(objs);
      }
      eObjects.addAll(modelEMFConverter.convert(toConvert));
      for (EObject eObject : eObjects) {
        if (eObject.eResource() == null && eObject.eContainer() == null) {
          resource.getContents().add(eObject);
        }
      }
      resource.save(getResourceSaveOptions());
      resource = null;
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * Method to indicate that a transaction can be rolled back by the underlying implementation.
   */
  @Override
  public void rollback() {
    resource.unload();
    resource = null;
  }

  /**
   * Method to indicate that the underlying implementation can be closed.
   */
  @Override
  public void close() {
    resource = null;
  }

  public URIConverter getURIConverter() {
    if (uriConverter == null) {
      uriConverter = ComponentProvider.getInstance().newInstance(TexoEMFResourceURIConverter.class);
    }
    return uriConverter;
  }

  public void setURIConverter(URIConverter converter) {
    uriConverter = converter;
  }

  protected Resource getResource() {
    return resource;
  }

  protected void setResource(Resource resource) {
    this.resource = resource;
  }

}
