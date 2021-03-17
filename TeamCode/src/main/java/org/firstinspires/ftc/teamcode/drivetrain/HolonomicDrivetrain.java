/**
 * Jacob Bazata (jacobbazata@gmail.com)
 * 9/16/19
 */

package org.firstinspires.ftc.teamcode.drivetrain;

import com.qualcomm.robotcore.util.Range;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.firstinspires.ftc.teamcode.RobotConstants;

/* Fully holonomic drivetrain */
public class HolonomicDrivetrain implements IDrivetrain {
    private double exponent = RobotConstants.ControlExponent;
    private static final RealMatrix INVERSEMATRIX = MatrixUtils.createRealMatrix(new double[][] {
            {1,  1, -1}, // FL
            {1, -1,  1}, // FR
            {1, -1, -1}, // RL
            {1,  1,  1}, // RR
    });

    public HolonomicDrivetrain() {}

    @Override
    public double[] vectorTranslate(double longitudinal, double lateral, double yaw) {
        RealMatrix VelocityColumn = MatrixUtils.createColumnRealMatrix(new double[] {longitudinal, lateral, yaw});
        double[] product = INVERSEMATRIX.multiply(VelocityColumn).getColumn(0);
        return product;
    }

    @Override
    public double[] gamepadTranslate(double rawLeftX, double rawLeftY, double rawRightX, double rawRightY) {
        exponent = RobotConstants.ControlExponent;

        double LeftX = rawLeftX;
        double LeftY = rawLeftY;
        double RightX = rawRightX;
        double RightY = rawRightY;

        // Clip input
        LeftX = Range.clip(LeftX, -1, 1);
        LeftY = Range.clip(LeftY, -1, 1);
        RightX = Range.clip(RightX, -1, 1);
        RightY = Range.clip(RightY, -1, 1);

        // Apply logarithmic control scale
        LeftX = Math.signum(LeftX) * Math.pow(LeftX, exponent);
        LeftY = Math.signum(LeftY) * Math.pow(LeftY, exponent);
        RightX = Math.signum(RightX) * Math.pow(RightX, exponent);
        RightY = Math.signum(RightY) * Math.pow(RightY, exponent);

        // Round to 3 decimal places (Do not remove the decimal, it will cause floating point imprecision)
        LeftX = Math.round(LeftX * 1000)/1000.0;
        LeftY = Math.round(LeftY * 1000)/1000.0;
        RightX = Math.round(RightX * 1000)/1000.0;
        RightY = Math.round(RightY * 1000)/1000.0;

        return vectorTranslate(LeftY, LeftX, RightX);
    }
}
