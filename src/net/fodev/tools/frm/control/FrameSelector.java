package net.fodev.tools.frm.control;

import javafx.scene.image.Image;
import net.fodev.tools.frm.model.FrmFrame;
import net.fodev.tools.frm.model.FrmHeader;

public class FrameSelector {
	final private static int defaultFramesPerSecond = 8;

	private FrmHeader header;
	private int direction = 0;
	private int frameOffset = 0;

	public FrameSelector() {
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
		header = FrmFileReader.readFrm(currentFile);
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

	public FrmFrame getCurrentFrame() {
		return header.getFrame(getCurrentFrameIndex());
	}

	public Image getImage() {
		FrmFrame frame = header.getFrame(getCurrentFrameIndex());
		Image image = FrmImageConverter.getJavaFXImage(frame.getData(), frame.getWidth(), frame.getHeight(),
				frame.getFrameOffset(getCurrentFrameIndex()));
		return image;
	}

	public Image[] getImagesForAnimation() {
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
			FrmFrame frame = header.getFrame(currentFrameIndex + i);
			images[i] = FrmImageConverter.getJavaFXImage(frame.getData(), frame.getWidth(), frame.getHeight(),
					frame.getFrameOffset(currentFrameIndex + i));
		}
		return images;
	}

	public int getFrameOffsetIndex() {
		return header.getFrame(getCurrentFrameIndex()).getFrameOffset(getCurrentFrameIndex());
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
}
