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
}