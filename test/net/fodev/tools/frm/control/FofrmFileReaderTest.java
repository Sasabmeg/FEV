package net.fodev.tools.frm.control;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.fodev.tools.frm.model.FofrmFrame;
import net.fodev.tools.frm.model.FofrmHeader;

public class FofrmFileReaderTest {
	@Test
	public void TestHeaderParser() {
		List<String> lines =  new ArrayList<String>();
		lines.add("fPs=11");
		lines.add("count = 2  ");
		lines.add("offs_x = 3  ");
		lines.add("offs_y = 4  ");
		lines.add("[dir_0]");
		lines.add("offs_x=5");
		lines.add("offs_y=6");
		lines.add("next_x=7");
		lines.add("next_y=8");
		lines.add("frm_0=NE0.png");
		lines.add("next_x=9");
		lines.add("next_y=10");
		lines.add("frm_1=NE1.png");
		lines.add("[dir_1]");
		lines.add("frm_0=E0.png");
		lines.add("frm_1=E1.png");
		lines.add("[dir_2]");
		lines.add("frm_0=SE0.png");
		lines.add("frm_1=SE1.png");
		lines.add("[dir_3]");
		lines.add("frm_0=SW0.png");
		lines.add("frm_1=SW1.png");
		lines.add("[dir_4]");
		lines.add("frm_0=W0.png");
		lines.add("frm_1=W1.png");
		lines.add("[dir_5]");
		lines.add("frm_0=NW0.png");
		lines.add("frm_1=NW1.png");
		try {
			FofrmHeader header = FofrmFileReader.parseLines(lines);
			assertEquals(11, header.getFramesPerSecond());
			assertEquals(2, header.getFramesPerDirection());
			assertEquals(3, header.getOffsetX());
			assertEquals(4, header.getOffsetY());
			assertEquals(5, header.getDirectionDefaultOffsetX(0));
			assertEquals(6, header.getDirectionDefaultOffsetY(0));

			assertEquals(7, ((FofrmFrame)header.getFrame(0)).getOffsetX());
			assertEquals(8, ((FofrmFrame)header.getFrame(0)).getOffsetY());
			assertEquals("NE0.png", ((FofrmFrame)header.getFrame(0)).getFileName());

			assertEquals(9, ((FofrmFrame)header.getFrame(1)).getOffsetX());
			assertEquals(10, ((FofrmFrame)header.getFrame(1)).getOffsetY());
			assertEquals("NE1.png", ((FofrmFrame)header.getFrame(1)).getFileName());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
