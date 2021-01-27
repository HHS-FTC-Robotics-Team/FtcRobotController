package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;

public class Shooter extends LinearOpMode {

    //init the two motors and distance sensor
    private DcMotor Topmtr = null;
    private DcMotor Bottommtr = null;
    private Servo leftservo = null;
    private Servo rightservo = null;

    public Shooter (DcMotor l, DcMotor r, Servo sl, Servo sr) {
        Topmtr = l;
        Bottommtr = r;
        //direction for one is reversed so that
        Topmtr.setDirection(DcMotor.Direction.REVERSE); // TODO: try to find the built in PID stuff
        Bottommtr.setDirection(DcMotor.Direction.FORWARD); // TODO: runWithEncoders or something
        //servos move opposite
        leftservo = sl;
        rightservo = sr;
    }

    public void out() { // TODO: find speeds and find which motor should be reverse
        Topmtr.setPower(-1);
        Bottommtr.setPower(-1);
    }

    public void rest() {
        Topmtr.setPower(0);
        Bottommtr.setPower(0);
    }

    public void setPower(double p) {
        Topmtr.setPower(p);
        Bottommtr.setPower(p);
    }

    public String getPower() {
        double lp = Topmtr.getPower();
        double rp = Bottommtr.getPower();
        String s = lp + " / " + rp;
        return s;
    }

    public void pivot(double goal) { // if goal = .1
        leftservo.setPosition(goal); // servo goes to .1
        rightservo.setPosition(Math.abs(goal - 1)); // servo goes to .9
    }


    public void runOpMode() {

    }
}
