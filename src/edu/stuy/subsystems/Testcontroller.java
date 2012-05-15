package edu.stuy.subsystems;

import edu.stuy.*;
import edu.stuy.commands.*;

import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.PWM;

/**
 *
 * @author Pockets
 */
public class Testcontroller extends Subsystem implements TestcontrollerControls {
    public PWM testcontroller; // Create a PWM instance
    
    public Testcontroller(int channel) {
         testcontroller = new PWM(channel); // Construct the PWM instance
         initController(); // Configure the PWM instance
    }
    
    public Testcontroller(int slot, int channel) {
         testcontroller = new PWM(slot, channel); // Construct the PWM instance
         initController(); // Configure the PWM instance
    }
    
    public void initController() {
         testcontroller.setBounds(kMaxDutyCycle, kUpperDeadband, kNeutral, kLowerDeadband, kMinDutyCycle); // 
         testcontroller.setPeriodMultiplier(PWM.PeriodMultiplier.k2X);
         testcontroller.setRaw(kNeutral); //
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new StopTestcontroller()); //
        
    }
    
    public void setDutyCycle(int value) {
        testcontroller.setRaw(value);
    }
}


