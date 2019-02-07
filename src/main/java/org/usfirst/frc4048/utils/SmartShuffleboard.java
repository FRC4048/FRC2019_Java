/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.utils;

import java.util.HashMap;

import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

/**
 * Add your docs here.
 */
public class SmartShuffleboard {

    private static HashMap<String, SmartShuffleboardTab> smartTabMap = new HashMap<String, SmartShuffleboardTab>(); 

    public static void add(String tabName, String fieldName, Object value)    // value is primitive
    {
        SmartShuffleboardTab smartTab;

        if (smartTabMap.containsKey(tabName))
        {
            smartTab = smartTabMap.get(tabName);
        }
        else
        {
            smartTab = new SmartShuffleboardTab(tabName);
            smartTabMap.put(tabName, smartTab);
        }
        smartTab.add(fieldName, value);
    }

    public static void add(String tabName, String layoutName, String fieldName, Object value)    // value is primitive
    {
        SmartShuffleboardTab smartTab;

        if (smartTabMap.containsKey(tabName))
        {
            smartTab = smartTabMap.get(tabName);
        }
        else
        {
            smartTab = new SmartShuffleboardTab(tabName);
            smartTabMap.put(tabName, smartTab);
        }
        smartTab.add(fieldName, layoutName, value);
    }


    public static SimpleWidget getWidget(String tabName, String fieldName)
    {
        if (!smartTabMap.containsKey(tabName))
        {
            return null;
        }
        else
        {
            return  smartTabMap.get(tabName).getWidget(fieldName);
        }
    }
}
