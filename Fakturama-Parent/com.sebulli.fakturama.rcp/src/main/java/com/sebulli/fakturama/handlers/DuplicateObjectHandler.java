
package com.sebulli.fakturama.handlers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.model.ObjectDuplicator.DuplicateMode;
import com.sebulli.fakturama.parts.DocumentEditor;
import com.sebulli.fakturama.parts.ProductEditor;

public class DuplicateObjectHandler {

	@Inject
	@Optional
	private IPreferenceStore preferences;

	@Inject
	private EHandlerService handlerService;

	@Inject
	private ECommandService commandService;

	@CanExecute
	public boolean canExecute(@Optional @Active MPart activePart) {
		boolean retval = false;
		if(activePart != null) {
			switch (activePart.getElementId()) {
			case ProductEditor.ID:
				// at the moment it only works for Product Editor
				retval = true;
				break;
			case DocumentEditor.ID:
				// any open document may be used as a template; DocumentEditor#init() decides
				// what actually happens (same-type Offer duplicate with a dialog, cross-type
				// template copy, or nothing at all)
				retval = activePart.getObject() instanceof DocumentEditor;
				break;
			default:
				break;
			}
		}
		return retval;
	}

	@Execute
	public void execute(@Optional @Active MPart activePart) {
		// which kind of copy (same customer / blank / cross-type template) is decided
		// inside DocumentEditor#init() - see FakturamaCoolbarAction for why that decision
		// (and its dialog) doesn't belong here.
		Map<String, Object> params = new HashMap<>();
		params.put(CallEditor.PARAM_EDITOR_TYPE, activePart.getElementId());
		params.put(CallEditor.PARAM_COPY, Boolean.TRUE);
		params.put(CallEditor.PARAM_OBJ_ID, activePart.getTransientData().get(CallEditor.PARAM_OBJ_ID));
		ParameterizedCommand pCmdCopy = commandService.createCommand(CommandIds.CMD_CALL_EDITOR, params);
		if (handlerService.canExecute(pCmdCopy)) {
			handlerService.executeHandler(pCmdCopy);
		}
	}

	/**
	 * Asks the user whether an Offer-to-Offer copy should keep pointing to the
	 * same customer or start out blank. Called from {@link com.sebulli.fakturama.parts.DocumentEditor#init}
	 * once the target editor/document actually exists, regardless of whether the
	 * duplication was triggered via this handler's keybinding or a Ctrl+Click on a
	 * toolbar icon ({@link com.sebulli.fakturama.parts.FakturamaCoolbarAction}).
	 *
	 * @return the chosen {@link DuplicateMode}, or {@code null} if the user cancelled
	 *         (Cancel button, or closing the dialog via Esc/[x])
	 */
	public static DuplicateMode askDuplicateMode(final Shell shell, final Messages msg) {
		final MessageDialog dialog = new MessageDialog(shell, msg.commandDuplicateOfferTitle, null, msg.commandDuplicateOfferMessage,
				MessageDialog.QUESTION,
				new String[] { msg.commandDuplicateOfferSamecustomer, msg.commandDuplicateOfferNewcustomer, IDialogConstants.CANCEL_LABEL },
				0) {
			@Override
			protected void cancelPressed() {
				// closing via Esc/[x] must not collide with a labeled button's index
				// (Dialog#cancelPressed() otherwise sets the same return code as our
				// "Neues Angebot" button at index 1)
				setReturnCode(SWT.DEFAULT);
				close();
			}
		};
		final int choice = dialog.open();
		if (choice == 2 || choice == SWT.DEFAULT) {
			return null;
		}
		return choice == 0 ? DuplicateMode.SAME_CUSTOMER : DuplicateMode.NEW_DOCUMENT;
	}

}