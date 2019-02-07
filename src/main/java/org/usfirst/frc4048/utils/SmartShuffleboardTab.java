/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4048.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

/**
 * Add your docs here.
 */
public class SmartShuffleboardTab {
    private Map<String, SimpleWidget> widgetMap = new HashMap();    // “field name” à widget
    private ShuffleboardTab tab;
     
    SmartShuffleboardTab(String tabName) 
    {
        tab = Shuffleboard.getTab(tabName);
    }
         
    public SimpleWidget getWidget(String fieldName)     // return widget handle
    {
        return widgetMap.get(fieldName);
    }

    public ShuffleboardLayout getLayout(String layoutName)     // return layout handle
    {
        try {
            return tab.getLayout(layoutName);
        }   
        catch (Exception noSuchElementException)
        {
            return null;
        }
    }
         
    public void put(String fieldName, Object value)   //primitive
    {
        SimpleWidget widget = widgetMap.get(fieldName);
        if (widget != null)
        {
            NetworkTableEntry ntEntry= widget.getEntry();
            ntEntry.setValue(value);
        }
        else
        {
            widget = tab.add(fieldName, value);
            widgetMap.put(fieldName, widget);
        }
    }
 
    public void put(String fieldName, String layoutName, Object value)   //primitive
    {
        ShuffleboardLayout layout;
        try {
            layout = tab.getLayout(layoutName);
        } catch (NoSuchElementException ex) {
            layout = tab.getLayout(layoutName, BuiltInLayouts.kList);
            // layout initialization here
        }

        SimpleWidget widget = widgetMap.get(fieldName);
        if (widget != null)
        {
            NetworkTableEntry ntEntry= widget.getEntry();
            ntEntry.setValue(value);
        }
        else
        {
            widget = layout.add(fieldName, value);
            widgetMap.put(fieldName, widget);
        }
    }

}
