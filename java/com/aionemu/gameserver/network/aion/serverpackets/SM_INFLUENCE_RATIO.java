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

import com.aionemu.gameserver.model.siege.Influence;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.SiegeService;

/**
 * @author Nemiroff
 */
public class SM_INFLUENCE_RATIO extends AionServerPacket
{
	@Override
	protected void writeImpl(AionConnection con)
	{
		final Influence inf = Influence.getInstance();
		writeD(SiegeService.getInstance().getSecondsBeforeHourEnd());
		writeF(inf.getGlobalElyosInfluence());
		writeF(inf.getGlobalAsmodiansInfluence());
		writeF(inf.getGlobalBalaursInfluence());
		writeH(4);
		// ======[INGGISON]=======
		writeD(210050000);
		writeF(inf.getInggisonElyosInfluence());
		writeF(inf.getInggisonAsmodiansInfluence());
		writeF(inf.getInggisonBalaursInfluence());
		// ======[GELKMAROS]======
		writeD(220070000);
		writeF(inf.getGelkmarosElyosInfluence());
		writeF(inf.getGelkmarosAsmodiansInfluence());
		writeF(inf.getGelkmarosBalaursInfluence());
		// ========[ABYSS]========
		writeD(400010000);
		writeF(inf.getAbyssElyosInfluence());
		writeF(inf.getAbyssAsmodiansInfluence());
		writeF(inf.getAbyssBalaursInfluence());
		// ======[KALDOR]=========
		writeD(600090000);
		writeF(inf.getKaldorElyosInfluence());
		writeF(inf.getKaldorAsmodiansInfluence());
		writeF(inf.getKaldorBalaursInfluence());
	}
}