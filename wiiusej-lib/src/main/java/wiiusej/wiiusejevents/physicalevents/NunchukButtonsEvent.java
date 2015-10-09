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
 * Class which represents a buttons event from a Nunchuk.
 * 
 * @author guiguito
 */
public class NunchukButtonsEvent extends ButtonsEvent {

	private static short NUNCHUK_BUTTON_Z = 0x01;
	private static short NUNCHUK_BUTTON_C = 0x02;
	private static short NUNCHUK_BUTTON_ALL = 0x03;

	/**
	 * Constructor of the nunchuk buttons Event.
	 * 
	 * @param id
	 *            id of the wiimote.
	 * @param buttonsJustPressed
	 *            buttons just pressed.
	 * @param buttonsJustReleased
	 *            buttons just released.
	 * @param buttonsHeld
	 *            buttons just pressed.
	 */
	public NunchukButtonsEvent(int id, short buttonsJustPressed,
			short buttonsJustReleased, short buttonsHeld) {
		super(id, buttonsJustPressed, buttonsJustReleased, buttonsHeld);
	}

	/* Button Z */

	public boolean isButtonZJustPressed() {
		return isButtonJustPressed(NUNCHUK_BUTTON_Z);
	}

	public boolean isButtonZJustReleased() {
		return isButtonJustReleased(NUNCHUK_BUTTON_Z);
	}

	public boolean isButtonZeHeld() {
		return isButtonHeld(NUNCHUK_BUTTON_Z);
	}

	public boolean isButtonZPressed() {
		return isButtonPressed(NUNCHUK_BUTTON_Z);
	}

	/* Button Z */

	public boolean isButtonCJustPressed() {
		return isButtonJustPressed(NUNCHUK_BUTTON_C);
	}

	public boolean isButtonCJustReleased() {
		return isButtonJustReleased(NUNCHUK_BUTTON_C);
	}

	public boolean isButtonCHeld() {
		return isButtonHeld(NUNCHUK_BUTTON_C);
	}

	public boolean isButtonCPressed() {
		return isButtonPressed(NUNCHUK_BUTTON_C);
	}

	@Override
	public String toString() {
		return "/******** Buttons for Nunchuk Event ********/\n"
				+ super.toString();
	}

}
