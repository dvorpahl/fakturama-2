
package com.sebulli.fakturama.handlers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.misc.DocumentType;
import com.sebulli.fakturama.model.ObjectDuplicator.DuplicateMode;
import com.sebulli.fakturama.parts.DocumentEditor;
import com.sebulli.fakturama.parts.ProductEditor;

public class DuplicateObjectHandler {

	@Inject
	@Translation
	protected Messages msg;

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
				// for documents we have to check if it's an Offer
				Object currentObject = activePart.getObject();
				if(currentObject != null && currentObject instanceof DocumentEditor) {
					retval = DocumentType.OFFER.equals(((DocumentEditor)currentObject).getDocumentType());
				}
				break;
			default:
				break;
			}
		}
		return retval;
	}

	@Execute
	public void execute(@Optional @Active MPart activePart, @Optional @Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		DuplicateMode mode = DuplicateMode.NEW_DOCUMENT;
		if (DocumentEditor.ID.equals(activePart.getElementId())) {
			// only Offers can be duplicated (see canExecute); ask the user whether the
			// copy should keep pointing to the same customer or start out blank
			MessageDialog dialog = new MessageDialog(shell, msg.commandDuplicateOfferTitle, null, msg.commandDuplicateOfferMessage,
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
			int choice = dialog.open();
			if (choice == 2 || choice == SWT.DEFAULT) {
				// user cancelled (Cancel button, or Esc/[x])
				return;
			}
			mode = choice == 0 ? DuplicateMode.SAME_CUSTOMER : DuplicateMode.NEW_DOCUMENT;
		}

		Map<String, Object> params = new HashMap<>();
		params.put(CallEditor.PARAM_EDITOR_TYPE, activePart.getElementId());
		params.put(CallEditor.PARAM_COPY, Boolean.TRUE);
		params.put(CallEditor.PARAM_COPY_MODE, mode.name());
		params.put(CallEditor.PARAM_OBJ_ID, activePart.getTransientData().get(CallEditor.PARAM_OBJ_ID));
		ParameterizedCommand pCmdCopy = commandService.createCommand(CommandIds.CMD_CALL_EDITOR, params);
		if (handlerService.canExecute(pCmdCopy)) {
			handlerService.executeHandler(pCmdCopy);
		}
	}

}