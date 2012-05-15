/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.command.*;
/**
 *
 * @author Pockets
 */
public class VoltageMonitor extends Subsystem{    
    private AnalogChannel m_analog;

    public VoltageMonitor(int channel) {
        m_analog = new AnalogChannel(channel);
    }
    
    public double getVoltage() {
        return m_analog.getVoltage();
    }
    
    public void initDefaultCommand() {}
}