package net.fodev.tools.frm.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public abstract class Header {
	protected short framesPerSecond;			//	unsigned
	protected short framesPerDirection;			//	unsigned
	protected List<Frame> frames;

	public Header() {
		frames = new ArrayList<Frame>();
	}

	public short getFramesPerSecond() {
		return framesPerSecond;
	}
	public void setFramesPerSecond(short framesPerSecond) {
		this.framesPerSecond = framesPerSecond;
	}
	public short getFramesPerDirection() {
		return framesPerDirection;
	}
	public void setFramesPerDirection(short framesPerDirection) {
		this.framesPerDirection = framesPerDirection;
	}
	public void addFrame(Frame frame) {
		frames.add(frame);
	}
	public Frame getFrame(int index) {
		if (frames == null) {
			throw new NullPointerException("Frames list is not initialized.");
		}
		if (index < 0 || index >= frames.size()) {
			throw new IndexOutOfBoundsException("Cannot get index " + index + " for frame list. Curent size of list is: " + frames.size());
		}
		return frames.get(index);
	}
	public int getTotalFrames() {
		return frames.size();
	}
	public abstract Image getImage(int direction, int frameIndex, boolean hasBackground) throws Exception;
}
