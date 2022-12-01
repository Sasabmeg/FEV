package net.fodev.tools.frm.model;

public abstract class Frame {
	protected short width;			//	unsigned
	protected short height;			//	unsigned
	protected int	dataSize;		//	unsigned
	protected short offsetX;		//	signed
	protected short offsetY;		//	signed
	public Frame() {
	}
	public short getWidth() {
		return width;
	}
	public void setWidth(short width) {
		this.width = width;
	}
	public short getHeight() {
		return height;
	}
	public void setHeight(short height) {
		this.height = height;
	}
	public int getDataSize() {
		return dataSize;
	}
	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}
	public short getOffsetX() {
		return offsetX;
	}
	public void setOffsetX(short offsetX) {
		this.offsetX = offsetX;
	}
	public short getOffsetY() {
		return offsetY;
	}
	public void setOffsetY(short offsetY) {
		this.offsetY = offsetY;
	}
	public int getFrameSize() {
		return width * height;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FrameWidth: " + getWidth() + System.lineSeparator());
		sb.append("FrameHeight: " + getHeight() + System.lineSeparator());
		sb.append("DataSize: " + getDataSize() + System.lineSeparator());
		sb.append("OffsetX: " + getOffsetX() + System.lineSeparator());
		sb.append("OffsetY: " + getOffsetY() + System.lineSeparator());
		return sb.toString();
	}
}
