/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.utils;

import java.util.HashMap;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

/**
 * Add your docs here.
 */
public class SmartShuffleboardTab {
    private HashMap<String, SimpleWidget> widgetMap = new HashMap();    // “field name” à widget
    private ShuffleboardTab tab;
     
    SmartShuffleboardTab(String tabName) 
    {
        tab = Shuffleboard.getTab(tabName);
    }
         
    public SimpleWidget getWidget(String fieldName)     // return widget handle
    {
        return widgetMap.get(fieldName);
    }
         
    public void add(String fieldName, Object value)   //primitive
    {
        if (widgetMap.containsKey(fieldName))
        {
            NetworkTableEntry ntEntry= widgetMap.get(fieldName).getEntry();
            ntEntry.setValue(value);
        }
        else
        {
            SimpleWidget widget = tab.add(fieldName, value); 
            widgetMap.put(fieldName, widget);
        }
    }
 
}
