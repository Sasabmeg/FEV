package net.fodev.tools.frm.model;

import javafx.scene.image.Image;

public class FofrmHeader extends Header {
	private int offsetX;
	private int offsetY;
	private int directionDefaultOffsetX[];
	private int directionDefaultOffsetY[];
	public FofrmHeader() {
		framesPerSecond = 10;
		framesPerDirection = 1;
		offsetX = 0;
		offsetY = 0;
		directionDefaultOffsetX = new int[6];
		directionDefaultOffsetY = new int[6];
	}
	public int getOffsetX() {
		return offsetX;
	}
	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}
	public int getOffsetY() {
		return offsetY;
	}
	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Format: fofrm" + System.lineSeparator());
		sb.append("Frames per second: " + getFramesPerSecond() + System.lineSeparator());
		sb.append("Frames per direction: " + getFramesPerDirection() + System.lineSeparator());
		sb.append("Total number of frames: " + getTotalFrames() + System.lineSeparator());
		return sb.toString();
	}
	public int getDirectionDefaultOffsetX(int dir) throws Exception {
		if (dir < 0 || dir >= 6) {
			throw new Exception("Direction is out of bounds. (0-6) -> " + dir);
		}
		return directionDefaultOffsetX[dir];
	}
	public void setDirectionDefaultOffsetX(int dir, int value) throws Exception {
		if (dir < 0 || dir >= 6) {
			throw new Exception("Direction is out of bounds. (0-6) -> " + dir);
		}
		directionDefaultOffsetX[dir] = value;
	}
	public int getDirectionDefaultOffsetY(int dir) throws Exception {
		if (dir < 0 || dir >= 6) {
			throw new Exception("Direction is out of bounds. (0-6) -> " + dir);
		}
		return directionDefaultOffsetY[dir];
	}
	public void setDirectionDefaultOffsetY(int dir, int value) throws Exception {
		if (dir < 0 || dir >= 6) {
			throw new Exception("Direction is out of bounds. (0-6) -> " + dir);
		}
		directionDefaultOffsetY[dir] = value;
	}
	@Override
	public Image getImage(int direction, int frameIndex, boolean hasBackground) throws Exception {
		FofrmFrame frame = (FofrmFrame) getFrame(direction * framesPerDirection + frameIndex);
		Image image = frame.getData();
		return image;
	}
}
