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
package com.aionemu.gameserver.spawnengine;

import java.util.HashSet;

import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.templates.spawns.SpawnGroup2;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.model.templates.spawns.TemporarySpawn;

import javolution.util.FastList;
import javolution.util.FastMap;

public class TemporarySpawnEngine
{
	
	private static final FastList<SpawnGroup2> temporarySpawns = new FastList<>();
	private static final FastMap<SpawnGroup2, HashSet<Integer>> tempSpawnInstanceMap = new FastMap<>();
	
	public static void spawnAll()
	{
		spawn(true);
	}
	
	public static void onHourChange()
	{
		despawn();
		spawn(false);
	}
	
	private static void despawn()
	{
		for (final SpawnGroup2 spawn : temporarySpawns)
		{
			for (final SpawnTemplate template : spawn.getSpawnTemplates())
			{
				if (template.getTemporarySpawn().canDespawn())
				{
					final VisibleObject object = template.getVisibleObject();
					if (object == null)
					{
						continue;
					}
					if (object instanceof Npc)
					{
						final Npc npc = (Npc) object;
						if (!npc.getLifeStats().isAlreadyDead() && template.hasPool())
						{
							spawn.setTemplateUse(npc.getInstanceId(), template, false);
						}
						npc.getController().cancelTask(TaskId.RESPAWN);
					}
					if (object.isSpawned())
					{
						object.getController().onDelete();
					}
				}
			}
		}
	}
	
	private static void spawn(boolean startCheck)
	{
		for (final SpawnGroup2 spawn : temporarySpawns)
		{
			final HashSet<Integer> instances = tempSpawnInstanceMap.get(spawn);
			if (spawn.hasPool())
			{
				final TemporarySpawn temporarySpawn = spawn.geTemporarySpawn();
				if (temporarySpawn.canSpawn() || (startCheck && (spawn.getRespawnTime() != 0) && temporarySpawn.isInSpawnTime()))
				{
					for (final Integer instanceId : instances)
					{
						spawn.resetTemplates(instanceId);
						for (int pool = 0; pool < spawn.getPool(); pool++)
						{
							final SpawnTemplate template = spawn.getRndTemplate(instanceId);
							SpawnEngine.spawnObject(template, instanceId);
						}
					}
				}
			}
			else
			{
				for (final SpawnTemplate template : spawn.getSpawnTemplates())
				{
					final TemporarySpawn temporarySpawn = template.getTemporarySpawn();
					if (temporarySpawn.canSpawn() || (startCheck && !template.isNoRespawn() && temporarySpawn.isInSpawnTime()))
					{
						for (final Integer instanceId : instances)
						{
							SpawnEngine.spawnObject(template, instanceId);
						}
					}
				}
			}
		}
	}
	
	/**
	 * @param spawnTemplate
	 */
	public static void addSpawnGroup(SpawnGroup2 spawn, int instanceId)
	{
		temporarySpawns.add(spawn);
		HashSet<Integer> instances = tempSpawnInstanceMap.get(spawn);
		if (instances == null)
		{
			instances = new HashSet<>();
			tempSpawnInstanceMap.put(spawn, instances);
		}
		instances.add(instanceId);
	}
}