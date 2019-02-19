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
package system.handlers.admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

/**
 * @author Imaginary
 */
public class AdminChat extends AdminCommand
{
	public AdminChat()
	{
		super("s");
	}
	
	@Override
	public void execute(Player admin, String... params)
	{
		if (!admin.isGM())
		{
			PacketSendUtility.sendMessage(admin, "Vous devez etre au moins rang " + AdminConfig.GM_LEVEL + " pour utiliser cette commande");
			return;
		}
		if (admin.isGagged())
		{
			PacketSendUtility.sendMessage(admin, "Vous avez ete reduit au silence ...");
			return;
		}
		
		final StringBuilder sbMessage = new StringBuilder("[Admin] " + admin.getName() + " : ");
		
		for (String p : params)
		{
			sbMessage.append(p + " ");
		}
		final String message = sbMessage.toString().trim();
		for (Player a : World.getInstance().getAllPlayers())
		{
			if (a.isGM())
			{
				PacketSendUtility.sendWhiteMessageOnCenter(a, message);
			}
		}
	}
	
	@Override
	public void onFail(Player admin, String message)
	{
		PacketSendUtility.sendMessage(admin, "Syntax: //s <message>");
	}
}