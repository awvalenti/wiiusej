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
package wiiusej.utils;

import wiiusej.values.Orientation;
import wiiusej.wiiusejevents.GenericEvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;

/**
 * Panel to display Orientation in a MotionSensingEvent from a wiimote.
 * 
 * @author guiguito
 */
public class OrientationWiimoteEventPanel extends OrientationPanel {

	@Override
	public Orientation getOrientationValue(GenericEvent e) {
		if (e instanceof MotionSensingEvent) {
			return ((MotionSensingEvent) e).getOrientation();
		}
		return null;
	}

}
