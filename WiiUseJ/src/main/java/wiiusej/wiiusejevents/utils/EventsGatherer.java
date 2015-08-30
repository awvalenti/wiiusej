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
package wiiusej.wiiusejevents.utils;

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
 * This class is used to gather events during a call to the Wiiuse API.
 * 
 * @author guiguito
 */
public class EventsGatherer {

	private WiiUseApiEvent[] events;
	private int index = 0;
	private WiimoteEvent genericEvent = null;

	/**
	 * Create EventsGatherer.
	 * 
	 * @param nbWiimotes
	 *            nb wiimotes (nb a of events possible in a call to Wiiuse API).
	 */
	public EventsGatherer(int nbWiimotes) {
		events = new WiiUseApiEvent[nbWiimotes];
	}

	/**
	 * Add an event to the array.
	 * 
	 * @param e
	 *            the event to add.
	 */
	private void addEvent(WiiUseApiEvent e) {
		events[index] = e;
		index++;
	}

	/**
	 * Prepare a wiimote event to add.
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
	public void prepareWiiMoteEvent(int id, short buttonsJustPressed,
			short buttonsJustReleased, short buttonsHeld) {
		genericEvent = new WiimoteEvent(id, buttonsJustPressed,
				buttonsJustReleased, buttonsHeld);
	}

	/**
	 * Prepare an IR event to populate.
	 * 
	 * @param x
	 *            calculated X coordinate.
	 * @param y
	 *            calculated Y coordinate.
	 * @param z
	 *            calculated distance.
	 * @param ax
	 *            absolute X coordinate.
	 * @param ay
	 *            absolute Y coordinate.
	 * @param xVRes
	 *            IR virtual screen x resolution.
	 * @param yVRes
	 *            IR virtual screen y resolution.
	 * @param xOffset
	 *            IR X correction offset.
	 * @param yOffset
	 *            IR Y correction offset.
	 * @param sensorBarPostion
	 *            aspect ratio of the screen.
	 * @param screenAsPectRatio
	 *            IR sensor bar position.
	 * @param irSensitivity
	 *            Sensitivity of the infrared camera.
	 * @param distance
	 *            Pixel Distance between first two dots.
	 */
	public void prepareIRevent(int x, int y, float z, int ax, int ay,
			int xVRes, int yVRes, int xOffset, int yOffset,
			short sensorBarPostion, short screenAsPectRatio,
			short irSensitivity, float distance) {
		genericEvent.prepareIRevent(x, y, z, ax, ay, xVRes, yVRes, xOffset,
				yOffset, sensorBarPostion, screenAsPectRatio, irSensitivity,
				distance);

	}

	/**
	 * Add an IR point to the WiiMoteEvent prepared.
	 * 
	 * @param x
	 *            x coordinates.
	 * @param y
	 *            y coordinates.
	 * @param rx
	 *            raw X coordinate (0-1023).
	 * @param ry
	 *            raw Y coordinate (0-1023).
	 * @param size
	 *            size of the IR dot (0-15).
	 */
	public void addIRPointToPreparedWiiMoteEvent(int x, int y, short rx,
			short ry, short size) {
		if (genericEvent != null) {
			genericEvent.addIRpoint(x, y, rx, ry, size);
		}
	}

	/**
	 * Set orientation and gravity force of the prepared event.
	 * 
	 * @param orientationThreshold
	 *            value of the minimum angle between two events with the
	 *            accelerometer.
	 * @param accelerationThreshold
	 *            value of the value variation between two events with the
	 *            accelerometer.
	 * @param smoothingState
	 *            true if smoothing flag is activated.
	 * @param alphaSmooth
	 *            value of the alpha smoothing parameter.
	 * @param r
	 *            roll.
	 * @param p
	 *            pitch.
	 * @param ya
	 *            yaw.
	 * @param ar
	 *            absolute roll.
	 * @param ap
	 *            absolute pitch.
	 * @param x
	 *            gravity force on x axis.
	 * @param y
	 *            gravity force on y axis.
	 * @param z
	 *            gravity force on z axis.
	 * @param xx
	 *            raw acceleration on x axis.
	 * @param yy
	 *            raw acceleration on y axis.
	 * @param zz
	 *            raw acceleration on z axis.
	 */
	public void addMotionSensingValues(float orientationThreshold,
			int accelerationThreshold, boolean smoothingState,
			float alphaSmooth, float r, float p, float ya, float ar, float ap,
			float x, float y, float z, short xx, short yy, short zz) {
		if (genericEvent != null) {
			genericEvent.setMotionSensingEvent(orientationThreshold,
					accelerationThreshold, smoothingState, alphaSmooth, r, p,
					ya, ar, ap, x, y, z, xx, yy, zz);
		}
	}

	/**
	 * Set a NunchukEvent to the prepared WiimoteEvent.
	 * 
	 * @param buttonsJustPressed
	 *            buttons just pressed.
	 * @param buttonsJustReleased
	 *            buttons just released.
	 * @param buttonsHeld
	 *            buttons just pressed.
	 * @param orientationThreshold
	 *            value of the minimum angle between two events with the
	 *            accelerometer.
	 * @param accelerationThreshold
	 *            value of the value variation between two events with the
	 *            accelerometer.
	 * @param smoothingState
	 *            true if smoothing flag is activated.
	 * @param alphaSmooth
	 *            value of the alpha smoothing parameter.
	 * @param r
	 *            roll.
	 * @param p
	 *            pitch.
	 * @param ya
	 *            yaw.
	 * @param ar
	 *            absolute roll.
	 * @param ap
	 *            absolute pitch.
	 * @param x
	 *            gravity force on x axis.
	 * @param y
	 *            gravity force on y axis.
	 * @param z
	 *            gravity force on z axis.
	 * @param xx
	 *            raw acceleration on x axis.
	 * @param yy
	 *            raw acceleration on y axis.
	 * @param zz
	 *            raw acceleration on z axis.
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
	public void addNunchunkEventToPreparedWiimoteEvent(
			short buttonsJustPressed, short buttonsJustReleased,
			short buttonsHeld, float orientationThreshold,
			int accelerationThreshold, boolean smoothingState,
			float alphaSmooth, float r, float p, float ya, float ar, float ap,
			float x, float y, float z, short xx, short yy, short zz,
			float angle, float magnitude, short max1, short max2, short min1,
			short min2, short center1, short center2) {
		if (genericEvent != null) {
			genericEvent.setNunchukEvent(buttonsJustPressed,
					buttonsJustReleased, buttonsHeld, orientationThreshold,
					accelerationThreshold, smoothingState, alphaSmooth, r, p,
					ya, ar, ap, x, y, z, xx, yy, zz, angle, magnitude, max1,
					max2, min1, min2, center1, center2);
		}
	}

	/**
	 * Set a GuitarHeroEvent to the prepared WiimoteEvent.
	 * 
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
	public void addGuitarHeroEventToPreparedWiimoteEvent(
			short buttonsJustPressed, short buttonsJustReleased,
			short buttonsHeld, float whammyBar, float angle, float magnitude,
			short max1, short max2, short min1, short min2, short center1,
			short center2) {
		if (genericEvent != null) {
			genericEvent.setGuitarHeroEvent(buttonsJustPressed,
					buttonsJustReleased, buttonsHeld, whammyBar, angle,
					magnitude, max1, max2, min1, min2, center1, center2);
		}
	}

	/**
	 * Set a ClassicControllerEvent to the prepared WiimoteEvent.
	 * 
	 * @param buttonsJustPressed
	 *            buttons just pressed.
	 * @param buttonsJustReleased
	 *            buttons just released.
	 * @param buttonsHeld
	 *            buttons just pressed.
	 * @param rightShoulder
	 *            right shoulder button (range 0-1).
	 * @param leftShoulder
	 *            left shoulder button (range 0-1).
	 * @param langle
	 *            angle the left joystick is being held.
	 * @param lmagnitude
	 *            magnitude of the left joystick (range 0-1).
	 * @param lmax1
	 *            maximum left joystick value 1.
	 * @param lmax2
	 *            maximum left joystick value 2.
	 * @param lmin1
	 *            minimum left joystick value 1.
	 * @param lmin2
	 *            minimum left joystick value 2.
	 * @param lcenter1
	 *            center left joystick value 1.
	 * @param lcenter2
	 *            center left joystick value 2.
	 * @param rangle
	 *            angle the right joystick is being held.
	 * @param rmagnitude
	 *            magnitude of the right joystick (range 0-1).
	 * @param rmax1
	 *            maximum right joystick value 1.
	 * @param rmax2
	 *            maximum right joystick value 2.
	 * @param rmin1
	 *            minimum right joystick value 1.
	 * @param rmin2
	 *            minimum right joystick value 2.
	 * @param rcenter1
	 *            center right joystick value 1.
	 * @param rcenter2
	 *            center right joystick value 2.
	 */
	public void addClassicControllerEventToPreparedWiimoteEvent(short buttonsJustPressed,
			short buttonsJustReleased, short buttonsHeld, float rightShoulder,
			float leftShoulder, float langle, float lmagnitude, short lmax1,
			short lmax2, short lmin1, short lmin2, short lcenter1,
			short lcenter2, float rangle, float rmagnitude, short rmax1,
			short rmax2, short rmin1, short rmin2, short rcenter1,
			short rcenter2) {
		if (genericEvent != null) {
			genericEvent.setClassicControllerEvent(buttonsJustPressed,
					buttonsJustReleased, buttonsHeld, rightShoulder,
					leftShoulder, langle, lmagnitude, lmax1, lmax2, lmin1,
					lmin2, lcenter1, lcenter2, rangle, rmagnitude, rmax1,
					rmax2, rmin1, rmin2, rcenter1, rcenter2);
		}
	}

	/**
	 * Add the prepared WiimoteEvent to the gatherer.
	 */
	public void addWiimoteEvent() {
		if (genericEvent != null) {
			addEvent(genericEvent);
			genericEvent = null;
		}
	}

	/**
	 * Add a StatusEvent to the gatherer.
	 * 
	 * @param id
	 *            id of the wiimote.
	 * @param connect
	 *            true if the wiimote is connected.
	 * @param batt
	 *            battery level.
	 * @param led
	 *            status of leds.
	 * @param speak
	 *            speakers status.
	 * @param attach
	 *            attachment status.
	 * @param rumbleState
	 *            true if rumble is active.
	 * @param continuousState
	 *            true if continuous flag is activated.
	 * @param irState
	 *            true if ir is active.
	 * @param motionSensingState
	 *            true if accelerometer is active.
	 */
	public void addStatusEvent(int id, boolean connect, float batt, short led,
			boolean speak, int attach, boolean rumbleState,
			boolean continuousState, boolean irState, boolean motionSensingState) {
		StatusEvent evt = new StatusEvent(id, connect, batt, led, speak,
				attach, rumbleState, continuousState, irState,
				motionSensingState);
		addEvent(evt);
	}

	/**
	 * Add a DisconnectionEvent to the gatherer.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	public void addDisconnectionEvent(int id) {
		DisconnectionEvent evt = new DisconnectionEvent(id);
		addEvent(evt);
	}

	/**
	 * Add a NunchukInsertedEvent to the gatherer.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	public void addNunchukInsertedEvent(int id) {
		NunchukInsertedEvent evt = new NunchukInsertedEvent(id);
		addEvent(evt);
	}

	/**
	 * Add a NunchukRemovedEvent to the gatherer.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	public void addNunchukRemovedEvent(int id) {
		NunchukRemovedEvent evt = new NunchukRemovedEvent(id);
		addEvent(evt);
	}

	/**
	 * Add a GuitarHeroInsertedEvent to the gatherer.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	public void addGuitarHeroInsertedEvent(int id) {
		GuitarHeroInsertedEvent evt = new GuitarHeroInsertedEvent(id);
		addEvent(evt);
	}

	/**
	 * Add a GuitarHeroRemovedEvent to the gatherer.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	public void addGuitarHeroRemovedEvent(int id) {
		GuitarHeroRemovedEvent evt = new GuitarHeroRemovedEvent(id);
		addEvent(evt);
	}

	/**
	 * Add a ClassicControllerInsertedEvent to the gatherer.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	public void addClassicControllerInsertedEvent(int id) {
		ClassicControllerInsertedEvent evt = new ClassicControllerInsertedEvent(
				id);
		addEvent(evt);
	}

	/**
	 * Add a ClassicControllerRemovedEvent to the gatherer.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	public void addClassicControllerRemovedEvent(int id) {
		ClassicControllerRemovedEvent evt = new ClassicControllerRemovedEvent(
				id);
		addEvent(evt);
	}

	/**
	 * Return an array containing the events.
	 * 
	 * @return events received.
	 */
	public WiiUseApiEvent[] getEvents() {
		WiiUseApiEvent[] ev = new WiiUseApiEvent[index];
        System.arraycopy(events, 0, ev, 0, index);
        return ev;
	}

	/**
	 * Clear the gatherer and remove objects.
	 */
	public void clearEvents() {
		for (int i = 0; i < events.length; i++) {
			events[i] = null;
		}
		genericEvent = null;
		index = 0;
	}

}
