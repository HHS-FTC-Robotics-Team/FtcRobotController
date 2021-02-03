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

    private boolean incrementAngleButtonIsDown = false;
    private boolean decrementAngleButtonIsDown = false;

    private double shooterSpeed = 100;
    private double shooterAngle = 19;

    private String state = "drive";
    private float theta = 0.0f;

    private Sensors touchin;
    private Sensors touchout;
    private Sensors colorLeft;
    private Sensors colorRight;

    private Eyes cam1;
//    private Eyes cam2;

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
                0.7, 0.79);

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
//old                hardwareMap.get(WebcamName.class, "Webcam 1"), 8.0f, 0.625f, 2.42f //the correct offset for the center of the robot
                hardwareMap.get(WebcamName.class, "Webcam 1"), 8.75f, 0.5f, 1.5f //the offset for the shooter pivot
        );


//        cam2 = new Eyes(
//                hardwareMap.get(WebcamName.class, "Webcam 2"), 0.375f, -8.75f, 5.625f //TODO find the offsets for the second camera
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

            col.Release(); //Todo: Fix!

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


            if (gamepad1.dpad_up) {
                gear.incrementToPos("min");
            } else if (gamepad1.dpad_down) {
                gear.incrementToPos("max");
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
                        col.setPower(0.5 * gamepad2.left_stick_y);
                    }
                }
            } else /*if (gear.isPos("min"))*/  { //isPos will check if servo is at the specified goal, does not move servo like incrementToPos does
                col.rest();
            }

//            if (gamepad2.dpad_up) {
//                hpos = "zero";
//            } else if (gamepad2.dpad_right) {
//                hpos = "one";
//            } else if (gamepad2.dpad_down) {
//                hpos = "two";
//            } else if (gamepad2.dpad_left) {
//                hpos = "three";
//            }
//            hopper.incrementToPos(hpos);

//            if (gamepad2.y) {
//            hopper.out();
//            shooter.setPower(1);
//            } else {
//                hopper.rest();
//                shooter.rest();
//                //hopper.movePlatform(0.1);
//            }

            if (gamepad1.a && !turningButtonIsDown) {
                turningButtonIsDown = true;
                // auto rotation towards the goal ==================================================
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
                        float rotationAngle = (heading - theta);
                        if (rotationAngle >= -180) {
                            d.rotateToAngle(rotationAngle, -0.5); // TODO conditional does not work
                        } else if (rotationAngle < -180) {
                            d.rotateToAngle(rotationAngle, 0.5);
                        }
                    }
//                    else
//                        if (cam2.isTargetVisible()) {
//                        heading = cam2.getHeading() - 180;
//                        x = 72 - cam2.getPositionX();
//                        y = 36 - cam2.getPositionY();
//                        theta = (float) (Math.atan2(y, x) * (180/Math.PI));
//                        float rotationAngle = (heading - theta);
//                        if (rotationAngle >= -180) {
//                            d.rotateToAngle(rotationAngle, -0.5); // TODO conditional does not work
//                        } else if (rotationAngle < -180) {
//                            d.rotateToAngle(rotationAngle, 0.5);
//                        }
//                    }
                }
                // auto pivoting towards the goal ==================================================
                double distToGoal;

                if(cam1.isTargetVisible()) {
                    double x = 72 - cam1.getPositionX() + 1.125;
                    double y = 36 - cam1.getPositionY();
                    distToGoal = Math.sqrt((x * x) + (y * y));
                    shooterAngle = (0.00181854)*(distToGoal-106.033)*(distToGoal-106.033) + 26.5169;
                    shooter.pivotToAngle(shooterAngle);
                }
//                else {
//                if (cam2.isTargetVisible()) {
//                    double x = 72 - cam2.getPositionX() + 1.125;
//                    double y = 36 - cam2.getPositionY();
//                    distToGoal = Math.sqrt((x * x) + (y * y));
//                    shooterAngle = (0.00181854)*(distToGoal-106.033)*(distToGoal-106.033) + 26.5169;
//                    shooter.pivotToAngle(shooterAngle);
//                }
//                }
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
                if (shooterSpeed < 0.99) {
                    shooterSpeed += 0.01;
                } else if (shooterSpeed >= 0.99) {
                    shooterSpeed = 1.0;
                }
            } else if (!gamepad2.y) { incrementSpeedButtonIsDown = false; }

            if (gamepad2.a && !decrementSpeedButtonIsDown) { decrementSpeedButtonIsDown = true;
                if (shooterSpeed > 0.01) {
                    shooterSpeed -= 0.01;
                } else if (shooterSpeed <= 0.01) {
                    shooterSpeed = 0.0;
                }
            } else if (!gamepad2.a) { decrementSpeedButtonIsDown = false; }

            if (gamepad2.b) {
                shooter.setPower(shooterSpeed);
                hopper.out();
            } else if (!gamepad2.b) {
//                shooter.pivotToAngle(19);
                hopper.rest();
                shooter.rest();
            }

            //for testing on 2/3
            //Increment shoooter angle using gamepad 2 left and right bumpers

            if (gamepad2.right_bumper && !incrementAngleButtonIsDown) { incrementAngleButtonIsDown = true;
                if (shooterAngle < 29.5) {
                    shooterAngle += 0.5;
                } else if (shooterAngle >= 29.5) {
                    shooterAngle = 30;
                }
            } else if (!gamepad2.right_bumper) { incrementAngleButtonIsDown = false; }

            if (gamepad2.left_bumper && !decrementAngleButtonIsDown) { decrementAngleButtonIsDown = true;
                if (shooterAngle > 19.5) {
                    shooterAngle -= 0.5;
                } else if (shooterAngle <= 19.5) {
                    shooterAngle = 19;
                }
            } else if (!gamepad2.left_bumper) { decrementAngleButtonIsDown = false; }

            shooter.pivotToAngle(shooterAngle);

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
                telemetry.addData("Vuf 1 translation", cam1.getTranslation());
                telemetry.addData("Vuf 1 rotation", cam1.getRotation());
            }
//            if (cam2.isTargetVisible()) {
//                telemetry.addData("Vuf 2 translation", cam2.getTranslation());
//                telemetry.addData("Vuf 2 rotation", cam2.getRotation());
//            }
            telemetry.addData("Visible Target 1", cam1.isTargetVisible());
//            telemetry.addData("Visible Target 2", cam2.isTargetVisible());
            telemetry.addData("Drive state", state);
            telemetry.addData("Drive theta", theta);
            telemetry.addData("Hopper position", hopperPos);
            telemetry.addData("Shooter speed", shooterSpeed);
            telemetry.addData("Shooter angle", shooterAngle);
            telemetry.update();

        }
    }
}
