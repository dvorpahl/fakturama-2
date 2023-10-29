/*
 * Fakturama - Free Invoicing Software - http://fakturama.sebulli.com
 * 
 * Copyright (C) 2012 Gerd Bartelt
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Gerd Bartelt - initial API and implementation
 */

package com.sebulli.fakturama;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.core.runtime.IBundleGroupProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.update.configurator.IPlatformConfiguration;
import org.eclipse.update.internal.configurator.FeatureEntry;
import org.eclipse.update.internal.configurator.PlatformConfiguration;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import com.opcoach.e4.preferences.IPreferenceStoreProvider;
import com.sebulli.fakturama.preferences.FakturamaPreferenceStoreProvider;

// import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author Gerd Bartelt
 */
public class Activator implements BundleActivator, IBundleGroupProvider {

    // The bundle ID (Bundle-SymbolicName)
    public static final String PLUGIN_ID = "com.sebulli.fakturama.rcp";

    // The shared instance
    private static BundleContext context;
    ServiceRegistration<?> bundleGroupProviderSR;
    private PlatformConfiguration configuration;

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static BundleContext getContext() {
        return context;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void start(final BundleContext bundleContext) throws Exception {
        Activator.context = bundleContext;
        // background color for focused widgets
        //        JFaceResources.getColorRegistry().put(Constants.COLOR_BGYELLOW, new RGB(255, 255, 225));

        // perhaps: set RTL mode and configure the Workbench like so:
        if (BooleanUtils.toBoolean(System.getProperty("force.rtl"))) {
            Window.setDefaultOrientation(SWT.RIGHT_TO_LEFT);
        }

        // background for Browser
        //		JFaceResources.getColorRegistry().put(Constants.COLOR_WHITE, new RGB(0xff, 0xff, 0xff));
        registerBundleGroupProvider();

        // register preference store provider
        bundleContext.registerService(IPreferenceStoreProvider.class, FakturamaPreferenceStoreProvider.getInstance(), null);
        //        generatePersistenceUnits();
    }

    //    /**
    //     *  here for backup, needs to be removed
    //     */
    //    private void generatePersistenceUnits() {
    //
    //        // create old datasource
    //        Map<String, Object> oldProperties = new HashMap<>();
    //        oldProperties.put(PersistenceUnitProperties.JDBC_URL, "jdbc:hsqldb:file:///fakturamaDB");//DefaultScope.INSTANCE.getNode("OLD_JDBC_URL").get("OLD_JDBC_URL", ""));
    //        oldProperties.put(PersistenceUnitProperties.JDBC_DRIVER, "org.hsqldb.jdbc.JDBCDriver");
    //        oldProperties.put(PersistenceUnitProperties.JDBC_USER, "sa");
    //        oldProperties.put(PersistenceUnitProperties.JDBC_PASSWORD, "");
    //        oldProperties.put(PersistenceUnitProperties.LOGGING_LEVEL, "INFO");
    //        oldProperties.put(PersistenceUnitProperties.WEAVING, "false");
    //        oldProperties.put(PersistenceUnitProperties.WEAVING_INTERNAL, "false");
    //        PersistenceProvider persistenceProvider = new org.eclipse.persistence.jpa.PersistenceProvider();
    //        EntityManagerFactory entityManagerFactory = persistenceProvider.createEntityManagerFactory("origin-datasource", oldProperties);
    //        //        EntityManager entityManager = entityManagerFactory.createEntityManager();
    //
    //        Persistence.createEntityManagerFactory("origin-datasource", oldProperties);
    //
    //        // create new datasource
    //        Map<String, Object> newProperties = new HashMap<>();
    //        newProperties.put(PersistenceUnitProperties.JDBC_URL, DefaultScope.INSTANCE.getNode("").get(PersistenceUnitProperties.JDBC_DRIVER, ""));
    //        newProperties.put(PersistenceUnitProperties.JDBC_DRIVER, DefaultScope.INSTANCE.getNode("").get(PersistenceUnitProperties.JDBC_URL, ""));
    //        newProperties.put(PersistenceUnitProperties.JDBC_USER, DefaultScope.INSTANCE.getNode("").get(PersistenceUnitProperties.JDBC_USER, ""));
    //        newProperties.put(PersistenceUnitProperties.JDBC_PASSWORD, DefaultScope.INSTANCE.getNode("").get(PersistenceUnitProperties.JDBC_PASSWORD, ""));
    //        newProperties.put(PersistenceUnitProperties.LOGGING_LEVEL, "INFO");
    //        //        newProperties.put(PersistenceUnitProperties.WEAVING, "false");
    //        newProperties.put(PersistenceUnitProperties.WEAVING_INTERNAL, "false");
    //
    //        Persistence.createEntityManagerFactory("unconfigured2", newProperties);
    //
    //    }

    private void registerBundleGroupProvider() {
        final String serviceName = IBundleGroupProvider.class.getName();
        try {
            //don't register the service if this bundle has already registered it declaratively
            ServiceReference<?>[] refs = getContext().getServiceReferences(serviceName, null);
            if (refs != null) {
                for (int i = 0; i < refs.length; i++) {
                    if (PLUGIN_ID.equals(refs[i].getBundle().getSymbolicName())) {
                        return;
                    }
                }
            }
        } catch (InvalidSyntaxException e) {
            //can't happen because we don't pass a filter
        }
        bundleGroupProviderSR = getContext().registerService(serviceName, this, null);
    }

    @Override
    public String getName() {
        return "Bundle Group Provider";
    }

    @Override
    public IBundleGroup[] getBundleGroups() {
        if (configuration == null) {
            return new IBundleGroup[0];
        }

        IPlatformConfiguration.IFeatureEntry[] features = configuration.getConfiguredFeatureEntries();
        List<IBundleGroup> bundleGroups = new ArrayList<>(features.length);
        for (int i = 0; i < features.length; i++) {
            if (features[i] instanceof FeatureEntry && ((FeatureEntry) features[i]).hasBranding()) {
                bundleGroups.add((IBundleGroup) features[i]);
            }
        }
        return bundleGroups.toArray(new IBundleGroup[bundleGroups.size()]);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void stop(final BundleContext bundleContext) throws Exception {
        Activator.context = null;
    }
}
