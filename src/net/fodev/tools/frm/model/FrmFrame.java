package net.fodev.tools.frm.model;

import javafx.scene.image.Image;
import net.fodev.tools.frm.control.FrmImageConverter;

public class FrmFrame extends Frame {
	private byte data[];			//
	public FrmFrame() {
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte data[]) {
		this.data = data;
	}
	public int getFrameOffset(int index) {
		if (index >= 0 && (index + 1) * width * height <= data.length) {
			return index * width * height;
		} else {
			return 0;
		}
	}
	public Image getImage(int index, boolean hasBackground) {
		return FrmImageConverter.getJavaFXImage(data, width, height, getFrameOffset(index), hasBackground);
	}
}
