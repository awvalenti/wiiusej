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

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;

/**
 * Main Class to launch WiiuseJ GUI Test.
 * 
 * @author guiguito
 */
public class Main {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
		WiiuseJGuiTest gui = null;
		if (wiimotes.length > 0) {
			gui = new WiiuseJGuiTest(wiimotes[0]);
		} else {
			gui = new WiiuseJGuiTest();
		}
		gui.setDefaultCloseOperation(WiiuseJGuiTest.EXIT_ON_CLOSE);
		gui.setVisible(true);
	}

}
