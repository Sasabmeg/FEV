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
}
