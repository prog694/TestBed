/*----------------------------------------------------------------------------*/
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/* @author Pockets, FRC694                                                    */
/*----------------------------------------------------------------------------*/

package edu.stuy;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Pockets
 */
public class LinearVictor extends Victor {
    public LinearVictor(final int channel) {
        super(channel);
    }
    
    public LinearVictor(final int slot, final int channel) {
        super(slot, channel);
    }
    
    /* Set the PWM value.
     *
     * The PWM value is set using a range of -1.0 to 1.0, appropriately
     * scaling the value for the FPGA.
     *
     * The Victor's output curve with respect to the PWM signal, however,
     * is not linear. This means that the graph of the Victor output with respect
     * to speed (the argument of setLinear()) is nonlinear. For ease of control,
     * however, because the standard way to control a Victor is to do
     * set(Joystick.getY());, we want this graph to be linear.
     *
     * To force the Victor to produce a linear response, this can be done by
     * sending a non-linear signal to the Victor. Specifically, we scale speed
     * into another variable linearFeed, which contains a value such that when
     * the Victor takes linearFeed as an input, the graph of the Victor output
     * with respect to speed will be linear.
     *
     * To do this, imagine there is a function victor() which takes an argument
     * speed and returns a value voltage. Let the function victorNorm() mimic
     * the behavior of victor(), but with normalized values (that is, scaled to
     * [-1.0, 1.0]). If we reflect victorNorm() over y=x, we obtain the inverse
     * of victorNorm(); let this function be called invertedFeed().
     *
     * What invertedFeed() now does is takes an input speed and scales it to a
     * value that when fed into victor() produces a linear graph. We know this
     * because the graph of victorNorm(invertedFeed()) is linear (specifically,
     * it's the graph y = x, because the output of victorNorm(invertedFeed()) is
     * whatever the input was), and victorNorm() merely represents the normalized
     * graph of victor().
     *
     * Therefore, what we want setLinear to do is send invertedFeed(speed) to the
     * Victor so its output is linear. In the method, this is referred to as
     * linearFeed.
     *
     * The guts of the linearFeed function were obtained by fitting a curve
     * to the normalized plot of input data vs. Victor output. The victor itself
     * contains a natural deadband for neutral values, so instead of using a
     * polynomial to approximate the data curve sans deadband, then making use
     * of conditionals to shift the polynomial above and below the deadband, a
     * more direct approach was taken, by building a deadband function into
     * linearFeed. The deadband function used here is a curve that mimics the
     * behavior of sgn(x), scaled to fit the data. The polynomial approximation
     * does the bulk of the work in scaling the speed input to obtain linearFeed.
     * 
     *!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * KEEP IN MIND:
     *      This method has not been tested. While functionally sound, there is 
     *      no guarantee that this method will actually produce the linear
     *      behavior that it is hoped it will produce.
     * 
     *      There is also a possibility that the entire Victor class will have
     *      to be rewritten instead of just extending it because of the way
     *      that the setSpeed() method in PWM works.
     *!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @param speed The speed value between -1.0 and 1.0 to set.
     */
    public void setLinear(double speed) {
        double x = speed; // x is used in place of speed for ease of transferral to graphing programs
        double linearFeed; // value that will be fed to the Victor to produce a linear output

        double k = .001; // Heaviside step function constant
        double a = 5.0;  // Polynomial approximation constant
        double b = 1.0;  // Polynomial approximation constant

        /* Heaviside deadband
         *        1 - e^(-x/k)                   H(x/1.5)
         * H(x) = ------------     heavyReturn = --------
         *        1 + e^(-x/k)                      15   
         */
        double heavyFeed = x/1.5; // stretches the Heaviside graph out along the x-axis
        double heavisideTransform = (1.0 - MathUtils.exp(-heavyFeed/k)) / (1.0 + MathUtils.exp(-heavyFeed/k)); // Modified Heaviside() that mimics sgn(x)
        double heavyReturn = heavisideTransform/15.0; // compresses the Heaviside graph along the y-axis
        
        /* Polynomial approximation
         *        a*x^11 + b*x
         * P(x) = -------------    polyReturn = 1.5*P(x/1.05)
         *            a + b
         */
        double polyFeed = x/1.05; // stretch the graph out along the x-axis
        double polyRegress = ((a * MathUtils.pow(polyFeed,11)) + (b * polyFeed)) /(5.0 + 1.0); // Polynomial approximation of inverse Victor output data
        double polyReturn = polyRegress*1.5; //stretch the graph out along the y-axis

        linearFeed = heavyReturn + polyReturn; // = H(x/1.5)/15 + 1.5*P(x/1.05)

        super.set(linearFeed);
    }
    
    public void set(double speed) {
        super.set(speed);
    }
}
