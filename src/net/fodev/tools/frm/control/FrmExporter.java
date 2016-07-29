package net.fodev.tools.frm.control;

import java.io.IOException;

import javafx.scene.image.Image;
import net.fodev.tools.frm.model.FrmFrame;

public class FrmExporter {
	public static void exportFrameToFile(FrmFrame frame, int currentIndex, String filename) throws IOException {
		Image image = FrmImageConverter.getJavaFXImage(frame.getData(), frame.getWidth(), frame.getHeight(),
				frame.getFrameOffset(currentIndex));
		FrmImageConverter.WriteImageToBmpFile(image, filename);;
	}
}
