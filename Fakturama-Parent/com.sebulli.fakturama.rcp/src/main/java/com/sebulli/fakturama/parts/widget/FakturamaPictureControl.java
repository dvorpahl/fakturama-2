/**
 * 
 */
package com.sebulli.fakturama.parts.widget;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.nebula.widgets.picture.PictureControl;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;

import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.resources.core.Icon;
import com.sebulli.fakturama.resources.core.IconSize;

/**
 * a {@link org.eclipse.nebula.widgets.picture.PictureControl} with some
 * modifications.
 *
 */
public class FakturamaPictureControl extends PictureControl {

    private static final String PICTURE_CONTROL_SETTING = "PICTURE_CONTROL";

    private static final String PICTURE_CONTROL_LAST_USED_PATH = "PICTURE_CONTROL_LAST_USED_PATH";

    private static final String PICTURE_CONTROL_LAST_USED_FILTER = "PICTURE_CONTROL_LAST_USED_FILTER";

    @Inject
    private IDialogSettings settings;

    @Inject
    @Translation
    protected Messages msg;

    @Inject
    protected IPreferenceStore defaultValuePrefs;

    private String[] filterExtensions = new String[] { "*.png", "*.jpg", "*.bmp" };

    public FakturamaPictureControl(final Composite parent) {
        super(parent);
    }

    @PostConstruct
    public void init() {
        setModifyImageLinkText(msg.editorProductButtonChoosepicName);
        setDeleteImageLinkText(msg.mainMenuEditDeleteName);
    }

    @PreDestroy
    void cleanUp() {
        this.dispose();
    }

    @Override
    public void setImageByteArray(final byte[] imageByteArray) {
        super.setImageByteArray(imageByteArray);
        Menu menu = getPictureLabel().getMenu();
        menu.getItem(0).setText(msg.mainMenuEditDeleteName);
        menu.getItem(0).setImage(Icon.COMMAND_DELETE.getImage(IconSize.DefaultIconSize));
        menu.getItem(1).setText(msg.editorProductButtonChoosepicName);
        menu.getItem(1).setImage(Icon.COMMAND_ORDER_PROCESSING.getImage(IconSize.DefaultIconSize));
    }

    @Override
    protected void configure(final FileDialog fd) {
        super.configure(fd);
        IDialogSettings dialogSettings = getDialogSettings(PICTURE_CONTROL_SETTING);
        String lastUsedPath = dialogSettings.get(PICTURE_CONTROL_LAST_USED_PATH);
        String filterPath = StringUtils.isNotBlank(lastUsedPath) ? lastUsedPath : defaultValuePrefs.getString(Constants.GENERAL_WORKSPACE);
        fd.setFilterPath(filterPath);

        int lastUsedIndex;
        try {
            lastUsedIndex = dialogSettings.getInt(PICTURE_CONTROL_LAST_USED_FILTER);
        } catch (NumberFormatException e) {
            lastUsedIndex = 0;
        }
        fd.setFilterIndex(lastUsedIndex);

        fd.setText(msg.editorProductButtonChoosepicName);
        fd.setFilterExtensions(filterExtensions);
    }

    /**
     * Open the Explorer File to select a new image.
     * 
     * copy of
     * org.eclipse.nebula.widgets.picture.AbstractPictureControl.handleModifyImage()
     */
    @Override
    protected void handleModifyImage() {
        FileDialog fd = new FileDialog(this.getShell(), getFileDialogStyle());
        configure(fd);
        String selected = fd.open();
        if (selected != null && selected.length() > 0) {
            File f = new File(selected);
            String fileExtension = FilenameUtils.getExtension(selected);
            int filterIndex = Arrays.binarySearch(filterExtensions, String.format("*.%s", fileExtension));
            saveDialogSettings(f.getParent(), filterIndex);
            try {
                FileInputStream in = new FileInputStream(f);
                // First, check if image is ok, after this, set it
                ImageData image = new ImageData(in);
                if (image != null) {
                    setImageByteArray(image.data);
                }
            } catch (SWTException e) {
                setImageByteArray(null);
                handleError(e);
            } catch (Throwable e) {
                setImageByteArray(null);
                handleError(e);
            }

        }
    }

    private void saveDialogSettings(final String currentPath, final int filterIndex) {
        IDialogSettings dialogSettings = getDialogSettings(PICTURE_CONTROL_SETTING);
        dialogSettings.put(PICTURE_CONTROL_LAST_USED_PATH, currentPath);
        dialogSettings.put(PICTURE_CONTROL_LAST_USED_FILTER, filterIndex);
    }

    private IDialogSettings getDialogSettings(final String section) {
        if (settings.getSection(section) == null) {
            settings.addNewSection(section);
        }
        return settings.getSection(section);
    }

}
