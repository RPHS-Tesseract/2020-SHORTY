package org.firstinspires.ftc.teamcode.drivetrain;

/* This Translator will do nothing. */
public class NullDrivetrain implements IDrivetrain {
    public NullDrivetrain() {}

    @Override
    public double[] vectorTranslate(double longitudinal, double lateral, double yaw) {
        return new double[]{};
    }

    @Override
    public double[] gamepadTranslate(double rawLeftX, double rawLeftY, double rawRightX, double rawRightY) {
        return new double[]{};
    }
}
