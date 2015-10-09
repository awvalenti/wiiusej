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

import wiiusej.wiiusejevents.wiiuseapievents.WiiUseApiEvent;

/**
 * This is the interface to implement to listen to events from the wiiuse API.
 * 
 * @author guiguito
 */
public interface WiiUseApiListener extends java.util.EventListener {

	/**
	 * Method called when a WiiUseApiEvent occurs. A WiiUseApiEvent can be : -
	 * WiimoteEvent (Storing ButtonsEvent and eventually IREvent and
	 * MotionSensingEvent) - StatusEvent - DisconnectionEvent
	 * 
	 * @param e
	 */
	void onWiiUseApiEvent(WiiUseApiEvent e);

}
