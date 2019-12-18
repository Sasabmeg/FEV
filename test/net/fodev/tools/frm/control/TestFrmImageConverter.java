package net.fodev.tools.frm.control;

import java.awt.image.BufferedImage;

//import javax.imageio.ImageIO;

import org.junit.Test;

public class TestFrmImageConverter {
	
	@Test
	public void getJavaFXImage_Simple_Savetransparent() {
		byte[] pixels = new byte[10000];
		int width = 100;
		int height = 100;
		int offset = 0;
		for (int i = 0; i < pixels.length; i++) {
			if ((i/10) % 10 == 4) {
				pixels[i] = 0;
			} else {
				pixels[i] = 1;
			}
		}
		BufferedImage image = FrmImageConverter.createBufferedImage(pixels, width, height, offset, null, false);
		//ImageIO.write(image, "png", );
	}

}
