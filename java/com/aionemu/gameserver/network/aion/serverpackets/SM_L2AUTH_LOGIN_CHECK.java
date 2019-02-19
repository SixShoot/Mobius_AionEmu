/*
 * This file is part of the Aion-Emu project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.commons.utils.PrintUtils;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author -Nemesiss-
 */
public class SM_L2AUTH_LOGIN_CHECK extends AionServerPacket
{
	
	/**
	 * True if client is authed.
	 */
	private final boolean ok;
	private final String accountName;
	private static byte[] data;
	
	static
	{
		data = PrintUtils.hex2bytes("000000000000000101010202020303030404040505050606060707070808080909090A0A0A0B0B0B0C0C0C0D0D0D0E0E0E0F0F0F1010101111111212121313131414141515151616161717171818181919191A1A1A1B1B1B1C1C1C1D1D1D1E1E1E1F1F1F2020202121212222222323232424242525252626262727272828282929292A2A2A2B2B2B2C2C2C2D2D2D2E2E2E2F2F2F303030313131323232333333343434353535363636000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000463737473838483939493A3A4A3B3B4B3C3C4C3D3D0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000101010202020303030404040505050606060707070808080909090A0A0A0B0B0B0C0C0C0D0D0D0E0E0E0F0F0F1010101111111212121313131414141515151616161717171818181919191A1A1A1B1B1B1C1C1C1D1D1D1E1E1E1F1F1F2020202121212222222323232424242525252626262727272828282929292A2A2A2B2B2B2C2C2C2D2D2D2E2E2E2F2F2F303030313131323232333333343434353535363636463737473838483939493A3A4A3B3B4B3C3C4C3D3D000000000000A30010ABD71701001062580801008046280701001004C1070100F0888F060100103527070100205C2707010010161D0D050030641D0D0300203D1D0D010050B21D0D0100408B1D0D010070001E0D010080271E0D0100904E1E0D0A00B09C1E0D010010B9FE1E0100C0C31E0D010090E4512A01009011832B0100107BEA2A0100104EB9290100B050E31100003018E2110000304513130000406C13130000B0AE7A120000C0D57A12000010CAE111000010F712130000201E13130000E0F21413000090607A120000A0877A120000400E7C12000060FEE4110000C0CAEA110000608DE21100009002E311000080DBE2110000E0C5E3110000E0DCF41100002079F5110000C077E3110000C08EF4110000002BF5110000D09EE3110000D0B5F41100001052F51100005066E211000070B4E2110000E036E61100007007EA11000030FAE6110000A00BE81100004021E7110000B032E81100000085E6110000D080E81100009055EA110000E06BF21100004056F311000080F2F3110000103BE41100007025E5110000902F14130000C0A41413000060BA13130000D0CB14130000509313130000802EEA110000A05614130000802700120000800814130000B07D14130000F0191513000050D7E4110000C059E811000050D0FA11000070ADF81100003089E4110000A022F9110000B049F9110000C070F9110000D097F91100009073E5110000A040F4110000000DFA110000205BFA1100001034FA11000060F7FA110000E0BEF9110000906CFB110000807EC423010000BAF211000000727B120000F003F51100006015F611000060E0E911000000F6E811000030A0F51100008045FB110000B0A3EA11000040C7F51100009019F41100002008F3110000A0B1F6110000701EFB110000B067F4110000908AF6110000F092F21100008063F611000050EEF51100006086F8110000403FE2110000E0237B120000F04A7B12000030E77B120000D0FC7A12000010997B12000020C07B1200003082FA11000040A9FA11000040B0E4110000D0F1EA11000000D8ED110000B0D8F611000080D4F8110000A029E3110000E0A7E811000070E113130000804CE5110000C0FFF6110000F0E5F91100000014E4110000F0ECE3110000306BE91100004092E9110000F03FEB1100008092A83501006051EC11000010E1F2110000C0E8E5110000E018EB110000909E8E060100A0C58E060100A0CCC423010090A5C4230100A0751E0D020010B8850C0200907F840C0500B0CD840C0300A0A6840C0100E042850C0100C0F4840C0100D01B850C0100F069850C01000091850C0A0020DF850C01009022661E01003006860C01006044A8350100106DC3230100");
	}
	
	/**
	 * Constructs new <tt>SM_L2AUTH_LOGIN_CHECK </tt> packet
	 * @param ok
	 * @param accountName
	 */
	public SM_L2AUTH_LOGIN_CHECK(boolean ok, String accountName)
	{
		this.ok = ok;
		this.accountName = accountName;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void writeImpl(AionConnection con)
	{
		writeD(ok ? 0x00 : 0x01);
		writeB(data);
		writeS(accountName);
	}
}
