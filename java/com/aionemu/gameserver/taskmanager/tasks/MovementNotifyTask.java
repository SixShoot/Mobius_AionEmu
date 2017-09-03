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
package com.aionemu.gameserver.taskmanager.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.aionemu.gameserver.ai2.AI2Logger;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.event.AIEventType;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.world.WorldMapTemplate;
import com.aionemu.gameserver.taskmanager.AbstractFIFOPeriodicTaskManager;
import com.aionemu.gameserver.world.knownlist.VisitorWithOwner;

public class MovementNotifyTask extends AbstractFIFOPeriodicTaskManager<Creature>
{
	private static Map<Integer, int[]> moveBroadcastCounts = new HashMap<>();
	
	static
	{
		final Iterator<WorldMapTemplate> iter = DataManager.WORLD_MAPS_DATA.iterator();
		while (iter.hasNext())
		{
			moveBroadcastCounts.put(iter.next().getMapId(), new int[2]);
		}
	}
	
	private static final class SingletonHolder
	{
		private static final MovementNotifyTask INSTANCE = new MovementNotifyTask();
	}
	
	public static MovementNotifyTask getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private final MoveNotifier MOVE_NOTIFIER = new MoveNotifier();
	
	public MovementNotifyTask()
	{
		super(500);
	}
	
	@Override
	protected void callTask(Creature creature)
	{
		if (creature.getLifeStats().isAlreadyDead())
		{
			return;
		}
		final int limit = (creature.getWorldId() == 400010000) || // Reshanta.
			(creature.getWorldId() == 400020000) || // Belus.
			(creature.getWorldId() == 400030000) || // Transidium Annex.
			(creature.getWorldId() == 400040000) || // Aspida.
			(creature.getWorldId() == 400050000) || // Atanatos.
			(creature.getWorldId() == 400060000) ? 200 : Integer.MAX_VALUE; // Disillon.
		final int iterations = creature.getKnownList().doOnAllNpcsWithOwner(MOVE_NOTIFIER, limit);
		if (!(creature instanceof Player))
		{
			final int[] maxCounts = moveBroadcastCounts.get(creature.getWorldId());
			synchronized (maxCounts)
			{
				if (iterations > maxCounts[0])
				{
					maxCounts[0] = iterations;
					maxCounts[1] = creature.getObjectTemplate().getTemplateId();
				}
			}
		}
	}
	
	public String[] dumpBroadcastStats()
	{
		final List<String> lines = new ArrayList<>();
		lines.add("------- Movement broadcast counts -------");
		for (final Entry<Integer, int[]> entry : moveBroadcastCounts.entrySet())
		{
			lines.add("WorldId=" + entry.getKey() + ": " + entry.getValue()[0] + " (NpcId " + entry.getValue()[1] + ")");
		}
		lines.add("-----------------------------------------");
		return lines.toArray(new String[0]);
	}
	
	@Override
	protected String getCalledMethodName()
	{
		return "notifyOnMove()";
	}
	
	private class MoveNotifier implements VisitorWithOwner<Npc, VisibleObject>
	{
		@Override
		public void visit(Npc object, VisibleObject owner)
		{
			if ((object.getAi2().getState() == AIState.DIED) || object.getLifeStats().isAlreadyDead())
			{
				if (object.getAi2().isLogging())
				{
					AI2Logger.moveinfo(object, "WARN: NPC died but still in knownlist");
				}
				return;
			}
			object.getAi2().onCreatureEvent(AIEventType.CREATURE_MOVED, (Creature) owner);
		}
	}
}