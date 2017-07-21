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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.worldgrower.World;
import org.worldgrower.gui.WorldPanel;

public class GuiShowExplorationOverviewAction extends AbstractAction {
	private WorldPanel worldPanel;
	private World world;
	
	public GuiShowExplorationOverviewAction(WorldPanel worldPanel, World world) {
		super();
		this.worldPanel = worldPanel;
		this.world = world;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame frame = new JFrame();
		JPanel contentPanel = new JPanel(new FlowLayout());
		frame.add(contentPanel);
		
		int screenWidth = worldPanel.getWidth() / 48;
	    int screenHeight = worldPanel.getHeight() / 48;
		JLabel infoLabel = new JLabel("offSetX=" + worldPanel.getScreenX(0) + ",OffSetY="+worldPanel.getScreenY(0) + ",screenWidth=" + screenWidth + ",screenHeight=" + screenHeight);
		contentPanel.add(infoLabel);
		
		JButton isWorldObjectExploredButton = new JButton("WorldObject Explored?");
		contentPanel.add(isWorldObjectExploredButton);
		isWorldObjectExploredButton.addActionListener(ev -> showWorldObjectExploredGui());
		
		JButton isTileExploredButton = new JButton("Tile Explored?");
		contentPanel.add(isTileExploredButton);
		isTileExploredButton.addActionListener(ev -> showTileExploredGui());
		
		frame.setBounds(100,  100, 500, 800);
		frame.setVisible(true);
	}
	
	private void showWorldObjectExploredGui() {
		int x = Integer.parseInt(JOptionPane.showInputDialog("X?"));
		int y = Integer.parseInt(JOptionPane.showInputDialog("Y?"));
		int width = Integer.parseInt(JOptionPane.showInputDialog("width?"));
		int height = Integer.parseInt(JOptionPane.showInputDialog("height?"));
		JOptionPane.showMessageDialog(null, "WorldObject isExplored=" + world.getTerrain().isExplored(x, y, width, height));
	}
	
	private void showTileExploredGui() {
		int x = Integer.parseInt(JOptionPane.showInputDialog("X?"));
		int y = Integer.parseInt(JOptionPane.showInputDialog("Y?"));
		JOptionPane.showMessageDialog(null, "Tile isExplored=" + world.getTerrain().isExplored(x, y));
	}
}