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
package org.worldgrower.goal;

import java.util.List;

import org.worldgrower.Constants;
import org.worldgrower.OperationInfo;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.attribute.SkillProperty;
import org.worldgrower.attribute.SkillUtils;
import org.worldgrower.conversation.Conversations;
import org.worldgrower.profession.Profession;

public class LearnSkillGoal implements Goal {

	@Override
	public OperationInfo calculateGoal(WorldObject performer, World world) {
		WorldObject organization = GroupPropertyUtils.findProfessionOrganization(performer, world);
		
		if (organization != null) {
			List<WorldObject> organizationMembers = world.findWorldObjects(w -> w.hasProperty(Constants.GROUP) && w.getProperty(Constants.GROUP).contains(organization));
			
			for(WorldObject target : organizationMembers) {
				if (SkillUtils.canTargetTeachPerformer(performer, target)) {
					return new OperationInfo(performer, target, Conversations.createArgs(Conversations.LEARN_SKILLS_USING_ORGANIZATION), Actions.TALK_ACTION);
				}
			}
		}
		return null;
	}
	
	@Override
	public void goalMetOrNot(WorldObject performer, World world, boolean goalMet) {
	}

	@Override
	public boolean isGoalMet(WorldObject performer, World world) {
		Profession performerProfession = performer.getProperty(Constants.PROFESSION);		
		SkillProperty skillProperty = performerProfession.getSkillProperty();
		return performer.getProperty(skillProperty).getLevel() > 2;
	}
	
	@Override
	public boolean isUrgentGoalMet(WorldObject performer, World world) {
		return isGoalMet(performer, world);
	}

	@Override
	public String getDescription() {
		return "improving my skills";
	}

	@Override
	public int evaluate(WorldObject performer, World world) {
		Profession performerProfession = performer.getProperty(Constants.PROFESSION);		
		SkillProperty skillProperty = performerProfession.getSkillProperty();
		return performer.getProperty(skillProperty).getLevel();
	}
}