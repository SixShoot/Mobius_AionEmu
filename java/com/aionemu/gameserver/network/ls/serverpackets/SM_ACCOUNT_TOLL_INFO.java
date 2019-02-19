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
package com.aionemu.gameserver.network.ls.serverpackets;

import com.aionemu.gameserver.network.ls.LoginServerConnection;
import com.aionemu.gameserver.network.ls.LsServerPacket;

/**
 * @author xTz
 */
public class SM_ACCOUNT_TOLL_INFO extends LsServerPacket
{
	private final long toll;
	private final long luna;
	
	private final String accountName;
	
	public SM_ACCOUNT_TOLL_INFO(long toll, long luna, String accountName)
	{
		super(0x09);
		this.accountName = accountName;
		this.toll = toll;
		this.luna = luna;
	}
	
	@Override
	protected void writeImpl(LoginServerConnection con)
	{
		writeQ(toll);
		writeQ(luna);
		writeS(accountName);
	}
}
