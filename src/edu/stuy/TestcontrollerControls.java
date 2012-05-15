/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy;

/**
 *
 * @author Pockets
 */
public interface TestcontrollerControls extends RobotMap {
    int kMaxDutyCycle = 255; //---- Maximum PWM duty cycle that elicits a response
    int kUpperDeadband = 126; //=== Upper bound of the speed controller's deadband
    int kNeutral = 126; //--------- Center of the deadband, default neutral value
    int kLowerDeadband = 126; //=== Lower bound of the speed controller's deadband
    int kMinDutyCycle = 0; //------ Minimum PWM duty cycle that elicits a response
    
    
}
