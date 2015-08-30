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

import javax.swing.event.EventListenerList;

import wiiusej.wiiusejevents.utils.WiiUseApiListener;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;
import wiiusej.wiiusejevents.wiiuseapievents.WiiUseApiEvent;
import wiiusej.wiiusejevents.wiiuseapievents.WiimoteEvent;

/**
 * Class that represents a wiimote. You can register as an observer of this
 * wiimote to listen events from it. You manage it.
 * 
 * @author guiguito
 */
public class Wiimote implements WiiUseApiListener {

	private int id = -1;// wiimote id

	private EventListenerList listeners = new EventListenerList();

	private WiiUseApiManager manager;

	/**
	 * Constructor.
	 * 
	 * @param idd
	 *            id of the wiimote
	 * @param manager
	 *            manager wo built it.
	 */
	public Wiimote(int idd, WiiUseApiManager manager) {
		id = idd;
		this.manager = manager;
	}

	/**
	 * Get the unique id of the wiimote.
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Disconnect this wiimote.
	 */
	public void disconnect() {
		deactivateIRTRacking();
		deactivateMotionSensing();
		deactivateRumble();
		manager.closeConnection(id);
	}

	/**
	 * Activate the rumble.
	 */
	public void activateRumble() {
		manager.activateRumble(id);
	}

	/**
	 * Deactivate the rumble.
	 */
	public void deactivateRumble() {
		manager.deactivateRumble(id);
	}

	/**
	 * Activate IR Tracking.
	 */
	public void activateIRTRacking() {
		manager.activateIRTRacking(id);
	}

	/**
	 * Deactivate IR Tracking.
	 */
	public void deactivateIRTRacking() {
		manager.deactivateIRTRacking(id);
	}

	/**
	 * Activate motion sensing.
	 */
	public void activateMotionSensing() {
		manager.activateMotionSensing(id);
	}

	/**
	 * Deactivate motion sensing.
	 */
	public void deactivateMotionSensing() {
		manager.deactivateMotionSensing(id);
	}

	/**
	 * Activate smoothing.
	 */
	public void activateSmoothing() {
		manager.activateSmoothing(id);
	}

	/**
	 * Deactivate smoothing.
	 */
	public void deactivateSmoothing() {
		manager.deactivateSmoothing(id);
	}

	/**
	 * Activate continuous.
	 */
	public void activateContinuous() {
		manager.activateContinuous(id);
	}

	/**
	 * Deactivate continuous.
	 */
	public void deactivateContinuous() {
		manager.deactivateContinuous(id);

	}

	/**
	 * Set leds status.
	 * 
	 * @param l1
	 *            status of led1. True : ON, False : OFF
	 * @param l2
	 *            status of led2. True : ON, False : OFF
	 * @param l3
	 *            status of led3. True : ON, False : OFF
	 * @param l4
	 *            status of led4. True : ON, False : OFF
	 */
	public void setLeds(boolean l1, boolean l2, boolean l3, boolean l4) {
		manager.setLeds(id, l1, l2, l3, l4);
	}

	/**
	 * Set the orientation threshold (minimum angle between two degrees with
	 * accelerometer).
	 * 
	 * @param th
	 *            threshold in degrees
	 */
	public void setOrientationThreshold(float th) {
		manager.setOrientationThreshold(id, th);
	}

	/**
	 * Set the acceleration threshold(minimum angle between two degrees with
	 * accelerometer).
	 * 
	 * @param th
	 *            threshold
	 */
	public void setAccelerationThreshold(int th) {
		manager.setAccelerationThreshold(id, th);
	}

	/**
	 * Set the alpha smoothing value.
	 * 
	 * @param th
	 *            threshold
	 */
	public void setAlphaSmoothingValue(float th) {
		manager.setAlphaSmoothing(id, th);
	}

	/**
	 * Set the screen aspect ratio to be considered as 4/3.
	 */
	public void setScreenAspectRatio43() {
		manager.setScreenAspectRatio43(id);
	}

	/**
	 * Set the screen aspect ratio to be considered as 16/9.
	 */
	public void setScreenAspectRatio169() {
		manager.setScreenAspectRatio169(id);
	}

	/**
	 * Set the sensor bar to be considered above the screen.
	 */
	public void setSensorBarAboveScreen() {
		manager.setSensorBarAboveScreen(id);
	}

	/**
	 * Set the sensor bar to be considered below the screen.
	 */
	public void setSensorBarBelowScreen() {
		manager.setSensorBarBelowScreen(id);
	}

	/**
	 * Set the screen resolution of the you are pointing at with your wiimote.
	 * 
	 * @param x
	 *            x resolution.
	 * @param y
	 *            y resolution.
	 */
	public void setVirtualResolution(int x, int y) {
		manager.setVirtualResolution(id, x, y);
	}

	/**
	 * Set the nunchuk orientation threshold for the given id. (minimum angle
	 * between two events)
	 * 
	 * @param th
	 *            threshold in degrees.
	 */
	public void setNunchukOrientationThreshold(float th) {
		manager.setNunchukOrientationThreshold(id, th);
	}

	/**
	 * Set the nunchuk acceleration threshold for the given id. (minimum angle
	 * between two events)
	 * 
	 * @param th
	 *            threshold.
	 */
	public void setNunchukAccelerationThreshold(int th) {
		manager.setNunchukAccelerationThreshold(id, th);
	}

	/**
	 * Try to resync the wiimote by starting a new handshake.
	 */
	public void reSync() {
		manager.reSync(id);
	}

	/**
	 * Ask for the status of the wiimote. The result will be received in a
	 * status event object. Implements onStatusEvent on wiimote listener to get
	 * it.
	 */
	public void getStatus() {
		manager.getStatus(id);
	}

	/**
	 * Set the normal and expansion handshake timeouts for this wiimote. Normal
	 * time out is for classic polling. The expansion timeout is used when an
	 * expansion is detected until the expansion successfully handshakes.
	 * 
	 * @param normalTimeout
	 *            The timeout in milliseconds for a normal read.
	 * @param expansionTimeout
	 *            The timeout in millisecondsd to wait for an expansion
	 *            handshake.
	 */
	public void setTimeout(short normalTimeout, short expansionTimeout) {
		manager.setTimeout(id, normalTimeout, expansionTimeout);
	}

	/**
	 * Set the IR sensitivity.
	 * 
	 * @param level
	 *            1-5, same as Wii system sensitivity setting. If the level is <
	 *            1, then level will be set to 1. If the level is > 5, then
	 *            level will be set to 5.
	 */
	public void setIrSensitivity(int level) {
		manager.setIrSensitivity(id, level);
	}

	/**
	 * Method called when a WiiUseApiEvent occurs.
	 * 
	 * @param e
	 *            the WiiUseApiEvent.
	 */
	public void onWiiUseApiEvent(WiiUseApiEvent e) {
		if (e.getWiimoteId() == id) {
			if (e.getEventType() == WiiUseApiEvent.GENERIC_EVENT) {
				notifyWiiMoteEventListeners((WiimoteEvent) e);
			} else if (e.getEventType() == WiiUseApiEvent.STATUS_EVENT) {
				notifyStatusEventListeners((StatusEvent) e);
			} else if (e.getEventType() == WiiUseApiEvent.DISCONNECTION_EVENT) {
				notifyDisconnectionEventListeners((DisconnectionEvent) e);
			} else if (e.getEventType() == WiiUseApiEvent.WIIUSE_NUNCHUK_INSERTED) {
				notifyNunchukInsertedEventListeners((NunchukInsertedEvent) e);
			} else if (e.getEventType() == WiiUseApiEvent.WIIUSE_NUNCHUK_REMOVED) {
				notifyNunchukRemovedEventListeners((NunchukRemovedEvent) e);
			} else if (e.getEventType() == WiiUseApiEvent.WIIUSE_GUITAR_HERO_3_CTRL_INSERTED) {
				notifyGuitarHeroInsertedEventListeners((GuitarHeroInsertedEvent) e);
			} else if (e.getEventType() == WiiUseApiEvent.WIIUSE_GUITAR_HERO_3_CTRL_REMOVED) {
				notifyGuitarHeroRemovedEventListeners((GuitarHeroRemovedEvent) e);
			} else if (e.getEventType() == WiiUseApiEvent.WIIUSE_CLASSIC_CTRL_INSERTED) {
				notifyClassicControllerInsertedEventListeners((ClassicControllerInsertedEvent) e);
			} else if (e.getEventType() == WiiUseApiEvent.WIIUSE_CLASSIC_CTRL_REMOVED) {
				notifyClassicControllerRemovedEventListeners((ClassicControllerRemovedEvent) e);
			}
			/*
			 * events not managed yet || e.getEventType() == WIIUSE_READ_DATA
			 * WiiUseApiEvent.WIIUSE_CLASSIC_CTRL_INSERTED || e.getEventType() ==
			 * WiiUseApiEvent.WIIUSE_CLASSIC_CTRL_REMOVED || e.getEventType() ==
			 * WiiUseApiEvent.WIIUSE_GUITAR_HERO_3_CTRL_INSERTED ||
			 * e.getEventType() ==
			 * WiiUseApiEvent.WIIUSE_GUITAR_HERO_3_CTRL_REMOVED
			 */
		}
	}

	/**
	 * Add a WiimoteListener to the listeners list.
	 * 
	 * @param listener
	 *            a WiimoteListener
	 */
	public void addWiiMoteEventListeners(WiimoteListener listener) {
		listeners.add(WiimoteListener.class, listener);
	}

	/**
	 * Remove a WiimoteListener from the listeners list.
	 * 
	 * @param listener
	 *            a WiimoteListener
	 */
	public void removeWiiMoteEventListeners(WiimoteListener listener) {
		listeners.remove(WiimoteListener.class, listener);
	}

	/**
	 * Get the list of WiimoteListener.
	 * 
	 * @return the list of WiimoteListener.
	 */
	public WiimoteListener[] getWiiMoteEventListeners() {
		return listeners.getListeners(WiimoteListener.class);
	}

	/**
	 * Notify WiimoteListeners that an event occured. Notify in first the
	 * listeners for Buttons Events. In second the listeners for IR Events. In
	 * third the listeners for Motion sensing events.
	 * 
	 * @param evt
	 *            GenericEvent occured
	 */
	private void notifyWiiMoteEventListeners(WiimoteEvent evt) {
		for (WiimoteListener listener : getWiiMoteEventListeners()) {
			listener.onButtonsEvent(evt.getButtonsEvent());
			if (evt.isThereIrEvent()) {
				listener.onIrEvent(evt.getIREvent());
			}
			if (evt.isThereMotionSensingEvent()) {
				listener.onMotionSensingEvent(evt.getMotionSensingEvent());
			}
			if (evt.isThereExpansionEvent()) {
				listener.onExpansionEvent(evt.getExpansionEvent());
			}
		}
	}

	/**
	 * Notify WiimoteListener that a status event occured.
	 * 
	 * @param evt
	 *            status event occured
	 */
	private void notifyStatusEventListeners(StatusEvent evt) {
		for (WiimoteListener listener : getWiiMoteEventListeners()) {
			listener.onStatusEvent(evt);
		}
	}

	/**
	 * Notify WiimoteListener that a disconnection event occured.
	 * 
	 * @param evt
	 *            disconnection event occured
	 */
	private void notifyDisconnectionEventListeners(DisconnectionEvent evt) {
		for (WiimoteListener listener : getWiiMoteEventListeners()) {
			listener.onDisconnectionEvent(evt);
		}
	}

	/**
	 * Notify WiimoteListener that a NunchukInserted Event occured.
	 * 
	 * @param evt
	 *            NunchukInserted Event occured
	 */
	private void notifyNunchukInsertedEventListeners(NunchukInsertedEvent evt) {
		for (WiimoteListener listener : getWiiMoteEventListeners()) {
			listener.onNunchukInsertedEvent(evt);
		}
	}

	/**
	 * Notify WiimoteListener that a NunchukRemoved Event occured.
	 * 
	 * @param evt
	 *            NunchukRemoved Event occured
	 */
	private void notifyNunchukRemovedEventListeners(NunchukRemovedEvent evt) {
		for (WiimoteListener listener : getWiiMoteEventListeners()) {
			listener.onNunchukRemovedEvent(evt);
		}
	}

	/**
	 * Notify WiimoteListener that a GuitarHeroInserted Event occured.
	 * 
	 * @param evt
	 *            GuitarHeroInserted Event occured
	 */
	private void notifyGuitarHeroInsertedEventListeners(GuitarHeroInsertedEvent evt) {
		for (WiimoteListener listener : getWiiMoteEventListeners()) {
			listener.onGuitarHeroInsertedEvent(evt);
		}
	}

	/**
	 * Notify WiimoteListener that a GuitarHeroRemoved Event occured.
	 * 
	 * @param evt
	 *            GuitarHeroRemoved Event occured
	 */
	private void notifyGuitarHeroRemovedEventListeners(GuitarHeroRemovedEvent evt) {
		for (WiimoteListener listener : getWiiMoteEventListeners()) {
			listener.onGuitarHeroRemovedEvent(evt);
		}
	}
	
	/**
	 * Notify WiimoteListener that a ClassicControllerInserted Event occured.
	 * 
	 * @param evt
	 *            ClassicControllerInserted Event occured
	 */
	private void notifyClassicControllerInsertedEventListeners(ClassicControllerInsertedEvent evt) {
		for (WiimoteListener listener : getWiiMoteEventListeners()) {
			listener.onClassicControllerInsertedEvent(evt);
		}
	}

	/**
	 * Notify WiimoteListener that a ClassicControllerRemoved Event occured.
	 * 
	 * @param evt
	 *            ClassicControllerRemoved Event occured
	 */
	private void notifyClassicControllerRemovedEventListeners(ClassicControllerRemovedEvent evt) {
		for (WiimoteListener listener : getWiiMoteEventListeners()) {
			listener.onClassicControllerRemovedEvent(evt);
		}
	}
	
	@Override
	public String toString() {
		return "Wiimote with ID : " + id;
	}

}
