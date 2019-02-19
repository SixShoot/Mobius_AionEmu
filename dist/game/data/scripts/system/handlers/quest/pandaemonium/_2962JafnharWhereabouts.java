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
package system.handlers.quest.pandaemonium;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Cheatkiller
 */
public class _2962JafnharWhereabouts extends QuestHandler
{
	
	private static final int questId = 2962;
	
	public _2962JafnharWhereabouts()
	{
		super(questId);
	}
	
	int rewIdex;
	
	@Override
	public void register()
	{
		qe.registerQuestNpc(204253).addOnQuestStart(questId);
		qe.registerQuestNpc(204253).addOnTalkEvent(questId);
		qe.registerQuestNpc(278067).addOnTalkEvent(questId);
		qe.registerQuestNpc(278137).addOnTalkEvent(questId);
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
			if (targetId == 204253)
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
			if (targetId == 278067)
			{
				if (dialog == QuestDialog.START_DIALOG)
				{
					if (qs.getQuestVarById(0) == 0)
					{
						return sendQuestDialog(env, 1011);
					}
				}
				else if (dialog == QuestDialog.STEP_TO_1)
				{
					return defaultCloseDialog(env, 0, 1);
				}
			}
			else if (targetId == 278137)
			{
				if (dialog == QuestDialog.START_DIALOG)
				{
					if (qs.getQuestVarById(0) == 1)
					{
						return sendQuestDialog(env, 1352);
					}
				}
				else if (dialog == QuestDialog.STEP_TO_2)
				{
					return defaultCloseDialog(env, 1, 2);
				}
			}
			else if (targetId == 204253)
			{
				if (dialog == QuestDialog.START_DIALOG)
				{
					if (qs.getQuestVarById(0) == 2)
					{
						return sendQuestDialog(env, 1693);
					}
				}
				else if (dialog == QuestDialog.SELECT_ACTION_1694)
				{
					return sendQuestDialog(env, 1694);
				}
				else if (dialog == QuestDialog.SELECT_ACTION_1779)
				{
					return sendQuestDialog(env, 1779);
				}
				else if (dialog == QuestDialog.STEP_TO_3)
				{
					return defaultCloseDialog(env, 2, 2, true, true);
				}
				else if (dialog == QuestDialog.STEP_TO_4)
				{
					rewIdex = 1;
					return defaultCloseDialog(env, 2, 2, true, true);
				}
			}
		}
		else if (qs.getStatus() == QuestStatus.REWARD)
		{
			if (targetId == 204253)
			{
				if (dialog == QuestDialog.USE_OBJECT)
				{
					return sendQuestDialog(env, 5 + rewIdex);
				}
				return sendQuestEndDialog(env, rewIdex);
			}
		}
		return false;
	}
}
