package net.fodev.tools.frm.control;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.IndexColorModel;
import java.awt.image.RGBImageFilter;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import net.fodev.tools.frm.model.ColorCycleOffset;
import net.fodev.tools.frm.model.FoPalette;

public class FrmImageConverter {
	public static Image getJavaFXImage(byte[] rawPixels, int width, int height, int offset, boolean hasBackground) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.out.println(String.format("rawPixels lenght = %d, width = %d, height = %d, offset = %d", 
				rawPixels.length, width, height, offset));
		try {
			BufferedImage bufferedImage = createBufferedImage(rawPixels, width, height, offset, null, hasBackground);
			ImageIO.write(bufferedImage, "png", out);
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
	
	//for export
    public static byte[] getSingleColorCycledImage(byte[] rawPixels, int width, int height, 
            int offset, ColorCycleOffset colorCycleOffset, boolean hasBackground) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try {
            ImageIO.write((RenderedImage) createBufferedImage(rawPixels, width, height, offset, colorCycleOffset, hasBackground), "png", out);
            out.flush();
        } catch (IOException ex) {            
            ex.printStackTrace();
        }
        byte[] result = out.toByteArray();
        out.close();
        return result;      
    }
    
    public static void writeCycledImageToFile(byte[] image, String filename) throws IOException {       
        //System.out.println("file: " + filename);
        FileOutputStream outputF = new FileOutputStream(new File(filename));
        outputF.write(image);
        outputF.close();
    }

	static BufferedImage createBufferedImage(byte[] pixels, int width, int height, int offset, ColorCycleOffset cco, boolean hasBackground) {
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
		//return ImageToBufferedImage( TransformColorToTransparency( image, new Color(0, 0, 255), new Color(0, 0, 255) ), width, height );
		return image;
	}

	/**
	 * Used to change hard blue to transparent.
	 * @param image
	 * @param c1
	 * @param c2
	 * @return
	 */
	private static java.awt.Image TransformColorToTransparency(BufferedImage image, Color c1, Color c2)
	{
	    // Primitive test, just an example
	    final int r1 = c1.getRed();
	    final int g1 = c1.getGreen();
	    final int b1 = c1.getBlue();
	    final int r2 = c2.getRed();
	    final int g2 = c2.getGreen();
	    final int b2 = c2.getBlue();
	    ImageFilter filter = new RGBImageFilter()
	    {
			public final int filterRGB(int x, int y, int rgb)
			{
			    int r = (rgb & 0xFF0000) >> 16;
			    int g = (rgb & 0xFF00) >> 8;
			    int b = rgb & 0xFF;
			    if (r >= r1 && r <= r2 &&
			        g >= g1 && g <= g2 &&
			        b >= b1 && b <= b2)
			    {
			      // Set fully transparent but keep color
			      return rgb & 0xFFFFFF;
			    }
			    return rgb;
			}
	    };

	    ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
	      return Toolkit.getDefaultToolkit().createImage(ip);
	}
	
	private static BufferedImage ImageToBufferedImage(java.awt.Image image, int width, int height)
	{
		BufferedImage dest = new BufferedImage(
		    width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = dest.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		return dest;
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
		@SuppressWarnings("restriction")
		BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
		BufferedImage imageRGB;
		if (filename.endsWith("bmp")) {
			// Remove alpha-channel from buffered image, otherwise BMP save fails
			imageRGB = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.OPAQUE);
		} else {
			imageRGB = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		}
		Graphics2D graphics = imageRGB.createGraphics();
		graphics.drawImage(bufferedImage, 0, 0, null);
		ImageIO.write(imageRGB, FrmUtils.getFileExtension(filename), new File(filename));
		graphics.dispose();
	}

}
