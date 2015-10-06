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

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.event.EventListenerList;

import wiiusej.wiiusejevents.utils.EventsGatherer;
import wiiusej.wiiusejevents.utils.WiiUseApiListener;
import wiiusej.wiiusejevents.wiiuseapievents.WiiUseApiEvent;

/**
 * Class that manages the use of Wiiuse API.
 * 
 * @author guiguito
 */
public class WiiUseApiManager extends Thread {

	private static WiiUseApiManager instance = new WiiUseApiManager();

	private final EventListenerList listeners = new EventListenerList();

	private Semaphore semaphore = new Semaphore(0);

	private Wiimote[] wiimotes;

	private WiiUseApi wiiuse = WiiUseApi.getInstance();

	private int connected = -1;

	private AtomicBoolean running = new AtomicBoolean(false);

	private boolean leave = false;

	public static int WIIUSE_STACK_UNKNOWN = 0;
	public static int WIIUSE_STACK_MS = 1;
	public static int WIIUSE_STACK_BLUESOLEIL = 2;

	public static WiiUseApiManager getInstance() {
		return instance;
	}

	/**
	 * Get wiimotes. Load library if necessary. Connect to wiimotes if
	 * necessary. Start polling if necessary. Return an array with the connected
	 * wiimotes.
	 * 
	 * @param nb
	 *            try to connect nb wiimotes.
	 * @param rumble
	 *            make the connected wiimotes rumble.
	 * 
	 * @return an array with connected wiimotes or NULL.
	 */
	public static Wiimote[] getWiimotes(int nb, boolean rumble) {
		return getWiimotesPrivate(nb, rumble, false, WIIUSE_STACK_UNKNOWN);
	}

	/**
	 * Get wiimotes. Load library if necessary. Connect to wiimotes if
	 * necessary. Start polling if necessary. Return an array with the connected
	 * wiimotes.
	 * 
	 * @param nb
	 *            try to connect nb wiimotes.
	 * @param rumble
	 *            make the connected wiimotes rumble.*
	 * @param stackType
	 *            the stack type : WiiUseApiManager.WIIUSE_STACK_UNKNOWN or
	 *            WiiUseApiManager.WIIUSE_STACK_MS or
	 *            WiiUseApiManager.WIIUSE_STACK_BLUESOLEIL
	 * 
	 * @return an array with connected wiimotes or NULL.
	 */
	public static Wiimote[] getWiimotes(int nb, boolean rumble, int stackType) {
		return getWiimotesPrivate(nb, rumble, true, stackType);
	}

	/**
	 * Get wiimotes. Load library if necessary. Connect to wiimotes if
	 * necessary. Start polling if necessary. Return an array with the connected
	 * wiimotes.
	 * 
	 * @param nb
	 *            try to connect nb wiimotes.
	 * @param rumble
	 *            make the connected wiimotes rumble.*
	 * @param forceStackType
	 *            true if we want to force the stack type.
	 * @param stackType
	 *            the stack type : WiiUseApiManager.WIIUSE_STACK_UNKNOWN or
	 *            WiiUseApiManager.WIIUSE_STACK_MS or
	 *            WiiUseApiManager.WIIUSE_STACK_BLUESOLEIL
	 * 
	 * @return an array with connected wiimotes or NULL.
	 */
	private synchronized static Wiimote[] getWiimotesPrivate(int nb,
			boolean rumble, boolean forceStackType, int stackType) {
		WiiUseApiManager manager = getInstance();

		if (manager.leave)
			return null;// wiiusej definitively stopped

		if (manager.connected <= 0 && !manager.running.get()) {
			// connect wiimotes.
			int nbWiimotes = manager.connectWiimotes(nb, rumble,
					forceStackType, stackType);
			manager.wiimotes = new Wiimote[nbWiimotes];
			for (int i = 0; i < nbWiimotes; i++) {
				Wiimote wim = new Wiimote(WiiUseApi.getInstance().getUnId(i),
						manager);
				manager.wiimotes[i] = wim;
				manager.addWiiUseApiListener(wim);
			}
			// Set leds on wiimote
			for (Wiimote wiimote : manager.wiimotes) {
				int id = wiimote.getId();
				if (id % 4 == 0) {
					wiimote.setLeds(true, true, true, true);
				} else if (id % 4 == 1) {
					wiimote.setLeds(true, false, false, false);
				} else if (id % 4 == 2) {
					wiimote.setLeds(true, true, false, false);
				} else if (id % 4 == 3) {
					wiimote.setLeds(true, true, true, false);
				}
			}
			// make the connected wiimotes rumble
			if (rumble) {
				for (Wiimote wiimote : manager.wiimotes) {
					wiimote.activateRumble();
				}
				try {
					sleep(500);
				} catch (InterruptedException e) {
				}
				for (Wiimote wiimote : manager.wiimotes) {
					wiimote.deactivateRumble();
				}
			}
		}

		if (manager.connected == 0) {// no wiimote connected
			// return empty array
			return new Wiimote[0];
		}

		if (!manager.isAlive())// start wiiuseJ polling
			manager.start();

		manager.semaphore.release(1);

		return manager.wiimotes;
	}

	/**
	 * Connect wiimote and get the number of wiimotes connected. Supposed to be
	 * used once.
	 * 
	 * @param nb
	 *            try to connect nb wiimotes
	 * @param rumble
	 *            make the connected wiimotes rumble
	 * @param forceStackType
	 *            true if we want to force the stack type.
	 * @param stackType
	 *            the stack type : WiiUseApiManager.WIIUSE_STACK_UNKNOWN or
	 *            WiiUseApiManager.WIIUSE_STACK_MS or
	 *            WiiUseApiManager.WIIUSE_STACK_BLUESOLEIL
	 * @return 0 if nothing connected or the number of wiimotes connected.
	 */
	private int connectWiimotes(int nb, boolean rumble, boolean forceStackType,
			int stackType) {
		if (connected <= 0) {
			int nbWiimotesFound;
			wiiuse.init(nb);
			// force bluetooth stack type ?
			if (forceStackType)
				setBlueToothstackType(stackType);
			nbWiimotesFound = wiiuse.find(nb, 3);
			connected = wiiuse.connect(nbWiimotesFound);
			return connected;
		} else {// library not loaded, no wiimotes connected
			return 0;
		}
	}

	/**
	 * Ask the thread to close a connection.
	 * 
	 * @param id
	 *            id of the wiimote to disconnect.
	 */
	protected void closeConnection(int id) {
		int index = 0;
		boolean found = false;
		while (index < wiimotes.length && !found) {
			if (wiimotes[index]!=null && wiimotes[index].getId() == id) {// we have a wiimote with this
				// id
				// remove the wiimote
				removeWiiUseApiListener(wiimotes[index]);
				wiimotes[index] = null;
				connected--;
				if (connected == 0) {// stop this thread if there is
					// no more wiimotes connected.
					// stop thread
					running.set(false);
				}
				/* Close connection in wiiuse */
				wiiuse.closeConnection(index);
			}
			index++;
		}
	}

	/**
	 * Get the number of wiimotes connected.
	 * 
	 * @return the number of wiimotes connected.
	 */
	public static int getNbConnectedWiimotes() {
		return getInstance().connected;
	}

	/**
	 * Stop thread and shutdown wiiuse Api.
	 */
	public static void shutdown() {
		WiiUseApiManager manager = getInstance();
		int pastConnected = manager.connected; 
		if (manager.connected > 0) {
			for (Wiimote wim : manager.wiimotes) {
				if (wim != null)
					wim.disconnect();
			}
		}
		manager.running.set(false);
		if (pastConnected > 0) {
			manager.wiiuse.cleanUp();
		}
	}

	/**
	 * Stop wiiuseJ definitively for this program. It finishes Wiiusej thread
	 * and shutdown wiiuse API.
	 */
	public static void definitiveShutdown() {
		getInstance().leave = true;
		shutdown();
	}

	/**
	 * Activate the rumble for the wiimote with the given id.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	protected void activateRumble(int id) {
		wiiuse.activateRumble(id);
	}

	/**
	 * Deactivate the rumble for the wiimote with the given id.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	protected void deactivateRumble(int id) {
		wiiuse.deactivateRumble(id);
	}

	/**
	 * Activate IR Tracking for the wiimote with the given id.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	protected void activateIRTRacking(int id) {
		wiiuse.activateIRTracking(id);
	}

	/**
	 * Deactivate IR Tracking for the wiimote with the given id.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	protected void deactivateIRTRacking(int id) {
		wiiuse.deactivateIRTracking(id);
	}

	/**
	 * Activate motion sensing for the wiimote with the given id.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	protected void activateMotionSensing(int id) {
		wiiuse.activateMotionSensing(id);
	}

	/**
	 * Deactivate motion sensing for the wiimoter with the given id.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	protected void deactivateMotionSensing(int id) {
		wiiuse.deactivateMotionSensing(id);
	}

	/**
	 * Activate smoothing the wiimotes with the given id.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	protected void activateSmoothing(int id) {
		wiiuse.activateSmoothing(id);
	}

	/**
	 * Deactivate smoothing the wiimotes with the given id.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	protected void deactivateSmoothing(int id) {
		wiiuse.deactivateSmoothing(id);
	}

	/**
	 * Activate continuous for the wiimotes with the given id.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	protected void activateContinuous(int id) {
		wiiuse.activateContinuous(id);
	}

	/**
	 * Deactivate continuous for the wiimotes with the given id.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	protected void deactivateContinuous(int id) {
		wiiuse.deactivateContinuous(id);
	}

	/**
	 * Set leds for the wiimotes with the given id.
	 * 
	 * @param id
	 *            id of the wiimote
	 * @param l1
	 *            status of led1. True : ON, False : OFF.
	 * @param l2
	 *            status of led2. True : ON, False : OFF.
	 * @param l3
	 *            status of led3. True : ON, False : OFF.
	 * @param l4
	 *            status of led4. True : ON, False : OFF.
	 */
	protected void setLeds(int id, boolean l1, boolean l2, boolean l3,
			boolean l4) {
		wiiuse.setLeds(id, l1, l2, l3, l4);
	}

	/**
	 * Set the orientation threshold for the given id. (minimum angle between
	 * two events)
	 * 
	 * @param id
	 *            id of the wiimote.
	 * @param th
	 *            threshold in degrees.
	 */
	protected void setOrientationThreshold(int id, float th) {
		wiiuse.setOrientThreshold(id, th);
	}

	/**
	 * Set the acceleration threshold for the given id. (minimum angle between
	 * two events)
	 * 
	 * @param id
	 *            id of the wiimote.
	 * @param th
	 *            threshold.
	 */
	protected void setAccelerationThreshold(int id, int th) {
		wiiuse.setAccelThreshold(id, th);
	}

	/**
	 * Set alpha smoothing for the given id.
	 * 
	 * @param id
	 *            id of the wiimote.
	 * @param th
	 *            threshold.
	 */
	protected void setAlphaSmoothing(int id, float th) {
		wiiuse.setAlphaSmoothing(id, th);
	}

	/**
	 * Try to resync with the wiimote by starting a new handshake.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	protected void reSync(int id) {
		wiiuse.reSync(id);
	}

	/**
	 * Set screen aspect ratio to 4/3 for the given id.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	protected void setScreenAspectRatio43(int id) {
		wiiuse.setScreenRatio43(id);
	}

	/**
	 * Set screen aspect ratio to 16/9 for the given id.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	protected void setScreenAspectRatio169(int id) {
		wiiuse.setScreenRatio169(id);
	}

	/**
	 * Set the sensor bar to be above the screen.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	protected void setSensorBarAboveScreen(int id) {
		wiiuse.setSensorBarAboveScreen(id);
	}

	/**
	 * Set the sensor bar to be below the screen.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	protected void setSensorBarBelowScreen(int id) {
		wiiuse.setSensorBarBelowScreen(id);
	}

	/**
	 * Set virtual resolution. It is used to automatically compute the position
	 * of a cursor on this virtual screen using the sensor bar. These results
	 * come in the IREvent.
	 * 
	 * @param id
	 *            id of the wiimote.
	 * @param x
	 *            x resolution.
	 * @param y
	 *            y resolution.
	 */
	protected void setVirtualResolution(int id, int x, int y) {
		wiiuse.setVirtualScreenResolution(id, x, y);
	}

	/**
	 * Get Status for the wiimote for the given id.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	protected void getStatus(int id) {
		wiiuse.getStatus(id);
	}

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
	protected void setTimeout(int id, short normalTimeout,
			short expansionTimeout) {
		wiiuse.setTimeout(id, normalTimeout, expansionTimeout);
	}

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
	protected void setIrSensitivity(int id, int level) {
		wiiuse.setIrSensitivity(id, level);
	}

	/**
	 * Set the nunchuk orientation threshold for the given id. (minimum angle
	 * between two events)
	 * 
	 * @param id
	 *            id of the wiimote.
	 * @param th
	 *            threshold in degrees.
	 */
	protected void setNunchukOrientationThreshold(int id, float th) {
		wiiuse.setNunchukOrientationThreshold(id, th);
	}

	/**
	 * Set the nunchuk acceleration threshold for the given id. (minimum angle
	 * between two events)
	 * 
	 * @param id
	 *            id of the wiimote.
	 * @param th
	 *            threshold.
	 */
	protected void setNunchukAccelerationThreshold(int id, int th) {
		wiiuse.setNunchukAccelerationThreshold(id, th);
	}

	/**
	 * Force the bluetooth stack type.(useful only for windows)
	 * 
	 * @param type
	 *            must be WIIUSE_STACK_UNKNOWN or WIIUSE_STACK_MS or
	 *            WIIUSE_STACK_BLUESOLEIL.
	 */
	private void setBlueToothstackType(int type) {
		wiiuse.windowsSetBluetoothStack(type);
	}

	@Override
	public void run() {

		while (!leave) {
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}

			if (connected > 0) {
				running.set(true);

				EventsGatherer gather = new EventsGatherer(connected);

				// Start polling and tell the observers when there are Wiimote
				// events
				while (running.get() && connected > 0) {

					/* Polling */
					wiiuse.specialPoll(gather);

					/* deal with events gathered in Wiiuse API */
					for (WiiUseApiEvent evt : gather.getEvents()) {
						if (evt.getWiimoteId() != -1) {// event filled
							// there is an event notify observers
							notifyWiiUseApiListener(evt);
							if (evt.getEventType() == WiiUseApiEvent.DISCONNECTION_EVENT) {
								// check if it was a disconnection
								// in this case disconnect the wiimote
								closeConnection(evt.getWiimoteId());
							}
						} else {
							System.out
									.println("There is an event with id == -1 ??? there is a problem !!! : "
											+ evt);
						}
					}
					gather.clearEvents();
				}
			}/* else {
				if (connected <= 0) {
					System.out.println("No wiimotes connected !");
				}
			}*/
		}// end while true
	}

	/**
	 * Add WiiUseApiListener to the listeners list.
	 * 
	 * @param listener
	 *            a WiiUseApiListener
	 */
	protected void addWiiUseApiListener(WiiUseApiListener listener) {
		listeners.add(WiiUseApiListener.class, listener);
	}

	/**
	 * Remove WiiUseApiListener from the listeners list.
	 * 
	 * @param listener
	 *            a WiiUseApiListener
	 */
	protected void removeWiiUseApiListener(WiiUseApiListener listener) {
		listeners.remove(WiiUseApiListener.class, listener);
	}

	/**
	 * Get the list of WiiUseApiListeners.
	 * 
	 * @return the list of WiiUseApiListeners.
	 */
	protected WiiUseApiListener[] getWiiUseApiListeners() {
		return listeners.getListeners(WiiUseApiListener.class);
	}

	/**
	 * Notify WiiUseApiListeners that an event occured.
	 * 
	 * @param evt
	 *            GenericEvent occured
	 */
	private void notifyWiiUseApiListener(WiiUseApiEvent evt) {
		for (WiiUseApiListener listener : getWiiUseApiListeners()) {
			listener.onWiiUseApiEvent(evt);
		}
	}

	/**
	 * Called by the garbage collector at the end.
	 */
	protected void finalize() throws Throwable {
		shutdown();
	}

}
