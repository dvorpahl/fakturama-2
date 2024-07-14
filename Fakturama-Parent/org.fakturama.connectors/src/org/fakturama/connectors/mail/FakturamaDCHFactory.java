package org.fakturama.connectors.mail;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.osgi.framework.FrameworkUtil;

import com.sebulli.fakturama.log.ILogger;

import jakarta.activation.DataContentHandler;
import jakarta.activation.DataContentHandlerFactory;

public class FakturamaDCHFactory implements DataContentHandlerFactory {

    @Inject
    private ILogger log;

    @Override
    public DataContentHandler createDataContentHandler(final String mimeType) {
        try {
            var handler = "org.eclipse.angus.mail.handlers." + mimeType.replace('/', '_');
            var cl = FrameworkUtil.getBundle(org.eclipse.angus.mail.handlers.handler_base.class).loadClass(handler);
            return (DataContentHandler) cl.getConstructor().newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException exc) {
            log.error(exc);
        }
        return null;
    }
}
