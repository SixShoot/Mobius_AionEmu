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
package system.handlers.ai.worlds.inggison;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.player.Player;

import system.handlers.ai.ActionItemNpcAI2;

/**
 * @author Rinzler (Encom)
 */
@AIName("acidcanister")
public class AcidCanisterAI2 extends ActionItemNpcAI2
{
	@Override
	protected void handleDialogStart(Player player)
	{
		super.handleDialogStart(player);
	}
	
	@Override
	protected void handleUseItemFinish(Player player)
	{
		spawn(216620, 1374.6608f, 1866.3596f, 317.34265f, (byte) 35); // Crimsonbark.
		AI2Actions.deleteOwner(this);
		AI2Actions.scheduleRespawn(this);
	}
}