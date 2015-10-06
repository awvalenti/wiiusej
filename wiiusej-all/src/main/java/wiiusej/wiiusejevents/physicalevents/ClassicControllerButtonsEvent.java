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
 * Class which represents a buttons event from a Classic controller.
 * 
 * @author guiguito
 */
public class ClassicControllerButtonsEvent extends ButtonsEvent{
	
	private static short CLASSIC_CTRL_BUTTON_UP = 0x0001;
	private static short CLASSIC_CTRL_BUTTON_LEFT = 0x0002;
	private static short CLASSIC_CTRL_BUTTON_ZR = 0x0004;
	private static short CLASSIC_CTRL_BUTTON_X = 0x0008;
	private static short CLASSIC_CTRL_BUTTON_A = 0x0010;
	private static short CLASSIC_CTRL_BUTTON_Y = 0x0020;
	private static short CLASSIC_CTRL_BUTTON_B = 0x0040;
	private static short CLASSIC_CTRL_BUTTON_ZL = 0x0080;
	private static short CLASSIC_CTRL_BUTTON_FULL_R = 0x0200;
	private static short CLASSIC_CTRL_BUTTON_PLUS = 0x0400;
	private static short CLASSIC_CTRL_BUTTON_HOME = 0x0800;
	private static short CLASSIC_CTRL_BUTTON_MINUS = 0x1000;
	private static short CLASSIC_CTRL_BUTTON_FULL_L = 0x2000;
	private static short CLASSIC_CTRL_BUTTON_DOWN = 0x4000;
	private static int CLASSIC_CTRL_BUTTON_RIGHT = 0x8000;
	private static int CLASSIC_CTRL_BUTTON_ALL = 0xFEFF;
	
	/**
	 * Constructor of the classic controller buttons Event.
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
	public ClassicControllerButtonsEvent(int id, short buttonsJustPressed,
			short buttonsJustReleased, short buttonsHeld) {
		super(id, buttonsJustPressed, buttonsJustReleased, buttonsHeld);
	}

	/* Button LEFT */

	public boolean isButtonLeftJustPressed() {
		return isButtonJustPressed(CLASSIC_CTRL_BUTTON_LEFT);
	}

	public boolean isButtonLeftJustReleased() {
		return isButtonJustReleased(CLASSIC_CTRL_BUTTON_LEFT);
	}

	public boolean isButtonLeftHeld() {
		return isButtonHeld(CLASSIC_CTRL_BUTTON_LEFT);
	}

	public boolean isButtonLeftPressed() {
		return isButtonPressed(CLASSIC_CTRL_BUTTON_LEFT);
	}

	/* Button RIGHT */

	public boolean isButtonRightJustPressed() {
		return isButtonJustPressed(CLASSIC_CTRL_BUTTON_RIGHT);
	}

	public boolean isButtonRightJustReleased() {
		return isButtonJustReleased(CLASSIC_CTRL_BUTTON_RIGHT);
	}

	public boolean isButtonRightHeld() {
		return isButtonHeld(CLASSIC_CTRL_BUTTON_RIGHT);
	}

	public boolean isButtonRightPressed() {
		return isButtonPressed(CLASSIC_CTRL_BUTTON_RIGHT);
	}

	/* Button UP */

	public boolean isButtonUpJustPressed() {
		return isButtonJustPressed(CLASSIC_CTRL_BUTTON_UP);
	}

	public boolean isButtonUpJustReleased() {
		return isButtonJustReleased(CLASSIC_CTRL_BUTTON_UP);
	}

	public boolean isButtonUpHeld() {
		return isButtonHeld(CLASSIC_CTRL_BUTTON_UP);
	}

	public boolean isButtonUpPressed() {
		return isButtonPressed(CLASSIC_CTRL_BUTTON_UP);
	}

	/* Button DOWN */

	public boolean isButtonDownJustPressed() {
		return isButtonJustPressed(CLASSIC_CTRL_BUTTON_DOWN);
	}

	public boolean isButtonDownJustReleased() {
		return isButtonJustReleased(CLASSIC_CTRL_BUTTON_DOWN);
	}

	public boolean isButtonDownHeld() {
		return isButtonHeld(CLASSIC_CTRL_BUTTON_DOWN);
	}

	public boolean isButtonDownPressed() {
		return isButtonPressed(CLASSIC_CTRL_BUTTON_DOWN);
	}
	
	/* Button A */

	public boolean isButtonAJustPressed() {
		return isButtonJustPressed(CLASSIC_CTRL_BUTTON_A);
	}

	public boolean isButtonAJustReleased() {
		return isButtonJustReleased(CLASSIC_CTRL_BUTTON_A);
	}

	public boolean isButtonAHeld() {
		return isButtonHeld(CLASSIC_CTRL_BUTTON_A);
	}

	public boolean isButtonAPressed() {
		return isButtonPressed(CLASSIC_CTRL_BUTTON_A);
	}
	
	/* Button B */

	public boolean isButtonBJustPressed() {
		return isButtonJustPressed(CLASSIC_CTRL_BUTTON_B);
	}

	public boolean isButtonBJustReleased() {
		return isButtonJustReleased(CLASSIC_CTRL_BUTTON_B);
	}

	public boolean isButtonBHeld() {
		return isButtonHeld(CLASSIC_CTRL_BUTTON_B);
	}

	public boolean isButtonBPressed() {
		return isButtonPressed(CLASSIC_CTRL_BUTTON_B);
	}
	
	/* Button X */

	public boolean isButtonXJustPressed() {
		return isButtonJustPressed(CLASSIC_CTRL_BUTTON_X);
	}

	public boolean isButtonXJustReleased() {
		return isButtonJustReleased(CLASSIC_CTRL_BUTTON_X);
	}

	public boolean isButtonXHeld() {
		return isButtonHeld(CLASSIC_CTRL_BUTTON_X);
	}

	public boolean isButtonXPressed() {
		return isButtonPressed(CLASSIC_CTRL_BUTTON_X);
	}

	/* Button Y */

	public boolean isButtonYJustPressed() {
		return isButtonJustPressed(CLASSIC_CTRL_BUTTON_Y);
	}

	public boolean isButtonYJustReleased() {
		return isButtonJustReleased(CLASSIC_CTRL_BUTTON_Y);
	}

	public boolean isButtonYHeld() {
		return isButtonHeld(CLASSIC_CTRL_BUTTON_Y);
	}

	public boolean isButtonYPressed() {
		return isButtonPressed(CLASSIC_CTRL_BUTTON_Y);
	}
	
	/* Button FullLeft */

	public boolean isButtonFullLeftJustPressed() {
		return isButtonJustPressed(CLASSIC_CTRL_BUTTON_FULL_L);
	}

	public boolean isButtonFullLeftJustReleased() {
		return isButtonJustReleased(CLASSIC_CTRL_BUTTON_FULL_L);
	}

	public boolean isButtonFullLeftHeld() {
		return isButtonHeld(CLASSIC_CTRL_BUTTON_FULL_L);
	}

	public boolean isButtonFullLeftPressed() {
		return isButtonPressed(CLASSIC_CTRL_BUTTON_FULL_L);
	}
	
	/* Button FullRight */

	public boolean isButtonFullRightJustPressed() {
		return isButtonJustPressed(CLASSIC_CTRL_BUTTON_FULL_R);
	}

	public boolean isButtonFullRightJustReleased() {
		return isButtonJustReleased(CLASSIC_CTRL_BUTTON_FULL_R);
	}

	public boolean isButtonFullRightHeld() {
		return isButtonHeld(CLASSIC_CTRL_BUTTON_FULL_R);
	}

	public boolean isButtonFullRightPressed() {
		return isButtonPressed(CLASSIC_CTRL_BUTTON_FULL_R);
	}
	
	/* Button Home */

	public boolean isButtonHomeJustPressed() {
		return isButtonJustPressed(CLASSIC_CTRL_BUTTON_HOME);
	}

	public boolean isButtonHomeJustReleased() {
		return isButtonJustReleased(CLASSIC_CTRL_BUTTON_HOME);
	}

	public boolean isButtonHomeHeld() {
		return isButtonHeld(CLASSIC_CTRL_BUTTON_HOME);
	}

	public boolean isButtonHomePressed() {
		return isButtonPressed(CLASSIC_CTRL_BUTTON_HOME);
	}
	
	/* Button Minus */

	public boolean isButtonMinusJustPressed() {
		return isButtonJustPressed(CLASSIC_CTRL_BUTTON_MINUS);
	}

	public boolean isButtonMinusJustReleased() {
		return isButtonJustReleased(CLASSIC_CTRL_BUTTON_MINUS);
	}

	public boolean isButtonMinusHeld() {
		return isButtonHeld(CLASSIC_CTRL_BUTTON_MINUS);
	}

	public boolean isButtonMinusPressed() {
		return isButtonPressed(CLASSIC_CTRL_BUTTON_MINUS);
	}	
	
	/* Button Plus */

	public boolean isButtonPlusJustPressed() {
		return isButtonJustPressed(CLASSIC_CTRL_BUTTON_PLUS);
	}

	public boolean isButtonPlusJustReleased() {
		return isButtonJustReleased(CLASSIC_CTRL_BUTTON_PLUS);
	}

	public boolean isButtonPlusHeld() {
		return isButtonHeld(CLASSIC_CTRL_BUTTON_PLUS);
	}

	public boolean isButtonPlusPressed() {
		return isButtonPressed(CLASSIC_CTRL_BUTTON_PLUS);
	}	
	
	/* Button ZL */

	public boolean isButtonZLJustPressed() {
		return isButtonJustPressed(CLASSIC_CTRL_BUTTON_ZL);
	}

	public boolean isButtonZLJustReleased() {
		return isButtonJustReleased(CLASSIC_CTRL_BUTTON_ZL);
	}

	public boolean isButtonZLHeld() {
		return isButtonHeld(CLASSIC_CTRL_BUTTON_ZL);
	}

	public boolean isButtonZLPressed() {
		return isButtonPressed(CLASSIC_CTRL_BUTTON_ZL);
	}
	
	/* Button ZR */

	public boolean isButtonZRJustPressed() {
		return isButtonJustPressed(CLASSIC_CTRL_BUTTON_ZR);
	}

	public boolean isButtonZRJustReleased() {
		return isButtonJustReleased(CLASSIC_CTRL_BUTTON_ZR);
	}

	public boolean isButtonZRHeld() {
		return isButtonHeld(CLASSIC_CTRL_BUTTON_ZR);
	}

	public boolean isButtonZRPressed() {
		return isButtonPressed(CLASSIC_CTRL_BUTTON_ZR);
	}	

}
