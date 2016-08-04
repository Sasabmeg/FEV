package net.fodev.tools.frm.control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FrmFileSelector {
	private String currentFolder;
	private String currentExportFolder;
	private List<String> fileList;
	private List<String> filteredList;
	private int index = 0;
	private String mask = "*.frm";

	public FrmFileSelector() {
	}
	public void setCurrentFolder(String folder) {
		currentFolder = folder;
		fileList = getFilesInFolder(folder);
		filteredList = getFilteredList(fileList);
	}
	public String getCurrentFolder() {
		return currentFolder;
	}
	public boolean isFileListEmpty() {
		return fileList.isEmpty();
	}
	private List<String> getFilesInFolder(String path) {
		List<String> fileList = new ArrayList<String>();
		try {
			File folder = new File(path);
			for (File fileEntry : folder.listFiles()) {
				if (fileEntry.isDirectory()) {
					// recursive call? Nah
				} else {
					fileList.add(fileEntry.getName());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fileList;
	}

	private List<String> getFilteredList(List<String> list) {
		List<String> filtered = new ArrayList<String>();
		String regexpMatch = mask.replace(".", "\\.").replace("*", ".*").toLowerCase();
		for (String file : list) {
			if (file.toLowerCase().matches(regexpMatch)) {
				filtered.add(file);
			}
		}
		return filtered;
	}

	public void setCurrentFile(String fileNameAndpath) {
		index = filteredList.indexOf(fileNameAndpath);
		if (index < 0) {
			index = 0;
		}
	}

	public void setFileMask(String mask) {
		this.mask = mask;
		filteredList = getFilteredList(fileList);
		index = 0;
	}

	public void prev() {
		if (index > 0) {
			index--;
		}
	}

	public void next() {
		if (index < filteredList.size() - 1) {
			index++;
		}
	}

	public String getCurrentFileNameAndPath() throws Exception {
		if (index < 0 || index >= filteredList.size()) {
			if (filteredList.size() > 0) {
				return currentFolder + "/" + filteredList.get(0);
			} else {
				throw new Exception("File not found in filtered list.");
			}
		}
		return currentFolder + "/" + filteredList.get(index);
	}

	public String getCurrentFileName() throws Exception {
		if (index < 0 || index >= filteredList.size()) {
			if (filteredList.size() > 0) {
				return currentFolder + "/" + filteredList.get(0);
			} else {
				throw new Exception("File not found in filtered list.");
			}
		}
		return filteredList.get(index);
	}

	public int getMatchingFilesInList() {
		return filteredList.size();
	}

	public boolean isCurrentFileMatchingPattern() {
		String regexpMatch = mask.replace(".", "\\.").replace("*", ".*").toLowerCase();
		return fileList.get(index).toLowerCase().matches(regexpMatch);
	}

	public void firstMatchingPattern() {
		index = 0;
	}

	public String getCurrentExportFolder() {
		return currentExportFolder;
	}

	public void setCurrentExportFolder(String currentExportFolder) {
		this.currentExportFolder = currentExportFolder;
	}

	public int getCurrentIndex() {
		return index;
	}
	public void prevSkin() {
		try {
			String prefix = getCurrentFileName().substring(0,  5);
			int i = index - 1;
			boolean found = false;
			while (i >= 0 && !found) {
				if (!filteredList.get(i).startsWith(prefix)) {
					found = true;
					index = i;
					String nextPrefix = filteredList.get(i).substring(0, 5);
					boolean first = false;
					while (i > 0 && !first) {
						i--;
						if (!filteredList.get(i).startsWith(nextPrefix)) {
							index = i + 1;
							first = true;
						}
						if (i == 0) {
							index = 0;
							first = true;
						}
					}
				}
				i--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void nextSkin() {
		try {
			String prefix = getCurrentFileName().substring(0,  4);
			int i = index + 1;
			boolean found = false;
			while (i < filteredList.size() && !found) {
				if (!filteredList.get(i).startsWith(prefix)) {
					found = true;
					index = i;
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}