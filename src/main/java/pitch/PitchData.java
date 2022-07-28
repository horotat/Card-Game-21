/*******************************************************************************
 * Copyright (c) 2014 Gabriel Skantze.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Gabriel Skantze - initial API and implementation
 ******************************************************************************/
package pitch;

import java.util.Locale;

public class PitchData {

	public double pitchHz;
	public double pitchCent;
	public double conf;
	public double time;
	double[] yinBuffer;
	public double signalPower;

	PitchData(double pitchHz, double signalPower, double conf, double time) {
		this.pitchHz = pitchHz;
		if (pitchHz > -1) 
			this.pitchCent = pitchHzToCent(pitchHz);
		else
			this.pitchCent = -1;
		this.conf = conf;
		this.time = time;
		this.signalPower = signalPower;
	}
	
	PitchData(double pitchHz, double signalPower, double conf, double time, double[] yinBuffer) {
		this(pitchHz, signalPower, conf, time);
		this.yinBuffer = yinBuffer;
	}
	
	PitchData(double pitchHz, double signalPower, double conf, double time, double pitchZ) {
		this(pitchHz, signalPower, conf, time);
	}

	private static final double CENT_CONST = 1731.2340490667560888319096172f;
	
    public static double pitchCentToHz(double pitchCent) {
    	return (Math.exp(pitchCent / CENT_CONST) * 110);
	}
    
    public static double pitchHzToCent(double pitchHz) {
    	return (CENT_CONST * Math.log(pitchHz / 110));
	}
    
    @Override
    public String toString() {
    	return String.format(Locale.US,  "hz: %.2f, power: %.2f, conf: %.2f, time: %.2f", pitchHz, signalPower, conf, time);
    }
	
	
}
