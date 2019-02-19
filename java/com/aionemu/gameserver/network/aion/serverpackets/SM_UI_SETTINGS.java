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

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author ATracer
 */
public class SM_UI_SETTINGS extends AionServerPacket
{
	private final byte[] data;
	private final int type;
	
	public SM_UI_SETTINGS(byte[] data, int type)
	{
		this.data = data;
		this.type = type;
	}
	
	@Override
	protected void writeImpl(AionConnection con)
	{
		writeC(type);
		writeH(0x1C00);
		writeB(data);
		if (0x1C00 > data.length)
		{
			writeB(new byte[0x1C00 - data.length]);
		}
	}
}