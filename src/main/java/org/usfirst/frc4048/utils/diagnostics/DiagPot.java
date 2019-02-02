package org.usfirst.frc4048.utils.diagnostics;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DiagPot implements Diagnosable{

    private String name;
    private double minVoltage;
    private double maxVoltage;
    private AnalogPotentiometer pot;
    private NetworkTableEntry networkTableEntry;

    private boolean seenMinVoltage = false;
    private boolean seenMaxVoltage = false;

    public DiagPot(String name, double minVoltage, double maxVoltage, AnalogPotentiometer pot, ShuffleboardTab shuffleboardTab) {
        this.name = name;
        this.minVoltage = minVoltage;
        this.maxVoltage = maxVoltage;
        this.pot = pot;

        networkTableEntry = shuffleboardTab.add(name, false).getEntry();
    }

    @Override
    public void refresh() {
        double potValue = pot.get();
        if (potValue >= maxVoltage) {
            seenMaxVoltage = true;
        }else if (potValue <= minVoltage){
            seenMinVoltage = true;
        }
        SmartDashboard.putNumber("Pot value", potValue);
        networkTableEntry.setBoolean(seenMaxVoltage && seenMinVoltage);
    }

    @Override
    public void reset() {
        seenMinVoltage = seenMaxVoltage = false;
    }
}
