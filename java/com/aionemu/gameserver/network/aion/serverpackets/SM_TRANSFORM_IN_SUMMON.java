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

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author xTz
 */
public class SM_TRANSFORM_IN_SUMMON extends AionServerPacket
{
	private final Player player;
	private final int summonObject;
	
	public SM_TRANSFORM_IN_SUMMON(Player player, Creature creature)
	{
		this(player, creature.getObjectId());
	}
	
	public SM_TRANSFORM_IN_SUMMON(Player player, int creatureObjectId)
	{
		this.player = player;
		summonObject = creatureObjectId;
	}
	
	@Override
	protected void writeImpl(AionConnection con)
	{
		writeD(summonObject);
		writeS(player.getName());
		writeD(player.getObjectId());
	}
}
