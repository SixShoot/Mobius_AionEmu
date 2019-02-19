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
package system.handlers.ai.instance.kamarBattlefield;

import java.util.concurrent.atomic.AtomicBoolean;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.knownlist.Visitor;

import system.handlers.ai.AggressiveNpcAI2;

/**
 * @author Rinzler (Encom)
 */
@AIName("kamar_general")
public class KamarGeneralAI2 extends AggressiveNpcAI2
{
	private final AtomicBoolean isAggred = new AtomicBoolean(false);
	
	@Override
	protected void handleAttack(Creature creature)
	{
		super.handleAttack(creature);
		if (isAggred.compareAndSet(false, true))
		{
			switch (getNpcId())
			{
				case 233321: // General Varga.
				case 233322: // General Varga.
				case 233323: // General Varga.
				{
					announceIDKamarDrakanGeneral();
					break;
				}
				case 233327: // Acting Commander Cripsin.
				case 233329: // Acting Commander Cripsin.
				{
					announceIDKamarLightGeneral();
					break;
				}
				case 233328: // Acting Commander Tepes.
				case 233330: // Acting Commander Tepes.
				{
					announceIDKamarDarkGeneral();
					break;
				}
			}
		}
	}
	
	private void announceIDKamarLightGeneral()
	{
		getPosition().getWorldMapInstance().doOnAllPlayers(new Visitor<Player>()
		{
			@Override
			public void visit(Player player)
			{
				if (player.isOnline())
				{
					// Acting Commander Crispin is under attack.
					PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_IDKamar_LightGeneral_Hit);
				}
			}
		});
	}
	
	private void announceIDKamarDarkGeneral()
	{
		getPosition().getWorldMapInstance().doOnAllPlayers(new Visitor<Player>()
		{
			@Override
			public void visit(Player player)
			{
				if (player.isOnline())
				{
					// Acting Commander Tepes is under attack.
					PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_IDKamar_DarkGeneral_Hit);
				}
			}
		});
	}
	
	private void announceIDKamarDrakanGeneral()
	{
		getPosition().getWorldMapInstance().doOnAllPlayers(new Visitor<Player>()
		{
			@Override
			public void visit(Player player)
			{
				if (player.isOnline())
				{
					// Commander Varga is under attack.
					PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_IDKamar_DrakanGeneral_Hit);
				}
			}
		});
	}
}