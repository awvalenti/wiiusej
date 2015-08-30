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
package wiiusej;

import wiiusej.wiiusejevents.utils.EventsGatherer;

/**
 * Singleton used to manipulate WiiUse Api.
 * 
 * @author guiguito
 */
public class WiiUseApi {

	static {
		System.loadLibrary("WiiuseJ");
	}

	private static WiiUseApi instance = new WiiUseApi();

	/**
	 * Get the only instance of WiiUseApi.
	 * 
	 * @return the only instace of WiiUseApi.
	 */
	protected static WiiUseApi getInstance() {
		return instance;
	}

	/**
	 * Connect to a wiimote or wiimotes once an address is known.
	 * 
	 * @param nbWiimotes
	 *            The number of wiimotes.
	 * @return The number of wiimotes that successfully connected.
	 */
	synchronized native int connect(int nbWiimotes);

	/**
	 * Find a wiimote or wiimotes.
	 * 
	 * @param nbMaxWiimotes
	 *            The number of wiimotes.
	 * @param timeout
	 *            The number of seconds before the search times out.
	 * @return The number of wiimotes found.
	 */
	synchronized native int find(int nbMaxWiimotes, int timeout);

	/**
	 * Initialize an array of wiimote structures (for the C side of the
	 * library).
	 * 
	 * @param nbPossibleWiimotes
	 *            size of the array.
	 */
	synchronized native void init(int nbPossibleWiimotes);

	/**
	 * Close connection to the wiimote with the given id.
	 * 
	 */
	synchronized native void closeConnection(int id);

	/**
	 * Get unique id of a wiimote in the wiimotes array. Please make sure you
	 * call an existing index with a wiimote initialized at this index, other
	 * wise you'll get a wrong value.
	 * 
	 * @param index
	 *            index of the wiimote in the wiimotes array.
	 * @return the unid of the wiimote, or a wrong value if the index was false.
	 * 
	 */
	synchronized native int getUnId(int index);

	/**
	 * CleanUp Wiiuse API.
	 */
	synchronized native void cleanUp();

	/**
	 * Activate rumble on the wiimote with the given id.
	 * 
	 * @param id
	 *            the id of the wiimote.
	 */
	synchronized native void activateRumble(int id);

	/**
	 * Deactivate rumble on the wiimote with the given id.
	 * 
	 * @param id
	 *            the id of the wiimote.
	 */
	synchronized native void deactivateRumble(int id);

	/**
	 * Activate IR Tracking on the wiimote with the given id.
	 * 
	 * @param id
	 *            the id of the wiimote.
	 */
	synchronized native void activateIRTracking(int id);

	/**
	 * Deactivate IR Tracking on the wiimote with the given id.
	 * 
	 * @param id
	 *            the id of the wiimote.
	 */
	synchronized native void deactivateIRTracking(int id);

	/**
	 * Activate motion sensing on the wiimote with the given id.
	 * 
	 * @param id
	 *            the id of the wiimote.
	 */
	synchronized native void activateMotionSensing(int id);

	/**
	 * Deactivate motion sensing on the wiimote with the given id.
	 * 
	 * @param id
	 *            the id of the wiimote.
	 */
	synchronized native void deactivateMotionSensing(int id);

	/**
	 * Set wiimote leds status.
	 * 
	 * @param id
	 *            the id of the wiimote concerned.
	 * @param led1
	 *            status of led1: True=ON, False=OFF.
	 * @param led2
	 *            status of led2: True=ON, False=OFF.
	 * @param led3
	 *            status of led3: True=ON, False=OFF.
	 * @param led4
	 *            status of led4: True=ON, False=OFF.
	 */
	synchronized native void setLeds(int id, boolean led1, boolean led2,
			boolean led3, boolean led4);

	/**
	 * Set how many degrees an angle must change to generate an event.
	 * 
	 * @param id
	 *            id of the wiimote concerned.
	 * @param angle
	 *            minimum angle detected by an event.
	 */
	synchronized native void setOrientThreshold(int id, float angle);

	/**
	 * Set how much acceleration must change to generate an event.
	 * 
	 * @param id
	 *            id of the wiimote concerned.
	 * @param value
	 *            minimum value detected by an event.
	 */
	synchronized native void setAccelThreshold(int id, int value);

	/**
	 * Set alpha smoothing parameter for the given id.
	 * 
	 * @param id
	 *            id of the wiimote concerned.
	 * @param value
	 *            alpha smoothing value.
	 */
	synchronized native void setAlphaSmoothing(int id, float value);

	/**
	 * Try to resync with the wiimote by starting a new handshake.
	 * 
	 * @param id
	 *            id of the wiimote concerned.
	 */
	synchronized native void reSync(int id);

	/**
	 * Make the the accelerometers give smoother results. This is set by
	 * default.
	 * 
	 * @param id
	 *            the id of the wiimote concerned.
	 */
	synchronized native void activateSmoothing(int id);

	/**
	 * Make the the accelerometers give raw results.
	 * 
	 * @param id
	 *            the id of the wiimote concerned.
	 */
	synchronized native void deactivateSmoothing(int id);

	/**
	 * Make the wiimote generate an event each time we poll. Not set by default.
	 * 
	 * @param id
	 *            the id of the wiimote concerned.
	 */
	synchronized native void activateContinuous(int id);

	/**
	 * Make the wiimote generate an event only when there is one.
	 * 
	 * @param id
	 *            the id of the wiimote concerned.
	 */
	synchronized native void deactivateContinuous(int id);

	/**
	 * Notify wiiuse that your screen has an aspect ratio of 4/3.
	 * 
	 * @param id
	 *            the id of the wiimote of which we want the status.
	 */
	synchronized native void setScreenRatio43(int id);

	/**
	 * Notify wiiuse that your screen has an aspect ratio of 16/9.
	 * 
	 * @param id
	 *            the id of the wiimote of which we want the status.
	 */
	synchronized native void setScreenRatio169(int id);

	/**
	 * Notify wiiuse that the sensor bar is above your screen.
	 * 
	 * @param id
	 *            the id of the wiimote of which we want the status.
	 */
	synchronized native void setSensorBarAboveScreen(int id);

	/**
	 * Notify wiiuse that the sensor bar is below your screen.
	 * 
	 * @param id
	 *            the id of the wiimote of which we want the status.
	 */
	synchronized native void setSensorBarBelowScreen(int id);

	/**
	 * Set virtual screen resolution. It is used to automatically compute the
	 * position of a cursor on this virtual screen using the sensor bar. These
	 * results come in the IREvent.
	 * 
	 * @param id
	 *            the id of the wiimote of which we want the status.
	 * @param x
	 *            x resolution.
	 * @param y
	 *            y resolution.
	 */
	synchronized native void setVirtualScreenResolution(int id, int x, int y);

	/**
	 * Get status and values from the wiimotes and send it through callbacks.
	 * 
	 * @param id
	 *            the id of the wiimote of which we want the status.
	 */
	synchronized native void getStatus(int id);

	/**
	 * Set the normal and expansion handshake timeouts.
	 * 
	 * @param id
	 *            the id of the wiimote concerned.
	 * @param normalTimeout
	 *            The timeout in milliseconds for a normal read.
	 * @param expansionTimeout
	 *            The timeout in millisecondsd to wait for an expansion
	 *            handshake.
	 */
	synchronized native void setTimeout(int id, short normalTimeout,
			short expansionTimeout);

	/**
	 * Set the IR sensitivity.
	 * 
	 * @param id
	 *            the id of the wiimote concerned.
	 * @param level
	 *            1-5, same as Wii system sensitivity setting. If the level is <
	 *            1, then level will be set to 1. If the level is > 5, then
	 *            level will be set to 5.
	 */
	synchronized native void setIrSensitivity(int id, int level);

	/**
	 * Set how many degrees an angle must change to generate an event for the
	 * nunchuk.
	 * 
	 * @param id
	 *            id of the wiimote concerned.
	 * @param angle
	 *            minimum angle detected by an event.
	 */
	synchronized native void setNunchukOrientationThreshold(int id, float angle);

	/**
	 * Set how much acceleration must change to generate an event for the
	 * nunchuk.
	 * 
	 * @param id
	 *            id of the wiimote concerned.
	 * @param value
	 *            minimum value detected by an event.
	 */
	synchronized native void setNunchukAccelerationThreshold(int id, int value);

	/**
	 * Force the bluetooth stack type.(useful only for windows)
	 * 
	 * @param bluetoothStackType
	 *            must be WiiUseApi.WIIUSE_STACK_UNKNOWN or
	 *            WiiUseApi.WIIUSE_STACK_MS or
	 *            WiiUseApi.WIIUSE_STACK_BLUESOLEIL.
	 */
	native void windowsSetBluetoothStack(int bluetoothStackType);

	/**
	 * Check for new Events and Get it.
	 * 
	 * @param gath
	 *            the object where we store all the new events.
	 */
	native void specialPoll(EventsGatherer gath);

}
