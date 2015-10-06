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
package wiiusej.wiiusejevents;

/**
 * Abstract mother class representing an event with a wiimote id.
 * 
 * @author guiguito
 */
public abstract class GenericEvent {
	
	/* ID */
	private int wiimoteId = -1;
	
	/**
	 * Construct the WiiUseApiEvent setting up the id.
	 * 
	 * @param id
	 *            the Wiimote id           
	 */
	public GenericEvent(int id) {
		wiimoteId = id;
	}
	
	/**
	 * Get Wiimote ID
	 * 
	 * @return the wiimote id.
	 */
	public int getWiimoteId() {
		return wiimoteId;
	}
	
	/**
	 * Set Wiimote ID
	 * 
	 * @param wiimoteId
	 *            id of the wiimote
	 */
	void setWiimoteId(int wiimoteId) {
		this.wiimoteId = wiimoteId;
	}
	
	public abstract String toString();
}
