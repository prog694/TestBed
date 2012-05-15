package edu.stuy.commands;

import edu.stuy.subsystems.*;
import edu.stuy.TestcontrollerControls;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Pockets
 * 
 * Use this class to test the response of a speed controller to all 256 PWM duty
 * cycles. Basic data about the Victor's response pattern can be found here:
 * 
 * http://www.ifirobotics.com/forum/viewtopic.php?t=317
 * 
 */
public class RunTestcontroller extends CommandBase implements TestcontrollerControls {
    int[] controlSignals = new int[256];
    double[] controlOutputs = new double[controlSignals.length];
    double wait;
    int executeSignal;
    int executeIncrement;
    
    /**
     * Constructor requires the existence of a testcontroller to test and a 
     * voltageMonitor to record the output of the testcontroller.
     * 
     * It also creates the contents of controlSignals, the integers in [0, 255].
     */
    public RunTestcontroller() {
        requires(testcontroller);
        requires(voltageMonitor);
        
        for (int i = 0; i < controlSignals.length; i++) 
            controlSignals[i] = i;
    }
    
    protected void initialize() { // Sets the values of the constants to be used
        wait = .1;
        
        executeSignal = kNeutral; // The PWM duty cycle to start from while testing
        executeIncrement = 1; // Begin by increasing the PWM duty cycle by 1
    }

    protected void execute() {      
        testcontroller.setDutyCycle(controlSignals[executeSignal]);
        //TODO:  implement something to show what the duty cycle has been set to?
        Timer.delay(wait);
        
        if ((executeIncrement == 1) && (executeSignal == 256)) {
            executeIncrement = -1; // Decrease executeSignal when it is next incremented because it has hit the upper bound of the signal range
        } else if ((executeIncrement == -1) && (executeSignal == 0)) {
            executeIncrement = 1; // Increase executeSignal when it is next incremented because it has hit the lower bound of the signal range
        }
        
        executeSignal += executeIncrement; // Increase/decrease executeSignal
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        int controlSignal;
        testcontroller.setDutyCycle(0); //set full reverse to begin the test
        
        for (int i = 0; i < controlSignals.length; i++) {
            controlSignal = controlSignals[i];
            
            testcontroller.setDutyCycle(controlSignal); //send control value to testcontroller
            Timer.delay(.5); //wait half a second for testcontroller output to stabilize
            controlOutputs[i] = voltageMonitor.getVoltage(); //read the testcontroller response
        }
        testcontroller.setDutyCycle(kNeutral); //bring the testcontroller to a stop   
        
        //TODO: write/print out the contents of controlOutputs[]
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
