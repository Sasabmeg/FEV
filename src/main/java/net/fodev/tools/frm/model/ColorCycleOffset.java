package net.fodev.tools.frm.model;

public class ColorCycleOffset {
	public int slimeOffset;
	public int shorelineOffset;
	public int slowfireOffset;
	public int fastfireOffset;
	public int monitorsOffset;
	public int alarmOffset;

	public ColorCycleOffset() {
		slimeOffset = 0;
		shorelineOffset = 0;
		slowfireOffset = 0;
		fastfireOffset = 0;
		monitorsOffset = 0;
		alarmOffset = 0;
	}

	public void step() {
		slimeOffset++;
		shorelineOffset++;
		slowfireOffset++;
		fastfireOffset++;
		monitorsOffset++;
		alarmOffset++;
	}
}
