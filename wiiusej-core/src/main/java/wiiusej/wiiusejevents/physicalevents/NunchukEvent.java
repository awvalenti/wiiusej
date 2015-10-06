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

/**
 * This class represents the values from the joystick and its events.
 * 
 * @author guiguito
 */
public class NunchukEvent extends ExpansionEvent {

	private NunchukButtonsEvent buttonsEvent;
	private MotionSensingEvent nunchukMotionSensingEvent;
	private JoystickEvent nunchukJoystickEvent;

	/**
	 * Constructor of NunchukEvent.
	 * 
	 * @param id
	 *            id of the wiimote.
	 * @param buttonsJustPressed
	 *            buttons just pressed.
	 * @param buttonsJustReleased
	 *            buttons just released.
	 * @param buttonsHeld
	 *            buttons just pressed.
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
	public NunchukEvent(int id, short buttonsJustPressed,
			short buttonsJustReleased, short buttonsHeld,
			float orientationThreshold, int accelerationThreshold,
			boolean smoothingState, float alphaSmooth, float r, float p,
			float ya, float ar, float ap, float x, float y, float z, short xx,
			short yy, short zz, float angle, float magnitude, short max1,
			short max2, short min1, short min2, short center1, short center2) {
		super(id);
		buttonsEvent = new NunchukButtonsEvent(id, buttonsJustPressed,
				buttonsJustReleased, buttonsHeld);
		nunchukMotionSensingEvent = new MotionSensingEvent(id,
				orientationThreshold, accelerationThreshold, smoothingState,
				alphaSmooth, r, p, ya, ar, ap, x, y, z, xx, yy, zz);
		nunchukJoystickEvent = new JoystickEvent(id, angle, magnitude, max1,
				max2, min1, min2, center1, center2);
	}

	/**
	 * Tell if there is a nunchuk motion sensing Event.
	 * 
	 * @return TRUE if there is a nunchuk motion sensing event, false otherwise.
	 */
	public boolean isThereMotionSensingEvent() {
		return nunchukMotionSensingEvent != null;
	}

	/**
	 * Tell if there is a nunchuk joystick event.
	 * 
	 * @return TRUE if there is a nunchuk joystick event, false otherwise.
	 */
	public boolean isThereNunchukJoystickEvent() {
		return nunchukJoystickEvent != null;
	}

	/**
	 * Get nunchuk buttons event.
	 * 
	 * @return the nunchuk buttons event if there is one or null.
	 */
	public NunchukButtonsEvent getButtonsEvent() {
		return buttonsEvent;
	}

	/**
	 * Get the nunchuk motion sensing event.
	 * 
	 * @return the nunchuk motion sensing event if there is one or null.
	 */
	public MotionSensingEvent getNunchukMotionSensingEvent() {
		return nunchukMotionSensingEvent;
	}

	/**
	 * Get the nunchuk joystick event.
	 * 
	 * @return the nunchuk Joystick Event if there is one or null.
	 */
	public JoystickEvent getNunchukJoystickEvent() {
		return nunchukJoystickEvent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wiiusej.wiiusejevents.GenericEvent#toString()
	 */
	@Override
	public String toString() {
		String out = "";
		/* Status */
		out += "/*********** Nunchuk EVENT : WIIMOTE   ID :" + getWiimoteId()
				+ " ********/\n";
		out += buttonsEvent;
		out += nunchukJoystickEvent;
		out += nunchukMotionSensingEvent;
		return out;
	}

}
