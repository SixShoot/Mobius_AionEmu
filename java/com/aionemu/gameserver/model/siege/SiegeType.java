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
package com.aionemu.gameserver.model.siege;

/**
 * @author Sarynth
 */
public enum SiegeType
{
	FORTRESS(0),
	ARTIFACT(1),
	BOSSRAID_LIGHT(2),
	BOSSRAID_DARK(3),
	INDUN(4),
	UNDERPASS(5),
	TOWER(6);
	
	private int typeId;
	
	private SiegeType(int id)
	{
		typeId = id;
	}
	
	public int getTypeId()
	{
		return typeId;
	}
}