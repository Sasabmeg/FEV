package net.fodev.tools.frm.control;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FrmTileSelectorTest {
	@Test
	public void Test() {
		FrmFileSelector selector = new FrmFileSelector();
		List<String> fileList = new ArrayList<String>();
		fileList.add("f:\\crittters\\HFJMPSEA.FRM");
		fileList.add("f:\\crittters\\HANPWRGC.FRM");
		fileList.add("f:\\crittters\\HANPWRGB.FRM");
		//selector.setFileList(fileList);
		selector.prev();
	}
}
