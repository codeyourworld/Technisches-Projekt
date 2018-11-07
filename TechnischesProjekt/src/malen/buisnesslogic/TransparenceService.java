package malen.buisnesslogic;

import static java.awt.GraphicsDevice.WindowTranslucency.TRANSLUCENT;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TransparenceService {

	public static void setTransparency(JFrame frame) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();

		// If translucent windows aren't supported, exit.
		if (!gd.isWindowTranslucencySupported(TRANSLUCENT)) {
			System.err.println("Translucency is not supported");
		} else {

			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					JFrame.setDefaultLookAndFeelDecorated(true);
					frame.setOpacity(0.55f);
					frame.setVisible(true);
					
				}
			});
		}
	}

}
