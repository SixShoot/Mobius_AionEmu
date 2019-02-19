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
package com.aionemu.gameserver.model.templates.spawns.svsspawns;

import com.aionemu.gameserver.model.svs.SvsStateType;
import com.aionemu.gameserver.model.templates.spawns.SpawnGroup2;
import com.aionemu.gameserver.model.templates.spawns.SpawnSpotTemplate;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;

/**
 * @author Rinzler (Encom)
 */
public class SvsSpawnTemplate extends SpawnTemplate
{
	private int id;
	private SvsStateType svsType;
	
	public SvsSpawnTemplate(SpawnGroup2 spawnGroup, SpawnSpotTemplate spot)
	{
		super(spawnGroup, spot);
	}
	
	public SvsSpawnTemplate(SpawnGroup2 spawnGroup, float x, float y, float z, byte heading, int randWalk, String walkerId, int entityId, int fly)
	{
		super(spawnGroup, x, y, z, heading, randWalk, walkerId, entityId, fly);
	}
	
	public int getId()
	{
		return id;
	}
	
	public SvsStateType getPStateType()
	{
		return svsType;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void setPStateType(SvsStateType svsType)
	{
		this.svsType = svsType;
	}
	
	public final boolean isSvs()
	{
		return svsType.equals(SvsStateType.SVS);
	}
	
	public final boolean isSvsPeace()
	{
		return svsType.equals(SvsStateType.PEACE);
	}
}