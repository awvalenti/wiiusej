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
 * Class which represents a buttons event.
 * 
 * @author guiguito
 */
public abstract class ButtonsEvent extends GenericEvent {

	/* Buttons */
	private short buttonsJustPressed = 0;
	private short buttonsJustReleased = 0;
	private short buttonsHeld = 0;

	/**
	 * Constructor of the button Event.
	 * 
	 * @param id
	 *            id of the wiimote concerned.
	 * @param buttonsJustPressed
	 *            buttons just pressed.
	 * @param buttonsJustReleased
	 *            buttons just released.
	 * @param buttonsHeld
	 *            buttons just held.
	 */
	public ButtonsEvent(int id, short buttonsJustPressed,
			short buttonsJustReleased, short buttonsHeld) {
		super(id);
		setAllButtons(buttonsJustPressed, buttonsJustReleased, buttonsHeld);
	}

	/**
	 * Set all buttons in one method.
	 * 
	 * @param buttonsJustPressed
	 * @param buttonsJustReleased
	 * @param buttonsHeld
	 */
	private void setAllButtons(short buttonsJustPressed,
			short buttonsJustReleased, short buttonsHeld) {
		this.buttonsJustPressed = buttonsJustPressed;
		this.buttonsJustReleased = buttonsJustReleased;
		this.buttonsHeld = buttonsHeld;
	}

	/**
	 * Get the short storing the buttons just pressed
	 * 
	 * @return the short storing the buttons just pressed
	 */
	public short getButtonsJustPressed() {
		return buttonsJustPressed;
	}

	/**
	 * Get the short storing the buttons just released
	 * 
	 * @return the short storing the buttons just released
	 */
	public short getButtonsJustReleased() {
		return buttonsJustReleased;
	}

	/**
	 * get the short storing the buttons held
	 * 
	 * @return the short storing the buttons held
	 */
	public short getButtonsHeld() {
		return buttonsHeld;
	}

	/** **************** BUTTONS Methods ***************** */
	/* generic button functions */

	protected boolean buttonTest(int buttonBitsDefinition, int buttons) {
		return (buttons & buttonBitsDefinition) == buttonBitsDefinition;
	}

	protected boolean isButtonJustPressed(int buttonBitsDefinition) {
		return buttonTest(buttonBitsDefinition, buttonsJustPressed)
				&& !isButtonHeld(buttonBitsDefinition);
	}

	protected boolean isButtonJustReleased(int buttonBitsDefinition) {
		return buttonTest(buttonBitsDefinition, buttonsJustReleased);
	}

	protected boolean isButtonHeld(int buttonBitsDefinition) {
		return buttonTest(buttonBitsDefinition, buttonsHeld);
	}

	protected boolean isButtonPressed(int buttonBitsDefinition) {
		return isButtonHeld(buttonBitsDefinition)
				|| isButtonJustPressed(buttonBitsDefinition);
	}

	@Override
	public String toString() {
		String out = "";
		/* Display buttons */
		out += "/******** Buttons ********/\n";
		out += "--- Buttons just pressed : " + buttonsJustPressed + "\n";
		out += "--- Buttons just released : " + buttonsJustReleased + "\n";
		out += "--- Buttons held : " + buttonsHeld + "\n";
		return out;
	}

}
