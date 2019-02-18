/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.utils;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Add your docs here.
 */
public class DoubleSolenoidUtil {
    private Solenoid fwdPiston;
    private Solenoid revPiston;

    public enum State {
        forward, reverse, off;
    }

    public DoubleSolenoidUtil(int pcm, int fwd, int rev) {
        fwdPiston = new Solenoid(pcm, fwd);
        revPiston = new Solenoid(pcm, rev);
    }

    public void set(State state) {
        switch (state) {
        case forward:
            fwdPiston.set(true);
            revPiston.set(false);
            break;
        case reverse:
            fwdPiston.set(false);
            revPiston.set(true);
            break;
        case off:
            fwdPiston.set(false);
            revPiston.set(false);
            break;
        }
    }
}
