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
package wiiusej.values;

/**
 * Class used for IR sources.
 * 
 * @author guiguito
 */
public class IRSource {

	private int x;
	private int y;
	private short rx;
	private short ry;
	private short size;

	/**
	 * Build an IR source with all details.
	 * 
	 * @param xx
	 *            xx interpolated coordinates.
	 * @param yy
	 *            yy interpolated coordinates.
	 * @param rxx
	 *            raw X coordinate (0-1023).
	 * @param ryy
	 *            raw Y coordinate (0-1023).
	 * @param si
	 *            size of the IR dot (0-15).
	 */
	public IRSource(int xx, int yy, short rxx, short ryy, short si) {
		x = xx;
		y = yy;
		rx = rxx;
		ry = ryy;
		size = si;
	}

	/**
	 * Return x interpolated coordinates.
	 * 
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Return y interpolated coordinates.
	 * 
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Return raw X coordinate (0-1023).
	 * 
	 * @return the rx
	 */
	public short getRx() {
		return rx;
	}

	/**
	 * Return raw Y coordinate (0-1023).
	 * 
	 * @return the ry
	 */
	public short getRy() {
		return ry;
	}

	/**
	 * Return size of the IR dot (0-15).
	 * 
	 * @return the size
	 */
	public short getSize() {
		return size;
	}

	@Override
	public String toString() {
		return "Interpolated coordinates (" + x + "," + y
				+ "), Raw coordinates(" + rx + "," + ry + "), source size : "
				+ size + ")";
	}

}
