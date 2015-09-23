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
package org.worldgrower.gui.music;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import org.worldgrower.Main;

public class BackgroundMusicUtils {

	public static MusicPlayer startBackgroundMusic() {
		MusicPlayer musicPlayer = new MusicPlayer();
		new Thread() {
        	@Override
        	public void run() {
        		try {
					musicPlayer.play(new BufferedInputStream(new GZIPInputStream(Main.class.getResourceAsStream("/woodland_fantasy_0.wav.gz"))));
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
        	}
        }.start();
        return musicPlayer;
	}
}