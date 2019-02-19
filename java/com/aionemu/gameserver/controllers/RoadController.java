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
package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.controllers.observer.RoadObserver;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.road.Road;

import javolution.util.FastMap;

/**
 * @author SheppeR
 */
public class RoadController extends VisibleObjectController<Road>
{
	FastMap<Integer, RoadObserver> observed = new FastMap<Integer, RoadObserver>().shared();
	
	@Override
	public void see(VisibleObject object)
	{
		final Player p = (Player) object;
		final RoadObserver observer = new RoadObserver(getOwner(), p);
		p.getObserveController().addObserver(observer);
		observed.put(p.getObjectId(), observer);
	}
	
	@Override
	public void notSee(VisibleObject object, boolean isOutOfRange)
	{
		final Player p = (Player) object;
		final RoadObserver observer = observed.remove(p.getObjectId());
		if (isOutOfRange)
		{
			observer.moved();
		}
		p.getObserveController().removeObserver(observer);
	}
}
