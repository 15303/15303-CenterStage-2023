package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class Teleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("fl");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("bl");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("fr");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("br");
        DcMotor rotator = hardwareMap.dcMotor.get("rotator");
        DcMotor lifter = hardwareMap.dcMotor.get("lifter");


        Servo launchServo = hardwareMap.servo.get("launcher");
        Servo grabberTilt = hardwareMap.servo.get("grabberTilt");
        Servo grabberR = hardwareMap.servo.get("grabberR");
        Servo grabberL = hardwareMap.servo.get("grabberL");

        boolean leftGrabberOpen = false;
        boolean rightGrabberOpen = false;

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;


            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);


            //
            // Game Pad #1
            //

            // launcher
            if (gamepad1.y) {
                launchServo.setPosition(0);
            }

            //
            // Game Pad #2

            // grabber tilt
            if (gamepad2.dpad_down) {       // put down grabbers
                grabberTilt.setPosition(0.1);

            }
            else if (gamepad2.dpad_up) {    // pull up grabbers
                grabberTilt.setPosition(1.0);
            }

            // grabber
            if (gamepad2.left_bumper) {
                if (leftGrabberOpen) {
                    // Close grabber
                    grabberL.setPosition(0);
                }
                else {
                    // Open grabber
                    grabberL.setPosition(1);

                }
                leftGrabberOpen = !leftGrabberOpen;
            }

            if (gamepad2.right_bumper) {
                if (rightGrabberOpen) {
                    // Close grabber
                    grabberR.setPosition(0);
                }
                else {
                    // Open grabber
                    grabberR.setPosition(1);

                }
                rightGrabberOpen = !rightGrabberOpen;
            }

            // arm rotation
            if (gamepad2.a){
                rotator.setPower(1);
            }
            else if(gamepad2.b){
                rotator.setPower(-1);
            }
            else {
                rotator.setPower(0);
            }

            // actuator extension and retraction
            if (gamepad2.x){
                lifter.setPower(1);
            }
            else if(gamepad2.y){
                lifter.setPower(-1);
            }
            else {
                lifter.setPower(0);
            }
        }
    }
}