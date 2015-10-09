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
 * This class represents the values from the GuitarHero and its events.
 * 
 * @author guiguito
 */
public class GuitarHeroEvent extends ExpansionEvent{
	
	private float whammyBar;
	private GuitarHeroButtonsEvent buttonsEvent;
	private JoystickEvent guitarHeroJoystickEvent;		
	
	/**
	 * Constructor of GuitarHeroEvent.
	 * 
	 * @param id
	 *            id of the wiimote.
	 * @param buttonsJustPressed
	 *            buttons just pressed.
	 * @param buttonsJustReleased
	 *            buttons just released.
	 * @param buttonsHeld
	 *            buttons just pressed.
	 * @param whammyBar
	 *            whammy bar (range 0-1).
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
	public GuitarHeroEvent(int id, short buttonsJustPressed,
			short buttonsJustReleased, short buttonsHeld, float whammyBar,
			 float angle, float magnitude, short max1,
			short max2, short min1, short min2, short center1,
			short center2) {
		super(id);
		this.whammyBar = whammyBar;
		buttonsEvent = new GuitarHeroButtonsEvent(id,
				buttonsJustPressed, buttonsJustReleased, buttonsHeld);
		guitarHeroJoystickEvent = new JoystickEvent(id, angle,
				magnitude, max1, max2, min1, min2, center1, center2);

	}
	
	/**
	 * Tell if there is a Guitar Hero joystick event.
	 * 
	 * @return TRUE if there is a Guitar Hero joystick event, false otherwise.
	 */
	public boolean isThereGuitarHeroJoystickEvent() {
		return guitarHeroJoystickEvent != null;
	}	

	/**
	 * Whammy bar (range 0-1).
	 * @return the whammyBar value.
	 */
	public float getWhammyBar() {
		return whammyBar;
	}

	/**
	 * Get buttons event for the guitar hero controller.
	 * @return the guitar hero controller buttons event if there is one or null.
	 */
	public GuitarHeroButtonsEvent getButtonsEvent() {
		return buttonsEvent;
	}

	/**
	 * Get event from the joystick of the guitar hero controller.
	 * @return the guitar hero controller joystick Event if there is one or null.
	 */
	public JoystickEvent getGuitarHeroJoystickEvent() {
		return guitarHeroJoystickEvent;
	}

	@Override
	public String toString() {
		String out = "";
		/* Status */
		out += "/*********** Guitar Hero 3 EVENT : WIIMOTE   ID :" + getWiimoteId()
				+ " ********/\n";
		out += buttonsEvent;
		out += "Whammy Bar : "+whammyBar+"\n";
		out += guitarHeroJoystickEvent;
		return out;
	}

}
