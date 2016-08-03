package net.fodev.tools.frm.model;

import java.util.ArrayList;

public class FrmHeader extends Header {
	private int version;					//	unsigned
	private short actionFrame;				//	unsigned
	private short directionSwitchX[];		//	signed, size 6
	private short directionSwitchY[];		//	signed, size 6
	private int directionDataOffset[];		//	signed, size 6
	private int frameDataSize;				//	unsigned

	public FrmHeader() {
		directionSwitchX = new short[6];
		directionSwitchY = new short[6];
		directionDataOffset = new int[6];
		frames = new ArrayList<Frame>();
	}
	public int getVersion() {
		return version;
	}
	public int setVersion(int version) {
		this.version = version;
		return version;
	}
	public short getActionFrame() {
		return actionFrame;
	}
	public void setActionFrame(short actionFrame) {
		this.actionFrame = actionFrame;
	}
	public short getDirectionSwitchX(int index) {
		if (index < 0 || index > 5) {
			throw new IndexOutOfBoundsException("Cannot get index " + index + " for direction switch X array. Must be between 0 and 5.");
		}
		return directionSwitchX[index];
	}
	public void setDirectionSwitchX(int index, short value) {
		if (index < 0 || index > 5) {
			throw new IndexOutOfBoundsException("Cannot set index " + index + " for direction switch X array. Must be between 0 and 5.");
		}
		this.directionSwitchX[index] = value;
	}

	public short getDirectionSwitchY(int index) {
		if (index < 0 || index > 5) {
			throw new IndexOutOfBoundsException("Cannot get index " + index + " for direction switch Y array. Must be between 0 and 5.");
		}
		return directionSwitchY[index];
	}
	public void setDirectionSwitchY(int index, short value) {
		if (index < 0 || index > 5) {
			throw new IndexOutOfBoundsException("Cannot set index " + index + " for direction switch Y array. Must be between 0 and 5.");
		}
		this.directionSwitchY[index] = value;
	}

	public int getDirectionDataOffset(int index) {
		if (index < 0 || index > 5) {
			throw new IndexOutOfBoundsException("Cannot get index " + index + " for direction data offset array. Must be between 0 and 5.");
		}
		return directionDataOffset[index];
	}
	public void setDirectionDataOffset(int index, int value) {
		if (index < 0 || index > 5) {
			throw new IndexOutOfBoundsException("Cannot set index " + index + " for direction data offset array. Must be between 0 and 5.");
		}
		this.directionDataOffset[index] = value;
	}

	public int getFrameDataSize() {
		return frameDataSize;
	}
	public void setFrameDataSize(int frameDataSize) {
		this.frameDataSize = frameDataSize;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Version: " + getVersion() + System.lineSeparator());
		sb.append("Frames per second: " + getFramesPerSecond() + System.lineSeparator());
		sb.append("Action frame index: " + getActionFrame() + System.lineSeparator());
		sb.append("Frames per direction: " + getFramesPerDirection() + System.lineSeparator());
		sb.append("DirectionSwitchX: ");
		for (int i = 0; i < 6; i++) {
			sb.append(getDirectionSwitchX(i) + " ");
		}
		sb.append(System.lineSeparator());
		sb.append("DirectionSwitchY: ");
		for (int i = 0; i < 6; i++) {
			sb.append(getDirectionSwitchY(i) + " ");
		}
		sb.append(System.lineSeparator());
		sb.append("DirectionDataOffset: ");
		for (int i = 0; i < 6; i++) {
			sb.append(getDirectionDataOffset(i) + " ");
		}
		sb.append(System.lineSeparator());
		sb.append("FrameDataSize: " + getFrameDataSize() + System.lineSeparator());
		sb.append("Total number of frames: " + frames.size());
		return sb.toString();
	}
}