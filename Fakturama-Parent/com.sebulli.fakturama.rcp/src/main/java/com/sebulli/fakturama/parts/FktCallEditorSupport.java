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

package com.sebulli.fakturama.parts;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;

import com.sebulli.fakturama.handlers.CallEditor;
import com.sebulli.fakturama.handlers.CommandIds;

/**
 * Shared helper for the {@code FKT.open*} bridge functions (see {@link FktBridge}): opens an
 * editor part by entity id through the same {@link CallEditor} command that the data table
 * double-click handlers use (e.g. {@code ProductListTable}, {@code DocumentsListTable}).
 */
final class FktCallEditorSupport {

    private FktCallEditorSupport() {
    }

    static void openEditor(final IEclipseContext ctx, final String editorType, final Long objId) {
        Map<String, Object> params = new HashMap<>();
        params.put(CallEditor.PARAM_OBJ_ID, Long.toString(objId));
        params.put(CallEditor.PARAM_EDITOR_TYPE, editorType);

        ECommandService commandService = ctx.get(ECommandService.class);
        EHandlerService handlerService = ctx.get(EHandlerService.class);
        ParameterizedCommand command = commandService.createCommand(CommandIds.CMD_CALL_EDITOR, params);
        handlerService.executeHandler(command);
    }

    /**
     * Converts a raw JS argument (a {@code Double} for a JS number literal, or a {@code String})
     * into a {@code Long} id. Returns {@code null} if the argument is missing or not a valid id.
     */
    static Long toId(final Object[] arguments) {
        if (arguments.length < 1 || arguments[0] == null) {
            return null;
        }
        if (arguments[0] instanceof Number) {
            return ((Number) arguments[0]).longValue();
        }
        if (arguments[0] instanceof String) {
            try {
                return Long.valueOf(((String) arguments[0]).trim());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}
