/**
 * This file is part of WiiuseJ.
 *
 *  WiiuseJ is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  WiiuseJ is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with WiiuseJ.  If not, see <http://www.gnu.org/licenses/>.
 */
package wiiusej.test;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import wiiusej.WiiUseApiManager;

/**
 * This class is used to close wiiusej cleanly.
 * 
 * @author guiguito
 */
public class CloseGuiTestCleanly implements WindowListener {

	public void windowOpened(WindowEvent e) {
		// nothing
	}

	public void windowClosing(WindowEvent e) {
		WiiUseApiManager.definitiveShutdown();
	}

	public void windowClosed(WindowEvent e) {
		// nothing
	}

	public void windowIconified(WindowEvent e) {
		// nothing
	}

	public void windowDeiconified(WindowEvent e) {
		// nothing
	}

	public void windowActivated(WindowEvent e) {
		// nothing
	}

	public void windowDeactivated(WindowEvent e) {
		// nothing
	}

}
