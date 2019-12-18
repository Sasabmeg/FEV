package net.fodev.tools.frm.model;

import java.awt.image.IndexColorModel;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Arrays;

public class FoPalette {
	public static int[] getColorModelFromFile(String filename) {
		//	It will handle only up to 32k palette files, which were the only example I found at fallout.dat
		byte[] buff = new byte[33534];
		int[] data = new int[11178];
		try {
			FileInputStream fis = new FileInputStream(filename);
			DataInputStream dis = new DataInputStream(fis);
			//	put this in a loop or java handles this?
			int totalRead = dis.read(buff, 0, 33534);
			System.out.println("Bytes from color palette read: " + totalRead + " should be 33534");
			for (int i = 0; i < data.length; i++) {
				data[i] = buff[i * 3] * 65536 + buff[i * 3 + 1] * 256 + buff[i * 3 + 2];

			}
			dis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	    return data;
	}

	public static IndexColorModel getDefaultColorModel(boolean hasBackground) {
	    byte[] r = getDefaultColorModelRedComponents();
	    byte[] g = getDefaultColorModelGreenComponents();
	    byte[] b = getDefaultColorModelBlueComponents();
	    byte[] a = new byte[256];
	    if (hasBackground) {
		    a[0] = (byte)255;
	    } else {
	    	a[0] = (byte)0;
	    }
	    for (int i = 1; i < 256; i++) {
	    	a[i] = (byte)255;
	    }
	    IndexColorModel defaultColorModel = new IndexColorModel(8, 256, r, g, b, a);
	    return defaultColorModel;
	}

	public static IndexColorModel getAnimatedDefaultColorModel(ColorCycleOffset cco, boolean hasBackground) {
	    byte[] r = getDefaultColorModelRedComponents();
	    byte[] g = getDefaultColorModelGreenComponents();
	    byte[] b = getDefaultColorModelBlueComponents();
	    byte[] a = new byte[256];
	    if (hasBackground) {
		    a[0] = (byte)255;
	    } else {
	    	a[0] = (byte)0;
	    }
	    for (int i = 1; i < 256; i++) {
	    	a[i] = (byte)255;
	    }

	    int slimeIndex = 229;
	    int slimeSize = 4;
	    byte slimeRed[] = Arrays.copyOfRange(r, slimeIndex, slimeIndex + slimeSize);
	    byte slimeGreen[] = Arrays.copyOfRange(g, slimeIndex, slimeIndex + slimeSize);
	    byte slimeBlue[] = Arrays.copyOfRange(b, slimeIndex, slimeIndex + slimeSize);
	    for (int i = 0; i < slimeSize; i++) {
	    	r[slimeIndex + i] = slimeRed[(i + cco.slimeOffset) % slimeSize];
	    	g[slimeIndex + i] = slimeGreen[(i + cco.slimeOffset) % slimeSize];
	    	b[slimeIndex + i] = slimeBlue[(i + cco.slimeOffset) % slimeSize];
	    }

	    int shorelineIndex = 248;
	    int shorelineSize = 6;
	    byte shorelineRed[] = Arrays.copyOfRange(r, shorelineIndex, shorelineIndex + shorelineSize);
	    byte shorelineGreen[] = Arrays.copyOfRange(g, shorelineIndex, shorelineIndex + shorelineSize);
	    byte shorelineBlue[] = Arrays.copyOfRange(b, shorelineIndex, shorelineIndex + shorelineSize);
	    for (int i = 0; i < shorelineSize; i++) {
	    	r[shorelineIndex + i] = shorelineRed[(i + cco.shorelineOffset) % shorelineSize];
	    	g[shorelineIndex + i] = shorelineGreen[(i + cco.shorelineOffset) % shorelineSize];
	    	b[shorelineIndex + i] = shorelineBlue[(i + cco.shorelineOffset) % shorelineSize];
	    }

	    int slowfireIndex = 238;
	    int slowfireSize =  5;
	    byte slowfireRed[] = Arrays.copyOfRange(r, slowfireIndex, slowfireIndex + slowfireSize);
	    byte slowfireGreen[] = Arrays.copyOfRange(g, slowfireIndex, slowfireIndex + slowfireSize);
	    byte slowfireBlue[] = Arrays.copyOfRange(b, slowfireIndex, slowfireIndex + slowfireSize);
	    for (int i = 0; i < slowfireSize; i++) {
	    	r[slowfireIndex + i] = slowfireRed[(i + cco.slowfireOffset) % slowfireSize];
	    	g[slowfireIndex + i] = slowfireGreen[(i + cco.slowfireOffset) % slowfireSize];
	    	b[slowfireIndex + i] = slowfireBlue[(i + cco.slowfireOffset) % slowfireSize];
	    }

	    int fastfireIndex = 243;
	    int fastfireSize = 5;
	    byte fastfireRed[] = Arrays.copyOfRange(r, fastfireIndex, fastfireIndex + fastfireSize);
	    byte fastfireGreen[] = Arrays.copyOfRange(g, fastfireIndex, fastfireIndex + fastfireSize);
	    byte fastfireBlue[] = Arrays.copyOfRange(b, fastfireIndex, fastfireIndex + fastfireSize);
	    for (int i = 0; i < fastfireSize; i++) {
	    	r[fastfireIndex + i] = fastfireRed[(i + cco.fastfireOffset) % fastfireSize];
	    	g[fastfireIndex + i] = fastfireGreen[(i + cco.fastfireOffset) % fastfireSize];
	    	b[fastfireIndex + i] = fastfireBlue[(i + cco.fastfireOffset) % fastfireSize];
	    }

	    int monitorsIndex = 233;
	    int monitorsSize = 5;
	    byte monitorsRed[] = Arrays.copyOfRange(r, monitorsIndex, monitorsIndex + monitorsSize);
	    byte monitorsGreen[] = Arrays.copyOfRange(g, monitorsIndex, monitorsIndex + monitorsSize);
	    byte monitorsBlue[] = Arrays.copyOfRange(b, monitorsIndex, monitorsIndex + monitorsSize);
	    for (int i = 0; i < monitorsSize; i++) {
	    	r[monitorsIndex + i] = monitorsRed[(i + cco.monitorsOffset) % monitorsSize];
	    	g[monitorsIndex + i] = monitorsGreen[(i + cco.monitorsOffset) % monitorsSize];
	    	b[monitorsIndex + i] = monitorsBlue[(i + cco.monitorsOffset) % monitorsSize];
	    }


	    IndexColorModel defaultColorModel = new IndexColorModel(8, 256, r, g, b, a);
	    return defaultColorModel;

	}

	private static byte[] getDefaultColorModelRedComponents() {
		byte[] r =  new byte[256];
	    r[0] = (byte)0;	    	r[1] = (byte)236;	    r[2] = (byte)220;	    r[3] = (byte)204;
	    r[4] = (byte)188;	    r[5] = (byte)176;	    r[6] = (byte)160;	    r[7] = (byte)144;
	    r[8] = (byte)128;	    r[9] = (byte)116;	    r[10] = (byte)100;	    r[11] = (byte)84;
	    r[12] = (byte)72;	    r[13] = (byte)56;	    r[14] = (byte)40;	    r[15] = (byte)32;
	    r[16] = (byte)252;	    r[17] = (byte)236;	    r[18] = (byte)220;	    r[19] = (byte)208;
	    r[20] = (byte)192;	    r[21] = (byte)176;	    r[22] = (byte)164;	    r[23] = (byte)148;
	    r[24] = (byte)132;	    r[25] = (byte)120;	    r[26] = (byte)104;	    r[27] = (byte)88;
	    r[28] = (byte)76;	    r[29] = (byte)60;	    r[30] = (byte)44;	    r[31] = (byte)32;
	    r[32] = (byte)236;	    r[33] = (byte)216;	    r[34] = (byte)196;	    r[35] = (byte)176;
	    r[36] = (byte)160;	    r[37] = (byte)144;	    r[38] = (byte)128;	    r[39] = (byte)112;
	    r[40] = (byte)96;	    r[41] = (byte)84;	    r[42] = (byte)68;	    r[43] = (byte)56;
	    r[44] = (byte)44;	    r[45] = (byte)36;	    r[46] = (byte)24;	    r[47] = (byte)16;
	    r[48] = (byte)252;	    r[49] = (byte)196;	    r[50] = (byte)104;	    r[51] = (byte)76;
	    r[52] = (byte)56;	    r[53] = (byte)40;	    r[54] = (byte)36;	    r[55] = (byte)28;
	    r[56] = (byte)252;	    r[57] = (byte)252;	    r[58] = (byte)228;	    r[59] = (byte)204;
	    r[60] = (byte)184;	    r[61] = (byte)164;	    r[62] = (byte)144;	    r[63] = (byte)124;
	    r[64] = (byte)108;	    r[65] = (byte)88;	    r[66] = (byte)72;	    r[67] = (byte)52;
	    r[68] = (byte)32;	    r[69] = (byte)216;	    r[70] = (byte)180;	    r[71] = (byte)152;
	    r[72] = (byte)120;	    r[73] = (byte)92;	    r[74] = (byte)64;	    r[75] = (byte)40;
	    r[76] = (byte)112;	    r[77] = (byte)84;	    r[78] = (byte)56;	    r[79] = (byte)104;
	    r[80] = (byte)112;	    r[81] = (byte)112;	    r[82] = (byte)96;	    r[83] = (byte)76;
	    r[84] = (byte)56;	    r[85] = (byte)156;	    r[86] = (byte)120;	    r[87] = (byte)88;
	    r[88] = (byte)64;	    r[89] = (byte)56;	    r[90] = (byte)48;	    r[91] = (byte)40;
	    r[92] = (byte)32;	    r[93] = (byte)28;	    r[94] = (byte)20;	    r[95] = (byte)16;
	    r[96] = (byte)24;	    r[97] = (byte)16;	    r[98] = (byte)8;	    r[99] = (byte)4;
	    r[100] = (byte)4;	    r[101] = (byte)140;	    r[102] = (byte)120;	    r[103] = (byte)100;
	    r[104] = (byte)80;	    r[105] = (byte)64;	    r[106] = (byte)48;	    r[107] = (byte)44;
	    r[108] = (byte)40;	    r[109] = (byte)32;	    r[110] = (byte)28;	    r[111] = (byte)24;
	    r[112] = (byte)156;	    r[113] = (byte)56;    	r[114] = (byte)80;	    r[115] = (byte)88;
	    r[116] = (byte)56;	    r[117] = (byte)188;	    r[118] = (byte)172;	    r[119] = (byte)160;
	    r[120] = (byte)148;	    r[121] = (byte)136;	    r[122] = (byte)124;	    r[123] = (byte)112;
	    r[124] = (byte)100;	    r[125] = (byte)88;	    r[126] = (byte)252;	    r[127] = (byte)252;
	    r[128] = (byte)252;	    r[129] = (byte)252;	    r[130] = (byte)252;	    r[131] = (byte)252;
	    r[132] = (byte)252;	    r[133] = (byte)252;	    r[134] = (byte)224;	    r[135] = (byte)196;
	    r[136] = (byte)168;	    r[137] = (byte)144;	    r[138] = (byte)116;	    r[139] = (byte)88;
	    r[140] = (byte)64;	    r[141] = (byte)252;	    r[142] = (byte)252;	    r[143] = (byte)252;
	    r[144] = (byte)252;	    r[145] = (byte)252;	    r[146] = (byte)252;	    r[147] = (byte)252;
	    r[148] = (byte)252;    	r[149] = (byte)220;	    r[150] = (byte)192;	    r[151] = (byte)164;
	    r[152] = (byte)132;    	r[153] = (byte)104;	    r[154] = (byte)76;	    r[155] = (byte)48;
	    r[156] = (byte)248;	    r[157] = (byte)216;	    r[158] = (byte)200;	    r[159] = (byte)188;
	    r[160] = (byte)172;    	r[161] = (byte)156;     r[162] = (byte)140;	    r[163] = (byte)124;
	    r[164] = (byte)112;     r[165] = (byte)96;	    r[166] = (byte)80;	    r[167] = (byte)64;
	    r[168] = (byte)52;	    r[169] = (byte)252;	    r[170] = (byte)232;	    r[171] = (byte)212;
	    r[172] = (byte)196;	    r[173] = (byte)176;	    r[174] = (byte)160;	    r[175] = (byte)144;
	    r[176] = (byte)132;	    r[177] = (byte)120;	    r[178] = (byte)108;	    r[179] = (byte)92;
	    r[180] = (byte)72;	    r[181] = (byte)60;	    r[182] = (byte)252;	    r[183] = (byte)248;
	    r[184] = (byte)244;	    r[185] = (byte)240;	    r[186] = (byte)240;	    r[187] = (byte)240;
	    r[188] = (byte)216;	    r[189] = (byte)192;	    r[190] = (byte)168;	    r[191] = (byte)144;
	    r[192] = (byte)120;	    r[193] = (byte)96;	    r[194] = (byte)72;	    r[195] = (byte)56;
	    r[196] = (byte)100;	    r[197] = (byte)20;	    r[198] = (byte)0;	    r[199] = (byte)80;
	    r[200] = (byte)0;	    r[201] = (byte)140;	    r[202] = (byte)28;	    r[203] = (byte)104;
	    r[204] = (byte)48;	    r[205] = (byte)140;	    r[206] = (byte)72;	    r[207] = (byte)12;
	    r[208] = (byte)60;	    r[209] = (byte)108;	    r[210] = (byte)120;	    r[211] = (byte)136;
	    r[212] = (byte)148;	    r[213] = (byte)88;	    r[214] = (byte)96;	    r[215] = (byte)60;
	    r[216] = (byte)56;	    r[217] = (byte)52;	    r[218] = (byte)48;	    r[219] = (byte)40;
	    r[220] = (byte)252;	    r[221] = (byte)240;	    r[222] = (byte)208;	    r[223] = (byte)152;
	    r[224] = (byte)104;	    r[225] = (byte)80;	    r[226] = (byte)52;	    r[227] = (byte)24;
	    r[228] = (byte)0;	    r[229] = (byte)0;	    r[230] = (byte)11;	    r[231] = (byte)27;
	    r[232] = (byte)43;	    r[233] = (byte)107;	    r[234] = (byte)99;	    r[235] = (byte)87;
	    r[236] = (byte)0;	    r[237] = (byte)107;	    r[238] = (byte)255;	    r[239] = (byte)215;
	    r[240] = (byte)147;	    r[241] = (byte)255;	    r[242] = (byte)255;	    r[243] = (byte)71;
	    r[244] = (byte)123;	    r[245] = (byte)179;	    r[246] = (byte)123;	    r[247] = (byte)71;
	    r[248] = (byte)83;	    r[249] = (byte)75;	    r[250] = (byte)67;	    r[251] = (byte)63;
	    r[252] = (byte)55;	    r[253] = (byte)51;	    r[254] = (byte)252;	    r[255] = (byte)255;
	    return r;
	}

	private static byte[] getDefaultColorModelGreenComponents() {
		byte[] g =  new byte[256];
		g[0] = (byte)0;			g[1] = (byte)236;		g[2] = (byte)220;		g[3] = (byte)204;
		g[4] = (byte)188;		g[5] = (byte)176;		g[6] = (byte)160;		g[7] = (byte)144;
		g[8] = (byte)128;		g[9] = (byte)116;		g[10] = (byte)100;		g[11] = (byte)84;
		g[12] = (byte)72;		g[13] = (byte)56;		g[14] = (byte)40;		g[15] = (byte)32;
		g[16] = (byte)236;		g[17] = (byte)216;		g[18] = (byte)196;		g[19] = (byte)176;
		g[20] = (byte)160;		g[21] = (byte)144;		g[22] = (byte)128;		g[23] = (byte)112;
		g[24] = (byte)96;		g[25] = (byte)84;		g[26] = (byte)68;		g[27] = (byte)56;
		g[28] = (byte)44;		g[29] = (byte)36;		g[30] = (byte)24;		g[31] = (byte)16;
		g[32] = (byte)236;		g[33] = (byte)216;		g[34] = (byte)196;		g[35] = (byte)176;
		g[36] = (byte)160;		g[37] = (byte)144;		g[38] = (byte)128;		g[39] = (byte)112;
		g[40] = (byte)96;		g[41] = (byte)84;		g[42] = (byte)68;		g[43] = (byte)56;
		g[44] = (byte)44;		g[45] = (byte)36;		g[46] = (byte)24;		g[47] = (byte)16;
		g[48] = (byte)176;		g[49] = (byte)96;		g[50] = (byte)36;		g[51] = (byte)20;
		g[52] = (byte)12;		g[53] = (byte)16;		g[54] = (byte)4;		g[55] = (byte)12;
		g[56] = (byte)252;		g[57] = (byte)252;		g[58] = (byte)216;		g[59] = (byte)184;
		g[60] = (byte)156;		g[61] = (byte)136;		g[62] = (byte)120;		g[63] = (byte)104;
		g[64] = (byte)88;		g[65] = (byte)72;		g[66] = (byte)56;		g[67] = (byte)40;
		g[68] = (byte)24;		g[69] = (byte)252;		g[70] = (byte)216;		g[71] = (byte)184;
		g[72] = (byte)152;		g[73] = (byte)120;		g[74] = (byte)88;		g[75] = (byte)56;
		g[76] = (byte)96;		g[77] = (byte)72;		g[78] = (byte)48;		g[79] = (byte)120;
		g[80] = (byte)120;		g[81] = (byte)104;		g[82] = (byte)96;		g[83] = (byte)68;
		g[84] = (byte)48;		g[85] = (byte)172;		g[86] = (byte)148;		g[87] = (byte)124;
		g[88] = (byte)104;		g[89] = (byte)88;		g[90] = (byte)76;		g[91] = (byte)68;
		g[92] = (byte)60;		g[93] = (byte)48;		g[94] = (byte)40;		g[95] = (byte)32;
		g[96] = (byte)48;		g[97] = (byte)36;		g[98] = (byte)28;		g[99] = (byte)20;
		g[100] = (byte)12;		g[101] = (byte)156;		g[102] = (byte)148;		g[103] = (byte)136;
		g[104] = (byte)124;		g[105] = (byte)108;		g[106] = (byte)88;		g[107] = (byte)76;
		g[108] = (byte)68;		g[109] = (byte)56;		g[110] = (byte)48;		g[111] = (byte)40;
		g[112] = (byte)164;		g[113] = (byte)72;		g[114] = (byte)88;		g[115] = (byte)104;
		g[116] = (byte)64;		g[117] = (byte)188;		g[118] = (byte)164;		g[119] = (byte)144;
		g[120] = (byte)124;		g[121] = (byte)104;		g[122] = (byte)88;		g[123] = (byte)72;
		g[124] = (byte)60;		g[125] = (byte)48;		g[126] = (byte)204;		g[127] = (byte)176;
		g[128] = (byte)152;		g[129] = (byte)124;		g[130] = (byte)100;		g[131] = (byte)72;
		g[132] = (byte)48;		g[133] = (byte)0;		g[134] = (byte)0;		g[135] = (byte)0;
		g[136] = (byte)0;		g[137] = (byte)0;		g[138] = (byte)0;		g[139] = (byte)0;
		g[140] = (byte)0;		g[141] = (byte)224;		g[142] = (byte)196;		g[143] = (byte)184;
		g[144] = (byte)172;		g[145] = (byte)156;		g[146] = (byte)148;		g[147] = (byte)136;
		g[148] = (byte)124;		g[149] = (byte)108;		g[150] = (byte)96;		g[151] = (byte)80;
		g[152] = (byte)68;		g[153] = (byte)52;		g[154] = (byte)36;		g[155] = (byte)24;
		g[156] = (byte)212;		g[157] = (byte)176;		g[158] = (byte)160;		g[159] = (byte)144;
		g[160] = (byte)128;		g[161] = (byte)116;		g[162] = (byte)100;		g[163] = (byte)88;
		g[164] = (byte)76;		g[165] = (byte)64;		g[166] = (byte)52;		g[167] = (byte)40;
		g[168] = (byte)32;		g[169] = (byte)228;		g[170] = (byte)200;		g[171] = (byte)172;
		g[172] = (byte)144;		g[173] = (byte)116;		g[174] = (byte)92;		g[175] = (byte)76;
		g[176] = (byte)60;		g[177] = (byte)44;		g[178] = (byte)32;		g[179] = (byte)20;
		g[180] = (byte)12;		g[181] = (byte)4;		g[182] = (byte)232;		g[183] = (byte)212;
		g[184] = (byte)192;		g[185] = (byte)176;		g[186] = (byte)160;		g[187] = (byte)148;
		g[188] = (byte)128;		g[189] = (byte)112;		g[190] = (byte)96;		g[191] = (byte)80;
		g[192] = (byte)64;		g[193] = (byte)48;		g[194] = (byte)36;		g[195] = (byte)24;
		g[196] = (byte)228;		g[197] = (byte)152;		g[198] = (byte)164;		g[199] = (byte)80;
		g[200] = (byte)108;		g[201] = (byte)140;		g[202] = (byte)28;		g[203] = (byte)80;
		g[204] = (byte)40;		g[205] = (byte)112;		g[206] = (byte)56;		g[207] = (byte)12;
		g[208] = (byte)60;		g[209] = (byte)116;		g[210] = (byte)132;		g[211] = (byte)148;
		g[212] = (byte)164;		g[213] = (byte)104;		g[214] = (byte)112;		g[215] = (byte)248;
		g[216] = (byte)212;		g[217] = (byte)180;		g[218] = (byte)148;		g[219] = (byte)116;
		g[220] = (byte)252;		g[221] = (byte)236;		g[222] = (byte)184;		g[223] = (byte)124;
		g[224] = (byte)88;		g[225] = (byte)64;		g[226] = (byte)40;		g[227] = (byte)16;
		g[228] = (byte)0;		g[229] = (byte)108;		g[230] = (byte)115;		g[231] = (byte)123;
		g[232] = (byte)131;		g[233] = (byte)107;		g[234] = (byte)103;		g[235] = (byte)107;
		g[236] = (byte)147;		g[237] = (byte)187;		g[238] = (byte)0;		g[239] = (byte)0;
		g[240] = (byte)43;		g[241] = (byte)119;		g[242] = (byte)59;		g[243] = (byte)0;
		g[244] = (byte)0;		g[245] = (byte)0;		g[246] = (byte)0;		g[247] = (byte)0;
		g[248] = (byte)63;		g[249] = (byte)59;		g[250] = (byte)55;		g[251] = (byte)51;
		g[252] = (byte)47;		g[253] = (byte)43;		g[254] = (byte)0;		g[255] = (byte)255;
		return g;
	}

	private static byte[] getDefaultColorModelBlueComponents() {
		byte[] b =  new byte[256];
		b[0] = (byte)255;		b[1] = (byte)236;		b[2] = (byte)220;		b[3] = (byte)204;
		b[4] = (byte)188;		b[5] = (byte)176;		b[6] = (byte)160;		b[7] = (byte)144;
		b[8] = (byte)128;		b[9] = (byte)116;		b[10] = (byte)100;		b[11] = (byte)84;
		b[12] = (byte)72;		b[13] = (byte)56;		b[14] = (byte)40;		b[15] = (byte)32;
		b[16] = (byte)236;		b[17] = (byte)216;		b[18] = (byte)196;		b[19] = (byte)176;
		b[20] = (byte)160;		b[21] = (byte)144;		b[22] = (byte)128;		b[23] = (byte)112;
		b[24] = (byte)96;		b[25] = (byte)84;		b[26] = (byte)68;		b[27] = (byte)56;
		b[28] = (byte)44;		b[29] = (byte)36;		b[30] = (byte)24;		b[31] = (byte)16;
		b[32] = (byte)252;		b[33] = (byte)236;		b[34] = (byte)220;		b[35] = (byte)208;
		b[36] = (byte)192;		b[37] = (byte)176;		b[38] = (byte)164;		b[39] = (byte)148;
		b[40] = (byte)132;		b[41] = (byte)120;		b[42] = (byte)104;		b[43] = (byte)88;
		b[44] = (byte)76;		b[45] = (byte)60;		b[46] = (byte)44;		b[47] = (byte)32;
		b[48] = (byte)240;		b[49] = (byte)168;		b[50] = (byte)96;		b[51] = (byte)72;
		b[52] = (byte)52;		b[53] = (byte)36;		b[54] = (byte)36;		b[55] = (byte)24;
		b[56] = (byte)200;		b[57] = (byte)124;		b[58] = (byte)12;		b[59] = (byte)28;
		b[60] = (byte)40;		b[61] = (byte)48;		b[62] = (byte)36;		b[63] = (byte)24;
		b[64] = (byte)16;		b[65] = (byte)8;		b[66] = (byte)4;		b[67] = (byte)0;
		b[68] = (byte)0;		b[69] = (byte)156;		b[70] = (byte)132;		b[71] = (byte)112;
		b[72] = (byte)92;		b[73] = (byte)72;		b[74] = (byte)52;		b[75] = (byte)32;
		b[76] = (byte)80;		b[77] = (byte)52;		b[78] = (byte)32;		b[79] = (byte)80;
		b[80] = (byte)32;		b[81] = (byte)40;		b[82] = (byte)36;		b[83] = (byte)36;
		b[84] = (byte)32;		b[85] = (byte)156;		b[86] = (byte)120;		b[87] = (byte)88;
		b[88] = (byte)64;		b[89] = (byte)88;		b[90] = (byte)72;		b[91] = (byte)60;
		b[92] = (byte)44;		b[93] = (byte)36;		b[94] = (byte)24;		b[95] = (byte)16;
		b[96] = (byte)24;		b[97] = (byte)12;		b[98] = (byte)4;		b[99] = (byte)0;
		b[100] = (byte)0;		b[101] = (byte)156;		b[102] = (byte)152;		b[103] = (byte)148;
		b[104] = (byte)144;		b[105] = (byte)140;		b[106] = (byte)140;		b[107] = (byte)124;
		b[108] = (byte)108;		b[109] = (byte)92;		b[110] = (byte)76;		b[111] = (byte)64;
		b[112] = (byte)164;		b[113] = (byte)104;		b[114] = (byte)88;		b[115] = (byte)132;
		b[116] = (byte)80;		b[117] = (byte)188;		b[118] = (byte)152;		b[119] = (byte)124;
		b[120] = (byte)96;		b[121] = (byte)76;		b[122] = (byte)52;		b[123] = (byte)36;
		b[124] = (byte)20;		b[125] = (byte)8;		b[126] = (byte)204;		b[127] = (byte)176;
		b[128] = (byte)152;		b[129] = (byte)124;		b[130] = (byte)100;		b[131] = (byte)72;
		b[132] = (byte)48;		b[133] = (byte)0;		b[134] = (byte)0;		b[135] = (byte)0;
		b[136] = (byte)0;		b[137] = (byte)0;		b[138] = (byte)0;		b[139] = (byte)0;
		b[140] = (byte)0;		b[141] = (byte)200;		b[142] = (byte)148;		b[143] = (byte)120;
		b[144] = (byte)96;		b[145] = (byte)72;		b[146] = (byte)44;		b[147] = (byte)20;
		b[148] = (byte)0;		b[149] = (byte)0;		b[150] = (byte)0;		b[151] = (byte)0;
		b[152] = (byte)0;		b[153] = (byte)0;		b[154] = (byte)0;		b[155] = (byte)0;
		b[156] = (byte)164;		b[157] = (byte)120;		b[158] = (byte)100;		b[159] = (byte)84;
		b[160] = (byte)68;		b[161] = (byte)52;		b[162] = (byte)40;		b[163] = (byte)28;
		b[164] = (byte)20;		b[165] = (byte)8;		b[166] = (byte)4;		b[167] = (byte)0;
		b[168] = (byte)0;		b[169] = (byte)184;		b[170] = (byte)152;		b[171] = (byte)124;
		b[172] = (byte)100;		b[173] = (byte)76;		b[174] = (byte)56;		b[175] = (byte)44;
		b[176] = (byte)32;		b[177] = (byte)24;		b[178] = (byte)16;		b[179] = (byte)8;
		b[180] = (byte)4;		b[181] = (byte)0;		b[182] = (byte)220;		b[183] = (byte)188;
		b[184] = (byte)160;		b[185] = (byte)132;		b[186] = (byte)108;		b[187] = (byte)92;
		b[188] = (byte)84;		b[189] = (byte)72;		b[190] = (byte)64;		b[191] = (byte)56;
		b[192] = (byte)48;		b[193] = (byte)36;		b[194] = (byte)28;		b[195] = (byte)20;
		b[196] = (byte)100;		b[197] = (byte)20;		b[198] = (byte)0;		b[199] = (byte)72;
		b[200] = (byte)0;		b[201] = (byte)132;		b[202] = (byte)28;		b[203] = (byte)56;
		b[204] = (byte)32;		b[205] = (byte)96;		b[206] = (byte)40;		b[207] = (byte)12;
		b[208] = (byte)60;		b[209] = (byte)108;		b[210] = (byte)120;		b[211] = (byte)136;
		b[212] = (byte)148;		b[213] = (byte)96;		b[214] = (byte)104;		b[215] = (byte)0;
		b[216] = (byte)8;		b[217] = (byte)16;		b[218] = (byte)20;		b[219] = (byte)24;
		b[220] = (byte)252;		b[221] = (byte)208;		b[222] = (byte)136;		b[223] = (byte)80;
		b[224] = (byte)60;		b[225] = (byte)36;		b[226] = (byte)28;		b[227] = (byte)12;
		b[228] = (byte)0;		b[229] = (byte)0;		b[230] = (byte)7;		b[231] = (byte)15;
		b[232] = (byte)27;		b[233] = (byte)111;		b[234] = (byte)127;		b[235] = (byte)143;
		b[236] = (byte)163;		b[237] = (byte)255;		b[238] = (byte)0;		b[239] = (byte)0;
		b[240] = (byte)11;		b[241] = (byte)0;		b[242] = (byte)0;		b[243] = (byte)0;
		b[244] = (byte)0;		b[245] = (byte)0;		b[246] = (byte)0;		b[247] = (byte)0;
		b[248] = (byte)43;		b[249] = (byte)43;		b[250] = (byte)39;		b[251] = (byte)39;
		b[252] = (byte)35;		b[253] = (byte)35;		b[254] = (byte)0;		b[255] = (byte)255;
		return b;
	}
}