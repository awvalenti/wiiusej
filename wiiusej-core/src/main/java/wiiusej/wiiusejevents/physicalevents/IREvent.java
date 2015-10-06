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

import wiiusej.values.IRSource;
import wiiusej.wiiusejevents.GenericEvent;

/**
 * Class which represents an IR event.
 * 
 * @author guiguito
 */
public class IREvent extends GenericEvent {

	/* IR Tracking */
	private IRSource[] IRPoints;
	private short indexPoints = 0;
	private int x;
	private int y;
	private float z;// distance from the sensor bar
	private int ax;
	private int ay;
	private int xVRes;
	private int yVRes;
	private int xOffset;
	private int yOffset;
	private short sensorBarPostion;
	private short screenAsPectRatio;
	private short irSensitivity;
	private float distance;

	static private short WIIUSE_IR_ABOVE = 0;
	static private short WIIUSE_IR_BELOW = 1;
	static private short WIIUSE_SCREEN_RATIO_4_3 = 0;
	static private short WIIUSE_SCREEN_RATIO_16_9 = 1;

	private static short NB_POINTS = 4;// number of points IR can track

	/**
	 * Constructor of IREvent with full infos.
	 * 
	 * @param id
	 *            d of the wiimote concerned.
	 * @param x
	 *            calculated X coordinate.
	 * @param y
	 *            calculated Y coordinate.
	 * @param z
	 *            calculated distance.
	 * @param ax
	 *            absolute X coordinate.
	 * @param ay
	 *            absolute Y coordinate
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
	 *            Pixel Distance between first two dots
	 */
	public IREvent(int id, int x, int y, float z, int ax, int ay, int xVRes,
			int yVRes, int xOffset, int yOffset, short sensorBarPostion,
			short screenAsPectRatio, short irSensitivity, float distance) {
		super(id);
		this.x = x;
		this.y = y;
		this.z = z;
		this.ax = ax;
		this.ay = ay;
		this.xVRes = xVRes;
		this.yVRes = yVRes;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.sensorBarPostion = sensorBarPostion;
		this.screenAsPectRatio = screenAsPectRatio;
		this.irSensitivity = irSensitivity;
		this.distance = distance;
		IRPoints = new IRSource[NB_POINTS];
	}

	/**
	 * Get list of IR points.
	 * 
	 * @return the list of 2D points
	 */
	public IRSource[] getIRPoints() {
		IRSource[] ir = new IRSource[indexPoints];
        System.arraycopy(IRPoints, 0, ir, 0, indexPoints);
        return ir;
	}

	/**
	 * Add IR Point in the list (Max 4 points)
	 * 
	 * @param x
	 *            x value
	 * @param y
	 *            y value
	 * @param rx
	 *            raw X coordinate (0-1023).
	 * @param ry
	 *            raw Y coordinate (0-1023).
	 * @param size
	 *            size of the IR dot (0-15).
	 */
	public void addIRpoint(int x, int y, short rx, short ry, short size) {
		IRPoints[indexPoints] = new IRSource(x, y, rx, ry, size);
		indexPoints++;
		return;
	}

	/**
	 * Return calculated X coordinate.
	 * 
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Return calculated Y coordinate.
	 * 
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Return calculated distance.
	 * 
	 * @return the z
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Return absolute X coordinate.
	 * 
	 * @return the ax
	 */
	public int getAx() {
		return ax;
	}

	/**
	 * Return absolute Y coordinate.
	 * 
	 * @return the ay
	 */
	public int getAy() {
		return ay;
	}

	/**
	 * Return IR virtual screen x resolution.
	 * 
	 * @return the xVRes
	 */
	public int getXVRes() {
		return xVRes;
	}

	/**
	 * Return IR virtual screen y resolution.
	 * 
	 * @return the yVRes
	 */
	public int getYVRes() {
		return yVRes;
	}

	/**
	 * Return IR X correction offset.
	 * 
	 * @return the xOffset
	 */
	public int getXOffset() {
		return xOffset;
	}

	/**
	 * Return IR Y correction offset.
	 * 
	 * @return the yOffset
	 */
	public int getYOffset() {
		return yOffset;
	}

	/**
	 * Return true if the sensor bar is above.
	 * 
	 * @return true if the sensor bar is above.
	 */
	public boolean isSensorBarAbove() {
		return sensorBarPostion == WIIUSE_IR_ABOVE;
	}

	/**
	 * Return true if the sensor bar is below.
	 * 
	 * @return true if the sensor bar is below.
	 */
	public boolean isSensorBarBelow() {
		return sensorBarPostion == WIIUSE_IR_BELOW;
	}

	/**
	 * Return true if screen aspect ratio set is 4/3.
	 * 
	 * @return true if screen aspect ratio set is 4/3.
	 */
	public boolean isScreenAspectRatio43() {
		return screenAsPectRatio == WIIUSE_SCREEN_RATIO_4_3;
	}

	/**
	 * Return true if screen aspect ratio set is 16/9.
	 * 
	 * @return true if screen aspect ratio set is 16/9.
	 */
	public boolean isScreenAspectRatio169() {
		return screenAsPectRatio == WIIUSE_SCREEN_RATIO_16_9;
	}

	/**
	 * Return aspect ratio of the screen.
	 * 
	 * @return the screenAsPectRatio
	 */
	public short getScreenAsPectRatio() {
		return screenAsPectRatio;
	}

	/**
	 * The sensitivity of the IR camera can be turned up or down depending on
	 * your needs. Like the Wii, wiiusej and wiiuse can set the camera
	 * sensitivity to a degree between 1 (lowest) and 5 (highest). The default
	 * is 3.
	 * 
	 * @return the irSensitivity
	 */
	public short getIrSensitivity() {
		return irSensitivity;
	}

	/**
	 * Pixel distance between first 2 dots.
	 * 
	 * @return the distance between first 2 dots.
	 */
	public float getDistance() {
		return distance;
	}

	@Override
	public String toString() {
		String out = "";
		/* Display IR Tracking */
		out += "/******** IR Tracking ********/\n";
		out += "--- Active : true\n";
		out += "--- calculated X coordinate : " + x + "\n";
		out += "--- calculated Y coordinate : " + y + "\n";
		out += "--- calculated Z coordinate : " + z + "\n";
		out += "--- calculated distance : " + distance + "\n";
		out += "--- absolute X coordinate : " + ax + "\n";
		out += "--- absolute Y coordinate : " + ay + "\n";
		out += "--- IR virtual screen x resolution : " + xVRes + "\n";
		out += "--- IR virtual screen y resolution : " + yVRes + "\n";
		out += "--- IR X correction offset : " + xOffset + "\n";
		out += "--- IR Y correction offset : " + yOffset + "\n";
		out += "--- IR Sensitivity (between 1-5) : " + irSensitivity + "\n";
		if (isScreenAspectRatio43()) {
			out += "--- aspect ratio of the screen : 4/3\n";
		} else if (isScreenAspectRatio169()) {
			out += "--- aspect ratio of the screen : 16/9\n";
		}
		if (isSensorBarAbove()) {
			out += "--- IR sensor bar position. : Above\n";
		} else if (isSensorBarBelow()) {
			out += "--- IR sensor bar position. : Below\n";
		}		
		out += "--- Seen points\n";
		for (int i = 0; i < IRPoints.length; i++) {
			if (IRPoints[i] != null) {
				out += IRPoints[i].toString();
			}
		}
		out += "\n";
		return out;
	}
}
