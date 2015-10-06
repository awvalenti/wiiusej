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
#ifndef WIN32
#include <unistd.h>
#else

#endif

#include "wiiusej_WiiUseApi.h"
#include "wiiuse.h"
/*
 *	These are some identifiers for wiimotes
 *
 *	See below in main() for what they are used for.
 */
#define WIIMOTE_STATE_RUMBLE			0x0010
#define WIIMOTE_STATE_CONNECTED			0x0008
#define WIIMOTE_IS_SET(wm, s)			((wm->state & (s)) == (s))
#define WIIMOTE_IS_FLAG_SET(wm, s)		((wm->flags & (s)) == (s))
#define WIIUSE_GET_IR_SENSITIVITY_CORRECTED(wm, lvl)									\
			do {														\
				if ((wm->state & 0x0200) == 0x0200) 		*lvl = 1;	\
				else if ((wm->state & 0x0400) == 0x0400) 	*lvl = 2;	\
				else if ((wm->state & 0x0800) == 0x0800) 	*lvl = 3;	\
				else if ((wm->state & 0x1000) == 0x1000) 	*lvl = 4;	\
				else if ((wm->state & 0x2000) == 0x2000) 	*lvl = 5;	\
				else									*lvl = 0;		\
			} while (0)

/********************* VARIABLES DECLARATIONS *****************************/

/*
 *	Make a temp array of wiimote ids.
 *	Here I only anticipate connecting up to
 *	two wiimotes.  Each wiimote connected
 *	will get one of these ids.
 */
static wiimote** wiimotes;
static int nbMaxWiimotes;

/****************** GENERAL FUNCTIONS DEFINITIONS *************************/

/**
 * Connect to a wiimote or wiimotes once an address is known.
 * @param nbWiimotes The number of wiimotes.
 * @return The number of wiimotes that successfully connected.
 */
JNIEXPORT jint JNICALL Java_wiiusej_WiiUseApi_connect
(JNIEnv *env, jobject obj, jint nbWiimotes) {
	return wiiuse_connect(wiimotes, nbWiimotes);
}

/**
 * Find a wiimote or wiimotes.
 * @param nbMaxWiimotes The number of wiimotes.
 * @param timeout The number of seconds before the search times out.
 * @return The number of wiimotes found.
 */
JNIEXPORT jint JNICALL Java_wiiusej_WiiUseApi_find
(JNIEnv *env, jobject obj, jint nbMaxWiimotes, jint timeout) {
	return wiiuse_find(wiimotes, nbMaxWiimotes, timeout);
}

/**
 * Initialize an array of wiimote structures (for the C side of the library).
 * @param nbPossibleWiimotes size of the array.
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_init
(JNIEnv *env, jobject obj, jint nbPossibleWiimotes) {
	wiimotes = wiiuse_init(nbPossibleWiimotes);
	nbMaxWiimotes = nbPossibleWiimotes;
}

/**
 * Close connection to the wiimote with the given id.
 * 
 * @param id the id of the wiimote to disconnect.Must be 1 or 2.
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_closeConnection
(JNIEnv *env, jobject obj, jint id) {
	wiiuse_disconnect(wiimotes[id]);
}

/**
 * Get unique id of a wiimote in the wiimotes array.
 * Please make sure you call an existing index with a 
 * wiimote initialized at this index,
 * other wise you'll get a wrong value.
 * @param index index of the wiimote in the wiimotes array. 
 */
JNIEXPORT jint JNICALL Java_wiiusej_WiiUseApi_getUnId
(JNIEnv *env, jobject obj, jint index) {
	return wiimotes[index]->unid;
}

/**
 * Shutdown api.
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_cleanUp
(JNIEnv *env, jobject obj) {
	wiiuse_cleanup(wiimotes, nbMaxWiimotes);
}

/**
 * Activate rumble for the wiimote with the given id.
 * @param id the id of the wiimote.Must be 1 or 2.
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_activateRumble
(JNIEnv *env, jobject obj, jint id) {
	wiiuse_rumble(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), 1);
}

/**
 * Deactivate rumble for the wiimote with the given id.
 * @param id the id of the wiimote.Must be 1 or 2.
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_deactivateRumble
(JNIEnv *env, jobject obj, jint id) {
	wiiuse_rumble(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), 0);
}

/**
 * Activate IR TRacking for the wiimote with the given id.
 * @param id the id of the wiimote.
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_activateIRTracking
(JNIEnv *env, jobject obj, jint id) {
	wiiuse_set_ir(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), 1);
}

/**
 * Deactivate IR TRacking for the wiimote with the given id.
 * @param id the id of the wiimote.Must be 1 or 2.
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_deactivateIRTracking
(JNIEnv *env, jobject obj, jint id) {
	wiiuse_set_ir(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), 0);
}

/**
 * Activate Motion Sensing for the wiimote with the given id.
 * @param id the id of the wiimote.Must be 1 or 2.
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_activateMotionSensing
(JNIEnv *env, jobject obj, jint id) {
	wiiuse_motion_sensing(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), 1);
}

/**
 * Deactivate Motion Sensing for the wiimote with the given id.
 * @param id the id of the wiimote.Must be 1 or 2.
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_deactivateMotionSensing
(JNIEnv *env, jobject obj, jint id) {
	wiiuse_motion_sensing(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), 0);
}

/**
 * Set wiimote leds status.
 * @param id the id of the wiimote concerned
 * @param led1 status of led1: True=ON, False=OFF
 * @param led2 status of led2: True=ON, False=OFF
 * @param led3 status of led3: True=ON, False=OFF
 * @param led4 status of led4: True=ON, False=OFF
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_setLeds
(JNIEnv *env, jobject obj, jint id, jboolean led1, jboolean led2, jboolean led3, jboolean led4) {
	int leds = 0;

	if (led1) leds |= WIIMOTE_LED_1;
	if (led2) leds |= WIIMOTE_LED_2;
	if (led3) leds |= WIIMOTE_LED_3;
	if (led4) leds |= WIIMOTE_LED_4;

	wiiuse_set_leds(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), leds);
}

/**
 * Set how many degrees an angle must change to generate an event.
 * @param id id of the wiimote concerned
 * @param thresh minimum angle detected by an event
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_setOrientThreshold
(JNIEnv *env, jobject obj, jint id, jfloat thresh) {
	wiiuse_set_orient_threshold(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), thresh);
}

/**
 * Set how much acceleration must change to generate an event.
 * @param id id of the wiimote concerned
 * @param val minimum value detected by an event
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_setAccelThreshold
(JNIEnv *env, jobject obj, jint id, jint val) {
	wiiuse_set_accel_threshold(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), val);
}

/**
 * Set alpha smoothing parameter for the given id.
 * @param id id of the wiimote concerned
 * @param value alpha smoothing value
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_setAlphaSmoothing
(JNIEnv *env, jobject obj, jint id, jfloat val) {
	wiiuse_set_smooth_alpha(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), val);
}

/**
 * Try to resync with the wiimote by starting a new handshake.
 * @param id id of the wiimote concerned
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_reSync
(JNIEnv *env, jobject obj, jint id) {
	wiiuse_resync(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id));
}

/**
 * Make the the accelerometers give smoother results.
 * This is set by default.
 * @param id the id of the wiimote concerned
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_activateSmoothing
(JNIEnv *env, jobject obj, jint id) {
	wiiuse_set_flags(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), WIIUSE_SMOOTHING, 0);
}

/**
 * Make the the accelerometers give raw results.
 * @param id the id of the wiimote concerned
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_deactivateSmoothing
(JNIEnv *env, jobject obj, jint id) {
	wiiuse_set_flags(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), 0, WIIUSE_SMOOTHING);
}

/**
 * Make the wiimote generate an event each time we poll.
 * Not set by default.
 * @param id the id of the wiimote concerned
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_activateContinuous
(JNIEnv *env, jobject obj, jint id) {
	wiiuse_set_flags(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), WIIUSE_CONTINUOUS, 0);
}

/**
 * Make the wiimote generate an event only when there is one.
 * (default behavior)
 * @param id the id of the wiimote concerned
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_deactivateContinuous
(JNIEnv *env, jobject obj, jint id) {
	wiiuse_set_flags(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), 0, WIIUSE_CONTINUOUS);
}

/**
 * Notify wiiuse that your screen has an aspect ratio of 4/3.
 * @param id the id of the wiimote concerned
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_setScreenRatio43
(JNIEnv *env, jobject obj, jint id) {
	wiiuse_set_aspect_ratio(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), WIIUSE_ASPECT_4_3);
}

/**
 * Notify wiiuse that your screen has an aspect ratio of 16/9.
 * @param id the id of the wiimote concerned
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_setScreenRatio169
(JNIEnv *env, jobject obj, jint id) {
	wiiuse_set_aspect_ratio(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), WIIUSE_ASPECT_4_3);
}

/**
 * Notify wiiuse that the sensor bar is above your screen.
 * @param id the id of the wiimote concerned
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_setSensorBarAboveScreen
(JNIEnv *env, jobject obj, jint id) {
	wiiuse_set_ir_position(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), WIIUSE_IR_ABOVE);
}

/**
 * Notify wiiuse that the sensor bar is below your screen.
 * @param id the id of the wiimote concerned
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_setSensorBarBelowScreen
(JNIEnv *env, jobject obj, jint id) {
	wiiuse_set_ir_position(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), WIIUSE_IR_BELOW);
}

/**
 * Set virtual screen resolution. It is used to automatically 
 * compute the position of a cursor on this virtual screen
 *  using the sensor bar. These results come in the IREvent.
 * @param id the id of the wiimote concerned
 * @param x x resolution.
 * @param y y resolution.
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_setVirtualScreenResolution
(JNIEnv *env, jobject obj, jint id, jint x, jint y) {
	wiiuse_set_ir_vres(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), x, y);
}

/**
 * Get status from the wiimotes and send it through call backs.
 * 
 * @param id the id of the wiimote. Must be 1 or 2.
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_getStatus
(JNIEnv *env, jobject obj, jint id) {
	wiiuse_status(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id));
}

/**
 * Set the normal and expansion handshake timeouts.
 * 
 * @param id
 *            the id of the wiimote concerned.
 * @param normalTimeout
 *            The timeout in milliseconds for a normal read.
 * @param expansionTimeout
 *            The timeout in millisecondsd to wait for an expansion
 *            handshake.
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_setTimeout
(JNIEnv *env, jobject obj, jint id, jshort normalTimeout, jshort expansionTimeout) {
	wiiuse_set_timeout(wiimotes, nbMaxWiimotes, normalTimeout, expansionTimeout);
}

/**
 * Set the IR sensitivity.
 * 
 * @param id
 *            the id of the wiimote concerned.
 * @param level
 *            1-5, same as Wii system sensitivity setting. If the level is <
 *            1, then level will be set to 1. If the level is > 5, then
 *            level will be set to 5.
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_setIrSensitivity
(JNIEnv *env, jobject obj, jint id, jint level) {
	wiiuse_set_ir_sensitivity(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), level);
}

/**
 * Set how many degrees an angle must change to generate an event for the nunchuk.
 * @param id id of the wiimote concerned
 * @param thresh minimum angle detected by an event
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_setNunchukOrientationThreshold
(JNIEnv *env, jobject obj, jint id, jfloat thresh) {
	wiiuse_set_nunchuk_orient_threshold(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), thresh);
}

/**
 * Set how much acceleration must change to generate an event for the nunchuk.
 * @param id id of the wiimote concerned
 * @param val minimum value detected by an event
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_setNunchukAccelerationThreshold
(JNIEnv *env, jobject obj, jint id, jint val) {
	wiiuse_set_nunchuk_accel_threshold(wiiuse_get_by_id(wiimotes, nbMaxWiimotes, id), val);
}

/**
 * Force the bluetooth stack type.(useful only for windows)
 * 
 * @param bluetoothStackType
 *            must be WiiUseApi.WIIUSE_STACK_UNKNOWN or WiiUseApi.WIIUSE_STACK_MS or
 *            WiiUseApi.WIIUSE_STACK_BLUESOLEIL.
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_windowsSetBluetoothStack
(JNIEnv *env, jobject obj, jint bluetoothStackType) {
	if (bluetoothStackType == 0) {
		wiiuse_set_bluetooth_stack(wiimotes, nbMaxWiimotes, WIIUSE_STACK_UNKNOWN);
	} else if (bluetoothStackType == 1) {
		wiiuse_set_bluetooth_stack(wiimotes, nbMaxWiimotes, WIIUSE_STACK_MS);
	} else if (bluetoothStackType == 2) {
		wiiuse_set_bluetooth_stack(wiimotes, nbMaxWiimotes, WIIUSE_STACK_BLUESOLEIL);
	}
}

/**
 * Get status and values from the wiimotes and send it through callbacks.
 * @param wim the wiimote object to fill with the datas.
 */
JNIEXPORT void JNICALL Java_wiiusej_WiiUseApi_specialPoll
(JNIEnv *env, jobject obj, jobject gath) {

	/* Variables Declarations */
	int i;
	short leds = 0;
	jclass cls = (*env)->GetObjectClass(env, gath);
	jmethodID mid;

	if (wiiuse_poll(wiimotes, nbMaxWiimotes)) {
		/*
		 *	This happens if something happened on any wiimote.
		 *	So go through each one and check if anything happened.
		 */
		for (i=0; i < nbMaxWiimotes; ++i) {
			switch (wiimotes[i]->event) {
				case WIIUSE_EVENT:
				/* a generic event occured */
				mid = (*env)->GetMethodID(env, cls, "prepareWiiMoteEvent", "(ISSS)V");
				if (mid == 0) {
					return;
				}
				(*env)->CallVoidMethod(env, gath, mid, wiimotes[i]->unid, wiimotes[i]->btns,
						wiimotes[i]->btns_released, wiimotes[i]->btns_held);
				/*
				 *	If IR tracking is enabled then print the coordinates
				 *	on the virtual screen that the wiimote is pointing to.
				 *
				 *	Also make sure that we see at least 1 dot.
				 */
				if (WIIUSE_USING_IR(wiimotes[i])) {
					int a;
					WIIUSE_GET_IR_SENSITIVITY_CORRECTED(wiimotes[i], &a);

					mid = (*env)->GetMethodID(env, cls, "prepareIRevent",
							"(IIFIIIIIISSSF)V");
					if (mid == 0) {
						return;
					}
					(*env)->CallVoidMethod(env, gath, mid,
							wiimotes[i]->ir.x, wiimotes[i]->ir.y, wiimotes[i]->ir.z,
							wiimotes[i]->ir.ax, wiimotes[i]->ir.ay,
							wiimotes[i]->ir.vres[0], wiimotes[i]->ir.vres[1],
							wiimotes[i]->ir.offset[0], wiimotes[i]->ir.offset[1],
							wiimotes[i]->ir.pos, wiimotes[i]->ir.aspect,
							a , wiimotes[i]->ir.distance);

					mid = (*env)->GetMethodID(env, cls, "addIRPointToPreparedWiiMoteEvent",
							"(IISSS)V");
					if (mid == 0) {
						return;
					}
					/* go through each of the 4 possible IR sources */
					for (a=0; a < 4; a++) {
						/* check if the source is visible */
						if (wiimotes[i]->ir.dot[a].visible) {
							(*env)->CallVoidMethod(env, gath, mid,
									wiimotes[i]->ir.dot[a].x, wiimotes[i]->ir.dot[a].y,
									wiimotes[i]->ir.dot[a].rx, wiimotes[i]->ir.dot[a].ry,
									wiimotes[i]->ir.dot[a].size);
						}
					}
				}

				/* Motion Sensing */
				if (WIIUSE_USING_ACC(wiimotes[i])) {
					/* set orientation and gravity force */
					mid = (*env)->GetMethodID(env, cls,
							"addMotionSensingValues", "(FIZFFFFFFFFFSSS)V");
					if (mid == 0) {
						return;
					}
					(*env)->CallVoidMethod(env, gath, mid,
							wiimotes[i]->orient_threshold, wiimotes[i]->accel_threshold,
							WIIMOTE_IS_FLAG_SET(wiimotes[i],WIIUSE_SMOOTHING), wiimotes[i]->accel_calib.st_alpha,
							wiimotes[i]->orient.roll, wiimotes[i]->orient.pitch, wiimotes[i]->orient.yaw,
							wiimotes[i]->orient.a_roll, wiimotes[i]->orient.a_pitch,
							wiimotes[i]->gforce.x, wiimotes[i]->gforce.y, wiimotes[i]->gforce.z,
							wiimotes[i]->accel.x, wiimotes[i]->accel.y, wiimotes[i]->accel.z);
				}

				/* Expansions support support*/
				if (WIIUSE_USING_EXP(wiimotes[i])) {
					/* Nunchuk support */
					if (wiimotes[i]->exp.type == EXP_NUNCHUK) {
						/* put nunchuk values in wiimote generic event */
						mid = (*env)->GetMethodID(env, cls,
								"addNunchunkEventToPreparedWiimoteEvent", "(SSSFIZFFFFFFFFFSSSFFSSSSSS)V");
						if (mid == 0) {
							return;
						}
						struct nunchuk_t* nc = (nunchuk_t*)&wiimotes[i]->exp.nunchuk;

						(*env)->CallVoidMethod(env, gath, mid,
								/* buttons */
								nc->btns,nc->btns_released,nc->btns_held,
								/* motion sensing */
								nc->orient_threshold,nc->accel_threshold,
								WIIMOTE_IS_FLAG_SET(wiimotes[i],WIIUSE_SMOOTHING),nc->accel_calib.st_alpha,
								nc->orient.roll, nc->orient.pitch, nc->orient.yaw,
								nc->orient.a_roll, nc->orient.a_pitch,
								nc->gforce.x, nc->gforce.y, nc->gforce.z,
								nc->accel.x, nc->accel.y, nc->accel.z,
								/* joystick */
								nc->js.ang,nc->js.mag,
								nc->js.max.x,nc->js.max.y,
								nc->js.min.x,nc->js.min.y,
								nc->js.center.x,nc->js.center.y);
					} else if (wiimotes[i]->exp.type == EXP_GUITAR_HERO_3) {
						/* put guitar hero values in wiimote generic event */
						mid = (*env)->GetMethodID(env, cls,
								"addGuitarHeroEventToPreparedWiimoteEvent", "(SSSFFFSSSSSS)V");
						if (mid == 0) {
							return;
						}
						struct guitar_hero_3_t* gh = (guitar_hero_3_t*)&wiimotes[i]->exp.gh3;

						(*env)->CallVoidMethod(env, gath, mid,
								/* buttons */
								gh->btns,gh->btns_released,gh->btns_held,
								/* whammy bar */
								gh->whammy_bar,
								/* joystick */
								gh->js.ang,gh->js.mag,
								gh->js.max.x,gh->js.max.y,
								gh->js.min.x,gh->js.min.y,
								gh->js.center.x,gh->js.center.y);
					}if (wiimotes[i]->exp.type == EXP_CLASSIC) {
						/* put classic controller values in wiimote generic event */
						mid = (*env)->GetMethodID(env, cls,
								"addClassicControllerEventToPreparedWiimoteEvent", "(SSSFFFFSSSSSSFFSSSSSS)V");
						if (mid == 0) {
							return;
						}
						struct classic_ctrl_t* cl = (classic_ctrl_t*)&wiimotes[i]->exp.classic;

						(*env)->CallVoidMethod(env, gath, mid,
								/* buttons */
								cl->btns,cl->btns_released,cl->btns_held,
								/* shoulder buttons */
								cl->r_shoulder,cl->l_shoulder,
								/* joystick left*/
								cl->ljs.ang,cl->ljs.mag,
								cl->ljs.max.x,cl->ljs.max.y,
								cl->ljs.min.x,cl->ljs.min.y,
								cl->ljs.center.x,cl->ljs.center.y,
								/* joystick right */
								cl->rjs.ang,cl->rjs.mag,
								cl->rjs.max.x,cl->rjs.max.y,
								cl->rjs.min.x,cl->rjs.min.y,
								cl->rjs.center.x,cl->rjs.center.y);
					}
				}

				/* add generic event to java object used to gather events in c environment */
				mid = (*env)->GetMethodID(env, cls, "addWiimoteEvent",
						"()V");
				if (mid == 0) {
					return;
				}
				(*env)->CallVoidMethod(env, gath, mid);
				break;

				case WIIUSE_DISCONNECT:
				/* the wiimote disconnected */
				mid = (*env)->GetMethodID(env, cls, "addDisconnectionEvent", "(I)V");
				if (mid == 0) {
					return;
				}
				(*env)->CallVoidMethod(env, gath, mid, wiimotes[i]->unid);
				break;

				case WIIUSE_UNEXPECTED_DISCONNECT:
				/* the wimote disconnected */
				mid = (*env)->GetMethodID(env, cls, "addDisconnectionEvent", "(I)V");
				if (mid == 0) {
					return;
				}
				(*env)->CallVoidMethod(env, gath, mid, wiimotes[i]->unid);
				break;

				case WIIUSE_NUNCHUK_INSERTED:
				/* the nunchuk was just connected */
				mid = (*env)->GetMethodID(env, cls, "addNunchukInsertedEvent", "(I)V");
				if (mid == 0) {
					return;
				}
				(*env)->CallVoidMethod(env, gath, mid, wiimotes[i]->unid);
				break;

				case WIIUSE_NUNCHUK_REMOVED:
				/* the nunchuk disconnected */
				mid = (*env)->GetMethodID(env, cls, "addNunchukRemovedEvent", "(I)V");
				if (mid == 0) {
					return;
				}
				(*env)->CallVoidMethod(env, gath, mid, wiimotes[i]->unid);
				break;

				case WIIUSE_GUITAR_HERO_3_CTRL_INSERTED:
				/* the guitar hero was just connected */
				mid = (*env)->GetMethodID(env, cls, "addGuitarHeroInsertedEvent", "(I)V");
				if (mid == 0) {
					return;
				}
				(*env)->CallVoidMethod(env, gath, mid, wiimotes[i]->unid);
				break;

				case WIIUSE_GUITAR_HERO_3_CTRL_REMOVED:
				/* the guitar hero disconnected */
				mid = (*env)->GetMethodID(env, cls, "addGuitarHeroRemovedEvent", "(I)V");
				if (mid == 0) {
					return;
				}
				(*env)->CallVoidMethod(env, gath, mid, wiimotes[i]->unid);
				break;

				case WIIUSE_CLASSIC_CTRL_INSERTED:
				/* the classic controller was just connected */
				mid = (*env)->GetMethodID(env, cls, "addClassicControllerInsertedEvent", "(I)V");
				if (mid == 0) {
					return;
				}
				(*env)->CallVoidMethod(env, gath, mid, wiimotes[i]->unid);
				break;

				case WIIUSE_CLASSIC_CTRL_REMOVED:
				/* the classic controller disconnected */
				mid = (*env)->GetMethodID(env, cls, "addClassicControllerRemovedEvent", "(I)V");
				if (mid == 0) {
					return;
				}
				(*env)->CallVoidMethod(env, gath, mid, wiimotes[i]->unid);
				break;

				case WIIUSE_STATUS:
				/* a status event occured */
				mid = (*env)->GetMethodID(env, cls, "addStatusEvent", "(IZFSZIZZZZ)V");
				if (mid == 0) {
					return;
				}
				/* LEDS */
				if (WIIUSE_IS_LED_SET(wiimotes[i], 1)) leds += 1;
				if (WIIUSE_IS_LED_SET(wiimotes[i], 2)) leds += 2;
				if (WIIUSE_IS_LED_SET(wiimotes[i], 3)) leds += 4;
				if (WIIUSE_IS_LED_SET(wiimotes[i], 4)) leds += 8;

				(*env)->CallVoidMethod(env, gath, mid,
						wiimotes[i]->unid, WIIMOTE_IS_SET(wiimotes[i], WIIMOTE_STATE_CONNECTED),
						wiimotes[i]->battery_level, leds, WIIUSE_USING_SPEAKER(wiimotes[i]),
						wiimotes[i]->exp.type,WIIMOTE_IS_SET(wiimotes[i], WIIMOTE_STATE_RUMBLE),
						WIIMOTE_IS_FLAG_SET(wiimotes[i],WIIUSE_CONTINUOUS),
						WIIUSE_USING_IR(wiimotes[i]),WIIUSE_USING_ACC(wiimotes[i]));
				break;

				default:
				break;
			}
		}
	}
}
