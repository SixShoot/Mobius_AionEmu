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
package com.aionemu.gameserver.model.gameobjects.player;

public class PortalCooldownItem
{
	private final int worldId;
	private int entryCount;
	private long cooldown;
	
	public PortalCooldownItem(int worldId, int entryCount, long cooldown)
	{
		this.worldId = worldId;
		this.entryCount = entryCount;
		this.cooldown = cooldown;
	}
	
	public int getWorldId()
	{
		return worldId;
	}
	
	public int getEntryCount()
	{
		return entryCount;
	}
	
	public void setEntryCount(int entryCount)
	{
		this.entryCount = entryCount;
	}
	
	public long getCooldown()
	{
		return cooldown;
	}
	
	public void setCooldown(long cooldown)
	{
		this.cooldown = cooldown;
	}
}
