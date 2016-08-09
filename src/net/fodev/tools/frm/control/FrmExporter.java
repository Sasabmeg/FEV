package net.fodev.tools.frm.control;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import net.fodev.tools.frm.model.FoPalette;
import net.fodev.tools.frm.model.Frame;
import net.fodev.tools.frm.model.FrmFrame;
import net.fodev.tools.frm.model.FrmHeader;

public class FrmExporter {
	public static void exportSingleFrameToFile(Frame frame, int currentIndex, String filename, boolean hasBackground) throws IOException {
		try {
			Image image = frame.getImage(currentIndex, hasBackground);
			FrmImageConverter.writeImageToBmpFile(image, filename);
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
	}

	public static void exportAnimationToFile(FrmHeader header, String folderName, String frameFileName, boolean hasBackground) throws IOException {
		String newFolderName = folderName + "/" + frameFileName;
		new File(newFolderName).mkdirs();
		for (int direction = 0; direction < header.getTotalFrames() / header.getFramesPerDirection(); direction++) {
			for (int index = 0; index < header.getFramesPerDirection(); index++) {
				Frame frame = header.getFrame(direction * header.getFramesPerDirection() + index);
				exportSingleFrameToFile((FrmFrame)frame, 0, newFolderName + "/" + direction + "_" + index + ".png", hasBackground);
			}
		}
	}

	public static void exportDefaultFoPaletteIntoFile(String filename) throws IOException {
		BufferedImage bufferedImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
		IndexColorModel foColorModel = FoPalette.getDefaultColorModel(true);
		for (int i = 0; i < 16; i ++) {
			for (int j = 0; j < 16; j++) {
				int index = i * 16 + j;
				int red = foColorModel.getRed(index);
				int green = foColorModel.getGreen(index);
				int blue = foColorModel.getBlue(index);
				int rgb = 65536 * red + 256 * green + blue;
				for (int x = 0; x < 16; x++) {
					for (int y = 0; y < 16; y++) {
						bufferedImage.setRGB(j * 16 + y, i * 16 + x, rgb);
					}
				}
			}
		}
		File outputfile = new File(filename);
		ImageIO.write(bufferedImage, "png", outputfile);
	}
}
