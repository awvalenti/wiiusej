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
 * Event that represents the connection of a classic controller to a wiimote.
 * 
 * @author guiguito
 * 
 */
public class ClassicControllerInsertedEvent extends WiiUseApiEvent {

	/**
	 * Construct the ClassicControllerInsertedEvent setting up the id.
	 * 
	 * @param id
	 *            id of the wiimote.
	 */
	public ClassicControllerInsertedEvent(int id) {
		super(id, WIIUSE_CLASSIC_CTRL_INSERTED);
	}

	@Override
	public String toString() {
		String out = "";
		/* Status */
		out += "/*********** CLASSIC CONTROLLER INSERTED EVENT : WIIMOTE   ID :"
				+ super.getWiimoteId() + " ********/\n";
		return out;
	}

}
