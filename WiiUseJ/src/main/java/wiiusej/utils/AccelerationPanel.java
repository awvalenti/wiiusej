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
package wiiusej.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import wiiusej.values.RawAcceleration;
import wiiusej.wiiusejevents.GenericEvent;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;

/**
 * This panel is used to watch raw acceleration values from a
 * MotionSensingEvent.
 * 
 * @author guiguito
 */
public abstract class AccelerationPanel extends javax.swing.JPanel implements
		WiimoteListener {

	private Image mImage;// image for double buffering
	private Color xColor = Color.RED;
	private Color yColor = Color.GREEN;
	private Color zColor = Color.BLUE;
	private Color backgroundColor = Color.WHITE;
	private Color lineColor = Color.BLACK;
	private ArrayList<RawAcceleration> values = new ArrayList<RawAcceleration>();

	/** Creates new form AccelerationPanel */
	public AccelerationPanel() {
		initComponents();
	}

	/**
	 * Constructor used to choose the colors used by the AccelerationPanel.
	 * 
	 * @param bgColor
	 *            background color.
	 * @param xColor
	 *            x color.
	 * @param yColor
	 *            y color.
	 * @param zColor
	 *            z color.
	 * @param lColor
	 *            line color.
	 */
	public AccelerationPanel(Color bgColor, Color xColor, Color yColor,
			Color zColor, Color lColor) {
		backgroundColor = bgColor;
		this.xColor = xColor;
		this.yColor = yColor;
		this.zColor = zColor;
		lineColor = lColor;
		initComponents();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension d = getSize();
		checkOffScreenImage();
		Graphics offG = mImage.getGraphics();
		offG.setColor(backgroundColor);
		offG.fillRect(0, 0, d.width, d.height);
		Graphics2D g2 = (Graphics2D) mImage.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// draw medium line
		int yLine = getHeight() - 25;

		g2.setPaint(lineColor);
		g2.drawLine(0, yLine, getWidth(), yLine);

		RawAcceleration[] valuesArray = values.toArray(new RawAcceleration[0]);

		double unit = yLine / 255.0;
		int previousX = 0;
		int previousY = 0;
		int previousZ = 0;
		// draw curves
		for (int i = 0; i < valuesArray.length && i < getWidth(); i++) {
			RawAcceleration acceleration = valuesArray[i];
			// draw X
			g2.setPaint(xColor);
			int yDelta = (int) Math.round(unit * acceleration.getX());
			int y = -1 * yDelta + yLine;
			g2.drawLine(i - 1, previousX, i, y);
			g2.setTransform(new AffineTransform());
			previousX = y;
			// draw Y
			g2.setPaint(yColor);
			yDelta = (int) Math.round(unit * acceleration.getY());
			y = -1 * yDelta + yLine;
			g2.drawLine(i - 1, previousY, i, y);
			g2.setTransform(new AffineTransform());
			previousY = y;
			// draw Z
			g2.setPaint(zColor);
			yDelta = (int) Math.round(unit * acceleration.getZ());
			y = -1 * yDelta + yLine;
			g2.drawLine(i - 1, previousZ, i, y);
			g2.setTransform(new AffineTransform());
			previousZ = y;
		}

		// draw legend
		g2.setPaint(xColor);
		g2.drawLine(5, getHeight() - 10, 25, getHeight() - 10);
		g2.setPaint(yColor);
		g2.drawLine(60, getHeight() - 10, 80, getHeight() - 10);
		g2.setPaint(zColor);
		g2.drawLine(120, getHeight() - 10, 140, getHeight() - 10);

		g2.setPaint(lineColor);
		g2.drawString("X", 30, getHeight() - 5);
		g2.drawString("Y", 85, getHeight() - 5);
		g2.drawString("Z", 145, getHeight() - 5);
		g2.drawString("0", 2, yLine - 5);
		g2.drawString("255", 2, 15);
		// put offscreen image on the screen
		g.drawImage(mImage, 0, 0, null);
	}

	/**
	 * check if the mImage variable has been initialized. If it's not the case
	 * it initializes it with the dimensions of the panel. mImage is for double
	 * buffering.
	 */
	private void checkOffScreenImage() {
		Dimension d = getSize();
		if (mImage == null || mImage.getWidth(null) != d.width
				|| mImage.getHeight(null) != d.height) {
			mImage = createImage(d.width, d.height);
		}
	}

	public void onButtonsEvent(WiimoteButtonsEvent arg0) {
		// nothing
	}

	public void onIrEvent(IREvent arg0) {
		// nothing
	}

	public void onMotionSensingEvent(MotionSensingEvent arg0) {
		draw(arg0);
	}

	public void onExpansionEvent(ExpansionEvent arg0) {
		draw(arg0);
	}

	public void onStatusEvent(StatusEvent arg0) {
		// nothing
	}

	public void onDisconnectionEvent(DisconnectionEvent arg0) {
		// Clear points.
		values.clear();
		repaint();
	}

	public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {
		// nothing
	}

	public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {
		// nothing
	}
	
	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {
		// nothing
	}

	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {
		// nothing
	}

	public void onClassicControllerInsertedEvent(
			ClassicControllerInsertedEvent arg0) {
		// nothing
	}

	public void onClassicControllerRemovedEvent(
			ClassicControllerRemovedEvent arg0) {
		// nothing
	}

	private void draw(GenericEvent arg0) {
		if (values.size() >= getWidth()) {
			// if there are as many values as pixels in the width
			// clear points
			values.clear();
		}
		RawAcceleration rawAcceleration = getRawAccelerationValue(arg0);
		if (rawAcceleration != null)
			values.add(rawAcceleration);
		repaint();
	}

	public abstract RawAcceleration getRawAccelerationValue(GenericEvent e);

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public Color getXColor() {
		return xColor;
	}

	public Color getYColor() {
		return yColor;
	}

	public Color getZColor() {
		return zColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public void setXColor(Color xColor) {
		this.xColor = xColor;
	}

	public void setYColor(Color yColor) {
		this.yColor = yColor;
	}

	public void setZColor(Color zColor) {
		this.zColor = zColor;
	}

	public void clearView() {
		values.clear();
		repaint();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300,
				Short.MAX_VALUE));
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	// End of variables declaration//GEN-END:variables
}
