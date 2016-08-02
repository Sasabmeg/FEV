package net.fodev.tools.frm.control;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import net.fodev.tools.frm.model.ColorCycleOffset;
import net.fodev.tools.frm.model.FoPalette;

public class FrmImageConverter {
	public static Image getJavaFXImage(byte[] rawPixels, int width, int height, int offset, boolean hasBackground) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write((RenderedImage) createBufferedImage(rawPixels, width, height, offset, null, hasBackground), "png", out);
			out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		return new javafx.scene.image.Image(in);
	}

	public static Image getJavaFXImageWithColorCycle(byte[] rawPixels, int width, int height, int offset, ColorCycleOffset colorCycleOffset, boolean hasBackground) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write((RenderedImage) createBufferedImage(rawPixels, width, height, offset, colorCycleOffset, hasBackground), "png", out);
			out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		return new javafx.scene.image.Image(in);

	}

	private static BufferedImage createBufferedImage(byte[] pixels, int width, int height, int offset, ColorCycleOffset cco, boolean hasBackground) {
		SampleModel sm = getIndexSampleModel(width, height, cco, hasBackground);
		DataBuffer db = new DataBufferByte(pixels, width * height * 2, offset);
		WritableRaster raster = Raster.createWritableRaster(sm, db, null);
		IndexColorModel cm;
		if (cco == null) {
			cm = FoPalette.getDefaultColorModel(hasBackground);
		} else {
			cm = FoPalette.getAnimatedDefaultColorModel(cco, hasBackground);
		}
		BufferedImage image = new BufferedImage(cm, raster, false, null);
		return image;
	}

	private static SampleModel getIndexSampleModel(int width, int height, ColorCycleOffset cco, boolean hasBackground) {
		IndexColorModel icm;
		if (cco == null) {
			icm = FoPalette.getDefaultColorModel(hasBackground);
		} else {
			icm = FoPalette.getAnimatedDefaultColorModel(cco, hasBackground);
		}
		WritableRaster wr = icm.createCompatibleWritableRaster(1, 1);
		SampleModel sampleModel = wr.getSampleModel();
		sampleModel = sampleModel.createCompatibleSampleModel(width, height);
		return sampleModel;
	}

	public static void writeImageToBmpFile(Image image, String filename) throws IOException {
		BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
		// Remove alpha-channel from buffered image:
		BufferedImage imageRGB = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.OPAQUE);
		Graphics2D graphics = imageRGB.createGraphics();
		graphics.drawImage(bufferedImage, 0, 0, null);
		ImageIO.write(imageRGB, FrmUtils.getFileExtension(filename), new File(filename));
		graphics.dispose();
	}

}
