package net.fodev.tools.frm.control;

public class FrmUtils {

	public static String getFileExtension(String filename) {
		String extension = "";
		int i = filename.lastIndexOf('.');
		int p = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));
		if (i > p) {
		    extension = filename.substring(i+1);
		}
		return extension;
	}

	public static String getParentDir(String filenameAndPath) {
		String parent = "";
		int i = Math.max(filenameAndPath.lastIndexOf('\\'), filenameAndPath.lastIndexOf('/'));
		parent = filenameAndPath.substring(0, i + 1);
		return parent;
	}
}
