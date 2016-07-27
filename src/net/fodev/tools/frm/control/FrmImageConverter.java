package net.fodev.tools.frm.control;

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
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.scene.image.Image;
import net.fodev.tools.frm.model.FoPalette;

public class FrmImageConverter {
	public static Image getJavaFXImage(byte[] rawPixels, int width, int height, int offset) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write((RenderedImage) createBufferedImage(rawPixels, width, height, offset), "png", out);
			out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		return new javafx.scene.image.Image(in);
	}

	private static BufferedImage createBufferedImage(byte[] pixels, int width, int height, int offset) {
		SampleModel sm = getIndexSampleModel(width, height);
		DataBuffer db = new DataBufferByte(pixels, width * height, offset);
		WritableRaster raster = Raster.createWritableRaster(sm, db, null);
		IndexColorModel cm = FoPalette.getDefaultColorModel();
		BufferedImage image = new BufferedImage(cm, raster, false, null);
		return image;
	}

	private static SampleModel getIndexSampleModel(int width, int height) {
		IndexColorModel icm = FoPalette.getDefaultColorModel();
		WritableRaster wr = icm.createCompatibleWritableRaster(1, 1);
		SampleModel sampleModel = wr.getSampleModel();
		sampleModel = sampleModel.createCompatibleSampleModel(width, height);
		return sampleModel;
	}
}
