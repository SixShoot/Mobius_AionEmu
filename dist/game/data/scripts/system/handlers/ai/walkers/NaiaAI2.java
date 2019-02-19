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
package system.handlers.ai.walkers;

import java.util.List;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.handler.MoveEventHandler;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.templates.npcshout.NpcShout;
import com.aionemu.gameserver.model.templates.npcshout.ShoutEventType;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.utils.MathUtil;

import system.handlers.ai.GeneralNpcAI2;

/**
 * @author Rolandas
 */
@AIName("naia")
public class NaiaAI2 extends GeneralNpcAI2
{
	boolean saidCannon = false;
	boolean saidQydro = false;
	
	@Override
	protected void handleMoveArrived()
	{
		MoveEventHandler.onMoveArrived(this);
		Npc npc2 = null;
		final Npc cannon = getPosition().getWorldMapInstance().getNpc(203145);
		final Npc qydro = getPosition().getWorldMapInstance().getNpc(203125);
		final boolean isCannonNear = MathUtil.isIn3dRange(getOwner(), cannon, getOwner().getAggroRange());
		final boolean isQydroNear = MathUtil.isIn3dRange(getOwner(), qydro, getOwner().getAggroRange());
		int delay = 0;
		List<NpcShout> shouts = null;
		if (!saidCannon && isCannonNear)
		{
			saidCannon = true;
			npc2 = cannon;
			delay = 10;
			shouts = DataManager.NPC_SHOUT_DATA.getNpcShouts(getPosition().getMapId(), getNpcId(), ShoutEventType.WALK_WAYPOINT, "2", 0);
		}
		else if (saidCannon && !isCannonNear)
		{
			saidCannon = false;
		}
		if (!saidQydro && isQydroNear)
		{
			saidQydro = true;
			npc2 = qydro;
			shouts = DataManager.NPC_SHOUT_DATA.getNpcShouts(getPosition().getMapId(), getNpcId(), ShoutEventType.WALK_WAYPOINT, "1", 0);
		}
		else if (saidQydro && !isQydroNear)
		{
			saidQydro = false;
		}
		if (shouts != null)
		{
			NpcShoutsService.getInstance().shout(getOwner(), npc2, shouts, delay, false);
			shouts.clear();
		}
	}
}