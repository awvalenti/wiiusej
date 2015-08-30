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

import wiiusej.wiiusejevents.GenericEvent;

/**
 * This class describes the structure of an event from the WiiUse API event.
 * 
 * @author guiguito
 */
public abstract class WiiUseApiEvent extends GenericEvent{
	
	public static int NONE_EVENT = 0;
	public static int GENERIC_EVENT = 1;
	public static int STATUS_EVENT = 2;
	public static int DISCONNECTION_EVENT = 3;
	public static int WIIUSE_READ_DATA = 4;
	public static int WIIUSE_NUNCHUK_INSERTED = 5;	
	public static int WIIUSE_NUNCHUK_REMOVED = 6;
	public static int WIIUSE_CLASSIC_CTRL_INSERTED = 7;
	public static int WIIUSE_CLASSIC_CTRL_REMOVED = 8;
	public static int WIIUSE_GUITAR_HERO_3_CTRL_INSERTED = 9;
	public static int WIIUSE_GUITAR_HERO_3_CTRL_REMOVED = 10;
	
	/* Event Type */
	private int eventType;
	
	/**
	 * Construct the WiiUseApiEvent setting up the id.
	 * 
	 * @param id
	 *            the Wiimote id
	 * @param type
	 *            type of the event            
	 */
	public WiiUseApiEvent(int id, int type) {
		super(id);
		eventType = type;
	}		
	
	/**
	 * Get the event type.
	 * @return the eventType
	 */
	public int getEventType() {
		return eventType;
	}
	
	public abstract String toString();	

}
