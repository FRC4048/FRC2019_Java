/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.triggers;

import org.usfirst.frc4048.utils.SmartShuffleboard;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * Add your docs here.
 */
public class DownDPADTrigger extends Trigger {
  XboxController xboxController;

  public DownDPADTrigger(XboxController xboxController) {
    this.xboxController = xboxController;
  }
  @Override
  public boolean get() {
    if(xboxController.getPOV() <= 195 && xboxController.getPOV() >= 165) {
      return true;
    } else {
      return false;
    }
  }
}
