package net.fodev.tools.frm.control;

import javafx.scene.image.Image;
import net.fodev.tools.frm.model.ColorCycleOffset;
import net.fodev.tools.frm.model.Frame;
import net.fodev.tools.frm.model.FrmFrame;
import net.fodev.tools.frm.model.Header;

public class FrameSelector {
	final private static int defaultFramesPerSecond = 8;
	private Header header;
	private int direction = 0;
	private int frameOffset = 0;
	private boolean hasBackground;

	public FrameSelector() {
	}

	public boolean isHasBackground() {
		return hasBackground;
	}

	public void setHasBackground(boolean hasBackground) {
		this.hasBackground = hasBackground;
	}

	public String getHeaderInfo() {
		return header.toString();
	}

	public String getFrameInfo() {
		return header.getFrame(getCurrentFrameIndex()).toString();
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int dir) {
		direction = dir;
	}

	public void readHeaderFromFile(String currentFile) {
		String extension = FrmUtils.getFileExtension(currentFile).toLowerCase();
		if (extension.equals("frm")) {
			header = FrmFileReader.readFrm(currentFile);
		}
		if (extension.equals("fofrm")) {
			header = FofrmFileReader.readFofrm(currentFile);
		}
	}

	public int getFramesPerDirection() {
		return header.getFramesPerDirection();
	}

	public int getCurrentFrameIndex() {
		int framesPerDirection = header.getFramesPerDirection();
		int totalFrames = header.getTotalFrames();
		int currentFrameIndex = 0;
		if (frameOffset < 0 || frameOffset > framesPerDirection - 1) {
			frameOffset = 0;
		}
		if (direction + 1 <= totalFrames / framesPerDirection) {
			currentFrameIndex = framesPerDirection * direction + frameOffset;
		} else {
			currentFrameIndex = 0;
		}
		return currentFrameIndex;
	}

	public Frame getCurrentFrame() {
		return header.getFrame(getCurrentFrameIndex());
	}

	public Image getImage() {
		try {
			Frame frame = header.getFrame(getCurrentFrameIndex());
			Image image = frame.getImage(getCurrentFrameIndex(), hasBackground);
			return image;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public Image[] getImagesForAnimation() {
		try {
		Image[] images;
		int framesPerDirection = header.getFramesPerDirection();
		images = new Image[framesPerDirection];
		int totalFrames = header.getTotalFrames();
		int currentFrameIndex;
		if (direction + 1 <= totalFrames / framesPerDirection) {
			currentFrameIndex = framesPerDirection * direction;
		} else {
			currentFrameIndex = 0;
		}
		for (int i = 0; i < framesPerDirection; i++) {
			Frame frame = header.getFrame(currentFrameIndex + i);
			images[i] = frame.getImage(currentFrameIndex + i, hasBackground);
		}
		return images;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public Image[] getImagesForColorCycleAnimation() {
		Image[] images;
		images = new Image[5];
		ColorCycleOffset cco = new ColorCycleOffset();
		for (int i = 0; i < 5; i++) {
			FrmFrame frame = (FrmFrame) header.getFrame(0);
			images[i] = FrmImageConverter.getJavaFXImageWithColorCycle(frame.getData(), frame.getWidth(),
					frame.getHeight(), frame.getFrameOffset(0), cco, hasBackground);
			cco.step();
		}
		return images;
	}

	public int getFrameIndex() {
		return getCurrentFrameIndex();
	}

	public void next() {
		if (frameOffset < header.getFramesPerDirection() - 1) {
			frameOffset++;
		}
	}

	public void last() {
		frameOffset = header.getFramesPerDirection() - 1;
	}

	public void prev() {
		if (frameOffset > 0) {
			frameOffset--;
		}
	}

	public void first() {
		frameOffset = 0;
	}

	public int getFramesPerSecond() {
		return header.getFramesPerSecond() > 0 ? header.getFramesPerSecond() : defaultFramesPerSecond;
	}

	public Header getHeader() {
		return header;
	}
}
