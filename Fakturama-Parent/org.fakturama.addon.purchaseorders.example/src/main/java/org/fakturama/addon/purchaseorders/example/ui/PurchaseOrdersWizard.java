package org.fakturama.addon.purchaseorders.example.ui;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.fakturama.addon.purchaseorders.example.repository.JpaPurchaseOrderRepository;
import org.fakturama.wizards.IImportWizard;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

/** Import-registry contribution supplied by an independent OSGi bundle. */
public final class PurchaseOrdersWizard extends Wizard implements IImportWizard {
    @Inject
    @PersistenceUnit(unitName = "unconfigured2")
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void initialize() {
        setWindowTitle("Warenbestellungen");
        setNeedsProgressMonitor(false);
        addPage(new PurchaseOrdersPage(new JpaPurchaseOrderRepository(entityManagerFactory)));
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        // Fakturama supplies dependencies through its e4 context before showing the wizard.
    }

    @Override
    public boolean performFinish() {
        return true;
    }
}
