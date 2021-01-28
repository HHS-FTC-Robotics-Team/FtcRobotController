package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

public class TwoPosServo extends LinearOpMode {

    private Servo servo;
    private double max; // Maximum rotational position
    private double min; // Minimum rotational position


    private String currentPos = "min";

    public TwoPosServo (Servo s, double mininput, double maxinput) {
        servo = s;
        max = maxinput;
        min = mininput;
    }

    public void minPos() {
        servo.setPosition(min);
    }
    public void maxPos() {
        servo.setPosition(max);
    }

    public void nextPos() {
        if(currentPos.equals("min")) {
            currentPos = "max";
            maxPos();
        } else if(currentPos.equals("max")) {
            currentPos = "min";
            minPos();
        }
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
        double increment = 0.06;
        double pos1 = servo.getPosition();
        if (pos1 > goal) {
            pos1 -= increment;
        } else if (pos1 < goal){
            pos1 += increment;
        } else if (pos1 > goal - error && pos1 < goal + error) { //TODO switch < and > signs if they're wrong
            pos1 = goal;
        }
        servo.setPosition(pos1);
        return pos1 > goal - error && pos1 < goal + error;
    }

    public String getState() {
        return currentPos;
    }

    public double getPos() {
        return servo.getPosition();
    }

    public boolean isPos(String g) { // is position equal to (min or max)?
        if (g == "max") {
            return servo.getPosition() == max;
        } else if (g == "min") {
            return servo.getPosition() == min;
        } else {
            return false;
        }
    }

    public void runOpMode() {

    }
}
