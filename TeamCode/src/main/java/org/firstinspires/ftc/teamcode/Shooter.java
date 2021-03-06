package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;

public class Shooter extends LinearOpMode {

    //init the two motors and distance sensor
//    private DcMotor Topmtr = null;
//   private DcMotor Bottommtr = null;
    private DcMotorEx Topmtr = null;
    private DcMotorEx Bottommtr = null;

    private Servo leftservo = null;
    private Servo rightservo = null;

    public Shooter (DcMotorEx l, DcMotorEx r, Servo sl, Servo sr) {
        Topmtr = l;
        Bottommtr = r;
        //direction for one is reversed so that
        Topmtr.setDirection(DcMotor.Direction.REVERSE);
        Bottommtr.setDirection(DcMotor.Direction.FORWARD);

        //servos move opposite
        leftservo = sl;
        rightservo = sr;
        Topmtr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Bottommtr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

//        //Control
//        Topmtr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        Bottommtr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void out() {
        Topmtr.setPower(-1);
        Bottommtr.setPower(1);
    }

    public void rest() {
        Topmtr.setPower(0);
        Bottommtr.setPower(0);
    }

    public void setPower(double p) {
        Topmtr.setPower(-p);
        Bottommtr.setPower(p);

    }

    public double getTopVelocity() {
        return Topmtr.getVelocity();
    }

    public void setSpeed(double p) {
        Topmtr.setVelocity(-p);
        Bottommtr.setVelocity(p);
    }
    public boolean isSpeedCorrect(double p) {
        return Topmtr.getVelocity() < -p+25 && Topmtr.getVelocity() > -p-25
                && Bottommtr.getVelocity() < p+25 && Bottommtr.getVelocity() > p-25;
    }

    public String getPower() {
        double lp = Topmtr.getPower();
        double rp = Bottommtr.getPower();
        String s = lp + " / " + rp;
        return s;
    }

    public void pivot(double goal) { // if goal = .1
        leftservo.setPosition(goal); // servo goes to .1
        rightservo.setPosition(Math.abs(goal - 1)); // servo goes to .9 because this servo is flipped
    }

    public void pivotToAngle(double goal) { // if goal = 40 degrees
        double minAngle = 19; // the angle of the shooter at the lowest
        double maxAngle = 30; // the angle of the shooter at the highest
        double minPos = 0.22; // the position of the servos at the lowest
        double maxPos = 0.42; // the position of the servos at the highest

        //convert an angle between min and max degrees into the proper servo position between min and max pos
        double goalPos = minAngle;
        if (goal > minAngle && goal < maxAngle) {
            goalPos = ( (goal-minAngle) / (maxAngle-minAngle) * (maxPos-minPos) ) + minPos;
        } else if (goal <= minAngle) {
            goalPos = minPos;
        } else if (goal >= maxAngle) {
            goalPos = maxPos;
        }

        leftservo.setPosition(goalPos);
        rightservo.setPosition(Math.abs(goalPos - 1));
    }

    public void runOpMode() {

    }
}
