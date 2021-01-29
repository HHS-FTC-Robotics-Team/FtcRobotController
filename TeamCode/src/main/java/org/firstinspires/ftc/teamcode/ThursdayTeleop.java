package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName; //for "eyes" init

@TeleOp(name="Thursday Teleop", group="Linear Opmode")

public class ThursdayTeleop extends LinearOpMode {

    //creating objects for all of the different parts

    private Drive d;

    private TwoPosServo claw; //the file this used to be is still called Foundation btw
    private TwoPosServo gear;
    private boolean clawButtonIsDown = false; // controls the claw servo button press
    private boolean gearboxButtonIsDown = false; // controls the gearbox servo button press

    private boolean aButtonIsDown = false; // controls the a button press
    private boolean bButtonIsDown = false; // controls the b button press
    private boolean xButtonIsDown = false; // controls the x button press
    private boolean yButtonIsDown = false; // controls the y button press

    // controls all of the d buttons
    private boolean dpadUpIsDown = false;
    private boolean dpadRightIsDown = false;
    private boolean dpadDownIsDown = false;
    private boolean dpadLeftIsDown = false;

    private String hpos = "zero";

    //  private Sensors touchin;
    // private Sensors touchout;


    private Collect col;
    private Hopper hopper;
    private Shooter shooter;


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

  //This is where you can change the range of the servos. Just change the minimum and maximum
  //servo values. Remember, only positive values for servo positions
        claw = new TwoPosServo(
                hardwareMap.get(Servo.class, "claw"),
                0.15, 0.54);
        gear = new TwoPosServo(
                hardwareMap.get(Servo.class, "gearbox"),
                0.74, 0.82);

//        touchin = new Sensors(
//                hardwareMap.get(DigitalChannel.class, "touchin")
//        );
//
//        touchout = new Sensors(
//                hardwareMap.get(DigitalChannel.class, "touchout")
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

//            if (gamepad1.right_bumper) {
//                lift.up(1);
//            } else if (gamepad1.left_bumper){
//                lift.down(1);
//            } else {
//                lift.rest();
//            }




//            if (state == "rotate") {

            //else if(cam2.isTargetVisible()) {
//                    heading = cam2.getHeading();
//                    x = 72 - cam2.getPosition().get(0);
//                    y = 36 - cam2.getPosition().get(1);
//                    theta = (float) Math.tan(y/x);
//                }
//                if (d.isBusy() == false) {
//                    d.resetAllEncoders();
//                    state = "drive";
//                }
//            } else if (state == "drive") {



            d.setPower(
                    gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x,
                    gamepad1.right_trigger
            );
//            }


            if (gamepad1.y && !yButtonIsDown) {
                yButtonIsDown = true;

            } else if (!gamepad1.y) {
                yButtonIsDown = false;
            }

            if (gamepad1.x && !xButtonIsDown) {
                xButtonIsDown = true;

            } else if (!gamepad1.x) {
                xButtonIsDown = false;
            }

            if (gamepad1.b && !bButtonIsDown) {
                bButtonIsDown = true;

            } else if (!gamepad1.b) {
                bButtonIsDown = false;
            }

            if (gamepad1.a && !aButtonIsDown) {
                aButtonIsDown = true;

            } else if (!gamepad1.a) {
                aButtonIsDown = false;
            }



            if (gamepad2.dpad_up && !dpadUpIsDown) {
                dpadUpIsDown = true;

            } else if (!gamepad2.dpad_up) {
                dpadUpIsDown = false;
            }

            if (gamepad2.dpad_right && !dpadRightIsDown) {
                dpadRightIsDown = true;

            } else if (!gamepad2.dpad_right) {
                dpadRightIsDown = false;
            }

            if (gamepad2.dpad_down && !dpadDownIsDown) {
                dpadDownIsDown = true;

            } else if (!gamepad2.dpad_down) {
                dpadDownIsDown = false;
            }

            if (gamepad2.dpad_left && !dpadLeftIsDown) {
                dpadLeftIsDown = true;

            } else if (!gamepad2.dpad_left) {
                dpadLeftIsDown = false;
            }

//
//            if (gamepad1.left_bumper) {
//                shooter.pivot(0.42);
//
//            } else if (!gamepad1.left_bumper) {
//                shooter.pivot(0.22);
//            }
//
//
//            if (gamepad1.right_bumper) {
//                hopper.out();
//
//            } else if (!gamepad1.right_bumper) {
//                hopper.rest();
//            }
//
            //Official controls for Thursday 1/28/20

           // these are the controls for the various shooter settings.
           // you can change the number in the parenthesis next to "setPower" to change
           // the motor power. Motor powers range from -1 to 1, negative power will
           // reverse direction.
            if (gamepad2.left_bumper) {
                hopper.out();
                shooter.setPower(0.5);

            } else if (gamepad2.right_bumper) {
                hopper.out();
                shooter.setPower(0.6);

            } else if (gamepad2.a) {
                hopper.out();
                shooter.setPower(0.7);

            } else if (gamepad2.b) {
                hopper.out();
                shooter.setPower(0.8);

            } else if (gamepad2.x) {
                hopper.out();
                shooter.setPower(0.9);

            } else if (gamepad2.y) {
                hopper.out();
                shooter.setPower(1);
               // hopper.movePlatform(0);

            } else {
                hopper.rest();
                shooter.rest();
                //hopper.movePlatform(0.1);
            }

            // This controls the d-pad controls. Change the "g" values to change the servo position
            // for each d-pad button.
//            if (gamepad2.dpad_up) {
//                hopper.movePlatform(0);
//            } else if (gamepad2.dpad_right) {
//                hopper.movePlatform(0.1);
//            } else if (gamepad2.dpad_down) {
//                hopper.movePlatform(0.16);
//            } else if (gamepad2.dpad_left) {
//                hopper.movePlatform(0.27);
//            }

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

            // This sets the shooter pivot servos to whatever value you hold the joystick to.
            if (gamepad2.left_stick_y < 0) {
                shooter.pivot(((-gamepad2.left_stick_y) * 0.2) + 0.22);
            }
            //hopper.movePlatform(0);


    // This instructs the claw and gearbox positions to switch when you press the appropriate button(s).
            // you change the actual servo positions above.
            if (gamepad1.x && !clawButtonIsDown) {
                clawButtonIsDown = true;
                claw.nextPos();
            } else if (!gamepad1.x) {
                clawButtonIsDown = false;
            }

            if (gamepad1.y && !gearboxButtonIsDown) {
                gearboxButtonIsDown = true;
                gear.minPos();
            } else if (!gamepad1.y) {
                gearboxButtonIsDown = false;
            }

//            if (gamepad1.a && !gearboxButtonIsDown) {
//                gearboxButtonIsDown = true;
//                gear.maxPos();
//            } else if (!gamepad1.a) {
//                gearboxButtonIsDown = false;
//            }

            //TODO the new stuff below ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            //TODO see the TODO things in this file, the Collect file, and the TwoPosServo file

         //    lift motor
            if (gamepad1.a) {
                if (gear.incrementToPos("max")) { //TODO test this (moves lift) ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    if (!col.isBusy()) {
                        col.nextLiftPosition();
                    }
                }
            }


            // This contains instructions for the collector
            if (gamepad1.left_bumper) {
                if (gear.incrementToPos("min")) { //TODO test this (collector only) ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    col.setPower(-1);
                }
            } else if (gamepad1.right_bumper) {
                if (gear.incrementToPos("min")) {
                    col.setPower(1);
                }
            } else if (gamepad2.right_stick_y != 0) {
                if (gear.incrementToPos("max")) { //incrementToPos will both move the gearbox servo AND return if it has reached the goal yet
                    if (!col.isBusy()) {
                        col.setPower(0.5 * gamepad2.right_stick_y);
                    }
                }
            } else if (gear.isPos("min"))  { //isPos will check if servo is at the specified goal, does not move servo like incrementToPos does
                col.rest();
            }

//            // Anish's primitive version:
//            if (gamepad1.left_bumper) {
//                if (gear.getPos() > 0.76) {
//                    gear.minPos();
//                } else if (gear.getPos() < 0.76) {
//                    col.in();
//                }
//            } else if (gamepad1.right_bumper) {
//                if (gear.getPos() > 0.76) {
//                    gear.minPos();
//                } else if (gear.getPos() < 0.76) {
//                    col.out();
//                }
//            } else if (gamepad2.right_stick_y != 0) {
//                if (gear.getPos() < 0.79) {
//                    gear.maxPos();
//                } else if (gear.getPos() > 0.79) {
//                    col.setPower(gamepad2.right_stick_y);
//                }
//            } else if (gamepad1.a) {
//                if (gear.getPos() > 0.79) {
//                    col.nextLiftPosition();
//                } else if (gear.getPos() < 0.79) {
//                    gear.maxPos();
//                }
//            } else {
//                col.rest();
//            }

//            if (gamepad1.a && !turningButtonIsDown) {
//                turningButtonIsDown = true;
//                if (state == "rotate") {
//                    state = "drive";
//                } else {
//                    state = "rotate";
//                    d.resetAllEncoders();
//                    float heading = 0f;
//                    float x = 0f;
//                    float y = 0f;
//                    if(cam1.isTargetVisible()) { // TODO make getTheta method in Eyes
//                        heading = cam1.getHeading() - 90;
//                        x = 72 - cam1.getPositionX();
//                        y = 36 - cam1.getPositionY();
//                        theta = (float) (Math.atan2(y, x) * (180/Math.PI));
//                        d.rotateToAngle((heading + theta), 0.25);
//                    } // TODO now heading is weird fix it tomorrow
//                }
//            } else if (!gamepad1.a) {
//                turningButtonIsDown = false;
//            }

//            cam1.trackPosition(); // vuforia
//            cam2.trackPosition();

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
            telemetry.addData("platform position", hopper.getPlatformPos());
            telemetry.addData("gearbox servo", gear.getPos());
            telemetry.addData("gearbox max?", gear.isPos("max"));
            telemetry.addData("motor power", col.getPower());
            telemetry.addData("Busy?", col.isBusy());
//            telemetry.addData("Lift", lift.getClicks());
//            telemetry.addData("touch in", touchin.getTouch());
//            telemetry.addData("touch out", touchout.getTouch());
//            if (cam1.isTargetVisible()) {
//                telemetry.addData("Vuf translation", cam1.getTranslation());
//                telemetry.addData("Vuf rotation", cam1.getRotation());
//            }
//            telemetry.addData("Visible Target", cam1.isTargetVisible()/* && cam2.isTargetVisible()*/);
//            telemetry.addData("Drive state", state);
//            telemetry.addData("Drive theta", theta);
            telemetry.update();

        }
    }
}
