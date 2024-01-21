package com.sebulli.fakturama.office;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;

import com.sebulli.fakturama.dao.DocumentsDAO;
import com.sebulli.fakturama.i18n.ILocaleService;
import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.log.ILogger;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.model.BillingType;
import com.sebulli.fakturama.model.Debitor;
import com.sebulli.fakturama.model.Invoice;
import com.sebulli.fakturama.office.FileOrganizer.PathOption;

import ch.qos.logback.classic.spi.LogbackServiceProvider;

public class FileOrganizerTest {

    @Mock
    private IPreferenceStore preferenceStore;
    @Mock
    private Messages messages;
    @Mock
    private ILogger logger;
    @Mock
    private DocumentsDAO documentsDAO;
    @Mock
    private Invoice invoice;
    @Mock
    private Debitor debitor;

    @Before
    public void injectMocks() {
        // start common for locale, money for money
        try {
            FrameworkUtil.getBundle(LogbackServiceProvider.class).start();
            FrameworkUtil.getBundle(ILocaleService.class).start();

        } catch (BundleException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        MockitoAnnotations.initMocks(this); // This could be pulled up into a
                                            // shared base class
    }

    @InjectMocks
    private FileOrganizer f;

    @Test
    @Ignore
    public void testRenaming() {
        Set<PathOption> pathOptions = new HashSet<>();
        pathOptions.add(PathOption.WITH_FILENAME);
        pathOptions.add(PathOption.WITH_EXTENSION);
        messages.pathsDocumentsName = "Documents";
        Mockito.when(preferenceStore.getString("OPENOFFICE_" + TargetFormat.ODT.getPrefId() + "_PATH_FORMAT"))
                .thenReturn("ODT/{yyyy}/{doctype}/{docname}-{docref}-_{address}-{yyyy}.odt");
        Mockito.when(preferenceStore.getString(Constants.GENERAL_WORKSPACE)).thenReturn("/TestWorkSpace");

        Mockito.when(debitor.getName()).thenReturn("Hugo Meier");
        //		Mockito.when(invoice.getBillingContact()).thenReturn(debitor);

        Mockito.when(invoice.getName()).thenReturn("KD_Tester001");
        Mockito.when(invoice.getCustomerRef()).thenReturn("TestRef01");
        Mockito.when(invoice.getBillingType()).thenReturn(BillingType.INVOICE);
        Mockito.when(invoice.getAddressFirstLine()).thenReturn("Hans Magnus Tester, FreeTest Inc.");
        Mockito.when(invoice.getDocumentDate()).thenReturn(new Date(116, 1, 5));

        Mockito.when(messages.getMessageFromKey(Mockito.anyString())).then(new Answer<String>() {
            /*
             * (non-Javadoc)
             * 
             * @see org.mockito.stubbing.Answer#answer(org.mockito.invocation.
             * InvocationOnMock)
             */
            @Override
            public String answer(final InvocationOnMock invocation) throws Throwable {
                String argument = (String) invocation.getArguments()[0];
                if (StringUtils.defaultString(argument).contentEquals("document.type.invoice.plural")) {
                    return "Rechnungen";
                } else {
                    return BillingType.INVOICE.getName();
                }
            }
        });

        Path documentPath = f.getDocumentPath(pathOptions, TargetFormat.ODT, invoice);
        Path p = Paths.get("\\TestWorkSpace\\Documents\\ODT\\2016\\Rechnungen\\KD_Tester001-TestRef01-_Hans_Magnus_Tester,_FreeTest_Inc.-2016.ODT");
        Assert.assertEquals(documentPath, p);
    }

}
