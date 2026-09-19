package com.sebulli.fakturama.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.sebulli.fakturama.dao.AbstractCategoriesDAO;
import com.sebulli.fakturama.dao.ContactCategoriesDAO;
import com.sebulli.fakturama.dao.ItemListTypeCategoriesDAO;
import com.sebulli.fakturama.dao.ProductCategoriesDAO;
import com.sebulli.fakturama.dao.ShippingCategoriesDAO;
import com.sebulli.fakturama.dao.TextCategoriesDAO;
import com.sebulli.fakturama.dao.VatCategoriesDAO;
import com.sebulli.fakturama.dao.VoucherCategoriesDAO;
import com.sebulli.fakturama.exception.FakturamaStoringException;
import com.sebulli.fakturama.model.AbstractCategory;
import com.sebulli.fakturama.model.ContactCategory;
import com.sebulli.fakturama.model.ItemListTypeCategory;
import com.sebulli.fakturama.model.ProductCategory;
import com.sebulli.fakturama.model.ShippingCategory;
import com.sebulli.fakturama.model.TextCategory;
import com.sebulli.fakturama.model.VATCategory;
import com.sebulli.fakturama.model.VoucherCategory;
import com.sebulli.fakturama.converter.CommonConverter;

/** Central editor for all persisted category types. */
public class CategoryManagerDialog extends Dialog {
    private static final class CategoryType<T extends AbstractCategory> {
        final String label;
        final AbstractCategoriesDAO<T> dao;
        final Supplier<T> factory;
        CategoryType(final String label, final AbstractCategoriesDAO<T> dao, final Supplier<T> factory) {
            this.label = label;
            this.dao = dao;
            this.factory = factory;
        }
        @Override public String toString() { return label; }
    }

    private static final class NoCategory {
        @Override public String toString() { return "Keine Kategorie"; }
    }

    private final List<CategoryType<? extends AbstractCategory>> types;
    private Combo typeCombo;
    private TreeViewer tree;
    private CategoryType<? extends AbstractCategory> currentType;

    public CategoryManagerDialog(final Shell parentShell, final ProductCategoriesDAO products,
            final ContactCategoriesDAO contacts, final ShippingCategoriesDAO shippings,
            final VatCategoriesDAO vats, final VoucherCategoriesDAO vouchers,
            final TextCategoriesDAO texts, final ItemListTypeCategoriesDAO lists) {
        super(parentShell);
        types = Arrays.asList(
                new CategoryType<>("Artikel", products, ProductCategory::new),
                new CategoryType<>("Kontakte", contacts, ContactCategory::new),
                new CategoryType<>("Versandarten", shippings, ShippingCategory::new),
                new CategoryType<>("Steuersätze", vats, VATCategory::new),
                new CategoryType<>("Zahlungen", vouchers, VoucherCategory::new),
                new CategoryType<>("Texte", texts, TextCategory::new),
                new CategoryType<>("Listen-/Kontenarten", lists, ItemListTypeCategory::new));
    }

    @Override protected void configureShell(final Shell shell) {
        super.configureShell(shell);
        shell.setText("Kategorien verwalten");
        shell.setMinimumSize(560, 420);
    }

    @Override protected org.eclipse.swt.widgets.Control createDialogArea(final Composite parent) {
        final Composite area = (Composite) super.createDialogArea(parent);
        final Composite content = new Composite(area, SWT.NONE);
        content.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        content.setLayout(new GridLayout(2, false));

        new Label(content, SWT.NONE).setText("Kategorie-Typ:");
        typeCombo = new Combo(content, SWT.READ_ONLY);
        typeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        types.forEach(type -> typeCombo.add(type.label));
        typeCombo.addListener(SWT.Selection, event -> selectType(typeCombo.getSelectionIndex()));

        tree = new TreeViewer(content, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);
        tree.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
        tree.setContentProvider(new CategoryContentProvider());
        tree.setLabelProvider(new LabelProvider() {
            @Override public String getText(final Object element) {
                return ((AbstractCategory) element).getName();
            }
        });

        final Composite buttons = new Composite(content, SWT.NONE);
        buttons.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        buttons.setLayout(new GridLayout(3, true));
        final Button add = new Button(buttons, SWT.PUSH);
        add.setText("Unterkategorie anlegen");
        add.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        add.addListener(SWT.Selection, event -> addCategory());
        final Button rename = new Button(buttons, SWT.PUSH);
        rename.setText("Umbenennen");
        rename.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        rename.addListener(SWT.Selection, event -> renameCategory());
        final Button delete = new Button(buttons, SWT.PUSH);
        delete.setText("Löschen und Inhalte umhängen");
        delete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        delete.addListener(SWT.Selection, event -> deleteCategory());

        typeCombo.select(0);
        selectType(0);
        return area;
    }

    private void selectType(final int index) {
        currentType = types.get(index);
        reload();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void reload() {
        tree.setInput(currentType.dao.findAll());
        tree.expandToLevel(2);
    }

    private AbstractCategory selected() {
        final Object selection = tree.getStructuredSelection().getFirstElement();
        return selection instanceof AbstractCategory ? (AbstractCategory) selection : null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void addCategory() {
        final InputDialog dialog = new InputDialog(getShell(), "Kategorie anlegen", "Name:", "", value -> value.trim().isEmpty() ? "Bitte einen Namen eingeben." : null);
        if (dialog.open() != OK) return;
        final AbstractCategory category = (AbstractCategory) currentType.factory.get();
        category.setName(dialog.getValue().trim());
        category.setParent(selected());
        try { ((AbstractCategoriesDAO) currentType.dao).save(category); reload(); }
        catch (FakturamaStoringException e) { MessageDialog.openError(getShell(), "Fehler", e.getMessage()); }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void renameCategory() {
        final AbstractCategory category = selected();
        if (category == null) return;
        final InputDialog dialog = new InputDialog(getShell(), "Kategorie umbenennen", "Name:", category.getName(), value -> value.trim().isEmpty() ? "Bitte einen Namen eingeben." : null);
        if (dialog.open() != OK) return;
        category.setName(dialog.getValue().trim());
        try { ((AbstractCategoriesDAO) currentType.dao).update(category); reload(); }
        catch (FakturamaStoringException e) { MessageDialog.openError(getShell(), "Fehler", e.getMessage()); }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void deleteCategory() {
        final AbstractCategory category = selected();
        if (category == null) return;
        final List<AbstractCategory> availableTargets = new ArrayList<>();
        for (AbstractCategory candidate : (List<AbstractCategory>) currentType.dao.findAll()) {
            if (candidate != category && !isDescendant(candidate, category)) availableTargets.add(candidate);
        }
        final InputDialog targetDialog = new InputDialog(getShell(), "Zielkategorie auswählen",
                "Pfad der Zielkategorie (leer = ohne Kategorie):", "",
                value -> {
                    if (value.trim().isEmpty()) return null;
                    return availableTargets.stream().anyMatch(candidate ->
                            CommonConverter.getCategoryName(candidate, "").equals(value.trim()))
                                    ? null : "Diese Kategorie existiert nicht oder ist kein gültiges Ziel.";
                });
        if (targetDialog.open() != OK) return;
        final String targetPath = targetDialog.getValue().trim();
        final AbstractCategory target = targetPath.isEmpty() ? null : ((AbstractCategoriesDAO) currentType.dao).findCategoryByName(targetPath);
        if (!MessageDialog.openConfirm(getShell(), "Kategorie löschen", "Kategorie und alle Inhalte umhängen und löschen?")) return;
        try {
            final List<AbstractCategory> all = (List<AbstractCategory>) currentType.dao.findAll();
            for (AbstractCategory child : all) {
                if (child.getParent() == category) {
                    child.setParent(target);
                    ((AbstractCategoriesDAO) currentType.dao).update(child);
                }
            }
            ((AbstractCategoriesDAO) currentType.dao).moveContents(category, target);
            ((AbstractCategoriesDAO) currentType.dao).deleteCategoryOnly(category);
            reload();
        } catch (FakturamaStoringException e) { MessageDialog.openError(getShell(), "Fehler", e.getMessage()); }
    }

    private static boolean isDescendant(final AbstractCategory candidate, final AbstractCategory ancestor) {
        AbstractCategory current = candidate.getParent();
        while (current != null) {
            if (current == ancestor || current.getId() == ancestor.getId()) return true;
            current = current.getParent();
        }
        return false;
    }

    private static final class CategoryContentProvider implements ITreeContentProvider {
        private List<?> input = List.of();
        @Override public Object[] getElements(final Object inputElement) { input = (List<?>) inputElement; return children(null); }
        @Override public Object[] getChildren(final Object parentElement) { return children((AbstractCategory) parentElement); }
        @Override public Object getParent(final Object element) { return ((AbstractCategory) element).getParent(); }
        @Override public boolean hasChildren(final Object element) { return children((AbstractCategory) element).length > 0; }
        private Object[] children(final AbstractCategory parent) {
            return input.stream().filter(item -> ((AbstractCategory) item).getParent() == parent).toArray();
        }
    }
}
