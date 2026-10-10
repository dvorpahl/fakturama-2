package com.sebulli.fakturama.views.datatable.common;

import com.sebulli.fakturama.model.*;
import com.sebulli.fakturama.views.datatable.common.CommonListItemMatcher;
import com.sebulli.fakturama.views.datatable.tree.ui.TreeObjectType;

public class CommonListItemMatcherTest {
    static Shipping shipping(String name, ShippingCategory parent) {
        ShippingCategory category = new ShippingCategory();
        category.setName(name);
        category.setParent(parent);
        Shipping shipping = new Shipping();
        shipping.setCategories(category);
        return shipping;
    }
    static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
    @org.junit.jupiter.api.Test
    public void shippingCategoryPaths() {
        Shipping shop = shipping("Shop", null);
        Shipping child = shipping("EU", shop.getCategories());
        CommonListItemMatcher<Shipping> filter = new CommonListItemMatcher<>("Shop", TreeObjectType.LEAF_NODE, "");
        check(filter.matches(shop), "Shop shipping must be visible");
        check(filter.matches(child), "Subcategory must be visible");
        check(!filter.matches(shipping("Shopping", null)), "Similar category must not match");
        check(!filter.matches(shipping("Fracht", null)), "Unrelated category must not match");
        check(!filter.matches(new Shipping()), "Uncategorized shipping must not match Shop");
        check(new CommonListItemMatcher<Shipping>("Shop/EU", TreeObjectType.LEAF_NODE, "EU").matches(child), "Nested path must match");
        check(new CommonListItemMatcher<Shipping>("/Shop/", TreeObjectType.DEFAULT_NODE, "Shop").matches(shop), "Boundary slashes must normalize");
        check(new CommonListItemMatcher<Shipping>("", TreeObjectType.ALL_NODE, "").matches(new Shipping()), "All must include uncategorized items");
        check(new CommonListItemMatcher<Shipping>("", TreeObjectType.ROOT_NODE, "").matches(shop), "Root must show all");
    }
}
