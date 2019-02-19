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
package system.handlers.ai.instance.cradleOfEternity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.actions.CreatureActions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldPosition;

import system.handlers.ai.AggressiveNpcAI2;

/****/
/**
 * Author Rinzler, Ranastic (Encom) /
 ****/

@AIName("Vile_Typhon")
public class Vile_TyphonAI2 extends AggressiveNpcAI2
{
	private int snakeRPhase = 0;
	private Future<?> phaseTask;
	private boolean canThink = true;
	private final AtomicBoolean isAggred = new AtomicBoolean(false);
	
	@Override
	protected void handleAttack(Creature creature)
	{
		super.handleAttack(creature);
		if (isAggred.compareAndSet(false, true))
		{
		}
		checkPercentage(getLifeStats().getHpPercentage());
	}
	
	private void checkPercentage(int hpPercentage)
	{
		if ((hpPercentage == 95) && (snakeRPhase < 1))
		{
			snakeRPhase = 1;
			startPhaseTask();
		}
		if ((hpPercentage == 70) && (snakeRPhase < 2))
		{
			snakeRPhase = 2;
			startPhaseTask();
		}
		if ((hpPercentage == 50) && (snakeRPhase < 3))
		{
			snakeRPhase = 3;
			startPhaseTask();
		}
		if ((hpPercentage == 30) && (snakeRPhase < 4))
		{
			snakeRPhase = 4;
			startPhaseTask();
		}
		if ((hpPercentage == 10) && (snakeRPhase < 5))
		{
			snakeRPhase = 5;
			startPhaseTask();
		}
	}
	
	private void startPhaseTask()
	{
		phaseTask = ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable) () ->
		{
			if (isAlreadyDead())
			{
				cancelPhaseTask();
			}
			else
			{
				SkillEngine.getInstance().getSkill(getOwner(), 23024, 60, getOwner()).useNoAnimationSkill(); // Inferno Fiend.
				final List<Player> players = getLifedPlayers();
				if (!players.isEmpty())
				{
					final int size = players.size();
					if (players.size() < 6)
					{
						for (Player p : players)
						{
							spawnSnakeRSummon(p);
						}
					}
					else
					{
						final int count = Rnd.get(6, size);
						for (int i = 0; i < count; i++)
						{
							if (players.isEmpty())
							{
								break;
							}
							spawnSnakeRSummon(players.get(Rnd.get(players.size())));
						}
					}
				}
			}
		}, 20000, 40000);
	}
	
	private void spawnSnakeRSummon(Player player)
	{
		final float x = player.getX();
		final float y = player.getY();
		final float z = player.getZ();
		if ((x > 0) && (y > 0) && (z > 0))
		{
			spawn(220554, x, y, z, (byte) 0); // IDEternity_02_Snake_Area.
			ThreadPoolManager.getInstance().schedule((Runnable) () ->
			{
				if (!isAlreadyDead())
				{
					spawn(220542, x, y, z, (byte) 0); // IDEternity_02_SnakeR_Boss_Summon.
				}
			}, 3000);
		}
	}
	
	@Override
	public boolean canThink()
	{
		return canThink;
	}
	
	private List<Player> getLifedPlayers()
	{
		final List<Player> players = new ArrayList<>();
		for (Player player : getKnownList().getKnownPlayers().values())
		{
			if (!CreatureActions.isAlreadyDead(player))
			{
				players.add(player);
			}
		}
		return players;
	}
	
	void cancelPhaseTask()
	{
		if ((phaseTask != null) && !phaseTask.isDone())
		{
			phaseTask.cancel(true);
		}
	}
	
	private void deleteHelpers()
	{
		final WorldMapInstance instance = getPosition().getWorldMapInstance();
		if (instance != null)
		{
			deleteNpcs(instance.getNpcs(220542)); // IDEternity_02_SnakeR_Boss_Summon.
			deleteNpcs(instance.getNpcs(220554)); // IDEternity_02_Snake_Area.
		}
	}
	
	@Override
	protected void handleDied()
	{
		cancelPhaseTask();
		final WorldPosition p = getPosition();
		if (p != null)
		{
			deleteNpcs(p.getWorldMapInstance().getNpcs(220542)); // IDEternity_02_SnakeR_Boss_Summon.
			deleteNpcs(p.getWorldMapInstance().getNpcs(220554)); // IDEternity_02_Snake_Area.
		}
		super.handleDied();
	}
	
	private void deleteNpcs(List<Npc> npcs)
	{
		for (Npc npc : npcs)
		{
			if (npc != null)
			{
				npc.getController().onDelete();
			}
		}
	}
	
	@Override
	protected void handleDespawned()
	{
		cancelPhaseTask();
		super.handleDespawned();
	}
	
	@Override
	protected void handleBackHome()
	{
		canThink = true;
		cancelPhaseTask();
		deleteHelpers();
		isAggred.set(false);
		super.handleBackHome();
	}
}