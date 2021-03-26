package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "LimitSwitchTest", group = "Testing")
public class ButtonTest extends OpMode {
    private TouchSensor limitSwitch;

    @Override
    public void init() {
        limitSwitch = hardwareMap.get(TouchSensor.class, "LimitSwitch");
    }

    @Override
    public void loop() {
        telemetry.addData("isPressed()", "%s", limitSwitch.isPressed());
    }
}
