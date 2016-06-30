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
package org.worldgrower.gui.debug;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import org.worldgrower.Constants;
import org.worldgrower.OperationInfo;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.creaturetype.CreatureType;
import org.worldgrower.deity.Deity;
import org.worldgrower.goal.Goal;
import org.worldgrower.goal.GroupPropertyUtils;
import org.worldgrower.profession.Profession;

public class GuiShowCommonersOverviewAction extends AbstractAction {
	private World world;
	
	public GuiShowCommonersOverviewAction(World world) {
		super();
		this.world = world;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame frame = new JFrame();
		
		JTable table = new JTable(new WorldModel(world));
		table.setBounds(50, 50, 800, 1000);
		table.getColumnModel().getColumn(15).setCellRenderer(new TooltipCellRenderer());
		frame.add(new JScrollPane(table));
		
		table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
		        JTable table =(JTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table.rowAtPoint(p);
		        if (me.getClickCount() == 2) {
		        	WorldObject target = getNPCs().get(row);
		            ShowPerformedActionsAction showPerformedActionsAction = new ShowPerformedActionsAction(target, world);
		            showPerformedActionsAction.actionPerformed(null);
		        }
		    }
		});
		
		Timer timer = new Timer(500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				table.repaint();
			}
			
		});
		
		timer.start();
		
		frame.setBounds(100,  100, 900, 900);
		frame.setVisible(true);
	}
	
	private List<WorldObject> getNPCs() {
		return world.findWorldObjects(w -> w.isControlledByAI() && w.hasIntelligence() && w.getProperty(Constants.CREATURE_TYPE) != CreatureType.COW_CREATURE_TYPE);
	}
	
	class TooltipCellRenderer extends DefaultTableCellRenderer {
	    public Component getTableCellRendererComponent(
	                        JTable table, Object value,
	                        boolean isSelected, boolean hasFocus,
	                        int row, int column) {
	        JLabel c = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	        String pathValue = value.toString();
	        c.setToolTipText(pathValue);
	        return c;
	    }
	}
	
	private class WorldModel extends AbstractTableModel {

		private World world;
		
		public WorldModel(World world) {
			super();
			this.world = world;
		}

		@Override
		public int getColumnCount() {
			return 16;
		}

		@Override
		public int getRowCount() {
			return getNPCs().size();
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			if (columnIndex == 0) {
				return "Name";
			} else if (columnIndex == 1) {
				return "Goal";
			} else if (columnIndex == 2) {
				return "Immediate Goal";
			} else if (columnIndex == 3) {
				return "Profession";
			} else if (columnIndex == 4) {
				return "Food";
			} else if (columnIndex == 5) {
				return "Water";
			} else if (columnIndex == 6) {
				return "Energy";
			} else if (columnIndex == 7) {
				return "Organization";
			} else if (columnIndex == 8) {
				return "Deity";
			} else if (columnIndex == 9) {
				return "Gold";
			} else if (columnIndex == 10) {
				return "OrganizationGold";
			} else if (columnIndex == 11) {
				return "Id";
			} else if (columnIndex == 12) {
				return "Conditions";
			} else if (columnIndex == 13) {
				return "Creature type";
			} else if (columnIndex == 14) {
				return "Level";
			} else if (columnIndex == 15) {
				return "Inventory";
			} else {
				return null;
			}
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			WorldObject npc = getNPCs().get(rowIndex);
			if (columnIndex == 0) {
				return npc.getProperty(Constants.NAME);
			} else if (columnIndex == 1) {
				Goal goal = world.getGoal(npc);
				if (goal != null) {
					return goal.getClass().getSimpleName();
				} else {
					return "";
				}
			} else if (columnIndex == 2) {
				OperationInfo immediateGoal = world.getImmediateGoal(npc, world);
				if (immediateGoal != null) {
					return immediateGoal.getManagedOperation().getClass().getSimpleName();
				} else {
					return "";
				}
			} else if (columnIndex == 3) {
				Profession profession = npc.getProperty(Constants.PROFESSION);
				if (profession != null) {
					return profession.getDescription();
				} else {
					return "";
				}
			} else if (columnIndex == 4) {
				return npc.getProperty(Constants.FOOD);
			} else if (columnIndex == 5) {
				return npc.getProperty(Constants.WATER);
			} else if (columnIndex == 6) {
				return npc.getProperty(Constants.ENERGY);
			} else if (columnIndex == 7) {
				List<WorldObject> organizations = GroupPropertyUtils.getOrganizations(npc, world);
				StringBuilder builder = new StringBuilder();
				for(WorldObject organization : organizations) {
					builder.append(organization.getProperty(Constants.NAME)).append(";");
				}
				return builder.toString();
			} else if (columnIndex == 8) {
				Deity deity = npc.getProperty(Constants.DEITY);
				return deity != null ? deity.getName() : "";
			} else if (columnIndex == 9) {
				return npc.getProperty(Constants.GOLD);
			} else if (columnIndex == 10) {
				return npc.getProperty(Constants.ORGANIZATION_GOLD);
			} else if (columnIndex == 11) {
				return npc.getProperty(Constants.ID);
			} else if (columnIndex == 12) {
				return npc.getProperty(Constants.CONDITIONS).getDescriptions().toString();
			} else if (columnIndex == 13) {
				return npc.getProperty(Constants.CREATURE_TYPE).getDescription();
			} else if (columnIndex == 14) {
				return npc.getProperty(Constants.LEVEL);
			} else if (columnIndex == 15) {
				StringBuilder inventoryDescriptionBuilder = new StringBuilder("<html>");
				WorldObjectContainer inventory = npc.getProperty(Constants.INVENTORY);
				for(int i=0; i<inventory.size(); i++) {
					WorldObject inventoryItem = inventory.get(i);
					if (inventoryItem != null) {
						inventoryDescriptionBuilder.append(inventoryItem).append("<br>");
					}
				}
				inventoryDescriptionBuilder.append("</html>");
				return inventoryDescriptionBuilder.toString();
			} else {
				return null;
			}
		}
	}
}