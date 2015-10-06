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
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.JoystickEvent;
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
 * Panel to display joystick events.
 * 
 * @author guiguito
 */
public abstract class JoystickEventPanel extends javax.swing.JPanel implements
		WiimoteListener {

	private Image mImage;// image for double buffering
	private Color backgroundColor = Color.BLACK;
	private Color borderColor = Color.RED;
	private Color pointColor = Color.RED;
	private Shape shape = new java.awt.geom.Ellipse2D.Double(0, 0, 30, 30);
	private JoystickEvent lastJoystickEvent = null;

	/** Creates new form JoystickPanel */
	public JoystickEventPanel() {
		initComponents();
	}

	/**
	 * Constructor used to choose the colors used by the JoystickPanel.
	 * 
	 * @param bgColor
	 *            background color.
	 * @param pColor
	 *            point color.
	 * @param bdColor
	 *            border color for the shape.
	 * @param sh
	 *            shape of what is drawn.
	 */
	public JoystickEventPanel(Color bgColor, Color pColor, Color bdColor,
			Shape sh) {
		backgroundColor = bgColor;
		pointColor = pColor;
		shape = sh;
		borderColor = bdColor;
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
		g2.setTransform(new AffineTransform());

		// compute center
		int xCenter = (int) Math.round(d.getWidth() / 2.0);
		int yCenter = (int) Math.round(d.getHeight() / 2.0);

		// compute coordinates
		if (lastJoystickEvent != null) {
			double xAng = Math.sin(lastJoystickEvent.getAngle() * Math.PI
					/ 180.0)
					* lastJoystickEvent.getMagnitude();
			double yAng = Math.cos(lastJoystickEvent.getAngle() * Math.PI
					/ 180.0)
					* lastJoystickEvent.getMagnitude();
			int halfWidth = (int) Math.round(shape.getBounds().getWidth() / 2);
			int halHeight = (int) Math.round(shape.getBounds().getHeight() / 2);
			int xAmplitude = (int) Math.round(xCenter - shape.getBounds().getWidth());
			int yAmplitude = (int) Math.round(xCenter - shape.getBounds().getHeight());
			int xShift = (int) Math.round(xAng * xAmplitude);
			int yShift = (int) Math.round(yAng * yAmplitude);
			int x = xCenter + xShift - halfWidth;
			int y = yCenter - yShift - halHeight;
			// shape
			g2.translate(x, y);
			g2.setPaint(borderColor);
			g2.draw(shape);
			g2.setPaint(pointColor);
			g2.fill(shape);
		}
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
		// nothing
	}

	public void onExpansionEvent(ExpansionEvent arg0) {
		JoystickEvent joy = getJoystickEvent(arg0);
		if (joy != null) {
			lastJoystickEvent = joy;
		}
		repaint();
	}

	public void onStatusEvent(StatusEvent arg0) {
		// nothing
	}

	public void onDisconnectionEvent(DisconnectionEvent arg0) {
		// nothing
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

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Color getPointColor() {
		return pointColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public Shape getShape() {
		return shape;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setPointColor(Color pointColor) {
		this.pointColor = pointColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public abstract JoystickEvent getJoystickEvent(ExpansionEvent e);

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
