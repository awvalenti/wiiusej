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
import java.awt.Toolkit;
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
 * This panel is used to see what buttons are pressed on the wiimote. It
 * displays the result of last ButtonsEvent.
 * 
 * @author guiguito
 */
public class ButtonsEventPanel extends javax.swing.JPanel implements
		WiimoteListener {

	private Image mImage;// image for double buffering
	private Image wiimoteImage;// image for double buffering
	private WiimoteButtonsEvent buttons;
	private Color pressedColor = Color.RED;
	private Color heldColor = Color.ORANGE;
	private Color releasedColor = Color.YELLOW;
	private Shape shape = new java.awt.geom.Ellipse2D.Double(0, 0, 13, 13);

	/**
	 * Default constructor. Red : button just pressed. Orange : button held.
	 * Yellow : button just released.
	 */
	public ButtonsEventPanel() {
		Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		java.net.URL url = ButtonsEventPanel.class
				.getResource("/img/wiimote.png");
		wiimoteImage = toolkit.createImage(url);
		initComponents();
	}

	/**
	 * Constructor used to set colors and shape used.
	 * 
	 * @param pressColor
	 *            color of a button just pressed.
	 * @param hColor
	 *            color of a button held.
	 * @param relColor
	 *            color of a button just released.
	 * @param sh
	 *            shape draw on the buttons.
	 */
	public ButtonsEventPanel(Color pressColor, Color hColor, Color relColor,
			Shape sh) {
		pressedColor = pressColor;
		heldColor = hColor;
		releasedColor = relColor;
		shape = sh;
		Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		wiimoteImage = toolkit.createImage("img\\wiimote.png");		
		initComponents();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension d = getSize();
		checkOffScreenImage();
		Graphics offG = mImage.getGraphics();
		// offG.setColor(backgroundColor);
		offG.fillRect(0, 0, d.width, d.height);
		Graphics2D g2 = (Graphics2D) mImage.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// draw buttons pushed
		g2.drawImage(wiimoteImage, 0, 0, this);
		g2.setTransform(new AffineTransform());

		if (buttons != null) {
			/* button ONE */
			if (buttons.isButtonOneJustPressed()) {
				drawFunction(g2, pressedColor, 53, 353);
			}
			if (buttons.isButtonOneHeld()) {
				drawFunction(g2, heldColor, 53, 353);
			}
			if (buttons.isButtonOneJustReleased()) {
				drawFunction(g2, releasedColor, 53, 353);
			}

			/* button TWO */
			if (buttons.isButtonTwoJustPressed()) {
				drawFunction(g2, pressedColor, 53, 395);
			}
			if (buttons.isButtonTwoHeld()) {
				drawFunction(g2, heldColor, 53, 395);
			}
			if (buttons.isButtonTwoJustReleased()) {
				drawFunction(g2, releasedColor, 53, 395);
			}

			/* button A */
			if (buttons.isButtonAJustPressed()) {
				drawFunction(g2, pressedColor, 53, 150);
			}
			if (buttons.isButtonAHeld()) {
				drawFunction(g2, heldColor, 53, 150);
			}
			if (buttons.isButtonAJustReleased()) {
				drawFunction(g2, releasedColor, 53, 150);
			}

			/* button B */
			if (buttons.isButtonBJustPressed()) {
				drawFunction(g2, pressedColor, 16, 149);
			}
			if (buttons.isButtonBHeld()) {
				drawFunction(g2, heldColor, 16, 149);
			}
			if (buttons.isButtonBJustReleased()) {
				drawFunction(g2, releasedColor, 16, 149);
			}

			/* button LEFT */
			if (buttons.isButtonLeftJustPressed()) {
				drawFunction(g2, pressedColor, 33, 77);
			}
			if (buttons.isButtonLeftHeld()) {
				drawFunction(g2, heldColor, 33, 77);
			}
			if (buttons.isButtonLeftJustReleased()) {
				drawFunction(g2, releasedColor, 33, 77);
			}

			/* button RIGHT */
			if (buttons.isButtonRightJustPressed()) {
				drawFunction(g2, pressedColor, 73, 77);
			}
			if (buttons.isButtonRightHeld()) {
				drawFunction(g2, heldColor, 73, 77);
			}
			if (buttons.isButtonRightJustReleased()) {
				drawFunction(g2, releasedColor, 73, 77);
			}

			/* button UP */
			if (buttons.isButtonUpJustPressed()) {
				drawFunction(g2, pressedColor, 54, 60);
			}
			if (buttons.isButtonUpHeld()) {
				drawFunction(g2, heldColor, 54, 60);
			}
			if (buttons.isButtonUpJustReleased()) {
				drawFunction(g2, releasedColor, 54, 60);
			}

			/* button DOWN */
			if (buttons.isButtonDownJustPressed()) {
				drawFunction(g2, pressedColor, 54, 97);
			}
			if (buttons.isButtonDownHeld()) {
				drawFunction(g2, heldColor, 54, 97);
			}
			if (buttons.isButtonDownJustReleased()) {
				drawFunction(g2, releasedColor, 54, 97);
			}

			/* button MINUS */
			if (buttons.isButtonMinusJustPressed()) {
				drawFunction(g2, pressedColor, 20, 230);
			}
			if (buttons.isButtonMinusHeld()) {
				drawFunction(g2, heldColor, 20, 230);
			}
			if (buttons.isButtonMinusJustReleased()) {
				drawFunction(g2, releasedColor, 20, 230);
			}

			/* button PLUS */
			if (buttons.isButtonPlusJustPressed()) {
				drawFunction(g2, pressedColor, 86, 230);
			}
			if (buttons.isButtonPlusHeld()) {
				drawFunction(g2, heldColor, 86, 230);
			}
			if (buttons.isButtonPlusJustReleased()) {
				drawFunction(g2, releasedColor, 86, 230);
			}

			/* button HOME */
			if (buttons.isButtonHomeJustPressed()) {
				drawFunction(g2, pressedColor, 53, 230);
			}
			if (buttons.isButtonHomeHeld()) {
				drawFunction(g2, heldColor, 53, 230);
			}
			if (buttons.isButtonHomeJustReleased()) {
				drawFunction(g2, releasedColor, 53, 230);
			}

			buttons = null;
		}

		// put offscreen image on the screen
		g.drawImage(mImage, 0, 0, null);
	}

	/**
	 * Function used to factorize code.
	 * 
	 * @param g2
	 *            where to draw a shape.
	 * @param col
	 *            color to use.
	 * @param x
	 *            x coordinates.
	 * @param y
	 *            y coordinates.
	 */
	private void drawFunction(Graphics2D g2, Color col, int x, int y) {
		g2.setPaint(col);
		g2.translate(x, y);
		g2.draw(shape);
		g2.fill(shape);
		g2.setTransform(new AffineTransform());
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
		setSize(wiimoteImage.getWidth(this), wiimoteImage.getHeight(this));
		buttons = arg0;
		repaint();
	}

	public void onIrEvent(IREvent arg0) {
		// nothing
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
            clearView();
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

	public Color getHeldColor() {
		return heldColor;
	}

	public Color getPressedColor() {
		return pressedColor;
	}

	public Color getReleasedColor() {
		return releasedColor;
	}

	public Shape getShape() {
		return shape;
	}

	public void setHeldColor(Color heldColor) {
		this.heldColor = heldColor;
	}

	public void setPressedColor(Color pressedColor) {
		this.pressedColor = pressedColor;
	}

	public void setReleasedColor(Color releasedColor) {
		this.releasedColor = releasedColor;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public void clearView() {
		buttons = null;
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
