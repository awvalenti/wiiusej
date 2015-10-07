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
 * This panel is used to see what the IR camera of the wiimote sees.
 * 
 * @author guiguito
 */
public class IRPanel extends javax.swing.JPanel implements WiimoteListener {

	private static int MAX_NB_POINTS = 4;
	private Color color = Color.YELLOW;
	private Color backgroundColor = Color.BLACK;
	private Color borderColor = Color.BLUE;
	private Shape shape;
	private Image mImage;// image for double buffering
	private int[] xCoordinates;
	private int[] yCoordinates;
	private int nbPoints = -1;

	/**
	 * Default constructor for IR Panel. Background color : black. IR sources
	 * color : yellow. Border color of IR sources : blue. Shape of the IR
	 * sources : circle with a diameter of 10.
	 */
	public IRPanel() {
		shape = new java.awt.geom.Ellipse2D.Double(0, 0, 10, 10);
		initArrays();
		initComponents();
	}

	/**
	 * Constructor used to parameterize the IR panel.
	 * 
	 * @param bgColor
	 *            color.
	 * @param ptColor
	 *            IR sources color.
	 * @param bdColor
	 *            border color of IR sources.
	 * @param sh
	 *            Shape of the IR sources.
	 */
	public IRPanel(Color bgColor, Color ptColor, Color bdColor, Shape sh) {
		backgroundColor = bgColor;
		color = ptColor;
		borderColor = bdColor;
		shape = sh;
		initArrays();
		initComponents();
	}

	private void initArrays() {
		xCoordinates = new int[MAX_NB_POINTS];
		yCoordinates = new int[MAX_NB_POINTS];
		for (int i = 0; i < MAX_NB_POINTS; i++) {
			xCoordinates[i] = -1;
			yCoordinates[i] = -1;
		}
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

		// draw points
		int i = 0;
		while (i < nbPoints) {
			double x = xCoordinates[i];
			double y = yCoordinates[i];

			long xx = getWidth() - Math.round((double) getWidth() * x / 1024.0);
			long yy = getHeight()
					- Math.round((double) getHeight() * y / 768.0);
			g2.translate(xx, yy);

			g2.setPaint(borderColor);
			g2.draw(shape);
			g2.setPaint(color);
			g2.fill(shape);

			g2.setTransform(new AffineTransform());
			i++;
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
		repaint();
	}

	public void onIrEvent(IREvent arg0) {
		// transfer points
		wiiusej.values.IRSource[] points = arg0.getIRPoints();
		nbPoints = points.length;
		for (int i = 0; i < points.length; i++) {
			xCoordinates[i] = (int) points[i].getRx();
			yCoordinates[i] = (int) points[i].getRy();
		}
		for (int i = points.length; i < MAX_NB_POINTS; i++) {
			xCoordinates[i] = -1;
			yCoordinates[i] = -1;
		}

		// redraw panel
		repaint();
	}

	public void onMotionSensingEvent(MotionSensingEvent arg0) {
		// nothing
	}

	public void onExpansionEvent(ExpansionEvent e) {
		// nothing
	}

	public void onStatusEvent(StatusEvent arg0) {
		// nothing
	}

	public void onDisconnectionEvent(DisconnectionEvent arg0) {
		// clear previous points
		for (int i = 0; i < MAX_NB_POINTS; i++) {
			xCoordinates[i] = -1;
			yCoordinates[i] = -1;
		}
		// redraw panel
		repaint();
	}

	public void onNunchukInsertedEvent(NunchukInsertedEvent e) {
		// nothing
	}

	public void onNunchukRemovedEvent(NunchukRemovedEvent e) {
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

	public Color getBorderColor() {
		return borderColor;
	}

	public Color getColor() {
		return color;
	}

	public Shape getShape() {
		return shape;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public void clearView() {
		initArrays();
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
