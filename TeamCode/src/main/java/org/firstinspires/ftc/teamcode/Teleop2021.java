package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName; //for "eyes" init

@TeleOp(name="Teleop2021", group="Linear Opmode")

public class Teleop2021 extends LinearOpMode {

    //creating objects for all of the different parts
    private SciLift lift;

    private Drive d;

    private TwoPosServo claw; //the file this used to be is still called Foundation btw
    private TwoPosServo gear;
    private boolean clawButtonIsDown = false; // controls the claw servo button press
    private boolean gearboxButtonIsDown = false; // controls the gearbox servo button press
    private boolean turningButtonIsDown = false; // controls the gearbox servo button press
    private String state = "drive";
    private float theta = 0.0f;

    private Sensors touchin;
    private Sensors touchout;

    private Eyes cam1;
    private Eyes cam2;

    private Collect col;
    private Hopper hopper;
    private Shooter shooter;


    @Override
    public void runOpMode() {

        //initializing every motor, servo, and sensor
        //these names all need to match the names in the config
        lift = new SciLift(
                hardwareMap.get(DcMotor.class, "liftmotor")
        );

        d = new Drive(
                hardwareMap.get(DcMotor.class, "rbmotor"),
                hardwareMap.get(DcMotor.class, "rfmotor"),
                hardwareMap.get(DcMotor.class, "lfmotor"),
                hardwareMap.get(DcMotor.class, "lbmotor")
        );

        claw = new TwoPosServo(
                hardwareMap.get(Servo.class, "claw"),
                0.5, 1);
        gear = new TwoPosServo(
                hardwareMap.get(Servo.class, "gearbox"),
                0.5, 1);

        touchin = new Sensors(
                hardwareMap.get(DigitalChannel.class, "touchin")
        );

        touchout = new Sensors(
                hardwareMap.get(DigitalChannel.class, "touchout")
        );

        cam1 = new Eyes(
                hardwareMap.get(WebcamName.class, "Webcam 1")
        );

//        cam2 = new Eyes(
//                hardwareMap.get(WebcamName.class, "Webcam 2")
//        );

        col = new Collect(
                hardwareMap.get(DcMotor.class, "collectmotor"),
                hardwareMap.get(Servo.class, "release")
        );
        hopper = new Hopper(
                hardwareMap.get(DcMotor.class, "hoppermotor"),
                hardwareMap.get(Servo.class, "hopperservo")
        );
        shooter = new Shooter(
                hardwareMap.get(DcMotor.class, "shooterleft"),
                hardwareMap.get(DcMotor.class, "shooterright"),
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

            if (gamepad1.right_bumper) {
                lift.up(1);
            } else if (gamepad1.left_bumper){
                lift.down(1);
            } else {
                lift.rest();
            }




            if (state == "rotate") {

                 //else if(cam2.isTargetVisible()) {
//                    heading = cam2.getHeading();
//                    x = 72 - cam2.getPosition().get(0);
//                    y = 36 - cam2.getPosition().get(1);
//                    theta = (float) Math.tan(y/x);
//                }
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


            if (gamepad1.y && !clawButtonIsDown) {
                clawButtonIsDown = true;
                claw.nextPos();
            } else if (!gamepad1.y) {
                clawButtonIsDown = false;
            }

            if (gamepad1.x && !gearboxButtonIsDown) {
                gearboxButtonIsDown = true;
                gear.nextPos();
            } else if (!gamepad1.x) {
                gearboxButtonIsDown = false;
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
                        d.rotateToAngle((heading + theta), 0.25);
                    } // TODO now heading is weird fix it tomorrow
                }
            } else if (!gamepad1.a) {
                turningButtonIsDown = false;
            }

            cam1.trackPosition(); // vuforia
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
            telemetry.addData("Lift", lift.getClicks());
            telemetry.addData("touch in", touchin.getTouch());
            telemetry.addData("touch out", touchout.getTouch());
            if (cam1.isTargetVisible()) {
                telemetry.addData("Vuf translation", cam1.getTranslation());
                telemetry.addData("Vuf rotation", cam1.getRotation());
            }
            telemetry.addData("Visible Target", cam1.isTargetVisible()/* && cam2.isTargetVisible()*/);
            telemetry.addData("Drive state", state);
            telemetry.addData("Drive theta", theta);
            telemetry.update();

        }
    }
}
