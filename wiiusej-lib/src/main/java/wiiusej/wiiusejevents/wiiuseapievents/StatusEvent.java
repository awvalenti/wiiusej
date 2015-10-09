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
package wiiusej.wiiusejevents.wiiuseapievents;

/**
 * Class used to represent a status event. This class is used to know what are
 * the settings of the wiimote.
 * 
 * @author guiguito
 */
public class StatusEvent extends WiiUseApiEvent {

	protected static short WIIMOTE_LED_1 = 1;
	protected static short WIIMOTE_LED_2 = 2;
	protected static short WIIMOTE_LED_3 = 4;
	protected static short WIIMOTE_LED_4 = 8;

	/* ATTACHMENT CONSTANTS */

	private static short EXP_NONE = 0;
	private static short EXP_NUNCHUK = 1;
	private static short EXP_CLASSIC = 2;
	private static short EXP_GUITAR_HERO_3 = 3;

	/* Status variables */
	private boolean connected = false;

	private float batteryLevel = -1;

	private short leds = 0;

	private boolean isSpeakerEnabled = false;

	private int attachment = 0;

	private boolean isRumbleActive = false;

	private boolean isContinuousActive = false;

	private boolean isIrActive = false;

	private boolean isMotionSensingActive = false;

	/**
	 * Construct the StatusEvent setting up the id.
	 * 
	 * @param id
	 *            the Wiimote id
	 */
	public StatusEvent(int id) {
		super(id, WiiUseApiEvent.STATUS_EVENT);
	}

	/**
	 * Build a StatusEvent with all fields set.
	 * 
	 * @param id
	 *            id of the wiimote
	 * @param connect
	 *            true if the wiimote is connected
	 * @param batt
	 *            battery level
	 * @param led
	 *            status of leds
	 * @param speak
	 *            speakers status
	 * @param attach
	 *            attachment status
	 * @param rumbleState
	 *            true if rumble is active
	 * @param continuousState
	 *            true if continuous flag is activated
	 * @param irState
	 *            true if ir is active
	 * @param motionSensingState
	 *            true if accelerometer is active
	 */
	public StatusEvent(int id, boolean connect, float batt, short led,
			boolean speak, int attach, boolean rumbleState,
			boolean continuousState, boolean irState, boolean motionSensingState) {
		super(id, WiiUseApiEvent.STATUS_EVENT);
		connected = connect;
		this.batteryLevel = batt;
		this.leds = led;
		this.isSpeakerEnabled = speak;
		this.attachment = attach;
		isRumbleActive = rumbleState;
		isContinuousActive = continuousState;
		isIrActive = irState;
		isMotionSensingActive = motionSensingState;
	}

	/**
	 * True if the wiimote is connected false otherwise.
	 * 
	 * @return return the connected status.
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * Get battery level.
	 * 
	 * @return battery level. 1 = 100%
	 */
	public float getBatteryLevel() {
		return batteryLevel;
	}

	/**
	 * Get status of the leds .
	 * 
	 * @return a short representing LEDS turned on.
	 */
	public short getLeds() {
		return leds;
	}

	/**
	 * Tells if the given led is turned on according to the leds status int.
	 * 
	 * @param led
	 *            the int encoding a led.
	 * @return true if the led is turned on false otherwise.
	 */
	private boolean ledStatusCheck(short led) {
		if ((leds & led) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get led1 status.
	 * 
	 * @return true if the led is set.
	 */
	public boolean isLed1Set() {
		return ledStatusCheck(WIIMOTE_LED_1);
	}

	/**
	 * Get led2 status.
	 * 
	 * @return true if the led is set.
	 */
	public boolean isLed2Set() {
		return ledStatusCheck(WIIMOTE_LED_2);
	}

	/**
	 * Get led3 status.
	 * 
	 * @return true if the led is set.
	 */
	public boolean isLed3Set() {
		return ledStatusCheck(WIIMOTE_LED_3);
	}

	/**
	 * Get led4 status.
	 * 
	 * @return true if the led is set.
	 */
	public boolean isLed4Set() {
		return ledStatusCheck(WIIMOTE_LED_4);
	}

	/**
	 * Tell if the speaker is enable for this wiimote
	 * 
	 * @return TRUE if it enabled false otherwise
	 */
	public boolean isSpeakerEnabled() {
		return isSpeakerEnabled;
	}

	/**
	 * Get the int representing the attachment type.
	 * 
	 * @return value of the Attachment Type
	 */
	public int getAttachment() {
		return attachment;
	}

	/**
	 * Get the status of rumble.
	 * 
	 * @return true if the rumble is active false otherwise
	 */
	public boolean isRumbleActive() {
		return isRumbleActive;
	}

	/**
	 * Tell if the CONTINUOUS option is activated.
	 * 
	 * @return the isContinuousActive
	 */
	public boolean isContinuousActive() {
		return isContinuousActive;
	}

	/**
	 * Tell if the IR Tracking is active.
	 * 
	 * @return TRUE if it is active or false otherwise.
	 */
	public boolean isIrActive() {
		return isIrActive;
	}

	/**
	 * Get the flag indicating if the motion sensing is active.
	 * 
	 * @return true if the motion sensing is active false otherwise
	 */
	public boolean isMotionSensingActive() {
		return isMotionSensingActive;
	}

	/**
	 * Tells if an attachment is connected.
	 * 
	 * @return true if anything is connected to the wiimote false otherwise.
	 */
	public boolean isAttachmentConnected() {
		return attachment == EXP_NONE;
	}

	/**
	 * Tells if a nunchuk is connected.
	 * 
	 * @return true if a nunchuk is connected to the wiimote false otherwise.
	 */
	public boolean isNunchukConnected() {
		return attachment == EXP_NUNCHUK;
	}

	/**
	 * Tells if a classic controller is connected.
	 * 
	 * @return true if a classic controller is connected to the wiimote false otherwise.
	 */
	public boolean isClassicControllerConnected() {
		return attachment == EXP_CLASSIC;
	}
	
	/**
	 * Tells if a guitar hero controller is connected.
	 * 
	 * @return true if a guitar hero controllerr is connected to the wiimote false otherwise.
	 */
	public boolean isGuitarHeroConnected() {
		return attachment == EXP_GUITAR_HERO_3;
	}

	@Override
	public String toString() {
		String out = "";
		/* Status */
		out += "/*********** STATUS EVENT : WIIMOTE ID :"
				+ super.getWiimoteId() + " ********/\n";
		out += "--- connected : " + connected + "\n";
		out += "--- Battery level : " + batteryLevel + "\n";
		out += "--- Leds : " + leds + "\n";
		out += "--- Speaker enabled : " + isSpeakerEnabled + "\n";
		out += "--- Attachment ? : " + attachment + "\n";
		out += "--- Rumble ? : " + isRumbleActive + "\n";
		out += "--- Continuous ? : " + isContinuousActive + "\n";
		out += "--- IR active ? : " + isIrActive + "\n";
		out += "--- Motion sensing active ? : " + isMotionSensingActive + "\n";
		return out;
	}

}
