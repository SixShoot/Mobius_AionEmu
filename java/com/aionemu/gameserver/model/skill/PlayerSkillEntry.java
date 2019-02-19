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
package com.aionemu.gameserver.model.skill;

import com.aionemu.gameserver.configs.main.CraftConfig;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.stats.container.StatEnum;

/**
 * @author ATracer
 */
public class PlayerSkillEntry extends SkillEntry
{
	
	private final boolean isStigma;
	private final boolean isLinked;
	
	/**
	 * for crafting skills
	 */
	private int currentXp;
	
	private PersistentState persistentState;
	
	public PlayerSkillEntry(int skillId, boolean isStigma, boolean isLinked, int skillLvl, PersistentState persistentState)
	{
		super(skillId, skillLvl);
		this.isStigma = isStigma;
		this.isLinked = isLinked;
		this.persistentState = persistentState;
	}
	
	/**
	 * @return isStigma
	 */
	public boolean isStigma()
	{
		return isStigma;
	}
	
	public boolean isLinked()
	{
		return isLinked;
	}
	
	@Override
	public void setSkillLvl(int skillLevel)
	{
		super.setSkillLvl(skillLevel);
		setPersistentState(PersistentState.UPDATE_REQUIRED);
	}
	
	/**
	 * @return The skill extra lvl
	 */
	public int getExtraLvl()
	{
		switch (skillId)
		{
			case 30002:
			case 30003:
			{
				if ((skillLevel > 399) && (skillLevel < 500))
				{
					return 4;
				}
			}
			case 40001:
			case 40002:
			case 40003:
			case 40004:
			case 40007:
			case 40008:
			case 40010:
			{
				if ((skillLevel > 449) && (skillLevel < 500))
				{
					return 5;
				}
				else if ((skillLevel > 499) && (skillLevel < 550))
				{
					return 6;
				}
				else
				{
					return skillLevel / 100;
				}
			}
		}
		return 0;
	}
	
	/**
	 * @return the currentXp
	 */
	public int getCurrentXp()
	{
		return currentXp;
	}
	
	/**
	 * @param currentXp the currentXp to set
	 */
	public void setCurrentXp(int currentXp)
	{
		this.currentXp = currentXp;
	}
	
	/**
	 * @param player
	 * @param xp
	 * @return
	 */
	public boolean addSkillXp(Player player, int xp)
	{
		currentXp += xp;
		int requiredExp = (int) (0.23 * (skillLevel + 17.2) * (skillLevel + 17.2));
		final StatEnum boostStat = StatEnum.getModifier(skillId);
		if (boostStat != null)
		{
			final float statRate = player.getGameStats().getStat(boostStat, 100).getCurrent() / 100f;
			if (statRate > 0)
			{
				requiredExp /= statRate;
			}
		}
		if (currentXp > requiredExp)
		{
			if (CraftConfig.UNABLE_CRAFT_SKILLS_UNRESTRICTED_LEVELUP == true)
			{
				final float skillUpRatio = (currentXp / (0.23f * (skillLevel + 17.2f) * (skillLevel + 17.2f)));
				int skillUp = skillLevel + (int) skillUpRatio;
				
				if ((skillLevel > 0) && (skillLevel < 99))
				{
					if (skillUp > 99)
					{
						skillUp = 99;
					}
				}
				else if ((skillLevel > 99) && (skillLevel < 199))
				{
					if (skillUp > 199)
					{
						skillUp = 199;
					}
				}
				else if ((skillLevel > 199) && (skillLevel < 299))
				{
					if (skillUp > 299)
					{
						skillUp = 299;
					}
				}
				else if ((skillLevel > 299) && (skillLevel < 399))
				{
					if (skillUp > 399)
					{
						skillUp = 399;
					}
				}
				else if ((skillLevel > 399) && (skillLevel < 449))
				{
					if (skillUp > 449)
					{
						skillUp = 449;
					}
				}
				else if ((skillLevel > 449) && (skillLevel < 499))
				{
					if (skillUp > 499)
					{
						skillUp = 499;
					}
				}
				else if ((skillLevel > 499) && (skillLevel < 549))
				{
					if (skillUp > 549)
					{
						skillUp = 549;
					}
				}
				
				setSkillLvl(skillUp);
				currentXp = 0;
			}
			else
			{
				setSkillLvl(skillLevel + 1);
				currentXp = 0;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * @return the pState
	 */
	public PersistentState getPersistentState()
	{
		return persistentState;
	}
	
	/**
	 * @param persistentState the pState to set
	 */
	public void setPersistentState(PersistentState persistentState)
	{
		switch (persistentState)
		{
			case DELETED:
			{
				if (this.persistentState == PersistentState.NEW)
				{
					this.persistentState = PersistentState.NOACTION;
				}
				else
				{
					this.persistentState = PersistentState.DELETED;
				}
				break;
			}
			case UPDATE_REQUIRED:
			{
				if (this.persistentState != PersistentState.NEW)
				{
					this.persistentState = PersistentState.UPDATE_REQUIRED;
				}
				break;
			}
			case NOACTION:
			{
				break;
			}
			default:
			{
				this.persistentState = persistentState;
			}
		}
	}
	
}
