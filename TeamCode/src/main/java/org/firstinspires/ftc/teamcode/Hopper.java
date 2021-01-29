package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;

public class Hopper extends LinearOpMode {

    //init the two motors and distance sensor
    private DcMotor wheelmotor = null;
    private Servo platformservo = null;

    public Hopper (DcMotor m, Servo s) {
        wheelmotor = m;
        //servos move opposite
        platformservo = s;
    }

//    public void in() { // TODO: find speeds
//        wheelmotor.setPower(1);
//    }

    public void out() { // TODO: find speeds
        wheelmotor.setPower(-1);
    }

    public void rest() {
        wheelmotor.setPower(0);
    }

    public void setPower(double p) {
        wheelmotor.setPower(p);
    }

    public double getPower() {
        return wheelmotor.getPower();
    }

    public void movePlatform(double g) { //move to given pos
        platformservo.setPosition(g);
    }

    public void incrementPlatform() {
        double x = platformservo.getPosition();
        platformservo.setPosition(x + 0.1); //TODO: find what 0.1 should actually be
    }

    public void decrementPlatform() {
        double x = platformservo.getPosition();
        platformservo.setPosition(x - 0.1); //TODO: find what 0.1 should actually be
    }

    public double getPlatformPos() {
        return platformservo.getPosition();
    }

    public boolean incrementToPos(String g) {
        double goal;
        if (g == "max") {
            goal = max;
        } else if (g == "min") {
            goal = min;
        } else {
            goal = min;
        }
        double error = 0.05;
        double increment = 0.01; //TODO if this is too fast make the number smaller
        double pos1 = servo.getPosition();
        if (pos1 > goal) {
            pos1 -= increment;
        } else if (pos1 < goal){
            pos1 += increment;
        } else if (pos1 > goal - error && pos1 < goal + error) { //TODO switch < and > signs if they're wrong
            //   } else if (pos1 < goal - error && pos1 > goal + error) { //TODO switch < and > signs if they're wrong
            pos1 = goal;
        }
        servo.setPosition(pos1);
        return pos1 > goal - error && pos1 < goal + error;
    }

    public void runOpMode() {

    }
}
