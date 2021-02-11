//package org.firstinspires.ftc.teamcode.legacy;
//
//import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DigitalChannel;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName; //for "eyes" init
//import org.firstinspires.ftc.teamcode.Collect;
//import org.firstinspires.ftc.teamcode.Drive;
//import org.firstinspires.ftc.teamcode.Hopper;
//import org.firstinspires.ftc.teamcode.Shooter;
//
//@TeleOp(name="Ring Troubleshoot", group="Linear Opmode")
//@Disabled
//
//public class RingTroubleshoot extends LinearOpMode {
//
//    //creating objects for all of the different parts
//
//    private Drive d;
//
//    private boolean aButtonIsDown = false; // controls the a button press
//    private boolean bButtonIsDown = false; // controls the b button press
//    private boolean xButtonIsDown = false; // controls the x button press
//    private boolean yButtonIsDown = false; // controls the y button press
//
//
//  //  private Sensors touchin;
//   // private Sensors touchout;
//
//
//    private Collect col;
//    private Hopper hopper;
//    private Shooter shooter;
//
//
//    @Override
//    public void runOpMode() {
//
//        //initializing every motor, servo, and sensor
//        //these names all need to match the names in the config
//
//
//        d = new Drive(
//                hardwareMap.get(DcMotor.class, "rbmotor"),
//                hardwareMap.get(DcMotor.class, "rfmotor"),
//                hardwareMap.get(DcMotor.class, "lfmotor"),
//                hardwareMap.get(DcMotor.class, "lbmotor")
//        );
//
////        touchin = new Sensors(
////                hardwareMap.get(DigitalChannel.class, "touchin")
////        );
////
////        touchout = new Sensors(
////                hardwareMap.get(DigitalChannel.class, "touchout")
////        );
//
//
////        col = new Collect(
////                hardwareMap.get(DcMotor.class, "collectmotor"),
////                hardwareMap.get(Servo.class, "release")
////        );
//        hopper = new Hopper(
//                hardwareMap.get(DcMotor.class, "hoppermotor"),
//                hardwareMap.get(Servo.class, "hopperservo")
//        );
//        shooter = new Shooter(
//                hardwareMap.get(DcMotor.class, "shootertop"),
//                hardwareMap.get(DcMotor.class, "shooterbottom"),
//                hardwareMap.get(Servo.class, "pivotleft"),
//                hardwareMap.get(Servo.class, "pivotright")
//        );
//
//
//        waitForStart();
//        while (opModeIsActive()) {
//
////            if (-gamepad2.right_stick_y > 0) {
////                lift.up(Math.abs(gamepad2.right_stick_y));
////            } else if (-gamepad2.right_stick_y < 0){
////                lift.down(Math.abs(gamepad2.right_stick_y));
////            } else {
////                lift.rest();
////            }
//
////            if (gamepad1.right_bumper) {
////                lift.up(1);
////            } else if (gamepad1.left_bumper){
////                lift.down(1);
////            } else {
////                lift.rest();
////            }
//
//
//
//
////            if (state == "rotate") {
//
//                //else if(cam2.isTargetVisible()) {
////                    heading = cam2.getHeading();
////                    x = 72 - cam2.getPosition().get(0);
////                    y = 36 - cam2.getPosition().get(1);
////                    theta = (float) Math.tan(y/x);
////                }
////                if (d.isBusy() == false) {
////                    d.resetAllEncoders();
////                    state = "drive";
////                }
////            } else if (state == "drive") {
//                d.setPower(
//                        gamepad1.left_stick_y,
//                        gamepad1.left_stick_x,
//                        gamepad1.right_stick_x,
//                        gamepad1.right_trigger
//                );
////            }
//
//
//            if (gamepad1.y && !yButtonIsDown) {
//                yButtonIsDown = true;
//
//            } else if (!gamepad1.y) {
//                yButtonIsDown = false;
//            }
//
//            if (gamepad1.x && !xButtonIsDown) {
//                xButtonIsDown = true;
//
//            } else if (!gamepad1.x) {
//                xButtonIsDown = false;
//            }
//
//            if (gamepad1.b && !bButtonIsDown) {
//                bButtonIsDown = true;
//
//            } else if (!gamepad1.b) {
//                bButtonIsDown = false;
//            }
//
//            if (gamepad1.a && !aButtonIsDown) {
//                aButtonIsDown = true;
//
//            } else if (!gamepad1.a) {
//                aButtonIsDown = false;
//            }
//
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
//
////            if (gamepad1.a && !turningButtonIsDown) {
////                turningButtonIsDown = true;
////                if (state == "rotate") {
////                    state = "drive";
////                } else {
////                    state = "rotate";
////                    d.resetAllEncoders();
////                    float heading = 0f;
////                    float x = 0f;
////                    float y = 0f;
////                    if(cam1.isTargetVisible()) { // TODO make getTheta method in Eyes
////                        heading = cam1.getHeading() - 90;
////                        x = 72 - cam1.getPositionX();
////                        y = 36 - cam1.getPositionY();
////                        theta = (float) (Math.atan2(y, x) * (180/Math.PI));
////                        d.rotateToAngle((heading + theta), 0.25);
////                    } // TODO now heading is weird fix it tomorrow
////                }
////            } else if (!gamepad1.a) {
////                turningButtonIsDown = false;
////            }
//
////            cam1.trackPosition(); // vuforia
////            cam2.trackPosition();
//
//            telemetry.addData("Status", "Run Time: ");
//            telemetry.addData("Motor Power", gamepad1.left_stick_y);
//            telemetry.addData("Right Stick Pos", gamepad1.right_stick_y);
//            telemetry.addData("Ly", gamepad1.left_stick_y);
//            telemetry.addData("Lx", gamepad1.left_stick_x);
//            telemetry.addData("Rx", gamepad1.right_stick_x);
//            telemetry.addData("Clicks: ", d.getClickslf());
//            telemetry.addData("lf", d.getPowerlf());
//            telemetry.addData("lb", d.getPowerlb());
//            telemetry.addData("rf", d.getPowerrf());
//            telemetry.addData("rb", d.getPowerrb());
////            telemetry.addData("Lift", lift.getClicks());
////            telemetry.addData("touch in", touchin.getTouch());
////            telemetry.addData("touch out", touchout.getTouch());
////            if (cam1.isTargetVisible()) {
////                telemetry.addData("Vuf translation", cam1.getTranslation());
////                telemetry.addData("Vuf rotation", cam1.getRotation());
////            }
////            telemetry.addData("Visible Target", cam1.isTargetVisible()/* && cam2.isTargetVisible()*/);
////            telemetry.addData("Drive state", state);
////            telemetry.addData("Drive theta", theta);
//            telemetry.update();
//
//        }
//    }
//}
