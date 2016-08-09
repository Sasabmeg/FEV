package net.fodev.tools.frm.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.image.Image;
import net.fodev.tools.frm.model.FofrmFrame;
import net.fodev.tools.frm.model.FofrmHeader;

public class FofrmFileReader {
	private static final String FPS = "(^\\s*fps\\s*=\\s*)(\\d*)(\\s*$)";
	private static final String COUNT = "(^\\s*count\\s=\\s*)(\\d*)(\\s*$)";
	private static final String OFFSET_X = "(^\\s*offs_x\\s*=\\s*)(\\d*)(\\s*$)";
	private static final String OFFSET_Y = "(^\\s*offs_y\\s*=\\s*)(\\d*)(\\s*$)";
	private static final String DIR = "(^\\s*\\[dir_)(\\d*)(\\]\\s*$)";
	private static final String NEXT_OFFSET_X = "(^\\s*next_x\\s*=\\s*)(\\d*)(\\s*$)";
	private static final String NEXT_OFFSET_Y = "(^\\s*next_y\\s*=\\s*)(\\d*)(\\s*$)";
	private static final String FRAME = "(^\\s*frm_\\d=\\s*)(.*)(\\s*$)";

	public static FofrmHeader readFofrm(String file) {
		FofrmHeader header = null;
		List<String> lines = readLinesFromFile(file);
		try {
			header = parseLines(lines);
			loadImages(header, FrmUtils.getParentDir(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return header;
	}

	private static void loadImages(FofrmHeader header, String parentDir) {
		for (int i = 0; i < header.getTotalFrames(); i++) {
			FofrmFrame frame = (FofrmFrame) header.getFrame(i);
			String fileUrl = parentDir + frame.getFileName();
			System.out.println(fileUrl);
			File file = new File(fileUrl);
			System.out.println(file.toURI().toString());
			Image img = new Image(file.toURI().toString());
			frame.setData(img);
		}
	}

	public static List<String> readLinesFromFile(String file) {
		List<String> lines = readLines(file);
		return lines;
	}

	public static FofrmHeader parseLines(List<String> lines) throws Exception {
		FofrmHeader header = new FofrmHeader();
		int i = 0;
		while (i < lines.size()) {
			String line = lines.get(i);
			Pattern pattern;
			Matcher matcher;
			pattern = Pattern.compile(FPS, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(line);
			if (matcher.find()) {
				header.setFramesPerSecond(Short.parseShort(matcher.group(2)));
			}

			pattern = Pattern.compile(COUNT, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(line);
			if (matcher.find()) {
				header.setFramesPerDirection(Short.parseShort(matcher.group(2)));
			}

			Pattern patternOffsetX = Pattern.compile(OFFSET_X, Pattern.CASE_INSENSITIVE);
			matcher = patternOffsetX.matcher(line);
			if (matcher.find()) {
				header.setOffsetX(Integer.parseInt(matcher.group(2)));
			}

			Pattern patternOffsetY = Pattern.compile(OFFSET_Y, Pattern.CASE_INSENSITIVE);
			matcher = patternOffsetY.matcher(line);
			if (matcher.find()) {
				header.setOffsetY(Integer.parseInt(matcher.group(2)));
			}

			Pattern patternDir = Pattern.compile(DIR, Pattern.CASE_INSENSITIVE);
			matcher = patternDir.matcher(line);
			if (matcher.find()) {
				int dir = Integer.parseInt(matcher.group(2));
				if (dir < 0 || dir >= 6) {
					throw new Exception("Direction is out of bounds. (0-6) -> " + dir);
				}
				Pattern patternNextX = Pattern.compile(NEXT_OFFSET_X, Pattern.CASE_INSENSITIVE);
				Pattern patternNextY = Pattern.compile(NEXT_OFFSET_Y, Pattern.CASE_INSENSITIVE);
				Pattern patternFrame = Pattern.compile(FRAME, Pattern.CASE_INSENSITIVE);
				FofrmFrame frame = new FofrmFrame();
				boolean done = false;
				i++;
				while (i < lines.size() && !done) {
					line = lines.get(i);

					matcher = patternOffsetX.matcher(line);
					if (matcher.find()) {
						System.out.println("dir_" + dir + " offsetX -> " + line);
						header.setDirectionDefaultOffsetX(dir, Short.parseShort(matcher.group(2)));
					}

					matcher = patternOffsetY.matcher(line);
					if (matcher.find()) {
						System.out.println("dir_" + dir + " offsetY -> " + line);
						header.setDirectionDefaultOffsetY(dir, Short.parseShort(matcher.group(2)));
					}

					matcher = patternNextX.matcher(line);
					if (matcher.find()) {
						System.out.println("dir_" + dir + " nextX -> " + line);
						frame.setOffsetX(Short.parseShort(matcher.group(2)));
					}

					matcher = patternNextY.matcher(line);
					if (matcher.find()) {
						System.out.println("dir_" + dir + " nextY -> " + line);
						frame.setOffsetY(Short.parseShort(matcher.group(2)));
					}

					matcher = patternFrame.matcher(line);
					if (matcher.find()) {
						System.out.println("dir_" + dir + " frm -> " + line);
						frame.setFileName(matcher.group(2));
						header.addFrame(frame);
						frame = new FofrmFrame();
					}

					matcher = patternDir.matcher(line);
					if (matcher.find()) {
						done = true;
						i -= 2;
					}
					i++;
				}
			}
			i++;
		}
		return header;
	}

	private static List<String> readLines(String file) {
		List<String> lines = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
}