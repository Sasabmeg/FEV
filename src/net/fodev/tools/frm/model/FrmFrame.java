package net.fodev.tools.frm.model;

public class FrmFrame {
	private short width;			//	unsigned
	private short height;			//	unsigned
	private int	dataSize;			//	unsigned
	private short offsetX;			//	signed
	private short offsetY;			//	signed
	private byte data[];			//
	public FrmFrame() {
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
