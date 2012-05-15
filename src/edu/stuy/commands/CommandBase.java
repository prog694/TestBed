/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.*;

import edu.stuy.*;
import edu.stuy.subsystems.*;

/**
 *
 * @author Pockets
 */
public abstract class CommandBase extends CommandGroup {
    public static Testcontroller testcontroller;
    public static VoltageMonitor voltageMonitor;
    
    static {
           testcontroller = new Testcontroller(TestcontrollerControls.testcontroller1_channel);
           voltageMonitor = new VoltageMonitor(TestcontrollerControls.voltageMonitor_channel);
    }
    
    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
