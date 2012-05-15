/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.stuy.TestcontrollerControls;

/**
 *
 * @author Pockets
 */
public class StopTestcontroller extends CommandBase implements TestcontrollerControls{
    
    public StopTestcontroller() {
        requires(testcontroller);
    }

    protected void initialize() {
        testcontroller.setDutyCycle(kNeutral);
    }

    protected void execute() {
        testcontroller.setDutyCycle(kNeutral);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        testcontroller.setDutyCycle(kNeutral);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        testcontroller.setDutyCycle(kNeutral);
    }
}
