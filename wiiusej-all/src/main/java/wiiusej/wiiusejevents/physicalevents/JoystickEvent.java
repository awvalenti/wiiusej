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
package wiiusej.wiiusejevents.physicalevents;

import wiiusej.wiiusejevents.GenericEvent;

/**
 * Class that stores values on a joystick Event.
 * 
 * @author guiguito
 */
public class JoystickEvent extends GenericEvent {

	private float angle;
	private float magnitude;
	private short[] max;
	private short[] min;
	private short[] center;

	/**
	 * Constructor of a JoystickEvent.
	 * 
	 * @param id
	 *            id of the wiimote connected.
	 * @param angle
	 *            angle the joystick is being held.
	 * @param magnitude
	 *            magnitude of the joystick (range 0-1).
	 * @param max1
	 *            maximum joystick value 1.
	 * @param max2
	 *            maximum joystick value 2.
	 * @param min1
	 *            minimum joystick value 1.
	 * @param min2
	 *            minimum joystick value 2.
	 * @param center1
	 *            center joystick value 1.
	 * @param center2
	 *            center joystick value 2.
	 */
	public JoystickEvent(int id, float angle, float magnitude, short max1,
			short max2, short min1, short min2, short center1, short center2) {
		super(id);
		this.angle = angle;
		this.magnitude = magnitude;
		max = new short[2];
		max[0] = max1;
		max[1] = max2;
		min = new short[2];
		min[0] = min1;
		min[1] = min2;
		center = new short[2];
		center[0] = center1;
		center[1] = center2;
	}

	/**
	 * Get angle the joystick is being held.
	 * 
	 * @return the angle angle the joystick.
	 */
	public float getAngle() {
		return angle;
	}

	/**
	 * Get magnitude of the joystick (range 0-1).
	 * 
	 * @return the magnitude magnitude of the joystick.
	 */
	public float getMagnitude() {
		return magnitude;
	}

	/**
	 * Maximum joystick values.
	 * 
	 * @return the max
	 */
	public short[] getMax() {
		return max;
	}

	/**
	 * Minimum joystick values.
	 * 
	 * @return the min
	 */
	public short[] getMin() {
		return min;
	}

	/**
	 * Center joystick values.
	 * 
	 * @return the center
	 */
	public short[] getCenter() {
		return center;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wiiusej.wiiusejevents.GenericEvent#toString()
	 */
	@Override
	public String toString() {
		String out = "";
		/* Display IR Tracking */
		out += "/******** Joystick ********/\n";
		out += "--- angle : " + angle + "\n";
		out += "--- magnitude : " + magnitude + "\n";
		out += "--- maximum values : " + max[0] + "," + max[1] + "\n";
		out += "--- minimum values : " + min[0] + "," + min[1] + "\n";
		out += "--- center values : " + center[0] + "," + center[1] + "\n";
		return out;
	}

}
