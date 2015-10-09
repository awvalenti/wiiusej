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

import wiiusej.values.GForce;
import wiiusej.values.Orientation;
import wiiusej.values.RawAcceleration;
import wiiusej.wiiusejevents.GenericEvent;

/**
 * Class which represents a motion sensing event.
 * 
 * @author guiguito
 */
public class MotionSensingEvent extends GenericEvent {

	/* Motion Sensing */
	private Orientation orientation;
	private GForce gforce;
	private RawAcceleration acceleration;

	private float orientationThreshold = 0;
	private int accelerationThreshold = 0;
	private float alphaSmoothing = 0;
	private boolean isSmoothingActive = false;

	/**
	 * Constructor for a Motion Sensing Event.
	 * 
	 * @param id
	 *            id of the wiimote concerned.
	 * @param orientationThreshold
	 *            value of the minimum angle between two events with the
	 *            accelerometer.
	 * @param accelerationThreshold
	 *            value of the value variation between two events with the
	 *            accelerometer.
	 * @param smoothingState
	 *            true if smoothing flag is activated.
	 * @param alphaSmooth
	 *            value of the alpha smoothing parameter.
	 * @param r
	 *            roll.
	 * @param p
	 *            pitch.
	 * @param ya
	 *            yaw.
	 * @param ar
	 *            absolute roll.
	 * @param ap
	 *            absolute pitch.
	 * @param x
	 *            gravity force on x axis.
	 * @param y
	 *            gravity force on y axis.
	 * @param z
	 *            gravity force on z axis.
	 * @param xx
	 *            raw acceleration on x axis.
	 * @param yy
	 *            raw acceleration on y axis.
	 * @param zz
	 *            raw acceleration on z axis.
	 */
	public MotionSensingEvent(int id, float orientationThreshold,
			int accelerationThreshold, boolean smoothingState,
			float alphaSmooth, float r, float p, float ya, float ar, float ap,
			float x, float y, float z, short xx, short yy, short zz) {
		super(id);
		this.orientationThreshold = orientationThreshold;
		this.accelerationThreshold = accelerationThreshold;
		this.isSmoothingActive = smoothingState;
		this.alphaSmoothing = alphaSmooth;
		setOrientationAndGforce(r, p, ya, ar, ap, x, y, z, xx, yy, zz);
	}

	/**
	 * Set orientation, gravity force and raw acceleration.
	 * 
	 * @param r
	 *            roll
	 * @param p
	 *            pitch
	 * @param ya
	 *            yaw
	 * @param ar
	 *            absolute roll
	 * @param ap
	 *            absolute pitch
	 * @param x
	 *            gravity force on x axis
	 * @param y
	 *            gravity force on y axis
	 * @param z
	 *            gravity force on z axis
	 * @param xx
	 *            raw acceleration on x axis
	 * @param yy
	 *            raw acceleration on y axis
	 * @param zz
	 *            raw acceleration on z axis
	 */
	private void setOrientationAndGforce(float r, float p, float ya, float ar,
			float ap, float x, float y, float z, short xx, short yy, short zz) {
		this.orientation = new Orientation(r, p, ya, ar, ap);
		this.gforce = new GForce(x, y, z);
		this.acceleration = new RawAcceleration(xx, yy, zz);
	}

	/**
	 * @return the orientation
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * Get the gravity force.
	 * 
	 * @return the gforce
	 */
	public GForce getGforce() {
		return gforce;
	}

	/**
	 * Get the raw acceleration.
	 * 
	 * @return the raw acceleration
	 */
	public RawAcceleration getRawAcceleration() {
		return acceleration;
	}

	/**
	 * Get orientation threshold.
	 * 
	 * @return the orientationThreshold
	 */
	public float getOrientationThreshold() {
		return orientationThreshold;
	}

	/**
	 * Get acceleration threshold.
	 * 
	 * @return the accelerationThreshold
	 */
	public int getAccelerationThreshold() {
		return accelerationThreshold;
	}

	/**
	 * Get alpha smoothing.
	 * 
	 * @return the alphaSmoothing
	 */
	public float getAlphaSmoothing() {
		return alphaSmoothing;
	}

	/**
	 * Tell if the option SMOOTHING is activated.
	 * 
	 * @return the isSmoothingActive
	 */
	public boolean isSmoothingActive() {
		return isSmoothingActive;
	}

	@Override
	public String toString() {
		String out = "";
		/* Motion sensing */
		out += "/******** Motion sensing ********/\n";
		out += "--- Motion sensing : true \n";
		out += "--- Orientation threshold value ? : " + orientationThreshold
				+ "\n";
		out += "--- Acceleration threshold value ? : " + accelerationThreshold
				+ "\n";
		out += "--- Alpha smoothing threshold value ? : " + alphaSmoothing
				+ "\n";
		out += "--- Smoothing ? : " + isSmoothingActive + "\n";
		out += "--- " + orientation + "\n";
		out += "--- " + gforce + "\n";
		out += "--- " + acceleration + "\n";
		return out;
	}
}
