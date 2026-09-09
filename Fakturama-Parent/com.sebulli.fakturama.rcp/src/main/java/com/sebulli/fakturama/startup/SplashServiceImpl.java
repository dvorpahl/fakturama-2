package com.sebulli.fakturama.startup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

public class SplashServiceImpl implements ISplashService {
	private String pluginId = null;
	private String splashPath = "splash.bmp";
	private Shell splashShell = null;
	private Label textLabel = null;
	private String nextMessage = null;
	private Rectangle textRect = null;
	private Color textColor = null;
	private Font textFont = null;
	private Rectangle progressRect = null;
	private int totalWork = 0;

	private ProgressBar progressBar = null;
	private int progress = 0;

	@Override
	public void setSplashPluginId(String pluginId) {
		Assert.isLegal(pluginId != null && !pluginId.equals(""));
		this.pluginId = pluginId;
	}

	@Override
	public void setSplashImagePath(String splashPath) {
		Assert.isLegal(splashPath != null && !splashPath.equals(""));
		this.splashPath = splashPath;
	}

	@Override
	public void setTextBounds(Rectangle rect) {
		Assert.isLegal(rect != null);
		this.textRect = rect;
		if (textLabel != null)
			textLabel.setBounds(rect);
	}

	@Override
	public void setTextColor(Color color) {
		Assert.isLegal(color != null);
		this.textColor = color;
		if (textLabel != null)
			textLabel.setForeground(color);
	}

	@Override
	public void setTextFont(Font font) {
		Assert.isLegal(font != null);
		this.textFont = font;
		if (textLabel != null)
			textLabel.setFont(font);
	}

	@Override
	public void setProgressBarBounds(Rectangle rect) {
		Assert.isLegal(rect != null);
		this.progressRect = rect;
		if (progressBar != null)
			progressBar.setBounds(rect);
	}

	@Override
	public void setTotalWork(int totalWork) {
		this.totalWork = totalWork;
		if (progressBar != null) {
			progressBar.setMaximum(totalWork);
		}
	}

	@Override
	public void worked(int worked) {
		if (progressBar != null && !progressBar.isDisposed()) {
			progress += worked;
			splashShell.getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					progressBar.setSelection(progress);
					splashShell.update();
				}
			});
		}
	}

	@Override
	public void open() {
		if (pluginId == null)
			throw new IllegalStateException("The SplashPluginId has not been set.");
		if (splashPath == null)
			throw new IllegalStateException("The SplashImagePath has not been set.");
		
//		logger.info("Showing Splash Screen ...");
		splashShell = createSplashShell();
		splashShell.open();
	}
	
	@Override
    public Shell getSplashShell() {
	    return splashShell;
	}
	
	private Shell createSplashShell() {
		final Shell shell = new Shell(SWT.TOOL | SWT.NO_TRIM);
		Image image = createBackgroundImage(shell);
		shell.setBackgroundImage(image);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		Rectangle imageBounds = image.getBounds();
		
//		final GridLayout layout = new GridLayout();
//		layout.numColumns = 1;
//		layout.marginHeight = 40;
//		layout.marginWidth = 20;
//		layout.verticalSpacing = 6;
//		layout.horizontalSpacing = 6;
//		shell.setLayout(layout);
		
		// TODO Set the position and style of the text from outside to make the service reusable
		textLabel = createTextLabel(shell);
		if (textRect == null) {
			// 2 lines tall (was 1): LifecycleManager now accumulates every startup step into one
			// growing, comma-separated message (see its appendProgress() Javadoc) instead of
			// replacing it - a single line was too short to show more than the first ~15-20
			// characters of that combined text without SWT.WRAP silently clipping the rest at
			// the label's bottom edge. Kept anchored to the same bottom edge as before (only
			// grown upward, to 50px from the original 40px) so it still doesn't encroach on the
			// progress bar area below it, while staying clear of the background image's icon row
			// above it.
			textRect = new Rectangle(10, imageBounds.height - 60,
					imageBounds.width - 40, 50);
		}
		textLabel.setBounds(textRect);

		if (totalWork != 0) {
			progressBar = createProgressBar(shell);
			if (progressRect == null) {
				progressRect = new Rectangle(0, imageBounds.height - 10
						- progressBar.getBorderWidth(), imageBounds.width
						- progressBar.getBorderWidth(), 10);
			}
			progressBar.setBounds(progressRect);
		}

		shell.setSize(imageBounds.width, imageBounds.height);
		shell.setLocation(getMonitorCenter(shell));
		return shell;
	}

	private ProgressBar createProgressBar(Shell shell) {
		int style = SWT.BORDER | SWT.SMOOTH | SWT.HORIZONTAL;
		if (totalWork < 0) {
			// FIXME This does not work ... could be an issue with the UI thread
			// being busy in the background, so the progress bar has no chance
			// to update in indeterminate mode
			style |= SWT.INDETERMINATE;
		}
		ProgressBar pb = new ProgressBar(shell, style);
		if (totalWork > 0) {
			pb.setMinimum(0);
			pb.setMaximum(totalWork);
		}
		return pb;
	}

	private Image createBackgroundImage(Shell parent) {
		final Image splashImage = getImageDescriptor(pluginId, splashPath).createImage();
		parent.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				splashImage.dispose();
			}
		});
		return splashImage;
	}
	
	private ImageDescriptor getImageDescriptor(String pluginId, String path) {
		try {
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			URL url = new URL("platform:/plugin/" + pluginId + path);
			url = FileLocator.resolve(url);
			return ImageDescriptor.createFromURL(url);
		} catch (MalformedURLException e) {
			String msg = NLS.bind("The image path {0} in not a valid location the bundle {1}.", path, pluginId);
			throw new RuntimeException(msg, e);
		} catch (IOException e) {
			String msg = NLS.bind("The image {0} was not found in the bundle {1}.", path, pluginId);
			throw new RuntimeException(msg, e);
		}
	}

	private Label createTextLabel(Composite parent) {
		Label label = new Label(parent, SWT.WRAP);
//		GridData gd = new GridData();
//		gd.horizontalAlignment = SWT.FILL;
//		gd.verticalAlignment = SWT.BOTTOM;
//		gd.grabExcessHorizontalSpace = true;
//		gd.grabExcessVerticalSpace = true;
//		label.setLayoutData(gd);
		
		if (textColor == null) {
			textColor = parent.getDisplay().getSystemColor(SWT.COLOR_WHITE);
		}
		label.setForeground(textColor);
		if (textFont == null) {
			textFont = parent.getDisplay().getSystemFont();
		}
		label.setFont(textFont);

		if (nextMessage != null) {
			label.setText(nextMessage);
		}
		return label;
	}

	private Point getMonitorCenter(Shell shell) {
		// Prefer landing this Shell exactly where the native launcher already placed its own
		// splash bitmap, instead of independently recomputing a "center of primary monitor" -
		// the native launcher's centering (raw pixels, its own monitor-selection heuristic) and
		// this computation (SWT's DPI-scaled Display.getPrimaryMonitor()) don't always agree on
		// multi-monitor/HiDPI setups, which used to show the native splash and this one visibly
		// offset from each other instead of one cleanly handing off to the other.
		Point nativeSplashLocation = getNativeSplashLocation(shell.getDisplay());
		if (nativeSplashLocation != null) {
			return nativeSplashLocation;
		}
		Monitor primary = shell.getDisplay().getPrimaryMonitor ();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		return new Point(x, y);
	}

	/**
	 * When started via the native launcher (not from within the IDE), the equinox launcher
	 * exposes the already-open native splash window's handle via the
	 * "org.eclipse.equinox.launcher.splash.handle" system property (this is the same
	 * undocumented mechanism org.eclipse.ui.internal.WorkbenchPlugin#getSplashShell uses).
	 * Shell#internal_new() lets us wrap that handle to read back its real on-screen bounds -
	 * the actual position the native launcher computed - so our own Shell can match it exactly
	 * instead of guessing. Returns null (falling back to manual centering) whenever there is no
	 * native splash to align with, e.g. when launched from the IDE.
	 */
	private Point getNativeSplashLocation(Display display) {
		String splashHandle = System.getProperty("org.eclipse.equinox.launcher.splash.handle");
		if (splashHandle == null) {
			return null;
		}
		try {
			Rectangle bounds = Shell.internal_new(display, Long.parseLong(splashHandle.trim())).getBounds();
			return new Point(bounds.x, bounds.y);
		} catch (RuntimeException e) {
			return null;
		}
	}

	@Override
	public void close() {
		// isDisposed() guard: LifecycleManager's ACTIVATE subscriber can be invoked more than
		// once before its own unsubscribe() takes effect if several parts activate in a rapid,
		// nested burst during initial perspective construction (most likely on a genuinely
		// fresh profile with no saved perspective layout to just restore) - without this guard,
		// the second call hit an already-disposed Shell and threw
		// "org.eclipse.swt.SWTException: Widget is disposed" right after startup.
		if (splashShell != null && !splashShell.isDisposed()) {
			splashShell.close();
		}
		splashShell = null;
	}

	@Override
	public void setMessage(final String message) {
		if (textLabel != null && !textLabel.isDisposed()) {
			splashShell.getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					textLabel.setText(message);
					splashShell.layout();
					splashShell.update();
				}
			});
		} else {
			nextMessage  = message;
		}
	}
}