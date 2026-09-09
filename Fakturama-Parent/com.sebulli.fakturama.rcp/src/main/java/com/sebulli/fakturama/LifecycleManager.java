package com.sebulli.fakturama;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessAdditions;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ISaveHandler;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.osgi.service.prefs.BackingStoreException;

import com.opcoach.e4.preferences.IPreferenceStoreProvider;
import com.sebulli.fakturama.dao.ItemAccountTypeDAO;
import com.sebulli.fakturama.dao.ItemListTypeCategoriesDAO;
import com.sebulli.fakturama.dao.PaymentsDAO;
import com.sebulli.fakturama.dao.PropertiesDAO;
import com.sebulli.fakturama.dao.ShippingsDAO;
import com.sebulli.fakturama.dao.UnCefactCodeDAO;
import com.sebulli.fakturama.dao.VatsDAO;
import com.sebulli.fakturama.dbservice.IDbUpdateService;
import com.sebulli.fakturama.exception.FakturamaStoringException;
import com.sebulli.fakturama.handlers.SaveHandler;
import com.sebulli.fakturama.parts.BrowserEditor;
import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.log.ILogger;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.UNTDID4461;
import com.sebulli.fakturama.misc.UNTDID5305;
import com.sebulli.fakturama.model.CEFACTCode;
import com.sebulli.fakturama.model.FakturamaModelFactory;
import com.sebulli.fakturama.model.FakturamaModelPackage;
import com.sebulli.fakturama.model.Payment;
import com.sebulli.fakturama.model.Shipping;
import com.sebulli.fakturama.model.ShippingVatType;
import com.sebulli.fakturama.model.UserProperty;
import com.sebulli.fakturama.model.VAT;
import com.sebulli.fakturama.preferences.PreferencesInDatabase;
import com.sebulli.fakturama.resources.core.TemplateResourceManager;
import com.sebulli.fakturama.startup.ConfigurationManager;
import com.sebulli.fakturama.startup.ISplashService;

import jakarta.persistence.PersistenceException;

/**
 * The LifecycleManager controls the start and the end of an application.
 *
 */
public class LifecycleManager {

    /**
     * The name of the dialog settings file (value
     * <code>"dialog_settings.xml"</code>).
     */
    private static final String FN_DIALOG_SETTINGS = "dialog_settings.xml"; //$NON-NLS-1$

    private static final String CODELISTS_XLSX = "codelists.xlsx";

    @Inject
    private IEclipseContext context;

    @Inject
    private ILogger log;

    @Inject
    @Preference
    private IEclipsePreferences eclipsePrefs;

    @Inject
    private IDbUpdateService dbUpdateService;

    @Inject
    @Translation
    protected Messages msg;

    private IDialogSettings dialogSettings;

    // Accumulated, comma-separated splash progress text (see appendProgress()'s Javadoc) - a
    // plain instance field rather than a parameter threaded through checksBeforeStartup() and
    // fillWithInitialData() so every setMessage() call site in both methods can append to the
    // same running list without changing method signatures.
    private String splashProgress = "";

    // Timestamp of the last appendProgress() call, 0 until the first one - used to only make up
    // the *remaining* balance of MIN_STEP_DWELL_MILLIS instead of blocking the full amount on
    // every single call (see appendProgress()'s Javadoc).
    private long lastProgressAt;

    private static final boolean RESTART_APPLICATION = true;

    private Job dbInitJob;

    @PostContextCreate
    public void checksBeforeStartup(final ISplashService splashService, final IEventBroker eventBroker) {
        // Must happen before the first org.eclipse.swt.browser.Browser is created anywhere
        // (e.g. BrowserEditor) - WebKitGTK reads this JVM system property once, in a static
        // initializer. GTK/Linux only; for development use only (self-signed dev servers).
        if (eclipsePrefs.getBoolean(Constants.PREFERENCES_BROWSER_ALLOW_INVALID_CERTS, false)) {
            System.setProperty("org.eclipse.swt.internal.webkitgtk.ignoretlserrors", "true");
        }

        splashService.setSplashPluginId(Activator.PLUGIN_ID);
        splashService.setSplashImagePath("splash-rcp.png");
        splashService.setTotalWork(40);
        splashService.open();
        splashService.setTextColor(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
        // Same version string already used for the "About" dialog/saved program version
        // (see fillWithInitialData() below) and the FKT bridge - shows the actual running
        // build (e.g. "2.2.1.rc") instead of a generic, unchanging label.
        final String appVersion = Platform.getProduct().getDefiningBundle().getVersion().toString();
        appendProgress(splashService, "Loading v" + appVersion);

        // There should be a better way to close the Splash
        // see https://bugs.eclipse.org/bugs/show_bug.cgi?id=376821
        //
        // unsubscribe() BEFORE close(), not after: several parts can activate in a rapid,
        // nested burst while the initial perspective is being built (more likely on a fresh
        // profile with no saved layout to just restore instead of freshly constructing one),
        // and each activation publishes its own ACTIVATE event synchronously. Unsubscribing
        // first shrinks the window in which a nested event re-enters this same handler before
        // the previous call has deregistered it - closing the splash Shell can itself pump
        // further synchronous SWT/event-broker activity, so doing it last matters. Combined
        // with SplashServiceImpl#close()'s own isDisposed() guard as a second line of defense,
        // in case a nested event still slips through this ordering.
        eventBroker.subscribe(UIEvents.UILifeCycle.ACTIVATE, new EventHandler() {
            @Override
            public void handleEvent(final Event event) {
                eventBroker.unsubscribe(this);
                splashService.close();
            }
        });

        final ConfigurationManager configMgr = ContextInjectionFactory.make(ConfigurationManager.class, context);
        // launch ConfigurationManager.checkFirstStart
        configMgr.checkAndUpdateConfiguration();

        // splashService.setMessage("checks before startup");
        // at first we check if we have to migrate an older version
        // check if the db connection is set
        if (StringUtils.isNotEmpty(eclipsePrefs.get(PersistenceUnitProperties.JDBC_DRIVER, ""))) {

            // comment this if you want to generate or update the database with
            // EclipseLink
            // (but don't forget to enable it in persistence.xml)

            appendProgress(splashService, "checking database");

            final boolean dbupdate = dbUpdateService.updateDatabase();
            if (!dbupdate) {
                log.error("couldn't create or update database!");
                MessageDialog.openError(splashService.getSplashShell(), msg.dialogMessageboxTitleError,
                        withDetail(msg.startErrorNodatabase, dbUpdateService.getLastError()));
                System.exit(1);
            }

            splashService.worked(15);

            appendProgress(splashService, "initialize classes");
            dbInitJob = new Job("initDb") {

                @Override
                protected IStatus run(final IProgressMonitor monitor) {
                    log.debug("start DAOs - begin");
                    try {
                        // these DAOs are needed in a later stage of
                        // initialization
                        // (see fillWithInitialData)
                        context.set(VatsDAO.class, ContextInjectionFactory.make(VatsDAO.class, context));
                        context.set(ShippingsDAO.class, ContextInjectionFactory.make(ShippingsDAO.class, context));
                        context.set(PaymentsDAO.class, ContextInjectionFactory.make(PaymentsDAO.class, context));
                        context.set(UnCefactCodeDAO.class, ContextInjectionFactory.make(UnCefactCodeDAO.class, context));
                        context.set(ItemListTypeCategoriesDAO.class, ContextInjectionFactory.make(ItemListTypeCategoriesDAO.class, context));
                        context.set(ItemAccountTypeDAO.class, ContextInjectionFactory.make(ItemAccountTypeDAO.class, context));
                        context.set(PropertiesDAO.class, ContextInjectionFactory.make(PropertiesDAO.class, context));
                        log.debug("start DAOs - end");

                        return Status.OK_STATUS;
                    } catch (final PersistenceException e) {
                        log.error(e, "Datenbank kann nicht gestartet werden. Anwendung wird beendet.");
                        MessageDialog.openError(splashService.getSplashShell(), msg.dialogMessageboxTitleError,
                                withDetail(msg.startErrorNodatabase, e));
                        return Status.CANCEL_STATUS;
                    }
                }
            };

            dbInitJob.schedule(10); // timeout that the OSGi env can be started
                                    // before
            try {
                fillWithInitialData(splashService);
            } catch (final FakturamaStoringException sqlex) {
                log.error(sqlex, "couldn't fill with initial data! " + sqlex);

            }
            splashService.worked(5);

            // register event handler for saving and closing editors before
            // shutdown
            eventBroker.subscribe(UIEvents.UILifeCycle.APP_SHUTDOWN_STARTED, event -> {
                // formerly known as Workbench.busyClose()
                closeAndSaveEditors(context);
                // eventBroker.unsubscribe(eventHandler)
            });

        } else {
            // if db connection is not set, it is a certain sign that the
            // application
            // is started the first time
            eventBroker.subscribe(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE, new AppStartupCompleteEventHandler(context, RESTART_APPLICATION));
        }
    }

    /**
     * Used to always call the "closeAll" command (see {@link com.sebulli.fakturama.handlers.CloseAllHandler})
     * here, which - after prompting to save any dirty document - hides *and removes* every open
     * document editor from the model, no matter whether it was dirty or not. That's fine for the
     * user manually invoking "close all", but here, right before the model gets serialized for the
     * next launch, it meant every document tab was gone from workbench.xmi, dirty or not - so
     * "reopen the same product/invoice I had open" could never work, independent of the
     * transientData vs. persistedState issue fixed in CallEditor#createEditorPart().
     * <p>
     * Now only dirty documents get the same prompt-then-close treatment (unsaved edits still
     * shouldn't silently survive as a half-finished, un-reconstructable editor state - restoring
     * in-progress unsaved field values across a restart is out of scope). Already-saved (clean)
     * documents are left untouched in the model, so they - and, since CallEditor mirrors their
     * record id into persistedState, their actual content too - are still there next launch.
     */
    @PreSave
    public final void closeAndSaveEditors(final IEclipseContext context2) {
        final EModelService modelService = context.get(EModelService.class);
        final EPartService partService = context.get(EPartService.class);
        final MApplication application = context.get(MApplication.class);
        final MPartStack documentPartStack = (MPartStack) modelService.find(Constants.DETAILPANEL_ID, application);
        if (documentPartStack == null) {
            return;
        }
        final List<MStackElement> stackElements = documentPartStack.getChildren().stream()
                .filter(elem -> !elem.getElementId().equals(BrowserEditor.ID) && elem.getTags().contains("documentWindow"))
                .collect(Collectors.toList());
        for (final MStackElement stackElement : stackElements) {
            final MPart documentPart = (MPart) stackElement;
            if (documentPart.getContext() != null && documentPart.isDirty()) {
                // savePart() can return false if the user cancels the save-confirmation dialog -
                // don't force the part closed (and its unsaved edits lost) in that case.
                if (partService.savePart(documentPart, true)) {
                    partService.hidePart(documentPart, true);
                }
            }
        }
    }

    /**
     * Some steps to do before workbench is showing. If a new database was
     * created, fill some data with initial values
     * 
     * @param splashService
     * @throws FakturamaStoringException
     */
    /**
     * Appends an item to a running, comma-separated splash progress message (e.g. "Loading
     * Application, checking database, initialize classes") and pushes the updated text to the
     * splash screen, instead of replacing it outright like a plain setMessage() call would.
     * <p>
     * Originally only used for the VAT/Shipping/Payment sub-steps inside fillWithInitialData()
     * (which only run on a fresh/reinit database) - every *other* step in
     * checksBeforeStartup()/fillWithInitialData() still called setMessage() directly, so on a
     * normal (non-reinit) start the whole sequence from "Loading Application" onward replaces
     * itself several times within well under a second, and a user only ever perceives whichever
     * message happened to be showing at the one moment their eyes actually caught the splash -
     * in practice always the last one ("Loading preferences...") since that is also the one
     * showing right before the splash closes. Now used for every step in both methods, so the
     * splash instead accumulates a readable history of what happened, however fast it happened.
     * <p>
     * Also holds each update visible for a minimum dwell time (see MIN_STEP_DWELL_MILLIS) - the
     * accumulating text alone guarantees the *final* frame shows the full history, but on a fast
     * (typically local/demo) database the individual steps still replace each other faster than a
     * human can consciously read them appearing one at a time.
     * <p>
     * The dwell only makes up the *remaining* balance of MIN_STEP_DWELL_MILLIS since the previous
     * call, not a flat 150ms added on top of every single call regardless of how much real work
     * (DB update, class init, ...) already ran between them - a normal (non-reinit) startup, whose
     * steps are mostly real work rather than near-instant DB writes, therefore no longer pays a
     * blocking ~150ms tax per step it didn't need.
     * <p>
     * A first attempt paused with a plain Thread.sleep() here, which made no visible difference
     * at all (confirmed: still only the final accumulated string ever appeared, even across
     * several distinct steps that should each have been visible for 150ms). checksBeforeStartup()
     * runs on the UI thread itself, and Shell#update() (called inside setMessage()) only flushes
     * *already pending, already-mapped* paint requests - it does not pump the display's event
     * queue, which is what actually lets the window manager map/expose an SWT.TOOL splash shell
     * for the first time and process each subsequent repaint. A plain sleep on this thread blocks
     * that pumping entirely for its whole duration, so the shell likely never got shown - or
     * re-painted - at all until something *else* later in startup finally ran a real event loop,
     * by which point every queued text change had long since been overwritten by the next one.
     * Spending the dwell time in Display#readAndDispatch() instead keeps the event queue moving,
     * so the window can actually be mapped and each intermediate frame actually painted.
     */
    private static final int MIN_STEP_DWELL_MILLIS = 150;

    private void appendProgress(final ISplashService splashService, final String item) {
        final long now = System.currentTimeMillis();
        final long elapsedSincePrevious = lastProgressAt == 0 ? 0 : now - lastProgressAt;
        lastProgressAt = now;

        splashProgress = splashProgress.isEmpty() ? item : splashProgress + ", " + item;
        splashService.setMessage(splashProgress);

        if (elapsedSincePrevious >= MIN_STEP_DWELL_MILLIS) {
            // The previous message was already visible long enough thanks to real work done
            // between calls - no need to also block for the flat dwell time here.
            return;
        }

        final Shell splashShell = splashService.getSplashShell();
        if (splashShell == null || splashShell.isDisposed()) {
            return;
        }
        final Display display = splashShell.getDisplay();
        final long deadline = now + (MIN_STEP_DWELL_MILLIS - elapsedSincePrevious);
        while (System.currentTimeMillis() < deadline) {
            if (!display.readAndDispatch()) {
                // Nothing queued right now - a short real sleep (not a busy-loop) until the
                // deadline, or until dispatch() has something to do again, whichever is sooner.
                try {
                    Thread.sleep(10);
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    /**
     * Appends the root cause's message to a user-facing error text, so a technical hint (connection refused,
     * access denied, lock wait timeout, ...) is shown alongside the friendly explanation instead of leaving the
     * user with only a generic guess as to what went wrong.
     */
    private String withDetail(final String message, final Throwable cause) {
        if (cause == null) {
            return message;
        }
        return message + "\n\n" + ExceptionUtils.getRootCauseMessage(cause);
    }

    private void fillWithInitialData(final ISplashService splashService) throws FakturamaStoringException {
        // wait some seconds until dbInitJob is finished.
        // else you get a NPE!!!
        try {
            dbInitJob.join();
        } catch (final InterruptedException e) {
            log.info("ready to go ahead and looking for default values in db.");
        }

        appendProgress(splashService, "preparing data");
        splashService.worked(1);

        final FakturamaModelFactory modelFactory = FakturamaModelPackage.MODELFACTORY;

        // the following is a workaround for the error if the database isn't
        // available.
        // there was only a strange error message which doesn't helped the user.
        // If no database is available, a NPE is thrown.
        VatsDAO vatsDAO = null;
        try {
            vatsDAO = context.get(VatsDAO.class);
        } catch (final NullPointerException npe) {
            MessageDialog.openError(splashService.getSplashShell(), msg.dialogMessageboxTitleError,
                    withDetail(msg.startErrorNodatabase, npe));
            System.exit(1);
        }

        final ShippingsDAO shippingsDAO = context.get(ShippingsDAO.class);
        final PaymentsDAO paymentsDAO = context.get(PaymentsDAO.class);
        final UnCefactCodeDAO unCefactCodeDAO = context.get(UnCefactCodeDAO.class);
        final ItemListTypeCategoriesDAO itemListTypeCategoriesDAO = context.get(ItemListTypeCategoriesDAO.class);
        final ItemAccountTypeDAO itemAccountTypeDAO = context.get(ItemAccountTypeDAO.class);
        final PropertiesDAO propertiesDao = context.get(PropertiesDAO.class);

        // Fill some default data
        // see old sources:
        // com.sebulli.fakturama.data.Data#fillWithInitialData()
        final ServiceReference<IPreferenceStoreProvider> serviceReference = Activator.getContext().getServiceReference(IPreferenceStoreProvider.class);
        if (serviceReference == null) {
            log.error("no preference store available, Service Ref is very null");
        }
        IPreferenceStore defaultValuesNode = EclipseContextFactory.getServiceContext(Activator.getContext()).get(IPreferenceStore.class);
        if (defaultValuesNode == null) {
            defaultValuesNode = Activator.getContext().getService(serviceReference).getPreferenceStore();
            EclipseContextFactory.getServiceContext(Activator.getContext()).set(IPreferenceStore.class, defaultValuesNode);
            if (EclipseContextFactory.getServiceContext(Activator.getContext()).get(IPreferenceStore.class) == null) {
                log.error("Cannot setup prefstore");
            }

        }

        splashService.worked(1);

        // Set the default values to this entries
        if (eclipsePrefs.getBoolean("isreinit", false) || eclipsePrefs.getLong(Constants.DEFAULT_VAT, Long.valueOf(0)) == 0L) {
            appendProgress(splashService, "VAT");
            VAT defaultVat = modelFactory.createVAT();
            defaultVat.setName(msg.dataDefaultVat);
            defaultVat.setDescription(msg.dataDefaultVatDescription);
            defaultVat.setTaxValue(Double.valueOf(0.0));
            defaultVat.setCode(UNTDID5305.S);
            defaultVat = vatsDAO.findOrCreate(defaultVat);
            // defaultValuesNode.setValue(Constants.DEFAULT_VAT,
            eclipsePrefs.putLong(Constants.DEFAULT_VAT, defaultVat.getId());
        }

        splashService.worked(1);

        if (eclipsePrefs.getBoolean("isreinit", false) || eclipsePrefs.getLong(Constants.DEFAULT_SHIPPING, Long.valueOf(0)) == 0L) {
            appendProgress(splashService, "Shipping");
            Shipping defaultShipping = modelFactory.createShipping();
            defaultShipping.setName(msg.dataDefaultShipping);
            defaultShipping.setDescription(msg.dataDefaultShippingDescription);
            defaultShipping.setShippingValue(Double.valueOf(0.0));
            defaultShipping.setAutoVat(ShippingVatType.SHIPPINGVATGROSS);
            defaultShipping.setShippingVat(vatsDAO.findById(eclipsePrefs.getLong(Constants.DEFAULT_VAT, Long.valueOf(0))));
            defaultShipping = shippingsDAO.findOrCreate(defaultShipping);
            // defaultValuesNode.setValue(Constants.DEFAULT_SHIPPING,
            eclipsePrefs.putLong(Constants.DEFAULT_SHIPPING, defaultShipping.getId());
        }

        splashService.worked(1);

        if (eclipsePrefs.getBoolean("isreinit", false) || eclipsePrefs.getLong(Constants.DEFAULT_PAYMENT, Long.valueOf(0)) == 0L) {
            appendProgress(splashService, "Payment");
            Payment defaultPayment = modelFactory.createPayment();
            // defaultPayment.setCode(Constants.TAX_DEFAULT_CODE);
            defaultPayment.setName(msg.dataDefaultPayment);
            defaultPayment.setDescription(msg.dataDefaultPaymentDescription);
            defaultPayment.setDiscountValue(Double.valueOf(0.0));
            defaultPayment.setDiscountDays(Integer.valueOf(0));
            defaultPayment.setDiscountDays(Integer.valueOf(0));
            defaultPayment.setPaidText(msg.dataDefaultPaymentPaidtext);
            defaultPayment.setDepositText(msg.dataDefaultPaymentDescription);
            defaultPayment.setUnpaidText(msg.dataDefaultPaymentUnpaidtext);
            defaultPayment.setCode(UNTDID4461.VALUE_ZZZ.getCode());
            defaultPayment = paymentsDAO.findOrCreate(defaultPayment);
            // defaultValuesNode.setValue(Constants.DEFAULT_PAYMENT,
            // defaultPayment.getId());
            eclipsePrefs.putLong(Constants.DEFAULT_PAYMENT, defaultPayment.getId());
        }

        // store current program version
        appendProgress(splashService, "saving program version");
        final UserProperty userProp = new UserProperty();
        userProp.setName(Constants.CURRENT_PROGRAM_VERSION);
        userProp.setValue(Platform.getProduct().getDefiningBundle().getVersion().toString());
        propertiesDao.insertOrUpdate(userProp);

        splashService.worked(1);
        // init UN/CEFACT codes
        // if(eclipsePrefs.getBoolean("isreinit", false) ||
        // Long.valueOf(0L).compareTo(unCefactCodeDAO.getCount()) == 0) {
        // initializeCodes(unCefactCodeDAO, modelFactory);
        // }
        splashService.worked(1);

        // init salutations TODO activate!
        // if(eclipsePrefs.getBoolean("isreinit", false) ||
        // Long.valueOf(0L).compareTo(itemAccountTypeDAO.getCountOf("data.list.salutations"))
        // == 0) {
        // ItemListTypeCategory salutationCategory =
        // itemListTypeCategoriesDAO.getCategory("data.list.salutations", true);
        // ContactUtil contactUtil =
        // ContextInjectionFactory.make(ContactUtil.class, context);
        //
        // for (int i = 0; i <= ContactUtil.MAX_SALUTATION_COUNT; i++) {
        // ItemAccountType salutation = modelFactory.createItemAccountType();
        // salutation.setCategory(salutationCategory);
        // salutation.setName(msg.commonFieldSalutation + " " +
        // contactUtil.getSalutationString(i));
        // salutation.setValue(contactUtil.getSalutationString(i));
        // itemAccountTypeDAO.save(salutation);
        // }
        // }
        splashService.worked(1);

        try {
            eclipsePrefs.flush();
        } catch (final BackingStoreException e) {
            log.error(e);
        }
        context.set(IPreferenceStore.class, defaultValuesNode);
        context.getParent().set(IPreferenceStore.class, defaultValuesNode);
        // the DefaultPreferences gets initialized through the calling extension
        // point (which is defined in META-INF).
        // here we have to restore the preference values from database
        appendProgress(splashService, "loading preferences");
        final PreferencesInDatabase preferencesInDatabase = ContextInjectionFactory.make(PreferencesInDatabase.class, context);
        context.set(PreferencesInDatabase.class, preferencesInDatabase);
        preferencesInDatabase.loadPreferencesFromDatabase();
        splashService.worked(1);
    }

    /**
     * Initializes all the UN/CEFACT codes which are needed for later ZUGFeRD
     * export. The codes are read from resources bundle (file codelists.xlsx).
     * 
     * Format: Rubrik | Code | Name_en | Name_de | abbrev_en | abbrev_de |
     * Hinweis
     * 
     * @param unCefactCodeDAO
     *            the dao
     * @param modelFactory
     */
    private void initializeCodes(final UnCefactCodeDAO unCefactCodeDAO, final FakturamaModelFactory modelFactory) {
        try (InputStream wbStream = FrameworkUtil.getBundle(TemplateResourceManager.class).getResource(CODELISTS_XLSX).openStream();) {
            log.info("importing code lists from " + CODELISTS_XLSX);
            // Workbook wb = WorkbookFactory.create(wbStream);
            final Workbook wb = new XSSFWorkbook(wbStream);
            final Sheet sheet = wb.getSheetAt(0);
            final int rows = sheet.getPhysicalNumberOfRows();
            // skip the first n rows
            final int skiprows = 1; // in case we have somedays more than one
                                    // header line
            for (int r = skiprows; r < rows; r++) {
                final Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                int i = 0; // column index
                final CEFACTCode cEFACTCode = modelFactory.createCEFACTCode();

                // TEST ONLY (HSQLDB claims about updates of id field :-(
                // cEFACTCode.setId(r*(-1));

                cEFACTCode.setTarget(row.getCell(i++).getStringCellValue());
                cEFACTCode.setCode(row.getCell(i++).getStringCellValue());
                cEFACTCode.setName(getNullSafeCellValue(row.getCell(i++)));
                cEFACTCode.setName_de(getNullSafeCellValue(row.getCell(i++)));
                cEFACTCode.setAbbreviation_en(getNullSafeCellValue(row.getCell(i++)));
                cEFACTCode.setAbbreviation_de(getNullSafeCellValue(row.getCell(i++)));
                cEFACTCode.setValidFrom(Date.from(Instant.now()));
                // cEFACTCode.setDateAdded(Date.from(Instant.now()));
                unCefactCodeDAO.save(cEFACTCode);
            }
        } catch (IOException | FakturamaStoringException e) {
            log.error(e);
        }
    }

    private String getNullSafeCellValue(final Cell cell) {
        String retval = null;
        if (cell != null) {
            retval = cell.getStringCellValue();
        }
        return retval;
    }

    @PreDestroy
    public void postWindowClose(@Named(E4Workbench.INSTANCE_LOCATION) final Location instanceLocation) {
        final PreferencesInDatabase preferencesInDatabase = context.get(PreferencesInDatabase.class);
        if (preferencesInDatabase != null) {
            log.debug("Storing preferences in database");
            preferencesInDatabase.savePreferencesInDatabase();

            if (dbUpdateService != null && dbUpdateService.isDbAlive()) {
                dbUpdateService.shutDownDb();
            }
        }
        try {
            eclipsePrefs.flush();
        } catch (final BackingStoreException e) {
            log.error(e);
        }
        saveDialogSettings(instanceLocation);
    }

    /**
     * Saves this plug-in's dialog settings. Any problems which arise are
     * silently ignored.
     * 
     * @param instanceLocation
     */
    protected void saveDialogSettings(final Location instanceLocation) {
        if (dialogSettings == null) {
            return;
        }
        log.debug("save dialog settings");

        try {
            final URL path = instanceLocation.getDataArea(Activator.PLUGIN_ID);
            Path storage = null;
            if (path == null) {
                return;
            } else {
                storage = Paths.get(path.toURI());
                if (Files.notExists(storage)) {
                    Files.createDirectories(storage);
                }
            }

            storage = storage.resolve(FN_DIALOG_SETTINGS);
            if (Files.notExists(storage)) {
                Files.createFile(storage);
            }
            dialogSettings.save(storage.toString());
        } catch (IOException | IllegalStateException | URISyntaxException e) {
            log.error("Can't save dialog settings. Reason: " + e.getMessage());
        }
    }

    @ProcessAdditions
    void processAdditions(final IEventBroker eventBroker, final MApplication app, final EModelService modelService, final IApplicationContext appContext,
            @Named(E4Workbench.INSTANCE_LOCATION) final Location instanceLocation, final ISplashService splashService) {

        // TODO put the Login Dialog in here
        if (eclipsePrefs.getBoolean("isreinit", false)) {
            dbUpdateService.updateDatabase();
        }

        if (eclipsePrefs.get(ConfigurationManager.GENERAL_WORKSPACE_REQUEST, null) != null) {
            eventBroker.subscribe(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE, new AppStartupCompleteEventHandler(context, RESTART_APPLICATION));
            final IPreferenceStore defaultValuesNode = EclipseContextFactory.getServiceContext(Activator.getContext()).get(IPreferenceStore.class);
            context.set(IPreferenceStore.class, defaultValuesNode);
            context.getParent().set(IPreferenceStore.class, defaultValuesNode);

        } else {

            eventBroker.subscribe(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE, new AppStartupCompleteEventHandler(context, false, modelService, app));

        }
        eclipsePrefs.putBoolean("isreinit", false);

        final MTrimmedWindow mainMTrimmedWindow = (MTrimmedWindow) modelService.find("com.sebulli.fakturama.application", app);
        mainMTrimmedWindow.setLabel(msg.applicationName + " - " + eclipsePrefs.get(Constants.GENERAL_WORKSPACE, null));

        // see run-demo.sh / -Dfakturama.demoMode=true: a demo run should always open full-screen
        // rather than whatever size the (freshly created, so never-before-persisted) window state
        // would otherwise default to. A plain asyncExec() here fires too early - something later
        // in startup still restores/sets the window bounds and stomps on it - so wait for
        // APP_STARTUP_COMPLETE (the same "the app is now truly up" signal used just above for the
        // workspace-restart handlers), and even then give the bounds-restoration a moment to
        // finish first via a short timerExec before maximizing.
        if (Boolean.getBoolean("fakturama.demoMode")) {
            eventBroker.subscribe(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE, new EventHandler() {
                @Override
                public void handleEvent(final Event event) {
                    eventBroker.unsubscribe(this);
                    Display.getDefault().timerExec(300, () -> {
                        if (mainMTrimmedWindow.getWidget() instanceof final Shell shell) {
                            shell.setMaximized(true);
                        }
                    });
                }
            });
        }

        initDialogSettings(instanceLocation);
        splashService.worked(2);

        // close the static splash screen
        // TODO check if we could call it twice (one call is before
        // Migrationmanager)
        appContext.applicationRunning();
    }

    /**
     * Because we don't have a complete workbench at this stage, the
     * {@link EventHandler} is registered so that we can restart the application
     * if the working directory has changed or the application is launched the
     * first time.
     *
     */
    private static final class AppStartupCompleteEventHandler implements EventHandler {
        private final IEclipseContext _context;
        private final boolean restartApplication;
        private final EModelService modelService;
        private final MApplication app;

        AppStartupCompleteEventHandler(final IEclipseContext context, final boolean restartApplication) {
            this(context, restartApplication, null, null);
        }

        AppStartupCompleteEventHandler(final IEclipseContext context, final boolean restartApplication, final EModelService modelService,
                final MApplication app) {
            _context = context;
            this.restartApplication = restartApplication;
            this.modelService = modelService;
            this.app = app;
        }

        @Override
        public void handleEvent(final Event event) {
            if (modelService != null) {
                final MTrimmedWindow mainMTrimmedWindow = (MTrimmedWindow) modelService.find("com.sebulli.fakturama.application", app);
                final ISaveHandler saveHandler = ContextInjectionFactory.make(SaveHandler.class, mainMTrimmedWindow.getContext());
                mainMTrimmedWindow.getContext().set(ISaveHandler.class, saveHandler);
            }

            final IWorkbench workbench = _context.get(IWorkbench.class);
            if (restartApplication) {
                workbench.restart();
            }
        }
    }

    private IDialogSettings initDialogSettings(final Location instanceLocation) {
        if (dialogSettings == null) {
            dialogSettings = loadDialogSettings(instanceLocation);
            context.set(IDialogSettings.class, dialogSettings);
        }
        return dialogSettings;
    }

    /**
     * Loads the dialog settings for this plug-in. The default implementation
     * first looks for a standard named file in the plug-in's read/write state
     * area; if no such file exists, the plug-in's install directory is checked
     * to see if one was installed with some default settings; if no file is
     * found in either place, a new empty dialog settings is created. If a
     * problem occurs, an empty settings is silently used.
     * <p>
     * This framework method may be overridden, although this is typically
     * unnecessary.
     * </p>
     * 
     * Borrowed from org.eclipse.ui.plugin.AbstractUIPlugin
     * 
     * @param instanceLocation
     */
    private IDialogSettings loadDialogSettings(final Location instanceLocation) {
        dialogSettings = new DialogSettings("Workbench"); //$NON-NLS-1$

        // look for bundle specific dialog settings
        URL dsURL = null;
        try {
            dsURL = instanceLocation.getDataArea(Activator.PLUGIN_ID + "/" + FN_DIALOG_SETTINGS);
        } catch (final IOException e1) {
            log.error(e1, "Cannot determine current data area. Reason: ");
        }
        if (dsURL == null) {
            return null;
        }

        try {
            final BufferedReader reader = Files.newBufferedReader(Paths.get(dsURL.toURI()));
            dialogSettings.load(reader);
        } catch (IOException | URISyntaxException e) {
            // load failed so ensure we have an empty settings
            dialogSettings = new DialogSettings("Workbench"); //$NON-NLS-1$
        }
        return dialogSettings;
    }
}