package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName; //for "eyes" init

@TeleOp(name="Teleop2021", group="Linear Opmode")

public class Teleop2021 extends LinearOpMode {

    //creating objects for all of the different parts
    private Drive d;

    private TwoPosServo claw; //the file this used to be is still called Foundation btw
    private TwoPosServo gear;
    private boolean clawButtonIsDown = false; // controls the claw servo button press
    private boolean gearboxButtonIsDown = false; // controls the gearbox servo button press
    private boolean turningButtonIsDown = false; // controls the gearbox servo button press

    // controls all of the d buttons
    private boolean dpadUpIsDown = false;
    private boolean dpadRightIsDown = false;
    private boolean dpadDownIsDown = false;
    private boolean dpadLeftIsDown = false;
    private String hopperPos = "three";
    private boolean incrementSpeedButtonIsDown = false;
    private boolean decrementSpeedButtonIsDown = false;
    private double shooterSpeed = 0;

    private String state = "drive";
    private float theta = 0.0f;

    private Sensors touchin;
    private Sensors touchout;
    private Sensors colorLeft;
    private Sensors colorRight;

    private Eyes cam1;
   // private Eyes cam2;

    private Collect col;
    private Hopper hopper;
    private Shooter shooter;

    private String hpos = "three";


    @Override
    public void runOpMode() {

        //initializing every motor, servo, and sensor
        //these names all need to match the names in the config
        d = new Drive(
                hardwareMap.get(DcMotor.class, "rbmotor"),
                hardwareMap.get(DcMotor.class, "rfmotor"),
                hardwareMap.get(DcMotor.class, "lfmotor"),
                hardwareMap.get(DcMotor.class, "lbmotor")
        );

        claw = new TwoPosServo(
                hardwareMap.get(Servo.class, "claw"),
                0.15, 0.54);
        gear = new TwoPosServo(
                hardwareMap.get(Servo.class, "gearbox"),
                0.74, 0.82);

        touchin = new Sensors(
                hardwareMap.get(TouchSensor.class, "touchin")
        );

        touchout = new Sensors(
                hardwareMap.get(TouchSensor.class, "touchout")
        );

        colorLeft = new Sensors(
                hardwareMap.get(ColorSensor.class, "colorleft")
        );

        colorRight = new Sensors(
                hardwareMap.get(ColorSensor.class, "colorright")
        );

        cam1 = new Eyes(
                hardwareMap.get(WebcamName.class, "Webcam 1"), 8.0f, 0.625f, 2.42f
        );

//        cam2 = new Eyes(
//                hardwareMap.get(WebcamName.class, "Webcam 2"), 0, 0, 0 //TODO find the offsets for the second camera
//        );

        col = new Collect(
                hardwareMap.get(DcMotor.class, "liftmotor"),
                hardwareMap.get(Servo.class, "release")
        );
        hopper = new Hopper(
                hardwareMap.get(DcMotor.class, "hoppermotor"),
                hardwareMap.get(Servo.class, "hopperservo")
        );
        shooter = new Shooter(
                hardwareMap.get(DcMotor.class, "shootertop"),
                hardwareMap.get(DcMotor.class, "shooterbottom"),
                hardwareMap.get(Servo.class, "pivotleft"),
                hardwareMap.get(Servo.class, "pivotright")
        );


        waitForStart();
        while (opModeIsActive()) {

//            if (-gamepad2.right_stick_y > 0) {
//                lift.up(Math.abs(gamepad2.right_stick_y));
//            } else if (-gamepad2.right_stick_y < 0){
//                lift.down(Math.abs(gamepad2.right_stick_y));
//            } else {
//                lift.rest();
//            }




            if (state == "rotate") {
                if (d.isBusy() == false) {
                    d.resetAllEncoders();
                    state = "drive";
                }
            } else if (state == "drive") {
                d.setPower(
                    gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x,
                    gamepad1.right_trigger
                );
            }


            if (gamepad2.x && !clawButtonIsDown) {
                clawButtonIsDown = true;
                claw.nextPos();
            } else if (!gamepad2.x) {
                clawButtonIsDown = false;
            }

            // This contains instructions for the collector and lift (lift is the stick)
            if (gamepad1.left_bumper) {
                if (gear.incrementToPos("min")) {
                    col.setPower(-1);
                }
            } else if (gamepad1.right_bumper) {
                if (gear.incrementToPos("min")) {
                    col.setPower(1);
                }
            } else if (gamepad2.left_stick_y != 0) {
                if (gear.incrementToPos("max")) { //incrementToPos will both move the gearbox servo AND return if it has reached the goal yet
                    if (!col.isBusy()) {
                        col.setPower(0.5 * gamepad2.right_stick_y);
                    }
                }
            } else if (gear.isPos("min"))  { //isPos will check if servo is at the specified goal, does not move servo like incrementToPos does
                col.rest();
            }

            if (gamepad2.dpad_up) {
                hpos = "zero";
            } else if (gamepad2.dpad_right) {
                hpos = "one";
            } else if (gamepad2.dpad_down) {
                hpos = "two";
            } else if (gamepad2.dpad_left) {
                hpos = "three";
            }
            hopper.incrementToPos(hpos);

            if (gamepad2.y) {
            hopper.out();
            shooter.setPower(1);
            } else {
                hopper.rest();
                shooter.rest();
                //hopper.movePlatform(0.1);
            }

            if (gamepad1.a && !turningButtonIsDown) {
                turningButtonIsDown = true;
                if (state == "rotate") {
                    state = "drive";
                } else {
                    state = "rotate";
                    d.resetAllEncoders();
                    float heading = 0f;
                    float x = 0f;
                    float y = 0f;
                    if(cam1.isTargetVisible()) { // TODO make getTheta method in Eyes
                        heading = cam1.getHeading() - 90;
                        x = 72 - cam1.getPositionX();
                        y = 36 - cam1.getPositionY();
                        theta = (float) (Math.atan2(y, x) * (180/Math.PI));
                        d.rotateToAngle((heading + theta), 0.25); // TODO now heading is weird fix it tomorrow
                    }
//                    else if (cam2.isTargetVisible()) {
//                        heading = cam2.getHeading() - 90;
//                        x = 72 - cam2.getPositionX();
//                        y = 36 - cam2.getPositionY();
//                        theta = (float) (Math.atan2(y, x) * (180/Math.PI));
//                        d.rotateToAngle((heading + theta), 0.25); // TODO now heading is weird fix it tomorrow
//                    }
                }
            } else if (!gamepad1.a) {
                turningButtonIsDown = false;
            }

            cam1.trackPosition(); // vuforia
//            cam2.trackPosition();

            //manually set the position of the hopper servo
            if (gamepad2.dpad_up && !dpadUpIsDown) { dpadUpIsDown = true;
                hopperPos = "zero";
            } else if (!gamepad2.dpad_up) { dpadUpIsDown = false; }

            if (gamepad2.dpad_right && !dpadRightIsDown) { dpadRightIsDown = true;
                hopperPos = "one";
            } else if (!gamepad2.dpad_right) { dpadRightIsDown = false; }

            if (gamepad2.dpad_down && !dpadDownIsDown) { dpadDownIsDown = true;
                hopperPos = "two";
            } else if (!gamepad2.dpad_down) { dpadDownIsDown = false; }

            if (gamepad2.dpad_left && !dpadLeftIsDown) { dpadLeftIsDown = true;
                hopperPos = "three";
            } else if (!gamepad2.dpad_left) { dpadLeftIsDown = false; }

            hopper.incrementToPos(hopperPos);

            // control the shooter motor speed using the A and Y buttons on gamepad 2.
            // after setting the speed, power the shooter by holding down the B button.
            // with the B button held down, select the proper hopper position on the dPad to insert a ring into the shooter.
            if (gamepad2.y && !incrementSpeedButtonIsDown) { incrementSpeedButtonIsDown = true;
                if (shooterSpeed < 0.95) {
                    shooterSpeed += 0.05;
                } else if (shooterSpeed >= 0.95) {
                    shooterSpeed = 1.0;
                }
            } else if (!gamepad2.y) { incrementSpeedButtonIsDown = false; }

            if (gamepad2.a && !decrementSpeedButtonIsDown) { decrementSpeedButtonIsDown = true;
                if (shooterSpeed > 0.05) {
                    shooterSpeed -= 0.05;
                } else if (shooterSpeed <= 0.05) {
                    shooterSpeed = 0.0;
                }
            } else if (!gamepad2.a) { decrementSpeedButtonIsDown = false; }

            if (gamepad2.b) {
                shooter.setPower(shooterSpeed);
                hopper.out();
            } else if (!gamepad2.b) {
                hopper.rest();
                shooter.rest();
            }


            telemetry.addData("Status", "Run Time: ");
            telemetry.addData("Motor Power", gamepad1.left_stick_y);
            telemetry.addData("Right Stick Pos", gamepad1.right_stick_y);
            telemetry.addData("Ly", gamepad1.left_stick_y);
            telemetry.addData("Lx", gamepad1.left_stick_x);
            telemetry.addData("Rx", gamepad1.right_stick_x);
            telemetry.addData("Clicks: ", d.getClickslf());
            telemetry.addData("lf", d.getPowerlf());
            telemetry.addData("lb", d.getPowerlb());
            telemetry.addData("rf", d.getPowerrf());
            telemetry.addData("rb", d.getPowerrb());
            telemetry.addData("touch in", touchin.getTouch());
            telemetry.addData("touch out", touchout.getTouch());
            if (cam1.isTargetVisible()) {
                telemetry.addData("Vuf translation", cam1.getTranslation());
                telemetry.addData("Vuf rotation", cam1.getRotation());
            }
            telemetry.addData("Visible Target", cam1.isTargetVisible()/* && cam2.isTargetVisible()*/);
            telemetry.addData("Drive state", state);
            telemetry.addData("Drive theta", theta);
            telemetry.addData("Hopper position", hopperPos);
            telemetry.addData("Shooter speed", shooterSpeed);
            telemetry.update();

        }
    }
}
