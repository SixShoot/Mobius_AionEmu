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
package system.handlers.quest.sanctum;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Rolandas
 */
public class _3969SexiestManAlive extends QuestHandler
{
	
	private static final int questId = 3969;
	
	public _3969SexiestManAlive()
	{
		super(questId);
	}
	
	@Override
	public void register()
	{
		qe.registerQuestNpc(798390).addOnQuestStart(questId);// Palentine
		qe.registerQuestNpc(798391).addOnTalkEvent(questId);// Andu
		qe.registerQuestNpc(798390).addOnTalkEvent(questId);// Palentine
	}
	
	@Override
	public boolean onDialogEvent(QuestEnv env)
	{
		final Player player = env.getPlayer();
		int targetId = 0;
		
		final QuestState qs2 = player.getQuestStateList().getQuestState(3968);
		if ((qs2 == null) || (qs2.getStatus() != QuestStatus.COMPLETE))
		{
			return false;
		}
		
		if (env.getVisibleObject() instanceof Npc)
		{
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		}
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		
		if (targetId == 798390)// Palentine
		{
			if ((qs == null) || (qs.getStatus() == QuestStatus.NONE))
			{
				if (env.getDialog() == QuestDialog.USE_OBJECT)
				{
					return sendQuestDialog(env, 1011);
				}
				else if (env.getDialogId() == 1002)
				{
					if (giveQuestItem(env, 182206126, 1))
					{
						return sendQuestStartDialog(env);
					}
					return true;
				}
				else
				{
					return sendQuestStartDialog(env);
				}
			}
		}
		
		if (qs == null)
		{
			return false;
		}
		
		int var = qs.getQuestVarById(0);
		
		if (targetId == 798391)// Andu
		{
			if ((qs.getStatus() == QuestStatus.START) && (var == 0))
			{
				if (env.getDialog() == QuestDialog.USE_OBJECT)
				{
					return sendQuestDialog(env, 1352);
				}
				else if (env.getDialog() == QuestDialog.STEP_TO_1)
				{
					if (player.getInventory().getItemCountByItemId(182206126) > 0)
					{
						removeQuestItem(env, 182206126, 1);
						qs.setQuestVar(++var);
						qs.setStatus(QuestStatus.REWARD);
						updateQuestStatus(env);
						PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
					}
					return true;
				}
				else
				{
					return sendQuestStartDialog(env);
				}
			}
		}
		else if (targetId == 798390)// Palentine
		{
			if (qs.getStatus() == QuestStatus.REWARD)
			{
				if (env.getDialog() == QuestDialog.USE_OBJECT)
				{
					return sendQuestDialog(env, 2375);
				}
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}