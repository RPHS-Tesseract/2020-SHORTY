/**
 * Jacob Bazata (jacobbazata@gmail.com)
 * 9/16/19
 */

package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.RobotCore;
import org.firstinspires.ftc.teamcode.drivetrain.HolonomicDrivetrain;
import org.firstinspires.ftc.teamcode.util.gamepad.ButtonWrapper;

@TeleOp(name="SHORTY")
public class PrimaryTeleOp extends OpMode {
    private FtcDashboard dashboard;
    private Telemetry tele;

    private RobotCore robot;
    private RobotConstants constants;

    private ButtonWrapper gamepad1_ButtonA;

    // Loop state
    private long lastLauncherUpdate;
    private byte launcherDir = -1;
    private double launcherSpeed = 0;

    @Override
    public void init() {
        dashboard = FtcDashboard.getInstance();
        tele = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        // Visualize scale: https://www.desmos.com/calculator/0xtjtrmqfk
        robot = new RobotCore(hardwareMap, HolonomicDrivetrain.class);
        robot.registerDefaults();

        gamepad1_ButtonA = ButtonWrapper.wrap(gamepad1,"a");
        lastLauncherUpdate = (long) robot.timer.milliseconds();

        gamepad1.setJoystickDeadzone(0.02f);
    }
    @Override
    public void loop() {
        robot.gamepadDrive(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_trigger - gamepad1.left_trigger, gamepad1.right_stick_y);
        updateLauncher();

        launcherDir = (byte) (gamepad1.a ? 1 : (gamepad1.b ? -1 : launcherDir));

        /*if (gamepad1.a) {
            launcherDir = 1;
            robot.AcceleratorFlag.setPosition(0.5);
        }

        if (gamepad1.b) {
            launcherDir = -1;
            robot.AcceleratorFlag.setPosition(0);
        }*/

        if (gamepad1.left_stick_button && gamepad1.y) System.exit(69);

        byte beltDir = (byte) (gamepad1.left_bumper ? -1 : (gamepad1.right_bumper ? 1 : 0));

        robot.launcher.setPower(launcherSpeed);
        robot.spinner.setPower(launcherSpeed);
        robot.belt.setPower(beltDir);

        tele.addData("LS:", "X[%.3f] Y[%.3f]", gamepad1.left_stick_x, gamepad1.left_stick_y);
        tele.addData("RS:", "X[%.3f] Y[%.3f]", gamepad1.right_stick_x, gamepad1.right_stick_y);
        tele.addData("Launcher", "speed %.0f", launcherSpeed * 100);
        tele.addData("Servo", "%.3f", robot.AcceleratorFlag.getController().getServoPosition(robot.AcceleratorFlag.getPortNumber()));
        tele.update();
    }


    private void updateLauncher() {
        long currentMillis = (long) robot.timer.milliseconds();
        if (currentMillis > lastLauncherUpdate + constants.Launcher_MillisPerUnitVelocity ) {
            lastLauncherUpdate = currentMillis;
            launcherSpeed = Range.clip(launcherSpeed + Math.signum(launcherDir) * 0.01, 0, 1);
        }
    }
}
