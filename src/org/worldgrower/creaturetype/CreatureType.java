/*******************************************************************************
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.worldgrower.creaturetype;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public interface CreatureType extends Serializable {
	public boolean canTalk();
	public boolean canMove();
	
	public static final List<CreatureType> ALL_CREATURE_TYPES = new ArrayList<>();
	
	public static final UndeadCreatureType UNDEAD_CREATURE_TYPE = new UndeadCreatureType(ALL_CREATURE_TYPES);
	public static final HumanCreatureType HUMAN_CREATURE_TYPE = new HumanCreatureType(ALL_CREATURE_TYPES);
	public static final RatCreatureType RAT_CREATURE_TYPE = new RatCreatureType(ALL_CREATURE_TYPES);
	public static final SpiderCreatureType SPIDER_CREATURE_TYPE = new SpiderCreatureType(ALL_CREATURE_TYPES);
	public static final SlimeCreatureType SLIME_CREATURE_TYPE = new SlimeCreatureType(ALL_CREATURE_TYPES);
	public static final DeityCreatureType DEITY_CREATURE_TYPE = new DeityCreatureType(ALL_CREATURE_TYPES);
	public static final PlantCreatureType PLANT_CREATURE_TYPE = new PlantCreatureType(ALL_CREATURE_TYPES);
	public static final GoblinCreatureType GOBLIN_CREATURE_TYPE = new GoblinCreatureType(ALL_CREATURE_TYPES);
	
	public default Object readResolveImpl() throws ObjectStreamException {
		Class<?> clazz = getClass();
		List<CreatureType> allCreatureTypes = ALL_CREATURE_TYPES;
		
		for(CreatureType creatureType : allCreatureTypes) {
			if (creatureType.getClass() == clazz) {
				return creatureType;
			}
		}
		throw new IllegalStateException("CreatureType with class " + clazz + " not found");
	}
}