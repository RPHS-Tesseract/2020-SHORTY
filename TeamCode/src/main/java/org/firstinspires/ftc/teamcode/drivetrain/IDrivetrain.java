/**
 * Jacob Bazata (jacobbazata@gmail.com)
 * 9/16/19
 */

package org.firstinspires.ftc.teamcode.drivetrain;

/* Translators have two purposes:
    1. Convert desired velocity vectors to motor power arrays
    2. Convert gamepad input into motor power arrays
 */

public interface IDrivetrain {
    double[] vectorTranslate(double longitudinal, double lateral, double yaw); // For autonomous
    double[] gamepadTranslate(double rawLeftX, double rawLeftY, double rawRightX, double rawRightY);
}