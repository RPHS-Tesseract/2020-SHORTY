package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

@TeleOp(name = "Hardware Test", group = "Testing")
public class HardwareTest extends LinearOpMode {
    private static final Class<HardwareDevice>[] TestGroups = new Class[]{
        DcMotor.class
    };

    private Telemetry tele;

    @Override
    public void runOpMode() {
        List<DcMotor> dcmotors = hardwareMap.getAll(DcMotor.class);
        tele = telemetry;

        waitForStart();

        for (int i = 0; i < dcmotors.size(); i++) {
            DcMotor currentMotor = dcmotors.get(i);
            tele.addData("Currently running", "%s, %s", currentMotor.getDeviceName(), currentMotor.getConnectionInfo());
            tele.update();
            currentMotor.setPower(1);
            try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
            currentMotor.setPower(0);
        }
    }
}
