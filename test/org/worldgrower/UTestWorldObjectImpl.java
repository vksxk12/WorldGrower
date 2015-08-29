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
package org.worldgrower;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.worldgrower.attribute.ManagedProperty;

public class UTestWorldObjectImpl {

	@Test
	public void testGetProperty() {
		Map<ManagedProperty<?>, Object> properties = new HashMap<>();
		properties.put(Constants.FOOD, 500);
		properties.put(Constants.ID, 1);
		WorldObject worldObject = new WorldObjectImpl(properties);
		
		assertEquals(500, worldObject.getProperty(Constants.FOOD).intValue());
	}
	
	@Test
	public void testInvalidProperty() {
		Map<ManagedProperty<?>, Object> properties = new HashMap<>();
		properties.put(Constants.FOOD, -1);
		properties.put(Constants.ID, 1);
		try {
			new WorldObjectImpl(properties);
			fail("method should fail");
		} catch(IllegalStateException ex) {
			assertEquals("value -1 is lower than minValue 0 for food", ex.getMessage());
		}
	}
	
	@Test
	public void testIncrement() {
		Map<ManagedProperty<?>, Object> properties = new HashMap<>();
		properties.put(Constants.FOOD, 500);
		WorldObject worldObject = new WorldObjectImpl(properties);
		
		worldObject.increment(Constants.FOOD, 1000);
		assertEquals(1000, worldObject.getProperty(Constants.FOOD).intValue());
		
		worldObject.increment(Constants.FOOD, -2000);
		assertEquals(0, worldObject.getProperty(Constants.FOOD).intValue());
	}
	
	@Test
	public void testequals() {
		WorldObject person1 = TestUtils.createIntelligentWorldObject(1, Constants.FOOD, 500);
		WorldObject person2 = TestUtils.createIntelligentWorldObject(2, Constants.FOOD, 500);
		
		assertEquals(false, person1.equals(person2));
		assertEquals(true, person1.equals(person1));
	}
}