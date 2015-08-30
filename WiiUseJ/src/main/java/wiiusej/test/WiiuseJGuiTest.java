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
package wiiusej.test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.utils.AccelerationPanel;
import wiiusej.utils.AccelerationWiimoteEventPanel;
import wiiusej.utils.ButtonsEventPanel;
import wiiusej.utils.GForcePanel;
import wiiusej.utils.IRPanel;
import wiiusej.utils.OrientationPanel;
import wiiusej.utils.OrientationWiimoteEventPanel;
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
 * Gui class to test WiiuseJ.
 * 
 * @author guiguito
 */
public class WiiuseJGuiTest extends javax.swing.JFrame implements
		WiimoteListener {

	private Wiimote wiimote;
	private Robot robot = null;
	private boolean statusMotionRequested = false;
	private boolean statusIRRequested = false;
	private JFrame expansionFrame = null;
	private boolean isFirstStatusGot = false;
	private WindowListener buttonSetter = new WindowListener() {

		public void windowOpened(WindowEvent e) {
			// nothing
		}

		public void windowClosing(WindowEvent e) {
			// nothing
		}

		public void windowClosed(WindowEvent e) {
			// nothing
		}

		public void windowIconified(WindowEvent e) {
			// nothing
		}

		public void windowDeiconified(WindowEvent e) {
			// nothing
		}

		public void windowActivated(WindowEvent e) {
			showExpansionWiimoteButton.setEnabled(false);
                        if (expansionFrame instanceof NunchukGuiTest){
                            showExpansionWiimoteButton.setText("Hide Nunchuk");
                        }else if(expansionFrame instanceof GuitarHero3GuiTest){
                            showExpansionWiimoteButton.setText("Hide Guitar");
                        }else if(expansionFrame instanceof ClassicControllerGuiTest){
                            showExpansionWiimoteButton.setText("Hide Classic Controller");
                        }			
		}

		public void windowDeactivated(WindowEvent e) {
			showExpansionWiimoteButton.setEnabled(true);			
                        if (expansionFrame instanceof NunchukGuiTest){
                            showExpansionWiimoteButton.setText("Show Nunchuk");
                        }else if(expansionFrame instanceof GuitarHero3GuiTest){
                            showExpansionWiimoteButton.setText("Show Guitar");
                        }else if(expansionFrame instanceof ClassicControllerGuiTest){
                            showExpansionWiimoteButton.setText("Show Classic controller");
                        }
		}
	};

	/**
	 * default constructor
	 */
	public WiiuseJGuiTest() {
		initComponents();
		this.addWindowListener(new CloseGuiTestCleanly());
	}

	/**
	 * Creates new form WiiuseJGuiTest
	 */
	public WiiuseJGuiTest(Wiimote wiimote) {
		initComponents();
		this.addWindowListener(new CloseGuiTestCleanly());
		if (wiimote != null) {
			this.wiimote = wiimote;
			registerListeners();
			initWiimote();
			isFirstStatusGot = false;
			getStatusButtonMousePressed(null);
		}
	}

	/**
	 * Clear all views
	 */
	private void clearViews() {
		((IRPanel) irViewPanel).clearView();
		((ButtonsEventPanel) buttonsPanel).clearView();
		((OrientationPanel) motionSensingPanel).clearView();
		((GForcePanel) gForcePanel).clearView();
		((AccelerationPanel) accelerationPanel).clearView();
	}

	/**
	 * Unregister all listeners.
	 */
	private void unregisterListeners() {
		wiimote.removeWiiMoteEventListeners((IRPanel) irViewPanel);
		wiimote.removeWiiMoteEventListeners((ButtonsEventPanel) buttonsPanel);
		wiimote
				.removeWiiMoteEventListeners((OrientationPanel) motionSensingPanel);
		wiimote.removeWiiMoteEventListeners((GForcePanel) gForcePanel);
		wiimote
				.removeWiiMoteEventListeners((AccelerationPanel) accelerationPanel);
		wiimote.removeWiiMoteEventListeners(this);
	}

	private void initWiimote() {
		wiimote.deactivateContinuous();
		wiimote.deactivateSmoothing();
		wiimote.setScreenAspectRatio169();
		wiimote.setSensorBarBelowScreen();
	}

	/**
	 * Register all listeners
	 */
	private void registerListeners() {
		wiimote.addWiiMoteEventListeners((IRPanel) irViewPanel);
		wiimote.addWiiMoteEventListeners((ButtonsEventPanel) buttonsPanel);
		wiimote.addWiiMoteEventListeners((OrientationPanel) motionSensingPanel);
		wiimote.addWiiMoteEventListeners((GForcePanel) gForcePanel);
		wiimote.addWiiMoteEventListeners((AccelerationPanel) accelerationPanel);
		wiimote.addWiiMoteEventListeners(this);

	}

	public void onButtonsEvent(WiimoteButtonsEvent arg0) {
		if (robot != null) {
			if (arg0.isButtonAPressed()) {
				robot.mousePress(InputEvent.BUTTON1_MASK);

			}
			if (arg0.isButtonBPressed()) {
				robot.mousePress(InputEvent.BUTTON2_MASK);

			}
			if (arg0.isButtonOnePressed()) {
				robot.mousePress(InputEvent.BUTTON3_MASK);

			}
			if (arg0.isButtonAJustReleased()) {
				robot.mouseRelease(InputEvent.BUTTON1_MASK);

			}
			if (arg0.isButtonBJustReleased()) {
				robot.mouseRelease(InputEvent.BUTTON2_MASK);

			}
			if (arg0.isButtonOneJustReleased()) {
				robot.mouseRelease(InputEvent.BUTTON3_MASK);

			}
			if (arg0.isButtonUpPressed()) {// mouse wheel up
				robot.mouseWheel(-1);
			}
			if (arg0.isButtonDownPressed()) {// mouse wheel down
				robot.mouseWheel(1);
			}

			if (arg0.isButtonTwoPressed()) {// stop mouse control
				mouseIRControlButtonMousePressed(null);
			}
		}
	}

	public void onIrEvent(IREvent arg0) {
		if (robot != null) {// if mouse control activated
			robot.mouseMove(arg0.getX(), arg0.getY());
		}
		if (statusIRRequested) {
			xResolutionTextField.setText("" + arg0.getXVRes());
			yResolutionTextField.setText("" + arg0.getYVRes());
			statusIRRequested = false;
		}
	}

	public void onMotionSensingEvent(MotionSensingEvent arg0) {
		if (statusMotionRequested) {// Status requested
			accelerationThresholdTextField.setText(""
					+ arg0.getAccelerationThreshold());
			orientationThresholdTextField.setText(""
					+ arg0.getOrientationThreshold());
			alphaSmoothingTextField.setText("" + arg0.getAlphaSmoothing());
			statusMotionRequested = false;
		}
	}

	public void onExpansionEvent(ExpansionEvent e) {
		// nothing yet
	}

	public void onStatusEvent(StatusEvent arg0) {
		if (!isFirstStatusGot) {
			if (arg0.isNunchukConnected()) {
				showExpansionWiimoteButton.setEnabled(true);
				showExpansionWiimoteButton.setText("Show Nunchuk");
				expansionFrame = new NunchukGuiTest(wiimote);
				expansionFrame
						.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				expansionFrame.addWindowListener(buttonSetter);
				isFirstStatusGot = true;
			}else if(arg0.isClassicControllerConnected()){
                                showExpansionWiimoteButton.setEnabled(true);
				showExpansionWiimoteButton.setText("Show Classic Controller");
				expansionFrame = new ClassicControllerGuiTest(wiimote);
				expansionFrame
						.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				expansionFrame.addWindowListener(buttonSetter);
				isFirstStatusGot = true;
                        }
                        else if(arg0.isGuitarHeroConnected()){
                                showExpansionWiimoteButton.setEnabled(true);
				showExpansionWiimoteButton.setText("Show Guitar Hero 3 Controller");
				expansionFrame = new GuitarHero3GuiTest(wiimote);
				expansionFrame
						.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				expansionFrame.addWindowListener(buttonSetter);
				isFirstStatusGot = true;
                        }
		}
		messageText.setText("Status received !");
		batteryLevelText.setText(arg0.getBatteryLevel() + " %");
		led1Button.setEnabled(arg0.isLed1Set());
		led2Button.setEnabled(arg0.isLed2Set());
		led3Button.setEnabled(arg0.isLed3Set());
		led4Button.setEnabled(arg0.isLed4Set());
		if (arg0.isNunchukConnected()) {
                    ((NunchukGuiTest) expansionFrame).requestThresholdsUpdate();
		}
		// attachments
		int eventType = arg0.getEventType();
		if (eventType == StatusEvent.WIIUSE_CLASSIC_CTRL_INSERTED) {
			expansionText.setText("Classic control connected.");
		} else if (eventType == StatusEvent.WIIUSE_CLASSIC_CTRL_REMOVED) {
			expansionText.setText("Classic control removed.");
		} else if (eventType == StatusEvent.WIIUSE_NUNCHUK_INSERTED) {
			expansionText.setText("Nunchuk connected.");
		} else if (eventType == StatusEvent.WIIUSE_NUNCHUK_REMOVED) {
			expansionText.setText("Nunchuk removed.");
		} else if (eventType == StatusEvent.WIIUSE_GUITAR_HERO_3_CTRL_INSERTED) {
			expansionText.setText("Guitar Hero 3 control connected.");
		} else if (eventType == StatusEvent.WIIUSE_GUITAR_HERO_3_CTRL_REMOVED) {
			expansionText.setText("Guitar Hero 3 control removed.");
		}
	}

	public void onDisconnectionEvent(DisconnectionEvent arg0) {
		messageText.setText("Wiimote Disconnected !");
		unregisterListeners();
		clearViews();
		isFirstStatusGot = false;
	}

	public void onNunchukInsertedEvent(NunchukInsertedEvent e) {
		messageText.setText("Nunchuk connected !");
		expansionText.setText("Expansion connected : Nunchuk.");
		showExpansionWiimoteButton.setEnabled(true);
		showExpansionWiimoteButton.setText("Show nunchuk");
		expansionFrame = new NunchukGuiTest(wiimote);
		expansionFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		expansionFrame.addWindowListener(buttonSetter);
	}

	public void onNunchukRemovedEvent(NunchukRemovedEvent e) {
		messageText.setText("Nunchuk disconnected !");
		expansionText.setText("No expansion connected.");
		showExpansionWiimoteButton.setEnabled(false);
		showExpansionWiimoteButton.setText("No expansion");
		if (expansionFrame != null) {
			if (expansionFrame instanceof NunchukGuiTest) {
				((NunchukGuiTest) expansionFrame).unRegisterListeners();
			}
			expansionFrame.setEnabled(false);
			expansionFrame.dispose();
			expansionFrame = null;
		}
	}

	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {
		messageText.setText("Guitar Hero 3 connected !");
		expansionText.setText("Expansion connected : Guitar Hero 3.");
		showExpansionWiimoteButton.setEnabled(true);
		showExpansionWiimoteButton.setText("Show Guitar Hero 3");
		expansionFrame = new GuitarHero3GuiTest(wiimote);
		expansionFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		expansionFrame.addWindowListener(buttonSetter);
	}

	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {
		messageText.setText("Guitar Hero 3 disconnected !");
		expansionText.setText("No expansion connected.");
		showExpansionWiimoteButton.setEnabled(false);
		showExpansionWiimoteButton.setText("No expansion");
		if (expansionFrame != null) {
			if (expansionFrame instanceof GuitarHero3GuiTest) {
				((GuitarHero3GuiTest) expansionFrame).unRegisterListeners();
			}
			expansionFrame.setEnabled(false);
			expansionFrame.dispose();
			expansionFrame = null;
		}
	}

	public void onClassicControllerInsertedEvent(
			ClassicControllerInsertedEvent arg0) {
                messageText.setText("Classic controller connected !");
		expansionText.setText("Expansion connected : Classic Controller.");
		showExpansionWiimoteButton.setEnabled(true);
		showExpansionWiimoteButton.setText("Show Classic Controller");
		expansionFrame = new ClassicControllerGuiTest(wiimote);
		expansionFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		expansionFrame.addWindowListener(buttonSetter);
	}

	public void onClassicControllerRemovedEvent(
			ClassicControllerRemovedEvent arg0) {
		messageText.setText("Classic controller disconnected !");
		expansionText.setText("No expansion connected.");
		showExpansionWiimoteButton.setEnabled(false);
		showExpansionWiimoteButton.setText("No expansion");
		if (expansionFrame != null) {
			if (expansionFrame instanceof ClassicControllerGuiTest) {
				((ClassicControllerGuiTest) expansionFrame).unRegisterListeners();
			}
			expansionFrame.setEnabled(false);
			expansionFrame.dispose();
			expansionFrame = null;
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        leftPanel = new javax.swing.JPanel();
        irViewPanel = new IRPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        accelerationPanel = new AccelerationWiimoteEventPanel();
        motionSensingPanel = new OrientationWiimoteEventPanel();
        gForcePanel = new wiiusej.utils.GForceWiimoteEventPanel();
        rightPanel = new javax.swing.JPanel();
        fixedWiimotePanel = new javax.swing.JPanel();
        buttonsPanel = new ButtonsEventPanel();
        controlsPanel = new javax.swing.JPanel();
        activateRumbleIRPanel = new javax.swing.JPanel();
        toggleRumbleButton = new javax.swing.JButton();
        toggleIRTrackingButton = new javax.swing.JButton();
        activateMotionSensingPanel = new javax.swing.JPanel();
        toggleMotionSensingTrackingButton = new javax.swing.JButton();
        activateSmoothingContinuousPanel = new javax.swing.JPanel();
        toggleSmoothingButton = new javax.swing.JButton();
        toggleContinuousButton = new javax.swing.JButton();
        setLedsPanel = new javax.swing.JPanel();
        led1Button = new javax.swing.JButton();
        led2Button = new javax.swing.JButton();
        led3Button = new javax.swing.JButton();
        led4Button = new javax.swing.JButton();
        setLedsButton = new javax.swing.JButton();
        setAlphaSmoothingPanel = new javax.swing.JPanel();
        alphaSmoothingTextField = new javax.swing.JTextField();
        alphaSmoothingButton = new javax.swing.JButton();
        setOrientationThresholdPanel = new javax.swing.JPanel();
        orientationThresholdTextField = new javax.swing.JTextField();
        orientationThresholdButton = new javax.swing.JButton();
        setAccelerationThresholdPanel = new javax.swing.JPanel();
        accelerationThresholdTextField = new javax.swing.JTextField();
        accelerationThresholdButton = new javax.swing.JButton();
        getStatusPanel = new javax.swing.JPanel();
        getStatusButton = new javax.swing.JButton();
        batteryText = new javax.swing.JLabel();
        batteryLevelText = new javax.swing.JLabel();
        setIrSensitivyPanel = new javax.swing.JPanel();
        setIrSensitivySpinner = new javax.swing.JSpinner();
        setIrSensitivyButton = new javax.swing.JButton();
        setTimeoutButton = new javax.swing.JButton();
        setTimeoutPanel = new javax.swing.JPanel();
        normalTimeoutSpinner = new javax.swing.JSpinner();
        normalTimeoutText = new javax.swing.JLabel();
        expansionHandshakeTimeoutSpinner = new javax.swing.JSpinner();
        expansionHandshakeTimeoutText = new javax.swing.JLabel();
        setIRConfPanel = new javax.swing.JPanel();
        toggleSensorBarPositionButton = new javax.swing.JButton();
        toggleScreenAspectRatioButton = new javax.swing.JButton();
        setVirtualResolutionPanel = new javax.swing.JPanel();
        xLabel = new javax.swing.JLabel();
        xResolutionTextField = new javax.swing.JTextField();
        yLabel = new javax.swing.JLabel();
        yResolutionTextField = new javax.swing.JTextField();
        setVirtualResolutionButton = new javax.swing.JButton();
        startMouseControlPanel = new javax.swing.JPanel();
        mouseIRControlButton = new javax.swing.JButton();
        exPansionPanel = new javax.swing.JPanel();
        expansionText = new javax.swing.JLabel();
        expansionButtonPanel = new javax.swing.JPanel();
        showExpansionWiimoteButton = new javax.swing.JButton();
        showExpansionWiimoteButton.setEnabled(false);
        messagesPanel = new javax.swing.JPanel();
        reconnectWiimotesButton = new javax.swing.JButton();
        messageLabelText = new javax.swing.JLabel();
        messageText = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WiiuseJ Test GUI");
        setName("WiiuseJ Test GUI"); // NOI18N

        leftPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        irViewPanel.setBackground(new java.awt.Color(0, 0, 0));
        irViewPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 2, true), "IR View", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 0, 51)));
        irViewPanel.setToolTipText("IREvent");

        javax.swing.GroupLayout irViewPanelLayout = new javax.swing.GroupLayout(irViewPanel);
        irViewPanel.setLayout(irViewPanelLayout);
        irViewPanelLayout.setHorizontalGroup(
            irViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 272, Short.MAX_VALUE)
        );
        irViewPanelLayout.setVerticalGroup(
            irViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );

        accelerationPanel.setToolTipText("MotionSensingEvent");

        javax.swing.GroupLayout accelerationPanelLayout = new javax.swing.GroupLayout(accelerationPanel);
        accelerationPanel.setLayout(accelerationPanelLayout);
        accelerationPanelLayout.setHorizontalGroup(
            accelerationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );
        accelerationPanelLayout.setVerticalGroup(
            accelerationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 213, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Acceleration", accelerationPanel);

        javax.swing.GroupLayout motionSensingPanelLayout = new javax.swing.GroupLayout(motionSensingPanel);
        motionSensingPanel.setLayout(motionSensingPanelLayout);
        motionSensingPanelLayout.setHorizontalGroup(
            motionSensingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );
        motionSensingPanelLayout.setVerticalGroup(
            motionSensingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 213, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Orientation", motionSensingPanel);

        javax.swing.GroupLayout gForcePanelLayout = new javax.swing.GroupLayout(gForcePanel);
        gForcePanel.setLayout(gForcePanelLayout);
        gForcePanelLayout.setHorizontalGroup(
            gForcePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );
        gForcePanelLayout.setVerticalGroup(
            gForcePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 213, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("GForce", gForcePanel);

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(irViewPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPanelLayout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(irViewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Orientation");

        rightPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        rightPanel.setLayout(new javax.swing.BoxLayout(rightPanel, javax.swing.BoxLayout.LINE_AXIS));

        fixedWiimotePanel.setMaximumSize(new java.awt.Dimension(120, 32767));
        fixedWiimotePanel.setMinimumSize(new java.awt.Dimension(120, 100));
        fixedWiimotePanel.setPreferredSize(new java.awt.Dimension(120, 100));
        fixedWiimotePanel.setRequestFocusEnabled(false);
        fixedWiimotePanel.setLayout(null);

        buttonsPanel.setMaximumSize(new java.awt.Dimension(120, 484));
        buttonsPanel.setMinimumSize(new java.awt.Dimension(120, 484));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setPreferredSize(new java.awt.Dimension(120, 484));

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 484, Short.MAX_VALUE)
        );

        fixedWiimotePanel.add(buttonsPanel);
        buttonsPanel.setBounds(0, 0, 120, 484);

        rightPanel.add(fixedWiimotePanel);

        controlsPanel.setMinimumSize(new java.awt.Dimension(100, 264));
        controlsPanel.setPreferredSize(new java.awt.Dimension(190, 264));
        controlsPanel.setLayout(new java.awt.GridLayout(16, 1));

        toggleRumbleButton.setText("Activate Rumble");
        toggleRumbleButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                toggleRumbleButtonMousePressed(evt);
            }
        });
        activateRumbleIRPanel.add(toggleRumbleButton);

        toggleIRTrackingButton.setText("Activate IR Tracking");
        toggleIRTrackingButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                toggleIRTrackingButtonMousePressed(evt);
            }
        });
        activateRumbleIRPanel.add(toggleIRTrackingButton);

        controlsPanel.add(activateRumbleIRPanel);

        toggleMotionSensingTrackingButton.setText("Activate motion sensing Tracking");
        toggleMotionSensingTrackingButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                toggleMotionSensingTrackingButtonMousePressed(evt);
            }
        });
        activateMotionSensingPanel.add(toggleMotionSensingTrackingButton);

        controlsPanel.add(activateMotionSensingPanel);

        toggleSmoothingButton.setText("Activate Smoothing");
        toggleSmoothingButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                toggleSmoothingButtonMousePressed(evt);
            }
        });
        activateSmoothingContinuousPanel.add(toggleSmoothingButton);

        toggleContinuousButton.setText("Activate Continuous");
        toggleContinuousButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                toggleContinuousButtonMousePressed(evt);
            }
        });
        activateSmoothingContinuousPanel.add(toggleContinuousButton);

        controlsPanel.add(activateSmoothingContinuousPanel);

        led1Button.setText("Led1");
        led1Button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                led1ButtonMousePressed(evt);
            }
        });
        setLedsPanel.add(led1Button);

        led2Button.setText("Led2");
        led2Button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                led2ButtonMousePressed(evt);
            }
        });
        setLedsPanel.add(led2Button);

        led3Button.setText("Led3");
        led3Button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                led3ButtonMousePressed(evt);
            }
        });
        setLedsPanel.add(led3Button);

        led4Button.setText("Led4");
        led4Button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                led4ButtonMousePressed(evt);
            }
        });
        setLedsPanel.add(led4Button);

        setLedsButton.setText("Set leds");
        setLedsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                setLedsButtonMousePressed(evt);
            }
        });
        setLedsPanel.add(setLedsButton);

        controlsPanel.add(setLedsPanel);

        alphaSmoothingTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        alphaSmoothingTextField.setPreferredSize(new java.awt.Dimension(100, 20));
        setAlphaSmoothingPanel.add(alphaSmoothingTextField);

        alphaSmoothingButton.setText("Set alpha smoothing");
        alphaSmoothingButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                alphaSmoothingButtonMousePressed(evt);
            }
        });
        setAlphaSmoothingPanel.add(alphaSmoothingButton);

        controlsPanel.add(setAlphaSmoothingPanel);

        orientationThresholdTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        orientationThresholdTextField.setPreferredSize(new java.awt.Dimension(100, 20));
        setOrientationThresholdPanel.add(orientationThresholdTextField);

        orientationThresholdButton.setText("Set orientation threshold");
        orientationThresholdButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                orientationThresholdButtonMousePressed(evt);
            }
        });
        setOrientationThresholdPanel.add(orientationThresholdButton);

        controlsPanel.add(setOrientationThresholdPanel);

        accelerationThresholdTextField.setPreferredSize(new java.awt.Dimension(100, 20));
        setAccelerationThresholdPanel.add(accelerationThresholdTextField);

        accelerationThresholdButton.setText("Set acceleration threshold");
        accelerationThresholdButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                accelerationThresholdButtonMousePressed(evt);
            }
        });
        setAccelerationThresholdPanel.add(accelerationThresholdButton);

        controlsPanel.add(setAccelerationThresholdPanel);

        getStatusButton.setText("Get status");
        getStatusButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                getStatusButtonMousePressed(evt);
            }
        });
        getStatusPanel.add(getStatusButton);

        batteryText.setFont(new java.awt.Font("Tahoma", 0, 14));
        batteryText.setText("Battery level :");
        getStatusPanel.add(batteryText);

        batteryLevelText.setFont(new java.awt.Font("Arial", 0, 14));
        batteryLevelText.setText(" %");
        getStatusPanel.add(batteryLevelText);

        controlsPanel.add(getStatusPanel);

        setIrSensitivySpinner.setPreferredSize(new java.awt.Dimension(50, 18));
        setIrSensitivySpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                setIrSensitivySpinnerStateChanged(evt);
            }
        });
        setIrSensitivyPanel.add(setIrSensitivySpinner);

        setIrSensitivyButton.setText("SetIrSensivity");
        setIrSensitivyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                setIrSensitivyButtonMousePressed(evt);
            }
        });
        setIrSensitivyPanel.add(setIrSensitivyButton);

        setTimeoutButton.setText("Set timeouts in ms");
        setTimeoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                setTimeoutButtonMousePressed(evt);
            }
        });
        setIrSensitivyPanel.add(setTimeoutButton);

        controlsPanel.add(setIrSensitivyPanel);

        normalTimeoutSpinner.setPreferredSize(new java.awt.Dimension(40, 18));
        normalTimeoutSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                normalTimeoutSpinnerStateChanged(evt);
            }
        });
        setTimeoutPanel.add(normalTimeoutSpinner);

        normalTimeoutText.setText("Normal timeout");
        setTimeoutPanel.add(normalTimeoutText);

        expansionHandshakeTimeoutSpinner.setPreferredSize(new java.awt.Dimension(40, 18));
        expansionHandshakeTimeoutSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                expansionHandshakeTimeoutSpinnerStateChanged(evt);
            }
        });
        setTimeoutPanel.add(expansionHandshakeTimeoutSpinner);

        expansionHandshakeTimeoutText.setText("Expansion handshake timeout");
        setTimeoutPanel.add(expansionHandshakeTimeoutText);

        controlsPanel.add(setTimeoutPanel);

        toggleSensorBarPositionButton.setText("Set sensor bar above");
        toggleSensorBarPositionButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                toggleSensorBarPositionButtonMousePressed(evt);
            }
        });
        setIRConfPanel.add(toggleSensorBarPositionButton);

        toggleScreenAspectRatioButton.setText("Set screen aspect ratio 4/3");
        toggleScreenAspectRatioButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                toggleScreenAspectRatioButtonMousePressed(evt);
            }
        });
        setIRConfPanel.add(toggleScreenAspectRatioButton);

        controlsPanel.add(setIRConfPanel);

        xLabel.setText("X");
        setVirtualResolutionPanel.add(xLabel);

        xResolutionTextField.setMinimumSize(new java.awt.Dimension(40, 20));
        xResolutionTextField.setPreferredSize(new java.awt.Dimension(40, 20));
        setVirtualResolutionPanel.add(xResolutionTextField);

        yLabel.setText("Y");
        setVirtualResolutionPanel.add(yLabel);

        yResolutionTextField.setFocusTraversalPolicyProvider(true);
        yResolutionTextField.setMinimumSize(new java.awt.Dimension(40, 20));
        yResolutionTextField.setPreferredSize(new java.awt.Dimension(40, 20));
        setVirtualResolutionPanel.add(yResolutionTextField);

        setVirtualResolutionButton.setText("Set virtual resolution");
        setVirtualResolutionButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                setVirtualResolutionButtonMousePressed(evt);
            }
        });
        setVirtualResolutionPanel.add(setVirtualResolutionButton);

        controlsPanel.add(setVirtualResolutionPanel);

        mouseIRControlButton.setText("Start infrared mouse control");
        mouseIRControlButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mouseIRControlButtonMousePressed(evt);
            }
        });
        startMouseControlPanel.add(mouseIRControlButton);

        controlsPanel.add(startMouseControlPanel);

        expansionText.setText("No expansion connected.");
        exPansionPanel.add(expansionText);

        controlsPanel.add(exPansionPanel);

        showExpansionWiimoteButton.setText("No expansion connected");
        showExpansionWiimoteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                showExpansionWiimoteButtonMousePressed(evt);
            }
        });
        expansionButtonPanel.add(showExpansionWiimoteButton);

        controlsPanel.add(expansionButtonPanel);

        reconnectWiimotesButton.setText("Reconnect wiimote");
        reconnectWiimotesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                reconnectWiimotesButtonMousePressed(evt);
            }
        });
        messagesPanel.add(reconnectWiimotesButton);

        messageLabelText.setFont(new java.awt.Font("Tahoma", 0, 14));
        messageLabelText.setText("Message : ");
        messagesPanel.add(messageLabelText);

        messageText.setFont(new java.awt.Font("Arial", 0, 14));
        messageText.setText("None");
        messagesPanel.add(messageText);

        controlsPanel.add(messagesPanel);

        rightPanel.add(controlsPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(leftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(leftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(rightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-800)/2, (screenSize.height-600)/2, 800, 600);
    }// </editor-fold>//GEN-END:initComponents

	private void toggleRumbleButtonMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_toggleRumbleButtonMousePressed
		if (toggleRumbleButton.isEnabled()) {
			wiimote.activateRumble();
			toggleRumbleButton.setEnabled(false);
			toggleRumbleButton.setText("Deactivate Rumble");
			messageText.setText("Rumble activated");
		} else {
			wiimote.deactivateRumble();
			toggleRumbleButton.setEnabled(true);
			toggleRumbleButton.setText("Activate Rumble");
			messageText.setText("Rumble deactivated");
		}
	}// GEN-LAST:event_toggleRumbleButtonMousePressed

	private void toggleIRTrackingButtonMousePressed(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_toggleIRTrackingButtonMousePressed
		if (toggleIRTrackingButton.isEnabled()) {
			wiimote.activateIRTRacking();
			toggleIRTrackingButton.setEnabled(false);
			toggleIRTrackingButton.setText("Deactivate IR Tracking");
			messageText.setText("IR Tracking activated");
		} else {
			wiimote.deactivateIRTRacking();
			toggleIRTrackingButton.setEnabled(true);
			toggleIRTrackingButton.setText("Activate IR Tracking");
			((IRPanel) irViewPanel).onDisconnectionEvent(null);
			messageText.setText("IR Tracking deactivated");
		}
	}// GEN-LAST:event_toggleIRTrackingButtonMousePressed

	private void toggleMotionSensingTrackingButtonMousePressed(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_toggleMotionSensingTrackingButtonMousePressed
		if (toggleMotionSensingTrackingButton.isEnabled()) {
			wiimote.activateMotionSensing();
			toggleMotionSensingTrackingButton.setEnabled(false);
			toggleMotionSensingTrackingButton
					.setText("Deactivate Motion Sensing");
			messageText.setText("Motion Sensing activated");
		} else {
			wiimote.deactivateMotionSensing();
			toggleMotionSensingTrackingButton.setEnabled(true);
			toggleMotionSensingTrackingButton
					.setText("Activate Motion Sensing");
			((OrientationPanel) motionSensingPanel).onDisconnectionEvent(null);
			((GForcePanel) gForcePanel).onDisconnectionEvent(null);
			messageText.setText("Motion Sensing deactivated");
		}
	}// GEN-LAST:event_toggleMotionSensingTrackingButtonMousePressed

	private void toggleSmoothingButtonMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_toggleSmoothingButtonMousePressed
		if (toggleSmoothingButton.isEnabled()) {
			wiimote.activateSmoothing();
			toggleSmoothingButton.setEnabled(false);
			toggleSmoothingButton.setText("Deactivate Alpha Smoothing");
			messageText.setText("Alpha Smoothing activated");
		} else {
			wiimote.deactivateSmoothing();
			toggleSmoothingButton.setEnabled(true);
			toggleSmoothingButton.setText("Activate Alpha Smoothing");
			messageText.setText("Alpha Smoothing deactivated");
		}
	}// GEN-LAST:event_toggleSmoothingButtonMousePressed

	private void toggleContinuousButtonMousePressed(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_toggleContinuousButtonMousePressed
		if (toggleContinuousButton.isEnabled()) {
			wiimote.activateContinuous();
			toggleContinuousButton.setEnabled(false);
			toggleContinuousButton.setText("Deactivate Continuous");
			messageText.setText("Continuous activated");
		} else {
			wiimote.deactivateContinuous();
			toggleContinuousButton.setEnabled(true);
			toggleContinuousButton.setText("Activate Continuous");
			messageText.setText("Continuous deactivated");
		}
	}// GEN-LAST:event_toggleContinuousButtonMousePressed

	private void led1ButtonMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_led1ButtonMousePressed
		if (led1Button.isEnabled()) {
			led1Button.setEnabled(false);
		} else {
			led1Button.setEnabled(true);
		}
	}// GEN-LAST:event_led1ButtonMousePressed

	private void led2ButtonMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_led2ButtonMousePressed
		if (led2Button.isEnabled()) {
			led2Button.setEnabled(false);
		} else {
			led2Button.setEnabled(true);
		}
	}// GEN-LAST:event_led2ButtonMousePressed

	private void led3ButtonMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_led3ButtonMousePressed
		if (led3Button.isEnabled()) {
			led3Button.setEnabled(false);
		} else {
			led3Button.setEnabled(true);
		}
	}// GEN-LAST:event_led3ButtonMousePressed

	private void led4ButtonMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_led4ButtonMousePressed
		if (led4Button.isEnabled()) {
			led4Button.setEnabled(false);
		} else {
			led4Button.setEnabled(true);
		}
	}// GEN-LAST:event_led4ButtonMousePressed

	private void setLedsButtonMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_setLedsButtonMousePressed
		wiimote.setLeds(led1Button.isEnabled(), led2Button.isEnabled(),
				led3Button.isEnabled(), led4Button.isEnabled());
		messageText.setText("Leds set");
	}// GEN-LAST:event_setLedsButtonMousePressed

	private void alphaSmoothingButtonMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_alphaSmoothingButtonMousePressed
		try {
			float nb = Float.parseFloat(alphaSmoothingTextField.getText());
			wiimote.setAlphaSmoothingValue(nb);
			messageText.setText("Alpha smoothing set to " + nb);
		} catch (NumberFormatException e) {
			messageText
					.setText("Number is not a float, alpha smoothing not set !");
		}
	}// GEN-LAST:event_alphaSmoothingButtonMousePressed

	private void orientationThresholdButtonMousePressed(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_orientationThresholdButtonMousePressed
		try {
			float nb = Float
					.parseFloat(orientationThresholdTextField.getText());
			wiimote.setOrientationThreshold(nb);
			messageText.setText("Orientation threshold set to " + nb);
		} catch (NumberFormatException e) {
			messageText
					.setText("Number is not a float, orientation threshold not set !");
		}
	}// GEN-LAST:event_orientationThresholdButtonMousePressed

	private void accelerationThresholdButtonMousePressed(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_accelerationThresholdButtonMousePressed
		try {
			int nb = Integer.parseInt(accelerationThresholdTextField.getText());
			wiimote.setAccelerationThreshold(nb);
			messageText.setText("Acceleration threshold set to " + nb);
		} catch (NumberFormatException e) {
			messageText
					.setText("Number is not an integer, acceleration threshold not set !");
		}
	}// GEN-LAST:event_accelerationThresholdButtonMousePressed

	private void getStatusButtonMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_getStatusButtonMousePressed
		wiimote.getStatus();
		statusMotionRequested = true;
		statusIRRequested = true;
		if (expansionFrame instanceof NunchukGuiTest) {
			((NunchukGuiTest) expansionFrame).requestThresholdsUpdate();
		}
	}// GEN-LAST:event_getStatusButtonMousePressed

	private void toggleSensorBarPositionButtonMousePressed(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_toggleSensorBarPositionButtonMousePressed
		if (toggleSensorBarPositionButton.isEnabled()) {
			wiimote.setSensorBarBelowScreen();
			toggleSensorBarPositionButton.setEnabled(false);
			toggleSensorBarPositionButton.setText("Set sensor bar below");
			messageText.setText("Sensor bar set above");
		} else {
			wiimote.setSensorBarAboveScreen();
			toggleSensorBarPositionButton.setEnabled(true);
			toggleSensorBarPositionButton.setText("Set sensor bar above");
			messageText.setText("Sensor bar set below");
		}
	}// GEN-LAST:event_toggleSensorBarPositionButtonMousePressed

	private void toggleScreenAspectRatioButtonMousePressed(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_toggleScreenAspectRatioButtonMousePressed
		if (toggleScreenAspectRatioButton.isEnabled()) {
			wiimote.setScreenAspectRatio43();
			toggleScreenAspectRatioButton.setEnabled(false);
			toggleScreenAspectRatioButton
					.setText("Set screen aspect ratio 16/9");
			messageText.setText("creen aspect ratio to 4/3");
		} else {
			wiimote.setScreenAspectRatio169();
			toggleScreenAspectRatioButton.setEnabled(true);
			toggleScreenAspectRatioButton
					.setText("Set screen aspect ratio 4/3");
			messageText.setText("Screen aspect ratio to 16/9");
		}
	}// GEN-LAST:event_toggleScreenAspectRatioButtonMousePressed

	private void setVirtualResolutionButtonMousePressed(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_setVirtualResolutionButtonMousePressed
		try {
			int xres = Integer.parseInt(xResolutionTextField.getText());
			int yres = Integer.parseInt(yResolutionTextField.getText());
			wiimote.setVirtualResolution(xres, yres);
			messageText.setText("Virtual resolution set to " + xres + "X"
					+ yres);
		} catch (NumberFormatException e) {
			messageText
					.setText("A number in the virtual resolution is not an integer. Virtual resolution not set!");
		}
	}// GEN-LAST:event_setVirtualResolutionButtonMousePressed

	private void mouseIRControlButtonMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_mouseIRControlButtonMousePressed
		if (mouseIRControlButton.isEnabled()) {
			try {
				mouseIRControlButton.setEnabled(false);
				mouseIRControlButton.setText("Stop infrared mouse control");
				robot = new Robot();
				messageText.setText("Infrared mouse control started");
			} catch (AWTException ex) {
				Logger.getLogger(WiiuseJGuiTest.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		} else {
			mouseIRControlButton.setEnabled(true);
			mouseIRControlButton.setText("Start infrared mouse control");
			robot = null;
			messageText.setText("Infrared mouse control stopped");
		}
	}// GEN-LAST:event_mouseIRControlButtonMousePressed

	private void normalTimeoutSpinnerStateChanged(
			javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_normalTimeoutSpinnerStateChanged
		String value = normalTimeoutSpinner.getValue().toString();
		boolean isInt = true;
		int valueInt = 0;
		try {
			valueInt = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			isInt = false;
			messageText.setText("Wrong value for normal timeout.");
		}
		if (isInt) {
			if (valueInt > 1000) {
				normalTimeoutSpinner.setValue("1000");
			} else if (valueInt < 0) {
				normalTimeoutSpinner.setValue("0");
			}
		}
	}// GEN-LAST:event_normalTimeoutSpinnerStateChanged

	private void expansionHandshakeTimeoutSpinnerStateChanged(
			javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_expansionHandshakeTimeoutSpinnerStateChanged
		String value = expansionHandshakeTimeoutSpinner.getValue().toString();
		boolean isInt = true;
		int valueInt = 0;
		try {
			valueInt = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			isInt = false;
			messageText.setText("Wrong value for expansion handshake timeout.");
		}
		if (isInt) {
			if (valueInt > 1000) {
				expansionHandshakeTimeoutSpinner.setValue("1000");
			} else if (valueInt < 0) {
				expansionHandshakeTimeoutSpinner.setValue("0");
			}
		}
	}// GEN-LAST:event_expansionHandshakeTimeoutSpinnerStateChanged

	private void setIrSensitivySpinnerStateChanged(
			javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_setIrSensitivySpinnerStateChanged
		String value = setIrSensitivySpinner.getValue().toString();
		boolean isInt = true;
		int valueInt = 0;
		try {
			valueInt = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			isInt = false;
			messageText.setText("Wrong value for IR senstivity.");
		}
		if (isInt) {
			if (valueInt > 5) {
				setIrSensitivySpinner.setValue("1000");
			} else if (valueInt < 0) {
				setIrSensitivySpinner.setValue("0");
			}
		}
	}// GEN-LAST:event_setIrSensitivySpinnerStateChanged

	private void setIrSensitivyButtonMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_setIrSensitivyButtonMousePressed
		String value = setIrSensitivySpinner.getValue().toString();
		boolean isInt = true;
		int valueInt = 0;
		try {
			valueInt = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			isInt = false;
			messageText
					.setText("Wrong value for IR sensitivity. It must be an int !");
		}
		if (isInt) {
			if (valueInt >= 1 && valueInt <= 5) {
				wiimote.setIrSensitivity(valueInt);
				messageText.setText("IR senstivity set to: " + valueInt + ".");
			} else {
				messageText
						.setText("Wrong value for IR senstivity. It muset be between 1 and 5 !");
			}
		}
	}// GEN-LAST:event_setIrSensitivyButtonMousePressed

	private void setTimeoutButtonMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_setTimeoutButtonMousePressed
		// get normal timeout
		String value = normalTimeoutSpinner.getValue().toString();
		boolean isInt = true;
		short valueInt = 0;
		try {
			valueInt = Short.parseShort(value);
		} catch (NumberFormatException e) {
			isInt = false;
			messageText
					.setText("Wrong value for normal timeout. It must be an int !");
		}
		// get expansion handshake timeout
		String value2 = expansionHandshakeTimeoutSpinner.getValue().toString();
		boolean isInt2 = true;
		short valueInt2 = 0;
		try {
			valueInt2 = Short.parseShort(value2);
		} catch (NumberFormatException e) {
			isInt2 = false;
			messageText
					.setText("Wrong value for expansion handshake timeout. It must be an int !");
		}
		if (isInt && isInt2) {
			if (valueInt > 0 && valueInt2 > 0) {
				wiimote.setTimeout(valueInt, valueInt2);
				messageText.setText("Normal timeout set to: " + valueInt
						+ " and expansion handshake timeout set to: "
						+ valueInt2 + "!");
			} else {
				messageText
						.setText("Wrong value for one of the timeout value. It must be an integer > 0 !");
			}
		}
	}// GEN-LAST:event_setTimeoutButtonMousePressed

	private void reconnectWiimotesButtonMousePressed(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_reconnectWiimotesButtonMousePressed
		// stop manager
		WiiUseApiManager.shutdown();

		// unregister previous wiimote
		if (wiimote != null) {
			onDisconnectionEvent(null);
		}

		// Reset Gui
		// remove frame for expansion
		if (expansionFrame != null) {
			if (expansionFrame instanceof NunchukGuiTest) {
				((NunchukGuiTest) expansionFrame).unRegisterListeners();
			}else if (expansionFrame instanceof ClassicControllerGuiTest) {
				((ClassicControllerGuiTest) expansionFrame).unRegisterListeners();
			}
			expansionFrame.setEnabled(false);
			expansionFrame.dispose();
			expansionFrame = null;
		}

		// setup buttons In first state
		toggleRumbleButton.setText("Activate Rumble");
		toggleRumbleButton.setEnabled(true);
		toggleMotionSensingTrackingButton
				.setText("Activate motion sensing Tracking");
		toggleMotionSensingTrackingButton.setEnabled(true);
		toggleIRTrackingButton.setText("Activate IR Tracking");
		toggleIRTrackingButton.setEnabled(true);
		toggleContinuousButton.setText("Activate Continuous");
		toggleContinuousButton.setEnabled(true);
		toggleScreenAspectRatioButton.setText("Set screen aspect ratio 4/3");
		toggleScreenAspectRatioButton.setEnabled(true);
		toggleSensorBarPositionButton.setText("Set sensor bar above");
		toggleSensorBarPositionButton.setEnabled(true);
		toggleSmoothingButton.setText("Activate Smoothing");
		toggleSmoothingButton.setEnabled(true);
		mouseIRControlButton.setText("Start infrared mouse control");
		mouseIRControlButton.setEnabled(true);

		// get wiimote
		Wiimote[] listWiimote = WiiUseApiManager.getWiimotes(1, true);
		if (listWiimote != null && listWiimote.length > 0) {
			wiimote = listWiimote[0];

			// registers listeners
			registerListeners();
			initWiimote();

			isFirstStatusGot = false;
			getStatusButtonMousePressed(null);
		}
	}// GEN-LAST:event_reconnectWiimotesButtonMousePressed

	private void showExpansionWiimoteButtonMousePressed(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_showExpansionWiimoteButtonMousePressed
		if (expansionFrame != null) {
			if (showExpansionWiimoteButton.isEnabled()) {// expansion frame
				// not shown
				// show it
				expansionFrame.setEnabled(true);
				expansionFrame.setVisible(true);
				showExpansionWiimoteButton.setEnabled(false);
                                if (expansionFrame instanceof NunchukGuiTest){
                                    showExpansionWiimoteButton.setText("Hide Nunchuk");
                                    messageText.setText("Nunchuk displayed !");
                                }else if(expansionFrame instanceof GuitarHero3GuiTest){
                                    showExpansionWiimoteButton.setText("Hide Guitar");
                                    messageText.setText("Guitar displayed !");
                                }else if(expansionFrame instanceof ClassicControllerGuiTest){
                                    showExpansionWiimoteButton.setText("Hide Classic controller");
                                    messageText.setText("Classic controller displayed !");
                                }
			} else {// already being shown
				expansionFrame.setEnabled(false);
				expansionFrame.setVisible(false);
				showExpansionWiimoteButton.setEnabled(true);
                                if (expansionFrame instanceof NunchukGuiTest){
                                    showExpansionWiimoteButton.setText("Show Nunchuk");
                                    messageText.setText("Nunchuk hidden !");
                                }else if(expansionFrame instanceof GuitarHero3GuiTest){
                                    showExpansionWiimoteButton.setText("Show Guitar");
                                    messageText.setText("Guitar hidden !");
                                }else if(expansionFrame instanceof ClassicControllerGuiTest){
                                    showExpansionWiimoteButton.setText("Show Classic controller");
                                    messageText.setText("Classic controller hidden !");
                                }				
			}
		}
	}// GEN-LAST:event_showExpansionWiimoteButtonMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel accelerationPanel;
    private javax.swing.JButton accelerationThresholdButton;
    private javax.swing.JTextField accelerationThresholdTextField;
    private javax.swing.JPanel activateMotionSensingPanel;
    private javax.swing.JPanel activateRumbleIRPanel;
    private javax.swing.JPanel activateSmoothingContinuousPanel;
    private javax.swing.JButton alphaSmoothingButton;
    private javax.swing.JTextField alphaSmoothingTextField;
    private javax.swing.JLabel batteryLevelText;
    private javax.swing.JLabel batteryText;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JPanel controlsPanel;
    private javax.swing.JPanel exPansionPanel;
    private javax.swing.JPanel expansionButtonPanel;
    private javax.swing.JSpinner expansionHandshakeTimeoutSpinner;
    private javax.swing.JLabel expansionHandshakeTimeoutText;
    private javax.swing.JLabel expansionText;
    private javax.swing.JPanel fixedWiimotePanel;
    private javax.swing.JPanel gForcePanel;
    private javax.swing.JButton getStatusButton;
    private javax.swing.JPanel getStatusPanel;
    private javax.swing.JPanel irViewPanel;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton led1Button;
    private javax.swing.JButton led2Button;
    private javax.swing.JButton led3Button;
    private javax.swing.JButton led4Button;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JLabel messageLabelText;
    private javax.swing.JLabel messageText;
    private javax.swing.JPanel messagesPanel;
    private javax.swing.JPanel motionSensingPanel;
    private javax.swing.JButton mouseIRControlButton;
    private javax.swing.JSpinner normalTimeoutSpinner;
    private javax.swing.JLabel normalTimeoutText;
    private javax.swing.JButton orientationThresholdButton;
    private javax.swing.JTextField orientationThresholdTextField;
    private javax.swing.JButton reconnectWiimotesButton;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JPanel setAccelerationThresholdPanel;
    private javax.swing.JPanel setAlphaSmoothingPanel;
    private javax.swing.JPanel setIRConfPanel;
    private javax.swing.JButton setIrSensitivyButton;
    private javax.swing.JPanel setIrSensitivyPanel;
    private javax.swing.JSpinner setIrSensitivySpinner;
    private javax.swing.JButton setLedsButton;
    private javax.swing.JPanel setLedsPanel;
    private javax.swing.JPanel setOrientationThresholdPanel;
    private javax.swing.JButton setTimeoutButton;
    private javax.swing.JPanel setTimeoutPanel;
    private javax.swing.JButton setVirtualResolutionButton;
    private javax.swing.JPanel setVirtualResolutionPanel;
    private javax.swing.JButton showExpansionWiimoteButton;
    private javax.swing.JPanel startMouseControlPanel;
    private javax.swing.JButton toggleContinuousButton;
    private javax.swing.JButton toggleIRTrackingButton;
    private javax.swing.JButton toggleMotionSensingTrackingButton;
    private javax.swing.JButton toggleRumbleButton;
    private javax.swing.JButton toggleScreenAspectRatioButton;
    private javax.swing.JButton toggleSensorBarPositionButton;
    private javax.swing.JButton toggleSmoothingButton;
    private javax.swing.JLabel xLabel;
    private javax.swing.JTextField xResolutionTextField;
    private javax.swing.JLabel yLabel;
    private javax.swing.JTextField yResolutionTextField;
    // End of variables declaration//GEN-END:variables
}
