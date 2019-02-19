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
package system.handlers.quest.gelkmaros;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author vlog
 */
public class _21460AShulacksStory extends QuestHandler
{
	
	private static final int questId = 21460;
	
	public _21460AShulacksStory()
	{
		super(questId);
	}
	
	@Override
	public void register()
	{
		qe.registerQuestNpc(799258).addOnQuestStart(questId);
		qe.registerQuestNpc(799258).addOnTalkEvent(questId);
		qe.registerQuestNpc(799502).addOnTalkEvent(questId);
		qe.registerQuestNpc(799276).addOnTalkEvent(questId);
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
			if (targetId == 799258)
			{ // Denskel
				if (dialog == QuestDialog.START_DIALOG)
				{
					return sendQuestDialog(env, 1011);
				}
				return sendQuestStartDialog(env);
			}
		}
		else if (qs.getStatus() == QuestStatus.START)
		{
			final int var = qs.getQuestVarById(0);
			switch (targetId)
			{
				case 799502:
				{ // Dorkin
					switch (dialog)
					{
						case START_DIALOG:
						{
							if (var == 0)
							{
								return sendQuestDialog(env, 1352);
							}
						}
						case STEP_TO_1:
						{
							return defaultCloseDialog(env, 0, 1, 182209520, 1, 0, 0); // 1
						}
					}
					break;
				}
				case 799276:
				{ // Chenkiki
					switch (dialog)
					{
						case START_DIALOG:
						{
							if (var == 1)
							{
								return sendQuestDialog(env, 2375);
							}
						}
						case SELECT_REWARD:
						{
							if (removeQuestItem(env, 182209520, 1))
							{
								changeQuestStep(env, 1, 1, true); // reward
							}
							return sendQuestDialog(env, 5);
						}
					}
				}
			}
		}
		else if (qs.getStatus() == QuestStatus.REWARD)
		{
			if (targetId == 799276)
			{ // Chenkiki
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}
