package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Hawk TeleOp")
public class DriveTrain extends LinearOpMode {
    @Override
    public void runOpMode() {
        // These names must match the active robot configuration.
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");    // Port 0
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");      // Port 1
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");    // Port 2
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");  // Port 3

        // Start with the right-side motors reversed for mirrored mounting.
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);   // Port 0
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);    // Port 1
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);   // Port 2
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);  // Port 3

        DcMotor[] driveMotors = {frontLeft, backLeft, backRight, frontRight};
        for (DcMotor motor : driveMotors) {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        telemetry.addLine("Ready: left stick drives, right stick turns");
        telemetry.update();
        waitForStart();

        try {
            while (opModeIsActive()) {
                double drive = -gamepad1.left_stick_y;
                double turn = gamepad1.right_stick_x;

                double leftPower = drive + turn;
                double rightPower = drive - turn;
                double maxPower = Math.max(1.0, Math.max(Math.abs(leftPower), Math.abs(rightPower)));

                frontLeft.setPower(leftPower / maxPower);
                backLeft.setPower(leftPower / maxPower);
                backRight.setPower(rightPower / maxPower);
                frontRight.setPower(rightPower / maxPower);
            }
        } finally {
            for (DcMotor motor : driveMotors) {
                motor.setPower(0);
            }
        }
    }
}
