/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2014 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: The Fakturama Team - initial API and implementation
 */

package com.sebulli.fakturama.log;

// import jakarta.inject.Provider;

import org.apache.commons.lang3.ClassUtils;
import org.eclipse.equinox.log.ExtendedLogService;
import org.osgi.service.log.LogLevel;
// import org.eclipse.e4.core.services.statusreporter.StatusReporter;
import org.osgi.service.log.LogService;

import ch.qos.logback.classic.spi.CallerData;
import jakarta.inject.Inject;

/**
 * A wrapper class for the Fakturama logger. This Logger delegates all calls to
 * the {@link LogService}, which then calls the {@link LogbackAdapter} for the
 * "real" logging (done with SLF4J and LogBack).
 */
public class FakturamaLogger implements ILogger {

    @Inject
    private ExtendedLogService delegate;

    //    // TODO prove to use this
    //    @Inject
    //    private Provider<StatusReporter> statusReporter;

    /* (non-Javadoc)
     * @see com.sebulli.fakturama.log.ILogger#debug(java.lang.String)
     */
    @Override
    public void debug(final String message, final Object... objects) {
        log(LogLevel.DEBUG, message, objects);
    }

    private void log(final LogLevel level, String message, final Object... objects) {
        if (delegate != null) {
            message = extractMessageWithCaller(message);
            switch (level) {
            case DEBUG:
                this.delegate.debug(message, objects);
                break;
            case INFO:
                this.delegate.info(message, objects);
                break;
            case WARN:
                this.delegate.warn(message, objects);
                break;
            case ERROR:
                this.delegate.error(message, objects);
                break;

            default:
                // fallback
                StringBuilder sb = new StringBuilder();
                for (Object object : objects) {
                    sb.append(object.toString());
                    sb.append("; ");
                }
                System.out.println(message + " Parameter: " + sb.toString());
                break;
            }
        } else {
            // fallback
            System.out.println(message);
        }
    }

    private String extractMessageWithCaller(String message) {
        StackTraceElement[] caller = CallerData.extract(new Throwable(), this.getClass().getName(), 1, null);
        if (caller != null && caller.length > 0) {
            message = String.format("%s.%s:%d|%s", ClassUtils.getAbbreviatedName(caller[0].getClassName(), 15), caller[0].getMethodName(),
                    caller[0].getLineNumber(), message);
        }
        return message;
    }

    /* (non-Javadoc)
     * @see com.sebulli.fakturama.log.ILogger#info(java.lang.String)
     */
    @Override
    public void info(final String message, final Object... objects) {
        log(LogLevel.INFO, message, objects);

    }

    /* (non-Javadoc)
     * @see com.sebulli.fakturama.log.ILogger#warn(java.lang.String)
     */
    @Override
    public void warn(final String message, final Object... objects) {
        log(LogLevel.WARN, message, objects);
    }

    /* (non-Javadoc)
     * @see com.sebulli.fakturama.log.ILogger#error(java.lang.String, java.lang.Throwable)
     */
    @Override
    public void error(final Throwable exception, final String message) {
        this.delegate.error(message, exception);
    }

    @Override
    public void error(final Throwable exception) {
        delegate.error("Exception occured: ", exception);
    }

    @Override
    public void error(final String message, final Object... objects) {
        log(LogLevel.ERROR, message, objects);
    }

    /**
     * @return the delegate
     */
    public LogService getDelegate() {
        return delegate;
    }

    /**
     * @param delegate
     *            the delegate to set
     */
    public void setDelegate(final ExtendedLogService delegate) {
        this.delegate = delegate;
    }

    /**
     * @param delegate
     *            the delegate to set
     */
    public void unsetDelegate(final LogService delegate) {
        this.delegate = null;
    }

    @Override
    public boolean isDebugEnabled() {
        return this.delegate.isDebugEnabled();
    }
}
