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

import wiiusej.values.Orientation;
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
 * This panel is used to watch orientation values from a MotionSensingEvent.
 * 
 * @author guiguito
 */
public abstract class OrientationPanel extends javax.swing.JPanel implements
		WiimoteListener {

	private Image mImage;// image for double buffering
	private Color rollColor = Color.RED;
	private Color pitchColor = Color.GREEN;
	private Color yawColor = Color.BLUE;
	private Color backgroundColor = Color.WHITE;
	private Color lineColor = Color.BLACK;
	private ArrayList<Orientation> values = new ArrayList<Orientation>();

	/**
	 * Default constructor. Background color : White. Roll color : Red. Pitch
	 * color : Green. Yaw color : Blue.
	 */
	public OrientationPanel() {
		initComponents();
	}

	/**
	 * Constructor used to choose the colors used by the OrientationPanel.
	 * 
	 * @param bgColor
	 *            background color.
	 * @param rColor
	 *            roll color.
	 * @param pColor
	 *            pitch color.
	 * @param yColor
	 *            yaw color.
	 * @param lColor
	 *            line color.
	 */
	public OrientationPanel(Color bgColor, Color rColor, Color pColor,
			Color yColor, Color lColor) {
		backgroundColor = bgColor;
		rollColor = rColor;
		pitchColor = pColor;
		yawColor = yColor;
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
		double yMiddleFloat = getHeight() / 2.0;
		int yMiddle = (int) Math.round(yMiddleFloat);

		g2.setPaint(lineColor);
		g2.drawLine(0, yMiddle, getWidth(), yMiddle);

		Orientation[] valuesArray = values.toArray(new Orientation[0]);
		double unit = yMiddleFloat / 180.0;
		int previousRoll = 0;
		int previousPitch = 0;
		int previousYaw = 0;
		// draw curves
		for (int i = 0; i < valuesArray.length && i < getWidth(); i++) {
			Orientation orientation = valuesArray[i];
			// draw roll
			g2.setPaint(rollColor);
			int yDelta = (int) Math.round(unit * orientation.getRoll());
			int y = -1 * yDelta + yMiddle;
			g2.drawLine(i - 1, previousRoll, i, y);
			g2.setTransform(new AffineTransform());
			previousRoll = y;
			// draw pitch
			g2.setPaint(pitchColor);
			yDelta = (int) Math.round(unit * orientation.getPitch());
			y = -1 * yDelta + yMiddle;
			g2.drawLine(i - 1, previousPitch, i, y);
			g2.setTransform(new AffineTransform());
			previousPitch = y;
			// draw yaw
			g2.setPaint(yawColor);
			yDelta = (int) Math.round(unit * orientation.getYaw());
			y = -1 * yDelta + yMiddle;
			g2.drawLine(i - 1, previousYaw, i, y);
			g2.setTransform(new AffineTransform());
			previousYaw = y;
		}

		// draw legend
		g2.setPaint(rollColor);
		g2.drawLine(5, getHeight() - 10, 25, getHeight() - 10);
		g2.setPaint(pitchColor);
		g2.drawLine(60, getHeight() - 10, 80, getHeight() - 10);
		g2.setPaint(yawColor);
		g2.drawLine(120, getHeight() - 10, 140, getHeight() - 10);

		g2.setPaint(lineColor);
		g2.drawString("Roll", 30, getHeight() - 5);
		g2.drawString("Pitch", 85, getHeight() - 5);
		g2.drawString("Yaw", 145, getHeight() - 5);
		g2.drawString("0", 2, yMiddle - 5);
		g2.drawString("180", 2, 10);
		g2.drawString("-180", 2, getHeight() - 15);
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
		Orientation orientation = getOrientationValue(arg0);
		if (orientation != null)
			values.add(orientation);
		repaint();
	}

	public abstract Orientation getOrientationValue(GenericEvent e);

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public Color getPitchColor() {
		return pitchColor;
	}

	public Color getRollColor() {
		return rollColor;
	}

	public Color getYawColor() {
		return yawColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public void setPitchColor(Color pitchColor) {
		this.pitchColor = pitchColor;
	}

	public void setRollColor(Color rollColor) {
		this.rollColor = rollColor;
	}

	public void setYawColor(Color yawColor) {
		this.yawColor = yawColor;
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
