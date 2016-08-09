package net.fodev.tools.frm.control;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FrmUtilsTest {
	@Test
	public void TestGetParentDirParser() {
		String filenameAndPath = "F:\\Sasa/23\\frm0.png";
		assertEquals("F:\\Sasa/23\\", FrmUtils.getParentDir(filenameAndPath));
	}
}
