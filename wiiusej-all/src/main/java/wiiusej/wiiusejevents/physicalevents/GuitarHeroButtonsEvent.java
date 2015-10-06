/* This file is part of WiiuseJ.
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
 * Class which represents a buttons event from a Guitar Hero controller.
 * 
 * @author guiguito
 */
public class GuitarHeroButtonsEvent extends ButtonsEvent {

	private static short GUITAR_HERO_3_BUTTON_STRUM_UP = 0x0001;
	private static short GUITAR_HERO_3_BUTTON_YELLOW = 0x0008;
	private static short GUITAR_HERO_3_BUTTON_GREEN = 0x0010;
	private static short GUITAR_HERO_3_BUTTON_BLUE = 0x0020;
	private static short GUITAR_HERO_3_BUTTON_RED = 0x0040;
	private static short GUITAR_HERO_3_BUTTON_ORANGE = 0x0080;
	private static short GUITAR_HERO_3_BUTTON_PLUS = 0x0400;
	private static short GUITAR_HERO_3_BUTTON_MINUS = 0x1000;
	private static short GUITAR_HERO_3_BUTTON_STRUM_DOWN = 0x4000;
	private static int GUITAR_HERO_3_BUTTON_ALL = 0xFEFF;

	/**
	 * Constructor of the guitar hero buttons Event.
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
	public GuitarHeroButtonsEvent(int id, short buttonsJustPressed,
			short buttonsJustReleased, short buttonsHeld) {
		super(id, buttonsJustPressed, buttonsJustReleased, buttonsHeld);
	}
	
	/* Button Strum Up */

	public boolean isButtonStrumUpJustPressed() {
		return isButtonJustPressed(GUITAR_HERO_3_BUTTON_STRUM_UP);
	}
	
	public boolean isButtonStrumUpJustReleased() {
		return isButtonJustReleased(GUITAR_HERO_3_BUTTON_STRUM_UP);
	}

	public boolean isButtonStrumUpeHeld() {
		return isButtonHeld(GUITAR_HERO_3_BUTTON_STRUM_UP);
	}

	public boolean isButtonStrumUpPressed() {
		return isButtonPressed(GUITAR_HERO_3_BUTTON_STRUM_UP);
	}
	
	/* Button Strum Down */

	public boolean isButtonStrumDownJustPressed() {
		return isButtonJustPressed(GUITAR_HERO_3_BUTTON_STRUM_DOWN);
	}
	
	public boolean isButtonStrumDownJustReleased() {
		return isButtonJustReleased(GUITAR_HERO_3_BUTTON_STRUM_DOWN);
	}

	public boolean isButtonStrumDowneHeld() {
		return isButtonHeld(GUITAR_HERO_3_BUTTON_STRUM_DOWN);
	}

	public boolean isButtonStrumDownPressed() {
		return isButtonPressed(GUITAR_HERO_3_BUTTON_STRUM_DOWN);
	}
	
	/* Button blue */

	public boolean isButtonBlueJustPressed() {
		return isButtonJustPressed(GUITAR_HERO_3_BUTTON_BLUE);
	}
	
	public boolean isButtonBlueJustReleased() {
		return isButtonJustReleased(GUITAR_HERO_3_BUTTON_BLUE);
	}

	public boolean isButtonBlueHeld() {
		return isButtonHeld(GUITAR_HERO_3_BUTTON_BLUE);
	}

	public boolean isButtonBluePressed() {
		return isButtonPressed(GUITAR_HERO_3_BUTTON_BLUE);
	}
	
	/* Button Green */

	public boolean isButtonGreenJustPressed() {
		return isButtonJustPressed(GUITAR_HERO_3_BUTTON_GREEN);
	}
	
	public boolean isButtonGreenJustReleased() {
		return isButtonJustReleased(GUITAR_HERO_3_BUTTON_GREEN);
	}

	public boolean isButtonGreenHeld() {
		return isButtonHeld(GUITAR_HERO_3_BUTTON_GREEN);
	}

	public boolean isButtonGreenPressed() {
		return isButtonPressed(GUITAR_HERO_3_BUTTON_GREEN);
	}	

	/* Button Minus */

	public boolean isButtonMinusJustPressed() {
		return isButtonJustPressed(GUITAR_HERO_3_BUTTON_MINUS);
	}
	
	public boolean isButtonMinusJustReleased() {
		return isButtonJustReleased(GUITAR_HERO_3_BUTTON_MINUS);
	}

	public boolean isButtonMinusHeld() {
		return isButtonHeld(GUITAR_HERO_3_BUTTON_MINUS);
	}

	public boolean isButtonMinusPressed() {
		return isButtonPressed(GUITAR_HERO_3_BUTTON_MINUS);
	}
	
	/* Button Orange */

	public boolean isButtonOrangeJustPressed() {
		return isButtonJustPressed(GUITAR_HERO_3_BUTTON_ORANGE);
	}
	
	public boolean isButtonOrangeJustReleased() {
		return isButtonJustReleased(GUITAR_HERO_3_BUTTON_ORANGE);
	}

	public boolean isButtonOrangeHeld() {
		return isButtonHeld(GUITAR_HERO_3_BUTTON_ORANGE);
	}

	public boolean isButtonOrangePressed() {
		return isButtonPressed(GUITAR_HERO_3_BUTTON_ORANGE);
	}
	
	/* Button Plus */

	public boolean isButtonPlusJustPressed() {
		return isButtonJustPressed(GUITAR_HERO_3_BUTTON_PLUS);
	}
	
	public boolean isButtonPlusJustReleased() {
		return isButtonJustReleased(GUITAR_HERO_3_BUTTON_PLUS);
	}

	public boolean isButtonPlusHeld() {
		return isButtonHeld(GUITAR_HERO_3_BUTTON_PLUS);
	}

	public boolean isButtonPlusPressed() {
		return isButtonPressed(GUITAR_HERO_3_BUTTON_PLUS);
	}
	
	/* Button Red */

	public boolean isButtonRedJustPressed() {
		return isButtonJustPressed(GUITAR_HERO_3_BUTTON_RED);
	}
	
	public boolean isButtonRedJustReleased() {
		return isButtonJustReleased(GUITAR_HERO_3_BUTTON_RED);
	}

	public boolean isButtonRedHeld() {
		return isButtonHeld(GUITAR_HERO_3_BUTTON_RED);
	}

	public boolean isButtonRedPressed() {
		return isButtonPressed(GUITAR_HERO_3_BUTTON_RED);
	}
	
	/* Button Yellow */

	public boolean isButtonYellowJustPressed() {
		return isButtonJustPressed(GUITAR_HERO_3_BUTTON_YELLOW);
	}
	
	public boolean isButtonYellowJustReleased() {
		return isButtonJustReleased(GUITAR_HERO_3_BUTTON_YELLOW);
	}

	public boolean isButtonYellowHeld() {
		return isButtonHeld(GUITAR_HERO_3_BUTTON_YELLOW);
	}

	public boolean isButtonYellowPressed() {
		return isButtonPressed(GUITAR_HERO_3_BUTTON_YELLOW);
	}	
}
