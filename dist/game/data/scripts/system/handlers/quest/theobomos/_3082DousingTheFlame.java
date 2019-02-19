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
package system.handlers.quest.theobomos;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;

/**
 * @author Cheatkiller
 */
public class _3082DousingTheFlame extends QuestHandler
{
	private static final int questId = 3082;
	
	public _3082DousingTheFlame()
	{
		super(questId);
	}
	
	@Override
	public void register()
	{
		qe.registerQuestNpc(798116).addOnQuestStart(questId);
		qe.registerQuestNpc(204030).addOnTalkEvent(questId);
		qe.registerQuestNpc(700416).addOnTalkEvent(questId);
		qe.registerQuestNpc(798155).addOnTalkEvent(questId);
	}
	
	@Override
	public boolean onDialogEvent(QuestEnv env)
	{
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		final QuestDialog dialog = env.getDialog();
		final int targetId = env.getTargetId();
		
		if ((qs == null) || (qs.getStatus() == QuestStatus.NONE))
		{
			if (targetId == 798116)
			{
				if (dialog == QuestDialog.START_DIALOG)
				{
					return sendQuestDialog(env, 4762);
				}
				return sendQuestStartDialog(env);
			}
		}
		else if (qs.getStatus() == QuestStatus.START)
		{
			if (targetId == 204030)
			{
				if (dialog == QuestDialog.START_DIALOG)
				{
					if (qs.getQuestVarById(0) == 0)
					{
						return sendQuestDialog(env, 1011);
					}
					else if (qs.getQuestVarById(0) == 1)
					{
						return sendQuestDialog(env, 1352);
					}
				}
				else if (dialog == QuestDialog.CHECK_COLLECTED_ITEMS)
				{
					return checkQuestItems(env, 0, 1, false, 10000, 10001, 182208060, 1);
				}
				else if (dialog == QuestDialog.STEP_TO_2)
				{
					return defaultCloseDialog(env, 1, 2);
				}
			}
			else if ((targetId == 700416) && (qs.getQuestVarById(0) == 2))
			{
				final Npc npc = (Npc) env.getVisibleObject();
				QuestService.addNewSpawn(npc.getWorldId(), npc.getInstanceId(), 700417, npc.getX(), npc.getY(), npc.getZ(), (byte) 0);
				removeQuestItem(env, 182208060, 1);
				return useQuestObject(env, 2, 2, true, false);
			}
		}
		else if (qs.getStatus() == QuestStatus.REWARD)
		{
			if (targetId == 798155)
			{
				switch (dialog)
				{
					case USE_OBJECT:
					{
						return sendQuestDialog(env, 10002);
					}
					default:
					{
						return sendQuestEndDialog(env);
					}
				}
			}
		}
		return false;
	}
}
