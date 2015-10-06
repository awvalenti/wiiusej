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
package wiiusej.wiiusejevents.physicalevents;

import wiiusej.wiiusejevents.GenericEvent;

/**
 * Mother Class of all expansions event.
 * 
 * @author guiguito
 */
public abstract class ExpansionEvent extends GenericEvent {

	/**
	 * Constructor of an ExpansionEvent.
	 * 
	 * @param id
	 *            id of the wiimote to which the expansion is connected.
	 */
	public ExpansionEvent(int id) {
		super(id);
	}
	
	public abstract String toString();

}
