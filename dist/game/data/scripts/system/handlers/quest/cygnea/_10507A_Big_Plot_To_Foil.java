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
package system.handlers.quest.cygnea;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Rinzler (Encom)
 */
public class _10507A_Big_Plot_To_Foil extends QuestHandler
{
	public static final int questId = 10507;
	
	public _10507A_Big_Plot_To_Foil()
	{
		super(questId);
	}
	
	@Override
	public void register()
	{
		final int[] npcs =
		{
			804711,
			804712,
			804713,
			804714,
			804715
		};
		for (int npc : npcs)
		{
			qe.registerQuestNpc(npc).addOnTalkEvent(questId);
		}
		qe.registerOnLevelUp(questId);
		qe.registerOnMovieEndQuest(993, questId);
		qe.registerOnEnterZoneMissionEnd(questId);
		qe.registerQuestNpc(236264).addOnKillEvent(questId); // Beritra Trailblazer Assassin.
		qe.registerQuestNpc(236265).addOnKillEvent(questId); // Beritra Trailblazer Spelltongue.
		qe.registerQuestNpc(702668).addOnKillEvent(questId); // Beritra Supplies Box.
		qe.registerOnEnterZone(ZoneName.get("LF5_SENSORYAREA_Q10507_210070000"), questId);
	}
	
	@Override
	public boolean onZoneMissionEndEvent(QuestEnv env)
	{
		final int[] cygneaQuests =
		{
			10500,
			10501,
			10502,
			10503,
			10504,
			10505,
			10506
		};
		return defaultOnZoneMissionEndEvent(env, cygneaQuests);
	}
	
	@Override
	public boolean onLvlUpEvent(QuestEnv env)
	{
		final int[] cygneaQuests =
		{
			10500,
			10501,
			10502,
			10503,
			10504,
			10505,
			10506
		};
		return defaultOnLvlUpEvent(env, cygneaQuests, true);
	}
	
	@Override
	public boolean onDialogEvent(QuestEnv env)
	{
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null)
		{
			return false;
		}
		final int var = qs.getQuestVarById(0);
		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc)
		{
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		}
		if (qs.getStatus() == QuestStatus.START)
		{
			if (targetId == 804711)
			{
				switch (env.getDialog())
				{
					case START_DIALOG:
					{
						if (var == 0)
						{
							return sendQuestDialog(env, 1011);
						}
						else if (var == 4)
						{
							return sendQuestDialog(env, 2375);
						}
					}
					case STEP_TO_1:
					{
						changeQuestStep(env, 0, 1, false);
						return closeDialogWindow(env);
					}
					case STEP_TO_5:
					{
						changeQuestStep(env, 4, 5, false);
						return closeDialogWindow(env);
					}
				}
			}
			if (targetId == 804712)
			{
				switch (env.getDialog())
				{
					case START_DIALOG:
					{
						if (var == 1)
						{
							return sendQuestDialog(env, 1352);
						}
					}
					case STEP_TO_2:
					{
						changeQuestStep(env, 1, 2, false);
						return closeDialogWindow(env);
					}
				}
			}
			if (targetId == 804713)
			{
				switch (env.getDialog())
				{
					case START_DIALOG:
					{
						if (var == 2)
						{
							return sendQuestDialog(env, 1693);
						}
					}
					case STEP_TO_3:
					{
						changeQuestStep(env, 2, 3, false);
						return closeDialogWindow(env);
					}
				}
			}
			if (targetId == 804714)
			{
				switch (env.getDialog())
				{
					case START_DIALOG:
					{
						if (var == 3)
						{
							return sendQuestDialog(env, 2034);
						}
					}
					case STEP_TO_4:
					{
						changeQuestStep(env, 3, 4, false);
						return closeDialogWindow(env);
					}
				}
			}
			if (targetId == 804715)
			{
				switch (env.getDialog())
				{
					case START_DIALOG:
					{
						if (var == 5)
						{
							return sendQuestDialog(env, 2716);
						}
					}
					case STEP_TO_6:
					{
						changeQuestStep(env, 5, 6, false);
						return closeDialogWindow(env);
					}
				}
			}
		}
		else if (qs.getStatus() == QuestStatus.REWARD)
		{
			if (targetId == 804711)
			{
				if (env.getDialog() == QuestDialog.START_DIALOG)
				{
					return sendQuestDialog(env, 10002);
				}
				else if (env.getDialog() == QuestDialog.SELECT_REWARD)
				{
					return sendQuestDialog(env, 5);
				}
				else
				{
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean onKillEvent(QuestEnv env)
	{
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if ((qs != null) && (qs.getStatus() == QuestStatus.START))
		{
			final int var = qs.getQuestVarById(0);
			if (var == 6)
			{
				final int[] mobs =
				{
					236264,
					236265
				};
				final int[] chest =
				{
					702668
				};
				final int targetId = env.getTargetId();
				final int var1 = qs.getQuestVarById(1);
				final int var2 = qs.getQuestVarById(2);
				switch (targetId)
				{
					case 236264: // Beritra Trailblazer Assassin.
					case 236265:
					{ // Beritra Trailblazer Spelltongue.
						if (var1 < 4)
						{
							return defaultOnKillEvent(env, mobs, 0, 4, 1);
						}
						else if (var1 == 4)
						{
							if (var2 == 3)
							{
								qs.setQuestVar(7);
								updateQuestStatus(env);
								return true;
							}
							return defaultOnKillEvent(env, mobs, 4, 5, 1);
						}
						break;
					}
					case 702668:
					{ // Beritra Supplies Box.
						if (var2 < 2)
						{
							return defaultOnKillEvent(env, chest, 0, 2, 2);
						}
						else if (var2 == 2)
						{
							if (var1 == 5)
							{
								qs.setQuestVar(7);
								updateQuestStatus(env);
								return true;
							}
							return defaultOnKillEvent(env, chest, 2, 3, 2);
						}
						break;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName)
	{
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if ((qs != null) && (qs.getStatus() == QuestStatus.START))
		{
			final int var = qs.getQuestVarById(0);
			if (zoneName == ZoneName.get("LF5_SENSORYAREA_Q10507_210070000"))
			{
				if (var == 7)
				{
					playQuestMovie(env, 993);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean onMovieEndEvent(QuestEnv env, int movieId)
	{
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (movieId == 993)
		{
			qs.setStatus(QuestStatus.REWARD);
			updateQuestStatus(env);
			return true;
		}
		return false;
	}
}