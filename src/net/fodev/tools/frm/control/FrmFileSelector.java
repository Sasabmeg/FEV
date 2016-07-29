package net.fodev.tools.frm.control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FrmFileSelector {
	private String currentFolder;
	private String currentExportFolder;
	private List<String> fileList;
	private int index = 0;
	private String mask = "*.frm";

	public FrmFileSelector() {
	}

	public void setFileList(List<String> list) {
		fileList = list;
	}

	public void setCurrentFolder(String folder) {
		currentFolder = folder;
		fileList = getFilesInFolder(folder);
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
					// recursive call?
				} else {
					fileList.add(fileEntry.getName());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// fileList.sort(null);
		return fileList;
	}

	public void setCurrentFile(String fileNameAndpath) {
		index = fileList.indexOf(fileNameAndpath);
		if (index < 0) {
			index = 0;
		}
	}

	public void setFileMask(String mask) {
		this.mask = mask;
	}

	public void prev() {
		int oldIndex = index;
		boolean found = false;
		String regexpMatch = mask.replace(".", "\\.").replace("*", ".*").toLowerCase();
		while (index > 0 && !found) {
			index--;
			if (fileList.get(index).toLowerCase().matches(regexpMatch)) {
				found = true;
			}
		}
		if (!found) {
			index = fileList.size();
			while (index > oldIndex && !found) {
				index--;
				if (fileList.get(index).toLowerCase().matches(regexpMatch)) {
					found = true;
				}
			}
		}
	}

	public void next() {
		int oldIndex = index;
		boolean found = false;
		String regexpMatch = mask.replace(".", "\\.").replace("*", ".*").toLowerCase();
		while (index < fileList.size() - 1 && !found) {
			index++;
			if (fileList.get(index).toLowerCase().matches(regexpMatch)) {
				found = true;
			}
		}
		if (!found) {
			index = -1;
			while (index < oldIndex && !found) {
				index++;
				if (fileList.get(index).toLowerCase().matches(regexpMatch)) {
					found = true;
				}
			}
		}
	}

	public String getCurrentFileNameAndPath() throws Exception {
		if (index < 0 || index >= fileList.size()) {
			throw new Exception("No files found with specific index");
		}
		return currentFolder + "/" + fileList.get(index);
	}

	public String getCurrentFileName() throws Exception {
		if (index < 0 || index >= fileList.size()) {
			throw new Exception("No files found with specific index");
		}
		return fileList.get(index);
	}

	public int getTotalFilesInList() {
		return fileList.size();
	}

	public int getMatchingFilesInList() {
		int ret = 0;
		String regexpMatch = mask.replace(".", "\\.").replace("*", ".*").toLowerCase();
		for (String filename : fileList) {
			if (filename.toLowerCase().matches(regexpMatch)) {
				ret++;
			}
		}
		return ret;
	}

	public boolean isCurrentFileMatchingPattern() {
		String regexpMatch = mask.replace(".", "\\.").replace("*", ".*").toLowerCase();
		return fileList.get(index).toLowerCase().matches(regexpMatch);
	}

	public void firstMatchingPattern() {
		String regexpMatch = mask.replace(".", "\\.").replace("*", ".*").toLowerCase();
		for (int i = 0; i < fileList.size(); i++) {
			if (fileList.get(i).toLowerCase().matches(regexpMatch)) {
				index = i;
				return;
			}
		}
	}

	public String getCurrentExportFolder() {
		return currentExportFolder;
	}

	public void setCurrentExportFolder(String currentExportFolder) {
		this.currentExportFolder = currentExportFolder;
	}
}