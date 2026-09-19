package com.sebulli.fakturama.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import com.sebulli.fakturama.dao.ContactCategoriesDAO;
import com.sebulli.fakturama.dao.ItemListTypeCategoriesDAO;
import com.sebulli.fakturama.dao.ProductCategoriesDAO;
import com.sebulli.fakturama.dao.ShippingCategoriesDAO;
import com.sebulli.fakturama.dao.TextCategoriesDAO;
import com.sebulli.fakturama.dao.VatCategoriesDAO;
import com.sebulli.fakturama.dao.VoucherCategoriesDAO;
import com.sebulli.fakturama.dialogs.CategoryManagerDialog;

public class OpenCategoryManagerHandler {
    @Execute
    public void execute(final Shell shell, final ProductCategoriesDAO products,
            final ContactCategoriesDAO contacts, final ShippingCategoriesDAO shippings,
            final VatCategoriesDAO vats, final VoucherCategoriesDAO vouchers,
            final TextCategoriesDAO texts, final ItemListTypeCategoriesDAO lists) {
        new CategoryManagerDialog(shell,
                products, contacts, shippings, vats, vouchers, texts, lists).open();
    }
}
