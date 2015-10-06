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
 * This class represents the values from the classic controller and its events.
 * 
 * @author guiguito
 */
public class ClassicControllerEvent extends ExpansionEvent {

	private float rightShoulder;
	private float leftShoulder;
	private ClassicControllerButtonsEvent buttonsEvent;
	private JoystickEvent classicControllerRightJoystickEvent;
	private JoystickEvent classicControllerLeftJoystickEvent;

	/**
	 * Constructor of ClassicControllerEvent.
	 * 
	 * @param id
	 *            id of the wiimote.
	 * @param buttonsJustPressed
	 *            buttons just pressed.
	 * @param buttonsJustReleased
	 *            buttons just released.
	 * @param buttonsHeld
	 *            buttons just pressed.
	 * @param rightShoulder
	 *            right shoulder button (range 0-1).
	 * @param leftShoulder
	 *            left shoulder button (range 0-1).
	 * @param langle
	 *            angle the left joystick is being held.
	 * @param lmagnitude
	 *            magnitude of the left joystick (range 0-1).
	 * @param lmax1
	 *            maximum left joystick value 1.
	 * @param lmax2
	 *            maximum left joystick value 2.
	 * @param lmin1
	 *            minimum left joystick value 1.
	 * @param lmin2
	 *            minimum left joystick value 2.
	 * @param lcenter1
	 *            center left joystick value 1.
	 * @param lcenter2
	 *            center left joystick value 2.
	 * @param rangle
	 *            angle the right joystick is being held.
	 * @param rmagnitude
	 *            magnitude of the right joystick (range 0-1).
	 * @param rmax1
	 *            maximum right joystick value 1.
	 * @param rmax2
	 *            maximum right joystick value 2.
	 * @param rmin1
	 *            minimum right joystick value 1.
	 * @param rmin2
	 *            minimum right joystick value 2.
	 * @param rcenter1
	 *            center right joystick value 1.
	 * @param rcenter2
	 *            center right joystick value 2.
	 */
	public ClassicControllerEvent(int id, short buttonsJustPressed,
			short buttonsJustReleased, short buttonsHeld, float rightShoulder,
			float leftShoulder, float langle, float lmagnitude, short lmax1,
			short lmax2, short lmin1, short lmin2, short lcenter1,
			short lcenter2, float rangle, float rmagnitude, short rmax1,
			short rmax2, short rmin1, short rmin2, short rcenter1,
			short rcenter2) {
		super(id);
		this.leftShoulder = leftShoulder;
		this.rightShoulder = rightShoulder;
		buttonsEvent = new ClassicControllerButtonsEvent(id,
				buttonsJustPressed, buttonsJustReleased, buttonsHeld);
		classicControllerLeftJoystickEvent = new JoystickEvent(id, langle,
				lmagnitude, lmax1, lmax2, lmin1, lmin2, lcenter1, lcenter2);
		classicControllerRightJoystickEvent = new JoystickEvent(id, rangle,
				rmagnitude, rmax1, rmax2, rmin1, rmin2, rcenter1, rcenter2);
	}

	/**
	 * Tell if there is a classic controller left joystick event.
	 * 
	 * @return TRUE if there is a classic controller left joystick event, false
	 *         otherwise.
	 */
	public boolean isThereClassicControllerLeftJoystickEvent() {
		return classicControllerLeftJoystickEvent != null;
	}

	/**
	 * Tell if there is a classic controller right joystick event.
	 * 
	 * @return TRUE if there is a classic controller right joystick event, false
	 *         otherwise.
	 */
	public boolean isThereClassicControllerRightJoystickEvent() {
		return classicControllerRightJoystickEvent != null;
	}

	/**
	 * Get the right shoulder button(range 0-1).
	 * 
	 * @return value of the rightShoulder button.
	 */
	public float getRightShoulder() {
		return rightShoulder;
	}

	/**
	 * Get the left shoulder button(range 0-1).
	 * 
	 * @return value of the leftShoulder button.
	 */
	public float getLeftShoulder() {
		return leftShoulder;
	}

	/**
	 * Get buttons event for the classic controller.
	 * 
	 * @return the classic controller buttons event if there is one or null.
	 */
	public ClassicControllerButtonsEvent getButtonsEvent() {
		return buttonsEvent;
	}

	/**
	 * Get event from the right joystick of the classic controller.
	 * 
	 * @return the classic controller right Joystick Event if there is one or null.
	 */
	public JoystickEvent getClassicControllerRightJoystickEvent() {
		return classicControllerRightJoystickEvent;
	}

	/**
	 * Get event from the left joystick of the classic controller.
	 * 
	 * @return the classic controller left Joystick Event if there is one or null.
	 */
	public JoystickEvent getClassicControllerLeftJoystickEvent() {
		return classicControllerLeftJoystickEvent;
	}

	@Override
	public String toString() {
		String out = "";
		/* Status */
		out += "/*********** Classic Controller EVENT : WIIMOTE   ID :"
				+ getWiimoteId() + " ********/\n";
		out += buttonsEvent;
		out += "Left shoulder : " + leftShoulder + "\n";
		out += "Right shoulder : " + rightShoulder + "\n";
		out += classicControllerLeftJoystickEvent;
		out += classicControllerRightJoystickEvent;
		return out;
	}

}
