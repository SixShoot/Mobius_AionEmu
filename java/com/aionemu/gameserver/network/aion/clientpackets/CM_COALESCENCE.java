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
package com.aionemu.gameserver.network.aion.clientpackets;

import java.util.ArrayList;
import java.util.List;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.item.CoalescenceService;

/**
 * @author Ranastic
 */
public class CM_COALESCENCE extends AionClientPacket
{
	private int ItemSize;
	private int upgradedItemObjectId;
	private int Items;
	private List<Integer> ItemsList = new ArrayList<>();
	
	public CM_COALESCENCE(int opcode, State state, State... restStates)
	{
		super(opcode, state, restStates);
	}
	
	@Override
	protected void readImpl()
	{
		ItemsList = new ArrayList<>();
		upgradedItemObjectId = readD();
		ItemSize = readH();
		for (int i = 0; i < ItemSize; i++)
		{
			Items = readD();
			ItemsList.add(Items);
		}
	}
	
	@Override
	protected void runImpl()
	{
		final Player player = getConnection().getActivePlayer();
		if (player != null)
		{
			CoalescenceService.startCoalescence(player, upgradedItemObjectId, ItemsList);
		}
		else
		{
		}
	}
}
