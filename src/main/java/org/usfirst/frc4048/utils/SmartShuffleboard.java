/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.utils;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

/**
 * This is a wrapper around the Shuffleboard class to make it easier to add data to shuffleboard.
 * Adding and updating a value is done in the same manner, and there's no need to handle networkTable entries.
 * Data can be displayed in its own widget or items can be grouped into a list widget.
 * Example:
 * 
 *                          tab name   list name    field name       value
 *     SmartShuffleboard.put("Drive", "encoders", "Front Right", steerFR.getSelectedSensorPosition(0));
 *     SmartShuffleboard.put("Drive", "encoders", "Front Left", steerFL.getSelectedSensorPosition(0));
 *     SmartShuffleboard.put("Drive", "encoders", "Rear Right", steerRR.getSelectedSensorPosition(0));
 *     SmartShuffleboard.put("Drive", "encoders", "Rear Left", steerRL.getSelectedSensorPosition(0));
 * 
 *                           tab name   field name    value
 *     SmartShuffleboard.put("Drive", "Gyro", getGyro());
 */
public class SmartShuffleboard {

    private final static Map<String, SmartShuffleboardTab> smartTabMap = new HashMap<String, SmartShuffleboardTab>();

    public static void put(String tabName, String fieldName, Object value)    // value is primitive
    {
        SmartShuffleboardTab smartTab = getOrCreateTab(tabName);
        smartTab.put(fieldName, value);
    }

    public static void put(String tabName, String layoutName, String fieldName, Object value)    // value is primitive
    {
        SmartShuffleboardTab smartTab = getOrCreateTab(tabName);
        smartTab.put(fieldName, layoutName, value);
    }

    public static void putCommand(String tabName, String fieldName, Command cmd)    // value is primitive
    {
        SmartShuffleboardTab smartTab = getOrCreateTab(tabName);
        smartTab.putCommand(fieldName, cmd);
    }

    public static SimpleWidget getWidget(String tabName, String fieldName)
    {
        SmartShuffleboardTab tab = smartTabMap.get(tabName);

        if (tab == null)
        {
            return null;
        }
        else
        {
            return tab.getWidget(fieldName);
        }
    }

    public static ShuffleboardLayout getLayout(String tabName, String layoutName)
    {
        SmartShuffleboardTab tab = smartTabMap.get(tabName);

        if (tab == null)
        {
            return null;
        }
        else
        {
            return tab.getLayout(layoutName);
        }
    }

    private static SmartShuffleboardTab getOrCreateTab(String tabName) {
        SmartShuffleboardTab smartTab = smartTabMap.get(tabName);

        if (smartTab == null) {
            smartTab = new SmartShuffleboardTab(tabName);
            smartTabMap.put(tabName, smartTab);
        }
        return smartTab;
    }

}
