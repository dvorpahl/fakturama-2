package com.sebulli.fakturama.log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.services.events.EventBrokerFactory;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogLevel;
import org.osgi.service.log.LogListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.sebulli.fakturama.common.Activator;

import ch.qos.logback.classic.ClassicConstants;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * The LogbackAdaptor converts the LogEntry objects it receives into calls to
 * the slf4j loggers (the native interface of logback).
 * <p>
 * The error messages are written into a log file and displayed in an error view
 * in the workbench.
 * 
 * <p>
 * see <a href="https://code.google.com/p/osgi-logging/w/list">OSGi
 * logging-related tools and documentation</a>
 * </p>
 * 
 */
public class LogbackAdapter implements LogListener {

    private static final String LOGBACK_TEMPLATE = "logback.template.xml";

    private IEventBroker eventBroker;

    /**
     * The token for replacing the file name in a <tt>logback.xml</tt> file.
     */
    public static final String LOG_FILE_NAME_TOKEN = "logFileName";

    /**
     * A {@link Map} of all valid {@link Logger}s.
     */
    Map<Long, Logger> loggers = new HashMap<>();

    /**
     * Default constructor where initializing takes place.
     * 
     */
    public LogbackAdapter(final String workspaceLocation) {
        /*
         * We have to put the log file into {workspace}/Log directory. However,
         * we DON'T know at this point if the user has switched the workspace
         * or if the application is started the first time
         */
        final Path logFile = getLogfileName(workspaceLocation);
        if (logFile != null && LoggerFactory.getILoggerFactory() instanceof final LoggerContext loggerContext) {
            // determine the configuration file location
            // if a workspace is set we can adapt the log configuration file location
            // at first check if the configuration is set via a switch
            String defaultLogConfigFileName = System.getProperty(ClassicConstants.CONFIG_FILE_PROPERTY) != null
                    ? System.getProperty(ClassicConstants.CONFIG_FILE_PROPERTY)
                    : workspaceLocation;
            defaultLogConfigFileName += "/" + ClassicConstants.AUTOCONFIG_FILE;

            final URL logTemplate = FrameworkUtil.getBundle(getClass()).getResource(LOGBACK_TEMPLATE);
            try (InputStream logTemplateIS = logTemplate.openStream();) {
                // First always copy template to resource folder as template
                final Path defaultLogConfigFileTemplate = Paths.get(defaultLogConfigFileName + ".template");
                Files.copy(logTemplateIS, defaultLogConfigFileTemplate, StandardCopyOption.REPLACE_EXISTING);

                if (StringUtils.isNotBlank(defaultLogConfigFileName)) {
                    final Path defaultLogConfigFile = Paths.get(defaultLogConfigFileName);
                    if (!Files.exists(defaultLogConfigFile)) {
                        // oh, there's no configuration file... 
                        // then we create it from our own template!
                        Files.copy(defaultLogConfigFileTemplate, defaultLogConfigFile);
                    }
                    final JoranConfigurator jc = new JoranConfigurator();
                    loggerContext.putProperty(LOG_FILE_NAME_TOKEN, logFile.toString());
                    jc.setContext(loggerContext);
                    // now try to set this log file 
                    jc.doConfigure(defaultLogConfigFileName);
                }
            } catch (JoranException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This methods is called by the LogReaderService and dispatches all calls
     * to the appropriate SLF4J loggers.
     */
    @Override
    public void logged(final LogEntry log) {
        Marker bundleMarker = Activator.BUNDLE_MARKER;
        if (log.getBundle() == null || log.getBundle().getSymbolicName() == null) {
            // if there is no name, it's probably the framework emitting a log
            // This should not happen and we don't want to log something anonymous
            return;
        }

        // Retrieve a Logger object, or create it if none exists.
        Logger logger = loggers.get(log.getBundle().getBundleId());
        if (logger == null) {
            logger = LoggerFactory.getLogger(log.getBundle().getSymbolicName());
            loggers.put(log.getBundle().getBundleId(), logger);
        }

        // Show the error view (only if it is not just an information message)
        if (log.getLogLevel() == LogLevel.ERROR && !showErrorView(log)) {
            logger.error(bundleMarker, "Can't show the error message in Error View because no EventBroker is available!");
        }

        // If there is an exception available, use it, otherwise just log 
        // the message
        String message = log.getMessage();
        if (log.getException() != null) {
            switch (log.getLogLevel()) {
                case DEBUG:
                    logger.debug(message, log.getException());
                    break;
                case INFO:
                    logger.info(message, log.getException());
                    break;
                case WARN:
                    logger.warn(message, log.getException());
                    break;
                case ERROR:
                    logger.error(message, log.getException());
                    break;
                case TRACE:
                    logger.trace(message, log.getException());
                    break;
                default:
                    logger.debug(message, log.getException());
            }
        } else {
            if (!ignoreMessage(message)) {
                if (message.contains("|")) {
                    final String splittedString[] = message.split("\\|");
                    bundleMarker = MarkerFactory.getMarker(splittedString[0]);
                    message = splittedString[1];
                }
                switch (log.getLogLevel()) {
                    case TRACE:
                        logger.trace(bundleMarker, message);
                        break;
                    case DEBUG:
                        logger.debug(bundleMarker, message);
                        break;
                    case INFO:
                        logger.info(bundleMarker, message);
                        break;
                    case WARN:
                        logger.warn(bundleMarker, message);
                        break;
                    case ERROR:
                        logger.error(bundleMarker, message);
                        break;
                    default:
                        logger.debug(message, log.getException());

                }
            }
        }
    }

    /**
     * Filter unwanted messages.
     *
     * @param message
     *            the message
     */
    private boolean ignoreMessage(final String message) {
        return (message.startsWith("BundleEvent") || message.startsWith("ServiceEvent"));
    }

    /**
     * Shows the error view and sets the error text. We post a message to all
     * listeners. If one of them is responsible for showing a {@link LogEntry},
     * it grabs this {@link LogEntry} and displays it.
     * 
     * At the moment this is the ErrorView from com.sebulli.fakturama.rcp
     * bundle.
     */
    private boolean showErrorView(final LogEntry log) {
        boolean retval = true;
        if (getEventBroker() != null) {
            getEventBroker().post("Log/Error", log);
        } else {
            retval = false;
        }
        return retval;
    }

    /**
     * Return the name of the log file.
     * 
     * @param workspaceLoc
     * 
     * @return Name of the log file or an empty string, if workspace is not set
     */
    private Path getLogfileName(final String workspaceLoc) {
        // Do not save log files, if there is no workspace set
        if (StringUtils.isBlank(workspaceLoc)) {
            return null;
        }

        // Do not save log files if workspace is not created
        Path directory = Paths.get(workspaceLoc);
        if (Files.notExists(directory)) {
            return null;
        }

        // Create a sub folder "Log" if it does not exist yet.
        try {
            directory = Files.createDirectories(directory.resolve("Log/"));
        } catch (final IOException e) {
            e.printStackTrace();
        }

        // Name of the log file
        directory = directory.resolve("Error"); // .log is substituted in the configuration file, as there's a date before it

        return directory;
    }

    /**
     * @return the eventBroker
     */
    public IEventBroker getEventBroker() {
        if (eventBroker == null) {

            /*
             * FIXME
             * Ok - I know, you shouldn't do this (I know it, really!). But in
             * this case I've no other chance to get
             * the EventBroker service. If I use the "official" way like
             * 
             * IEclipseContext eclipseContext =
             * EclipseContextFactory.getServiceContext(Activator.getContext());
             * IEventBroker eventBroker = (IEventBroker)
             * eclipseContext.get(IEventBroker.class);
             * 
             * or
             * 
             * EventBrokerFactory eventBrokerFactory = new EventBrokerFactory();
             * eventBrokerFactory.compute(wb.getContext(), null);
             * 
             * I get an InjectionException because a Logger could not be found
             * in the given context. It's only in the Workbench context. But the
             * IWorkbench interface doesn't have a getContext() method.
             * Therefore
             * I use the (internal) E4Workbench class. It works for the moment,
             * but
             * if anybody out there has an idea for getting the EventBroker the
             * right way please let me know. You are welcome!
             */

            final ServiceReference<IWorkbench> serviceReference = Activator.getContext().getServiceReference(IWorkbench.class);
            if (serviceReference != null) {
                final E4Workbench wb = (E4Workbench) Activator.getContext().getService(serviceReference);
                final EventBrokerFactory eventBrokerFactory = new EventBrokerFactory();
                setEventBroker((IEventBroker) eventBrokerFactory.compute(wb.getContext(), null));
            }
        }
        return eventBroker;
    }

    /**
     * @param eventBroker
     *            the eventBroker to set
     */
    public void setEventBroker(final IEventBroker eventBroker) {
        this.eventBroker = eventBroker;
    }
}