package net.fodev.tools.frm.control;

import java.io.File;
import java.io.IOException;

import javafx.scene.image.Image;
import net.fodev.tools.frm.model.FrmFrame;
import net.fodev.tools.frm.model.FrmHeader;

public class FrmExporter {
	public static void exportSingleFrameToFile(FrmFrame frame, int currentIndex, String filename) throws IOException {
		Image image = FrmImageConverter.getJavaFXImage(frame.getData(), frame.getWidth(), frame.getHeight(),
				frame.getFrameOffset(currentIndex));
		FrmImageConverter.WriteImageToBmpFile(image, filename);;
	}

	public static void exportAnimationToFile(FrmHeader header, String folderName, String frameFileName) throws IOException {
		String newFolderName = folderName + "/" + frameFileName;
		new File(newFolderName).mkdirs();
		for (int direction = 0; direction < header.getTotalFrames() / header.getFramesPerDirection(); direction++) {
			for (int index = 0; index < header.getFramesPerDirection(); index++) {
				FrmFrame frame = header.getFrame(direction * header.getFramesPerDirection() + index);
				exportSingleFrameToFile(frame, 0, newFolderName + "/" + direction + "_" + index + ".png");
			}
		}
	}
}
