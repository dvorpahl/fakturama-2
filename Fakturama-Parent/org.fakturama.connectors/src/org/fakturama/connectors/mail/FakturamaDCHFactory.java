package org.fakturama.connectors.mail;

import java.lang.reflect.InvocationTargetException;

import org.osgi.framework.FrameworkUtil;

import jakarta.activation.DataContentHandler;
import jakarta.activation.DataContentHandlerFactory;

public class FakturamaDCHFactory implements DataContentHandlerFactory {

	@Override
	public DataContentHandler createDataContentHandler(String mimeType) {
		try {
			var handler = "org.eclipse.angus.mail.handlers." + mimeType.replace('/', '_');
			var cl = FrameworkUtil.getBundle(org.eclipse.angus.mail.handlers.handler_base.class).loadClass(handler);
			return (DataContentHandler)
				    cl.getConstructor().newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
