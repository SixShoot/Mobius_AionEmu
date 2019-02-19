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
package system.handlers.instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.drop.DropItem;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.StaticDoor;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.drop.DropRegistrationService;
import com.aionemu.gameserver.services.player.PlayerReviveService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;

/**
 * @author Rinzler (Encom)
 */
@InstanceID(300700000)
public class TheHexwayInstance extends GeneralInstanceHandler
{
	private boolean isStartTimer1 = false;
	private boolean isStartTimer2 = false;
	private boolean isStartTimer3 = false;
	private boolean isStartTimer4 = false;
	private boolean isStartTimer5 = false;
	private boolean isStartTimer6 = false;
	private boolean isStartTimer7 = false;
	private boolean isStartTimer8 = false;
	private boolean isStartTimer9 = false;
	private boolean isStartTimer10 = false;
	private boolean isStartTimer11 = false;
	private boolean isStartTimer12 = false;
	private Map<Integer, StaticDoor> doors;
	private final List<Npc> theHexwayTreasureBox = new ArrayList<>();
	
	@Override
	public void onInstanceCreate(WorldMapInstance instance)
	{
		super.onInstanceCreate(instance);
		doors = instance.getDoors();
	}
	
	@Override
	public void onDropRegistered(Npc npc)
	{
		final Set<DropItem> dropItems = DropRegistrationService.getInstance().getCurrentDropMap().get(npc.getObjectId());
		final int npcId = npc.getNpcId();
		switch (npcId)
		{
			case 219609: // Captain Jarka.
			{
				switch (Rnd.get(1, 6))
				{
					case 1:
					{
						dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000130, 1)); // Balaur Warehouse No.1 Key.
						break;
					}
					case 2:
					{
						dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000131, 1)); // Balaur Warehouse No.2 Key.
						break;
					}
					case 3:
					{
						dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000132, 1)); // Balaur Warehouse No.3 Key.
						break;
					}
					case 4:
					{
						dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000133, 1)); // Balaur Warehouse No.4 Key.
						break;
					}
					case 5:
					{
						dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000134, 1)); // Balaur Warehouse No.5 Key.
						break;
					}
					case 6:
					{
						dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000135, 1)); // Balaur Warehouse No.6 Key.
						break;
					}
				}
				break;
			}
		}
	}
	
	@Override
	public void onDie(Npc npc)
	{
		switch (npc.getObjectTemplate().getTemplateId())
		{
			case 219617: // Balaur Barricade.
			{
				despawnNpc(npc);
				break;
			}
			case 219609: // Captain Jarka.
			{
				sendMsg("[Congratulation]: you finish <The Hexway>");
				if (!isStartTimer1)
				{
					isStartTimer1 = true;
					System.currentTimeMillis();
					instance.doOnAllPlayers(player1 ->
					{
						if (player1.isOnline())
						{
							PacketSendUtility.sendPacket(player1, new SM_QUEST_ACTION(0, 300));
						}
					});
					theHexwayTreasureBox.add((Npc) spawn(701664, 212.09007f, 741.0567f, 366.20367f, (byte) 10));
					theHexwayTreasureBox.add((Npc) spawn(701664, 239.12955f, 755.24274f, 365.43304f, (byte) 102));
					theHexwayTreasureBox.add((Npc) spawn(701664, 210.2166f, 697.1134f, 365.69165f, (byte) 99));
					theHexwayTreasureBox.add((Npc) spawn(701664, 188.26668f, 675.905f, 365.71332f, (byte) 7));
					theHexwayTreasureBox.add((Npc) spawn(701664, 182.42268f, 631.6112f, 366.24146f, (byte) 111));
					theHexwayTreasureBox.add((Npc) spawn(701664, 181.081f, 608.83777f, 365.52753f, (byte) 99));
					theHexwayTreasureBox.add((Npc) spawn(701664, 181.32057f, 561.24915f, 365.01053f, (byte) 113));
					theHexwayTreasureBox.add((Npc) spawn(701664, 181.63654f, 539.41473f, 365.01053f, (byte) 15));
					theHexwayTreasureBox.add((Npc) spawn(701664, 191.39304f, 495.07608f, 366.49414f, (byte) 65));
					theHexwayTreasureBox.add((Npc) spawn(701664, 197.46051f, 471.78418f, 365.32578f, (byte) 82));
					theHexwayTreasureBox.add((Npc) spawn(701664, 223.41487f, 409.03143f, 365.01053f, (byte) 26));
					theHexwayTreasureBox.add((Npc) spawn(701664, 213.39343f, 425.5012f, 366.57892f, (byte) 8));
					ThreadPoolManager.getInstance().schedule(() ->
					{
						StartTimer2();
						sendMsg(1400245);
						theHexwayTreasureBox.get(0).getController().onDelete();
					}, 300000);
				}
				break;
			}
		}
	}
	
	private void StartTimer2()
	{
		if (!isStartTimer2)
		{
			isStartTimer2 = true;
			System.currentTimeMillis();
			instance.doOnAllPlayers(player ->
			{
				if (player.isOnline())
				{
					PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 300));
				}
			});
			ThreadPoolManager.getInstance().schedule(() ->
			{
				StartTimer3();
				sendMsg(1400245);
				theHexwayTreasureBox.get(1).getController().onDelete();
			}, 300000);
		}
	}
	
	private void StartTimer3()
	{
		if (!isStartTimer3)
		{
			isStartTimer3 = true;
			System.currentTimeMillis();
			instance.doOnAllPlayers(player ->
			{
				if (player.isOnline())
				{
					PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 300));
				}
			});
			ThreadPoolManager.getInstance().schedule(() ->
			{
				StartTimer4();
				sendMsg(1400245);
				theHexwayTreasureBox.get(2).getController().onDelete();
			}, 300000);
		}
	}
	
	private void StartTimer4()
	{
		if (!isStartTimer4)
		{
			isStartTimer4 = true;
			System.currentTimeMillis();
			instance.doOnAllPlayers(player ->
			{
				if (player.isOnline())
				{
					PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 300));
				}
			});
			ThreadPoolManager.getInstance().schedule(() ->
			{
				StartTimer5();
				sendMsg(1400245);
				theHexwayTreasureBox.get(3).getController().onDelete();
			}, 300000);
		}
	}
	
	private void StartTimer5()
	{
		if (!isStartTimer5)
		{
			isStartTimer5 = true;
			System.currentTimeMillis();
			instance.doOnAllPlayers(player ->
			{
				if (player.isOnline())
				{
					PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 300));
				}
			});
			ThreadPoolManager.getInstance().schedule(() ->
			{
				StartTimer6();
				sendMsg(1400245);
				theHexwayTreasureBox.get(4).getController().onDelete();
			}, 300000);
		}
	}
	
	private void StartTimer6()
	{
		if (!isStartTimer6)
		{
			isStartTimer6 = true;
			System.currentTimeMillis();
			instance.doOnAllPlayers(player ->
			{
				if (player.isOnline())
				{
					PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 300));
				}
			});
			ThreadPoolManager.getInstance().schedule(() ->
			{
				StartTimer7();
				sendMsg(1400245);
				theHexwayTreasureBox.get(5).getController().onDelete();
			}, 300000);
		}
	}
	
	private void StartTimer7()
	{
		if (!isStartTimer7)
		{
			isStartTimer7 = true;
			System.currentTimeMillis();
			instance.doOnAllPlayers(player ->
			{
				if (player.isOnline())
				{
					PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 300));
				}
			});
			ThreadPoolManager.getInstance().schedule(() ->
			{
				StartTimer8();
				sendMsg(1400245);
				theHexwayTreasureBox.get(6).getController().onDelete();
			}, 300000);
		}
	}
	
	private void StartTimer8()
	{
		if (!isStartTimer8)
		{
			isStartTimer8 = true;
			System.currentTimeMillis();
			instance.doOnAllPlayers(player ->
			{
				if (player.isOnline())
				{
					PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 300));
				}
			});
			ThreadPoolManager.getInstance().schedule(() ->
			{
				StartTimer9();
				sendMsg(1400245);
				theHexwayTreasureBox.get(7).getController().onDelete();
			}, 300000);
		}
	}
	
	private void StartTimer9()
	{
		if (!isStartTimer9)
		{
			isStartTimer9 = true;
			System.currentTimeMillis();
			instance.doOnAllPlayers(player ->
			{
				if (player.isOnline())
				{
					PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 300));
				}
			});
			ThreadPoolManager.getInstance().schedule(() ->
			{
				StartTimer10();
				sendMsg(1400245);
				theHexwayTreasureBox.get(8).getController().onDelete();
			}, 300000);
		}
	}
	
	private void StartTimer10()
	{
		if (!isStartTimer10)
		{
			isStartTimer10 = true;
			System.currentTimeMillis();
			instance.doOnAllPlayers(player ->
			{
				if (player.isOnline())
				{
					PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 300));
				}
			});
			ThreadPoolManager.getInstance().schedule(() ->
			{
				StartTimer11();
				sendMsg(1400245);
				theHexwayTreasureBox.get(9).getController().onDelete();
			}, 300000);
		}
	}
	
	private void StartTimer11()
	{
		if (!isStartTimer11)
		{
			isStartTimer11 = true;
			System.currentTimeMillis();
			instance.doOnAllPlayers(player ->
			{
				if (player.isOnline())
				{
					PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 300));
				}
			});
			ThreadPoolManager.getInstance().schedule(() ->
			{
				StartTimer12();
				sendMsg(1400245);
				theHexwayTreasureBox.get(10).getController().onDelete();
			}, 300000);
		}
	}
	
	private void StartTimer12()
	{
		if (!isStartTimer12)
		{
			isStartTimer12 = true;
			System.currentTimeMillis();
			instance.doOnAllPlayers(player ->
			{
				if (player.isOnline())
				{
					PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 300));
				}
			});
			ThreadPoolManager.getInstance().schedule(() ->
			{
				sendMsg(1400244);
				sendMsg(1400245);
				theHexwayTreasureBox.get(11).getController().onDelete();
			}, 300000);
		}
	}
	
	private void sendMsg(String str)
	{
		instance.doOnAllPlayers(player -> PacketSendUtility.sendMessage(player, str));
	}
	
	private void despawnNpc(Npc npc)
	{
		if (npc != null)
		{
			npc.getController().onDelete();
		}
	}
	
	@Override
	public void onInstanceDestroy()
	{
		doors.clear();
	}
	
	@Override
	public boolean onReviveEvent(Player player)
	{
		player.getGameStats().updateStatsAndSpeedVisually();
		PlayerReviveService.revive(player, 100, 100, false, 0);
		PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_REBIRTH_MASSAGE_ME);
		PacketSendUtility.sendPacket(player, new SM_QUESTION_WINDOW(SM_QUESTION_WINDOW.STR_INSTANT_DUNGEON_RESURRECT, 0, 0));
		return TeleportService2.teleportTo(player, mapId, instanceId, 672.8951f, 606.1681f, 321.19f, (byte) 46);
	}
	
	@Override
	public boolean onDie(Player player, Creature lastAttacker)
	{
		PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);
		PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
		return true;
	}
}