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
 * Class which represents a buttons event for a generic event. It means buttons
 * from a wiimote.
 * 
 * @author guiguito
 */
public class WiimoteButtonsEvent extends ButtonsEvent {

	/* Buttons MACRO */
	private static short WIIMOTE_BUTTON_TWO = 0x0001;
	private static short WIIMOTE_BUTTON_ONE = 0x0002;
	private static short WIIMOTE_BUTTON_B = 0x0004;
	private static short WIIMOTE_BUTTON_A = 0x0008;
	private static short WIIMOTE_BUTTON_MINUS = 0x0010;
	private static short WIIMOTE_BUTTON_ZACCEL_BIT6 = 0x0020;
	private static short WIIMOTE_BUTTON_ZACCEL_BIT7 = 0x0040;
	private static short WIIMOTE_BUTTON_HOME = 0x0080;
	private static short WIIMOTE_BUTTON_LEFT = 0x0100;
	private static short WIIMOTE_BUTTON_RIGHT = 0x0200;
	private static short WIIMOTE_BUTTON_DOWN = 0x0400;
	private static short WIIMOTE_BUTTON_UP = 0x0800;
	private static short WIIMOTE_BUTTON_PLUS = 0x1000;
	private static short WIIMOTE_BUTTON_ZACCEL_BIT4 = 0x2000;
	private static short WIIMOTE_BUTTON_ZACCEL_BIT5 = 0x4000;
	private static int WIIMOTE_BUTTON_UNKNOWN = 0x8000;
	private static short WIIMOTE_BUTTON_ALL = 0x1F9F;

	/**
	 * Constructor of the wiimote button Event.
	 * 
	 * @param id
	 *            id of the wiimote.
	 * @param buttonsJustPressed
	 *            buttons just pressed.
	 * @param buttonsJustReleased
	 *            buttons just released.
	 * @param buttonsHeld
	 *            buttons held.
	 */
	public WiimoteButtonsEvent(int id, short buttonsJustPressed,
			short buttonsJustReleased, short buttonsHeld) {
		super(id, buttonsJustPressed, buttonsJustReleased, buttonsHeld);
	}

	/* Button ONE */

	public boolean isButtonOneJustPressed() {
		return isButtonJustPressed(WIIMOTE_BUTTON_ONE);
	}

	public boolean isButtonOneJustReleased() {
		return isButtonJustReleased(WIIMOTE_BUTTON_ONE);
	}

	public boolean isButtonOneHeld() {
		return isButtonHeld(WIIMOTE_BUTTON_ONE);
	}

	public boolean isButtonOnePressed() {
		return isButtonPressed(WIIMOTE_BUTTON_ONE);
	}

	/* Button TWO */

	public boolean isButtonTwoJustPressed() {
		return isButtonJustPressed(WIIMOTE_BUTTON_TWO);
	}

	public boolean isButtonTwoJustReleased() {
		return isButtonJustReleased(WIIMOTE_BUTTON_TWO);
	}

	public boolean isButtonTwoHeld() {
		return isButtonHeld(WIIMOTE_BUTTON_TWO);
	}

	public boolean isButtonTwoPressed() {
		return isButtonPressed(WIIMOTE_BUTTON_TWO);
	}

	/* Button A */

	public boolean isButtonAJustPressed() {
		return isButtonJustPressed(WIIMOTE_BUTTON_A);
	}

	public boolean isButtonAJustReleased() {
		return isButtonJustReleased(WIIMOTE_BUTTON_A);
	}

	public boolean isButtonAHeld() {
		return isButtonHeld(WIIMOTE_BUTTON_A);
	}

	public boolean isButtonAPressed() {
		return isButtonPressed(WIIMOTE_BUTTON_A);
	}

	/* Button B */

	public boolean isButtonBJustPressed() {
		return isButtonJustPressed(WIIMOTE_BUTTON_B);
	}

	public boolean isButtonBJustReleased() {
		return isButtonJustReleased(WIIMOTE_BUTTON_B);
	}

	public boolean isButtonBHeld() {
		return isButtonHeld(WIIMOTE_BUTTON_B);
	}

	public boolean isButtonBPressed() {
		return isButtonPressed(WIIMOTE_BUTTON_B);
	}

	/* Button LEFT */

	public boolean isButtonLeftJustPressed() {
		return isButtonJustPressed(WIIMOTE_BUTTON_LEFT);
	}

	public boolean isButtonLeftJustReleased() {
		return isButtonJustReleased(WIIMOTE_BUTTON_LEFT);
	}

	public boolean isButtonLeftHeld() {
		return isButtonHeld(WIIMOTE_BUTTON_LEFT);
	}

	public boolean isButtonLeftPressed() {
		return isButtonPressed(WIIMOTE_BUTTON_LEFT);
	}

	/* Button RIGHT */

	public boolean isButtonRightJustPressed() {
		return isButtonJustPressed(WIIMOTE_BUTTON_RIGHT);
	}

	public boolean isButtonRightJustReleased() {
		return isButtonJustReleased(WIIMOTE_BUTTON_RIGHT);
	}

	public boolean isButtonRightHeld() {
		return isButtonHeld(WIIMOTE_BUTTON_RIGHT);
	}

	public boolean isButtonRightPressed() {
		return isButtonPressed(WIIMOTE_BUTTON_RIGHT);
	}

	/* Button UP */

	public boolean isButtonUpJustPressed() {
		return isButtonJustPressed(WIIMOTE_BUTTON_UP);
	}

	public boolean isButtonUpJustReleased() {
		return isButtonJustReleased(WIIMOTE_BUTTON_UP);
	}

	public boolean isButtonUpHeld() {
		return isButtonHeld(WIIMOTE_BUTTON_UP);
	}

	public boolean isButtonUpPressed() {
		return isButtonPressed(WIIMOTE_BUTTON_UP);
	}

	/* Button DOWN */

	public boolean isButtonDownJustPressed() {
		return isButtonJustPressed(WIIMOTE_BUTTON_DOWN);
	}

	public boolean isButtonDownJustReleased() {
		return isButtonJustReleased(WIIMOTE_BUTTON_DOWN);
	}

	public boolean isButtonDownHeld() {
		return isButtonHeld(WIIMOTE_BUTTON_DOWN);
	}

	public boolean isButtonDownPressed() {
		return isButtonPressed(WIIMOTE_BUTTON_DOWN);
	}

	/* Button - */

	public boolean isButtonMinusJustPressed() {
		return isButtonJustPressed(WIIMOTE_BUTTON_MINUS);
	}

	public boolean isButtonMinusJustReleased() {
		return isButtonJustReleased(WIIMOTE_BUTTON_MINUS);
	}

	public boolean isButtonMinusHeld() {
		return isButtonHeld(WIIMOTE_BUTTON_MINUS);
	}

	public boolean isButtonMinusPressed() {
		return isButtonPressed(WIIMOTE_BUTTON_MINUS);
	}

	/* Button + */

	public boolean isButtonPlusJustPressed() {
		return isButtonJustPressed(WIIMOTE_BUTTON_PLUS);
	}

	public boolean isButtonPlusJustReleased() {
		return isButtonJustReleased(WIIMOTE_BUTTON_PLUS);
	}

	public boolean isButtonPlusHeld() {
		return isButtonHeld(WIIMOTE_BUTTON_PLUS);
	}

	public boolean isButtonPlusPressed() {
		return isButtonPressed(WIIMOTE_BUTTON_PLUS);
	}

	/* Button HOME */

	public boolean isButtonHomeJustPressed() {
		return isButtonJustPressed(WIIMOTE_BUTTON_HOME);
	}

	public boolean isButtonHomeJustReleased() {
		return isButtonJustReleased(WIIMOTE_BUTTON_HOME);
	}

	public boolean isButtonHomeHeld() {
		return isButtonHeld(WIIMOTE_BUTTON_HOME);
	}

	public boolean isButtonHomePressed() {
		return isButtonPressed(WIIMOTE_BUTTON_HOME);
	}

	@Override
	public String toString() {
		return "/******** Buttons for Wiimote generic Event ********/\n"
				+ super.toString();
	}

}
