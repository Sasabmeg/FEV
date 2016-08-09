package net.fodev.tools.frm.model;

import javafx.scene.image.Image;

public class FofrmFrame extends Frame {
	private Image data;
	private String fileName;
	public Image getData() {
		return data;
	}
	public void setData(Image data) {
		this.data = data;
	}
	public FofrmFrame() {
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public Image getImage(int currentFrameIndex, boolean hasBackground) throws Exception {
		if (currentFrameIndex < 0 || currentFrameIndex >= 1) {
			throw new Exception("Index out of bounds at FofrmFrame: " + currentFrameIndex);
		}
		return data;
	}
}