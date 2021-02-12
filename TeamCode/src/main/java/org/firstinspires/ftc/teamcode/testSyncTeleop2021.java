package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName; //for "eyes" init
import org.firstinspires.ftc.teamcode.states.ForwardUntil;
import org.firstinspires.ftc.teamcode.states.MoveClaw;
import org.firstinspires.ftc.teamcode.states.NoThoughtsHeadEmpty;
import org.firstinspires.ftc.teamcode.states.TurnUntilAngle;

@TeleOp(name="testSyncTeleop2021", group="Linear Opmode")

public class testSyncTeleop2021 extends LinearOpMode {
    public RobotHardware robotHardware = new RobotHardware();

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

    private double shooterSpeed = 2100;
    private double shooterAngle = 19;

    private String state = "drive";
    private float theta = 0.0f;

    private Sensors touchin;
    private Sensors touchout;
    private Sensors colorLeft;
    private Sensors colorRight;
    private String onMidline = "no";

    //    private Eyes cam1;
    private Eyes cam2;

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
                0.15, 0.56);
        gear = new TwoPosServo(
                hardwareMap.get(Servo.class, "gearbox"),
                0.7, 0.81);

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

//        cam1 = new Eyes(
////old                hardwareMap.get(WebcamName.class, "Webcam 1"), 8.0f, 0.625f, 2.42f //the correct offset for the center of the robot
//                hardwareMap.get(WebcamName.class, "Webcam 1"), 8.75f, 0.5f, 1.5f //the offset for the shooter pivot
//        );


        cam2 = new Eyes(
//                hardwareMap.get(WebcamName.class, "Webcam 2"), 0.375f, -8.5f, 5.625f
//                hardwareMap.get(WebcamName.class, "Webcam 2"), 1f, 8.125f, 4f //from the PIVOT, positive y is blue side
                hardwareMap.get(WebcamName.class, "Webcam 2"), -2.0625f, -8.125f, 4f //CLAW-SIDE, x from the PIVOT, positive y is blue side
        );

        col = new Collect(
                hardwareMap.get(DcMotor.class, "liftmotor"),
                hardwareMap.get(Servo.class, "release")
        );
        hopper = new Hopper(
                hardwareMap.get(DcMotor.class, "hoppermotor"),
                hardwareMap.get(Servo.class, "hopperservo")
        );
        shooter = new Shooter(
                hardwareMap.get(DcMotorEx.class, "shootertop"),
                hardwareMap.get(DcMotorEx.class, "shooterbottom"),
                hardwareMap.get(Servo.class, "pivotleft"),
                hardwareMap.get(Servo.class, "pivotright")
        );



        OurState[] syncStatesList =  {
                new TurnUntilAngle(90),
                new NoThoughtsHeadEmpty(),
        };
        SynchronousStack states = new SynchronousStack(syncStatesList);
        robotHardware.build(hardwareMap);
        states.init(robotHardware);

        states.init_loop();
        waitForStart();
        states.start();

        col.releaseCollector();

        while (opModeIsActive()) {

            states.loop();

            if (gamepad1.y && !incrementSpeedButtonIsDown) { incrementSpeedButtonIsDown = true;
                OurState[] x = {
                        new ForwardUntil(6)
                };
                states.addState(x);
            } else if (!gamepad1.y) { incrementSpeedButtonIsDown = false; }

            telemetry.addData("Running?", states.running);
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
//            if (cam1.isTargetVisible()) {
//                telemetry.addData("Vuf 1 translation", cam1.getTranslation());
//                telemetry.addData("Vuf 1 rotation", cam1.getRotation());
//            }
            if (cam2.isTargetVisible()) {
                telemetry.addData("Vuf 2 translation", cam2.getTranslation());
                telemetry.addData("Vuf 2 rotation", cam2.getRotation());
            }
//            telemetry.addData("Visible Target 1", cam1.isTargetVisible());
            telemetry.addData("Visible Target 2", cam2.isTargetVisible());
            telemetry.addData("Drive state", state);
            telemetry.addData("Drive theta", theta);
            telemetry.addData("Hopper position", hopperPos);
            telemetry.addData("Shooter speed", shooterSpeed);
            telemetry.addData("Shooter angle", shooterAngle);
            telemetry.addData("On Midline?", onMidline);
            telemetry.update();

        }
        states.stop();
    }
}
