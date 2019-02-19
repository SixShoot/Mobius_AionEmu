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
package com.aionemu.gameserver.model.dorinerk_wardrobe;

import com.aionemu.gameserver.model.gameobjects.Creature;

/**
 * @author Ranastic
 * @param <T>
 */
public interface WardrobeList<T extends Creature>
{
	
	boolean addItem(T creature, int itemId, int slot, int reskin_count);
	
	boolean removeItem(T creature, int itemId);
	
	int size();
}
