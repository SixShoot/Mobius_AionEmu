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

import org.apache.commons.lang.math.NumberUtils;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.IuService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class Iu extends AdminCommand
{
	private static final String COMMAND_START = "start";
	private static final String COMMAND_STOP = "stop";
	
	public Iu()
	{
		super("iu");
	}
	
	@Override
	public void execute(Player player, String... params)
	{
		if (params.length == 0)
		{
			showHelp(player);
			return;
		}
		if (COMMAND_STOP.equalsIgnoreCase(params[0]) || COMMAND_START.equalsIgnoreCase(params[0]))
		{
			handleStartStopConcert(player, params);
		}
	}
	
	protected void handleStartStopConcert(Player player, String... params)
	{
		if ((params.length != 2) || !NumberUtils.isDigits(params[1]))
		{
			showHelp(player);
			return;
		}
		final int iuId = NumberUtils.toInt(params[1]);
		if (!isValidConcertLocationId(player, iuId))
		{
			showHelp(player);
			return;
		}
		if (COMMAND_START.equalsIgnoreCase(params[0]))
		{
			if (IuService.getInstance().isConcertInProgress(iuId))
			{
				PacketSendUtility.sendMessage(player, "<Concert> " + iuId + " is already start");
			}
			else
			{
				PacketSendUtility.sendMessage(player, "<Concert> " + iuId + " started!");
				IuService.getInstance().startConcert(iuId);
			}
		}
		else if (COMMAND_STOP.equalsIgnoreCase(params[0]))
		{
			if (!IuService.getInstance().isConcertInProgress(iuId))
			{
				PacketSendUtility.sendMessage(player, "<Concert> " + iuId + " is not start!");
			}
			else
			{
				PacketSendUtility.sendMessage(player, "<Concert> " + iuId + " stopped!");
				IuService.getInstance().stopConcert(iuId);
			}
		}
	}
	
	protected boolean isValidConcertLocationId(Player player, int iuId)
	{
		if (!IuService.getInstance().getIuLocations().keySet().contains(iuId))
		{
			PacketSendUtility.sendMessage(player, "Id " + iuId + " is invalid");
			return false;
		}
		return true;
	}
	
	protected void showHelp(Player player)
	{
		PacketSendUtility.sendMessage(player, "AdminCommand //iu start|stop <Id>");
	}
}