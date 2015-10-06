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
package wiiusej.values;

/**
 * Class that represents the orientation of the wiimote.
 * 
 * @author guiguito
 */
public class Orientation {

	private float roll;
	private float pitch;
	private float yaw;
	private float a_roll;
	private float a_pitch;

	/**
	 * Default constructor.
	 */
	public Orientation() {
		roll = 0;
		pitch = 0;
		yaw = 0;
		a_roll = 0;
		a_pitch = 0;
	}

	/**
	 * Contructor with raw, pitch , yaw.
	 * 
	 * @param r
	 *            roll (can be smoothed)
	 * @param p
	 *            pitch (can be smoothed)
	 * @param y
	 *            yaw
	 * @param ar
	 *            absolute roll
	 * @param ap
	 *            absolute pitch
	 */
	public Orientation(float r, float p, float y, float ar, float ap) {
		roll = r;
		pitch = p;
		yaw = y;
		a_roll = ar;
		a_pitch = ap;
	}

	/**
	 * Get the roll (can be smoothed).
	 * 
	 * @return the roll
	 */
	public float getRoll() {
		return roll;
	}

	/**
	 * Get the pitch (can be smoothed).
	 * 
	 * @return the pitch
	 */
	public float getPitch() {
		return pitch;
	}

	/**
	 * Get the yaw.
	 * 
	 * @return the yaw
	 */
	public float getYaw() {
		return yaw;
	}

	/**
	 * Get absolute roll (can not be smoothed).
	 * 
	 * @return the a_roll
	 */
	public float getARoll() {
		return a_roll;
	}

	/**
	 * Get absolute pitch (can not be smoothed).
	 * 
	 * @return the a_pitch
	 */
	public float getAPitch() {
		return a_pitch;
	}

	@Override
	public String toString() {
		return "Orientation : (roll: " + roll + ", pitch: " + pitch + ", yaw: "
				+ yaw + ", absolute roll: " + a_roll + ", absolute pitch: "
				+ a_pitch + ")";
	}

}
