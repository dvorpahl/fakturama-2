/* 
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2021 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     The Fakturama Team - initial API and implementation
 */
 
package com.sebulli.fakturama.views.datatable.common;

import org.apache.commons.lang3.StringUtils;

import com.sebulli.fakturama.converter.CommonConverter;
import com.sebulli.fakturama.model.AbstractCategory;
import com.sebulli.fakturama.model.ICategorizable;
import com.sebulli.fakturama.views.datatable.tree.ui.TreeObjectType;

import ca.odell.glazedlists.matchers.Matcher;

/**
 * Common class for List category matchers.
 *
 */
public class CommonListItemMatcher<T extends ICategorizable<? extends AbstractCategory>> implements Matcher<T>{
    private final String categoryName;
    private final boolean isRootNode;
    
    /**
     * Constructor
     * 
     * @param categoryName category name which is selected in tree viewer
     * @param treeObjectType the selected {@link TreeObjectType}
     * @param rootNodeName legacy argument; tree filters now contain only real category paths 
     */
    public CommonListItemMatcher(String categoryName, TreeObjectType treeObjectType, String rootNodeName) {
        this.categoryName = StringUtils.strip(StringUtils.defaultString(categoryName), "/");
        this.isRootNode = treeObjectType == TreeObjectType.ALL_NODE || treeObjectType == TreeObjectType.ROOT_NODE;
    }

    @Override
    public boolean matches(T item) {
        if (isRootNode()) {
            return true;
        }
        if (item == null || item.getCategories() == null || categoryName.isEmpty()) {
            return false;
        }
        // TopicTreeViewer omits the synthetic root and localized "all" nodes.
        String fullCategoryName = CommonConverter.getCategoryName(item.getCategories(), "");
        return fullCategoryName.equals(categoryName)
                || fullCategoryName.startsWith(categoryName + "/");
    }

    public boolean isRootNode() {
        return isRootNode;
    }

}
