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
package system.handlers.ai.instance.dragonLordRefuge;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.teleport.TeleportService2;

import system.handlers.ai.ActionItemNpcAI2;

/**
 * @author Ranastic (Encom)
 */
@AIName("internal_passage_out")
public class Internal_Passage_OutAI2 extends ActionItemNpcAI2
{
	@Override
	protected void handleUseItemFinish(Player player)
	{
		switch (getNpcId())
		{
			case 730633: // Internal Passage Out 1
			{
				switch (player.getWorldId())
				{
					case 300520000:
					{
						TeleportService2.teleportTo(player, 300520000, 530.0911f, 480.24875f, 417.40436f, (byte) 103, TeleportAnimation.BEAM_ANIMATION);
						break;
					}
					case 300630000:
					{
						TeleportService2.teleportTo(player, 300630000, 530.0911f, 480.24875f, 417.40436f, (byte) 103, TeleportAnimation.BEAM_ANIMATION);
						break;
					}
				}
				break;
			}
			case 730634: // Internal Passage Out 2
			{
				switch (player.getWorldId())
				{
					case 300520000:
					{
						TeleportService2.teleportTo(player, 300520000, 477.32306f, 549.42285f, 417.40436f, (byte) 43, TeleportAnimation.BEAM_ANIMATION);
						break;
					}
					case 300630000:
					{
						TeleportService2.teleportTo(player, 300630000, 477.32306f, 549.42285f, 417.40436f, (byte) 43, TeleportAnimation.BEAM_ANIMATION);
						break;
					}
				}
				break;
			}
			case 730635: // Internal Passage Out 3
			{
				switch (player.getWorldId())
				{
					case 300520000:
					{
						TeleportService2.teleportTo(player, 300520000, 530.8401f, 549.626f, 417.40436f, (byte) 17, TeleportAnimation.BEAM_ANIMATION);
						break;
					}
					case 300630000:
					{
						TeleportService2.teleportTo(player, 300630000, 530.8401f, 549.626f, 417.40436f, (byte) 17, TeleportAnimation.BEAM_ANIMATION);
						break;
					}
				}
				break;
			}
			case 730636: // Internal Passage Out 4
			{
				switch (player.getWorldId())
				{
					case 300520000:
					{
						TeleportService2.teleportTo(player, 300520000, 504.3792f, 520.4297f, 417.40436f, (byte) 61, TeleportAnimation.BEAM_ANIMATION);
						break;
					}
					case 300630000:
					{
						TeleportService2.teleportTo(player, 300630000, 504.3792f, 520.4297f, 417.40436f, (byte) 61, TeleportAnimation.BEAM_ANIMATION);
						break;
					}
				}
				break;
			}
		}
	}
}