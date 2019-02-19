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
package com.aionemu.gameserver.services.anohaservice;

import com.aionemu.gameserver.model.anoha.AnohaLocation;
import com.aionemu.gameserver.model.anoha.AnohaStateType;

/**
 * @author Rinzler (Encom)
 */

public class DanuarHero extends BerserkAnoha<AnohaLocation>
{
	public DanuarHero(AnohaLocation anoha)
	{
		super(anoha);
	}
	
	@Override
	public void startAnoha()
	{
		getAnohaLocation().setActiveAnoha(this);
		despawn();
		spawn(AnohaStateType.FIGHT);
	}
	
	@Override
	public void stopAnoha()
	{
		getAnohaLocation().setActiveAnoha(null);
		despawn();
		spawn(AnohaStateType.PEACE);
	}
}